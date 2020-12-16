package com.ibm.follow.servlet.cloud.ibm_manage_role.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_manage_role.entity.IbmManageRole;
import org.apache.commons.collections.CollectionUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBM_角色 的服务类
 *
 * @author Robot
 */
public class IbmManageRoleService extends BaseServicePlus {

	/**
	 * 保存IBM_角色对象数据
	 *
	 * @param entity IbmManageRole对象数据
	 */
	public String save(IbmManageRole entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 更新IbmManageRole实体信息
	 *
	 * @param entity IBM_角色实体
	 */
	public void update(IbmManageRole entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_manage_role表主键查找IbmManageRole实体
	 *
	 * @param id ibm_manage_point 主键
	 * @return IBM_用户点数实体
	 */
	public IbmManageRole find(String id) throws Exception {
		return (IbmManageRole) this.dao.find(IbmManageRole.class, id);

	}

	/**
	 * 获取会员可开启盘口ids和会员最大在线数量
	 *
	 * @param userId 用户id
	 * @return 可开启盘口ids和会员最大在线数量
	 */
	public Map<String, Object> findMemberHandicapById(String userId) throws SQLException {
		String sql = "SELECT r.MEMBER_HANDICAP_ID_,r.ONLINE_HM_NUMBER_MAX_ from app_user u LEFT JOIN ibm_manage_role r ON u.APP_USER_TYPE_ = r.ROLE_CODE_ where u.APP_USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		return dao.findMap(sql, parameterList);
	}

	/**
	 * 获取代理可开启盘口ids和代理最大在线数量
	 *
	 * @param userId 用户id
	 * @return 可开启盘口ids和代理最大在线数量
	 */
	public Map<String, Object> findAgentHandicapById(String userId) throws SQLException {
		String sql = "SELECT r.AGENT_HANDICAP_ID_,r.ONLINE_HA_NUMBER_MAX_ from app_user u LEFT JOIN ibm_manage_role r ON u.APP_USER_TYPE_ = r.ROLE_CODE_ where u.APP_USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		return dao.findMap(sql, parameterList);
	}

	public Map<String, Object> findTypeByHandicapId(String handicapId) throws SQLException {
		Map<String, Object> map;
		String sql;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(handicapId);

		sql = "select ROLE_CODE_ from ibm_manage_role WHERE find_in_set( ? ,MEMBER_HANDICAP_ID_)";
		map = dao.findMap(sql, parameterList);
		if (StringTool.isEmpty(map)) {
			sql = "select ROLE_CODE_ from ibm_manage_role WHERE find_in_set( ? ,AGENT_HANDICAP_ID_)";
			map = dao.findMap(sql, parameterList);
		} else {
			return map;
		}
		return map;
	}

	/**
	 * 判断ibm_manage_role表中是否含有FREE,ADMIN这样的数据，没有创建，有则修改
	 *
	 * @param handicapId   盘口ID
	 * @param handicapCode 盘口类型
	 * @param level        角色等级
	 */
	public void updateMemberHandicapId(String handicapId, String handicapCode, int level) throws SQLException {
		List<Object> parameterList = new ArrayList<>(1);
		String sql;
		sql = "select IBM_MANAGE_ROLE_ID_ from ibm_manage_role where ROLE_CODE_ = ? and STATE_ = ? ";
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		Map<String, Object> map = dao.findMap(sql, parameterList);
		parameterList.clear();
		if (StringTool.isEmpty(map.get("IBM_MANAGE_ROLE_ID_"))) {
			sql = "insert into ibm_manage_role(ROLE_NAME_,ROLE_CODE_,ROLE_LEVEL_,MEMBER_HANDICAP_ID_,AGENT_HANDICAP_ID_,ONLINE_HM_NUMBER_MAX_,ONLINE_HA_NUMBER_MAX_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_,DESC_ values(?,?,?,?,?,?,?,?,?,?,?,?,?))";
			parameterList.add(IbmTypeEnum.valueOf(handicapCode).getMsg() + "用户");
			parameterList.add(handicapCode);
			parameterList.add(level);
			parameterList.add(handicapId);
			parameterList.add("");
			parameterList.add(1);
			parameterList.add(1);
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(IbmStateEnum.OPEN.name());
			parameterList.add("创建" + handicapCode);
			dao.execute(sql, parameterList);
		} else {
			sql = "update ibm_manage_role set MEMBER_HANDICAP_ID_ = CONCAT(MEMBER_HANDICAP_ID_, ? ) where ROLE_CODE_ = ? and STATE_ = ? ";
			parameterList.add(handicapId);
			parameterList.add(handicapCode);
			parameterList.add(IbmStateEnum.OPEN.name());
			dao.execute(sql, parameterList);
		}
	}

	/**
	 * 判断ibm_manage_role表中是否含有FREE,ADMIN这样的数据，没有创建，有则修改
	 *
	 * @param handicapId   盘口ID
	 * @param handicapCode 盘口类型
	 * @param level        角色等级
	 */
	public void updateAgentHandicapId(String handicapId, String handicapCode, int level) throws SQLException {
		List<Object> parameterList = new ArrayList<>(1);
		String sql;
		sql = "select IBM_MANAGE_ROLE_ID_ from ibm_manage_role where ROLE_CODE_ = ? and STATE_ = ? ";
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		Map<String, Object> map = dao.findMap(sql, parameterList);
		parameterList.clear();
		if (StringTool.isEmpty(map.get("IBM_MANAGE_ROLE_ID_"))) {
			sql = "insert into ibm_manage_role(ROLE_NAME_,ROLE_CODE_,ROLE_LEVEL_,MEMBER_HANDICAP_ID_,AGENT_HANDICAP_ID_,ONLINE_HM_NUMBER_MAX_,ONLINE_HA_NUMBER_MAX_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_,DESC_ values(?,?,?,?,?,?,?,?,?,?,?,?,?))";
			parameterList.add(IbmTypeEnum.valueOf(handicapCode).getMsg() + "用户");
			parameterList.add(handicapCode);
			parameterList.add(level);
			parameterList.add("");
			parameterList.add(handicapId);
			parameterList.add(1);
			parameterList.add(1);
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(IbmStateEnum.OPEN.name());
			parameterList.add("创建" + handicapCode);
			dao.execute(sql, parameterList);
		} else {
			sql = "update ibm_manage_role set AGENT_HANDICAP_ID_ = CONCAT(AGENT_HANDICAP_ID_, ? ) where ROLE_CODE_ = ? and STATE_ = ? ";
			parameterList.add(handicapId);
			parameterList.add(handicapCode);
			parameterList.add(IbmStateEnum.OPEN.name());
			dao.execute(sql, parameterList);
		}
	}

	/**
	 * 删除 ibm_manage_role 表中的 代理盘口ID
	 *
	 * @param handicapId 盘口ID
	 */
	public void delAgentHandicapId(String handicapId) throws SQLException {
		String sql = "update ibm_manage_role set AGENT_HANDICAP_ID_ = REPLACE(AGENT_HANDICAP_ID_, ? ,'') where STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		dao.execute(sql, parameterList);
	}

	/**
	 * 删除 ibm_manage_role 表中的 会员盘口ID
	 *
	 * @param handicapId 盘口ID
	 */
	public void delMemberHandicapId(String handicapId) throws SQLException {
		String sql = "update ibm_manage_role set MEMBER_HANDICAP_ID_ = REPLACE(MEMBER_HANDICAP_ID_, ? ,'') where STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		dao.execute(sql, parameterList);
	}

	/**
	 * 移除角色中的盘口主键
	 *
	 * @param handicapId 盘口主键
	 * @param roleType   角色类型
	 * @param category   角色类别
	 * @param nowTime    更新时间
	 * @param desc       描述
	 */
	public void removeHandicapId(String handicapId, IbmTypeEnum roleType, IbmTypeEnum category, Date nowTime,
			String desc) throws SQLException {

		//查询已存在的盘口主键
		Set<String> handicapIds = listExistHandicapId(roleType, category);
		//盘口主键列表为空
		if (ContainerTool.isEmpty(handicapIds)) {
			return;
		}
		//移除指定盘口主键
		handicapIds.remove(handicapId);

		//更新数据
		updateHandicapIds(roleType, category, nowTime, desc, handicapIds);
	}

	/**
	 * 向指定角色中添加盘口
	 *  @param handicapId 盘口主键
	 * @param roleType   角色类型
	 * @param category   角色类别
	 * @param nowTime    更新时间
	 * @param desc       描述
	 * @return 最大容量
	 */
	public Integer addHandicapId(String handicapId, IbmTypeEnum roleType, IbmTypeEnum category, Date nowTime, String desc)
			throws SQLException {
		//查询已存在的盘口主键
		Set<String> handicapIds = listExistHandicapId(roleType, category);
		//盘口主键列表为空
		if (ContainerTool.isEmpty(handicapIds)) {
			handicapIds = new HashSet<>();
		}
		//移除指定盘口主键
		handicapIds.add(handicapId);

		//更新数据
		updateHandicapIds(roleType, category, nowTime, desc, handicapIds);

		return NumberTool.getInteger(getOnlineNumberMax(roleType, category), 0);
	}

	/**
	 * 查找是否包含某个角色
	 *
	 * @param roleType 角色类型
	 * @return 包含true
	 */
	public boolean isExistRole(IbmTypeEnum roleType) throws SQLException {
		String sql = "SELECT IBM_MANAGE_ROLE_ID_ FROM `ibm_manage_role` where ROLE_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(roleType.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return ContainerTool.notEmpty(super.findMapList(sql, parameterList));
	}

	/**
	 * 保存角色信息
	 *
	 * @param handicapIds 盘口主键列表
	 * @param roleType    角色类型
	 * @param category    角色类别
	 * @param nowTime     更新时间
	 * @param desc        描述
	 * @return 最大在线数
	 */
	public int save(List<String> handicapIds, IbmTypeEnum roleType, IbmTypeEnum category, Date nowTime, String desc)
			throws Exception {
		IbmManageRole manageRole = new IbmManageRole();
		manageRole.setRoleName(roleType.getMsg());
		manageRole.setRoleCode(roleType.name());
//		if (IbmTypeEnum.AGENT.equals(category)) {
//			manageRole.setAgentHandicapId(String.join(",", handicapIds));
//		} else {
//			manageRole.setMemberHandicapId(String.join(",", handicapIds));
//		}
		manageRole.setRoleLevel(roleType.ordinal());
//		manageRole.setOnlineHmNumberMax(10);
//		manageRole.setOnlineHaNumberMax(10);
		manageRole.setCreateTime(nowTime);
		manageRole.setCreateTimeLong(System.currentTimeMillis());
		manageRole.setUpdateTimeLong(System.currentTimeMillis());
		manageRole.setState(IbmStateEnum.OPEN.name());
		manageRole.setDesc(desc);
		save(manageRole);
		return 10;
	}

	/**
	 * 获取已存在盘口主键列表
	 *
	 * @param roleType 角色类型
	 * @param category 角色类别
	 * @return 已存在盘口主键列表
	 */
	private Set<String> listExistHandicapId(IbmTypeEnum roleType, IbmTypeEnum category) throws SQLException {
		//查询已存在的盘口主键
		String sqlFormat = "SELECT %s as key_ FROM `ibm_manage_role` where ROLE_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(roleType.name());
		parameterList.add(IbmStateEnum.DEL.name());
		String handicapIdsStr;
		if (IbmTypeEnum.AGENT.equals(category)) {
			handicapIdsStr = super.findString(String.format(sqlFormat, "AGENT_HANDICAP_ID_"), parameterList);
		} else {
			handicapIdsStr = super.findString(String.format(sqlFormat, "MEMBER_HANDICAP_ID_"), parameterList);
		}
		//盘口主键列表为空
		if (StringTool.isEmpty(handicapIdsStr)) {
			return null;
		}
		//转换字符串为列表
		Set<String> handicapIds = new HashSet<>();
		CollectionUtils.addAll(handicapIds, handicapIdsStr.split(","));
		return handicapIds;
	}

	/**
	 * 更新盘口主键列表
	 *
	 * @param roleType    角色类型
	 * @param category    角色类别
	 * @param nowTime     更新时间
	 * @param desc        更新描述
	 * @param handicapIds 盘口主键列表
	 */
	private void updateHandicapIds(IbmTypeEnum roleType, IbmTypeEnum category, Date nowTime, String desc,
			Set<String> handicapIds) throws SQLException {
		String sqlFormat = "UPDATE ibm_manage_role set %s = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where ROLE_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(String.join(",", handicapIds));
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(roleType.name());
		parameterList.add(IbmStateEnum.DEL.name());

		if (IbmTypeEnum.AGENT.equals(category)) {
			super.execute(String.format(sqlFormat, "AGENT_HANDICAP_ID_"), parameterList);
		} else {
			super.execute(String.format(sqlFormat, "MEMBER_HANDICAP_ID_"), parameterList);
		}
	}

	public String getOnlineNumberMax(IbmTypeEnum roleType, IbmTypeEnum category) throws SQLException {
		//查询已存在的盘口主键
		String sql = "SELECT %s as key_ FROM `ibm_manage_role` where ROLE_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(roleType.name());
		parameterList.add(IbmStateEnum.DEL.name());
		if (IbmTypeEnum.AGENT.equals(category)) {
			sql = String.format(sql, "ONLINE_HA_NUMBER_MAX_");
		} else {
			sql = String.format(sql, "ONLINE_HM_NUMBER_MAX_");
		}
		return super.findString(sql, parameterList);
	}

	/**
	 * 获取角色应有的盘口ID列表
	 * @param roleName
	 * @return
	 * @throws SQLException
	 */
	public Map<String,Object> getRoleHaveHandicp(String roleName) throws SQLException {
		int roleLevel ;
		switch (roleName){
			case "SYS":
				roleLevel = 5;
				break;
			case "ADMIN":
				roleLevel = 4;
				break;
			case "CHARGE":
				roleLevel = 3;
				break;
			default:
				roleLevel = 0;
				break;
		}
		String sql = "SELECT GROUP_CONCAT(MEMBER_HANDICAP_ID_) hmIds ,GROUP_CONCAT(AGENT_HANDICAP_ID_) haIds,ONLINE_HM_NUMBER_MAX_,ONLINE_HA_NUMBER_MAX_ FROM `ibm_manage_role` where  ROLE_LEVEL_ <= ? ORDER BY ROLE_LEVEL_ DESC";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(roleLevel);
		return super.dao.findMap(sql,parameterList);
	}



	public List<Map<String, Object>> listShow() throws SQLException {
		String sql = "SELECT IBM_MANAGE_ROLE_ID_,ROLE_NAME_,ROLE_CODE_,AGENT_HANDICAP_ID_,ONLINE_HA_NUMBER_MAX_,MEMBER_HANDICAP_ID_,ONLINE_HM_NUMBER_MAX_ FROM `ibm_manage_role` WHERE STATE_ !='DEL' ORDER BY ROLE_LEVEL_ DESC" ;
		return super.dao.findMapList(sql,null);
	}

	/**
	 * 获取角色应有的盘口ID列表
	 * @param roleLevel
	 * @return
	 * @throws SQLException
	 */
	public String getRoleHandicp(int roleLevel) throws SQLException {
		String sql = "SELECT GROUP_CONCAT(AGENT_HANDICAP_ID_) haIds FROM `ibm_manage_role` where  ROLE_LEVEL_ = ? ORDER BY ROLE_LEVEL_ DESC";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(roleLevel);
		return super.dao.findString("haIds",sql,parameterList);
	}




}
