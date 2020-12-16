package com.ibm.follow.servlet.client.ibmc_exist_ha.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.client.ibmc_exist_ha.entity.IbmcExistHa;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmcExistHaService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcExistHa对象数据
	 */
	public String save(IbmcExistHa entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_exist_ha的 IBMC_EXIST_HA_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_exist_ha set state_='DEL' where IBMC_EXIST_HA_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_EXIST_HA_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_exist_ha的 IBMC_EXIST_HA_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibmc_exist_ha set state_='DEL' where IBMC_EXIST_HA_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_exist_ha的 IBMC_EXIST_HA_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_exist_ha where IBMC_EXIST_HA_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_EXIST_HA_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_exist_ha的 IBMC_EXIST_HA_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_exist_ha where IBMC_EXIST_HA_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcExistHa实体信息
	 *
	 * @param entity IbmcExistHa实体
	 */
	public void update(IbmcExistHa entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_exist_ha表主键查找IbmcExistHa实体
	 *
	 * @param id ibmc_exist_ha 主键
	 * @return IbmcExistHa实体
	 */
	public IbmcExistHa find(String id) throws Exception {
		return (IbmcExistHa) this.dao.find(IbmcExistHa.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_exist_ha where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_exist_ha  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_exist_ha  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcExistHa数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcExistHa数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_exist_ha where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_exist_ha  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_exist_ha  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcExistHa.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT IBMC_EXIST_HA_ID_ as existHaId,HANDICAP_CODE_ as handicapCode FROM ibmc_exist_ha  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 查找盘口存在代理id
	 *
	 * @param handicapAgentId 盘口代理id
	 * @return 存在盘口代理id
	 */
	public String findExitsId(String handicapAgentId) throws SQLException {
		String sql = "SELECT IBMC_EXIST_HA_ID_ FROM ibmc_exist_ha where HANDICAP_AGENT_ID_ = ? and state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(handicapAgentId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBMC_EXIST_HA_ID_", sql, parameters);
	}
	/**
	 * 获取已存在的盘口代理
	 *
	 * @param handicapCode 盘口code
	 * @return
	 */
	public Map<String, Object> findExitsCount(String handicapCode) throws SQLException {
		Map<String, Object> exitsCount = new HashMap<>(2);
		String sql = "SELECT count(*) AS cnt FROM ibmc_exist_ha  where state_ = ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbmStateEnum.OPEN.name());
		String total = super.dao.findString("cnt", sql, parameters);
		exitsCount.put("exitsCount", total);
		sql += " and HANDICAP_CODE_ = ? ";
		parameters.add(handicapCode);
		//查询出已开启的盘口中会员数量
		String cnt = super.dao.findString("cnt", sql, parameters);
		exitsCount.put("capacityHa", cnt);
		return exitsCount;
	}
	/**
	 * 添加已存在盘口代理信息
	 *
	 * @param existHaId       存在盘口代理id
	 * @param handicapAgentId 盘口代理id
	 * @param handicapCode    盘口code
	 * @return 已存在盘口代理id
	 */
	public String save(String existHaId, String handicapAgentId, String handicapCode) throws Exception {
		IbmcExistHa existHa = new IbmcExistHa();
		existHa.setIbmcExistHaId(existHaId);
		existHa.setHandicapAgentId(handicapAgentId);
		existHa.setHandicapCode(handicapCode);
		existHa.setCreateTime(new Date());
		existHa.setCreateTimeLong(System.currentTimeMillis());
		existHa.setState(IbmStateEnum.OPEN.name());
		return this.save(existHa);
	}
	/**
	 * 清除已存在盘口代理信息
	 *
	 * @param existHaId 已存在盘口代理id
	 */
	public void removeExistHaInfo(String existHaId) throws SQLException {
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(existHaId);
		parameters.add(IbmStateEnum.DEL.name());
		//已存在盘口代理
		String sql = "update ibmc_exist_ha set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where IBMC_EXIST_HA_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//代理会员信息
		sql = "update ibmc_agent_member_info set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HA_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//代理跟随投注
		sql = "update ibmc_ha_follow_bet set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HA_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//代理游戏设置
		sql = "update ibmc_ha_game_set set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HA_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//代理设置
		sql = "update ibmc_ha_set set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HA_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//盘口代理信息
		sql = "update ibmc_ha_info set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HA_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//盘口代理
		sql = "update ibmc_handicap_agent set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HA_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
	}

    /**
     * 删除所有代理信息
     * @param existHaIds 已存在盘口代理ids
     */
    public void removeExistHaInfo(List<String> existHaIds) throws SQLException {
        StringBuilder sqlPlus=new StringBuilder();
        List<Object> parameters = new ArrayList<>(5);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(new Date());
        parameters.add(System.currentTimeMillis());
        parameters.add(IbmStateEnum.DEL.name());
        for(String existHaId:existHaIds){
            sqlPlus.append("?,");
            parameters.add(existHaId);
        }
        sqlPlus.replace(sqlPlus.length()-1,sqlPlus.length(),")");
        //已存在盘口代理
        String sql = "update ibmc_exist_ha set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where  STATE_!=? and IBMC_EXIST_HA_ID_ in( ";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //代理会员信息
        sql = "update ibmc_agent_member_info set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HA_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //代理跟随投注
        sql = "update ibmc_ha_follow_bet set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HA_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //代理游戏设置
        sql = "update ibmc_ha_game_set set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HA_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //代理设置
        sql = "update ibmc_ha_set set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HA_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //盘口代理信息
        sql = "update ibmc_ha_info set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HA_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //盘口代理
        sql = "update ibmc_handicap_agent set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HA_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
    }

	/**
	 * 获取存在信息
	 *
	 * @return 存在信息 《EXITS_COUNT，$handicapCode$》
	 */
	public Map<String, Object> findExitsInfo() throws SQLException {
		Map<String, Object> exitsInfo = new HashMap<>(HandicapUtil.codes().length + 1);
		int exitsCount = 0;
		for (HandicapUtil.Code code : HandicapUtil.codes()) {
			Map<String, Object> exitsHaIds = listExistHaId(code.name());
			exitsCount += exitsHaIds.size();
			exitsInfo.put(code.name(), exitsHaIds);
		}
		exitsInfo.put("EXITS_COUNT", exitsCount);
		return exitsInfo;

	}

	/**
	 * 获取存在盘口代理主键
	 *
	 * @param handicapCode 盘口编码
	 * @return 存在盘口代理主键列表
	 */
	public Map<String, Object> listExistHaId(String handicapCode) throws SQLException {
		String sql = "SELECT HANDICAP_AGENT_ID_ as key_,IBMC_EXIST_HA_ID_ as value_ FROM ibmc_exist_ha  where HANDICAP_CODE_ = ? and state_ = ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(handicapCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}
	/**
	 * 获取盘口code
	 *
	 * @param existHaId 已存在盘口代理id
	 * @return 盘口code
	 */
	public String findHandicapCode(String existHaId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_ FROM ibmc_exist_ha where IBMC_EXIST_HA_ID_ = ? and state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(existHaId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("HANDICAP_CODE_", sql, parameters);
	}
	/**
	 * 获取已存在盘口代理ids
	 *
	 * @return 已存在盘口代理ids
	 */
	public List<String> findExistIds() throws Exception {
		String sql = "SELECT IBMC_EXIST_HA_ID_ FROM ibmc_exist_ha  where state_!='DEL'";
		return super.dao.findStringList("IBMC_EXIST_HA_ID_", sql, null);
	}

	/**
	 * 获取状态集合
	 * @return 状态集合
	 */
	public Map<Object,Object> mapState() throws SQLException {
		String sql = "SELECT IBMC_EXIST_HA_ID_ as key_,state_ as value_ FROM ibmc_exist_ha  where state_ != 'DEL'";
		return super.findKVMap(sql,null);
	}

	/**
	 * 获取重新加载的基础信息
	 * @return 代理账户信息
	 */
	public List<Map<String, Object>> listInfo2reload() throws SQLException {
		String sql = "SELECT IBMC_EXIST_HA_ID_,HANDICAP_CODE_,AGENT_ACCOUNT_,AGENT_PASSWORD_,HANDICAP_URL_, "
				+ " HANDICAP_CAPTCHA_ FROM `ibmc_exist_ha` ieh LEFT JOIN ibmc_handicap_agent iha ON "
				+ " ieh.IBMC_EXIST_HA_ID_ = iha.EXIST_HA_ID_ where ieh.STATE_ = ? and iha.STATE_ = ? ORDER BY HANDICAP_CODE_";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取迁移数据的基础信息
	 * @return 代理迁移信息
	 */
	public List<Map<String, Object>> listInfo2migrate() throws SQLException {
		String sql = "SELECT HANDICAP_CODE_,HANDICAP_AGENT_ID_ FROM `ibmc_exist_ha` WHERE STATE_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

    /**
     * 批量新增已存在代理info
     * @param handicapAgentInfos    代理信息
     * @return
     */
    public List<String> save(JSONArray handicapAgentInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        Date nowTime=new Date();
        sql.append("insert into ibmc_exist_ha (IBMC_EXIST_HA_ID_,HANDICAP_AGENT_ID_,HANDICAP_CODE_,CREATE_TIME_,CREATE_TIME_LONG_,"
                + "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<String> handicapAgentIds=new ArrayList<>();
        for(int i=0;i<handicapAgentInfos.size();i++){
            JSONObject handicapAgentInfo=handicapAgentInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(handicapAgentInfo.get("HANDICAP_AGENT_ID_"));
            parameters.add(handicapAgentInfo.get("HANDICAP_CODE_"));
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(IbmStateEnum.OPEN.name());

            handicapAgentIds.add(handicapAgentInfo.get("HANDICAP_AGENT_ID_").toString());
        }
        sql.delete(sql.length()-1,sql.length());
        super.dao.execute(sql.toString(),parameters);

        return handicapAgentIds;
    }

    /**
     * 获取存在infos
     * @param handicapAgentIds      盘口代理ids
     * @return
     */
    public Map<String, Object> findExitsInfo(List<String> handicapAgentIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("select HANDICAP_AGENT_ID_ as key_,IBMC_EXIST_HA_ID_ as value_ from ibmc_exist_ha where HANDICAP_AGENT_ID_ in(");
        List<Object> parameters = new ArrayList<>();
        for(String handicapAgentId:handicapAgentIds){
            sql.append("?,");
            parameters.add(handicapAgentId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.findKVMap(sql.toString(),parameters);
    }
    /**
     * 获取存在代理列表
     * @param handicapCode  盘口code
     * @return
     */
    public List<String> findByHandicapCode(HandicapUtil.Code handicapCode) throws SQLException {
        String sql="select IBMC_EXIST_HA_ID_ from ibmc_exist_ha where HANDICAP_CODE_=? and STATE_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(handicapCode);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("IBMC_EXIST_HA_ID_",sql,parameters);
    }

    /**
     * 获取状态
     * @param existHaId     已存在盘口代理id
     * @return
     */
    public String findState(String existHaId) throws SQLException {
        String sql="select STATE_ from ibmc_exist_ha where IBMC_EXIST_HA_ID_=? and STATE_!=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(existHaId);
        parameters.add(IbmStateEnum.DEL.name());
        return super.dao.findString("STATE_",sql,parameters);
    }

	public String findIdByHaId(String handicapAgentId) throws SQLException {
		String sql = "SELECT IBMC_EXIST_HA_ID_ as key_ FROM ibmc_exist_ha where HANDICAP_AGENT_ID_ = ? and state_!= ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapAgentId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.findString(sql, parameters);
	}
}
