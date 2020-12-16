package com.ibs.plan.module.mq.listener;

import com.common.enums.CodeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.core.IbsCommMq;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.mq.controller.MergeBetController;
import com.ibs.plan.module.mq.controller.PlanBetController;
import org.doming.core.tools.StringTool;
/**
 * 投注详情消息监听器
 *
 * @Author: Dongming
 * @Date: 2020-05-09 15:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetItemListener extends IbsCommMq {
	private String period;
	private String existHmId;
	private String betMode;
	private GameUtil.Code gameCode;
	private HandicapUtil.Code handicapCode;
	@Override public String execute(String message) throws Exception {
		log.info(getLog("投注详情，接收的消息是：" + message));
		if (valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("投注详情，处理的结果是：".concat(bean.toString())));
			return null;
		}
		try {
			IbspClientHmService clientHmService = new IbspClientHmService();
			String handicapMemberId = clientHmService.findHmIdByExistId(existHmId);
			if (StringTool.isEmpty(handicapMemberId)) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
				bean.putSysEnum(CodeEnum.CODE_404);
				log.debug(getLog("会员信息消息监听器，处理完成，处理的结果是：".concat(bean.toString())));
				return null;
			}
			switch (method) {
				case PLAN_BET:
					//  方案投注
					bean = new PlanBetController(handicapMemberId,handicapCode,gameCode,period,betMode).execute(msgObj);
					break;
				case MERGE_BET:
					//  合并投注
					bean =  new MergeBetController(handicapMemberId,handicapCode,gameCode,period,betMode).execute(msgObj);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error("错误的会员登录方法接收:{}", method.name());
					return null;

			}
			log.debug(getLog("校验信息，处理完成，处理的结果是：".concat(bean.toString())));
		} catch (Exception e) {
			log.error(getLog("校验信息,处理错误:".concat(e.getMessage())));
			throw e;
		}

		return null;
	}

	@Override protected boolean valiParameter(String message) {
		if (super.valiParameter(message)) {
			return true;
		}
		existHmId = StringTool.getString(msgObj, "EXIST_HM_ID_", "");
		Object handicapCodeObj = msgObj.get("HANDICAP_CODE_");
		Object gameCodeObj = msgObj.get("GAME_CODE_");
		betMode =StringTool.getString(msgObj, "BET_MODE_", "");
		period = StringTool.getString(msgObj, "PERIOD_", "");
		if (StringTool.isEmpty(gameCodeObj,handicapCodeObj, existHmId,period, betMode)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		if(StringTool.isContains(period,"-")){
			period=period.substring(4);
		}
		gameCode = GameUtil.Code.valueOf(gameCodeObj.toString());
		handicapCode = HandicapUtil.Code.valueOf(handicapCodeObj.toString());
		return false;
	}
}
