package com.ibm.old.v1.common.doming.test;
import c.a.util.core.test.CommTest;
import com.alibaba.fastjson.JSONArray;
import com.ibm.old.v1.cloud.ibm_plan.t.entity.IbmPlanT;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemT;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.entity.IbmPlanUserT;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import com.ibm.old.v1.pc.ibm_plan_user.t.controller.IbmPlanUserController;
import org.doming.core.tools.StringTool;
import org.junit.Test;

import java.util.*;
/**
 * @Description: 方案测试类
 * @Author: Dongming
 * @Date: 2018-12-21 15:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PlanTest extends CommTest {

	@Test public void test() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			Collection<Map<String, Object>> list = new IbmPlanUserController()
					.listHomeGamePlanOrder("5b630e6bf92e4693a52f85919e698d19");

			System.out.println(new JSONArray(Arrays.asList(list.toArray())));
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

	private JsonResultBeanPlus initUserPlan(String userId) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		AppUserService appUserService = new AppUserService();
		String userType = appUserService.findType(userId);
		if (StringTool.isEmpty(userType)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		IbmPlanTService planTService = new IbmPlanTService();
		List<IbmPlanT> planTList = planTService.listByType(userType);

		IbmPlanUserTService planUserTService = new IbmPlanUserTService();
		IbmPlanUserT planUserT;

		for (IbmPlanT planT : planTList) {
			planUserT = planUserTService.findByUserIdAndTableName(userId, planT.getPlanItemTableName());
			if (planUserT != null) {
				continue;
			}

			IbmPlanItemT planItemT = new IbmPlanItemService().initPlanItem(planT.getCode(), userId);
			planUserT = new IbmPlanUserT();
			planUserT.setPlanId(planT.getIbmPlanId());
			planUserT.setPlanItemTableName(planT.getPlanItemTableName());
			planUserT.setPlanItemTableId(planItemT.getId());
			planUserT.setAppUserId(userId);
			planUserT.setGameId(planT.getGameId());
			planUserT.setPlanName(planT.getPlanName());
			planUserT.setProfitLimitMaxT(0);
			planUserT.setLossLimitMinT(0);
			planUserT.setPlanIcon(planT.getPlanIcon());
			planUserT.setMonitorPeriod(planItemT.getMonitorPeriod());
			planUserT.setBetMode(planItemT.getBetMode());
			planUserT.setOperateFrequency(0);
			planUserT.setCreateTime(new Date());
			planUserT.setCreateTimeLong(planUserT.getCreateTime().getTime());
			planUserT.setUpdateTime(new Date());
			planUserT.setUpdateTimeLong(planUserT.getUpdateTime().getTime());
			planUserT.setState(planItemT.getState());
			planUserTService.save(planUserT);
		}
		bean.success();
		return bean;
	}
}
