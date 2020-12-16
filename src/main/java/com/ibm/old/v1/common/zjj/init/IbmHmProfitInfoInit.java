package com.ibm.old.v1.common.zjj.init;

import c.a.util.core.test.CommTest;
import org.junit.Test;

public class IbmHmProfitInfoInit extends CommTest{
	@Test
	public void demo(){
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
//			profitInfo();
//			profitInfoVr();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
		
	}
//	private void profitInfoVr() throws Exception {
//		Date nowTime=new Date();
//		IbmHmProfitInfoVrTService ibmHmProfitInfoTService=new IbmHmProfitInfoVrTService();
//		IbmHmProfitInfoVrT ibmHmProfitInfoT=new IbmHmProfitInfoVrT();
//		ibmHmProfitInfoT.setHandicapId("15200330aa69441793eb35fc5ecacd83");
//		ibmHmProfitInfoT.setAppUserId("5b630e6bf92e4693a52f85919e698d19");
//		ibmHmProfitInfoT.setHandicapMemberId("f22a3e4c56cc4612b68d991ab2391a71");
//		ibmHmProfitInfoT.setBalanceT(10000);
//		ibmHmProfitInfoT.setNumberT(100);
//		ibmHmProfitInfoT.setPreT(100);
//		ibmHmProfitInfoT.setBetTotalT(1111);
//		ibmHmProfitInfoT.setBetNumberT(22);
//		ibmHmProfitInfoT.setProfitLimitMaxT(1000);
//		ibmHmProfitInfoT.setLossLimitMinT(2000);
//		ibmHmProfitInfoT.setCreateTime(nowTime);
//		ibmHmProfitInfoT.setCreateTimeLong(nowTime.getTime());
//		ibmHmProfitInfoTService.save(ibmHmProfitInfoT);
//	}
	//真实投注init
//	private void profitInfo() throws Exception {
//		Date nowTime=new Date();
//		IbmHmProfitInfoTService ibmHmProfitInfoTService=new IbmHmProfitInfoTService();
//		IbmHmProfitInfoT ibmHmProfitInfoT=new IbmHmProfitInfoT();
//		ibmHmProfitInfoT.setHandicapId("15200330aa69441793eb35fc5ecacd83");
//		ibmHmProfitInfoT.setAppUserId("5b630e6bf92e4693a52f85919e698d19");
//		ibmHmProfitInfoT.setHandicapMemberId("f22a3e4c56cc4612b68d991ab2391a71");
//		ibmHmProfitInfoT.setBalanceT(10000);
//		ibmHmProfitInfoT.setNumberT(100);
//		ibmHmProfitInfoT.setPreT(100);
//		ibmHmProfitInfoT.setBetTotalT(1000);
//		ibmHmProfitInfoT.setBetNumberT(20);
//		ibmHmProfitInfoT.setProfitLimitMaxT(1000);
//		ibmHmProfitInfoT.setLossLimitMinT(2000);
//		ibmHmProfitInfoT.setCreateTime(nowTime);
//		ibmHmProfitInfoT.setCreateTimeLong(nowTime.getTime());
//		ibmHmProfitInfoTService.save(ibmHmProfitInfoT);
//	
//	}
}
