package com.ibm.follow.connector.pc.customer.login;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 盘口会员登录
 * @Author: null
 * @Date: 2019-11-26 15:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/logout/member", method = HttpConfig.Method.GET) public class MemberLogoutAction
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
			return super.returnJson(bean);
		}
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
		try {
			IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
			IbmHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
			if (handicapMember == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			IbmHmInfoService hmInfoService = new IbmHmInfoService();
			String loginState = hmInfoService.findLoginState(handicapMemberId);
			if (StringTool.isEmpty(loginState) || !IbmStateEnum.LOGIN.name().equals(loginState)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//用户登出清理数据
			new LogoutHmController().execute(handicapMemberId);
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("登出失败")+e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}
}
