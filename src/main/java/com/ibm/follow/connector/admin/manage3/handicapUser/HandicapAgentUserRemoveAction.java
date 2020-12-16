package com.ibm.follow.connector.admin.manage3.handicapUser;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description:  设置用户 逻辑删除
 * @Author: lxl
 * @Date: 2019-10-14 11:09
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/agentUserRemove")
public class HandicapAgentUserRemoveAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String ibmHaUserId = request.getParameter("ibmHaUserId");
        if (StringTool.isEmpty(ibmHaUserId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }

        try{
            IbmHaUserService haUserService = new IbmHaUserService();
            haUserService.del(ibmHaUserId);
            bean.success();
        }catch (Exception e){
            bean.setMessage("代理删除 异常，请稍后重试");
        }
        return bean;
    }
}
