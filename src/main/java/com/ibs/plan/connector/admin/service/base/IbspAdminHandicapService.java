package com.ibs.plan.connector.admin.service.base;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
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
public class IbspAdminHandicapService extends IbspHandicapService {

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
		String sql = "SELECT HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_EXPLAIN_,HANDICAP_WORTH_T_, "
				+ " IBSP_HANDICAP_ID_,HANDICAP_TYPE_ FROM `ibsp_handicap` where STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbsStateEnum.DEL.name());
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
	 * 根据盘口主键查找盘口信息
	 *
	 * @param handicapId 盘口主键
	 * @return 盘口信息
	 */
	@Override
	public Map<String, Object> findInfo(String handicapId) throws SQLException {
		String sql = "SELECT HANDICAP_NAME_,HANDICAP_CODE_FROM `ibsp_handicap` where IBSP_HANDICAP_ID_ = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

}
