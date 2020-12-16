package com.ibm.follow.connector.admin.manage3.handicap.service;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-08 09:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmAdminHandicapUserService extends BaseServicePlus {

	/**
	 * 获取盘口详情列表
	 *
	 * @param category   盘口类别
	 * @param handicapId 盘口主键
	 * @param key        查询key
	 * @return 盘口详情列表
	 */
	public List<Map<String, Object>> listShow(IbmTypeEnum category, String handicapId, String key) throws SQLException {
		String sql = "SELECT APP_USER_NAME_, HANDICAP_NAME_, HANDICAP_ID_, %s AS ONLINE_COUNT_, "
				+ " ONLINE_NUMBER_MAX_, %s AS ONLINE_IDS_, %s AS HANDICAP_USER_ID_,'%s' as CATEGORY "
				+ " FROM %s ihu LEFT JOIN app_user au ON ihu.APP_USER_ID_ = au.APP_USER_ID_ "
				+ " WHERE ihu.STATE_ != ? AND au.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		if (StringTool.notEmpty(handicapId)) {
			sql += " AND HANDICAP_ID_ = ? ";
			parameterList.add(handicapId);
		}
		if (StringTool.notEmpty(key)) {
			sql += " AND ( HANDICAP_NAME_ LIKE ? or APP_USER_NAME_ LIKE ?) ";
			key = "%" + key + "%";
			parameterList.add(key);
			parameterList.add(key);
		}
		sql += " ORDER BY HANDICAP_CODE_,APP_USER_NAME_";
		if (IbmTypeEnum.AGENT.equals(category)) {
			return super.findMapList(String.format(sql, "ONLINE_AGENTS_COUNT_", "ONLINE_AGENTS_IDS_", "IBM_HA_USER_ID_",
					category.getMsg(), "ibm_ha_user"), parameterList);
		} else {
			return super.findMapList(
					String.format(sql, "ONLINE_MEMBERS_COUNT_", "ONLINE_MEMBERS_IDS_", "IBM_HM_USER_ID_",
							category.getMsg(), "ibm_hm_user"), parameterList);
		}
	}

	/**
	 * 获取引用的列表
	 *
	 * @param handicapUserId 盘口用户主键
	 * @param category       盘口类别
	 * @return 引用的引用的列表
	 */
	public List<String> listCitations(String handicapUserId, IbmTypeEnum category) throws SQLException {
		String sql = "SELECT %s AS key_ FROM %s WHERE %s = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		if (category.equal(IbmTypeEnum.AGENT)) {
			return super
					.findStringList(String.format(sql, "IBM_HANDICAP_AGENT_ID_", "ibm_handicap_agent", "HA_USER_ID_"),
							parameterList);
		} else {
			return super
					.findStringList(String.format(sql, "IBM_HANDICAP_MEMBER_ID_", "ibm_handicap_member", "HM_USER_ID_"),
							parameterList);
		}
	}

	/**
	 * 删除盘口用户信息
	 *
	 * @param handicapUserId 盘口用户主键
	 * @param category       盘口类别
	 * @param desc           描述
	 * @param nowTime        更新时间
	 */
	public void delete(String handicapUserId, IbmTypeEnum category, String desc, Date nowTime) throws SQLException {
		String sql = "UPDATE %s SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE %s = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		if (category.equal(IbmTypeEnum.AGENT)) {
			super.execute(String.format(sql, "ibm_ha_user", "IBM_HA_USER_ID_"), parameterList);
		} else {
			super.execute(String.format(sql, "ibm_hm_user", "IBM_HM_USER_ID_"), parameterList);
		}
	}

	/**
	 * 获取用户的盘口名称
	 *
	 * @param category 盘口类别
	 * @param userId   用户主键
	 * @return 盘口名称列表
	 */
	public List<String> listHandicapNameByUserId(IbmTypeEnum category, String userId) throws SQLException {
		String sql = "SELECT HANDICAP_NAME_ AS key_ FROM %s WHERE APP_USER_ID_ = ? AND STATE_ != ? ORDER BY HANDICAP_CODE_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		if (category.equal(IbmTypeEnum.AGENT)) {
			return super.findStringList(String.format(sql, "ibm_ha_user"), parameterList);
		} else {
			return super.findStringList(String.format(sql, "ibm_hm_user"), parameterList);
		}
	}

	/**
	 * 获取用户的盘口名称
	 *
	 * @param userIdList 用户ID集合
	 * @return 盘口名称列表
	 */
	public Map<String, List<String>>  listHandicapNameByUserIds(IbmTypeEnum category,List<String> userIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT APP_USER_ID_ key_,HANDICAP_NAME_ AS value_ FROM %s WHERE  STATE_ != ? and APP_USER_ID_ in(  " );
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String uid:userIdList){
			sql.append("?,");
			parameterList.add(uid);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(") ORDER BY HANDICAP_CODE_");
		if (category.equal(IbmTypeEnum.AGENT)) {
			return super.findKVsMapList(String.format(sql.toString(), "ibm_ha_user"), parameterList);
		} else {
			return super.findKVsMapList(String.format(sql.toString(), "ibm_hm_user"), parameterList);
		}

	}

	/**
	 * 获取用户的盘口ID
	 *
	 * @param category 盘口类别
	 * @param userId   用户主键
	 * @return 盘口名称列表
	 */
	public List<String> listHandicapIdByUserId(IbmTypeEnum category, String userId) throws SQLException {
		String sql = "SELECT HANDICAP_ID_ AS key_ FROM %s WHERE APP_USER_ID_ = ? AND STATE_ != ? ORDER BY HANDICAP_CODE_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		if (category.equal(IbmTypeEnum.AGENT)) {
			return super.findStringList(String.format(sql, "ibm_ha_user"), parameterList);
		} else {
			return super.findStringList(String.format(sql, "ibm_hm_user"), parameterList);
		}
	}

	/**
	 * 获取盘口用户信息
	 *
	 * @param category  盘口类别
	 * @param appUserId 用户主键
	 * @return 盘口用户信息列表
	 */
	public List<Map<String, Object>> listInfoByUserId(IbmTypeEnum category, String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_NAME_, %s AS ONLINE_COUNT_, ONLINE_NUMBER_MAX_, %s AS HANDICAP_USER_ID_ "
				+ " FROM %s WHERE APP_USER_ID_ = ? AND STATE_ != ?  ORDER BY HANDICAP_CODE_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());

		if (IbmTypeEnum.AGENT.equals(category)) {
			return super.findMapList(String.format(sql, "ONLINE_AGENTS_COUNT_", "IBM_HA_USER_ID_", "ibm_ha_user"),
					parameterList);
		} else {
			return super.findMapList(String.format(sql, "ONLINE_MEMBERS_COUNT_", "IBM_HM_USER_ID_", "ibm_hm_user"),
					parameterList);
		}
	}

	/**
	 * 获取盘口用户信息
	 *
	 * @param handicapUserId 盘口用户主键
	 * @param category       盘口类别
	 * @return 盘口用户信息
	 */
	public Map<String, Object> findInfo(String handicapUserId, IbmTypeEnum category) throws SQLException {
		String sql = "SELECT HANDICAP_NAME_,HANDICAP_CODE_, %s AS ONLINE_COUNT_, ONLINE_NUMBER_MAX_, "
				+ " %s AS HANDICAP_USER_ID_ FROM %s WHERE %s = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		if (IbmTypeEnum.AGENT.equals(category)) {
			return super.findMap(
					String.format(sql, "ONLINE_AGENTS_COUNT_", "IBM_HA_USER_ID_", "ibm_ha_user", "IBM_HA_USER_ID_"),
					parameterList);
		} else {
			return super.findMap(
					String.format(sql, "ONLINE_MEMBERS_COUNT_", "IBM_HM_USER_ID_", "ibm_hm_user", "IBM_HM_USER_ID_"),
					parameterList);
		}

	}
	/**
	 * 获取详情信息
	 *
	 * @param handicapUserId 盘口用户主键
	 * @param category       盘口类别
	 * @return 盘口会员详情
	 */
	public List<Map<String, Object>> listItemInfo(String handicapUserId, IbmTypeEnum category) throws SQLException {
		String sql = "SELECT ih.%s AS ACCOUNT_,HANDICAP_URL_,HANDICAP_CAPTCHA_,%s AS ACCOUNT_ID_, "
				+ " ii.STATE_ FROM %s ih LEFT JOIN %s ii ON ih.%s = ii.%s "
				+ " LEFT JOIN ibm_handicap_item ihi ON ih.HANDICAP_ITEM_ID_ = ihi.IBM_HANDICAP_ITEM_ID_ WHERE %s = ? "
				+ " AND ih.STATE_ != ? AND ii.STATE_ != ? AND ihi.STATE_ != ? ORDER BY ii.STATE_ ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		if (IbmTypeEnum.AGENT.equals(category)) {
			return super.findMapList(
					String.format(sql, "AGENT_ACCOUNT_", "IBM_HANDICAP_AGENT_ID_", "ibm_handicap_agent", "ibm_ha_info",
							"IBM_HANDICAP_AGENT_ID_", "HANDICAP_AGENT_ID_", "HA_USER_ID_"), parameterList);
		} else {
			return super.findMapList(
					String.format(sql, "MEMBER_ACCOUNT_", "IBM_HANDICAP_MEMBER_ID_", "ibm_handicap_member",
							"ibm_hm_info", "IBM_HANDICAP_MEMBER_ID_", "HANDICAP_MEMBER_ID_", "HM_USER_ID_"),
					parameterList);
		}
	}

	/**
	 * 查询在线数
	 *
	 * @param handicapUserId 盘口用户主键
	 * @param category       盘口类别
	 * @return 在线数
	 */
	public String getOnlineCount(String handicapUserId, IbmTypeEnum category) throws SQLException {
		String sql = "SELECT  %s AS key_ FROM %s WHERE %s = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		if (IbmTypeEnum.AGENT.equals(category)) {
			return super.findString(String.format(sql, "ONLINE_AGENTS_COUNT_", "ibm_ha_user", "IBM_HA_USER_ID_"),
					parameterList);
		} else {
			return super.findString(String.format(sql, "ONLINE_MEMBERS_COUNT_", "ibm_hm_user", "IBM_HM_USER_ID_"),
					parameterList);
		}
	}

	/**
	 * 更新最大在线数
	 *
	 * @param handicapUserId  盘口用户主键
	 * @param category        盘口类别
	 * @param onlineNumberMax 最大在线数
	 * @param desc            描述
	 */
	public void updateOnlineMax(String handicapUserId, IbmTypeEnum category, int onlineNumberMax, String desc)
			throws SQLException {
		String sql = "UPDATE %s SET ONLINE_NUMBER_MAX_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,"
				+ " DESC_ = ? WHERE %s = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(onlineNumberMax);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		if (IbmTypeEnum.AGENT.equals(category)) {
			super.execute(String.format(sql, "ibm_ha_user", "IBM_HA_USER_ID_"), parameterList);
		} else {
			super.execute(String.format(sql, "ibm_hm_user", "IBM_HM_USER_ID_"), parameterList);
		}
	}

	/**
	 * 获取用户没有的盘口信息
	 *
	 * @param appUserId 用户主键
	 * @param category  盘口主键
	 * @return 盘口信息列表
	 */
	public List<Map<String, Object>> listNoHandicap(String appUserId, IbmTypeEnum category) throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ID_,HANDICAP_NAME_,HANDICAP_CODE_,HANDICAP_CATEGORY_ FROM ibm_handicap "
				+ " WHERE IBM_HANDICAP_ID_ NOT IN (SELECT HANDICAP_ID_ FROM %s WHERE APP_USER_ID_ = ? "
				+ " AND STATE_ != ?) AND HANDICAP_CATEGORY_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(category.name());
		parameterList.add(IbmStateEnum.DEL.name());
		if (IbmTypeEnum.AGENT.equals(category)) {
			return super.findMapList(String.format(sql, "ibm_ha_user"), parameterList);
		} else {
			return super.findMapList(String.format(sql, "ibm_hm_user"), parameterList);
		}
	}

	/**
	 * 保存盘口用户数据
	 *
	 * @param handicapUser 盘口用户数据
	 * @return 盘口用户主键
	 */
	public String save(Object handicapUser) throws Exception {
		return super.dao.save(handicapUser);
	}

	public List<String> findUserByType(String userType) throws SQLException {
		String sql = "SELECT APP_USER_ID_ FROM `app_user` WHERE APP_USER_TYPE_ =? AND STATE_ !=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userType);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findStringList("APP_USER_ID_",sql,parameterList);
	}
}
