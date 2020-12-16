package com.ibm.old.v1.cloud.ibm_message.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.entity.IbmCloudReceiptT;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.service.IbmCloudReceiptTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_handicap_member.t.controller.LoginHandicapController;
import net.sf.json.JSONObject;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 消息回执
 * @Author: zjj
 * @Date: 2019-04-09 15:22
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class MessageReceiptAction extends BaseAppAction {

	@Override public String run() throws Exception {
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
		//获取回执码
		IbmStateEnum state = IbmStateEnum.getReceiptState(receiptState);
		if (state == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_STATE);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		try {

			IbmCloudReceiptTService receiptTService = new IbmCloudReceiptTService();
			IbmCloudReceiptT messageReceiptT = receiptTService.findRevise(messageReceiptId, state);

			if (messageReceiptT == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(bean);
			}
			if (IbmStateEnum.FINISH.equals(state)) {
				IbmMethodEnum method = IbmMethodEnum.getMethod(messageReceiptT.getMessageMethod());
				if (method == null) {
					bean.putEnum(IbmCodeEnum.IBM_404_NOT_CAPTURE_METHOD);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return returnJson(bean);
				}
				JSONObject processResult = JSONObject.fromObject(dataMap.get("processResult"));
				switch (method) {
					case LOGIN:
						IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
						if (processResult.getBoolean("success")) {
							//设置盘口会员登录信息
							LoginHandicapController loginHandicapController = new LoginHandicapController();
							loginHandicapController.execute(messageReceiptT.getHandicapMemberId(),
									processResult.get("data").toString());
						} else {
							handicapMemberTService
									.updateMemberInfo(messageReceiptT.getHandicapMemberId(), processResult.get("data"));
						}
						break;
					case OTHER:
					default:
						//TODO 暂时仅针对登陆进行处理，后续可追加其余的方法处理
						break;

				}
				// 更新消息回执内容
				messageReceiptT.setProcessResult(processResult);
			}
			// 更新消息回执状态
			messageReceiptT.setReceiptState(state.name());
			messageReceiptT.setUpdateTime(new Date());
			messageReceiptT.setUpdateTimeLong(messageReceiptT.getUpdateTime().getTime());
			receiptTService.update(messageReceiptT);
			bean.success();
		} catch (Exception e) {
			log.error("更新消息回执状态失败，消息回执id为" + messageReceiptId);
			throw e;
		}

		return this.returnJson(bean);
	}
}
