package com.ibm.follow.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 游戏列表
 * @Author: null
 * @Date: 2020-04-17 11:22
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/game/list")
public class GameListAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //游戏名称
        String gameName = StringTool.getString(dataMap, "gameName", "");
        try {
            Map<String, Object> map = new HashMap<>(2);
            IbmGameService gameService = new IbmGameService();
            List<Map<String, Object>> gameInfo = gameService.listShow(gameName);
            for(Map<String,Object> info : gameInfo){
                info.put("GAME_WORTH_T_", NumberTool.doubleT(info.get("GAME_WORTH_T_")));
            }
            map.put("gameName", gameName);
            map.put("gameInfo", gameInfo);
            bean.setData(gameInfo);
            bean.success();
        } catch (Exception e) {
            log.error("游戏列表错误", e);
            return bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
