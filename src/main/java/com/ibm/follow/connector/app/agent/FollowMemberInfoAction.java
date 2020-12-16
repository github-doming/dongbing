package com.ibm.follow.connector.app.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.connector.pc.home.AgentOpenHandicapAction;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 跟投会员信息
 * @Author: null
 * @Date: 2019-11-28 17:45
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/agent/followMemberInfo", method = HttpConfig.Method.GET)
public class FollowMemberInfoAction extends CommCoreAction {

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
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Map<String, Object> haSetInfo = new IbmHaSetService().findShowInfo(handicapAgentId);
            //跟随会员列表转换数据类型
            AgentOpenHandicapAction.turnFormat(haSetInfo);

            bean.success(haSetInfo);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("获取盘口代理的跟投会员信息错误"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
