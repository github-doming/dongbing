package com.ibm.follow.servlet.server.core.mq;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.period.PeriodHandicapTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.JS10Tool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_js10.entity.IbmRepDrawJs10;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_js10.service.IbmRepDrawJs10Service;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_js10.entity.IbmRepGrabJs10;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_js10.service.IbmRepGrabJs10Service;
import com.ibm.follow.servlet.server.core.job.game.SettleJs10BetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: JS10 开奖结果
 * @Author: Dongming
 * @Date: 2019-10-17 18:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryJs10Listener  extends BaseCommMq {
	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.JS10;
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
        String basePeriod=PeriodHandicapTool.findLotteryJs10Period(handicapCode).toString();
		if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
		    if(new IbmRepDrawJs10Service().isExist(period.toString(),type)){
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
		if (StringTool.isEmpty(drawNumber) || drawNumber.toString().split(",").length != 10) {
			log.warn("获取开奖号码失败，传输的开奖号码为【" + drawNumber + "】");
			return null;
		}
		try {
			IbmRepGrabJs10Service repGrabJs10Service = new IbmRepGrabJs10Service();
			CurrentTransaction.transactionCommit();
			if (repGrabJs10Service.isExist(period.toString(),type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = GameUtil.id(game);
			String[] number = drawNumber.toString().split(",");
			IbmRepGrabJs10 repGrabJs10 = new IbmRepGrabJs10();
            repGrabJs10.setHandicapCode(handicapCode);
			repGrabJs10.setGameId(gameId);
            repGrabJs10.setType(type);
			repGrabJs10.setHandicapCode(handicapCode.name());
			repGrabJs10.setPeriod(period.toString());
			repGrabJs10.setDrawTime(new Date(drawTimeLong));
			repGrabJs10.setDrawTimeLong(drawTimeLong);
			repGrabJs10.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 10; i++) {
				ReflectTool.set(repGrabJs10, "setP" + (i + 1) + "Number", number[i]);
			}
			repGrabJs10.setCreateTime(new Date());
			repGrabJs10.setCreateTimeLong(System.currentTimeMillis());
			repGrabJs10.setUpdateTimeLong(System.currentTimeMillis());
			repGrabJs10.setState(IbmStateEnum.OPEN.name());
			repGrabJs10.setDesc(IpTool.getLocalIpList().toString());
			String grabId = repGrabJs10Service.save(repGrabJs10);
			IbmRepDrawJs10 repDrawJs10 = JS10Tool.draw(grabId, repGrabJs10);
			new IbmRepDrawJs10Service().save(repDrawJs10);

			if(!period.toString().equals(basePeriod)){
                return TypeEnum.TRUE.name();
            }

			CurrentTransaction.transactionCommit();
			//结算JS10信息
			new SettleJs10BetItemJob(handicapCode,type).executeJob(null);
		} catch (Exception e) {
			log.warn("保存JS10开奖结果失败");
			throw e;
		}

		return TypeEnum.TRUE.name();

	}
}
