package org.doming.develop.system.service;

import org.doming.develop.system.bean.CpuInfoBean;
import org.doming.develop.system.bean.DiskInfoBean;
import org.doming.develop.system.bean.MemoryInfoBean;
import org.doming.develop.system.bean.MonitorInfoBean;

import java.util.List;

/**
 * 获取系统信息的业务逻辑类接口
 *
 * @Author: Dongming
 * @Date: 2020-04-15 11:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface MonitorService {
	/**
	 * 获得当前的监控对象.
	 *
	 * @return 返回构造好的监控对象
	 * @throws Exception 获取异常
	 */
	MonitorInfoBean getMonitorInfoBean() throws Exception;

	/**
	 * 获得CPU监控对象.
	 *
	 * @return 返回构造好的监控对象
	 * @throws Exception 获取异常
	 */
	CpuInfoBean getCpuInfo() throws Exception;

	/**
	 * 获得内存监控对象.
	 *
	 * @return 返回构造好的监控对象
	 */
	MemoryInfoBean getMemoryInfo() ;

	/**
	 * 获得磁盘监控对象.
	 *
	 * @return 返回构造好的监控对象
	 */
	List<DiskInfoBean> getDiskInfos();
}
