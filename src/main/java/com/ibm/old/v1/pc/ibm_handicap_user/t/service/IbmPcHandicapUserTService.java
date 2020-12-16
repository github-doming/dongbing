package com.ibm.old.v1.pc.ibm_handicap_user.t.service;

import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPcHandicapUserTService extends IbmHandicapUserTService {

	/**
	 * 获取盘口用户对象是否存在
	 *
	 * @param userId     用户Id
	 * @param handicapId 盘口Id
	 * @return 存在true，不存在false
	 */
	public boolean isExist(String userId, String handicapId) throws Exception {
		String sql = "select IBM_HANDICAP_USER_ID_ from ibm_handicap_user where APP_USER_ID_=? and HANDICAP_ID_=? and state_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		String handicapUserId = super.dao.findString("IBM_HANDICAP_USER_ID_", sql, parameterList);
		return StringTool.notEmpty(handicapUserId);
	}

	/**
	 * 已托管的盘口用户id
	 *
	 * @param userId     用户id
	 * @param handicapId 盘口id
	 * @return 已托管的盘口用户id
	 */
	public List<Map<String, Object>> listOnHostingHMInfo(String userId, String handicapId) throws SQLException {
		String onlineMembersIds = this.findOnlineMembersIds(userId, handicapId);
		if (StringTool.isEmpty(onlineMembersIds)) {
			return null;
		}
		String sql = "SELECT ihm.IBM_HANDICAP_MEMBER_ID_ AS HANDICAP_MEMBER_ID_,ihm.MEMBER_ACCOUNT_,ihm.HANDICAP_CODE_ "
				+ "FROM `ibm_handicap_member` ihm LEFT JOIN ibm_hm_set ihs ON ihm.IBM_HANDICAP_MEMBER_ID_ = ihs.HANDICAP_MEMBER_ID_ where 1 = 1 ";
		StringBuilder sqlBuilder = new StringBuilder(" and ihm.IBM_HANDICAP_MEMBER_ID_ IN (");
		List<Object> parameterList = new ArrayList<>();
		for (String onlineMembersId : onlineMembersIds.split(",")) {
			sqlBuilder.append("?,");
			parameterList.add(onlineMembersId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") and ihm.LOGIN_STATE_ = ? and ihm.STATE_ = ? ");
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		sql += sqlBuilder.toString();
		return super.dao.findMapList(sql, parameterList);

	}

	/**
	 * 获取在线会员id数组
	 *
	 * @param userId     用户id
	 * @param handicapId 盘口code
	 * @return 在线会员id数组
	 */
	private String findOnlineMembersIds(String userId, String handicapId) throws SQLException {
		String sql = "SELECT ihu.ONLINE_MEMBERS_IDS_ FROM `ibm_handicap_user` ihu WHERE ihu.APP_USER_ID_ = ? "
				+ " and HANDICAP_ID_ = ? AND STATE_ != ? and ONLINE_MEMBERS_COUNT_ > 0";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("ONLINE_MEMBERS_IDS_", sql, parameterList);
	}

	/**
	 * 未托管的盘口用户id
	 *
	 * @param userId     用户id
	 * @param handicapId 盘口id
	 * @return 未托管的盘口用户id
	 */
	public List<Map<String, Object>> listUnHostingHMInfo(String userId, String handicapId) throws SQLException {
		String sql =
				"SELECT ihm.IBM_HANDICAP_MEMBER_ID_ AS HANDICAP_MEMBER_ID_,ihm.MEMBER_ACCOUNT_,ihm.HANDICAP_CODE_ FROM "
						+ "`ibm_handicap_member` ihm LEFT JOIN ibm_hm_set ihs ON ihm.IBM_HANDICAP_MEMBER_ID_ = ihs.HANDICAP_MEMBER_ID_  "
						+ "where 1 = 1 and ihm.LOGIN_STATE_ = ? and ihm.STATE_ = ? AND ihm.APP_USER_ID_ = ? AND ihm.HANDICAP_ID_ = ? AND ihs.SAVE_ACCOUNT_ = ? ";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(IbmStateEnum.LOGOUT.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmTypeEnum.TRUE.name());
		return super.dao.findMapList(sql, parameterList);

	}

	/**
	 * 获取用户最近打开过的盘口信息
	 *
	 * @param userId 用户id
	 * @return 最近打开过的盘口信息
	 */
	public List<Map<String, Object>> listRecentLoginHandicapInfo(String userId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_,HANDICAP_NAME_ FROM `ibm_handicap_user` where APP_USER_ID_ = ? and "
				+ " STATE_ != ? and FREQUENCY_ != ? ORDER BY UPDATE_TIME_LONG_,FREQUENCY_ DESC";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(0);
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取正在托管的盘口信息
	 *
	 * @param userId 用户id
	 * @return 正在托管的盘口信息
	 */
	public List<Map<String, Object>> listOnHostingHandicapInfo(String userId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_,HANDICAP_NAME_ FROM `ibm_handicap_user` where APP_USER_ID_ = ? and "
				+ " ONLINE_MEMBERS_COUNT_ > 0 and STATE_ != ? ORDER BY FREQUENCY_,UPDATE_TIME_LONG_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 根据用户code 盘口code获取盘口用户信息
	 *
	 * @param handicapCode 盘口code
	 * @param userId       用户id
	 * @return 盘口用户信息
	 */
	public String findId(String handicapCode, String userId) throws Exception {
		String sql = "select IBM_HANDICAP_USER_ID_ from ibm_handicap_user where HANDICAP_CODE_ = ? and APP_USER_ID_ = ? and state_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapCode);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("IBM_HANDICAP_USER_ID_", sql, parameterList);
	}

	/**
	 * @param handicapId 盘口id
	 * @param userId     用户id
	 * @return
	 * @throws SQLException
	 */
	public boolean limit(String handicapId, String userId) throws SQLException {
		String sql = "SELECT ONLINE_MEMBERS_COUNT_,ONLINE_NUMBER_MAX_ FROM `ibm_handicap_user` where HANDICAP_ID_ = ? "
				+ " and APP_USER_ID_ = ? and STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapId);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());

		Map<String, Object> handicapUserCount = dao.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(handicapUserCount)) {
			return false;
		}

		sql = "SELECT CLOUD_CONFIG_VALUE_ FROM ibm_cloud_config  where CLOUD_CONFIG_KEY_ = ? and state_ != ?";
		parameterList.clear();
		parameterList.add("ONLINE_NUMBER_MAX");
		parameterList.add(IbmStateEnum.DEL.name());
		String cloudOnlineNumberMax = super.dao.findString("CLOUD_CONFIG_VALUE_", sql, parameterList);
		if (StringTool.isEmpty(cloudOnlineNumberMax)) {
			return false;
		}

		int online = Integer.parseInt(handicapUserCount.getOrDefault("ONLINE_MEMBERS_COUNT_", "0").toString());
		int onlineUserMax = Integer.parseInt(handicapUserCount.getOrDefault("ONLINE_NUMBER_MAX_", "0").toString());
		int onlineCloudMax = Integer.parseInt(cloudOnlineNumberMax);
		//在线增加1
		online++;
		return (online <= onlineUserMax) && (online <= onlineCloudMax);

	}

	/**
	 * 获取用户拥有的盘口列表
	 *
	 * @param userId 用户id
	 * @return 盘口信息列表<br>
	 * HANDICAP_NAME_	盘口名称<br>
	 * HANDICAP_CODE_	盘口编码<br>
	 * HANDICAP_ICON_	盘口图标<br>
	 * SN_	盘口顺序<br>
	 */
	public List<Map<String, Object>> listUserHandicap(String userId) throws SQLException {
		String sql = "SELECT ih.HANDICAP_NAME_,ih.HANDICAP_CODE_,ih.HANDICAP_ICON_,ih.SN_ FROM `ibm_handicap_user` ihu"
				+ " LEFT JOIN ibm_handicap ih ON ihu.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_ "
				+ " where  ihu.APP_USER_ID_ = ? and ihu.state_ = ? and ih.state_ != ? ORDER BY ih.SN_";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * @param appUserId 用户id
	 * @return 游戏列表
	 * @Description: 根据用户id查询游戏列表
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> listGame(String appUserId) throws SQLException {
		String sql = "SELECT ihg.GAME_CODE_,ihg.GAME_ID_ FROM ibm_handicap_user ihu LEFT JOIN ibm_handicap_game ihg "
				+ "ON ihu.HANDICAP_ID_ = ihg.HANDICAP_ID_ WHERE ihu.STATE_ != ? AND ihg.STATE_ != ? "
				+ "AND ihu.APP_USER_ID_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(appUserId);
		return super.dao.findMapList(sql, parameters);
	}
}
