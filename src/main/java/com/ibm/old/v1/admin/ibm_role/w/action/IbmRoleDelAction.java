package com.ibm.old.v1.admin.ibm_role.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_role.w.service.IbmRoleWService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 删除角色
 * @Author: zjj
 * @Date: 2019-08-13 14:24
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmRoleDelAction extends BaseAppAction {
	@Override public String run() throws Exception {
		String roleId = request.getParameter("id");
		IbmRoleWService roleWService = new IbmRoleWService();
		roleWService.del(roleId);
		List<String> msgList = new ArrayList<>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
