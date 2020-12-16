package com.ibm.follow.servlet.server.core.thread;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.LoginHaController;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户绑定处理线程任务
 * @Author: null
 * @Date: 2020-01-13 11:25
 * @Version: v1.0
 */
public class UserBindThread extends BaseCommThread {

    private List<Map<String, Object>> haInfos;

    public UserBindThread(List<Map<String, Object>> haInfos) {
        this.haInfos=haInfos;
    }

    @Override public String execute(String ignore) throws Exception {
        IbmHmInfoService hmInfoService=new IbmHmInfoService();
        IbmHandicapAgentMemberService handicapAgentMemberService=new IbmHandicapAgentMemberService();
        IbmHandicapMemberService handicapMemberService=new IbmHandicapMemberService();
        IbmClientHmService clientHmService = new IbmClientHmService();
        IbmEventConfigSetService configSetService = new IbmEventConfigSetService();

        IbmHaGameSetService haGameSetService=new IbmHaGameSetService();
        //绑定消息主体
        JSONObject content = new JSONObject();
        content.put("SET_ITEM_", IbmMethodEnum.SET_BIND.name());
        content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
        Date nowTime = new Date();
        //获取用户登录的会员信息
        for(Map<String ,Object> haInfo:haInfos){
            List<String> handicapMemberIds=hmInfoService.listHostingHmIdByUserId(haInfo.get("APP_USER_ID_").toString());
            if(ContainerTool.isEmpty(handicapMemberIds)){
                continue;
            }
            //获取已绑定的会员列表信息
            List<String> bindHmIds=handicapAgentMemberService.listHmId(haInfo.get("HANDICAP_AGENT_ID_").toString());

            //剔除已存在绑定信息的会员，得到未绑定会员列表，进行重新绑定
            handicapMemberIds.removeAll(bindHmIds);
            if (ContainerTool.isEmpty(handicapMemberIds)) {
                continue;
            }
            //获取代理拥有游戏和类型
            Map<String,Object> agentGameInfo=haGameSetService.findGameType(haInfo.get("HANDICAP_AGENT_ID_").toString());

            content.put("HANDICAP_AGENT_ID_", haInfo.get("HANDICAP_AGENT_ID_").toString());
            Map<String,Object> memberInfo;
            for (String handicapMemberId : handicapMemberIds) {
                memberInfo=handicapMemberService.findMemberAccountInfo(handicapMemberId);
                // 保存数据
                handicapAgentMemberService.save(haInfo.get("APP_USER_ID_").toString(), haInfo.get("HANDICAP_AGENT_ID_").toString(),
                        handicapMemberId, memberInfo.get("HANDICAP_CODE_").toString(),memberInfo.get("MEMBER_ACCOUNT_").toString(), nowTime);

                LoginHaController.bindUserMember(memberInfo,agentGameInfo,content,configSetService,clientHmService);
            }
        }
        return null;
    }

}
