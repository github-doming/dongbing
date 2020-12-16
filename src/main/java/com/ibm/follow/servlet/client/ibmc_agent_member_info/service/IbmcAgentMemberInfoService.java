package com.ibm.follow.servlet.client.ibmc_agent_member_info.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.ibmc_agent_member_info.entity.IbmcAgentMemberInfo;
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
public class IbmcAgentMemberInfoService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcAgentMemberInfo对象数据
	 */
	public String save(IbmcAgentMemberInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_agent_member_info的 IBMC_AGENT_MEMBER_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_agent_member_info set state_='DEL' where IBMC_AGENT_MEMBER_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_AGENT_MEMBER_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_agent_member_info的 IBMC_AGENT_MEMBER_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibmc_agent_member_info set state_='DEL' where IBMC_AGENT_MEMBER_INFO_ID_ in("
					+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_agent_member_info的 IBMC_AGENT_MEMBER_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_agent_member_info where IBMC_AGENT_MEMBER_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_AGENT_MEMBER_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_agent_member_info的 IBMC_AGENT_MEMBER_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibmc_agent_member_info where IBMC_AGENT_MEMBER_INFO_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcAgentMemberInfo实体信息
	 *
	 * @param entity IbmcAgentMemberInfo实体
	 */
	public void update(IbmcAgentMemberInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_agent_member_info表主键查找IbmcAgentMemberInfo实体
	 *
	 * @param id ibmc_agent_member_info 主键
	 * @return IbmcAgentMemberInfo实体
	 */
	public IbmcAgentMemberInfo find(String id) throws Exception {
		return (IbmcAgentMemberInfo) this.dao.find(IbmcAgentMemberInfo.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_agent_member_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_agent_member_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_agent_member_info  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcAgentMemberInfo数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcAgentMemberInfo数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_agent_member_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_agent_member_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_agent_member_info  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmcAgentMemberInfo.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_agent_member_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcAgentMemberInfo数据信息
	 *
	 * @return 可用<IbmcAgentMemberInfo>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_agent_member_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcAgentMemberInfo.class, sql);
	}

	/**
	 * 获取发送投注信息的会员信息
	 *
	 * @param existHaId 已存在盘口代理主键
	 * @return 会员信息
	 */
	public List<Map<String, Object>> listHmInfo4SendBet(String existHaId) throws SQLException {
		String sql = "SELECT EXIST_HM_ID_,MEMBER_CLIENT_CODE_,BET_MODE_INFO_,MEMBER_HANDICAP_CODE_,MEMBER_ACCOUNT_"
                +" FROM ibmc_agent_member_info where EXIST_HA_ID_ = ? and  state_ = ? order by UPDATE_TIME_ desc";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(existHaId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameterList);

	}

	/**
	 * 获取 客户端代理会员主键
	 *
	 * @param existHaId 已存在盘口代理主键
	 * @param existHmId 已存在盘口会员主键
	 * @return 客户端代理会员主键
	 */
	public String findId(String existHaId, String existHmId) throws SQLException {
		String sql = "SELECT IBMC_AGENT_MEMBER_INFO_ID_ FROM `ibmc_agent_member_info` where "
				+ " EXIST_HA_ID_ = ? and EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(existHaId);
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString("IBMC_AGENT_MEMBER_INFO_ID_", sql, parameterList);
	}

	/**
	 * 更新代理会员信息
	 *
	 * @param agentMemberInfoId 客户端代理会员主键
	 * @param clientCode        会员客户端编码
	 * @param betModeInfo       投注模式
	 * @param nowTime           更新时间
	 */
	public void update(String agentMemberInfoId, String clientCode, JSONObject betModeInfo,String memberHandicapCode,String memberAccount, Date nowTime)
			throws SQLException {
		String sql = "UPDATE `ibmc_agent_member_info` set MEMBER_CLIENT_CODE_ = ?,BET_MODE_INFO_ = ?,MEMBER_HANDICAP_CODE_=?,MEMBER_ACCOUNT_=?,UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBMC_AGENT_MEMBER_INFO_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(clientCode);
		parameterList.add(betModeInfo.toString());
        parameterList.add(memberHandicapCode);
        parameterList.add(memberAccount);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("更新代理会员信息");
		parameterList.add(agentMemberInfoId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);

	}
	/**
	 * 解绑代理会员
	 * @param existHaId 已存在盘口代理主键
	 * @param existHmId 已存在盘口会员主键
	 */
	public void unbind(String existHaId, String existHmId) throws SQLException {
		String sql = "UPDATE `ibmc_agent_member_info` set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where EXIST_HA_ID_ = ? and EXIST_HM_ID_=? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(IbmStateEnum.UNBIND.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("更新代理会员信息");
		parameterList.add(existHaId);
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);
	}

    /**
     * 获取投注模式信息
     * @param existHaId     已存在盘口代理id
     * @param existHmId     已存在盘口会员id
     * @return
     */
    public String findBetModeInfo(String existHaId, String existHmId) throws SQLException {
        String sql = "SELECT BET_MODE_INFO_ FROM `ibmc_agent_member_info` where "
                + " EXIST_HA_ID_ = ? and EXIST_HM_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(existHaId);
        parameterList.add(existHmId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findString("BET_MODE_INFO_", sql, parameterList);
    }

	/**
	 * 获取投注模式信息
	 * @param existHmId     已存在盘口会员id
	 * @return
	 */
	public String findBetModeInfo(String existHmId) throws SQLException {
		String sql = "SELECT BET_MODE_INFO_ FROM `ibmc_agent_member_info` where "
				+ "  EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString("BET_MODE_INFO_", sql, parameterList);
	}

    /**
     * 修改投注模式信息
     * @param existHaId     已存在盘口代理id
     * @param existHmId     已存在盘口会员id
     * @param betModeInfo   投注模式信息
     */
    public void updateBetModeInfo(String existHaId, String existHmId, JSONObject betModeInfo) throws SQLException {
        String sql="update ibmc_agent_member_info set BET_MODE_INFO_=?,UPDATE_TIME_LONG_ = ?,DESC_=?"
                + " where EXIST_HA_ID_ = ? and EXIST_HM_ID_=? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(betModeInfo.toString());
        parameterList.add(System.currentTimeMillis());
        parameterList.add("修改游戏投注模式");
        parameterList.add(existHaId);
        parameterList.add(existHmId);
        parameterList.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql,parameterList);
    }

	/**
	 * 修改投注模式信息
	 * @param existHmId     已存在盘口会员id
	 * @param betModeInfo   投注模式信息
	 */
	public void updateBetModeInfo(String existHmId, JSONObject betModeInfo) throws SQLException {
		String sql="update ibmc_agent_member_info set BET_MODE_INFO_=?,UPDATE_TIME_LONG_ = ?,DESC_=?"
				+ " where EXIST_HM_ID_=? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(betModeInfo.toString());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("修改游戏投注模式");
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql,parameterList);
	}
}
