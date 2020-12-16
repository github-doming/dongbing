package com.ibm.follow.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口游戏表单
 * @Author: null
 * @Date: 2020-04-18 10:53
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/form")
public class HandicapGameFormAction extends CommAdminAction {
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
        //盘口id
        String handicapId = StringTool.getString(dataMap, "handicapId", "");
        try {
            Map<String, Object> data = new HashMap<>();
            IbmHandicapGameService handicapGameService = new IbmHandicapGameService();
            if (StringTool.notEmpty(handicapGameId)) {
                Map<String, Object> handicapGameInfo = handicapGameService.findInfoById(handicapGameId);
                data.put("handicapGameInfo", handicapGameInfo);
            }
            //新增盘口游戏页面
            if (StringTool.notEmpty(handicapId)) {
                IbmGameService gameService = new IbmGameService();
                List<Map<String, Object>> list = handicapGameService.findByHandicapId(handicapId);
                List<String> gameCodes = gameService.findAllGameCode();
                for (Map<String, Object> map : list) {
                    gameCodes.remove(map.get("GAME_CODE_"));
                }
                if (ContainerTool.isEmpty(gameCodes)) {
                    bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
                    bean.putSysEnum(IbmCodeEnum.CODE_404);
                    return bean.toJsonString();
                }

                List<Map<String, Object>> gameInfos = new ArrayList<>(gameCodes.size());
				GameUtil.Code game;
                for (String gameCode : gameCodes) {
					game=GameUtil.value(gameCode);
					if(game!=null){
						Map<String, Object> gameInfo = new HashMap<>(2);
						String gameName =game.getName();
						gameInfo.put("gameCode", gameCode.toUpperCase());
						gameInfo.put("gameName", gameName);
						gameInfos.add(gameInfo);
					}
                }
                data.put("gameInfo", gameInfos);
                data.put("handicapId", handicapId);
            }

            bean.setData(data);
            bean.success();
        } catch (Exception e) {
            log.error("盘口游戏表单错误", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
