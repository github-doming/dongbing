package com.ibm.follow.connector.app.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 修改投注模式
 * @Author: null
 * @Date: 2019-11-26 18:09
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/member/hmBetMode", method = HttpConfig.Method.GET)
public class BetModeSetAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
        String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        String betMode = dataMap.getOrDefault("BET_MODE_", "").toString();

        if (StringTool.isEmpty(handicapMemberId,gameCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        if(!IbmTypeEnum.betModelType(betMode)){
            bean.putEnum(IbmCodeEnum.IBM_401_STATE);
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
            String gameId= GameUtil.id(gameCode);
            if(StringTool.isEmpty(gameId)){
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
            IbmHmGameSet hmGameSet = hmGameSetService.findByHmIdAndGameCode(handicapMemberId, gameId);
            if (hmGameSet == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return super.returnJson(bean);
            }
            if(!hmGameSet.getBetMode().equals(betMode)){
                hmGameSet.setBetMode(betMode);

                Map<String, Object> bindInfo = new IbmClientHmService().findBindInfo(handicapMemberId);
                if(ContainerTool.isEmpty(bindInfo)){
                    log.error("获取盘口会员【" + handicapMemberId + "】存在会员信息失败");
                    bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                    bean.putSysEnum(IbmCodeEnum.CODE_404);
                    return super.returnJson(bean);
                }

                Date nowTime=new Date();
                JSONObject content = new JSONObject();
                content.put("EXIST_HM_ID_",bindInfo.get("EXIST_HM_ID_"));
                content.put("SET_ITEM_", IbmMethodEnum.SET_BET_MODE.name());
                content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
                content.put("GAME_CODE_", gameCode);
                content.put("BET_MODE_",betMode);
                //获取绑定信息
                IbmHandicapAgentMemberService agentMemberService=new IbmHandicapAgentMemberService();
                List<String> handicapAgentIds=agentMemberService.findHaIdByHmId(handicapMemberId);
				String desc= this.getClass().getName().concat("，添加绑定数据设置");
                for(String handicapAgentId:handicapAgentIds){
					EventThreadDefine.sendClientConfig(content,handicapAgentId,IbmTypeEnum.AGENT,desc);
                }

                // 修改投注模式
                hmGameSet.setUpdateTimeLong(nowTime.getTime());
                hmGameSetService.update(hmGameSet);
            }

            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("修改游戏投注状态失败"),e);
            bean.error(e.getMessage());
        }
        return bean;
    }

}
