package com.ibm.follow.connector.admin.manage3.client;

import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description: 客户机切换表单
 * @Author: null
 * @Date: 2020-02-26 18:25
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/clientReplaceForm1",method = HttpConfig.Method.GET)
public class ClientReplaceFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
//        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }

        Map<String,Object> map=new HashMap<>(2);
        IbmClientService clientService=new IbmClientService();
        List<String> clientList=clientService.findObjectAll();

        map.put("client",clientList);
        return new ModelAndView("/pages/com/ibm/admin/manager/client/clientReplaceForm.jsp", map);
    }
}
