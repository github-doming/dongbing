package com.ibm.old.v1.cloud.core.controller.mq;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.entity.IbmCloudReceiptT;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.service.IbmCloudReceiptTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import net.sf.json.JSONObject;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 设置游戏投注状态
 * @Author: Dongming
 * @Date: 2019-03-11 18:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SetGameBetStateController implements CloudExecutor {
	@Override public JsonResultBeanPlus execute(String... inVar) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String handicapMemberId = inVar[0];
		String gameId = inVar[1];
		String betState = inVar[2];
		String gameCode = GameTool.findCode(gameId).name();

		// 查找是否存在客户端已存在盘口会员主键
		IbmCloudClientHmTService clientHmTService = new IbmCloudClientHmTService();
		String existHmId = clientHmTService.findExistHmId(handicapMemberId);
		// 中心端客户端盘口会员为空，即不已存在连接通道
		if (StringTool.isEmpty(existHmId)) {
			//设置盘口游戏投注信息
			return new SetGameBetInfoController().execute(inVar);
		}
		existHmId = clientHmTService.findExistHmId(handicapMemberId);

		//查询消息回执
		IbmCloudReceiptTService receiptTService = new IbmCloudReceiptTService();
		IbmCloudReceiptT receiptT = receiptTService.find(existHmId, IbmMethodEnum.SET_GAME_BET_STATE.name());

		Date nowTime = new Date();
		String receiptId;
		IbmStateEnum state;
		//没有发送过投注比例消息
		if (receiptT == null) {
			//添加消息回执
			receiptId = receiptTService
					.newMessageReceipt(existHmId, handicapMemberId, IbmMethodEnum.SET_GAME_BET_STATE, nowTime);
		} else {
			receiptId = receiptT.getIbmCloudReceiptId();
			for (int i = 0; i < 10; i++) {
				state = receiptTService.findReceiptState(receiptId);
				// 发送mq消息成功,解析date,回传信息
				if (IbmStateEnum.FINISH.equals(state)) {
					bean.setData(receiptId);
					bean.success();
					return bean;
				}
				Thread.sleep(100);
			}
			//登陆失败重新发送
			receiptTService.updateNewMessageReceipt(receiptId, IbmMethodEnum.SET_GAME_BET_STATE, nowTime,this.getClass().getName());
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("clientExistHmId", existHmId);
		jsonObject.put("messageReceiptId", receiptId);
		jsonObject.put("gameCode", gameCode);
		jsonObject.put("method", IbmMethodEnum.SET_GAME_BET_STATE.name());
		jsonObject.put("gameId", gameId);
		jsonObject.put("betState", betState);
		// 发送用户mq消息到客户端
		String result = RabbitMqIbmUtil.sendExchange4User(handicapMemberId, "game", jsonObject.toString());
		if (!Boolean.parseBoolean(result)) {
			// 发送mq消息失败
			bean.putEnum(IbmCodeEnum.IBM_404_MQ_SEND_FAIL);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		for (int i = 0; i < 20; i++) {
			state = receiptTService.findReceiptState(receiptId);
			// 发送mq消息成功,解析date,回传信息
			if (IbmStateEnum.PROCESS.equals(state)) {
				bean.process();
				return bean;
			}
			if (IbmStateEnum.FINISH.equals(state)) {
				bean.setData(receiptId);
				bean.success();
				return bean;
			}
			Thread.sleep(100);
		}

		// 发送mq消息失败
		bean.putEnum(IbmCodeEnum.IBM_404_MQ_SEND_FAIL);
		bean.putSysEnum(IbmCodeEnum.CODE_404);
		return bean;
	}
}
