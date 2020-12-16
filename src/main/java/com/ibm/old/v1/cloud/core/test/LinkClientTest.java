package com.ibm.old.v1.cloud.core.test;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.core.controller.mq.*;
import org.junit.Test;
/**
 * @Description: 测试登陆mq
 * @Author: Dongming
 * @Date: 2019-03-06 17:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LinkClientTest extends CommTest {

	@Test public void test() throws Exception {

		try {

			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);

			String handicapMemberId = "39ea7abed94e4608847ccd1d13d1bc59";
			String gameId = "2ddb654f1c44497c879dab19298dd186";

			//			openClient(handicapMemberId);

			//			gameBetInfoSet(handicapMemberId, gameId);
			//			gameBetStateSet(handicapMemberId, gameId);
			//			betRateSet(handicapMemberId);
			//			gameBetStateSet(handicapMemberId, gameId);
			//
			//			loginClient(handicapMemberId);
			//			logonClient(handicapMemberId);
			//			loginTimeClient(handicapMemberId);

			//			closeClient(handicapMemberId);
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

	private void gameBetDetailSet(String handicapMemberId, String gameId) throws Exception {
		CloudExecutor controller = new SetGameBetDetailController();
		System.out.println(JsonThreadLocal.bean2json(controller.execute( handicapMemberId,gameId)));
	}

	private void gameBetStateSet(String handicapMemberId, String gameId) throws Exception {
		CloudExecutor controller = new SetGameBetStateController();
		System.out.println(JsonThreadLocal.bean2json(controller.execute( handicapMemberId,gameId,"TRUE")));
	}
	private void gameBetInfoSet(String handicapMemberId, String gameId) throws Exception {
		CloudExecutor controller = new SetGameBetInfoController();
		System.out.println(JsonThreadLocal.bean2json(controller.execute(handicapMemberId, gameId)));

	}

	private void logonClient(String handicapMemberId) throws Exception {
		CloudExecutor controller = new LogonClientController();
		System.out.println(JsonThreadLocal.bean2json(controller.execute(handicapMemberId)));
	}

	private void loginClient(String handicapMemberId) throws Exception {
		CloudExecutor controller = new LoginClientController();
		System.out.println(JsonThreadLocal.bean2json(controller.execute(handicapMemberId)));
	}

	public void openClient(String handicapMemberId) throws Exception {
		CloudExecutor controller = new OpenClientController();
		System.out.println(controller.execute(handicapMemberId));
	}

	private void closeClient(String handicapMemberId) throws Exception {
		CloudExecutor controller = new CloseClientController();
		System.out.println(controller.execute(handicapMemberId));
	}
}
