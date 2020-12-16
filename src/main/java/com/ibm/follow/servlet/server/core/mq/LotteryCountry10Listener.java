package com.ibm.follow.servlet.server.core.mq;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.period.PeriodHandicapTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.COUNTRY10Tool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_country_10.entity.IbmRepDrawCountry10;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_country_10.service.IbmRepDrawCountry10Service;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_country_10.entity.IbmRepGrabCountry10;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_country_10.service.IbmRepGrabCountry10Service;
import com.ibm.follow.servlet.server.core.job.game.SettleCountry10BetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * 国家赛车
 * @Author: Dongming
 * @Date: 2020-04-30 17:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryCountry10Listener extends BaseCommMq {
	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.COUNTRY_10;
		log.info("接收到，游戏【" + game.getName() + "】，的开奖结果【" + message + "】");
		JSONObject jObj = JSONObject.parseObject(message);
		Object gameCode = jObj.get("code");
		Object period = jObj.get("period");
		if (!game.name().equals(gameCode)) {
			log.warn("非法的游戏code传输，应有的游戏code为【" + game.name() + "】，传输的code为【" + gameCode + "】");
			return null;
		}
		HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(jObj.getString("handicapCode"));

		String type = HandicapGameUtil.lotteryType(game, handicapCode);
		String basePeriod = PeriodHandicapTool.findLotteryCountry10Period(handicapCode).toString();

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
			IbmRepDrawCountry10Service drawCountry10Service = new IbmRepDrawCountry10Service();
			if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
				if (drawCountry10Service.isExist(period.toString(), type)) {
					log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【" + period + "】");
					return null;
				}
			}

			IbmRepGrabCountry10Service grabCountry10Service = new IbmRepGrabCountry10Service();
			CurrentTransaction.transactionCommit();
			if (grabCountry10Service.isExist(period.toString(), type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}
			String gameId = GameUtil.id(gameCode.toString());
			String[] number = drawNumber.toString().split(",");

			IbmRepGrabCountry10 grabCountry10 = new IbmRepGrabCountry10();
			grabCountry10.setGameId(gameId);
			grabCountry10.setType(type);
			grabCountry10.setHandicapCode(handicapCode.name());
			grabCountry10.setPeriod(period.toString());
			grabCountry10.setDrawTime(new Date(drawTimeLong));
			grabCountry10.setDrawTimeLong(drawTimeLong);
			grabCountry10.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 10; i++) {
				ReflectTool.set(grabCountry10, "setP" + (i + 1) + "Number", number[i]);
			}
			grabCountry10.setCreateTime(new Date());
			grabCountry10.setCreateTimeLong(System.currentTimeMillis());
			grabCountry10.setUpdateTimeLong(System.currentTimeMillis());
			grabCountry10.setState(IbmStateEnum.OPEN.name());
			grabCountry10.setDesc(IpTool.getLocalIpList().toString());
			String grabId = grabCountry10Service.save(grabCountry10);
			IbmRepDrawCountry10 drawSelf102 = COUNTRY10Tool.draw(grabId, grabCountry10);
			drawCountry10Service.save(drawSelf102);

			if(!period.toString().equals(basePeriod)){
				return TypeEnum.TRUE.name();
			}
			CurrentTransaction.transactionCommit();
			//结算0信息
			new SettleCountry10BetItemJob(handicapCode,type).executeJob(null);
		} catch (Exception e) {
			log.warn("保存{}开奖结果失败", game.getName());
			throw e;
		}

		return TypeEnum.TRUE.name();
	}
}
