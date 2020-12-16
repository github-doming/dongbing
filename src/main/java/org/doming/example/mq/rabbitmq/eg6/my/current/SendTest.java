package org.doming.example.mq.rabbitmq.eg6.my.current;
import c.a.util.core.test.CommTest;
import com.rabbitmq.client.Connection;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
import org.junit.Test;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-22 15:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendTest extends CommTest {

	@Test public void test() {
		try {
			RabbitMqUtil.setConfigPathVal("/config/com/cloud/mq/rabbitmq.xml");
			RabbitMqUtil util = RabbitMqUtil.findInstance();

			String exchangeName = "exchange.topic.demo.simple";
			String routingKey = "topic.eg.simple";
			try (Connection connection = util.createConnection()) {
				for (int i = 0; i < 10; i++) {
					String msg = "测试消息" + i;
					System.out.println("发送消息队列：" + msg + "，结果：" + util
							.sendExchangeTopic(connection, exchangeName + (i % 2), routingKey, msg));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RabbitMqUtil.destroy();
		}
	}
}
