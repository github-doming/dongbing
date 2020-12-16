package com.ibm.old.v1.cloud.ibm_point.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_point.t.entity.IbmPointT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPointTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmPointT对象数据
	 */
	public String save(IbmPointT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_point的 IBM_POINT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_point set state_='DEL' where IBM_POINT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_POINT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_point的 IBM_POINT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_point set state_='DEL' where IBM_POINT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_point的 IBM_POINT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_point where IBM_POINT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_POINT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_point的 IBM_POINT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_point where IBM_POINT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmPointT实体信息
	 *
	 * @param entity IbmPointT实体
	 */
	public void update(IbmPointT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_point表主键查找IbmPointT实体
	 *
	 * @param id ibm_point 主键
	 * @return IbmPointT实体
	 */
	public IbmPointT find(String id) throws Exception {
		return (IbmPointT) this.dao.find(IbmPointT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_point where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_point  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_point  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmPointT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmPointT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_point where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_point  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_point  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmPointT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_point  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmPointT数据信息
	 *
	 * @return 可用<IbmPointT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_point  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmPointT.class, sql);
	}

	/**
	 * 获取用户的积分使用情况
	 * @param userId 用户Id
	 * @return  积分使用情况
	 */
	public IbmPointT findByUserId(String userId) throws Exception {
		String sql = "SELECT * FROM ibm_point  where APP_USER_ID_ = ? and state_ != 'DEL' ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		return (IbmPointT) super.dao.findObject(IbmPointT.class, sql, parameterList);
	}
}
