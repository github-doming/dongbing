package org.doming.example.mq.rabbitmq.eg6.my.exchange.direct;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-01-22 14:31
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
			String exchangeName = "exchange.direct.demo.simple";
			System.out.println("开始接收订阅型路由消息" + util.receiveExchangeDirect(exchangeName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
