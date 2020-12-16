package com.ibs.plan.module.client.core.mq;

import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.core.IbsCommMq;
import com.ibs.plan.module.client.core.executor.ManualBetExecutor;
import org.doming.core.tools.StringTool;
/**
 * 客户端会员信息监听器
 *
 * @Author: Dongming
 * @Date: 2020-05-09 15:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberInfoListener extends IbsCommMq {
	private String existHmId;
	@Override public String execute(String message) throws Exception {
		log.info(getLog("客户端会员信息，接收的消息是：" + message));
		//消息预处理
		if (valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			bean.setRequestType(IbsStateEnum.FAIL.name());
			log.error("客户端会员信息，返回的结果是：{}", bean.toString());
		}
		try {
			//消息接收-process
			switch (method) {
				case MANUAL_BET:
					//手动投注
					bean = new ManualBetExecutor(existHmId).execute(msgObj);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error("错误的客户端会员信息方法接收:{}", method.name());
			}
			log.info(getLog("客户端会员信息,处理完成，返回的结果是：".concat(bean.toString())));
		} catch (Exception e) {
			log.error(getLog("客户端会员信息,处理错误:".concat(e.getMessage())));
			throw e;
		}
		return null;
	}
	@Override protected boolean valiParameter(String message) {
		if (super.valiParameter(message)) {
			return true;
		}
		existHmId = msgObj.getOrDefault("EXIST_HM_ID_", "").toString();
		if (StringTool.isEmpty(existHmId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		bean.setCommand(method.name());
		return false;
	}
}
