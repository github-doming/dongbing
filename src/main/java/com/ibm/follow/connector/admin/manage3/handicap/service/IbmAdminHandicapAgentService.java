package com.ibm.follow.connector.admin.manage3.handicap.service;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 后台管理盘口代理服务类
 * @Author: Dongming
 * @Date: 2019-11-07 17:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmAdminHandicapAgentService extends IbmHandicapAgentService {


	/**
	 * 删除某个盘口指定用户的盘口代理的信息
	 *
	 * @param handicapId 盘口主键
	 * @param nowTime    删除时间
	 * @param desc       描述
	 */
	public void delByHandicapId(List<String> users,String handicapId, Date nowTime, String desc) throws SQLException {
		//关联盘口代理
		String sqlFormat = "UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, "
				+ " DESC_ = ? WHERE HANDICAP_AGENT_ID_ IN (SELECT IBM_HANDICAP_AGENT_ID_ FROM ibm_handicap_agent WHERE "
				+ " HANDICAP_ID_ = ? AND STATE_ != ? AND APP_USER_ID_ IN (";
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
		super.execute(String.format(sqlFormat, "ibm_handicap_agent_member"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_ha_member_bet_item"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_ha_notice"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_ha_info"), parameterList);

		//关联盘口
		sqlFormat = "UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? "
				+ " WHERE HANDICAP_ID_ = ? AND STATE_ != ?";
		parameterList.clear();
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(String.format(sqlFormat, "ibm_ha_set"), parameterList);

		sb = new StringBuilder();
		for (String userId :users){
			sb.append("?,");
			parameterList.add(userId);
		}
		sb.replace(sb.length()-1,sb.length(),")");
		sqlFormat = sqlFormat+" AND APP_USER_ID_ IN ("+ sb.toString();
		super.execute(String.format(sqlFormat, "ibm_handicap_agent"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_ha_user"), parameterList);

	}

	/**
	 * 删除某个盘口所有盘口代理的信息
	 *
	 * @param handicapAgentIds 盘口代理主键列表
	 * @param nowTime          删除时间
	 * @param desc             描述
	 */
	public void delByHaId(List<String> handicapAgentIds, Date nowTime, String desc) throws SQLException {
		//关联盘口代理
		StringBuilder sqlFormat = new StringBuilder("UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ ",  DESC_ = ? WHERE STATE_ != ? AND %s IN (");
		List<Object> parameterList = new ArrayList<>(7 + handicapAgentIds.size());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String handicapAgentId : handicapAgentIds) {
			sqlFormat.append("?,");
			parameterList.add(handicapAgentId);
		}
		sqlFormat.deleteCharAt(sqlFormat.lastIndexOf(",")).append(")");

		super.execute(String.format(sqlFormat.toString(), "ibm_handicap_agent_member","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_ha_member_bet_item","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_ha_notice","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_ha_info","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_ha_set","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_handicap_agent","IBM_HANDICAP_AGENT_ID_"), parameterList);

	}

	/**
	 * 删除某个盘口所有盘口代理的信息
	 *
	 * @param handicapAgentId 盘口代理主键
	 * @param nowTime          删除时间
	 * @param desc             描述
	 */
	public void delByHaId(String handicapAgentId, Date nowTime, String desc) throws SQLException {
		//关联盘口代理
		StringBuilder sqlFormat = new StringBuilder("UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ ",  DESC_ = ? WHERE %s = ? AND STATE_ != ?  ");
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());

		super.execute(String.format(sqlFormat.toString(), "ibm_ha_follow_period","HANDICAP_AGENT_ID_"), parameterList);
        super.execute(String.format(sqlFormat.toString(), "ibm_ha_game_set","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_ha_member_bet_item","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_ha_notice","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_ha_info","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_ha_set","HANDICAP_AGENT_ID_"), parameterList);
		super.execute(String.format(sqlFormat.toString(), "ibm_handicap_agent","IBM_HANDICAP_AGENT_ID_"), parameterList);

	}

	/**
	 * 根据盘口id获取所有盘口代理信息
	 *
	 * @param handicapId 盘口id
	 * @return 盘口代理信息列表
	 */
	public List<Map<String, Object>> listInfoByHandicapId(String handicapId) throws SQLException {
		String sql = "select IBM_HANDICAP_AGENT_ID_ as HANDICAP_AGENT_ID_,APP_USER_ID_ from ibm_handicap_agent where "
				+ " HANDICAP_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 根据盘口用户id查找主键列表
	 * @param handicapUserId 盘口用户id
	 * @return 主键列表
	 */
	public List<String> listIdByHuId(String handicapUserId) throws SQLException {
		String sql = "select IBM_HANDICAP_AGENT_ID_ as key_ from ibm_handicap_agent where "
				+ " HA_USER_ID_ = ? and STATE_ ！= ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql, parameterList);
	}

	/**
	 * 主键是否存在
	 * @param handicapAgentId 代理Id
	 *
	 **/
	public boolean isExist(String handicapAgentId) throws SQLException {
		String sql = "select STATE_ as key_ from ibm_handicap_agent where "
				+ " IBM_HANDICAP_AGENT_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());
		return ContainerTool.notEmpty(super.findMap(sql,parameterList));
	}

	/**
	 * 获取盘口代理/会员信息
	 *
	 * @param category   盘口类别
	 * @param handicapId 盘口主键
	 * @param key        查询key
	 **/
	public List<Map<String, Object>> listHandicapAgents(IbmTypeEnum category, String handicapId, String key) throws SQLException {
		String sql = "SELECT APP_USER_NAME_,HANDICAP_NAME_,ihu.HANDICAP_ID_ AS HANDICAP_ID_,IBM_HANDICAP_AGENT_ID_,ihi.STATE_,ihu.AGENT_ACCOUNT_, '%s' AS CATEGORY " +
				"FROM ibm_handicap_agent ihu LEFT JOIN app_user au ON ihu.APP_USER_ID_ = au.APP_USER_ID_ " +
				"LEFT JOIN ibm_handicap ih ON ihu.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_ " +
				"LEFT JOIN ibm_ha_info ihi ON ihu.IBM_HANDICAP_AGENT_ID_ = ihi.HANDICAP_AGENT_ID_ " +
				"WHERE ihu.STATE_ != ? AND au.STATE_ != ? ";

		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		if (StringTool.notEmpty(handicapId)) {
			sql += " AND HANDICAP_ID_ = ? ";
			parameterList.add(handicapId);
		}
		if (StringTool.notEmpty(key)) {
			sql += " AND ( HANDICAP_NAME_ LIKE ? or APP_USER_NAME_ LIKE ? or AGENT_ACCOUNT_ LIKE ?) ";
			key = "%" + key + "%";
			parameterList.add(key);
			parameterList.add(key);
			parameterList.add(key);
		}
		sql += " ORDER BY ihi.STATE_,ihu.HANDICAP_CODE_,ihu.AGENT_ACCOUNT_";
		return super.findMapList(String.format(sql, category.getMsg()),parameterList);
	}

	/**
	 * 删除某个盘口指定用户所有盘口代理的信息
	 *
	 * @param handicapId 盘口主键
	 * @param nowTime    删除时间
	 * @param desc       描述
	 */
	public void delByHandicapId(String handicapId, Date nowTime, String desc) throws SQLException {
		//关联盘口代理
		String sqlFormat = "UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, "
				+ " DESC_ = ? WHERE HANDICAP_AGENT_ID_ IN (SELECT IBM_HANDICAP_AGENT_ID_ FROM ibm_handicap_agent WHERE "
				+ " HANDICAP_ID_ = ? AND STATE_ != ?) AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(String.format(sqlFormat, "ibm_handicap_agent_member"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_ha_member_bet_item"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_ha_notice"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_ha_info"), parameterList);

		//关联盘口
		sqlFormat = "UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? "
				+ " WHERE HANDICAP_ID_ = ? AND STATE_ != ?";
		parameterList.remove(parameterList.size() - 1);
		super.execute(String.format(sqlFormat, "ibm_ha_set"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_handicap_agent"), parameterList);
		super.execute(String.format(sqlFormat, "ibm_ha_user"), parameterList);

	}

}
