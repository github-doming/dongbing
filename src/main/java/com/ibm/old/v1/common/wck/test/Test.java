package com.ibm.old.v1.common.wck.test;

import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.security.util.CommASEUtil;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_plan.t.entity.IbmPlanT;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test extends CommTest{

	@org.junit.Test
	public void test1(){
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}


	public static void main(String[] args) throws Exception {
		Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse("2019-03-29 10:44:09.321");
		System.out.println(System.currentTimeMillis());
		decodePwd();
	}

	private static void decodePwd(){
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}

		String password = CommASEUtil.decode("191E756848F58ABA7B9773E76125F6D49869B119", "+/TMWdilBoI92bV2uoDEwA==");
		System.out.println(password);
	}


	//通过游戏id初始化方案
	private void initPlanByGameId(String gameId) throws Exception{
		Date nowTime = new Date();
		IbmPlanTService planTService = new IbmPlanTService();
		List<Map<String, Object>> list = planTService.findByGameId(gameId);
		if (list==null||list.size()<=0) {
			List<IbmPlanT> defPlans = planTService.findDefPayPlan();
			for (IbmPlanT plan : defPlans) {
				plan.setGameId(gameId);
				plan.setCreateUser("wck");
				plan.setCreateTime(nowTime);
				plan.setCreateTimeLong(nowTime.getTime());
				plan.setUpdateUser("wck");
				plan.setUpdateTime(nowTime);
				plan.setUpdateTimeLong(nowTime.getTime());
				plan.setState(IbmStateEnum.OPEN.name());
				planTService.save(plan);
			}
		}
	}

}
