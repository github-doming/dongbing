package com.ibm.old.v1.client.core.listener.mq.method;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.core.controller.method.HmMqCheckController;
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
 * @Description: 用户操作-消息接收端
 * @Author: Dongming
 * @Date: 2018-12-01 15:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MethodMQListener extends BaseCommMq implements MessageReceipt {

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private JSONObject msgObj;
	private String existHmId;
	private IbmMethodEnum method;

	@Override public String execute(String message) throws Exception {
		log.trace("用户操作-消息接收端，接收的消息是：" + message);
		if (!valiParameter(message)) {
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}

		try {
			IbmClientExistHmTService hmExistTService = new IbmClientExistHmTService();
			IbmClientExistHmT hmExistT = hmExistTService.find(existHmId);
			if (hmExistT == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(bean);
			}
			ClientExecutor execute;
			switch (method) {
				// 盘口会员定时校验
				case CHECK:
					execute = new HmMqCheckController();
					bean = execute.execute(msgObj);
					return messageReceiptHmCheck(existHmId,  bean);
				case OTHER:
				default:
					bean.putEnum(IbmCodeEnum.IBM_404_NOT_CAPTURE_METHOD);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
			}
			log.trace("用户操作-消息接收端，返回的结果是：" + returnJson(bean));
			return  returnJson(bean);
		} catch (Exception e) {
			log.error("用户操作-消息接收端" + method.name() + "失败", e);
			bean.putSysEnum(IbmCodeEnum.CODE_500);
			bean.setData(e.getMessage());
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
		existHmId = msgObj.getString("clientExistHmId");
		if (StringTool.isEmpty(existHmId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_CLIENT_HM_EXIST_ID);
			return false;
		}
		method = IbmMethodEnum.getMethod(msgObj.get("method"));
		if (method == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_METHOD);
			return false;
		}
		return true;
	}
}
