package com.ibm.follow.connector.app.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 获取游戏设置信息
 * @Author: null
 * @Date: 2019-11-28 17:20
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/member/gameInfo", method = HttpConfig.Method.GET)
public class GameSetInfoAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        if (StringTool.isEmpty(handicapMemberId, gameCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            //用户是否登录
            if (!IbmStateEnum.LOGIN.equal(new IbmHmInfoService().findLoginState(handicapMemberId))){
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
			String gameId =GameUtil.id(gameCode);
            if (StringTool.isEmpty(gameId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_GAME);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            //展示信息
            Map<String, Object> gameInfo = new IbmHmGameSetService().findShowInfo(handicapMemberId, gameId);
            if (ContainerTool.isEmpty(gameInfo)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }

            bean.success(gameInfo);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("获取游戏设置信息错误"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
