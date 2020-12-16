package org.doming.example.mq.rabbitmq.eg5.rpc;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
/**
 * @Description:
 * @Author: null
 * @Date: 2018-12-15 15:30
 * @Version: v1.0
 */
public class RPCClient implements AutoCloseable {
	private Connection connection;
	private Channel channel;

	public RPCClient() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitMQConfiguration.HOST_NAME);
		factory.setUsername(RabbitMQConfiguration.USER_NAME);
		factory.setPassword(RabbitMQConfiguration.PASS_WORD);
		connection = factory.newConnection();
		channel = connection.createChannel();
	}

	public String call(String message) throws IOException, InterruptedException {
		final String corrId = UUID.randomUUID().toString();
		String replyQueueName = channel.queueDeclare().getQueue();
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
				.build();

		channel.basicPublish("", RabbitMQConfiguration.QUEUE_NAME_RPC, props, message.getBytes(StandardCharsets.UTF_8));

		final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
		String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
			}
		}, consumerTag -> {
		});

		String result = response.take();
		channel.basicCancel(ctag);
		return result;
	}

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		RPCClient client = new RPCClient();
		System.out.println(client.call("12"));


	}

	@Override public void close() throws Exception {
		connection.close();
	}
}
