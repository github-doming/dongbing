package com.ibm.follow.connector.admin.manage3.client;

import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 *
 * @Description: 盘口容量修改表单
 * @Author: null
 * @Date: 2020-02-19 17:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicapCapacityForm1",method = HttpConfig.Method.GET)
public class HandicapCapacityFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
//        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }
        String handicapCode = request.getParameter("handicapCode");
        String clientCode = request.getParameter("clientCode");
        IbmClientHandicapCapacityService handicapCapacityService=new IbmClientHandicapCapacityService();
        Map<String,Object> map=handicapCapacityService.findHandicapCapacityInfo(clientCode,handicapCode);

        return new ModelAndView("/pages/com/ibm/admin/manager/client/handicapCapacityForm.jsp", map);
    }
}
