package com.ibm.follow.connector.admin.manage3.handicap.list;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapService;
import com.ibm.follow.servlet.cloud.ibm_manage_role.service.IbmManageRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
/**
 * @Description: 删除盘口
 * @Author: Dongming
 * @Date: 2019-11-05 16:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/delete", method = HttpConfig.Method.POST) public class HandicapDeleteAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		String handicapId = request.getParameter("handicapId");
		if (StringTool.isEmpty(handicapId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",删除盘口:").concat(handicapId);
			Date nowTime = new Date();

			IbmAdminHandicapService handicapService = new IbmAdminHandicapService();
			IbmTypeEnum type = IbmTypeEnum.valueOf(handicapService.findType(handicapId));
			IbmTypeEnum category = HandicapUtil.category(handicapId);

			// 退出相关客户
			HandicapTool.delete(handicapId,category,  desc, nowTime);

			// 删除所有相关信息
			handicapService.deleteHandicap(handicapId);

			// 移除角色信息
			new IbmManageRoleService().removeHandicapId(handicapId, type, category, nowTime, desc);

			bean.success();
		} catch (Exception e) {
			log.error("删除盘口错误", e);
			throw e;
		}
		return bean;
	}
}
