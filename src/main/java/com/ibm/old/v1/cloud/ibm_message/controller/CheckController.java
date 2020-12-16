package com.ibm.old.v1.cloud.ibm_message.controller;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.core.controller.mq.CloseClientController;
import com.ibm.old.v1.cloud.core.controller.mq.LoginClientController;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 定时校验控制器
 * @Author: zjj
 * @Date: 2019-04-13 10:48
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class CheckController implements CloudExecutor {
	protected Logger log = LogManager.getLogger(CheckController.class);

	@Override public JsonResultBeanPlus execute(String... inVar) throws Exception {
		JsonResultBeanPlus bean;

		String handicapMemberId = inVar[0];
		Object resultBean = inVar[1];
		JSONObject resultJson = JSONObject.fromObject(resultBean);

		if (!resultJson.getBoolean("success")) {
			log.info("校验失败，返回结果为：" + resultJson);
			return null;
		}
		JSONObject result = JSONObject.fromObject(resultJson.get("data"));
		IbmStateEnum state = IbmStateEnum.getLogState(result.get("state"));
		if (state == null) {
			log.info("校验失败，返回结果为：" + resultJson);
			return null;
		}
		//添加盘口盈亏信息
		if (StringTool.notEmpty(result.get("profit"))) {
			betProfit(handicapMemberId, NumberTool.longValueT(result.get("profit")));
		}
		IbmHandicapMemberTService handicapMemberService = new IbmHandicapMemberTService();
		handicapMemberService.updateMemberInfo(handicapMemberId, result.get("memberInfo"));

		CloudExecutor executor;
		switch (state) {
			case TRACE:
				log.trace("盘口会员" + handicapMemberId + "校验结果正常");
				break;
			case INFO:
				if (StringTool.contains(result.getString("code"), IbmHcCodeEnum.IBM_403_USER_STATE.getCode(),
						IbmHcCodeEnum.IBM_403_TIMING_CHECKOUT.getCode(), IbmHcCodeEnum.IBM_403_CHECK_CODE.getCode())) {
					log.info("盘口会员" + handicapMemberId + "校验结果异常，检验code=" + result.get("code"));
					break;
				}
				executor = new LoginClientController();
				bean = executor.execute(handicapMemberId);
				log.info("盘口会员" + handicapMemberId + "校验结果异常，并进行重新登录，登录结果：" + bean.toJsonString());
				break;
			case ERROR:
				JSONObject jsonObject = new JSONObject();
				executor = new CloseClientController();
				bean = executor.execute(handicapMemberId);
				//设置错误信息
				if (IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN.getCode().equals(result.get("code"))) {
					jsonObject.put("MESSAGE", IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN.getMsgCn());
					jsonObject.put("CODE", IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN.getCode());
				} else if (IbmHcCodeEnum.IBM_403_USER_ACCOUNT.getCode().equals(result.get("code"))) {
					jsonObject.put("MESSAGE", IbmHcCodeEnum.IBM_403_USER_ACCOUNT.getMsgCn());
					jsonObject.put("CODE", IbmHcCodeEnum.IBM_403_USER_ACCOUNT.getCode());
				} else {
					jsonObject.put("MESSAGE", IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN.getMsgCn());
					jsonObject.put("CODE", IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN.getCode());
				}
				log.info("盘口会员" + handicapMemberId + "异常登出，登出结果：" + bean.toJsonString());
				//更新盘口会员信息

				handicapMemberService.updateLogout(handicapMemberId, jsonObject);
				new IbmHandicapUserTService().updateLogout(handicapMemberId, new Date());
				break;
			default:
				return null;
		}
		return null;
	}
	/**
	 * 设置盘口盈亏信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param profit           盈亏信息
	 * @throws Exception
	 */
	private void betProfit(String handicapMemberId, Long profit) throws Exception {
		IbmHmSetTService hmSetTService = new IbmHmSetTService();
		IbmHmSetT hmSetT = hmSetTService.findByHandicapMemberId(handicapMemberId);

		IbmProfitTService profitTService = new IbmProfitTService();
		String profitId = profitTService.findIdByHmId(handicapMemberId);

		if (StringTool.isEmpty(profitId)) {
			profitTService.save(hmSetT, handicapMemberId, profit);
		} else {
			profitTService.updateHandicapProfit(profitId, profit, this.getClass().getName());
		}
	}

}
