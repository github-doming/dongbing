package com.ibm.follow.servlet.client.ibmc_hm_bet_item.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.alibaba.fastjson.JSONArray;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_hm_bet_item.entity.IbmcHmBetItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmcHmBetItemService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcHmBetItem对象数据
	 */
	public String save(IbmcHmBetItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_hm_bet_item的 IBMC_HM_BET_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_hm_bet_item set state_='DEL' where IBMC_HM_BET_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HM_BET_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet_item的 IBMC_HM_BET_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_hm_bet_item set state_='DEL' where IBMC_HM_BET_ITEM_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_hm_bet_item的 IBMC_HM_BET_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_hm_bet_item where IBMC_HM_BET_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HM_BET_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet_item的 IBMC_HM_BET_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_hm_bet_item where IBMC_HM_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHmBetItem实体信息
	 *
	 * @param entity IbmcHmBetItem实体
	 */
	public void update(IbmcHmBetItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_hm_bet_item表主键查找IbmcHmBetItem实体
	 *
	 * @param id ibmc_hm_bet_item 主键
	 * @return IbmcHmBetItem实体
	 */
	public IbmcHmBetItem find(String id) throws Exception {
		return (IbmcHmBetItem) this.dao.find(IbmcHmBetItem.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet_item  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHmBetItem数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcHmBetItem数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet_item  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHmBetItem.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHmBetItem数据信息
	 *
	 * @return 可用<IbmcHmBetItem>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHmBetItem.class, sql);
	}
	/**
	 * 保存投注结果信息
	 *
	 * @param existHmId   已存在盘口会员id
	 * @param hmBetInfoId 投注信息主键
	 * @param period      期数
	 * @param gameCode    游戏code
	 * @param betResult   投注结果
	 * @param betItems    投注项
	 * @return
	 */
	public void save(String existHmId, String hmBetInfoId, Object period, GameUtil.Code gameCode, JSONArray betResult,
			List<String> betItems) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters=new ArrayList<>();
		sql.append("insert into ibmc_hm_bet_item (IBMC_HM_BET_ITEM_ID_,EXIST_HM_ID_,HM_BET_INFO_ID_,");
		sql.append("PERIOD_,GAME_CODE_,BET_INFO_,BET_NUMBER_,ODDS_,CREATE_TIME_,CREATE_TIME_LONG_,");
		sql.append("UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
		Date nowTime=new Date();
		for(int i=0;i<betResult.size();i++){
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?),");
			JSONArray betInfo=betResult.getJSONArray(i);
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(existHmId);
			parameters.add(hmBetInfoId);
			parameters.add(period);
			parameters.add(gameCode.name());
			parameters.add(betInfo.getString(1));
			parameters.add(betInfo.get(0).toString());
			parameters.add(betInfo.getString(3));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbmStateEnum.OPEN.name());
			betItems.remove(betInfo.getString(1).concat("|").concat(String.valueOf(NumberTool.longValueT(betInfo.getDouble(2)))));
		}
		sql.deleteCharAt(sql.length()-1);
		super.dao.execute(sql.toString(),parameters);
	}
}
