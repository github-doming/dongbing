package com.ibm.follow.connector.admin.manage3.user.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Description: 删除用户盘口账户
 * @Author: Dongming
 * @Date: 2019-11-09 17:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicap/account/delete", method = HttpConfig.Method.POST)
public class HandicapAccountDeleteAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String accountId = request.getParameter("accountId");
		String userCategory = request.getParameter("userCategory");
		if (StringTool.isEmpty(accountId, userCategory)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		IbmTypeEnum category = IbmTypeEnum.valueOf(userCategory);

		try {
			String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName())
					.concat(",删除用户盘口账户:").concat(accountId);
			//放入数据
			List<String> clientIds = new ArrayList<>();
			clientIds.add(accountId);

			// 退出相关客户
			HandicapTool.delete(clientIds, category, desc, new Date());

			bean.success();
		} catch (Exception e) {
			log.error("删除用户盘口账户错误");
			throw e;
		}
		return bean;
	}
}
