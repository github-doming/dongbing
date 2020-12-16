package com.ibm.follow.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 会员投注表单信息
 * @Author: null
 * @Date: 2020-03-19 15:58
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/member/betForm") public class MemberBetFormAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口会员id
		String handicapMemberId = StringTool.getString(dataMap.get("handicapMemberId"), "");
		try {
			Map<String, Object> name = new IbmHandicapMemberService().findName(handicapMemberId);
			if (name == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
			List<Map<String, Object>> list = hmGameSetService.findBetInfoByHmId(handicapMemberId);
			Map<String, Object> data = new HashMap<>(2);
			data.put("name",name);
			data.put("list",list);
			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("登录盘口会员失败", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
