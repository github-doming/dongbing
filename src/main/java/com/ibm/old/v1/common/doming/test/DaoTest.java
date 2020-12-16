package com.ibm.old.v1.common.doming.test;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemMain;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.junit.Test;

import java.util.List;
import java.util.Map;
/**
 * @Description: ceshi
 * @Author: Dongming
 * @Date: 2019-08-05 11:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DaoTest extends CommTest {
	@Test public void test() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);

			PlanTool.Code plan = PlanTool.Code.valueOf("LOCATION_BET_NUMBER");
			IbmPlanItemMain planItem = new IbmPlanItemService().findPlanItem(plan, "1ece29ffd8864df0be048858d9ab31b3");

			System.out.println(planItem);
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

	@Test public void test01(){
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			String handicapMemberId = "a315610d705c459dab52fcedfaed7ca1";
			String gameCode = "PK10";

			String handicapId = new IbmHandicapMemberTService().findHandicapId(handicapMemberId);
			String gameId = GameTool.findId(gameCode);
			String tableName = HandicapGameTool
					.getTableNameById(gameId, handicapId);


			Object[] periods = PeriodTool.findHistoryPeriod(IbmGameEnum.valueOf(gameCode), 2);
			//获取当期投注总额和当期投注总数
			List<Map<String, Object>> betResults =  new IbmExecBetItemTService()
					.findBetResult(handicapMemberId, tableName, periods);
			if (ContainerTool.notEmpty(betResults)) {
				for (Map<String, Object> betResult : betResults) {
					betResult.put("betAmount", NumberTool.doubleT(betResult.remove("betAmountT")));
					betResult.put("profit", NumberTool.doubleT(betResult.remove("profitT")));
				}
			}

			System.out.println(betResults);




			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
}
