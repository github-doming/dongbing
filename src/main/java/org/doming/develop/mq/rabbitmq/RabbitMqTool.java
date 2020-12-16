package org.doming.develop.mq.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.doming.core.Executor;
import org.doming.core.configs.CharsetConfig;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.RandomTool;
import org.doming.develop.DefaultConfig;
import org.doming.develop.mq.common.MqTool;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @Description: RabbitMQ工具类
 * @Author: Dongming
 * @Date: 2019-06-17 15:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2028 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class RabbitMqTool extends MqTool {

	private static final Logger log = LogManager.getLogger(RabbitMqTool.class);

	/**
	 * 执行实例类
	 *
	 * @param className 类名称
	 * @param message   发送消息
	 * @return 执行结果消息
	 * @throws Throwable 执行实例类错误
	 */
	private static String executeClazz(String className, String message) throws Throwable {
		String response;
		try {
			Class<?> clazz = Class.forName(className);
			Object object = clazz.newInstance();
			if (!(object instanceof Executor)) {
				//获取执行类实例
				throw new IllegalAccessException("不合法访问异常，实例化MQ类出错,出错的MQ=" + className);
			}
			Executor instance = (Executor) object;
			//执行操作
			response = instance.run(message);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("找不到类异常，加载MQ类出错,出错的MQ=" + className);
		} catch (InstantiationException e) {
			throw new InstantiationException("实例化异常，实例化MQ类出错,出错的MQ=" + className);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessException("不合法访问异常，实例化MQ类出错,出错的MQ=" + className);
		} catch (RuntimeException e) {
			// 必须重新抛出异常给系统才能跳转
			throw new RuntimeException("运行中RuntimeException，业务出错,出错的MQ=" + className);
		} catch (Throwable t) {
			throw new Throwable("Throwable异常，业务出错,出错的MQ=" + className);
		}
		return response;
	}

    /**
     * 注册消费者
     * @param executor 线程执行器
     * @param className  执行类名
     * @param channel  Mq通道
     * @param queueName 队列名称
     */
    private static void registerConsumer(ThreadPoolExecutor executor, String className, Channel channel,
            String queueName) throws IOException {
        channel.basicQos(1);
        //消息消费者
        final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), CharsetConfig.UTF_8);
            try {
                if (executor != null) {
                    executor.execute(() -> {
                        try {
                            executeClazz(className, message);
                        } catch (Throwable throwable) {
                            log.error("执行MQ出错误", throwable);
                        }
                    });
                } else {
                    executeClazz(className, message);
                }
            } catch (Throwable throwable) {
                log.error(DefaultConfig.SIGN_MQ.concat("执行实例类错误"), throwable);
            } finally {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        //绑定消费者 和队列
        channel.basicConsume(queueName, false, deliverCallback, (CancelCallback) log::error);
    }

	/**
	 * 获取路由类型
	 *
	 * @param type 路由类型字符串
	 * @return 路由类型
	 */
	public static BuiltinExchangeType getExchangeType(String type) {
		switch (type) {
			case "TOPIC":
				return BuiltinExchangeType.TOPIC;
			case "DIRECT":
				return BuiltinExchangeType.DIRECT;
			case "FANOUT":
				return BuiltinExchangeType.FANOUT;
			case "HEADERS":
				return BuiltinExchangeType.HEADERS;
			default:
				throw new RuntimeException("未知的通道类型传入");
		}
	}

	/**
	 * 发送队列消息
	 *
	 * @param connection 消息队列链接通道
	 * @param queueName  队列名称
	 * @param message    发送消息
	 * @return 发送结果
	 */
	public static String sendQueue(Connection connection, String queueName, String message)
			throws IOException, TimeoutException {
		try (Channel channel = connection.createChannel()) {
			channel.queueDeclare(queueName, true, false, false, null);
			channel.basicPublish("", queueName, null, message.getBytes(CharsetConfig.UTF_8));
		}
		return TypeEnum.TRUE.name();
	}

	/**
	 * 接收并处理队列消息
	 *
	 * @param connection 消息队列链接通道
	 * @param queueName  队列名称
	 * @param className  执行类名
	 * @return 路由通道
	 * @throws IOException 发送队列消息失败
	 */
	public static Channel receiveQueue(Connection connection, String queueName, String className) throws IOException {
		return receiveQueue(null, connection, queueName, className);
	}

	protected static Channel receiveQueue(ThreadPoolExecutor executor, Connection connection, String queueName,
			String className) throws IOException {
		final Channel channel = connection.createChannel();
		channel.queueDeclare(queueName, true, false, false, null);

		//注册消费者
		registerConsumer(executor, className, channel, queueName);
		return channel;
	}

	/**
	 * 发送路由消息
	 *
	 * @param connection   消息队列链接通道
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      发送消息
	 * @return 发送结果
	 * @throws IOException 发送队列消息失败
	 */
	public static String sendExchange(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
			String routingKey, String message) throws IOException, TimeoutException {
		try (Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(exchangeName, exchangeType);
			channel.basicPublish(exchangeName, routingKey, null, message.getBytes(CharsetConfig.UTF_8));
		}
		return TypeEnum.TRUE.name();
	}

	/**
	 * 接收路由消息
	 *
	 * @param connection   消息队列链接通道
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingKeys  路由键数组
	 * @param className    执行类名
	 * @return 路由通道
	 */
	public static Channel receiveExchange(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
			String[] routingKeys, String className) throws IOException {
		return receiveExchange(null, connection, exchangeType, exchangeName, routingKeys, className);
	}
	/**
	 * 接收路由消息
	 *
	 * @param connection   消息队列链接通道
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingKeys  路由键数组
	 * @param className    执行类名
	 * @return 路由通道
	 */
	protected static Channel receiveExchange(ThreadPoolExecutor executor, Connection connection,
			BuiltinExchangeType exchangeType, String exchangeName, String[] routingKeys, String className)
			throws IOException {
		final Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchangeName, exchangeType);
		String queueName = channel.queueDeclare().getQueue();
		for (String bindingKey : routingKeys) {
			channel.queueBind(queueName, exchangeName, bindingKey);
		}
		//注册消费者
		registerConsumer(executor, className, channel, queueName);
		return channel;
	}

	/**
	 * 发送RPC路由消息
	 *
	 * @param connection   消息队列链接通道
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键数组
	 * @param message      发送消息
	 * @return 回调信息
	 */
	public static String sendExchangeRpc(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
			String routingKey, String message) throws IOException, InterruptedException, TimeoutException {
		try (Channel channel = connection.createChannel()) {
			//唯一id
			final String corrId = RandomTool.getNumLetter32();
			//回传通道名称
			String replyQueueName = channel.queueDeclare().getQueue();
			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId)
					.replyTo(replyQueueName).build();
			channel.exchangeDeclare(exchangeName, exchangeType);
			channel.basicPublish(exchangeName, routingKey, props, message.getBytes(CharsetConfig.UTF_8));
			//回传队列
			final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
			//接收消费者
			String consumerStr = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
				if (delivery.getProperties().getCorrelationId().equals(corrId)) {
					response.offer(new String(delivery.getBody(), CharsetConfig.UTF_8));
				}
			}, (CancelCallback) log::error);
			String result = response.take();
			channel.basicCancel(consumerStr);
			return result;
		}
	}

	/**
	 * 接收RPC路由消息
	 *
	 * @param connection   消息队列链接通道
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingKeys  路由键数组
	 * @param className    发送消息
	 * @return 路由通道
	 */
	public static Channel receiveExchangeRpc(Connection connection, BuiltinExchangeType exchangeType,
			String exchangeName, String[] routingKeys, String className) throws IOException {
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(exchangeName, exchangeType);
		String queueName = channel.queueDeclare().getQueue();
		for (String bindingKey : routingKeys) {
			channel.queueBind(queueName, exchangeName, bindingKey);
		}
		channel.basicQos(1);
		Object monitor = new Object();
		final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
					.correlationId(delivery.getProperties().getCorrelationId()).build();
			String response = "";
			try {
				String message = new String(delivery.getBody(), CharsetConfig.UTF_8);
				response = executeClazz(className, message);
			} catch (Throwable throwable) {
				log.error(DefaultConfig.SIGN_MQ.concat("执行实例类错误"), throwable);
				JSONObject jObj = new JSONObject();
				jObj.put("success", false);
				jObj.put("msg", throwable.getMessage());
				jObj.put("codeSys", "500");
				jObj.put("code", "500");
				response = jObj.toString();
			} finally {
				log.debug("返回的消息为=" + response);
				channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps,
						response.getBytes(CharsetConfig.UTF_8));
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				synchronized (monitor) {
					monitor.notify();
				}
			}
		};
		channel.basicConsume(queueName, false, deliverCallback, (CancelCallback) log::error);
		return channel;

	}
}
