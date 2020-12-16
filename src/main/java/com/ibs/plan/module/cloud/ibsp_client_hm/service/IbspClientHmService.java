package com.ibs.plan.module.cloud.ibsp_client_hm.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_client_hm.entity.IbspClientHm;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBSP_客户端盘口会员 服务类
 * @author Robot
 */
public class IbspClientHmService extends BaseServiceProxy {

	/**
	 * 保存IBSP_客户端盘口会员 对象数据
	 * @param entity IbspClientHm对象数据
	 */
	public String save(IbspClientHm entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_client_hm 的 IBSP_CLIENT_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_client_hm set state_='DEL' where IBSP_CLIENT_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSP_CLIENT_HM_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_client_hm 的 IBSP_CLIENT_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_client_hm set state_='DEL' where IBSP_CLIENT_HM_ID_ in(" + stringBuilder.toString() + ")";
			super.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_client_hm  的 IBSP_CLIENT_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_client_hm where IBSP_CLIENT_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		super.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSP_CLIENT_HM_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_client_hm 的 IBSP_CLIENT_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_client_hm where IBSP_CLIENT_HM_ID_ in(" + stringBuilder.toString() + ")";
			super.execute(sql, null);
		}
	}

	/**
	 * 更新IbspClientHm实体信息
	 * @param entity IBSP_客户端盘口会员 实体
	 */
	public void update(IbspClientHm entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_client_hm表主键查找 IBSP_客户端盘口会员 实体
	 * @param id ibsp_client_hm 主键
	 * @return IBSP_客户端盘口会员 实体
	 */
	public IbspClientHm find(String id) throws Exception {
		return dao.find(IbspClientHm.class,id);
	}

	/**
	 * 获取存在会员ID列表
	 *
	 * @param clientId     客户端主键
	 * @param handicapCode 盘口编码
	 * @return 存在会员ID列表
	 */
	public List<String> listExitsId(String clientId, String handicapCode) throws SQLException {
		String sql = "SELECT EXIST_HM_ID_ FROM ibsp_client_hm WHERE CLIENT_ID_ = ? AND HANDICAP_CODE_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientId);
		parameterList.add(handicapCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findStringList("EXIST_HM_ID_", sql, parameterList);
	}

	/**
	 * 获取存在会员id
	 * @param handicapMemberId	盘口会员id
	 * @return 存在会员id
	 */
	public Map<String, Object> findExistHmInfo(String handicapMemberId) throws SQLException {
		String sql = "select CLIENT_CODE_,EXIST_HM_ID_ from ibsp_client_hm where HANDICAP_MEMBER_ID_= ? and STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 获取实体对象
	 * @param handicapMemberId	盘口会员id
	 * @return 实体对象
	 */
	public IbspClientHm findByHmId(String handicapMemberId) throws Exception {
		String sql="select * from ibsp_client_hm where HANDICAP_MEMBER_ID_= ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspClientHm.class,sql,parameterList);
	}

	/**
	 * 根据存在主键查找会员主键
	 * @param existHmId 存在主键
	 * @return 会员主键
	 */
	public String findHmIdByExistId(String existHmId) throws SQLException {
		String sql = "select HANDICAP_MEMBER_ID_ as key_ from ibsp_client_hm where EXIST_HM_ID_= ? and STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(existHmId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 根据 会员主键 查找 存在主键
	 * @param handicapMemberId 会员主键
	 * @return 存在主键
	 */
	public String findExistIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "select EXIST_HM_ID_ as key_ from ibsp_client_hm where HANDICAP_MEMBER_ID_ = ? and STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 获取用户会员列表信息
	 * @param appUserId	用户id
	 * @return 会员列表信息
	 */
	public Map<String, String> findUserMemberInfos(String appUserId) throws SQLException {
		String sql="SELECT EXIST_HM_ID_ as key_,CLIENT_CODE_ as value_ FROM ibsp_client_hm ich"
				+ " LEFT JOIN ibsp_handicap_member ihm ON ich.HANDICAP_MEMBER_ID_ = ihm.IBSP_HANDICAP_MEMBER_ID_"
				+ " WHERE ihm.APP_USER_ID_=? AND ich.STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findKVMap(sql,parameterList);
	}

	/**
	 * 获取用户会员列表信息
	 * @param appUserId	用户id
	 * @return 会员列表信息
	 */
	public List<Map<String, Object>> findMemberInfos(String appUserId) throws SQLException {
		String sql="SELECT EXIST_HM_ID_,HANDICAP_MEMBER_ID_,CLIENT_CODE_ FROM ibsp_client_hm ich"
				+ " LEFT JOIN ibsp_handicap_member ihm ON ich.HANDICAP_MEMBER_ID_ = ihm.IBSP_HANDICAP_MEMBER_ID_"
				+ " WHERE ihm.APP_USER_ID_=? AND ich.STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameterList);
	}

	/**
	 * 通过盘口会员主键查询并删除已存在盘口会员信息
	 * @param handicapMemberId	盘口会员id
	 */
	public void updateByHmId(String handicapMemberId) throws SQLException {
		List<Object> parameterList = new ArrayList<>(6);
		//更新数据
		String sql = "update ibsp_client_hm set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ? "
				+ " where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbsStateEnum.CLOSE.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取客户机会员列表
	 * @param clientCode    客户端编码
	 * @return
	 */
	public  List<String> findHmIds(String clientCode,String handicapCode) throws SQLException {
		String sql="select HANDICAP_MEMBER_ID_ from ibsp_client_hm where CLIENT_CODE_=?  and STATE_=? ";
		List<Object> parameterList=new ArrayList<>(3);
		parameterList.add(clientCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		if(StringTool.notEmpty(handicapCode)){
			sql += " and HANDICAP_CODE_=? ";
			parameterList.add(handicapCode);
		}
		return super.findStringList("HANDICAP_MEMBER_ID_",sql,parameterList);
	}

	/**
	 * 获取会员ID列表
	 * @param clientId	中心端客户端主键
	 */
	public List<String> listHmIds(String clientId) throws SQLException {
		String sql="select HANDICAP_MEMBER_ID_ from ibsp_client_hm where CLIENT_ID_=? and STATE_=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(clientId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findStringList("HANDICAP_MEMBER_ID_",sql,parameterList);
	}

	/**
	 * 批量修改
	 * @param handicapMemberIds     盘口会员ids
	 */
	public void updateByHmIds(List<String> handicapMemberIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibsp_client_hm set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ?,DESC_ = ?"
				+ " where STATE_=? and HANDICAP_MEMBER_ID_ in(");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbsStateEnum.CLOSE.name());
		parameterList.add("切换客户机登出");
		parameterList.add(IbsStateEnum.OPEN.name());
		for(String handicapMemberId:handicapMemberIds){
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(),parameterList);
	}

}
