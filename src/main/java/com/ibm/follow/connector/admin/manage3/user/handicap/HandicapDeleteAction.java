package com.ibm.follow.connector.admin.manage3.user.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
/**
 * @Description: 删除用户盘口
 * @Author: Dongming
 * @Date: 2019-11-09 17:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicap/delete", method = HttpConfig.Method.POST)
public class HandicapDeleteAction extends CommAdminAction {
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
			String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",删除用户盘口:")
					.concat(handicapUserId);

			IbmAdminHandicapUserService handicapUserService = new IbmAdminHandicapUserService();
			List<String> clientIds = handicapUserService.listCitations(handicapUserId, category);

			Date nowTime = new Date();
			if (ContainerTool.notEmpty(clientIds)){
				// 退出相关客户
				HandicapTool.delete(clientIds,category,  desc, nowTime);
			}

			// 删除所有相关信息
			handicapUserService.delete(handicapUserId,category,  desc, nowTime);

			bean.success();
		} catch (Exception e) {
			log.error("删除用户盘口错误");
			throw e;
		}
		return bean;
	}
}
