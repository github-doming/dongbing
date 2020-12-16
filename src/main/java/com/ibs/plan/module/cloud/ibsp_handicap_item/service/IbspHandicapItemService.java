package com.ibs.plan.module.cloud.ibsp_handicap_item.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.connector.pc.handicap.HandicapItemEditAction;
import com.ibs.plan.module.cloud.ibsp_handicap_item.entity.IbspHandicapItem;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBSP_盘口详情 服务类
 * @author Robot
 */
public class IbspHandicapItemService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口详情 对象数据
	 * @param entity IbspHandicapItem对象数据
	 */
	public String save(IbspHandicapItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_handicap_item 的 IBSP_HANDICAP_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_handicap_item set state_='DEL' where IBSP_HANDICAP_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HANDICAP_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_handicap_item 的 IBSP_HANDICAP_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_handicap_item set state_='DEL' where IBSP_HANDICAP_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_handicap_item  的 IBSP_HANDICAP_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_handicap_item where IBSP_HANDICAP_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HANDICAP_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_handicap_item 的 IBSP_HANDICAP_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_handicap_item where IBSP_HANDICAP_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHandicapItem实体信息
	 * @param entity IBSP_盘口详情 实体
	 */
	public void update(IbspHandicapItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_handicap_item表主键查找 IBSP_盘口详情 实体
	 * @param id ibsp_handicap_item 主键
	 * @return IBSP_盘口详情 实体
	 */
	public IbspHandicapItem find(String id) throws Exception {
		return dao.find(IbspHandicapItem.class,id);
	}

	/**
	 * 修改最后一次使用时间
	 * @param handicapItemId    盘口详情
	 */
	public void updateUseTime(String handicapItemId, Date nowTime) throws SQLException {
		String sql="update ibsp_handicap_item set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where IBSP_HANDICAP_ITEM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapItemId);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.execute(sql,parameterList);
	}


	/**
	 * 获取盘口详情信息
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口校验码
	 * @return 盘口详情信息
	 */
	public Map<String, Object> findInfo(String handicapUrl, String handicapCaptcha) throws SQLException {
		String sql = "SELECT IBSP_HANDICAP_ITEM_ID_ as HANDICAP_ITEM_ID_,HANDICAP_ID_ FROM ibsp_handicap_item where "
				+ " HANDICAP_URL_ = ? and HANDICAP_CAPTCHA_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapUrl);
		parameterList.add(handicapCaptcha);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取盘口详情信息
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口校验码
	 * @param handicapId      盘口ID
	 * @return 盘口详情信息
	 */
	public String findId(String handicapUrl, String handicapCaptcha, String handicapId) throws SQLException {
		String sql = "SELECT IBSP_HANDICAP_ITEM_ID_ FROM ibsp_handicap_item where "
				+ " HANDICAP_ID_ = ? and HANDICAP_URL_ = ? and HANDICAP_CAPTCHA_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(handicapId);
		parameterList.add(handicapUrl);
		parameterList.add(handicapCaptcha);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("IBSP_HANDICAP_ITEM_ID_", sql, parameterList);
	}

	/**
	 * 获取 盘口详情信息
	 *
	 * @param handicapItemId 盘口详情主键
	 * @return 盘口详情信息
	 */
	public Map<String, Object> findInfo(String handicapItemId) throws SQLException {
		String sql = "SELECT HANDICAP_URL_,HANDICAP_CAPTCHA_,HANDICAP_ID_ FROM ibsp_handicap_item where "
				+ " IBSP_HANDICAP_ITEM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapItemId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
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
		String sql = "UPDATE ibsp_handicap_item SET HANDICAP_URL_ = ?, HANDICAP_CAPTCHA_ = ?, UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ = ?, DESC_ = ? WHERE IBSP_HANDICAP_ITEM_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(handicapUrl);
		parameterList.add(handicapCaptcha);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(HandicapItemEditAction.class.getName().concat("盘口详情修正"));
		parameterList.add(handicapItemId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.execute(sql, parameterList);
	}

	/**
	 *  获取盘口详情列表
	 * @param handicapId    盘口id
	 * @return
	 */
	public PageCoreBean<Map<String, Object>> findHandicapItem(String handicapId, int pageIndex, int pageSize) throws SQLException {
		String sqlCount = "select count(*) FROM ibsp_handicap_item where HANDICAP_ID_=? and STATE_=?";
		String sql="select IBSP_HANDICAP_ITEM_ID_,HANDICAP_URL_,HANDICAP_CAPTCHA_,UPDATE_TIME_LONG_ from "
				+ " ibsp_handicap_item where HANDICAP_ID_=? and STATE_=? ORDER BY UPDATE_TIME_LONG_ DESC ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}
}
