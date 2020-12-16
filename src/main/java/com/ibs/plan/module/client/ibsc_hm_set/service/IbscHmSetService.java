package com.ibs.plan.module.client.ibsc_hm_set.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.client.ibsc_hm_set.entity.IbscHmSet;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSC_客户端会员设置 服务类
 *
 * @author Robot
 */
public class IbscHmSetService extends BaseServiceProxy {

	/**
	 * 保存IBSC_客户端会员设置 对象数据
	 *
	 * @param entity IbscHmSet对象数据
	 */
	public String save(IbscHmSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_hm_set 的 IBSC_HM_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_hm_set set state_='DEL' where IBSC_HM_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_hm_set 的 IBSC_HM_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsc_hm_set set state_='DEL' where IBSC_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_hm_set  的 IBSC_HM_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_hm_set where IBSC_HM_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_hm_set 的 IBSC_HM_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_hm_set where IBSC_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscHmSet实体信息
	 *
	 * @param entity IBSC_客户端会员设置 实体
	 */
	public void update(IbscHmSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_hm_set表主键查找 IBSC_客户端会员设置 实体
	 *
	 * @param id ibsc_hm_set 主键
	 * @return IBSC_客户端会员设置 实体
	 */
	public IbscHmSet find(String id) throws Exception {
		return (IbscHmSet) dao.find(IbscHmSet.class, id);

	}
	/**
	 * 获取会员设置信息
	 *
	 * @param existHmIds 已存在盘口会员ids
	 * @return
	 */
	public Map<String, Map<String, Object>> findSetInfo(Set<String> existHmIds) throws SQLException {
		StringBuilder sql = new StringBuilder("select ieh.HANDICAP_CODE_,EXIST_HM_ID_,BET_RATE_T_,BET_MERGER_,BLAST_STOP_"
				+ " FROM ibsc_exist_hm ieh LEFT JOIN ibsc_hm_set ihs ON ieh.IBSC_EXIST_HM_ID_=ihs.EXIST_HM_ID_"
				+ " where ieh.STATE_=? and ieh.IBSC_EXIST_HM_ID_ in(");
		List<Object> parameterList = new ArrayList<>(existHmIds.size());
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String existHmId : existHmIds) {
			sql.append("?,");
			parameterList.add(existHmId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findKeyMap(sql.toString(),parameterList,"EXIST_HM_ID_");
	}
	/**
	 * 获取会员设置信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
	 */
	public Map<String, Object> findSetInfo(String existHmId) throws SQLException {
		String sql="select BET_RATE_T_,BET_MERGER_,BLAST_STOP_ FROM ibsc_hm_set ihs"
				+ " where STATE_=? and EXIST_HM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(existHmId);
		return super.dao.findMap(sql,parameterList);
	}
	/**
	 * 获取配置信息
	 *
	 * @param existHmId 存在盘口会员主键
	 * @return 配置信息
	 */
	public IbscHmSet findEntity(String existHmId) throws Exception {
		String sql = "select * from ibsc_hm_set where EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(existHmId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbscHmSet.class, sql, parameters);
	}
	/**
	 * 获取投注比率
	 * @param existHmId 存在盘口会员主键
	 * @return 投注比率
	 */
	public double findBetRate(String existHmId) throws SQLException {
		String sql = "select BET_RATE_T_ from ibsc_hm_set" + " where STATE_ = ? and EXIST_HM_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(existHmId);
		Map<String, Object> info = super.findMap(sql, parameterList);
		return NumberTool.doubleT(info.getOrDefault("BET_RATE_T_", 1000));
	}
}
