package com.ibm.follow.connector.app.home;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 点击代理
 * @Author: null
 * @Date: 2019-11-23 16:32
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/home/clickAgent", method = HttpConfig.Method.GET)
public class AgentClickAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try {
            Map<String, Object> data = new HashMap<>(2);

            IbmHaUserService haUserService = new IbmHaUserService();
            IbmHaInfoService haInfoService = new IbmHaInfoService();
            // 代理盘口
            List<Map<String, Object>> agentHandicapInfo = haUserService.listUserHandicap(appUserId);

            List<Map<String, Object>> offlineInfo;
            List<Map<String, Object>> onlineInfo;
            for (Map<String, Object> map : agentHandicapInfo) {
                map.remove("ROW_NUM");
                String handicapCode = map.get("HANDICAP_CODE_").toString();
                String handicapId =HandicapUtil.id(handicapCode,IbmTypeEnum.AGENT);
                String onlineAgentIds = haUserService.findOnlineAgentIds(appUserId, handicapId);
                if (StringTool.isEmpty(onlineAgentIds)) {
                    onlineInfo=new ArrayList<>();
                    offlineInfo = haInfoService.listOfflineInfo(appUserId, handicapId, null);
                }else{
                    //已托管代理
                    onlineInfo = haInfoService.listOnlineInfoByHaIds(onlineAgentIds.split(","));
                    //未托管代理
                    offlineInfo = haInfoService.listOfflineInfo(appUserId, handicapId,
                            ContainerTool.getValSet4Key(onlineInfo, "HANDICAP_AGENT_ID_"));
                }
                map.put("onHostingInfo", onlineInfo);
                map.put("offHostingInfo", offlineInfo);
            }
            data.put("agentInfo", agentHandicapInfo);

            bean.success();
            bean.setData(data);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("点击代理错误"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
