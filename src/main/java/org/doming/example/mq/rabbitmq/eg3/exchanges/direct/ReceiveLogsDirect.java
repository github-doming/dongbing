package org.doming.example.mq.rabbitmq.eg3.exchanges.direct;
import com.rabbitmq.client.*;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 消费者
 * @Author: Dongming
 * @Date: 2018-11-08 11:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ReceiveLogsDirect {

	public static void main(String[] args) {
		new ReceiveLogsDirect().test();
	}

	public void test() {

		//socket连接的抽象，为我们处理了通信协议版本协商以及认证等
		Connection connection;
		//通道（channel），大部分的API操作均在这里完成
		Channel channel;
		try {
			//创建一个连接到Rabbit服务器的连接
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(RabbitMQConfiguration.HOST_NAME);
			factory.setUsername(RabbitMQConfiguration.USER_NAME);
			factory.setPassword(RabbitMQConfiguration.PASS_WORD);
			connection = factory.newConnection();
			channel = connection.createChannel();

			//声明路由器和类型
			channel.exchangeDeclare(RabbitMQConfiguration.EXCHANGE_NAME_DIRECT, BuiltinExchangeType.DIRECT);

			//声明队列
			String queueName = channel.queueDeclare().getQueue();

			//定义要监听的级别
			String[] severities = {"trace", "debug", "info", "warning", "error"};


			//根据绑定键绑定,绑定想要的路由密钥
			for (String severity : severities) {
				channel.queueBind(queueName,RabbitMQConfiguration.EXCHANGE_NAME_DIRECT,severity);
			}

			System.out.println(" [doming] 等待error消息中，按 CTRL+F2 退出");
			final Consumer consumer = new DefaultConsumer(channel){
				@Override public void handleDelivery(String consumerTag, Envelope envelope,
						AMQP.BasicProperties properties, byte[] body) throws IOException {
					String message = new String(body, StandardCharsets.UTF_8);
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(" [doming] 收到 '" + message + "'");
				}
			};
			/*
			 * 如果消费者意外关闭，失去消息
			 */
			channel.basicConsume(queueName,true,consumer);

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
