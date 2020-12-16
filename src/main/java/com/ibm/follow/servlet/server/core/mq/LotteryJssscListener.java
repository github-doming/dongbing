package com.ibm.follow.servlet.server.core.mq;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.period.PeriodHandicapTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.JSSSCTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_jsssc.entity.IbmRepDrawJsssc;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_jsssc.service.IbmRepDrawJssscService;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_jsssc.entity.IbmRepGrabJsssc;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_jsssc.service.IbmRepGrabJssscService;
import com.ibm.follow.servlet.server.core.job.game.SettleJssscBetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: JSSSC 开奖结果
 * @Author: Dongming
 * @Date: 2019-10-17 18:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryJssscListener extends BaseCommMq {

	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.JSSSC;
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
        String basePeriod=PeriodHandicapTool.findLotteryJssscPeriod(handicapCode).toString();
		if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
		    if(new IbmRepDrawJssscService().isExist(period.toString(),type)){
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
		if (StringTool.isEmpty(drawNumber) || drawNumber.toString().split(",").length != 5) {
			log.warn("获取开奖号码失败，传输的开奖号码为【" + drawNumber + "】");
			return null;
		}
		try {
			IbmRepGrabJssscService repGrabJssscService = new IbmRepGrabJssscService();
			CurrentTransaction.transactionCommit();
			if (repGrabJssscService.isExist(period.toString(),type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = GameUtil.id(game);
			String[] number = drawNumber.toString().split(",");

			IbmRepGrabJsssc repGrabJsssc = new IbmRepGrabJsssc();
			repGrabJsssc.setGameId(gameId);
			repGrabJsssc.setHandicapCode(handicapCode.name());
            repGrabJsssc.setType(type);
			repGrabJsssc.setPeriod(period.toString());
			repGrabJsssc.setDrawTime(new Date(drawTimeLong));
			repGrabJsssc.setDrawTimeLong(drawTimeLong);
			repGrabJsssc.setDrawNumber(drawNumber.toString());
			repGrabJsssc.setP1Number(number[0]);
			repGrabJsssc.setP2Number(number[1]);
			repGrabJsssc.setP3Number(number[2]);
			repGrabJsssc.setP4Number(number[3]);
			repGrabJsssc.setP5Number(number[4]);
			repGrabJsssc.setCreateTime(new Date());
			repGrabJsssc.setCreateTimeLong(System.currentTimeMillis());
			repGrabJsssc.setUpdateTimeLong(System.currentTimeMillis());
			repGrabJsssc.setState(IbmStateEnum.OPEN.name());
			repGrabJsssc.setDesc(IpTool.getLocalIpList().toString());
			String grabId = repGrabJssscService.save(repGrabJsssc);
			IbmRepDrawJsssc repDrawJsssc = JSSSCTool.draw(grabId,repGrabJsssc);
			new IbmRepDrawJssscService().save(repDrawJsssc);

			if(!period.toString().equals(basePeriod)){
                return TypeEnum.TRUE.name();
            }
			CurrentTransaction.transactionCommit();
			//结算JSSSC信息
			new SettleJssscBetItemJob(handicapCode,type).executeJob(null);
		} catch (Exception e) {
			log.warn("保存JSSSC开奖结果失败");
			throw e;
		}

		return TypeEnum.TRUE.name();
	}
}
