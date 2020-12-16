package com.ibm.follow.servlet.server.core.mq;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.period.PeriodHandicapTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.SELF105Tool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_10_5.entity.IbmRepDrawSelf105;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_10_5.service.IbmRepDrawSelf105Service;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_10_5.entity.IbmRepGrabSelf105;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_10_5.service.IbmRepGrabSelf105Service;
import com.ibm.follow.servlet.server.core.job.game.SettleSelf105BetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * 自有赛车五分彩 --飞艇五分
 *
 * @Author: Dongming
 * @Date: 2020-04-29 15:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotterySelf105Listener extends BaseCommMq {
	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.SELF_10_5;
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
		String basePeriod = PeriodHandicapTool.findLotterySelf105Period(handicapCode).toString();

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
			IbmRepDrawSelf105Service drawSelf105Service = new IbmRepDrawSelf105Service();
			if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
				if (drawSelf105Service.isExist(period.toString(), type)) {
					log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【" + period + "】");
					return null;
				}
			}
			
			IbmRepGrabSelf105Service grabSelf105Service = new IbmRepGrabSelf105Service();
			CurrentTransaction.transactionCommit();
			if (grabSelf105Service.isExist(period.toString(), type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = GameUtil.id(gameCode.toString());
			String[] number = drawNumber.toString().split(",");

			IbmRepGrabSelf105 grabSelf105 = new IbmRepGrabSelf105();
			grabSelf105.setGameId(gameId);
			grabSelf105.setType(type);
			grabSelf105.setHandicapCode(handicapCode.name());
			grabSelf105.setPeriod(period.toString());
			grabSelf105.setDrawTime(new Date(drawTimeLong));
			grabSelf105.setDrawTimeLong(drawTimeLong);
			grabSelf105.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 10; i++) {
				ReflectTool.set(grabSelf105, "setP" + (i + 1) + "Number", number[i]);
			}
			grabSelf105.setCreateTime(new Date());
			grabSelf105.setCreateTimeLong(System.currentTimeMillis());
			grabSelf105.setUpdateTimeLong(System.currentTimeMillis());
			grabSelf105.setState(IbmStateEnum.OPEN.name());
			grabSelf105.setDesc(IpTool.getLocalIpList().toString());
			String grabId = grabSelf105Service.save(grabSelf105);
			IbmRepDrawSelf105 drawSelf105 = SELF105Tool.draw(grabId, grabSelf105);
			drawSelf105Service.save(drawSelf105);

			if(!period.toString().equals(basePeriod)){
				return TypeEnum.TRUE.name();
			}

			CurrentTransaction.transactionCommit();
			//结算0信息
			new SettleSelf105BetItemJob(handicapCode,type).executeJob(null);
		} catch (Exception e) {
			log.warn("保存{}开奖结果失败", game.getName());
			throw e;
		}

		return TypeEnum.TRUE.name();
	}
}
