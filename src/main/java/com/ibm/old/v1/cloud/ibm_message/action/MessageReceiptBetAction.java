package com.ibm.old.v1.cloud.ibm_message.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.controller.mq.CloseClientController;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.entity.IbmCloudReceiptBetT;
import com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.service.IbmCloudReceiptBetTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 投注消息回执
 * @Author: zjj
 * @Date: 2019-04-09 15:22
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class MessageReceiptBetAction extends BaseAppAction {

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findDateMap();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String messageReceiptId = BeanThreadLocal.find(dataMap.get("messageReceiptId"), "");
		String receiptState = BeanThreadLocal.find(dataMap.get("receiptState"), "");

		if (StringTool.isEmpty(messageReceiptId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}

		if (!IbmStateEnum.isReceiptState(receiptState)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_STATE);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		try {
			IbmCloudReceiptBetTService receiptBetTService = new IbmCloudReceiptBetTService();
			IbmCloudReceiptBetT receiptBetT = receiptBetTService.find(messageReceiptId);

			if (receiptBetT == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(bean);
			}
			if (ContainerTool.notEmpty(dataMap.get("processResult"))) {
				// 更新消息回执状态与内容
				JSONObject processResult = JSONObject.fromObject(dataMap.get("processResult"));
				if (IbmHcCodeEnum.IBM_403_USER_STATE.name().equals(processResult.get("code"))){
					IbmCloudClientHmTService cloudClientHmTService = new IbmCloudClientHmTService();
					String handicapMemberId = cloudClientHmTService.findHandicapMemberId(receiptBetT.getClientExistHmId());
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("MESSAGE",processResult.get("message"));
					jsonObject.put("CODE",processResult.get("code"));
					IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
					handicapMemberTService.updateMemberInfo(handicapMemberId,jsonObject);
					new CloseClientController().execute(handicapMemberId);
				}
				receiptBetT.setProcessResult(processResult.toString());
			}
			receiptBetT.setReceiptState(receiptState);
			receiptBetT.setUpdateTime(new Date());
			receiptBetT.setUpdateTimeLong(receiptBetT.getUpdateTime().getTime());
			receiptBetTService.update(receiptBetT);

			bean.success();
		} catch (Exception e) {
			log.error("更新消息回执状态失败，消息回执id为" + messageReceiptId);
			throw e;
		}
		return this.returnJson(bean);
	}
}
