package com.ibm.follow.connector.admin.manage.client.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 盘口容量修改表单
 * @Author: null
 * @Date: 2020-03-24 16:49
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicap/form",method = HttpConfig.Method.GET)
public class HandicapCapacityFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String clientCode = dataMap.getOrDefault("clientCode","").toString();
        String handicapCode = dataMap.getOrDefault("handicapCode","").toString();

        try{
            Map<String,Object> map=new IbmClientHandicapCapacityService().findHandicapCapacityInfo(clientCode,handicapCode);

            bean.setData(map);
            bean.success();
        } catch (Exception e) {
            log.error("盘口容量修改表单错误", e);
            return bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
