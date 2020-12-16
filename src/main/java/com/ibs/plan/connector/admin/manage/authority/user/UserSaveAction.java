package com.ibs.plan.connector.admin.manage.authority.user;

import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.Map;

/**
 * 操作员新增
 *
 * @Author: Dongming
 * @Date: 2020-04-06 16:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/user/add")
public class UserSaveAction extends CommAdminAction {

	private String userName, userAccount, userPassWord, roleId, state;
	private Date nowTime = new Date();

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		if (valiParameters()) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		String regExpAccount = "[a-zA-Z0-9]{6,20}$";
		String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
		if (!userAccount.matches(regExpAccount) || !userPassWord.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		try {

			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> role = authorityService.findRole(roleId);
			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("userId", appUserId);
			data.put("token", token);
			data.put("channelType", ChannelTypeEnum.ADMIN);
			data.put("channelUserType", role.get("APP_GROUP_CODE_"));
			data.put("userAccount", userAccount);
			data.put("userPassWord", userPassWord);
			data.put("state", IbsStateEnum.OPEN.name());
			data.put("tenantCode", IbsConfig.TENANT_CODE);
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/registerUser";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);

			if (!result.getBoolean("success")) {
				return result;
			}
			JSONObject resultData = result.getJSONObject("data");
			String userId = resultData.getString("userId");
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(CodeEnum.IBS_403_ERROR);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}


			//初始化本地用户信息
			IbspUser user = new IbspUser();
			user.setAppUserId(userId);
			user.setNickname(resultData.getString("userName"));
			user.setUserType(role.get("APP_GROUP_CODE_"));
			user.setTenantCode(IbsTypeEnum.SYS.name());
			user.setCreateTime(new Date());
			user.setCreateTimeLong(System.currentTimeMillis());
			user.setUpdateTimeLong(System.currentTimeMillis());
			user.setState(IbsStateEnum.OPEN.name());
			new IbspUserService().save(user);

			//保存角色信息
			authorityService.saveUserRole(userId, roleId, appUserId, nowTime);

			//添加充值卡管理员信息
			//校验云接口数据
			data = new JSONObject();
			data.put("userId", userId);
			data.put("token", token);
			data.put("userType", role.get("APP_GROUP_CODE_"));
			time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			cloudUrl = url + "/cloud/user/api/initCardUser";
			html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			result = JSON.parseObject(html);

			if (!result.getBoolean("success")) {
				return result;
			}

			return bean.success();
		} catch (Exception e) {
			log.error("操作员新增 错误");
			throw e;
		}
	}


	private boolean valiParameters() {
		userName = StringTool.getString(dataMap, "userName", null);
		userAccount = StringTool.getString(dataMap, "userAccount", null);
		userPassWord = StringTool.getString(dataMap, "userPassWord", null);
		roleId = StringTool.getString(dataMap, "roleId", null);
		state = StringTool.getString(dataMap, "state", null);
		return StringTool.isEmpty(userName, userAccount, userPassWord, roleId, state);
	}
}
