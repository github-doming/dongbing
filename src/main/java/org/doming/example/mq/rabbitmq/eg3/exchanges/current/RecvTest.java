package org.doming.example.mq.rabbitmq.eg3.exchanges.current;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-22 17:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RecvTest {
	public static void main(String[] args) throws IOException, TimeoutException {
		new RecvTest().recvTest();
	}

	private void recvTest() throws IOException, TimeoutException {
		//建立连接和通道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitMQConfiguration.HOST_NAME);
		factory.setUsername(RabbitMQConfiguration.USER_NAME);
		factory.setPassword(RabbitMQConfiguration.PASS_WORD);
		Connection connection = factory.newConnection();

		String routingKey = "critical.critical";
		System.out.println("开始接收消息00");
		Channel channel = connection.createChannel();
		//声明路由器和路由器类型
		channel.exchangeDeclare(RabbitMQConfiguration.EXCHANGE_NAME_TOPIC, BuiltinExchangeType.TOPIC);
		channel.basicQos(1);

			String queueName = channel.queueDeclare().getQueue();

		channel.queueBind(queueName, RabbitMQConfiguration.EXCHANGE_NAME_TOPIC, routingKey);

		BlockingQueue queue = new LinkedBlockingDeque<Runnable>(20);
		//线程创建工厂类
		ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("测试并发接收线程池-%d")
				.daemon(true).build();
		//为执行错误的任务，进行错误处理
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 200,
				TimeUnit.SECONDS, queue, threadFactory);
		executor.allowCoreThreadTimeOut(true);


		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			executor.execute(()->doWork(message));
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
