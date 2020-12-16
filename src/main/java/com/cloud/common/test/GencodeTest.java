package com.cloud.common.test;
import com.cloud.common.core.BaseGen;
import com.cloud.common.core.CommTest;
import org.junit.Test;
/**
 * 生成代码
 * @Author: Dongming
 * @Date: 2020-05-19 17:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GencodeTest extends CommTest {

	@Test public void cloud() {
		super.transactionBegin();
		String folderPath = "d:\\gen";
		String uri = "cloud";
		String fun = "";
		String packageServer = "com.cloud.user";
		try {
			BaseGen gen = new BaseGen();

			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "user_login_log");

			System.out.println("end");
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
}
