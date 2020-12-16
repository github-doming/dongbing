package com.ibm.old.v1.client.core.job.bet;
import com.ibm.old.v1.client.ibm_client_bet.t.service.IbmClientBetTService;
import com.ibm.old.v1.client.ibm_client_bet_error.t.service.IbmClientBetErrorTService;
import com.ibm.old.v1.client.ibm_client_bet_fail.t.service.IbmClientBetFailTService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.common.doming.configs.SgWinConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.SgWinUtil;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.*;
/**
 * @Description: SgWin定时投注任务
 * @Author: zjj
 * @Date: 2019-08-10 10:37
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class BetSgWinJob extends BaseCommJob {
	private String existHmId;
	private String game;
	private String period;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		String existHmId = context.getMergedJobDataMap().getString("existHmId");
		String period = context.getMergedJobDataMap().get("period").toString();
		String gameCode = context.getMergedJobDataMap().get("gameCode").toString();
		log.trace("定时投注SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】触发");
		try {
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.error("定时投注SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败，用户处于登出状态");
				return;
			}
			JsonResultBeanPlus bean = execute(existHmId, gameCode, period);
			if (bean.isSuccess()) {
				log.info("定时投注SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】成功:" + bean
						.toJsonString());
			} else {
				log.error("定时投注SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败:" + bean
						.toJsonString());
			}
		} catch (Exception e) {
			log.error("定时投注SgWin盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】异常:" + e.getMessage());
			throw e;
		}

	}
	/**
	 * 执行投注
	 *
	 * @param existHmId 存在盘口会员id
	 * @param game      游戏code
	 * @param period    期数
	 * @return 投注结果
	 */
	public JsonResultBeanPlus execute(String existHmId, String game, String period) throws Exception {
		CurrentTransaction.transactionEnd();
		this.existHmId = existHmId;
		this.game = game;
		this.period = period;

		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		IbmClientExistBetTService existBetTService = new IbmClientExistBetTService();
		List<Map<String, Object>> existBetTList = existBetTService.findBetInfo(existHmId, game, period);
		if (ContainerTool.isEmpty(existBetTList)) {
			bean.putEnum(IbmCodeEnum.IBM_404_BET_INFO);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}

		List<String> existBetIds = new ArrayList<>(existBetTList.size());
		JSONArray betItems;
		//第一次投注
		for (Map<String, Object> existBetInfo : existBetTList) {
			existBetIds.add(existBetInfo.get("CLIENT_EXIST_BET_ID_").toString());
			//等待投注信息
			if (ContainerTool.isEmpty(existBetInfo.get("BET_INFO_"))) {
				continue;
			}
			JSONObject betInfo = JSONObject.fromObject(existBetInfo.get("BET_INFO_"));
			//每一个客户端投注项id，的待投项
			for (Object betHandicap : betInfo.keySet()) {
				betItems = betInfo.getJSONArray(betHandicap.toString());
				bean = betProcess(betItems, existBetInfo, betHandicap, true);
				if (!bean.isSuccess()) {
					log.error("SgWin盘口会员【" + existHmId + "】投注进SgWin中失败,原因:" + bean.toJsonString());
				}
			}
		}

		IbmClientBetFailTService betFailTService = new IbmClientBetFailTService();
		List<Map<String, Object>> againBetItem = betFailTService.findAgainBetItem(existHmId, period, game);
		List<String> betFailIds = new ArrayList<>(againBetItem.size());

		//复投，投注失败项重新投注
		for (Map<String, Object> existBetInfo : againBetItem) {
			log.trace("SgWin盘口会员【" + existHmId + "】期数【" + period + "】补投项:" + existBetInfo);
			betItems = JSONArray.fromObject(existBetInfo.get("FAIL_BET_INFO_").toString().split(","));
			String betHandicap = existBetInfo.get("BET_TYPE_").toString();
			betProcess(betItems, existBetInfo, betHandicap, false);
			betFailIds.add(existBetInfo.get("IBM_CLIENT_BET_FAIL_ID_").toString());
		}

		// 更新投注失败信息
		if (ContainerTool.notEmpty(betFailIds)) {
			betFailTService.updateBet(betFailIds);
		}

		// 更新正常投注信息，返回到主服务器
		List<String> existBetItemIds = existBetTService.updateBet(existBetIds);

		//获取更新异常的投注信息，返回到主服务器
		IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
		Map<String, List<String>> errorBetInfo = betErrorTService.listErrorBetInfo(existHmId, period, game);
		if (ContainerTool.notEmpty(errorBetInfo)) {
			MessageReceipt.messageReceiptBetError(errorBetInfo, game, period, IbmHandicapEnum.SGWIN.name());
		}
		//启动定时器校验投注结果,查询到下一期开奖结果
		JsonResultBeanPlus profitBean = QuartzIbmUtil.betProfitJob(existHmId, IbmHandicapEnum.SGWIN, game, period);
		log.trace("SgWin盘口会员【" + existHmId + "】获取开奖结果定时器启动结果:" + profitBean.toJsonString());

		//获取用户信息
		JSONObject userInfo = getUserInfo();
		bean.setData(MessageReceipt
				.messageReceiptBetSuccess(userInfo, existBetItemIds, game, period, IbmHandicapEnum.SGWIN.name()));
		bean.success();
		return bean;
	}
	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	private JSONObject getUserInfo() {
		JSONObject info = new JSONObject();
		SgWinUtil sgWinUtil = SgWinUtil.findInstance();
		Map<String, String> userMap = sgWinUtil.getUserInfo(existHmId);
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
	 * 投注处理
	 *
	 * @param betItems     投注项
	 * @param existBetInfo 投注基本信息
	 * @param betHandicap  投注盘口
	 * @param flag         投注状态
	 * @return 投注结果
	 */
	private JsonResultBeanPlus betProcess(List<String> betItems, Map<String, Object> existBetInfo, Object betHandicap,
			boolean flag) throws Exception {
		String gameCode = SgWinConfig.GAME_CODE_MAP.get(game);

		Date nowTime = new Date();
		String existBetId = existBetInfo.get("CLIENT_EXIST_BET_ID_").toString();
		String execBetItemId = existBetInfo.get("EXEC_BET_ITEM_ID_").toString();
		String cloudReceiptBetId = existBetInfo.get("CLOUD_RECEIPT_BET_ID_").toString();
		String handicapMemberId = existBetInfo.get("HANDICAP_MEMBER_ID_").toString();

		SgWinUtil sgWinUtil = SgWinUtil.findInstance();
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//信用额度
		Map<String, String> userInfo = sgWinUtil.getUserInfo(existHmId);
		if (ContainerTool.isEmpty(userInfo) || StringTool.isEmpty(userInfo.get("usedQuota"))) {
			log.error("SgWin盘口会员【" + existHmId + "】用户信息 = " + userInfo);
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			betErrorTService
					.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
							String.join(",", betItems).concat("-投注失败"), nowTime);
			bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}

		//判断余额是否充足
		if (lessCredit(Double.parseDouble(userInfo.get("usedQuota")), betItems) < 0) {
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			betErrorTService
					.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
							String.join(",", betItems).concat("-余额不足"), nowTime);
			bean.putEnum(IbmHcCodeEnum.IBM_404_USER_CREDIT);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}
		//投注信息限制
		bean = limitBetInfo(sgWinUtil, gameCode);
		if (!bean.isSuccess()) {
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			if (IbmHcCodeEnum.IBM_403_SEAL_HANDICAP.getCode().equals(bean.getCode()) || IbmHcCodeEnum.IBM_403_PERIOD
					.getCode().equals(bean.getCode())) {
				betErrorTService
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								String.join(",", betItems).concat("-已封盘"), nowTime);
			} else if (IbmHcCodeEnum.IBM_403_USER_BAN_BET.getCode().equals(bean.getCode())) {
				betErrorTService
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								String.join(",", betItems).concat("-已停押"), nowTime);
			}
			return bean;
		}
		bean.setSuccess(false);
		//开奖期数
		String drawNumber = period.replace("-", "");

		//获取赔率信息
		bean = sgWinUtil.oddsInfo(existHmId, gameCode, SgWinConfig.GAME_ODDS_CODE.get(betHandicap.toString()));

		if (!bean.isSuccess()) {
			log.error("SgWin盘口会员【" + existHmId + "】获取赔率信息 = " + bean.toJsonString());
			return bean;
		}
		bean.setSuccess(false);
		JSONObject odds = (JSONObject) bean.getData();
		//投注
		bean = sgWinUtil.betting(existHmId, gameCode, drawNumber, betItems, odds);

		if (!bean.isSuccess()) {
			return bean;
		}
		bean.setSuccess(false);

		//获取正在投注信息
		bean = sgWinUtil.getNoSettle(existHmId, gameCode);
		if (!bean.isSuccess()) {
			return bean;
		}
		IbmClientBetTService betTService = new IbmClientBetTService();

		//投注失败项信息
		List<String> failInfo = new ArrayList<>();
		//通过匹配投注项，找到对应的注单号(相同投注位置和投注金额获取到一样的注单号也没关系)
		JSONArray noSettleArray = JSONArray.fromObject(bean.getData());
		for (String item : betItems) {
			for (Object object : noSettleArray) {
				//投注成功项
				Map<String, String> successInfo = new HashMap<>(4);
				//object结果信息={"id":"2019081013415224156235020001","t":"冠军 大","o":"1.9826","a":12}
				JSONObject betResultObj = (JSONObject) object;
				String[] betItem = item.split("#");
				//投注成功
				if (SgWinConfig.BET_RESULT_CODE.get(betResultObj.getString("t")).equals(betItem[0]) && betItem[1]
						.equals(String.valueOf(betResultObj.getInt("a")))) {
					successInfo.put("betInfo", betItem[0]);
					successInfo.put("betNumber", betResultObj.getString("id"));
					//可盈利信息=投注金额*（赔率-1）
					successInfo.put("profit", String.valueOf(
							betResultObj.getInt("a") * (Double.parseDouble(betResultObj.getString("o")) - 1)));
					successInfo.put("odds", betResultObj.getString("o"));
					betTService.saveBetSuccess(existHmId, game, period, betHandicap, existBetId, successInfo, nowTime);
					break;
				}
			}
			//保存投注失败项
			failInfo.add(item);
		}
		if (ContainerTool.notEmpty(failInfo)) {
			if (flag) {
				//第一次投注，投注失败，重新投注
				new IbmClientBetFailTService()
						.save(existHmId, game, period, betHandicap, existBetId, execBetItemId, cloudReceiptBetId,
								handicapMemberId, failInfo.toString(), nowTime);
			} else {
				//复投，投注失败，发送错误信息
				new IbmClientBetErrorTService()
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								failInfo.toString().concat("投注失败"), nowTime);
			}
		}
		return bean;
	}
	/**
	 * 投注信息限制
	 *
	 * @param sgWinUtil sgWin工具类
	 * @param gameCode  游戏code
	 * @return 投注信息限制结果
	 */
	private JsonResultBeanPlus limitBetInfo(SgWinUtil sgWinUtil, String gameCode) {

		JsonResultBeanPlus bean = sgWinUtil.gameInfo(existHmId, gameCode);
		if (!bean.isSuccess()) {
			log.error("SgWin盘口会员【" + existHmId + "】游戏投注信息 = " + bean.toJsonString());
			return bean;
		}
		JSONObject betInfo = (JSONObject) bean.getData();
		//状态等于2时处于封盘时间
		if (!betInfo.containsKey("status") || betInfo.getInt("status") == 2) {
			bean.putEnum(IbmHcCodeEnum.IBM_403_SEAL_HANDICAP);
			bean.putSysEnum(IbmHcCodeEnum.CODE_403);
			bean.setSuccess(false);
			return bean;
		}

		//期数不匹配
		if (!period.replace("-", "").equals(betInfo.getString("drawNumber"))) {
			log.error(
					"SgWin盘口会员【" + existHmId + "】期数不匹配，投注期数为：【" + period + "】，实际期数为：【" + betInfo.getString("drawNumber")
							+ "】");
			bean.putEnum(IbmHcCodeEnum.IBM_403_PERIOD);
			bean.putSysEnum(IbmHcCodeEnum.CODE_403);
			bean.setSuccess(false);
			return bean;
		}
		//封盘时间
		long timeClose = betInfo.getLong("closeTime") - System.currentTimeMillis();
		log.debug("根据盘口封盘时间进入睡眠");
		try {
			if (timeClose > 100 * 1000) {
				Thread.sleep(RandomTool.getInt(2000));
			} else if (timeClose > 80 * 1000) {
				Thread.sleep(RandomTool.getInt(1500));
			} else if (timeClose > 60 * 1000) {
				Thread.sleep(RandomTool.getInt(1000));
			} else if (timeClose > 40 * 1000) {
				Thread.sleep(RandomTool.getInt(500));
			} else if (timeClose > 20 * 1000) {
				Thread.sleep(RandomTool.getInt(200));
			} else if (timeClose > SgWinConfig.TIME_CLOSE) {
				Thread.sleep(RandomTool.getInt(50));
			} else if (timeClose < SgWinConfig.TIME_CLOSE - 2) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_SEAL_HANDICAP);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				bean.setSuccess(false);
				return bean;
			}

		} catch (InterruptedException e) {
			log.error("投注等待失败，继续投注", e);
		}
		return bean;
	}

	/**
	 * 减小可用余额
	 *
	 * @param credit   可用余额
	 * @param betItems 投注项
	 * @return 可用余额
	 */
	private static double lessCredit(double credit, List<String> betItems) {
		for (String betItem : betItems) {
			credit -= NumberTool.getInteger(betItem.split("#")[1]);
		}
		return credit;
	}
}
