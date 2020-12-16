package com.ibm.follow.connector.admin.manage.base.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 盘口表单
 * @Author: null
 * @Date: 2020-04-16 17:35
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/form")
public class HandicapFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //盘口id
        String handicapId = StringTool.getString(dataMap, "handicapId", "");
        try {
            //获取盘口信息
            Map<String, Object> handicapInfo = new IbmHandicapService().findInfo(handicapId);
            handicapInfo.put("HANDICAP_WORTH_T_", NumberTool.doubleT(handicapInfo.get("HANDICAP_WORTH_T_")));

            bean.setData(handicapInfo);
            bean.success();
        } catch (Exception e) {
            log.error("获取盘口修改表单页面信息错误", e.getMessage());
            bean.error(e.getMessage());
        }
        return bean;
    }
}
