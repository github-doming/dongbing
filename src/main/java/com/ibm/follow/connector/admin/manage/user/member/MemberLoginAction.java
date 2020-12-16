package com.ibm.follow.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.module.event_new.LoginThread;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 会员登录
 * @Author: null
 * @Date: 2020-03-19 14:19
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/member/login")
public class MemberLoginAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口会员id
		String handicapMemberId = StringTool.getString(dataMap.get("handicapMemberId"), "");

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
			if (StringTool.notEmpty(loginState) && IbmStateEnum.LOGIN.name().equals(loginState)) {
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			//此账号是否已经被登录
			if (hmInfoService.isLogin(handicapMember.getHandicapId(), handicapMember.getMemberAccount())) {
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			if (IbmStateEnum.LOGIN.name().equals(handicapMember.getOperating())) {
				String eventId = new IbmEventLoginService().isExist(handicapMemberId, IbmTypeEnum.MEMBER);
				if (StringTool.notEmpty(eventId)) {
					bean.success();
					return bean.toJsonString();
				}
			}
			saveEventLogin(handicapMember);

			bean.success();
		} catch (Exception e) {
			log.error("登录盘口会员失败", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}

	private void saveEventLogin(IbmHandicapMember handicapMember) throws Exception {
		//  马上登录
		IbmHandicapItemService handicapItemService = new IbmHandicapItemService();
		Map<String, Object> itemInfo = handicapItemService.findInfo(handicapMember.getHandicapItemId());
		handicapItemService.updateUseTime(handicapMember.getHandicapItemId());
		//写入登录事件
		JSONObject content = new JSONObject();
		content.put("HANDICAP_MEMBER_ID_", handicapMember.getIbmHandicapMemberId());
		content.put("MEMBER_ACCOUNT_", handicapMember.getMemberAccount());
		content.put("MEMBER_PASSWORD_", handicapMember.getMemberPassword());
		content.putAll(itemInfo);
		String eventId = EventThreadDefine.saveMemberLoginEvent(content, new Date(), this.getClass().getName().concat("，登录"),
				new IbmEventLoginService(), handicapMember.getIbmHandicapMemberId());
		ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventId, IbmTypeEnum.MEMBER));
	}

}
