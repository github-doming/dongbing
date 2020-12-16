package com.ibm.old.v1.cloud.ibm_message.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.core.controller.mq.CloseClientController;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.service.IbmCloudReceiptBetTService;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * @Description: 投注数据消息回执
 * @Author: zjj
 * @Date: 2019-04-09 15:20
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class MessageReceiptBetDataAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findDateMap();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String receiptState = BeanThreadLocal.find(dataMap.get("receiptState"), "");
		String gameCode = BeanThreadLocal.find(dataMap.get("gameCode"), "");
		String handicapCode = BeanThreadLocal.find(dataMap.get("handicapCode"), "");
		String period = BeanThreadLocal.find(dataMap.get("period"), "");
		if (ContainerTool.isEmpty(dataMap.get("resultData")) || StringTool.isEmpty(receiptState, period)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		String tableName = HandicapGameTool.getTableName(gameCode, handicapCode);
		if (tableName == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		//更新盘口会员余额信息
		if (!ContainerTool.isEmpty(dataMap.get("userInfo"))) {
			IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
			JSONObject userInfo = JSONObject.fromObject(dataMap.get("userInfo"));
			String existHmId = userInfo.remove("existHmId").toString();
			//判断用户状态是否为停押状态，是的话将用户登出
			if (userInfo.containsKey("USER_STATE") && IbmHcCodeEnum.IBM_403_USER_BAN_BET.getCode()
					.equals(userInfo.get("USER_STATE"))) {
				IbmCloudClientHmTService cloudClientHmTService = new IbmCloudClientHmTService();
				String handicapMemberId = cloudClientHmTService.findHandicapMemberId(existHmId);
				CloudExecutor executor = new CloseClientController();
				executor.execute(handicapMemberId);
			} else {
				handicapMemberTService.updateMemberInfoByExistHmId(existHmId, userInfo);
			}
		}
		try {
			IbmStateEnum receipt = IbmStateEnum.getReceiptState(receiptState);
			if (receipt == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_NOT_STATE);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return returnJson(bean);
			}
			switch (receipt) {
				case FINISH:
					messageReceiptFinish();
					break;
				case ERROR:
					messageReceiptError(tableName);
					break;
				case SUCCESS:
					messageReceiptSuccess(period, tableName);
					break;
				default:
					bean.putEnum(IbmCodeEnum.IBM_401_NOT_STATE);
					bean.putSysEnum(IbmCodeEnum.CODE_401);
					return returnJson(bean);
			}

			bean.success();
		} catch (Exception e) {
			log.error("更新消息回执结果数据失败");
			throw e;
		}
		return this.returnJson(bean);
	}
	/**
	 * 消息执行成功
	 *
	 * @param period    期数
	 * @param tableName 表名
	 */
	private void messageReceiptSuccess(String period, String tableName) throws SQLException {
		String successData = dataMap.get("resultData").toString();
		IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
		execBetItemTService.updateExecState(successData.split(","), period, IbmStateEnum.SUCCESS.name(), tableName);

	}

	/**
	 * 消息执行异常
	 *
	 * @param tableName 表名
	 */
	private void messageReceiptError(String tableName) throws SQLException {
		Map<String, List<String>> errorBetInfo = (Map<String, List<String>>) dataMap.get("resultData");
		if (ContainerTool.notEmpty(errorBetInfo)) {
			IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
			execBetItemTService.updateExecFailDesc(errorBetInfo, tableName);
		}
	}

	/**
	 * 消息执行完成
	 */
	private void messageReceiptFinish() throws Exception {
		Map<String, List<Map<String, Object>>> finishData = (Map<String, List<Map<String, Object>>>) dataMap
				.get("resultData");

		IbmCloudReceiptBetTService receiptBetTService = new IbmCloudReceiptBetTService();
		for (Map.Entry<String, List<Map<String, Object>>> finishEntry : finishData.entrySet()) {
			String cloudReceiptBetId = finishEntry.getKey();
			List<Map<String, Object>> execBetItems = finishEntry.getValue();
			//	更新消息回执结果
			receiptBetTService.updateResult(cloudReceiptBetId, execBetItems, this.getClass().getName());

		}
	}
}
