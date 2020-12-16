package com.ibm.follow.connector.pc.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 清空表格数据
 * @Author: null
 * @Date: 2019-12-13 14:32
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/clearForm", method = HttpConfig.Method.GET)
public class ClearFormAction extends CommCoreAction {
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
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
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
            String handicapId = new IbmHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
            if (StringTool.isEmpty(handicapId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            GameUtil.Code game = GameUtil.Code.valueOf(gameCode);
            HandicapUtil.Code handicap=HandicapUtil.code(handicapId);

            long time = game.getGameFactory().period(handicap).getLotteryDrawTime();

            IbmHmBetItemService hmBetItemService = new IbmHmBetItemService();
            hmBetItemService.clearForm(gameId,handicapMemberId,time);

            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("清空表格数据信息错误"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
