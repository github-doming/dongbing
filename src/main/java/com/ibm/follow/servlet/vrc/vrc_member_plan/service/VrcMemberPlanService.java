package com.ibm.follow.servlet.vrc.vrc_member_plan.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.vrc.vrc_member_plan.entity.VrcMemberPlan;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* VRC客户端_会员与方案 服务类
 * @author Robot
 */
public class VrcMemberPlanService extends BaseServiceProxy {

	/**
	 * 保存VRC客户端_会员与方案 对象数据
	 * @param entity VrcMemberPlan对象数据
	 */
	public String save(VrcMemberPlan entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vrc_member_plan 的 VRC_MEMBER_PLAN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_member_plan set state_='DEL' where VRC_MEMBER_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_MEMBER_PLAN_ID_主键id数组的数据
	 * @param idArray 要删除 vrc_member_plan 的 VRC_MEMBER_PLAN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_member_plan set state_='DEL' where VRC_MEMBER_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vrc_member_plan  的 VRC_MEMBER_PLAN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_member_plan where VRC_MEMBER_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_MEMBER_PLAN_ID_主键id数组的数据
	 * @param idArray 要删除vrc_member_plan 的 VRC_MEMBER_PLAN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_member_plan where VRC_MEMBER_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcMemberPlan实体信息
	 * @param entity VRC客户端_会员与方案 实体
	 */
	public void update(VrcMemberPlan entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_member_plan表主键查找 VRC客户端_会员与方案 实体
	 * @param id vrc_member_plan 主键
	 * @return VRC客户端_会员与方案 实体
	 */
	public VrcMemberPlan find(String id) throws Exception {
		return dao.find(VrcMemberPlan.class,id);
	}

	/**
	 * 获取会员方案信息
	 * @param existMemberId
	 * @param planCode
	 * @return
	 * @throws Exception
	 */
	public VrcMemberPlan find(String existMemberId,String planCode,String gameCode) throws Exception {
		String sql = "SELECT * FROM `vrc_member_plan` WHERE EXIST_MEMBER_VR_ID_ = ? AND PLAN_CODE_= ? AND GAME_CODE_=? AND state_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(existMemberId);
		parameterList.add(planCode);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findObject(VrcMemberPlan.class,sql,parameterList);
	}
	/**
	 * 获取开启的方案信息
	 *
	 * @param gameCode 游戏编码
	 * @param drawType 游戏类型
	 * @return
	 */
	public List<String> getOpenItemIds(GameUtil.Code gameCode, String drawType) throws SQLException {
		String sql = "SELECT vmp.PLAN_ITEM_ID_ FROM vrc_member_plan vmp"
				+ " LEFT JOIN vrc_member_game_set vmgs ON vmp.EXIST_MEMBER_VR_ID_=vmgs.EXIST_MEMBER_VR_ID_"
				+ " AND vmp.GAME_CODE_=vmgs.GAME_CODE_"
				+ " WHERE vmp.GAME_CODE_ =? AND vmgs.GAME_DRAW_TYPE_ =? AND vmp.PLAN_STATE_ =?"
				+ " AND vmp.STATE_ =? AND vmgs.BET_STATE_=? AND vmgs.STATE_ =?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(gameCode.name());
		parameters.add(drawType);
		parameters.add(IbmTypeEnum.TRUE.name());
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmTypeEnum.TRUE.name());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("PLAN_ITEM_ID_", sql, parameters);
	}


}
