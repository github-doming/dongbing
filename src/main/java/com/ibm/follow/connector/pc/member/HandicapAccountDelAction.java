package com.ibm.follow.connector.pc.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * 删除账户
 * @Author: Dongming
 * @Date: 2020-04-21 10:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/deleteAccount", method = HttpConfig.Method.POST) public class HandicapAccountDelAction
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		if (StringTool.isEmpty(handicapMemberId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
			String handicapId = handicapMemberService.findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			//会员登出状态
			logoutHappening(handicapMemberId);

			String desc = this.getClass().getSimpleName().concat(",").concat(appUser.getNickname()).concat(",删除会员信息:")
					.concat(handicapMemberId);

			//删除盘口会员信息
			handicapMemberService.delHmInfo(handicapMemberId,desc);
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("获取盘口代理的盘口信息错误")+e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

	private void logoutHappening(String handicapMemberId) throws Exception {
		String loginState = new IbmHmInfoService().findLoginState(handicapMemberId);
		if (StringTool.notEmpty(loginState) && IbmStateEnum.LOGIN.name().equals(loginState)) {
			new LogoutHmController().execute(handicapMemberId);
		}
	}
}
