package com.ibm.follow.servlet.cloud.vr_rank_monthly.service;

import com.ibm.follow.servlet.cloud.vr_rank_monthly.entity.VrRankMonthly;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* VR_月度榜单 服务类
 * @author Robot
 */
public class VrRankMonthlyService extends BaseServiceProxy {

	/**
	 * 保存VR_月度榜单 对象数据
	 * @param entity VrRankMonthly对象数据
	 */
	public String save(VrRankMonthly entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_rank_monthly 的 VR_RANK_MONTHLY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_rank_monthly set state_='DEL' where VR_RANK_MONTHLY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_RANK_MONTHLY_ID_主键id数组的数据
	 * @param idArray 要删除 vr_rank_monthly 的 VR_RANK_MONTHLY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_rank_monthly set state_='DEL' where VR_RANK_MONTHLY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_rank_monthly  的 VR_RANK_MONTHLY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_rank_monthly where VR_RANK_MONTHLY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_RANK_MONTHLY_ID_主键id数组的数据
	 * @param idArray 要删除vr_rank_monthly 的 VR_RANK_MONTHLY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_rank_monthly where VR_RANK_MONTHLY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrRankMonthly实体信息
	 * @param entity VR_月度榜单 实体
	 */
	public void update(VrRankMonthly entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_rank_monthly表主键查找 VR_月度榜单 实体
	 * @param id vr_rank_monthly 主键
	 * @return VR_月度榜单 实体
	 */
	public VrRankMonthly find(String id) throws Exception {
		return dao.find(VrRankMonthly.class,id);
	}
}
