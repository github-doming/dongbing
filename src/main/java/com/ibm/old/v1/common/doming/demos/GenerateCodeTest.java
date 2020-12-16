package com.ibm.old.v1.common.doming.demos;
import c.a.tools.jdbc.IJdbcTool;
import c.a.util.core.test.CommTest;
import c.x.platform.root.compo.gencode.util.common.BaseGen;
import org.junit.Test;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-06-10 17:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GenerateCodeTest extends CommTest {

	@Test public void createIbmEvent() {
		BaseGen gen = new BaseGen();
		String folderPath = "d:\\gen" ;
		IJdbcTool jdbcTool = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);

			gen.gen_singleSimple(folderPath, "ibm.servlet.plan_statistics", "com.ibm.old.v1.servlet.ibm_plan_statistics",
					"com.ibm.old.v1.servlet.ibm_plan_statistics", "t", "IBM_EVENT_PLANSTATISTICS");

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
		System.out.println("end");
	}
}
