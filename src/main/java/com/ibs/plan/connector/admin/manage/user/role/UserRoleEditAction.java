package com.ibs.plan.connector.admin.manage.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.*;

/**
 * 角色信息修改
 *
 * @Author: Dongming
 * @Date: 2020-05-07 15:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/role/edit", method = HttpConfig.Method.POST)
public class UserRoleEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String expRoleId = StringTool.getString(dataMap, "expRoleId", "");
		String roleName = StringTool.getString(dataMap, "roleName", "");
		int roleLevel = NumberTool.getInteger(dataMap, "roleLevel", 0);
		String gameCodes = StringTool.getString(dataMap, "gameCodes", "");
		String handicapCodes = StringTool.getString(dataMap, "handicapCodes", "");
		int hmOnlineMax = NumberTool.getInteger(dataMap, "hmOnlineMax", 0);
		int onlineMax = NumberTool.getInteger(dataMap, "onlineMax", 0);
		String state = StringTool.getString(dataMap, "state", "");
		if (StringTool.isEmpty(expRoleId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		Date nowTime = new Date();
		try {
			IbspExpRoleService expRoleService = new IbspExpRoleService();
			IbspExpRole expRole = expRoleService.find(expRoleId);
			if (expRole == null) {
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			expRole.setRoleName(roleName);
			expRole.setRoleLevel(roleLevel);
			expRole.setGameCodes(gameCodes);
			expRole.setHandicapCodes(handicapCodes);
			expRole.setHmOnlineMax(hmOnlineMax);
			expRole.setOnlineMax(onlineMax);
			expRole.setState(state);
			expRole.setUpdateUser(appUserId);
			expRole.setUpdateTime(nowTime);
			expRole.setUpdateTimeLong(System.currentTimeMillis());
			expRole.setState(state);
			expRoleService.update(expRole);

			//修改对应角色用户
			IbspHmUserService hmUserService = new IbspHmUserService();
			IbspHandicapService handicapService = new IbspHandicapService();
			IbspExpUserService expUserService = new IbspExpUserService();
			Map<String, Object> expUserMap = expUserService.findByRoleId(expRoleId);
			List<String> expUserIds = new ArrayList<>();
			LogoutHmController logoutHmController = new LogoutHmController();

			List<String> newHandicaps=Arrays.asList(handicapCodes.split(","));
			Map<Object, Object> memberInfo=new HashMap<>(8);

			for(String handicapCode:newHandicaps){
				memberInfo.put(handicapCode,hmOnlineMax);
			}

			List<String> expHandicapCodes;
			Set<String> saveUserPlanCode;
			Set<Object> delUserPlanCode;
			List<Map<String, Object>> handicaps;
			for (Map.Entry<String, Object> entry : expUserMap.entrySet()) {
				expUserIds.add(entry.getKey());
				expHandicapCodes = hmUserService.findHandicapCodeByUserId(entry.getValue().toString());
				//新盘口codes-原盘口codes----需要添加盘口
				saveUserPlanCode = new HashSet<>((Collection<? extends String>) ContainerTool.getDifferent(newHandicaps, expHandicapCodes));
				if(ContainerTool.notEmpty(saveUserPlanCode)){
					handicaps = handicapService.findHandicap(saveUserPlanCode);
					hmUserService.save(handicaps, entry.getValue().toString(), memberInfo);
				}
				//原盘口codes-新盘口codes----需要删除盘口
				delUserPlanCode = new HashSet<>(ContainerTool.getDifferent(expHandicapCodes, newHandicaps));
				if(ContainerTool.notEmpty(delUserPlanCode)){
					handicaps = hmUserService.findHandicap(entry.getValue().toString(), delUserPlanCode);
					for (Map<String, Object> handicap : handicaps) {
						if (StringTool.notEmpty(handicap.get("ONLINE_MEMBERS_IDS_"))) {
							for (String handicapMemberId : handicap.get("ONLINE_MEMBERS_IDS_").toString().split(",")) {
								logoutHmController.execute(handicapMemberId, nowTime);
							}
						}
						hmUserService.updateByAppUserId(entry.getValue().toString(), handicap.get("HANDICAP_ID_").toString());
					}
				}
			}
			if (ContainerTool.notEmpty(expUserIds)) {
				expUserService.updateByIds(expRole, expUserIds);
			}

			bean.success();
		} catch (Exception e) {
			log.error("用户角色信息修改错误");
			throw e;
		}
		return bean;
	}
}
