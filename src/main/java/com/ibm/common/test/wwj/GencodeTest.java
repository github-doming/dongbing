package com.ibm.common.test.wwj;
import com.ibm.common.core.CommTest;
import com.ibm.common.test.BaseGen;
import org.junit.Test;
/**
 * @Description: 生成代码
 * @Author: Dongming
 * @Date: 2019-06-20 15:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GencodeTest extends CommTest {
	@Test public void testCloud() {
		super.transactionBegin();
		String folderPath = "d:\\gen";
		String uri = "ibm";
		String fun = "";
		String packageCloud = "com.ibm.follow.servlet.cloud";
		try {
			BaseGen gen = new BaseGen();
			/*
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_client");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_client_capacity");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_client_ha");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_client_handicap_capacity");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_client_hm");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_config");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_event_client_close");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_event_client_open");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_event_config_info");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_event_config_set");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_event_login");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_event_login_snatch");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_event_login_vali");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_ha_game_set");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_ha_info");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_ha_member_bet_item");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_ha_notice");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_ha_set");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_ha_user");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_handicap");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_handicap_agent");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_handicap_agent_member");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_handicap_game");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_handicap_item");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_handicap_member");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_bet_item");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_game_set");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_info");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_notice");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_profit");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_profit_item");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_profit_period");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_profit_period_vr");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_profit_vr");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_set");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_hm_user");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_log_ha");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_log_hm");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_available_time");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_draw_cqssc");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_draw_pk10");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_draw_xyft");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_grab_cqssc");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_grab_pk10");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_grab_xyft");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_manage_time");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_manage_point");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_manage_point");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "IBM_SYS_SERVLET_IP");

			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "IBM_CLIENT");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_handicap_game");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "IBM_GAME");


			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_sys_bet_odds");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_sys_bet_odds_tree");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_sys_periods");
			*/

//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_grab_js10");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_grab_jsssc");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_rep_draw_js10");
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibm_ha_follow_period");

			System.out.println("end");
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test public void testService() {
		super.transactionBegin();
		String folderPath = "d:\\gen";
		String uri = "ibm";
		String fun = "";
		String packageServer = "com.ibm.follow.servlet.server";
		try {
			BaseGen gen = new BaseGen();
			//			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "ibm_rep_grab_xyft");

			System.out.println("end");
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test public void testClient() {
		super.transactionBegin();
		String folderPath = "d:\\gen";
		String uri = "ibm";
		String fun = "";
		String packageClient = "com.ibm.follow.servlet.client";
		try {
			BaseGen gen = new BaseGen();
			/*
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_agent_member_info");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_config");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_exist_ha");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_exist_hm");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_ha_follow_bet");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_ha_game_set");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_ha_info");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_handicap_agent");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_handicap_member");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_hm_bet");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_hm_bet_error");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_hm_bet_fail");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_hm_bet_info");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_hm_bet_item");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_hm_game_set");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_hm_info");
			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_ha_set");
			*/

			gen.genSingleSimple(folderPath, uri, packageClient, packageClient, fun, "ibmc_agent_member_info");

			System.out.println("end");
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test public void cloud() {
		super.transactionBegin();
		String folderPath = "d:\\gen";
		String uri = "ibm";
		String fun = "";
		String packageServer = "com.cloud";
		try {
			BaseGen gen = new BaseGen();
			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "cloud_lottery_js10");
			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "cloud_lottery_jsssc");

			System.out.println("end");
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
}
