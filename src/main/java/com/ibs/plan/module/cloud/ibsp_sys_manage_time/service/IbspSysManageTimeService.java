package com.ibs.plan.module.cloud.ibsp_sys_manage_time.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.entity.IbspSysManageTime;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IBSP_用户时长 服务类
 *
 * @author Robot
 */
public class IbspSysManageTimeService extends BaseServiceProxy {

	/**
	 * 保存IBSP_用户时长 对象数据
	 *
	 * @param entity IbspSysManageTime对象数据
	 */
	public String save(IbspSysManageTime entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_sys_manage_time 的 IBSP_SYS_MANAGE_TIME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_sys_manage_time set state_='DEL' where IBSP_SYS_MANAGE_TIME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_SYS_MANAGE_TIME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_sys_manage_time 的 IBSP_SYS_MANAGE_TIME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_sys_manage_time set state_='DEL' where IBSP_SYS_MANAGE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_sys_manage_time  的 IBSP_SYS_MANAGE_TIME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_sys_manage_time where IBSP_SYS_MANAGE_TIME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_SYS_MANAGE_TIME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_sys_manage_time 的 IBSP_SYS_MANAGE_TIME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_sys_manage_time where IBSP_SYS_MANAGE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspSysManageTime实体信息
	 *
	 * @param entity IBSP_用户时长 实体
	 */
	public void update(IbspSysManageTime entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_sys_manage_time表主键查找 IBSP_用户时长 实体
	 *
	 * @param id ibsp_sys_manage_time 主键
	 * @return IBSP_用户时长 实体
	 */
	public IbspSysManageTime find(String id) throws Exception {
		return dao.find(IbspSysManageTime.class, id);
	}

	/**
	 * 获取到期时间戳
	 *
	 * @param userIdList 用户ID集合
	 * @return 到期时间戳
	 */
	public Map<String,Object> getUsersEndTime(List<String> userIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT  APP_USER_ID_ key_,END_TIME_LONG_ value_ FROM ibsp_sys_manage_time where  STATE_ != ? and APP_USER_ID_ in( ");
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
	 * 获取实体对象
	 * @param appUserId     用户id
	 * @return 实体
	 */
	public IbspSysManageTime findByUserId(String appUserId) throws Exception {
		String sql="select * from ibsp_sys_manage_time where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findObject(IbspSysManageTime.class,sql,parameterList);
	}



	/**
	 * 获取结束时间
	 * @param userId 用户id
	 * @return 结束时间
	 */
	public long getEndLongTime(String userId) throws SQLException {
		String sql = "SELECT END_TIME_LONG_  FROM ibsp_sys_manage_time where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbsStateEnum.DEL.name());
		Map<String, Object> map = super.findMap(sql, parameterList);
		return NumberTool.getLong(map,"END_TIME_LONG_",0L);
	}

}
