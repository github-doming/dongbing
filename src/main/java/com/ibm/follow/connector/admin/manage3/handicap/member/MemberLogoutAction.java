package com.ibm.follow.connector.admin.manage3.handicap.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 登出会员
 * @Author: null
 * @Date: 2020-03-11 16:03
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/member/logout", method = HttpConfig.Method.GET)
public class MemberLogoutAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }
        String handicapMemberId = request.getParameter("handicapMemberId");
        if (StringTool.isEmpty(handicapMemberId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try{
            IbmHandicapMemberService handicapMemberService=new IbmHandicapMemberService();
            if (!handicapMemberService.isExist(handicapMemberId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHmInfoService hmInfoService = new IbmHmInfoService();
            String loginState = hmInfoService.findLoginState(handicapMemberId);
            if (StringTool.isEmpty(loginState) || !IbmStateEnum.LOGIN.name().equals(loginState)) {
                bean.putEnum(IbmCodeEnum.IBM_404_CUSTOMER_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            //用户登出清理数据
            new LogoutHmController().execute(handicapMemberId);
            bean.success();
        }catch (Exception e) {
            log.error("登出盘口用户错误", e);
            throw e;
        }
        return bean;
    }
}
