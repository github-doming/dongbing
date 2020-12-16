package com.ibm.follow.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口游戏列表
 * @Author: null
 * @Date: 2020-04-18 10:38
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/list")
public class HandicapGameListAction extends CommAdminAction {
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
            IbmHandicapService handicapService = new IbmHandicapService();
            IbmHandicap handicap = handicapService.find(handicapId);
            if (handicap == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Map<String, Object> data = new HashMap<>(2);

            IbmHandicapGameService handicapGameService = new IbmHandicapGameService();
            List<Map<String, Object>> list = handicapGameService.findByHandicapId(handicapId);

            data.put("list", list);
            data.put("handicapName", handicap.getHandicapName());
            data.put("handicapCategory", handicap.getHandicapCategory());
            bean.setData(data);
            bean.success();
        } catch (Exception e) {
            log.error("盘口游戏列表错误", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
