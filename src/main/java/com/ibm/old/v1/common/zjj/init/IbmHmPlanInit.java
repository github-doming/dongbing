package com.ibm.old.v1.common.zjj.init;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_plan.t.entity.IbmPlanT;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import org.junit.Test;

import java.util.Date;

public class IbmHmPlanInit extends CommTest{
	@Test
	public void demo(){
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			ibmHmPlan();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
		
	}

	private void ibmHmPlan() throws Exception {

		IbmPlanTService planTService=new IbmPlanTService();
		IbmPlanT planT=new IbmPlanT();
		planT.setPlanName("跟上期双面");
		planT.setPlanCode("FOLLOW_TWO_SIDE");
		planT.setPlanItemTableName("IBM_PI_FOLLOW_TWO_SIDE");
		planT.setPlanType("FREE");
		planT.setPlanWorthT(0);
		planT.setSn(2);
		planT.setCreateUser("doming");
		planT.setCreateTime(new Date());
		planT.setCreateTimeLong(planT.getCreateTime().getTime());
		planT.setUpdateUser("doming");
		planT.setUpdateTime(new Date());
		planT.setUpdateTimeLong(planT.getUpdateTime().getTime());
		planT.setState("OPEN");
		for(int i=0;i<10000;i++){
			planTService.save(planT);
		}



	}
}
