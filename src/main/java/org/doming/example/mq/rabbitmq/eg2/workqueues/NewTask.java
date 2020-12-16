package org.doming.example.mq.rabbitmq.eg2.workqueues;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 工作队列  生产者
 * @Author: Dongming
 * @Date: 2018-11-07 15:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class NewTask {

	public static void main(String[] args) throws IOException, TimeoutException {

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

			/*
			 * 为通道指明队列
			 * queue - 队列的名称
			 * durable - 如果我们声明一个持久队列（队列将在服务器重启后仍然存在），则为true
			 * exclusive - 如果我们声明一个独占队列（仅限于此连接），则为true
			 * autoDelete - 如果我们声明一个自动删除队列，则为true（服务器将在不再使用时将其删除）
			 * arguments - 队列的其他属性（构造参数）
			 */
			channel.queueDeclare(RabbitMQConfiguration.QUEUE_NAME_1, true, false, false, null);

			//发送任务
//			new NewTask().task1(channel);
			new NewTask().task2(channel);

		} finally {
			if (channel != null) {
				channel.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * 任务体 传送任务的信息
	 * 非持久化消息
	 *
	 * @param channel 通道
	 */
	public void task1(Channel channel) throws IOException {
		String key = "....";
		for (int i = 1; i <= 10; i++) {
			String message = i+key;
			/*
			 * 发布消息。 如果发布到不存在的消息，将导致通道级协议异常，从而关闭通道。
			 * 如果资源异常生效，Channel＃basicPublish的调用最终将被阻止。
			 * exchange – 交换机
			 * routingKey – 路由密钥
			 * props – 消息的其他属性 - 路由标题等
			 * body – 信息体
			 */
			channel.basicPublish("", RabbitMQConfiguration.QUEUE_NAME, null, message.getBytes());
			System.out.println(" [doming] 发送 '" +  message + "'");
		}
	}

	/**
	 * 任务体 传送任务的信息
	 * 持久化消息
	 * 将消息标记为持久化并不能完全保证消息不会丢失。
	 * 尽管它告诉RabbitMQ将消息保存到磁盘中，但是在RabbitMQ接收到消息和保存消息之间会与一个很短的时间窗。
	 * 同时，RabbitMQ不会为每个消息做fsync(2)处理，消息可能仅仅保存到缓存中而不会真正地写入到磁盘中。
	 * 这种持久化保证尽管不够健壮，但已经远远足够我们的简单任务队列。如果你需要更强大的保证，
	 * 可以使用[publisher confirms](https://www.rabbitmq.com/confirms.html)。
	 *
	 * @param channel 通道
	 */
	public void task2(Channel channel) throws IOException {
		String key = "....";
		for (int i = 1; i <= 10; i++) {
			String message = i+key;
			/*
			 * 发布消息。 如果发布到不存在的消息，将导致通道级协议异常，从而关闭通道。
			 * 如果资源异常生效，Channel＃basicPublish的调用最终将被阻止。
			 * exchange – 交换机
			 * routingKey – 路由密钥
			 * props – 消息的其他属性 - 路由标题等
			 * body – 信息体
			 */
			channel.basicPublish("", RabbitMQConfiguration.QUEUE_NAME_1, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" [doming] 发送 '" +  message + "'");
		}
	}
}
