package com.ibm.follow.servlet.client.core.job.bet;
/**
 * @Author: Dongming
 * @Date: 2019-12-18 14:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DetailInfo {

	private String betItem;

	private Double funds;

	public DetailInfo(String betItem, Double funds) {
		this.betItem = betItem;
		this.funds = funds;
	}

	public String betItem() {
		return betItem;
	}
	public Double funds() {
		return funds;
	}
}
