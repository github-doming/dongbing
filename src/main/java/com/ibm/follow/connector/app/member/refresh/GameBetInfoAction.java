package com.ibm.follow.connector.app.member.refresh;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;

import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口会员游戏投注信息
 * @Author: null
 * @Date: 2019-11-26 16:38
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/member/gameBetInfo", method = HttpConfig.Method.GET)
public class GameBetInfoAction extends CommCoreAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        String checkTimeStr = dataMap.getOrDefault("checkTime", "").toString();

        //校验
        if (StringTool.isEmpty(gameCode, handicapMemberId)) {
            bean.putEnum(IbmCodeEnum.IBM_404_GAME);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return bean;
        }
		String gameId =GameUtil.id(gameCode);
        if (StringTool.isEmpty(gameId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }

        //检查时间为空时，获取最近20期投注数据
        int number = StringTool.isEmpty(checkTimeStr) ? 20 : 2;

        try {

            // 判断主键是否存在
            if (!new IbmHandicapMemberService().isExist(handicapMemberId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }

            Map<String,Object> gameSetInfo=new IbmHmGameSetService().findInfo(handicapMemberId,gameId);
            if(ContainerTool.isEmpty(gameSetInfo)){
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }

            List<Map<String, Object>> betInfos =new IbmHmProfitService().findBetInfo(handicapMemberId,gameId,gameSetInfo.get("BET_MODE_"),number);
            for(Map<String,Object> betInfo:betInfos){
                betInfo.remove("ROW_NUM");
                betInfo.put("PROFIT_",NumberTool.doubleT(betInfo.remove("PROFIT_T_")));
                betInfo.put("BET_FUNDS_",NumberTool.doubleT(betInfo.remove("BET_FUNDS_T_")));
            }
            Map<String, Object> data = new HashMap<>(2);
            //展示信息
            Map<String, Object> gameInfo = new IbmHmGameSetService().findShowInfo(handicapMemberId, gameId);
            data.put("gameInfo",gameInfo);
            data.put("betInfos",betInfos);
            bean.success(data);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("查询盘口会员投注信息-出错"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
