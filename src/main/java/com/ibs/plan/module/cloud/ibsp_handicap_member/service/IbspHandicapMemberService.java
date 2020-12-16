package com.ibs.plan.module.cloud.ibsp_handicap_member.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.tools.EncryptTool;
import com.ibs.plan.connector.pc.handicap.MemberLoginAction;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_盘口会员 服务类
 *
 * @author Robot
 */
public class IbspHandicapMemberService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员 对象数据
	 *
	 * @param entity IbspHandicapMember对象数据
	 */
	public String save(IbspHandicapMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_handicap_member 的 IBSP_HANDICAP_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_handicap_member set state_='DEL' where IBSP_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSP_HANDICAP_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_handicap_member 的 IBSP_HANDICAP_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_handicap_member set state_='DEL' where IBSP_HANDICAP_MEMBER_ID_ in(" + stringBuilder
							.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_handicap_member  的 IBSP_HANDICAP_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_handicap_member where IBSP_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSP_HANDICAP_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_handicap_member 的 IBSP_HANDICAP_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibsp_handicap_member where IBSP_HANDICAP_MEMBER_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHandicapMember实体信息
	 *
	 * @param entity IBSP_盘口会员 实体
	 */
	public void update(IbspHandicapMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_handicap_member表主键查找 IBSP_盘口会员 实体
	 *
	 * @param id ibsp_handicap_member 主键
	 * @return IBSP_盘口会员 实体
	 */
	public IbspHandicapMember find(String id) throws Exception {
		return dao.find(IbspHandicapMember.class, id);
	}

	/**
	 * 获取操作为登陆和登出的盘口会员
	 *
	 * @param existHmIds 有异常的存在盘口主键
	 * @return 正在操作的主键
	 */
	public List<String> listOperatingId(Set<Object> existHmIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT ich.EXIST_HM_ID_ FROM ibsp_client_hm ich "
				+ " LEFT JOIN ibsp_handicap_member ihm ON ich.HANDICAP_MEMBER_ID_ = ihm.IBSP_HANDICAP_MEMBER_ID_ "
				+ " WHERE ihm.OPERATING_ IN (?, ?) AND ich.EXIST_HM_ID_ IN (");
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(IbsStateEnum.LOGOUT.name());
		for (Object existHmId : existHmIds) {
			sql.append("?,");
			parameterList.add(existHmId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findStringList("EXIST_HM_ID_", sql.toString(), parameterList);
	}

	/**
	 * 获取登录信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 登录信息
	 */
	public Map<String, Object> findLoginInfo(String handicapMemberId) throws SQLException {
		String sql =
				"SELECT ihi.HANDICAP_CODE_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,ihm.MEMBER_ACCOUNT_,ihm.MEMBER_PASSWORD_ "
						+ " FROM `ibsp_handicap_member` ihm LEFT JOIN ibsp_handicap_item ihi ON ihi.IBSP_HANDICAP_ITEM_ID_ = ihm.HANDICAP_ITEM_ID_ "
						+ " WHERE ihm.IBSP_HANDICAP_MEMBER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		return super.findMap(sql, parameterList);

	}
	/**
	 * 批量获取多用户登录信息
	 *
	 * @param handicapMemberIds 盘口会员ids
	 * @return
	 */
	public Map<String, Map<String, Object>> findLoginInfo(Set<String> handicapMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select ihm.IBSP_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_,ihi.HANDICAP_CODE_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,"
						+ "ihm.MEMBER_ACCOUNT_,ihm.MEMBER_PASSWORD_ from ibsp_handicap_member ihm LEFT JOIN ibsp_handicap_item ihi ON ihi.IBSP_HANDICAP_ITEM_ID_ = ihm.HANDICAP_ITEM_ID_"
						+ " WHERE ihm.STATE_ = ? AND ihm.IBSP_HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameterList);
		if (ContainerTool.isEmpty(list)) {
			return new HashMap<>(1);
		}
		Map<String, Map<String, Object>> handicapMemberInfo = new HashMap<>(list.size());
		for (Map<String, Object> map : list) {
			handicapMemberInfo.put(map.get("HANDICAP_MEMBER_ID_").toString(), map);
		}
		return handicapMemberInfo;
	}
	/**
	 * 获取盘口会员信息
	 * @param handicapMemberIds 盘口会员ids
	 * @return
	 */
	public List<Map<String, Object>> findMemberInfos(List<String> handicapMemberIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		List<Object> parameterList = new ArrayList<>();
		sql.append("select IBSP_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_,MEMBER_ACCOUNT_,MEMBER_PASSWORD_,HANDICAP_URL_,HANDICAP_CAPTCHA_,HANDICAP_CODE_");
		sql.append(" from ibsp_handicap_member LEFT JOIN ibsp_handicap_item on HANDICAP_ITEM_ID_=IBSP_HANDICAP_ITEM_ID_ where IBSP_HANDICAP_MEMBER_ID_ in(");
		for(String handicapMemberId:handicapMemberIds){
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.dao.findMapList(sql.toString(),parameterList);
	}

	/**
	 * 获取登录信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param appUserId        用户主键
	 * @return 登录信息
	 */
	public String findHandicapId(String handicapMemberId, String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_ID_ FROM `ibsp_handicap_member` "
				+ " where IBSP_HANDICAP_MEMBER_ID_ = ? and APP_USER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("HANDICAP_ID_", sql, parameterList);
	}

	/**
	 * 获取会员信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 会员信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT MEMBER_ACCOUNT_,MEMBER_PASSWORD_,HANDICAP_ITEM_ID_,OPERATING_ FROM `ibsp_handicap_member` "
				+ " where IBSP_HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 查询该用户所有账号信息
	 *
	 * @param appUserId  用户id
	 * @param handicapId 盘口id
	 * @return 账号信息
	 */
	public List<Map<String, Object>> listAllAccount(String appUserId, String handicapId) throws SQLException {
		String sql = "SELECT ihm.IBSP_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_, ihm.MEMBER_ACCOUNT_ FROM "
				+ " ibsp_handicap_member ihm LEFT JOIN ibsp_hm_info ihi ON ihm.IBSP_HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
				+ " WHERE ihm.APP_USER_ID_ = ? AND ihm.HANDICAP_ID_ = ? AND ihm.STATE_ = ? AND ihi.STATE_ = ? AND ihi.LOGIN_STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(appUserId);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.LOGIN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取会员离线信息
	 *
	 * @param appUserId         用户id
	 * @param handicapId        盘口id
	 * @param handicapMemberIds 在线 盘口会员id数组
	 * @return 会员离线信息
	 */
	public List<Map<String, Object>> listOfflineInfo(String appUserId, String handicapId, Set<Object> handicapMemberIds)
			throws SQLException {
		String sql = "SELECT IBSP_HANDICAP_MEMBER_ID_ AS HANDICAP_MEMBER_ID_, MEMBER_ACCOUNT_,HANDICAP_ITEM_ID_ "
				+ " FROM ibsp_handicap_member WHERE APP_USER_ID_ = ? AND HANDICAP_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(appUserId);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		if (ContainerTool.notEmpty(handicapMemberIds)) {
			StringBuilder sqlBuilder = new StringBuilder(" and IBSP_HANDICAP_MEMBER_ID_ not in ( ");
			for (Object handicapMemberId : handicapMemberIds) {
				sqlBuilder.append("?,");
				parameterList.add(handicapMemberId);
			}
			sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(")");
			sql += sqlBuilder.toString();
		}
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 会员ID登录 修改
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param landedTimeLong   定时登录事件
	 * @param memberAccount    会员账户
	 * @param memberPassword   会员密码
	 * @param handicapItemId   盘口详情主键
	 * @param nowTime          修改时间
	 */
	public void update(String handicapMemberId, Long landedTimeLong, String memberAccount, String memberPassword,
			String handicapItemId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibsp_handicap_member set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? ";
		List<Object> parameterList = new ArrayList<>(9);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());

		if (landedTimeLong != null) {
			sql = sql.concat(",IS_LANDED_ = ?,LANDED_TIME_LONG_ = ? ");
			parameterList.add(IbsTypeEnum.TRUE.name());
			parameterList.add(landedTimeLong + 6000L);
		} else {
			sql = sql.concat(",OPERATING_ = ? ");
			parameterList.add(IbsStateEnum.LOGIN.name());
		}
		if (StringTool.notEmpty(memberAccount)) {
			sql = sql.concat(",MEMBER_ACCOUNT_ = ? ");
			parameterList.add(memberAccount);
		}
		if (StringTool.notEmpty(memberPassword)) {
			sql = sql.concat(",MEMBER_PASSWORD_ = ? ");
			parameterList.add(memberPassword);
		}
		if (StringTool.notEmpty(handicapItemId)) {
			sql = sql.concat(",HANDICAP_ITEM_ID_ = ? ");
			parameterList.add(handicapItemId);
		}
		sql = sql.concat(",DESC_ = ? where IBSP_HANDICAP_MEMBER_ID_ = ? and STATE_ = ?");
		parameterList.add(MemberLoginAction.class.getName().concat("，会员ID登录"));
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取 盘口会员主键
	 *
	 * @param appUserId     用户主键
	 * @param memberAccount 会员账户
	 * @param handicapId    盘口主键
	 * @return 盘口会员主键
	 */
	public Map<String, Object> findMemberInfo(String appUserId, String memberAccount, String handicapId)
			throws SQLException {
		String sql =
				"SELECT IBSP_HANDICAP_MEMBER_ID_,OPERATING_,STATE_ FROM ibsp_handicap_member where APP_USER_ID_ = ? and "
						+ " MEMBER_ACCOUNT_ = ? and HANDICAP_ID_ = ? and STATE_ != ? LIMIT 1";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(memberAccount);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 逻辑删除盘口会员相关信息
	 *
	 * @param handicapMemberId 盘口会员id
	 */
	public void delHmInfo(String handicapMemberId, Date nowTime, String desc) throws SQLException {
		String sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
				+ " HANDICAP_MEMBER_ID_ =? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());

		super.dao.execute(String.format(sqlFormat, "ibsp_hm_bet_item"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_game_set"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_info"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_set"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_plan_hm"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_vr"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_period"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_period_vr"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_plan"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_plan_vr"), parameterList);

		sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
				+ " IBSP_HANDICAP_MEMBER_ID_ =? and STATE_ != ?";
		super.dao.execute(String.format(sqlFormat, "ibsp_handicap_member"), parameterList);
	}

	/**
	 * 修改操作
	 *
	 * @param handicapMemberId 盘口会员id
	 */
	public void updateOperating(String handicapMemberId) throws SQLException {
		String sql = "update ibsp_handicap_member set OPERATING_=?,UPDATE_TIME_LONG_=? where IBSP_HANDICAP_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbsStateEnum.NONE.name());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 修改90S前操作状态为登陆或登出的会员信息
	 */
	public void updateOperating() throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("select IBSP_HANDICAP_MEMBER_ID_ from ibsp_handicap_member where (OPERATING_=? or OPERATING_=?) and STATE_!=?")
				.append(" and UPDATE_TIME_LONG_ < ?");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(IbsStateEnum.LOGOUT.name());
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(System.currentTimeMillis()-90*1000L);
		List<String> handicapMemberIds=super.dao.findStringList("IBSP_HANDICAP_MEMBER_ID_",sql.toString(),parameterList);
		if(ContainerTool.isEmpty(handicapMemberIds)){
			return ;
		}
		sql.delete(0,sql.length());
		parameterList.clear();
		sql.append("update ibsp_handicap_member set OPERATING_=?,UPDATE_TIME_LONG_=?,DESC_=? where IBSP_HANDICAP_MEMBER_ID_ in(");
		parameterList.add(IbsStateEnum.NONE.name());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("自动管理修改操作状态");
		for(String handicapMemberId:handicapMemberIds){
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(),parameterList);
	}

	/**
	 * 获取一分钟以内的定时登录的盘口会员
	 *
	 * @return 盘口会员信息列表
	 */
	public List<Map<String, Object>> listTimeLanded() throws SQLException {
		String sql = "SELECT ihi.HANDICAP_MEMBER_ID_, ihm.APP_USER_ID_,ihm.MEMBER_ACCOUNT_,ihm.HANDICAP_ID_ "
				+ " FROM `ibsp_handicap_member` ihm "
				+ " LEFT JOIN ibsp_hm_info ihi ON ihm.IBSP_HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ AND ihi.LOGIN_STATE_ != ? "
				+ " WHERE IS_LANDED_ = ? and OPERATING_ = ? AND LANDED_TIME_LONG_ BETWEEN ? AND ? AND ihm.STATE_ = ? AND ihi.STATE_ = ? ORDER BY HANDICAP_ID_";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(IbsTypeEnum.TRUE.name());
		parameterList.add(IbsStateEnum.NONE.name());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(System.currentTimeMillis() + 60000L);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql,parameterList);
	}

	/**
	 * 获取会员信息
	 * @param handicapMemberId	会员id
	 * @param password			会员密码
	 * @return
	 */
	public IbspHandicapMember findHmByPassword(String handicapMemberId, String password) throws Exception {
		password = EncryptTool.encode(EncryptTool.Type.ASE,password);

		List<Object> parameterList = new ArrayList<>();
		String sql = "select * from ibsp_handicap_member where IBSP_HANDICAP_MEMBER_ID_=? and MEMBER_PASSWORD_=?";
		parameterList.add(handicapMemberId);
		parameterList.add(password);
		return this.dao.findObject(IbspHandicapMember.class, sql, parameterList);
	}

	/**
	 * 获取用户会员信息
	 * @param appUserId     用户id
	 * @return
	 */
	public List<Map<String, Object>> findMemberByUserId(String appUserId) throws SQLException {
		String sql="SELECT ihm.IBSP_HANDICAP_MEMBER_ID_,ihm.HANDICAP_ID_,ihi.STATE_ FROM"
				+ " ibsp_handicap_member ihm LEFT JOIN ibsp_hm_info ihi ON ihm.IBSP_HANDICAP_MEMBER_ID_=ihi.HANDICAP_MEMBER_ID_"
				+ " WHERE ihm.APP_USER_ID_ =? AND ihm.STATE_ =?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameterList);
	}

	/**
	 * 获取详情信息
	 * @param handicapMemberId  盘口会员id
	 * @return 详情信息
	 */
	public Map<String, Object> findItemInfo(String handicapMemberId) throws SQLException {
		String sql="SELECT iu.NICKNAME_ APP_USER_NAME_,ihm.MEMBER_ACCOUNT_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,ihm.LANDED_TIME_LONG_,ihm.HANDICAP_ID_"
				+ " FROM ibsp_handicap_member ihm LEFT JOIN ibsp_handicap_item ihi ON ihm.HANDICAP_ITEM_ID_=ihi.IBSP_HANDICAP_ITEM_ID_ LEFT JOIN ibsp_user iu ON ihm.APP_USER_ID_ = iu.APP_USER_ID_"
				+ " WHERE ihm.IBSP_HANDICAP_MEMBER_ID_=? and ihm.STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql,parameterList);
	}

	/**
	 * 获取用户名和会员名
	 * @param handicapMemberId 会员主键
	 * @return  用户名和会员名
	 */
	public Map<String, Object> findName(String handicapMemberId) throws SQLException {
		String sql = "SELECT ihm.MEMBER_ACCOUNT_, iu.NICKNAME_ FROM `ibsp_handicap_member` ihm "
				+ " LEFT JOIN ibsp_user iu ON ihm.APP_USER_ID_ = iu.APP_USER_ID_  where ihm.IBSP_HANDICAP_MEMBER_ID_ = ?"
				+ " and ihm.STATE_ != ? and iu.STATE_ != ?;";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findMap(sql,parameterList);
	}

	/**
	 * 获取主键信息
	 * @param appUserId 游戏主键
	 * @param handicapCode 盘口编码
	 * @return 主键信息列表
	 */
	public List<String> listId(String appUserId, String handicapCode) throws SQLException {
		String sql = "SELECT IBSP_HANDICAP_MEMBER_ID_ as key_ FROM `ibsp_handicap_member` "
				+ " where HANDICAP_CODE_ = ? and APP_USER_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapCode);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findStringList(sql,parameterList);
	}

	/**
	 *  获取主键信息
	 * @param userIds 用户Id集合
	 * @return 游戏信息
	 */
	public List<Map<String,Object>> lisIds(List<String> userIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBSP_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_ FROM `ibsp_handicap_member` where HANDICAP_CODE_ = ?  AND STATE_ = ? and APP_USER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String userId :userIds){
			parameterList.add(userId);
			sql.append("?,");
		}
		sql.deleteCharAt(sql.length()-1);
		return super.dao.findMapList(sql.toString(),parameterList);
	}

	/**
	 * 获取用户id
	 * @param handicapMemberId	盘口会员id
	 * @return
	 */
	public String findUserId(String handicapMemberId) throws SQLException {
		String sql="select APP_USER_ID_ as key_ from ibsp_handicap_member where IBSP_HANDICAP_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		return super.findString(sql,parameterList);
	}

	/**
	 * 获取会员方案类型
	 * @param handicapMemberId	盘口会员id
	 * @return
	 */
	public String findHmPlanType(String handicapMemberId) throws SQLException {
		String sql="SELECT ROLE_CODE_ FROM ibsp_handicap_member ihm LEFT JOIN ibsp_exp_user ieu ON ihm.APP_USER_ID_=ieu.APP_USER_ID_"
				+ " LEFT JOIN ibsp_exp_role ier ON ieu.EXP_ROLE_ID_=ier.IBSP_EXP_ROLE_ID_ WHERE ihm.IBSP_HANDICAP_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(handicapMemberId);
		return super.findString("ROLE_CODE_",sql,parameterList);
	}
}
