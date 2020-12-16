package com.ibm.old.v1.common.doming.test;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.core.job.manage.DataCleaningJob;
import com.ibm.old.v1.cloud.core.thread.plan.CodingPlanItemThread;
import com.ibm.old.v1.cloud.core.thread.plan.TurnPlanGroupThread;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.core.tool.XYFTTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.junit.Test;

import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-25 17:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JobTest extends CommTest {

	@Test public void testSettle() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			String gameId = "2ddb654f1c44497c879dab19298dd186" ;
			String handicapId = "5b0bf47af4e249a2b2fe56ba37f1d0d8" ;
			String period = "20190426-007" ;
			String tableName = "ibm_exec_bet_item_xyft" ;
			IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
			Map<String, List<Map<String, Object>>> execBetItemMap = execBetItemTService
					.mapExecBetItemInfo(period, tableName);
			System.out.println(JSONObject.fromObject(execBetItemMap));

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}

	}

	@Test public void testDataCleaning() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);

			new DataCleaningJob().executeJob(null);

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

	@Test public void test01() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);

			String gameId = GameTool.findId(IbmGameEnum.XYFT.name());

			//已开启的游戏方案
			List<Map<String, Object>> planInfo = new IbmPlanTService().listInfoByGameId(gameId);
			if (ContainerTool.isEmpty(planInfo)) {
				return;
			}

			String period = "20190805-069";
			//已开启的盘口id数组
			List<String> handicapIds = new IbmHandicapTService().listOpenId();

			if (ContainerTool.isEmpty(handicapIds)) {
				return;
			}
			XYFTTool.find20HistoryData(period);
			XYFTTool.findHotAndCold(period);
			for (Map<String, Object> plan : planInfo) {
				for (String handicapId : handicapIds) {
					new CodingPlanItemThread(gameId, plan.get("IBM_PLAN_ID_").toString(), handicapId, period,
							IbmGameEnum.XYFT, PlanTool.Code.valueOf(plan.get("PLAN_CODE_").toString())).execute("");

				}
			}
			super.doTransactionPost();
			for (Map<String, Object> plan : planInfo) {
				for (String handicapId : handicapIds) {
					new TurnPlanGroupThread(gameId, plan.get("IBM_PLAN_ID_").toString(), handicapId, period,
							IbmGameEnum.XYFT, PlanTool.Code.valueOf(plan.get("PLAN_CODE_").toString())).execute("");

				}
			}
			System.out.println("finish");
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

}
