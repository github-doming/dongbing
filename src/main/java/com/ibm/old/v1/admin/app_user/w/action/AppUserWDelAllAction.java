package com.ibm.old.v1.admin.app_user.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;

/**
 * 
 * 
 * @Description: 删除选中用户
 * @date 2019年2月20日 下午2:34:05 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class AppUserWDelAllAction extends BaseAction{

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		AppUserService service = new AppUserService();
		service.delAllUser(ids);
		return CommViewEnum.Default.toString();
	}

}
