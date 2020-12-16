package com.cloud.recharge.connector.recharge;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.card_recharge.entity.CardRecharge;
import com.cloud.recharge.card_recharge.service.CardRechargeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * 修改充值卡信息
 *
 * @Author: Dongming
 * @Date: 2020-06-19 18:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/edit", method = HttpConfig.Method.POST)
public class CardRechargeEditAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String cardId = StringTool.getString(dataMap.get("cardId"), "");
		String cardState = StringTool.getString(dataMap.get("cardState"), "");
		String state = StringTool.getString(dataMap, "state", "");
		String desc = StringTool.getString(dataMap, "desc", "");
		if (StringTool.isEmpty(cardId)) {
			return bean.put401Data();
		}
		if (!CardTool.isCreateState(cardState)) {
			return bean.put401Data();
		}
		try {
			CardRechargeService rechargeService = new CardRechargeService();
			CardRecharge cardRecharge = rechargeService.find(cardId);
			if (cardRecharge == null) {
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if (CardTool.State.ACTIVATE.name().equals(cardRecharge.getCardRechargeState())) {
				bean.putEnum(CodeEnum.CLOUD_403_ACTIVATE);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			//更新数据
			Date nowTime = new Date();
			rechargeService.update(cardId, cardState, state, desc, nowTime);

			saveLog(nowTime, cardRecharge.getCardTreeName(), cardRecharge.getCardRechargeState());
			bean.success();
		} catch (Exception e) {
			log.error("修改充值卡信息出错",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(Date nowTime,String cardName,String state) throws Exception {
		String format ="修改充值卡{%s},卡状态{%s}";
		CardOperateLog log = new CardOperateLog();
		log.setUserId(user.getAppUserId());
		log.setUserName(user.getUserName());
		log.setUserPath(user.getUserPath());
		log.setOpertType("RECHARGE");
		log.setIp(findServletIp());
		log.setOpertContent(String.format(format,cardName,state));
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTimeLong(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}
}
