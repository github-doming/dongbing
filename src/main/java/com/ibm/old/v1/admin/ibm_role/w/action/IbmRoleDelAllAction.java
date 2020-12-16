package com.ibm.old.v1.admin.ibm_role.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_role.w.service.IbmRoleWService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
/**
 * @Description: 删除选中角色
 * @Author: zjj
 * @Date: 2019-08-13 14:25
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmRoleDelAllAction extends BaseAppAction {
	@Override public String run() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		IbmRoleWService roleWService = new IbmRoleWService();
		roleWService.delAll(ids);
		return CommViewEnum.Default.toString();
	}
}
