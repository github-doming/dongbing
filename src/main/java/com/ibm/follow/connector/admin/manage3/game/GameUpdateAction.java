package com.ibm.follow.connector.admin.manage3.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 更新游戏信息
 * @Author: lxl
 * @Date: 2019-10-18 16:38
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/game/updateGame")
public class GameUpdateAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String gameId = request.getParameter("gameId");
        String gameName = request.getParameter("gameName");
        String gameCode = request.getParameter("gameCode");
        String gameIcon = request.getParameter("gameIcon");
        String gameSn = request.getParameter("gameSn");
        String drawTime = request.getParameter("drawTime");
        if (StringTool.isEmpty(gameId,gameCode,gameName,gameIcon,gameSn,drawTime)){
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            IbmGameService gameService = new IbmGameService();
            gameService.updateInfo(gameId,gameCode,gameName,gameIcon,gameSn,drawTime);
            bean.success();
        }catch (Exception e){
            bean.fail("更新游戏信息失败，请稍后重试");
        }
        return bean;
    }
}
