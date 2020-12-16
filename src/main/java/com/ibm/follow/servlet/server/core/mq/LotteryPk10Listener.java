package com.ibm.follow.servlet.server.core.mq;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.PK10Tool;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_pk10.entity.IbmRepDrawPk10;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_pk10.service.IbmRepDrawPk10Service;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_pk10.entity.IbmRepGrabPk10;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_pk10.service.IbmRepGrabPk10Service;
import com.ibm.follow.servlet.server.core.job.game.SettlePk10BetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: pk10 开奖结果
 * @Author: Dongming
 * @Date: 2019-02-13 14:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryPk10Listener extends BaseCommMq {

	@Override
	public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.PK10;
		log.info("接收到，游戏【" + game.getName() + "】，的开奖结果【" + message + "】");

		JSONObject jObj = JSONObject.parseObject(message);
		Object gameCode = jObj.get("code");
		Object period = jObj.get("period");
		HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(jObj.getString("handicapCode"));
		if (!game.name().equals(gameCode)) {
			log.warn("非法的游戏code传输，应有的游戏code为【" + game.getName() + "】，传输的code为【" + gameCode + "】");
			return null;
		}
		String type = HandicapGameUtil.lotteryType(game, handicapCode);
		Integer basePeriod =NumberTool.getInteger(PeriodTool.findLotteryPeriod(game, handicapCode));
		if (StringTool.isEmpty(period) || !PeriodTool.equals(basePeriod, period)) {
			if (new IbmRepDrawPk10Service().isExist(period.toString(), type)) {
				log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【" + period + "】");
				return null;
			}
		}
		long drawTimeLong;
		try {
			drawTimeLong = jObj.getLong("drawTimeLong");
		} catch (Exception e) {
			log.warn("获取开奖时间戳失败，传输的时间戳为【" + jObj.get("drawTimeLong") + "】", e);
			return null;
		}
		Object drawNumber = jObj.get("drawNumber");
		if (StringTool.isEmpty(drawNumber) || drawNumber.toString().split(",").length != 10) {
			log.warn("获取开奖号码失败，传输的开奖号码为【" + drawNumber + "】");
			return null;
		}

		try {
			IbmRepGrabPk10Service repGrabPk10Service = new IbmRepGrabPk10Service();
			CurrentTransaction.transactionCommit();
			if (repGrabPk10Service.isExist(period.toString(), type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = new IbmGameService().findId("PK10");
			String[] number = drawNumber.toString().split(",");
			//报表保存结果数据
			IbmRepGrabPk10 repGrabPk10 = new IbmRepGrabPk10();
			repGrabPk10.setGameId(gameId);
			repGrabPk10.setHandicapCode(handicapCode);
			repGrabPk10.setPeriod(period.toString());
			repGrabPk10.setType(type);
			repGrabPk10.setDrawTime(new Date(drawTimeLong));
			repGrabPk10.setDrawTimeLong(drawTimeLong);
			repGrabPk10.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 10; i++) {
				ReflectTool.set(repGrabPk10, "setP" + (i + 1) + "Number", number[i]);
			}
			repGrabPk10.setCreateTime(new Date());
			repGrabPk10.setCreateTimeLong(repGrabPk10.getCreateTime().getTime());
			repGrabPk10.setUpdateTime(new Date());
			repGrabPk10.setUpdateTimeLong(repGrabPk10.getUpdateTime().getTime());
			repGrabPk10.setState(IbmStateEnum.OPEN.name());
			repGrabPk10.setDesc(IpTool.getLocalIpList().toString());
			String grabId = repGrabPk10Service.save(repGrabPk10);

			IbmRepDrawPk10 repDrawPk10 = PK10Tool.draw(grabId, repGrabPk10);
			new IbmRepDrawPk10Service().save(repDrawPk10);

			//期数和基准期数不一样时就不进行结算处理
			if (!PeriodTool.equals(basePeriod, period)) {
				return TypeEnum.TRUE.name();
			}

			CurrentTransaction.transactionCommit();
			//将开奖信息保存到内存中
			if (!PK10Tool.setLottery(NumberTool.getInteger(period), drawNumber.toString())) {
				log.warn("保存PK10开奖结果失败，找到【" + period + "】期的开奖信息为【" + PK10Tool.getLottery(NumberTool.getInteger(period))
						+ "】，想要保存的开奖信息为【" + drawNumber.toString() + "】");
			}

			//结算PK10信息
			new SettlePk10BetItemJob(handicapCode, type).executeJob(null);

		} catch (Exception e) {
			log.warn("保存PK10开奖结果失败");
			throw e;
		}
		return TypeEnum.TRUE.name();
	}
}
