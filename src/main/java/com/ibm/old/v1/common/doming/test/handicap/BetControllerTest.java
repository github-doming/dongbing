package com.ibm.old.v1.common.doming.test.handicap;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.client.core.controller.bet.BetWS2Controller;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.service.IbmClientExistHmTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import net.sf.json.JSONObject;
import org.junit.Test;
/**
 * @Description: 测试投注controller
 * @Author: Dongming
 * @Date: 2019-03-25 17:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetControllerTest extends CommTest {

	@Test public void test() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);

			betInWs2Controller();

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
	private void betInWs2Controller() throws Exception {
		String existHmId = "850fada4507b4788b68f6010de901e12";

		IbmClientExistHmTService hmExistTService = new IbmClientExistHmTService();
		IbmClientExistHmT existHmT = hmExistTService.find(existHmId);
		JSONObject msgObj = JSONObject.fromObject(
				"{\"clientExistHmId\":\"850fada4507b4788b68f6010de901e12\",\"period\":\"20190326-040\",\"gameId\":\"2ddb654f1c44497c879dab19298dd186\",\"betInfo\":[{\"BET_CONTENT_\":\"第二名|2\",\"IBM_EXEC_BET_ITEM_ID_\":\"1d34c23734004b3db98d024433406a1e\",\"FUND_T_\":20000},{\"BET_CONTENT_\":\"第一名|1#第一名|6#第一名|9\",\"IBM_EXEC_BET_ITEM_ID_\":\"4ab5acce02114044ad6c4398cf4f316c\",\"FUND_T_\":20000}],\"handicapId\":\"15200330aa69441793eb35fc5ecacd83\",\"messageReceiptBetId\":\"bda39da4e36844a1af926af3c3e60a99\"}");

		JsonResultBeanPlus bean = new BetWS2Controller().execute(msgObj, existHmT);
		System.out.println(bean.toJsonString());

	}
}
