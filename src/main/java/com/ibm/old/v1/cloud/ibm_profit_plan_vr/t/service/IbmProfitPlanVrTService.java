package com.ibm.old.v1.cloud.ibm_profit_plan_vr.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_profit_plan_vr.t.entity.IbmProfitPlanVrT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmProfitPlanVrTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmProfitPlanVrT对象数据
	 */
	public String save(IbmProfitPlanVrT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_profit_plan_vr的 IBM_PROFIT_PLAN_VR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_profit_plan_vr set state_='DEL' where IBM_PROFIT_PLAN_VR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PROFIT_PLAN_VR_ID_主键id数组的数据
	 * @param idArray 要删除ibm_profit_plan_vr的 IBM_PROFIT_PLAN_VR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_profit_plan_vr set state_='DEL' where IBM_PROFIT_PLAN_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_profit_plan_vr的 IBM_PROFIT_PLAN_VR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_profit_plan_vr where IBM_PROFIT_PLAN_VR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PROFIT_PLAN_VR_ID_主键id数组的数据
	 * @param idArray 要删除ibm_profit_plan_vr的 IBM_PROFIT_PLAN_VR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_profit_plan_vr where IBM_PROFIT_PLAN_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmProfitPlanVrT实体信息
	 * @param entity IbmProfitPlanVrT实体
	 */
	public void update(IbmProfitPlanVrT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_profit_plan_vr表主键查找IbmProfitPlanVrT实体
	 * @param id ibm_profit_plan_vr 主键
	 * @return IbmProfitPlanVrT实体
	 */
	public IbmProfitPlanVrT find(String id) throws Exception {
		return (IbmProfitPlanVrT) this.dao.find(IbmProfitPlanVrT. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_profit_plan_vr where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit_plan_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_profit_plan_vr  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmProfitPlanVrT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmProfitPlanVrT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_profit_plan_vr where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit_plan_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_profit_plan_vr  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmProfitPlanVrT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit_plan_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmProfitPlanVrT数据信息
	 * @return 可用<IbmProfitPlanVrT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit_plan_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmProfitPlanVrT. class,sql);
	}

	/**
	 * 获取盘口会员方案盈亏主键
	 *
	 * @param profitId 盘口会员盈亏主键
	 * @param planId   方案主键
	 * @return 盘口会员方案盈亏主键
	 */
	public String findId(String profitId, String planId) throws SQLException {
		String sql = "SELECT IBM_PROFIT_PLAN_VR_ID_ FROM `ibm_profit_plan_vr` where PROFIT_VR_ID_ = ? "
				+ " and PLAN_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(profitId);
		parameterList.add(planId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_PROFIT_PLAN_VR_ID_", sql, parameterList);
	}

	/**
	 * 更新盈利信息
	 *
	 * @param profitPlanId 盘口会员方案盈亏主键
	 * @param profitT      投注盈亏
	 * @param betFundsT    投注积分
	 * @param betLen       投注项长度
	 * @param nowTime      更新时间
	 */
	public void updateProfit(String profitPlanId, long profitT, long betFundsT, int betLen, Date nowTime,String className)
			throws SQLException {
		String sql = "UPDATE ibm_profit_plan_vr SET DESC_=?,PROFIT_T_ = PROFIT_T_ + ?, BET_FUNDS_T_ = BET_FUNDS_T_ + ?, "
				+ " BET_LEN_ = BET_LEN_ + ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? WHERE IBM_PROFIT_PLAN_VR_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(className+"更新盈利信息");
		parameterList.add(profitT);
		parameterList.add(betFundsT);
		parameterList.add(betLen);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(profitPlanId);
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 重置盘口会员方案模拟盈亏信息
	 * @param handicapMemberId		盘口会员id
	 * @throws SQLException
	 */
	public void resetPlanProfitVr(String handicapMemberId,String className) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("select IBM_PROFIT_PLAN_VR_ID_ from ibm_profit_plan_vr where HANDICAP_MEMBER_ID_=? and STATE_=?");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<String> profitPlanIds=super.dao.findStringList("IBM_PROFIT_PLAN_VR_ID_",sql.toString(),parameterList);
		if(ContainerTool.isEmpty(profitPlanIds)){
			return ;
		}
		parameterList.clear();
		sql.delete(0,sql.length());
		sql.append("update ibm_profit_plan_vr set DESC_=?,STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_PROFIT_PLAN_VR_ID_ in (");
		parameterList.add(className+"重置盘口会员方案模拟盈亏信息");
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for (String profitPlanId:profitPlanIds){
			sql.append("?,");
			parameterList.add(profitPlanId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(),parameterList);
	}
	/**
	 * 获取盘口会员方案模拟盈亏信息
	 * @param handicapMemberId
	 * @return
	 * @throws Exception
	 */
	public List<IbmProfitPlanVrT> findByHmId(String handicapMemberId) throws Exception {
		String sql="select * from ibm_profit_plan_vr where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findObjectList(IbmProfitPlanVrT.class,sql,parameterList);
	}
}
