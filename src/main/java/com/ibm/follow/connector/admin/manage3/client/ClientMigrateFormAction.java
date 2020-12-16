package com.ibm.follow.connector.admin.manage3.client;

import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description: 客户机盘口迁移表单
 * @Author: null
 * @Date: 2020-02-26 18:25
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicapMigrateForm1",method = HttpConfig.Method.GET)
public class ClientMigrateFormAction extends CommAdminAction {
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

        IbmHandicapService handicapService=new IbmHandicapService();
        Map<String, String> handicap=handicapService.mapIdCode();

        map.put("client",clientList);
        map.put("handicap",handicap);
        return new ModelAndView("/pages/com/ibm/admin/manager/client/handicapMigrateForm.jsp", map);
    }
}
