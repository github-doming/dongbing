package com.ibm.old.v1.client.core.listener.mq.bet;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.core.controller.bet.BetIDCController;
import com.ibm.old.v1.client.core.controller.bet.BetSgWinController;
import com.ibm.old.v1.client.core.controller.bet.BetWS2Controller;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.service.IbmClientExistHmTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import net.sf.json.JSONObject;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

/**
 * @Description: 定时投注-消息接收端
 * @Author: Dongming
 * @Date: 2018-12-01 15:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetMQListener extends BaseCommMq implements MessageReceipt {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private String clientExistHmId, messageReceiptBetId;
	private JSONObject msgObj;
	@Override public String execute(String message) throws Exception {
		log.trace("投注进盘口中，接收的消息是：" + message);

		if (!valiParameter(message)) {
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			if (StringTool.notEmpty(messageReceiptBetId)) {
				return messageReceiptBetFinish(messageReceiptBetId, bean);
			}
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_MESSAGE_RECEIPT_ID);
			return returnJson(bean);
		}
		messageReceiptBetProcess(messageReceiptBetId);
		try {
			IbmClientExistHmTService hmExistTService = new IbmClientExistHmTService();
			IbmClientExistHmT hmExistT = hmExistTService.find(clientExistHmId);
			if (hmExistT == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_HM_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return messageReceiptBetFinish(messageReceiptBetId, bean);
			}
			IbmHandicapEnum handicap = hmExistT.getHandicapCodeEnum();
			if (handicap == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return messageReceiptBetFinish(messageReceiptBetId, bean);
			}
			ClientExecutor execute;
			switch (handicap) {
				//WS2盘口
				case WS2:
					execute = new BetWS2Controller();
					bean = execute.execute(msgObj, hmExistT, messageReceiptBetId);
					break;
				//IDC盘口
				case IDC:
					execute = new BetIDCController();
					bean = execute.execute(msgObj, hmExistT, messageReceiptBetId);
					break;
				case SGWIN:
					execute = new BetSgWinController();
					bean = execute.execute(msgObj, hmExistT, messageReceiptBetId);
					break;
				//TODO 其他盘口
				default:
					bean.putEnum(IbmCodeEnum.IBM_404_NOT_CAPTURE_METHOD);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
			}
			log.trace("投注进盘口中，返回的结果是：" + returnJson(bean));
			return messageReceiptBetSuccess(messageReceiptBetId, bean);
		} catch (Exception e) {
			log.error("投注进盘口中失败", e);
			bean.putSysEnum(IbmCodeEnum.CODE_500);
			bean.setData(e.getMessage());
			messageReceiptBetFinish(messageReceiptBetId, bean);
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
		if (StringTool.isEmpty(clientExistHmId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			return false;
		}
		messageReceiptBetId = msgObj.getString("messageReceiptBetId");
		return true;
	}
}
