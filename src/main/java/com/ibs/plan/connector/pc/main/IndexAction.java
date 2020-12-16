package com.ibs.plan.connector.pc.main;

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
import com.ibs.plan.module.cloud.core.controller.init.RegisterInitController;
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
 * @Author: Dongming
 * @Date: 2020-06-16 11:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/index", method = HttpConfig.Method.POST)
public class IndexAction extends CommCoreAction {
	public IndexAction() {
		super.isTime = false;
	}
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
		try {
			//校验本地数据
			IbspUserTokenService userTokenService = new IbspUserTokenService();
			IbspUserService userService = new IbspUserService();
			String userId = userTokenService.findUserId(token, ChannelTypeEnum.PC, IbsTypeEnum.USER);
			//校验TOKEN，校验存在
			if (StringTool.notEmpty(userId) && userService.findExist(userId, IbsTypeEnum.USER)) {
				return bean.success();
			}
			JSONObject result=getUserId(name,token);
			if (!result.getBoolean("success")) {
				return result;
			}
			userId = result.getJSONObject("data").getString("userId");
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(CodeEnum.IBS_403_ERROR);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			String tokenId=userTokenService.findId(userId,ChannelTypeEnum.PC, IbsTypeEnum.USER);

			Date nowTime = new Date();
			long periodTimeLong = System.currentTimeMillis() + DateTool.getMillisecondsMinutes(30);
			if(StringTool.isEmpty(tokenId)){
				//添加数据
				IbspUserToken userToken = new IbspUserToken();
				userToken.setAppUserId(userId);
				userToken.setValue(token);
				userToken.setUserType(IbsTypeEnum.USER);
				userToken.setChannelType(ChannelTypeEnum.PC);
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
				userTokenService.updateValue(tokenId,token,findServletIp(),periodTimeLong,nowTime);
			}

			if (userService.findExist(userId, IbsTypeEnum.USER)) {
				return bean.success();
			}

			IbspUser user = new IbspUser();
			user.setAppUserId(userId);
			user.setNickname(name);
			user.setUserType(IbsTypeEnum.USER);
			user.setLoginTime(nowTime);
			user.setLoginTimeLong(System.currentTimeMillis());
			user.setCreateTime(nowTime);
			user.setCreateTimeLong(System.currentTimeMillis());
			user.setUpdateTime(nowTime);
			user.setUpdateTimeLong(System.currentTimeMillis());
			user.setState(IbsStateEnum.OPEN.name());
			userService.save(user);

			//注册初始化信息
			new RegisterInitController().execute(userId);
			return bean.success();
		} catch (Exception e) {
			log.error("{}用户登录失败，{}", IbsConfig.LOG_SIGN, e.getMessage());
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
			return bean;
		}
	}

	private JSONObject getUserId(String name, String token) {
		try {
			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("name", name);
			data.put("token", token);
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url=SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/verifyToken";
			String html = HttpTool.doGet(cloudUrl,HttpTool.paramJson(data));
			return JSON.parseObject(html);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "页面获取用户信息错误", e);
			return new JSONObject();
		}
	}
}
