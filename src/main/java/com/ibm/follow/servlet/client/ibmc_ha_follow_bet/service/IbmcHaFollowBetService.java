package com.ibm.follow.servlet.client.ibmc_ha_follow_bet.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import com.ibm.follow.servlet.client.ibmc_ha_follow_bet.entity.IbmcHaFollowBet;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Robot
 */
public class IbmcHaFollowBetService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmcHaFollowBet对象数据
	 */
	public String save(IbmcHaFollowBet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibmc_ha_follow_bet的 IBMC_HA_FOLLOW_BET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_ha_follow_bet set state_='DEL' where IBMC_HA_FOLLOW_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HA_FOLLOW_BET_ID_主键id数组的数据
	 * @param idArray 要删除ibmc_ha_follow_bet的 IBMC_HA_FOLLOW_BET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibmc_ha_follow_bet set state_='DEL' where IBMC_HA_FOLLOW_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibmc_ha_follow_bet的 IBMC_HA_FOLLOW_BET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_ha_follow_bet where IBMC_HA_FOLLOW_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HA_FOLLOW_BET_ID_主键id数组的数据
	 * @param idArray 要删除ibmc_ha_follow_bet的 IBMC_HA_FOLLOW_BET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_ha_follow_bet where IBMC_HA_FOLLOW_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHaFollowBet实体信息
	 * @param entity IbmcHaFollowBet实体
	 */
	public void update(IbmcHaFollowBet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_ha_follow_bet表主键查找IbmcHaFollowBet实体
	 * @param id ibmc_ha_follow_bet 主键
	 * @return IbmcHaFollowBet实体
	 */
	public IbmcHaFollowBet find(String id) throws Exception {
		return (IbmcHaFollowBet) this.dao.find(IbmcHaFollowBet. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_ha_follow_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_ha_follow_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_ha_follow_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHaFollowBet数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmcHaFollowBet数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_ha_follow_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_ha_follow_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_ha_follow_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHaFollowBet. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_ha_follow_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHaFollowBet数据信息
	 * @return 可用<IbmcHaFollowBet>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_ha_follow_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHaFollowBet. class,sql);
	}
}
