package com.ibm.follow.servlet.client.core.mq.member;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.bet.VrFollowBetInfoController;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

/**
 * @Description: 虚拟会员投注信息
 * @Author: null
 * @Date: 2020-07-17 09:29
 * @Version: v1.0
 */
public class VrBetInfoListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private JSONObject msgObj;
	private IbmMethodEnum method;
	private GameUtil.Code gameCode;
	private String existHmId;
	private Object period;
	private String betContent;
	private double funds;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("投注信息，接收的消息是：" + message));
		// 消息预处理
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			bean.setRequestType(IbmStateEnum.FAIL.name());
			log.error(getLog("投注信息，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendInfoReceipt(bean.toJsonString(), "bet.result");
			return null;
		}
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			bean.setRequestType(IbmStateEnum.FAIL.name());
			log.error(getLog("投注信息，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendInfoReceipt(bean.toJsonString(), "bet.result");
			return null;
		}
		try {
			//消息接收-process
			bean.setRequestType(IbmStateEnum.PROCESS.name());
			RabbitMqTool.sendInfoReceipt(bean.toJsonString(), "bet.result");
			// 信息处理
			switch (method) {
				case FOLLOW_VR_MEMBER_BET:
					//跟随虚拟会员投注
					bean=new VrFollowBetInfoController(existHmId, gameCode, period, betContent, funds).execute(msgObj);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的投注信息方法接收".concat(method.name())));
			}
			log.info(getLog("投注信息,处理完成，返回的结果是：".concat(bean.toJsonString())));
		} catch (Exception e) {
			log.error(getLog("投注信息,处理错误:".concat(e.getMessage())));
			throw e;
		}
		return null;
	}

	@Override protected boolean valiParameter(String message) {
		if (StringTool.isEmpty(message)) {
			bean.putEnum(CodeEnum.IBS_401_MESSAGE);
			return false;
		}
		msgObj = JSON.parseObject(message);
		if (ContainerTool.isEmpty(msgObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		Object methodObj = msgObj.get("METHOD_");
		Object gameCodeObj = msgObj.get("GAME_CODE_");
		existHmId = msgObj.getOrDefault("EXIST_HM_ID_", "").toString();
		period = msgObj.getOrDefault("PERIOD_", "");
		betContent = msgObj.getOrDefault("BET_CONTENT_", "").toString();
		Object fundTh = msgObj.get("FUNDS_T_");
		if (StringTool.isEmpty(methodObj, gameCodeObj, existHmId, period, betContent, fundTh)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		method = IbmMethodEnum.valueOf(methodObj.toString());
		gameCode = GameUtil.Code.valueOf(gameCodeObj.toString());
		funds = NumberTool.doubleT(fundTh);

		JSONObject result = new JSONObject();
		result.put("EXIST_HM_ID_",existHmId);
		result.put("GAME_CODE_",gameCodeObj);
		result.put("PERIOD_",period);
		result.put("BET_INFO_CODE_", msgObj.getOrDefault("BET_INFO_CODE_",""));
		bean.setToken(result.toString());
		bean.setCommand(method.name());
		return true;

	}
}
