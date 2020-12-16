package org.doming.example.mq.rabbitmq.eg3.exchanges.direct;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.doming.core.tools.RandomTool;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 生产者
 * @Author: Dongming
 * @Date: 2018-11-08 11:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class EmitLogDirect {

	public static void main(String[] args) throws IOException, TimeoutException {
		new EmitLogDirect().test();
	}

	public void test() throws IOException, TimeoutException {
		//socket连接的抽象，为我们处理了通信协议版本协商以及认证等
		Connection connection = null;
		//通道（channel），大部分的API操作均在这里完成
		Channel channel = null;
		try {
			//创建一个连接到Rabbit服务器的连接
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(RabbitMQConfiguration.HOST_NAME);
			factory.setUsername(RabbitMQConfiguration.USER_NAME);
			factory.setPassword(RabbitMQConfiguration.PASS_WORD);
			connection = factory.newConnection();
			channel = connection.createChannel();

			//声明路由以及路由的类型
			channel.exchangeDeclare(RabbitMQConfiguration.EXCHANGE_NAME_DIRECT, BuiltinExchangeType.DIRECT);

			task(channel);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			if (channel != null) {
				channel.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}
	private void task(Channel channel) throws IOException, InterruptedException {
		String[] severities = {"trace", "debug", "info", "warning", "error"};
		String severity;
		String message;
		for (int i = 1; i <= 50; i++) {
			severity = (String) RandomTool.getOneSelect(severities);
			message = "......" + i + " 这是一个【" + severity + "】消息...... ";
			//发布消息
			channel.basicPublish(RabbitMQConfiguration.EXCHANGE_NAME_DIRECT, severity, null,
					message.getBytes(StandardCharsets.UTF_8));
			Thread.sleep(100L);
			System.out.println(" [doming] 发送 '" + message + "'");
		}

	}
}
