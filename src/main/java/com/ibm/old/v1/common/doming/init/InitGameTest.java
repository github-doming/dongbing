package com.ibm.old.v1.common.doming.init;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_game.t.entity.IbmGameT;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.junit.Test;

import java.util.Date;
/**
 * @Description: 初始化游戏
 * @Author: Dongming
 * @Date: 2018-10-15 17:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InitGameTest extends CommTest {

	/**
	 * 初始化游戏
	 *
	 * @throws Exception
	 */
	@Test public void initGame() {

		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			initGamePk10();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

	/**
	 * 初始化北京赛车pk10
	 *
	 * @throws Exception
	 */
	private static void initGamePk10() throws Exception {
		IbmGameT gameT = new IbmGameT();
		IbmGameTService gameTService = new IbmGameTService();
		gameT.setGameName("北京赛车");
		gameT.setGameCode("PK10");
		gameT.setRepGrabTableName("IBM_REP_GRAB_PK10");
		gameT.setRepDrawTableName("IBM_REP_DRAW_PK10");
		gameT.setSn(1);
		gameT.setCreateUser("doming");
		gameT.setCreateTime(new Date());
		gameT.setCreateTimeLong(gameT.getCreateTime().getTime());
		gameT.setUpdateTime(new Date());
		gameT.setUpdateUser("doming");
		gameT.setUpdateTimeLong(gameT.getUpdateTime().getTime());
		gameT.setState(IbmStateEnum.OPEN.name());
		gameTService.save(gameT);
	}

}
