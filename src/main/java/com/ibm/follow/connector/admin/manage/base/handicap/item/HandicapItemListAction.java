package com.ibm.follow.connector.admin.manage.base.handicap.item;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 盘口详情列表
 * @Author: null
 * @Date: 2020-03-21 17:21
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/item/list")
public class HandicapItemListAction extends CommAdminAction {
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

        // 分页
        Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
        Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
        Map<String, Object> data = new HashMap<>(5);
        try {
            IbmHandicapService handicapService = new IbmHandicapService();
            IbmHandicap handicap = handicapService.find(handicapId);
            if (handicap == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHandicapItemService handicapItemService = new IbmHandicapItemService();
            PageCoreBean<Map<String, Object>> basePage = handicapItemService.findHandicapItem(handicapId, pageIndex, pageSize);

            data.put("HANDICAP_CODE_", handicap.getHandicapCode());
            data.put("HANDICAP_CATEGORY_", handicap.getHandicapCategory());
            data.put("rows", basePage.getList());
            data.put("index", pageIndex);
            data.put("total", basePage.getTotalCount());
        } catch (Exception e) {
            log.error("盘口详情列表失败", e);
            data.put("rows", null);
            data.put("index", 0);
            data.put("total", 0);
        }
        return data;
    }
}
