package com.ibm.follow.servlet.cloud.ibm_sys_card_price.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_card_price.entity.IbmSysCardPrice;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBM_点卡价格 服务类
 * @author Robot
 */
public class IbmSysCardPriceService extends BaseServiceProxy {

	/**
	 * 保存IBM_点卡价格 对象数据
	 * @param entity IbmSysCardPrice对象数据
	 */
	public String save(IbmSysCardPrice entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_sys_card_price 的 IBM_SYS_CARD_PRICE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_card_price set state_='DEL' where IBM_SYS_CARD_PRICE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBM_SYS_CARD_PRICE_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_sys_card_price 的 IBM_SYS_CARD_PRICE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_sys_card_price set state_='DEL' where IBM_SYS_CARD_PRICE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_sys_card_price  的 IBM_SYS_CARD_PRICE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_card_price where IBM_SYS_CARD_PRICE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBM_SYS_CARD_PRICE_ID_主键id数组的数据
	 * @param idArray 要删除ibm_sys_card_price 的 IBM_SYS_CARD_PRICE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_card_price where IBM_SYS_CARD_PRICE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysCardPrice实体信息
	 * @param entity IBM_点卡价格 实体
	 */
	public void update(IbmSysCardPrice entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_card_price表主键查找 IBM_点卡价格 实体
	 * @param id ibm_sys_card_price 主键
	 * @return IBM_点卡价格 实体
	 */
	public IbmSysCardPrice find(String id) throws Exception {
		return dao.find(IbmSysCardPrice.class,id);
	}

	/**
	 * 获取卡种信息
	 * @return
	 */
	public List<Map<String, Object>> findCardInfos() throws SQLException {
		String sql="select USE_TIME_,CURRENT_PRICE_ from ibm_sys_card_price where STATE_=? order by USE_TIME_";
		List<Object> parameters=new ArrayList<>();
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql,parameters);
	}

	/**
	 * 判断是否存在卡种
	 * @param useTime		使用时长
	 * @param currentPrice	现价
	 * @return
	 */
	public String findCrad(int useTime, int currentPrice) throws SQLException {
		String sql="select IBM_SYS_CARD_PRICE_ID_ from ibm_sys_card_price where USE_TIME_=? and CURRENT_PRICE_=?"
				+ " and STATE_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(useTime);
		parameters.add(currentPrice);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_SYS_CARD_PRICE_ID_",sql,parameters);
	}

	/**
	 * 获取点数所有信息
	 * @return
	 */
	public List<Map<String, Object>> findAllInfo() throws SQLException {
		String sql="select IBM_SYS_CARD_PRICE_ID_ as CARD_PRICE_ID_,PRICE_NAME_,USE_TIME_,CURRENT_PRICE_,SN_,CREATE_USER_,"
				+ "CREATE_TIME_LONG_,STATE_ from ibm_sys_card_price where STATE_!=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(IbmStateEnum.DEL.name());
		return super.findMapList(sql,parameters);
	}
}
