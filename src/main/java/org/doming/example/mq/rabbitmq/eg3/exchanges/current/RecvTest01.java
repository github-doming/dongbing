package org.doming.example.mq.rabbitmq.eg3.exchanges.current;
import com.rabbitmq.client.*;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-22 17:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RecvTest01 {
	public static void main(String[] args) throws IOException, TimeoutException {
		//建立连接和通道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitMQConfiguration.HOST_NAME);
		factory.setUsername(RabbitMQConfiguration.USER_NAME);
		factory.setPassword(RabbitMQConfiguration.PASS_WORD);
		Connection connection = factory.newConnection();

		String routingKey = "critical.critical";
		System.out.println("开始接收消息");
		Channel channel = connection.createChannel();
		//声明路由器和路由器类型
		channel.exchangeDeclare(RabbitMQConfiguration.EXCHANGE_NAME_TOPIC, BuiltinExchangeType.TOPIC);
		channel.basicQos(1);

		String queueName = channel.queueDeclare().getQueue();

		channel.queueBind(queueName, RabbitMQConfiguration.EXCHANGE_NAME_TOPIC, routingKey);
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			doWork(message);
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		};
		channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
		});

	}
	private static void doWork(String message) {
		System.out.println("消息内容为：" + message);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
