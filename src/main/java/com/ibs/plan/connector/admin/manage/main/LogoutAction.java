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
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import com.ibs.plan.module.cloud.ibsp_user_token.service.IbspUserTokenService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpTool;

/**
 * @Description: 退出登录
 * @Author: admin1
 * @Date: 2020/6/28 13:48
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/main/logout",method = HttpConfig.Method.GET)
public class LogoutAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try{
			String userType = new IbspUserService().find(appUserId).getUserType();
			IbspUserTokenService tokenService = new IbspUserTokenService();
			String userId = tokenService.findUserId(token, ChannelTypeEnum.ADMIN, IbsTypeEnum.getUserType(userType));
			if(StringTool.isEmpty(userId)){
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			tokenService.logout(token);

			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("token", token);
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url+"/cloud/user/api/logoutUser";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);
			if (!result.getBoolean("success")) {
				return result;
			}

			bean.success();
		}catch (Exception e){
			log.error("注销登录失败", e);
			throw e;
		}
		return bean;

	}
}
