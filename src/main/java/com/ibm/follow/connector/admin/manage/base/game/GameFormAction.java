package com.ibm.follow.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 游戏表单页面
 * @Author: null
 * @Date: 2020-03-25 11:31
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/game/form")
public class GameFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String gameId = StringTool.getString(dataMap, "gameId", "");
        try {
            IbmGameService gameService = new IbmGameService();
            Map<String, Object> map = gameService.findInfoById(gameId);
            map.put("GAME_WORTH_T_", NumberTool.doubleT(map.get("GAME_WORTH_T_")));
            bean.setData(map);
            bean.success();
        } catch (Exception e) {
            log.error("游戏表单页面错误", e);
            return bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
