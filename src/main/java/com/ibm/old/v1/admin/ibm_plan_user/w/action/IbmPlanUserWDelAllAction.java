package com.ibm.old.v1.admin.ibm_plan_user.w.action;


import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;

/**
 * 
 * 
 * @Description: 删除选中消息
 * @date 2019年2月20日 下午2:34:05 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmPlanUserWDelAllAction extends BaseAction{

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		IbmPlanUserTService service = new IbmPlanUserTService();
		service.delAll(ids);
		return CommViewEnum.Default.toString();
	}

}
