package com.ibm.follow.connector.admin.manage.user;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

import java.util.Date;

/**
 * @Description: 删除用户信息
 * @Author: Dongming
 * @Date: 2019-11-09 09:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/delete")
public class AppUserDeleteAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String appUserId =  dataMap.getOrDefault("appUserId","").toString();
		if (StringTool.isEmpty(appUserId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",删除用户信息:")
					.concat(appUserId);
			Date nowTime = new Date();

			// 退出相关客户
			HandicapTool.delete(appUserId);

			IbmAdminAppUserService appUserService = new IbmAdminAppUserService();
			// 删除所有盘口信息
			appUserService.delByUserId(appUserId, nowTime, desc);

			// 移除用户信息，账号信息，token信息，点卡信息
            appUserService.delUserInfo(appUserId);

			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("token", token);
			data.put("userId", appUserId);
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url+"/cloud/user/api/delUser";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);
			if (!result.getBoolean("success")) {
				return result;
			}

			bean.success();
		} catch (Exception e) {
			log.error("删除用户信息错误",e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
