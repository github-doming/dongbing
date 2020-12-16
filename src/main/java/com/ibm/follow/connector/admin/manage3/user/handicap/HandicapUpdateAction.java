package com.ibm.follow.connector.admin.manage3.user.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
/**
 * @Description: 更新用户盘口
 * @Author: Dongming
 * @Date: 2019-11-11 14:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicap/update", method = HttpConfig.Method.POST)
public class HandicapUpdateAction extends CommAdminAction {

	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapUserId = request.getParameter("HANDICAP_USER_ID_");
		String userCategory = request.getParameter("USER_CATEGORY_");
		String onlineNumberMaxStr = request.getParameter("ONLINE_NUMBER_MAX_");
		int onlineNumberMax = NumberTool.getInteger(onlineNumberMaxStr, 0);
		if (StringTool.isEmpty(handicapUserId, userCategory) || onlineNumberMax < 0) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmTypeEnum category = IbmTypeEnum.valueOf(userCategory);
		try {
			IbmAdminHandicapUserService handicapUserService = new IbmAdminHandicapUserService();
			int onlineCount = NumberTool.getInteger(handicapUserService.getOnlineCount(handicapUserId, category), 0);
			if (onlineNumberMax < onlineCount) {
				bean.putEnum(IbmCodeEnum.IBM_403_MAX_USER_CAPACITY);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName())
					.concat(",更新用户盘口最大容量:").concat(handicapUserId);
			handicapUserService.updateOnlineMax(handicapUserId, category, onlineNumberMax, desc);

			bean.success();
		} catch (Exception e) {
			log.error("更新用户盘口错误", e);
			throw e;
		}
		return bean;
	}
}
