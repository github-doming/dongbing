package com.ibm.follow.servlet.client.core.mq.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.core.servlet.boot.IbmModuleListener;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.core.controller.config.*;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 客户端信息消息
 * @Author: Dongming
 * @Date: 2019-08-26 16:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InfoListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private JSONObject msgObj;
	private IbmMethodEnum method;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("客户端信息，接收的消息是：" + message));
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
            bean.setToken(IbmModuleListener.servletCode());
            bean.setCommand(method.name());
            bean.setRequestType(IbmStateEnum.FAIL.name());
            log.error(getLog("客户端信息，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendConfigReceipt(bean.toJsonString(), "client");
			return null;
		}
		try {
			//消息接收不需要process
			//信息处理
            ClientExecutor execute;
            switch (method){
                case CAPACITY_SET:
                    execute=new CapacitySetController();
                    break;
                case HANDICAP_CAPACITY_SET:
                    execute=new HandicapCapacitySetController();
                    break;
                case HANDICAP_CAPACITY_DEL:
                    execute=new HandicapCapacityDelController();
                    break;
                case CLIENT_DEL:
                    execute=new ClientDelController();
                    break;
                case SEAL_TIME:
                    execute=new SealTimeSetController();
                    break;
                case CLIENT_MIGRATE:
                    execute=new HandicapMigrateController();
                    break;
                case REPLACE:
                    execute=new ClientReplaceController();
                    break;
                case HEARTBEAT:
                    execute=new StopHeartbeatController();
                    break;
				case CLEAR:
					execute=new ClearController();
					break;
                default:
                    bean.putEnum(CodeEnum.IBS_404_METHOD);
                    bean.putSysEnum(CodeEnum.CODE_404);
                    log.error(getLog( "错误的客户配置方法接收".concat(method.name())));
                    return null;
            }
            bean=execute.execute(msgObj);
		} catch (Exception e) {
            bean.putEnum(CodeEnum.IBS_500);
            bean.putSysEnum(CodeEnum.CODE_500);
            log.error(getLog("客户端信息,处理错误:".concat(e.getMessage())));
            throw e;
		} finally {
			// 消息处理完成
            bean.setToken(IbmModuleListener.servletCode());
            bean.setCommand(method.name());
            bean.setRequestType(IbmStateEnum.SUCCESS.name());
            log.info(getLog("客户端信息,处理完成，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendConfigReceipt(bean.toJsonString(), "client");
		}
		return null;
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
        Object methodObj = msgObj.get("METHOD_");

        if (StringTool.isEmpty(methodObj)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            return false;
        }
        method = IbmMethodEnum.valueOf(methodObj.toString());

        return true;

	}
}
