package com.ibs.plan.module.cloud.ibsp_hm_set.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_hm_set.entity.IbspHmSet;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_盘口会员设置 服务类
 *
 * @author Robot
 */
public class IbspHmSetService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员设置 对象数据
	 *
	 * @param entity IbspHmSet对象数据
	 */
	public String save(IbspHmSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_hm_set 的 IBSP_HM_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_hm_set set state_='DEL' where IBSP_HM_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_hm_set 的 IBSP_HM_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_hm_set set state_='DEL' where IBSP_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_hm_set  的 IBSP_HM_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_hm_set where IBSP_HM_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_hm_set 的 IBSP_HM_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_hm_set where IBSP_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHmSet实体信息
	 *
	 * @param entity IBSP_盘口会员设置 实体
	 */
	public void update(IbspHmSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_hm_set表主键查找 IBSP_盘口会员设置 实体
	 *
	 * @param id ibsp_hm_set 主键
	 * @return IBSP_盘口会员设置 实体
	 */
	public IbspHmSet find(String id) throws Exception {
		return dao.find(IbspHmSet.class, id);
	}

	/**
	 * 获取盘口会员的设置展示信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 盈亏信息
	 */
	public Map<String, Object> findShowInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT BET_MERGER_,BET_RATE_T_,PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_,"
				+ " PROFIT_LIMIT_MIN_,RESET_TYPE_,RESET_PROFIT_MAX_,RESET_LOSS_MIN_,"
				+ "MODE_CUTOVER_STATE_,BLAST_STOP_"
				+ " FROM ibsp_hm_set where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取初始化信息
	 *
	 * @return
	 */
	public Map<String, Object> findInitInfo() throws SQLException {
		String sql =
				"select BET_RATE_T_,PROFIT_LIMIT_MAX_,PROFIT_LIMIT_MIN_,LOSS_LIMIT_MIN_,RESET_TYPE_,RESET_PROFIT_MAX_,"
						+ "RESET_LOSS_MIN_,BLAST_STOP_,BET_MERGER_ from ibsp_hm_set where STATE_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.DEF.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 通过盘口会员id获取止盈止损信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 止盈止损信息
	 */
	public Map<String, Object> findByHmId(String handicapMemberId) throws SQLException {
		String sql = "select PROFIT_LIMIT_MAX_,PROFIT_LIMIT_MIN_,LOSS_LIMIT_MIN_,BET_RATE_T_,BLAST_STOP_,BET_MERGER_"
				+ " from ibsp_hm_set where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取实体对象
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 实体对象
	 */
	public IbspHmSet findEntityByHmId(String handicapMemberId) throws Exception {
		String sql = "select * from ibsp_hm_set where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspHmSet.class, sql, parameterList);
	}

	/**
	 * 获取方案重置信息
	 *
	 * @return 方案重置信息
	 */
	public List<Map<String, Object>> listPlanResetInfo() throws SQLException {
		String sql = "SELECT ihi.HANDICAP_MEMBER_ID_,ihi.HANDICAP_ID_,ihi.APP_USER_ID_,ihi.MEMBER_ACCOUNT_ FROM `ibsp_hm_set` ihs "
				+ " LEFT JOIN ibsp_hm_info ihi ON ihs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ AND ihi.STATE_ = ? "
				+ " LEFT JOIN ibsp_profit ip ON ihs.HANDICAP_MEMBER_ID_ = ip.HANDICAP_MEMBER_ID_ AND ip.STATE_ = ? "
				+ " LEFT JOIN ibsp_profit_vr ipv ON ihs.HANDICAP_MEMBER_ID_ = ipv.HANDICAP_MEMBER_ID_ AND ipv.STATE_ = ? "
				+ " WHERE ihs.RESET_TYPE_ = ? AND ihi.LOGIN_STATE_ = ? AND ihs.STATE_ = ? AND "
				+ " ((ip.HANDICAP_PROFIT_T_ - ip.CORRECTION_T_  > ihs.RESET_PROFIT_MAX_ OR ip.HANDICAP_PROFIT_T_ -ip.CORRECTION_T_ < ihs.RESET_LOSS_MIN_) "
				+ " OR (ipv.PROFIT_T_ -ipv.CORRECTION_T_> ihs.RESET_PROFIT_MAX_ OR ipv.PROFIT_T_-ipv.CORRECTION_T_ < ihs.RESET_LOSS_MIN_)) ";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.CUSTOM.name());
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取切换模式
	 *
	 * @return 切换模式
	 */
	public Map<String, Map<String, Object>> listModeCutover() throws SQLException {
		String sql =
				"SELECT ihi.HANDICAP_MEMBER_ID_,ihi.HANDICAP_ID_,ihi.APP_USER_ID_,ihi.MEMBER_ACCOUNT_,ihmc.CUTOVER_GROUP_DATA_,"
						+ "ihmc.CUTOVER_KEY_,ihmc.CURRENT_INDEX_,ihmc.RESET_PROFIT_,ihmc.PROFIT_T_ FROM `ibsp_hm_set` ihs "
						+ " LEFT JOIN ibsp_hm_info ihi ON ihs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
						+ " LEFT JOIN ibsp_hm_mode_cutover ihmc ON ihs.HANDICAP_MEMBER_ID_=ihmc.HANDICAP_MEMBER_ID_"
						+ " WHERE ihs.MODE_CUTOVER_STATE_ = ? AND ihi.LOGIN_STATE_ = ? AND ihs.STATE_ = ? AND ihmc.CURRENT_INDEX_!=-1";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findKeyMap(sql, parameterList, "HANDICAP_MEMBER_ID_");
	}

	/**
	 * 获取切换列表
	 *
	 * @param handicapMemberIds 会员主键
	 * @return 真实切换列表
	 */
	public Map<String, List<Map<String, Object>>> listCutoverInfo(Set<String> handicapMemberIds) throws SQLException {
		return listCutoverInfo(handicapMemberIds, "ibsp_profit", "HANDICAP_PROFIT_T_", IbsTypeEnum.REAL);
	}

	/**
	 * 获取切换列表
	 *
	 * @param handicapMemberIds 会员主键
	 * @return 模拟切换列表
	 */
	public Map<String, List<Map<String, Object>>> listCutoverVrInfo(Set<String> handicapMemberIds) throws SQLException {
		return listCutoverInfo(handicapMemberIds, "ibsp_profit_period_vr", "PROFIT_T_", IbsTypeEnum.VIRTUAL);
	}

	/**
	 * 获取切换会员主键
	 *
	 * @param handicapMemberIds 会员主键
	 * @param tableName         盈利表名
	 * @param profitKey         盈利表键
	 * @param betMode           投注模式
	 * @return 切换会员主键
	 */
	private Map<String, List<Map<String, Object>>> listCutoverInfo(Set<String> handicapMemberIds, String tableName, String profitKey,
																   IbsTypeEnum betMode) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT ihs.HANDICAP_MEMBER_ID_,igs.GAME_ID_, ");
		sql.append(" igs.IBSP_HM_GAME_SET_ID_ FROM `ibsp_hm_set` ihs LEFT JOIN ibsp_hm_game_set igs ON ")
				.append(" ihs.HANDICAP_MEMBER_ID_ = igs.HANDICAP_MEMBER_ID_ AND igs.STATE_ = ? and igs.BET_STATE_ = ? ");
		sql.append(" LEFT JOIN ").append(tableName).append(" ip ON ihs.HANDICAP_MEMBER_ID_ = ip.HANDICAP_MEMBER_ID_ ");
		sql.append(" AND ip.STATE_ = ? WHERE ihs.STATE_ = ? AND igs.BET_MODE_ = ? and ihs.HANDICAP_MEMBER_ID_ in ( ");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsTypeEnum.TRUE.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(betMode);
		for (Object handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") AND (ip.").append(profitKey).append(" > ihs.CUTOVER_PROFIT_ OR ip.").append(profitKey)
				.append(" < ihs.CUTOVER_LOSS_)");
		return super.findKeyMaps(sql.toString(), parameterList, "HANDICAP_MEMBER_ID_");
	}

	/**
	 * 获取投注模式切换状态
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return
	 */
	public String findModeCutoverState(String handicapMemberId) throws SQLException {
		String sql = "select MODE_CUTOVER_STATE_ from ibsp_hm_set where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findString("MODE_CUTOVER_STATE_", sql, parameterList);
	}

	/**
	 * 获取有开启轮盘的会员信息
	 *
	 * @param handicapMemberIds 盘口会员ids
	 */
	public Map<String, String> listModeCutover(Set<String> handicapMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select ihs.HANDICAP_MEMBER_ID_ as key_,CURRENT_ as value_ from ibsp_hm_set ihs")
				.append(" left join ibsp_hm_mode_cutover ihmc on ihs.HANDICAP_MEMBER_ID_=ihmc.HANDICAP_MEMBER_ID_")
				.append(" where ihs.MODE_CUTOVER_STATE_=? AND ihmc.CURRENT_INDEX_>=0 AND ihs.HANDICAP_MEMBER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findKVMap(sql.toString(), parameterList);
	}

	/**
	 * 修改检验
	 *
	 * @param hmInfos 会员信息
	 */
	public void updateCorrection(List<Map<String, Object>> hmInfos) throws SQLException {
		String sql = "update %s set CORRECTION_T_=CORRECTION_T_+%s where HANDICAP_MEMBER_ID_ in(";
		StringBuffer sqlPlus = new StringBuffer();
		List<Object> parameterList = new ArrayList<>(4);
		hmInfos.forEach(info -> {
			sqlPlus.append("?,");
			parameterList.add(info.get("HANDICAP_MEMBER_ID_"));
		});
		sqlPlus.replace(sqlPlus.length() - 1, sqlPlus.length(), ")");
		super.dao.execute(String.format(sql + sqlPlus.toString(), "ibsp_profit", "HANDICAP_PROFIT_T_"), parameterList);
		super.dao.execute(String.format(sql + sqlPlus.toString(), "ibsp_profit_vr", "PROFIT_T_"), parameterList);
	}

	/**
	 * 获取止盈止损信息
	 *
	 * @param handicapMemberIds 会员ids
	 * @return
	 */
	public List<Map<String, Object>> findByHmIds(List<String> handicapMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select HANDICAP_MEMBER_ID_,BET_RATE_T_,BET_MERGER_"
				+ " from ibsp_hm_set where STATE_=? and HANDICAP_MEMBER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");

		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 获取每天重置方案的在线会员ids
	 */
	public List<String> everyDayResetHmIds() throws SQLException {
		String sql = "select ihs.HANDICAP_MEMBER_ID_ from ibsp_hm_set ihs LEFT JOIN ibsp_hm_info ihi ON ihs.HANDICAP_MEMBER_ID_=ihi.HANDICAP_MEMBER_ID_"
				+ " where RESET_TYPE_=? and ihs.STATE_=? AND ihi.LOGIN_STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.DAY.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.LOGIN.name());
		return super.dao.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);
	}


	/**
	 * 获取配置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 配置信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId) throws Exception {
		String sql = "select BET_RATE_T_,BET_MERGER_ from ibsp_hm_set where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 更新实体
	 *
	 * @param handicapMemberId 盘口会员ID
	 * @param betMerger        开启合并
	 * @param modeState        真实模拟切换
	 * @param nowTime          时间
	 * @param desc             备注
	 * @return 更新是否成功
	 * @throws SQLException
	 */
	public boolean updateEntity(String handicapMemberId, String betMerger,String modeState ,Date nowTime, String desc) throws SQLException {
		String sql = "UPDATE ibsp_hm_set SET UPDATE_TIME_=?,UPDATE_TIME_LONG_= ?,DESC_ = ? ";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);

		if(StringTool.notEmpty(betMerger)){
			sql += ",BET_MERGER_=?";
			parameterList.add(betMerger);
		}
		if(StringTool.notEmpty(modeState)){
			sql += ",MODE_CUTOVER_STATE_ =? ";
			parameterList.add(modeState);
		}
		sql+=" WHERE HANDICAP_MEMBER_ID_=? and STATE_ = ? ";
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		int changenum = super.dao.execute(sql, parameterList);
		return changenum >= 1;
	}
}
