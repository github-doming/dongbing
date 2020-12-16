package com.ibm.follow.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 删除会员信息
 * @Author: null
 * @Date: 2020-03-19 14:45
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/member/delete")
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
        String handicapMemberId= StringTool.getString(dataMap.get("handicapMemberId"), "");

        try{
            IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
            IbmHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
            if (handicapMember == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            //会员登出状态
            logoutHappening(handicapMemberId);

            String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",删除会员信息:")
                    .concat(handicapMemberId);

            //删除盘口会员信息
            handicapMemberService.delHmInfo(handicapMemberId,desc);

            bean.success();
        } catch (Exception e) {
            log.error("删除盘口会员失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    private void logoutHappening(String handicapMemberId) throws Exception {
        String loginState = new IbmHmInfoService().findLoginState(handicapMemberId);
        if (StringTool.notEmpty(loginState) && IbmStateEnum.LOGIN.name().equals(loginState)) {
            new LogoutHmController().execute(handicapMemberId);
        }
    }
}
