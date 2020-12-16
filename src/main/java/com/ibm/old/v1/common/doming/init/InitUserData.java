package com.ibm.old.v1.common.doming.init;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.pc.app_user.t.controller.AppUserController;
import org.junit.Test;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-01-10 17:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InitUserData extends CommTest {

	/**
	 * 初始化用户数据
	 *
	 */
	@Test public void test() {

		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			initAvailableTime();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
	private void initAvailableTime() throws Exception {
		String userId = "5b630e6bf92e4693a52f85919e698d19";
		new AppUserController().initAvailableTime(userId);

	}


}
