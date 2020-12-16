package com.ibs.plan.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口迁移
 * @Author: null
 * @Date: 2020-03-24 16:54
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/handicapMigrate", method = HttpConfig.Method.POST)
public class HandicapMigrateAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		//盘口code
		String handicapCode = dataMap.getOrDefault("handicapCode", "").toString();
		//发送端客户机active
		String sendClientCode = dataMap.getOrDefault("sendClientCode", "").toString();
		//接收端客户机passive
		String receiveClientCode = dataMap.getOrDefault("receiveClientCode", "").toString();


		String handicapId = new IbspHandicapService().findId(handicapCode);
		if (StringTool.isEmpty(handicapId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}

		try {
			bean = ClientReplaceAction.checkClient(sendClientCode, receiveClientCode);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.success(null);

			//盘口会员ids或者盘口代理ids
			//清除旧客户机信息
			List<String> customerIds = clearOldInfo(handicapCode, sendClientCode);

			//发消息到发送端
			JSONObject content = new JSONObject();
			content.put("METHOD_", IbsMethodEnum.CLIENT_MIGRATE.name());
			content.put("handicapCode", handicapCode);
			content.put("methodType", IbsMethodEnum.SEND.name());
			String result = RabbitMqTool.sendClientConfig(content.toString(), sendClientCode, "info");
			if (StringTool.isEmpty(result)) {
				bean.putEnum(CodeEnum.IBS_401_MESSAGE);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(CodeEnum.IBS_403_MQ);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			//重启事务
			transactionRestart();

			//发消息到接收端
			if (ContainerTool.notEmpty(customerIds)) {
				getCustomerInfo(customerIds, content);
			}

			content.put("methodType", IbsMethodEnum.RECEIVE.name());
			result = RabbitMqTool.sendClientConfig(content.toString(), receiveClientCode, "info");
			if (StringTool.isEmpty(result)) {
				bean.putEnum(CodeEnum.IBS_401_MESSAGE);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(CodeEnum.IBS_403_MQ);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}


			bean.success();
		} catch (Exception e) {
			log.error("盘口迁移错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 清除旧的信息
	 *
	 * @param handicapCode   盘口code
	 * @param sendClientCode 发送端客户机编码
	 */
	private List<String> clearOldInfo(String handicapCode,
									  String sendClientCode) throws Exception {
		//获取需要迁移的客户
		IbspClientHmService clientHmService = new IbspClientHmService();
		List<String> customerIds = clientHmService.findHmIds(sendClientCode, handicapCode);
		if (ContainerTool.notEmpty(customerIds)) {
			clientHmService.updateByHmIds(customerIds);
		}
		return customerIds;
	}

	/**
	 * 获取客户信息
	 *
	 * @param customerIds 盘口会员ids
	 */
	private void getCustomerInfo(List<String> customerIds, JSONObject content) throws SQLException {
		List<Map<String, Object>> memberInfos = new IbspHandicapMemberService().findMemberInfos(customerIds);
		//获取会员游戏设置信息
		List<Map<String, Object>> memberGameInfos = new IbspHmGameSetService().findByHmIds(customerIds);
		//获取会员设置信息
		List<Map<String, Object>> hmSetInfos = new IbspHmSetService().findByHmIds(customerIds);
		content.put("memberGameInfos", memberGameInfos);
		content.put("hmSetInfos", hmSetInfos);
		content.put("customerInfos", memberInfos);
	}
}
