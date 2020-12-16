package com.ibm.follow.servlet.client.core.job.bet;
import org.doming.core.tools.NumberTool;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author: Dongming
 * @Date: 2019-12-18 14:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DetailBox {

	private Pages pages;
	private String maxOddNumber;
	private List<DetailInfo> details;

	private Boolean hasNext;

	public DetailBox pages(Object pageObj, Object totalObj) {
		int page = NumberTool.getInteger(pageObj, 0);
		int total = NumberTool.getInteger(totalObj, 0);
		if (pages == null) {
			pages = new Pages();
		}
		pages.pages(1, page, total);
		details = new ArrayList<>(total * 3 / 4 + 1);
		return this;
	}

	public List<DetailInfo> details() {
		return details;
	}

	public String getMaxOddNumber(String defaultOddNumber) {
		return maxOddNumber != null ? maxOddNumber : defaultOddNumber;
	}
	public void maxOddNumber(String maxOddNumber) {
		this.maxOddNumber = maxOddNumber;
	}
	public String maxOddNumber() {
		return maxOddNumber;
	}

	public boolean hasNext() {
		if (hasNext == null) {
			return pages.hasNext();
		}
		return hasNext;
	}

	public DetailBox hasNext(boolean hasNext) {
		this.hasNext = hasNext;
		return this;
	}
	public Pages getPages() {
		return pages;
	}
}
