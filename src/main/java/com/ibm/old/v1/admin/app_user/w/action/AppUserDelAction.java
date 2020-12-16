package com.ibm.old.v1.admin.app_user.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.pc.app_user.t.service.AppAccountService;
import com.ibm.old.v1.pc.app_user.t.service.AppTokenService;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 删除用户
 * @Author: zjj
 * @Date: 2019-08-13 14:37
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppUserDelAction extends BaseAppAction {

	@Override public String run() throws Exception {
		String userId = request.getParameter("id");
		if(StringTool.isEmpty(userId)){
			return CommViewEnum.Default.toString();
		}
		//删除用户信息
		AppUserService userService = new AppUserService();
		userService.delUser(userId);
		//删除账号信息
		AppAccountService accountService = new AppAccountService();
		accountService.delByUserId(userId);
		//删除token信息
		AppTokenService tokenService = new AppTokenService();
		tokenService.delByUserId(userId);

		List<String> msgList = new ArrayList<>(2);
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);


		//删除盘口用户信息
		IbmHandicapUserTService handicapUserTService=new IbmHandicapUserTService();
		List<String> handicapUserIdList= handicapUserTService.findByUserId(userId);

		if(ContainerTool.isEmpty(handicapUserIdList)){
			return CommViewEnum.Default.toString();
		}
		handicapUserTService.delByUserId(userId);
		//删除盘口会员信息
		IbmHandicapMemberTService handicapMemberTService=new IbmHandicapMemberTService();
		List<String> handicapMemberList=handicapMemberTService.findByUserId(userId);

		if(ContainerTool.isEmpty(handicapMemberList)){
			return CommViewEnum.Default.toString();
		}
		handicapMemberTService.delById(handicapMemberList);

		IbmHmGameSetTService hmGameSetTService=new IbmHmGameSetTService();
		hmGameSetTService.delByHmId(handicapMemberList);

		IbmHmSetTService hmSetTService=new IbmHmSetTService();
		hmSetTService.delByHmId(handicapMemberList);
		//TODO,删除方案信息

		//TODO,删除投注信息

		return CommViewEnum.Default.toString();
	}
}
