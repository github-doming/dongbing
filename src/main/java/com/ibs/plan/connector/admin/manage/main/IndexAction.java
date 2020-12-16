package com.ibs.plan.connector.admin.manage.main;

import c.a.config.SysConfig;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import com.ibs.plan.module.cloud.ibsp_user_token.entity.IbspUserToken;
import com.ibs.plan.module.cloud.ibsp_user_token.service.IbspUserTokenService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

import java.util.Date;

/**
 * 用户主页-判断客户是否拥有访问权限并初始化
 *
 * 	1.ibs、ibm创建的操作员用户全平台通用
 * 			能登录成功的用户只有ADMIN类型
 * 	2.开卡平台创建的用户只能在开卡平台使用
 *
 * @Author: Dongming
 * @Date: 2020-06-16 11:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/main/index", method = HttpConfig.Method.POST)
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
			return bean.put401Data();
		}
		Date nowTime = new Date();
		try {
			//校验本地数据
			IbspUserTokenService userTokenService = new IbspUserTokenService();
			IbspUserService userService = new IbspUserService();
			String userId =userTokenService.findUserId(token,ChannelTypeEnum.ADMIN);
			//校验TOKEN，校验存在
			if (StringTool.notEmpty(userId) && userService.findExist(userId)) {
				return bean.success();
			}
			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("name", name);
			data.put("token", token);
			data.put("channelType", ChannelTypeEnum.ADMIN.name());
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url+"/cloud/user/api/verifyToken";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);
			if (!result.getBoolean("success")) {
				return result;
			}
			userId = result.getJSONObject("data").getString("userId");
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(CodeEnum.IBS_403_ERROR);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			name = result.getJSONObject("data").getString("nickname");
			String userType = new AuthorityService().findUserType(userId);

			//本地没有该用户
			if (StringTool.isEmpty(userType)) {
				//获取该用户的开卡平台角色
				userType = result.getJSONObject("data").getString("userType");
				if (StringTool.isEmpty(userType)) {
					bean.putEnum(CodeEnum.IBS_403_ERROR);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				}
				AuthorityService authorityService = new AuthorityService();
				IbsTypeEnum type = IbsTypeEnum.getType(userType);
				if (type==null) {
					bean.putEnum(CodeEnum.IBS_403_PERMISSION);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				}
				userType = type.name();
				//保存角色信息
				authorityService.saveUserRole(userId, authorityService.findRoleId(userType), appUserId, nowTime);
			}

			//获取tokenId,类型不为user
			String tokenId=userTokenService.findId(userId,ChannelTypeEnum.ADMIN);
			long periodTimeLong = System.currentTimeMillis() + DateTool.getMillisecondsMinutes(30);
			if(StringTool.isEmpty(tokenId)){
				//添加数据
				IbspUserToken userToken = new IbspUserToken();
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
				userToken.setState(IbsStateEnum.OPEN.name());
				userTokenService.save(userToken);
			}else{
				userTokenService.updateValue(tokenId,token,findServletIp(),userType,periodTimeLong,nowTime);
			}


			data.clear();
			data.put("roleCode",userType);
			bean.setData(data);

			if (userService.findExist(userId)) {
				bean.success();
				return bean;
			}
			IbspUser user = new IbspUser();
			user.setAppUserId(userId);
			user.setNickname(name);
			user.setUserType(userType);
			user.setLoginTime(nowTime);
			user.setLoginTimeLong(System.currentTimeMillis());
			user.setCreateTime(nowTime);
			user.setCreateTimeLong(System.currentTimeMillis());
			user.setUpdateTime(nowTime);
			user.setUpdateTimeLong(System.currentTimeMillis());
			user.setState(IbsStateEnum.OPEN.name());
			userService.save(user);

			bean.success();
			return bean;
		} catch (Exception e) {
			log.error("{}用户登录失败，{}", IbsConfig.LOG_SIGN, e.getMessage());
			throw e;
		}
	}
}
