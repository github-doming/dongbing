package com.ibm.follow.servlet.server.core.mq;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.XYFTTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_xyft.entity.IbmRepDrawXyft;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_xyft.service.IbmRepDrawXyftService;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_xyft.entity.IbmRepGrabXyft;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_xyft.service.IbmRepGrabXyftService;
import com.ibm.follow.servlet.server.core.job.game.SettleXyftBetItemJob;
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
		GameUtil.Code game = GameUtil.Code.XYFT;
		log.info("接收到，游戏【" + game.getName() + "】，的开奖结果【" + message + "】");
		JSONObject jObj = JSONObject.parseObject(message);
		Object gameCode = jObj.get("code");
		Object period = jObj.get("period");
        HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(jObj.getString("handicapCode"));
		if (!game.name().equals(gameCode)) {
			log.warn("非法的游戏code传输，应有的游戏code为【" + game.getName() + "】，传输的code为【" + gameCode + "】");
			return null;
		}
        String type=HandicapGameUtil.lotteryType(game,handicapCode);

		//基准期数
        String basePeriod=PeriodTool.findLotteryPeriod(game,handicapCode);
		if (StringTool.isEmpty(period) || !basePeriod.equals(period)) {
		    //判断是否存在该期开奖数据,不为空就直接返回，为空就存起来暂不处理
		    if(new IbmRepDrawXyftService().isExist(period.toString(),type)){
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
		if (StringTool.isEmpty(drawNumber) || drawNumber.toString().split(",").length != 10) {
			log.warn("获取开奖号码失败，传输的开奖号码为【" + drawNumber + "】");
			return null;
		}

		try {

			IbmRepGrabXyftService repGrabXyftService = new IbmRepGrabXyftService();
			CurrentTransaction.transactionCommit();
			if (repGrabXyftService.isExist(period.toString(),type)) {
				log.info("该期数已存在数据【" + period + "】");
				return null;
			}
			String gameId = GameUtil.id(game);

			String[] number = drawNumber.toString().split(",");

			//报表保存结果数据
			IbmRepGrabXyft repGrabXyft = new IbmRepGrabXyft();
            repGrabXyft.setHandicapCode(handicapCode);
			repGrabXyft.setGameId(gameId);
			repGrabXyft.setType(type);
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

			IbmRepDrawXyft repDrawXyft = XYFTTool.draw(grabId, repGrabXyft);
			new IbmRepDrawXyftService().save(repDrawXyft);

			//期数和基准期数不一样时就不进行结算处理
			if(!basePeriod.equals(period)){
                return TypeEnum.TRUE.name();
            }
			CurrentTransaction.transactionCommit();
			//将开奖信息保存到内存中
			if (!XYFTTool.setLottery(period.toString(), drawNumber.toString(),type)) {
				log.warn("保存 XYFT 开奖结果失败，找到【" + period + "】期的开奖信息为【" + XYFTTool.getLottery(period.toString(),type)
						+ "】，想要保存的开奖信息为【" + drawNumber.toString() + "】");
			}
			//结算XYFT信息
			new SettleXyftBetItemJob(handicapCode,type).executeJob(null);
		} catch (Exception e) {
			log.warn("保存 XYFT 开奖结果失败");
			throw e;
		}
		return TypeEnum.TRUE.name();
	}
}
