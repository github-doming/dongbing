package com.ibm.old.v1.servlet.ibm_plan_statistics.ibm_event_planstatistics.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.ibm_event_planstatistics.t.entity.IbmEventPlanstatisticsT;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmEventPlanstatisticsTService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmEventPlanstatisticsT对象数据
	 */
	public String save(IbmEventPlanstatisticsT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_event_planstatistics的 IBM_EVENT_PLANSTATISTICS_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_event_planstatistics set state_='DEL' where IBM_EVENT_PLANSTATISTICS_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EVENT_PLANSTATISTICS_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_event_planstatistics的 IBM_EVENT_PLANSTATISTICS_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_event_planstatistics set state_='DEL' where IBM_EVENT_PLANSTATISTICS_ID_ in("
					+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_event_planstatistics的 IBM_EVENT_PLANSTATISTICS_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_event_planstatistics where IBM_EVENT_PLANSTATISTICS_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EVENT_PLANSTATISTICS_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_event_planstatistics的 IBM_EVENT_PLANSTATISTICS_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_event_planstatistics where IBM_EVENT_PLANSTATISTICS_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmEventPlanstatisticsT实体信息
	 *
	 * @param entity IbmEventPlanstatisticsT实体
	 */
	public void update(IbmEventPlanstatisticsT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_event_planstatistics表主键查找IbmEventPlanstatisticsT实体
	 *
	 * @param id ibm_event_planstatistics 主键
	 * @return IbmEventPlanstatisticsT实体
	 */
	public IbmEventPlanstatisticsT find(String id) throws Exception {
		return (IbmEventPlanstatisticsT) this.dao.find(IbmEventPlanstatisticsT.class, id);

	}

	/**
	 * 获取分页Map数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_event_planstatistics where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_event_planstatistics  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_event_planstatistics  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmEventPlanstatisticsT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmEventPlanstatisticsT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_event_planstatistics where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_event_planstatistics  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_event_planstatistics  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmEventPlanstatisticsT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_event_planstatistics  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmEventPlanstatisticsT数据信息
	 *
	 * @return 可用<IbmEventPlanstatisticsT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_event_planstatistics  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmEventPlanstatisticsT.class, sql);
	}

	/**
	 * 获取一项新的事件数据
	 *
	 * @return 事件数据信息
	 */
	public Map<String, Object> findNewEvent() throws SQLException {
		//新的事件数据
		String sql = "SELECT IBM_EVENT_PLANSTATISTICS_ID_ ,EVENT_CONTENT_ FROM ibm_event_planstatistics  where "
				+ " EVENT_STATE_ = ? and STATE_ = ?  LIMIT 1";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.BEGIN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		Map<String, Object> newInfo = super.dao.findMap(sql, parameters);
		if (ContainerTool.isEmpty(newInfo)) {
			return null;
		}
		//更新信息为处理中
		parameters.clear();
		sql = "UPDATE ibm_event_planstatistics set EVENT_STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where IBM_EVENT_PLANSTATISTICS_ID_ = ?";
		parameters.add(IbmStateEnum.PROCESS.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(newInfo.get("IBM_EVENT_PLANSTATISTICS_ID_"));
		super.dao.execute(sql, parameters);

		return newInfo;
	}

	/**
	 * 更新处理结果
	 *
	 * @param eventPlanstatisticsId 事件id
	 * @param result                处理结果
	 * @param path                  结果路径
	 * @return 更新条数
	 */
	public Object updateResult(String eventPlanstatisticsId, Object result, String path) throws SQLException {
		String sql = "UPDATE ibm_event_planstatistics SET EVENT_RESULT_ = ?,EVENT_PATH_ = ?, EVENT_STATE_ = ?, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE IBM_EVENT_PLANSTATISTICS_ID_ = ?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(result.toString());
		parameters.add(path);
		parameters.add(IbmStateEnum.FINISH.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(eventPlanstatisticsId);
		return super.dao.execute(sql, parameters);

	}

	/**
	 * 获取统计结果
	 *
	 * @param eventId 事件id
	 * @return 统计结果
	 */
	public String findResult(String eventId) throws SQLException {
		String sql = "SELECT EVENT_RESULT_ FROM ibm_event_planstatistics  where "
				+ " IBM_EVENT_PLANSTATISTICS_ID_ = ? and EVENT_STATE_ = ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(eventId);
		parameters.add(IbmStateEnum.FINISH.name());
		return super.dao.findString("EVENT_RESULT_", sql, parameters);
	}
	public String findFile(String eventId) throws SQLException {
		String sql = "SELECT EVENT_PATH_ FROM ibm_event_planstatistics  where "
				+ " IBM_EVENT_PLANSTATISTICS_ID_ = ? and EVENT_STATE_ = ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(eventId);
		parameters.add(IbmStateEnum.FINISH.name());
		return super.dao.findString("EVENT_PATH_", sql, parameters);
	}
}
