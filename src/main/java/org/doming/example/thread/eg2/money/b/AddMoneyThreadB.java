package org.doming.example.thread.eg2.money.b;
/**
 * @Description: 存钱线程
 * @Author: Dongming
 * @Date: 2018-11-02 17:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AddMoneyThreadB implements Runnable {
	// 存入账户
	private AccountB account;
	// 存入金额
	private double money;

	public AddMoneyThreadB(AccountB account, double money) {
		this.account = account;
		this.money = money;
	}

	@Override public void run() {
		account.deposit(money);
	}

}
