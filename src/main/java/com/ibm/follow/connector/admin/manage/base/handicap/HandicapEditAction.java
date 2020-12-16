package com.ibm.follow.connector.admin.manage.base.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 修改盘口信息
 * @Author: null
 * @Date: 2020-04-16 17:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/edit")
public class HandicapEditAction extends CommAdminAction {
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
        //盘口类型
        String handicapType = StringTool.getString(dataMap, "handicapType", "");
        //盘口状态
        String state = StringTool.getString(dataMap, "state", "");
        //盘口价值
        double handicapWorth = NumberTool.getDouble(dataMap.get("handicapWorth") , 0);

        try {
            IbmHandicapService handicapService = new IbmHandicapService();
            IbmHandicap handicap = handicapService.find(handicapId);
            if (handicap == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            if (!handicap.getState().equals(state)) {
                handicap.setState(state);
                //修改盘口游戏状态
                new IbmHandicapGameService().updateByHandicapId(handicap.getIbmHandicapId(), state, adminUser.getUserName());
            }
            handicap.setHandicapType(handicapType);
            handicap.setHandicapWorthT(NumberTool.intValueT(handicapWorth));
            handicap.setUpdateUser(adminUser.getUserName());
            handicap.setUpdateTime(new Date());
            handicap.setUpdateTimeLong(System.currentTimeMillis());
            handicapService.update(handicap);


            bean.success();
        } catch (Exception e) {
            log.error("获取盘口修改表单页面信息错误", e.getMessage());
            bean.error(e.getMessage());
        }
        return bean;
    }

}
