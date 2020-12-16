package org.doming.example.mq.rabbitmq.eg1.helloworld;
import com.rabbitmq.client.*;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 接收者（消费者）测试类
 * @Author: Dongming
 * @Date: 2018-11-07 14:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RecvTest {

	public static void main(String[] args) {

		//socket连接的抽象，为我们处理了通信协议版本协商以及认证等
		Connection connection;
		//通道（channel），大部分的API操作均在这里完成
		Channel channel;
		try {

			//建立连接和通道
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(RabbitMQConfiguration.HOST_NAME);
			factory.setUsername(RabbitMQConfiguration.USER_NAME);
			factory.setPassword(RabbitMQConfiguration.PASS_WORD);
			connection = factory.newConnection();
			channel = connection.createChannel();


			/*
			 * 声明要消费的队列(可能在启动生产者之前启动了消费者应用，我们想确保在从一个队列消费消息之前，这个队列是存在的)
			 * queue - 队列的名称
			 * durable - 如果我们声明一个持久队列（队列将在服务器重启后仍然存在），则为true
			 * exclusive - 如果我们声明一个独占队列（仅限于此连接），则为true
			 * autoDelete - 如果我们声明一个自动删除队列，则为true（服务器将在不再使用时将其删除）
			 * arguments - 队列的其他属性（构造参数）
			 */
			channel.queueDeclare(RabbitMQConfiguration.QUEUE_NAME, false, false, false, null);
			System.out.println(" [doming] 等待消息中，按 CTRL+F2 退出");

			channel.basicQos(1);
			//回调消费消息(这样，消费者就会一直监听声明的队列。运行一次生产者（即Send.java中的main方法），消费者就会打印出接受到的消息。)
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
				try {
					doWork(message);
				} finally {
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				}
			};

			/*
			 * 使用服务器生成的consumerTag启动非本地非排他性使用者。
			 * queue - 队列的名称
			 * callback - 消费者对象的接口
			 */
			channel.basicConsume(RabbitMQConfiguration.QUEUE_NAME,  false, deliverCallback, consumerTag -> { });

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}

	}
	private static void doWork(String message) {
		try {
			System.out.println(" [doming] 收到 '" + message + "'");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}


