package com.ibm.follow.servlet.client.core.job.bet;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.CustomerTool;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.GameMergeTool;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.bet.FollowBetInfoController;
import com.ibm.follow.servlet.client.ibmc_hm_bet.entity.IbmcHmBet;
import com.ibm.follow.servlet.client.ibmc_hm_bet.service.IbmcHmBetService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 合并投注项后投注
 * @Author: Dongming
 * @Date: 2019-09-16 14:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientMergeBetJob extends BaseCommJob {
	private String existHmId;
	private GameUtil.Code gameCode;
	private HandicapUtil.Code handicapCode;
	private Object period;
	private double betRate;
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		//合并
		if (context != null) {
			JobDataMap map = context.getMergedJobDataMap();
			existHmId = map.getString("existHmId");
            if(map.get("gameCode") instanceof Enum){
                gameCode = (GameUtil.Code) map.get("gameCode");
            }else{
                gameCode=GameUtil.Code.valueOf(map.get("gameCode").toString());
            }
			handicapCode = (HandicapUtil.Code) map.get("handicapCode");
			period = map.get("period");
			betRate = map.getDouble("betRate");
		}
        if (CustomerCache.stateInfo(existHmId) == null){
            log.error(String.format("用户【%s】的【%s】游戏在【%s】期，会员已经登出，无需合并", existHmId, gameCode.getName(), period));
            return;
        }

		Map<String, int[][]> betInfo = CustomerTool.getBetInfo(existHmId, gameCode);
        String followBetIds= CustomerTool.getFollowBetIds(existHmId,gameCode);
		if (ContainerTool.isEmpty(betInfo)|| StringTool.isEmpty(followBetIds)) {
			log.error(String.format("用户【%s】的【%s】游戏在【%s】期，获取到的合并投注编码为空，无法合并,错误跟投id为【%s】", existHmId, gameCode.getName(), period,followBetIds));
			return;
		}
		//合并投注项资金
		GameMergeTool.mergeInfo(gameCode, betInfo);

		//合并投注项
		List<Object> info = GameMergeTool.mergeItem(gameCode,betInfo,betRate);
		String betItem= (String) info.get(0);
		Integer fundsTh = (Integer) info.get(1);

		// 存储投注信息到数据库
		IbmcHmBetService hmBetService = new IbmcHmBetService();
		IbmcHmBet hmBet = new IbmcHmBet();
		hmBet.setExistHmId(existHmId);
		hmBet.setHaFollowBetId(followBetIds);
		hmBet.setGameCode(gameCode.name());
		hmBet.setPeriod(period);
		hmBet.setBetInfo(betItem);
		hmBet.setBetFundT(fundsTh);
		hmBet.setBetInfoCode(EncryptTool.encode(EncryptTool.Type.MD5, hmBet.getBetInfo()));
		hmBet.setCreateTime(new Date());
		hmBet.setCreateTimeLong(System.currentTimeMillis());
		hmBet.setUpdateTimeLong(System.currentTimeMillis());
		if(StringTool.isEmpty(betItem)){
            hmBet.setState(IbmStateEnum.BET.name());
            hmBet.setDesc("合并投注信息为空");
        }else{
            hmBet.setState(IbmStateEnum.OPEN.name());
        }
		hmBetService.save(hmBet);
		//投注
		FollowBetInfoController.followBet(existHmId, gameCode, handicapCode, period);
		log.info(String.format("用户【%s】的【%s】游戏在【%s】期，获取到的合并投注完成", existHmId, gameCode.getName(), period));
	}

	public ClientMergeBetJob mergeBet(String existHmId, GameUtil.Code gameCode, Object period,
			HandicapUtil.Code handicapCode, double betRate) {
		this.existHmId = existHmId;
		this.gameCode = gameCode;
		this.period = period;
		this.handicapCode = handicapCode;
		this.betRate = betRate;
		return this;
	}

}
