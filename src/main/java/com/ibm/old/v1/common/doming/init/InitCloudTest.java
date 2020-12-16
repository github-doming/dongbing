package com.ibm.old.v1.common.doming.init;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_game.t.entity.IbmGameT;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_handicap.t.entity.IbmHandicapT;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.entity.IbmHandicapGameT;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.entity.IbmHandicapItemT;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.service.IbmHandicapItemTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.entity.IbmHandicapUserT;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.entity.IbmHmGameSetT;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.cloud.ibm_sys_client_ip.t.entity.IbmSysClientIpT;
import com.ibm.old.v1.cloud.ibm_sys_client_ip.t.service.IbmSysClientIpTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import org.doming.core.tools.DateTool;
import org.junit.Test;

import java.util.Date;
/**
 * @Description: 初始化中心数据
 * @Author: Dongming
 * @Date: 2018-12-13 16:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InitCloudTest extends CommTest {

	@Test public void initCloudTest() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			createGame();
			createHandicap();
			createHandicapGame();
			createHandicapItem();
			createUser();
			createHandicapUser();
			createHandicapMember();
			createRegisterIp();
			createHandicapMemberSet();
			createHandicapMemberGameSet();

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
		System.out.println("end");
	}

	/**
	 * 盘口会员游戏设置
	 * @return
	 * @throws Exception
	 */
	private IbmHmGameSetT createHandicapMemberGameSet() throws Exception {
		IbmHandicapMemberT handicapMemberT = createHandicapMember();
		IbmHandicapGameT handicapGameT = createHandicapGame();

		IbmHmGameSetTService hmGameSetTService = new IbmHmGameSetTService();
		IbmHmGameSetT hmGameSetT = hmGameSetTService
				.find(handicapMemberT.getIbmHandicapMemberId(), handicapGameT.getIbmHandicapGameId());
		if (hmGameSetT != null) {
			return hmGameSetT;
		}
		hmGameSetT = new IbmHmGameSetT();
		hmGameSetT.setHandicapMemberId(handicapMemberT.getIbmHandicapMemberId());
		hmGameSetT.setHandicapGameId(handicapGameT.getIbmHandicapGameId());
		hmGameSetT.setHandicapId(handicapMemberT.getHandicapId());
		hmGameSetT.setUserId(handicapMemberT.getAppUserId());
		hmGameSetT.setGameId(handicapGameT.getGameId());
		hmGameSetT.setBetState(IbmTypeEnum.TRUE.name());
		hmGameSetT.setBetMode(IbmTypeEnum.REAL.name());
		hmGameSetT.setBetAutoStop(IbmTypeEnum.TRUE.name());
		hmGameSetT.setBetAutoStopTime("10:30:00");
		hmGameSetT.setBetAutoStart(IbmTypeEnum.FALSE.name());
		hmGameSetT.setBetAutoStartTime("10:30:00");
		hmGameSetT.setIncreaseState(IbmTypeEnum.TRUE.name());
		hmGameSetT.setIncreaseStopTime("10:30:00");
		hmGameSetT.setBetSecond(120);
		hmGameSetT.setSplitTwoSideAmount(0);
		hmGameSetT.setSplitNumberAmount(0);
		hmGameSetT.setCreateTime(new Date());
		hmGameSetT.setCreateTimeLong(hmGameSetT.getCreateTime().getTime());
		hmGameSetT.setUpdateTime(new Date());
		hmGameSetT.setUpdateTimeLong(hmGameSetT.getUpdateTime().getTime());
		hmGameSetT.setState(IbmStateEnum.OPEN.name());
		hmGameSetT.setIbmHmGameSetId(hmGameSetTService.save(hmGameSetT));
		return hmGameSetT;
	}

	/**
	 * 盘口会员设置
	 *
	 * @return
	 */
	private IbmHmSetT createHandicapMemberSet() throws Exception {
		IbmHandicapMemberT handicapMemberT = createHandicapMember();

		IbmHmSetTService hmSetTService = new IbmHmSetTService();
		IbmHmSetT hmSetT = hmSetTService.findByHandicapMemberId(handicapMemberT.getIbmHandicapMemberId());
		if (hmSetT != null) {
			return hmSetT;
		}
		hmSetT = new IbmHmSetT();
		hmSetT.setHandicapMemberId(handicapMemberT.getIbmHandicapMemberId());
		hmSetT.setHandicapId(handicapMemberT.getHandicapId());
		hmSetT.setLandedTime("10:30:00");
		hmSetT.setBetRecordRows(500);
		hmSetT.setBetRateT(5000);
		hmSetT.setProfitLimitMaxT(9000000000L);
		hmSetT.setProfitLimitMinT(0L);
		hmSetT.setLossLimitMinT(-9000000000L);
		hmSetT.setResetType("2");
		hmSetT.setResetProfitMaxT(900000000L);
		hmSetT.setResetLossMinT(-900000000L);
		hmSetT.setBlastStop(IbmTypeEnum.CLOSE.name());
		hmSetT.setBetMerger(IbmTypeEnum.CLOSE.name());
		hmSetT.setCreateTime(new Date());
		hmSetT.setCreateTimeLong(hmSetT.getCreateTime().getTime());
		hmSetT.setUpdateTime(new Date());
		hmSetT.setUpdateTimeLong(hmSetT.getUpdateTime().getTime());
		hmSetT.setState(IbmStateEnum.OPEN.name());
		hmSetT.setIbmHmSetId(hmSetTService.save(hmSetT));
		return hmSetT;
	}

	/**
	 * 服务ip
	 *
	 * @return
	 * @throws Exception
	 */
	private IbmSysClientIpT createRegisterIp() throws Exception {
		String ip = "127.0.0.1";
		IbmSysClientIpTService clientIpTService = new IbmSysClientIpTService();
		IbmSysClientIpT clientIpT = clientIpTService.findByIP(ip);
		if (clientIpT != null) {
			return clientIpT;
		}
		clientIpT = new IbmSysClientIpT();
		clientIpT.setIp(ip);
		clientIpT.setStartTime(DateTool.getDate("2000-01-01"));
		clientIpT.setStartTimeLong(clientIpT.getStartTime().getTime());
		clientIpT.setEndTime(DateTool.getDate("2099-12-31"));
		clientIpT.setEndTimeLong(clientIpT.getEndTime().getTime());
		clientIpT.setCreateTime(new Date());
		clientIpT.setCreateTimeLong(clientIpT.getCreateTime().getTime());
		clientIpT.setUpdateTime(new Date());
		clientIpT.setUpdateTimeLong(clientIpT.getUpdateTime().getTime());
		clientIpT.setState(IbmStateEnum.OPEN.name());
		clientIpTService.save(clientIpT);

		return clientIpT;
	}

	/**
	 * 盘口用户
	 *
	 * @return
	 * @throws Exception
	 */
	private IbmHandicapUserT createHandicapUser() throws Exception {
		IbmHandicapUserTService huService = new IbmHandicapUserTService();
		IbmHandicapUserT handicapUserT = huService.findByCode("WS2", "doming");
		if (handicapUserT != null) {
			return handicapUserT;
		}
		AppUserService userService = new AppUserService();
		AppUserT userT = userService.findByCode("doming");
		if (userT == null) {
			userT = createUser();
		}

		IbmHandicapTService handicapTService = new IbmHandicapTService();
		IbmHandicapT handicapT = handicapTService.findByCode("WS2");
		if (handicapT == null) {
			handicapT = createHandicap();
		}

		handicapUserT = new IbmHandicapUserT();
		handicapUserT.setAppUserId(userT.getAppUserId());
		handicapUserT.setHandicapId(handicapT.getIbmHandicapId());
		handicapUserT.setHandicapCode(handicapT.getHandicapCode());
		handicapUserT.setHandicapName(handicapT.getHandicapName());
		handicapUserT.setCreateTime(new Date());
		handicapUserT.setCreateTimeLong(handicapUserT.getCreateTime().getTime());
		handicapUserT.setUpdateTime(new Date());
		handicapUserT.setUpdateTimeLong(handicapUserT.getUpdateTime().getTime());
		handicapUserT.setState(IbmStateEnum.OPEN.name());
		huService.save(handicapUserT);
		return handicapUserT;
	}

	/**
	 * 盘口会员
	 *
	 * @throws Exception
	 */
	private IbmHandicapMemberT createHandicapMember() throws Exception {
		IbmHandicapMemberTService hmService = new IbmHandicapMemberTService();
		IbmHandicapMemberT handicapMemberT = hmService.findByAccount("WS2", "cs765432");
		if (handicapMemberT != null) {
			return handicapMemberT;
		}

		IbmHandicapUserTService huService = new IbmHandicapUserTService();
		IbmHandicapUserT handicapUserT = huService.findByCode("WS2", "doming");
		if (handicapUserT == null) {
			handicapUserT = createHandicapUser();
		}

		IbmHandicapItemTService hiService = new IbmHandicapItemTService();
		IbmHandicapItemT handicapItemT = hiService.findByHandicapId(handicapUserT.getHandicapId());
		if (handicapItemT == null) {
			handicapItemT = createHandicapItem();
		}

		handicapMemberT = new IbmHandicapMemberT();
		handicapMemberT.setHandicapUserId(handicapUserT.getIbmHandicapUserId());
		handicapMemberT.setHandicapId(handicapUserT.getHandicapId());
		handicapMemberT.setHandicapCode("WS2");
		handicapMemberT.setHandicapItemId(handicapItemT.getIbmHandicapItemId());
		handicapMemberT.setAppUserId(handicapUserT.getAppUserId());
		handicapMemberT.setMemberAccount("cs765432");
		handicapMemberT.setMemberPassword("Cs987we12.");
		handicapMemberT.setCreateTime(new Date());
		handicapMemberT.setCreateTimeLong(handicapMemberT.getCreateTime().getTime());
		handicapMemberT.setUpdateTime(new Date());
		handicapMemberT.setUpdateTimeLong(handicapMemberT.getUpdateTime().getTime());
		handicapMemberT.setState(IbmStateEnum.OPEN.name());
		handicapMemberT.setHandicapItemId(hmService.save(handicapMemberT));
		return handicapMemberT;
	}

	/**
	 * 创建用户
	 *
	 * @return 用户
	 * @throws Exception
	 */
	private AppUserT createUser() throws Exception {
		AppUserService userService = new AppUserService();
		AppUserT userT = userService.findByCode("doming");
		if (userT != null) {
			return userT;
		}
		userT = new AppUserT();
		userT.setAppUserName("dongming");
		userT.setAppUserCode("doming");
		userT.setAppUserType("ADMIN");
		userT.setSn(1);
		userT.setCreateTime(new Date());
		userT.setCreateTimeLong(userT.getCreateTime().getTime());
		userT.setUpdateTime(new Date());
		userT.setUpdateTimeLong(userT.getUpdateTime().getTime());
		userT.setState(IbmStateEnum.OPEN.name());
		userService.save(userT);
		return userT;
	}

	/**
	 * 创建盘口
	 *
	 * @throws Exception
	 */
	private IbmHandicapT createHandicap() throws Exception {
		IbmHandicapTService handicapTService = new IbmHandicapTService();
		IbmHandicapT handicapT = handicapTService.findByCode("WS2");
		if (handicapT != null) {
			return handicapT;
		}
		handicapT = new IbmHandicapT();
		handicapT.setHandicapName("泰源");
		handicapT.setHandicapCode("WS2");
		handicapT.setHandicapWorthT(0);
		handicapT.setHandicapVersion("0.1");
		handicapT.setSn(1);
		handicapT.setCreateUser("doming");
		handicapT.setCreateTime(new Date());
		handicapT.setCreateTimeLong(handicapT.getCreateTime().getTime());
		handicapT.setUpdateTime(new Date());
		handicapT.setUpdateTimeLong(handicapT.getUpdateTime().getTime());
		handicapT.setState(IbmStateEnum.OPEN.name());
		handicapTService.save(handicapT);
		return handicapT;
	}

	/**
	 * 创建游戏
	 *
	 * @throws Exception
	 */
	private IbmGameT createGame() throws Exception {
		IbmGameTService gameTService = new IbmGameTService();
		IbmGameT gameT = gameTService.findByCode("PK10");
		if (gameT != null) {
			return gameT;
		}
		gameT = new IbmGameT();
		gameT.setGameName("北京赛车");
		gameT.setGameCode("PK10");
		gameT.setRepDrawTableName("IBM_REP_DRAW_PK10");
		gameT.setRepGrabTableName("IBM_REP_GRAB_PK10");
		gameT.setSn(1);
		gameT.setCreateUser("doming");
		gameT.setCreateTime(new Date());
		gameT.setCreateTimeLong(gameT.getCreateTime().getTime());
		gameT.setUpdateTime(new Date());
		gameT.setUpdateTimeLong(gameT.getUpdateTime().getTime());
		gameT.setState(IbmStateEnum.OPEN.name());
		gameTService.save(gameT);
		return gameT;
	}
	/**
	 * 初始化盘口游戏
	 *
	 * @throws Exception
	 */
	private IbmHandicapGameT createHandicapGame() throws Exception {
		IbmHandicapGameTService hgService = new IbmHandicapGameTService();
		IbmHandicapGameT handicapGameT = hgService.findByCode("WS2", "PK10");
		if (handicapGameT != null) {
			return handicapGameT;
		}
		IbmHandicapT handicapT = createHandicap();
		IbmGameT gameT = createGame();
		handicapGameT = new IbmHandicapGameT();
		handicapGameT.setHandicapId(handicapT.getIbmHandicapId());
		handicapGameT.setGameId(gameT.getIbmGameId());
		handicapGameT.setGameCode(gameT.getGameCode());
		handicapGameT.setHandicapName(handicapT.getHandicapName());
		handicapGameT.setHandicapCode(handicapT.getHandicapCode());
		handicapGameT.setGameName(gameT.getGameName());
		handicapGameT.setSn(1);
		handicapGameT.setCreateUser("doming");
		handicapGameT.setCreateTime(new Date());
		handicapGameT.setCreateTimeLong(handicapGameT.getCreateTime().getTime());
		handicapGameT.setUpdateTime(new Date());
		handicapGameT.setUpdateTimeLong(handicapGameT.getUpdateTime().getTime());
		handicapGameT.setState(IbmStateEnum.OPEN.name());
		handicapGameT.setIbmHandicapGameId(hgService.save(handicapGameT));
		return handicapGameT;
	}

	/**
	 * 创建盘口详情
	 *
	 * @throws Exception
	 */
	private IbmHandicapItemT createHandicapItem() throws Exception {
		IbmHandicapTService handicapTService = new IbmHandicapTService();
		IbmHandicapT handicapT = handicapTService.findByCode("WS2");
		if (handicapT == null) {
			handicapT = createHandicap();
		}

		IbmHandicapItemTService hiService = new IbmHandicapItemTService();
		IbmHandicapItemT handicapItemT = hiService.findByHandicapId(handicapT.getIbmHandicapId());
		if (handicapItemT != null) {
			return handicapItemT;
		}
		handicapItemT = new IbmHandicapItemT();
		handicapItemT.setHandicapId(handicapT.getIbmHandicapId());
		handicapItemT.setHandicapName(handicapT.getHandicapName());
		handicapItemT.setHandicapUrl("http://sf33.qr68.us/");
		handicapItemT.setHandicapCaptcha("az311");
		handicapItemT.setCreateTime(new Date());
		handicapItemT.setCreateTimeLong(handicapItemT.getCreateTime().getTime());
		handicapItemT.setUpdateTime(new Date());
		handicapItemT.setUpdateTimeLong(handicapItemT.getUpdateTime().getTime());
		handicapItemT.setState(IbmStateEnum.OPEN.name());
		hiService.save(handicapItemT);
		return handicapItemT;
	}

}
