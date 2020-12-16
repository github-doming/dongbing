package com.ibs.plan.module.mq.listener;

import com.common.enums.CodeEnum;
import com.ibs.plan.common.core.IbsCommMq;
import com.ibs.plan.module.mq.controller.ClientHeartbeatController;
import com.ibs.plan.module.mq.controller.ClientMonitorController;
/**
 * 客户端信息消息监听器
 *
 * @Author: Dongming
 * @Date: 2020-05-09 15:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InfoClientListener extends IbsCommMq {


	@Override public String execute(String message) throws Exception {
		log.info(getLog("校验信息，接收的消息是：" + message));
		if (valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("校验信息，处理的结果是：".concat(bean.toString())));
			return null;
		}
		try {
			switch (method) {
				case CLIENT_HEARTBEAT:
					//  客户端心跳
					bean = new ClientHeartbeatController().execute(msgObj);
					break;
				case CLIENT_MONITOR:
					bean = new ClientMonitorController().execute(msgObj);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error("错误的会员登录方法接收:{}", method.name());
					return null;

			}
			log.debug(getLog("校验信息，处理完成，处理的结果是：".concat(bean.toString())));
		} catch (Exception e) {
			log.error(getLog("校验信息,处理错误:"+e.getMessage()));
			throw e;
		}


		return null;
	}
}
