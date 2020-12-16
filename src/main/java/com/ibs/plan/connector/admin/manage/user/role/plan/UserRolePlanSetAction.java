package com.ibs.plan.connector.admin.manage.user.role.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @Description: 角色方案设置
 * @Author: admin1
 * @Date: 2020/6/22 15:58
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/role/plan/set")
public class UserRolePlanSetAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String roleId = StringTool.getString(dataMap, "roleId", "");
		String planUsable = StringTool.getString(dataMap, "planUsable", "");
		try {
			IbspExpRoleService expRoleService = new IbspExpRoleService();
			IbspExpRole expRole = expRoleService.find(roleId);
			if (expRole == null) {
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			updateUserPlan(expRole, planUsable);
			expRole.setPlanCodes(planUsable);
			expRoleService.update(expRole);

			//修改角色类型用户的可用方案信息
			IbspExpUserService expUserService=new IbspExpUserService();
			expUserService.updateAvailablePlan(expRole.getIbspExpRoleId(),planUsable);

			bean.success();
		} catch (Exception e) {
			log.error("获取计划列表", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}

	private void updateUserPlan(IbspExpRole expRole, String planUsable) throws SQLException {

		List<String> oldList = Arrays.asList(expRole.getPlanCodes().split(","));
		List<String> newList = Arrays.asList(planUsable.split(","));
		//新方案codes-原方案codes----需要添加方案
//		Set<String> saveUserPlanCode = new HashSet<>((Collection<? extends String>) ContainerTool.getDifferent(newList, oldList));

		//原方案codes-新方案codes----需要删除方案
		Set<String> delUserPlanCode = new HashSet<>((Collection<? extends String>) ContainerTool.getDifferent(oldList, newList));

		IbspPlanUserService planUserService = new IbspPlanUserService();
		IbspPlanService planService = new IbspPlanService();
		//获取方案的基本信息

		IbspExpUserService expUserService = new IbspExpUserService();
		List<Map<String, Object>> expUserList = expUserService.findInfoByRoleId(expRole.getIbspExpRoleId());

		Map<String, Object> planInfo;
//		if (ContainerTool.notEmpty(saveUserPlanCode)&&ContainerTool.notEmpty(expUserList)) {
//			IbspGameService gameService = new IbspGameService();
//			// 方案信息 k 方案CODE v 方案详情
//			Map<String, Map<String, Object>> planInfos = planService.findPlanInfo(saveUserPlanCode);
//			List<Map<String, Object>> planItems;
//
//			// 方案游戏信息
//			Map<String, List<Object>> planGames = new IbspPlanGameService().listInfoByCodes(saveUserPlanCode);
//			List<Object> planGameIds;
//
//			//获取方案初始化信息
//			Map<Object, Map<String, Object>> initPlanInfo = new IbspPlanItemService().findInitInfo(saveUserPlanCode);
//
//			Set<String> gameCodes;
//			List<String> gameIds;
//			if (ContainerTool.notEmpty(planGames) && ContainerTool.notEmpty(initPlanInfo)) {
//				for (Map<String, Object> expUserInfo : expUserList) {
//					String userId = expUserInfo.get("APP_USER_ID_").toString();
//					//获取可用的方案游戏,在结合可用游戏进行过滤
//					gameCodes = new HashSet<>(Arrays.asList(expUserInfo.get("AVAILABLE_GAME_").toString().split(",")));
//					gameIds = gameService.findIds(gameCodes);
//
//					for (Map.Entry<String, List<Object>> entry : planGames.entrySet()) {
//						String planCode = entry.getKey();
//						planGameIds = entry.getValue();
//
//						planInfo = planInfos.get(planCode);
//						//初始化方案详情信息
//						planItems = planService.savePlanItem(planInfo, planGameIds, gameIds, userId, initPlanInfo.get(planCode));
//						for(Map<String,Object> planItem:planItems){
//							String snStr = planUserService.findUserPlanMaxSn(userId, planItem.get("GAME_ID_").toString());
//							int sn = StringTool.isEmpty(snStr) ? 1 : NumberTool.getInteger(snStr)+1;
//							planItem.put("SN_",sn);
//						}
//						planUserService.saveRegister(planCode, planInfo, planItems, userId);
//					}
//				}
//			}
//		}
		//删除方案 PLAN_ID_,PLAN_NAME_,PLAN_CODE_,PLAN_ITEM_TABLE_NAME_
		if (ContainerTool.notEmpty(delUserPlanCode)&&ContainerTool.notEmpty(expUserList)) {
			List<String> userIds = new ArrayList<>();
			Map<String, Map<String, Object>> planInfos = planService.findPlanInfo(delUserPlanCode);
			List<String> planIds = new ArrayList<>();
			for (Map<String, Object> expUserInfo : expUserList) {
				String userId = expUserInfo.get("APP_USER_ID_").toString();
				userIds.add(userId);
			}
			for (String planCode : delUserPlanCode) {
				planInfo = planInfos.get(planCode);
				planIds.add(planInfo.get("PLAN_ID_").toString());
				// 清理相关方案表信息
				planUserService.clearUserPlanInfo(userIds, planInfo.get("PLAN_ID_").toString(), planInfo.get("PLAN_ITEM_TABLE_NAME_").toString());
			}
			for (String userId : userIds) {
				// 清理ibsp_plan_user
				planUserService.clearPlanUser(userId, planIds);
			}
		}

	}


}
