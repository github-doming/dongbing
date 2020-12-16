package org.doming.develop.system.bean;

import java.io.Serializable;

/**
 * 磁盘信息
 *
 * @Author: Dongming
 * @Date: 2020-04-15 10:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DiskInfoBean implements Cloneable, Serializable {
	/**
	 * 磁盘名称
	 */
	private String path;

	/**
	 * 空闲空间
	 */
	private long freeSpace;
	/**
	 * 使用空间
	 */
	private long usableSpace;
	/**
	 * 总空间
	 */
	private long totalSpace;


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(long freeSpace) {
		this.freeSpace = freeSpace;
	}

	public long getUsableSpace() {
		return usableSpace;
	}

	public void setUsableSpace(long usableSpace) {
		this.usableSpace = usableSpace;
	}

	public long getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(long totalSpace) {
		this.totalSpace = totalSpace;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
