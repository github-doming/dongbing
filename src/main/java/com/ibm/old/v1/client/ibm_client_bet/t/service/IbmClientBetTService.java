package com.ibm.old.v1.client.ibm_client_bet.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.client.ibm_client_bet.t.entity.IbmClientBetT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.*;
/**
 * @author Administrator
 */
public class IbmClientBetTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientBetT对象数据
	 */
	public String save(IbmClientBetT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_bet的 IBM_CLIENT_BET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_bet set state_='DEL' where IBM_CLIENT_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_BET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_bet的 IBM_CLIENT_BET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_client_bet set state_='DEL' where IBM_CLIENT_BET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_bet的 IBM_CLIENT_BET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_bet where IBM_CLIENT_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_BET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_bet的 IBM_CLIENT_BET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client_bet where IBM_CLIENT_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientBetT实体信息
	 *
	 * @param entity IbmClientBetT实体
	 */
	public void update(IbmClientBetT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_bet表主键查找IbmClientBetT实体
	 *
	 * @param id ibm_client_bet 主键
	 * @return IbmClientBetT实体
	 */
	public IbmClientBetT find(String id) throws Exception {
		return (IbmClientBetT) this.dao.find(IbmClientBetT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientBetT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientBetT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmClientBetT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientBetT数据信息
	 *
	 * @return 可用<IbmClientBetT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientBetT.class, sql);
	}
	/**
	 * 获取投注失败项
	 *
	 * @param clientExistHmId 已存在盘口会员id
	 * @param period          期数
	 * @param gameCode        游戏code
	 * @return 投注失败项
	 */
	public List<Map<String, Object>> againBetItem(String clientExistHmId, String period, String gameCode)
			throws Exception {
		String sql = "SELECT icb.IBM_CLIENT_BET_ID_,icb.BET_TYPE_,icb.BET_INFO_,icb.CLIENT_BET_INFO_EXIST_ID_ as "
				+ " BET_INFO_EXIST_ID_,icb.STATE_,iceb.HANDICAP_MEMBER_ID_,iceb.EXEC_BET_ITEM_ID_ FROM ibm_client_bet icb "
				+ " LEFT JOIN ibm_client_exist_bet iceb ON icb.CLIENT_BET_INFO_EXIST_ID_ = iceb.IBM_CLIENT_EXIST_BET_ID_ "
				+ " WHERE icb.CLIENT_EXIST_HM_ID_ = ? AND icb.PERIOD_ = ? AND iceb.GAME_CODE_ = ? AND icb.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(clientExistHmId);
		parameterList.add(period);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.FAIL.name());
		return this.dao.findMapList(sql, parameterList);
	}
	/**
	 * 合并盈利信息和赔率
	 *
	 * @param clientExistHmId 已存在盘口会员id
	 * @param period          期数
	 * @param gameCode        游戏code
	 * @return 盈利信息和赔率
	 */
	public Map<String, Map<String, Object>> findBetResult(String clientExistHmId, String period, String gameCode)
			throws SQLException {
		String sql = "select icb.CLIENT_EXIST_BET_ID_,icb.PROFIT_T_,icb.ODDS_"
				+ " from ibm_client_bet icb where icb.CLIENT_EXIST_HM_ID_=? and icb.PERIOD_=? and icb.GAME_CODE_=? and"
				+ " icb.STATE_=? ORDER BY icb.CREATE_TIME_ DESC";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(clientExistHmId);
		parameterList.add(period);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.FINISH.name());
		List<Map<String, Object>> finishBets = super.dao.findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(finishBets)) {
			return null;
		}

		Map<String, Map<String, Object>> finishBetItem = new HashMap<>(finishBets.size() / 2);
		finishBets.forEach(map -> {
			String betInfoExistId = map.get("CLIENT_EXIST_BET_ID_").toString();
			long profitT = (long) map.get("PROFIT_T_");
			String odds = map.get("ODDS_").toString();
			if (finishBetItem.containsKey(betInfoExistId)) {
				Map<String, Object> finish = finishBetItem.get(betInfoExistId);
				finish.put("PROFIT_T_", Long.parseLong(finish.get("PROFIT_T_").toString()) + profitT);
				finish.put("ODDS_", finish.get("ODDS_").toString().concat(",").concat(odds));
			} else {
				Map<String, Object> finish = new HashMap<>(2);
				finish.put("PROFIT_T_", profitT);
				finish.put("ODDS_", odds);
				finishBetItem.put(betInfoExistId, finish);
			}
		});

		return finishBetItem;
	}
	/**
	 * 获取成功投注项list
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param period    期数
	 * @return 成功投注项list
	 */
	public List<IbmClientBetT> findList(String existHmId, String period, String gameCode) throws Exception {
		String sql = "SELECT * FROM ibm_client_bet icb where icb.CLIENT_EXIST_HM_ID_=? "
				+ " and icb.GAME_CODE_=? AND icb.PERIOD_ =? and icb.STATE_=? ORDER BY icb.CREATE_TIME_ DESC";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(existHmId);
		parameterList.add(gameCode);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.SUCCESS.name());
		return (List<IbmClientBetT>) this.dao.findObjectList(IbmClientBetT.class, sql, parameterList);
	}

	/**
	 * 更新结算信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @param period    游戏期数
	 * @param result    更新信息
	 */
	public Map<String, Object> updateSettlement(String existHmId, String gameCode, String period,
			Map<String, String> result) throws SQLException {
		//获取存在信息
		String sql = "SELECT IBM_CLIENT_BET_ID_,CLIENT_EXIST_BET_ID_ FROM `ibm_client_bet` WHERE "
				+ " CLIENT_EXIST_HM_ID_ = ? AND BET_NUMBER_ = ? AND GAME_CODE_ = ? AND PERIOD_ = ? "
				+ " AND STATE_ = ? AND BET_RESULT_ IS NULL";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(existHmId);
		parameterList.add(result.get("betNumber"));
		parameterList.add(gameCode);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.SUCCESS.name());
		Map<String, Object> existInfo = super.dao.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(existInfo)) {
			return null;
		}
		int profitT = NumberTool.intValueT(result.get("profit").replace(",",""));

		//更新结果
		sql = "UPDATE `ibm_client_bet` SET BET_RESULT_ = ?,PROFIT_T_ = ?,ODDS_ = ?,UPDATE_TIME_ = ? "
				+ " ,UPDATE_TIME_LONG_ = ?, STATE_ = ? WHERE IBM_CLIENT_BET_ID_ = ? ";
		parameterList.clear();
		parameterList.add(result.get("betResult"));
		parameterList.add(profitT);
		parameterList.add(result.get("odds"));
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmStateEnum.FINISH.name());
		parameterList.add(existInfo.get("IBM_CLIENT_BET_ID_"));

		int col = super.dao.execute(sql, parameterList);
		if (col != 1) {
			return null;
		}

		//回传信息
		Map<String, Object> settlementInfo = new HashMap<>(3);
		settlementInfo.put("existBetId", existInfo.get("CLIENT_EXIST_BET_ID_"));
		settlementInfo.put("ODDS_", result.get("odds"));
		settlementInfo.put("PROFIT_T_", profitT);
		return settlementInfo;
	}

	/**
	 * 保存投注成功信息
	 *
	 * @param existHmId   存在盘口会员id
	 * @param gameCode    游戏code
	 * @param period      期数
	 * @param betHandicap 投注盘
	 * @param nowTime     现在时间
	 * @param existBetId  存在投注id
	 * @param successInfo 成功信息
	 */
	public void saveBetSuccess(String existHmId, String gameCode, String period, Object betHandicap, String existBetId,
			Map<String, String> successInfo, Date nowTime) throws Exception {
		IbmClientBetT betT = new IbmClientBetT();
		betT.setClientExistHmId(existHmId);
		betT.setClientExistBetId(existBetId);
		betT.setGameCode(gameCode);
		betT.setPeriod(period);
		betT.setBetType(betHandicap.toString());
		betT.setBetInfo(successInfo.get("betInfo"));
		betT.setBetNumber(successInfo.get("betNumber"));
		betT.setProfitT(NumberTool.intValueT(successInfo.get("profit")));
		betT.setOdds(successInfo.get("odds"));
		betT.setCreateTime(nowTime);
		betT.setCreateTimeLong(System.currentTimeMillis());
		betT.setState(IbmStateEnum.SUCCESS.name());
		save(betT);
	}
}
