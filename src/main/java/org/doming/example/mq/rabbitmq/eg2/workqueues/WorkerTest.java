package org.doming.example.mq.rabbitmq.eg2.workqueues;
import com.rabbitmq.client.*;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 工作 消费者
 * @Author: Dongming
 * @Date: 2018-11-07 15:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class WorkerTest {

	public static void main(String[] args) {
		//test01();
		//test02();
		test03();
	}

	/**
	 * 如果你杀死一个工作者，我们将会失去它正在处理的消息，同时也会丢失所有发给这个工作者但这个工作者还未处理的消息
	 */
	private static void test01() {
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

			final Consumer consumer = new DefaultConsumer(channel) {
				@Override public void handleDelivery(String consumerTag, Envelope envelope,
						AMQP.BasicProperties properties, byte[] body) {
					String message = new String(body, StandardCharsets.UTF_8);
					System.out.println(" [doming] 收到 '" + message + "'");
					try {
						new WorkerTest().doWork(message);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						System.out.println(" [doming] 完成");
					}
				}
			};
			//消息确认
			boolean autoAck = true;
			/*
			 * 使用服务器生成的consumerTag启动非本地非排他性使用者。
			 * queue - 队列的名称
			 * autoAck - 消息是否确认
			 * callback - 消费者对象的接口
			 */
			channel.basicConsume(RabbitMQConfiguration.QUEUE_NAME, autoAck, consumer);

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 我们不想丢掉任务，如果一个工作者死掉，我们想将这个任务发给其他的工作者
	 */
	private static void test02() {
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
			 * durable - 持久化队列，则为true
			 * exclusive - 如果我们声明一个独占队列（仅限于此连接），则为true
			 * autoDelete - 如果我们声明一个自动删除队列，则为true（服务器将在不再使用时将其删除）
			 * arguments - 队列的其他属性（构造参数）
			 */
			channel.queueDeclare(RabbitMQConfiguration.QUEUE_NAME, false, false, false, null);
			System.out.println(" [doming] 等待消息中，按 CTRL+F2 退出");

			// 一次只接受一条未包含的消息（见下文）
			channel.basicQos(3);

			final Consumer consumer = new DefaultConsumer(channel) {
				@Override public void handleDelivery(String consumerTag, Envelope envelope,
						AMQP.BasicProperties properties, byte[] body) throws IOException {
					String message = new String(body, StandardCharsets.UTF_8);
					System.out.println(" [doming] 收到 '" + message + "'");
					try {
						new WorkerTest().doWork(message);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						System.out.println(" [doming] 完成");
						//确认收到一条或多条收到的消息
						channel.basicAck(envelope.getDeliveryTag(), true);
					}
				}
			};
			//消息确认
			boolean autoAck = false;
			/*
			 * 使用服务器生成的consumerTag启动非本地非排他性使用者。
			 * queue - 队列的名称
			 * autoAck - 消息s是否确认
			 * callback - 消费者对象的接口
			 */
			channel.basicConsume(RabbitMQConfiguration.QUEUE_NAME, autoAck, consumer);

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 我们不想丢掉任务，如果一个工作者死掉，我们想将这个任务发给其他的工作者
	 */
	private static void test03() {
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
			 * durable - 持久化队列，则为true
			 * exclusive - 如果我们声明一个独占队列（仅限于此连接），则为true
			 * autoDelete - 如果我们声明一个自动删除队列，则为true（服务器将在不再使用时将其删除）
			 * arguments - 队列的其他属性（构造参数）
			 */
			channel.queueDeclare(RabbitMQConfiguration.QUEUE_NAME_1, true, false, false, null);
			System.out.println(" [doming] 等待消息中，按 CTRL+F2 退出");

			// 一次只接受一条未包含的消息（一次性接受N条记录）
			//channel.basicQos(6);

			final Consumer consumer = new DefaultConsumer(channel) {
				@Override public void handleDelivery(String consumerTag, Envelope envelope,
						AMQP.BasicProperties properties, byte[] body) throws IOException {
					String message = new String(body, StandardCharsets.UTF_8);
					System.out.println(" [doming] 收到 '" + message + "'");
					try {
						new WorkerTest().doWork(message);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						System.out.println(" [doming] 完成");
						//确认收到一条或多条收到的消息
						channel.basicAck(envelope.getDeliveryTag(), true);
					}
				}
			};
			//消息确认
			boolean autoAck = false;
			/*
			 * 使用服务器生成的consumerTag启动非本地非排他性使用者。
			 * queue - 队列的名称
			 * autoAck - 消息s是否确认
			 * callback - 消费者对象的接口
			 */
			channel.basicConsume(RabbitMQConfiguration.QUEUE_NAME_1, autoAck, consumer);

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	private void doWork(String task) throws InterruptedException {
		System.out.print(" [doming] 任务执行中" + System.currentTimeMillis());
		for (char ch : task.toCharArray()) {
			if ('.' == ch) {
				Thread.sleep(5000L);
				System.out.print('.');
			}
		}
		System.out.println();
	}

}
