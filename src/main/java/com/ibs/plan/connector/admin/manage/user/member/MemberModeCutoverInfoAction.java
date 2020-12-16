package com.ibs.plan.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 会员真实模拟切换信息
 * @Author: admin1
 * @Date: 2020/6/17 16:16
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/member/modeCutover/info")
public class MemberModeCutoverInfoAction extends CommAdminAction {

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
			IbspHandicapMember member = new IbspHandicapMemberService().find(handicapMemberId);
			if(member==null){
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String,Object> groupData = new IbspHmModeCutoverService().findCutoverGroupData(handicapMemberId);
			bean.success(groupData);
		} catch (Exception e) {
			log.error("获取会员真实模拟切换信息失败", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
