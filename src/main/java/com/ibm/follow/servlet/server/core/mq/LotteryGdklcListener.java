package com.ibm.follow.servlet.server.core.mq;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.GDKLCTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_gdklc.entity.IbmRepDrawGdklc;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_gdklc.service.IbmRepDrawGdklcService;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_gdklc.entity.IbmRepGrabGdklc;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_gdklc.service.IbmRepGrabGdklcService;
import com.ibm.follow.servlet.server.core.job.game.SettleGdklcBetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * 广东快乐彩
 *
 * @Author: Dongming
 * @Date: 2020-04-28 15:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryGdklcListener extends BaseCommMq {
	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.GDKLC;

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
		String basePeriod = PeriodTool.findLotteryPeriod(game, handicapCode).toString();

		long drawTimeLong;
		try {
			drawTimeLong = jObj.getLong("drawTimeLong");
		} catch (Exception e) {
			log.warn("获取开奖时间戳失败，传输的时间戳为【" + jObj.get("drawTimeLong") + "】", e);
			return null;
		}
		Object drawNumber = jObj.get("drawNumber");
		if (StringTool.isEmpty(drawNumber) || drawNumber.toString().split(",").length != 8) {
			log.warn("获取开奖号码失败，传输的开奖号码为【" + drawNumber + "】");
			return null;
		}

		try {
			IbmRepGrabGdklcService repGrabGdklcService = new IbmRepGrabGdklcService();
			IbmRepDrawGdklcService repDrawGdklcService = new IbmRepDrawGdklcService();
			if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
				if (repDrawGdklcService.isExist(period.toString(), type)) {
					log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【" + period + "】");
					return null;
				}
			}
			CurrentTransaction.transactionCommit();
			if (repGrabGdklcService.isExist(period.toString(), type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}
			String gameId = GameUtil.id(gameCode.toString());
			String[] number = drawNumber.toString().split(",");
			//报表保存结果数据
			IbmRepGrabGdklc repGrabGdklc = new IbmRepGrabGdklc();
			repGrabGdklc.setHandicapCode(handicapCode);
			repGrabGdklc.setGameId(gameId);
			repGrabGdklc.setType(type);
			repGrabGdklc.setPeriod(period.toString());
			repGrabGdklc.setDrawTime(new Date(drawTimeLong));
			repGrabGdklc.setDrawTimeLong(drawTimeLong);
			repGrabGdklc.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 8; i++) {
				ReflectTool.set(repGrabGdklc, "setP" + (i + 1) + "Number", number[i]);
			}
			repGrabGdklc.setCreateTime(new Date());
			repGrabGdklc.setCreateTimeLong(repGrabGdklc.getCreateTime().getTime());
			repGrabGdklc.setUpdateTime(new Date());
			repGrabGdklc.setUpdateTimeLong(repGrabGdklc.getUpdateTime().getTime());
			repGrabGdklc.setState(IbmStateEnum.OPEN.name());
			repGrabGdklc.setDesc(IpTool.getLocalIpList().toString());
			String grabId = repGrabGdklcService.save(repGrabGdklc);

			IbmRepDrawGdklc repDrawGdklc = GDKLCTool.draw(grabId, repGrabGdklc);
			repDrawGdklcService.save(repDrawGdklc);

			//期数和基准期数不一样时就不进行结算处理
			if (!basePeriod.equals(period)) {
				return TypeEnum.TRUE.name();
			}

			CurrentTransaction.transactionCommit();
			//将开奖信息保存到内存中
			if (!GDKLCTool.setLottery(period.toString(), drawNumber.toString(), type)) {
				log.warn("保存 "+game.getName()+" 开奖结果失败，找到【" + period + "】期的开奖信息为【" + GDKLCTool.getLottery(period.toString(), type)
						+ "】，想要保存的开奖信息为【" + drawNumber.toString() + "】");
			}

			//结算XYFT信息
			new SettleGdklcBetItemJob(handicapCode, type).executeJob(null);

		} catch (Exception e) {
			log.warn("保存 XYNC 开奖结果失败");
			throw e;
		}
		return TypeEnum.TRUE.name();
	}
}
