package com.ibm.old.v1.cloud.core.listener.mq;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.tool.CQSSCTool;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_rep_draw_cqssc.t.entity.IbmRepDrawCqsscT;
import com.ibm.old.v1.cloud.ibm_rep_draw_cqssc.t.service.IbmRepDrawCqsscTService;
import com.ibm.old.v1.cloud.ibm_rep_grab_cqssc.t.entity.IbmRepGrabCqsscT;
import com.ibm.old.v1.cloud.ibm_rep_grab_cqssc.t.service.IbmRepGrabCqsscTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: CQSSC 开奖结果
 * @Author: Dongming
 * @Date: 2019-02-13 17:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryCqsscListener extends BaseCommMq {

	@Override public String execute(String message) throws Exception {
		IbmGameEnum game = IbmGameEnum.CQSSC;
		JSONObject jObj = JSONObject.parseObject(message);
		Object gameCode = jObj.get("code");
		Object period = jObj.get("period");

		if (!game.name().equals(gameCode)) {
			log.warn("非法的游戏code传输，应有的游戏code为【" + game.getName()+ "】，传输的code为【" + gameCode + "】");
			return null;
		}
		if (StringTool.isEmpty(period) || !PeriodTool.findLotteryCQSSCPeriod().equals(period)) {
			log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + PeriodTool.findLotteryCQSSCPeriod() + "】，传输的游戏游戏期数为【" + period + "】");
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
		if (StringTool.isEmpty(drawNumber) || drawNumber.toString().split(",").length != 5) {
			log.warn("获取开奖号码失败，传输的开奖号码为【" + drawNumber + "】");
			return null;
		}
		try {

			IbmRepGrabCqsscTService repGrabCqsscService = new IbmRepGrabCqsscTService();
			CurrentTransaction.transactionCommit();
			if (repGrabCqsscService.isExist(period.toString())) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = GameTool.findId(game.name());

			String[] number = drawNumber.toString().split(",");

			//报表保存结果数据
			IbmRepGrabCqsscT repGrabCqssc = new IbmRepGrabCqsscT();
			repGrabCqssc.setGameId(gameId);
			repGrabCqssc.setPeriod(period.toString());
			repGrabCqssc.setDrawTime(new Date(drawTimeLong));
			repGrabCqssc.setDrawTimeLong(drawTimeLong);
			repGrabCqssc.setDrawNumber(drawNumber.toString());
			repGrabCqssc.setP1Number(number[0]);
			repGrabCqssc.setP2Number(number[1]);
			repGrabCqssc.setP3Number(number[2]);
			repGrabCqssc.setP4Number(number[3]);
			repGrabCqssc.setP5Number(number[4]);
			repGrabCqssc.setCreateTime(new Date());
			repGrabCqssc.setCreateTimeLong(repGrabCqssc.getCreateTime().getTime());
			repGrabCqssc.setUpdateTime(new Date());
			repGrabCqssc.setUpdateTimeLong(repGrabCqssc.getUpdateTime().getTime());
			repGrabCqssc.setState(IbmStateEnum.OPEN.name());
			repGrabCqssc.setDesc(IpTool.getLocalIpList().toString());
			String grabId = repGrabCqsscService.save(repGrabCqssc);

			IbmRepDrawCqsscT repDrawCqssc = GameTool.draw(grabId, repGrabCqssc);
			new IbmRepDrawCqsscTService().save(repDrawCqssc);
			CurrentTransaction.transactionCommit();
			//将开奖信息保存到内存中
			if (!CQSSCTool.setLottery(period.toString(), drawNumber.toString())) {
				log.warn("保存CQSSC开奖结果失败，找到【" + period + "】期的开奖信息为【" + CQSSCTool.getLottery(period.toString())
						+ "】，想要保存的开奖信息为【" + drawNumber.toString() + "】");
			}
		} catch (Exception e) {
			log.warn("保存CQSSC开奖结果失败");
			throw e;
		}

		return TypeEnum.TRUE.name();
	}
}
