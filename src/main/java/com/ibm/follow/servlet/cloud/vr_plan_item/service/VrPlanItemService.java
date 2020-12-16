package com.ibm.follow.servlet.cloud.vr_plan_item.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_plan_item.entity.VrPlanItem;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * VR_方案详情信息 服务类
 *
 * @author Robot
 */
public class VrPlanItemService extends BaseServiceProxy {

	/**
	 * 保存VR_方案详情信息 对象数据
	 *
	 * @param entity VrPlanItem对象数据
	 */
	public String save(VrPlanItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除vr_plan_item 的 VR_PLAN_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_plan_item set state_='DEL' where VR_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_PLAN_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 vr_plan_item 的 VR_PLAN_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_plan_item set state_='DEL' where VR_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 vr_plan_item  的 VR_PLAN_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_plan_item where VR_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_PLAN_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除vr_plan_item 的 VR_PLAN_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_plan_item where VR_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrPlanItem实体信息
	 *
	 * @param entity VR_方案详情信息 实体
	 */
	public void update(VrPlanItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_plan_item表主键查找 VR_方案详情信息 实体
	 *
	 * @param id vr_plan_item 主键
	 * @return VR_方案详情信息 实体
	 */
	public VrPlanItem find(String id) throws Exception {
		return dao.find(VrPlanItem.class, id);
	}


	/**
	 * 获取方案详情信息
	 *
	 * @param planCode 方案编码
	 * @return 详情信息
	 */
	public Map<String, Object> findPlanItemInfo(String planCode) throws SQLException {
		String sql = "SELECT vp.SN_,vp.plan_name_,vp.PLAN_CODE_,vpi.PROFIT_LIMIT_MAX_,vpi.LOSS_LIMIT_MIN_," +
				"vpi.FUNDS_LIST_,vpi.FOLLOW_PERIOD_,vpi.MONITOR_PERIOD_,vpi.EXPAND_INFO_,vpi.BET_MODE_," +
				"vpi.FUND_SWAP_MODE_,vpi.PERIOD_ROLL_MODE_,vpi.PLAN_GROUP_DATA_,vpi.PLAN_GROUP_ACTIVE_KEY_ " +
				" FROM vr_plan_item vpi LEFT JOIN vr_plan vp ON vp.PLAN_CODE_=vpi.PLAN_CODE_ " +
				" WHERE vp.PLAN_CODE_ = ? AND vp.STATE_!=? AND vpi.STATE_!=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(planCode);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}


}
