package com.ibm.old.v1.common.doming.test.handicap;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.client.core.job.bet.BetProfitWs2Job;
import com.ibm.old.v1.client.core.job.login.LoginHandicapWs2Job;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import org.junit.Test;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-03-29 10:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetProfitWs2JobTest   extends CommTest {

	@Test public void test() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);

			betProfitWs2();

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
	private void betProfitWs2() throws Exception {
		String existHmId = "0947e25555e046159f0c90c93a9c1fdb";
		String memberAccount = "hgdb-0001";
		String gameCode = 	"PK10";
		String period = 	"731415";
		JsonResultBeanPlus bean  = new LoginHandicapWs2Job().execute(existHmId,memberAccount);
		System.out.println("登陆结果="+bean.toJsonString());
		if (bean.isSuccess()){
			bean = 	new BetProfitWs2Job().execute(existHmId,gameCode,period);
			System.out.println("盈利信息="+bean.toJsonString());
		}


	}
}
