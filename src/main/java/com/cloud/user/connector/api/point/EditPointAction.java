package com.cloud.user.connector.api.point;

import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.CodeEnum;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.app_user_point.service.AppUserPointService;
import com.cloud.user.app_user_point_rep.service.AppUserPointRepService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 修改用户点数
 * @Author: admin1
 * @Date: 2020/7/7 14:51
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/api/point/editPoint", method = HttpConfig.Method.GET)
public class EditPointAction extends BaseApiAction {
	@Override protected Object execute() throws Exception {
		String userId = StringTool.trimAll(StringTool.getString(jsonData, "userId", ""));
		String useablePoint = StringTool.trimAll(StringTool.getString(jsonData, "useablePoint", ""));
		try {
			if (!StateEnum.OPEN.name().equals(new AppUserService().findState(userId, "USER"))) {
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Date date = new Date();
			AppUserPointService userPointService = new AppUserPointService();
			Map<String, Object> lastRepInfo = userPointService.findLastRepInfo(userId);

			new AppUserPointService().updatePoint(lastRepInfo.get("APP_USER_POINT_ID_").toString(), NumberTool.getInteger(useablePoint),
					lastRepInfo.get("REP_POINT_ID_").toString(),date);

			new AppUserPointRepService().savePointLog(lastRepInfo, userId, NumberTool.getInteger(useablePoint), "修改积分", date);
			bean.success();
		} catch (Exception e) {
			log.error("末次报表信息错误", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}