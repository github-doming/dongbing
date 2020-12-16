package org.doming.example.mq.rabbitmq.eg1.helloworld;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 发送者（生产者）测试类
 * @Author: Dongming
 * @Date: 2018-11-07 13:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendTest {

	public static void main(String[] args) throws IOException, TimeoutException {
		new SendTest().test();
	}

	public void test() throws IOException, TimeoutException {

		//创建一个连接到Rabbit服务器的连接
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitMQConfiguration.HOST_NAME);
		factory.setUsername(RabbitMQConfiguration.USER_NAME);
		factory.setPassword(RabbitMQConfiguration.PASS_WORD);

		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			/*
			 * 为通道指明队列
			 * queue - 队列的名称
			 * durable - 如果我们声明一个持久队列（队列将在服务器重启后仍然存在），则为true
			 * exclusive - 如果我们声明一个独占队列（仅限于此连接），则为true
			 * autoDelete - 如果我们声明一个自动删除队列，则为true（服务器将在不再使用时将其删除）
			 * arguments - 队列的其他属性（构造参数）
			 */
			channel.queueDeclare(RabbitMQConfiguration.QUEUE_NAME, true, false, false, null);

			String message = "Hello world";

			/*
			 * 发布消息。 如果发布到不存在的消息，将导致通道级协议异常，从而关闭通道。
			 * 如果资源异常生效，Channel＃basicPublish的调用最终将被阻止。
			 * exchange – 将消息发布到的交换
			 * routingKey – 路由密钥
			 * props – 消息的其他属性 - 路由标题等
			 * body – 信息体
			 */
			channel.basicPublish("", RabbitMQConfiguration.QUEUE_NAME, null, message.getBytes());
		}
	}

}
