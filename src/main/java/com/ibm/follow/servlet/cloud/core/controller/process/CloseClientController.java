package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.service.IbmEventClientCloseService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 关闭客户端控制器
 * @Author: zjj
 * @Date: 2019-09-05 10:02
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class CloseClientController implements CloudExecutor {

	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		IbmEventClientCloseService closeService=new IbmEventClientCloseService();
		//事件id
		String eventId = msgObj.getString("token");
		switch (requestType){
			case SUCCESS:
				Map<String,Object> eventInfo=closeService.findEventInfo(eventId);
				//参数错误
				if (ContainerTool.isEmpty(eventInfo) || StringTool.contains(msgObj.getString("codeSys"),
						CodeEnum.CODE_500.getCode(), CodeEnum.CODE_401.getCode())) {
					bean.putEnum(CodeEnum.IBS_404_DATA);
					bean.putSysEnum(CodeEnum.CODE_404);
					return bean;
				}
				IbmTypeEnum customerType=IbmTypeEnum.valueOf(eventInfo.get("CUSTOMER_TYPE_").toString());
				JSONObject eventContent=JSONObject.parseObject(eventInfo.get("EVENT_CONTENT_").toString());
				switch (customerType) {
					case MEMBER:
						IbmClientHmService clientHmService=new IbmClientHmService();
						String handicapMemberId=clientHmService.findHmId(eventContent.getString("EXIST_HM_ID_"));

						//修改已存在盘口会员信息
						clientHmService.updateByHmId(handicapMemberId, new Date(), "盘口会员登出");
						new IbmHandicapMemberService().updateOperating(handicapMemberId);
						break;
					case AGENT:
						IbmClientHaService clientHaService=new IbmClientHaService();
						String handicapAgentId=clientHaService.findHaId(eventContent.getString("EXIST_HA_ID_"));
						//修改已存在盘口代理信息
						clientHaService.updateByHaId(handicapAgentId, new Date(), "盘口代理登出");
						new IbmHandicapAgentService().updateOperating(handicapAgentId);
						break;
					default:
						bean.putEnum(CodeEnum.IBS_404_DATA);
						bean.putSysEnum(CodeEnum.CODE_404);
						return bean;
				}
				requestType= IbmStateEnum.FINISH;
				break;
			case PROCESS:
			case FAIL:
				break;
			default:
				bean.putEnum(CodeEnum.IBS_404_METHOD);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
		}

		if(!closeService.updateResultByState(eventId,msgObj,requestType)){
			bean.putEnum(CodeEnum.IBS_404_TYPE);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		bean.success();
		return bean;


	}

}
