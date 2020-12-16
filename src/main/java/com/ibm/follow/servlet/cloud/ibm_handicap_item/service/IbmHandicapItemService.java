package com.ibm.follow.servlet.cloud.ibm_handicap_item.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.pc.handicap.HandicapItemEditAction;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.entity.IbmHandicapItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHandicapItemService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicapItem对象数据
	 */
	public String save(IbmHandicapItem entity) throws Exception {
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
			String sql =
					"delete from ibm_handicap_item where IBM_HANDICAP_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHandicapItem实体信息
	 *
	 * @param entity IbmHandicapItem实体
	 */
	public void update(IbmHandicapItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_handicap_item表主键查找IbmHandicapItem实体
	 *
	 * @param id ibm_handicap_item 主键
	 * @return IbmHandicapItem实体
	 */
	public IbmHandicapItem find(String id) throws Exception {
		return (IbmHandicapItem) this.dao.find(IbmHandicapItem.class, id);

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
	 * 获取分页IbmHandicapItem数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicapItem数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmHandicapItem.class, sql, null, pageIndex, pageSize, sqlCount);
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
	 * 按照更新顺序查询所有可用IbmHandicapItem数据信息
	 *
	 * @return 可用<IbmHandicapItem>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicapItem.class, sql);
	}

	/**
	 * 获取 盘口详情信息
	 *
	 * @param handicapItemId 盘口详情主键
	 * @return 盘口详情信息
	 */
	public Map<String, Object> findInfo(String handicapItemId) throws SQLException {
		String sql = "SELECT HANDICAP_URL_,HANDICAP_CAPTCHA_,HANDICAP_ID_ FROM `ibm_handicap_item` where "
				+ " IBM_HANDICAP_ITEM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapItemId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取盘口详情信息
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口校验码
	 * @param category        盘口类别
	 * @return 盘口详情信息
	 */
	public Map<String, Object> findInfo(String handicapUrl, String handicapCaptcha, IbmTypeEnum category)
			throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ITEM_ID_ as HANDICAP_ITEM_ID_,HANDICAP_ID_ FROM `ibm_handicap_item` where "
				+ " HANDICAP_URL_ = ? and HANDICAP_CAPTCHA_ = ? and HANDICAP_CATEGORY_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapUrl);
		parameterList.add(handicapCaptcha);
		parameterList.add(category.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 获取盘口详情信息
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口校验码
	 * @param handicapId      盘口ID
	 * @param category        盘口类别
	 * @return 盘口详情信息
	 */
	public String findId(String handicapUrl, String handicapCaptcha, String handicapId, IbmTypeEnum category)
			throws SQLException {
		String sql = "SELECT IBM_HANDICAP_ITEM_ID_ FROM `ibm_handicap_item` where "
				+ " HANDICAP_ID_ = ? and HANDICAP_URL_ = ? and HANDICAP_CAPTCHA_ = ? and HANDICAP_CATEGORY_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(handicapId);
		parameterList.add(handicapUrl);
		parameterList.add(handicapCaptcha);
		parameterList.add(category.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_HANDICAP_ITEM_ID_", sql, parameterList);
	}

	/**
	 * 更新盘口详情信息
	 *
	 * @param handicapItemId  盘口详情ID
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @return 更新结果
	 */
	public int update(String handicapItemId, String handicapUrl, String handicapCaptcha) throws SQLException {
		String sql = "UPDATE `ibm_handicap_item` SET HANDICAP_URL_ = ?, HANDICAP_CAPTCHA_ = ?, UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ = ?, DESC_ = ? WHERE IBM_HANDICAP_ITEM_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(handicapUrl);
		parameterList.add(handicapCaptcha);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(HandicapItemEditAction.class.getName().concat("盘口详情修正"));
		parameterList.add(handicapItemId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.execute(sql, parameterList);
	}

    /**
     *  获取盘口详情列表
     * @param handicapId    盘口id
     * @return
     */
    public PageCoreBean<Map<String, Object>>  findHandicapItem(String handicapId,int pageIndex,int pageSize) throws SQLException {
    	String sqlCount = "select count(*) FROM ibm_handicap_item where HANDICAP_ID_=? and STATE_=?";
        String sql="select IBM_HANDICAP_ITEM_ID_,HANDICAP_URL_,HANDICAP_CAPTCHA_,UPDATE_TIME_LONG_ from "
                + " ibm_handicap_item where HANDICAP_ID_=? and STATE_=? ORDER BY UPDATE_TIME_LONG_ DESC ";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
    }

    /**
     * 修改最后一次使用时间
     * @param handicapItemId    盘口详情
     */
    public void updateUseTime(String handicapItemId) throws SQLException {
        String sql="update ibm_handicap_item set UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_HANDICAP_ITEM_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(handicapItemId);
        parameterList.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql,parameterList);
    }
}
