package com.ibm.old.v1.admin.ibm_role.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_role.w.entity.IbmRoleW;
import com.ibm.old.v1.cloud.ibm_role.w.service.IbmRoleWService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.wck.init.InitUserDataInfo;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
/**
 * @Description: 保存角色
 * @Author: zjj
 * @Date: 2019-08-13 14:18
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmRoleSaveAction extends BaseAppAction {
	@Override public String run() throws Exception {
		//角色id
		String id = request.getParameter("IBM_ROLE_ID_");
		//角色名称
		String roleName = request.getParameter("ROLE_NAME_");
		//角色code
		String roleCode = request.getParameter("ROLE_CODE_");
		//角色等级
		String roleLevel = request.getParameter("ROLE_LEVEL_");
		//盘口会员最大在线数量
		String onlineNumberMax = request.getParameter("ONLINE_NUMBER_MAX_");
		//盘口id
		String handicapId = request.getParameter("HANDICAP_ID_");
		//方案id
		String planId = request.getParameter("PLAN_ID_");

		Date nowTime = new Date();
		IbmRoleW roleW = new IbmRoleW();
		IbmRoleWService roleWService = new IbmRoleWService();
		roleW.setIbmRoleId(id);
		roleW.setRoleName(roleName);
		roleW.setRoleCode(roleCode);
		roleW.setRoleLevel(Integer.parseInt(roleLevel));
		roleW.setOnlineNumberMax(Integer.parseInt(onlineNumberMax));
		roleW.setHandicapId(handicapId);
		roleW.setPlanId(planId);
		roleW.setState("OPEN");
		roleW.setUpdateTime(nowTime);
		roleW.setUpdateTimeLong(nowTime.getTime());
		if(StringTool.isEmpty(id)){
			roleW.setCreateTime(nowTime);
			roleW.setCreateTimeLong(nowTime.getTime());
			roleWService.save(roleW);
		}else{
			IbmRoleW ibmRoleW = roleWService.find(id);
			roleW.setCreateTime(ibmRoleW.getCreateTime());
			roleW.setCreateTimeLong(ibmRoleW.getCreateTimeLong());
			roleWService.update(roleW);
		}

		//获取所有用户信息
		AppUserService appUserService = new AppUserService();
		List<String> userIdList = appUserService.findAllId();
		for (String userId : userIdList) {
			//初始化用户信息
			new InitUserDataInfo(userId);
		}
		return CommViewEnum.Default.toString();
	}
}
