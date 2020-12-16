package org.doming.example.mq.rabbitmq.eg6.my.exchange.fanout;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
/**
 * @Description: 路由接受者测试类
 * @Author: Dongming
 * @Date: 2019-01-23 10:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RecvTest {

	public static void main(String[] args) {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		try {
			RabbitMqUtil util = RabbitMqUtil.findInstance();
			System.out.println("开始接收广播型路由消息" +util.receiveExchangeFanout("exchange.fanout.demo.simple"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
