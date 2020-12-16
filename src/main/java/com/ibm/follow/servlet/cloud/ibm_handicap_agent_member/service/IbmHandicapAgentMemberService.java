package com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.entity.IbmHandicapAgentMember;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHandicapAgentMemberService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicapAgentMember对象数据
	 */
	public String save(IbmHandicapAgentMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_handicap_agent_member的 IBM_HANDICAP_AGENT_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_handicap_agent_member set state_='DEL' where IBM_HANDICAP_AGENT_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HANDICAP_AGENT_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_agent_member的 IBM_HANDICAP_AGENT_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_handicap_agent_member set state_='DEL' where IBM_HANDICAP_AGENT_MEMBER_ID_ in("
					+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_handicap_agent_member的 IBM_HANDICAP_AGENT_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_handicap_agent_member where IBM_HANDICAP_AGENT_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HANDICAP_AGENT_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_agent_member的 IBM_HANDICAP_AGENT_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_handicap_agent_member where IBM_HANDICAP_AGENT_MEMBER_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHandicapAgentMember实体信息
	 *
	 * @param entity IbmHandicapAgentMember实体
	 */
	public void update(IbmHandicapAgentMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_handicap_agent_member表主键查找IbmHandicapAgentMember实体
	 *
	 * @param id ibm_handicap_agent_member 主键
	 * @return IbmHandicapAgentMember实体
	 */
	public IbmHandicapAgentMember find(String id) throws Exception {
		return (IbmHandicapAgentMember) this.dao.find(IbmHandicapAgentMember.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_handicap_agent_member where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_agent_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_agent_member  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHandicapAgentMember数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicapAgentMember数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap_agent_member where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_agent_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_agent_member  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmHandicapAgentMember.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_agent_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHandicapAgentMember数据信息
	 *
	 * @return 可用<IbmHandicapAgentMember>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_agent_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicapAgentMember.class, sql);
	}

	/**
	 * 获取盘口会员id和盘口代理id
	 *
	 * @param appUserId 用户主键
	 * @return 盘口代理id和会员ids MAP
	 */
	public Map<String, List<String>> mapHaIdAndHmId(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_AGENT_ID_ as key_,HANDICAP_MEMBER_ID_ as value_ FROM ibm_handicap_agent_member "
				+ "  where APP_USER_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findKVsMapList(sql, parameterList);

	}

	/**
	 * 获取盘口代理id和盘口会员id
	 *
	 * @param appUserId 用户主键
	 * @return 盘口会员id和代理ids MAP
	 */
	public Map<String, List<String>> mapHmIdAndHaId(String appUserId) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ as key_,HANDICAP_AGENT_ID_ as value_ FROM ibm_handicap_agent_member "
				+ "  where APP_USER_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findKVsMapList(sql, parameterList);
	}

	/**
	 * 获取盘口代理ids
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 盘口代理ids
	 */
	public List<String> listHaId(String handicapMemberId) throws SQLException {
		String sql = "SELECT HANDICAP_AGENT_ID_ FROM ibm_handicap_agent_member where HANDICAP_MEMBER_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findStringList("HANDICAP_AGENT_ID_", sql, parameterList);

	}

	/**
	 * 获取 盘口会员ids
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 盘口会员ids
	 */
	public List<String> listHmId(String handicapAgentId) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ FROM ibm_handicap_agent_member where HANDICAP_AGENT_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);
	}

	/**
	 * 保存 盘口代理会员
	 *
	 * @param appUserId        用户主键
	 * @param handicapAgentId  盘口代理主键
	 * @param handicapMemberId 盘口会员主键
	 * @param nowTime          保存事件
	 */
	public void save(String appUserId, String handicapAgentId, String handicapMemberId,
                     String memberHandicapCode,String memberAccount, Date nowTime) throws Exception {
		IbmHandicapAgentMember handicapAgentMember = new IbmHandicapAgentMember();
		handicapAgentMember.setAppUserId(appUserId);
		handicapAgentMember.setHandicapAgentId(handicapAgentId);
		handicapAgentMember.setHandicapMemberId(handicapMemberId);
        handicapAgentMember.setMemeberHandicapCode(memberHandicapCode);
        handicapAgentMember.setMemberAccount(memberAccount);
		handicapAgentMember.setCreateTime(nowTime);
		handicapAgentMember.setCreateTimeLong(System.currentTimeMillis());
		handicapAgentMember.setUpdateTimeLong(System.currentTimeMillis());
		handicapAgentMember.setState(IbmStateEnum.OPEN.name());
		save(handicapAgentMember);
	}

	/**
	 * 代理解绑
	 *
	 * @param handicapAgentId 盘口代理主键
	 * @param nowTime         解绑时间
	 */
	public void unbindByHaId(String handicapAgentId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_handicap_agent_member set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_= ? "
				+ " where HANDICAP_AGENT_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.UNBIND.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("代理解绑");
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);
	}
	/**
	 * 会员解绑
	 *
	 * @param handicapAgentId  盘口代理主键
	 * @param handicapMemberId 盘口会员主键
	 * @param nowTime          解绑时间
	 */
	public void unbindByHaId(String handicapAgentId, String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_handicap_agent_member set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_= ? "
				+ " where HANDICAP_AGENT_ID_ = ? AND HANDICAP_MEMBER_ID_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.UNBIND.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("会员解绑");
		parameterList.add(handicapAgentId);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);
	}

    public void delByAppUserId(String appUserId) throws SQLException {
		String sql = "delete from ibm_handicap_agent_member where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		dao.execute(sql,parameterList);
    }

	public void updateStateByAppUserId(String appUserId) throws SQLException {
		String sql = "update ibm_handicap_agent_member set STATE_ = ? where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(appUserId);
		dao.execute(sql,parameterList);
	}

    /**
     * 通过盘口会员id获取绑定的代理id
     * @param handicapMemberId      盘口会员id
     * @return
     */
    public List<String> findHaIdByHmId(String handicapMemberId) throws SQLException {
        String sql="select HANDICAP_AGENT_ID_ from ibm_handicap_agent_member where HANDICAP_MEMBER_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(handicapMemberId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("HANDICAP_AGENT_ID_",sql,parameterList);
    }
}
