package com.ibm.follow.connector.admin.manage3.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 游戏列表
 * @Author: lxl
 * @Date: 2019-10-18 15:58
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@Deprecated
@ActionMapping(value = "/ibm/admin/manage/game/list1")
public class GameListAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        Map<String,Object> map = new HashMap<>(1);
        try {
            IbmGameService gameService = new IbmGameService();
            map.put("gameLists",gameService.findAll());
        }catch (Exception e){
            log.error("获取游戏列表出现异常，请稍后重试",e);
        }
        return new ModelAndView("/pages/com/ibm/admin/manager/game/gameList.jsp",map);
    }
}
