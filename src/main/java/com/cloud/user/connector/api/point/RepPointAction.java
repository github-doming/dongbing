package com.cloud.user.connector.api.point;

import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.CodeEnum;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.app_user_point.service.AppUserPointService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;
/**
 * 末次报表信息
 *
 * @Author: Dongming
 * @Date: 2020-06-24 15:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/api/point/repPoint", method = HttpConfig.Method.GET)
public class RepPointAction extends BaseApiAction {
	@Override protected Object execute() throws Exception {
		String userId = StringTool.trimAll(StringTool.getString(jsonData, "userId", ""));
		try {
			if (!StateEnum.OPEN.name().equals(new AppUserService().findState(userId, "USER"))) {
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String, Object> lastRepInfo = new AppUserPointService().findLastRepInfo(userId);
			bean.success(lastRepInfo);
		} catch (Exception e) {
			log.error("末次报表信息错误", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
