package com.ibm.follow.connector.admin.manage3.handicap.service;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 后台管理盘口会员服务类
 * @Author: Dongming
 * @Date: 2019-11-07 17:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmAdminHandicapMemberService extends IbmHandicapMemberService {

	/**
	 * 删除某个盘口所有盘口会员的信息
	 *
	 * @param handicapId 盘口主键
	 * @param nowTime    删除时间
	 * @param desc       描述
	 */
	public void delByHandicapId(String handicapId, Date nowTime, String desc) throws SQLException {
		//关联盘口会员
		String sqlFormat = "UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, "
				+ " DESC_ = ? WHERE HANDICAP_MEMBER_ID_ IN (SELECT IBM_HANDICAP_MEMBER_ID_ FROM ibm_handicap_member WHERE "
				+ " HANDICAP_ID_ = ? AND STATE_ != ?) AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(String.format(sqlFormat, "ibm_hm_info"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_notice"), parameterList);

		//关联盘口
		sqlFormat = "UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? "
				+ " WHERE HANDICAP_ID_ = ? AND STATE_ != ?";
		parameterList.remove(parameterList.size() - 1);
		super.execute(String.format(sqlFormat, "ibm_hm_bet_item"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_game_set"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit_item"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit_period"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit_period_vr"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit_vr"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_set"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_user"), parameterList);

		super.execute(String.format(sqlFormat, "ibm_handicap_member"), parameterList);
	}
	/**
	 * 删除某个盘口所有盘口代理的信息
	 *
	 * @param handicapMemberIds 盘口会员主键列表
	 * @param nowTime          删除时间
	 * @param desc             描述
	 */
	public void delByHmId(List<String> handicapMemberIds, Date nowTime, String desc) throws SQLException {
		//关联盘口会员
		StringBuilder sqlFormat = new StringBuilder("UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ ",  DESC_ = ? WHERE STATE_ != ? AND %s IN (");
		List<Object> parameterList = new ArrayList<>(7 + handicapMemberIds.size());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String handicapMemberId : handicapMemberIds) {
			sqlFormat.append("?,");
			parameterList.add(handicapMemberId);
		}
		sqlFormat.deleteCharAt(sqlFormat.lastIndexOf(",")).append(")");

		super.execute(String.format(sqlFormat.toString(), "ibm_hm_bet_item","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_info","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_notice","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit_item","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit_period","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit_period_vr","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit_vr","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_set","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_game_set","HANDICAP_MEMBER_ID_"), parameterList);

		super.execute(String.format(sqlFormat.toString(), "ibm_handicap_member","IBM_HANDICAP_MEMBER_ID_"), parameterList);
	}

	/**
	 * 删除某个盘口所有盘口代理的信息
	 *
	 * @param handicapMemberId 盘口会员主键列表
	 * @param nowTime          删除时间
	 * @param desc             描述
	 */
	public void delByHmId(String handicapMemberId, Date nowTime, String desc) throws SQLException {
		//关联盘口会员
		StringBuilder sqlFormat = new StringBuilder("UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ ",  DESC_ = ? WHERE %s =? AND STATE_ != ? ");
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());

		super.execute(String.format(sqlFormat.toString(), "ibm_hm_bet_item","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_info","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_notice","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit_item","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit_period","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit_period_vr","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_profit_vr","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_set","HANDICAP_MEMBER_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_hm_game_set","HANDICAP_MEMBER_ID_"), parameterList);

		super.execute(String.format(sqlFormat.toString(), "ibm_handicap_member","IBM_HANDICAP_MEMBER_ID_"), parameterList);
	}

	/**
	 * 根据盘口ID查找盘口会员信息
	 *
	 * @param handicapId 盘口ID
	 * @return 盘口会员信息列表
	 */
	public List<Map<String, Object>> listInfoByHandicapId(String handicapId) throws SQLException {
		String sql = "select IBM_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_,APP_USER_ID_ from ibm_handicap_member "
				+ " where HANDICAP_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return dao.findMapList(sql, parameterList);
	}


	/**
	 * 获取盘口代理/会员信息
	 *
	 * @param category   盘口类别
	 * @param handicapId 盘口主键
	 * @param key        查询key
	 **/
	public List<Map<String, Object>> listHandicapMembers(IbmTypeEnum category, String handicapId, String key) throws SQLException {
		String sql = "SELECT APP_USER_NAME_,HANDICAP_NAME_,ihu.HANDICAP_ID_ AS HANDICAP_ID_,IBM_HANDICAP_MEMBER_ID_,ihi.STATE_,ihu.MEMBER_ACCOUNT_, '%s' AS CATEGORY " +
				"FROM ibm_handicap_member ihu LEFT JOIN app_user au ON ihu.APP_USER_ID_ = au.APP_USER_ID_ " +
				"LEFT JOIN ibm_handicap ih ON ihu.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_ " +
				"LEFT JOIN ibm_hm_info ihi ON ihu.IBM_HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ " +
				"WHERE ihu.STATE_ != ? AND au.STATE_ != ? ";

		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		if (StringTool.notEmpty(handicapId)) {
			sql += " AND HANDICAP_ID_ = ? ";
			parameterList.add(handicapId);
		}
		if (StringTool.notEmpty(key)) {
			sql += " AND ( HANDICAP_NAME_ LIKE ? or APP_USER_NAME_ LIKE ? or MEMBER_ACCOUNT_ LIKE ?) ";
			key = "%" + key + "%";
			parameterList.add(key);
			parameterList.add(key);
			parameterList.add(key);
		}
		sql += " ORDER BY ihi.STATE_,ihu.HANDICAP_CODE_,ihu.MEMBER_ACCOUNT_";
		return super.findMapList(String.format(sql, category.getMsg()),parameterList);
	}

	/**
	 * 删除某个盘口指定用户的盘口会员的信息
	 *
	 * @param handicapId 盘口主键
	 * @param nowTime    删除时间
	 * @param desc       描述
	 */
	public void delByHandicapId(List<String> users,String handicapId, Date nowTime, String desc) throws SQLException {
		//关联盘口会员
		String sqlFormat = "UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, "
				+ " DESC_ = ? WHERE HANDICAP_MEMBER_ID_ IN (SELECT IBM_HANDICAP_MEMBER_ID_ FROM ibm_handicap_member WHERE "
				+ " HANDICAP_ID_ = ? AND STATE_ != ? AND APP_USER_ID_ IN(";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		StringBuilder sb = new StringBuilder();
		for (String userId :users){
			sb.append("?,");
			parameterList.add(userId);
		}
		sb.replace(sb.length()-1,sb.length(),")");
		sqlFormat = sqlFormat+ sb.toString()+") AND STATE_ != ?";
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(String.format(sqlFormat, "ibm_hm_info"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_notice"), parameterList);

		parameterList.clear();
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());

		//关联盘口
		sqlFormat = "UPDATE ibm_hm_user SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? "
				+ " WHERE HANDICAP_ID_ = ? AND STATE_ != ?  AND APP_USER_ID_ IN(";
		sb = new StringBuilder();
		for (String userId :users){
			sb.append("?,");
			parameterList.add(userId);
		}
		sb.replace(sb.length()-1,sb.length(),")");
		sqlFormat = sqlFormat+ sb.toString()+"";

		super.execute(sqlFormat, parameterList);

		//关联盘口
		sqlFormat = "UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? "
				+ " WHERE  STATE_ != ? AND HANDICAP_MEMBER_ID_ IN ( " +
				"SELECT IBM_HANDICAP_MEMBER_ID_ FROM `ibm_handicap_member` WHERE HANDICAP_ID_ = ? )";
		parameterList.clear();
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(handicapId);

		super.execute(String.format(sqlFormat, "ibm_hm_bet_item"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_game_set"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit_item"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit_period"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit_period_vr"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_profit_vr"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_hm_set"), parameterList);

		//关联盘口
		sqlFormat = "UPDATE ibm_handicap_member SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? "
				+ " WHERE   STATE_ != ?  AND HANDICAP_ID_ = ? ";
		super.execute(sqlFormat, parameterList);
	}
}
