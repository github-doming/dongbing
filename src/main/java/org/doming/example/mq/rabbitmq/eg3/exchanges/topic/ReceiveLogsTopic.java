package org.doming.example.mq.rabbitmq.eg3.exchanges.topic;
import com.rabbitmq.client.*;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
/**
 * @Description: 消费者
 * @Author: Dongming
 * @Date: 2018-11-08 16:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ReceiveLogsTopic {

	public static void main(String[] args) {
		new ReceiveLogsTopic().test();
	}

	public void test(){
		Connection connection;
		Channel channel;

		try {
			//建立连接和通道
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(RabbitMQConfiguration.HOST_NAME);
			factory.setUsername(RabbitMQConfiguration.USER_NAME);
			factory.setPassword(RabbitMQConfiguration.PASS_WORD);
			connection  = factory.newConnection();
			channel = connection.createChannel();

			//声明路由器和路由器类型
			channel.exchangeDeclare(RabbitMQConfiguration.EXCHANGE_NAME_TOPIC, BuiltinExchangeType.TOPIC);

			String queueName = channel.queueDeclare().getQueue();

			String[] bindingKeys = {"kern.*", "*.critical"};
			for (String bindingKey : bindingKeys){
				channel.queueBind(queueName,RabbitMQConfiguration.EXCHANGE_NAME_TOPIC,bindingKey);
			}

			System.out.println(" [doming] 等待【"+ Arrays.toString(bindingKeys) +"】消息中，按 CTRL+F2 退出");
			//监听消息
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
