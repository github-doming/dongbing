package com.ibm.follow.connector.admin.manage3.handicap.service;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @Description: 后台管理盘口详情服务类
 * @Author: Dongming
 * @Date: 2019-11-07 14:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmAdminHandicapItemService extends IbmHandicapItemService {

	/**
	 * 获取盘口详情列表
	 *
	 * @param handicapCategory 盘口类别
	 * @param handicapId       盘口主键
	 * @param key              查询key
	 * @return 盘口详情列表
	 */
	public List<Map<String, Object>> listShow(String handicapCategory, String handicapId, String key)
			throws SQLException {
		String sql = "SELECT HANDICAP_NAME_,HANDICAP_URL_,HANDICAP_CAPTCHA_,HANDICAP_CATEGORY_,HANDICAP_ID_, "
				+ " IBM_HANDICAP_ITEM_ID_ as HANDICAP_ITEM_ID_  FROM `ibm_handicap_item` where STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		if (StringTool.notEmpty(handicapId)) {
			sql += " AND HANDICAP_ID_ = ? ";
			parameterList.add(handicapId);
		}
		if (StringTool.notEmpty(handicapCategory)) {
			sql += " AND HANDICAP_CATEGORY_ = ? ";
			parameterList.add(handicapCategory);
		}
		if (StringTool.notEmpty(key)) {
			sql += " AND ( HANDICAP_URL_ LIKE ? or HANDICAP_CATEGORY_ LIKE ?) ";
			key = "%" + key + "%";
			parameterList.add(key);
			parameterList.add(key);
		}
		sql += " ORDER BY HANDICAP_NAME_,UPDATE_TIME_LONG_ desc ";
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取引用的数量
	 *
	 * @param handicapItemId 盘口详情主键
	 * @param category       盘口类别
	 * @return 引用的引用的数量
	 */
	public String getCitations(String handicapItemId, IbmTypeEnum category) throws SQLException {
		String sql = "SELECT COUNT(*) as key_ FROM %s where HANDICAP_ITEM_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapItemId);
		parameterList.add(IbmStateEnum.DEL.name());

		if (category.equal(IbmTypeEnum.AGENT)) {
			return super.findString(String.format(sql, "ibm_handicap_agent"), parameterList);
		} else {
			return super.findString(String.format(sql, "ibm_handicap_member"), parameterList);
		}

	}

	/**
	 * 获取盘口类别
	 *
	 * @param handicapItemId 盘口详情id
	 * @return 盘口类别
	 */
	public String getCategory(String handicapItemId) throws SQLException {
		String sql = "SELECT HANDICAP_CATEGORY_ as key_ FROM `ibm_handicap_item` where IBM_HANDICAP_ITEM_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapItemId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findString(sql, parameterList);
	}
	/**
	 * 获取引用的列表
	 *
	 * @param handicapItemId 盘口详情主键
	 * @param category       盘口类别
	 * @return 引用的引用的列表
	 */
	public List<String> listCitations(String handicapItemId, IbmTypeEnum category) throws SQLException {
		String sql = "SELECT %s as key_ FROM %s where HANDICAP_ITEM_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapItemId);
		parameterList.add(IbmStateEnum.DEL.name());

		if (category.equal(IbmTypeEnum.AGENT)) {
			return super
					.findStringList(String.format(sql, "IBM_HANDICAP_AGENT_ID_", "ibm_handicap_agent"), parameterList);
		} else {
			return super.findStringList(String.format(sql, "IBM_HANDICAP_MEMBER_ID_", "ibm_handicap_member"),
					parameterList);
		}
	}
}
