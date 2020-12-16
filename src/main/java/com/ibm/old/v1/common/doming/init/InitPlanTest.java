package com.ibm.old.v1.common.doming.init;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_game.t.entity.IbmGameT;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_pi_follow_open_bet.t.entity.IbmPiFollowOpenBetT;
import com.ibm.old.v1.cloud.ibm_pi_follow_open_bet.t.service.IbmPiFollowOpenBetTService;
import com.ibm.old.v1.cloud.ibm_pi_follow_two_side.t.entity.IbmPiFollowTwoSideT;
import com.ibm.old.v1.cloud.ibm_pi_follow_two_side.t.service.IbmPiFollowTwoSideTService;
import com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.entity.IbmPiLocationBetNumberT;
import com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.service.IbmPiLocationBetNumberTService;
import com.ibm.old.v1.cloud.ibm_pi_number_to_track.t.entity.IbmPiNumberToTrackT;
import com.ibm.old.v1.cloud.ibm_pi_number_to_track.t.service.IbmPiNumberToTrackTService;
import com.ibm.old.v1.cloud.ibm_pi_rank_hot_and_cold.t.entity.IbmPiRankHotAndColdT;
import com.ibm.old.v1.cloud.ibm_pi_rank_hot_and_cold.t.service.IbmPiRankHotAndColdTService;
import com.ibm.old.v1.cloud.ibm_plan.t.entity.IbmPlanT;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.entity.IbmPlanHmT;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemT;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.junit.Test;

import java.util.Date;
import java.util.List;
/**
 * @Description: 初始化方案
 * @Author: Dongming
 * @Date: 2018-10-16 09:55:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InitPlanTest extends CommTest {

	/**
	 * 初始化方案
	 *
	 * @throws Exception
	 */
	@Test public void initPlan() {

		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			initPlanUser();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

	private void initPlanUser() throws Exception {

		String planId = "bf1035cef46e43cd98a61aba10bdb78b";
		IbmPlanTService planTService = new IbmPlanTService();
		IbmPlanT planT = planTService.find(planId);


		AppUserService appUserService = new AppUserService();
		List<String> userIds = appUserService.listIdByType(planT.getPlanType());
		if (ContainerTool.isEmpty(userIds)) {
			return;
		}
		IbmPlanItemService planItemService = new IbmPlanItemService();
		IbmPlanItemT planItemT;
		IbmPlanUserTService planUserTService = new IbmPlanUserTService();
		String planUserId;

		IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();

		IbmPlanHmTService planHmTService = new IbmPlanHmTService();
		IbmPlanHmT planHmT;
		Date nowTime = new Date();
		for (String userId : userIds) {
			planItemT = planItemService.initPlanItem(planT.getCode(), userId);
			// 初始化盘口详情
			planUserId = planUserTService.save(planT, planItemT, userId);
			List<String> handicapMemberIds = handicapMemberTService.listIdByUserId(userId);
			if (ContainerTool.isEmpty(handicapMemberIds)) {
				continue;
			}
			for (String handicapMemberId : handicapMemberIds) {
				planHmT = new IbmPlanHmT();
				planHmT.setPlanId(planT.getIbmPlanId());
				planHmT.setHandicapMemberId(handicapMemberId);
				planHmT.setPlanUserId(planUserId);
				planHmT.setGameId(planT.getGameId());
				planHmT.setPlanItemTableId(planItemT.getId());
				planHmT.setCreateTime(nowTime);
				planHmT.setCreateTimeLong(System.currentTimeMillis());
				planHmT.setUpdateTime(nowTime);
				planHmT.setUpdateTimeLong(System.currentTimeMillis());
				planHmT.setState(IbmStateEnum.CLOSE.name());
				planHmTService.save(planHmT);
			}
		}
	}

	/**
	 * 跟随投注_双面
	 */
	private void initPlanFollowTwoSide() throws Exception {
		IbmPlanTService planTService = new IbmPlanTService();
		IbmPlanT planT = planTService.findByCode("FOLLOW_TWO_SIDE");
		if (planT != null) {
			return;
		}
		planT = new IbmPlanT();

		//获取游戏id
		IbmGameTService gameTService = new IbmGameTService();
		IbmGameT gameT = gameTService.findByCode("PK10");
		//初始化方案表
		planT.setGameId(gameT.getIbmGameId());
		planT.setPlanName("跟上期双面");
		planT.setPlanCode("FOLLOW_TWO_SIDE");
		planT.setPlanExplain("");
		planT.setPlanItemTableName("IBM_PI_FOLLOW_TWO_SIDE");
		planT.setPlanType(IbmTypeEnum.FREE.name());
		planT.setPlanWorthT(0);
		planT.setSn(2);
		planT.setCreateUser("doming");
		planT.setCreateTime(new Date());
		planT.setCreateTimeLong(planT.getCreateTime().getTime());
		planT.setUpdateTime(new Date());
		planT.setUpdateUser("doming");
		planT.setUpdateTimeLong(planT.getUpdateTime().getTime());
		planT.setState(IbmStateEnum.OPEN.name());
		String planTId = planTService.save(planT);

		//初始化详情方案表

		//初始化方案组数据
		JSONObject jsonPlanGroupData = initPlanGroupDataFollowTwoSide();

		IbmPiFollowTwoSideTService piFollowTwoSideTService = new IbmPiFollowTwoSideTService();
		IbmPiFollowTwoSideT piFollowTwoSideT = new IbmPiFollowTwoSideT();

		piFollowTwoSideT.setPlanId(planTId);
		piFollowTwoSideT.setUserId("doming");
		piFollowTwoSideT.setProfitLimitMaxT(0L);
		piFollowTwoSideT.setLossLimitMinT(0L);
		piFollowTwoSideT.setFundsList("10,20,40,80,160,320,640,1280,2560,5120,10240");
		piFollowTwoSideT.setFundsListId(null);
		piFollowTwoSideT.setFollowPeriod(1);
		piFollowTwoSideT.setMonitorPeriod(1);
		piFollowTwoSideT.setBetMode(IbmModeEnum.BET_MODE_REGULAR.name());
		piFollowTwoSideT.setFundSwapMode(IbmModeEnum.FUND_SWAP_MODE_NO_SWAP_ON_RESET.name());
		piFollowTwoSideT.setPeriodRollMode(null);
		piFollowTwoSideT.setPlanGroupData(jsonPlanGroupData.toString());
		piFollowTwoSideT.setPlanGroupActiveKey("");
		piFollowTwoSideT.setUnBetting(IbmTypeEnum.FALSE.name());
		piFollowTwoSideT.setCreateTime(new Date());
		piFollowTwoSideT.setCreateTimeLong(piFollowTwoSideT.getCreateTime().getTime());
		piFollowTwoSideT.setUpdateTime(new Date());
		piFollowTwoSideT.setUpdateTimeLong(piFollowTwoSideT.getUpdateTime().getTime());
		piFollowTwoSideT.setState(IbmStateEnum.DEF.name());
		piFollowTwoSideTService.save(piFollowTwoSideT);
	}

	/**
	 * 位置投注_号码
	 *
	 * @throws Exception
	 */
	private void initPlanLocationBetNumber() throws Exception {

		IbmPlanTService planTService = new IbmPlanTService();
		IbmPlanT planT = planTService.findByCode("LOCATION_BET_NUMBER");
		if (planT != null) {
			return;
		}
		planT = new IbmPlanT();

		//获取游戏id
		IbmGameTService gameTService = new IbmGameTService();
		IbmGameT gameT = gameTService.findByCode("PK10");

		//初始化方案表
		planT.setGameId(gameT.getIbmGameId());
		planT.setPlanName("位置投注");
		planT.setPlanCode("LOCATION_BET_NUMBER");
		planT.setPlanExplain("");
		planT.setPlanItemTableName("IBM_PI_LOCATION_BET_NUMBER");
		planT.setPlanType(IbmTypeEnum.FREE.name());
		planT.setPlanWorthT(0);
		planT.setSn(1);
		planT.setCreateUser("doming");
		planT.setCreateTime(new Date());
		planT.setCreateTimeLong(planT.getCreateTime().getTime());
		planT.setUpdateTime(new Date());
		planT.setUpdateUser("doming");
		planT.setUpdateTimeLong(planT.getUpdateTime().getTime());
		planT.setState(IbmStateEnum.OPEN.name());
		String planTId = planTService.save(planT);

		//初始化详情方案表

		//初始化方案组数据
		JSONObject jsonPlanGroupData = initPlanGroupDataLocationBetNumber();

		IbmPiLocationBetNumberTService piLocationBetNumberTService = new IbmPiLocationBetNumberTService();
		IbmPiLocationBetNumberT piLocationBetNumberT = new IbmPiLocationBetNumberT();

		piLocationBetNumberT.setPlanId(planTId);
		piLocationBetNumberT.setUserId("doming");
		piLocationBetNumberT.setProfitLimitMaxT(0L);
		piLocationBetNumberT.setLossLimitMinT(0L);
		piLocationBetNumberT.setFundsList("10,20,40,80,160,320,640,1280,2560,5120,10240");
		piLocationBetNumberT.setFundsListId(null);
		piLocationBetNumberT.setFollowPeriod(1);
		piLocationBetNumberT.setMonitorPeriod(1);
		piLocationBetNumberT.setBetMode(IbmModeEnum.BET_MODE_REGULAR.name());
		piLocationBetNumberT.setFundSwapMode(IbmModeEnum.FUND_SWAP_MODE_NO_SWAP_ON_RESET.name());
		piLocationBetNumberT.setPeriodRollMode(null);
		piLocationBetNumberT.setPlanGroupData(jsonPlanGroupData.toString());
		piLocationBetNumberT.setPlanGroupActiveKey("1");
		piLocationBetNumberT.setUnIncrease(IbmTypeEnum.FALSE.name());
		piLocationBetNumberT.setCreateTime(new Date());
		piLocationBetNumberT.setCreateTimeLong(piLocationBetNumberT.getCreateTime().getTime());
		piLocationBetNumberT.setUpdateTime(new Date());
		piLocationBetNumberT.setUpdateTimeLong(piLocationBetNumberT.getUpdateTime().getTime());
		piLocationBetNumberT.setState(IbmStateEnum.DEF.name());
		piLocationBetNumberTService.save(piLocationBetNumberT);
	}

	/**
	 * 初始化方案组数据
	 *
	 * @return 方案组数据json
	 */
	private JSONObject initPlanGroupDataLocationBetNumber() {
		JSONObject jsonObject = new JSONObject();

		JSONObject jObj1 = new JSONObject();
		jObj1.put("state", IbmTypeEnum.FALSE.name());
		jObj1.put("select", "1,2,3");
		jObj1.put("bet", "1");
		jsonObject.put("0", jObj1);

		JSONObject jObj2 = new JSONObject();
		jObj2.put("state", IbmTypeEnum.TRUE.name());
		jObj2.put("select", "2,3,4,5,6");
		jObj2.put("bet", "2");
		jsonObject.put("1", jObj2);

		JSONObject jObj3 = new JSONObject();
		jObj3.put("state", IbmTypeEnum.FALSE.name());
		jObj3.put("select", "2,3,4,5,6,7,8");
		jObj3.put("bet", "2");
		jsonObject.put("2", jObj3);

		JSONObject jObj4 = new JSONObject();
		jObj4.put("state", IbmTypeEnum.FALSE.name());
		jObj4.put("select", "1");
		jObj4.put("bet", "2,3,4");
		jsonObject.put("3", jObj4);

		return jsonObject;
	}

	/**
	 * 跟上期双面
	 *
	 * @return
	 */
	private JSONObject initPlanGroupDataFollowTwoSide() {
		String[] rank = {"第一名", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名", "冠亚"};
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < rank.length; i++) {
			JSONObject jObj = new JSONObject();
			jObj.put("state", IbmTypeEnum.NONE.name());
			jObj.put("code", rank[i] + "_大小");
			jsonObject.put(i + "", jObj);
		}
		for (int i = 0; i < rank.length; i++) {
			JSONObject jObj = new JSONObject();
			jObj.put("state", IbmTypeEnum.NONE.name());
			jObj.put("code", rank[i] + "_单双");
			jsonObject.put((i + 11) + "", jObj);
		}
		for (int i = 0; i < 5; i++) {
			JSONObject jObj = new JSONObject();
			jObj.put("state", IbmTypeEnum.NONE.name());
			jObj.put("code", rank[i] + "_龙虎");
			jsonObject.put((i + 22) + "", jObj);
		}
		return jsonObject;
	}

	/**
	 * 手动投注
	 */
	private void initPlanManualBetNumber() throws Exception {
		IbmPlanTService planTService = new IbmPlanTService();
		IbmPlanT planT = planTService.findByCode("MANUAL");
		if (planT != null) {
			return;
		}
		planT = new IbmPlanT();

		//获取游戏id
		IbmGameTService gameTService = new IbmGameTService();
		IbmGameT gameT = gameTService.findByCode("PK10");
		//初始化方案表
		planT.setGameId(gameT.getIbmGameId());
		planT.setPlanName("手动投注");
		planT.setPlanCode("MANUAL");
		planT.setPlanExplain("");
		planT.setPlanItemTableName("IBM_PI_MANUAL_BET_NUMBER");
		planT.setPlanType(IbmTypeEnum.FREE.name());
		planT.setPlanWorthT(0);
		planT.setSn(3);
		planT.setCreateUser("doming");
		planT.setCreateTime(new Date());
		planT.setCreateTimeLong(planT.getCreateTime().getTime());
		planT.setUpdateTime(new Date());
		planT.setUpdateUser("doming");
		planT.setUpdateTimeLong(planT.getUpdateTime().getTime());
		planT.setState(IbmStateEnum.OPEN.name());
		planTService.save(planT);

	}

	/**
	 * 冷热排名
	 */
	private void initFollowOpenBet() throws Exception {
		//初始化详情方案表

		//初始化方案组数据
		JSONObject jsonPlanGroupData = initPlanGroupDataFollowOpenBet();

		String planId = new IbmPlanTService().findId("FOLLOW_OPEN_BET", GameTool.findId(IbmGameEnum.PK10.name()));

		IbmPiFollowOpenBetT followOpenBetT = new IbmPiFollowOpenBetT();

		followOpenBetT.setPlanId(planId);
		followOpenBetT.setUserId("doming");
		followOpenBetT.setProfitLimitMaxT(0L);
		followOpenBetT.setLossLimitMinT(0L);
		followOpenBetT.setFundsList("10,20,40,80,160,320,640,1280,2560,5120,10240");
		followOpenBetT.setFundsListId("NULL");
		followOpenBetT.setFollowPeriod(1);
		followOpenBetT.setMonitorPeriod(1);
		followOpenBetT.setBetMode(IbmModeEnum.BET_MODE_REGULAR.name());
		followOpenBetT.setFundSwapMode(IbmModeEnum.FUND_SWAP_MODE_NO_SWAP_ON_RESET.name());
		followOpenBetT.setPeriodRollMode(null);
		followOpenBetT.setPlanGroupData(jsonPlanGroupData.toString());
		followOpenBetT.setPlanGroupActiveKey("1,3,6");
		followOpenBetT.setDoubleToBetSame(IbmTypeEnum.FALSE.name());
		followOpenBetT.setCreateTime(new Date());
		followOpenBetT.setCreateTimeLong(followOpenBetT.getCreateTime().getTime());
		followOpenBetT.setUpdateTime(new Date());
		followOpenBetT.setUpdateTimeLong(followOpenBetT.getUpdateTime().getTime());
		followOpenBetT.setState(IbmStateEnum.DEF.name());
		new IbmPiFollowOpenBetTService().save(followOpenBetT);
	}

	private JSONObject initPlanGroupDataFollowOpenBet() {
		JSONObject planGroup = new JSONObject();
		planGroup.put("selectKey", "1,3");

		JSONObject select = new JSONObject();

		JSONObject jObj0 = new JSONObject();
		jObj0.put("state", IbmTypeEnum.FALSE.name());
		jObj0.put("select", "2");
		jObj0.put("bet", "2,3,4,5,6");
		select.put("0", jObj0);

		JSONObject jObj1 = new JSONObject();
		jObj1.put("state", IbmTypeEnum.TRUE.name());
		jObj1.put("select", "4");
		jObj1.put("bet", "2,3,4,5,6");
		select.put("1", jObj1);

		JSONObject jObj2 = new JSONObject();
		jObj2.put("state", IbmTypeEnum.FALSE.name());
		jObj2.put("select", "5");
		jObj2.put("bet", "2,3,4,5,6,7,8");
		select.put("2", jObj2);

		JSONObject jObj3 = new JSONObject();
		jObj3.put("state", IbmTypeEnum.TRUE.name());
		jObj3.put("select", "1");
		jObj3.put("bet", "2,3,4");
		select.put("3", jObj3);

		planGroup.put("select", select);
		return planGroup;

	}

	/**
	 * 号码追踪
	 * @throws Exception
	 */
	private void initNumberToTrack() throws Exception {
		//初始化详情方案表

		//初始化方案组数据
		JSONObject jsonPlanGroupData = initPlanGroupDataNumberToTrack();

		String planId = new IbmPlanTService().findId("NUMBER_TO_TRACK",GameTool.findId(IbmGameEnum.PK10.name()));

		IbmPiNumberToTrackT numberToTrackT = new IbmPiNumberToTrackT();

		numberToTrackT.setPlanId(planId);
		numberToTrackT.setUserId("doming");
		numberToTrackT.setProfitLimitMaxT(0L);
		numberToTrackT.setLossLimitMinT(0L);
		numberToTrackT.setFundsList("10,20,40,80,160,320,640,1280,2560,5120,10240");
		numberToTrackT.setFundsListId("NULL");
		numberToTrackT.setFollowPeriod(1);
		numberToTrackT.setMonitorPeriod(1);
		numberToTrackT.setBetMode(IbmModeEnum.BET_MODE_REGULAR.name());
		numberToTrackT.setFundSwapMode(IbmModeEnum.FUND_SWAP_MODE_NO_SWAP_ON_RESET.name());
		numberToTrackT.setPeriodRollMode(null);
		numberToTrackT.setPlanGroupData(jsonPlanGroupData.toString());
		numberToTrackT.setPlanGroupActiveKey("1");
		numberToTrackT.setCreateTime(new Date());
		numberToTrackT.setCreateTimeLong(numberToTrackT.getCreateTime().getTime());
		numberToTrackT.setUpdateTime(new Date());
		numberToTrackT.setUpdateTimeLong(numberToTrackT.getUpdateTime().getTime());
		numberToTrackT.setState(IbmStateEnum.DEF.name());
		new IbmPiNumberToTrackTService().save(numberToTrackT);
	}

	private JSONObject initPlanGroupDataNumberToTrack() {
		JSONObject planGroup = new JSONObject();

		JSONObject jObj1 = new JSONObject();
		jObj1.put("state", IbmTypeEnum.FALSE.name());
		jObj1.put("track", "1");
		jObj1.put("bet", "1,2,3");
		planGroup.put("0", jObj1);

		JSONObject jObj2 = new JSONObject();
		jObj2.put("state", IbmTypeEnum.TRUE.name());
		jObj2.put("track", "6");
		jObj2.put("bet", "2,3,4,5");
		planGroup.put("1", jObj2);

		JSONObject jObj3 = new JSONObject();
		jObj3.put("state", IbmTypeEnum.FALSE.name());
		jObj3.put("track", "2");
		jObj3.put("bet", "2,3,4,5,6,7,8");
		planGroup.put("2", jObj3);

		JSONObject jObj4 = new JSONObject();
		jObj4.put("state", IbmTypeEnum.FALSE.name());
		jObj4.put("track", "5");
		jObj4.put("bet", "2,3,4");
		planGroup.put("3", jObj4);
		return planGroup;
	}

	private void initRankHotAndCold() throws Exception {
		//初始化详情方案表

		//初始化方案组数据
		JSONObject jsonPlanGroupData = initPlanGroupDataRankHotAndCold();

		String planId = new IbmPlanTService().findId("RANK_HOT_AND_COLD", GameTool.findId(IbmGameEnum.PK10.name()));

		IbmPiRankHotAndColdT rankHotAndColdT = new IbmPiRankHotAndColdT();

		rankHotAndColdT.setPlanId(planId);
		rankHotAndColdT.setUserId("doming");
		rankHotAndColdT.setProfitLimitMaxT(0L);
		rankHotAndColdT.setLossLimitMinT(0L);
		rankHotAndColdT.setFundsList("10,20,40,80,160,320,640,1280,2560,5120,10240");
		rankHotAndColdT.setFundsListId("NULL");
		rankHotAndColdT.setFollowPeriod(1);
		rankHotAndColdT.setBetMode(IbmModeEnum.BET_MODE_REGULAR.name());
		rankHotAndColdT.setFundSwapMode(IbmModeEnum.FUND_SWAP_MODE_NO_SWAP_ON_RESET.name());
		rankHotAndColdT.setPeriodRollMode(null);
		rankHotAndColdT.setPlanGroupData(jsonPlanGroupData.toString());
		rankHotAndColdT.setPlanGroupActiveKey("1");
		rankHotAndColdT.setAllowRepeatNum(IbmTypeEnum.FALSE.name());
		rankHotAndColdT.setCounterattack(IbmTypeEnum.FALSE.name());
		rankHotAndColdT.setCreateTime(new Date());
		rankHotAndColdT.setCreateTimeLong(rankHotAndColdT.getCreateTime().getTime());
		rankHotAndColdT.setUpdateTime(new Date());
		rankHotAndColdT.setUpdateTimeLong(rankHotAndColdT.getUpdateTime().getTime());
		rankHotAndColdT.setState(IbmStateEnum.DEF.name());
		new IbmPiRankHotAndColdTService().save(rankHotAndColdT);
	}

	private JSONObject initPlanGroupDataRankHotAndCold() {
		JSONObject planGroup = new JSONObject();

		JSONObject jObj1 = new JSONObject();
		jObj1.put("state", IbmTypeEnum.FALSE.name());
		jObj1.put("bet", "1");
		jObj1.put("rank", "1,2,3");
		planGroup.put("0", jObj1);

		JSONObject jObj2 = new JSONObject();
		jObj2.put("state", IbmTypeEnum.TRUE.name());
		jObj2.put("bet", "2");
		jObj2.put("rank", "2,3,4,5");
		planGroup.put("1", jObj2);

		JSONObject jObj3 = new JSONObject();
		jObj3.put("state", IbmTypeEnum.FALSE.name());
		jObj3.put("bet", "2");
		jObj3.put("rank", "2,3,4,5,6,7,8");
		planGroup.put("2", jObj3);

		JSONObject jObj4 = new JSONObject();
		jObj4.put("state", IbmTypeEnum.FALSE.name());
		jObj4.put("bet", "5");
		jObj4.put("rank", "2,3,4");
		planGroup.put("3", jObj4);
		return planGroup;
	}

}
