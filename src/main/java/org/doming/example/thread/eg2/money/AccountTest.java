package org.doming.example.thread.eg2.money;

import org.doming.example.thread.eg2.money.a.AccountA;
import org.doming.example.thread.eg2.money.a.AddMoneyThreadA;
import org.doming.example.thread.eg2.money.b.AccountB;
import org.doming.example.thread.eg2.money.b.AddMoneyThreadB;
import org.doming.example.thread.eg2.money.c.AccountC;
import org.doming.example.thread.eg2.money.c.AddMoneyThreadC;
import org.doming.example.thread.eg2.money.d.AccountD;
import org.doming.example.thread.eg2.money.d.AddMoneyThreadD;
import org.doming.example.thread.eg2.money.e.AccountE;
import org.doming.example.thread.eg2.money.e.AddMoneyThreadE;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-11-02 15:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AccountTest {

	long count = 1000L;
	int pool = 100;

	@Test public void testTime() {

		long startTime = System.currentTimeMillis();
		runA();
		long endTime = System.currentTimeMillis();
		System.out.println("A类消耗时间: " + (endTime - startTime));
		System.out.println("不加锁");
		System.out.println("-------------------------");

		startTime = System.currentTimeMillis();
		runB();
		endTime = System.currentTimeMillis();
		System.out.println("B类消耗时间: " + (endTime - startTime));
		System.out.println("加锁存款");
		System.out.println("-------------------------");

		startTime = System.currentTimeMillis();
		runC();
		endTime = System.currentTimeMillis();
		System.out.println("C类消耗时间: " + (endTime - startTime));
		System.out.println("加锁运行");
		System.out.println("-------------------------");

		startTime = System.currentTimeMillis();
		runD();
		endTime = System.currentTimeMillis();
		System.out.println("D类消耗时间: " + (endTime - startTime));
		System.out.println("JDK 1.5锁机制");
		System.out.println("-------------------------");

		startTime = System.currentTimeMillis();
		runE();
		endTime = System.currentTimeMillis();
		System.out.println("E类消耗时间: " + (endTime - startTime));
		System.out.println("原子锁锁机制");
		System.out.println("-------------------------");
	}

	private void runA() {
		AccountA account = new AccountA();
		ExecutorService service = Executors.newFixedThreadPool(pool);
		for (long i = 0; i < count; i++) {
			service.execute(new AddMoneyThreadA(account, 1));
		}
		service.shutdown();
		while (!service.isTerminated()) {
		}
		System.out.println("A类账户余额: " + account.getBalance());
	}
	private void runB() {
		AccountB account = new AccountB();
		ExecutorService service = Executors.newFixedThreadPool(pool);
		for (long i = 0; i < count; i++) {
			service.execute(new AddMoneyThreadB(account, 1));
		}
		service.shutdown();
		while (!service.isTerminated()) {
		}
		System.out.println("B类账户余额: " + account.getBalance());
	}
	private void runC() {
		AccountC account = new AccountC();
		ExecutorService service = Executors.newFixedThreadPool(pool);
		for (long i = 0; i < count; i++) {
			service.execute(new AddMoneyThreadC(account, 1));
		}
		service.shutdown();
		while (!service.isTerminated()) {
		}
		System.out.println("C类账户余额: " + account.getBalance());
	}
	private void runD() {
		AccountD account = new AccountD();
		ExecutorService service = Executors.newFixedThreadPool(pool);
		for (long i = 0; i < count; i++) {
			service.execute(new AddMoneyThreadD(account, 1));
		}
		service.shutdown();
		while (!service.isTerminated()) {
		}
		System.out.println("D类账户余额: " + account.getBalance());
	}
	private void runE() {
		AccountE account = new AccountE();
		ExecutorService service = Executors.newFixedThreadPool(pool);
		for (long i = 0; i < count; i++) {
			service.execute(new AddMoneyThreadE(account, 1));
		}
		service.shutdown();
		while (!service.isTerminated()) {
		}
		System.out.println("E类账户余额: " + account.getBalance());
	}

}
