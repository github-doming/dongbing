package com.ibm.follow.connector.admin.main;

import c.a.config.SysConfig;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_user.entity.IbmUser;
import com.ibm.follow.servlet.cloud.ibm_user.service.IbmUserService;
import com.ibm.follow.servlet.cloud.ibm_user_token.entity.IbmUserToken;
import com.ibm.follow.servlet.cloud.ibm_user_token.service.IbmUserTokenService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.Date;

/**
 * 用户主页-判断客户是否拥有访问权限并初始化
 * <p>
 * 1.ibs、ibm创建的操作员用户全平台通用
 * 能登录成功的用户只有ADMIN类型
 * 2.开卡平台创建的用户只能在开卡平台使用
 *
 * @Author: Dongming
 * @Date: 2020-06-16 11:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/main/index", method = HttpConfig.Method.POST)
public class IndexAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);

		String name = StringTool.trimAll(StringTool.getString(jsonData, "name", ""));
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(name, token)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		Date nowTime = new Date();
		try {
			//校验本地数据
			IbmUserTokenService userTokenService = new IbmUserTokenService();
			IbmUserService userService = new IbmUserService();
			String userId = userTokenService.findUserId(token, ChannelTypeEnum.ADMIN);
			//校验TOKEN，校验存在
			if (StringTool.notEmpty(userId) && userService.findExist(userId)) {
				return bean.success();
			}
			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("name", name);
			data.put("token", token);
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/verifyToken";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);
			if (!result.getBoolean("success")) {
				return result;
			}
			userId = result.getJSONObject("data").getString("userId");
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			name = result.getJSONObject("data").getString("nickname");

			String userType = new AuthorityService().findUserType(userId);

			//本地没有该用户
			if(StringTool.isEmpty(userType)){
				//获取该用户的开卡平台角色
				userType = "ADMIN";
				if(StringTool.isEmpty(userType)){
					bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
					bean.putSysEnum(IbmCodeEnum.CODE_403);
					return bean;
				}
				AuthorityService authorityService = new AuthorityService();
				IbmTypeEnum type = IbmTypeEnum.valueOf(userType);

				userType = type.name();
				//保存角色信息
				authorityService.saveUserRole(userId, authorityService.findRoleId(userType), appUserId, nowTime);
			}

			long periodTimeLong = System.currentTimeMillis() + DateTool.getMillisecondsMinutes(30);
			//添加数据
			IbmUserToken userToken = new IbmUserToken();
			userToken.setAppUserId(userId);
			userToken.setValue(token);
			userToken.setUserType(userType);
			userToken.setChannelType(ChannelTypeEnum.ADMIN);
			userToken.setIp(findServletIp());
			userToken.setPeriodTime(new Date(periodTimeLong));
			userToken.setPeriodTimeLong(periodTimeLong);
			userToken.setCreateTime(nowTime);
			userToken.setCreateTimeLong(System.currentTimeMillis());
			userToken.setUpdateTime(nowTime);
			userToken.setUpdateTimeLong(System.currentTimeMillis());
			userToken.setState(IbmStateEnum.OPEN.name());
			userTokenService.save(userToken);


			data.clear();
			data.put("roleCode", userType);
			bean.setData(data);

			if (userService.findExist(userId)) {
				bean.success();
				return bean;
			}
			IbmUser user = new IbmUser();
			user.setAppUserId(userId);
			user.setNickname(name);
			user.setUserType(userType);
			user.setLoginTime(nowTime);
			user.setLoginTimeLong(System.currentTimeMillis());
			user.setCreateTime(nowTime);
			user.setCreateTimeLong(System.currentTimeMillis());
			user.setUpdateTime(nowTime);
			user.setUpdateTimeLong(System.currentTimeMillis());
			user.setState(IbmStateEnum.OPEN.name());
			userService.save(user);

			bean.success();

		} catch (Exception e) {
			log.error("{}用户登录失败，{}", IbmMainConfig.LOG_SIGN, e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}
}
