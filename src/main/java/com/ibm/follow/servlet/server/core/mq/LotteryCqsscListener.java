package com.ibm.follow.servlet.server.core.mq;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.CQSSCTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_cqssc.entity.IbmRepDrawCqssc;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_cqssc.service.IbmRepDrawCqsscService;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_cqssc.entity.IbmRepGrabCqssc;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_cqssc.service.IbmRepGrabCqsscService;
import com.ibm.follow.servlet.server.core.job.game.SettleCqsscBetItemJob;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.ReflectTool;
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
		GameUtil.Code game = GameUtil.Code.CQSSC;
		log.info("接收到，游戏【" + game.getName() + "】，的开奖结果【" + message + "】");
		JSONObject jObj = JSONObject.parseObject(message);
		Object gameCode = jObj.get("code");
		Object period = jObj.get("period");
        HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(jObj.getString("handicapCode"));
		if (!game.name().equals(gameCode)) {
			log.warn("非法的游戏code传输，应有的游戏code为【" + game.getName()+ "】，传输的code为【" + gameCode + "】");
			return null;
		}
        String type= HandicapGameUtil.lotteryType(game,handicapCode);
        String basePeriod=PeriodTool.findLotteryPeriod(game,handicapCode);
		if (StringTool.isEmpty(period) || !basePeriod.equals(period)) {
		    if(new IbmRepDrawCqsscService().isExist(period.toString(),type)){
                log.warn("开奖期数匹配错误，应有的游戏游戏期数为【" + basePeriod + "】，传输的游戏游戏期数为【" + period + "】");
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

			IbmRepGrabCqsscService repGrabCqsscService = new IbmRepGrabCqsscService();
			CurrentTransaction.transactionCommit();
			if (repGrabCqsscService.isExist(period.toString(),type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}

			String gameId = GameUtil.id(game);

			String[] number = drawNumber.toString().split(",");

			//报表保存结果数据
			IbmRepGrabCqssc repGrabCqssc = new IbmRepGrabCqssc();
            repGrabCqssc.setHandicapCode(handicapCode);
			repGrabCqssc.setGameId(gameId);
            repGrabCqssc.setType(type);
			repGrabCqssc.setPeriod(period.toString());
			repGrabCqssc.setDrawTime(new Date(drawTimeLong));
			repGrabCqssc.setDrawTimeLong(drawTimeLong);
			repGrabCqssc.setDrawNumber(drawNumber.toString());
			for (int i = 0; i < 5; i++) {
				ReflectTool.set(repGrabCqssc, "setP" + (i + 1) + "Number", number[i]);
			}
			repGrabCqssc.setCreateTime(new Date());
			repGrabCqssc.setCreateTimeLong(repGrabCqssc.getCreateTime().getTime());
			repGrabCqssc.setUpdateTime(new Date());
			repGrabCqssc.setUpdateTimeLong(repGrabCqssc.getUpdateTime().getTime());
			repGrabCqssc.setState(IbmStateEnum.OPEN.name());
			repGrabCqssc.setDesc(IpTool.getLocalIpList().toString());
			String grabId = repGrabCqsscService.save(repGrabCqssc);

			IbmRepDrawCqssc repDrawCqssc = CQSSCTool.draw(grabId, repGrabCqssc);
			new IbmRepDrawCqsscService().save(repDrawCqssc);

            if (!basePeriod.equals(period)) {
                return TypeEnum.TRUE.name();
            }
			CurrentTransaction.transactionCommit();
			//将开奖信息保存到内存中
			if (!CQSSCTool.setLottery(period.toString(), drawNumber.toString())) {
				log.warn("保存CQSSC开奖结果失败，找到【" + period + "】期的开奖信息为【" + CQSSCTool.getLottery(period.toString())
						+ "】，想要保存的开奖信息为【" + drawNumber.toString() + "】");
			}

            //结算CQSSC信息
            new SettleCqsscBetItemJob(handicapCode,type).executeJob(null);
		} catch (Exception e) {
			log.warn("保存CQSSC开奖结果失败");
			throw e;
		}

		return TypeEnum.TRUE.name();
	}
}
