package com.ibm.follow.servlet.cloud.ibm_rep_manage_time.service;

import c.x.platform.root.common.service.BaseService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.entity.IbmRepManageTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBM_可用时长情况 的服务类
 * @author Robot
 */
public class IbmRepManageTimeService extends BaseService {

	/**
	 * 保存IBM_可用时长情况对象数据
	 * @param entity IbmRepManageTime对象数据
	 */
	public String save(IbmRepManageTime entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_rep_manage_time 的 IBM_REP_MANAGE_TIME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_rep_manage_time set state_='DEL' where IBM_REP_MANAGE_TIME_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_REP_MANAGE_TIME_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_rep_manage_time 的 IBM_REP_MANAGE_TIME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_rep_manage_time set state_='DEL' where IBM_REP_MANAGE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_rep_manage_time  的 IBM_REP_MANAGE_TIME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_rep_manage_time where IBM_REP_MANAGE_TIME_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_REP_MANAGE_TIME_ID_主键id数组的数据
	 * @param idArray 要删除IBM_可用时长情况的 IBM_REP_MANAGE_TIME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_rep_manage_time where IBM_REP_MANAGE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmRepManageTime实体信息
	 * @param entity IBM_可用时长情况实体
	 */
	public void update(IbmRepManageTime entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_rep_manage_time表主键查找IbmRepManageTime实体
	 * @param id ibm_rep_manage_time 主键
	 * @return IBM_可用时长情况实体
	 */
	public IbmRepManageTime find(String id) throws Exception {
		return (IbmRepManageTime) this.dao.find(IbmRepManageTime. class,id);

	}

	/**
	 * 用户最后一条积分修改记录主键
	 * @param appUserId
	 * @return 
	 */
	public Map<String,Object> findLastRepByUserId(String appUserId) throws SQLException {
		String sql = "SELECT IBM_REP_MANAGE_TIME_ID_ preKey,START_TIME_,START_TIME_LONG_,END_TIME_,END_TIME_LONG_ FROM `ibm_rep_manage_time` WHERE USER_ID_ =? ORDER BY CREATE_TIME_LONG_ DESC LIMIT 1 ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		return dao.findMap(sql,parameterList);
	}
}
