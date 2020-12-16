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
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
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
 * @Description: 验证登录
 * @Author: Dongming
 * @Date: 2019-09-02 16:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/login/member", method = HttpConfig.Method.POST)
public class MemberLoginAction
        extends CommCoreAction {

    private String handicapCode;

    private String handicapMemberId;
    private long landedTimeLong;

    private String handicapItemId;
    private String memberAccount;
    private String memberPassword;

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
        if (super.denyTime()) {
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
        try {
			String handicapId =HandicapUtil.id(handicapCode,IbmTypeEnum.MEMBER);
            if (StringTool.isEmpty(handicapId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            //判断盘口开关
            IbmHandicapService handicapService = new IbmHandicapService();
            if (IbmStateEnum.CLOSE.name().equals(handicapService.findState(handicapId))) {
                bean.putEnum(IbmCodeEnum.IBM_403_MAX_HANDICAP_CAPACITY);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }

            String hmUserId = new IbmHmUserService().findId(appUserId, handicapId);
            if (StringTool.isEmpty(hmUserId)) {
                bean.putEnum(IbmCodeEnum.IBM_403_MAX_USER_CAPACITY);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            //此账号是否已经被登录
            if (new IbmHmInfoService().isLogin(handicapId, memberAccount)) {
                bean.putEnum(IbmCodeEnum.IBM_403_EXIST_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            //存在密码，需要加密
            if (StringTool.notEmpty(memberPassword)) {
                memberPassword = EncryptTool.encode(EncryptTool.Type.ASE, memberPassword);
            }
            IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
            String eventId;
            if (StringTool.notEmpty(handicapMemberId)) {
                if (landedTimeLong == 0) {
                    loginCode = "login";
					handicapMemberService.update(handicapMemberId, null, memberAccount, memberPassword, handicapItemId);
                    Map<String, Object> memberInfo = handicapMemberService.findInfo(handicapMemberId);
					if(ContainerTool.isEmpty(memberInfo)){
						bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
						bean.putSysEnum(IbmCodeEnum.CODE_404);
						return super.returnJson(bean);
					}
                    IbmStateEnum operating = IbmStateEnum.valueOf(memberInfo.remove("OPERATING_").toString());
                    switch (operating) {
                        case LOGIN:
                            //操作状态为login时，不在重新写入新的登录事件（防止重复登录）
                            eventId = new IbmEventLoginService().isExist(handicapMemberId, IbmTypeEnum.MEMBER);
                            if (StringTool.isEmpty(eventId)) {
                                eventId = saveEventLogin(handicapMemberService);
                            }
                            break;
                        case LOGOUT:
                            //当状态为logout的时候，防止出现异常情况
                            bean.putEnum(IbmCodeEnum.IBM_403_LOGOUT);
                            bean.putSysEnum(IbmCodeEnum.CODE_403);
                            return bean;
                        default:
                            eventId = saveEventLogin(handicapMemberService);
                            break;
                    }
                } else {
                    loginCode = "landedLogin";
                    //定时登录
                    handicapMemberService.update(handicapMemberId, landedTimeLong, memberAccount, memberPassword, handicapItemId);
                    Map<String, Object> data = new HashMap<>(2);
                    data.put("loginCode", "landedLogin");
                    data.put("HANDICAP_MEMBER_ID_", handicapMemberId);
                    return bean.success(data);
                }
            } else {
                eventId = accountLogin(handicapId, hmUserId, handicapMemberService);
                if(StringTool.isEmpty(eventId)){
                    bean.putEnum(IbmCodeEnum.IBM_403_LOGOUT);
                    bean.putSysEnum(IbmCodeEnum.CODE_403);
                    return bean;
                }
            }
            Map<String, Object> data = new HashMap<>(2);
            data.put("loginCode", loginCode);
            data.put("eventId", eventId);
            bean.setData(data);
            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("验证登录失败")+e.getMessage());
            bean.error(e.getMessage());
        }
        return bean;
    }

    private String saveEventLogin(IbmHandicapMemberService handicapMemberService) throws Exception {
        //  马上登录
        handicapMemberService.update(handicapMemberId, null, memberAccount, memberPassword, handicapItemId);
		new IbmHandicapItemService().updateUseTime(handicapItemId);
        //写入登录事件
        JSONObject content = new JSONObject();
        content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
        String eventId = EventThreadDefine.saveMemberLoginEvent(content, new Date(), this.getClass().getName().concat("，登录"),
                new IbmEventLoginService(), handicapMemberId);
        ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventId,IbmTypeEnum.MEMBER));

        return eventId;
    }

    /**
     * 登录
     *
     * @param handicapId 盘口主键
     * @param hmUserId   会员用户主键
     * @return 事件主键
     */
    private String accountLogin(String handicapId, String hmUserId, IbmHandicapMemberService handicapMemberService) throws Exception {
		new IbmHandicapItemService().updateUseTime(handicapItemId);
        Map<String, Object> memberInfo = handicapMemberService.findMemberInfo(appUserId, memberAccount, handicapId);
        Date nowTime = new Date();
        if (ContainerTool.isEmpty(memberInfo)) {
            // 首次登录
            IbmHandicapMember handicapMember = new IbmHandicapMember();
            handicapMember.setHmUserId(hmUserId);
            handicapMember.setHandicapId(handicapId);
            handicapMember.setHandicapItemId(handicapItemId);
            handicapMember.setAppUserId(appUserId);
            handicapMember.setHandicapCode(handicapCode);
            handicapMember.setMemberAccount(memberAccount);
            handicapMember.setMemberPassword(memberPassword);
            handicapMember.setOperating(IbmStateEnum.LOGIN.name());
            handicapMember.setCreateTime(nowTime);
            handicapMember.setCreateTimeLong(System.currentTimeMillis());
            handicapMember.setUpdateTimeLong(System.currentTimeMillis());
            handicapMember.setState(IbmStateEnum.FIRST.name());
            handicapMember.setDesc(this.getClass().getName().concat("，初次登录"));
            handicapMemberId = handicapMemberService.save(handicapMember);
            //添加验证登录事件
            return saveEventLoginVali();
        } else {
            loginCode = "login";
            handicapMemberId = memberInfo.get("IBM_HANDICAP_MEMBER_ID_").toString();

            IbmStateEnum operating = IbmStateEnum.valueOf(memberInfo.remove("OPERATING_").toString());
            switch (operating) {
                case LOGIN:
                    String eventId = new IbmEventLoginService().isExist(handicapMemberId, IbmTypeEnum.MEMBER);
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
            handicapMemberService.update(handicapMemberId, memberPassword, handicapItemId, nowTime);

            if (IbmStateEnum.FIRST.name().equals(memberInfo.get("STATE_"))) {
                String eventId = new IbmEventLoginValiService().isExist(handicapMemberId, IbmTypeEnum.MEMBER);
                if (StringTool.notEmpty(eventId)) {
                    return eventId;
                }
                return saveEventLoginVali();
            }
            //写入登录事件
            JSONObject content = new JSONObject();
            content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
            content.remove("ROW_NUM");
            String eventId = EventThreadDefine.saveMemberLoginEvent(content, nowTime, this.getClass().getName().concat("，登录"),
                    new IbmEventLoginService(), handicapMemberId);
            ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventId,IbmTypeEnum.MEMBER));
            return eventId;
        }
    }

    private String saveEventLoginVali() throws Exception {
        loginCode = "valiLogin";
        //写入验证登录
        JSONObject content = new JSONObject();
        content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
        content.remove("ROW_NUM");

        IbmEventLoginVali eventLoginVali = new IbmEventLoginVali();
        eventLoginVali.setCustomerId(handicapMemberId);
        eventLoginVali.setCustomerType(IbmTypeEnum.MEMBER.name());
        eventLoginVali.setEventContent(content);
        eventLoginVali.setEventState(IbmStateEnum.BEGIN.name());
        eventLoginVali.setExecNumber(0);
        eventLoginVali.setCreateTime(new Date());
        eventLoginVali.setCreateTimeLong(System.currentTimeMillis());
        eventLoginVali.setUpdateTimeLong(System.currentTimeMillis());
        eventLoginVali.setState(IbmStateEnum.OPEN.name());
        eventLoginVali.setDesc(this.getClass().getName().concat("，验证登录"));
        String eventId =  new IbmEventLoginValiService().save(eventLoginVali);
		CurrentTransaction.transactionCommit();

        ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginValiThread(eventId));
        return eventId;
    }

    private boolean valiParameters() {
        handicapCode = dataMap.getOrDefault("HANDICAP_CODE_", "").toString();
        if (StringTool.isEmpty(handicapCode)) {
            return false;
        }
        handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        landedTimeLong = NumberTool.getLong(dataMap.get("LANDED_TIME_"));

        memberAccount = dataMap.getOrDefault("MEMBER_ACCOUNT_", "").toString().replace(" ", "");
        memberPassword = dataMap.getOrDefault("MEMBER_PASSWORD_", "").toString().replace(" ", "");
        handicapItemId = dataMap.getOrDefault("HANDICAP_ITEM_ID_", "").toString();

        return StringTool.notEmpty(handicapMemberId, memberAccount) || StringTool
                .notEmpty(handicapItemId, memberAccount, memberPassword);

    }
}
