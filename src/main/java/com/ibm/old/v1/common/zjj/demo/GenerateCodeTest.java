package com.ibm.old.v1.common.zjj.demo;
import c.a.tools.jdbc.IJdbcTool;
import c.a.util.core.test.CommTest;
import com.ibm.common.test.BaseGen;
import org.junit.Test;
/**
 * @Description: 只创建service和实体类
 * @Author: zjj
 * @Date: 2019-08-28 14:50
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class GenerateCodeTest extends CommTest {
	@Test public void createServiceAndEntity(){
		BaseGen gen = new BaseGen();
		String folderPath = "d:\\gen" ;
		IJdbcTool jdbcTool = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);

			gen.genSingleSimple(folderPath, "ibm.servlet.ibm_event_close_client", "com.ibm.follow.servlet.ibm_event_close_client",
					"com.ibm.follow.servlet", "t", "ibm_event_close_client");

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
