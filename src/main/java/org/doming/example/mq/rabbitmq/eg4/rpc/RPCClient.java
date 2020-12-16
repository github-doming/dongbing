package org.doming.example.mq.rabbitmq.eg4.rpc;
import com.rabbitmq.client.*;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @Description: RPC客户端
 * @Author: null
 * @Date: 2018-11-08 17:43
 * @Version: v1.0
 */
public class RPCClient {

	private Connection connection;
	private Channel channel;
	private String replyQueueName;

	/**
	 * 定义一个RPC客户端
	 */
	public RPCClient() throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitMQConfiguration.HOST_NAME);
		factory.setUsername(RabbitMQConfiguration.USER_NAME);
		factory.setPassword(RabbitMQConfiguration.PASS_WORD);

		connection = factory.newConnection();
		channel = connection.createChannel();

		replyQueueName = channel.queueDeclare().getQueue();
	}

	/**
	 * 真正地请求
	 *
	 * @param message 消息
	 * @return 请求消息
	 */
	public String call(String message) throws IOException, InterruptedException {
		final String corrId = UUID.randomUUID().toString();
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
				.build();

		channel.basicPublish("", RabbitMQConfiguration.QUEUE_NAME_RPC, props, message.getBytes(StandardCharsets.UTF_8));

		final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

		channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
			@Override public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) {
				if (properties.getCorrelationId().equals(corrId)) {
					System.out.println(" [客户端当前] ： " + System.currentTimeMillis());
					response.offer(new String(body, StandardCharsets.UTF_8));
				}

			}
		});

		return response.take();
	}

	public void close() throws IOException {
		connection.close();
	}

	public static void test() {
		RPCClient client = null;
		String response;
		try {
			String num = "50";
			client = new RPCClient();
			System.out.println(" [doming] 请求 fib(" + num + ")");
			//RPC客户端发送调用请求，并等待影响，直到接收到
			response = client.call(num);
			System.out.println(" [````````````] 得到 '" + response + "'");
		} catch (IOException | TimeoutException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		test();
	}
}
