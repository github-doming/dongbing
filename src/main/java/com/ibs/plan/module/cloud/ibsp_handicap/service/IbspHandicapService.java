package com.ibs.plan.module.cloud.ibsp_handicap.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_handicap.entity.IbspHandicap;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * IBSP_盘口 服务类
 *
 * @author Robot
 */
public class IbspHandicapService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口 对象数据
	 *
	 * @param entity IbspHandicap对象数据
	 */
	public String save(IbspHandicap entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_handicap 的 IBSP_HANDICAP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_handicap set state_='DEL' where IBSP_HANDICAP_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HANDICAP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_handicap 的 IBSP_HANDICAP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_handicap set state_='DEL' where IBSP_HANDICAP_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_handicap  的 IBSP_HANDICAP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_handicap where IBSP_HANDICAP_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HANDICAP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_handicap 的 IBSP_HANDICAP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_handicap where IBSP_HANDICAP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHandicap实体信息
	 *
	 * @param entity IBSP_盘口 实体
	 */
	public void update(IbspHandicap entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_handicap表主键查找 IBSP_盘口 实体
	 *
	 * @param id ibsp_handicap 主键
	 * @return IBSP_盘口 实体
	 */
	public IbspHandicap find(String id) throws Exception {
		return dao.find(IbspHandicap.class, id);
	}

	/**
	 * 根据编码列表查找信息列表
	 *
	 * @param handicapCodes 编码列表
	 * @return 信息列表
	 */
	public List<Map<String, Object>> listInfoByCodes(Set<String> handicapCodes) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT IBSP_HANDICAP_ID_,HANDICAP_CODE_,HANDICAP_NAME_ from ibsp_handicap where STATE_ = ? "
						+ " and HANDICAP_CODE_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String handicapCode : handicapCodes) {
			sql.append("?,");
			parameterList.add(handicapCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 获取 盘口id为key，盘口code为value的集合
	 *
	 * @return 盘口id为key，盘口code为value
	 */
	public Map<String, String> mapIdCode() throws SQLException {
		String sql = "SELECT IBSP_HANDICAP_ID_ as key_, HANDICAP_CODE_ as value_ FROM ibsp_handicap where STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 查找盘口id
	 *
	 * @param handicapCode 盘口code
	 * @return 盘口id
	 */
	public String findId(String handicapCode) throws SQLException {
		String sql = "SELECT IBSP_HANDICAP_ID_ FROM `ibsp_handicap` where HANDICAP_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("IBSP_HANDICAP_ID_", sql, parameterList);
	}

	/**
	 * 获取盘口状态
	 *
	 * @param handicapId 盘口id
	 * @return 盘口状态
	 */
	public String findState(String handicapId) throws SQLException {
		String sql = "select STATE_ from ibsp_handicap where IBSP_HANDICAP_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findString("STATE_", sql, parameterList);
	}

	/**
	 * 获取盘口编码
	 *
	 * @param handicapId 盘口id
	 * @return 盘口编码
	 */
	public String findCode(String handicapId) throws SQLException {
		String sql = "select HANDICAP_CODE_ from ibsp_handicap where IBSP_HANDICAP_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("HANDICAP_CODE_", sql, parameterList);
	}

	/**
	 * 获取所有盘口codes
	 */
	public List<String> findHandicapCode() throws SQLException {
		String sql = "select HANDICAP_CODE_ from ibsp_handicap where  STATE_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findStringList("HANDICAP_CODE_", sql, parameterList);
	}

	/**
	 * 获取盘口信息
	 * @param usable   可用盘口codes
	 */
	public List<Map<String, Object>> findHandicap(Set<String> usable) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT IBSP_HANDICAP_ID_,HANDICAP_CODE_,HANDICAP_NAME_ from ibsp_handicap where STATE_ = ? and HANDICAP_CODE_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String handicapCode : usable) {
			sql.append("?,");
			parameterList.add(handicapCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 根据类别获取盘口信息
	 * @return  HANDICAP_NAME_,HANDICAP_CODE_
	 */
	public List<Map<String, Object>> listHandicap() throws SQLException {
		String sql="select HANDICAP_NAME_,HANDICAP_CODE_ from ibsp_handicap where STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);

		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameterList);

	}

	/**
	 * 获取分页信息
	 * @param handicapName      盘口名称
	 * @param handicapType      盘口类型
	 * @param pageIndex         页数
	 * @param pageSize          条数
	 * @return
	 */
	public PageCoreBean<Map<String, Object>> listShow(String handicapName, String handicapType, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount="select count(IBSP_HANDICAP_ID_) from ibsp_handicap where STATE_!=? ";
		String sql="SELECT IBSP_HANDICAP_ID_,HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_TYPE_,HANDICAP_WORTH_T_,CAPACITY_,CAPACITY_MAX_,STATE_"
				+ " from ibsp_handicap where STATE_!=? ";
		ArrayList<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.DEL.name());
		String sqlPlus = "";
		if(StringTool.notEmpty(handicapName)){
			sqlPlus+=" and HANDICAP_NAME_ like ?";
			parameterList.add("%"+handicapName+"%");
		}

		if(StringTool.notEmpty(handicapType)){
			sqlPlus+=" and HANDICAP_TYPE_=?";
			parameterList.add(handicapType);
		}
		sqlPlus += " order by SN_";
		sqlCount += sqlPlus;
		sql += sqlPlus;
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 获取盘口信息
	 * @param handicapId        盘口id
	 * @return
	 */
	public Map<String, Object> findInfo(String handicapId) throws SQLException {
		String sql="select IBSP_HANDICAP_ID_,HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_TYPE_,HANDICAP_WORTH_T_,"
				+ "STATE_ from ibsp_handicap where IBSP_HANDICAP_ID_=?";
		ArrayList<Object> parameterList = new ArrayList<>(1);
		parameterList.add(handicapId);
		return super.dao.findMap(sql,parameterList);
	}

	/**
	 * 查找盘口code 是否存在
	 *
	 * @param handicapCode     盘口code
	 * @return 存在-true
	 */
	public boolean isExist(String handicapCode) throws SQLException {
		String sql = "SELECT STATE_ FROM ibsp_handicap WHERE HANDICAP_CODE_ = ?  AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapCode);
		parameterList.add(IbsStateEnum.DEL.name());
		return ContainerTool.notEmpty(super.dao.findMap(sql, parameterList));
	}

}
