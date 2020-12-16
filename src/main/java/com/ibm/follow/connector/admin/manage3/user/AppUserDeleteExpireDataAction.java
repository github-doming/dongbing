package com.ibm.follow.connector.admin.manage3.user;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;

import java.util.Date;
import java.util.List;

/**
 * @Description: 清理超过60天未登陆的用户信息
 * @Author: wwj
 * @Date: 2019-11-13 09:55
 * @Email:
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/deleteExpireData") public class AppUserDeleteExpireDataAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			Date nowTime = new Date();
			Date expireDate = DateTool.getBeforeDays(nowTime,60);

			IbmAdminAppUserService appUserService = new IbmAdminAppUserService();

			List<String> userIds = appUserService.listExpireUser(expireDate);
			for (String appUserId : userIds){
				String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",删除用户信息:")
					.concat(appUserId);
				// 删除所有盘口信息
				appUserService.delByUserId(appUserId, nowTime, desc);

				// 移除用户信息
				appUserService.del(appUserId);
			}

			bean.success();
		} catch (Exception e) {
			log.error("删除用户信息错误");
			throw e;
		}
		return bean;
	}

}
