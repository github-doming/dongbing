package com.ibs.plan.connector.pc.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 删除盘口账号信息
 * @Author: null
 * @Date: 2020-05-26 17:42
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/deleteAccount", method = HttpConfig.Method.POST)
public class HandicapAccountDelAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		if(StringTool.isEmpty(handicapMemberId)){
			return bean.put401Data();
		}
		try{
			IbspHandicapMemberService handicapMemberService=new IbspHandicapMemberService();
			String handicapId = new IbspHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				return bean.put404HandicapMember();
			}

			//会员登出状态
			logoutHappening(handicapMemberId);

			String desc = this.getClass().getSimpleName().concat(",").concat(appUserId).concat(",删除会员信息:")
					.concat(handicapMemberId);

			//删除盘口会员信息
			handicapMemberService.delHmInfo(handicapMemberId,new Date(),desc);
			return bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取盘口代理的盘口信息错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
			return bean;
		}
	}

	private void logoutHappening(String handicapMemberId) throws Exception {
		String loginState=new IbspHmInfoService().findLoginState(handicapMemberId);
		if (StringTool.notEmpty(loginState) && IbsStateEnum.LOGIN.name().equals(loginState)) {
			new LogoutHmController().execute(handicapMemberId,new Date());
		}

	}
}
