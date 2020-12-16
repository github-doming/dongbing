package com.ibs.plan.module.cloud.ibsp_hm_game_set.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.entity.IbspHmGameSet;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_盘口会员游戏设置 服务类
 *
 * @author Robot
 */
public class IbspHmGameSetService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员游戏设置 对象数据
	 *
	 * @param entity IbspHmGameSet对象数据
	 */
	public String save(IbspHmGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_hm_game_set 的 IBSP_HM_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_hm_game_set set state_='DEL' where IBSP_HM_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_hm_game_set 的 IBSP_HM_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_hm_game_set set state_='DEL' where IBSP_HM_GAME_SET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_hm_game_set  的 IBSP_HM_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_hm_game_set where IBSP_HM_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_hm_game_set 的 IBSP_HM_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_hm_game_set where IBSP_HM_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHmGameSet实体信息
	 *
	 * @param entity IBSP_盘口会员游戏设置 实体
	 */
	public void update(IbspHmGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_hm_game_set表主键查找 IBSP_盘口会员游戏设置 实体
	 *
	 * @param id ibsp_hm_game_set 主键
	 * @return IBSP_盘口会员游戏设置 实体
	 */
	public IbspHmGameSet find(String id) throws Exception {
		return dao.find(IbspHmGameSet.class, id);
	}

	/**
	 * 获取游戏信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 游戏信息
	 */
	public List<Map<String, Object>> listGameInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT BET_STATE_,HANDICAP_GAME_NAME_ as GAME_NAME_,GAME_CODE_,ICON_ FROM ibsp_hm_game_set ihs"
				+ " LEFT JOIN ibsp_handicap_game ihg ON ihs.HANDICAP_ID_=ihg.HANDICAP_ID_"
				+ " AND ihs.GAME_ID_=ihg.GAME_ID_ WHERE ihs.HANDICAP_MEMBER_ID_ = ? AND ihg.STATE_ =?"
				+ " AND ihs.STATE_ = ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapMemberId);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}
	/**
	 * 获取盘口会员的游戏设置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 游戏设置信息
	 */
	public List<Map<String, Object>> listInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT GAME_CODE_,BET_STATE_,BET_MODE_,IS_AUTO_STOP_BET_,IS_AUTO_START_BET_ FROM ibsp_hm_game_set ihgs"
				+ " LEFT JOIN ibsp_handicap_game ihg ON ihgs.HANDICAP_ID_=ihg.HANDICAP_ID_ AND  ihgs.GAME_ID_=ihg.GAME_ID_"
				+ " WHERE HANDICAP_MEMBER_ID_ = ? AND ihg.STATE_ = ? AND ihgs.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}
	/**
	 * 获取游戏ids
	 *
	 * @param handicapId 盘口id
	 * @return 游戏ids
	 */
	public List<String> findGameIds(String handicapId, String[] availableGames) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"select GAME_ID_ from ibsp_handicap_game where HANDICAP_ID_=? and STATE_=? and GAME_CODE_ in(");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String game : availableGames) {
			sql.append("?,");
			parameterList.add(game);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");

		return super.dao.findStringList("GAME_ID_", sql.toString(), parameterList);
	}
	/**
	 * 获取盘口会员游戏设置初始化信息
	 *
	 * @return 初始化信息
	 */
	public Map<String, Object> findInitInfo() throws SQLException {
		String sql = "select BET_STATE_,BET_MODE_,IS_AUTO_STOP_BET_,AUTO_STOP_BET_TIME_LONG_,IS_AUTO_START_BET_,"
				+ "AUTO_START_BET_TIME_LONG_,IS_INVERSE_,INCREASE_STATE_,INCREASE_STOP_TIME_LONG_,BET_SECOND_,SPLIT_TWO_SIDE_AMOUNT_, "
				+ " SPLIT_NUMBER_AMOUNT_ from ibsp_hm_game_set where STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.DEF.name());
		return super.findMap(sql, parameterList);
	}
	/**
	 * 获取盘口会员所有游戏设置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 游戏设置信息
	 */
	public List<Map<String, Object>> findByHmId(String handicapMemberId) throws SQLException {
		String sql =
				"SELECT ihg.GAME_CODE_,ihg.GAME_DRAW_TYPE_,ihgs.BET_STATE_,ihgs.BET_MODE_,ihgs.BET_SECOND_,ihgs.INCREASE_STATE_,"
						+ "ihgs.SPLIT_TWO_SIDE_AMOUNT_,ihgs.SPLIT_NUMBER_AMOUNT_ FROM ibsp_hm_game_set ihgs LEFT JOIN ibsp_handicap_game ihg"
						+ " ON ihgs.GAME_ID_=ihg.GAME_ID_ AND ihgs.HANDICAP_ID_=ihg.HANDICAP_ID_ WHERE HANDICAP_MEMBER_ID_ =? AND ihgs.STATE_ =? and ihg.STATE_ =?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(handicapMemberId);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取会员开启投注的信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 信息
	 */
	public List<Map<String, Object>> listOnBets(String handicapMemberId) throws SQLException {
		String sql = "SELECT IBSP_HM_GAME_SET_ID_,ihgs.GAME_ID_,ihgs.HANDICAP_ID_,"
				+ " ihgs.HANDICAP_MEMBER_ID_,ihi.APP_USER_ID_,ihi.MEMBER_ACCOUNT_ FROM ibsp_hm_game_set ihgs "
				+ " LEFT JOIN ibsp_hm_info ihi ON ihgs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
				+ " WHERE ihgs.HANDICAP_MEMBER_ID_ = ? AND ihi.LOGIN_STATE_ = ? AND BET_STATE_ = ? AND BET_MODE_ = ? "
				+ " AND ihi.STATE_ = ? AND ihgs.STATE_ = ?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(handicapMemberId);
		parameters.add(IbsStateEnum.LOGIN.name());
		parameters.add(IbsTypeEnum.TRUE.name());
		parameters.add(IbsTypeEnum.REAL.name());
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 修改盘口会员所有游戏投注状态
	 *
	 * @param gameSetIds 盘口会员游戏设置ID列表
	 * @param nowTime    修改事件
	 */
	public void updateBetState(List<String> gameSetIds, Date nowTime, String desc) throws SQLException {
		List<Object> parameterList = new ArrayList<>(8);
		StringBuilder sql = new StringBuilder();
		sql.append("update ibsp_hm_game_set set BET_STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where ")
				.append(" IBSP_HM_GAME_SET_ID_ in(");
		parameterList.add(IbsTypeEnum.FALSE.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		for (String gameSetId : gameSetIds) {
			parameterList.add(gameSetId);
			sql.append("?,");
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		super.execute(sql.toString(), parameterList);
	}
	/**
	 * 获取配置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 * @return 配置信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId, String gameId) throws SQLException {
		String sql = "select BET_STATE_,BET_SECOND_,BET_MODE_ from ibsp_hm_game_set where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapMemberId);
		parameters.add(gameId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameters);
	}
	/**
	 * 获取游戏设置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 * @return
	 */
	public Map<String, Object> findShowInfo(String handicapMemberId, String gameId) throws SQLException {
		String sql = "SELECT BET_STATE_,IS_AUTO_STOP_BET_,AUTO_STOP_BET_TIME_LONG_,IS_AUTO_START_BET_,"
				+ " AUTO_START_BET_TIME_LONG_,IS_INVERSE_,BET_MODE_,INCREASE_STATE_,INCREASE_STOP_TIME_LONG_,BET_SECOND_, "
				+ " SPLIT_TWO_SIDE_AMOUNT_,SPLIT_NUMBER_AMOUNT_ FROM `ibsp_hm_game_set` "
				+ " where  HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 获取游戏设置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏Id
	 * @return 游戏设置信息
	 */
	public IbspHmGameSet findEntity(String handicapMemberId, String gameId) throws Exception {
		String sql = "select * from ibsp_hm_game_set where HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspHmGameSet.class, sql, parameterList);
	}

	/**
	 * 获取自动开始会员信息
	 *
	 * @return 会员信息
	 */
	public List<Map<String, Object>> listAutoStart() throws SQLException {
		return listAutoState("IS_AUTO_START_BET_", "AUTO_START_BET_TIME_LONG_", IbsTypeEnum.FALSE);
	}
	/**
	 * 获取自动停止会员信息
	 *
	 * @return 会员信息
	 */
	public List<Map<String, Object>> listAutoStop() throws SQLException {
		return listAutoState("IS_AUTO_STOP_BET_", "AUTO_STOP_BET_TIME_LONG_", IbsTypeEnum.TRUE);
	}

	/**
	 * 获取自动会员信息
	 *
	 * @param autoKey      自动Key
	 * @param autoTimeLong 自动时间
	 * @param betState     投注状态
	 * @return 会员信息
	 */
	private List<Map<String, Object>> listAutoState(String autoKey, String autoTimeLong, IbsTypeEnum betState)
			throws SQLException {
		String sql = "SELECT IBSP_HM_GAME_SET_ID_,ihgs.GAME_ID_,ihgs.HANDICAP_ID_,ihgs.HANDICAP_MEMBER_ID_, "
				+ " ihi.APP_USER_ID_,ihi.MEMBER_ACCOUNT_ FROM ibsp_hm_game_set ihgs "
				+ " LEFT JOIN ibsp_hm_info ihi ON ihgs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
				+ " WHERE ihi.LOGIN_STATE_ = ? AND BET_STATE_ = ? AND " + autoKey + " = ? AND " + autoTimeLong
				+ " BETWEEN ? AND ? AND ihi.STATE_ = ? AND ihgs.STATE_ = ?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(IbsStateEnum.LOGIN.name());
		parameters.add(betState.name());
		parameters.add(IbsTypeEnum.TRUE.name());
		parameters.add(System.currentTimeMillis());
		parameters.add(System.currentTimeMillis() + 60000L);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 更新自动开始投注
	 *
	 * @param gameSetIds 更新主键
	 * @param nowTime    更新时间
	 * @param desc       更新描述
	 */
	public void updateAutoStart(List<String> gameSetIds, Date nowTime, String desc) throws SQLException {
		updateAutoState(gameSetIds, nowTime, desc, IbsTypeEnum.TRUE, IbsTypeEnum.FALSE, "IS_AUTO_START_BET_");
	}
	/**
	 * 更新自动投注投注
	 *
	 * @param gameSetIds 更新主键
	 * @param nowTime    更新时间
	 * @param desc       更新描述
	 */
	public void updateAutoStop(List<String> gameSetIds, Date nowTime, String desc) throws SQLException {
		updateAutoState(gameSetIds, nowTime, desc, IbsTypeEnum.FALSE, IbsTypeEnum.TRUE, "IS_AUTO_STOP_BET_");
	}

	/**
	 * @param gameSetIds  更新主键
	 * @param nowTime     更新时间
	 * @param desc        更新描述
	 * @param newBetState 新的投注状态
	 * @param oldBetState 老投注状态
	 * @param autoKey     认定自动键
	 */
	private void updateAutoState(List<String> gameSetIds, Date nowTime, String desc, IbsTypeEnum newBetState,
			IbsTypeEnum oldBetState, String autoKey) throws SQLException {
		StringBuilder sql = new StringBuilder("UPDATE ibsp_hm_game_set SET BET_STATE_ = ?," + autoKey + " = ?, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE BET_STATE_ = ? " + " AND " + autoKey
				+ " = ? AND STATE_ = ? AND IBSP_HM_GAME_SET_ID_ IN (");
		List<Object> parameters = new ArrayList<>(5 + gameSetIds.size());
		parameters.add(newBetState.name());
		parameters.add(IbsTypeEnum.FALSE.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(desc);
		parameters.add(oldBetState.name());
		parameters.add(IbsTypeEnum.TRUE.name());
		parameters.add(IbsStateEnum.OPEN.name());
		for (String gameSetId : gameSetIds) {
			sql.append("?,");
			parameters.add(gameSetId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		super.execute(sql.toString(), parameters);
	}

	/**
	 * 获取自动停止新增列表
	 *
	 * @return 自动停止新增列表
	 */
	public List<Map<String, Object>> listAutoIncrease() throws SQLException {
		String sql = "SELECT IBSP_HM_GAME_SET_ID_,ihgs.GAME_ID_,ihgs.HANDICAP_ID_,ihgs.HANDICAP_MEMBER_ID_, "
				+ " ihi.APP_USER_ID_,ihi.MEMBER_ACCOUNT_ FROM ibsp_hm_game_set ihgs "
				+ " LEFT JOIN ibsp_hm_info ihi ON ihgs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
				+ " WHERE ihi.LOGIN_STATE_ = ? AND BET_STATE_ = ? AND INCREASE_STATE_ = ? AND INCREASE_STOP_TIME_LONG_ "
				+ " BETWEEN ? AND ? AND ihi.STATE_ = ? AND ihgs.STATE_ = ?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(IbsStateEnum.LOGIN.name());
		parameters.add(IbsTypeEnum.TRUE.name());
		parameters.add(IbsStateEnum.AUTO.name());
		parameters.add(System.currentTimeMillis());
		parameters.add(System.currentTimeMillis() + 60000L);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 修改自动停止新增
	 *
	 * @param gameSetIds 更新主键
	 * @param nowTime    更新时间
	 * @param desc       更新描述
	 */
	public void updateAutoIncrease(List<String> gameSetIds, Date nowTime, String desc) throws SQLException {
		StringBuilder sql = new StringBuilder("UPDATE ibsp_hm_game_set SET INCREASE_STATE_ = ?, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE BET_STATE_ = ? " + " AND "
				+ " INCREASE_STATE_ = ? AND STATE_ = ? AND IBSP_HM_GAME_SET_ID_ IN (");
		List<Object> parameters = new ArrayList<>(7 + gameSetIds.size());
		parameters.add(IbsStateEnum.NOW.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(desc);
		parameters.add(IbsTypeEnum.TRUE.name());
		parameters.add(IbsStateEnum.AUTO.name());
		parameters.add(IbsStateEnum.OPEN.name());
		for (String gameSetId : gameSetIds) {
			sql.append("?,");
			parameters.add(gameSetId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		super.execute(sql.toString(), parameters);
	}
	/**
	 * 获取正在投注的信息
	 * @param handicapMemberIds		盘口会员ids
	 * @param betMode					投注模式
	 * @return
	 */
	public List<Map<String, Object>> listOnBet(List<String> handicapMemberIds, String betMode) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBSP_HM_GAME_SET_ID_,ihgs.GAME_ID_,ihgs.HANDICAP_ID_,"
				+ " ihgs.HANDICAP_MEMBER_ID_,ihi.APP_USER_ID_,ihi.MEMBER_ACCOUNT_ FROM ibsp_hm_game_set ihgs "
				+ " LEFT JOIN ibsp_hm_info ihi ON ihgs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
				+ " WHERE ihi.LOGIN_STATE_ = ? AND BET_STATE_ = ? AND BET_MODE_ = ?  AND ihi.STATE_ = ? AND ihgs.STATE_ = ?"
				+ " AND ihgs.HANDICAP_MEMBER_ID_ IN (");
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(IbsStateEnum.LOGIN.name());
		parameters.add(IbsTypeEnum.TRUE.name());
		parameters.add(betMode);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		for (String handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameters.add(handicapMemberId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		return super.findMapList(sql.toString(), parameters);
	}

	/**
	 * 更新投注模式
	 * @param gameSetIds 游戏设置主键
	 * @param newBetMode 新的投注模式
	 * @param oldBetMode 老投注模式
	 * @param nowTime 更新时间
	 * @param desc 描述
	 */
	public void updateBetMode(List<String> gameSetIds, IbsTypeEnum newBetMode, IbsTypeEnum oldBetMode, Date nowTime, String desc)
			throws SQLException {
		StringBuilder sql = new StringBuilder("UPDATE ibsp_hm_game_set SET BET_MODE_ = ?, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE BET_STATE_ = ?  AND "
				+ " BET_MODE_ = ? AND STATE_ = ? AND IBSP_HM_GAME_SET_ID_ IN (");
		List<Object> parameters = new ArrayList<>(7 + gameSetIds.size());
		parameters.add(newBetMode.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(desc);
		parameters.add(IbsTypeEnum.TRUE.name());
		parameters.add(oldBetMode);
		parameters.add(IbsStateEnum.OPEN.name());
		for (String gameSetId : gameSetIds) {
			sql.append("?,");
			parameters.add(gameSetId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		super.execute(sql.toString(), parameters);
	}

	/**
	 * 新增会员游戏设置信息
	 *
	 * @param memberInfo  会员信息
	 * @param gameIds     游戏ids
	 * @param initGameSet 初始化信息
	 */
	public void save(Map<String, Object> memberInfo, List<String> gameIds, Map<String, Object> initGameSet) throws SQLException {
		StringBuilder sql = new StringBuilder("insert into ibsp_hm_game_set(`IBSP_HM_GAME_SET_ID_`, `HANDICAP_MEMBER_ID_`, `HANDICAP_ID_`," +
				" `GAME_ID_`, `BET_STATE_`, `BET_MODE_`, `IS_AUTO_STOP_BET_`, `AUTO_STOP_BET_TIME_LONG_`, `IS_AUTO_START_BET_`, " +
				"`AUTO_START_BET_TIME_LONG_`, `IS_INVERSE_`, `INCREASE_STATE_`, `INCREASE_STOP_TIME_LONG_`, `BET_SECOND_`, `SPLIT_TWO_SIDE_AMOUNT_`, " +
				"`SPLIT_NUMBER_AMOUNT_`, `CREATE_TIME_`, `CREATE_TIME_LONG_`, `UPDATE_TIME_`, `UPDATE_TIME_LONG_`, `STATE_`) VALUES");
		List<Object> parameterList = new ArrayList<>();
		Date nowTime = new Date();
		for (String gameId : gameIds) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(UUID.randomUUID().toString().replace("-", ""));
			parameterList.add(memberInfo.get("IBSP_HANDICAP_MEMBER_ID_"));
			parameterList.add(memberInfo.get("HANDICAP_ID_"));
			parameterList.add(gameId);
			parameterList.add(initGameSet.get("BET_STATE_"));

			parameterList.add(initGameSet.get("BET_MODE_"));
			parameterList.add(initGameSet.get("IS_AUTO_STOP_BET_"));
			parameterList.add(initGameSet.get("AUTO_STOP_BET_TIME_LONG_"));
			parameterList.add(initGameSet.get("IS_AUTO_START_BET_"));
			parameterList.add(initGameSet.get("AUTO_START_BET_TIME_LONG_"));

			parameterList.add(initGameSet.get("IS_INVERSE_"));
			parameterList.add(initGameSet.get("INCREASE_STATE_"));
			parameterList.add(initGameSet.get("INCREASE_STOP_TIME_LONG_"));
			parameterList.add(initGameSet.get("BET_SECOND_"));
			parameterList.add(initGameSet.get("SPLIT_TWO_SIDE_AMOUNT_"));
			parameterList.add(initGameSet.get("SPLIT_NUMBER_AMOUNT_"));

			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(IbsStateEnum.OPEN.name());
		}
		sql.deleteCharAt(sql.length() - 1);
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 获取投注中游戏信息
	 *
	 * @param memberInfo 会员信息
	 * @param gameIds    游戏ids
	 */
	public List<Map<String, Object>> findBetInfo(Map<String, Object> memberInfo, List<String> gameIds) throws SQLException {
		List<Object> parameterList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select HANDICAP_MEMBER_ID_,GAME_ID_ from ibsp_hm_game_set where HANDICAP_MEMBER_ID_=? "
				+ " and HANDICAP_ID_=? and BET_STATE_=? and GAME_ID_ in(");
		parameterList.add(memberInfo.get("IBSP_HANDICAP_MEMBER_ID_"));
		parameterList.add(memberInfo.get("HANDICAP_ID_"));
		parameterList.add(IbsTypeEnum.TRUE.name());
		for (String gameId : gameIds) {
			sql.append("?,");
			parameterList.add(gameId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 删除会员游戏设置信息
	 *
	 * @param memberInfo 会员信息
	 * @param gameIds    游戏ids
	 */
	public void delGameSet(Map<String, Object> memberInfo, List<String> gameIds) throws SQLException {
		List<Object> parameterList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("update ibsp_hm_game_set set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where HANDICAP_MEMBER_ID_=? "
				+ " and HANDICAP_ID_ = ? and GAME_ID_ in(");
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(memberInfo.get("IBSP_HANDICAP_MEMBER_ID_"));
		parameterList.add(memberInfo.get("HANDICAP_ID_"));
		for (String gameId : gameIds) {
			sql.append("?,");
			parameterList.add(gameId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 获取投注信息
	 * @param handicapMemberId  盘口会员id
	 * @return
	 */
	public List<Map<String, Object>> findBetInfoByHmId(String handicapMemberId) throws SQLException {
		String sql="SELECT ig.GAME_NAME_,ig.GAME_CODE_,ihgs.BET_STATE_,ihgs.IS_AUTO_STOP_BET_,ihgs.AUTO_STOP_BET_TIME_LONG_,"
				+ "ihgs.IS_AUTO_START_BET_,ihgs.AUTO_START_BET_TIME_LONG_,ihgs.IS_INVERSE_,ihgs.INCREASE_STATE_,ihgs.INCREASE_STOP_TIME_LONG_ from ibsp_hm_game_set ihgs "
				+ " LEFT JOIN ibsp_game ig ON ihgs.GAME_ID_=ig.IBSP_GAME_ID_ WHERE ihgs.HANDICAP_MEMBER_ID_=? and ihgs.STATE_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameterList);
	}

	/**
	 * 获取游戏设置主键
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏Id
	 * @return 游戏设置主键
	 */
	public String findId(String handicapMemberId, String gameId) throws Exception {
		String sql = "select IBSP_HM_GAME_SET_ID_ as key_ from ibm_hm_game_set where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString( sql, parameterList);
	}

	/**
	 * 获取会员游戏codes
	 * @param handicapMemberId  盘口会员id
	 * @return
	 */
	public List<String> listGameCodes(String handicapMemberId) throws SQLException {
		String sql = "SELECT GAME_CODE_ FROM ibsp_handicap_game ihg LEFT JOIN "
				+ " ibsp_hm_game_set ihs ON ihg.HANDICAP_ID_ = ihs.HANDICAP_ID_ AND ihg.GAME_ID_ = ihs.GAME_ID_ "
				+ " WHERE ihs.HANDICAP_MEMBER_ID_ = ? and ihg.STATE_ = ? AND ihs.STATE_ = ? ORDER BY SN_";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapMemberId);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findStringList("GAME_CODE_",sql, parameters);
	}

	/**
	 * 盘口游戏设置信息
	 * @param handicapMemberIds     盘口会员ids
	 * @return
	 */
	public List<Map<String, Object>> findByHmIds(List<String> handicapMemberIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT HANDICAP_MEMBER_ID_,ig.GAME_CODE_,ihgs.BET_STATE_,ihgs.BET_SECOND_,ihgs.SPLIT_TWO_SIDE_AMOUNT_,"
				+ "ihgs.SPLIT_NUMBER_AMOUNT_ FROM ibsp_hm_game_set ihgs LEFT JOIN ibsp_game ig ON ihgs.GAME_ID_=ig.IBSP_GAME_ID_"
				+ " WHERE ihgs.STATE_ =? and HANDICAP_MEMBER_ID_ in(");
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.OPEN.name());
		for(String handicapMemberId:handicapMemberIds){
			sql.append("?,");
			parameters.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.findMapList(sql.toString(), parameters);
	}

	/**
	 * 获取游戏设置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏Id
	 * @return 游戏设置信息
	 */
	public IbspHmGameSet findByHmIdAndGameCode(String handicapMemberId, String gameId) throws Exception {
		String sql = "select * from ibsp_hm_game_set where HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspHmGameSet.class, sql, parameterList);
	}


}
