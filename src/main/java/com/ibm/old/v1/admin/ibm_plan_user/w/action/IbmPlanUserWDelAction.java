package com.ibm.old.v1.admin.ibm_plan_user.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @Description: 删除消息
 * @date 2019年2月19日 下午2:09:40 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmPlanUserWDelAction extends BaseAction{

	@Override
	public String execute() throws Exception {
		String planUserId = request.getParameter("id");
		IbmPlanUserTService service = new IbmPlanUserTService();
		service.del(planUserId);
		List<String> msgList = new ArrayList<>(2);
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);

		return CommViewEnum.Default.toString();
	}

}
