package com.ibm.follow.connector.admin.manage3.user.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;
/**
 * @Description: 获取用户盘口列表
 * @Author: Dongming
 * @Date: 2019-11-09 11:08
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicap/list")
public class HandicapListAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserId = request.getParameter("appUserId");
		String userCategory = request.getParameter("userCategory");
		if (StringTool.isEmpty(appUserId, userCategory)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmTypeEnum category = IbmTypeEnum.valueOf(userCategory);

		try {
			//盘口用户信息
			List<Map<String, Object>> handicapUserInfos = new IbmAdminHandicapUserService().listInfoByUserId(category,appUserId);

			bean.success(handicapUserInfos);
		} catch (Exception e) {
			log.error("获取用户盘口列表错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
