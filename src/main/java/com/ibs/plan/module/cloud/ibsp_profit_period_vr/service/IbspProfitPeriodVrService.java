package com.ibs.plan.module.cloud.ibsp_profit_period_vr.service;

import com.ibs.plan.module.cloud.ibsp_profit_period_vr.entity.IbspProfitPeriodVr;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* IBSP_盘口会员当期模拟盈亏 服务类
 * @author Robot
 */
public class IbspProfitPeriodVrService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员当期模拟盈亏 对象数据
	 * @param entity IbspProfitPeriodVr对象数据
	 */
	public String save(IbspProfitPeriodVr entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_profit_period_vr 的 IBSP_PROFIT_PERIOD_VR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_profit_period_vr set state_='DEL' where IBSP_PROFIT_PERIOD_VR_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PROFIT_PERIOD_VR_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_profit_period_vr 的 IBSP_PROFIT_PERIOD_VR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_profit_period_vr set state_='DEL' where IBSP_PROFIT_PERIOD_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_profit_period_vr  的 IBSP_PROFIT_PERIOD_VR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_profit_period_vr where IBSP_PROFIT_PERIOD_VR_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PROFIT_PERIOD_VR_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_profit_period_vr 的 IBSP_PROFIT_PERIOD_VR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_profit_period_vr where IBSP_PROFIT_PERIOD_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspProfitPeriodVr实体信息
	 * @param entity IBSP_盘口会员当期模拟盈亏 实体
	 */
	public void update(IbspProfitPeriodVr entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_profit_period_vr表主键查找 IBSP_盘口会员当期模拟盈亏 实体
	 * @param id ibsp_profit_period_vr 主键
	 * @return IBSP_盘口会员当期模拟盈亏 实体
	 */
	public IbspProfitPeriodVr find(String id) throws Exception {
		return dao.find(IbspProfitPeriodVr.class,id);
	}
}
