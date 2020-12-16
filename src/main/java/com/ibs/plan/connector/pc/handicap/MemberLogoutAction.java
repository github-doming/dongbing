package com.ibs.plan.connector.pc.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 会员登出
 * @Author: null
 * @Date: 2020-06-02 10:32
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/handicap/logout", method = HttpConfig.Method.POST)
public class MemberLogoutAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();

		if (StringTool.isEmpty(handicapMemberId)) {
			return bean.put401Data();
		}

		if (super.denyTime()) {
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		try {
			//用户是否登录
			if(!IbsStateEnum.LOGIN.equal(new IbspHmInfoService().findLoginState(handicapMemberId))){
				bean.putEnum(CodeEnum.IBS_404_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//用户登出清理数据
			bean=new LogoutHmController().execute(handicapMemberId,new Date());
		} catch (Exception e) {
			log.error("{}会员登出错误，{}", IbsConfig.LOG_SIGN, e.getMessage());
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
