package org.doming.example.thread.eg2.money.d;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @Description: 银行账户
 * @Author: Dongming
 * @Date: 2018-11-02 17:43
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AccountD {

	private Lock accountLock = new ReentrantLock();
	// 账户余额
	private double balance;

	/**
	 * 存款
	 *
	 * @param money 存入金额
	 */
	public void deposit(double money) {
		accountLock.lock();
		try {
			double newBalance = balance + money;
			try {
				// 模拟此业务需要一段处理时间
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			balance = newBalance;
		} finally {
			accountLock.unlock();
		}
	}

	/**
	 * 获得账户余额
	 */
	public double getBalance() {
		return balance;
	}

}
