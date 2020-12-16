package com.ibm.old.v1.cloud.ibm_available_time.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_available_time.t.entity.IbmAvailableTimeT;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmAvailableTimeTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmAvailableTimeT对象数据
	 */
	public String save(IbmAvailableTimeT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_available_time的 IBM_AVAILABLE_TIME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_available_time set state_='DEL' where IBM_AVAILABLE_TIME_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_AVAILABLE_TIME_ID_主键id数组的数据
	 * @param idArray 要删除ibm_available_time的 IBM_AVAILABLE_TIME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_available_time set state_='DEL' where IBM_AVAILABLE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_available_time的 IBM_AVAILABLE_TIME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_available_time where IBM_AVAILABLE_TIME_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_AVAILABLE_TIME_ID_主键id数组的数据
	 * @param idArray 要删除ibm_available_time的 IBM_AVAILABLE_TIME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_available_time where IBM_AVAILABLE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmAvailableTimeT实体信息
	 * @param entity IbmAvailableTimeT实体
	 */
	public void update(IbmAvailableTimeT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_available_time表主键查找IbmAvailableTimeT实体
	 * @param id ibm_available_time 主键
	 * @return IbmAvailableTimeT实体
	 */
	public IbmAvailableTimeT find(String id) throws Exception {
		return (IbmAvailableTimeT) this.dao.find(IbmAvailableTimeT. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_available_time where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_available_time  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_available_time  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmAvailableTimeT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmAvailableTimeT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_available_time where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_available_time  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_available_time  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmAvailableTimeT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_available_time  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmAvailableTimeT数据信息
	 * @return 可用<IbmAvailableTimeT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_available_time  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmAvailableTimeT. class,sql);
	}

	/**
	 * 获取结束时间戳
	 * @param userId 用户id
	 * @return 结束时间戳
	 */
	public long findEndTimeLong(String userId) throws SQLException {
		String sql = "SELECT END_TIME_LONG_ FROM ibm_available_time  where APP_USER_ID_ = ? and state_!='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		String endTimeLong = super.dao.findString("END_TIME_LONG_",sql,parameterList);
		return Long.parseLong(endTimeLong);
	}

	/**
	 * 根据用户id获取用户可用时长
	 * @param userId 用户id
	 * @return 用户可用时长
	 */
	public IbmAvailableTimeT findByUserId(String userId) throws Exception {
		String sql = "SELECT * FROM ibm_available_time  where APP_USER_ID_ = ? and state_!='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		return (IbmAvailableTimeT) super.dao.findObject(IbmAvailableTimeT. class,sql,parameterList);

	}
}
