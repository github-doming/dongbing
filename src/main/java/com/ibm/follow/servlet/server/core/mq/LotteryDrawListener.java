package com.ibm.follow.servlet.server.core.mq;


import com.alibaba.fastjson.JSONObject;
import com.common.tools.CacheTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.server.core.controller.ClientDrawController;
import com.ibm.follow.servlet.server.core.controller.CloudDrawController;
import com.ibm.follow.servlet.server.core.thread.SettleVrBetItemThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.StringTool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 开奖信息接收监听
 * @Author: null
 * @Date: 2020-07-10 09:43
 * @Version: v1.0
 */
public class LotteryDrawListener extends BaseCommMq {
	private static final Logger log = LogManager.getLogger(LotteryDrawListener.class);

	@Override
	public String execute(String message) throws Exception {
		log.info("开奖信息接收监听，接收到的开奖结果【{}】", message);
		JSONObject drawInfo = JSONObject.parseObject(message);

		GameUtil.Code gameCode = GameUtil.value(drawInfo.getString("code"));
		if (gameCode == null) {
			log.warn("开奖游戏匹配错误，接收的信息为【{}】",message);
			return null;
		}
		Object period = drawInfo.get("period");
		String drawType = drawInfo.getString("drawType");
		HandicapUtil.Code handicapCode = HandicapUtil.Code.value(drawInfo.getString("handicapCode"));

		//基准期数
		Object basePeriod =gameCode.getGameFactory().period(handicapCode).findLotteryPeriod();
		if (StringTool.isEmpty(period) || !basePeriod.equals(period)) {
			if (CacheTool.isExist(gameCode, handicapCode,drawType, period)) {
				log.warn("开奖期数匹配错误，应有的游戏游戏期数为【{}】，传输的游戏游戏期数为【{}】", basePeriod, period);
				return null;
			}
		}
		String drawNumber = drawInfo.getString("drawNumber");

		//数据存在了，并且已经处理了
		if (CacheTool.isExist(gameCode,handicapCode, drawType, period)) {
			log.info("保存游戏【{}】开奖结果失败，找到【{}】期的开奖信息为【{}】，想要保存的开奖信息为【{}】", gameCode.name(), period,
					CacheTool.getDraw(gameCode,handicapCode, drawType, period), drawNumber);
			return null;
		}
		//拒绝该游戏相同类型的开奖数据,并将数据保存到内存里
		CacheTool.saveDraw(gameCode, drawType, drawNumber, period);
		//开启一个线程，进行计算。
		ThreadPoolExecutor executorService = ThreadExecuteUtil.findInstance().getJobExecutor();

		if (CacheTool.isClient()) {
			if (basePeriod.equals(period)) {
				executorService.execute(new ClientDrawController(gameCode, period, drawType, handicapCode, drawNumber,drawInfo));
			}
		} else {
			//开启一个线程，进行计算虚拟盈亏信息
			executorService.execute(new SettleVrBetItemThread(gameCode, period,handicapCode,drawInfo));
			executorService.execute(new CloudDrawController(gameCode, period, drawType,handicapCode, drawNumber,drawInfo));
		}
		return null;
	}
}
