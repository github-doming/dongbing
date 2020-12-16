package com.ibm.follow.connector.admin.manage3.sealTime;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 所有封盘时间信息
 * @Author: null
 * @Date: 2020-01-18 17:05
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/sealTime/list")
public class SealTimeListAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
//        List<Map<String,Object>> configList=new IbmConfigService().listSealTimeInfo(null, null);
//        Map<String,Object> map =new HashMap<>(1);
//        map.put("list",configList);

        return new ModelAndView("/pages/com/ibm/admin/manager/sealTime/list.jsp", null);
    }
}
