package com.ibm.follow.connector.admin.manage3.client;

import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description: 客户端盘口容量信息
 * @Author: null
 * @Date: 2020-01-16 15:17
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicapCapacity1",method = HttpConfig.Method.GET)
public class HandicapCapacityAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
//        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }

        Map<String,Object> map=new HashMap<>(1);
        String clientCode=request.getParameter("clientCode");

        IbmClientHandicapCapacityService handicapCapacityService=new IbmClientHandicapCapacityService();
//        List<Map<String,Object>> handicapCapacitys=handicapCapacityService.findByClientCode(clientCode);

//        map.put("list",handicapCapacitys);
        return new ModelAndView("/pages/com/ibm/admin/manager/client/handicapList.jsp", map);
    }
}
