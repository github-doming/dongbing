package com.ibm.old.v1.client.ibm_client_exist_bet.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.entity.IbmClientExistBetT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmClientExistBetTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientExistBetT对象数据
	 */
	public String save(IbmClientExistBetT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_exist_bet的 IBM_CLIENT_EXIST_BET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_exist_bet set state_='DEL' where IBM_CLIENT_EXIST_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_EXIST_BET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_exist_bet的 IBM_CLIENT_EXIST_BET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_client_exist_bet set state_='DEL' where IBM_CLIENT_EXIST_BET_ID_ in(" + stringBuilder
							.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_exist_bet的 IBM_CLIENT_EXIST_BET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_exist_bet where IBM_CLIENT_EXIST_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_EXIST_BET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_exist_bet的 IBM_CLIENT_EXIST_BET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_client_exist_bet where IBM_CLIENT_EXIST_BET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientExistBetT实体信息
	 *
	 * @param entity IbmClientExistBetT实体
	 */
	public void update(IbmClientExistBetT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_exist_bet表主键查找IbmClientExistBetT实体
	 *
	 * @param id ibm_client_exist_bet 主键
	 * @return IbmClientExistBetT实体
	 */
	public IbmClientExistBetT find(String id) throws Exception {
		return (IbmClientExistBetT) this.dao.find(IbmClientExistBetT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_exist_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_exist_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_exist_bet  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientExistBetT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientExistBetT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_exist_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_exist_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_exist_bet  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmClientExistBetT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_exist_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientExistBetT数据信息
	 *
	 * @return 可用<IbmClientExistBetT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_exist_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientExistBetT.class, sql);
	}

	/**
	 * 查询待投注内容
	 *
	 * @param existHmId 客户端已存在盘口会员主键
	 * @param gameCode  游戏Code
	 * @param period    期数
	 * @return 待投注内容
	 */
	public List<Map<String, Object>> findBetInfo(String existHmId, String gameCode, Object period) throws Exception {
		String sql = "SELECT IBM_CLIENT_EXIST_BET_ID_ AS CLIENT_EXIST_BET_ID_, BET_INFO_, EXEC_BET_ITEM_ID_, "
				+ " HANDICAP_MEMBER_ID_,CLOUD_RECEIPT_BET_ID_ FROM ibm_client_exist_bet WHERE CLIENT_EXIST_HM_ID_ = ? "
				+ " AND GAME_CODE_ = ? AND PERIOD_ = ? AND STATE_ = ? ORDER BY CREATE_TIME_LONG_ DESC";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(existHmId);
		parameterList.add(gameCode);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.OPEN.name());
		return this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取投注项
	 *
	 * @param existHmId 存在盘口会员id
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @return 已完成的投注项
	 */
	public Map<String, List<Map<String, Object>>> mapFinishBetItem(String existHmId, String gameCode, String period)
			throws SQLException {
		String sql = "SELECT CLOUD_RECEIPT_BET_ID_, EXEC_BET_ITEM_ID_, PROFIT_T_, ODDS_ FROM `ibm_client_exist_bet` "
				+ " WHERE CLIENT_EXIST_HM_ID_ = ? AND GAME_CODE_ = ? AND PERIOD_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(existHmId);
		parameterList.add(gameCode);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.FINISH.name());
		List<Map<String, Object>> finishBetInfos = super.dao.findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(finishBetInfos)) {
			return null;
		}

		//将list-map转换为map-list-map
		Map<String, List<Map<String, Object>>> finishBetItem = new HashMap<>(finishBetInfos.size());
		String cloudReceiptBetId;
		for (Map<String, Object> finishBetInfo : finishBetInfos) {
			cloudReceiptBetId = finishBetInfo.get("CLOUD_RECEIPT_BET_ID_").toString();
			finishBetInfo.remove("CLOUD_RECEIPT_BET_ID_");
			finishBetInfo.remove("ROW_NUM");
			if (finishBetItem.containsKey(cloudReceiptBetId)) {
				finishBetItem.get(cloudReceiptBetId).add(finishBetInfo);
			} else {
				List<Map<String, Object>> execBetItem = new ArrayList<>();
				execBetItem.add(finishBetInfo);
				finishBetItem.put(cloudReceiptBetId, execBetItem);
			}
		}
		return finishBetItem;
	}

	/**
	 * 批量更新结算信息
	 *
	 * @param mapBetProfit 结算信息
	 */
	public boolean updateSettlement(Map<String, Map<String, Object>> mapBetProfit) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> parameterList = new ArrayList<>();
		sqlBuilder
				.append("select IBM_CLIENT_EXIST_BET_ID_,PROFIT_T_,ODDS_ from ibm_client_exist_bet where IBM_CLIENT_EXIST_BET_ID_ in(");
		//添加所有更新数据的id
		for (String key : mapBetProfit.keySet()) {
			sqlBuilder.append("?,");
			parameterList.add(key);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
		sqlBuilder.append(")");
		//获取需更新的数据
		List<Map<String, Object>> clientExistBetMapList = this.dao.findMapList(sqlBuilder.toString(), parameterList);
		if (ContainerTool.isEmpty(clientExistBetMapList)) {
			return false;
		}
		parameterList.clear();
		sqlBuilder.delete(0, sqlBuilder.length());
		//批量更新PROFIT_T_
		Map<String, Object> mapInfo;
		sqlBuilder.append("update ibm_client_exist_bet set PROFIT_T_=CASE IBM_CLIENT_EXIST_BET_ID_");
		for (Map<String, Object> clientExistBetMap : clientExistBetMapList) {
			sqlBuilder.append(" when ? THEN ?");
			mapInfo = mapBetProfit.get(clientExistBetMap.get("IBM_CLIENT_EXIST_BET_ID_").toString());
			parameterList.add(clientExistBetMap.get("IBM_CLIENT_EXIST_BET_ID_"));
			if (StringTool.isEmpty(clientExistBetMap.get("PROFIT_T_"))) {
				parameterList.add(mapInfo.get("PROFIT_T_"));
			} else {
				parameterList.add(NumberTool.getLong(clientExistBetMap.get("PROFIT_T_")) + NumberTool
						.getLong(mapInfo.get("PROFIT_T_")));
			}
		}
		//批量更新ODDS_
		sqlBuilder.append(" end, ODDS_ = CASE IBM_CLIENT_EXIST_BET_ID_");
		for (Map<String, Object> clientExistBetMap : clientExistBetMapList) {
			sqlBuilder.append(" when ? THEN ?");
			mapInfo = mapBetProfit.get(clientExistBetMap.get("IBM_CLIENT_EXIST_BET_ID_").toString());
			parameterList.add(clientExistBetMap.get("IBM_CLIENT_EXIST_BET_ID_"));
			if (StringTool.isEmpty(clientExistBetMap.get("ODDS_"))) {
				parameterList.add(mapInfo.get("ODDS_").toString());
			} else {
				parameterList.add(clientExistBetMap.get("ODDS_").toString().concat(",")
						.concat(mapInfo.get("ODDS_").toString()));
			}
		}
		sqlBuilder.append(" end where IBM_CLIENT_EXIST_BET_ID_ in(");
		for (Map<String, Object> clientExistBetMap : clientExistBetMapList) {
			sqlBuilder.append("?,");
			parameterList.add(clientExistBetMap.get("IBM_CLIENT_EXIST_BET_ID_"));
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(","));
		sqlBuilder.append(")");
		this.dao.execute(sqlBuilder.toString(), parameterList);

		parameterList.clear();
		sqlBuilder.delete(0, sqlBuilder.length());

		sqlBuilder
				.append("update ibm_client_exist_bet set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where IBM_CLIENT_EXIST_BET_ID_ in (");
		Date nowTime = new Date();
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.FINISH.name());
		for (Map<String, Object> clientExistBetMap : clientExistBetMapList) {
			sqlBuilder.append("?,");
			parameterList.add(clientExistBetMap.get("IBM_CLIENT_EXIST_BET_ID_"));
		}
		sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
		sqlBuilder.append(")");
		this.dao.execute(sqlBuilder.toString(), parameterList);
		return true;
	}

	/**
	 * 更新投注信息为Ready
	 *
	 * @param betInfoExistIds 投注项id列表
	 * @return 更新的项，EXEC_BET_ITEM_ID_
	 */
	public List<String> updateBet(List<String> betInfoExistIds) throws SQLException {
		String sql = "update `ibm_client_exist_bet`  set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.READY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		StringBuilder sqlBuilder = new StringBuilder("IBM_CLIENT_EXIST_BET_ID_  in (");
		for (String betInfoExistId : betInfoExistIds) {
			sqlBuilder.append("?,");
			parameterList.add(betInfoExistId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") and STATE_ = ?");
		parameterList.add(IbmStateEnum.OPEN.name());
		sql += sqlBuilder.toString();
		super.dao.execute(sql, parameterList);

		sql = "SELECT EXEC_BET_ITEM_ID_ FROM `ibm_client_exist_bet` where ";
		parameterList.clear();
		parameterList.addAll(betInfoExistIds);
		parameterList.add(IbmStateEnum.READY.name());
		sql += sqlBuilder.toString();
		return super.dao.findStringList("EXEC_BET_ITEM_ID_", sql, parameterList);
	}
}
