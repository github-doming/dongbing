package com.cloud.user.connector.api.point;

import c.a.util.core.enums.ReturnCodeEnum;
import com.alibaba.fastjson.JSONArray;
import com.cloud.common.core.BaseApiAction;
import com.cloud.user.app_user_point.service.AppUserPointService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 批量获取用户积分信息
 * @Author: admin1
 * @Date: 2020/7/7 15:44
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/api/point/usersPointInfo", method = HttpConfig.Method.GET)
public class UsersPointAction extends BaseApiAction {
	@Override protected Object execute() throws Exception {
		try {
			JSONArray userIds=new JSONArray();
			if(ContainerTool.notEmpty(jsonData.get("userIds"))){
				userIds=jsonData.getJSONArray("userIds");
			}
			AppUserPointService userPointService = new AppUserPointService();
			Map<String,Object> pointInfos = userPointService.getUsersUseablePoint(userIds);
			bean.success(pointInfos);
		} catch (Exception e) {
			log.error("末次报表信息错误", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
