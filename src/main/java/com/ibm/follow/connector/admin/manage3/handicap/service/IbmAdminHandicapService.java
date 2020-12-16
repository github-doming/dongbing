package com.ibm.follow.connector.admin.manage3.handicap.service;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @Description: 后台管理盘口服务类
 * @Author: Dongming
 * @Date: 2019-11-07 14:51
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmAdminHandicapService extends IbmHandicapService {

	/**
	 * 获取盘口信息
	 *
	 * @param handicapCategory 盘口类别
	 * @param handicapType     盘口类型
	 * @param key              搜索key
	 * @return 盘口信息
	 */
	public List<Map<String, Object>> listShow(String handicapCategory, String handicapType, String key)
			throws SQLException {
		String sql = "SELECT HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_EXPLAIN_,HANDICAP_CATEGORY_,HANDICAP_WORTH_T_, "
				+ " IBM_HANDICAP_ID_,HANDICAP_TYPE_ FROM `ibm_handicap` where STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		if (StringTool.notEmpty(handicapCategory)) {
			sql += " AND HANDICAP_CATEGORY_ = ? ";
			parameterList.add(handicapCategory);
		}
		if (StringTool.notEmpty(handicapType)) {
			sql += " AND HANDICAP_TYPE_ = ? ";
			parameterList.add(handicapType);
		}
		if (StringTool.notEmpty(key)) {
			sql += " AND ( HANDICAP_NAME_ LIKE ? or HANDICAP_EXPLAIN_ LIKE ?) ";
			key = "%" + key + "%";
			parameterList.add(key);
			parameterList.add(key);
		}
		sql += " ORDER BY SN_,HANDICAP_CODE_";

		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取单独盘口信息
	 *
	 * @return 盘口信息
	 */
	public IbmHandicap findById(String handicapId) throws Exception {
		String sql = "SELECT HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_EXPLAIN_,HANDICAP_TYPE_,HANDICAP_CATEGORY_, "
				+ " HANDICAP_WORTH_T_,IBM_HANDICAP_ID_,SN_,DESC_ FROM `ibm_handicap` where IBM_HANDICAP_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(handicapId);
		return (IbmHandicap) super.dao.findObject(IbmHandicap.class, sql, parameterList);
	}

	/**
	 * 获取去盘口类型
	 *
	 * @param handicapId 盘口主键
	 * @return 盘口类型
	 */
	public String findType(String handicapId) throws SQLException {
		String sql = "select HANDICAP_TYPE_ as key_ from ibm_handicap where IBM_HANDICAP_ID_ = ? and STATE_ != ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(handicapId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.findString(sql, parameters);
	}

	/**
	 * 删除盘口信息
	 */
	public void deleteHandicap(String handicapId) throws Exception {
		String sql = "update %s set state_ = ? where %s = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		dao.execute(String.format(sql, "ibm_handicap_item", "HANDICAP_ID_"), parameterList);
		dao.execute(String.format(sql, "ibm_handicap", "IBM_HANDICAP_ID_"), parameterList);
		dao.execute(String.format(sql, "ibm_handicap_game", "HANDICAP_ID_"), parameterList);

	}

	/**
	 * 查找盘口code 是否存在
	 *
	 * @param handicapCode     盘口code
	 * @param handicapCategory 盘口类别
	 * @return 存在-true
	 */
	public boolean isExist(String handicapCode, String handicapCategory) throws SQLException {
		String sql = "SELECT STATE_ FROM ibm_handicap WHERE HANDICAP_CODE_ = ? and HANDICAP_CATEGORY_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapCode);
		parameterList.add(handicapCategory);
		parameterList.add(IbmStateEnum.DEL.name());
		return ContainerTool.notEmpty(super.dao.findMap(sql, parameterList));
	}

	/**
	 * 获取指定类型的所有盘口主键列表
	 *
	 * @param type 盘口类型
	 * @return 盘口主键列表
	 */
	public List<String> listIdByType(IbmTypeEnum type) throws SQLException {
		String sql = "select IBM_HANDICAP_ID_ as key_ from ibm_handicap where HANDICAP_TYPE_ = ? and STATE_ != ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(type.name());
		parameters.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql, parameters);

	}

	/**
	 * 根据盘口主键查找盘口信息
	 *
	 * @param handicapId 盘口主键
	 * @return 盘口信息
	 */
	@Override
    public Map<String, Object> findInfo(String handicapId) throws SQLException {
		String sql = "SELECT HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_CATEGORY_ FROM `ibm_handicap` where IBM_HANDICAP_ID_ = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

}
