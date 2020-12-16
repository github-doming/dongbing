package com.ibm.follow.servlet.cloud.ibm_ha_game_set.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.entity.IbmHaGameSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHaGameSetService extends BaseServiceProxy {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHaGameSet对象数据
	 */
	public String save(IbmHaGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_ha_game_set的 IBM_HA_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_ha_game_set set state_='DEL' where IBM_HA_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HA_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_ha_game_set的 IBM_HA_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_ha_game_set set state_='DEL' where IBM_HA_GAME_SET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_ha_game_set的 IBM_HA_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_ha_game_set where IBM_HA_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HA_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_ha_game_set的 IBM_HA_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_ha_game_set where IBM_HA_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHaGameSet实体信息
	 *
	 * @param entity IbmHaGameSet实体
	 */
	public void update(IbmHaGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_ha_game_set表主键查找IbmHaGameSet实体
	 *
	 * @param id ibm_ha_game_set 主键
	 * @return IbmHaGameSet实体
	 */
	public IbmHaGameSet find(String id) throws Exception {
		return (IbmHaGameSet) this.dao.find(IbmHaGameSet.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_ha_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_ha_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_ha_game_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHaGameSet数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHaGameSet数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_ha_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_ha_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_ha_game_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHaGameSet.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_ha_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHaGameSet数据信息
	 *
	 * @return 可用<IbmHaGameSet>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_ha_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHaGameSet.class, sql);
	}

	/**
	 * 获取展示的游戏修改信息列表
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 游戏修改信息列表
	 */
	public List<Map<String, Object>> listShow(String handicapAgentId) throws SQLException {
		String sql = "SELECT BET_STATE_, ihg.GAME_CODE_, ihg.GAME_NAME_, BET_FOLLOW_MULTIPLE_T_, "
				+ " BET_LEAST_AMOUNT_T_, BET_MOST_AMOUNT_T_, BET_FILTER_NUMBER_, BET_FILTER_TWO_SIDE_, NUMBER_OPPOSING_, "
				+ " TWO_SIDE_OPPOSING_, FILTER_PROJECT_, EXTENSION_SET_, BET_RECORD_ROWS_ FROM `ibm_ha_game_set` ihs "
				+ " LEFT JOIN ibm_handicap_game ihg on ihs.HANDICAP_ID_ = ihg.HANDICAP_ID_ and ihs.GAME_ID_ = ihg.GAME_ID_ "
				+ " WHERE HANDICAP_AGENT_ID_ = ? AND ihg.STATE_ =? and ihs.STATE_ = ? ORDER BY ihg.SN_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 修改盘口代理所有游戏状态
	 *
	 * @param handicapAgentId 盘口代理id
	 * @param nowTime         当前时间
	 */
	public void updateLogoutBetState(String handicapAgentId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_ha_game_set SET BET_STATE_ =?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " WHERE HANDICAP_AGENT_ID_ = ? AND BET_STATE_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmTypeEnum.FALSE.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(handicapAgentId);
		parameterList.add(IbmTypeEnum.TRUE.name());
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取代理游戏设置初始化信息
	 *
	 * @return 游戏设置初始化信息
	 */
	public Map<String, Object> findInitInfo() throws SQLException {
		String sql = "select BET_STATE_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_ "
				+ " ,BET_FILTER_NUMBER_,BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,BET_RECORD_ROWS_ "
				+ " from ibm_ha_game_set where STATE_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEF.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 获取代理游戏设置初始化信息
	 *
	 * @return 游戏设置初始化信息
	 */
	public IbmHaGameSet findDef() throws Exception {
		String sql = "select * from ibm_ha_game_set where STATE_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEF.name());
		return super.dao.findObject(IbmHaGameSet.class,sql, parameterList);
	}

	/**
	 * 获取游戏设置信息
	 *
	 * @param handicapAgentId 盘口代理id
	 * @param gameId          游戏id
	 * @return 游戏设置信息
	 */
	public IbmHaGameSet findByHaIdAndGameId(String handicapAgentId, String gameId) throws Exception {
		String sql = "select * from ibm_ha_game_set where HANDICAP_AGENT_ID_=? and GAME_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapAgentId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return (IbmHaGameSet) super.dao.findObject(IbmHaGameSet.class, sql, parameterList);
	}

	/**
	 * 根据盘口代理id和游戏id 查找 盘口代理游戏设置主键
	 *
	 * @param handicapAgentId 代理id
	 * @param gameId          游戏id
	 * @return 盘口代理游戏设置主键
	 */
	public String findId(String handicapAgentId, String gameId) throws SQLException {
		String sql = "select IBM_HA_GAME_SET_ID_ from ibm_ha_game_set where HANDICAP_AGENT_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapAgentId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString("IBM_HA_GAME_SET_ID_", sql, parameterList);
	}

	/**
	 * 更新 代理游戏跟投设置
	 *
	 * @param haGameSetId       盘口代理游戏设置主键
	 * @param betState          投注状态
	 * @param betFollowMultiple 跟投倍数
	 * @param betLeastAmount    最低金额
	 * @param betMostAmount     最高金额
	 * @param betFilterNumber   过滤数字
	 * @param betFilterTwoSide  过滤双面
	 * @param numberOpposing    数字反投
	 * @param twoSideOpposing   双面反投
	 * @param nowTime           更新时间
	 */
	public void update(String haGameSetId, String betState, int betFollowMultiple, int betLeastAmount,
			int betMostAmount, String betFilterNumber, String betFilterTwoSide, String numberOpposing,
			String twoSideOpposing, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_ha_game_set SET BET_STATE_ = ?, BET_FOLLOW_MULTIPLE_T_ = ?,BET_LEAST_AMOUNT_T_ = ?, "
				+ " BET_MOST_AMOUNT_T_ = ?,BET_FILTER_NUMBER_ = ?, BET_FILTER_TWO_SIDE_ = ?,NUMBER_OPPOSING_ = ?, "
				+ " TWO_SIDE_OPPOSING_ = ?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " WHERE IBM_HA_GAME_SET_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(13);

		parameterList.add(betState);
		parameterList.add(betFollowMultiple);
		parameterList.add(betLeastAmount);
		parameterList.add(betMostAmount);
		parameterList.add(betFilterNumber);
		parameterList.add(betFilterTwoSide);
		parameterList.add(numberOpposing);
		parameterList.add(twoSideOpposing);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("代理游戏跟投设置");
		parameterList.add(haGameSetId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);

	}

	public void updateFilterByGameId(String haGameSetId, String filterProject) throws SQLException {
		String sql = "UPDATE ibm_ha_game_set SET FILTER_PROJECT_ = ? WHERE IBM_HA_GAME_SET_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(filterProject);
		parameterList.add(haGameSetId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);
	}
	/**
	 * 获取盘口代理所有游戏设置信息
	 *
	 * @param handicapAgentId 盘口代理
	 * @return 游戏设置信息
	 */
	public List<Map<String, Object>> findByHaId(String handicapAgentId) throws SQLException {
		String sql = "select GAME_CODE_,BET_STATE_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_,"
				+ "BET_FILTER_NUMBER_,BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,"
				+ "FILTER_PROJECT_,EXTENSION_SET_ from ibm_ha_game_set ihgs LEFT JOIN ibm_game ig on"
				+ " ihgs.GAME_ID_=ig.IBM_GAME_ID_ where HANDICAP_AGENT_ID_=? and ihgs.STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapAgentId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}
    /**
     * 获取盘口代理所有游戏设置信息
     *
     * @param handicapAgentIds 盘口代理
     * @return 游戏设置信息
     */
    public List<Map<String, Object>> findByHaIds(List<String> handicapAgentIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("select HANDICAP_AGENT_ID_,GAME_CODE_,BET_STATE_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_,"
                + "BET_FILTER_NUMBER_,BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,"
                + "FILTER_PROJECT_,EXTENSION_SET_ from ibm_ha_game_set ihgs LEFT JOIN ibm_game ig on"
                + " ihgs.GAME_ID_=ig.IBM_GAME_ID_ where ihgs.STATE_=? and HANDICAP_AGENT_ID_ in(");
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.OPEN.name());
        for(String handicapAgentId:handicapAgentIds){
            sql.append("?,");
            parameterList.add(handicapAgentId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findMapList(sql.toString(), parameterList);
    }

	public void delData(String handicapAgentId, String gameId) throws SQLException {
		String sql = "update ibm_ha_game_set set STATE_ = ? WHERE HANDICAP_AGENT_ID_ = ? and GAME_ID_ = ? and STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(handicapAgentId);
		parameters.add(gameId);
		parameters.add(IbmStateEnum.OPEN.name());
		dao.execute(sql, parameters);

	}

	/**
	 * 删除盘口游戏的所有数据
	 * @param handicapId 盘口主键
	 * @param gameId 游戏主键
	 * @param desc 描述
	 * @param nowTime 更新时间
	 */
	public void delData(String handicapId, String gameId, String desc, Date nowTime) throws SQLException {
		String sql = "update ibm_ha_game_set set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE "
				+ " HANDICAP_ID_ = ? and GAME_ID_ = ? and STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(7);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(desc);
		parameters.add(handicapId);
		parameters.add(gameId);
		parameters.add(IbmStateEnum.OPEN.name());
		dao.execute(sql, parameters);
	}
	/**
	 * 获取游戏信息
	 *
	 * @param handicapAgentId 盘口代理主键
	 * @return 游戏信息
	 */
	public List<Map<String, Object>> listGameInfo(String handicapAgentId) throws SQLException {
		String sql = "SELECT BET_STATE_, GAME_NAME_, GAME_CODE_, ICON_ FROM ibm_handicap_game ihg LEFT JOIN "
				+ " ibm_ha_game_set ihs ON ihg.HANDICAP_ID_ = ihs.HANDICAP_ID_ AND ihg.GAME_ID_ = ihs.GAME_ID_ WHERE "
				+ " ihs.HANDICAP_AGENT_ID_ = ? AND ihg.STATE_=? AND ihs.STATE_ = ? ORDER BY ihg.SN_";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapAgentId);
        parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取正在托管的盘口代理主键
	 *
	 * @param handicapId 盘口主键
	 * @param gameId     游戏主键
	 * @return 盘口代理主键列表
	 */
	public List<String> listHostingHaId(String handicapId, String gameId) throws SQLException {
		String sql = "SELECT ihs.HANDICAP_AGENT_ID_ as key_ FROM `ibm_ha_game_set` ihs LEFT JOIN ibm_ha_info ihi ON "
				+ " ihs.HANDICAP_AGENT_ID_ = ihi.HANDICAP_AGENT_ID_ WHERE ihi.STATE_ = ? AND ihs.BET_STATE_ = ? AND "
				+ " ihs.HANDICAP_ID_ = ? AND ihs.GAME_ID_ = ? AND ihs.STATE_ = ?";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(IbmStateEnum.LOGIN.name());
		parameters.add(IbmTypeEnum.TRUE.name());
		parameters.add(handicapId);
		parameters.add(gameId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findStringList(sql, parameters);
	}

    public IbmHaGameSet find(String handicapAgentId, String gameId) throws Exception {
        String sql = "select * from ibm_ha_game_set where HANDICAP_AGENT_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapAgentId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return (IbmHaGameSet) super.dao.findObject(IbmHaGameSet.class,sql, parameterList);
    }

    /**
     * 修改投注状态
     * @param gameSetId     游戏设置id
     * @param betState      投注状态
     */
    public void updateBetState(String gameSetId, String betState) throws SQLException {
        String sql="update ibm_ha_game_set set BET_STATE_=?,UPDATE_TIME_LONG_=? where IBM_HA_GAME_SET_ID_=?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(betState);
        parameterList.add(System.currentTimeMillis());
        parameterList.add(gameSetId);
        super.dao.execute(sql,parameterList);
    }

    /**
     * 获取游戏设置信息
     * @param handicapAgentId       盘口代理id
     * @param gameId                游戏id
     * @return
     */
    public Map<String, Object> findGameSet(String handicapAgentId, String gameId) throws SQLException {
        String sql="select BET_STATE_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_,BET_FILTER_NUMBER_,BET_FILTER_TWO_SIDE_"
                +",NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,FILTER_PROJECT_ from ibm_ha_game_set where HANDICAP_AGENT_ID_=? and GAME_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapAgentId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql,parameterList);
    }

    /**
     * 获取游戏过滤项目信息
     * @param handicapAgentId   盘口代理id
     * @param gameId            游戏id
     * @return
     */
    public String findFilterProject(String handicapAgentId, String gameId) throws SQLException {
        String sql="select FILTER_PROJECT_ from ibm_ha_game_set where HANDICAP_AGENT_ID_=? and GAME_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapAgentId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findString("FILTER_PROJECT_",sql,parameterList);
    }

    /**
     * 获取游戏投注状态和游戏code
     * @param handicapAgentId   盘口代理id
     * @return
     */
    public List<Map<String, Object>> listGameInfos(String handicapAgentId) throws SQLException {
        String sql = "SELECT BET_STATE_, GAME_CODE_ FROM ibm_handicap_game ihg LEFT JOIN "
                + " ibm_ha_game_set ihs ON ihg.HANDICAP_ID_ = ihs.HANDICAP_ID_ AND ihg.GAME_ID_ = ihs.GAME_ID_ WHERE "
                + " ihs.HANDICAP_AGENT_ID_ = ? AND ihs.STATE_ = ? ORDER BY ihg.SN_";
        List<Object> parameters = new ArrayList<>(2);
        parameters.add(handicapAgentId);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findMapList(sql, parameters);
    }

    /**
     * 新增代理游戏设置信息
     * @param appUserId     用户id
     * @param agentInfo     代理信息
     * @param gameIds       游戏ids
     * @param initAgentGameSet  代理游戏设置
     */
    public void save(String appUserId, Map<String, Object> agentInfo, List<String> gameIds, Map<String, Object> initAgentGameSet) throws SQLException {
        StringBuilder sql=new StringBuilder("insert into ibm_ha_game_set(IBM_HA_GAME_SET_ID_,HANDICAP_AGENT_ID_,HANDICAP_ID_,"
                + "USER_ID_,GAME_ID_,BET_STATE_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_,BET_FILTER_NUMBER_,"
                + "BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,BET_RECORD_ROWS_,"
                + "CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<Object> parameterList = new ArrayList<>();
        Date nowTime = new Date();
        for(String gameId:gameIds){
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
            parameterList.add(UUID.randomUUID().toString().replace("-", ""));
            parameterList.add(agentInfo.get("IBM_HANDICAP_AGENT_ID_"));
            parameterList.add(agentInfo.get("HANDICAP_ID_"));
            parameterList.add(appUserId);
            parameterList.add(gameId);
            parameterList.add(initAgentGameSet.get("BET_STATE_"));
            parameterList.add(initAgentGameSet.get("BET_FOLLOW_MULTIPLE_T_"));
            parameterList.add(initAgentGameSet.get("BET_LEAST_AMOUNT_T_"));
            parameterList.add(initAgentGameSet.get("BET_MOST_AMOUNT_T_"));
            parameterList.add(initAgentGameSet.get("BET_FILTER_NUMBER_"));
            parameterList.add(initAgentGameSet.get("BET_FILTER_TWO_SIDE_"));
            parameterList.add(initAgentGameSet.get("NUMBER_OPPOSING_"));
            parameterList.add(initAgentGameSet.get("TWO_SIDE_OPPOSING_"));
            parameterList.add(initAgentGameSet.get("BET_RECORD_ROWS_"));
            parameterList.add(nowTime);
            parameterList.add(nowTime.getTime());
            parameterList.add(nowTime);
            parameterList.add(nowTime.getTime());
            parameterList.add(IbmStateEnum.OPEN.name());
        }
        sql.deleteCharAt(sql.length() - 1);
        super.dao.execute(sql.toString(), parameterList);
    }

    /**
     * 删除游戏设置信息
     * @param agenetInfo    代理信息
     * @param gameIds       游戏ids
     */
    public void delGameSet(Map<String, Object> agenetInfo, List<String> gameIds) throws SQLException {
        List<Object> parameterList = new ArrayList<>();
        StringBuilder sql=new StringBuilder();
        sql.append("update ibm_ha_game_set set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where HANDICAP_AGENT_ID_=? "
                + " and HANDICAP_ID_=? and GAME_ID_ in(");
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(agenetInfo.get("IBM_HANDICAP_AGENT_ID_"));
        parameterList.add(agenetInfo.get("HANDICAP_ID_"));
        for(String gameId:gameIds){
            sql.append("?,");
            parameterList.add(gameId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        super.dao.execute(sql.toString(),parameterList);
    }

    /**
     * 获取投注中的代理信息
     * @param agenetInfo    代理信息
     * @param gameIds       游戏ids
     * @return
     */
    public List<Map<String, Object>> findBetInfo(Map<String, Object> agenetInfo, List<String> gameIds) throws SQLException {
        List<Object> parameterList = new ArrayList<>();
        StringBuilder sql=new StringBuilder();
        sql.append("select HANDICAP_AGENT_ID_,GAME_ID_ from ibm_ha_game_set where HANDICAP_AGENT_ID_=? "
                + " and HANDICAP_ID_=? and BET_STATE_=? and GAME_ID_ in(");
        parameterList.add(agenetInfo.get("IBM_HANDICAP_AGENT_ID_"));
        parameterList.add(agenetInfo.get("HANDICAP_ID_"));
        parameterList.add(IbmTypeEnum.TRUE.name());
        for(String gameId:gameIds){
            sql.append("?,");
            parameterList.add(gameId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
       return super.dao.findMapList(sql.toString(),parameterList);
    }

    /**
     *
     * @param handicapAgentId
     * @return
     */
    public List<String> listGameCodes(String handicapAgentId) throws SQLException {
        String sql = "SELECT GAME_CODE_ FROM ibm_handicap_game ihg LEFT JOIN "
                + " ibm_ha_game_set ihgs ON ihg.HANDICAP_ID_ = ihgs.HANDICAP_ID_ AND ihg.GAME_ID_ = ihgs.GAME_ID_ "
                + " WHERE ihgs.HANDICAP_AGENT_ID_ = ? and ihgs.STATE_ = ? ORDER BY SN_";
        List<Object> parameters = new ArrayList<>(2);
        parameters.add(handicapAgentId);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findStringList("GAME_CODE_",sql, parameters);
    }

    /**
     * 获取代理游戏类型
     * @param handicapAgentId   盘口代理id
     * @return
     */
    public Map<String, Object> findGameType(String handicapAgentId) throws SQLException {
        String sql="SELECT GAME_CODE_ as key_,TYPE_ as value_ FROM ibm_handicap_game ihg"
                + " LEFT JOIN ibm_ha_game_set ihgs ON ihg.HANDICAP_ID_ = ihgs.HANDICAP_ID_ AND ihg.GAME_ID_ = ihgs.GAME_ID_"
                + " WHERE HANDICAP_AGENT_ID_=? and ihg.STATE_=? and ihgs.STATE_=?";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(handicapAgentId);
        parameters.add(IbmStateEnum.OPEN.name());
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findKVMap(sql,parameters);
    }
}
