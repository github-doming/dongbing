package com.ibm.follow.servlet.cloud.core.mq.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.CheckMemberController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * 会员信息
 *
 * @Author: Dongming
 * @Date: 2019-12-30 14:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private JSONObject msgObj;
	private IbmMethodEnum method;
	private String existHmId;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("会员信息，接收的消息是：" + message));
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("会员信息，处理的结果是：".concat(bean.toJsonString())));
			return null;
		}
		try {
			switch (method) {
				case LOGOUT:
					//  会员登出
					logout();
					break;
				case CUSTOMER_INFO:
					// 会员心跳
					CheckMemberController memberController=new CheckMemberController();
					bean=memberController.execute(msgObj);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的会员信息方法接收".concat(method.name())));
					return null;

			}
			log.debug(getLog("会员信息，处理完成，处理的结果是：".concat(bean.toJsonString())));
		} catch (Exception e) {
			log.error(getLog("会员信息,处理错误:".concat(e.getMessage())));
			throw e;
		}
		return null;
	}

	private void logout() throws Exception {
		/*
			1.记录错误信息
			2.写入登出事件
		 */
		String code = msgObj.getString("code");
		String message = msgObj.getString("message");

		String handicapMemberId = new IbmClientHmService().findHmId(existHmId);
		new IbmHmInfoService().updateMemberInfo(handicapMemberId,code,message);

		new LogoutHmController().execute(handicapMemberId);

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

		String methodStr = msgObj.getString("method");
		if (StringTool.isEmpty(methodStr)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		method = IbmMethodEnum.valueOf(methodStr);

		if (!msgObj.containsKey("EXIST_HM_ID_")) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		existHmId = msgObj.getString("EXIST_HM_ID_");
		return true;
	}

}
