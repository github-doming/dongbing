package com.ibm.old.v1.cloud.ibm_handicap_item.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.entity.IbmHandicapItemT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHandicapItemTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicapItemT对象数据
	 */
	public String save(IbmHandicapItemT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_handicap_item的 IBM_HANDICAP_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_handicap_item set state_='DEL' where IBM_HANDICAP_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HANDICAP_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_item的 IBM_HANDICAP_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_handicap_item set state_='DEL' where IBM_HANDICAP_ITEM_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_handicap_item的 IBM_HANDICAP_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_handicap_item where IBM_HANDICAP_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HANDICAP_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_item的 IBM_HANDICAP_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_handicap_item where IBM_HANDICAP_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHandicapItemT实体信息
	 *
	 * @param entity IbmHandicapItemT实体
	 */
	public void update(IbmHandicapItemT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_handicap_item表主键查找IbmHandicapItemT实体
	 *
	 * @param id ibm_handicap_item 主键
	 * @return IbmHandicapItemT实体
	 */
	public IbmHandicapItemT find(String id) throws Exception {
		return (IbmHandicapItemT) this.dao.find(IbmHandicapItemT. class,id);

	}

	/**
	 * 获取分页Map数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHandicapItemT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicapItemT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmHandicapItemT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHandicapItemT数据信息
	 *
	 * @return 可用<IbmHandicapItemT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicapItemT. class,sql);
	}

	/**
	 * 根据盘口主键获取盘口详情信息
	 *
	 * @param handicapId 盘口主键
	 * @return 盘口详情信息
	 * @throws Exception
	 */
	public IbmHandicapItemT findByHandicapId(String handicapId) throws Exception {
		String sql="select * from ibm_handicap_item where HANDICAP_ID_= ? and STATE_ != 'DEL'";
		List<Object> parameterList=new ArrayList<>(1);
		parameterList.add(handicapId);
		Object obj = super.dao.findObject(IbmHandicapItemT.class, sql, parameterList);
		return obj == null ? null : (IbmHandicapItemT) obj;
	}

	/**
	 * 
	 * @param handicapId 盘口ID
	 * @Description: 根据盘口ID逻辑删除
	 *
	 * 参数说明 
	 * @param handicapId 盘口ID
	 * <p>
	 * 参数说明
	 */
	public void delByHandicapId(String handicapId) throws SQLException {
		String sql = "update ibm_handicap_item set state_='DEL',UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_ID_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(handicapId);
		super.dao.execute(sql, parameters);
	}

	/**
	 * 根据盘口ID集合逻辑删除选中数据
	 * @param idArray	盘口ID集合
	 * @throws SQLException
	 */
	public void delAllByHandicapId(String[] idArray ) throws SQLException {
		if (idArray != null) {
			StringBuilder sql = new StringBuilder("update ibm_handicap_item set state_= ? ，UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_ID_ in(");
			List<Object> parameterList = new ArrayList<>();
			parameterList.add(IbmStateEnum.DEL.name());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			for (String id : idArray) {
				sql.append("?,");
				parameterList.add(id);
			}
			sql.replace(sql.length()-1, sql.length(), ")");
			sql.replace(sql.length() - 1, sql.length(), ")");
			dao.execute(sql.toString(), parameterList);
		}
	}


	/**
	 * 获取盘口详情的网址验证码
	 *
	 * @param handicapItemId 盘口详情id
	 * @return 网址，验证码
	 */
	public Map<String, Object> findHandicapInfo(String handicapItemId) throws SQLException {
		String sql = "select HANDICAP_URL_,HANDICAP_CAPTCHA_ from ibm_handicap_item where IBM_HANDICAP_ITEM_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapItemId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
}