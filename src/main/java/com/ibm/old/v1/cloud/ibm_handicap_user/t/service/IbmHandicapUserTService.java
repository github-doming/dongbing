package com.ibm.old.v1.cloud.ibm_handicap_user.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.entity.IbmHandicapUserT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHandicapUserTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicapUserT对象数据
	 */
	public String save(IbmHandicapUserT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_handicap_user的 IBM_HANDICAP_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_handicap_user set state_='DEL' where IBM_HANDICAP_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HANDICAP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_user的 IBM_HANDICAP_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_handicap_user set state_='DEL' where IBM_HANDICAP_USER_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_handicap_user的 IBM_HANDICAP_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_handicap_user where IBM_HANDICAP_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HANDICAP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_user的 IBM_HANDICAP_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_handicap_user where IBM_HANDICAP_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHandicapUserT实体信息
	 *
	 * @param entity IbmHandicapUserT实体
	 */
	public void update(IbmHandicapUserT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_handicap_user表主键查找IbmHandicapUserT实体
	 *
	 * @param id ibm_handicap_user 主键
	 * @return IbmHandicapUserT实体
	 */
	public IbmHandicapUserT find(String id) throws Exception {
		return (IbmHandicapUserT) this.dao.find(IbmHandicapUserT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_handicap_user where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_user  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHandicapUserT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicapUserT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap_user where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_user  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmHandicapUserT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHandicapUserT数据信息
	 *
	 * @return 可用<IbmHandicapUserT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicapUserT.class, sql);
	}
	/**
	 * 通过用户id和盘口id查找盘口用户
	 *
	 * @param userId
	 * @param handicapId
	 * @return
	 * @throws Exception
	 */
	public IbmHandicapUserT find(String userId, String handicapId) throws Exception {
		String sql = "select * from ibm_handicap_user where APP_USER_ID_=? and HANDICAP_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return (IbmHandicapUserT) dao.findObject(IbmHandicapUserT.class, sql, parameterList);
	}

	/**
	 * 根据用户code 盘口code获取盘口用户信息
	 *
	 * @param userCode     用户code
	 * @param handicapCode 盘口code
	 * @return 盘口用户信息
	 */
	public IbmHandicapUserT findByCode(String handicapCode, String userCode) throws Exception {
		String sql = "select * from ibm_handicap_user ihu LEFT JOIN app_user au on ihu.APP_USER_ID_ = au.APP_USER_ID_ "
				+ " where  ihu.HANDICAP_CODE_ = ? and au.APP_USER_CODE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapCode);
		parameterList.add(userCode);
		Object obj = super.dao.findObject(IbmHandicapUserT.class, sql, parameterList);
		return obj == null ? null : (IbmHandicapUserT) obj;
	}

	/**
	 * @param handicaps 盘口信息
	 * @param userId    用户ID
	 * @Description: 初始化用户盘口
	 */
	public void saveBatch(List<Map<String, Object>> handicaps, String userId) throws SQLException {
		Date nowTime = new Date();
		StringBuilder sql = new StringBuilder(
				"insert into ibm_handicap_user(IBM_HANDICAP_USER_ID_,APP_USER_ID_,HANDICAP_ID_,"
						+ "HANDICAP_CODE_,HANDICAP_NAME_,ONLINE_MEMBERS_COUNT_,ONLINE_NUMBER_MAX_,FREQUENCY_,CREATE_TIME_,"
						+ "CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_) VALUES");
		List<Object> parameterList = new ArrayList<>();
		for (Map<String, Object> handicap : handicaps) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(UUID.randomUUID().toString().replace("-", ""));
			parameterList.add(userId);
			parameterList.add(handicap.get("IBM_HANDICAP_ID_").toString());
			parameterList.add(handicap.get("HANDICAP_CODE_").toString());
			parameterList.add(handicap.get("HANDICAP_NAME_").toString());
			parameterList.add(0);
			parameterList.add(10);
			parameterList.add(0);
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
		}
		sql.deleteCharAt(sql.length() - 1);
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * @param handicapId 盘口ID
	 * @param userId     用户ID
	 * @return 用户盘口信息
	 * @Description: 根据盘口ID和用户ID查询用户盘口
	 * <p>
	 * 参数说明
	 */
	public IbmHandicapUserT findHandicap(String handicapId, String userId) throws Exception {
		String sql = "select * from ibm_handicap_user where HANDICAP_ID_ = ? and APP_USER_ID_ = ? and STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapId);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return (IbmHandicapUserT) super.dao.findObject(IbmHandicapUserT.class, sql, parameterList);
	}

	/**
	 * @param userId 用户ID
	 * @return 盘口ID集合
	 * @Description: 根据用户ID查询盘口ID
	 * <p>
	 * 参数说明
	 */
	public List<String> listHandicapByUserId(String userId) throws SQLException {
		String sql = "SELECT HANDICAP_ID_ from ibm_handicap_user where APP_USER_ID_ = ? and state_ !=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findStringList("HANDICAP_ID_", sql, parameterList);
	}

	/**
	 * @param handicapId
	 * @Description: 通过盘口ID逻辑删除
	 * <p>
	 * 参数说明
	 */
	public void delByHandicapId(String handicapId, String msg ) throws SQLException {
		String sql = "update ibm_handicap_user set STATE_=?,DESC_=?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?,DESC_ = ? where HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add("通过盘口ID逻辑删除.移除分配资源中不存在的盘口id");
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(msg);
		parameters.add(handicapId);
		dao.execute(sql, parameters);
	}

	/**
	 * 更新在线盘口会员信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 更新结果
	 */
	public boolean updateLogin(String handicapMemberId) throws SQLException {
		String sql = "SELECT ihu.ONLINE_MEMBERS_IDS_, ihu.IBM_HANDICAP_USER_ID_ " + " FROM `ibm_handicap_user` ihu "
				+ " LEFT JOIN ibm_handicap_member ihm ON ihu.IBM_HANDICAP_USER_ID_ = ihm.HANDICAP_USER_ID_"
				+ " WHERE ihm.IBM_HANDICAP_MEMBER_ID_ = ? AND ihu.ONLINE_MEMBERS_COUNT_ + 1 <= ihu.ONLINE_NUMBER_MAX_ "
				+ " AND ihu.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		Map<String, Object> info = super.dao.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return false;
		}
		String onlineMembersIds;
		if (StringTool.isEmpty(info.get("ONLINE_MEMBERS_IDS_"))) {
			onlineMembersIds = "";
		} else {
			onlineMembersIds = info.get("ONLINE_MEMBERS_IDS_").toString();
		}

		Set<String> onlineMembersIdSet = new HashSet<>(Arrays.asList(onlineMembersIds.split(",")));
		onlineMembersIdSet.add(handicapMemberId);
		updateOnlineMember(onlineMembersIdSet, info.get("IBM_HANDICAP_USER_ID_").toString(), new Date(), "login",this.getClass().getName());

		return true;
	}

	/**
	 * 更新在线盘口会员信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param nowTime          更新时间
	 */
	public void updateLogout(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "SELECT ihu.ONLINE_MEMBERS_IDS_, ihu.IBM_HANDICAP_USER_ID_ FROM `ibm_handicap_user` ihu "
				+ " LEFT JOIN ibm_handicap_member ihm ON ihu.IBM_HANDICAP_USER_ID_ = ihm.HANDICAP_USER_ID_ "
				+ " WHERE ihm.IBM_HANDICAP_MEMBER_ID_ = ? AND ihm.LOGIN_STATE_ = ? AND ihu.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, Object> info = super.dao.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return;
		}
		String onlineMembersIds = info.get("ONLINE_MEMBERS_IDS_").toString();

		//在线会员id集合
		Set<String> onlineMembersIdSet = new HashSet<>(Arrays.asList(onlineMembersIds.split(",")));
		if (ContainerTool.isEmpty(onlineMembersIdSet) || !onlineMembersIdSet.contains(handicapMemberId)) {
			return;
		}
		// 移除在线会员id
		onlineMembersIdSet.remove(handicapMemberId);
		// 更新
		updateOnlineMember(onlineMembersIdSet, info.get("IBM_HANDICAP_USER_ID_").toString(), nowTime, "logout",this.getClass().getName());
	}

	/**
	 * 更新在线盘口会员id
	 *
	 * @param onlineMembersIdSet 在线会员列表
	 * @param handicapUserId     盘口用户id
	 * @param nowTime            更新时间
	 */
	private void updateOnlineMember(Set<String> onlineMembersIdSet, String handicapUserId, Date nowTime, String method,String className)
			throws SQLException {
		StringBuilder sql = new StringBuilder(
				"UPDATE `ibm_handicap_user` SET DESC_=?,ONLINE_MEMBERS_IDS_ = ?, ONLINE_MEMBERS_COUNT_ = ?"
						+ ",UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? ");
		List<Object> parameterList = new ArrayList<>(5);
		if ("login".equals(method)) {
			sql.append(",FREQUENCY_ = FREQUENCY_ + 1 WHERE IBM_HANDICAP_USER_ID_ = ?");
		} else {
			sql.append("WHERE IBM_HANDICAP_USER_ID_ = ?");
		}
		parameterList.add(className+"更新在线盘口会员id");
		parameterList.add(StringUtils.join(onlineMembersIdSet, ","));
		parameterList.add(onlineMembersIdSet.size() - 1);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(handicapUserId);
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 后台管理分页
	 *
	 * @param handicapName 盘口名称
	 * @return 分页信息
	 */
	public PageCoreBean find(String handicapName, String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws SQLException {
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append("SELECT count(IBM_HANDICAP_USER_ID_) FROM ibm_handicap_user where state_!='DEL' ");
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT IBM_HANDICAP_USER_ID_,(SELECT APP_USER_NAME_ FROM app_user WHERE APP_USER_ID_ = ihu.APP_USER_ID_) APP_USER_NAME,HANDICAP_NAME_,HANDICAP_CODE_,ONLINE_MEMBERS_IDS_,ONLINE_MEMBERS_COUNT_,ONLINE_NUMBER_MAX_,FREQUENCY_ FROM ibm_handicap_user ihu where state_!='DEL' ");
		if (!StringUtil.isBlank(handicapName)) {
			ArrayList<Object> parameters = new ArrayList<>();
			ArrayList<Object> parametersCount = new ArrayList<>();
			parameters.add("%" + handicapName + "%");
			parametersCount.add("%" + handicapName + "%");
			sql.append("and HANDICAP_NAME_ like ? ");
			sqlCount.append("and HANDICAP_NAME_ like ? ");
			if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
				sql.append("order by UPDATE_TIME_LONG_ desc");
			} else {
				sql.append("order by ").append(sortFieldName).append(" ").append(sortOrderName);
			}
			return dao.page(sql.toString(), parameters, pageIndex, pageSize, sqlCount.toString(), parametersCount);
		} else {
			if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
				sql.append("order by UPDATE_TIME_LONG_ desc");
			} else {
				sql.append("order by ").append(sortFieldName).append(" ").append(sortOrderName);
			}
			return dao.page(sql.toString(), null, pageIndex, pageSize, sqlCount.toString());
		}
	}
	/**
	 * 查询盘口用户
	 * @param userId        用户id
	 * @return
	 */
	public List<String> findByUserId(String userId) throws Exception {
		String sql="select IBM_HANDICAP_USER_ID_ from ibm_handicap_user where APP_USER_ID_=? and STATE_!='DEL' ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		return super.dao.findStringList("IBM_HANDICAP_USER_ID_",sql,parameters);
	}
	/**
	 * 根据用户id删除盘口用户
	 * @param userId 		用户id
	 */
	public void delByUserId(String userId) throws SQLException {
		String sql="update ibm_handicap_user set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=? and STATE_!='DEL'";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(userId);
		super.dao.execute(sql,parameters);
	}
}
