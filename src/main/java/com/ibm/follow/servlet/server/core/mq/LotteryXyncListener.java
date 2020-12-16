package com.ibm.follow.servlet.server.core.mq;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.XYNCTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_xync.entity.IbmRepDrawXync;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_xync.service.IbmRepDrawXyncService;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_xync.entity.IbmRepGrabXync;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_xync.service.IbmRepGrabXyncService;
import com.ibm.follow.servlet.server.core.job.game.SettleXyncBetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * 幸运农场
 *
 * @Author: Dongming
 * @Date: 2020-04-22 15:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryXyncListener extends BaseCommMq {
	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.XYNC;

		log.info("接收到，游戏【" + game.getName() + "】，的开奖结果【" + message + "】");
		JSONObject jObj = JSONObject.parseObject(message);
		Object gameCode = jObj.get("code");
		Object period = jObj.get("period");
		HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(jObj.getString("handicapCode"));
		if (!game.name().equals(gameCode)) {
			log.warn("非法的游戏code传输，应有的游戏code为【" + game.getName() + "】，传输的code为【" + gameCode + "】");
			return null;
		}

		String type= HandicapGameUtil.lotteryType(game,handicapCode);
		String basePeriod= PeriodTool.findLotteryPeriod(game,handicapCode).toString();
		if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
			if(new IbmRepDrawXyncService().isExist(period.toString(),type)){
				log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【"
						+ period + "】");
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
		if (StringTool.isEmpty(drawNumber) || drawNumber.toString().split(",").length != 8) {
			log.warn("获取开奖号码失败，传输的开奖号码为【" + drawNumber + "】");
			return null;
		}
		try {
			IbmRepGrabXyncService repGrabXyncService = new IbmRepGrabXyncService();
			CurrentTransaction.transactionCommit();
			if (repGrabXyncService.isExist(period.toString(),type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}
			String gameId = GameUtil.id(game);

			String[] number = drawNumber.toString().split(",");

			//报表保存结果数据
			IbmRepGrabXync repGrabXync = new IbmRepGrabXync();
			repGrabXync.setHandicapCode(handicapCode);
			repGrabXync.setGameId(gameId);
			repGrabXync.setType(type);
			repGrabXync.setPeriod(period.toString());
			repGrabXync.setDrawTime(new Date(drawTimeLong));
			repGrabXync.setDrawTimeLong(drawTimeLong);
			repGrabXync.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 8; i++) {
				ReflectTool.set(repGrabXync, "setP" + (i + 1) + "Number", number[i]);
			}
			repGrabXync.setCreateTime(new Date());
			repGrabXync.setCreateTimeLong(repGrabXync.getCreateTime().getTime());
			repGrabXync.setUpdateTime(new Date());
			repGrabXync.setUpdateTimeLong(repGrabXync.getUpdateTime().getTime());
			repGrabXync.setState(IbmStateEnum.OPEN.name());
			repGrabXync.setDesc(IpTool.getLocalIpList().toString());
			String grabId = repGrabXyncService.save(repGrabXync);

			IbmRepDrawXync repDrawXync = XYNCTool.draw(grabId, repGrabXync);
			new IbmRepDrawXyncService().save(repDrawXync);

			//期数和基准期数不一样时就不进行结算处理
			if(!basePeriod.equals(period)){
				return TypeEnum.TRUE.name();
			}
			CurrentTransaction.transactionCommit();
			//将开奖信息保存到内存中
			if (!XYNCTool.setLottery(period.toString(), drawNumber.toString(),type)) {
				log.warn("保存 "+game.getName()+" 开奖结果失败，找到【" + period + "】期的开奖信息为【" + XYNCTool.getLottery(period.toString(),type)
						+ "】，想要保存的开奖信息为【" + drawNumber.toString() + "】");
			}

			//结算XYFT信息
			new SettleXyncBetItemJob(handicapCode,type).executeJob(null);


		} catch (Exception e) {
			log.warn("保存 XYNC 开奖结果失败");
			throw e;
		}
		return TypeEnum.TRUE.name();
	}

}
