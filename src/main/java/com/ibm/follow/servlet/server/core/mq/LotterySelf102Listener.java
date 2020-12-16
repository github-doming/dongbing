package com.ibm.follow.servlet.server.core.mq;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.period.PeriodHandicapTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.SELF102Tool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_10_2.entity.IbmRepDrawSelf102;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_10_2.service.IbmRepDrawSelf102Service;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_10_2.entity.IbmRepGrabSelf102;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_self_10_2.service.IbmRepGrabSelf102Service;
import com.ibm.follow.servlet.server.core.job.game.SettleSelf102BetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * 自有赛车两分彩
 *
 * @Author: Dongming
 * @Date: 2020-04-29 15:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotterySelf102Listener extends BaseCommMq {
	@Override public String execute(String message) throws Exception {
		GameUtil.Code game = GameUtil.Code.SELF_10_2;
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
		String basePeriod = PeriodHandicapTool.findLotterySelf102Period(handicapCode).toString();

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
			IbmRepDrawSelf102Service drawSelf102Service = new IbmRepDrawSelf102Service();
			if (StringTool.isEmpty(period) || !period.toString().equals(basePeriod)) {
				if (drawSelf102Service.isExist(period.toString(), type)) {
					log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【" + period + "】");
					return null;
				}
			}
			
			IbmRepGrabSelf102Service grabSelf102Service = new IbmRepGrabSelf102Service();
			CurrentTransaction.transactionCommit();
			if (grabSelf102Service.isExist(period.toString(), type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = GameUtil.id(gameCode.toString());
			String[] number = drawNumber.toString().split(",");

			IbmRepGrabSelf102 grabSelf102 = new IbmRepGrabSelf102();
			grabSelf102.setGameId(gameId);
			grabSelf102.setType(type);
			grabSelf102.setHandicapCode(handicapCode.name());
			grabSelf102.setPeriod(period.toString());
			grabSelf102.setDrawTime(new Date(drawTimeLong));
			grabSelf102.setDrawTimeLong(drawTimeLong);
			grabSelf102.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 10; i++) {
				ReflectTool.set(grabSelf102, "setP" + (i + 1) + "Number", number[i]);
			}
			grabSelf102.setCreateTime(new Date());
			grabSelf102.setCreateTimeLong(System.currentTimeMillis());
			grabSelf102.setUpdateTimeLong(System.currentTimeMillis());
			grabSelf102.setState(IbmStateEnum.OPEN.name());
			grabSelf102.setDesc(IpTool.getLocalIpList().toString());
			String grabId = grabSelf102Service.save(grabSelf102);
			IbmRepDrawSelf102 drawSelf102 = SELF102Tool.draw(grabId, grabSelf102);
			drawSelf102Service.save(drawSelf102);

			if(!period.toString().equals(basePeriod)){
				return TypeEnum.TRUE.name();
			}

			CurrentTransaction.transactionCommit();
			//结算0信息
			new SettleSelf102BetItemJob(handicapCode,type).executeJob(null);
		} catch (Exception e) {
			log.warn("保存{}开奖结果失败", game.getName());
			throw e;
		}

		return TypeEnum.TRUE.name();
	}
}
