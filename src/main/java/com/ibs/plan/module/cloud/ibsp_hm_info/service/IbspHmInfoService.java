package com.ibs.plan.module.cloud.ibsp_hm_info.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_hm_info.entity.IbspHmInfo;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP_盘口会员信息 服务类
 *
 * @author Robot
 */
public class IbspHmInfoService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员信息 对象数据
	 *
	 * @param entity IbspHmInfo对象数据
	 */
	public String save(IbspHmInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_hm_info 的 IBSP_HM_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_hm_info set state_='DEL' where IBSP_HM_INFO_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_hm_info 的 IBSP_HM_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_hm_info set state_='DEL' where IBSP_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_hm_info  的 IBSP_HM_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_hm_info where IBSP_HM_INFO_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_hm_info 的 IBSP_HM_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_hm_info where IBSP_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHmInfo实体信息
	 *
	 * @param entity IBSP_盘口会员信息 实体
	 */
	public void update(IbspHmInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_hm_info表主键查找 IBSP_盘口会员信息 实体
	 *
	 * @param id ibsp_hm_info 主键
	 * @return IBSP_盘口会员信息 实体
	 */
	public IbspHmInfo find(String id) throws Exception {
		return dao.find(IbspHmInfo.class, id);
	}

	/**
	 * 盘口会员登出
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param nowTime          当前时间
	 */
	public void updateLogout(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "update ibsp_hm_info set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,LOGIN_STATE_ = ? where "
				+ " HANDICAP_MEMBER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbsStateEnum.LOGOUT.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取在线的盘口会员主键数组
	 *
	 * @param appUserId 用户主键
	 * @return 盘口会员主键数组
	 */
	public List<String> listHostingHmIdByUserId(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ FROM ibsp_hm_info  where APP_USER_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.LOGIN.name());
		return super.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);
	}

	/**
	 * 获取会员在线信息
	 *
	 * @param handicapMemberIds 盘口会员id数组
	 * @return 会员在线信息
	 */
	public List<Map<String, Object>> listOnlineInfo(String[] handicapMemberIds) throws SQLException {
		String sql = "SELECT ihi.HANDICAP_MEMBER_ID_,ihm.MEMBER_ACCOUNT_,HANDICAP_ITEM_ID_ FROM ibsp_hm_info ihi "
				+ " LEFT JOIN ibsp_handicap_member ihm on ihi.HANDICAP_MEMBER_ID_ = ihm.IBSP_HANDICAP_MEMBER_ID_ "
				+ " where ihi.LOGIN_STATE_ = ? and ihi.STATE_ = ? and ihm.STATE_ = ? and ihi.HANDICAP_MEMBER_ID_ in (";
		List<Object> parameterList = new ArrayList<>(handicapMemberIds.length + 2);
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		StringBuilder sqlBuilder = new StringBuilder();
		for (String handicapMemberId : handicapMemberIds) {
			sqlBuilder.append("?,");
			parameterList.add(handicapMemberId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(")");
		sql += sqlBuilder.toString();
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取 盘口会员基本信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 基本信息
	 */
	public Map<String, Object> findShowInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT MEMBER_ACCOUNT_,HANDICAP_PROFIT_T_,AMOUNT_T_,LOGIN_STATE_ FROM ibsp_hm_info where "
				+ " HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 获取主键
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return
	 */
	public String findIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "select IBSP_HM_INFO_ID_ from ibsp_hm_info where HANDICAP_MEMBER_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findString("IBSP_HM_INFO_ID_", sql, parameterList);
	}
	/**
	 * 通过id修改盘口会员信息
	 *
	 * @param hmInfoId      盘口会员信息id
	 * @param memberAccount 会员账户
	 * @param profitAmount  盘口盈亏
	 * @param usedAmount    使用金额
	 * @param nowTime       当前时间
	 */
	public void updateById(String hmInfoId, String memberAccount, long profitAmount, long usedAmount, Date nowTime)
			throws SQLException {
		String sql = "update ibsp_hm_info set MEMBER_ACCOUNT_=?,HANDICAP_PROFIT_T_=?,AMOUNT_T_=?,UPDATE_TIME_=?, "
				+ " UPDATE_TIME_LONG_=?,LOGIN_STATE_=? where IBSP_HM_INFO_ID_=?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(memberAccount);
		parameterList.add(profitAmount);
		parameterList.add(usedAmount);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(hmInfoId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 该账号是否在某盘口已经登录
	 *
	 * @param handicapId    账户所在盘口
	 * @param memberAccount 盘口账号
	 * @return 登录 true
	 */
	public boolean isLogin(String handicapId, String memberAccount) throws SQLException {
		String sql = "SELECT IBSP_HM_INFO_ID_ FROM ibsp_hm_info ihi LEFT JOIN ibsp_handicap_member ihm ON "
				+ " ihi.HANDICAP_MEMBER_ID_ = ihm.IBSP_HANDICAP_MEMBER_ID_ WHERE ihm.HANDICAP_ID_ = ? "
				+ " AND ihm.MEMBER_ACCOUNT_ = ? AND ihi.LOGIN_STATE_ = ? and ihi.STATE_ = ? and ihm.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(handicapId);
		parameterList.add(memberAccount);
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.findMapList(sql, parameterList));
	}

	/**
	 * 获取登录状态
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 登录状态
	 */
	public String findLoginState(String handicapMemberId) throws SQLException {
		String sql = "select LOGIN_STATE_ from ibsp_hm_info where HANDICAP_MEMBER_ID_=? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("LOGIN_STATE_", sql, parameterList);
	}

	/**
	 * 更新校验结果信息
	 * @param handicapMemberId 会员主键
	 * @param checkInfo 校验结果信息
	 * @param nowTime 更新时间
	 * @return 更新条数
	 */
	public int updateCheckInfo(String handicapMemberId, JSONObject checkInfo, Date nowTime) throws SQLException {
		String sql = "update ibsp_hm_info set UPDATE_TIME_=?,UPDATE_TIME_LONG_ = ?,DESC_ = ? ";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("定时校验结果："+checkInfo.toString());
		if (checkInfo.containsKey("usedQuota")) {
			sql += " ,MEMBER_ACCOUNT_ = ?,HANDICAP_PROFIT_T_ = ?,AMOUNT_T_ = ? ";
			parameterList.add(checkInfo.get("memberAccount"));
			parameterList.add(NumberTool.longValueT(checkInfo.get("profitAmount")));
			parameterList.add(NumberTool.longValueT(checkInfo.get("usedQuota")));
		}else if (checkInfo.containsKey("code")){
			sql += " ,MEMBER_INFO_ = ?,MEMBER_INFO_CODE_ = ? ";
			parameterList.add(checkInfo.get("message"));
			parameterList.add(checkInfo.get("code"));
		}
		sql += "where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.execute(sql, parameterList);
	}
	/**
	 * 获取会员信息
	 *
	 * @param userName      用户名
	 * @param memberAccount 会员名
	 * @param handicapCode  盘口code
	 * @param onlineState   在线状态
	 * @param pageIndex     页数
	 * @param pageSize      条数
	 * @return
	 */
	public PageCoreBean<Map<String, Object>> findShow(String userName, String memberAccount, String handicapCode,
													  String onlineState, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount =
				"SELECT count(ihi.IBSP_HM_INFO_ID_) FROM ibsp_hm_info ihi LEFT JOIN ibsp_user iu ON ihi.APP_USER_ID_ = iu.APP_USER_ID_"
						+ " LEFT JOIN ibsp_handicap_member ihm ON ihi.HANDICAP_MEMBER_ID_ = ihm.IBSP_HANDICAP_MEMBER_ID_ "
						+ " LEFT JOIN ibsp_handicap_item ih ON ihm.HANDICAP_ITEM_ID_ = ih.IBSP_HANDICAP_ITEM_ID_"
						+ " where iu.STATE_!=? and ihm.STATE_!=?";
		String sql =
				"SELECT ihi.HANDICAP_MEMBER_ID_,iu.NICKNAME_,ih.HANDICAP_CODE_,ihi.MEMBER_ACCOUNT_,ih.HANDICAP_URL_,"
						+ "ih.HANDICAP_CAPTCHA_,ihi.STATE_ FROM ibsp_hm_info ihi LEFT JOIN ibsp_user iu ON ihi.APP_USER_ID_ = iu.APP_USER_ID_"
						+ " LEFT JOIN ibsp_handicap_member ihm ON ihi.HANDICAP_MEMBER_ID_ = ihm.IBSP_HANDICAP_MEMBER_ID_"
						+ " LEFT JOIN ibsp_handicap_item ih ON ihm.HANDICAP_ITEM_ID_ = ih.IBSP_HANDICAP_ITEM_ID_ where iu.STATE_!=? and ihm.STATE_!=?";
		ArrayList<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(IbsStateEnum.DEL.name());
		StringBuilder sqlPlus = new StringBuilder();
		if (StringTool.notEmpty(userName)) {
			sqlPlus.append(" and iu.NICKNAME_ like ?");
			userName = "%" + userName + "%";
			parameterList.add(userName);
		}
		if (StringTool.notEmpty(memberAccount)) {
			sqlPlus.append(" and ihi.MEMBER_ACCOUNT_ like ?");
			memberAccount = "%" + memberAccount + "%";
			parameterList.add(memberAccount);
		}
		if (StringTool.notEmpty(handicapCode)) {
			sqlPlus.append(" and ihm.HANDICAP_CODE_=?");
			parameterList.add(handicapCode);
		}
		if (StringTool.notEmpty(onlineState)) {
			sqlPlus.append(" and ihi.STATE_=?");
			parameterList.add(onlineState);
		}
		sqlPlus.append(" order by ihi.STATE_,iu.NICKNAME_");
		sqlCount += sqlPlus;
		sql += sqlPlus;
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}
}
