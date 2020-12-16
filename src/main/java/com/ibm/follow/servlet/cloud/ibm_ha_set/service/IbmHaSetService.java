package com.ibm.follow.servlet.cloud.ibm_ha_set.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_set.entity.IbmHaSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHaSetService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHaSet对象数据
	 */
	public String save(IbmHaSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_ha_set的 IBM_HA_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_ha_set set state_='DEL' where IBM_HA_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HA_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_ha_set的 IBM_HA_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_ha_set set state_='DEL' where IBM_HA_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_ha_set的 IBM_HA_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_ha_set where IBM_HA_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HA_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_ha_set的 IBM_HA_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_ha_set where IBM_HA_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHaSet实体信息
	 *
	 * @param entity IbmHaSet实体
	 */
	public void update(IbmHaSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_ha_set表主键查找IbmHaSet实体
	 *
	 * @param id ibm_ha_set 主键
	 * @return IbmHaSet实体
	 */
	public IbmHaSet find(String id) throws Exception {
		return (IbmHaSet) this.dao.find(IbmHaSet.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_ha_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_ha_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_ha_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHaSet数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHaSet数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_ha_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_ha_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_ha_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHaSet.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_ha_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHaSet数据信息
	 *
	 * @return 可用<IbmHaSet>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_ha_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHaSet.class, sql);
	}

	/**
	 * 获取盘口代理设置展示信息
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 设置展示信息
	 */
	public Map<String, Object> findShowInfo(String handicapAgentId) throws SQLException {
		String sql = "SELECT FOLLOW_MEMBER_TYPE_,MEMBER_LIST_INFO_,FOLLOW_MEMBER_LIST_INFO_ FROM `ibm_ha_set` where  "
				+ "HANDICAP_AGENT_ID_ = ? and  STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取会员列表信息
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 会员列表信息
	 */
	public String findMemberListInfo(String handicapAgentId) throws SQLException {
		String sql = "SELECT MEMBER_LIST_INFO_ as key_ FROM `ibm_ha_set` where HANDICAP_AGENT_ID_ = ? and  STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}
	/**
	 * 修改跟随会员列表信息
	 *
	 * @param handicapAgentId  盘口代理id
	 * @param followMemberType 跟随会员类型
	 * @param memberList       会员列表
	 */
	public void updateFollowList(String handicapAgentId, String followMemberType, String memberList)
			throws SQLException {
		String sql = "update ibm_ha_set set FOLLOW_MEMBER_TYPE_=?,FOLLOW_MEMBER_LIST_INFO_=?,"
				+ "UPDATE_TIME_LONG_=? where HANDICAP_AGENT_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(followMemberType);
		parameterList.add(memberList);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 获取
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 跟随信息
	 * @throws SQLException
	 */
	public Map<String, Object> findByHaId(String handicapAgentId, JSONArray memberListInfo) throws SQLException {
		Map<String,Object> map=findFollowInfo(handicapAgentId);
		if(ContainerTool.isEmpty(map)||ContainerTool.isEmpty(memberListInfo)){
			return map;
		}
		String sql="update ibm_ha_set set MEMBER_LIST_INFO_=? where IBM_HA_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(memberListInfo.toString());
		parameterList.add(map.remove("IBM_HA_SET_ID_"));
		super.dao.execute(sql,parameterList);
		return map;
	}

    /**
     * 获取跟投设置信息
     * @param handicapAgentIds  代理ids
     * @return
     */
    public List<Map<String, Object>> findByHaIds(List<String> handicapAgentIds) throws SQLException {
	    StringBuilder sql=new StringBuilder();
        sql.append("select HANDICAP_AGENT_ID_,FOLLOW_MEMBER_TYPE_,FOLLOW_MEMBER_LIST_INFO_ from ibm_ha_set"
                + " where STATE_=? and HANDICAP_AGENT_ID_ in(");
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.OPEN.name());
        for(String handicapAgentId:handicapAgentIds){
            sql.append("?,");
            parameterList.add(handicapAgentId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return  super.findMapList(sql.toString(), parameterList);
    }

	/**
	 * 跟随信息
	 * @param handicapAgentId 盘口代理id
	 * @return 跟随信息
	 */
	public Map<String, Object> findFollowInfo(String handicapAgentId) throws SQLException {
		String sql = "select IBM_HA_SET_ID_,FOLLOW_MEMBER_TYPE_,FOLLOW_MEMBER_LIST_INFO_ from ibm_ha_set"
				+ " where HANDICAP_AGENT_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return  super.findMap(sql, parameterList);
	}

    /**
     * 获取盘口代理跟投方式
     * @param handicapAgentId   盘口代理id
     * @return 跟投方式
     */
    public String findFollowMemberType(String handicapAgentId) throws SQLException {
        String sql = "SELECT FOLLOW_MEMBER_TYPE_ as key_ FROM `ibm_ha_set` where HANDICAP_AGENT_ID_ = ? and  STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findString(sql, parameterList);
    }

    /**
     * 修改会员列表
     * @param handicapAgentId   盘口代理id
     * @param memberList        会员列表
     */
    public void updateMemberList(String handicapAgentId, Object memberList) throws SQLException {
        String sql = "update ibm_ha_set set MEMBER_LIST_INFO_ = ?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_AGENT_ID_= ?"
                + " and  STATE_=?";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(memberList.toString());
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
        dao.execute(sql, parameterList);
    }
}
