package com.ibs.plan.module.cloud.ibsp_sys_manage_point.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_sys_manage_point.entity.IbspSysManagePoint;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IBSP_用户点数 服务类
 *
 * @author Robot
 */
public class IbspSysManagePointService extends BaseServiceProxy {

	/**
	 * 保存IBSP_用户点数 对象数据
	 *
	 * @param entity IbspSysManagePoint对象数据
	 */
	public String save(IbspSysManagePoint entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_sys_manage_point 的 IBSP_SYS_MANAGE_POINT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_sys_manage_point set state_='DEL' where IBSP_SYS_MANAGE_POINT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_SYS_MANAGE_POINT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_sys_manage_point 的 IBSP_SYS_MANAGE_POINT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_sys_manage_point set state_='DEL' where IBSP_SYS_MANAGE_POINT_ID_ in(" + stringBuilder
							.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_sys_manage_point  的 IBSP_SYS_MANAGE_POINT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_sys_manage_point where IBSP_SYS_MANAGE_POINT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_SYS_MANAGE_POINT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_sys_manage_point 的 IBSP_SYS_MANAGE_POINT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibsp_sys_manage_point where IBSP_SYS_MANAGE_POINT_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspSysManagePoint实体信息
	 *
	 * @param entity IBSP_用户点数 实体
	 */
	public void update(IbspSysManagePoint entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_sys_manage_point表主键查找 IBSP_用户点数 实体
	 *
	 * @param id ibsp_sys_manage_point 主键
	 * @return IBSP_用户点数 实体
	 */
	public IbspSysManagePoint find(String id) throws Exception {
		return dao.find(IbspSysManagePoint.class, id);
	}

	/**
	 * 获取可用积分
	 *
	 * @param userId 用户id
	 * @return 可用积分
	 */
	public double getUseablePoint(String userId) throws SQLException {
		String sql = "SELECT USEABLE_POINT_T_  FROM `ibsp_sys_manage_point` where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbsStateEnum.DEL.name());
		Map<String, Object> map = super.findMap(sql, parameterList);
		return NumberTool.getDouble(map,"USEABLE_POINT_T_",0);
	}

	/**
	 * 获取可用积分
	 *
	 * @param userIdList 用户ID集合
	 * @return 可用积分
	 */
	public Map<String,Object> getUsersUseablePoint(List<String> userIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT APP_USER_ID_ key_,USEABLE_POINT_T_ value_  FROM `ibsp_sys_manage_point` where STATE_ != ? and APP_USER_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.DEL.name());
		for (String uid:userIdList){
			sql.append("?,");
			parameterList.add(uid);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		return findKVMap(sql,parameterList);
	}

	/**
	 * 根据用户id获取实体对象
	 *
	 * @param appUserId 用户id
	 * @return
	 */
	public IbspSysManagePoint findByUserId(String appUserId) throws Exception {
		String sql = "SELECT *  FROM `ibsp_sys_manage_point` where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findObject(IbspSysManagePoint.class, sql, parameterList);
	}
}
