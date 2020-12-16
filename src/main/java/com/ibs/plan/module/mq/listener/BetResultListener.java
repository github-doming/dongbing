package com.ibs.plan.module.mq.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;
import com.ibs.plan.module.mq.controller.ErrorBetResultController;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投注结果消息监听器
 *
 * @Author: Dongming
 * @Date: 2020-05-09 15:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetResultListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private JSONObject msgObj;
	private IbsStateEnum requestType;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("投注结果，接收的消息是：" + message));
		if (valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("投注详情，处理的结果是：".concat(bean.toString())));
			return null;
		}
		try {
			IbspHmBetItemService betItemService=new IbspHmBetItemService();
			JSONArray clientBetIds;
			switch (requestType) {
				case PROCESS:
					JSONObject data = msgObj.getJSONObject("data");
					new IbspHmBetItemService()
							.updateResult(data.getString("BET_ITEM_ID_"), data.getString("CLIENT_BET_ID_"), requestType,
									new Date(), "");
					bean.success();
					break;
				case ERROR:
					String msg = msgObj.getOrDefault("msg", "").toString();
					bean = new ErrorBetResultController(msg).execute(msgObj);
					break;
				case SUCCESS:
					clientBetIds=msgObj.getJSONArray("data");
					betItemService.updateResult(clientBetIds,requestType,new Date(),"");
					break;
				case AGAIN:
					updateAgainResult(IbsStateEnum.SUCCESS,betItemService);
					break;
				case FAIL:
					updateAgainResult(requestType,betItemService);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog("错误的投注方法接收".concat(requestType.name())));
					return null;
			}
			log.debug(getLog("投注结果，处理完成，处理的结果是：".concat(bean.toString())));
		} catch (Exception e) {
			log.error(getLog("投注结果,处理错误:".concat(e.getMessage())));
			throw e;
		}

		return null;
	}

	private void updateAgainResult(IbsStateEnum requestType, IbspHmBetItemService betItemService) throws SQLException {
		JSONArray clientBetIds=msgObj.getJSONArray("data");
		if(ContainerTool.isEmpty(clientBetIds)){
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return ;
		}
		betItemService.updateResult(clientBetIds,requestType,new Date(),"");
		Map<String, Object> hmBetItems = betItemService.findBetInfos(clientBetIds);
		if(ContainerTool.isEmpty(hmBetItems)||ContainerTool.isEmpty(msgObj.get("msg"))){
			return ;
		}
		List<Map<String, Object>> errorInfos = (List<Map<String, Object>>) msgObj.get("msg");
		Map<String, String> errorMap = new HashMap<>(hmBetItems.size());
		for (Map<String, Object> errorInfo : errorInfos) {
			String msg = errorInfo.get("ERROR_BET_INFO_").toString();
			String[] failBetContents = errorInfo.get("FAIL_BET_CONTENT_").toString().split(",");
			for(String failBetContent:failBetContents){
				for (Map.Entry<String, Object> entry : hmBetItems.entrySet()) {
					String betContent = entry.getValue().toString();
					if (StringTool.isContains(betContent, failBetContent)) {
						if (errorMap.containsKey(entry.getKey())) {
							errorMap.put(entry.getKey(), errorMap.get(entry.getKey()).concat(",").concat(failBetContent));
						}else{
							errorMap.put(entry.getKey(), msg.concat("-").concat(failBetContent));
						}
						break;
					}
				}
			}
		}
		betItemService.updateErrorInfo(errorMap);
	}

	@Override protected boolean valiParameter(String message) {
		if (StringTool.isEmpty(message)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		msgObj = JSON.parseObject(message);
		if (ContainerTool.isEmpty(msgObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		Object requestTypeObj = msgObj.get("requestType");
		if (StringTool.isEmpty(requestTypeObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		requestType = IbsStateEnum.valueOf(requestTypeObj.toString());
		return false;
	}
}
