package com.ibs.plan.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_event_login.service.IbspEventLoginService;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.server.thread.LoginThread;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 会员登录
 * @Author: null
 * @Date: 2020-03-19 14:19
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/member/login")
public class MemberLoginAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //盘口会员id
        String handicapMemberId= StringTool.getString(dataMap.get("handicapMemberId"), "");

        try{
            IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();
            IbspHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
            if (handicapMember == null) {
                bean.putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            IbspHmInfoService hmInfoService=new IbspHmInfoService();
            String loginState = hmInfoService.findLoginState(handicapMemberId);
            if (StringTool.notEmpty(loginState) && IbsStateEnum.LOGIN.name().equals(loginState)) {
                bean.putEnum(CodeEnum.IBS_403_EXIST_LOGIN);
                bean.putSysEnum(CodeEnum.CODE_403);
                return bean;
            }
            //此账号是否已经被登录
            if (hmInfoService.isLogin(handicapMember.getHandicapId(),handicapMember.getMemberAccount())){
                bean.putEnum(CodeEnum.IBS_403_EXIST_LOGIN);
                bean.putSysEnum(CodeEnum.CODE_403);
                return bean;
            }
            if(IbsStateEnum.LOGIN.name().equals(handicapMember.getOperating())){
                String eventId=new IbspEventLoginService().isExist(handicapMemberId);
                if(StringTool.notEmpty(eventId)){
                    bean.success();
                    return bean;
                }
            }
            saveEventLogin(handicapMember,handicapMemberService);

            bean.success();
        } catch (Exception e) {
            log.error("登录盘口会员失败", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    private void saveEventLogin(IbspHandicapMember handicapMember,IbspHandicapMemberService handicapMemberService) throws Exception {
        Date nowTime = new Date();
		IbspEventLoginService loginService=new IbspEventLoginService();
        //  马上登录
        IbspHandicapItemService handicapItemService=new IbspHandicapItemService();
        Map<String, Object> itemInfo = handicapItemService.findInfo(handicapMember.getHandicapItemId());
        handicapItemService.updateUseTime(handicapMember.getHandicapItemId(),nowTime);

		handicapMember.setOperating(IbsMethodEnum.LOGIN.name());
		handicapMember.setUpdateTime(nowTime);
		handicapMember.setUpdateTimeLong(nowTime.getTime());
		handicapMemberService.update(handicapMember);
        //写入登录事件
        JSONObject content = new JSONObject();
		content.put("METHOD_", IbsMethodEnum.LOGIN.name());
        content.put("HANDICAP_MEMBER_ID_", handicapMember.getIbspHandicapMemberId());
        content.put("MEMBER_ACCOUNT_",handicapMember.getMemberAccount());
        content.put("MEMBER_PASSWORD_",handicapMember.getMemberPassword());
        content.put("HANDICAP_CODE_", HandicapUtil.code(itemInfo.remove("HANDICAP_ID_").toString()));
        content.putAll(itemInfo);
		String eventId = EventThreadDefine.saveLoginEvent(loginService, handicapMember.getIbspHandicapMemberId(),content, nowTime);
		content.put("EVENT_ID_", eventId);
		//重启事务
		CurrentTransaction.transactionCommit();

		//发送登录信息
		Map<String, Object> existInfo= LoginThread.getUsableClient(content);
		if (ContainerTool.isEmpty(existInfo)) {
			log.error("事件:{},开启客户端失败，客户机容量已满,请联系客服人员",eventId);
			content.clear();
			content.put("code", "error");
			content.put("msg", "开启客户端失败，客户机容量已满,请联系客服人员");
			loginService.updateResultByState(eventId,content, IbsStateEnum.FINISH);
			return;
		}
		String clientCode=existInfo.get("CLIENT_CODE_").toString();
		log.info("事件：{},登录结果：{}",eventId, RabbitMqTool.sendMember(content.toString(),clientCode,"manage"));
    }
}
