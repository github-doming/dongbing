package com.ibm.follow.servlet.server.core.mq;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.period.PeriodHandicapTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.COUNTRYSSCTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_country_ssc.entity.IbmRepDrawCountrySsc;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_country_ssc.service.IbmRepDrawCountrySscService;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_country_ssc.entity.IbmRepGrabCountrySsc;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_country_ssc.service.IbmRepGrabCountrySscService;
import com.ibm.follow.servlet.server.core.job.game.SettleCountrySscBetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * 国家时时彩
 *
 * @Author: Dongming
 * @Date: 2020-04-30 17:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryCountrySscListener  extends BaseCommMq {
	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.COUNTRY_SSC;
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
		String basePeriod = PeriodHandicapTool.findLotteryCountrySscPeriod(handicapCode).toString();

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
			IbmRepDrawCountrySscService drawCountrySscService = new IbmRepDrawCountrySscService();
			if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
				if (drawCountrySscService.isExist(period.toString(), type)) {
					log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【" + period + "】");
					return null;
				}
			}
			IbmRepGrabCountrySscService grabCountrySscService = new IbmRepGrabCountrySscService();
			CurrentTransaction.transactionCommit();
			if (grabCountrySscService.isExist(period.toString(), type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}
			String gameId = GameUtil.id(gameCode.toString());
			String[] number = drawNumber.toString().split(",");

			IbmRepGrabCountrySsc repGrabCountrySsc = new IbmRepGrabCountrySsc();
			repGrabCountrySsc.setGameId(gameId);
			repGrabCountrySsc.setType(type);
			repGrabCountrySsc.setHandicapCode(handicapCode.name());
			repGrabCountrySsc.setPeriod(period.toString());
			repGrabCountrySsc.setDrawTime(new Date(drawTimeLong));
			repGrabCountrySsc.setDrawTimeLong(drawTimeLong);
			repGrabCountrySsc.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 5; i++) {
				ReflectTool.set(repGrabCountrySsc, "setP" + (i + 1) + "Number", number[i]);
			}
			repGrabCountrySsc.setCreateTime(new Date());
			repGrabCountrySsc.setCreateTimeLong(System.currentTimeMillis());
			repGrabCountrySsc.setUpdateTimeLong(System.currentTimeMillis());
			repGrabCountrySsc.setState(IbmStateEnum.OPEN.name());
			repGrabCountrySsc.setDesc(IpTool.getLocalIpList().toString());
			String grabId = grabCountrySscService.save(repGrabCountrySsc);
			IbmRepDrawCountrySsc drawCountrySsc = COUNTRYSSCTool.draw(grabId, repGrabCountrySsc);
			drawCountrySscService.save(drawCountrySsc);

			CurrentTransaction.transactionCommit();
			//结算0信息
			new SettleCountrySscBetItemJob(handicapCode,type).executeJob(null);

		} catch (Exception e) {
			log.warn("保存{}开奖结果失败", game.getName());
			throw e;
		}

		return TypeEnum.TRUE.name();
	}
}
