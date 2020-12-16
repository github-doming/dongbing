package com.ibs.plan.module.cloud.ibsp_event_config_set.service;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_event_config_set.entity.IbspEventConfigSet;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBSP_客户设置事件 服务类
 * @author Robot
 */
public class IbspEventConfigSetService extends BaseServiceProxy {

	/**
	 * 保存IBSP_客户设置事件 对象数据
	 * @param entity IbspEventConfigSet对象数据
	 */
	public String save(IbspEventConfigSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_event_config_set 的 IBSP_EVENT_CONFIG_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_event_config_set set state_='DEL' where IBSP_EVENT_CONFIG_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_EVENT_CONFIG_SET_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_event_config_set 的 IBSP_EVENT_CONFIG_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_event_config_set set state_='DEL' where IBSP_EVENT_CONFIG_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_event_config_set  的 IBSP_EVENT_CONFIG_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_event_config_set where IBSP_EVENT_CONFIG_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_EVENT_CONFIG_SET_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_event_config_set 的 IBSP_EVENT_CONFIG_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_event_config_set where IBSP_EVENT_CONFIG_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspEventConfigSet实体信息
	 * @param entity IBSP_客户设置事件 实体
	 */
	public void update(IbspEventConfigSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_event_config_set表主键查找 IBSP_客户设置事件 实体
	 * @param id ibsp_event_config_set 主键
	 * @return IBSP_客户设置事件 实体
	 */
	public IbspEventConfigSet find(String id) throws Exception {
		return dao.find(IbspEventConfigSet.class,id);
	}

	/**
	 * 修改事件结果
	 * @param eventId		事件id
	 * @param result		事件结果
	 * @param requestType		事件状态
	 */
	public void updateResultByState(String eventId, JSONObject result, IbsStateEnum requestType) throws SQLException {
		String sql="update ibsp_event_config_set set EVENT_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,EVENT_RESULT_=? where "
				+ " IBSP_EVENT_CONFIG_SET_ID_=?";
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
		super.dao.execute(sql, parameters);
	}

	/**
	 * 获取事件内容
	 * @param eventIds	事件ids
	 * @return 事件内容
	 */
	public Map<String, String> findEventInfos(List<String> eventIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		//获取待发送
		sql.append("select IBSP_EVENT_CONFIG_SET_ID_ as key_,EVENT_CONTENT_ as value_ FROM ibsp_event_config_set"
				+ " where EVENT_STATE_ = ? and STATE_ = ? and IBSP_EVENT_CONFIG_SET_ID_ in (");
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.BEGIN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		StringBuilder sqlPlus=new StringBuilder();
		for(String eventId:eventIds){
			sqlPlus.append("?,");
			parameters.add(eventId);
		}
		sqlPlus.replace(sqlPlus.length()-1,sqlPlus.length(),")");
		sql.append(sqlPlus);
		Map<String, String> map=findKVMap(sql.toString(),parameters);

		if(ContainerTool.isEmpty(map)){
			return map;
		}
		parameters.clear();
		sql.delete(0,sql.length());
		//修改为发送
		sql.append("update ibsp_event_config_set set EVENT_STATE_ = ? where EVENT_STATE_ = ? and STATE_ = ? and IBSP_EVENT_CONFIG_SET_ID_ in (");
		parameters.add(IbsStateEnum.SEND.name());
		parameters.add(IbsStateEnum.BEGIN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.addAll(eventIds);
		sql.append(sqlPlus);
		super.dao.execute(sql.toString(),parameters);
		return map;
	}
}
