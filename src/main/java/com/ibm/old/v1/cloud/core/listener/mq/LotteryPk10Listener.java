package com.ibm.old.v1.cloud.core.listener.mq;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.job.pk10.*;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.PK10Tool;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_rep_draw_pk10.t.entity.IbmRepDrawPk10T;
import com.ibm.old.v1.cloud.ibm_rep_draw_pk10.t.service.IbmRepDrawPk10TService;
import com.ibm.old.v1.cloud.ibm_rep_grab_pk10.t.entity.IbmRepGrabPk10T;
import com.ibm.old.v1.cloud.ibm_rep_grab_pk10.t.service.IbmRepGrabPk10TService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
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

	@Override public String execute(String message) throws Exception {
		IbmGameEnum game = IbmGameEnum.PK10;

		JSONObject jObj = JSONObject.parseObject(message);
		Object gameCode = jObj.get("code");
		Object period = jObj.get("period");

		if (!game.name().equals(gameCode)) {
			log.warn("非法的游戏code传输，应有的游戏code为【" + game.getName() + "】，传输的code为【" + gameCode + "】");
			return null;
		}
		if (StringTool.isEmpty(period) || !PeriodTool.equals(PeriodTool.findLotteryPK10Period(), period)) {
			log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + PeriodTool.findLotteryPK10Period() + "】，传输的游戏游戏期数为【" + period + "】");
			return null;
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
			IbmRepGrabPk10TService repGrabPk10Service = new IbmRepGrabPk10TService();
			CurrentTransaction.transactionCommit();
			if (repGrabPk10Service.isExist(period.toString())) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = new IbmGameTService().findId("PK10");
			String[] number = drawNumber.toString().split(",");
			//报表保存结果数据
			IbmRepGrabPk10T repGrabPk10 = new IbmRepGrabPk10T();
			repGrabPk10.setGameId(gameId);
			repGrabPk10.setPeriod(period.toString());
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

			IbmRepDrawPk10T repDrawPk10 = GameTool.draw(grabId, repGrabPk10);
			new IbmRepDrawPk10TService().save(repDrawPk10);

			CurrentTransaction.transactionCommit();
			//将开奖信息保存到内存中
			if (!PK10Tool.setLottery(NumberTool.getInteger(period), drawNumber.toString())) {
				log.warn("保存PK10开奖结果失败，找到【" + period + "】期的开奖信息为【" + PK10Tool.getLottery(NumberTool.getInteger(period))
						+ "】，想要保存的开奖信息为【" + drawNumber.toString() + "】");
			}

			//结算PK10信息
			new SettlePk10BetItemJob().executeJob(null);
			Thread.sleep(3*1000);

			new CodingPk10PlanJob().executeJob(null);
			Thread.sleep(3*1000);

			new TurnPk10CodeJob().executeJob(null);
			Thread.sleep(3*1000);

			new MergePk10BetItemJob().executeJob(null);
			Thread.sleep(3*1000);

			new SendPk10BetItemJob().executeJob(null);
		} catch (Exception e) {
			log.warn("保存PK10开奖结果失败");
			throw e;
		}
		return TypeEnum.TRUE.name();
	}
}
