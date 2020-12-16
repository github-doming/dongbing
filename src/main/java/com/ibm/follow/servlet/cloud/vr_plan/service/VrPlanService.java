package com.ibm.follow.servlet.cloud.vr_plan.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_plan.entity.VrPlan;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* VR_方案 服务类
 * @author Robot
 */
public class VrPlanService extends BaseServiceProxy {

	/**
	 * 保存VR_方案 对象数据
	 * @param entity VrPlan对象数据
	 */
	public String save(VrPlan entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_plan 的 VR_PLAN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_plan set state_='DEL' where VR_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_PLAN_ID_主键id数组的数据
	 * @param idArray 要删除 vr_plan 的 VR_PLAN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_plan set state_='DEL' where VR_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_plan  的 VR_PLAN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_plan where VR_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_PLAN_ID_主键id数组的数据
	 * @param idArray 要删除vr_plan 的 VR_PLAN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_plan where VR_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrPlan实体信息
	 * @param entity VR_方案 实体
	 */
	public void update(VrPlan entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_plan表主键查找 VR_方案 实体
	 * @param id vr_plan 主键
	 * @return VR_方案 实体
	 */
	public VrPlan find(String id) throws Exception {
		return dao.find(VrPlan.class,id);
	}

	/**
	 * 获取分页信息
	 *
	 * @param planName  方案名称
	 * @param pageIndex 页数
	 * @param pageSize  条数
	 * @return
	 */
	public PageCoreBean<Map<String, Object>> listShow(String planName, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "select count(VR_PLAN_ID_) from vr_plan where STATE_!=? ";
		String sql = "SELECT VR_PLAN_ID_ PLAN_ID_,PLAN_NAME_,SN_,PLAN_CODE_,AVAILABLE_GAME_TYPE_,STATE_,DESC_ FROM `vr_plan` where STATE_!=? ";
		ArrayList<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbmStateEnum.DEL.name());
		String sqlPlus = "";
		if (StringTool.notEmpty(planName)) {
			sqlPlus += " and PLAN_NAME_ like ?";
			parameterList.add("%" + planName + "%");
		}
		sqlPlus += " order by SN_";
		sqlCount += sqlPlus;
		sql += sqlPlus;
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 更新方案状态
	 * @param planId
	 * @param state
	 * @throws SQLException
	 */
	public void updatePlanState(String planId, String state) throws SQLException {
		String sql = "UPDATE %s SET `UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=?, `STATE_`=? WHERE PLAN_ID_=? AND STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(state);
		parameterList.add(planId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(String.format(sql,"vr_plan_game"),parameterList);
		super.dao.execute(String.format(sql,"vr_plan_hm"),parameterList);
	}

	/**
	 * 获取方案ID
	 *
	 * @param planCode 方案编码
	 * @return 方案ID
	 */
	public String findId(String planCode) throws SQLException {
		String sql = "SELECT VR_PLAN_ID_ FROM `vr_plan` where PLAN_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString("VR_PLAN_ID_", sql, parameterList);
	}

	/**
	 * 获取方案ID
	 *
	 * @param planCode 方案编码
	 * @return 方案ID
	 */
	public VrPlan findPlan(String planCode) throws Exception {
		String sql = "SELECT * FROM `vr_plan` where PLAN_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		return dao.findObject(VrPlan.class,sql,parameterList);
	}

}
