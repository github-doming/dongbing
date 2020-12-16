package com.ibm.follow.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_game.entity.IbmGame;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 游戏设置
 * @Author: null
 * @Date: 2020-03-25 14:05
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/game/edit")
public class GameEditAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String gameId = StringTool.getString(dataMap, "gameId", "");

        String icon = StringTool.getString(dataMap, "icon", "");
        String drawTime = StringTool.getString(dataMap, "drawTime", "");
        String repGrabTableName = StringTool.getString(dataMap, "repGrabTableName", "");
        String repDrawTableName = StringTool.getString(dataMap, "repDrawTableName", "");
        String state = StringTool.getString(dataMap, "state", "");


        double gameWorth = NumberTool.getDouble( dataMap.get("gameWorth"), 0);
        int sn = NumberTool.getInteger(dataMap, "sn", 0);
        try {
            if (StringTool.isEmpty(gameId, state)) {
                bean.putEnum(IbmCodeEnum.IBM_401_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_401);
                return super.returnJson(bean);
            }

            IbmGameService gameService = new IbmGameService();
            IbmGame game = gameService.find(gameId);
            if (game == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_GAME);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            if (!game.getState().equals(state)) {
                game.setState(state);
                new IbmHandicapGameService().updateByGameId(game.getIbmGameId(), state, adminUser.getUserName());
            }

            if (StringTool.notEmpty(icon)) {
                game.setIcon(icon);
            }
            if (StringTool.notEmpty(icon)) {
                game.setDrawTime(Integer.parseInt(drawTime));
            }
            if (sn != 0) {
                game.setSn(sn);
            }
            if (StringTool.notEmpty(repGrabTableName)) {
                game.setRepGrabTableName(repGrabTableName);
            }
            if (StringTool.notEmpty(repDrawTableName)) {
                game.setRepDrawTableName(repDrawTableName);
            }
            game.setGameWorthT(NumberTool.intValueT(gameWorth));
            game.setUpdateUser(adminUser.getUserName());
            game.setUpdateTime(new Date());
            game.setUpdateTimeLong(System.currentTimeMillis());
            gameService.update(game);

            bean.success();
        } catch (Exception e) {
            log.error("游戏设置错误", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
