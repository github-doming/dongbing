package com.ibm.old.v1.admin.ibm_plan_user.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_plan_user.t.entity.IbmPlanUserT;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;

/**
 * 
 * 
 * @Description: 查询角色信息
 * @date 2019年2月20日 上午11:08:17 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmPlanUserWFormAction extends BaseAppAction{
	@Override
	public String run() throws Exception {
		String id = request.getParameter("id");
		if (id != null) {
			IbmPlanUserTService planUserTService = new IbmPlanUserTService();
			IbmPlanUserT planUser = planUserTService.find(id);

			request.setAttribute("s", planUser);
			request.setAttribute("id", id);
		}
		return CommViewEnum.Default.toString();
	}

}
