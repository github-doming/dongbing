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
 * @Description: 显示已存在的投注信息数据
 * @Author: wwj
 * @Date: 2020/2/22 20:14
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/sys/showMemoryData", method = HttpConfig.Method.GET) public class ShowSysMemoryDataAction
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
            int day = 7;
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, - day);// 默认7天
            IbmSysService sysService = new IbmSysService();
            List<Map<String,Object>> listMap = sysService.findBetInfo(Arrays.asList("ibm_ha_follow_period","ibm_ha_member_bet_item",
                    "ibm_hm_bet_item","ibm_hm_profit","ibm_hm_profit_item","ibm_hm_profit_period","ibm_hm_profit_period_vr",
                    "ibm_hm_profit_vr"),c.getTime().getTime());

            Map<String, Object> data = new HashMap<>(1);
            data.put("tableInfos",listMap);
            return new ModelAndView("/pages/com/ibm/admin/manager/sys/list.jsp", data);
        } catch (Exception e) {
            bean.fail("查询投注信息出错！");
        }
        return bean;

    }
}

