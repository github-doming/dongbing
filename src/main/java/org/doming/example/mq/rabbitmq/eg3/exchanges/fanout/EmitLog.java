package org.doming.example.mq.rabbitmq.eg3.exchanges.fanout;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 生产者
 * @Author: Dongming
 * @Date: 2018-11-07 18:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class EmitLog {

	public static void main(String[] args) throws IOException, TimeoutException {
		new EmitLog().test();
	}

	/**
	 * 如果路由器还没有绑定队列，这些发送给路由器的消息将会丢失。但这对我们无所谓，如果还没有消费者监听，我们可以安全地丢弃这些消息。
	 * channel.basicPublish(RabbitMQConfiguration.EXCHANGE_NAME_LOTTERY,"",null,message.getBytes(StandardCharsets.UTF_8));
	 */
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
			channel.exchangeDeclare(RabbitMQConfiguration.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

			String  message = "msg...";

			/*
			 * 发布消息。 如果发布到不存在的消息，将导致通道级协议异常，从而关闭通道。
			 * 如果资源异常生效，Channel＃basicPublish的调用最终将被阻止。
			 * exchange – 将消息发布到的交换
			 * routingKey – 路由密钥
			 * props – 消息的其他属性 - 路由标题等
			 * body – 信息体
			 */
			channel.basicPublish(RabbitMQConfiguration.EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
			System.out.println(" [doming] 发送 '" + message + "'");
		} catch (Exception e) {
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
}
