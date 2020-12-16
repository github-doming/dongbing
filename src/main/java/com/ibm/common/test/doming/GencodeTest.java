package com.ibm.common.test.doming;
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

			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_fm_member_bet_item");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_fm_game_set");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_member");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_member_bet_item");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_member_game_set");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_member_profit");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_member_profit_period");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_pi_member_plan_item");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_plan");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_plan_game");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_plan_hm");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_plan_item");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_profit_game");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_user_follow_member");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_rank_daily");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_rank_monthly");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vr_rank_weekly");


			System.out.println("end");
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test public void testIbs() {
		super.transactionBegin();
		String folderPath = "d:\\gen";
		String uri = "ibs";
		String fun = "";
		String packageCloud = "com.ibs.plan.module.cloud";
		try {
			BaseGen gen = new BaseGen();

//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_bet_item");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_bet_odds_tree");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_config");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_exist_hm");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_exist_hm");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_handicap_member");
//
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_hm_game_set");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_hm_info");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_hm_plan");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_hm_set");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_plan_item");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_profit_plan");
//
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_bet");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_bet_error");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_bet_fail");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_bet_info");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "ibsc_rep_draw");
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
//		String packageCloud = "com.ibm.follow.servlet.server";
		String packageCloud = "com.ibm.follow.servlet.vrc";
		try {
			BaseGen gen = new BaseGen();
			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vrc_fm_game_set");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vrc_exist_member");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vrc_member_bind_info");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vrc_member_coding_item");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vrc_member_plan");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vrc_plan_group_result");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vrc_plan_item");
//			gen.genSingleSimple(folderPath, uri, packageCloud, packageCloud, fun, "vrc_rep_draw");
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
		String packageServer = "com.cloud.lottery";
		try {
			BaseGen gen = new BaseGen();
			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "cloud_lottery_country_10");
			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "cloud_lottery_country_ssc");
			gen.genSingleSimple(folderPath, uri, packageServer, packageServer, fun, "cloud_lottery_self_ssc_5");

			System.out.println("end");
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
}
