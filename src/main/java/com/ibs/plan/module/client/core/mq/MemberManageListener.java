package com.ibs.plan.module.client.core.mq;

import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.core.IbsCommMq;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.core.executor.LoginExecutor;
import com.ibs.plan.module.client.core.executor.LogoutExecutor;
import com.ibs.plan.module.client.core.executor.VailLoginExecutor;
import org.doming.core.tools.StringTool;
/**
 * 客户端会员管理监听器
 *
 * @Author: null
 * @Date: 2020-05-09 15:17
 * @Version: v1.0
 */
public class MemberManageListener extends IbsCommMq {
	public MemberManageListener() {
		super.database = false;
	}

	private String eventId;
	@Override public String execute(String message) throws Exception {
		log.info("会员登录，接收的消息是：{}", message);
		if (valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			bean.setRequestType(IbsStateEnum.FAIL.name());
			log.error("会员登录，返回的结果是：{}", bean.toString());
			//数据验证错误一般为程序错误再怎么补正也没办法处理，需要开发人员介入 - 故接收端接收即可 accept
			return RabbitMqTool.sendReceipt(bean.toString(), "accept");
		}
		try {
			bean.setRequestType(IbsStateEnum.PROCESS.name());
			RabbitMqTool.sendReceipt(bean.toString(), "accept");
			ClientMqExecutor execute;
			switch (method) {
				case VALI_LOGIN:
					//验证登录会员
					execute = new VailLoginExecutor();
					break;
				case LOGIN:
					//登录会员
					execute = new LoginExecutor();
					break;
				case LOGOUT:
					//登出会员
					execute = new LogoutExecutor();
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error("错误的会员登录方法接收:{}", method.name());
					return null;
			}
			bean = execute.execute(msgObj);
		} finally {
			// 消息处理完成 - 登录结果需要程序后续操作，故为 handle
			bean.setToken(eventId);
			bean.setCommand(method.name());
			bean.setRequestType(IbsStateEnum.SUCCESS.name());
			log.info("会员登录,处理完成，返回的结果是：{}", bean.toString());
			RabbitMqTool.sendReceipt(bean.toString(), "handle");
		}
		return null;
	}

	@Override protected boolean valiParameter(String message) {
		if (super.valiParameter(message)) {
			return true;
		}
		eventId = msgObj.getString("EVENT_ID_");
		if (StringTool.isEmpty(eventId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		bean.setToken(eventId);
		bean.setCommand(method.name());
		return false;

	}

}
