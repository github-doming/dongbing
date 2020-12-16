package com.ibm.follow.connector.admin.manage.base.handicap.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.entity.IbmHandicapItem;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 盘口详情修改
 * @Author: null
 * @Date: 2020-03-21 17:36
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/item/edit")
public class HandicapItemEditAction extends CommAdminAction {
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
        String handicapUrl = StringTool.getString(dataMap, "handicapUrl", "");
        String handicapCaptcha = StringTool.getString(dataMap, "handicapCaptcha", "");
        try {
            IbmHandicapItemService handicapItemService = new IbmHandicapItemService();
            IbmHandicapItem handicapItem = handicapItemService.find(handicapItemId);
            if (handicapItem == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_ITEM);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            handicapItem.setHandicapUrl(handicapUrl);
            handicapItem.setHandicapCaptcha(handicapCaptcha);
            handicapItem.setUpdateTime(new Date());
            handicapItem.setUpdateTimeLong(System.currentTimeMillis());
            handicapItemService.update(handicapItem);

            bean.success();
        } catch (Exception e) {
            log.error("盘口详情修改失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
