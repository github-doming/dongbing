package org.doming.example.mq.rabbitmq.eg6.my;

import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.RandomTool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 消息接受者
 * @Author: null
 * @Date: 2019-01-22 14:44
 * @Version: v1.0
 */
public class RecvListener extends BaseCommMq {
	public RecvListener() {
		super.database = false;
	}
	public static final AtomicInteger COUNT = new AtomicInteger(0);
	@Override public String execute(String message) throws Exception {
		Thread.sleep(RandomTool.getInt(80, 800));
		System.out.println(String.format("线程[%10s]执行,消息内容为：{%8s},接收消息数量=%4d", Thread.currentThread().getName(), message,COUNT.incrementAndGet()));
		return TypeEnum.TRUE.name() + " : " + System.currentTimeMillis();
	}
}
