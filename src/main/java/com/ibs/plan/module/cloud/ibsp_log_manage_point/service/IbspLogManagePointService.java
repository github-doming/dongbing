package com.ibs.plan.module.cloud.ibsp_log_manage_point.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_log_manage_point.entity.IbspLogManagePoint;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBSP_点数使用情况 服务类
 * @author Robot
 */
public class IbspLogManagePointService extends BaseServiceProxy {

	/**
	 * 保存IBSP_点数使用情况 对象数据
	 * @param entity IbspLogManagePoint对象数据
	 */
	public String save(IbspLogManagePoint entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_log_manage_point 的 IBSP_LOG_MANAGE_POINT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_log_manage_point set state_='DEL' where IBSP_LOG_MANAGE_POINT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_LOG_MANAGE_POINT_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_log_manage_point 的 IBSP_LOG_MANAGE_POINT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_log_manage_point set state_='DEL' where IBSP_LOG_MANAGE_POINT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_log_manage_point  的 IBSP_LOG_MANAGE_POINT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_log_manage_point where IBSP_LOG_MANAGE_POINT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_LOG_MANAGE_POINT_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_log_manage_point 的 IBSP_LOG_MANAGE_POINT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_log_manage_point where IBSP_LOG_MANAGE_POINT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspLogManagePoint实体信息
	 * @param entity IBSP_点数使用情况 实体
	 */
	public void update(IbspLogManagePoint entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_log_manage_point表主键查找 IBSP_点数使用情况 实体
	 * @param id ibsp_log_manage_point 主键
	 * @return IBSP_点数使用情况 实体
	 */
	public IbspLogManagePoint find(String id) throws Exception {
		return dao.find(IbspLogManagePoint.class,id);
	}

	/**
	 * 用户最后一条积分修改记录主键
	 * @param appUserId
	 * @return
	 */
	public Map<String,Object> findLastRepByUserId(String appUserId) throws SQLException {
		String sql = "SELECT IBSP_LOG_MANAGE_POINT_ID_ preKey,PRE_T_,NUMBER_T_,BALANCE_T_ FROM `ibsp_log_manage_point` WHERE APP_USER_ID_ =? ORDER BY CREATE_TIME_LONG_ DESC LIMIT 1 ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		return dao.findMap(sql,parameterList);
	}

	/**
	 * 获取用户消息列表
	 *
	 * @param userId    用户Id
	 * @param pageIndex 起始页
	 * @param pageSize  页大小
	 * @return 分页信息
	 */
	public PageCoreBean<Map<String, Object>> pageUserPointRecord(String userId, Integer pageIndex, Integer pageSize) throws SQLException {
		String sql = " SELECT PRE_ID_,CREATE_TIME_LONG_,PRE_T_,NUMBER_T_,BALANCE_T_,TITLE_ FROM `ibsp_log_manage_point` where APP_USER_ID_ = ? AND STATE_!=? ORDER BY UPDATE_TIME_LONG_ DESC";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(IbsStateEnum.DEL.name());
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}
}
