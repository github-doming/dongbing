package org.doming.example.design.pattern.factory.entry;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 14:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ApplePhone extends Phone {
	@Override public void charge() {
		System.out.println("普通充电");
	}
}
