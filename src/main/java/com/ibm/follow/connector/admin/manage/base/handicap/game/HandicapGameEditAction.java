package com.ibm.follow.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.entity.IbmHandicapGame;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 盘口游戏修改
 * @Author: null
 * @Date: 2020-04-18 13:37
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/edit")
public class HandicapGameEditAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //盘口游戏id
        String handicapGameId = StringTool.getString(dataMap, "handicapGameId", "");
        // 盘口游戏名
        String handicapGameName  = StringTool.getString(dataMap, "handicapGameName", "");
        //游戏类型
        String type = StringTool.getString(dataMap, "type", "");
        //盘口游戏图标
        String icon = StringTool.getString(dataMap, "icon", "");
        //状态
        String state = StringTool.getString(dataMap, "state", "");
        //次序
        int sn = NumberTool.getInteger(dataMap, "sn", -1);
        //封盘时间
        int closeTime = NumberTool.getInteger(dataMap, "closeTime", -1);

        if (StringTool.isEmpty(handicapGameId,handicapGameName, type, closeTime, icon, state, sn)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        try {
            IbmHandicapGameService handicapGameService = new IbmHandicapGameService();
            IbmHandicapGame handicapGame = handicapGameService.find(handicapGameId);
            if (handicapGame == null) {
                bean.putEnum(IbmCodeEnum.IBM_401_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_401);
                return super.returnJson(bean);
            }
            handicapGame.setType(type);
            if (closeTime != -1) {
                handicapGame.setCloseTime(closeTime);
            }
            if (sn != -1) {
                handicapGame.setSn(sn);
            }
            handicapGame.setIcon(icon);
            handicapGame.setGameName(handicapGameName);
            handicapGame.setState(state);
            handicapGame.setUpdateUser(adminUser.getUserName());
            handicapGame.setUpdateTime(new Date());
            handicapGame.setUpdateTimeLong(System.currentTimeMillis());
            handicapGameService.update(handicapGame);

            bean.success();
        } catch (Exception e) {
            log.error("盘口游戏修改错误", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
