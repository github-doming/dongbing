package org.doming.example.mq.rabbitmq.eg3.exchanges.current;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-22 17:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendTest {

	@Test public void test() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitMQConfiguration.HOST_NAME);
		factory.setUsername(RabbitMQConfiguration.USER_NAME);
		factory.setPassword(RabbitMQConfiguration.PASS_WORD);

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

			String routingKey = "critical.critical";

			//声明路由器和路由器类型
			channel.exchangeDeclare(RabbitMQConfiguration.EXCHANGE_NAME_TOPIC, BuiltinExchangeType.TOPIC);

			for (int i = 0; i < 100; i++) {
				String msg = "测试消息" + i;
				channel.basicPublish(RabbitMQConfiguration.EXCHANGE_NAME_TOPIC, routingKey, null,
						msg.getBytes(StandardCharsets.UTF_8));
				System.out.println("发送消息队列：" + msg);
				Thread.sleep(100);
			}

		} catch (TimeoutException | IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
