package com.ibm.follow.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_game.entity.IbmGame;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 添加游戏
 * @Author: null
 * @Date: 2020-03-25 14:21
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/game/save")
public class GameSaveAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String gameCode = StringTool.getString(dataMap, "gameCode", "");
        String gameName = StringTool.getString(dataMap, "gameName", "");
        String icon = StringTool.getString(dataMap, "icon", "");

        double gameWorth = NumberTool.getDouble( dataMap.get("gameWorth"), 0);
        int drawTime = NumberTool.getInteger(dataMap, "drawTime", -1);
        int sn = NumberTool.getInteger(dataMap, "sn", -1);

        if (StringTool.isEmpty(gameName, gameCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }

        try {
            IbmGameService gameService = new IbmGameService();
            IbmGame game = new IbmGame();
            game.setGameName(gameName);
            game.setGameCode(gameCode);
            if (StringTool.notEmpty(icon)) {
                game.setIcon(icon);
            }
            if (drawTime != -1) {
                game.setDrawTime(drawTime);
            }
            if (sn != -1) {
                game.setSn(sn);
            }
            game.setGameWorthT(NumberTool.intValueT(gameWorth));
            game.setCreateUser(adminUser.getUserName());
            game.setCreateTime(new Date());
            game.setCreateTimeLong(System.currentTimeMillis());
            game.setUpdateTime(new Date());
            game.setUpdateTimeLong(System.currentTimeMillis());
            game.setState(IbmStateEnum.OPEN.name());
            gameService.save(game);

            bean.success();
        } catch (Exception e) {
            log.error("游戏添加错误", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
