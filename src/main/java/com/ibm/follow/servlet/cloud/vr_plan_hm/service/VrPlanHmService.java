package com.ibm.follow.servlet.cloud.vr_plan_hm.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_plan_hm.entity.VrPlanHm;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * VR_会员与方案 服务类
 *
 * @author Robot
 */
public class VrPlanHmService extends BaseServiceProxy {

	/**
	 * 保存VR_会员与方案 对象数据
	 *
	 * @param entity VrPlanHm对象数据
	 */
	public String save(VrPlanHm entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除vr_plan_hm 的 VR_PLAN_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_plan_hm set state_='DEL' where VR_PLAN_HM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_PLAN_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 vr_plan_hm 的 VR_PLAN_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_plan_hm set state_='DEL' where VR_PLAN_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 vr_plan_hm  的 VR_PLAN_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_plan_hm where VR_PLAN_HM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_PLAN_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除vr_plan_hm 的 VR_PLAN_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_plan_hm where VR_PLAN_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrPlanHm实体信息
	 *
	 * @param entity VR_会员与方案 实体
	 */
	public void update(VrPlanHm entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_plan_hm表主键查找 VR_会员与方案 实体
	 *
	 * @param id vr_plan_hm 主键
	 * @return VR_会员与方案 实体
	 */
	public VrPlanHm find(String id) throws Exception {
		return dao.find(VrPlanHm.class, id);
	}

	/**
	 * 查询会员方案信息
	 *
	 * @param gameCode 游戏编码
	 * @param memberId 会员ID
	 * @return 方案信息
	 */
	public List<Map<String, Object>> findHmPlanInfo(String gameCode, String memberId) throws SQLException {
		String  sql =  "SELECT vph.GAME_CODE_,vph.PLAN_CODE_,vph.VR_PLAN_HM_ID_,vph.PLAN_ITEM_TABLE_ID_," +
				"vph.PLAN_NAME_,vph.PLAN_STATE_ FROM vr_plan_hm vph LEFT JOIN vr_plan vp ON vph.PLAN_CODE_ = vp.PLAN_CODE_ " +
				" WHERE vph.VR_MEMBER_ID_ = ? AND vph.STATE_!=? AND vph.GAME_CODE_ =? ORDER BY vp.SN_" ;
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(memberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(gameCode);

		return super.findMapList(sql, parameterList);
	}

	/**
	 *  查询会员方案信息
	 * @param memberId 会员ID
	 * @param planId 方案ID
	 * @param gameCode 游戏编码
	 * @return 方案信息
	 */
	public Map<String,Object> findInfo(String memberId,String planId,String gameCode) throws SQLException {
		String  sql = "SELECT PLAN_CODE_,PLAN_NAME_,PLAN_STATE_ FROM vr_plan_hm WHERE VR_MEMBER_ID_ = ? AND STATE_!=? AND PLAN_ID_ =? AND GAME_CODE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(memberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(planId);
		parameterList.add(gameCode);
		return super.findMap(sql,parameterList);
	}
}
