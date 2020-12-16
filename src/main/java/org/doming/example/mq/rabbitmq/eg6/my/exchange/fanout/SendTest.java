package org.doming.example.mq.rabbitmq.eg6.my.exchange.fanout;
import c.a.util.core.test.CommTest;
import com.rabbitmq.client.Connection;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
import org.junit.Test;
/**
 * @Description: 发送端
 * @Author: Dongming
 * @Date: 2019-01-22 15:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendTest extends CommTest {

	@Test public void test() {

		try {
			RabbitMqUtil util = RabbitMqUtil.findInstance();

			String exchangeName = "exchange.fanout.demo.simple";

			Connection connection = util.createConnection();
			for (int i = 0; i < 30; i++) {
				util.sendExchangeFanout(connection, exchangeName, "测试消息" + i);
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RabbitMqUtil.destroy();
		}
	}
}
