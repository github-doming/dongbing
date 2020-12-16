package com.cloud.user.connector.api.point;

import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.app_user_point.service.AppUserPointService;
import com.cloud.user.app_user_point_rep.service.AppUserPointRepService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 激活卡信息
 *
 * @Author: Dongming
 * @Date: 2020-06-20 16:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/api/point/activate", method = HttpConfig.Method.GET) public class ActivateCardAction
		extends BaseApiAction {
	@Override protected Object execute() throws Exception {
		String userId = StringTool.trimAll(StringTool.getString(jsonData, "userId", ""));
		String cardPassword = StringTool.trimAll(StringTool.getString(jsonData, "cardPassword", ""));
		int point = NumberTool.getInteger(jsonData, "point", 0);
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(userId, cardPassword) || point <= 0) {
			return bean.put401Data();
		}
		try {
			if (!StateEnum.OPEN.name().equals( new AppUserService().findState(userId, "USER"))) {
				bean.putEnum(CodeEnum.CLOUD_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			Date nowTime = new Date();

			AppUserPointService userPointService = new AppUserPointService();
			AppUserPointRepService userPointRepService = new AppUserPointRepService();
			Map<String, Object> lastRepInfo = userPointService.findLastRepInfo(userId);
			String title = String.format("使用充值卡：%s", cardPassword);
			if (ContainerTool.notEmpty(lastRepInfo)) {
				String repId = userPointRepService.save(lastRepInfo, userId, point, title, nowTime);
				userPointService.update(lastRepInfo.get("APP_USER_POINT_ID_").toString(), point, repId, nowTime);
			} else {
				String repId = userPointRepService.save(new HashMap<>(), userId, point, title, nowTime);
				userPointService.save(userId,repId,point,nowTime);
			}
			bean.success(NumberTool.doubleT(lastRepInfo.get("BALANCE_T_")) + point );
		} catch (Exception e) {
			log.error("激活卡信息失败，{}", e.getMessage());
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
