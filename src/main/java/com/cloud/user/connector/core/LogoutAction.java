package com.cloud.user.connector.core;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.BaseMvcData;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.user.app_token.service.AppTokenService;
import com.cloud.user.app_user.entity.AppUser;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.user_login_log.entity.UserLoginLog;
import com.cloud.user.user_login_log.service.UserLoginLogService;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * 注销登录
 *
 * @Author: Dongming
 * @Date: 2020-06-15 18:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LogoutAction extends BaseMvcData {
	@Override public Object run() throws Exception {
		super.findDateMap();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(token)) {
			return bean.put401Data();
		}
		try {
			JSONObject data=JSON.parseObject(json);
			String platformType= data.getOrDefault("cmd","").toString();
			String userIp= data.getOrDefault("ip",findServletIp()).toString();
			// 找到AppUser
			AppTokenService tokenService = new AppTokenService();
			String userId = tokenService.findUserIdByToken(token);
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}
			AppUser appUser=new AppUserService().find(userId);
			if(appUser==null){
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}
			tokenService.logout(token);

			//添加登出日志信息
			saveLog(appUser.getAppUserId(),appUser.getAppUserName(),platformType,userIp);
			bean.success();
		} catch (Exception e) {
			log.error("注销登录失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private void saveLog(String appUserId,String accountName,String platformType,String userIp) throws Exception {
		Date nowTime=new Date();
		// 保存登录日志
		UserLoginLog loginLog = new UserLoginLog();
		if("CARD".equalsIgnoreCase(platformType)){
			Map<String, Object> cardAdminInfo = new CardAdminService().findUserInfo(appUserId,"");
			loginLog.setUserPath(cardAdminInfo.get("USER_PATH_"));
		}
		loginLog.setUserId(appUserId);
		loginLog.setUserName(accountName);
		loginLog.setChannelType(platformType);
		loginLog.setOpertContent(StateEnum.LOGOUT.name());
		loginLog.setIp(userIp);
		loginLog.setCreateTime(nowTime);
		loginLog.setCreateTimeLong(System.currentTimeMillis());
		loginLog.setUpdateTime(nowTime);
		loginLog.setUpdateTimeLong(System.currentTimeMillis());
		loginLog.setState(StateEnum.OPEN.name());
		new UserLoginLogService().save(loginLog);
	}
}
