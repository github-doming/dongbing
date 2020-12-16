package com.ibm.common.test.zjj;

import com.ibm.common.core.CommTest;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.entity.IbmGame;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.entity.IbmHandicapGame;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.entity.IbmSysBetOdds;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.service.IbmSysBetOddsService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds_tree.entity.IbmSysBetOddsTree;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds_tree.service.IbmSysBetOddsTreeService;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.entity.VrUserFollowMember;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.service.VrUserFollowMemberService;
import com.ibm.follow.servlet.server.core.job.service.MigrateService;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.junit.Test;

import java.util.*;

/**
 * @Description: 初始化数据
 * @Author: zjj
 * @Date: 2019-09-06 16:02
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class InitTest extends CommTest {

	@Test public void test() {
		super.transactionBegin();
		try {
			//库名
			String baseName="bak";
			//表名
			List<String> tableName = Arrays.asList("ibm_event_client_close","ibm_event_client_open","ibm_event_config_info",
					"ibm_event_config_set","ibm_event_login","ibm_event_login_vali","ibm_ha_follow_period","ibm_ha_member_bet_item",
					"ibm_hm_bet_item");
			List<String> tableNames = new ArrayList<>(tableName);

			//当天0点时间
			Date nowTime=DateTool.getDayStart(new Date());

			//数据切割时间
			Date cutDataTime= DateTool.getBeforeDays(nowTime,2);
			//表时间
			String tableTime = DateTool.getMDDate(DateTool.getBeforeDays(nowTime,3));

			MigrateService migrateService=new MigrateService();
			// 检测表数据是否超过10W条 ,没有则不备份
			migrateService.checkTableData(tableNames);
			if (ContainerTool.isEmpty(tableNames)) {
				return;
			}
			//创建表，迁移数据
			migrateService.createTable(cutDataTime,tableTime,tableNames,baseName);

			//删除表迁移数据
			migrateService.delMigrateData(cutDataTime,tableNames);

			//优化表
			migrateService.optimizeTable(tableNames);

			//表名
			tableNames = Arrays.asList("ibm_client_capacity","ibm_client_handicap_capacity","ibm_hm_profit","ibm_hm_profit_item",
					"ibm_hm_profit_period","ibm_hm_profit_period_vr",
					"ibm_hm_profit_vr");

			//清除7天前已删除废弃数据
			cutDataTime= DateTool.getBeforeDays(new Date(),6);

			migrateService.delData(cutDataTime,tableNames);
			//优化表
			migrateService.optimizeTable(tableNames);
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	@Test
	public void test05(){
		super.transactionBegin();
		try {
			Calendar date = Calendar.getInstance();
			String year = String.valueOf(date.get(Calendar.YEAR));

			//"ibmc_hm_bet",
			List<String> tableNames = Arrays.asList("ibmc_ha_follow_bet","ibmc_hm_bet","ibmc_hm_bet_error",
					"ibmc_hm_bet_fail","ibmc_hm_bet_info","ibmc_hm_bet_item");
			//当天0点时间
			Date nowTime= DateTool.getDayStart(new Date());

			//表时间
			String tableTime = DateTool.getMDDate(DateTool.getBeforeDays(nowTime,7));

//
//			System.out.println(DateTool.getBeforeDays(nowTime,7).getTime());
			int days=0;
			MigrateService migrateService=new MigrateService();
			migrateService.clearByDays(tableNames, days);



		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	@Test
	public void test06(){
		super.transactionBegin();
		try {
			VrUserFollowMember followMember=new VrUserFollowMember();
			followMember.setUserId("c2fa800f290a4eb0b1f99ddd114ffb1a");
			followMember.setVrMemberId("10b2dc525a924ba8834df9792245b114");
			followMember.setVrMemberAccount("ccc1234");
			followMember.setVrHandicapCode("IDC");
			followMember.setFollowState("TRUE");
			followMember.setCreateTime(new Date());
			followMember.setCreateTimeLong(System.currentTimeMillis());
			followMember.setUpdateTime(new Date());
			followMember.setUpdateTimeLong(System.currentTimeMillis());
			followMember.setState(StateEnum.OPEN.name());
			new VrUserFollowMemberService().save(followMember);


		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
    @Test
    public void test04(){
        super.transactionBegin();
        try {
			VrMember member=new VrMember();
			member.setHandicapCode("IDC");
			member.setVrUserName("aaa1234");
			member.setVrMemberAccount("aaa1234");
			member.setCreateTime(new Date());
			member.setCreateTimeLong(System.currentTimeMillis());
			member.setUpdateTime(new Date());
			member.setUpdateTimeLong(System.currentTimeMillis());
			member.setState(StateEnum.OPEN.name());
			new VrMemberService().save(member);

        } catch (Exception e) {
            log.error("测试错误", e);
            super.transactionRoll();
        } finally {
            super.transactionEnd();
        }
    }
	@Test public void test03() {
		super.transactionBegin();
		try {
			IbmSysBetOddsTreeService sysBetOddsTreeService = new IbmSysBetOddsTreeService();
			List<IbmSysBetOddsTree> sysBetOddsTrees = sysBetOddsTreeService
					.listByGameId("6475d6adec1f46e2936d385b725d808e");

			IbmSysBetOdds sysBetOdds = new IbmSysBetOdds();
			IbmSysBetOddsService sysBetOddsService=new IbmSysBetOddsService();
			sysBetOdds.setGameId("6475d6adec1f46e2936d385b725d808e");
			sysBetOdds.setGameCode(GameUtil.Code.JSSSC.name());
			sysBetOdds.setCreateTime(new Date());
			sysBetOdds.setCreateTimeLong(System.currentTimeMillis());
			sysBetOdds.setUpdateTime(new Date());
			sysBetOdds.setUpdateTimeLong(System.currentTimeMillis());
			sysBetOdds.setState("OPEN");
			for (IbmSysBetOddsTree sysBetOddsTree : sysBetOddsTrees) {
				sysBetOdds.setSysBetOddsTreeId(sysBetOddsTree.getIbmSysBetOddsTreeId());
				sysBetOdds.setOddsT(sysBetOddsTree.getOddsT());
				switch (sysBetOddsTree.getPlayTypeCode()) {
					case "RANK_NUMBER":
						for(int i=0;i<5;i++){
							for(int j=0;j<10;j++){
								sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,i,j));
								sysBetOddsService.save(sysBetOdds);
							}
						}
						break;
					case "RANK_SIZE":
						for(int i=0;i<5;i++){
							for(int j=0;j<2;j++){
								sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,i,10+j));
								sysBetOddsService.save(sysBetOdds);
							}
						}
						break;
					case "RANK_SINGLE_DOUBLE":
						for(int i=0;i<5;i++){
							for(int j=0;j<2;j++){
								sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,i,12+j));
								sysBetOddsService.save(sysBetOdds);
							}
						}
						break;
					case "SUM_SIZE":
							for(int j=0;j<2;j++){
								sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,5,10+j));
								sysBetOddsService.save(sysBetOdds);
							}
						break;
					case "SUM_SINGLE_DOUBLE":
						for(int j=0;j<2;j++){
							sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,5,12+j));
							sysBetOddsService.save(sysBetOdds);
						}
						break;
					case "DRAGON_TIGER":
						for(int j=0;j<2;j++){
							sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,6,14+j));
							sysBetOddsService.save(sysBetOdds);
						}
						break;
					case "DRAW":
						sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,6,16));
						sysBetOddsService.save(sysBetOdds);
						break;
					case "THREE_NUMBER_LEOPARD":
						for(int j=0;j<3;j++){
							sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,j+7,17));
							sysBetOddsService.save(sysBetOdds);
						}
						break;
					case "THREE_NUMBER_STRAIGHT":
						for(int j=0;j<3;j++){
							sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,j+7,18));
							sysBetOddsService.save(sysBetOdds);
						}
						break;
					case "THREE_NUMBER_PAIR":
						for(int j=0;j<3;j++){
							sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,j+7,19));
							sysBetOddsService.save(sysBetOdds);
						}
						break;
					case "THREE_NUMBER_HALF_STRAIGHT":
						for(int j=0;j<3;j++){
							sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,j+7,20));
							sysBetOddsService.save(sysBetOdds);
						}
						break;
					case "THREE_NUMBER_CLUTTER":
						for(int j=0;j<3;j++){
							sysBetOdds.setGamePlayName(GameTool.itemStr(GameUtil.Code.JSSSC,j+7,21));
							sysBetOddsService.save(sysBetOdds);
						}
						break;
					default:
						break;
				}
			}

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test public void test01() {
		super.transactionBegin();
		try {

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	@Test public void test02() {
		super.transactionBegin();
		try {
			Date nowTime = new Date();
			IbmGameService gameService = new IbmGameService();
			IbmGame game = gameService.find("e8b243c14b3f49cba7c5b0e0f63e9d97");

			IbmHandicapService handicapService = new IbmHandicapService();
			List<IbmHandicap> list = handicapService.findObjectAll();

			IbmHandicapGameService handicapGameService = new IbmHandicapGameService();
			IbmHandicapGame handicapGame = new IbmHandicapGame();

			for (IbmHandicap handicap : list) {
				handicapGame.setHandicapId(handicap.getIbmHandicapId());
				handicapGame.setGameId(game.getIbmGameId());
				handicapGame.setHandicapName(handicap.getHandicapName());
				handicapGame.setHandicapCode(handicap.getHandicapCode());
				handicapGame.setGameCode(game.getGameCode());
				handicapGame.setGameName(game.getGameName());
				handicapGame.setCloseTime(30);
				handicapGame.setSn(1);
				handicapGame.setIcon(game.getIcon());
				handicapGame.setCreateTime(nowTime);
				handicapGame.setCreateTimeLong(nowTime.getTime());
				handicapGame.setUpdateTime(nowTime);
				handicapGame.setUpdateTimeLong(nowTime.getTime());
				handicapGame.setState(IbmStateEnum.OPEN.name());
				handicapGameService.save(handicapGame);
			}

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
}
