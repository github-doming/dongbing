package com.ibm.follow.connector.admin.manage3.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;

import java.util.*;

/**
 * @Description: 获取未添加的游戏
 * @Author: wwj
 * @Date: 2019/11/18 14:18
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/game/no")
public class GameNoAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try{
            IbmGameService gameService = new IbmGameService();
            List<String> codeList = gameService.findAllGameCode();

            GameUtil.Code[] codes = GameUtil.codes();
            List<Map<String,Object>> data = new ArrayList<>();
            for (GameUtil.Code code : codes){
                if(!codeList.contains(code.toString())){
                    Map<String,Object> map = new HashMap<>();
                    map.put("code",code.toString());
                    map.put("name",code.getName());
                    data.add(map);
                }
            }
            if (ContainerTool.isEmpty(data)){
                return bean.fail("已添加所有游戏，请勿重复添加。");
            }

            bean.success(data);

        }catch (Exception e){
            log.error("添加游戏失败", e);
            bean.error(e.getMessage());
        }
        return bean;
    }


}
