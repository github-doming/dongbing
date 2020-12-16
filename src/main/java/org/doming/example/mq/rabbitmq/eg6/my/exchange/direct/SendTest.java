package org.doming.example.mq.rabbitmq.eg6.my.exchange.direct;
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
			RabbitMqUtil util = RabbitMqUtil.findInstance();

			String exchangeName = "exchange.direct.demo.simple";
			String[] routingKeys = new String[]{"SSC", "PK10"};
			String routingKey;
			Connection connection = util.createConnection();
			for (int i = 0; i < 30; i++) {
				routingKey = routingKeys[i % 2];
				util.sendExchangeDirect(connection, exchangeName, routingKey, i + "_" + "测试消息" + routingKey);
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RabbitMqUtil.destroy();
		}
	}
}
