package com.ibs.plan.module.cloud.ibsp_sys_thread_pool.service;


import com.ibs.plan.module.cloud.ibsp_sys_thread_pool.entity.IbspSysThreadPool;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBM_线程池信息 服务类
 * @author Robot
 */
public class IbspSysThreadPoolService extends BaseServiceProxy {

	/**
	 * 保存IBM_线程池信息 对象数据
	 * @param entity IbmSysThreadPool对象数据
	 */
	public String save(IbspSysThreadPool entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_sys_thread_pool 的 IBM_SYS_THREAD_POOL_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_sys_thread_pool set state_='DEL' where IBSP_SYS_THREAD_POOL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_THREAD_POOL_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_sys_thread_pool 的 IBM_SYS_THREAD_POOL_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_sys_thread_pool set state_='DEL' where IBSP_SYS_THREAD_POOL_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_sys_thread_pool  的 IBM_SYS_THREAD_POOL_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_sys_thread_pool where IBSP_SYS_THREAD_POOL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_THREAD_POOL_ID_主键id数组的数据
	 * @param idArray 要删除ibm_sys_thread_pool 的 IBM_SYS_THREAD_POOL_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_sys_thread_pool where IBSP_SYS_THREAD_POOL_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysThreadPool实体信息
	 * @param entity IBM_线程池信息 实体
	 */
	public void update(IbspSysThreadPool entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_thread_pool表主键查找 IBM_线程池信息 实体
	 * @param id ibm_sys_thread_pool 主键
	 * @return IBM_线程池信息 实体
	 */
	public IbspSysThreadPool find(String id) throws Exception {
		return  dao.find(IbspSysThreadPool.class,id);

	}

	/**
	 * 更新
	 * @param ip 服务IP
	 * @param config 配置信息
	 */
	public void update(String ip, Map<String, Object> config) throws Exception {
		Date nowTime = new Date();
		IbspSysThreadPool threadPool = 	find(ip,config.get("threadCode"));
		if (threadPool == null){
			threadPool = new IbspSysThreadPool();
			threadPool.setServerIp(ip);
			threadPool.setModuleCode(config.get("moduleCode"));
			threadPool.setModuleName(config.get("moduleName"));
			threadPool.setThreadCode(config.get("threadCode"));
			threadPool.setPoolSize(config.get("poolSize"));
			threadPool.setCoreSize(config.get("corePoolSize"));
			threadPool.setMaxSize(config.get("maximumPoolSize"));
			threadPool.setQueueSize(config.get("maxSize"));
			threadPool.setActiveCount(config.get("activeCount"));
			threadPool.setTaskCount(config.get("taskCount"));
			threadPool.setCompletedTaskCount(config.get("completedTaskCount"));
			threadPool.setKeepAliveTime(config.get("keepAliveTimeSeconds"));
			threadPool.setCreateTime(nowTime);
			threadPool.setCreateTimeLong(System.currentTimeMillis());
			threadPool.setUpdateTime(nowTime);
			threadPool.setUpdateTimeLong(System.currentTimeMillis());
			threadPool.setState(StateEnum.OPEN.name());
			save(threadPool);
		}else {
			threadPool.setPoolSize(config.get("poolSize"));

			;
			threadPool.setKeepAliveTime(config.get("keepAliveTimeSeconds"));
			threadPool.setUpdateTime(nowTime);
			threadPool.setUpdateTimeLong(System.currentTimeMillis());
			update(threadPool);
		}
	}

	/**
	 * 查找线程信息
	 * @param ip 服务ip
	 * @return 线程信息
	 */
	public IbspSysThreadPool find(String ip, Object threadCode) throws Exception {
		String sql = "SELECT * FROM ibsp_sys_thread_pool where SERVER_IP_ = ? and THREAD_CODE_ = ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(ip);
		parameters.add(threadCode);
		parameters.add(StateEnum.DEL.name());
		return super.dao.findObject(IbspSysThreadPool.class,sql,parameters);
	}

	/**
	 * 获取线程模块信息
	 */
	public List<Map<String, Object>> show() throws SQLException {
		String sql="select SERVER_IP_,MODULE_CODE_,MODULE_NAME_,THREAD_CODE_,CORE_SIZE_,MAX_SIZE_,"
				+ "QUEUE_SIZE_ from ibsp_sys_thread_pool where STATE_ = ?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(StateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameters);
	}
}
