package com.ibm.follow.connector.admin.manage3.handicapUser;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description:  修改用户在线数量，最大数量
 * @Author: lxl
 * @Date: 2019-10-14 11:09
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/updateMemberUser")
public class HandicapMemberUserUpdateAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String ibmHmUserId = request.getParameter("ibmHmUserId");
        String onlineMembersCount = request.getParameter("onlineMembersCount");
        String onlineNumberMax = request.getParameter("onlineNumberMax");
        if (StringTool.isEmpty(ibmHmUserId,onlineMembersCount,onlineNumberMax)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            IbmHmUserService hmUserService = new IbmHmUserService();
            hmUserService.updateOnlineInfoById(ibmHmUserId,onlineMembersCount,onlineNumberMax);
            bean.success();
        }catch (Exception e){
            bean.setMessage("修改会员在线数量，会员在线最大数量 失败，请稍后重试");
        }
        return bean;
    }
}
