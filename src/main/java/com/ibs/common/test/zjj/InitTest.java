package com.ibs.common.test.zjj;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.core.CommTest;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.module.cloud.ibsp_config.entity.IbspConfig;
import com.ibs.plan.module.cloud.ibsp_config.service.IbspConfigService;
import com.ibs.plan.module.cloud.ibsp_game.entity.IbspGame;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap.entity.IbspHandicap;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.entity.IbspHandicapGame;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.entity.IbspHmGameSet;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.entity.IbspHmModeCutover;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import com.ibs.plan.module.cloud.ibsp_plan.entity.IbspPlan;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_game.entity.IbspPlanGame;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import org.junit.Test;

import java.util.Date;

/**
 * @Description: 初始化信息
 * @Author: null
 * @Date: 2020-05-26 11:14
 * @Version: v1.0
 */
public class InitTest extends CommTest {

	@Test
	public void initModeCutover(){
		super.transactionBegin();
		try {
			JSONObject groupData=new JSONObject();
//			groupData.put("CUTOVER_KEY_","1,2,4");
			for(int i=0;i<5;i++){
				JSONObject data=new JSONObject();
				data.put("CURRENT_","REAL");
				data.put("CUTOVER_PROFIT_",100);
				data.put("CUTOVER_LOSS_",-100);
				data.put("CUTOVER_","VIRTUAL");
				data.put("RESET_PROFIT_","false");
				groupData.put(String.valueOf(i),data);
			}

			System.out.println(groupData.toString());

			IbspHmModeCutover hmModeCutover=new IbspHmModeCutover();
			hmModeCutover.setHandicapMemberId("1");
			hmModeCutover.setCutoverGroupData(groupData);
			hmModeCutover.setCreateTime(new Date());
			hmModeCutover.setCreateTimeLong(System.currentTimeMillis());
			hmModeCutover.setUpdateTime(new Date());
			hmModeCutover.setUpdateTimeLong(System.currentTimeMillis());
			hmModeCutover.setState(IbsStateEnum.DEF.name());
			new IbspHmModeCutoverService().save(hmModeCutover);


		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}


	@Test
	public void initGameSet(){
		super.transactionBegin();
		try {

			IbspHmGameSetService gameSetService=new IbspHmGameSetService();
			IbspHmGameSet gameSet=new IbspHmGameSet();
			gameSet.setHandicapMemberId(0);
			gameSet.setHandicapId(0);
			gameSet.setGameId(0);
			gameSet.setBetState("FALSE");
			gameSet.setIsAutoStartBet("FALSE");
			gameSet.setIsAutoStopBet("FALSE");
			gameSet.setIncreaseState("FALSE");
			gameSet.setBetSecond(0);
			gameSet.setSplitNumberAmount(30000);
			gameSet.setSplitTwoSideAmount(30000);
			gameSet.setCreateTime(new Date());
			gameSet.setCreateTimeLong(System.currentTimeMillis());
			gameSet.setUpdateTime(new Date());
			gameSet.setUpdateTimeLong(System.currentTimeMillis());
			gameSet.setState(IbsStateEnum.DEF.name());
			gameSetService.save(gameSet);

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	/**
	 * 初始化方案组数据
	 */
	@Test
	public void initPlanGroupData(){
		JSONObject groupData=new JSONObject();

		for(int i=0;i<10;i++){
			JSONObject data=new JSONObject();
			data.put("select","1");
			data.put("bet","1,2,3");
			groupData.put(String.valueOf(i),data.toString());
		}

		System.out.println(groupData);


	}
	/**
	 * 初始化ibsp_config信息
	 */
	@Test
	public void test() {
		super.transactionBegin();
		try {
			IbspConfigService configService = new IbspConfigService();
			IbspConfig config = new IbspConfig();
			config.setConfigKey("IDC#XYFT");
			config.setConfigValue(30);
			config.setConfigType("SEAL_TIME");
			config.setCreateUser("doming");
			config.setCreateTime(new Date());
			config.setCreateTimeLong(System.currentTimeMillis());
			config.setUpdateTime(new Date());
			config.setUpdateTimeLong(System.currentTimeMillis());
			config.setState(IbsStateEnum.OPEN.name());
			configService.save(config);

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}

	}

	/**
	 * 初始化游戏
	 */
	@Test
	public void initGame() {
		super.transactionBegin();
		try {
			Date nowTime = new Date();
			IbspGame game = new IbspGame();
			game.setGameName("幸运农场");
			game.setGameCode("XYNC");
			game.setIcon("/pages/com/ibm/image/XYNC.png");
			game.setRepDrawTableName("IBSP_REP_GRAB_XYNC");
			game.setDrawTime(0);
			game.setSn(7);
			game.setCreateUser("Doming");
			game.setCreateTime(nowTime);
			game.setCreateTimeLong(System.currentTimeMillis());
			game.setUpdateTime(nowTime);
			game.setUpdateTimeLong(System.currentTimeMillis());
			game.setState(IbsStateEnum.OPEN.name());
			String gameId = new IbspGameService().save(game);

			IbspHandicapGameService handicapGameService=new IbspHandicapGameService();
			IbspHandicapGame handicapGame=new IbspHandicapGame();
			handicapGame.setHandicapId(HandicapUtil.id("IDC", IbsTypeEnum.MEMBER));
			handicapGame.setGameId(gameId);
			handicapGame.setGameCode(game.getGameCode());
			handicapGame.setHandicapGameName(game.getGameName());
			handicapGame.setGameDrawType("CSJ");
			handicapGame.setIcon(game.getIcon());
			handicapGame.setSn(0);
			handicapGame.setCreateUser("Doming");
			handicapGame.setCreateTime(nowTime);
			handicapGame.setCreateTimeLong(System.currentTimeMillis());
			handicapGame.setUpdateTime(nowTime);
			handicapGame.setUpdateTimeLong(System.currentTimeMillis());
			handicapGame.setState(IbsStateEnum.OPEN.name());
			handicapGameService.save(handicapGame);

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	@Test
	public void initHandicapGame(){
		super.transactionBegin();
		try {
			Date nowTime = new Date();
			IbspHandicapGameService handicapGameService=new IbspHandicapGameService();
			IbspHandicapGame handicapGame=new IbspHandicapGame();
			handicapGame.setHandicapId(HandicapUtil.id("COM", IbsTypeEnum.MEMBER));
			handicapGame.setGameId("d43083f5ff9e4b38856996a1e480f20b");
			handicapGame.setGameCode("CQSSC");
			handicapGame.setHandicapGameName("重庆时时彩");
			handicapGame.setGameDrawType("CSJ");
			handicapGame.setCloseTime(90);
			handicapGame.setIcon("/pages/com/ibm/image/CQSSC.png");
			handicapGame.setSn(4);
			handicapGame.setCreateUser("Doming");
			handicapGame.setCreateTime(nowTime);
			handicapGame.setCreateTimeLong(System.currentTimeMillis());
			handicapGame.setUpdateTime(nowTime);
			handicapGame.setUpdateTimeLong(System.currentTimeMillis());
			handicapGame.setState(IbsStateEnum.OPEN.name());
			handicapGameService.save(handicapGame);

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	/**
	 * 初始化盘口信息
	 */
	@Test
	public void initHandicap() throws Exception {
		super.transactionBegin();
		try {
			IbspHandicapService handicapService = new IbspHandicapService();
			IbspHandicap handicap = new IbspHandicap();
			handicap.setHandicapName("COM");
			handicap.setHandicapCode("COM");
			handicap.setHandicapExplain("手动初始化");
			handicap.setHandicapType("FREE");
			handicap.setHandicapWorthT(0);
			handicap.setHandicapVersion("0.0.1");
			handicap.setSn(3);
			handicap.setCapacityMax(0);
			handicap.setCapacity(0);
			handicap.setCreateUser("Doming");
			handicap.setCreateTime(new Date());
			handicap.setCreateTimeLong(System.currentTimeMillis());
			handicap.setUpdateTime(new Date());
			handicap.setUpdateTimeLong(System.currentTimeMillis());
			handicap.setState(IbsStateEnum.OPEN.name());
			handicapService.save(handicap);
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	/**
	 * 初始化方案
	 */
	@Test
	public void initPlan() {
		super.transactionBegin();
		try{
			IbspPlanService planService=new IbspPlanService();
			IbspPlan plan=new IbspPlan();
			plan.setPlanName(PlanUtil.Code.RANDOM_PERIOD_KILL_NUMBER.getName());
			plan.setPlanCode(PlanUtil.Code.RANDOM_PERIOD_KILL_NUMBER.name());
			plan.setPlanItemTableName(PlanUtil.Code.RANDOM_PERIOD_KILL_NUMBER.getTableName());
			plan.setSn(31);
//			plan.setAvailableGameType("NUMBER");
			plan.setAvailableGameType("NUMBER,BALL,HAPPY");

			plan.setPlanType("FREE");
			plan.setCreateTime(new Date());
			plan.setCreateTimeLong(System.currentTimeMillis());
			plan.setUpdateTime(new Date());
			plan.setUpdateTimeLong(System.currentTimeMillis());
			plan.setState(IbsStateEnum.OPEN.name());
			planService.save(plan);
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	@Test
	public void initAllPlanGame(){
		super.transactionBegin();
		try{
			/**
			 * ea869c7d94254e92b3185ea4bb1be124
			 * 1426a381e83d4cc4be7ccfe5d6510da4
			 * 9e3f8e9427bf4d4997266cfb62a5ab8c
			 * 09aaa793a1e34dd39c45cc762d632bab
			 * d43083f5ff9e4b38856996a1e480f20b
			 * 7b187bbd40024ac396af3a0cbd64b8f0
			 * 83b7d47e8ce4448aa623b466c05243d9
			 * f905693c5ae04f408a1c88bad92e8cd2
			 */

			String str="ea869c7d94254e92b3185ea4bb1be124,1426a381e83d4cc4be7ccfe5d6510da4,9e3f8e9427bf4d4997266cfb62a5ab8c,"
					+ "09aaa793a1e34dd39c45cc762d632bab,d43083f5ff9e4b38856996a1e480f20b,7b187bbd40024ac396af3a0cbd64b8f0,"
					+ "83b7d47e8ce4448aa623b466c05243d9,f905693c5ae04f408a1c88bad92e8cd2";
			IbspPlanGameService planGameService=new IbspPlanGameService();
			String[] games=str.split(",");
			IbspPlanGame planGame=new IbspPlanGame();
			planGame.setPlanId("1bd9340b38f74a70bd537185bff19326");
			planGame.setPlanName(PlanUtil.Code.RANDOM_PERIOD_KILL_NUMBER.getName());
			planGame.setPlanCode(PlanUtil.Code.RANDOM_PERIOD_KILL_NUMBER.name());
			planGame.setSn(31);

			planGame.setCreateTime(new Date());
			planGame.setCreateTimeLong(System.currentTimeMillis());
			planGame.setUpdateTime(new Date());
			planGame.setUpdateTimeLong(System.currentTimeMillis());
			planGame.setState(IbsStateEnum.OPEN.name());
			for(String game:games){
				planGame.setGameId(game);
				planGameService.save(planGame);
			}
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test
	public void initNumberPlanGame(){
		super.transactionBegin();
		try{
			String str="1426a381e83d4cc4be7ccfe5d6510da4,83b7d47e8ce4448aa623b466c05243d9,9e3f8e9427bf4d4997266cfb62a5ab8c,"
					+ "ea869c7d94254e92b3185ea4bb1be124";
			IbspPlanGameService planGameService=new IbspPlanGameService();
			String[] games=str.split(",");
			IbspPlanGame planGame=new IbspPlanGame();
			planGame.setPlanId("be14e005dce840868ac24b128ad83006");
			planGame.setPlanName(PlanUtil.Code.TRACK_FOLLOW_OPEN_BET_SECOND.getName());
			planGame.setPlanCode(PlanUtil.Code.TRACK_FOLLOW_OPEN_BET_SECOND.name());
			planGame.setSn(40);

			planGame.setCreateTime(new Date());
			planGame.setCreateTimeLong(System.currentTimeMillis());
			planGame.setUpdateTime(new Date());
			planGame.setUpdateTimeLong(System.currentTimeMillis());
			planGame.setState(IbsStateEnum.OPEN.name());
			for(String game:games){
				planGame.setGameId(game);
				planGameService.save(planGame);
			}
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
}
