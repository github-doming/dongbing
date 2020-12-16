package com.ibm.follow.connector.pc.customer.login;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.cloud.ibm_event_login_vali.entity.IbmEventLoginVali;
import com.ibm.follow.servlet.cloud.ibm_event_login_vali.service.IbmEventLoginValiService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.entity.IbmHandicapAgent;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import com.ibm.follow.servlet.module.event_new.LoginThread;
import com.ibm.follow.servlet.module.event_new.LoginValiThread;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 代理登录
 * @Author: Dongming
 * @Date: 2019-09-02 18:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/login/agent", method = HttpConfig.Method.POST)
public class AgentLoginAction
        extends CommCoreAction {
    private String handicapCode;

    private String handicapAgentId;
    private long landedTimeLong;

    private String handicapItemId;
    private String agentAccount;
    private String agentPassword;

    private String loginCode;

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if (!valiParameters()) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
        try {
			String handicapId =HandicapUtil.id(handicapCode,IbmTypeEnum.AGENT);
            if (StringTool.isEmpty(handicapId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            //判断盘口开关
            IbmHandicapService handicapService=new IbmHandicapService();
            if(IbmStateEnum.CLOSE.name().equals(handicapService.findState(handicapId))){
                bean.putEnum(IbmCodeEnum.IBM_403_MAX_HANDICAP_CAPACITY);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            String haUserId = new IbmHaUserService().findId(appUserId, handicapId);
            if (StringTool.isEmpty(haUserId)) {
                bean.putEnum(IbmCodeEnum.IBM_403_MAX_USER_CAPACITY);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            //此账号是否已经被登录
            if (new IbmHaInfoService().isLogin(handicapId, agentAccount)) {
                bean.putEnum(IbmCodeEnum.IBM_403_EXIST_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            //存在密码，需要加密
            if (StringTool.notEmpty(agentPassword)) {
                agentPassword = EncryptTool.encode(EncryptTool.Type.ASE, agentPassword);
            }
            IbmHandicapAgentService handicapAgentService = new IbmHandicapAgentService();
            String eventId;
            if (StringTool.notEmpty(handicapAgentId)) {
                if (landedTimeLong == 0) {
                    loginCode = "login";
					handicapAgentService.update(handicapAgentId, null, agentAccount, agentPassword, handicapItemId);
                    Map<String, Object> agentInfo = handicapAgentService.findInfo(handicapAgentId, appUserId);
					if(ContainerTool.isEmpty(agentInfo)){
						bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
						bean.putSysEnum(IbmCodeEnum.CODE_404);
						return super.returnJson(bean);
					}
                    IbmStateEnum operating = IbmStateEnum.valueOf(agentInfo.remove("OPERATING_").toString());
                    switch (operating) {
                        case LOGIN:
                            //操作状态为login时，不在重新写入新的登录事件（防止重复登录）
                            eventId=new IbmEventLoginService().isExist(handicapAgentId,IbmTypeEnum.AGENT);
                            if (StringTool.isEmpty(eventId)) {
                                eventId=saveEventLogin(handicapAgentService);
                            }
                            break;
                        case LOGOUT:
                            //当状态为logout的时候，防止出现异常情况
                            bean.putEnum(IbmCodeEnum.IBM_403_LOGOUT);
                            bean.putSysEnum(IbmCodeEnum.CODE_403);
                            return bean;
                        default:
                            eventId=saveEventLogin(handicapAgentService);
                            break;
                    }
                } else {
                    //定时登录
                    handicapAgentService.update(handicapAgentId, landedTimeLong, agentAccount, agentPassword, handicapItemId);
                    Map<String, Object> data = new HashMap<>(2);
                    data.put("loginCode", "landedLogin");
                    data.put("HANDICAP_AGENT_ID_", handicapAgentId);
                    return bean.success(data);
                }
            } else {
                eventId = accountLogin(handicapId, haUserId, handicapAgentService);
            }
            Map<String, Object> data = new HashMap<>(2);
            data.put("loginCode", loginCode);
            data.put("eventId", eventId);
            bean.setData(data);
            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("验证登录失败"),e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    private String saveEventLogin(IbmHandicapAgentService handicapAgentService) throws Exception {
        handicapAgentService.update(handicapAgentId, null, agentAccount, agentPassword, handicapItemId);
		new IbmHandicapItemService().updateUseTime(handicapItemId);
        //写入登录事件
        JSONObject content = new JSONObject();
        content.put("HANDICAP_AGENT_ID_", handicapAgentId);
        String eventId = EventThreadDefine.saveAgentLoginEvent(content, new Date(),
                this.getClass().getName().concat("，登录"), new IbmEventLoginService(),handicapAgentId);
        ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventId,IbmTypeEnum.AGENT));
        return eventId;
    }

    private String accountLogin(String handicapId, String haUserId, IbmHandicapAgentService handicapAgentService) throws Exception {
		new IbmHandicapItemService().updateUseTime(handicapItemId);
        Map<String,Object> agentInfo = handicapAgentService.findAgentInfo(appUserId, agentAccount, handicapId);
        Date nowTime = new Date();
        if (StringTool.isEmpty(agentInfo)) {
            // 首次登录
            IbmHandicapAgent handicapAgent = new IbmHandicapAgent();
            handicapAgent.setHaUserId(haUserId);
            handicapAgent.setHandicapId(handicapId);
            handicapAgent.setHandicapItemId(handicapItemId);
            handicapAgent.setAppUserId(appUserId);
            handicapAgent.setHandicapCode(handicapCode);
            handicapAgent.setAgentAccount(agentAccount);
            handicapAgent.setAgentPassword(agentPassword);
            handicapAgent.setOperating(IbmStateEnum.LOGIN.name());
            handicapAgent.setCreateTime(nowTime);
            handicapAgent.setCreateTimeLong(System.currentTimeMillis());
            handicapAgent.setUpdateTimeLong(System.currentTimeMillis());
            handicapAgent.setState(IbmStateEnum.FIRST.name());
            handicapAgent.setDesc(this.getClass().getName().concat("，初次登录"));
            handicapAgentId = handicapAgentService.save(handicapAgent);

            //添加验证登录事件
            return saveEventLoginVali();
        } else {
            loginCode = "login";
            handicapAgentId=agentInfo.get("IBM_HANDICAP_AGENT_ID_").toString();
            IbmStateEnum operating = IbmStateEnum.valueOf(agentInfo.remove("OPERATING_").toString());
            switch (operating) {
                case LOGIN:
                    String eventId = new IbmEventLoginService().isExist(handicapAgentId, IbmTypeEnum.AGENT);
                    if (StringTool.notEmpty(eventId)) {
                        return eventId;
                    }
                    break;
                case LOGOUT:
                    return null;
                default:
                    break;
            }
            // 多次登录
            handicapAgentService.update(handicapAgentId, agentPassword,handicapItemId, nowTime);

            if(IbmStateEnum.FIRST.name().equals(agentInfo.get("STATE_"))){
                String eventId=new IbmEventLoginValiService().isExist(handicapAgentId,IbmTypeEnum.AGENT);
                if(StringTool.notEmpty(eventId)){
                    return eventId;
                }
                return saveEventLoginVali();
            }
            //写入登录事件-
            JSONObject content = new JSONObject();
            content.put("HANDICAP_AGENT_ID_", handicapAgentId);
            content.remove("ROW_NUM");
            String eventId = EventThreadDefine.saveAgentLoginEvent(content, new Date(),
                    this.getClass().getName().concat("，登录"), new IbmEventLoginService(),handicapAgentId);

            ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventId,IbmTypeEnum.AGENT));
            return eventId;
        }
    }

    private String saveEventLoginVali() throws Exception {
        loginCode = "valiLogin";
        //写入验证登录
        JSONObject content = new JSONObject();
        content.put("HANDICAP_AGENT_ID_", handicapAgentId);
        content.remove("ROW_NUM");

        IbmEventLoginVali eventLoginVali = new IbmEventLoginVali();
        eventLoginVali.setCustomerId(handicapAgentId);
        eventLoginVali.setCustomerType(IbmTypeEnum.AGENT.name());
        eventLoginVali.setEventContent(content);
        eventLoginVali.setEventState(IbmStateEnum.BEGIN.name());
        eventLoginVali.setExecNumber(0);
        eventLoginVali.setCreateTime(new Date());
        eventLoginVali.setCreateTimeLong(System.currentTimeMillis());
        eventLoginVali.setUpdateTimeLong(System.currentTimeMillis());
        eventLoginVali.setState(IbmStateEnum.OPEN.name());
        eventLoginVali.setDesc(this.getClass().getName().concat("，验证登录"));
        String eventId = new IbmEventLoginValiService().save(eventLoginVali);

		CurrentTransaction.transactionCommit();

        ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginValiThread(eventId));

        return eventId;
    }

    private boolean valiParameters() {
        handicapCode = dataMap.getOrDefault("HANDICAP_CODE_", "").toString();
        if (StringTool.isEmpty(handicapCode)) {
            return false;
        }
        handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
        landedTimeLong = NumberTool.getLong(dataMap.get("LANDED_TIME_"));

        agentAccount = dataMap.getOrDefault("AGENT_ACCOUNT_", "").toString().replace(" ", "");
        agentPassword = dataMap.getOrDefault("AGENT_PASSWORD_", "").toString().replace(" ", "");
        handicapItemId = dataMap.getOrDefault("HANDICAP_ITEM_ID_", "").toString();

        return StringTool.notEmpty(handicapAgentId) || StringTool.notEmpty(handicapItemId, agentAccount, agentPassword);
    }
}
