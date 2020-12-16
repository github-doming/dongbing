package com.ibm.follow.servlet.cloud.core.mq.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.CheckAgentController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

/**
 * @Description: 代理信息
 * @Author: Dongming
 * @Date: 2019-08-26 16:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AgentListener extends BaseCommMq {
    private JsonResultBeanPlus bean = new JsonResultBeanPlus();

    private JSONObject msgObj;
    private IbmMethodEnum method;
	private String existHaId;

    @Override public String execute(String message) throws Exception {
        log.info(getLog("代理信息，接收的消息是：" + message));
        if (!valiParameter(message)) {
            bean.putSysEnum(CodeEnum.CODE_401);
            log.error(getLog("代理信息，处理的结果是：".concat(bean.toJsonString())));
            return null;
        }
        try {
            switch (method) {
                case CUSTOMER_INFO:
                    // 代理心跳
                    CheckAgentController agentController=new CheckAgentController();
                    bean=agentController.execute(msgObj);
                    break;
				case LOGOUT:
					logout();
					break;
                default:
                    bean.putEnum(CodeEnum.IBS_404_METHOD);
                    bean.putSysEnum(CodeEnum.CODE_404);
                    log.error(getLog( "错误的代理信息方法接收".concat(method.name())));
                    return null;

            }
            log.debug(getLog("代理信息，处理完成，处理的结果是：".concat(bean.toJsonString())));
        } catch (Exception e) {
            log.error(getLog("代理信息,处理错误:".concat(e.getMessage())));
            throw e;
        }
        return null;
    }

	private void logout() throws Exception {
    	/*
			1.记录错误信息
			2.写入登出事件
		 */
		String code = msgObj.getString("code");
		String message = msgObj.getString("message");

		String handicapAgentId = new IbmClientHaService().findHaId(existHaId);
		new IbmHaInfoService().updateAgentInfo(handicapAgentId,code,message);

		new LogoutHaController().execute(handicapAgentId);

	}

	@Override protected boolean valiParameter(String message) {
        if (StringTool.isEmpty(message)) {
            bean.putEnum(CodeEnum.IBS_401_MESSAGE);
            return false;
        }
        msgObj = JSON.parseObject(message);
        if (ContainerTool.isEmpty(msgObj)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            return false;
        }

        String methodStr = msgObj.getString("method");
        if (StringTool.isEmpty(methodStr)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            return false;
        }
        method = IbmMethodEnum.valueOf(methodStr);

        if (!msgObj.containsKey("EXIST_HA_ID_")) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            return false;
        }
		existHaId = msgObj.getString("EXIST_HA_ID_");
        return true;
    }
}
