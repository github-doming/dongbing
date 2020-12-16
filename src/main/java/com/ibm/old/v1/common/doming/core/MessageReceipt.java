package com.ibm.old.v1.common.doming.core;
import c.a.util.core.json.JsonResultBean;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.http.IbmHttpConfig;
import net.sf.json.JSONObject;
import org.doming.develop.http.jsoup.HttpJsoupTool;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
/**
 * @Description: 消息回执类
 * @Author: Dongming
 * @Date: 2019-01-29 16:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface MessageReceipt extends CommMethod {
	/**
	 * 消息回执url
	 */
	String MESSAGE_RECEIPT_URL = IbmHttpConfig.HOST + IbmHttpConfig.PROJECT + "/ibm/cloud/t/manage/messageReceipt.do" ;
	/**
	 * 投注消息回执url
	 */
	String MESSAGE_RECEIPT_BET_URL =
			IbmHttpConfig.HOST + IbmHttpConfig.PROJECT + "/ibm/cloud/t/manage/messageReceiptBet.do" ;
	/**
	 * 投注数据回执url
	 */
	String MESSAGE_RECEIPT_BET_DATA_URL =
			IbmHttpConfig.HOST + IbmHttpConfig.PROJECT + "/ibm/cloud/t/manage/messageReceiptBetData.do" ;
	/**
	 * 检验数据回执url
	 */
	String MESSAGE_RECEIPT_HM_URL =
			IbmHttpConfig.HOST + IbmHttpConfig.PROJECT + "/ibm/cloud/t/manage/messageReceiptHm.do" ;

	/**
	 * 消息回执
	 *
	 * @param messageReceiptId 回执id
	 * @param state            回执状态
	 * @param bean             回执内容
	 * @param url              url
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	default String messageReceipt(String messageReceiptId, IbmStateEnum state, JsonResultBean bean, String url)
			throws IOException {
		JSONObject messageReceipt = new JSONObject();
		messageReceipt.put("messageReceiptId", messageReceiptId);
		messageReceipt.put("receiptState", state.name());
		messageReceipt.put("processResult", bean);
		JSONObject data = new JSONObject();
		data.put("data", messageReceipt);
		return HttpJsoupTool.doGetJson(60 * 1000, url, paramJson(data));
	}

	//TODO 基本通讯信息

	/**
	 * 消息回执，执行中
	 *
	 * @param messageReceiptId 回执id
	 * @throws IOException http请求错误
	 */
	default void messageReceiptProcess(String messageReceiptId) throws IOException {
		messageReceipt(messageReceiptId, IbmStateEnum.PROCESS, null, MESSAGE_RECEIPT_URL);
	}

	/**
	 * 投注消息回执，执行中
	 *
	 * @param messageReceiptBetId 回执id
	 * @throws IOException http请求错误
	 */
	default void messageReceiptBetProcess(String messageReceiptBetId) throws IOException {
		messageReceipt(messageReceiptBetId, IbmStateEnum.PROCESS, null, MESSAGE_RECEIPT_BET_URL);
	}

	/**
	 * 消息回执,完成
	 *
	 * @param messageReceiptId 回执id
	 * @param bean             回执内容
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	default String messageReceiptFinish(String messageReceiptId, JsonResultBean bean) throws IOException {
		return messageReceipt(messageReceiptId, IbmStateEnum.FINISH, bean, MESSAGE_RECEIPT_URL);
	}

	//TODO 投注项发送通讯信息

	/**
	 * 投注消息回执,完成
	 *
	 * @param messageReceiptBetId 回执id
	 * @param bean                回执内容
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	default String messageReceiptBetSuccess(String messageReceiptBetId, JsonResultBean bean) throws IOException {
		return messageReceipt(messageReceiptBetId, IbmStateEnum.SUCCESS, bean, MESSAGE_RECEIPT_BET_URL);
	}

	/**
	 * 投注消息回执,完成
	 *
	 * @param messageReceiptBetId 回执id
	 * @param bean                回执内容
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	default String messageReceiptBetFinish(String messageReceiptBetId, JsonResultBean bean) throws IOException {
		return messageReceipt(messageReceiptBetId, IbmStateEnum.FINISH, bean, MESSAGE_RECEIPT_BET_URL);
	}

	//TODO 投注项处理通讯信息

	/**
	 * 投注信息回执,完成
	 *
	 * @param resultData 投注信息
	 * @param userInfo   用户信息
	 * @param gameCode   游戏code
	 * @param period     期数
	 * @param handicap     盘口
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	static String messageReceiptBetSuccess(JSONObject userInfo, List<String> resultData, String gameCode, String period,
			String handicap) throws IOException {
		return messageReceiptBet(userInfo, String.join(",", resultData), gameCode, period, IbmStateEnum.SUCCESS,
				handicap);
	}

	/**
	 * 投注信息回执,完成
	 *
	 * @param resultData 投注信息
	 * @param gameCode   游戏code
	 * @param period     期数
	 * @param userInfo   用户信息
	 * @param handicap     盘口
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	static String messageReceiptBetFinish(JSONObject userInfo, Map<String, List<Map<String, Object>>> resultData,
			String gameCode, String period, String handicap) throws IOException {
		return messageReceiptBet(userInfo, resultData, gameCode, period, IbmStateEnum.FINISH, handicap);
	}

	/**
	 * 投注信息回执,异常
	 *
	 * @param resultData 投注信息
	 * @param gameCode   游戏code
	 * @param period     期数
	 * @param handicap     盘口
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	static String messageReceiptBetError(Map<String, List<String>> resultData, String gameCode, String period,
			String handicap) throws IOException {
		return messageReceiptBet(null, resultData, gameCode, period, IbmStateEnum.ERROR, handicap);
	}

	/**
	 * 投注信息回执
	 *
	 * @param userInfo   用户信息
	 * @param resultData 投注信息
	 * @param gameCode   游戏code
	 * @param period     期数
	 * @param state      回执状态
	 * @param handicap   盘口
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	static String messageReceiptBet(JSONObject userInfo, Object resultData, String gameCode, String period,
			IbmStateEnum state, String handicap) throws IOException {
		JSONObject messageReceipt = new JSONObject();
		messageReceipt.put("userInfo", userInfo);
		messageReceipt.put("resultData", resultData);
		messageReceipt.put("gameCode", gameCode);
		messageReceipt.put("period", period);
		messageReceipt.put("handicapCode", handicap);
		messageReceipt.put("receiptState", state.name());
		JSONObject data = new JSONObject();
		data.put("data", messageReceipt);
		return HttpJsoupTool.doGetJson(60 * 1000, MESSAGE_RECEIPT_BET_DATA_URL,
				"json=" + URLEncoder.encode(data.toString(), "UTF-8"));
	}

	//TODO 盘口会员通讯信息

	/**
	 * 盘口会员状态回执,校验
	 *
	 * @param existHmId 存在盘口会员id
	 * @param checkInfo  失败信息
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	default String messageReceiptHmCheck(String existHmId, JsonResultBeanPlus checkInfo) throws IOException {
		return messageReceiptHm(existHmId,checkInfo, IbmMethodEnum.CHECK.name());
	}

	/**
	 * 盘口会员状态回执,投注
	 *
	 * @param existHmId 存在盘口会员id
	 * @param betInfo  失败信息
	 * @return 请求结果
	 * @throws IOException http请求错误
	 */
	static String messageReceiptHmBet(String existHmId, JsonResultBeanPlus betInfo) throws IOException {
		return messageReceiptHm(existHmId,betInfo, IbmMethodEnum.BET.name());
	}

	/**
	 * 盘口会员状态回执
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param bean      结果bean
	 * @param state     处理状态
	 * @return 请求回执
	 * @throws IOException http请求错误
	 */
	static String messageReceiptHm(String existHmId, JsonResultBean bean, String state) throws IOException {
		JSONObject message = new JSONObject();
		message.put("existHmId", existHmId);
		message.put("bean", bean);
		message.put("receiptState", state);
		JSONObject data = new JSONObject();
		data.put("data", message);
		return HttpJsoupTool
				.doGetJson(60 * 1000, MESSAGE_RECEIPT_HM_URL, "json=" + URLEncoder.encode(data.toString(), "UTF-8"));
	}


}
