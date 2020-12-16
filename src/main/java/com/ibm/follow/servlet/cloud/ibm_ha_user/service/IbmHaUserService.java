package com.ibm.follow.servlet.cloud.ibm_ha_user.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_user.entity.IbmHaUser;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHaUserService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHaUser对象数据
	 */
	public String save(IbmHaUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_ha_user的 IBM_HA_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_ha_user set state_='DEL' where IBM_HA_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HA_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_ha_user的 IBM_HA_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_ha_user set state_='DEL' where IBM_HA_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_ha_user的 IBM_HA_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_ha_user where IBM_HA_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HA_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_ha_user的 IBM_HA_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_ha_user where IBM_HA_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHaUser实体信息
	 *
	 * @param entity IbmHaUser实体
	 */
	public void update(IbmHaUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_ha_user表主键查找IbmHaUser实体
	 *
	 * @param id ibm_ha_user 主键
	 * @return IbmHaUser实体
	 */
	public IbmHaUser find(String id) throws Exception {
		return (IbmHaUser) this.dao.find(IbmHaUser.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_ha_user where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_ha_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_ha_user  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHaUser数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHaUser数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_ha_user where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_ha_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_ha_user  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHaUser.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_ha_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHaUser数据信息
	 *
	 * @return 可用<IbmHaUser>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_ha_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHaUser.class, sql);
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
		String sql = "SELECT ih.HANDICAP_NAME_,ih.HANDICAP_CODE_,ih.HANDICAP_ICON_,ih.SN_ FROM `ibm_ha_user` ihu"
				+ " LEFT JOIN ibm_handicap ih ON ihu.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_ "
				+ " where  ihu.APP_USER_ID_ = ? and ihu.state_ = ? and ih.state_ != ? ORDER BY ih.SN_";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取用户正在托管的代理盘口信息
	 *
	 * @param userId 用户id
	 * @return 正在托管的代理盘口信息
	 */
	public List<Map<String, Object>> listOnHostingInfo(String userId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_,HANDICAP_NAME_ FROM `ibm_ha_user` where APP_USER_ID_ = ? and "
				+ " ONLINE_AGENTS_COUNT_ > 0 and STATE_ != ? ORDER BY FREQUENCY_,UPDATE_TIME_LONG_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取 在线代理主键组
	 *
	 * @param userId     用户主键
	 * @param handicapId 盘口主键
	 * @return 在线代理主键组
	 */
	public String findOnlineAgentIds(String userId, String handicapId) throws SQLException {
		String sql = "SELECT ONLINE_AGENTS_IDS_ FROM `ibm_ha_user` where APP_USER_ID_ = ? and HANDICAP_ID_ = ? AND"
				+ " ONLINE_AGENTS_COUNT_ > 0 and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("ONLINE_AGENTS_IDS_", sql, parameterList);
	}

	/**
	 * 获取盘口代理用户主键
	 *
	 * @param userId     用户主键
	 * @param handicapId 盘口主键
	 * @return 盘口代理用户主键
	 */
	public String findId(String userId, String handicapId) throws SQLException {
		String sql = "SELECT IBM_HA_USER_ID_ FROM `ibm_ha_user` where APP_USER_ID_ = ? and HANDICAP_ID_ = ? "
				+ " AND ONLINE_AGENTS_COUNT_ < ONLINE_NUMBER_MAX_ and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("IBM_HA_USER_ID_", sql, parameterList);
	}
	/**
	 * 盘口会员登出
	 *
	 * @param handicapAgentId 盘口代理id
	 * @param nowTime         当前时间
	 */
	public void updateLogout(String handicapAgentId, Date nowTime) throws SQLException {
		String sql = "SELECT ihu.ONLINE_AGENTS_IDS_, ihu.IBM_HA_USER_ID_ FROM `ibm_ha_user` ihu "
				+ " LEFT JOIN ibm_handicap_agent iha ON ihu.IBM_HA_USER_ID_ = iha.HA_USER_ID_ "
				+ " WHERE iha.IBM_HANDICAP_AGENT_ID_ = ? AND ihu.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, Object> info = super.dao.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return;
		}
		String onlineMembersIds = info.get("ONLINE_AGENTS_IDS_").toString();
		//在线会员id集合
		Set<String> onlineMembersIdSet = new HashSet<>(Arrays.asList(onlineMembersIds.split(",")));
		if (ContainerTool.isEmpty(onlineMembersIdSet)) {
			return;
		}
		// 移除在线会员id
		onlineMembersIdSet.remove(handicapAgentId);
		// 更新
		updateOnlineAgent(onlineMembersIdSet, info.get("IBM_HA_USER_ID_").toString(), nowTime,
				IbmStateEnum.LOGOUT.name());
	}
	/**
	 * 更新盘口代理在线数量
	 *
	 * @param onlineMembersIdSet 在线代理列表
	 * @param haUserId           盘口代理用户id
	 * @param nowTime            当前时间
	 * @param method             状态
	 */
	private void updateOnlineAgent(Set<String> onlineMembersIdSet, String haUserId, Date nowTime, String method)
			throws SQLException {
		StringBuilder sql = new StringBuilder(
				"UPDATE `ibm_ha_user` SET ONLINE_AGENTS_IDS_ = ?, ONLINE_AGENTS_COUNT_ = ?"
						+ ",UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? ");
		if (IbmStateEnum.LOGIN.name().equals(method)) {
			sql.append(",FREQUENCY_ = FREQUENCY_ + 1");
		}
		sql.append(" WHERE IBM_HA_USER_ID_ = ?");
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(StringUtils.join(onlineMembersIdSet, ","));
		parameterList.add(onlineMembersIdSet.size());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(haUserId);
		super.dao.execute(sql.toString(), parameterList);
	}
	/**
	 * 获取用户已开启代理盘口
	 *
	 * @param userId 用户id
	 * @return
	 */
	public List<String> findHandicapByUserId(String userId) throws SQLException {
		String sql = "SELECT HANDICAP_ID_ from ibm_ha_user where APP_USER_ID_ = ? and state_ !=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findStringList("HANDICAP_ID_", sql, parameterList);
	}

    /**
     * 获取用户已开启代理盘口codes
     * @param appUserId     用户id
     * @return
     */
    public List<String> findHandicapCodeByUserId(String appUserId) throws SQLException {
        String sql = "SELECT HANDICAP_CODE_ from ibm_ha_user where APP_USER_ID_ = ? and state_ !=? ";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(appUserId);
        parameterList.add(IbmStateEnum.DEL.name());
        return dao.findStringList("HANDICAP_CODE_", sql, parameterList);
    }
	/**
	 * 通过盘口id删除盘口
	 *
	 * @param handicapId 盘口id
	 * @param msg        信息内容
	 */
	public void delByHandicapId(String handicapId, String msg) throws SQLException {
		String sql = "update ibm_ha_user set STATE_=?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?,DESC_ =? where HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(msg);
		parameters.add(handicapId);
		dao.execute(sql, parameters);
	}
    /**
     * 通过盘口id删除盘口
     *
     * @param handicapCode 盘口code
     * @param msg        信息内容
     */
    public void delByHandicapCode(String handicapCode, String msg) throws SQLException {
        String sql = "update ibm_ha_user set STATE_=?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?,DESC_ =? where HANDICAP_CODE_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(new Date());
        parameters.add(System.currentTimeMillis());
        parameters.add(msg);
        parameters.add(handicapCode);
        dao.execute(sql, parameters);
    }

	/**
	 * 删除用户的耨个盘口
	 * @param appUserId
	 * @throws SQLException
	 */
	public void delByAppUserId(String appUserId, String handicapId) throws SQLException {
		String sql = "update ibm_ha_user set STATE_=?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?,DESC_ =? where APP_USER_ID_=? and HANDICAP_ID_=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("更改用户代理盘口");
		parameterList.add(appUserId);
		parameterList.add(handicapId);
		dao.execute(sql,parameterList);
	}
	/**
	 * 批量添加盘口用户信息
	 *
	 * @param handicaps 盘口ids
	 * @param userId    用户id
	 */
	public void saveBatch(List<Map<String, Object>> handicaps, String userId, Integer memberMax) throws SQLException {
		Date nowTime = new Date();
		StringBuilder sql = new StringBuilder("insert into ibm_ha_user(IBM_HA_USER_ID_,APP_USER_ID_,HANDICAP_ID_,"
				+ "HANDICAP_CODE_,HANDICAP_NAME_,ONLINE_AGENTS_COUNT_,ONLINE_NUMBER_MAX_,FREQUENCY_,CREATE_TIME_,"
				+ "CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) VALUES");
		List<Object> parameterList = new ArrayList<>();
		for (Map<String, Object> handicap : handicaps) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(UUID.randomUUID().toString().replace("-", ""));
			parameterList.add(userId);
			parameterList.add(handicap.get("IBM_HANDICAP_ID_").toString());
			parameterList.add(handicap.get("HANDICAP_CODE_").toString());
			parameterList.add(handicap.get("HANDICAP_NAME_").toString());
			parameterList.add(0);
			parameterList.add(memberMax);
			parameterList.add(0);
			parameterList.add(nowTime);
			parameterList.add(System.currentTimeMillis());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(IbmStateEnum.OPEN.name());
		}
		sql.deleteCharAt(sql.length() - 1);
		super.dao.execute(sql.toString(), parameterList);
	}
	/**
	 * 盘口代理登录
	 *
	 * @param handicapAgentId 盘口代理id
	 * @param nowTime         当前时间
	 */
	public void updateLogin(String handicapAgentId, Date nowTime) throws SQLException {
		String sql = "SELECT ihu.ONLINE_AGENTS_IDS_, ihu.IBM_HA_USER_ID_ FROM `ibm_ha_user` ihu "
				+ " LEFT JOIN ibm_handicap_agent iha ON ihu.IBM_HA_USER_ID_ = iha.HA_USER_ID_"
				+ " WHERE iha.IBM_HANDICAP_AGENT_ID_ = ? AND ihu.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.OPEN.name());
		Map<String, Object> info = super.dao.findMap(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return;
		}
		Set<String> onlineAgentIdsSet= new HashSet<>();
		if (StringTool.notEmpty(info.get("ONLINE_AGENTS_IDS_"))) {
			onlineAgentIdsSet.addAll(Arrays.asList(info.get("ONLINE_AGENTS_IDS_").toString().split(",")));
		}
		onlineAgentIdsSet.add(handicapAgentId);
		updateOnlineAgent(onlineAgentIdsSet, info.get("IBM_HA_USER_ID_").toString(), nowTime,
				IbmStateEnum.LOGIN.name());
	}

	/**
	 * 查询所有盘口下的盘口信息和用户信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findUserAll() throws Exception {
		String sql = "SELECT IBM_HA_USER_ID_,(SELECT APP_USER_NAME_ FROM app_user WHERE APP_USER_ID_ = haUser.APP_USER_ID_) as APP_USER_NAME_," +
				"HANDICAP_CODE_,HANDICAP_NAME_,ONLINE_AGENTS_COUNT_,ONLINE_NUMBER_MAX_,FREQUENCY_,CREATE_TIME_LONG_,DESC_" +
				" FROM ibm_ha_user AS haUser WHERE STATE_ != ? ORDER BY HANDICAP_CODE_ ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		return   this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 更新在线会员数量和最大在线会员数控i昂
	 * @param ibmHaUserId
	 * @param onlineAgentsCount
	 * @param onlineNumberMax
	 * @throws SQLException
	 */
    public void updateOnlineInfoById(String ibmHaUserId, String onlineAgentsCount, String onlineNumberMax) throws SQLException {
		String sql = "update ibm_ha_user set ONLINE_AGENTS_COUNT_ = ? ,ONLINE_NUMBER_MAX_= ? WHERE IBM_HA_USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(onlineAgentsCount);
		parameterList.add(onlineNumberMax);
		parameterList.add(ibmHaUserId);
		dao.execute(sql,parameterList);
    }

	/**
	 *
	 * @param handicapCode
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findUserAllByCode(String handicapCode) throws SQLException {
		String sql = "SELECT IBM_HA_USER_ID_,(SELECT APP_USER_NAME_ FROM app_user WHERE APP_USER_ID_ = haUser.APP_USER_ID_) as APP_USER_NAME_," +
				"HANDICAP_CODE_,HANDICAP_NAME_,ONLINE_AGENTS_COUNT_,ONLINE_NUMBER_MAX_,FREQUENCY_,CREATE_TIME_LONG_,DESC_" +
				" FROM ibm_ha_user AS haUser WHERE STATE_ != ? AND HANDICAP_CODE_ = ? ORDER BY HANDICAP_CODE_ ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(handicapCode);
		return   this.dao.findMapList(sql, parameterList);
	}

    public void delByAppUserId(String appUserId) throws SQLException {
		String sql = "delete from ibm_ha_user where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		dao.execute(sql,parameterList);
    }

	/**
	 * 更新状态
	 * @param appUserId
	 * @throws SQLException
	 */
	public void updateStateByAppUserId(String appUserId) throws SQLException {
		String sql = "update ibm_ha_user set STATE_ = ? where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(appUserId);
		dao.execute(sql,parameterList);
	}

	/**
	 * 更新盘口最大在线代理人数
	 * @param handicapId
	 * @param newOnlineMax
	 * @param defultOnlineMax
	 * @throws SQLException
	 */
	public int updateOnlineNumByHandIicapId(String handicapId,int newOnlineMax,int defultOnlineMax) throws SQLException {
		String sql = "UPDATE `ibm_ha_user` SET ONLINE_NUMBER_MAX_ = ? WHERE `HANDICAP_ID_` = ? AND `ONLINE_NUMBER_MAX_` =? AND STATE_ !=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(newOnlineMax);
		parameterList.add(handicapId);
		parameterList.add(defultOnlineMax);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.execute(sql,parameterList);
	}


    /**
     *
     * @param handicaps     盘口信息
     * @param appUserId     用户id
     * @return
     */
    public void save(List<Map<String, Object>> handicaps, String appUserId, Map<Object,Object>  agentInfo) throws SQLException {
        Date nowTime = new Date();
        StringBuilder sql = new StringBuilder("insert into ibm_ha_user(IBM_HA_USER_ID_,APP_USER_ID_,HANDICAP_ID_,"
                + "HANDICAP_CODE_,HANDICAP_NAME_,ONLINE_AGENTS_COUNT_,ONLINE_NUMBER_MAX_,FREQUENCY_,CREATE_TIME_,"
                + "CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<Object> parameterList = new ArrayList<>();
        for (Map<String, Object> handicap : handicaps) {
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?),");
            parameterList.add(UUID.randomUUID().toString().replace("-", ""));
            parameterList.add(appUserId);
            parameterList.add(handicap.get("IBM_HANDICAP_ID_").toString());
            parameterList.add(handicap.get("HANDICAP_CODE_").toString());
            parameterList.add(handicap.get("HANDICAP_NAME_").toString());
            parameterList.add(0);
            parameterList.add(agentInfo.get(handicap.get("HANDICAP_CODE_")));
            parameterList.add(0);
            parameterList.add(nowTime);
            parameterList.add(System.currentTimeMillis());
            parameterList.add(System.currentTimeMillis());
            parameterList.add(IbmStateEnum.OPEN.name());
        }
        sql.deleteCharAt(sql.length() - 1);
        super.dao.execute(sql.toString(), parameterList);
    }
    /**
     *
     * @param handicaps     盘口信息
     * @param appUserId     用户id
     * @return
     */
    public void save(List<Map<String, Object>> handicaps, String appUserId) throws SQLException {
        Date nowTime = new Date();
        StringBuilder sql = new StringBuilder("insert into ibm_ha_user(IBM_HA_USER_ID_,APP_USER_ID_,HANDICAP_ID_,"
                + "HANDICAP_CODE_,HANDICAP_NAME_,ONLINE_AGENTS_COUNT_,ONLINE_NUMBER_MAX_,FREQUENCY_,CREATE_TIME_,"
                + "CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<Object> parameterList = new ArrayList<>();
        for (Map<String, Object> handicap : handicaps) {
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?),");
            parameterList.add(UUID.randomUUID().toString().replace("-", ""));
            parameterList.add(appUserId);
            parameterList.add(handicap.get("IBM_HANDICAP_ID_").toString());
            parameterList.add(handicap.get("HANDICAP_CODE_").toString());
            parameterList.add(handicap.get("HANDICAP_NAME_").toString());
            parameterList.add(0);
            parameterList.add(10);
            parameterList.add(0);
            parameterList.add(nowTime);
            parameterList.add(System.currentTimeMillis());
            parameterList.add(System.currentTimeMillis());
            parameterList.add(IbmStateEnum.OPEN.name());
        }
        sql.deleteCharAt(sql.length() - 1);
        super.dao.execute(sql.toString(), parameterList);
    }

    /**
     * 获取用户代理盘口信息
     * @param appUserId     用户id
     * @param handicapCodes         盘口codes
     * @return
     */
    public List<Map<String, Object>> findHandicap(String appUserId, Set<Object> handicapCodes) throws SQLException {
        StringBuilder sql = new StringBuilder("select HANDICAP_ID_,ONLINE_AGENTS_IDS_ from ibm_ha_user where APP_USER_ID_=? and STATE_ = ? and HANDICAP_CODE_ in(");
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(appUserId);
        parameterList.add(IbmStateEnum.OPEN.name());
        for (Object handicapCode : handicapCodes) {
            sql.append("?,");
            parameterList.add(handicapCode);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        return super.dao.findMapList(sql.toString(), parameterList);
    }

    /**
     * 获取代理盘口信息
     * @param appUserId     用户id
     * @return 代理盘口信息
     */
    public List<Map<String, Object>> listHandicapInfo(String appUserId) throws SQLException {
        String sql="select HANDICAP_CODE_ ,ONLINE_NUMBER_MAX_  from ibm_ha_user where APP_USER_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(appUserId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findMapList(sql,parameterList);
    }

	/**
	 * 修改最大在线数
	 * @param appUserId 用户主键
	 * @param handicapCode 盘口编码
	 * @param onlineNumberMax 最大在线数
	 */
	public void updateOnlineNumMax(String appUserId, Object handicapCode, Object onlineNumberMax) throws SQLException {
		String sql ="UPDATE ibm_ha_user set ONLINE_NUMBER_MAX_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_USER_ID_ = ? and HANDICAP_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(onlineNumberMax);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(sql,parameterList);
	}
}
