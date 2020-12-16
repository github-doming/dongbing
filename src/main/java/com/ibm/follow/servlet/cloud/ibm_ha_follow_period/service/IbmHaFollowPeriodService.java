package com.ibm.follow.servlet.cloud.ibm_ha_follow_period.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_follow_period.entity.IbmHaFollowPeriod;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBM_盘口代理当期跟投信息
 服务类
 * @author Robot
 */
public class IbmHaFollowPeriodService extends BaseServicePlus {

	/**
	 * 保存IBM_盘口代理当期跟投信息
 对象数据
	 * @param entity IbmHaFollowPeriod对象数据
	 */
	public String save(IbmHaFollowPeriod entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_ha_follow_period 的 IBM_HA_FOLLOW_PERIOD_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_ha_follow_period set state_='DEL' where IBM_HA_FOLLOW_PERIOD_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HA_FOLLOW_PERIOD_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_ha_follow_period 的 IBM_HA_FOLLOW_PERIOD_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_ha_follow_period set state_='DEL' where IBM_HA_FOLLOW_PERIOD_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_ha_follow_period  的 IBM_HA_FOLLOW_PERIOD_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_ha_follow_period where IBM_HA_FOLLOW_PERIOD_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HA_FOLLOW_PERIOD_ID_主键id数组的数据
	 * @param idArray 要删除ibm_ha_follow_period 的 IBM_HA_FOLLOW_PERIOD_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_ha_follow_period where IBM_HA_FOLLOW_PERIOD_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHaFollowPeriod实体信息
	 * @param entity IBM_盘口代理当期跟投信息
 实体
	 */
	public void update(IbmHaFollowPeriod entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_ha_follow_period表主键查找 IBM_盘口代理当期跟投信息
 实体
	 * @param id ibm_ha_follow_period 主键
	 * @return IBM_盘口代理当期跟投信息
 实体
	 */
	public IbmHaFollowPeriod find(String id) throws Exception {
		return (IbmHaFollowPeriod) this.dao.find(IbmHaFollowPeriod. class,id);

	}

    /**
     * 获取盘口代理游戏当期投注信息
     * @param handicapAgentId       盘口代理id
     * @param gameId                游戏id
     * @param period                期数
     * @return
     */
    public IbmHaFollowPeriod find(String handicapAgentId,String gameId,Object period) throws Exception {
	    String sql="select * from ibm_ha_follow_period where HANDICAP_AGENT_ID_=? and GAME_ID_=?"
                +" and PERIOD_=? and STATE_=?";
	    List<Object> parameters=new ArrayList<>(4);
	    parameters.add(handicapAgentId);
	    parameters.add(gameId);
	    parameters.add(period);
	    parameters.add(IbmStateEnum.OPEN.name());
	    return (IbmHaFollowPeriod) super.dao.findObject(IbmHaFollowPeriod.class,sql,parameters);
    }

    /**
     * 获取跟投执行信息
     * @param handicapAgentIds      盘口代理ids
     * @param gameId                游戏id
     * @param period                期数
     * @return
     */
    public List<String> findFollowInfo(List<String> handicapAgentIds, String gameId, String period) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters=new ArrayList<>();
        sql.append("select IBM_HA_FOLLOW_PERIOD_ID_ from ibm_ha_follow_period where GAME_ID_=? and PERIOD_=?")
                .append(" and EXEC_STATE_!=? and HANDICAP_AGENT_ID_ in(");
        parameters.add(gameId);
        parameters.add(period);
        parameters.add(IbmStateEnum.SUCCESS.name());
        for(String handicapAgentId:handicapAgentIds){
            sql.append("?,");
            parameters.add(handicapAgentId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findStringList("IBM_HA_FOLLOW_PERIOD_ID_",sql.toString(),parameters);
    }

    /**
     * 修改执行状态
     * @param haFollowPeriodIds     盘口代理当期跟投信息ids
     * @param requestType           执行状态
     */
    public void updateExecState(List<String> haFollowPeriodIds, IbmStateEnum requestType) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("update ibm_ha_follow_period set EXEC_STATE_=?,UPDATE_TIME_LONG_=? where IBM_HA_FOLLOW_PERIOD_ID_ in(");
        List<Object> parameters=new ArrayList<>();
        parameters.add(requestType.name());
        parameters.add(System.currentTimeMillis());
        for(String haFollowPeriodId:haFollowPeriodIds){
            sql.append("?,");
            parameters.add(haFollowPeriodId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        super.dao.execute(sql.toString(),parameters);
    }

    /**
     * 获取投注信息
     * @param handicapAgentId       盘口代理id
     * @param gameId                游戏id
     * @param number                获取条数
     * @return
     */
    public List<Map<String, Object>> findBetInfo(String handicapAgentId, String gameId, long number) throws SQLException {
        String sql="select IBM_HA_FOLLOW_PERIOD_ID_ as HA_FOLLOW_PERIOD_ID_,PERIOD_,BET_LEN_,BET_FUNDS_T_,FOLLOW_FUND_T_,EXEC_STATE_ "
                +" from ibm_ha_follow_period where HANDICAP_AGENT_ID_=? and GAME_ID_=? and STATE_=?"
                +" ORDER BY CREATE_TIME_LONG_ DESC limit "+number;
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapAgentId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql,parameterList);
    }
}
