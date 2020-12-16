package org.doming.example.design.pattern.observer.eg1.cainiao;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-06 14:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class Observer {
	protected Subject subject;
	public abstract void update();
}
