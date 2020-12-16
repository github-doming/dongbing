package com.ibm.old.v1.common.doming.test.data;
import c.a.tools.jdbc.IJdbcTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;
import org.doming.core.common.thread.BaseCommThread;

import java.util.List;
import java.util.Map;
/**
 * @Description: 异库迁移
 * @Author: Dongming
 * @Date: 2019-05-10 17:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ExecBetItemCleaningThread extends BaseCommThread {

	@Override public String execute(String ignore) throws Exception {
		int migrationNum = 10000;

		long endTime = System.currentTimeMillis() - 5 * PeriodTool.XYFT_PERIOD;
		IbmExecBetItemTService betItemTService = new IbmExecBetItemTService();
		int migrationCnt = betItemTService.findMigrationCnt(endTime);

		//进行缓慢迁移-每次1000
		for (int i = 0; i < migrationCnt; i += migrationNum) {
			/*
				// 同库迁移
				1.全部迁移
				betItemTService.migration(endTime, i, migrationNum);
				2.以id迁移
				List<String> migrationIdList = betItemTService.listMigrationId(endTime, migrationNum);
				betItemTService.migration(migrationIdList);
			 */

			// 异库迁移
			List<Map<String, Object>> migrationInfoList = betItemTService.listMigrationInfo(endTime, migrationNum);
			otherMigration(migrationInfoList);

			CurrentTransaction.transactionCommit();
		}

		betItemTService.clearRedundancy(endTime);
		log.info("同库迁移完成，等待下一次迁移");
		return null;
	}
	private void otherMigration(List<Map<String, Object>> migrationInfoList) {
		TransactionsBase db = CurrentTransaction.getDataBase();
		IJdbcTool lastJdbcTool = db.transactionStart("migration");
		try {
			IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
			execBetItemTService.otherMigration(migrationInfoList);
		} catch (Exception e) {
			db.transactionRoll();
		} finally {
			db.transactionFinish(lastJdbcTool);
		}
	}

}
