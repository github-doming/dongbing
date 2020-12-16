package com.ibm.old.v1.cloud.core.thread.plan;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.entity.IbmCloudReceiptBetT;
import com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.service.IbmCloudReceiptBetTService;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.service.IbmExecResultTService;
import com.ibm.old.v1.common.doming.enums.*;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import com.rabbitmq.client.Connection;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 发送投注详情
 * @Author: Dongming
 * @Date: 2019-07-26 17:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendBetItemThread extends BaseCommThread {

	private String planId;
	private String gameId;
	private Object period;
	private String handicapId;
	private IbmGameEnum game;
	private PlanTool.Code planCode;

	public SendBetItemThread(String gameId, String planId, String handicapId, Object period, IbmGameEnum game,
			PlanTool.Code planCode) {
		this.planId = planId;
		this.gameId = gameId;
		this.handicapId = handicapId;
		this.period = period;
		this.game = game;
		this.planCode = planCode;
	}
	@Override public String execute(String ignore) throws Exception {
		long codingStart = System.currentTimeMillis(), codingEnd;
		IbmExecResultTService execResultService = new IbmExecResultTService();
		IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
		try {
			String result = null;
			for (int i = 0; i < 13; i++) {
				result = execResultService.findExecResult(planId, gameId, handicapId, period);
				if (StringTool.isEmpty(result)) {
					Thread.sleep(1000);
					CurrentTransaction.transactionCommit();
					continue;
				}
				if (StringTool.isEmpty(result)) {
					log.debug("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中【" + planCode.getName()
							+ "】方案发送错误【" + result + "】");
					return null;
				}
				switch (IbmTypeEnum.valueOf(result)) {
					case CODING:
					case TURN:
						Thread.sleep(1000);
						CurrentTransaction.transactionCommit();
						continue;
					case MERGE:
						break;
					case SEND:
						log.debug("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中【" + planCode.getName()
								+ "】方案已发送过数据");
						return null;
					default:
						log.debug("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中【" + planCode.getName()
								+ "】方案发送错误【" + result + "】");
						return null;
				}
				break;
			}
			if (StringTool.isEmpty(result)) {
				log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中【" + planCode.getName()
						+ "】方案发送未完成");
				return null;
			}

			//存储表
			String tableName = HandicapGameTool.getTableName(game.name(), handicap.name());
			if (tableName == null) {
				log.error("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中存储表不存在");
				return null;
			}

			// 获取没有合并的数据
			Map<String, JSONObject> sendInfo = new IbmExecBetItemTService().findSendInfo(planId, period, tableName);
			if (ContainerTool.isEmpty(sendInfo)) {
				log.info("未发现【" + handicapId + "】盘口【" + gameId + "】游戏中【" + planId + "】方案有需要发送的数据");
				return null;
			}

			send(sendInfo, gameId, period);

			return null;

		} finally {
			execResultService.updateExecResult(IbmTypeEnum.SEND, IbmTypeEnum.MERGE, planId, handicapId, period,this.getClass().getName());
			codingEnd = System.currentTimeMillis();
			log.debug("执行时长=" + (codingEnd - codingStart) + "ms,盘口【" + handicap.getName() + "】中【" + game.getName()
					+ "】游戏的【" + planCode.getName() + "】方案已发送结束");
		}
	}

	/**
	 * 发送数据
	 *
	 * @param sendInfo 待发送的数据
	 * @param gameId   游戏id
	 * @param period   期数
	 */
	public static void send(Map<String, JSONObject> sendInfo, String gameId, Object period)
			throws IOException, TimeoutException, SQLException {
		// 客户端存在盘口会员
		Map<Object, Object> clientExistHmIdMap = new IbmCloudClientHmTService().mapClientExistHmId(sendInfo.keySet());

		IbmCloudReceiptBetTService receiptBetService = new IbmCloudReceiptBetTService();
		IbmCloudReceiptBetT receiptBet;
		try (Connection connection = RabbitMqIbmUtil.connection()) {
			for (Map.Entry<String, JSONObject> entry : sendInfo.entrySet()) {
				String handicapMemberId = entry.getKey();
				JSONObject betInfo = entry.getValue();
				if (!clientExistHmIdMap.containsKey(handicapMemberId)) {
					log.error("盘口会员【" + handicapMemberId + "】，尚未开启客户端，无法发送投注信息");
					continue;
				}
				try {
					// 添加消息回执
					receiptBet = new IbmCloudReceiptBetT();
					receiptBet.setClientExistHmId(clientExistHmIdMap.get(handicapMemberId).toString());
					receiptBet.setExecBetItemIds(betInfo.getString("EXEC_BET_ITEM_IDS_"));
					receiptBet.setReceiptState(IbmTypeEnum.SEND.name());
					receiptBet.setCreateTime(new Date());
					receiptBet.setCreateTimeLong(receiptBet.getCreateTime().getTime());
					receiptBet.setState(IbmStateEnum.OPEN.name());
					String messageReceiptBetId = receiptBetService.save(receiptBet);

					JSONObject data = new JSONObject();
					data.put("clientExistHmId", clientExistHmIdMap.get(handicapMemberId));
					data.put("period", period);
					data.put("gameId", gameId);
					data.put("betInfo", betInfo.getJSONArray("BET_INFO"));
					data.put("messageReceiptBetId", messageReceiptBetId);
					data.put("method", IbmMethodEnum.AUTO_BET.name());
					RabbitMqIbmUtil.sendExchange4Bet(connection, handicapMemberId, data.toString());
				} catch (Exception e) {
					log.error("盘口会员【" + handicapMemberId + "】,发送投注信息失败,失败原因为", e);
				}
			}
		}
	}
}
