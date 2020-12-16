package com.ibm.follow.connector.admin.manage.user;

import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.core.controller.init.RegisterInitController;
import com.ibm.follow.servlet.cloud.ibm_exp_role.entity.IbmExpRole;
import com.ibm.follow.servlet.cloud.ibm_exp_role.service.IbmExpRoleService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.entity.IbmExpUser;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_user.entity.IbmUser;
import com.ibm.follow.servlet.cloud.ibm_user.service.IbmUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 添加用户
 * @Author: null
 * @Date: 2020-03-14 14:03
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/save", method = HttpConfig.Method.POST)
public class AppUserSaveAction extends CommAdminAction {
	private String accountName;
	private String password;
	private String endTime;
	private String useablePoint;
	private String agentUsable;
	private String memberUsable;
	private String gameUsable;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (valiParameters()) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		String regExpAccount = "[a-zA-Z0-9]{6,20}$";
		String regExpPwd = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}";
		if (!accountName.matches(regExpAccount) || !password.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		try {

			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("userId", adminUserId);
			data.put("token", token);
			data.put("channelType", ChannelTypeEnum.PC);
			data.put("userAccount", accountName);
			data.put("userPassWord", password);
			data.put("state", IbmStateEnum.OPEN.name());
			data.put("tenantCode", IbmMainConfig.TENANT_CODE);
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/registerUser";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);

			if (!result.getBoolean("success")) {
				return result;
			}
			JSONObject resultData = result.getJSONObject("data");
			String userId = resultData.getString("userId");
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			// 注册用户
			Date nowTime = new Date();
			IbmUser user = new IbmUser();
			user.setAppUserId(userId);
			user.setNickname(accountName);
			user.setUserType(IbmTypeEnum.USER);
			user.setCreateTime(nowTime);
			user.setCreateTimeLong(System.currentTimeMillis());
			user.setUpdateTime(nowTime);
			user.setUpdateTimeLong(System.currentTimeMillis());
			user.setState(IbmStateEnum.OPEN.name());
			new IbmUserService().save(user);

			//初始化用户信息
			initUserInfo(adminUserId, nowTime);


			bean.success();
		} catch (Exception e) {
			log.error("注册用户失败，", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}

	private void initUserInfo(String appUserId, Date nowTime) throws Exception {
		RegisterInitController controller = new RegisterInitController();
		IbmExpRoleService expRoleService = new IbmExpRoleService();
		IbmExpRole expRole = expRoleService.findByCode(IbmTypeEnum.CHARGE);

		//初始化用户角色信息,暂定为用户收费等级为1
		IbmExpUser expUser = new IbmExpUser();
		expUser.setAppUserId(appUserId);
		expUser.setExpRoleId(expRole.getIbmExpRoleId());
		expUser.setAvailableGame(gameUsable);
		expUser.setAgentAvailableHandicap(agentUsable);
		expUser.setAgentOnlineMax(expRole.getAgentOnlineMax());
		expUser.setAgentOnline(0);
		expUser.setMemberAvailableHandicap(memberUsable);
		expUser.setMemberOnlineMax(expRole.getMemberOnlineMax());
		expUser.setMemberOnline(0);
		expUser.setCreateUser(adminUser.getUserName());
		expUser.setCreateTime(nowTime);
		expUser.setCreateTimeLong(System.currentTimeMillis());
		expUser.setUpdateTime(nowTime);
		expUser.setUpdateTimeLong(System.currentTimeMillis());
		expUser.setState(IbmStateEnum.OPEN.name());
		new IbmExpUserService().save(expUser);

		//会员盘口
		Set<String> memberInfo = new HashSet<>(Arrays.asList(memberUsable.split(",")));
		controller.initUserMemberHandicap(appUserId, memberInfo, expRole.getHmOnlineMax());

		//代理盘口
		Set<String> agentInfo = new HashSet<>(Arrays.asList(agentUsable.split(",")));
		controller.initUserAgentHandicap(appUserId, agentInfo, expRole.getHaOnlineMax());

		//时长信息
		new RegisterInitController().initPointAndTime(appUserId, nowTime, NumberTool.getLong(useablePoint),
				Long.parseLong(endTime));
	}

	private boolean valiParameters() {
		//用户名
		accountName = dataMap.getOrDefault("accountName", "").toString();
		//密码
		password = dataMap.getOrDefault("password", "").toString();
		//点数到期时间
		endTime = dataMap.getOrDefault("endTime", "").toString();
		//点数
		useablePoint = dataMap.getOrDefault("useablePoint", "").toString();
		//代理可用盘口
		agentUsable = dataMap.getOrDefault("agentUsable", "").toString();
		//会员可用盘口 memberUsable
		memberUsable = dataMap.getOrDefault("memberUsable", "").toString();
		//可用游戏（代理和会员共同使用）
		gameUsable = dataMap.getOrDefault("gameUsable", "").toString();

		return StringTool.isEmpty(accountName, password, endTime, useablePoint, agentUsable, memberUsable, gameUsable);
	}
}
