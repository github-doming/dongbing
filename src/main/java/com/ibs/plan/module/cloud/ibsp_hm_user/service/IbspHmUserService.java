package com.ibs.plan.module.cloud.ibsp_hm_user.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_hm_user.entity.IbspHmUser;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_盘口会员用户 服务类
 *
 * @author Robot
 */
public class IbspHmUserService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员用户 对象数据
	 *
	 * @param entity IbspHmUser对象数据
	 */
	public String save(IbspHmUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_hm_user 的 IBSP_HM_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_hm_user set state_='DEL' where IBSP_HM_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HM_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_hm_user 的 IBSP_HM_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_hm_user set state_='DEL' where IBSP_HM_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_hm_user  的 IBSP_HM_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_hm_user where IBSP_HM_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HM_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_hm_user 的 IBSP_HM_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_hm_user where IBSP_HM_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHmUser实体信息
	 *
	 * @param entity IBSP_盘口会员用户 实体
	 */
	public void update(IbspHmUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_hm_user表主键查找 IBSP_盘口会员用户 实体
	 *
	 * @param id ibsp_hm_user 主键
	 * @return IBSP_盘口会员用户 实体
	 */
	public IbspHmUser find(String id) throws Exception {
		return dao.find(IbspHmUser.class, id);
	}

	/**
	 * 盘口会员登出
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param nowTime          当前时间
	 */
	public void updateLogout(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "SELECT ihu.ONLINE_MEMBERS_IDS_,ihu.IBSP_HM_USER_ID_ FROM `ibsp_hm_user` ihu "
				+ " LEFT JOIN ibsp_handicap_member ihm ON ihu.HANDICAP_ID_ = ihm.HANDICAP_ID_ AND ihu.APP_USER_ID_ = ihm.APP_USER_ID_ "
				+ " WHERE ihm.IBSP_HANDICAP_MEMBER_ID_ = ? AND ihu.STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());
		Map<String, Object> info = super.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return;
		}
		String onlineMembersIds = info.get("ONLINE_MEMBERS_IDS_").toString();
		//在线会员id集合
		Set<String> onlineMembersIdSet = new HashSet<>(Arrays.asList(onlineMembersIds.split(",")));
		if (ContainerTool.isEmpty(onlineMembersIdSet)) {
			return;
		}
		// 移除在线会员id
		onlineMembersIdSet.remove(handicapMemberId);
		// 更新
		updateOnlineMember(onlineMembersIdSet, info.get("IBSP_HM_USER_ID_").toString(), nowTime, IbsStateEnum.LOGOUT);
	}

	/**
	 * 盘口会员登录
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param nowTime          当前时间
	 */
	public void updateLogin(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "SELECT ihu.ONLINE_MEMBERS_IDS_,ihu.IBSP_HM_USER_ID_ FROM `ibsp_hm_user` ihu "
				+ " LEFT JOIN ibsp_handicap_member ihm ON ihu.HANDICAP_ID_ = ihm.HANDICAP_ID_ AND ihu.APP_USER_ID_ = ihm.APP_USER_ID_ "
				+ " WHERE ihm.IBSP_HANDICAP_MEMBER_ID_ = ? AND ihu.STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());
		Map<String, Object> info = super.dao.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return;
		}
		Set<String> onlineMembersIdSet = new HashSet<>();
		if (StringTool.notEmpty(info.get("ONLINE_MEMBERS_IDS_"))) {
			onlineMembersIdSet.addAll(Arrays.asList(info.get("ONLINE_MEMBERS_IDS_").toString().split(",")));
		}
		// 新增在线会员id
		onlineMembersIdSet.add(handicapMemberId);
		// 更新
		updateOnlineMember(onlineMembersIdSet, info.get("IBSP_HM_USER_ID_").toString(), nowTime,
				IbsStateEnum.LOGIN);

	}

	/**
	 * 更新盘口会员在线数量
	 *
	 * @param onlineMembersIdSet 在线会员列表
	 * @param hmUserId           盘口会员用户id
	 * @param nowTime            当前时间
	 * @param method             状态
	 */
	private void updateOnlineMember(Set<String> onlineMembersIdSet, String hmUserId, Date nowTime, IbsStateEnum method)
			throws SQLException {
		StringBuilder sql = new StringBuilder(
				"UPDATE `ibsp_hm_user` SET ONLINE_MEMBERS_IDS_ = ?, ONLINE_MEMBERS_COUNT_ = ?"
						+ ",UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? ");
		if (IbsStateEnum.LOGIN.equals(method)) {
			sql.append(",FREQUENCY_ = FREQUENCY_ + 1");
		}
		sql.append(" WHERE IBSP_HM_USER_ID_ = ?");
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(StringUtils.join(onlineMembersIdSet, ","));
		parameterList.add(onlineMembersIdSet.size());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(hmUserId);
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 根据用户ID查询盘口CODE
	 *
	 * @param appUserId 用户ID
	 * @return 盘口CODE 集合
	 */
	public List<String> findHandicapCodeByUserId(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_ from ibsp_hm_user where APP_USER_ID_ = ? and state_ !=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.DEL.name());
		return dao.findStringList("HANDICAP_CODE_", sql, parameterList);
	}

	/**
	 * 批量添加盘口用户信息
	 *
	 * @param handicaps 盘口ids
	 * @param appUserId 用户id
	 * @param memberMax 会员最大数
	 * @param nowTime   添加时间
	 */
	public void saveRegister(List<Map<String, Object>> handicaps, String appUserId, Integer memberMax, Date nowTime)
			throws SQLException {
		StringBuilder sql = new StringBuilder("insert into ibsp_hm_user(IBSP_HM_USER_ID_,APP_USER_ID_,HANDICAP_ID_,"
				+ "HANDICAP_CODE_,HANDICAP_NAME_,ONLINE_MEMBERS_COUNT_,ONLINE_NUMBER_MAX_,FREQUENCY_,CREATE_TIME_,"
				+ "CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
		List<Object> parameterList = new ArrayList<>();
		for (Map<String, Object> handicap : handicaps) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(appUserId);
			parameterList.add(handicap.get("IBSP_HANDICAP_ID_").toString());
			parameterList.add(handicap.get("HANDICAP_CODE_").toString());
			parameterList.add(handicap.get("HANDICAP_NAME_").toString());
			parameterList.add(0);
			parameterList.add(memberMax);
			parameterList.add(0);
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
	 * 获取用户拥有的盘口列表
	 *
	 * @param appUserId 用户id
	 * @return 盘口信息列表<br>
	 * HANDICAP_NAME_	盘口名称<br>
	 * HANDICAP_CODE_	盘口编码<br>
	 * HANDICAP_ICON_	盘口图标<br>
	 * SN_	盘口顺序<br>
	 */
	public List<Map<String, Object>> listHandicapShowInfo(String appUserId) throws SQLException {
		String sql = "SELECT ih.HANDICAP_NAME_,ih.HANDICAP_CODE_,ih.HANDICAP_ICON_,ih.SN_ FROM ibsp_hm_user ihu "
				+ " LEFT JOIN ibsp_handicap ih ON ihu.HANDICAP_ID_ = ih.IBSP_HANDICAP_ID_ WHERE "
				+ " ihu.APP_USER_ID_ = ? AND ihu.state_ = ? AND ih.state_ != ? ORDER BY ih.SN_";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findMapList(sql, parameterList);

	}

	/**
	 * 获取最近登录的盘口
	 *
	 * @param appUserId 用户id
	 * @return 登录的盘口
	 */
	public List<Map<String, Object>> listRecentLogin(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_,HANDICAP_NAME_ FROM ibsp_hm_user "
				+ " WHERE APP_USER_ID_ = ? AND STATE_ = ? AND FREQUENCY_ > ? ORDER BY FREQUENCY_,UPDATE_TIME_LONG_ DESC";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(0);
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取正在托管的盘口信息
	 *
	 * @param appUserId 用户id
	 * @return 正在托管的盘口信息
	 */
	public List<Map<String, Object>> listOnHosting(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_,HANDICAP_NAME_ FROM ibsp_hm_user "
				+ " WHERE APP_USER_ID_ = ? AND STATE_ = ? AND ONLINE_MEMBERS_COUNT_ > ? ORDER BY FREQUENCY_,UPDATE_TIME_LONG_ DESC";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(0);
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取 在线会员主键组
	 *
	 * @param appUserId  用户主键
	 * @param handicapId 盘口主键
	 * @return 在线会员主键组
	 */
	public String findOnlineMembersId(String appUserId, String handicapId) throws SQLException {
		String sql = "SELECT ONLINE_MEMBERS_IDS_ FROM `ibsp_hm_user` where APP_USER_ID_ = ? and HANDICAP_ID_ = ? AND"
				+ " ONLINE_MEMBERS_COUNT_ > 0 and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findString("ONLINE_MEMBERS_IDS_", sql, parameterList);
	}

	/**
	 * 获取盘口会员用户主键
	 *
	 * @param appUserId  用户主键
	 * @param handicapId 盘口主键
	 * @return 盘口会员用户主键
	 */
	public String findId(String appUserId, String handicapId) throws SQLException {
		String sql = "SELECT IBSP_HM_USER_ID_ FROM `ibsp_hm_user` where APP_USER_ID_ = ? and HANDICAP_ID_ = ? "
				+ " AND ONLINE_MEMBERS_COUNT_ < ONLINE_NUMBER_MAX_ and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findString("IBSP_HM_USER_ID_", sql, parameterList);
	}

	/**
	 * 获取盘口信息
	 *
	 * @param appUserId 用户id
	 * @return 盘口信息
	 */
	public List<Map<String, Object>> listHandicapInfo(String appUserId) throws SQLException {
		String sql = "select HANDICAP_CODE_ ,ONLINE_NUMBER_MAX_  from ibsp_hm_user WHERE APP_USER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取用户的盘口名称
	 *
	 * @param userIdList 用户ID集合
	 * @return 盘口名称列表
	 */
	public Map<String, List<String>>  listHandicapNameByUserIds(List<String> userIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT APP_USER_ID_ key_,HANDICAP_NAME_ AS value_ FROM ibsp_hm_user WHERE  STATE_ != ? and APP_USER_ID_ in(  " );
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.DEL.name());
		for (String uid:userIdList){
			sql.append("?,");
			parameterList.add(uid);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(") ORDER BY HANDICAP_CODE_");

		return super.findKVsMapList(sql, parameterList);
	}

	/**
	 * @param handicaps  盘口信息
	 * @param appUserId  用户id
	 * @param memberInfo 会员信息
	 * @return
	 */
	public void save(List<Map<String, Object>> handicaps, String appUserId, Map<Object, Object> memberInfo) throws SQLException {
		Date nowTime = new Date();
		StringBuilder sql = new StringBuilder("insert into ibsp_hm_user(`IBSP_HM_USER_ID_`, `APP_USER_ID_`, `HANDICAP_ID_`, `HANDICAP_CODE_`, `HANDICAP_NAME_`,  `ONLINE_MEMBERS_COUNT_`, `ONLINE_NUMBER_MAX_`, `FREQUENCY_`, " +
				"`CREATE_TIME_`, `CREATE_TIME_LONG_`, `UPDATE_TIME_`, `UPDATE_TIME_LONG_`, `STATE_`) VALUES");
		List<Object> parameterList = new ArrayList<>();
		for (Map<String, Object> handicap : handicaps) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(UUID.randomUUID().toString().replace("-", ""));
			parameterList.add(appUserId);
			parameterList.add(handicap.get("IBSP_HANDICAP_ID_").toString());
			parameterList.add(handicap.get("HANDICAP_CODE_").toString());
			parameterList.add(handicap.get("HANDICAP_NAME_").toString());
			parameterList.add(0);
			parameterList.add(memberInfo.get(handicap.get("HANDICAP_CODE_")));
			parameterList.add(0);
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
	 * 获取用户会员盘口信息
	 * @param appUserId     用户id
	 * @param handicapCodes     盘口code
	 * @return
	 */
	public List<Map<String, Object>> findHandicap(String appUserId, Set<Object> handicapCodes) throws SQLException {
		StringBuilder sql = new StringBuilder("select HANDICAP_ID_,ONLINE_MEMBERS_IDS_ from ibsp_hm_user where APP_USER_ID_=? and STATE_ = ? and HANDICAP_CODE_ in(");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (Object handicapCode : handicapCodes) {
			sql.append("?,");
			parameterList.add(handicapCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}


	/**
	 * 删除用户指定盘口
	 * @param appUserId 用户ID
	 * @param handicapId 盘口Id
	 * @throws SQLException
	 */
	public void updateByAppUserId(String appUserId, String handicapId) throws SQLException {
		String sql = "update ibsp_hm_user set STATE_=?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?,DESC_ =? where APP_USER_ID_=? AND HANDICAP_ID_=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("更改用户会员盘口");
		parameterList.add(appUserId);
		parameterList.add(handicapId);
		dao.execute(sql,parameterList);
	}

	/**
	 * 修改最大在线数
	 * @param appUserId 用户主键
	 * @param handicapCode 盘口编码
	 * @param onlineNumberMax 最大在线数
	 */
	public void updateOnlineNumMax(String appUserId, Object handicapCode, Object onlineNumberMax) throws SQLException {
		String sql ="UPDATE ibsp_hm_user set ONLINE_NUMBER_MAX_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_USER_ID_ = ? and HANDICAP_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(onlineNumberMax);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		parameterList.add(handicapCode);
		parameterList.add(IbsStateEnum.DEL.name());
		super.execute(sql,parameterList);
	}

}
