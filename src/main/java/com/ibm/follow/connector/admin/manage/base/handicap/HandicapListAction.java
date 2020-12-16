package com.ibm.follow.connector.admin.manage.base.handicap;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Dongming
 * @Date: 2020-04-16 15:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/list")
public class HandicapListAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //盘口名称
        String handicapName = StringTool.getString(dataMap, "handicapName", "");
        //盘口类别
        String handicapCategory = StringTool.getString(dataMap, "handicapCategory", "");
        //盘口类型
        String handicapType = StringTool.getString(dataMap, "handicapType", "");

        // 分页
        Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
        Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
        Map<String, Object> data = new HashMap<>(6);
        try {
            //获取盘口信息
            PageCoreBean<Map<String, Object>> basePage = new IbmHandicapService().listShow(handicapName, handicapCategory, handicapType, pageIndex, pageSize);
            for (Map<String,Object> map : basePage.getList()){
                map.put("HANDICAP_WORTH_T_",NumberTool.doubleT(map.get("HANDICAP_WORTH_T_")));
            }
            data.put("handicapCode", handicapName);
            data.put("handicapCategory", handicapCategory);
            data.put("handicapType", handicapType);
            data.put("rows", basePage.getList());
            data.put("index", pageIndex);
            data.put("total", basePage.getTotalCount());

        } catch (Exception e) {
            log.error("盘口列表错误", e);
            data.put("rows", null);
            data.put("index", 0);
            data.put("total", 0);
            data.put("handicapName", handicapName);
            data.put("handicapCategory", handicapCategory);
            data.put("handicapType", handicapType);

        }
        return data;
    }
}

