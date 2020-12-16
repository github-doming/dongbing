package com.ibs.connector.core;

import c.a.config.SysConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.uuid.Uuid;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_user_token.entity.IbspUserToken;
import com.ibs.plan.module.cloud.ibsp_user_token.service.IbspUserTokenService;
import org.doming.core.tools.Md5Tool;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.List;

/**
 * @Description: 登出接口
 * @Author: null
 * @Date: 2020-09-27 15:23
 */
public class LogoutAction extends CommCoreAction {
	protected LogoutAction() {
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
			IbspUserTokenService userTokenService=new IbspUserTokenService();
			IbspUserToken userToken=userTokenService.findAppTokenByToken(token);
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

			//云端登出
			logout(platformType);
			//退出该用户所有盘口托管状态
			if (isExitHosting) {
				//会员
				LogoutHmController logoutHmController=new LogoutHmController();
				Date nowTime=new Date();
				List<String> hostingInfo= new IbspHmInfoService().listHostingHmIdByUserId(appUserId);
				for(String handicapMemberId:hostingInfo){
					//用户登出清理数据
					bean=logoutHmController.execute(handicapMemberId,nowTime);
				}
			}
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN, e);
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
			data.put("ip", findServletIp());
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/pc/logout";
			HttpTool.doGet(cloudUrl,HttpTool.paramJson(data));
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN.concat("页面内容信息错误"), e);
		}
	}
}
