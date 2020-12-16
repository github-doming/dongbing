package com.ibm.follow.connector.admin.manage3.handicap.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapMemberService;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.service.IbmEventClientCloseService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

@ActionMapping(value = "/ibm/admin/manage/handicap/member/delete", method = HttpConfig.Method.GET)
public class MenberDeleteAction extends CommAdminAction {
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
            String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName())
                    .concat(",删除盘口会员信息:").concat(handicapMemberId);

            IbmAdminHandicapMemberService handicapMemberService=new IbmAdminHandicapMemberService();
            if (!handicapMemberService.isExist(handicapMemberId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHmInfoService hmInfoService = new IbmHmInfoService();
            String loginState = hmInfoService.findLoginState(handicapMemberId);
            if (StringTool.notEmpty(loginState) && IbmStateEnum.LOGIN.name().equals(loginState)) {
                //用户登出清理数据
                new LogoutHmController().execute(handicapMemberId);
            }
            Date nowTime = new Date();

            handicapMemberService.delByHmId(handicapMemberId,nowTime,desc);

            bean.success();
        }catch (Exception e) {
            log.error("删除盘口用户信息错误", e);
            throw e;
        }
        return bean;
    }
}
