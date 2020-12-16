package com.ibm.follow.connector.admin.manage3.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 删除游戏
 * @Author: lxl
 * @Date: 2019-10-18 16:46
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/game/delGame")
public class GameDelAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String gameId = request.getParameter("gameId");
        if (StringTool.isEmpty(gameId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            IbmGameService gameService = new IbmGameService();
            gameService.del(gameId);
            //删除该gameId的盘口游戏信息
            delAllGameInfo(gameId);

            bean.success();
        } catch (Exception e) {
            bean.fail("删除游戏信息异常，请重试");
        }
        return bean;
    }

    public void delAllGameInfo(String gameId) {
        try {
            IbmGameService gameService = new IbmGameService();
            List<Map<String, Object>> mapListGameIDs = gameService.findAllTableGameID();
            for (Map<String, Object> mapList : mapListGameIDs) {
                gameService.delAllInfoByGameId(mapList.get("table_name").toString(), gameId);
            }

            List<Map<String, Object>> mapListCodes = gameService.findAllTableGameCode();
            for (Map<String, Object> mapList : mapListCodes) {
                String gameCode = GameUtil.code(gameId).name();
                gameService.delAllInfoByGameCode(mapList.get("table_name").toString(), gameCode);
            }
        } catch (Exception e) {
            log.error("删除游戏信息时 同步删除附加信息异常", e);
        }

    }

}
