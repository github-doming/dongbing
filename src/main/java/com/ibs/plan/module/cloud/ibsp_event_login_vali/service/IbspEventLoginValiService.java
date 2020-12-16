package com.ibs.plan.module.cloud.ibsp_event_login_vali.service;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_event_login_vali.entity.IbspEventLoginVali;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBSP_客户验证登录事件 服务类
 * @author Robot
 */
public class IbspEventLoginValiService extends BaseServiceProxy {

	/**
	 * 保存IBSP_客户验证登录事件 对象数据
	 * @param entity IbspEventLoginVali对象数据
	 */
	public String save(IbspEventLoginVali entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_event_login_vali 的 IBSP_EVENT_LOGIN_VALI_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_event_login_vali set state_='DEL' where IBSP_EVENT_LOGIN_VALI_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_EVENT_LOGIN_VALI_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_event_login_vali 的 IBSP_EVENT_LOGIN_VALI_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_event_login_vali set state_='DEL' where IBSP_EVENT_LOGIN_VALI_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_event_login_vali  的 IBSP_EVENT_LOGIN_VALI_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_event_login_vali where IBSP_EVENT_LOGIN_VALI_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_EVENT_LOGIN_VALI_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_event_login_vali 的 IBSP_EVENT_LOGIN_VALI_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_event_login_vali where IBSP_EVENT_LOGIN_VALI_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspEventLoginVali实体信息
	 * @param entity IBSP_客户验证登录事件 实体
	 */
	public void update(IbspEventLoginVali entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_event_login_vali表主键查找 IBSP_客户验证登录事件 实体
	 * @param id ibsp_event_login_vali 主键
	 * @return IBSP_客户验证登录事件 实体
	 */
	public IbspEventLoginVali find(String id) throws Exception {
		return dao.find(IbspEventLoginVali.class,id);
	}

	/**
	 * 查找客户是否存在未完成事件
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 事件主键
	 */
	public String isExist(String handicapMemberId) throws SQLException {
		String sql = "select IBSP_EVENT_LOGIN_VALI_ID_ from ibsp_event_login_vali where HANDICAP_MEMBER_ID_ = ? and EVENT_STATE_ != ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapMemberId);
		parameters.add(IbsStateEnum.FINISH.name());
		return super.findString("IBSP_EVENT_LOGIN_VALI_ID_", sql, parameters);
	}

	/**
	 * 获取事件结果
	 * @param eventId		事件id
	 * @return 事件结果
	 */
	public Map<String, Object> findResult(String eventId) throws SQLException {
		String sql = "select HANDICAP_MEMBER_ID_,EVENT_RESULT_ from ibsp_event_login_vali where IBSP_EVENT_LOGIN_VALI_ID_ = ? and  EVENT_STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(eventId);
		parameters.add(IbsStateEnum.FINISH.name());
		return super.findMap(sql, parameters);
	}

	/**
	 * 获取事件信息
	 * @param eventId		事件id
	 * @return
	 */
	public String findEventInfo(String eventId) throws SQLException {
		String sql="select HANDICAP_MEMBER_ID_ from ibsp_event_login_vali where EVENT_STATE_=? and STATE_=?"
				+ " and IBSP_EVENT_LOGIN_VALI_ID_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbsStateEnum.BEGIN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(eventId);

		String handicapMemberId=super.dao.findString("HANDICAP_MEMBER_ID_",sql,parameters);

		if (StringTool.isEmpty(handicapMemberId)){
			return handicapMemberId;
		}
		parameters.clear();
		sql="update ibsp_event_login_vali set EVENT_STATE_=? where EVENT_STATE_=? and STATE_=?"
				+ " and IBSP_EVENT_LOGIN_VALI_ID_=?";
		parameters.add(IbsStateEnum.SEND.name());
		parameters.add(IbsStateEnum.BEGIN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(eventId);
		super.dao.execute(sql,parameters);
		return handicapMemberId;
	}
	/**
	 * 修改状态
	 *  @param eventId     事件id
	 * @param result      执行结果
	 * @param requestType 执行状态
	 */
	public boolean updateResultByState(String eventId, JSONObject result, IbsStateEnum requestType) throws SQLException {
		String sql="update ibsp_event_login_vali set EVENT_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,EVENT_RESULT_=? where "
				+ " IBSP_EVENT_LOGIN_VALI_ID_=?";
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(requestType.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(result.toString());
		parameters.add(eventId);
		if(IbsStateEnum.PROCESS.name().equals(requestType.name())){
			sql += " and EVENT_STATE_=?";
			parameters.add(IbsStateEnum.SEND.name());
		}
		synchronized (eventId) {
			return super.dao.execute(sql, parameters) == 1;
		}
	}
	/**
	 * 获取事件会员id
	 * @param eventId		事件id
	 * @return
	 */
	public String findEventHmId(String eventId) throws SQLException {
		String sql="select HANDICAP_MEMBER_ID_ from ibsp_event_login_vali where IBSP_EVENT_LOGIN_VALI_ID_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(eventId);
		return super.dao.findString("HANDICAP_MEMBER_ID_",sql,parameters);
	}
}
