package com.ibm.follow.connector.admin.manage3.handicap.item;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
/**
 * @Description: 删除盘口详情
 * @Author: Dongming
 * @Date: 2019-11-07 17:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/item/delete", method = HttpConfig.Method.POST) public class ItemDeleteAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		String handicapItemId = request.getParameter("handicapItemId");
		if (StringTool.isEmpty(handicapItemId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		try {
			String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",删除盘口详情:")
					.concat(handicapItemId);

			IbmAdminHandicapItemService handicapItemService = new IbmAdminHandicapItemService();
			String handicapCategory = handicapItemService.getCategory(handicapItemId);
			if (StringTool.isEmpty(handicapCategory)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			IbmTypeEnum category = IbmTypeEnum.valueOf(handicapCategory);
			List<String> clientIds = handicapItemService.listCitations(handicapItemId, category);

			Date nowTime = new Date();

			if (ContainerTool.notEmpty(clientIds)){
				// 退出相关客户
				HandicapTool.delete(clientIds,category,  desc, nowTime);
			}

			// 删除所有相关信息
			handicapItemService.del(handicapItemId);
			bean.success();
		} catch (Exception e) {
			log.error("删除盘口错误", e);
			throw e;
		}
		return bean;
	}
}
