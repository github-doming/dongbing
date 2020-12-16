package com.ibm.follow.connector.admin.manage.user.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.entity.IbmHandicapAgent;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import com.ibm.follow.servlet.module.event_new.LoginThread;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 代理登录
 * @Author: null
 * @Date: 2020-03-21 13:47
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/agent/login")
public class AgentLoginAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        //盘口代理id
        String handicapAgentId =  dataMap.getOrDefault("handicapAgentId","").toString();

        try{
            IbmHandicapAgentService handicapAgentService=new IbmHandicapAgentService();
            IbmHandicapAgent handicapAgent=handicapAgentService.find(handicapAgentId);
            if(handicapAgent==null){
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHaInfoService haInfoService=new IbmHaInfoService();
            String loginState = haInfoService.findLoginState(handicapAgentId);
            if (StringTool.notEmpty(loginState) && IbmStateEnum.LOGIN.name().equals(loginState)) {
                bean.putEnum(IbmCodeEnum.IBM_403_EXIST_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            //此账号是否已经被登录
            if (haInfoService.isLogin(handicapAgent.getHandicapId(),handicapAgent.getAgentAccount())){
                bean.putEnum(IbmCodeEnum.IBM_403_EXIST_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            if(IbmStateEnum.LOGIN.name().equals(handicapAgent.getOperating())){
                String eventId=new IbmEventLoginService().isExist(handicapAgentId, IbmTypeEnum.AGENT);
                if(StringTool.notEmpty(eventId)){
                    bean.success();
                    return bean.toJsonString();
                }
            }
            saveEventLogin(handicapAgent);

            bean.success();
        } catch (Exception e) {
            log.error("登录盘口代理失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    private void saveEventLogin(IbmHandicapAgent handicapAgent) throws Exception {
        IbmHandicapItemService handicapItemService=new IbmHandicapItemService();
        Map<String, Object> itemInfo = handicapItemService.findInfo(handicapAgent.getHandicapItemId());

        handicapItemService.updateUseTime(handicapAgent.getHandicapItemId());

        //写入登录事件
        JSONObject content = new JSONObject();
        content.put("HANDICAP_AGENT_ID_", handicapAgent.getIbmHandicapAgentId());
        content.put("AGENT_ACCOUNT_",handicapAgent.getAgentAccount());
        content.put("AGENT_PASSWORD_",handicapAgent.getAgentPassword());
        content.putAll(itemInfo);
        String eventId = EventThreadDefine.saveAgentLoginEvent(content, new Date(),
                this.getClass().getName().concat("，登录"), new IbmEventLoginService(),handicapAgent.getIbmHandicapAgentId());
        ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventId, IbmTypeEnum.AGENT));
    }
}
