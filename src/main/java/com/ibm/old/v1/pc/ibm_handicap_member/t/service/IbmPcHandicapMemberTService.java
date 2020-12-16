package com.ibm.old.v1.pc.ibm_handicap_member.t.service;

import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IbmPcHandicapMemberTService extends IbmHandicapMemberTService {

	/**
	 * 获取登陆结果
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 登陆结果信息
	 */
	public JSONObject findMemberInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT MEMBER_INFO_,LOGIN_STATE_ FROM ibm_handicap_member WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND "
				+ " STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String,Object> map = super.dao.findMap( sql, parameterList);
		if(ContainerTool.isEmpty(map)){
			return null;
		}
		JSONObject jObj = JSONObject.fromObject(map.get("MEMBER_INFO_"));
		jObj.put("LOGIN_STATE_",map.get("LOGIN_STATE_"));
		return jObj;
	}

	/**
	 * 根据盘口，用戶id和会员账户获取盘口会员信息
	 *
	 * @param handicapId    盘口id
	 * @param appUserId     用戶id
	 * @param memberAccount 会员账户
	 * @return 盘口会员信息
	 */
	public IbmHandicapMemberT findByAccount(String handicapId, String appUserId, String memberAccount) throws Exception {
		String sql = "select * from ibm_handicap_member where HANDICAP_ID_ = ? AND APP_USER_ID_ = ? AND "
				+ " MEMBER_ACCOUNT_ = ? AND STATE_ != 'DEL'";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(appUserId);
		parameterList.add(memberAccount);
		return (IbmHandicapMemberT) super.dao.findObject(IbmHandicapMemberT.class, sql, parameterList);

	}

	/**
	 * 查询该用户所有已保存账号信息
	 *
	 * @param userId     用户id
	 * @param handicapId 盘口id
	 * @return 该用户所有已保存账号信息
	 */
	public List<Map<String, Object>> listAllAccount(String userId, String handicapId) throws Exception {
		String sql = "SELECT ihm.IBM_HANDICAP_MEMBER_ID_,ihm.MEMBER_ACCOUNT_ FROM ibm_handicap_member ihm LEFT JOIN ibm_hm_set ihs ON"
				+ " ihm.IBM_HANDICAP_MEMBER_ID_ = ihs.HANDICAP_MEMBER_ID_ WHERE ihm.APP_USER_ID_ = ? AND ihm.HANDICAP_ID_ = ? AND"
				+ " ihm.STATE_ = ? AND ihs.SAVE_ACCOUNT_ = ? AND LOGIN_STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmTypeEnum.TRUE.name());
		parameterList.add(IbmStateEnum.LOGOUT.name());
		return this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取盘口ID和游戏ID
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameCode         游戏code
	 * @return 盘口ID和游戏ID
	 */
	public Map<String, Object> findByHmIdAndGameCode(String handicapMemberId, String gameCode) throws SQLException {
		String sql =
				"select ihm.HANDICAP_ID_,ihg.GAME_ID_ from ibm_handicap_member ihm LEFT JOIN ibm_handicap_game ihg ON "
						+ "ihm.HANDICAP_ID_ = ihg.HANDICAP_ID_ where ihm.IBM_HANDICAP_MEMBER_ID_ = ? and ihg.GAME_CODE_= ? and ihm.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 是否存在该盘口会员
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 是否存在该盘口会员
	 */
	public boolean hmIsExist(String handicapMemberId) throws SQLException {
		String sql = "SELECT count(IBM_HANDICAP_MEMBER_ID_) FROM ibm_handicap_member WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		String count = super.dao.findString("count(IBM_HANDICAP_MEMBER_ID_)",sql,parameterList);
		return StringTool.notEmpty(count)&&Integer.parseInt(count)>0;
	}

	/**
	 * 查询当前用户所有在登录的会员账号信息
	 *
	 * @param handicapMemberIds 盘口会员id集合
	 * @return 当前用户所有在登录的会员账号信息
	 */
	public List<Map<String, Object>> listLoginAccount(List<String> handicapMemberIds) throws Exception {
		String sql =
				"SELECT ihm.IBM_HANDICAP_MEMBER_ID_ as handicapMemberId,ihm.MEMBER_ACCOUNT_,ihs.LANDED_TIME_,ihs.BET_RECORD_ROWS_,ihs.BET_RATE_T_,"
						+ " ihs.PROFIT_LIMIT_MAX_T_,ihs.PROFIT_LIMIT_MIN_T_,ihs.LOSS_LIMIT_MIN_T_,ihs.RESET_TYPE_,ihs.RESET_PROFIT_MAX_T_,ihs.RESET_LOSS_MIN_T_,"
						+ " ihs.BLAST_STOP_,ihs.SAVE_ACCOUNT_,ihs.BET_MERGER_,ihm.MEMBER_INFO_ FROM ibm_handicap_member ihm"
						+ " LEFT JOIN ibm_hm_set ihs ON ihm.IBM_HANDICAP_MEMBER_ID_ = ihs.HANDICAP_MEMBER_ID_ WHERE"
						+ " ihm.IBM_HANDICAP_MEMBER_ID_ in(";
		List<Object> parameterList = new ArrayList<>();
		StringBuilder stringBuilder = new StringBuilder();
		for (String handicapMemberId : handicapMemberIds) {
			stringBuilder.append("?,");
			parameterList.add(handicapMemberId);
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		sql += stringBuilder + ")";

		return this.dao.findMapList(sql, parameterList);
	}
	/**
	 * 通过盘口会员id查询登录状态
	 * @param handicapMemberId	盘口会员id
	 * @return 盘口会员登录状态
	 */
	public String findLoginState(String handicapMemberId) throws SQLException {
		String sql="select LOGIN_STATE_ from ibm_handicap_member where IBM_HANDICAP_MEMBER_ID_=? and STATE_!=?";
		List<Object> parameterList=new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("LOGIN_STATE_",sql,parameterList);

	}
	/**
	 *
	 * @param handicapMemberId
	 */
	public void updateState(String handicapMemberId) throws SQLException {
		String sql="update ibm_handicap_member set STATE_=? where IBM_HANDICAP_MEMBER_ID_=?";
		List<Object> parameterList=new ArrayList<>(2);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(handicapMemberId);
		super.dao.execute(sql,parameterList);
	}
}
