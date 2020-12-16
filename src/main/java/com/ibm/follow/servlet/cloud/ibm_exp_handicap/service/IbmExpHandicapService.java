package com.ibm.follow.servlet.cloud.ibm_exp_handicap.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.follow.servlet.cloud.ibm_exp_handicap.entity.IbmExpHandicap;

import java.util.ArrayList;
import java.util.List;

/**
* IBM_盘口 服务类
 * @author Robot
 */
public class IbmExpHandicapService extends BaseServicePlus {

	/**
	 * 保存IBM_盘口 对象数据
	 * @param entity IbmExpHandicap对象数据
	 */
	public String save(IbmExpHandicap entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_exp_handicap 的 IBM_EXP_HANDICAP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_exp_handicap set state_='DEL' where IBM_EXP_HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EXP_HANDICAP_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_exp_handicap 的 IBM_EXP_HANDICAP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_exp_handicap set state_='DEL' where IBM_EXP_HANDICAP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_exp_handicap  的 IBM_EXP_HANDICAP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_exp_handicap where IBM_EXP_HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EXP_HANDICAP_ID_主键id数组的数据
	 * @param idArray 要删除ibm_exp_handicap 的 IBM_EXP_HANDICAP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_exp_handicap where IBM_EXP_HANDICAP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmExpHandicap实体信息
	 * @param entity IBM_盘口 实体
	 */
	public void update(IbmExpHandicap entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_exp_handicap表主键查找 IBM_盘口 实体
	 * @param id ibm_exp_handicap 主键
	 * @return IBM_盘口 实体
	 */
	public IbmExpHandicap find(String id) throws Exception {
		return (IbmExpHandicap) this.dao.find(IbmExpHandicap. class,id);

	}
}
