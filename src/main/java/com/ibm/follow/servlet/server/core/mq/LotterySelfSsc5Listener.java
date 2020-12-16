package com.ibm.follow.servlet.server.core.mq;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.period.PeriodHandicapTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.SELFSSC5Tool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_ssc_5.entity.IbmRepDrawSelfSsc5;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_ssc_5.service.IbmRepDrawSelfSsc5Service;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_ssc_5.entity.IbmRepGrabSelfSsc5;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_ssc_5.service.IbmRepGrabSelfSsc5Service;
import com.ibm.follow.servlet.server.core.job.game.SettleSelfSsc5BetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * 自有时时彩五分彩
 *
 * @Author: Dongming
 * @Date: 2020-04-30 16:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotterySelfSsc5Listener  extends BaseCommMq {
	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.SELF_SSC_5;
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
		String basePeriod = PeriodHandicapTool.findLotterySelfSsc5Period(handicapCode).toString();

		long drawTimeLong;
		try {
			drawTimeLong = jObj.getLong("drawTimeLong");
		} catch (Exception e) {
			log.warn("获取开奖时间戳失败，传输的时间戳为【" + jObj.get("drawTimeLong") + "】", e);
			return null;
		}
		Object drawNumber = jObj.get("drawNumber");
		if (StringTool.isEmpty(drawNumber) || drawNumber.toString().split(",").length != 5) {
			log.warn("获取开奖号码失败，传输的开奖号码为【" + drawNumber + "】");
			return null;
		}

		try {
			IbmRepDrawSelfSsc5Service drawSelfSsc5Service = new IbmRepDrawSelfSsc5Service();
			if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
				if (drawSelfSsc5Service.isExist(period.toString(), type)) {
					log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【" + period + "】");
					return null;
				}
			}
			IbmRepGrabSelfSsc5Service grabSelfSsc5Service = new IbmRepGrabSelfSsc5Service();
			CurrentTransaction.transactionCommit();
			if (grabSelfSsc5Service.isExist(period.toString(), type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}
			String gameId = GameUtil.id(gameCode.toString());
			String[] number = drawNumber.toString().split(",");

			IbmRepGrabSelfSsc5 repGrabSelfSsc5 = new IbmRepGrabSelfSsc5();
			repGrabSelfSsc5.setGameId(gameId);
			repGrabSelfSsc5.setType(type);
			repGrabSelfSsc5.setHandicapCode(handicapCode.name());
			repGrabSelfSsc5.setPeriod(period.toString());
			repGrabSelfSsc5.setDrawTime(new Date(drawTimeLong));
			repGrabSelfSsc5.setDrawTimeLong(drawTimeLong);
			repGrabSelfSsc5.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 5; i++) {
				ReflectTool.set(repGrabSelfSsc5, "setP" + (i + 1) + "Number", number[i]);
			}
			repGrabSelfSsc5.setCreateTime(new Date());
			repGrabSelfSsc5.setCreateTimeLong(System.currentTimeMillis());
			repGrabSelfSsc5.setUpdateTimeLong(System.currentTimeMillis());
			repGrabSelfSsc5.setState(IbmStateEnum.OPEN.name());
			repGrabSelfSsc5.setDesc(IpTool.getLocalIpList().toString());
			String grabId = grabSelfSsc5Service.save(repGrabSelfSsc5);
			IbmRepDrawSelfSsc5 drawSelf102 = SELFSSC5Tool.draw(grabId, repGrabSelfSsc5);
			drawSelfSsc5Service.save(drawSelf102);

			CurrentTransaction.transactionCommit();
			//结算0信息
			new SettleSelfSsc5BetItemJob(handicapCode,type).executeJob(null);

		} catch (Exception e) {
			log.warn("保存{}开奖结果失败", game.getName());
			throw e;
		}

		return TypeEnum.TRUE.name();
	}
}
