package com.ibs.plan.module.client.ibsc_hm_info.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.client.ibsc_hm_info.entity.IbscHmInfo;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * IBSC_客户端盘口会员信息 服务类
 *
 * @author Robot
 */
public class IbscHmInfoService extends BaseServiceProxy {

	/**
	 * 保存IBSC_客户端盘口会员信息 对象数据
	 *
	 * @param entity IbscHmInfo对象数据
	 */
	public String save(IbscHmInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_hm_info 的 IBSC_HM_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_hm_info set state_='DEL' where IBSC_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_hm_info 的 IBSC_HM_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsc_hm_info set state_='DEL' where IBSC_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_hm_info  的 IBSC_HM_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_hm_info where IBSC_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_hm_info 的 IBSC_HM_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_hm_info where IBSC_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscHmInfo实体信息
	 *
	 * @param entity IBSC_客户端盘口会员信息 实体
	 */
	public void update(IbscHmInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_hm_info表主键查找 IBSC_客户端盘口会员信息 实体
	 *
	 * @param id ibsc_hm_info 主键
	 * @return IBSC_客户端盘口会员信息 实体
	 */
	public IbscHmInfo find(String id) throws Exception {
		return dao.find(IbscHmInfo.class, id);

	}

	/**
	 * 根据存在主键查找会员信息
	 *
	 * @param existId 存在主键
	 * @return 会员信息
	 */
	public IbscHmInfo findByExistId(String existId) throws Exception {
		String sql = "SELECT * FROM ibsc_hm_info where EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbscHmInfo.class, sql, parameters);
	}
}
