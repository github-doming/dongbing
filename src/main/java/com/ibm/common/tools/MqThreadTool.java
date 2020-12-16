package com.ibm.common.tools;
import org.doming.core.tools.ThreadTool;

import java.util.concurrent.ThreadPoolExecutor;
/**
 * @Description: 消息队列线程池工具类
 * @Author: Dongming
 * @Date: 2019-10-23 10:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MqThreadTool extends ThreadTool {
	/**
	 * 开启客户端线程池
	 */
	private static volatile ThreadPoolExecutor betExecutor;

	/**
	 * 获取投注执行器
	 *
	 * @return 执行器
	 */
	private static ThreadPoolExecutor getBetExecutor() {
		if (betExecutor == null) {
			synchronized (MqThreadTool.class) {
				if (betExecutor == null) {
					betExecutor = createExecutor("bet-mq", 5, 30, 30, 100);
				}
			}
		}

		return betExecutor;
	}
	/**
	 * 执行投注信息
	 *
	 * @param runnable 投注类
	 */
	public static void executeBet(Runnable runnable) {
		getBetExecutor().execute(runnable);
	}
}
