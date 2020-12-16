package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.core.controller.tool.ProfitPeriodTool;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 手动投注结果控制器
 * @Author: null
 * @Date: 2019-09-26 09:51
 * @Version: v1.0
 */
public class ManualBetResultController implements CloudExecutor {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private String handicapMemberId;
	private String gameCode;
	private String period;
	private String betInfoCode;

	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {
		JSONObject data = JSONObject.parseObject(msgObj.getString("token"));
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		IbmClientHmService clientHmService = new IbmClientHmService();
        Map<String,Object> hmInfo =clientHmService.findHmInfo(data.getString("EXIST_HM_ID_"));
		if (StringTool.isEmpty(hmInfo)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
        handicapMemberId=hmInfo.get("HANDICAP_MEMBER_ID_").toString();
		gameCode = data.getString("GAME_CODE_");
		period = data.getString("PERIOD_");
		betInfoCode = data.getString("BET_INFO_CODE_");
		IbmHmBetItemService hmBetItemService = new IbmHmBetItemService();
		switch (requestType) {
			case PROCESS:
				//将状态SEND改为PROCESS
				updateResult(requestType, IbmStateEnum.SEND.name(), hmBetItemService);
				break;
			case AGAIN:
				updateAgainResult(msgObj, IbmStateEnum.SUCCESS, hmBetItemService);
				break;
			case SUCCESS:
                Map<String, Object> hmBetItems =updateResult(requestType, IbmStateEnum.PROCESS.name(), hmBetItemService);
                //统计当期投注信息
                ProfitPeriodTool.saveHmProfitPeriod(hmBetItems, GameUtil.id(gameCode),hmInfo,period,hmBetItemService);
				break;
			case FAIL:
				updateAgainResult(msgObj, requestType, hmBetItemService);
				break;
			case ERROR:
				//直接投注错误的,封盘,余额不足,投注项不符合限额，将状态改为fail
				updateErrorResult(msgObj,data.getString("FOLLOW_BET_ID_"), hmBetItemService);
				break;
			default:
				bean.putEnum(CodeEnum.IBS_404_METHOD);
				bean.putSysEnum(CodeEnum.CODE_404);
				break;
		}
		return bean;
	}

    /**
	 * 有失败项投注信息处理
	 *
	 * @param msgObj           消息
	 * @param hmBetItemService 服务类
	 */
	private void updateAgainResult(JSONObject msgObj, IbmStateEnum requestType, IbmHmBetItemService hmBetItemService)
			throws SQLException {
		if (ContainerTool.isEmpty(msgObj.get("errorInfo"))) {
			return;
		}
		Map<String, Object> hmBetItems = updateResult(requestType, IbmStateEnum.PROCESS.name(), hmBetItemService);
		if (ContainerTool.isEmpty(hmBetItems)) {
			return;
		}
		List<Map<String, Object>> errorInfos = (List<Map<String, Object>>) msgObj.get("errorInfo");

		Map<String, String> errorMap = new HashMap<>(hmBetItems.size());
		for (Map<String, Object> errorInfo : errorInfos) {
			String msg = errorInfo.get("ERROR_BET_INFO_").toString();

			String[] betItems = errorInfo.get("FAIL_BET_INFO_").toString().split(",");
			for (String betItem : betItems) {
				for (Map.Entry<String, Object> entry : hmBetItems.entrySet()) {
					String betContent = entry.getValue().toString();
					if (StringTool.isContains(betContent, betItem)) {
						if (errorMap.containsKey(entry.getKey())) {
							errorMap.put(entry.getKey(), errorMap.get(entry.getKey()).concat(",").concat(betItem));
						} else {
							errorMap.put(entry.getKey(), msg.concat("-").concat(betItem));
						}
						break;
					}
				}
			}
		}
		hmBetItemService.updateErrorInfo(errorMap);
	}

	private void updateErrorResult(JSONObject msgObj,String followBetIds, IbmHmBetItemService hmBetItemService) throws SQLException {
        Map<String, Object> hmBetItems = hmBetItemService.findByFollowId(followBetIds, IbmStateEnum.PROCESS.name());
		if (ContainerTool.isEmpty(hmBetItems)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            bean.putSysEnum(CodeEnum.CODE_401);
            return;
		}
        hmBetItemService.updateProcessInfo(hmBetItems, IbmStateEnum.FAIL);
		String msg = msgObj.getString("msg");
		JSONArray errorBetInfo = msgObj.getJSONArray("ERROR_BET_INFO_");
		Map<String, String> errorMap = new HashMap<>(hmBetItems.size());
		if (ContainerTool.isEmpty(errorBetInfo)) {
			for (String hmBetItemId : hmBetItems.keySet()) {
				errorMap.put(hmBetItemId, msg);
			}
		} else {
			for (Object betInfo : errorBetInfo) {
				for (String hmBetItemId : hmBetItems.keySet()) {
					if (StringTool.isContains(hmBetItems.get(hmBetItemId).toString(), betInfo.toString())) {
						String[] infos=betInfo.toString().split("#");
						for (String info:infos){
							String[] bet = info.split("\\|");
							if (errorMap.containsKey(hmBetItemId)) {
								errorMap.put(hmBetItemId,
										errorMap.get(hmBetItemId).concat(",").concat(bet[0]).concat("|").concat(bet[1]));
							} else {
								errorMap.put(hmBetItemId, msg.concat("-").concat(bet[0]).concat("|").concat(bet[1]));
							}
						}
						break;
					}
				}
			}
		}
		hmBetItemService.updateErrorInfo(errorMap);
	}
	/**
	 * 修改执行结果
	 *
	 * @param requestType 执行类型
	 * @param execState   执行状态
	 */
	private Map<String, Object> updateResult(IbmStateEnum requestType, String execState,
			IbmHmBetItemService hmBetItemService) throws SQLException {
		Map<String, Object> hmBetItems = hmBetItemService
				.findBetInfo(handicapMemberId, gameCode, period, betInfoCode, execState);
		if (ContainerTool.isEmpty(hmBetItems)) {
			hmBetItems = hmBetItemService
					.findBetInfo(handicapMemberId, gameCode, period, betInfoCode);
			if (ContainerTool.isEmpty(hmBetItems)) {
				bean.putEnum(CodeEnum.IBS_401_DATA);
				bean.putSysEnum(CodeEnum.CODE_401);
				return null;
			}
		}
		hmBetItemService.updateProcessInfo(hmBetItems, requestType);
		bean.success();
		return hmBetItems;
	}
}
