package com.ibm.follow.servlet.cloud.ibm_hm_info.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_info.entity.IbmHmInfo;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHmInfoService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHmInfo对象数据
	 */
	public String save(IbmHmInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_hm_info的 IBM_HM_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_info set state_='DEL' where IBM_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_info的 IBM_HM_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_hm_info set state_='DEL' where IBM_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_hm_info的 IBM_HM_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_info where IBM_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_info的 IBM_HM_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_hm_info where IBM_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHmInfo实体信息
	 *
	 * @param entity IbmHmInfo实体
	 */
	public void update(IbmHmInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_hm_info表主键查找IbmHmInfo实体
	 *
	 * @param id ibm_hm_info 主键
	 * @return IbmHmInfo实体
	 */
	public IbmHmInfo find(String id) throws Exception {
		return (IbmHmInfo) this.dao.find(IbmHmInfo.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_hm_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmInfo数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHmInfo数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHmInfo.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmInfo数据信息
	 *
	 * @return 可用<IbmHmInfo>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmInfo.class, sql);
	}

	/**
	 * 获取会员在线信息
	 *
	 * @param handicapMemberIds 盘口会员id数组
	 * @return 会员在线信息
	 */
	public List<Map<String, Object>> listOnlineInfoByHmIds(String[] handicapMemberIds) throws SQLException {
		String sql = "SELECT ihi.HANDICAP_MEMBER_ID_,ihm.MEMBER_ACCOUNT_,HANDICAP_ITEM_ID_ FROM ibm_hm_info ihi "
				+ " LEFT JOIN ibm_handicap_member ihm on ihi.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_ "
				+ " where ihi.STATE_ = ? and ihm.STATE_ = ? and ihi.HANDICAP_MEMBER_ID_ in (";
		List<Object> parameterList = new ArrayList<>(handicapMemberIds.length + 2);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
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
	 * 获取会员离线信息
	 *
	 * @param userId      用户id
	 * @param handicapId  盘口id
	 * @param onlineHmIds 在线 盘口会员id数组
	 * @return 会员离线信息
	 */
	public List<Map<String, Object>> listOfflineInfo(String userId, String handicapId, Set<Object> onlineHmIds)
			throws SQLException {
		String sql = "SELECT IBM_HANDICAP_MEMBER_ID_ AS HANDICAP_MEMBER_ID_, MEMBER_ACCOUNT_,HANDICAP_ITEM_ID_ "
				+ " FROM ibm_handicap_member WHERE APP_USER_ID_ = ? AND HANDICAP_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		if (ContainerTool.notEmpty(onlineHmIds)) {
			StringBuilder sqlBuilder = new StringBuilder(" and IBM_HANDICAP_MEMBER_ID_ not in ( ");
			for (Object handicapMemberId : onlineHmIds) {
				sqlBuilder.append("?,");
				parameterList.add(handicapMemberId);
			}
			sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(")");
			sql += sqlBuilder.toString();
		}
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取 盘口会员基本信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 基本信息
	 */
	public Map<String, Object> findShowInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT MEMBER_ACCOUNT_,HANDICAP_PROFIT_T_,AMOUNT_T_,STATE_ FROM `ibm_hm_info` where "
				+ " HANDICAP_MEMBER_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 盘口会员登出
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param nowTime          当前时间
	 */
	public void updateLogout(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "update ibm_hm_info set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where HANDICAP_MEMBER_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.LOGOUT.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 通过盘口会员id获取主键
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return
	 */
	public String findIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "select IBM_HM_INFO_ID_ from ibm_hm_info where HANDICAP_MEMBER_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("IBM_HM_INFO_ID_", sql, parameterList);
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
		String sql = "update ibm_hm_info set MEMBER_ACCOUNT_=?,HANDICAP_PROFIT_T_=?,AMOUNT_T_=?,UPDATE_TIME_=?, "
				+ " UPDATE_TIME_LONG_=?,STATE_=? where IBM_HM_INFO_ID_=?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(memberAccount);
		parameterList.add(profitAmount);
		parameterList.add(usedAmount);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(hmInfoId);
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 获取登录状态
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 登录状态
	 */
	public String findLoginState(String handicapMemberId) throws SQLException {
		String sql = "select STATE_ from ibm_hm_info where HANDICAP_MEMBER_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findString("STATE_", sql, parameterList);
	}

	/**
	 * 获取在线的盘口会员主键数组
	 *
	 * @param appUserId 用户主键
	 * @return 盘口会员主键数组
	 */
	public List<String> listHostingHmIdByUserId(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ FROM ibm_hm_info  where APP_USER_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.LOGIN.name());
		return super.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);

	}
	/**
	 * 修改用户余额信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param usedQuota        余额
	 * @param profitAmount     盈亏信息
	 */
	public int updateAmountInfo(String handicapMemberId, String usedQuota, String profitAmount) throws SQLException {
		String sql = "update ibm_hm_info set HANDICAP_PROFIT_T_=?,AMOUNT_T_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? "
				+ "where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(NumberTool.longValueT(profitAmount));
		parameterList.add(NumberTool.longValueT(usedQuota));
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.LOGIN.name());
		return super.dao.execute(sql, parameterList);
	}

	/**
	 * 更新校验信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param code             会员信息码
	 * @param message          会员信息
	 */
	public void updateMemberInfo(String handicapMemberId, String code, String message) throws SQLException {
		String sql = "update ibm_hm_info set MEMBER_INFO_=?,MEMBER_INFO_CODE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? "
				+ "where HANDICAP_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(message);
		parameterList.add(code);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 该账号是否在某盘口已经登录
	 *
	 * @param handicapId    账户所在盘口
	 * @param memberAccount 盘口zhangh
	 * @return 登录 true
	 */
	public boolean isLogin(String handicapId, String memberAccount) throws SQLException {
		String sql = "SELECT IBM_HM_INFO_ID_ FROM `ibm_hm_info` ihi LEFT JOIN ibm_handicap_member ihm ON "
				+ " ihi.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_ WHERE ihm.HANDICAP_ID_ = ? "
				+ " AND ihm.MEMBER_ACCOUNT_ = ? AND ihi.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(memberAccount);
		parameterList.add(IbmStateEnum.LOGIN.name());
		return ContainerTool.notEmpty(super.findMapList(sql, parameterList));

	}

	public void delByAppUserId(String appUserId) throws SQLException {
		String sql = "delete from ibm_hm_info where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	public void updateStateByAppUserId(String appUserId) throws SQLException {
		String sql = "UPDATE ibm_hm_info SET STATE_ = ? where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	/**
	 * 获取正在托管的盘口会员主键
	 *
	 * @param handicapId 盘口主键
	 * @return 盘口会员主键列表
	 */
	public List<String> listHostingHmId(String handicapId) throws SQLException {
		return listHostingHmId(handicapId, null);
	}

	/**
	 * 获取正在托管的盘口会员主键
	 *
	 * @param handicapId 盘口主键
	 * @param userIds    用户主键列表
	 * @return 盘口会员主键列表
	 */
	public List<String> listHostingHmId(String handicapId, List<String> userIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT ihi.HANDICAP_MEMBER_ID_ as key_ FROM `ibm_hm_info` ihi "
				+ "LEFT JOIN ibm_handicap_member ihm ON ihi.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_ WHERE "
				+ " ihm.HANDICAP_ID_ = ? and ihm.STATE_ != ? AND ihi.STATE_ = ?");
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.LOGIN.name());
		if (ContainerTool.notEmpty(userIds)) {
			sql.append("AND ihi.APP_USER_ID_ IN (");
			for (String userId : userIds) {
				sql.append("?,");
				parameterList.add(userId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}
		return super.findStringList(sql.toString(), parameterList);
	}

	/**
	 * 获取用户登录的盘口会员主键数组
	 *
	 * @param appUserId 用户主键
	 * @return 盘口会员主键数组
	 */
	public Map<String, Map<String, Object>> listHmIdByUserId(String appUserId) throws SQLException {
		String sql = "SELECT ich.HANDICAP_MEMBER_ID_,ich.CLIENT_CODE_,ich.EXIST_HM_ID_ FROM ibm_client_hm ich"
				+ " LEFT JOIN ibm_hm_info ihi ON ich.HANDICAP_MEMBER_ID_=ihi.HANDICAP_MEMBER_ID_"
				+ " WHERE ihi.APP_USER_ID_=? AND ihi.STATE_=? AND ich.STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findKeyMap(sql,parameterList,"HANDICAP_MEMBER_ID_");
	}

	/**
	 * 获取代理IDs 并删除该信息
	 *
	 * @param userIds
	 * @return
	 * @throws SQLException
	 */
	public List<String> listHmIdByUserIds(List<String> userIds) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ from ibm_hm_info where APP_USER_ID_ in( ";
		List<Object> parameterList = new ArrayList<>(userIds.size());
		StringBuilder sbStr = new StringBuilder();
		for (String uid : userIds) {
			sbStr.append("?,");
			parameterList.add(uid);
		}
		sbStr.deleteCharAt(sbStr.lastIndexOf(",")).append(")");
		sql = sql + sbStr.toString();
		List hmids = dao.findStringList("HANDICAP_MEMBER_ID_", sql.toString(), parameterList);
		String delSql = "DELETE FROM ibm_hm_info where APP_USER_ID_ in(";
		delSql = delSql + sbStr.toString();
		dao.execute(delSql, parameterList);
		return hmids;

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
				"SELECT count(ihi.IBM_HM_INFO_ID_) FROM ibm_hm_info ihi LEFT JOIN app_user au ON ihi.APP_USER_ID_ = au.APP_USER_ID_"
						+ " LEFT JOIN ibm_handicap_member ihm ON ihi.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_"
						+ " where au.STATE_!=? and ihm.STATE_!=?";
		String sql =
				"SELECT ihi.HANDICAP_MEMBER_ID_,au.APP_USER_NAME_,ihm.HANDICAP_CODE_,ihi.MEMBER_ACCOUNT_,ih.HANDICAP_URL_,"
						+ "ih.HANDICAP_CAPTCHA_,ihi.STATE_ FROM ibm_hm_info ihi LEFT JOIN app_user au ON ihi.APP_USER_ID_ = au.APP_USER_ID_"
						+ " LEFT JOIN ibm_handicap_member ihm ON ihi.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_"
						+ " LEFT JOIN ibm_handicap_item ih ON ihm.HANDICAP_ITEM_ID_ = ih.IBM_HANDICAP_ITEM_ID_ where au.STATE_!=? and ihm.STATE_!=?";
		ArrayList<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		StringBuilder sqlPlus = new StringBuilder();
		if (StringTool.notEmpty(userName)) {
			sqlPlus.append(" and au.APP_USER_NAME_ like ?");
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
		sqlPlus.append(" order by ihi.STATE_,au.APP_USER_NAME_");
		sqlCount += sqlPlus;
		sql += sqlPlus;
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}
}
