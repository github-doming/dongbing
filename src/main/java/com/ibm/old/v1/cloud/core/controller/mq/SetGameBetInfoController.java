package com.ibm.old.v1.cloud.core.controller.mq;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.entity.IbmCloudReceiptT;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.service.IbmCloudReceiptTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;
/**
 * @Description: 设置游戏投注基本信息
 * @Author: Dongming
 * @Date: 2019-03-11 15:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SetGameBetInfoController implements CloudExecutor {

	@Override public JsonResultBeanPlus execute(String... inVar) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String handicapMemberId = inVar[0];
		String gameId = inVar[1];
		IbmHmGameSetTService hmGameSetTService = new IbmHmGameSetTService();
		Map<String, Object> gameSetInfo = hmGameSetTService.findGameSetInfo(handicapMemberId, gameId);
		if (ContainerTool.isEmpty(gameSetInfo)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		// 查找是否存在客户端已存在盘口会员主键
		IbmCloudClientHmTService clientHmTService = new IbmCloudClientHmTService();
		String existHmId = clientHmTService.findExistHmId(handicapMemberId);
		// 中心端客户端盘口会员为空，即不已存在连接通道
		if (StringTool.isEmpty(existHmId)) {
			//开启客户机
			bean = new LoginClientController().execute(handicapMemberId);
			//开启失败
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setSuccess(false);
		}
		existHmId = clientHmTService.findExistHmId(handicapMemberId);

		//查询消息回执
		IbmCloudReceiptTService receiptTService = new IbmCloudReceiptTService();
		IbmCloudReceiptT receiptT = receiptTService.find(existHmId, IbmMethodEnum.SET_GAME_BET_INFO.name());

		Date nowTime = new Date();
		String receiptId;
		IbmStateEnum state;
		//没有发送过投注比例消息
		if (receiptT == null) {
			//添加消息回执
			receiptId = receiptTService
					.newMessageReceipt(existHmId, handicapMemberId, IbmMethodEnum.SET_GAME_BET_INFO, nowTime);
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
			receiptTService.updateNewMessageReceipt(receiptId, IbmMethodEnum.SET_GAME_BET_INFO, nowTime,this.getClass().getName());
		}
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("clientExistHmId", existHmId);
		jsonObject.put("messageReceiptId", receiptId);
		jsonObject.put("method", IbmMethodEnum.SET_GAME_BET_INFO.name());
		jsonObject.put("gameId", gameSetInfo.get("GAME_ID_"));
		jsonObject.put("gameCode", gameSetInfo.get("GAME_CODE_"));
		jsonObject.put("betState", gameSetInfo.get("BET_STATE_"));
		jsonObject.put("betSecond", NumberTool.getInteger(gameSetInfo.get("SEAL_TIME_")) + NumberTool
				.getInteger(gameSetInfo.get("BET_SECOND_")));
		jsonObject.put("splitTwoSideAmount", gameSetInfo.get("SPLIT_TWO_SIDE_AMOUNT_"));
		jsonObject.put("splitNumberAmount", gameSetInfo.get("SPLIT_NUMBER_AMOUNT_"));

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

