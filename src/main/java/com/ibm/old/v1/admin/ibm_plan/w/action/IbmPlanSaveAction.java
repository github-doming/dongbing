package com.ibm.old.v1.admin.ibm_plan.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_plan.t.entity.IbmPlanT;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.entity.IbmPlanHmT;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemT;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
/**
 * @Description: 保存方案
 * @Author: zjj
 * @Date: 2019-08-13 13:58
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmPlanSaveAction extends BaseAppAction {
	@Override public String run() throws Exception {
		//方案ID
		String id = request.getParameter("IBM_PLAN_ID_");
		//方案名称
		String planName = request.getParameter("PLAN_NAME_");
		//游戏ID
		String[] gameIds = request.getParameterValues("GAME_ID_");
		//方案CODE
		String planCode = request.getParameter("PLAN_CODE_");
		//方案CODE
		String planItemTableName = request.getParameter("PLAN_ITEM_TABLE_NAME_");
		//方案说明
		String planExplain = request.getParameter("PLAN_EXPLAIN_");
		//方案类型
		String planType = request.getParameter("PLAN_TYPE_");
		//方案价值
		String planWorth = request.getParameter("PLAN_WORTH_T_");

		IbmPlanTService planTService = new IbmPlanTService();
		if (ContainerTool.isEmpty(gameIds)) {
			if (StringTool.notEmpty(id)) {
				planTService.update(id, planName, null, planItemTableName, planExplain, planType, planWorth,
						this.getClass().getName());
			}
			return CommViewEnum.Default.toString();
		}
		Date nowTime = new Date();
		for (String gameId : gameIds) {
			String planId = planTService.findId(gameId, planCode);
			if (StringTool.notEmpty(planId)) {
				planTService.update(planId, planName, planItemTableName, planExplain, planType, planWorth,
						this.getClass().getName());
			} else {
				IbmPlanT planT = new IbmPlanT();
				planT.setPlanName(planName);
				planT.setPlanCode(planCode);
				planT.setPlanItemTableName(planItemTableName);
				planT.setGameId(gameId);
				planT.setPlanExplain(planExplain);
				planT.setPlanType(planType);
				planT.setPlanWorthT(Integer.parseInt(planWorth));
				planT.setState(IbmStateEnum.OPEN.name());
				planT.setCreateTime(nowTime);
				planT.setCreateTimeLong(System.currentTimeMillis());
				planT.setUpdateTime(nowTime);
				planT.setUpdateTimeLong(System.currentTimeMillis());
				planId = planTService.save(planT);
				planT.setIbmPlanId(planId);

				AppUserService appUserService = new AppUserService();
				List<String> userIds = appUserService.listIdByType(planType);
				if (ContainerTool.isEmpty(userIds)) {
					continue;
				}
				IbmPlanItemService planItemService = new IbmPlanItemService();
				IbmPlanItemT planItemT;
				IbmPlanUserTService planUserTService = new IbmPlanUserTService();
				String planUserId;

				IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();

				IbmPlanHmTService planHmTService = new IbmPlanHmTService();
				IbmPlanHmT planHmT;

				for (String userId : userIds) {
					planItemT = planItemService.initPlanItem(planT.getCode(), userId);
					// 初始化盘口详情
					planUserId = planUserTService.save(planT, planItemT, userId);
					List<String> handicapMemberIds = handicapMemberTService.listIdByUserId(userId);
					if (ContainerTool.isEmpty(handicapMemberIds)) {
						continue;
					}
					for (String handicapMemberId : handicapMemberIds) {
						planHmT = new IbmPlanHmT();
						planHmT.setPlanId(planId);
						planHmT.setHandicapMemberId(handicapMemberId);
						planHmT.setPlanUserId(planUserId);
						planHmT.setGameId(gameId);
						planHmT.setPlanItemTableId(planItemT.getId());
						planHmT.setCreateTime(nowTime);
						planHmT.setCreateTimeLong(System.currentTimeMillis());
						planHmT.setUpdateTime(nowTime);
						planHmT.setUpdateTimeLong(System.currentTimeMillis());
						planHmT.setState(IbmStateEnum.CLOSE.name());
						planHmTService.save(planHmT);
					}
				}
			}
		}

		return CommViewEnum.Default.toString();
	}
}
