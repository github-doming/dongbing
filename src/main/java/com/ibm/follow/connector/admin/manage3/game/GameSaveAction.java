package com.ibm.follow.connector.admin.manage3.game;

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
 * @Author: lxl
 * @Date: 2019-10-18 16:46
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/game/saveGame")
public class GameSaveAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String gameName = request.getParameter("gameName");
        String gameCode = request.getParameter("gameCode");
        String gameIcon = request.getParameter("gameIcon");
        String repGrabTableName = request.getParameter("repGrabTableName");
        String repDrawTableName = request.getParameter("repDrawTableName");
        String gameSn = request.getParameter("gameSn");
        String drawTime = request.getParameter("drawTime");
        if (StringTool.isEmpty(gameName,gameCode,drawTime)){
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            IbmGame game = new IbmGame();
            game.setGameName(gameName);
            game.setGameCode(gameCode);
            game.setIcon(gameIcon);
            game.setDrawTime(NumberTool.getInteger(drawTime));
            game.setRepGrabTableName(repGrabTableName);
            game.setRepDrawTableName(repDrawTableName);
            game.setSn(NumberTool.getInteger(gameSn));
            game.setCreateTime(new Date());
            game.setCreateTimeLong(System.currentTimeMillis());
            game.setUpdateTime(new Date());
            game.setUpdateTimeLong(System.currentTimeMillis());
            game.setState(IbmStateEnum.OPEN.name());
            game.setDesc("添加游戏"+gameName);

            IbmGameService gameService = new IbmGameService();
            gameService.save(game);
            bean.success();
        }catch (Exception e){
            bean.fail("添加游戏异常，请稍后重试");
        }
        return bean;
    }
}
