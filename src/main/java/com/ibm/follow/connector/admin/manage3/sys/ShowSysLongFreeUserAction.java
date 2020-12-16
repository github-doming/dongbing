package com.ibm.follow.connector.admin.manage3.sys;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.connector.admin.manage3.service.IbmSysService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.develop.http.HttpConfig;

import java.util.*;

/**
 * @Description: 显示长期空闲的用户
 * @Author: wwj
 * @Date: 2020/2/24 20:52
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/sys/showLongFreeUser", method = HttpConfig.Method.GET) public class ShowSysLongFreeUserAction
        extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        try {
            int day = 30;
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -day);
            IbmSysService sysService = new IbmSysService();
            List<Map<String,Object>> listMap = sysService.findExpireUser(c.getTime().getTime());

            Map<String, Object> data = new HashMap<>(1);
            data.put("userInfos",listMap);
            return new ModelAndView("/pages/com/ibm/admin/manager/sys/userlist.jsp", data);
        } catch (Exception e) {
            bean.fail("查询过期用户出错！");
        }
        return bean;
    }
}