package org.doming.example.thread.eg2.money.e;
import com.google.common.util.concurrent.AtomicDouble;
/**
 * @Description: 银行账户
 * 原文：https://blog.csdn.net/jackfrued/article/details/17403101
 * @Author: Dongming
 * @Date: 2018-11-02 15:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AccountE {


	// 账户余额
	private final  AtomicDouble balance = new AtomicDouble(0);

	/**
	 * 存款
	 *
	 * @param money 存入金额
	 */
	public   void deposit(double money) {
		balance.addAndGet(money);
		try {
			// 模拟此业务需要一段处理时间
			Thread.sleep(10);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获得账户余额
	 */
	public double getBalance() {
		return balance.doubleValue();
	}

}
