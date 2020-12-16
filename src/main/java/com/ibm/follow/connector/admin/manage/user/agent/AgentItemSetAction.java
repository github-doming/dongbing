package com.ibm.follow.connector.admin.manage.user.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.entity.IbmHandicapAgent;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 代理详情设置
 * @Author: null
 * @Date: 2020-03-21 14:22
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/agent/itemSet")
public class AgentItemSetAction extends CommAdminAction {
    private String handicapAgentId;
    private String handicapUrl;
    private String handicapCaptcha;
    private String autoLoginState;
    private String availableGame;
    
    
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        if (valiParameters()) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        try{
            IbmHandicapAgentService handicapAgentService=new IbmHandicapAgentService();
            IbmHandicapAgent handicapAgent=handicapAgentService.find(handicapAgentId);
            if(handicapAgent==null){
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Date nowTime = new Date();
            //修改自动登录时间
            if (IbmTypeEnum.TRUE.name().equals(autoLoginState)) {
                //自动登录时间
                handicapAgent.setLandedTimeLong(NumberTool.getLong(request.getParameter("landedTime")));
            } else {
                handicapAgent.setLandedTimeLong(0L);
            }
            handicapAgent.setUpdateTime(nowTime);
            handicapAgent.setUpdateTimeLong(nowTime.getTime());
            handicapAgentService.update(handicapAgent);

            //修改盘口详情
            new IbmHandicapItemService().update(handicapAgent.getHandicapItemId(), handicapUrl, handicapCaptcha);


            //修改可用游戏设置
            updateAvailableGameSet(handicapAgent);


            bean.success();
        } catch (Exception e) {
            log.error("会员详情设置失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    /**
     * 修改可用游戏
     * @param handicapAgent
     */
    private void updateAvailableGameSet(IbmHandicapAgent handicapAgent) throws Exception {
        Map<String, Object> agentInfo = new HashMap<>(2);
        agentInfo.put("IBM_HANDICAP_AGENT_ID_", handicapAgentId);
        agentInfo.put("HANDICAP_ID_", handicapAgent.getHandicapId());

        IbmHaGameSetService haGameSetService=new IbmHaGameSetService();
        List<String> usingGame =haGameSetService.listGameCodes(handicapAgentId);

        List<String> updateGame = Arrays.asList(availableGame.split(","));

        //新游戏codes-原来游戏codes-需要新加的游戏
        Set<Object> saveGameCodes = new HashSet<>(ContainerTool.getDifferent(updateGame, usingGame));

        //原来游戏codes-新游戏codes-需要删除的游戏
        Set<Object> delGameCodes = new HashSet<>(ContainerTool.getDifferent(usingGame, updateGame));


        List<String> gameIds = new ArrayList<>();
        if (ContainerTool.notEmpty(saveGameCodes)) {
            Map<String, Object> initAgentGameSet = haGameSetService.findInitInfo();
            for (Object code : saveGameCodes) {
                gameIds.add(GameUtil.id(code.toString()));
            }
            haGameSetService.save(handicapAgent.getAppUserId(), agentInfo, gameIds, initAgentGameSet);
        }

        if (ContainerTool.notEmpty(delGameCodes)) {
            gameIds.clear();
            for (Object code : delGameCodes) {
                gameIds.add(GameUtil.id(code.toString()));
            }
            haGameSetService.delGameSet(agentInfo, gameIds);
        }
        //判断代理是否登录中
        IbmHaInfoService haInfoService=new IbmHaInfoService();
        String loginState=haInfoService.findLoginState(handicapAgentId);
        if (StringTool.isEmpty(loginState) || !IbmStateEnum.LOGIN.name().equals(loginState)) {
            return ;
        }
        //获取游戏信息，重新发送游戏信息
        List<Map<String, Object>> gameInfo = haGameSetService.findByHaId(handicapAgentId);
        JSONObject content = new JSONObject();
        content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());
        content.put("GAME_INFO_", gameInfo);
        content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_INFO.name());

		String desc= this.getClass().getName().concat("，发送代理游戏设置信息");
		EventThreadDefine.sendClientConfig(content,handicapAgentId,IbmTypeEnum.AGENT,desc);

    }

    private boolean valiParameters() {
        //盘口代理id

        handicapAgentId = StringTool.getString(dataMap.get("handicapAgentId"),"");
        //导航地址
        handicapUrl = StringTool.getString(dataMap.get("handicapUrl"),"");
        //导航验证码
        handicapCaptcha = StringTool.getString(dataMap.get("handicapCaptcha"),"");
        //自动登录状态
        autoLoginState = StringTool.getString(dataMap.get("autoLoginState"),"");
        //可用游戏设置
        availableGame = StringTool.getString(dataMap.get("availableGame"),"");

        return StringTool.isEmpty(handicapAgentId, handicapUrl, handicapCaptcha, autoLoginState, availableGame);

    }
}
