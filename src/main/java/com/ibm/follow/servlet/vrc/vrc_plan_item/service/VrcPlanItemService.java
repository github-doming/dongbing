package com.ibm.follow.servlet.vrc.vrc_plan_item.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.vrc.vrc_plan_item.entity.VrcPlanItem;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* VRC方案详情 服务类
 * @author Robot
 */
public class VrcPlanItemService extends BaseServiceProxy {

	/**
	 * 保存VRC方案详情 对象数据
	 * @param entity VrcPlanItem对象数据
	 */
	public String save(VrcPlanItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vrc_plan_item 的 VRC_PLAN_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_plan_item set state_='DEL' where VRC_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_PLAN_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除 vrc_plan_item 的 VRC_PLAN_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_plan_item set state_='DEL' where VRC_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vrc_plan_item  的 VRC_PLAN_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_plan_item where VRC_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_PLAN_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除vrc_plan_item 的 VRC_PLAN_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_plan_item where VRC_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcPlanItem实体信息
	 * @param entity VRC方案详情 实体
	 */
	public void update(VrcPlanItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_plan_item表主键查找 VRC方案详情 实体
	 * @param id vrc_plan_item 主键
	 * @return VRC方案详情 实体
	 */
	public VrcPlanItem find(String id) throws Exception {
		return dao.find(VrcPlanItem.class,id);
	}

	public VrcPlanItem find(String existMemberId,String planCode) throws Exception {
		String sql = "SELECT * FROM `vrc_plan_item` where EXIST_MEMBER_VR_ID_ = ? and PLAN_CODE_=? AND STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(existMemberId);
		parameterList.add(planCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findObject(VrcPlanItem.class,sql,parameterList);
	}
	/**
	 * 获取开启的方案详情信息
	 * @param itemIds		详情ids
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> listInfo(List<String> itemIds) throws SQLException {
		StringBuilder sql = new StringBuilder("select VRC_PLAN_ITEM_ID_,EXIST_MEMBER_VR_ID_,PLAN_CODE_,FUNDS_LIST_,FOLLOW_PERIOD_,");
		sql.append("MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_,PERIOD_ROLL_MODE_,PLAN_GROUP_ACTIVE_DATA_,EXPAND_INFO_");
		sql.append(" from vrc_plan_item where VRC_PLAN_ITEM_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(itemIds.size());
		for (String itemId : itemIds) {
			sql.append("?,");
			parameterList.add(itemId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.findKeyMaps(sql,parameterList,"EXIST_MEMBER_VR_ID_");
	}


}
