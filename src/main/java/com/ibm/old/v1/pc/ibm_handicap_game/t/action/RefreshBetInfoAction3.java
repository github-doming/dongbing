package com.ibm.old.v1.pc.ibm_handicap_game.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynCommAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.pc.ibm_exec_bet_item.t.service.IbmPcExecBetItemTService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 刷新投注信息
 * @Author: zjj
 * @Date: 2019-05-07 09:44
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
@Deprecated
@ActionMapping(name = "refreshBetInfo", value = "/ibm/pc/ibm_handicap_game/refreshBetInfo3.dm") public class RefreshBetInfoAction3
		extends BaseAsynCommAction {

	@Override public String run() throws Exception {
		long nextTime = System.currentTimeMillis();
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		String gameCode = BeanThreadLocal.find(dataMap.get("GAME_CODE_"), "");
		String checkTimeStr = BeanThreadLocal.find(dataMap.get("checkTime"), "");
		if (StringTool.isEmpty(handicapMemberId, gameCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
			String handicapId = handicapMemberTService.findHandicapId(handicapMemberId);
			if (StringTool.isEmpty(handicapId)) {
				jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
				jrb.putSysEnum(IbmCodeEnum.CODE_401);
				return this.returnJson(jrb);
			}
			IbmHmSetTService hmSetTService = new IbmHmSetTService();
			String betMerger = hmSetTService.findBetMerger(handicapMemberId);
			String gameId = GameTool.findId(gameCode);
			// 判断游戏是否存在
			if (StringTool.isEmpty(gameId, betMerger)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			String tableName = HandicapGameTool.getTableNameById(gameId, handicapId);
			if (StringTool.isEmpty(tableName)) {
				log.error("盘口【" + handicapId + "】游戏【" + gameCode + "】不存在");
				jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}

			//检查时间
			long checkTime;
			if (StringTool.isEmpty(checkTimeStr)) {
				checkTime = PeriodTool.getLotteryDrawTime(gameCode);
			} else {
				checkTime = NumberTool.getLong(checkTimeStr) - 1000;
			}
			Map<String, Object> data = new HashMap<>(3);
			IbmPcExecBetItemTService execBetItemTService = new IbmPcExecBetItemTService();

			//新增加内容
			List<Map<String, Object>> newBetInfoList = execBetItemTService
					.listNewBetInfo(handicapMemberId, checkTime, tableName);
			//转换格式
			if (ContainerTool.notEmpty(newBetInfoList)) {
				listNewFormat(newBetInfoList, betMerger);
				data.put("newBetInfoList", newBetInfoList);
			}

			//历史更新数据
			List<Map<String, Object>> oldBetInfoList = execBetItemTService
					.listDrawInfo(handicapMemberId, checkTime, tableName);
			if (ContainerTool.notEmpty(oldBetInfoList)) {
				listFormat(oldBetInfoList, betMerger);
				data.put("oldBetInfoList", oldBetInfoList);
			}

			data.put("newTime", nextTime);
			jrb.setData(data);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			jrb.setSuccess(false);
		}
		return this.returnJson(jrb);
	}

	/**
	 * 格式化数据
	 *
	 * @param betInfoList 投注信息列表
	 * @param betMerger   合并模式
	 */
	private void listNewFormat(List<Map<String, Object>> betInfoList, String betMerger) {
		for (Map<String, Object> betInfo : betInfoList) {
			Map<String, String> betContentMap = new HashMap<>(5);
			String[] betItems = betInfo.get("BET_CONTENT_").toString().split("#");
			for (String betItem : betItems) {
				String[] bet = betItem.split("\\|");
				if (betContentMap.containsKey(bet[0])) {
					betContentMap.put(bet[0], betContentMap.get(bet[0]) + "," + bet[1]);
				} else {
					betContentMap.put(bet[0], bet[1]);
				}
			}
			StringBuilder betContent = new StringBuilder();
			for (Map.Entry<String, String> entry : betContentMap.entrySet()) {
				if (betContent.length() > 0) {
					betContent.append("#").append(entry.getKey()).append("-").append(entry.getValue());
				} else {
					betContent.append(entry.getKey()).append("-").append(entry.getValue());
				}
			}
			betInfo.put("BET_CONTENT_", betContent.toString());
			betInfo.put("FUND_", NumberTool.doubleT(betInfo.get("FUND_T_").toString()));
			betInfo.put("PROFIT_", NumberTool.doubleT(betInfo.get("PROFIT_T_").toString()));
			betInfo.put("FUNDS_INDEX_", "第" + StringTool.addOne(betInfo.get("FUNDS_INDEX_").toString()) + "次");

			String execState = betInfo.get("EXEC_STATE_").toString();
			String betMode = betInfo.get("BET_MODE_").toString();
			if (IbmTypeEnum.VIRTUAL.name().equals(betMode)) {
				betInfo.put("BET_MODE_", IbmTypeEnum.VIRTUAL.getMsgCn());
				if (IbmStateEnum.SUCCESS.name().equals(execState)) {
					betInfo.put("EXEC_STATE_", IbmStateEnum.SUCCESS.getMsgCn());
				} else {
					betInfo.put("EXEC_STATE_", "等待开奖");
				}
			} else {
				betInfo.put("BET_MODE_", IbmTypeEnum.REAL.getMsgCn());
				if (StringTool.notEmpty(betInfo.get("DESC_"))) {
					betInfo.put("EXEC_STATE_", betInfo.get("DESC_"));
				} else if (IbmStateEnum.SUCCESS.name().equals(execState)) {
					betInfo.put("EXEC_STATE_", IbmStateEnum.SUCCESS.getMsgCn());
				} else if (IbmStateEnum.OPEN.name().equals(betMerger)) {
					int betType = NumberTool.getInteger(betInfo.get("BET_TYPE_"));
					if (PlanTool.BET_TYPE_CODE - betType == 0) {
						betInfo.put("EXEC_STATE_", "等待合并");
					} else if (PlanTool.BET_TYPE_MERGE - betType == 0) {
						betInfo.put("EXEC_STATE_", "已合并");
					} else if (IbmStateEnum.READY.name().equals(execState)) {
						betInfo.put("EXEC_STATE_", "等待投注");
					}
				} else if (IbmStateEnum.READY.name().equals(execState)) {
					betInfo.put("EXEC_STATE_", "等待投注");
				}
			}
			betInfo.remove("FUND_T_");
			betInfo.remove("PROFIT_T_");
			betInfo.remove("DESC_");
			betInfo.remove("BET_TYPE_");
			betInfo.remove("ROW_NUM");
		}
	}

	private void listFormat(List<Map<String, Object>> betInfoList, String betMerger) {
		for (Map<String, Object> betInfo : betInfoList) {
			String execState = betInfo.get("EXEC_STATE_").toString();
			if (StringTool.notEmpty(betInfo.get("DESC_"))) {
				betInfo.put("EXEC_STATE_", betInfo.get("DESC_"));
			} else if (IbmStateEnum.FINISH.name().equals(execState)) {
				betInfo.put("EXEC_STATE_", IbmStateEnum.FINISH.getMsgCn());
			} else if (IbmStateEnum.SUCCESS.name().equals(execState)) {
				betInfo.put("EXEC_STATE_", IbmStateEnum.SUCCESS.getMsgCn());
			} else if (IbmStateEnum.OPEN.name().equals(betMerger)) {
				int betType = NumberTool.getInteger(betInfo.get("BET_TYPE_"));
				if (PlanTool.BET_TYPE_CODE - betType == 0) {
					betInfo.put("EXEC_STATE_", "等待合并");
				} else if (PlanTool.BET_TYPE_MERGE - betType == 0) {
					betInfo.put("EXEC_STATE_", "已合并");
				} else if (IbmStateEnum.READY.name().equals(execState)) {
					betInfo.put("EXEC_STATE_", "等待投注");
				}
			} else if (IbmStateEnum.READY.name().equals(execState)) {
				betInfo.put("EXEC_STATE_", "等待投注");
			}
			betInfo.remove("DESC_");
			betInfo.remove("BET_TYPE_");
			betInfo.remove("ROW_NUM");
		}
	}
}
