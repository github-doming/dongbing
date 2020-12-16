package com.ibs.plan.connector.pc.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.tools.EncryptTool;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_event_login.service.IbspEventLoginService;
import com.ibs.plan.module.cloud.ibsp_event_login_vali.service.IbspEventLoginValiService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.server.thread.LoginThread;
import com.ibs.plan.module.server.thread.ValiLoginThread;
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
 * 会员登录
 *
 * @Author: null
 * @Date: 2020-05-23 17:00
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/handicap/login", method = HttpConfig.Method.POST) public class MemberLoginAction
		extends CommCoreAction {

	private String handicapCode;
	private String handicapMemberId;

	private String handicapItemId;
	private String memberAccount;
	private String memberPassword;

	private long landedTimeLong;

	private String loginCode;
	private Date nowTime = new Date();

	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (!valiParameters()) {
			return bean.put401Data();
		}
		if (super.denyTime()) {
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		try {
			String handicapId = HandicapUtil.id(handicapCode, IbsTypeEnum.MEMBER);
			//判断盘口开关
			if (IbsStateEnum.CLOSE.name().equals(new IbspHandicapService().findState(handicapId))) {
				bean.putEnum(CodeEnum.IBS_403_HANDICAP);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			//用户盘口资源
			if (StringTool.isEmpty(new IbspHmUserService().findId(appUserId, handicapId))) {
				bean.putEnum(CodeEnum.IBS_403_HANDICAP);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			//此账号是否已经被登录
			if (new IbspHmInfoService().isLogin(handicapId, memberAccount)) {
				bean.putEnum(CodeEnum.IBS_403_EXIST_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			//存在密码，需要加密
			if (StringTool.notEmpty(memberPassword)) {
				memberPassword = EncryptTool.encode(EncryptTool.Type.ASE, memberPassword);
			}
			IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();
			String eventId;
			if (StringTool.notEmpty(handicapMemberId)) {
				if (landedTimeLong == 0) {
					loginCode = "login";
					Map<String, Object> memberInfo = handicapMemberService.findInfo(handicapMemberId);
					if(ContainerTool.isEmpty(memberInfo)){
						return bean.put404HandicapMember();
					}
					IbsStateEnum operating = IbsStateEnum.valueOf(memberInfo.remove("OPERATING_").toString());
					switch (operating) {
						case LOGIN:
							//操作状态为login时，不在重新写入新的登录事件（防止重复登录）
							eventId = new IbspEventLoginService().isExist(handicapMemberId);
							if (StringTool.notEmpty(eventId)) {
								break;
							}
						case LOGOUT:
							//当状态为logout的时候，防止出现异常情况
							bean.putEnum(CodeEnum.IBS_403_LOGOUT);
							bean.putSysEnum(CodeEnum.CODE_403);
							return bean;
						default:
							eventId = saveEventLogin(handicapMemberService);
							break;
					}
				} else {
					//定时登录
					handicapMemberService
							.update(handicapMemberId, landedTimeLong, memberAccount, memberPassword, handicapItemId,
									nowTime);
					Map<String, Object> data = new HashMap<>(2);
					data.put("loginCode", "landedLogin");
					data.put("HANDICAP_MEMBER_ID_", handicapMemberId);
					return bean.success(data);
				}
			} else {
				eventId = accountLogin(handicapId, handicapMemberService);
				if (StringTool.isEmpty(eventId)) {
					bean.putEnum(CodeEnum.IBS_403_LOGOUT);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				}
			}
			Map<String, Object> data = new HashMap<>(2);
			data.put("loginCode", loginCode);
			data.put("eventId", eventId);
			bean.success(data);
		} catch (Exception e) {
			log.error("{}会员登录错误，{}", IbsConfig.LOG_SIGN, e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}

	private String accountLogin(String handicapId, IbspHandicapMemberService handicapMemberService)
			throws Exception {
		new IbspHandicapItemService().updateUseTime(handicapItemId, nowTime);
		Map<String, Object> memberInfo = handicapMemberService.findMemberInfo(appUserId, memberAccount, handicapId);
		if (ContainerTool.isEmpty(memberInfo)) {
			// 首次登录
			IbspHandicapMember handicapMember = new IbspHandicapMember();
			handicapMember.setHandicapId(handicapId);
			handicapMember.setHandicapItemId(handicapItemId);
			handicapMember.setAppUserId(appUserId);
			handicapMember.setMemberAccount(memberAccount);
			handicapMember.setMemberPassword(memberPassword);
			handicapMember.setOperating(IbsStateEnum.LOGIN.name());
			handicapMember.setCreateTime(nowTime);
			handicapMember.setCreateTimeLong(System.currentTimeMillis());
			handicapMember.setUpdateTimeLong(System.currentTimeMillis());
			handicapMember.setState(IbsStateEnum.FIRST.name());
			handicapMember.setDesc(this.getClass().getName().concat("，初次登录"));
			handicapMemberId = handicapMemberService.save(handicapMember);
			return saveEventLoginVali();
		} else {
			loginCode = "login";
			handicapMemberId = memberInfo.get("IBSP_HANDICAP_MEMBER_ID_").toString();

			IbsStateEnum operating = IbsStateEnum.valueOf(memberInfo.remove("OPERATING_").toString());
			switch (operating) {
				case LOGIN:
					String eventId = new IbspEventLoginService().isExist(handicapMemberId);
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
			handicapMemberService.update(handicapMemberId, null, null, memberPassword, handicapItemId, nowTime);

			if (IbsStateEnum.FIRST.name().equals(memberInfo.get("STATE_"))) {
				String eventId = new IbspEventLoginValiService().isExist(handicapMemberId);
				if (StringTool.notEmpty(eventId)) {
					return eventId;
				}
				return saveEventLoginVali();
			}
			//写入登录事件
			JSONObject content = new JSONObject();
			content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
			String eventId = EventThreadDefine.saveLoginEvent(new IbspEventLoginService(), handicapMemberId, content, nowTime);
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventId));
			return eventId;
		}
	}

	private String saveEventLoginVali() throws Exception {
		loginCode = "valiLogin";
		//写入验证登录
		JSONObject content = new JSONObject();
		content.put("HANDICAP_MEMBER_ID_", handicapMemberId);

		String eventId=EventThreadDefine.saveLoginValiEvent(handicapMemberId, content, nowTime,this.getClass().getName().concat("，验证登录"));

		CurrentTransaction.transactionCommit();

		ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ValiLoginThread(eventId));

		return eventId;
	}

	private String saveEventLogin(IbspHandicapMemberService handicapMemberService) throws Exception {
		handicapMemberService.update(handicapMemberId, null, memberAccount, memberPassword, handicapItemId, nowTime);
		new IbspHandicapItemService().updateUseTime(handicapItemId, nowTime);
		JSONObject content = new JSONObject();
		content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
		String eventId = EventThreadDefine
				.saveLoginEvent(new IbspEventLoginService(), handicapMemberId, content, nowTime);
		ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventId));
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
