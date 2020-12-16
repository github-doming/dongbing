package com.ibm.follow.connector.app.agent.game.refresh;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONArray;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import net.sf.json.JSONObject;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 定时刷新代理信息
 * @Author: null
 * @Date: 2019-12-18 11:12
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/agent/refresh/info", method = HttpConfig.Method.GET)
public class HandicapInfoAction extends CommCoreAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
        if (StringTool.isEmpty(handicapAgentId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            String handicapId = new IbmHandicapAgentService().findHandicapId(handicapAgentId, appUserId);
            if (StringTool.isEmpty(handicapId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Map<String,Object> account =new IbmHandicapAgentService().findInfo(handicapAgentId,appUserId);
            if (ContainerTool.isEmpty(account)){
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Map<String, Object> haInfo = new IbmHaInfoService().findAgentInfo(handicapAgentId);
            if (ContainerTool.isEmpty(haInfo)) {
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            haInfo.put("AGENT_ACCOUNT_",account.get("AGENT_ACCOUNT_"));
            haInfo.remove("ROW_NUM");

            Map<String, Object> data = new HashMap<>(5);
            //用户是否登录
            data.put("haInfo", haInfo);

            //获取盘口代理跟投类型
            IbmHaSetService haSetService=new IbmHaSetService();
            String followMemberType=haSetService.findFollowMemberType(handicapAgentId);
            data.put("FOLLOW_MEMBER_TYPE_",followMemberType);
            Map<String, Object> haSetInfo = new IbmHaSetService().findShowInfo(handicapAgentId);
            if(IbmTypeEnum.ALL.name().equals(haSetInfo.get("FOLLOW_MEMBER_TYPE_"))){
                data.put("followAmount", JSONArray.parseArray(haSetInfo.get("MEMBER_LIST_INFO_").toString()).size());
            }else{
                data.put("followAmount", JSONObject.fromObject(haSetInfo.get("FOLLOW_MEMBER_LIST_INFO_")).size());
            }
            //盘口游戏信息获取
            List<Map<String, Object>> gameInfos = new IbmHaGameSetService().listGameInfos(handicapAgentId);
            data.put("gameInfo", gameInfos);

            bean.success(data);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("定时刷新代理信息错误"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
