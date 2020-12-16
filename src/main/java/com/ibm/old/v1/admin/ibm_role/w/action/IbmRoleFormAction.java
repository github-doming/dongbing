package com.ibm.old.v1.admin.ibm_role.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_role.w.entity.IbmRoleW;
import com.ibm.old.v1.cloud.ibm_role.w.service.IbmRoleWService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import org.doming.core.tools.StringTool;
/**
 * @Description: 修改页面
 * @Author: zjj
 * @Date: 2019-08-13 14:16
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmRoleFormAction extends BaseAppAction {
	@Override public String run() throws Exception {
		String id = request.getParameter("id");
		if (StringTool.isEmpty(id)) {
			return CommViewEnum.Default.toString();
		}
		IbmRoleWService roleService = new IbmRoleWService();
		IbmRoleW role = roleService.find(id);
		request.setAttribute("s", role);
		request.setAttribute("id", id);
		return CommViewEnum.Default.toString();
	}
}
