package com.cloud.user.connector.openapi;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_user_point.service.AppUserPointService;
import com.cloud.user.connector.core.BaseUserAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;
/**
 * 对外API 用户积分
 *
 * @Author: Dongming
 * @Date: 2020-06-24 15:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/out/userPoint", method = HttpConfig.Method.GET) public class UserPointAction
		extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			AppUserPointService pointService = new AppUserPointService();
			double point = pointService.findUseablePoint(userId);
			bean.success(point);
		} catch (Exception e) {
			log.error("检查是否存在用户名失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
