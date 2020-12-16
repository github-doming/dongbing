package com.ibm.follow.connector.admin.manage3.client;

import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 *
 * @Description: 客户端容量修改表单
 * @Author: null
 * @Date: 2020-02-19 17:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/clientCapacityForm1",method = HttpConfig.Method.GET)
public class ClientCapacityFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
//        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }

        String clientCode = request.getParameter("clientCode");
        IbmClientCapacityService clientCapacityService=new IbmClientCapacityService();
        Map<String,Object> map=clientCapacityService.findcapacityInfo(clientCode);

        return new ModelAndView("/pages/com/ibm/admin/manager/client/clientCapacityForm.jsp", map);
    }
}
