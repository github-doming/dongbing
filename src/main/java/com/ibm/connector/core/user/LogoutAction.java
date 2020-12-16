package com.ibm.connector.core.user;

import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.uuid.Uuid;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_user_token.entity.IbmUserToken;
import com.ibm.follow.servlet.cloud.ibm_user_token.service.IbmUserTokenService;
import org.doming.core.tools.Md5Tool;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.List;

/**
 * @Description: 注销登录
 * @Author: Dongming
 * @Date: 2019-08-28 19:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LogoutAction extends CommCoreAction {
	public LogoutAction() {
		super.isTime = false;
	}
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			JSONObject data=JSON.parseObject(json);
			boolean isExitHosting = (boolean) data.getOrDefault("isExitHosting", false);
			String platformType = data.getOrDefault("platformType", "").toString();
			// AppToken
			IbmUserTokenService userTokenService=new IbmUserTokenService();
			IbmUserToken userToken=userTokenService.findAppTokenByToken(token);
			if (userToken == null) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return this.returnJson(bean);
			}
			// 更新Token
			String tokenNew = Uuid.create().toString();
			userToken.setValue(tokenNew);
			userToken.setState(UserStateEnum.LOGOUT.getCode());
			userToken.setUpdateTime(new Date());
			userToken.setUpdateTimeLong(System.currentTimeMillis());
			userTokenService.update(userToken);
			logout(platformType);
			//退出该用户所有盘口托管状态
			if (isExitHosting) {
				/*
				 * 查询出该用户所有的托管的账户
				 * 登出清理信息
				 * 登出账户
				 */
				//会员
				List<String> hostingInfo = new IbmHmInfoService().listHostingHmIdByUserId(appUserId);
                LogoutHmController logoutHmController=new LogoutHmController();
				for (String hostingId : hostingInfo){
					//用户登出清理数据
                    logoutHmController.execute(hostingId);
				}

				//代理
				hostingInfo =  new IbmHaInfoService().listHostingHaIdByUserId(appUserId);
                LogoutHaController logoutHaController=new LogoutHaController();
				for (String hostingId : hostingInfo){
					//用户登出清理数据
                    logoutHaController.execute(hostingId);
				}
			}
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN, e);
			throw e;
		}
		return bean;
	}
	private void logout(String platformType){
		//校验云接口数据
		try {
			JSONObject data = new JSONObject();
			data.put("token", token);
			data.put("cmd", platformType);
			data.put("ip",  findServletIp());
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/pc/logout";
			HttpTool.doGet(cloudUrl,HttpTool.paramJson(data));
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("页面内容信息错误"), e);
		}
	}
}
