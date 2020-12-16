package com.ibm.follow.servlet.cloud.ibm_event_client_close.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.entity.IbmEventClientClose;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmEventClientCloseService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmEventClientClose对象数据
	 */
	public String save(IbmEventClientClose entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_event_client_close的 IBM_EVENT_CLIENT_CLOSE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_event_client_close set state_='DEL' where IBM_EVENT_CLIENT_CLOSE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EVENT_CLIENT_CLOSE_ID_主键id数组的数据
	 * @param idArray 要删除ibm_event_client_close的 IBM_EVENT_CLIENT_CLOSE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_event_client_close set state_='DEL' where IBM_EVENT_CLIENT_CLOSE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_event_client_close的 IBM_EVENT_CLIENT_CLOSE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_event_client_close where IBM_EVENT_CLIENT_CLOSE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EVENT_CLIENT_CLOSE_ID_主键id数组的数据
	 * @param idArray 要删除ibm_event_client_close的 IBM_EVENT_CLIENT_CLOSE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_event_client_close where IBM_EVENT_CLIENT_CLOSE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmEventClientClose实体信息
	 * @param entity IbmEventClientClose实体
	 */
	public void update(IbmEventClientClose entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_event_client_close表主键查找IbmEventClientClose实体
	 * @param id ibm_event_client_close 主键
	 * @return IbmEventClientClose实体
	 */
	public IbmEventClientClose find(String id) throws Exception {
		return (IbmEventClientClose) this.dao.find(IbmEventClientClose. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_event_client_close where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_event_client_close  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_event_client_close  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmEventClientClose数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmEventClientClose数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_event_client_close where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_event_client_close  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_event_client_close  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmEventClientClose. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_event_client_close  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmEventClientClose数据信息
	 * @return 可用<IbmEventClientClose>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_event_client_close  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmEventClientClose. class,sql);
	}
	/**
	 * 获取一项新的事件数据
	 *
	 * @return 事件数据信息
	 */
	public Map<String, Object> findNewEvent() throws SQLException {
		String sql = "SELECT IBM_EVENT_CLIENT_CLOSE_ID_ as EVENT_ID_,EVENT_CONTENT_,CUSTOMER_TYPE_ FROM ibm_event_client_close "
				+ " where EVENT_STATE_ = ? and STATE_ = ?  LIMIT 1";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.BEGIN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		Map<String, Object> newInfo = super.findMap(sql, parameters);
		if (ContainerTool.isEmpty(newInfo)) {
			return null;
		}
		//更新信息为发送中
		parameters.clear();
		sql = "UPDATE ibm_event_client_close set EVENT_STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where IBM_EVENT_CLIENT_CLOSE_ID_ = ? and EVENT_STATE_ = ?";
		parameters.add(IbmStateEnum.SEND.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(newInfo.get("EVENT_ID_"));
		parameters.add(IbmStateEnum.BEGIN.name());
		int col;
		synchronized (IbmEventClientCloseService.class) {
			col = super.dao.execute(sql, parameters);
		}
		if (col == 1) {
			return newInfo;
		}
		return null;
	}
	/**
	 * 更新失败，重新写入事件
	 *
	 * @param eventId 事件id
	 */
	public void roll(String eventId) throws SQLException {
		String sql = "UPDATE ibm_event_client_close set EVENT_STATE_ = ?,EXEC_NUMBER_=EXEC_NUMBER_+1,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where IBM_EVENT_CLIENT_CLOSE_ID_ = ?";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(IbmStateEnum.BEGIN.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(eventId);
		super.dao.execute(sql, parameters);
	}
	/**
	 * 更新执行结果
	 *
	 * @param eventId 事件id
	 * @param result  执行结果
	 * @return 更新状态
	 */
	public void updateResult(String eventId, JSONObject result) throws SQLException {
		String sql = "UPDATE ibm_event_client_close set EVENT_STATE_ = ?,EVENT_RESULT_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ? where IBM_EVENT_CLIENT_CLOSE_ID_ = ?";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(IbmStateEnum.FINISH.name());
		parameters.add(result.toString());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(eventId);
		super.dao.execute(sql, parameters);
	}
	/**
	 * 获取处理结果
	 * @param eventId		事件id
	 * @return
	 */
	public Map<String, Object> findEventResult(String eventId) throws SQLException {
		String sql="select EVENT_STATE_,EVENT_RESULT_ from ibm_event_client_close where "
				+ " IBM_EVENT_CLIENT_CLOSE_ID_=?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(eventId);
		return super.dao.findMap(sql,parameters);
	}
	/**
	 * 获取事件结果
	 * @param eventId		事件id
	 * @return
	 */
	public Map<String, Object> findResult(String eventId) throws SQLException {
		String sql="select CUSTOMER_TYPE_,EVENT_CONTENT_,EVENT_RESULT_ from ibm_event_client_close where "
				+ " IBM_EVENT_CLIENT_CLOSE_ID_ = ? and EVENT_STATE_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(eventId);
		parameters.add(IbmStateEnum.FINISH.name());
		return super.dao.findMap(sql,parameters);
	}

	/**
	 * 查询所有数据
	 * @return 所有数据
	 */
	public List<Map<String, Object>> findAllData(String tableName) throws SQLException {
		String sql="select CUSTOMER_TYPE_,EVENT_CONTENT_,EVENT_STATE_,EVENT_RESULT_,EXEC_NUMBER_,DESC_,CREATE_TIME_ from "+tableName+" where STATE_ = ? order by CREATE_TIME_ desc limit 0,100";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameters);
	}
	/**
	 * 修改状态
	 *
	 * @param eventId     事件id
	 * @param msgObj      执行结果
	 * @param requestType 执行状态
	 */
	public boolean updateResultByState(String eventId, JSONObject msgObj, IbmStateEnum requestType) throws SQLException {
		String sql = "update ibm_event_client_close set EVENT_RESULT_=?,EVENT_STATE_=? where IBM_EVENT_CLIENT_CLOSE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(msgObj.toString());
		parameters.add(requestType.name());
		parameters.add(eventId);
		if (IbmStateEnum.PROCESS.name().equals(requestType.name())) {
			sql += " and EVENT_STATE_=?";
			parameters.add(IbmStateEnum.SEND.name());
		}
		synchronized (eventId) {
			return super.dao.execute(sql, parameters) == 1;
		}
	}

	/**
	 * 获取事件内容
	 * @param eventIds	事件ids
	 * @return 会员主键 - 事件ID
	 */
	public Map<String, Object> findEventInfos(List<String> eventIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("select IBM_EVENT_CLIENT_CLOSE_ID_ as key_,EVENT_CONTENT_ as value_ FROM ibm_event_client_close "
				+ " where EVENT_STATE_ = ? and STATE_ = ? and IBM_EVENT_CLIENT_CLOSE_ID_ in(");
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.BEGIN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		for(String eventId:eventIds){
			sql.append("?,");
			parameters.add(eventId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return findKVMap(sql.toString(),parameters);
	}

	/**
	 * 获取会员id
	 * @param eventId		事件id
	 * @return
	 */
	public Map<String, Object> findEventInfo(String eventId) throws SQLException {
		String sql="select CUSTOMER_TYPE_,EVENT_CONTENT_ from ibm_event_client_close where IBM_EVENT_CLIENT_CLOSE_ID_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(eventId);
		return super.dao.findMap(sql,parameters);
	}
}
