package com.ibm.follow.connector.admin.authority.user;

import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.entity.IbmManageTime;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import com.ibm.follow.servlet.cloud.ibm_user.entity.IbmUser;
import com.ibm.follow.servlet.cloud.ibm_user.service.IbmUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.*;
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
@ActionMapping(value = "/ibm/admin/authority/user/add")
public class UserSaveAction extends CommAdminAction {

	private String userName, userAccount, userPassWord, roleId, state;


	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		if (valiParameters()) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		String regExpAccount = "[a-zA-Z0-9]{6,20}$";
		String regExpPwd = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}";
		if (!userAccount.matches(regExpAccount) || !userPassWord.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		Date nowTime = new Date();
		try {
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> role = authorityService.findRole(roleId);
			if (ContainerTool.isEmpty(role)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			int permGrade = NumberTool.getInteger(role, "PERMISSION_GRADE_", 90);
			// 该请求不能创建代理级别用户
			if (permGrade == 90) {
				bean.putEnum(IbmCodeEnum.IBM_403_USER_ADD);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			// 创建者的权限码
			int userGrade = NumberTool.getInteger(authorityService.findUserRole(adminUserId), "PERMISSION_GRADE_", 90);
			// 后台管理员越级创建代理或者股东
			if (permGrade > 80 && userGrade < 50) {
				bean.putEnum(IbmCodeEnum.IBM_403_USER_ADD);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			String userType = role.remove("APP_GROUP_CODE_").toString();
			//注册用户
			JSONObject data = new JSONObject();
			data.put("userId", adminUserId);
			data.put("token", token);
			data.put("channelType", ChannelTypeEnum.ADMIN);
			data.put("channelUserType", userType);
			data.put("userAccount", userAccount);
			data.put("userPassWord", userPassWord);
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

			//添加充值卡管理员信息
			data = new JSONObject();
			data.put("userId", userId);
			data.put("token", token);
			data.put("userType", userType);
			time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			cloudUrl = url + "/cloud/user/api/initCardUser";
			html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			result = JSON.parseObject(html);

			if (!result.getBoolean("success")) {
				return result;
			}

			//保存角色信息
			authorityService.saveUserRole(userId, roleId, adminUser.getUserId(), nowTime);

			//为管理用户添加一条默认时间数据，避免时间验证失败
			IbmManageTime manageTime = new IbmManageTime();
			manageTime.setAppUserId(userId);
			manageTime.setRepTimeId(IbmTypeEnum.CARD_ADMIN.name());
			manageTime.setStartTime(nowTime);
			manageTime.setStartTimeLong(System.currentTimeMillis());
			manageTime.setEndTime(DateTool.getDate("2200-12-31"));
			manageTime.setEndTimeLong(System.currentTimeMillis());
			manageTime.setCreateTime(nowTime);
			manageTime.setCreateTimeLong(System.currentTimeMillis());
			manageTime.setUpdateTimeLong(System.currentTimeMillis());
			manageTime.setState(IbmStateEnum.OPEN.name());
			new IbmManageTimeService().save(manageTime);

			//保存本地数据
			IbmUser user = new IbmUser();
			user.setAppUserId(userId);
			user.setNickname(userName);
			user.setUserType(userType);
			user.setTenantCode(IbmMainConfig.TENANT_CODE);
			user.setCreateTime(nowTime);
			user.setCreateTimeLong(System.currentTimeMillis());
			user.setUpdateTimeLong(System.currentTimeMillis());
			user.setState(IbmStateEnum.OPEN.name());
			new IbmUserService().save(user);

			bean.success();
		} catch (Exception e) {
			log.error("操作员新增 错误");
			bean.error(e.getMessage());
		}
		return bean;
	}

	private boolean valiParameters() {
		userName = StringTool.getString(dataMap, "userName", null);
		userAccount = StringTool.getString(dataMap, "userAccount", null);
		userPassWord = StringTool.getString(dataMap, "userPassWord", null);
		roleId = StringTool.getString(dataMap, "roleId", null);
		state = StringTool.getString(dataMap, "state", null);
		return StringTool.isEmpty(userName, userAccount, userPassWord, state);
	}
}
