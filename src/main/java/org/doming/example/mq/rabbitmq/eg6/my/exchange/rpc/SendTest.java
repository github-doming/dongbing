package org.doming.example.mq.rabbitmq.eg6.my.exchange.rpc;
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
public class SendTest extends CommTest {

	@Test public void test() throws IOException {

		try {
			RabbitMqUtil.setConfigPathKey("rabbitmq.ibm.xml");
			RabbitMqUtil util = RabbitMqUtil.findInstance();

			String exchangeName = "exchange.topic.demo.simple";
			String[] routingKeys ={ "topic.demo.simple", "topic.eg.simple"};
			try(Connection connection = util.createConnection()) {
				for (int i = 0; i < 3; i++) {
					String routingKey = routingKeys[i % 2];
					System.out.println(
							"发送消息队列：" + util.sendExchangeRpcTopic(connection, exchangeName, routingKey, "测试消息"+i));
					Thread.sleep(100);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RabbitMqUtil.destroy();
		}
	}
}
