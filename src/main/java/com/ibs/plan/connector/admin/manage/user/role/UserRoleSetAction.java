package com.ibs.plan.connector.admin.manage.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.manage.user.AppUserPlanSetAction;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import com.ibs.plan.module.cloud.ibsp_exp_user.entity.IbspExpUser;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.*;

/**
 * @Description: 用户角色设置
 * @Author: null
 * @Date: 2020-08-18 15:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/role/set",method = HttpConfig.Method.POST)
public class UserRoleSetAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserId = StringTool.getString(dataMap, "appUserId", "");
		String roleCode = StringTool.getString(dataMap, "roleCode", "");


		if(roleCode.split(",").length!=1){
			bean.putEnum(CodeEnum.IBS_401_ROLE_ERROR);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			IbspExpRoleService expRoleService = new IbspExpRoleService();
			IbspExpRole expRole = expRoleService.findByCode(roleCode);
			if(expRole==null){
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			IbspExpUserService expUserService = new IbspExpUserService();
			IbspExpUser expUser = expUserService.findByUserId(appUserId);
			if (expUser == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Date nowTime=new Date();
			// 初始化会员盘口
			Set<String> handicapCodes = new HashSet<>(Arrays.asList(expUser.getAvailableHandicap().split(",")));
			//获取用户拥有的所有盘口codes
			IbspHmUserService hmUserService = new IbspHmUserService();
			List<String> allHandicapCodes = hmUserService.findHandicapCodeByUserId(appUserId);
			for (String handicapCode : allHandicapCodes) {
				handicapCodes.remove(handicapCode);
			}
			//获取用户可开启的盘口信息
			if(ContainerTool.notEmpty(handicapCodes)){
				List<Map<String, Object>> handicaps = new IbspHandicapService().listInfoByCodes(handicapCodes);
				if (ContainerTool.notEmpty(handicaps)) {
					hmUserService.saveRegister(handicaps, appUserId, expRole.getHmOnlineMax(), nowTime);
				}
			}
			//更新方案
			AppUserPlanSetAction.updatePlan(expUser, expRole.getPlanCodes());

			//修改角色类型
			expUser.setExpRoleId(expRole.getIbspExpRoleId());
			expUser.setAvailableGame(expRole.getGameCodes());
			expUser.setAvailablePlan(expRole.getPlanCodes());
			expUser.setAvailableHandicap(expRole.getHandicapCodes());
			expUser.setOnlineMax(expRole.getOnlineMax());
			expUser.setCreateUser(appUser.getNickname());
			expUser.setUpdateTime(nowTime);
			expUser.setUpdateTimeLong(nowTime.getTime());
			expUserService.update(expUser);

			bean.success();
		} catch (Exception e) {
			log.error("获取计划列表", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
