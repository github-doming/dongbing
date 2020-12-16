package com.ibs.plan.module.client.core.mq;

import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.core.IbsCommMq;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.core.executor.set.*;
import org.doming.core.tools.StringTool;

/**
 * 客户端会员设置监听器
 *
 * @Author: Dongming
 * @Date: 2020-05-09 15:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberSetListener extends IbsCommMq {
	private String eventId;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("客户端会员设置，接收的消息是：" + message));
		//消息预处理
		if (valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			bean.setRequestType(IbsStateEnum.FAIL.name());
			log.error("客户配置，返回的结果是：{}", bean.toString());
			//数据验证错误一般为程序错误再怎么补正也没办法处理，需要开发人员介入 - 故接收端接收即可 accept
			return RabbitMqTool.sendReceipt(bean.toString(), "accept");
		}
		try {
			bean.setRequestType(IbsStateEnum.PROCESS.name());
			RabbitMqTool.sendReceipt(bean.toString(), "accept");
			ClientMqExecutor execute;
			switch (method) {
				case SET_HANDICAP:
					execute = new SetHandicapExecutor();
					break;
				case SET_GAME_INFO:
					execute = new SetGameInfoExecutor();
					break;
				case SET_GAME:
					execute = new SetGameExecutor();
					break;
				case SET_BET_STATE:
					execute = new SetBetStateExecutor();
					break;
				case SET_INVERSE:
					execute = new SetInverseExecutor();
					break;
				case SET_INCREASE:
					execute = new SetIncreaseExecutor();
					break;
				case SET_PLAN_INFO:
					execute = new SetPlanInfoExecutor();
					break;
				case SET_PLAN:
					execute = new SetPlanExecutor();
					break;
				case SET_PLAN_STATE:
					execute=new SetPlanStateExecutor();
					break;
				case SET_PLAN_BATCH_STATE:
					execute=new SetPlanBatchExecutor();
					break;
				case CLEAR_GAME_PLAN:
					execute=new ClearGamePlanExecutor();
					break;
				case DEL_GAME_PLAN:
					execute=new DelGamePlanExecutor();
					break;
				case SET_PLAN_RESET:
					execute = new SetPlanResetExecutor();
					break;
				case SET_BET_MODE:
					execute=new SetBetModeExecutor();
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error("错误的会员登录方法接收:{}", method.name());
					return null;
			}
			bean = execute.execute(msgObj);
		} finally {
			// 消息处理完成
			bean.setToken(eventId);
			bean.setCommand(method.name());
			bean.setRequestType(IbsStateEnum.SUCCESS.name());
			log.info("客户端会员设置,处理完成，返回的结果是：{}", bean.toString());
			RabbitMqTool.sendReceipt(bean.toString(), "accept");
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
