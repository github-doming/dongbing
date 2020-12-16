package com.ibm.follow.servlet.client.ibmc_ha_game_set.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.client.ibmc_ha_game_set.entity.IbmcHaGameSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmcHaGameSetService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcHaGameSet对象数据
	 */
	public String save(IbmcHaGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_ha_game_set的 IBMC_HA_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_ha_game_set set state_='DEL' where IBMC_HA_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HA_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_ha_game_set的 IBMC_HA_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_ha_game_set set state_='DEL' where IBMC_HA_GAME_SET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_ha_game_set的 IBMC_HA_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_ha_game_set where IBMC_HA_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HA_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_ha_game_set的 IBMC_HA_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_ha_game_set where IBMC_HA_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHaGameSet实体信息
	 *
	 * @param entity IbmcHaGameSet实体
	 */
	public void update(IbmcHaGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_ha_game_set表主键查找IbmcHaGameSet实体
	 *
	 * @param id ibmc_ha_game_set 主键
	 * @return IbmcHaGameSet实体
	 */
	public IbmcHaGameSet find(String id) throws Exception {
		return (IbmcHaGameSet) this.dao.find(IbmcHaGameSet.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_ha_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_ha_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_ha_game_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHaGameSet数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcHaGameSet数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_ha_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_ha_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_ha_game_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHaGameSet.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_ha_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHaGameSet数据信息
	 *
	 * @return 可用<IbmcHaGameSet>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_ha_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHaGameSet.class, sql);
	}
	/**
	 * 获取盘口代理设置信息
	 *
	 * @param existHaId 已存在盘口代理id
	 * @param gameCode  游戏code
	 * @return 置信息
	 */
	public IbmcHaGameSet findExist(String existHaId, String gameCode) throws Exception {
		String sql = "select * from ibmc_ha_game_set where EXIST_HA_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHaId);
		parameters.add(gameCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return (IbmcHaGameSet) super.dao.findObject(IbmcHaGameSet.class, sql, parameters);
	}

	/**
	 * 获取游戏设置信息
	 *
	 * @param existHaId 已存在盘口代理id
	 * @param gameCode  游戏code
	 * @return 设置信息
	 */
	public Map<String, Object> findSet(String existHaId, String gameCode) throws SQLException {
		String sql =
				"select BET_STATE_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_,BET_FILTER_NUMBER_, "
						+ " BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,FILTER_PROJECT_"
						+ " from ibmc_ha_game_set where EXIST_HA_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHaId);
		parameters.add(gameCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMap(sql, parameters);
	}

	/**
	 * 获取游戏设置主键
	 *
	 * @param existHaId 已存在盘口代理主键
	 * @param gameCode  游戏编码
	 * @return 游戏设置主键
	 */
	public String findId(String existHaId, String gameCode) throws SQLException {
		String sql = "select IBMC_HA_GAME_SET_ID_ from ibmc_ha_game_set where EXIST_HA_ID_ = ? and GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHaId);
		parameters.add(gameCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findString("IBMC_HA_GAME_SET_ID_", sql, parameters);

	}

	/**
	 * 更新盘口会员投注状态
	 *
	 * @param haGameSetId 盘口代理游戏设置主键
	 * @param betState    投注状态
	 */
	public void updateBetState(String haGameSetId, String betState) throws SQLException {
		String sql = "UPDATE ibmc_ha_game_set SET BET_STATE_ = ?,UPDATE_TIME_= ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ "WHERE IBMC_HA_GAME_SET_ID_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(betState);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("设置投注状态");
		parameters.add(haGameSetId);
		parameters.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 更新客户端代理盘口设置信息
	 *
	 * @param haGameSetId     客户端盘口代理游戏设置主键
	 * @param filterProject 投注状态
	 */
	public void updateFilter(String haGameSetId, String filterProject) throws SQLException {
		String sql = "UPDATE ibmc_ha_game_set SET FILTER_PROJECT_ = ? ,DESC_ = ? ,UPDATE_TIME_= ? " +
				" WHERE IBMC_HA_GAME_SET_ID_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(9);
		parameters.add(filterProject);
		parameters.add("更新代理盘口设置信息");
		parameters.add(new Date());
		parameters.add(haGameSetId);
		parameters.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}
	/**
	 * 获取盘口代理所有游戏设置
	 *
	 * @param existHaId 已存在盘口代理id
	 * @return 设置id数组
	 */
	public List<String> findIds(String existHaId) throws SQLException {
		String sql="select IBMC_HA_GAME_SET_ID_ from ibmc_ha_game_set where EXIST_HA_ID_=? and STATE_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(existHaId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("IBMC_HA_GAME_SET_ID_",sql,parameters);
	}

    /**
     * 批量新增
     * @param agentGameInfos    代理游戏设置信息
     * @param existInfos        存在信息
     * @return
     */
    public void save(JSONArray agentGameInfos, Map<String, Object> existInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        Date nowTime=new Date();
        sql.append("insert into ibmc_ha_game_set (IBMC_HA_GAME_SET_ID_,EXIST_HA_ID_,GAME_CODE_,BET_STATE_,BET_FOLLOW_MULTIPLE_T_,"
                + "BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_,BET_FILTER_NUMBER_,BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,"
                + "CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        for(int i=0;i<agentGameInfos.size();i++){
            JSONObject gameInfos=agentGameInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(existInfos.get(gameInfos.get("HANDICAP_AGENT_ID_")));
            parameters.add(gameInfos.get("GAME_CODE_"));
            parameters.add(gameInfos.get("BET_STATE_"));
            parameters.add(gameInfos.get("BET_FOLLOW_MULTIPLE_T_"));
            parameters.add(gameInfos.get("BET_LEAST_AMOUNT_T_"));
            parameters.add(gameInfos.get("BET_MOST_AMOUNT_T_"));
            parameters.add(gameInfos.get("BET_FILTER_NUMBER_"));
            parameters.add(gameInfos.get("BET_FILTER_TWO_SIDE_"));
            parameters.add(gameInfos.get("NUMBER_OPPOSING_"));
            parameters.add(gameInfos.get("TWO_SIDE_OPPOSING_"));
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(IbmStateEnum.OPEN.name());
        }
        sql.delete(sql.length()-1,sql.length());
        super.dao.execute(sql.toString(),parameters);
    }

    /**
     * 获取投注状态为true的游戏设置信息
     *
     * @param handicapAgentIds 盘口代理ids
     * @return
     */
    public List<Map<String, Object>> findGameInfo(List<String> handicapAgentIds) throws SQLException {
        StringBuilder sql=new StringBuilder("select ihgs.EXIST_HA_ID_,ihgs.GAME_CODE_,ieh.HANDICAP_CODE_ from ibmc_ha_game_set ihgs"
                + " LEFT JOIN ibmc_exist_ha ieh ON ihgs.EXIST_HA_ID_=ieh.IBMC_EXIST_HA_ID_ WHERE ihgs.BET_STATE_=? and ieh.HANDICAP_AGENT_ID_ in(");
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmTypeEnum.TRUE.name());
        for(Object handicapAgentId:handicapAgentIds){
            sql.append("?,");
            parameters.add(handicapAgentId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findMapList(sql.toString(),parameters);
    }
}
