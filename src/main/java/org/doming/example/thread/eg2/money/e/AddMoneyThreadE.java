package org.doming.example.thread.eg2.money.e;
/**
 * @Description: 存钱线程
 * 原文：https://blog.csdn.net/jackfrued/article/details/17403101
 * @Author: Dongming
 * @Date: 2018-11-02 15:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AddMoneyThreadE implements Runnable {
	/**
	 * 存入账户
	 */
	private AccountE account;

	/**
	 * 存入金额
	 */
	private double money;

	public AddMoneyThreadE(AccountE account, double money) {
		this.account = account;
		this.money = money;
	}

	@Override public void run() {
		account.deposit(money);
	}

}
