package com.ibm.follow.servlet.cloud.core.mq.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.controller.mq.FollowAgentController;
import com.ibm.follow.servlet.cloud.core.controller.mq.FollowController;
import com.ibm.follow.servlet.cloud.core.controller.mq.FollowMemberController;
import com.ibm.follow.servlet.cloud.core.controller.mq.FollowVrController;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 投注信息
 * @Author: Dongming
 * @Date: 2019-08-26 16:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private IbmMethodEnum method;
	private JSONObject msgObj;
	private GameUtil.Code gameCode;
	private Object period;
	private String betContent;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("会员投注信息，接收的消息是：" + message));
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("投注信息，处理的结果是：".concat(bean.toJsonString())));
			return null;
		}
		try {
			switch (method) {
				case MANUAL_BET:
					//手动投注
					bean.success("手动投注");
					System.out.println("手动投注"+ msgObj);
					break;
				case FOLLOW_BET:
					//跟随投注
					bean = 	new FollowController(gameCode, period, betContent).execute(msgObj);
					break;
				case AGENT_BET_INFO:
					bean = new FollowAgentController(gameCode, period, betContent).execute(msgObj);
					break;
				case FOLLOW_VR_MEMBER_BET:
					//跟随虚拟会员投注
					bean = 	new FollowVrController(gameCode, period, betContent).execute(msgObj);
					break;
				case VR_MEMBER_BET_INFO:
					//虚拟会员投注信息
					bean = 	new FollowMemberController(gameCode, period, betContent).execute(msgObj);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的投注方法接收".concat(method.name())));
					return null;
			}
			log.debug(getLog("投注信息，处理完成，处理的结果是：".concat(bean.toJsonString())));
		} catch (Exception e) {
			log.error(getLog("投注信息,处理错误:"+e.getMessage()));
			throw e;
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
		Object gameCodeObj = msgObj.get("GAME_CODE_");
		period = msgObj.getOrDefault("PERIOD_", "");
		betContent = msgObj.getOrDefault("BET_CONTENT_", "").toString();
		if (StringTool.isEmpty(methodObj, gameCodeObj, period, betContent)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		method = IbmMethodEnum.valueOf(methodObj.toString());
		gameCode = GameUtil.Code.valueOf(gameCodeObj.toString());
		return true;
	}
}
