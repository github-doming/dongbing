package com.ibm.old.v1.client.core.job.bet;
import com.ibm.old.v1.client.ibm_client_bet.t.service.IbmClientBetTService;
import com.ibm.old.v1.client.ibm_client_bet_error.t.service.IbmClientBetErrorTService;
import com.ibm.old.v1.client.ibm_client_bet_fail.t.service.IbmClientBetFailTService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.common.doming.configs.Ws2Config;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.*;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.Ws2Util;
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
 * @Description: WS2定时投注任务
 * @Author: zjj
 * @Date: 2019-04-02 17:50
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class BetWs2Job extends BaseCommJob {
	private String existHmId;
	private String game;
	private String period;
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		String existHmId = context.getMergedJobDataMap().getString("existHmId");
		String period = context.getMergedJobDataMap().get("period").toString();
		String gameCode = context.getMergedJobDataMap().get("gameCode").toString();
		log.trace("定时投注WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】触发");
		try {
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.error("定时投注WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败，用户处于登出状态");
				return;
			}
			JsonResultBeanPlus bean = execute(existHmId, gameCode, period);
			if (bean.isSuccess()) {
				log.info("定时投注WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】成功:" + bean
						.toJsonString());
			} else {
				log.error("定时投注WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败:" + bean
						.toJsonString());
			}
		} catch (Exception e) {
			log.error("定时投注WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】异常:" + e.getMessage());
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
		try {
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
						log.error("WS2盘口会员【" + existHmId + "】投注进WS2中失败,原因:" + bean.toJsonString());
					}
				}
			}

			IbmClientBetFailTService betFailTService = new IbmClientBetFailTService();
			List<Map<String, Object>> againBetItem = betFailTService.findAgainBetItem(existHmId, period, game);
			List<String> betFailIds = new ArrayList<>(againBetItem.size());
			//复投，投注失败项重新投注
			for (Map<String, Object> existBetInfo : againBetItem) {
				log.trace("WS2盘口会员【" + existHmId + "】期数【" + period + "】补投项:" + existBetInfo);
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
			Map<String, List<String>> errorBetInfos = betErrorTService.listErrorBetInfo(existHmId, period, game);
			if (ContainerTool.notEmpty(errorBetInfos)) {
				MessageReceipt.messageReceiptBetError(errorBetInfos, game, period, IbmHandicapEnum.WS2.name());
			}
			//获取用户信息
			JSONObject userInfo = getUserInfo();
			if (IbmHcCodeEnum.IBM_403_USER_BAN_BET.getCode().equals(bean.getCode())) {
				userInfo.put("USER_STATE", IbmHcCodeEnum.IBM_403_USER_BAN_BET.getCode());
			} else {
				//启动定时器校验投注结果,查询到下一期开奖结果
				JsonResultBeanPlus profitBean=QuartzIbmUtil.betProfitJob(existHmId, IbmHandicapEnum.WS2, game, period);
				log.trace("WS2盘口会员【" + existHmId + "】获取开奖结果定时器启动结果:" + profitBean.toJsonString());
			}

			bean.setData(MessageReceipt
					.messageReceiptBetSuccess(userInfo, existBetItemIds, game, period, IbmHandicapEnum.WS2.name()));
			bean.success();
			return bean;
		} finally {
			CurrentTransaction.transactionBegin();
		}
	}

	/**
	 * 投注处理
	 *
	 * @param betItems     投注项
	 * @param existBetInfo 投注基本信息
	 * @param betHandicap  投注盘口
	 * @return 投注结果
	 */
	private JsonResultBeanPlus betProcess(List<String> betItems, Map<String, Object> existBetInfo, Object betHandicap,
			boolean flag) throws Exception {
		String gameCode = Ws2Config.GAME_CODE_MAP.get(game);
		Date nowTime = new Date();
		String existBetId = existBetInfo.get("CLIENT_EXIST_BET_ID_").toString();
		String execBetItemId = existBetInfo.get("EXEC_BET_ITEM_ID_").toString();
		String cloudReceiptBetId = existBetInfo.get("CLOUD_RECEIPT_BET_ID_").toString();
		String handicapMemberId = existBetInfo.get("HANDICAP_MEMBER_ID_").toString();

		Ws2Util ws2Util = Ws2Util.findInstance();
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//信用额度
		Map<String, String> userInfo = ws2Util.getUserInfo(existHmId, Ws2Config.GAME_CODE_MAP.get(game));
		if (ContainerTool.isEmpty(userInfo) || StringTool.isEmpty(userInfo.get("usedQuota"))) {
			log.error("WS2盘口会员【" + existHmId + "】用户信息 = " + userInfo);
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			betErrorTService
					.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
							String.join(",", betItems).concat("投注失败"), nowTime);
			bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}

		//判断余额是否充足
		if (lessCredit(Integer.parseInt(userInfo.get("usedQuota")), betItems) < 0) {
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			betErrorTService
					.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
							String.join(",", betItems).concat("余额不足"), nowTime);
			bean.putEnum(IbmHcCodeEnum.IBM_404_USER_CREDIT);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}
		//投注信息限制
		bean = limitBetInfo(ws2Util, existHmId, gameCode, period, betHandicap);
		if (!bean.isSuccess()) {
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			if (IbmHcCodeEnum.IBM_403_SEAL_HANDICAP.getCode().equals(bean.getCode()) || IbmHcCodeEnum.IBM_403_PERIOD
					.getCode().equals(bean.getCode())) {
				betErrorTService
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								String.join(",", betItems).concat("已封盘"), nowTime);
			} else if (IbmHcCodeEnum.IBM_403_USER_BAN_BET.getCode().equals(bean.getCode())) {
				betErrorTService
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								String.join(",", betItems).concat("已停押"), nowTime);
			}
			return bean;
		}
		JSONObject betInfo = (JSONObject) bean.getData();
		JSONObject odds = null;
		String version = null;
		if (ContainerTool.notEmpty(betInfo.getJSONObject("data").get("integrate")) && ContainerTool
				.notEmpty(betInfo.getJSONObject("data").get("version_number"))) {
			odds = betInfo.getJSONObject("data").getJSONObject("integrate");
			version = betInfo.getJSONObject("data").getString("version_number");
		}

		//开始投注
		bean = ws2Util.betting(existHmId, gameCode, Ws2Config.getBetCode(game, betHandicap.toString()), betItems, odds,
				version);
		//用户已禁投,或用户数据出错
		if (IbmHcCodeEnum.IBM_403_BET_FAIL.getCode().equals(bean.getCode())) {
			bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
			bean.putSysEnum(IbmHcCodeEnum.CODE_403);
			return bean;
		} else if (IbmCodeEnum.IBM_404_DATA_NOT_FIND.getCode().equals(bean.getCode())) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}

		//投注成功
		if (bean.isSuccess()) {
			Map<String, List<Map<String, String>>> resultData = (Map<String, List<Map<String, String>>>) bean.getData();
			//投注成功
			if (ContainerTool.isContain(resultData, "successData")) {
				IbmClientBetTService betTService = new IbmClientBetTService();
				List<Map<String, String>> successData = resultData.get("successData");
				for (Map<String, String> successInfo : successData) {
					betItems.remove(successInfo.get("betInfo"));
					betTService.saveBetSuccess(existHmId, game, period, betHandicap, existBetId, successInfo, nowTime);
				}
			}
			//投注失败，补投
			if (flag && ContainerTool.isContain(resultData, "failData")) {
				List<Map<String, String>> failData = resultData.get("failData");
				StringBuilder failInfo = new StringBuilder();
				for (Map<String, String> result : failData) {
					betItems.remove(result.get("betInfo"));
					failInfo.append(result.get("betInfo")).append(",");
				}
				new IbmClientBetFailTService()
						.save(existHmId, game, period, betHandicap, existBetId, execBetItemId, cloudReceiptBetId,
								handicapMemberId, failInfo.toString(), nowTime);
			}
			//投注异常，返回信息
			if (ContainerTool.isContain(resultData, "errorData")) {
				List<Map<String, String>> errorData = resultData.get("errorData");
				StringBuilder errorInfo = getErrorInfo(betItems, errorData);
				IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
				betErrorTService
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								errorInfo.toString(), nowTime);
			}
		}
		if (ContainerTool.notEmpty(betItems)) {
			if (flag) {
				//第一次投注，投注失败，重新投注
				new IbmClientBetFailTService()
						.save(existHmId, game, period, betHandicap, existBetId, execBetItemId, cloudReceiptBetId,
								handicapMemberId, String.join(",", betItems), nowTime);
			} else {
				//复投，投注失败，发送错误信息
				StringBuilder errorInfo = new StringBuilder();
				for (String item : betItems) {
					errorInfo.append(item.split("#")[0]).append("#").append("投注失败").append(",");
				}
				errorInfo.deleteCharAt(errorInfo.length() - 1);
				new IbmClientBetErrorTService()
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								errorInfo.toString(), nowTime);
			}
		}
		return bean;
	}

	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	private JSONObject getUserInfo() {
		JSONObject info = new JSONObject();
		Ws2Util ws2Util = Ws2Util.findInstance();
		JsonResultBeanPlus userBean = ws2Util.userInfo(existHmId, Ws2Config.GAME_CODE_MAP.get(game));
		//获取信息失败
		if (!userBean.isSuccess()) {
			log.info("WS2盘口会员【" + existHmId + "】获取用户信息失败" + userBean.toJsonString());
		} else {
			log.trace("WS2盘口会员【" + existHmId + "】获取用户信息成功" + userBean.toJsonString());
			Map<String,Object>  userInfo = (Map<String, Object>) userBean.getData();
			info.put("existHmId", existHmId);
			info.put("NICKNAME", userInfo.get("memberAccount"));
			info.put("BALANCE", userInfo.get("usedQuota"));
			info.put("PROFIT",  userInfo.get("profitAmount") );
		}
		return info;
	}

	/**
	 * 获取异常信息
	 *
	 * @param betItems  投注项列表
	 * @param errorData 异常数据
	 * @return 错误信息
	 */
	private StringBuilder getErrorInfo(List<String> betItems, List<Map<String, String>> errorData) {
		StringBuilder errorInfo = new StringBuilder();
		Map<String, Map<String, Integer>> mapItem = new HashMap<>(betItems.size());
		for (String betItem : betItems) {
			String[] item = betItem.split("#");
			if (mapItem.containsKey(item[0])) {
				mapItem.get(item[0]).put(betItem, NumberTool.getInteger(item[1]));
			} else {
				Map<String, Integer> map = new HashMap<>(5);
				map.put(betItem, NumberTool.getInteger(item[1]));
				mapItem.put(item[0], map);
			}
		}
		for (Map<String, String> error : errorData) {
			String item = error.get("item");
			String msg = error.get("msg");
			Map<String, Integer> itemFunds = mapItem.get(item);
			if (IbmTypeEnum.isTrue(error.get("type"))) {
				String betInfo = ContainerTool.getKey4MaxVal(itemFunds);
				itemFunds.remove(betInfo);
				betItems.remove(betInfo);
			} else {

				String betInfo = ContainerTool.getKey4MinVal(itemFunds);
				itemFunds.remove(betInfo);
				betItems.remove(betInfo);
			}
			errorInfo.append(item).append("#").append(msg).append(",");
		}
		errorInfo.deleteCharAt(errorInfo.length() - 1);
		return errorInfo;
	}

	/**
	 * 投注信息限制
	 *
	 * @param ws2Util     ws2工具类
	 * @param existHmId   存在盘口会员id
	 * @param gameCode    游戏code
	 * @param period      期数
	 * @param betHandicap 盘口code
	 * @return 投注信息限制结果
	 */
	private JsonResultBeanPlus limitBetInfo(Ws2Util ws2Util, String existHmId, String gameCode, String period,
			Object betHandicap) {
		//投注信息
		JsonResultBeanPlus bean = ws2Util
				.betInfo(existHmId, gameCode, Ws2Config.getBetCode(game, betHandicap.toString()), IbmStateEnum.BET);
		if (!bean.isSuccess()) {
			log.error("WS2盘口会员【" + existHmId + "】游戏投注信息 = " + bean.toJsonString());
			return bean;
		}
		JSONObject betInfo = (JSONObject) bean.getData();
		//状态等于2时处于停押状态
		if (betInfo.getJSONObject("data").getInt("user_status") == 2) {
			bean.putEnum(IbmHcCodeEnum.IBM_403_USER_BAN_BET);
			bean.putSysEnum(IbmHcCodeEnum.CODE_403);
			bean.setSuccess(false);
			return bean;
		}

		//期数不匹配
		if (!period.replace("-", "")
				.equals(betInfo.getJSONObject("data").getJSONObject("betnotice").getString("timesnow"))) {
			log.error("WS2盘口会员【" + existHmId + "】期数不匹配，投注期数为：【" + period + "】，实际期数为：【" + betInfo.getJSONObject("data")
					.getJSONObject("betnotice").getString("timesnow") + "】");
			bean.putEnum(IbmHcCodeEnum.IBM_403_PERIOD);
			bean.putSysEnum(IbmHcCodeEnum.CODE_403);
			bean.setSuccess(false);
			return bean;
		}

		//封盘时间
		int timeClose = NumberTool
				.getInteger(betInfo.getJSONObject("data").getJSONObject("betnotice").getString("timeclose"));
		log.debug("根据盘口封盘时间进入睡眠");
		try {
			if (timeClose > 100) {
				Thread.sleep(RandomTool.getInt(5 * 1000));
			} else if (timeClose > 80) {
				Thread.sleep(RandomTool.getInt(4 * 1000));
			} else if (timeClose > 60) {
				Thread.sleep(RandomTool.getInt(3 * 1000));
			} else if (timeClose > 40) {
				Thread.sleep(RandomTool.getInt(2 * 1000));
			} else if (timeClose > 20) {
				Thread.sleep(RandomTool.getInt(1000));
			} else if (timeClose > Ws2Config.TIME_CLOSE) {
				Thread.sleep(RandomTool.getInt(500));
			} else if (timeClose < Ws2Config.TIME_CLOSE -2){
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
	private static Integer lessCredit(int credit, List<String> betItems) {
		for (String betItem : betItems) {
			credit -= NumberTool.getInteger(betItem.split("#")[1]);
		}
		return credit;
	}
}
