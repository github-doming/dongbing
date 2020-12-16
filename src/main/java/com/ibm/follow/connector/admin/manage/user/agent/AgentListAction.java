package com.ibm.follow.connector.admin.manage.user.agent;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 代理列表
 * @Author: null
 * @Date: 2020-03-20 18:33
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/agent/list")
public class AgentListAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        //用户名
        String userName= dataMap.getOrDefault("userName","").toString();
        request.getParameter("userName");
        //代理账号
        String agentAccount= dataMap.getOrDefault("agentAccount","").toString();
        request.getParameter("agentAccount");
        //盘口
        String handicapCode= dataMap.getOrDefault("handicapCode","").toString();
        request.getParameter("handicapCode");
        //在线状态
        String onlineState= dataMap.getOrDefault("onlineState","").toString();
        request.getParameter("onlineState");
        // 分页
        Integer pageIndex = NumberTool.getInteger( dataMap.get("pageIndex"),0);
        Integer pageSize =  NumberTool.getInteger( dataMap.get("pageSize"),15);

        Map<String, Object> data = new HashMap<>(3);
        try {

            IbmHaGameSetService haGameSetService=new IbmHaGameSetService();
            //获取所有会员信息
            IbmHaInfoService haInfoService=new IbmHaInfoService();
            PageCoreBean<Map<String, Object>> basePage=haInfoService.findShow(userName,agentAccount,handicapCode,onlineState,pageIndex,pageSize);

            List<Map<String, Object>> agentInfos=basePage.getList();
            for(Map<String,Object> agentInfo:agentInfos){
                String handicapAgentId=agentInfo.get("HANDICAP_AGENT_ID_").toString();

                StringBuilder availableGame= new StringBuilder();
                List<Map<String, Object>> gameInfos=haGameSetService.listGameInfo(handicapAgentId);
                for(Map<String,Object> gameInfo:gameInfos){
                    availableGame.append(gameInfo.get("GAME_NAME_")).append(",");
                }
                agentInfo.put("availableGame",availableGame.toString());
                //登录状态
                if(IbmStateEnum.LOGIN.name().equals(agentInfo.get("STATE_"))){
                    agentInfo.put("STATE_","在线");
                }else{
                    agentInfo.put("STATE_","离线");
                }
            }
            data.put("rows", agentInfos);

            data.put("index", pageIndex);
            data.put("total", basePage.getTotalCount());
        } catch (Exception e) {
            log.error("获取代理列表错误", e);
            data.clear();
            data.put("rows", null);
            data.put("index", 0);
            data.put("total", 0);
        }
        return data;
    }
}
