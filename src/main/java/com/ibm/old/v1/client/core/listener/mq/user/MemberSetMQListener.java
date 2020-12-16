package com.ibm.old.v1.client.core.listener.mq.user;

import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.core.controller.set.MemberLoginSetController;
import com.ibm.old.v1.client.core.controller.set.MemberLogonController;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.service.IbmClientExistHmTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import net.sf.json.JSONObject;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 用户设置-会员设置-消息接收端
 * @Author: Dongming
 * @Date: 2018-12-01 14:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberSetMQListener extends BaseCommMq implements MessageReceipt {

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private String clientExistHmId, messageReceiptId;
	private IbmMethodEnum method;
	private JSONObject msgObj;

	@Override public String execute(String message) throws Exception {
		log.trace("用户设置-会员设置，接收的消息是：" + message);

		if (!valiParameter(message)) {
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			if (StringTool.notEmpty(messageReceiptId)) {
				return messageReceiptFinish(messageReceiptId, bean);
			}
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_MESSAGE_RECEIPT_ID);
			return returnJson(bean);

		}
		messageReceiptProcess(messageReceiptId);
		try {
			IbmClientExistHmTService hmExistTService = new IbmClientExistHmTService();
			IbmClientExistHmT hmExistT = hmExistTService.find(clientExistHmId);
			if (hmExistT == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return messageReceiptFinish(messageReceiptId, bean);
			}
			ClientExecutor execute;
			switch (method) {
				//登陆设置
				case LOGIN:
					execute = new MemberLoginSetController();
					bean = execute.execute(msgObj, hmExistT);
					break;
				case LOGON:
					execute = new MemberLogonController();
					bean = execute.execute(msgObj, hmExistT);
					break;
				default:
					bean.putEnum(IbmCodeEnum.IBM_404_NOT_CAPTURE_METHOD);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
			}
			log.trace("用户设置-会员设置" + method.name() + "返回的结果是：" + returnJson(bean));
			return messageReceiptFinish(messageReceiptId, bean);
		} catch (Exception e) {
			log.error("用户设置-会员设置" + method.name() + "失败", e);
			bean.putSysEnum(IbmCodeEnum.CODE_500);
			bean.setData(e.getMessage());
			messageReceiptFinish(messageReceiptId, bean);
			throw e;
		}
	}

	@Override protected boolean valiParameter(String message) {
		if (StringTool.isEmpty(message)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_MESSAGE);
			return false;
		}
		msgObj = JSONObject.fromObject(message);
		if (ContainerTool.isEmpty(msgObj)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			return false;
		}
		clientExistHmId = msgObj.getString("clientExistHmId");
		if (clientExistHmId == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_CLIENT_HM_EXIST_ID);
			return false;
		}
		messageReceiptId = msgObj.getString("messageReceiptId");
		method = IbmMethodEnum.getMethod(msgObj.get("method"));
		if (method == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_METHOD);
			return false;
		}
		return true;
	}
}
