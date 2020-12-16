package com.common.handicap.crawler.entity;
import org.doming.core.tools.NumberTool;

/**
 * 会员用户
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberUser {
	/**
	 * 会员账号
	 */
	private String memberAccount;
	/**
	 * 会员盘
	 */
	private String memberType;
	/**
	 * 信用额度
	 */
	private double creditQuota = 0.0;
	/**
	 * 使用金额
	 */
	private double usedAmount = 0.0;
	/**
	 * 可用额度
	 */
	private double usedQuota = 0.0;
	/**
	 * 盈亏金额
	 */
	private double profitAmount = 0.0;

	/**
	 * 检验时间
	 */
	private long checkTime = 0L;

	public String memberAccount() {
		return memberAccount;
	}
	public MemberUser memberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
		return this;
	}
	public String memberType() {
		return memberType;
	}
	public void memberType(String memberType) {
		this.memberType = memberType;
	}
	public double creditQuota() {
		return creditQuota;
	}
	public void creditQuota(Double creditQuota) {
		this.creditQuota = creditQuota;
	}
	public void creditQuota(Object creditQuota) {
		creditQuota(NumberTool.getDouble(creditQuota));
	}
	public double usedAmount() {
		return usedAmount;
	}
	public void usedAmount(double usedAmount) {
		this.usedAmount = usedAmount;
	}
	public double usedQuota() {
		return usedQuota;
	}
	public void usedQuota(Double usedQuota) {
		this.usedQuota = usedQuota;
	}
	public void usedQuota(Object usedQuota) {
		usedQuota(NumberTool.getDouble(usedQuota));
	}
	public double profitAmount() {
		return profitAmount;
	}
	public void profitAmount(Double profitAmount) {
		this.profitAmount = profitAmount;
	}
	public void profitAmount(Object profitAmount) {
		profitAmount(NumberTool.getDouble(profitAmount));
	}
	public long checkTime() {
		return checkTime;
	}

	public void checkTime(long checkTime) {
		this.checkTime = checkTime;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public double getCreditQuota() {
		return creditQuota;
	}

	public void setCreditQuota(double creditQuota) {
		this.creditQuota = creditQuota;
	}

	public double getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(double usedAmount) {
		this.usedAmount = usedAmount;
	}

	public double getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(double usedQuota) {
		this.usedQuota = usedQuota;
	}

	public double getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(double profitAmount) {
		this.profitAmount = profitAmount;
	}

	public long getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(long checkTime) {
		this.checkTime = checkTime;
	}
}
