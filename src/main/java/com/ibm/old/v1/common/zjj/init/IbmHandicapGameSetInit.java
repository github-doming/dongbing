package com.ibm.old.v1.common.zjj.init;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_game.t.entity.IbmGameT;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.entity.IbmHandicapGameT;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.entity.IbmHmGameSetT;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class IbmHandicapGameSetInit extends CommTest{
	@Test
	public void demo(){
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
//			GameInit();
//			HandicapGameInit();
			GameSetInit();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
		
	}

	private void HandicapGameInit() throws Exception {
		Date nowTime= new Date();
		IbmHandicapGameTService ibmHandicapGameTService=new IbmHandicapGameTService();
		IbmHandicapGameT ibmHandicapGameT=new IbmHandicapGameT();
		ibmHandicapGameT.setHandicapId("15200330aa69441793eb35fc5ecacd83");
		ibmHandicapGameT.setGameId("b46a8d234555401aa57bacb89e439216");
		ibmHandicapGameT.setHandicapName("泰源");
		ibmHandicapGameT.setHandicapCode("WS2");
		ibmHandicapGameT.setGameName("极速时时彩");
		ibmHandicapGameT.setGameCode("JISUSSC");
		ibmHandicapGameT.setSn(5);
		ibmHandicapGameT.setCreateTime(nowTime);
		ibmHandicapGameT.setCreateUser("doming");
		ibmHandicapGameT.setCreateTimeLong(nowTime.getTime());
		ibmHandicapGameT.setUpdateTime(nowTime);
		ibmHandicapGameT.setUpdateTimeLong(nowTime.getTime());
		ibmHandicapGameT.setUpdateUser("doming");
		ibmHandicapGameT.setState("OPEN");
		ibmHandicapGameTService.save(ibmHandicapGameT);
		
	}

	private void GameInit() throws Exception {
		Date nowTime= new Date();
		IbmGameTService ibmGameTService=new IbmGameTService();
		IbmGameT ibmGameT=new IbmGameT();
		ibmGameT.setGameName("极速时时彩");
		ibmGameT.setGameCode("JISUSSC");
		ibmGameT.setRepGrabTableName("IBM_REP_GRAB_JISUSSC");
		ibmGameT.setRepDrawTableName("IBM_REP_DRAW_JISUSSC");
		ibmGameT.setSn(5);
		ibmGameT.setCreateUser("doming");
		ibmGameT.setCreateTime(nowTime);
		ibmGameT.setCreateTimeLong(nowTime.getTime());
		ibmGameT.setUpdateUser("doming");
		ibmGameT.setUpdateTime(nowTime);
		ibmGameT.setUpdateTimeLong(nowTime.getTime());
		ibmGameT.setCreateTimeLong(nowTime.getTime());
		ibmGameT.setState("OPEN");
		ibmGameTService.save(ibmGameT);
		
	}
	
	

	private void GameSetInit() throws Exception {
		IbmHandicapGameTService ibmHandicapGameTService=new IbmHandicapGameTService();
		List<Map<String, Object>> gameList=ibmHandicapGameTService.findAll();
		
		Date nowTime=new Date();
		IbmHmGameSetTService ibmHmGameSetTService=new IbmHmGameSetTService();
		IbmHmGameSetT ibmHmGameSetT=new IbmHmGameSetT();
		for(Map<String, Object> map:gameList){
			ibmHmGameSetT=new IbmHmGameSetT();
			ibmHmGameSetT.setHandicapMemberId("f22a3e4c56cc4612b68d991ab2391a71");
			ibmHmGameSetT.setHandicapGameId((String)map.get("IBM_HANDICAP_GAME_ID_"));
			ibmHmGameSetT.setHandicapId((String)map.get("HANDICAP_ID_"));
			ibmHmGameSetT.setUserId("5b630e6bf92e4693a52f85919e698d19");
			ibmHmGameSetT.setGameId((String)map.get("GAME_ID_"));
			ibmHmGameSetT.setBetState("TRUE");
			ibmHmGameSetT.setBetMode("REAL");
			ibmHmGameSetT.setBetAutoStop("OPEN");
			ibmHmGameSetT.setBetAutoStart("OPEN");
			ibmHmGameSetT.setIncreaseState("CLOSE");
			ibmHmGameSetT.setBetSecond(100);
			ibmHmGameSetT.setSplitTwoSideAmount(0);
			ibmHmGameSetT.setSplitNumberAmount(0);
			ibmHmGameSetT.setCreateTime(nowTime);
			ibmHmGameSetT.setCreateTimeLong(nowTime.getTime());
			ibmHmGameSetT.setUpdateTime(nowTime);
			ibmHmGameSetT.setUpdateTimeLong(nowTime.getTime());
			ibmHmGameSetT.setState("OPEN");
			ibmHmGameSetTService.save(ibmHmGameSetT);
		}
		
		
		
		
		
	}
}
