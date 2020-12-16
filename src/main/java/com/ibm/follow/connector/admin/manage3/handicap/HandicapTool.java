package com.ibm.follow.connector.admin.manage3.handicap;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.user.AppUserService;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapAgentService;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapMemberService;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.entity.IbmHaUser;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.entity.IbmHmUser;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口用户工具类
 * @Author: Dongming
 * @Date: 2019-11-06 14:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HandicapTool {

	/**
	 * 保存盘口用户
	 *
	 * @param handicapId      盘口主键
	 * @param handicapCode    盘口编码
	 * @param handicapName    盘口名称
	 * @param onlineNumberMax 在线最大数
	 * @param category        盘口类别
	 * @param types           用户类型列表
	 * @param desc            描述
	 * @param nowTime         保存时间
	 */
	public static void save(String handicapId, String handicapCode, String handicapName, int onlineNumberMax,
			IbmTypeEnum category, List<IbmTypeEnum> types, String desc, Date nowTime) throws Exception {
		List<String> userIds = new AppUserService().listIdByTypes(types);
		if (ContainerTool.isEmpty(userIds)) {
			return;
		}
		if (category.equal(IbmTypeEnum.AGENT)) {
			IbmHaUserService haUserService = new IbmHaUserService();
			for (String userId : userIds) {
				IbmHaUser haUser = new IbmHaUser();
				haUser.setAppUserId(userId);
				haUser.setHandicapId(handicapId);
				haUser.setHandicapCode(handicapCode);
				haUser.setHandicapName(handicapName);
				haUser.setOnlineAgentsCount(0);
				haUser.setOnlineNumberMax(onlineNumberMax);
				haUser.setFrequency(0);
				haUser.setCreateTime(nowTime);
				haUser.setCreateTimeLong(System.currentTimeMillis());
				haUser.setUpdateTimeLong(System.currentTimeMillis());
				haUser.setState(IbmStateEnum.OPEN.name());
				haUser.setDesc(desc);
				haUserService.save(haUser);
			}
		} else {
			IbmHmUserService hmUserService = new IbmHmUserService();
			for (String userId : userIds) {
				IbmHmUser hmUser = new IbmHmUser();
				hmUser.setAppUserId(userId);
				hmUser.setHandicapId(handicapId);
				hmUser.setHandicapCode(handicapCode);
				hmUser.setHandicapName(handicapName);
				hmUser.setOnlineMembersCount(0);
				hmUser.setOnlineNumberMax(onlineNumberMax);
				hmUser.setFrequency(0);
				hmUser.setCreateTime(nowTime);
				hmUser.setCreateTimeLong(System.currentTimeMillis());
				hmUser.setUpdateTimeLong(System.currentTimeMillis());
				hmUser.setState(IbmStateEnum.OPEN.name());
				hmUserService.save(hmUser);
			}
		}
	}

	/**
	 * 移除用户的所有数据
	 *
	 * @param appUserId 用户主键
	 */
	public static void delete(String appUserId) throws Exception {
        IbmHaInfoService haInfoService= new IbmHaInfoService();
        IbmHmInfoService hmInfoService= new IbmHmInfoService();

		List<String> clientIds = haInfoService.listHostingHaIdByUserId(appUserId);
		if (ContainerTool.notEmpty(clientIds)) {
            LogoutHaController logoutHaController= new LogoutHaController();
			for (String handicapAgentId : clientIds) {
                logoutHaController.execute(handicapAgentId);
			}
		}
		clientIds = hmInfoService.listHostingHmIdByUserId(appUserId);
		if (ContainerTool.notEmpty(clientIds)) {
            LogoutHmController logoutHmController=new LogoutHmController();
			for (String handicapMemberId : clientIds) {
                logoutHmController.execute(handicapMemberId);
			}
		}
	}

	/**
	 * 移除盘口中的所有数据
	 *
	 * @param handicapId 盘口主键
	 * @param category   盘口类别
	 * @param desc       描述
	 * @param nowTime    保存时间
	 */
	public static void delete(String handicapId, IbmTypeEnum category, String desc, Date nowTime) throws Exception {
		LogoutHaController haController=new LogoutHaController();
		LogoutHmController hmController=new LogoutHmController();
		if (category.equal(IbmTypeEnum.AGENT)) {
			// 退出所有托管盘口代理
			List<String> handicapAgentIds = new IbmHaInfoService().listHostingHaId(handicapId);
			for (String handicapAgentId : handicapAgentIds) {
				haController.execute(handicapAgentId);
			}
			// 删除所有盘口代理
			new IbmAdminHandicapAgentService().delByHandicapId(handicapId, nowTime, desc);
		} else {
			// 退出所有托管盘口会员
			List<String> handicapMemberIds = new IbmHmInfoService().listHostingHmId(handicapId);
			for (String handicapMemberId : handicapMemberIds) {
				hmController.execute(handicapMemberId);
			}
			// 删除所有盘口会员
			new IbmAdminHandicapMemberService().delByHandicapId(handicapId, nowTime, desc);
		}

	}

	/**
	 * 移除盘口中的所有数据
	 *
	 * @param clientIds 客户主键列表
	 * @param category  盘口类别
	 * @param desc      描述
	 * @param nowTime   保存时间
	 */
	public static void delete(List<String> clientIds, IbmTypeEnum category, String desc, Date nowTime)
			throws Exception {
		if (category.equal(IbmTypeEnum.AGENT)) {
			// 退出所有托管盘口代理
            LogoutHaController logoutHaController=new LogoutHaController();
			for (String handicapAgentId : clientIds) {
                logoutHaController.execute(handicapAgentId);
			}
			// 删除所有盘口代理
			new IbmAdminHandicapAgentService().delByHaId(clientIds, nowTime, desc);
		} else {
			// 退出所有托管盘口会员
            LogoutHmController logoutHmController=new LogoutHmController();
			for (String handicapMemberId : clientIds) {
                logoutHmController.execute(handicapMemberId);
			}
			// 删除所有盘口会员
			new IbmAdminHandicapMemberService().delByHmId(clientIds, nowTime, desc);
		}

	}
	/**
	 * 移除盘口中的所有数据
	 *
	 * @param handicapId 盘口主键
	 * @param types      用户类型列表
	 * @param category   盘口类别
	 * @param desc       描述
	 * @param nowTime    移除时间
	 */
	public static void delete(String handicapId, List<IbmTypeEnum> types, IbmTypeEnum category, String desc,
			Date nowTime) throws Exception {
		List<String> userIds = new AppUserService().listIdByTypes(types);
		if (ContainerTool.isEmpty(userIds)) {
			return;
		}
		if (category.equal(IbmTypeEnum.AGENT)) {
			// 退出所有托管盘口代理
			LogoutHaController haController=new LogoutHaController();
			List<String> handicapAgentIds = new IbmHaInfoService().listHostingHaId(handicapId, userIds);
			for (String handicapAgentId : handicapAgentIds) {
				haController.execute(handicapAgentId);
			}
			// 删除所有盘口代理
			new IbmAdminHandicapAgentService().delByHandicapId(handicapId, nowTime, desc);
		} else {
			// 退出所有托管盘口会员
			LogoutHmController hmController=new LogoutHmController();
			List<String> handicapMemberIds = new IbmHmInfoService().listHostingHmId(handicapId, userIds);
			for (String handicapMemberId : handicapMemberIds) {
				hmController.execute(handicapMemberId);
			}
			// 删除所有盘口会员
			new IbmAdminHandicapMemberService().delByHandicapId(handicapId, nowTime, desc);
		}
	}

	/**
	 * 获取类型集合
	 *
	 * @param type 类型
	 * @return 类型集合
	 */
	public static Map<String, Object> getTypeMap(IbmTypeEnum type) {
		Map<String, Object> agent = new HashMap<>(2);
		agent.put("code", type.name());
		agent.put("name", type.getMsg());
		return agent;
	}
}
