package org.doming.develop.system.bean;

import java.io.Serializable;

/**
 * 内存信息
 * @Author: Dongming
 * @Date: 2020-04-15 10:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemoryInfoBean implements Cloneable, Serializable {
	/**
	 * 总内存.
	 */
	private long totalPhysicalMemory;

	/**
	 * 剩余内存.
	 */
	private long freePhysicalMemory;

	/**
	 * 已使用内存.
	 */
	private long usedPhysicalMemory;
	/**
	 * 总内存.
	 */
	private long totalJvmMemory;

	/**
	 * 剩余内存.
	 */
	private long freeJvmMemory;
	/**
	 * 已使用内存.
	 */
	private long usedJvmMemory;

	/**
	 * 最大可获取内存.
	 */
	private long maxJvmMemory;


	public long getTotalPhysicalMemory() {
		return totalPhysicalMemory;
	}

	public void setTotalPhysicalMemory(long totalPhysicalMemory) {
		this.totalPhysicalMemory = totalPhysicalMemory;
	}

	public long getFreePhysicalMemory() {
		return freePhysicalMemory;
	}

	public void setFreePhysicalMemory(long freePhysicalMemory) {
		this.freePhysicalMemory = freePhysicalMemory;
	}

	public long getUsedPhysicalMemory() {
		return usedPhysicalMemory;
	}

	public void setUsedPhysicalMemory(long usedPhysicalMemory) {
		this.usedPhysicalMemory = usedPhysicalMemory;
	}

	public long getTotalJvmMemory() {
		return totalJvmMemory;
	}

	public void setTotalJvmMemory(long totalJvmMemory) {
		this.totalJvmMemory = totalJvmMemory;
	}

	public long getFreeJvmMemory() {
		return freeJvmMemory;
	}

	public void setFreeJvmMemory(long freeJvmMemory) {
		this.freeJvmMemory = freeJvmMemory;
	}

	public long getUsedJvmMemory() {
		return usedJvmMemory;
	}

	public void setUsedJvmMemory(long usedJvmMemory) {
		this.usedJvmMemory = usedJvmMemory;
	}

	public long getMaxJvmMemory() {
		return maxJvmMemory;
	}

	public void setMaxJvmMemory(long maxJvmMemory) {
		this.maxJvmMemory = maxJvmMemory;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
