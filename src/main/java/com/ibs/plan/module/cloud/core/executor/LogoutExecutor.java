package com.ibs.plan.module.cloud.core.executor;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_event_logout.service.IbspEventLogoutService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import org.doming.core.tools.StringTool;

/**
 * @Description: 登出执行器
 * @Author: null
 * @Date: 2020-06-02 10:56
 * @Version: v1.0
 */
public class LogoutExecutor implements CloudMqExecutor {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj, IbsStateEnum requestType) throws Exception {
		String eventId = msgObj.getString("token");

		IbspEventLogoutService logoutService=new IbspEventLogoutService();

		boolean flag;
		switch (requestType){
			case SUCCESS:
				String handicapMemberId=logoutService.findEventHmId(eventId);
				//参数错误
				if (StringTool.isEmpty(handicapMemberId) || StringTool.contains(msgObj.getString("codeSys"),
						CodeEnum.CODE_500.getCode(), CodeEnum.CODE_401.getCode())) {
					bean.putEnum(CodeEnum.IBS_404_DATA);
					bean.putSysEnum(CodeEnum.CODE_404);
					return bean;
				}
				new IbspClientHmService().updateByHmId(handicapMemberId);
				new IbspHandicapMemberService().updateOperating(handicapMemberId);
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
		flag=logoutService.updateResultByState(eventId,msgObj,requestType);
		if(!flag){
			bean.putEnum(CodeEnum.IBS_404_TYPE);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		bean.success();
		return bean;
	}
}
