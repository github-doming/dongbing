package com.ibm.follow.connector.admin.manage.base.handicap.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 盘口详情表单信息
 * @Author: null
 * @Date: 2020-03-21 17:33
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/item/form")
public class HandicapItemFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //盘口详情id
        String handicapItemId = StringTool.getString(dataMap, "handicapItemId", "");

        try {
            IbmHandicapItemService handicapItemService = new IbmHandicapItemService();
            Map<String, Object> itemInfo = handicapItemService.findInfo(handicapItemId);
            itemInfo.remove("HANDICAP_ID_");
            itemInfo.put("HANDICAP_ITEM_ID_", handicapItemId);

            bean.setData(itemInfo);
            bean.success();
        } catch (Exception e) {
            log.error("盘口详情表单信息失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
