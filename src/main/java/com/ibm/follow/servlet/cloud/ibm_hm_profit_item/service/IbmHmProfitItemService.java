package com.ibm.follow.servlet.cloud.ibm_hm_profit_item.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_item.entity.IbmHmProfitItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHmProfitItemService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHmProfitItem对象数据
	 */
	public String save(IbmHmProfitItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_hm_profit_item的 IBM_HM_PROFIT_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_profit_item set state_='DEL' where IBM_HM_PROFIT_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HM_PROFIT_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_profit_item的 IBM_HM_PROFIT_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_hm_profit_item set state_='DEL' where IBM_HM_PROFIT_ITEM_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_hm_profit_item的 IBM_HM_PROFIT_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_profit_item where IBM_HM_PROFIT_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HM_PROFIT_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_profit_item的 IBM_HM_PROFIT_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_hm_profit_item where IBM_HM_PROFIT_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHmProfitItem实体信息
	 *
	 * @param entity IbmHmProfitItem实体
	 */
	public void update(IbmHmProfitItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_hm_profit_item表主键查找IbmHmProfitItem实体
	 *
	 * @param id ibm_hm_profit_item 主键
	 * @return IbmHmProfitItem实体
	 */
	public IbmHmProfitItem find(String id) throws Exception {
		return (IbmHmProfitItem) this.dao.find(IbmHmProfitItem.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_hm_profit_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_profit_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_profit_item  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmProfitItem数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHmProfitItem数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_profit_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_profit_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_profit_item  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmHmProfitItem.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_profit_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmProfitItem数据信息
	 *
	 * @return 可用<IbmHmProfitItem>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_profit_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmProfitItem.class, sql);
	}

	/**
	 * 保存盘口会员盈亏详情
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param betItemInfo      投注项信息
	 * @param profitTh         盈亏金额
	 * @param fundTh           投注金额
	 * @param nowTime          创建时间
	 */
	public void save(String handicapMemberId,Map<String, Object> betItemInfo, long profitTh,
			long fundTh, Date nowTime) throws Exception {
		IbmHmProfitItem profitItem = new IbmHmProfitItem();
		profitItem.setHandicapMemberId(handicapMemberId);
		profitItem.setHmBetItemId(betItemInfo.get("BET_ITEM_ID_"));
		profitItem.setFollowMemberAccount(betItemInfo.get("FOLLOW_MEMBER_ACCOUNT_"));
		profitItem.setFundT(fundTh);
		profitItem.setProfitT(profitTh);
		profitItem.setCreateTime(nowTime);
		profitItem.setCreateTimeLong(System.currentTimeMillis());
		profitItem.setUpdateTimeLong(System.currentTimeMillis());
		profitItem.setState(IbmStateEnum.OPEN.name());
		this.save(profitItem);
	}
}
