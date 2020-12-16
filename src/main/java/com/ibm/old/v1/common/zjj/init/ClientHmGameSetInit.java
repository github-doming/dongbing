package com.ibm.old.v1.common.zjj.init;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.entity.IbmClientHmGameSetT;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.service.IbmClientHmGameSetTService;
import org.junit.Test;

import java.util.Date;

public class ClientHmGameSetInit extends CommTest{
	@Test
	public void demo() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			gameSet();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}

	}

	private void gameSet() throws Exception {
		IbmClientHmGameSetT ibmClientHmGameSetT=new IbmClientHmGameSetT();
		IbmClientHmGameSetTService ibmClientHmGameSetTService=new IbmClientHmGameSetTService();
		ibmClientHmGameSetT.setClientExistHmId("db285b6668804fd486a31c5fca5fce4c");
		ibmClientHmGameSetT.setHandicapMemberId("f22a3e4c56cc4612b68d991ab2391a71");
		ibmClientHmGameSetT.setGameId("2ddb654f1c44497c879dab19298dd186");
		ibmClientHmGameSetT.setGameCode("XYFT");
		ibmClientHmGameSetT.setBetSecond(120);
		ibmClientHmGameSetT.setCreateTime(new Date());
		ibmClientHmGameSetT.setCreateTimeLong(ibmClientHmGameSetT.getCreateTime().getTime());
		ibmClientHmGameSetT.setState("OPEN");
		ibmClientHmGameSetTService.save(ibmClientHmGameSetT);
		
	}

}
