package com.ibm.follow.servlet.cloud.ibm_hm_set.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_set.entity.IbmHmSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHmSetService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHmSet对象数据
	 */
	public String save(IbmHmSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_hm_set的 IBM_HM_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_set set state_='DEL' where IBM_HM_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_set的 IBM_HM_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_hm_set set state_='DEL' where IBM_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_hm_set的 IBM_HM_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_set where IBM_HM_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_set的 IBM_HM_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_hm_set where IBM_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHmSet实体信息
	 *
	 * @param entity IbmHmSet实体
	 */
	public void update(IbmHmSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_hm_set表主键查找IbmHmSet实体
	 *
	 * @param id ibm_hm_set 主键
	 * @return IbmHmSet实体
	 */
	public IbmHmSet find(String id) throws Exception {
		return (IbmHmSet) this.dao.find(IbmHmSet.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_hm_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmSet数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHmSet数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHmSet.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmSet数据信息
	 *
	 * @return 可用<IbmHmSet>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmSet.class, sql);
	}

	/**
	 * 获取盘口会员的设置展示信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 盈亏信息
	 */
	public Map<String, Object> findShowInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT BET_MERGER_,BET_RECORD_ROWS_,BET_RATE_T_,PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_,"
				+ " PROFIT_LIMIT_MIN_ FROM `ibm_hm_set` where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 通过盘口会员id获取止盈止损信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 止盈止损信息
	 */
	public Map<String, Object> findByHmId(String handicapMemberId) throws SQLException {
		String sql = "select PROFIT_LIMIT_MAX_,PROFIT_LIMIT_MIN_,LOSS_LIMIT_MIN_,BET_RATE_T_,BET_MERGER_"
				+ " from ibm_hm_set where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

    /**
     * 获取止盈止损信息
     * @param handicapMemberIds     会员ids
     * @return
     */
    public List<Map<String, Object>> findByHmIds(List<String> handicapMemberIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("select HANDICAP_MEMBER_ID_,BET_RATE_T_,BET_MERGER_"
                + " from ibm_hm_set where STATE_=? and HANDICAP_MEMBER_ID_ in(");
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.OPEN.name());
        for(String handicapMemberId:handicapMemberIds){
            sql.append("?,");
            parameterList.add(handicapMemberId);
        }
        sql.replace(sql.length()-1,sql.length(),")");

        return super.dao.findMapList(sql.toString(), parameterList);
    }
	/**
	 * 获取盘口设置初始化信息
	 *
	 * @return 盘口会员初始化信息
	 */
	public Map<String, Object> findInitInfo() throws SQLException {
		String sql = "select BET_RECORD_ROWS_,BET_RATE_T_,PROFIT_LIMIT_MAX_,PROFIT_LIMIT_MIN_,LOSS_LIMIT_MIN_,BET_MERGER_ from ibm_hm_set where STATE_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEF.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 获取实体对象
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 实体对象
	 */
	public IbmHmSet findEntityByHmId(String handicapMemberId) throws Exception {
		String sql = "select * from ibm_hm_set where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return (IbmHmSet) super.dao.findObject(IbmHmSet.class, sql, parameterList);
	}

	/**
	 * 获取配置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 配置信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId) throws Exception {
		String sql = "select BET_RATE_T_,BET_MERGER_ from ibm_hm_set where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 根据盘口会员主键获取限制信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 限制信息
	 */
	public Map<String, Object> findLimitInfoByHMId(String handicapMemberId) throws SQLException {
		String sql = "SELECT PROFIT_LIMIT_MAX_,PROFIT_LIMIT_MIN_,LOSS_LIMIT_MIN_ FROM `ibm_hm_set` where "
				+ " HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 更新实体
	 * @param handicapMemberId
	 * @param betMerger
	 * @param nowTime
	 * @param desc
	 * @return 更新是否成功
	 * @throws SQLException
	 */
	public boolean updateEntity(String handicapMemberId,String betMerger, Date nowTime,String desc) throws SQLException {
		String sql = "UPDATE ibm_hm_set SET BET_MERGER_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_= ?,DESC_ = ? WHERE HANDICAP_MEMBER_ID_=? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(betMerger);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		int changenum = super.dao.execute(sql,parameterList);
		return changenum >= 1;

	}

}
