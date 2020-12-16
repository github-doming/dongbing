package com.ibm.follow.servlet.cloud.ibm_ha_info.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_info.entity.IbmHaInfo;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHaInfoService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHaInfo对象数据
	 */
	public String save(IbmHaInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_ha_info的 IBM_HA_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_ha_info set state_='DEL' where IBM_HA_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HA_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_ha_info的 IBM_HA_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_ha_info set state_='DEL' where IBM_HA_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_ha_info的 IBM_HA_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_ha_info where IBM_HA_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HA_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_ha_info的 IBM_HA_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_ha_info where IBM_HA_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHaInfo实体信息
	 *
	 * @param entity IbmHaInfo实体
	 */
	public void update(IbmHaInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_ha_info表主键查找IbmHaInfo实体
	 *
	 * @param id ibm_ha_info 主键
	 * @return IbmHaInfo实体
	 */
	public IbmHaInfo find(String id) throws Exception {
		return (IbmHaInfo) this.dao.find(IbmHaInfo.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_ha_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_ha_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_ha_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHaInfo数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHaInfo数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_ha_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_ha_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_ha_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHaInfo.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_ha_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHaInfo数据信息
	 *
	 * @return 可用<IbmHaInfo>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_ha_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHaInfo.class, sql);
	}

	/**
	 * 获取在线代理信息
	 *
	 * @param handicapAgentIds 盘口代理d数组
	 * @return 在线代理信息
	 */
	public List<Map<String, Object>> listOnlineInfoByHaIds(String[] handicapAgentIds) throws SQLException {
		String sql = "SELECT ihi.HANDICAP_AGENT_ID_,iha.AGENT_ACCOUNT_,HANDICAP_ITEM_ID_ FROM ibm_ha_info ihi "
				+ " LEFT JOIN ibm_handicap_agent iha on ihi.HANDICAP_AGENT_ID_ = iha.IBM_HANDICAP_AGENT_ID_ "
				+ " where ihi.STATE_ = ? and iha.STATE_ = ? and ihi.HANDICAP_AGENT_ID_ in (";
		List<Object> parameterList = new ArrayList<>(handicapAgentIds.length + 2);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		StringBuilder sqlBuilder = new StringBuilder();
		for (String handicapAgentId : handicapAgentIds) {
			sqlBuilder.append("?,");
			parameterList.add(handicapAgentId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(")");
		sql += sqlBuilder.toString();
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取离线代理信息
	 *
	 * @param userId      用户id
	 * @param handicapId  盘口id
	 * @param onlineHaIds 在线 盘口代理id数组
	 * @return 离线代理信息
	 */
	public List<Map<String, Object>> listOfflineInfo(String userId, String handicapId, Set<Object> onlineHaIds)
			throws SQLException {
		String sql = "SELECT IBM_HANDICAP_AGENT_ID_ AS HANDICAP_AGENT_ID_, AGENT_ACCOUNT_,HANDICAP_ITEM_ID_ "
				+ " FROM ibm_handicap_agent WHERE APP_USER_ID_ = ? AND HANDICAP_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		if (ContainerTool.notEmpty(onlineHaIds)) {
			StringBuilder sqlBuilder = new StringBuilder(" and IBM_HANDICAP_AGENT_ID_ not in ( ");
			for (Object handicapAgentId : onlineHaIds) {
				sqlBuilder.append("?,");
				parameterList.add(handicapAgentId);
			}
			sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(")");
			sql += sqlBuilder.toString();
		}
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取 盘口代理 展示基本信息
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 基本信息
	 */
	public Map<String, Object> findShowInfo(String handicapAgentId) throws SQLException {
		String sql = "SELECT STATE_ FROM `ibm_ha_info` where  HANDICAP_AGENT_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 盘口代理登出
	 *
	 * @param handicapAgentId 盘口代理id
	 * @param nowTime         当前时间
	 */
	public void updateLogout(String handicapAgentId, Date nowTime) throws SQLException {
		String sql = "update ibm_ha_info set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where HANDICAP_AGENT_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.LOGOUT.name());
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 通过盘口代理id获取主键
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 主键
	 */
	public String findIdByHaId(String handicapAgentId) throws SQLException {
		String sql = "select IBM_HA_INFO_ID_ from ibm_ha_info where HANDICAP_AGENT_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("IBM_HA_INFO_ID_", sql, parameterList);
	}
	/**
	 * 通过盘口代理信息修改信息
	 *
	 * @param haInfoId       盘口代理信息id
	 * @param memberListInfo 会员列表信息
	 * @param nowTime        当前时间
	 */
	public void updateById(String haInfoId, JSONArray memberListInfo, Date nowTime) throws SQLException {
		String sql = "update ibm_ha_info set MEMBER_LIST_INFO_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where IBM_HA_INFO_ID_=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(memberListInfo.toString());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(haInfoId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 判断该盘口代理是否处于登录状态
	 *
	 * @param handicapAgentId 盘口代理主键
	 * @return 登录-true
	 */
	public boolean isLogin(String handicapAgentId) throws SQLException {
		String sql = "select STATE_ from ibm_ha_info where HANDICAP_AGENT_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());
		String state = super.dao.findString("STATE_", sql, parameterList);
		return StringTool.notEmpty(state) && IbmStateEnum.LOGIN.equal(state);
	}

	/**
	 * 该账号是否在某盘口已经登录
	 *
	 * @param handicapId   账户所在盘口
	 * @param agentAccount 盘口zhangh
	 * @return 登录 true
	 */
	public boolean isLogin(String handicapId, String agentAccount) throws SQLException {
		String sql = "SELECT IBM_HA_INFO_ID_ FROM `ibm_ha_info` ihi LEFT JOIN ibm_handicap_agent iha ON "
				+ " ihi.HANDICAP_AGENT_ID_ = iha.IBM_HANDICAP_AGENT_ID_ WHERE iha.HANDICAP_ID_ =  ? "
				+ " AND iha.AGENT_ACCOUNT_ = ? AND ihi.STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(agentAccount);
		parameterList.add(IbmStateEnum.LOGIN.name());
		return ContainerTool.notEmpty(super.findMapList(sql, parameterList));

	}
	/**
	 * 获取登录状态
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 登录状态
	 */
	public String findLoginState(String handicapAgentId) throws SQLException {
		String sql = "select STATE_ from ibm_ha_info where HANDICAP_AGENT_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("STATE_", sql, parameterList);
	}

	/**
	 * 获取代理信息
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 获取代理信息
	 */
	public Map<String, Object> findAgentInfo(String handicapAgentId) throws SQLException {
		String sql = "select STATE_ from ibm_ha_info where HANDICAP_AGENT_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 获取在线的盘口代理主键数组
	 *
	 * @param appUserId 用户主键
	 * @return 盘口代理主键数组
	 */
	public List<String> listHostingHaIdByUserId(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_AGENT_ID_ FROM ibm_ha_info  where APP_USER_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.LOGIN.name());
		return super.findStringList("HANDICAP_AGENT_ID_", sql, parameterList);
	}

	public void delByAppUserId(String appUserId) throws SQLException {
		String sql = "delete from ibm_ha_info where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	public void updateStateByAppUserId(String appUserId) throws SQLException {
		String sql = "update ibm_ha_info set STATE_ = ? where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	/**
	 * 获取正在托管的盘口代理主键
	 *
	 * @param handicapId 盘口主键
	 * @return 盘口代理主键列表
	 */
	public List<String> listHostingHaId(String handicapId) throws SQLException {
		return listHostingHaId(handicapId,null);
	}

	/**
	 * 获取正在托管的盘口代理主键
	 *
	 * @param handicapId 盘口主键
	 * @param userIds    用户主键列表
	 * @return 盘口代理主键列表
	 */
	public List<String> listHostingHaId(String handicapId, List<String> userIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT ihi.HANDICAP_AGENT_ID_ as key_ FROM `ibm_ha_info` ihi "
				+ " LEFT JOIN ibm_handicap_agent ihg ON ihi.HANDICAP_AGENT_ID_ = ihg.IBM_HANDICAP_AGENT_ID_ WHERE "
				+ " ihg.HANDICAP_ID_ = ? and ihg.STATE_ != ? AND ihi.STATE_ = ?");
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
     * 获取所有登录的用户信息
     * @return
     */
    public List<Map<String, Object>> findLoginInfoByHandicapCode(HandicapUtil.Code handicapCode) throws SQLException {
        String sql="select APP_USER_ID_,HANDICAP_AGENT_ID_ from ibm_ha_info where HANDICAP_CODE_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapCode.name());
        parameterList.add(IbmStateEnum.LOGIN.name());
        return super.dao.findMapList(sql,parameterList);
    }

	/**
	 * 获取用户代理ID
	 * @param appUserId
	 * @return
	 * @throws SQLException
	 */
	public List<String> listHaIdByUserId(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_AGENT_ID_ from ibm_ha_info where APP_USER_ID_= ? and STATE_=? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
        parameterList.add(IbmTypeEnum.AGENT.name());
		return dao.findStringList("HANDICAP_AGENT_ID_",sql,parameterList);
	}

	/**
	 * 获取代理IDs
	 * @param userIds
	 * @return
	 * @throws SQLException
	 */
	public List<String> listHaIdByUserIds(List<String> userIds) throws SQLException {
		StringBuilder sql = new  StringBuilder("SELECT HANDICAP_AGENT_ID_ from ibm_ha_info where APP_USER_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(userIds.size());
		for(String uid : userIds){
			sql.append("?,");
			parameterList.add(uid);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		return dao.findStringList("HANDICAP_AGENT_ID_",sql.toString(),parameterList);
	}

    /**
     * 获取代理信息
     * @param userName      用户名
     * @param agentAccount  代理账号
     * @param handicapCode  盘口code
     * @param onlineState   在线状态
     * @param pageIndex     页数
     * @param pageSize      条数
     * @return
     */
    public PageCoreBean findShow(String userName, String agentAccount, String handicapCode, String onlineState, Integer pageIndex, Integer pageSize) throws SQLException {
        String sqlCount="SELECT count(ihi.IBM_HA_INFO_ID_) FROM ibm_ha_info ihi LEFT JOIN app_user au ON ihi.APP_USER_ID_ = au.APP_USER_ID_"
                + " LEFT JOIN ibm_handicap_agent iha ON ihi.HANDICAP_AGENT_ID_ = iha.IBM_HANDICAP_AGENT_ID_"
                + " LEFT JOIN ibm_handicap_item ih ON iha.HANDICAP_ITEM_ID_ = ih.IBM_HANDICAP_ITEM_ID_ where au.STATE_!=? and iha.STATE_!=?";
        String sql="SELECT ihi.HANDICAP_AGENT_ID_,au.APP_USER_NAME_,iha.HANDICAP_CODE_,iha.AGENT_ACCOUNT_,ih.HANDICAP_URL_,"
                + "ih.HANDICAP_CAPTCHA_,ihi.STATE_ FROM ibm_ha_info ihi LEFT JOIN app_user au ON ihi.APP_USER_ID_ = au.APP_USER_ID_"
                + " LEFT JOIN ibm_handicap_agent iha ON ihi.HANDICAP_AGENT_ID_ = iha.IBM_HANDICAP_AGENT_ID_"
                + " LEFT JOIN ibm_handicap_item ih ON iha.HANDICAP_ITEM_ID_ = ih.IBM_HANDICAP_ITEM_ID_ where au.STATE_!=? and iha.STATE_!=?";
        ArrayList<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(IbmStateEnum.DEL.name());
        StringBuilder sqlPlus=new StringBuilder();
        if(StringTool.notEmpty(userName)){
            sqlPlus.append(" and au.APP_USER_NAME_ like ?");
            parameterList.add("%"+userName+"%");
        }
        if(StringTool.notEmpty(agentAccount)){
            sqlPlus.append(" and iha.AGENT_ACCOUNT_ like ?");
			parameterList.add("%"+agentAccount+"%");
        }
        if(StringTool.notEmpty(handicapCode)){
            sqlPlus.append(" and iha.HANDICAP_CODE_=?");
            parameterList.add(handicapCode);
        }
        if(StringTool.notEmpty(onlineState)){
            sqlPlus.append(" and ihi.STATE_=?");
            parameterList.add(onlineState);
        }
        sqlPlus.append(" order by ihi.STATE_ ,au.APP_USER_NAME_");
        sqlCount += sqlPlus;
        sql += sqlPlus;
        return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
    }

    /**
     * 修改会员列表
     * @param handicapAgentId   盘口代理id
     * @param memberList        会员列表
     */
    public void updateMemberList(String handicapAgentId, Object memberList) throws SQLException {
        String sql = "update ibm_ha_info set MEMBER_LIST_INFO_ = ?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_AGENT_ID_= ?"
                + " and  STATE_=?";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(memberList.toString());
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.LOGIN.name());
        dao.execute(sql, parameterList);
    }

	/**
	 * 更新检验信息
	 * @param handicapAgentId		盘口代理id
	 * @param code					代理信息码
	 * @param message				代理信息
	 */
	public void updateAgentInfo(String handicapAgentId, String code, String message) throws SQLException {
		String sql = "update ibm_ha_info set AGENT_INFO_=?,AGENT_INFO_CODE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? "
				+ "where HANDICAP_AGENT_ID_=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(message);
		parameterList.add(code);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapAgentId);
		super.dao.execute(sql, parameterList);
	}
}
