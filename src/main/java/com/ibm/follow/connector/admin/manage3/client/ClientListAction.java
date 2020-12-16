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
 * @Description: 获取所有的客户机信息
 * @Author: null
 * @Date: 2020-01-16 11:09
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/list1" ,method = HttpConfig.Method.GET)
public class ClientListAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
//        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }

        String key = request.getParameter("key");

        Map<String,Object> map=new HashMap<>(2);
        //获取所有客户机
        IbmClientService clientService=new IbmClientService();
        List<Map<String,Object>> list=clientService.listShow(key);

        map.put("list",list);
        map.put("key", key);
        return new ModelAndView("/pages/com/ibm/admin/manager/client/list.jsp", map);
    }
}
