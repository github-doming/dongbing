package com.ibm.old.v1.admin.ibm_plan.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.cloud.ibm_role.w.service.IbmRoleWService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.wck.init.InitUserDataInfo;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * @Description: 删除方案
 * @Author: zjj
 * @Date: 2019-08-13 13:53
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmPlanDelAction extends BaseAppAction {
	@Override public String run() throws Exception {
		String planId = request.getParameter("id");
		IbmPlanTService planTService = new IbmPlanTService();
		planTService.del(planId);
		List<String> msgList = new ArrayList<>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);

		//获取所有角色的方案权限
		IbmRoleWService roleService = new IbmRoleWService();
		List<Map<String, Object>> list = roleService.findAllPlanId();
		for (Map<String, Object> roleInfo : list) {
			List<String> planIdList = new ArrayList<>(Arrays.asList(roleInfo.get("PLAN_ID_").toString().split(",")));
			if (planIdList.contains(planId)) {
				planIdList.remove(planId);
			} else {
				continue;
			}
			//更新角色方案权限
			roleService.updatePlan(roleInfo.get("IBM_ROLE_ID_").toString(), planIdList);
		}
		//获取所有用户信息
		AppUserService appUserService = new AppUserService();
		List<String> userIdList = appUserService.findAllId();
		InitUserDataInfo dataInfo = new InitUserDataInfo();
		for (String userId : userIdList) {
			//初始化盘口用户信息
			dataInfo.initPlan(userId);
		}
		return CommViewEnum.Default.toString();
	}
}
