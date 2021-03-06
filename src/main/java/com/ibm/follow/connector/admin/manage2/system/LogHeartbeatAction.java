package com.ibm.follow.connector.admin.manage2.system;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_log_heartbeat.service.IbmLogHeartbeatService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 心跳检测日志
 * @Author: null
 * @Date: 2020-03-03 14:41
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/log/heartbeat", method = HttpConfig.Method.GET)
public class LogHeartbeatAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //心跳结果
        String heartbeatResult = request.getParameter("heartbeatResult");

        Map<String,Object> map=new HashMap<>(1);
        List<Map<String,Object>> list=new IbmLogHeartbeatService().findAll(heartbeatResult);

        map.put("list",list);
        return new ModelAndView("/pages/com/ibm/admin/manager/log/heartbeat.jsp", map);
    }
}
