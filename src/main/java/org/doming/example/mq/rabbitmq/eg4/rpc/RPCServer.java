package org.doming.example.mq.rabbitmq.eg4.rpc;
import com.rabbitmq.client.*;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
/**
 * @Description: RPC服务
 * @Author: null
 * @Date: 2018-11-08 17:20
 * @Version: v1.0
 */
public class RPCServer {

	public static void main(String[] args) throws IOException, TimeoutException {
		new RPCServer().test();
	}


	public void test() throws IOException, TimeoutException {
		//创建连接和通道
		Connection connection = null;
		try {
			//创建一个连接到Rabbit服务器的连接
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(RabbitMQConfiguration.HOST_NAME);
			factory.setUsername(RabbitMQConfiguration.USER_NAME);
			factory.setPassword(RabbitMQConfiguration.PASS_WORD);
			connection = factory.newConnection();

			final Channel channel = connection.createChannel();

			/*
			 * 为通道指明队列
			 * queue - 队列的名称
			 * durable - 如果我们声明一个持久队列（队列将在服务器重启后仍然存在），则为true
			 * exclusive - 如果我们声明一个独占队列（仅限于此连接），则为true
			 * autoDelete - 如果我们声明一个自动删除队列，则为true（服务器将在不再使用时将其删除）
			 * arguments - 队列的其他属性（构造参数）
			 */
			channel.queueDeclare(RabbitMQConfiguration.QUEUE_NAME_RPC, false, false, false, null);

			//一次只从队列中取出一个消息
			channel.basicQos(1);

			System.out.println(" [doming] 等待 RPC 请求");

			//监听消息（即RPC请求）
			final Consumer consumer = new DefaultConsumer(channel) {
				@Override public void handleDelivery(String consumerTag, Envelope envelope,
						AMQP.BasicProperties properties, byte[] body) throws IOException {
					AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
							.correlationId(properties.getCorrelationId()).build();

					//收到RPC请求后开始处理
					String response = "";
					try {

						String message = new String(body, StandardCharsets.UTF_8);
						int n = Integer.parseInt(message);
						System.out.println(" [......] fib(" + message + ")");
						response += fib(n);
					} catch (RuntimeException e) {
						System.out.println(" [......] error(" + e.toString() + ")");
					} finally {
						System.out.println(" [服务端当前] ： " + System.currentTimeMillis());
						channel.basicPublish("", properties.getReplyTo(), replyProps,
								response.getBytes(StandardCharsets.UTF_8));

						channel.basicAck(envelope.getDeliveryTag(),false);
					}
				}
			};

			channel.basicConsume(RabbitMQConfiguration.QUEUE_NAME_RPC,false,consumer);
			do {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ignore) {

				}
			} while (true);

			//循环以防止达到最终阻止
		} finally {

			if (connection != null) {
				connection.close();
			}
		}
	}

	private static int fib(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		return fib(n - 1) + fib(n - 2);
	}
}
