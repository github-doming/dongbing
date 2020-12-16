package com.ibm.old.v1.cloud.core.controller.mq;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.service.IbmCloudClientTService;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.service.IbmHandicapItemTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;
/**
 * @Description: 检验登录
 * @Author: zjj
 * @Date: 2019-05-16 16:58
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class CheckLoginController implements CloudExecutor {

	@Override public JsonResultBeanPlus execute(String handicapMemberId) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		IbmHandicapMemberTService hmService = new IbmHandicapMemberTService();
		IbmHandicapMemberT handicapMemberT = hmService.find(handicapMemberId);
		if (handicapMemberT == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmHandicapItemTService ibmHandicapItemTService = new IbmHandicapItemTService();
		Map<String, Object> handicapInfo = ibmHandicapItemTService
				.findHandicapInfo(handicapMemberT.getHandicapItemId());
		if(ContainerTool.isEmpty(handicapInfo)){
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmCloudClientTService clientTService = new IbmCloudClientTService();
		String machineCode = clientTService.findReadyClient(handicapMemberT.getHandicapCode());
		// 判断是否有可用客户端
		if (StringTool.isEmpty(machineCode)) {
			bean.putEnum(IbmCodeEnum.IBM_404_CLIENT_EXIST);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("handicapCode",handicapMemberT.getHandicapCode());
		jsonObject.put("memberAccount", handicapMemberT.getMemberAccount());
		jsonObject.put("memberPassword", handicapMemberT.getMemberPassword());
		jsonObject.put("handicapUrl", handicapInfo.get("HANDICAP_URL_"));
		jsonObject.put("handicapCaptcha", handicapInfo.get("HANDICAP_CAPTCHA_"));
		jsonObject.put("method", IbmMethodEnum.CHECK_LOGIN.name());

		String result =RabbitMqIbmUtil.sendExchange4Manage(machineCode,jsonObject.toString());

		// 处理返回结果
		if ( StringTool.isEmpty(result)) {
			bean.putEnum(IbmCodeEnum.IBM_404_MQ_CONNECTION_ERROR);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		JSONObject resultJson = JSONObject.fromObject(result);
		if (resultJson.getBoolean("success")) {
			bean.success();
		} else {
			bean.setCode(resultJson.get("code").toString());
			bean.setCodeSys(resultJson.get("codeSys").toString());
			bean.setMsg(resultJson.get("msg").toString());
			bean.setMessageSys(resultJson.get("messageSys").toString());
		}
		return bean;
	}
}
