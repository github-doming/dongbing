package com.ibm.follow.connector.admin.manage3.handicap.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author wwj
 * @Description 盘口代理列表
 * @Date 17:41 2019/11/8
 * @Version: v1.0
 **/
@ActionMapping(value = "/ibm/admin/manage/handicap/agent/list", method = HttpConfig.Method.GET)
public class AgentListAction extends CommAdminAction {

    @Override  public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }
        String handicapCode = request.getParameter("HANDICAP_CODE_");

        String key = request.getParameter("key");
        try {
            Map<String, Object> data = new HashMap<>(4);
            //盘口
            List<Map<String, Object>> handicaps = new ArrayList<>();
            for (HandicapUtil.Code code : HandicapUtil.codes()) {
                Map<String, Object> handicap = new HashMap<>(2);
                handicap.put("code", code.name());
                handicap.put("name", code.getName());
                handicaps.add(handicap);
            }
            data.put("handicaps", handicaps);
            String handicapId = null;

            if (StringTool.notEmpty(handicapCode)) {
                handicapId = HandicapUtil.id(handicapCode, IbmTypeEnum.AGENT);
            }

            IbmAdminHandicapAgentService handicapAgentService = new IbmAdminHandicapAgentService();
            List<Map<String, Object>>  handicapAgents = handicapAgentService.listHandicapAgents(IbmTypeEnum.AGENT, handicapId, key);
            handicapAgents.forEach(map->{
                if("LOGIN".equals(map.get("STATE_"))){
                    map.put("STATE_","在线");
                }else{
                    map.put("STATE_","离线");
                }
            });
            data.put("handicapAgents", handicapAgents);

            //回显数据
            data.put("HANDICAP_CODE_", handicapCode);
            data.put("key", key);

            return new ModelAndView("/pages/com/ibm/admin/manager/handicap/agent.jsp", data);
        }catch (Exception e) {
            log.error("盘口代理列表错误", e);
            return new JsonResultBeanPlus().error(e.getMessage());
        }
    }
}
