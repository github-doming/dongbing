package com.ibm.follow.servlet.cloud.ibm_event_login_snatch.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_event_login_snatch.entity.IbmEventLoginSnatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmEventLoginSnatchService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmEventLoginSnatch对象数据
	 */
	public String save(IbmEventLoginSnatch entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_event_login_snatch的 IBM_EVENT_LOGIN_SNATCH_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_event_login_snatch set state_='DEL' where IBM_EVENT_LOGIN_SNATCH_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EVENT_LOGIN_SNATCH_ID_主键id数组的数据
	 * @param idArray 要删除ibm_event_login_snatch的 IBM_EVENT_LOGIN_SNATCH_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_event_login_snatch set state_='DEL' where IBM_EVENT_LOGIN_SNATCH_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_event_login_snatch的 IBM_EVENT_LOGIN_SNATCH_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_event_login_snatch where IBM_EVENT_LOGIN_SNATCH_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EVENT_LOGIN_SNATCH_ID_主键id数组的数据
	 * @param idArray 要删除ibm_event_login_snatch的 IBM_EVENT_LOGIN_SNATCH_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_event_login_snatch where IBM_EVENT_LOGIN_SNATCH_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmEventLoginSnatch实体信息
	 * @param entity IbmEventLoginSnatch实体
	 */
	public void update(IbmEventLoginSnatch entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_event_login_snatch表主键查找IbmEventLoginSnatch实体
	 * @param id ibm_event_login_snatch 主键
	 * @return IbmEventLoginSnatch实体
	 */
	public IbmEventLoginSnatch find(String id) throws Exception {
		return (IbmEventLoginSnatch) this.dao.find(IbmEventLoginSnatch. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_event_login_snatch where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_event_login_snatch  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_event_login_snatch  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmEventLoginSnatch数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmEventLoginSnatch数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_event_login_snatch where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_event_login_snatch  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_event_login_snatch  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmEventLoginSnatch. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_event_login_snatch  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmEventLoginSnatch数据信息
	 * @return 可用<IbmEventLoginSnatch>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_event_login_snatch  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmEventLoginSnatch. class,sql);
	}

	/**
	 * 查询所有数据
	 * @return 所有数据
	 */
	public List<Map<String, Object>> findAllData(String tableName) throws SQLException {
		String sql="select CUSTOMER_TYPE_,EVENT_CONTENT_,EVENT_STATE_,EVENT_RESULT_,EXEC_NUMBER_,DESC_,CREATE_TIME_  from "+tableName+" where STATE_ = ?  order by CREATE_TIME_ desc limit 0,100";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameters);
	}
}
