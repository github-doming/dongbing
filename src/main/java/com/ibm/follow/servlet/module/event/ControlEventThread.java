package com.ibm.follow.servlet.module.event;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import org.doming.core.tools.RandomTool;

import java.util.concurrent.ThreadPoolExecutor;
/**
 * 事件线程基类 - 自动管理线程
 * @Author: Dongming
 * @Date: 2019-12-24 11:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class ControlEventThread extends CommEventThread {
	private ThreadPoolExecutor executor;
	protected  boolean isRun = true;
	public void stop() {
		isRun = false;
	}
	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public void sleep() throws InterruptedException {
		Thread.sleep(RandomTool.getInt(100, 1900));
	}
}
