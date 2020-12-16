package com.ibm.old.v1.client.core.job.bet;
import com.ibm.old.v1.client.ibm_client_bet.t.entity.IbmClientBetT;
import com.ibm.old.v1.client.ibm_client_bet.t.service.IbmClientBetTService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.common.doming.configs.SgWinConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.*;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.SgWinUtil;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: SgWin获取盈亏信息
 * @Author: zjj
 * @Date: 2019-08-09 14:18
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class BetProfitSgWinJob extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		String existHmId = context.getMergedJobDataMap().getString("existHmId");
		String period = context.getMergedJobDataMap().get("period").toString();
		String gameCode = context.getMergedJobDataMap().get("gameCode").toString();

		log.trace("投注结果SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】触发");
		try {
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.error("投注结果SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败，用户处于登出状态");
				return;
			}
			JsonResultBeanPlus bean = execute(existHmId, gameCode, period);
			if (bean.isSuccess()) {
				QuartzIbmUtil.removeBetProfitJob(existHmId, IbmHandicapEnum.SGWIN, gameCode, period);
				log.info("投注结果SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】成功:" + bean
						.toJsonString());
			} else {
				log.info("投注结果SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败:" + bean
						.toJsonString());
			}
		} catch (Exception e) {
			QuartzIbmUtil.removeBetProfitJob(existHmId, IbmHandicapEnum.SGWIN, gameCode, period);
			log.error("投注结果SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】异常:" + e.getMessage());
			throw e;
		}
	}
	/**
	 * 执行获取投注赔率的job
	 *
	 * @param existHmId 存在盘口会员id
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @return 处理结果
	 */
	private JsonResultBeanPlus execute(String existHmId, String gameCode, String period) throws Exception {
		IbmClientBetTService betTService = new IbmClientBetTService();
		JsonResultBeanPlus bean = null;
		CurrentTransaction.transactionEnd();
		try {
			bean = new JsonResultBeanPlus();

			SgWinUtil sgWinUtil = SgWinUtil.findInstance();
			//获取未结算的投注信息
			bean = sgWinUtil.getNoSettle(existHmId, SgWinConfig.GAME_CODE_MAP.get(gameCode));

			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setSuccess(false);
			//未结算信息不等于空时，根据注单号判断是否是当期的投注信息
			JSONArray noSettleArray = JSONArray.fromObject(bean.getData());
			//判断未结算信息是否为当期投注信息
			if (!isLottery(noSettleArray, PeriodTool.getDrawTime(gameCode, period))) {
				return bean;
			}

			//判断是否有投注成功项
			List<IbmClientBetT> clientBetList = betTService.findList(existHmId, period, gameCode);
			if (ContainerTool.isEmpty(clientBetList)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				bean.setSuccess(false);
				return bean;
			}
			IbmGameEnum game = IbmGameEnum.valueOf(gameCode);
			//当期开始投注时间
			long startTime = PeriodTool.getDrawTime(gameCode, PeriodTool.findLastPeriod(game, period));
			//页数
			int page = 0;

			boolean flag = true;
			while (flag) {
				//获取结算信息
				bean = sgWinUtil.profit(existHmId, ++page);
				if (!bean.isSuccess()) {
					return bean;
				}
				JSONArray array = (JSONArray) bean.getData();
				//循环匹对注单号
				flag=matchingSingle(clientBetList,array,betTService,startTime);
				if (array.size() < 15) {
					break;
				}
			}
			bean.setSuccess(false);

			//拼接盈利结果
			//获取投注结果
			Map<String, Map<String, Object>> mapBetProfit = betTService.findBetResult(existHmId, period, gameCode);
			if (ContainerTool.isEmpty(mapBetProfit)) {
				log.info("SgWin盘口用户【" + existHmId + "】未获取到已投注信息");
				bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			IbmClientExistBetTService existBetTService = new IbmClientExistBetTService();
			boolean status = existBetTService.updateSettlement(mapBetProfit);
			if (!status) {
				log.trace("SgWin盘口用户【" + existHmId + "】批量更新投注信息失败");
				bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			Map<String, List<Map<String, Object>>> finishBetItem = existBetTService
					.mapFinishBetItem(existHmId, gameCode, period);
			if (ContainerTool.isEmpty(finishBetItem)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_RESULT_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				bean.setSuccess(false);
				return bean;
			}
			//获取用户信息
			JSONObject userInfo=getUserInfo(existHmId);
			if (ContainerTool.isEmpty(userInfo)) {
				bean.setData(MessageReceipt
						.messageReceiptBetFinish(null, finishBetItem, gameCode, period, IbmHandicapEnum.SGWIN.name()));
			} else {
				bean.setData(MessageReceipt.messageReceiptBetFinish(userInfo, finishBetItem, gameCode, period,
						IbmHandicapEnum.SGWIN.name()));
			}
			//判断是否还有投注项未获取到盈利信息
			clientBetList = betTService.findList(existHmId, period, gameCode);
			if (!ContainerTool.isEmpty(clientBetList)) {
				log.info("SgWin盘口用户【" + existHmId + "】获取盈亏信息-定时任务未完成");
				bean.setSuccess(false);
				return bean;
			}
			bean.success();
		} catch (IOException e) {
			log.error("SgWin盘口用户【" + existHmId + "】获取盈亏信息-定时任务失败:", e);
		} finally {
			CurrentTransaction.transactionBegin();
		}
		return bean;
	}
	/**
	 * 匹配注单号
	 * @param clientBetList		投注信息
	 * @param array				结算信息
	 * @param betTService			service
	 * @param startTime			当期开始投注时间
	 * @return						是否继续匹配，true为继续，false为停止匹配
	 */
	private boolean matchingSingle(List<IbmClientBetT> clientBetList, JSONArray array, IbmClientBetTService betTService,long startTime)
			throws Exception {
		for (IbmClientBetT clientBet : clientBetList) {
			for (Object json : array) {
				JSONObject jsonObject = JSONObject.fromObject(json);
				//注单号匹配成功，保存盈亏信息
				if (jsonObject.getString("single").equals(clientBet.getBetNumber())) {
					if (Double.parseDouble(jsonObject.getString("result")) > 0) {
						clientBet.setBetResult(IbmTypeEnum.TRUE.name());
					} else {
						clientBet.setBetResult(IbmTypeEnum.FALSE.name());
					}
					clientBet.setProfitT((long) (Double.parseDouble(jsonObject.getString("result")) * 1000));
					clientBet.setUpdateTime(new Date());
					clientBet.setUpdateTimeLong(clientBet.getUpdateTime().getTime());
					clientBet.setState(IbmStateEnum.FINISH.name());
					betTService.update(clientBet);
					break;
				}
				//获取的投注时间小于开始投注时间，就不早继续获取结算信息
				if (jsonObject.getLong("betTime") < startTime) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	private JSONObject getUserInfo(String existHmId) {
		JSONObject info = new JSONObject();
		SgWinUtil sgWinUtil = SgWinUtil.findInstance();
		JsonResultBeanPlus bean=sgWinUtil.userInfo(existHmId);
		if (!bean.isSuccess()) {
			log.error("SgWin盘口用户【" + existHmId + "】获取用户信息失败,失败信息=", bean.toJsonString());
			return null;
		}
		Map<String, String> userMap = (Map<String, String>) bean.getData();
		if (ContainerTool.isEmpty(userMap)) {
			log.info("SgWin盘口会员【" + existHmId + "】获取用户信息失败");
			return null;
		}
		info.put("existHmId", existHmId);
		info.put("NICKNAME", userMap.get("memberAccount"));
		info.put("BALANCE", userMap.get("usedQuota"));
		info.put("PROFIT", userMap.get("profitAmount"));

		log.trace("SgWin盘口会员【" + existHmId + "】获取用户信息成功" + info);
		return info;
	}
	/**
	 * 判断是否结算
	 *
	 * @param noSettleArray 未结算信息
	 * @param drawTime      开奖时间
	 * @return
	 */
	private boolean isLottery(JSONArray noSettleArray, long drawTime) throws ParseException {
		//未结算信息为空时表示已结算
		if (ContainerTool.isEmpty(noSettleArray)) {
			return true;
		}
		//判断未结算信息是否是当期投注信息
		for (Object object : noSettleArray) {
			JSONObject betInfoObj = (JSONObject) object;
			//获取注单号
			String betSingle = betInfoObj.getString("id");
			StringBuilder builder = new StringBuilder(betSingle.substring(8, 12));
			builder.insert(2, ":");
			long betTime = DateTool
					.getStartTimeByTimeAndHm(DateTool.getDay(betSingle.substring(0, 8)), builder.toString()).getTime();
			//开奖时间大于投注时间,即还未结算
			if (drawTime > betTime) {
				return false;
			}
		}
		//所有未结算信息投注时间都不小于开奖时间，即已结算完成
		return true;
	}
}
