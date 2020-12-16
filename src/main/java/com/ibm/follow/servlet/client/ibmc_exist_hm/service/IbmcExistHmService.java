package com.ibm.follow.servlet.client.ibmc_exist_hm.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.client.ibmc_exist_hm.entity.IbmcExistHm;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmcExistHmService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcExistHm对象数据
	 */
	public String save(IbmcExistHm entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_exist_hm的 IBMC_EXIST_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_exist_hm set state_='DEL' where IBMC_EXIST_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_EXIST_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_exist_hm的 IBMC_EXIST_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibmc_exist_hm set state_='DEL' where IBMC_EXIST_HM_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_exist_hm的 IBMC_EXIST_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_exist_hm where IBMC_EXIST_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_EXIST_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_exist_hm的 IBMC_EXIST_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_exist_hm where IBMC_EXIST_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcExistHm实体信息
	 *
	 * @param entity IbmcExistHm实体
	 */
	public void update(IbmcExistHm entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_exist_hm表主键查找IbmcExistHm实体
	 *
	 * @param id ibmc_exist_hm 主键
	 * @return IbmcExistHm实体
	 */
	public IbmcExistHm find(String id) throws Exception {
		return (IbmcExistHm) this.dao.find(IbmcExistHm.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_exist_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_exist_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcExistHm数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcExistHm数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_exist_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_exist_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcExistHm.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT IBMC_EXIST_HM_ID_ as existHmId,HANDICAP_CODE_ as handicapCode FROM ibmc_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcExistHm数据信息
	 *
	 * @return 可用<IbmcExistHm>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_exist_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcExistHm.class, sql);
	}
	/**
	 * 判断该服务中开启某盘口会员的所在盘口
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return 包含  HANDICAP_CODE_
	 */
	public String findHandicapCode(String existHmId) throws SQLException {
		String sql = "SELECT HANDICAP_CODE_ FROM ibmc_exist_hm where IBMC_EXIST_HM_ID_ = ? and state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("HANDICAP_CODE_", sql, parameters);
	}
	/**
	 * 获取已存在的盘口会员
	 *
	 * @param handicapCode 盘口code
	 * @return 已存在的盘口会员数量信息 <br>
	 * exitsData    存在信息<br>
	 * exitsCount 存在数量 <br>
	 * handicapExitsCount 盘口存在数量
	 */
	public Map<String, Object> findExitsCount(String handicapCode) throws SQLException {
		Map<String, Object> exitsCount = new HashMap<>(2);
		String sql = "SELECT count(*) AS cnt FROM ibmc_exist_hm  where state_ = ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbmStateEnum.OPEN.name());
		String total = super.dao.findString("cnt", sql, parameters);
		exitsCount.put("exitsCount", total);
		sql += " and HANDICAP_CODE_ = ? ";
		parameters.add(handicapCode);
		//查询出已开启的盘口中会员数量
		String cnt = super.dao.findString("cnt", sql, parameters);
		exitsCount.put("capacityHm", cnt);
		return exitsCount;
	}
	/**
	 * 添加已存在盘口会员信息
	 *
	 * @param existHmId        c存盘口会员id
	 * @param handicapMemberId 盘口会员id
	 * @param handicapCode     盘口code
	 * @return 已存在盘口会员id
	 */
	public String save(String existHmId, String handicapMemberId, String handicapCode) throws Exception {
		IbmcExistHm existHm = new IbmcExistHm();
		existHm.setIbmcExistHmId(existHmId);
		existHm.setHandicapMemberId(handicapMemberId);
		existHm.setHandicapCode(handicapCode);
		existHm.setCreateTime(new Date());
		existHm.setCreateTimeLong(System.currentTimeMillis());
		existHm.setState(IbmStateEnum.OPEN.name());
		return this.save(existHm);
	}
	/**
	 * 清除盘口会员信息
	 *
	 * @param existHmId 已存在盘口会员id
	 */
	public void removeExistHmInfo(String existHmId) throws SQLException {
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.DEL.name());
		//已存在盘口会员
		String sql = "update ibmc_exist_hm set STATE_ =?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where IBMC_EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//代理会员信息
		sql = "update ibmc_agent_member_info set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//盘口会员
		sql = "update ibmc_handicap_member set STATE_= ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//会员投注
		sql = "update ibmc_hm_bet set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//会员投注异常
		sql = "update ibmc_hm_bet_error set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//会员投注失败
		sql = "update ibmc_hm_bet_fail set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//会员投注信息
		sql = "update ibmc_hm_bet_info set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//会员投注详情
		sql = "update ibmc_hm_bet_item set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//盘口会员游戏设置
		sql = "update ibmc_hm_game_set set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//盘口会员信息
		sql = "update ibmc_hm_info set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
		//盘口会员设置
		sql = "update ibmc_hm_set set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where EXIST_HM_ID_=? and STATE_!=?";
		super.dao.execute(sql, parameters);
	}

    /**
     * 批量删除会员信息
     * @param existHmIds    存在会员ids
     */
    public void removeExistHmInfo(List<String> existHmIds) throws SQLException {
        StringBuilder sqlPlus=new StringBuilder();
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(new Date());
        parameters.add(System.currentTimeMillis());
        parameters.add(IbmStateEnum.DEL.name());
        for(String existHmId:existHmIds){
            sqlPlus.append("?,");
            parameters.add(existHmId);
        }
        sqlPlus.replace(sqlPlus.length()-1,sqlPlus.length(),")");
        //已存在盘口会员
        String sql = "update ibmc_exist_hm set STATE_ =?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and IBMC_EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //代理会员信息
        sql = "update ibmc_agent_member_info set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //盘口会员
        sql = "update ibmc_handicap_member set STATE_= ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //会员投注
        sql = "update ibmc_hm_bet set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //会员投注异常
        sql = "update ibmc_hm_bet_error set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //会员投注失败
        sql = "update ibmc_hm_bet_fail set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //会员投注信息
        sql = "update ibmc_hm_bet_info set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //会员投注详情
        sql = "update ibmc_hm_bet_item set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //盘口会员游戏设置
        sql = "update ibmc_hm_game_set set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //盘口会员信息
        sql = "update ibmc_hm_info set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
        //盘口会员设置
        sql = "update ibmc_hm_set set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=? and EXIST_HM_ID_ in(";
        super.dao.execute(sql.concat(sqlPlus.toString()), parameters);
    }

	/**
	 * 删除定时任务信息
	 * @param existId		存在id
	 */
	public void removeQuertzInfo(String existId) throws SQLException {
		List<Object> parameters=new ArrayList<>();
		//定时任务处理
		String sql="select IBM_SYS_QUARTZ_TASK_ID_ from ibm_sys_quartz_task where TASK_NAME_ like ? and STATE_!=?";
		parameters.add(existId.concat("%"));
		parameters.add(IbmStateEnum.DEL.name());
		List<String> quartzTaskIds=super.dao.findStringList("IBM_SYS_QUARTZ_TASK_ID_",sql,parameters);
		if(ContainerTool.notEmpty(quartzTaskIds)){
			parameters.clear();
			StringBuilder sqlBuilder=new StringBuilder();
			sql="update ibm_sys_quartz_task set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where IBM_SYS_QUARTZ_TASK_ID_ in (";
			parameters.add(IbmStateEnum.DEL.name());
			parameters.add(new Date());
			parameters.add(System.currentTimeMillis());
			for(String quartzTaskId:quartzTaskIds){
				sqlBuilder.append("?,");
				parameters.add(quartzTaskId);
			}
			sqlBuilder.replace(sqlBuilder.length()-1,sqlBuilder.length(),")");

			super.dao.execute(sql.concat(sqlBuilder.toString()), parameters);
			//IBM_定时器运行设置
			sql="update ibm_sys_quartz_simple set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where QUARTZ_TASK_ID_ in (";
			super.dao.execute(sql.concat(sqlBuilder.toString()), parameters);
			//IBM_定时器CRON
			sql="update ibm_sys_quartz_cron set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where QUARTZ_TASK_ID_ in (";
			super.dao.execute(sql.concat(sqlBuilder.toString()), parameters);
		}
	}

    /**
     * 批量清除quertz信息
     */
    public void removeQuertzInfo() throws SQLException {
        List<Object> parameters=new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(new Date());
        parameters.add(System.currentTimeMillis());
        parameters.add(IbmStateEnum.DEL.name());
        String sql="update ibm_sys_quartz_task set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=?";
        super.dao.execute(sql, parameters);
        //IBM_定时器运行设置
        sql="update ibm_sys_quartz_simple set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=?";
        super.dao.execute(sql, parameters);
        //IBM_定时器CRON
        sql="update ibm_sys_quartz_cron set STATE_=?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where STATE_!=?";
        super.dao.execute(sql, parameters);

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
			Map<String, Object> exitsHmIds = listExitsHmId(code.name());
			exitsCount += exitsHmIds.size();
			exitsInfo.put(code.name(), exitsHmIds);
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
    private Map<String, Object> listExitsHmId(String handicapCode) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ as key_,IBMC_EXIST_HM_ID_ as value_ FROM ibmc_exist_hm  where HANDICAP_CODE_ = ? and state_ = ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(handicapCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}
	/**
	 * 获取已存在盘口会员id
	 *
	 * @return 已存在盘口会员ids
	 */
	public List<String> findExistIds() throws SQLException {
		String sql = "select IBMC_EXIST_HM_ID_ from ibmc_exist_hm where STATE_!='DEL'";
		return super.dao.findStringList("IBMC_EXIST_HM_ID_", sql, null);
	}

	/**
	 * 查找会员存在ID
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 已存在盘口会员id
	 */
	public String findId(String handicapMemberId) throws SQLException {
		String sql = "select IBM_CLIENT_HM_ID_ from ibm_client_hm where HANDICAP_MEMBER_ID_=? AND STATE_=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(handicapMemberId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_CLIENT_HM_ID_", sql, parameters);
	}

	/**
	 * 获取状态集合
	 *
	 * @return 状态集合
	 */
	public Map<Object, Object> mapState() throws SQLException {
		String sql = "SELECT IBMC_EXIST_HM_ID_ as key_,state_ as value_ FROM ibmc_exist_hm  where state_ != 'DEL'";
		return super.findKVMap(sql, null);
	}

	/**
	 * 获取重新加载的基础信息
	 * @return 会员账户信息
	 */
	public List<Map<String, Object>> listInfo2reload() throws SQLException {
		String sql = "SELECT IBMC_EXIST_HM_ID_,HANDICAP_CODE_,MEMBER_ACCOUNT_,MEMBER_PASSWORD_,HANDICAP_URL_, "
				+ " HANDICAP_CAPTCHA_ FROM `ibmc_exist_hm` ieh LEFT JOIN ibmc_handicap_member ihm ON "
				+ " ieh.IBMC_EXIST_HM_ID_ = ihm.EXIST_HM_ID_ where ieh.STATE_ = ? and ihm.STATE_ = ? ORDER BY HANDICAP_CODE_";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取迁移数据的基础信息
	 * @return 会员迁移信息
	 */
	public List<Map<String, Object>> listInfo2migrate() throws SQLException {
		String sql = "SELECT HANDICAP_CODE_,HANDICAP_MEMBER_ID_ FROM `ibmc_exist_hm` WHERE STATE_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

    /**
     * 批量添加已存在会员信息
     * @param handicapMemberInfos   会员账号信息
     * @return
     */
    public List<String> save(JSONArray handicapMemberInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        Date nowTime=new Date();
        sql.append("insert into ibmc_exist_hm (IBMC_EXIST_HM_ID_,HANDICAP_MEMBER_ID_,HANDICAP_CODE_,CREATE_TIME_,CREATE_TIME_LONG_,"
                + "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<String> handicapMemberIds=new ArrayList<>();
        for(int i=0;i<handicapMemberInfos.size();i++){
            JSONObject handicapMemberInfo=handicapMemberInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(handicapMemberInfo.get("HANDICAP_MEMBER_ID_"));
            parameters.add(handicapMemberInfo.get("HANDICAP_CODE_"));
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(IbmStateEnum.OPEN.name());

            handicapMemberIds.add(handicapMemberInfo.get("HANDICAP_MEMBER_ID_").toString());
        }
        sql.delete(sql.length()-1,sql.length());
        super.dao.execute(sql.toString(),parameters);

        return handicapMemberIds;
    }

    /**
     * 获取存在id信息
     * @param handicapMemberIds     会员ids
     * @return
     */
    public Map<String, Object> findExitsInfo(List<String> handicapMemberIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("select HANDICAP_MEMBER_ID_ as key_,IBMC_EXIST_HM_ID_ as value_ from ibmc_exist_hm where HANDICAP_MEMBER_ID_ in(");
        List<Object> parameters = new ArrayList<>();
        for(String handicapMemberId:handicapMemberIds){
            sql.append("?,");
            parameters.add(handicapMemberId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.findKVMap(sql.toString(),parameters);
    }

    /**
     * 获取存在会员列表
     * @param handicapCode  盘口code
     * @return
     */
    public List<String> findByHandicapCode(HandicapUtil.Code handicapCode) throws SQLException {
        String sql="select IBMC_EXIST_HM_ID_ from ibmc_exist_hm where HANDICAP_CODE_=? and STATE_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(handicapCode);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("IBMC_EXIST_HM_ID_",sql,parameters);
    }

    /**
     * 获取状态
     * @param existHmId     存在会员id
     * @return
     */
    public String findState(String existHmId) throws SQLException {
        String sql="select STATE_ from ibmc_exist_hm where IBMC_EXIST_HM_ID_=? and STATE_!=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(existHmId);
        parameters.add(IbmStateEnum.DEL.name());
        return super.dao.findString("STATE_",sql,parameters);
    }
	/**
	 * 根据盘口会员id查找存在主键
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 存在主键
	 */
	public String findIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "SELECT IBMC_EXIST_HM_ID_ as key_ FROM ibmc_exist_hm where HANDICAP_MEMBER_ID_ = ? and state_!= ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapMemberId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.findString(sql, parameters);
	}
}
