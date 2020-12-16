package org.doming.develop.system.bean;

import org.doming.core.tools.ContainerTool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 监控信息
 *
 * @Author: Dongming
 * @Date: 2020-04-15 11:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MonitorInfoBean implements Cloneable, Serializable {
	CpuInfoBean cpuInfo;
	MemoryInfoBean memoryInfo;
	List<DiskInfoBean> diskInfoBeans;

	public CpuInfoBean getCpuInfo() {
		return cpuInfo;
	}

	public void setCpuInfo(CpuInfoBean cpuInfo) {
		this.cpuInfo = cpuInfo;
	}

	public MemoryInfoBean getMemoryInfo() {
		return memoryInfo;
	}

	public void setMemoryInfo(MemoryInfoBean memoryInfo) {
		this.memoryInfo = memoryInfo;
	}

	public List<DiskInfoBean> getDiskInfoBeans() {
		return diskInfoBeans;
	}

	public void setDiskInfoBeans(List<DiskInfoBean> diskInfoBeans) {
		this.diskInfoBeans = diskInfoBeans;
	}

	/**
	 * 赋值监控信息
	 *
	 * @param cpuInfo       CPU信息
	 * @param memoryInfo    内存信息
	 * @param diskInfoBeans 磁盘信息
	 * @return 监控信息
	 */
	public MonitorInfoBean attr(CpuInfoBean cpuInfo, MemoryInfoBean memoryInfo, List<DiskInfoBean> diskInfoBeans) throws Exception {
		this.cpuInfo = (CpuInfoBean) cpuInfo.clone();
		this.memoryInfo = (MemoryInfoBean) memoryInfo.clone();
		this.diskInfoBeans = new ArrayList<>(diskInfoBeans.size());
		this.diskInfoBeans = (List<DiskInfoBean>) ContainerTool.deepCopy(diskInfoBeans);
		return this;
	}
}
