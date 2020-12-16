package com.ibm.old.v1.cloud.core.controller.mq;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.entity.IbmCloudReceiptBetT;
import com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.service.IbmCloudReceiptBetTService;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 手动投注
 * @Author: wck
 * @Date: 2019-3-27 17:12:42
 * @Email: 810160078@qq.com
 * @Version: v1.0
 */
public class ManualBetController implements CloudExecutor {

	private String gameCode;
	private Object period;
	private String handicapMemberId;
	private List<Map<String, Object>> betItemList;

	public ManualBetController(String gameCode, Object period, String handicapMemberId, List<Map<String, Object>> betItemList) {
		this.gameCode = gameCode;
		this.period = period;
		this.handicapMemberId = handicapMemberId;
		this.betItemList = betItemList;
	}

	@Override public JsonResultBeanPlus execute(String... ignore) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//获取游戏id
		IbmGameTService gameTService = new IbmGameTService();
		String gameId = gameTService.findId(gameCode);
		//获取盘口id
		IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
		String handicapId = handicapMemberTService.findHandicapId(handicapMemberId);

		if (StringTool.isEmpty(gameId,handicapId)) {
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
			if (!bean.isSuccess()){
				return bean;
			}
			bean.setSuccess(false);
		}
		existHmId = clientHmTService.findExistHmId(handicapMemberId);
		IbmCloudReceiptBetTService receiptBetTService = new IbmCloudReceiptBetTService();
		JSONArray betInfo = new JSONArray();
		StringBuilder execBetItemIds = new StringBuilder();

		JSONObject jObj = new JSONObject();
		for (Map<String, Object> execBetItem:betItemList){
            jObj.put("IBM_EXEC_BET_ITEM_ID_",execBetItem.get("IBM_EXEC_BET_ITEM_ID_"));
            jObj.put("BET_CONTENT_",execBetItem.get("BET_CONTENT_"));
            jObj.put("FUND_T_",NumberTool.longValueT(execBetItem.get("FUND_T_")));
            betInfo.add(jObj);
			execBetItemIds.append(",").append(execBetItem.get("IBM_EXEC_BET_ITEM_ID_"));
			jObj.clear();
		}

		//新增消息回执
		String receiptId = newMessageReceipt(execBetItemIds.toString(),existHmId,receiptBetTService);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("clientExistHmId", existHmId);
		jsonObject.put("messageReceiptBetId", receiptId);
		jsonObject.put("period",period);
		jsonObject.put("gameId",gameId);
		jsonObject.put("betInfo",betInfo);
		jsonObject.put("method", IbmMethodEnum.MANUAL_BET.name());

		// 发送用户mq消息到客户端
		String result = RabbitMqIbmUtil.sendExchange4Bet(handicapMemberId, jsonObject.toString());
		if (!Boolean.parseBoolean(result)) {
			// 发送mq消息失败
			bean.putEnum(IbmCodeEnum.IBM_404_MQ_SEND_FAIL);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}

		bean.success();
		return bean;
	}
	/**
	 * 新建一个消息回执
	 *
	 * @param execBetItemId 投注项id
	 * @param existHmId  存在客户端盘口会员id
	 * @param receiptBetTService  消息回执服务类
	 * @return 消息回执id
	 */
	private String newMessageReceipt(String execBetItemId, String existHmId,
			IbmCloudReceiptBetTService receiptBetTService) throws Exception {
		Date nowTime = new Date();
		IbmCloudReceiptBetT receiptBetT = new IbmCloudReceiptBetT();
		receiptBetT.setClientExistHmId(existHmId);
		receiptBetT.setExecBetItemIds(execBetItemId);
		receiptBetT.setReceiptState(IbmStateEnum.SEND.name());
		receiptBetT.setCreateTime(nowTime);
		receiptBetT.setCreateTimeLong(nowTime.getTime());
		receiptBetT.setUpdateTime(nowTime);
		receiptBetT.setUpdateTimeLong(nowTime.getTime());
		receiptBetT.setState(IbmStateEnum.OPEN.name());
		return receiptBetTService.save(receiptBetT);
	}

}

