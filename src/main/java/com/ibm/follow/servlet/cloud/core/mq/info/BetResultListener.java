package com.ibm.follow.servlet.cloud.core.mq.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.FollowBetResultController;
import com.ibm.follow.servlet.cloud.core.controller.process.ManualBetResultController;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 投注结果
 * @Author: Dongming
 * @Date: 2019-08-26 18:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetResultListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private JSONObject msgObj;
	private IbmMethodEnum method;
	private IbmStateEnum requestType;
	@Override public String execute(String message) throws Exception {
		log.info(getLog("投注结果，接收的消息是：" + message));

		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("投注结果，处理的结果是：".concat(bean.toJsonString())));
			return null;
		}
		try {
			switch (method) {
				case MANUAL_BET:
					ManualBetResultController manualBetController=new ManualBetResultController();
					bean = manualBetController.execute(msgObj,requestType);
					break;
				case FOLLOW_BET:
					FollowBetResultController followBetResultController=new FollowBetResultController();
					bean = followBetResultController.execute(msgObj,requestType);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的投注方法接收".concat(method.name())));
					return null;
			}
			log.debug(getLog("投注结果，处理完成，处理的结果是：".concat(bean.toJsonString())));
		} catch (Exception e) {
			log.error(getLog("投注结果,处理错误:".concat(e.getMessage())));
			throw e;
		}
		return null;
	}

	@Override public boolean valiParameter(String message) {
		if (StringTool.isEmpty(message)) {
			bean.putEnum(CodeEnum.IBS_401_MESSAGE);
			return false;
		}
		msgObj = JSON.parseObject(message);
		if (ContainerTool.isEmpty(msgObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		String methodStr = msgObj.getString("command");
		String requestTypeStr = msgObj.getString("requestType");
		if (StringTool.isEmpty(requestTypeStr)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}

		method = IbmMethodEnum.valueOf(methodStr);
		requestType = IbmStateEnum.valueOf(requestTypeStr);
		return true;
	}
}
