package com.ibm.follow.connector.admin.manage3.user.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 获取用户盘口信息
 * @Author: Dongming
 * @Date: 2019-11-09 14:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicap/info", method = HttpConfig.Method.GET)
public class HandicapInfoAction extends CommAdminAction {

	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapUserId = request.getParameter("handicapUserId");
		String userCategory = request.getParameter("userCategory");
		if (StringTool.isEmpty(handicapUserId, userCategory)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmTypeEnum category = IbmTypeEnum.valueOf(userCategory);
		try {
			IbmAdminHandicapUserService handicapUserService = new IbmAdminHandicapUserService();

			Map<String, Object> info = handicapUserService.findInfo(handicapUserId, category);
			List<Map<String, Object>> itemInfos = handicapUserService.listItemInfo(handicapUserId, category);

			Map<String, Object> data = new HashMap<>(2);
			data.put("info", info);
			data.put("itemInfos", ContainerTool.notEmpty(itemInfos) ? itemInfos : null);

			bean.success(data);
		} catch (Exception e) {
			log.error("获取用户盘口信息错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
