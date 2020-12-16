package org.doming.example.mq.rabbitmq.eg5.rpc;
import com.rabbitmq.client.*;
import org.doming.example.mq.rabbitmq.RabbitMQConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
/**
 * @Description:
 * @Author: null
 * @Date: 2018-12-15 15:42
 * @Version: v1.0
 */
public class RPCServer implements Runnable {

	private static Channel channel;
	static boolean isGoOn = true;

	public RPCServer() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitMQConfiguration.HOST_NAME);
		factory.setUsername(RabbitMQConfiguration.USER_NAME);
		factory.setPassword(RabbitMQConfiguration.PASS_WORD);
		Connection connection = factory.newConnection();
		channel = connection.createChannel();
	}

	public static void work() throws IOException {
		channel.queueDeclare( RabbitMQConfiguration.QUEUE_NAME_RPC, false, false, false, null);
		channel.queuePurge( RabbitMQConfiguration.QUEUE_NAME_RPC);
		channel.basicQos(1);
		Object monitor = new Object();
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			AMQP.BasicProperties replyProps = new AMQP.BasicProperties
					.Builder()
					.correlationId(delivery.getProperties().getCorrelationId())
					.build();
			String response = "";
			try {
				String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
				int n = Integer.parseInt(message);
				response += fib(n);
			} finally {
				channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes(
						StandardCharsets.UTF_8));
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				synchronized (monitor) {
					monitor.notify();
				}
			}
		};

		channel.basicConsume(RabbitMQConfiguration.QUEUE_NAME_RPC, false, deliverCallback, (consumerTag -> { }));
		while (isGoOn) {
			synchronized (monitor) {
				try {
					monitor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
	@Override public void run() {
		try {
			work();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(1);
		try {
			service.execute(new RPCServer());
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
