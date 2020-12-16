package com.ibm.old.v1.cloud.core.listener.mq;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.job.xyft.*;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.XYFTTool;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_rep_draw_xyft.t.entity.IbmRepDrawXyftT;
import com.ibm.old.v1.cloud.ibm_rep_draw_xyft.t.service.IbmRepDrawXyftTService;
import com.ibm.old.v1.cloud.ibm_rep_grab_xyft.t.entity.IbmRepGrabXyftT;
import com.ibm.old.v1.cloud.ibm_rep_grab_xyft.t.service.IbmRepGrabXyftTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: XYFT 开奖结果
 * @Author: Dongming
 * @Date: 2019-02-13 17:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryXyftListener extends BaseCommMq {

	@Override public String execute(String message) throws Exception {
		IbmGameEnum game = IbmGameEnum.XYFT;
		JSONObject jObj = JSONObject.parseObject(message);
		Object gameCode = jObj.get("code");
		Object period = jObj.get("period");

		if (!game.name().equals(gameCode)) {
			log.warn("非法的游戏code传输，应有的游戏code为【" + game.getName() + "】，传输的code为【" + gameCode + "】");
			return null;
		}
		if (StringTool.isEmpty(period) || !PeriodTool.findLotteryXYFTPeriod().equals(period)) {
			log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + PeriodTool.findLotteryXYFTPeriod() + "】，传输的游戏游戏期数为【" + period + "】");
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

			IbmRepGrabXyftTService repGrabXyftService = new IbmRepGrabXyftTService();
			CurrentTransaction.transactionCommit();
			if (repGrabXyftService.isExist(period.toString())) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = new IbmGameTService().findId("XYFT");

			String[] number = drawNumber.toString().split(",");

			//报表保存结果数据
			IbmRepGrabXyftT repGrabXyft = new IbmRepGrabXyftT();
			repGrabXyft.setGameId(gameId);
			repGrabXyft.setPeriod(period.toString());
			repGrabXyft.setDrawTime(new Date(drawTimeLong));
			repGrabXyft.setDrawTimeLong(drawTimeLong);
			repGrabXyft.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 10; i++) {
				ReflectTool.set(repGrabXyft, "setP" + (i + 1) + "Number", number[i]);
			}
			repGrabXyft.setCreateTime(new Date());
			repGrabXyft.setCreateTimeLong(repGrabXyft.getCreateTime().getTime());
			repGrabXyft.setUpdateTime(new Date());
			repGrabXyft.setUpdateTimeLong(repGrabXyft.getUpdateTime().getTime());
			repGrabXyft.setState(IbmStateEnum.OPEN.name());
			repGrabXyft.setDesc(IpTool.getLocalIpList().toString());
			String grabId = repGrabXyftService.save(repGrabXyft);

			IbmRepDrawXyftT repDrawXyft = GameTool.draw(grabId, repGrabXyft);
			new IbmRepDrawXyftTService().save(repDrawXyft);

			CurrentTransaction.transactionCommit();
			//将开奖信息保存到内存中
			if (!XYFTTool.setLottery(period.toString(), drawNumber.toString())) {
				log.warn("保存 XYFT 开奖结果失败，找到【" + period + "】期的开奖信息为【" + XYFTTool.getLottery(period.toString())
						+ "】，想要保存的开奖信息为【" + drawNumber.toString() + "】");
			}
			//结算PK10信息
			new SettleXyftBetItemJob().executeJob(null);
			Thread.sleep(3*1000);

			new CodingXyftPlanJob().executeJob(null);
			Thread.sleep(3*1000);

			new TurnXyftCodeJob().executeJob(null);
			Thread.sleep(3*1000);

			new MergeXyftBetItemJob().executeJob(null);
			Thread.sleep(3*1000);

			new SendXyftBetItemJob().executeJob(null);
		} catch (Exception e) {
			log.warn("保存 XYFT 开奖结果失败");
			throw e;
		}
		return TypeEnum.TRUE.name();
	}
}
