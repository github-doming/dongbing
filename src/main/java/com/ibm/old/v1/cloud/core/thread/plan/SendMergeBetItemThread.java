package com.ibm.old.v1.cloud.core.thread.plan;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.service.IbmExecResultTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;
/**
 * @Description: 发送合并投注详情
 * @Author: Dongming
 * @Date: 2019-01-08 13:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendMergeBetItemThread extends BaseCommThread {
	private String gameId;
	private Object period;
	private String handicapId;
	private IbmGameEnum game;

	public SendMergeBetItemThread(String gameId, String handicapId, Object period, IbmGameEnum game) {
		this.gameId = gameId;
		this.handicapId = handicapId;
		this.period = period;
		this.game = game;
	}
	@Override public String execute(String ignore) throws Exception {
		IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
		long codingStart = System.currentTimeMillis(), codingEnd;
		// 已经编码过
		IbmExecResultTService execResultService = new IbmExecResultTService();
		try {
			String result = null;
			for (int i = 0; i < 13; i++) {
				result = execResultService.findExecResult(gameId, handicapId, period);
				if (StringTool.isEmpty(result)) {
					Thread.sleep(1000);
					CurrentTransaction.transactionCommit();
					continue;
				}
				if (StringTool.isEmpty(result)) {
					log.debug("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏发送合并数据错误【" + result + "】");
					return null;
				}
				switch (IbmTypeEnum.valueOf(result)) {
					case CODING:
					case TURN:
					case MERGE:
						Thread.sleep(1000);
						CurrentTransaction.transactionCommit();
						continue;
					case SEND:
						break;
					default:
						log.debug("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏发送合并数据错误【" + result + "】");
						return null;
				}
				break;
			}
			if (StringTool.isEmpty(result)) {
				log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏发送合并数据未完成");
				return null;
			}

			String tableName = HandicapGameTool.getTableNameById(gameId, handicapId);
			if (tableName == null) {
				log.error("盘口【" + handicapId + "】游戏【" + gameId + "】不存在");
				return null;
			}

			// 已经合并完毕的数据
			Map<String, JSONObject> turnInfo = new IbmExecBetItemTService().listSendInfoByMerge(period, tableName);
			if (ContainerTool.isEmpty(turnInfo)) {
				codingEnd = System.currentTimeMillis();
				log.info("执行时长=" + (codingEnd - codingStart) + "ms,未发现【" + handicapId + "】盘口【" + gameId + "】游戏中"
						+ "没有需要发送的合并项数据");
				return null;
			}
			SendBetItemThread.send(turnInfo, gameId, period);
			return null;

		} finally {
			codingEnd = System.currentTimeMillis();
			log.debug("执行时长=" + (codingEnd - codingStart) + "ms,盘口【" + handicap.getName() + "】中【" + game.getName()
					+ "】游戏合并数据发送结束");
		}
	}
}
