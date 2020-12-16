package com.ibm.old.v1.cloud.ibm_handicap_member.t.service;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHandicapMemberTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicapMemberT对象数据
	 */
	public String save(IbmHandicapMemberT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_handicap_member的 IBM_HANDICAP_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_handicap_member set state_='DEL' where IBM_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HANDICAP_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_member的 IBM_HANDICAP_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_handicap_member set state_='DEL' where IBM_HANDICAP_MEMBER_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_handicap_member的 IBM_HANDICAP_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_handicap_member where IBM_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HANDICAP_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_member的 IBM_HANDICAP_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_handicap_member where IBM_HANDICAP_MEMBER_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHandicapMemberT实体信息
	 *
	 * @param entity IbmHandicapMemberT实体
	 */
	public void update(IbmHandicapMemberT entity) throws Exception {
		dao.update(entity);
		super.doTransactionPost();
	}

	/**
	 * 根据ibm_handicap_member表主键查找IbmHandicapMemberT实体
	 *
	 * @param id ibm_handicap_member 主键
	 * @return IbmHandicapMemberT实体
	 */
	public IbmHandicapMemberT find(String id) throws Exception {
		return (IbmHandicapMemberT) this.dao.find(IbmHandicapMemberT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_handicap_member where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHandicapMemberT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicapMemberT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap_member where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmHandicapMemberT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHandicapMemberT数据信息
	 *
	 * @return 可用<IbmHandicapMemberT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicapMemberT.class, sql);
	}

	/**
	 * 根据盘口id和会员账户获取盘口会员信息
	 *
	 * @param handicapCode 盘口code
	 * @param accountName  会员账户
	 * @return 盘口会员信息
	 */
	public IbmHandicapMemberT findByAccount(String handicapCode, String accountName) throws Exception {
		String sql = "select * from ibm_handicap_member where HANDICAP_CODE_= ? and MEMBER_ACCOUNT_ = ? and STATE_ != 'DEL'";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapCode);
		parameterList.add(accountName);
		Object obj = super.dao.findObject(IbmHandicapMemberT.class, sql, parameterList);
		return obj == null ? null : (IbmHandicapMemberT) obj;
	}

	/**
	 * @param handicapMemberId 盘口会员ID
	 * @return 盘口ID
	 * @Description: 获取盘口ID
	 * <p>
	 * 参数说明
	 */
	public String findHandicapId(String handicapMemberId) throws SQLException {
		String sql = "SELECT HANDICAP_ID_ FROM ibm_handicap_member WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("HANDICAP_ID_", sql, parameterList);
	}

	/**
	 * 更新登录信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param processResult    处理结果
	 * @return 登陆成功 true
	 */
	public boolean updateLogin(String handicapMemberId, Object processResult) throws Exception {
		super.doTransactionPost();
		//更新盘口会员信息
		String sql = "UPDATE ibm_handicap_member SET  MEMBER_INFO_ = ?,LOGIN_STATE_ = ?,FREQUENCY_ = FREQUENCY_ +1, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(6);

		parameterList.add(processResult.toString());
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		int col = super.dao.execute(sql, parameterList);
		if (col != 1) {
			IJdbcTool jdbcToolLocal = JdbcThreadLocal.findJdbcToolThreadLocal().get();
			super.transactionRoll(jdbcToolLocal);
			//更新条数不对
			return false;
		}
		return true;
	}

	/**
	 * 更新登录信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param nowTime          更新时间
	 */
	public void updateLogout(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_handicap_member SET LOGIN_STATE_ = ?, UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ? WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND LOGIN_STATE_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.LOGOUT.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 更新登录信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param processResult    更新时间
	 */
	public void updateLogout(String handicapMemberId, Object processResult) throws SQLException {
		String sql = "UPDATE ibm_handicap_member SET  MEMBER_INFO_ = ?,LOGIN_STATE_ = ?, UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ? WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND LOGIN_STATE_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(processResult.toString());
		parameterList.add(IbmStateEnum.LOGOUT.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 更新登录信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param processResult    处理结果
	 * @return 登陆失败 true
	 */
	public boolean updateMemberInfo(String handicapMemberId, Object processResult) throws SQLException {
		//更新盘口会员信息
		String sql = "UPDATE ibm_handicap_member SET MEMBER_INFO_ = ?,FREQUENCY_ = FREQUENCY_ +1, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(5);

		parameterList.add(processResult.toString());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		int col = super.dao.execute(sql, parameterList);
		if (col != 1) {
			IJdbcTool jdbcToolLocal = JdbcThreadLocal.findJdbcToolThreadLocal().get();
			super.transactionRoll(jdbcToolLocal);
			//更新条数不对
			return false;
		}

		return true;
	}

	/**
	 * @param handicapMemberId 盘口会员ID
	 * @return 盘口ID和用户id
	 * @Description: 获取盘口ID
	 * <p>
	 * 参数说明
	 */
	public Map<String, Object> findIds(String handicapMemberId) throws SQLException {
		String sql = "SELECT HANDICAP_ID_,APP_USER_ID_ FROM ibm_handicap_member WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 更新余额信息
	 *
	 * @param existHmId  已存在盘口会员id
	 * @param jsonObject 余额信息
	 */
	public void updateMemberInfoByExistHmId(String existHmId, JSONObject jsonObject) throws Exception {
		String sql = "select HANDICAP_MEMBER_ID_ from ibm_cloud_client_hm where CLIENT_EXIST_HM_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.DEL.name());
		String handicapMemberId = super.dao.findString("HANDICAP_MEMBER_ID_", sql, parameterList);
		if (StringTool.isEmpty(handicapMemberId)) {
			return;
		}
		Object profit = jsonObject.remove("PROFIT");
		parameterList.clear();
		sql = "UPDATE ibm_handicap_member SET MEMBER_INFO_ =?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? ";

		parameterList.add(jsonObject.toString());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);

		if (StringTool.notEmpty(profit)) {
			parameterList.clear();
			sql = "update ibm_profit set HANDICAP_PROFIT_T_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE HANDICAP_MEMBER_ID_=? and STATE_=?";

			parameterList.add(NumberTool.longValueT(profit.toString().replace(",", "")));
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(handicapMemberId);
			parameterList.add(IbmStateEnum.OPEN.name());
			super.dao.execute(sql, parameterList);
		}

	}

	/**
	 * 获取在线会员账号
	 *
	 * @param onlineMemberIds 在线会员id集合
	 * @return 在线会员账号
	 */
	public String findAccountByIds(String[] onlineMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT MEMBER_ACCOUNT_ FROM ibm_handicap_member WHERE IBM_HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>(onlineMemberIds.length);
		for (String onlineMemberId : onlineMemberIds) {
			sql.append("?,");
			parameterList.add(onlineMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<String> memberAccountList = super.dao.findStringList("MEMBER_ACCOUNT_", sql.toString(), parameterList);
		return StringUtils.join(memberAccountList, ",");
	}

	/**
	 * 通过盘口code获取盘口会员ids
	 *
	 * @param handicapCode 盘口code
	 * @return 盘口会员ids
	 */
	public List<String> findHandicapMemberIds(String handicapCode) throws SQLException {
		String sql =
				"SELECT IBM_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_ FROM ibm_handicap_member where LOGIN_STATE_=? "
						+ " AND STATE_!=? AND HANDICAP_CODE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(handicapCode);
		return super.dao.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);
	}
	/**
	 * 通过用户id获取盘口会员ids
	 *
	 * @param userId 用户id
	 * @return 盘口会员ids
	 */
	public List<String> listIdByUserId(String userId) throws SQLException {
		String sql = "select IBM_HANDICAP_MEMBER_ID_ from ibm_handicap_member where APP_USER_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findStringList("IBM_HANDICAP_MEMBER_ID_", sql, parameterList);
	}

	/**
	 * 获取盘口id和登陆状态
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 盘口id和登陆状态
	 */
	public Map<String, Object> findHandicapIdAndLoginState(String handicapMemberId) throws SQLException {
		String sql = "SELECT HANDICAP_ID_,LOGIN_STATE_ FROM ibm_handicap_member WHERE IBM_HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	public List<Map<String, Object>> listId() throws SQLException {
		String sql = "SELECT IBM_HANDICAP_MEMBER_ID_,HANDICAP_ID_ FROM ibm_handicap_member ";
		return super.dao.findMapList(sql, null);
	}

	/**
	 * 获取盘口会员的游戏状态
	 * @param handicapMemberId 盘口会员id
	 * @return 盘口会员的游戏状态
	 */
	public List<Map<String, Object>> listGameInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT ihg.GAME_NAME_,ihg.GAME_CODE_ FROM `ibm_handicap_member` ihm LEFT JOIN ibm_handicap_game"
				+ " ihg on ihm.HANDICAP_ID_ = ihg.HANDICAP_ID_ where ihm.IBM_HANDICAP_MEMBER_ID_ = ? "
				+ " AND ihm.STATE_ != ? and ihg.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}
	/**
	 * 获取盘口会员list
	 * @param userId			用户id
	 * @return
	 */
	public List<String> findByUserId(String userId) throws SQLException {
		String sql="select IBM_HANDICAP_MEMBER_ID_ from ibm_handicap_member where APP_USER_ID_=? and STATE_!='DEL'";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		return super.dao.findStringList("IBM_HANDICAP_MEMBER_ID_",sql,parameterList);
	}
	/**
	 * 通过盘口会员id删除用户信息
	 * @param handicapMemberList			盘口会员list
	 */
	public void delById(List<String> handicapMemberList) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibm_handicap_member set STATE_='DEL',UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for(String handicapMemberId:handicapMemberList){
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(),parameterList);
	}
}
