package org.doming.develop.system.bean;

import java.io.Serializable;

/**
 * CPU信息
 *
 * @Author: Dongming
 * @Date: 2020-04-15 10:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CpuInfoBean implements Cloneable, Serializable {
	/**
	 * 操作系统.
	 */
	private String osName;

	/**
	 * 线程总数.
	 */
	private int totalThread;

	/**
	 * cpu使用率.
	 */
	private double cpuRatio;

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public int getTotalThread() {
		return totalThread;
	}

	public void setTotalThread(int totalThread) {
		this.totalThread = totalThread;
	}

	public double getCpuRatio() {
		return cpuRatio;
	}

	public void setCpuRatio(double cpuRatio) {
		this.cpuRatio = cpuRatio;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
