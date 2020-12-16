package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 登录控制器
 * @Author: null
 * @Date: 2019-09-05 10:08
 * @Version: v1.0
 */
public class LoginController implements CloudExecutor {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {
		//事件id
		String eventId = msgObj.getString("token");
		switch (requestType) {
			case SUCCESS:
				//登录结果处理
				msgObj = process(eventId, msgObj);
				requestType= IbmStateEnum.FINISH;
				break;
			case PROCESS:
			case FAIL:
				bean.success();
				break;
			default:
				bean.putEnum(CodeEnum.IBS_404_METHOD);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
		}

		if(!new IbmEventLoginService().updateResultByState(eventId,msgObj,requestType)){
			bean.putEnum(CodeEnum.IBS_404_TYPE);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		return bean;
	}

	private JSONObject process(String eventId, JSONObject msgObj) throws Exception {
		JSONObject resultJson = new JSONObject();
		Map<String, Object> eventMap = new IbmEventLoginService().findEventInfo(eventId);
		//参数错误
		if (ContainerTool.isEmpty(eventMap) || StringTool.contains(msgObj.getString("codeSys"),
				CodeEnum.CODE_500.getCode(), CodeEnum.CODE_401.getCode())) {
			resultJson.put("code", "error");
			resultJson.put("msg", "用户验证登录参数错误");
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return resultJson;
		}
		IbmTypeEnum customerType=IbmTypeEnum.valueOf(eventMap.get("CUSTOMER_TYPE_").toString());

		//登录成功
		if (msgObj.getBoolean("success") && ContainerTool.notEmpty(msgObj.get("data"))) {
			// 绑定客户端,添加登录信息
			switch (customerType) {
				case MEMBER:
					String handicapMemberId = eventMap.get("CUSTOMER_ID_").toString();
					//添加初始化盘口会员信息
					LoginHmController hmController = new LoginHmController(handicapMemberId);
					hmController.execute(msgObj.get("data"));
					break;
				case AGENT:
					String handicapAgentId = eventMap.get("CUSTOMER_ID_").toString();

					//添加初始化盘口代理信息
					LoginHaController haController = new LoginHaController(handicapAgentId);
					haController.execute(msgObj.get("data"));
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_DATA);
					bean.putSysEnum(CodeEnum.CODE_404);
					return null;
			}
		}
		bean.success();
		resultJson.put("code", msgObj.getString("codeSys"));
		resultJson.put("msg", msgObj.getString("msg"));
		return resultJson;
	}
}
