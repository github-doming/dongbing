package com.ibm.follow.servlet.cloud.ibm_log_thread_item.service;

import com.ibm.follow.servlet.cloud.ibm_log_thread_item.entity.IbmLogThreadItem;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* IBM_线程池详情日志 服务类
 * @author Robot
 */
public class IbmLogThreadItemService extends BaseServiceProxy {

	/**
	 * 保存IBM_线程池详情日志 对象数据
	 * @param entity IbmLogThreadItem对象数据
	 */
	public String save(IbmLogThreadItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_log_thread_item 的 IBM_LOG_THREAD_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_log_thread_item set state_='DEL' where IBM_LOG_THREAD_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_LOG_THREAD_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_log_thread_item 的 IBM_LOG_THREAD_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_log_thread_item set state_='DEL' where IBM_LOG_THREAD_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_log_thread_item  的 IBM_LOG_THREAD_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_log_thread_item where IBM_LOG_THREAD_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_LOG_THREAD_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除ibm_log_thread_item 的 IBM_LOG_THREAD_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_log_thread_item where IBM_LOG_THREAD_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmLogThreadItem实体信息
	 * @param entity IBM_线程池详情日志 实体
	 */
	public void update(IbmLogThreadItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_log_thread_item表主键查找 IBM_线程池详情日志 实体
	 * @param id ibm_log_thread_item 主键
	 * @return IBM_线程池详情日志 实体
	 */
	public IbmLogThreadItem find(String id) throws Exception {
		return (IbmLogThreadItem) dao.find(IbmLogThreadItem.class,id);

	}
}
