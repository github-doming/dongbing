package com.ibs.plan.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 删除会员信息
 * @Author: null
 * @Date: 2020-03-19 14:45
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/member/delete")
public class MemberDeleteAction extends CommAdminAction {
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
		Date nowTim = new Date();
		try {
			IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();
			IbspHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
			if (handicapMember == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//会员登出状态
			logoutHappening(handicapMemberId, nowTim);
			String desc = this.getClass().getSimpleName().concat(",").concat(appUserId).concat(",删除会员信息:")
					.concat(handicapMemberId);
			//删除盘口会员信息
			handicapMemberService.delHmInfo(handicapMemberId, nowTim, desc);

			bean.success();
		} catch (Exception e) {
			log.error("删除盘口会员失败", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}

	private void logoutHappening(String handicapMemberId, Date nowTime) throws Exception {
		String loginState = new IbspHmInfoService().findLoginState(handicapMemberId);
		if (StringTool.notEmpty(loginState) && IbsStateEnum.LOGIN.name().equals(loginState)) {
			new LogoutHmController().execute(handicapMemberId, nowTime);
		}
	}
}
