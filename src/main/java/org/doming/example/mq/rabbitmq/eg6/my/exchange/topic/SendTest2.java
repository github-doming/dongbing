package org.doming.example.mq.rabbitmq.eg6.my.exchange.topic;
import c.a.util.core.test.CommTest;
import com.rabbitmq.client.Connection;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
import org.junit.Test;

import java.io.IOException;
/**
 * @Description: 发送端
 * @Author: Dongming
 * @Date: 2019-01-22 15:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendTest2 extends CommTest {

	@Test public void test() throws IOException {

		try {
			RabbitMqUtil util = RabbitMqUtil.findInstance();

			String exchangeName = "exchange.topic.demo.simple";

			Connection connection = util.createConnection();

			String[] routingKeys = new String[]{"topic.demo.simple", "topic.eg.simple", "topic.demo.double","topic.demo.three"};
			for (int i = 0; i < 30; i++) {
				String routingKey = routingKeys[i % 3];
				util.sendExchangeTopic(connection, exchangeName, routingKey, i + "_" + "测试消息" + routingKey);
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RabbitMqUtil.destroy();
		}
	}
}
