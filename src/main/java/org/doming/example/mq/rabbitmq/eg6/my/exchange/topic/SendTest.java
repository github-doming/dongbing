package org.doming.example.mq.rabbitmq.eg6.my.exchange.topic;
import c.a.util.core.test.CommTest;
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

			String exchangeName = "exchange.topic.demo.simple";
			System.out.println("发送消息队列" + util.sendExchangeTopic(exchangeName, "topic.demo.simple", "测试消息"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RabbitMqUtil.destroy();
		}
	}
}
