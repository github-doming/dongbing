package com.ibm.follow.servlet.cloud.ibm_log_hm.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import com.ibm.follow.servlet.cloud.ibm_log_hm.entity.IbmLogHm;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Robot
 */
public class IbmLogHmService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmLogHm对象数据
	 */
	public String save(IbmLogHm entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_log_hm的 IBM_LOG_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_log_hm set state_='DEL' where IBM_LOG_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_LOG_HM_ID_主键id数组的数据
	 * @param idArray 要删除ibm_log_hm的 IBM_LOG_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_log_hm set state_='DEL' where IBM_LOG_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_log_hm的 IBM_LOG_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_log_hm where IBM_LOG_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_LOG_HM_ID_主键id数组的数据
	 * @param idArray 要删除ibm_log_hm的 IBM_LOG_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_log_hm where IBM_LOG_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmLogHm实体信息
	 * @param entity IbmLogHm实体
	 */
	public void update(IbmLogHm entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_log_hm表主键查找IbmLogHm实体
	 * @param id ibm_log_hm 主键
	 * @return IbmLogHm实体
	 */
	public IbmLogHm find(String id) throws Exception {
		return (IbmLogHm) this.dao.find(IbmLogHm. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_log_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_log_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_log_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmLogHm数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmLogHm数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_log_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_log_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_log_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmLogHm. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_log_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmLogHm数据信息
	 * @return 可用<IbmLogHm>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_log_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmLogHm. class,sql);
	}
}
