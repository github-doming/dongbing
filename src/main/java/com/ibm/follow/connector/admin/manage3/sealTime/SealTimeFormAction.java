package com.ibm.follow.connector.admin.manage3.sealTime;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 封盘时间表单
 * @Author: null
 * @Date: 2020-02-19 17:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/sealTime/form",method = HttpConfig.Method.GET)
public class SealTimeFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String key = request.getParameter("key");
        IbmConfigService configService=new IbmConfigService();
        String value=configService.findConfigValue(key);

        Map<String,Object> map=new HashMap<>(2);
        map.put("key",key);
        map.put("value",value);

        return new ModelAndView("/pages/com/ibm/admin/manager/sealTime/form.jsp", map);
    }
}
