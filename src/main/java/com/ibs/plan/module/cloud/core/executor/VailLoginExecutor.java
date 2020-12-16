package com.ibs.plan.module.cloud.core.executor;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.core.controller.process.LoginHmController;
import com.ibs.plan.module.cloud.ibsp_event_login_vali.service.IbspEventLoginValiService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

/**
 * @Description: 验证登录执行器
 * @Author: null
 * @Date: 2020-05-28 17:06
 * @Version: v1.0
 */
public class VailLoginExecutor implements CloudMqExecutor {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj, IbsStateEnum requestType) throws Exception {
		String eventId = msgObj.getString("token");
		boolean flag;
		switch (requestType) {
			case SUCCESS:
				//登录结果处理
				msgObj = process(eventId, msgObj);
				requestType=IbsStateEnum.FINISH;
				break;
			case PROCESS:
			case FAIL:
				break;
			default:
				bean.putEnum(CodeEnum.IBS_404_METHOD);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
		}
		flag=new IbspEventLoginValiService().updateResultByState(eventId,msgObj,requestType);
		if(!flag){
			bean.putEnum(CodeEnum.IBS_404_TYPE);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		bean.success();
		return bean;
	}
	private JSONObject process(String eventId, JSONObject msgObj) throws Exception {
		JSONObject resultJson = new JSONObject();
		IbspEventLoginValiService loginValiService=new IbspEventLoginValiService();
		String handicapMemberId = loginValiService.findEventHmId(eventId);
		//参数错误
		if (StringTool.isEmpty(handicapMemberId) || StringTool.contains(msgObj.getString("codeSys"),
				CodeEnum.CODE_500.getCode(), CodeEnum.CODE_401.getCode())) {
			resultJson.put("code", "error");
			resultJson.put("msg", "用户登录参数错误");
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return resultJson;
		}
		//登录成功
		if (msgObj.getBoolean("success") && ContainerTool.notEmpty(msgObj.get("data"))) {
			new LoginHmController(handicapMemberId).execute(msgObj.get("data"));
			bean.success();
		} else {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
		}
		resultJson.put("code", msgObj.getString("codeSys"));
		resultJson.put("msg", msgObj.getString("msg"));
		return resultJson;
	}
}
