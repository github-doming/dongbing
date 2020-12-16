package com.ibm.old.v1.client.core.job.bet;
import com.ibm.old.v1.client.ibm_client_bet.t.service.IbmClientBetTService;
import com.ibm.old.v1.client.ibm_client_bet_error.t.service.IbmClientBetErrorTService;
import com.ibm.old.v1.client.ibm_client_bet_fail.t.service.IbmClientBetFailTService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.common.doming.configs.IdcConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.IdcTool;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.IdcUtil;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.quartz.JobExecutionContext;

import java.util.*;
/**
 * @Description: IDC投注定时任务
 * @Author: zjj
 * @Date: 2019-03-12 15:30
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class BetIDCJob extends BaseCommJob {
	private String existHmId;
	private String game;
	private String period;
	@Override public void executeJob(JobExecutionContext context) throws Exception {

		String gameCode = context.getMergedJobDataMap().getString("gameCode");
		String existHmId = context.getMergedJobDataMap().getString("existHmId");
		String period = context.getMergedJobDataMap().get("period").toString();
		log.trace("定时投注IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】触发");
		try {
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.error("定时投注IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败，用户处于登出状态");
				return;
			}
			JsonResultBeanPlus bean = execute(existHmId, gameCode, period);
			if (bean.isSuccess()) {
				log.info("定时投注IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】成功:" + bean
						.toJsonString());
			} else {
				log.error("定时投注IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败:" + bean
						.toJsonString());
			}
		} catch (Exception e) {
			log.error("定时投注IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】异常:" + e.getMessage());
			throw e;
		}
	}
	/**
	 * 执行
	 *
	 * @param existHmId 存在盘口会员id
	 * @param game      游戏code
	 * @param period    期数
	 * @return 执行结果，失败date存code，成功data存信息
	 */
	public JsonResultBeanPlus execute(String existHmId, String game, String period) throws Exception {
		CurrentTransaction.transactionCommit();
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
			//第一次投注
			for (Map<String, Object> existBetInfo : existBetTList) {
				existBetIds.add(existBetInfo.get("CLIENT_EXIST_BET_ID_").toString());
				//等待投注信息
				if (ContainerTool.isEmpty(existBetInfo.get("BET_INFO_"))) {
					continue;
				}
				JSONObject betItems = JSONObject.fromObject(existBetInfo.get("BET_INFO_"));
				//迭代未知key值
				Iterator sIterator = betItems.keySet().iterator();
				for (int i = 0; i < betItems.size(); i++) {
					String key = (String) sIterator.next();
					JSONArray betItem = betItems.getJSONArray(key);
					bean = betProcess(betItem, existBetInfo, key);
					if (!bean.isSuccess()) {
						log.error("IDC盘口会员【" + existHmId + "】投注进IDC中失败原因:" + bean.toJsonString());
						break;
					}
				}
			}

			//启动定时器校验投注结果
			//查询到下一期开奖结果
			JsonResultBeanPlus profitBean = QuartzIbmUtil.betProfitJob(existHmId, IbmHandicapEnum.IDC, game, period);
			log.trace("IDC盘口会员【" + existHmId + "】获取开奖结果定时器启动结果:" + profitBean.toJsonString());
			//获取更新异常的投注信息，返回到主服务器
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			Map<String, List<String>> errorBetInfos = betErrorTService.listErrorBetInfo(existHmId, period, game);
			if (ContainerTool.notEmpty(errorBetInfos)) {
				MessageReceipt.messageReceiptBetError(errorBetInfos, game, period, IbmHandicapEnum.IDC.name());
			}
			// 更新正常投注信息，返回到主服务器
			List<String> existBetItemIds = existBetTService.updateBet(existBetIds);
			//获取用户信息
			JSONObject userInfo = getUserInfo(existHmId);

			bean.setData(MessageReceipt
					.messageReceiptBetSuccess(userInfo, existBetItemIds, game, period, IbmHandicapEnum.IDC.name()));
			bean.success();
			return bean;
		} finally {
			CurrentTransaction.transactionBegin();
		}
	}
	/**
	 * 获取用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
	 */
	private JSONObject getUserInfo(String existHmId) {
		JSONObject userInfo = new JSONObject();

		IdcUtil idcUtil = IdcUtil.findInstance();
		JsonResultBeanPlus userBean = idcUtil.userInfo(existHmId);

		if (!userBean.isSuccess()) {
			log.info("IDC盘口会员【" + existHmId + "】获取用户信息失败" + userBean.toJsonString());
		} else {
			log.trace("IDC盘口会员【" + existHmId + "】获取用户信息成功" + userBean.toJsonString());
			Map<String, Object> user = (Map<String, Object>) userBean.getData();
			userInfo.put("existHmId", existHmId);
			userInfo.put("NICKNAME", user.get("memberAccount"));
			userInfo.put("BALANCE", user.get("usedQuota"));
			userInfo.put("PROFIT", user.get("profitAmount"));
		}
		return userInfo;
	}
	/**
	 * IDC投注过程
	 *
	 * @param betItem 投注内容
	 * @param map     已存在投注项
	 * @param betType 投注类型
	 * @return 投注结果
	 */
	private JsonResultBeanPlus betProcess(List<String> betItem, Map<String, Object> map, String betType)
			throws Exception {
		IdcUtil idcUtil = IdcUtil.findInstance();
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		Date nowTime = new Date();
		String existBetId = map.get("CLIENT_EXIST_BET_ID_").toString();
		String execBetItemId = map.get("EXEC_BET_ITEM_ID_").toString();
		String cloudReceiptBetId = map.get("CLOUD_RECEIPT_BET_ID_").toString();
		String handicapMemberId = map.get("HANDICAP_MEMBER_ID_").toString();

		Map<String, String> userInfo = idcUtil.getUserInfo(existHmId);
		if (ContainerTool.isEmpty(userInfo)) {
			bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}

		//判断余额是否充足
		if (IdcTool.isPointExcess(Double.parseDouble(userInfo.get("usedQuota")), betItem)) {
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			betErrorTService
					.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
							String.join(",", betItem).concat("-余额不足"), nowTime);
			bean.putEnum(IbmHcCodeEnum.IBM_403_POINT_NOT_ENOUGH);
			bean.putSysEnum(IbmHcCodeEnum.CODE_403);
			return bean;
		}
		//投注信息限制
		bean = limitBetInfo(idcUtil, existHmId, betType, period);
		if (!bean.isSuccess()) {
			if (IbmHcCodeEnum.IBM_403_SEAL_HANDICAP.getCode().equals(bean.getCode()) || IbmHcCodeEnum.IBM_403_PERIOD
					.getCode().equals(bean.getCode())) {
				IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
				betErrorTService
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								String.join(",", betItem).concat("-已封盘"), nowTime);
			}
			return bean;
		}
		bean.setSuccess(false);
		Map<String, JSONObject> betInfo = (Map<String, JSONObject>) bean.getData();
		JSONObject drawsInfo = betInfo.get("drawsInfo");
		JSONObject oddInfo = betInfo.get("oddInfo");
		bean = idcUtil
				.betting(existHmId, IdcConfig.GAME_BET_CODE.get(game), idcUtil.getBallCodeType(betType), drawsInfo,
						oddInfo, betItem, drawsInfo.getString("cd"));
		if (bean.isSuccess()) {
			// 投注部分成功-全部成功
			//处理成功的信息 - 写入bet	- successData
			Map<String, List<Map<String, String>>> resultData = (Map<String, List<Map<String, String>>>) bean.getData();
			//投注成功
			if (ContainerTool.isContain(resultData, "successData")) {
				IbmClientBetTService betTService = new IbmClientBetTService();
				List<Map<String, String>> successData = resultData.get("successData");
				for (Map<String, String> successInfo : successData) {
					betItem.remove(successInfo.get("betInfo"));
					betTService.saveBetSuccess(existHmId, game, period, betType, existBetId, successInfo, nowTime);
				}
			}
			//处理失败的信息 - 写入error - errorData
			if (ContainerTool.isContain(resultData, "errorData")) {
				List<Map<String, String>> errorData = resultData.get("errorData");
				StringBuilder errorInfo = getErrorInfo(betItem, errorData);
				IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
				betErrorTService
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								errorInfo.toString(), nowTime);
			}

			//处理未投注的信息 - 写入fail - betItem
			if (ContainerTool.isContain(resultData, "failData")) {
				List<Map<String, String>> failData = resultData.get("failData");
				StringBuilder failInfo = new StringBuilder();
				for (Map<String, String> result : failData) {
					betItem.remove(result.get("betInfo"));
					failInfo.append(result.get("betInfo")).append(",");
				}
				new IbmClientBetFailTService()
						.save(existHmId, game, period, betType, existBetId, execBetItemId, cloudReceiptBetId,
								handicapMemberId, failInfo.toString(), nowTime);
			}

		} else {
			// 投注失败 - 写入error - betItem
			IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
			if (bean.getData() != null) {
				StringBuilder errorInfo = new StringBuilder();
				for (String item : betItem) {
					errorInfo.append(item.split("#")[0]).append("#").append(bean.getData().toString()).append(",");
				}
				errorInfo.deleteCharAt(errorInfo.length() - 1);
				betErrorTService
						.save(existHmId, game, period, existBetId, execBetItemId, cloudReceiptBetId, handicapMemberId,
								errorInfo.toString(), nowTime);
			} else {
				//第一次投注，投注失败，重新投注
				new IbmClientBetFailTService()
						.save(existHmId, game, period, betType, existBetId, execBetItemId, cloudReceiptBetId,
								handicapMemberId, String.join(",", betItem), nowTime);
			}

		}
		return bean;
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
	 * @param idcUtil   idc工具类
	 * @param existHmId 存在盘口会员id
	 * @param betType   盘口code
	 * @param period    期数
	 * @return 投注信息限制结果
	 */
	private JsonResultBeanPlus limitBetInfo(IdcUtil idcUtil, String existHmId, String betType, String period) {
		JsonResultBeanPlus bean = idcUtil
				.betInfo(existHmId, IdcConfig.GAME_BET_CODE.get(game), idcUtil.getBetType(betType));
		if (!bean.isSuccess()) {
			log.error("IDC盘口会员【" + existHmId + "】游戏投注信息 = " + bean.toJsonString());
			return bean;
		}

		Map<String, JSONObject> betInfo = (Map<String, JSONObject>) bean.getData();
		JSONObject drawsInfo = betInfo.get("drawsInfo");
		log.trace("IDC盘口会员【" + existHmId + "】当前期数=" + drawsInfo.getString("cd"));
		log.trace("IDC盘口会员【" + existHmId + "】封盘时间=" + drawsInfo.getString("ceTs"));

		if (!period.replace("-", "").equals(drawsInfo.getString("cd"))) {
			bean.putEnum(IbmHcCodeEnum.IBM_403_PERIOD);
			bean.putSysEnum(IbmHcCodeEnum.CODE_403);
			JSONObject jsonData = new JSONObject();
			jsonData.put("period", period);
			jsonData.put("nowPeriod", drawsInfo.getString("cd"));
			bean.setData(jsonData);
			return bean;
		}

		//封盘时间
		int timeClose = NumberTool.getInteger(drawsInfo.getString("ceTs"));
		log.debug("根据盘口封盘时间进入睡眠");
		try {
			if (timeClose > 100) {
				Thread.sleep(RandomTool.getInt(2000));
			} else if (timeClose > 80) {
				Thread.sleep(RandomTool.getInt(1500));
			} else if (timeClose > 60) {
				Thread.sleep(RandomTool.getInt(1000));
			} else if (timeClose > 40) {
				Thread.sleep(RandomTool.getInt(500));
			} else if (timeClose > 20) {
				Thread.sleep(RandomTool.getInt(200));
			} else if (timeClose > IdcConfig.TIME_CLOSE) {
				Thread.sleep(RandomTool.getInt(50));
			} else if (timeClose < IdcConfig.TIME_CLOSE - 2) {
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

}
