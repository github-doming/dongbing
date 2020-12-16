package com.ibm.follow.connector.app.agent.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 获取游戏过滤项目
 * @Author: null
 * @Date: 2019-12-06 17:06
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/agent/getFilterProject", method = HttpConfig.Method.GET)
public class GameFilterProjectAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();

        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

        if (StringTool.isEmpty(handicapAgentId, gameCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        GameUtil.Code code = GameUtil.value(gameCode);
        if (code == null) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            //用户是否登录
            if (!IbmStateEnum.LOGIN.equal(new IbmHaInfoService().findLoginState(handicapAgentId))){
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
            // 数据校验
			String gameId =GameUtil.id(gameCode);
            String filterPorject=haGameSetService.findFilterProject(handicapAgentId,gameId);

            bean.success(filterPorject);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("修改跟投信息失败"),e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
