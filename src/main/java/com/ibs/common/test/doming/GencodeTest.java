package com.ibs.common.test.doming;
import com.ibs.common.core.BaseGen;
import com.ibs.common.core.CommTest;
import org.junit.Test;
/**
 * 生成代码
 * @Author: null
 * @Date: 2020-05-19 17:56
 * @Version: v1.0
 */
public class GencodeTest extends CommTest {

	@Test public void cloud() {
		super.transactionBegin();
		String folderPath = "d:\\gen";
		String uri = "ibs";
		String fun = "";
		String packageServer = "com.ibs.plan.module.cloud";
		try {
			BaseGen gen = new BaseGen();

			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "ibsp_sys_feedback");
			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "ibsp_sys_feedback_result");

			System.out.println("end");
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
}
