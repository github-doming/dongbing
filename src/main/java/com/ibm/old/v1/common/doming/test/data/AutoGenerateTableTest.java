package com.ibm.old.v1.common.doming.test.data;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import org.doming.core.tools.DateTool;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
/**
 * @Description: 根据日期生成表名
 * @Author: Dongming
 * @Date: 2019-05-13 11:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AutoGenerateTableTest extends CommTest {

	@Test public void test() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			//根据日期生成表名
			autoGenerateTable();

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
	private void autoGenerateTable() throws SQLException {
		IbmExecBetItemTService betItemTService = new IbmExecBetItemTService();
		Date nowTime = new Date();
		for (int i = 0; i < 6 ;i++) {
			String day = DateTool.getDay(DateTool.getAfterDays(nowTime, i));
			betItemTService.generateBakTable(day);
		}
	}
}
