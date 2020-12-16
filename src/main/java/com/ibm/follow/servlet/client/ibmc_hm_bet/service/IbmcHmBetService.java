package com.ibm.follow.servlet.client.ibmc_hm_bet.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_hm_bet.entity.IbmcHmBet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmcHmBetService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcHmBet对象数据
	 */
	public String save(IbmcHmBet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_hm_bet的 IBMC_HM_BET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_hm_bet set state_='DEL' where IBMC_HM_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HM_BET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet的 IBMC_HM_BET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_hm_bet set state_='DEL' where IBMC_HM_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_hm_bet的 IBMC_HM_BET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_hm_bet where IBMC_HM_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HM_BET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet的 IBMC_HM_BET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_hm_bet where IBMC_HM_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHmBet实体信息
	 *
	 * @param entity IbmcHmBet实体
	 */
	public void update(IbmcHmBet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_hm_bet表主键查找IbmcHmBet实体
	 *
	 * @param id ibmc_hm_bet 主键
	 * @return IbmcHmBet实体
	 */
	public IbmcHmBet find(String id) throws Exception {
		return (IbmcHmBet) this.dao.find(IbmcHmBet.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHmBet数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcHmBet数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHmBet.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHmBet数据信息
	 *
	 * @return 可用<IbmcHmBet>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHmBet.class, sql);
	}
	/**
	 * 根据id获取投注内容和金额
	 *
	 * @param hmBetId 盘口会员投注id
	 * @return
	 */
	public Map<String, Object> findById(String hmBetId) throws SQLException {
		String sql = "select IBMC_HM_BET_ID_ as key_,BET_INFO_ as value_ from ibmc_hm_bet where IBMC_HM_BET_ID_=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(hmBetId);
		return super.findKVMap(sql, parameters);
	}
	/**
	 * 修改数据投注状态
	 *
	 * @param hmBetIds 投注ids
	 * @param state    状态
	 */
	public void updateState(String hmBetIds, String state) throws SQLException {
		List<Object> parameters = new ArrayList<>();
		String sql = "update ibmc_hm_bet set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBMC_HM_BET_ID_ in ('"
				+ String.join("','", hmBetIds.split(",")) + "')";
		parameters.add(state);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		super.dao.execute(sql, parameters);
	}
	/**
	 * 获取投注信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @return
	 */
	public Map<String, Object> findBetInfo(String existHmId, GameUtil.Code gameCode, Object period)
			throws SQLException {
		String sql = "select IBMC_HM_BET_ID_ as key_,BET_INFO_ as value_ from ibmc_hm_bet where EXIST_HM_ID_=? and GAME_CODE_=? and PERIOD_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(gameCode.name());
		parameters.add(period);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}
	/**
	 * 获取投注信息codes
	 *
	 * @param hmBetIds 会员投注ids
	 * @return
	 */
	public List<String> findByIds(String hmBetIds) throws SQLException {
		String sql = "select HA_FOLLOW_BET_ID_ from ibmc_hm_bet where IBMC_HM_BET_ID_ in ('" + String
				.join("','", hmBetIds.split(",")) + "') AND STATE_ != ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findStringList("HA_FOLLOW_BET_ID_", sql, parameters);
	}
	/**
	 * 获取投注信息编码
	 * @param hmBetIds	会员投注ids
	 * @return
	 */
	public List<String> findBetInfoCodes(List<String> hmBetIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append("select BET_INFO_CODE_ from ibmc_hm_bet where IBMC_HM_BET_ID_ in(");
		for(String hmBetId:hmBetIds){
			sql.append("?,");
			parameters.add(hmBetId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.dao.findStringList("BET_INFO_CODE_",sql.toString(),parameters);
	}
	/**
	 * 获取投注信息编码
	 * @param hmBetInfoIds	投注信息ids
	 * @return
	 */
	public List<String> findSuccessBetInfoCodes(List<String> hmBetInfoIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append("select HM_BET_ID_LIST_ from ibmc_hm_bet_info where IBMC_HM_BET_INFO_ID_ in(");
		for (String hmBetInfoId:hmBetInfoIds){
			sql.append("?,");
			parameters.add(hmBetInfoId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		List<String> list=super.dao.findStringList("HM_BET_ID_LIST_",sql.toString(),parameters);
		if(ContainerTool.isEmpty(list)){
			return list;
		}
		List<String> hmBetIds=new ArrayList<>();
		for(String betIdList:list){
			String[] hmBetIdList =betIdList.split(",");
			for (String hmBetId : hmBetIdList) {
				if(!hmBetIds.contains(hmBetId)){
					hmBetIds.add(hmBetId);
				}
			}
		}
		return findBetInfoCodes(hmBetIds);
	}
}
