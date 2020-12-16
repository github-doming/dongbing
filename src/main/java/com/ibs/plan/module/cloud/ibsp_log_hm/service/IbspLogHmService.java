package com.ibs.plan.module.cloud.ibsp_log_hm.service;

import com.ibs.plan.module.cloud.ibsp_log_hm.entity.IbspLogHm;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* IBSP_盘口会员操作日志 服务类
 * @author Robot
 */
public class IbspLogHmService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员操作日志 对象数据
	 * @param entity IbspLogHm对象数据
	 */
	public String save(IbspLogHm entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_log_hm 的 IBSP_LOG_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_log_hm set state_='DEL' where IBSP_LOG_HM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_LOG_HM_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_log_hm 的 IBSP_LOG_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_log_hm set state_='DEL' where IBSP_LOG_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_log_hm  的 IBSP_LOG_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_log_hm where IBSP_LOG_HM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_LOG_HM_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_log_hm 的 IBSP_LOG_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_log_hm where IBSP_LOG_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspLogHm实体信息
	 * @param entity IBSP_盘口会员操作日志 实体
	 */
	public void update(IbspLogHm entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_log_hm表主键查找 IBSP_盘口会员操作日志 实体
	 * @param id ibsp_log_hm 主键
	 * @return IBSP_盘口会员操作日志 实体
	 */
	public IbspLogHm find(String id) throws Exception {
		return dao.find(IbspLogHm.class,id);
	}
}
