package com.ibm.follow.servlet.cloud.ibm_rep_manage_point.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.entity.IbmRepManagePoint;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBM_点数使用情况 的服务类
 * @author Robot
 */
public class IbmRepManagePointService extends BaseService {

	/**
	 * 保存IBM_点数使用情况对象数据
	 * @param entity IbmRepManagePoint对象数据
	 */
	public String save(IbmRepManagePoint entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_rep_manage_point 的 IBM_REP_MANAGE_POINT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_rep_manage_point set state_='DEL' where IBM_REP_MANAGE_POINT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_REP_MANAGE_POINT_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_rep_manage_point 的 IBM_REP_MANAGE_POINT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_rep_manage_point set state_='DEL' where IBM_REP_MANAGE_POINT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_rep_manage_point  的 IBM_REP_MANAGE_POINT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_rep_manage_point where IBM_REP_MANAGE_POINT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_REP_MANAGE_POINT_ID_主键id数组的数据
	 * @param idArray 要删除IBM_点数使用情况的 IBM_REP_MANAGE_POINT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_rep_manage_point where IBM_REP_MANAGE_POINT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmRepManagePoint实体信息
	 * @param entity IBM_点数使用情况实体
	 */
	public void update(IbmRepManagePoint entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_rep_manage_point表主键查找IbmRepManagePoint实体
	 * @param id ibm_rep_manage_point 主键
	 * @return IBM_点数使用情况实体
	 */
	public IbmRepManagePoint find(String id) throws Exception {
		return (IbmRepManagePoint) this.dao.find(IbmRepManagePoint. class,id);

	}

    public void delByAppUserId(String appUserId) throws SQLException {
		String sql = "delete from ibm_rep_manage_point where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		dao.execute(sql,parameterList);
    }

	public void updateStateByAppUserId(String appUserId) throws SQLException {
		String sql = "update ibm_rep_manage_point set STATE_ = ? where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(appUserId);
		dao.execute(sql,parameterList);
	}

	/**
	 * 用户最后一条积分修改记录主键
	 * @param appUserId
	 * @return 
	 */
	public Map<String,Object> findLastRepByUserId(String appUserId) throws SQLException {

		String sql = "SELECT IBM_REP_MANAGE_POINT_ID_ preKey,PRE_T_,NUMBER_T_,BALANCE_T_ FROM `ibm_rep_manage_point` WHERE APP_USER_ID_ =? ORDER BY CREATE_TIME_LONG_ DESC LIMIT 1 ";
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
		String sql = " SELECT PRE_ID_,CREATE_TIME_LONG_,PRE_T_,NUMBER_T_,BALANCE_T_,TITLE_ FROM `ibm_rep_manage_point` where APP_USER_ID_ = ? AND STATE_!=? ORDER BY UPDATE_TIME_LONG_ DESC";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(IbmStateEnum.DEL.name());
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}

}
