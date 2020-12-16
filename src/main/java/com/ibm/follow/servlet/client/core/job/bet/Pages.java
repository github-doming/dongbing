package com.ibm.follow.servlet.client.core.job.bet;
/**
 * @Author: Dongming
 * @Date: 2019-12-18 14:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Pages {
	private boolean hasNext;

	/**
	 * 页码索引
	 */
	private int pageIndex;

	/**
	 * 页面大小
	 */
	private int pageSize;

	/**
	 * 总页数
	 */
	private int pageCount;

	/**
	 * 总条数
	 */
	private int totalCount;

	public void pages(int pageIndex, int pageCount, int totalCount) {
		this.pageIndex = pageIndex;
		this.pageCount = pageCount;
		this.totalCount = totalCount;
		pageSize = this.totalCount / this.pageCount;
		hasNext = this.pageIndex < this.pageCount;
	}

	public boolean hasNext() {
		hasNext = this.pageIndex < this.pageCount;
		return hasNext;
	}
	public int nextPage() {
		pageIndex ++;
		hasNext = this.pageIndex < this.pageCount;
		return pageIndex;
	}

	public int pageSize() {
		return pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}
}
