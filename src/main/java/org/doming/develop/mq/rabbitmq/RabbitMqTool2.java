package org.doming.develop.mq.rabbitmq;

import c.a.tools.mvc.exception.BizRuntimeException;
import com.rabbitmq.client.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.doming.core.Executor;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.DefaultConfig;
import org.doming.develop.mq.common.MqTool;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Description: RabbitMQ工具类
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-13 10:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class RabbitMqTool2 extends MqTool {

	private static final Map<String, Builder> BUILDER_MAP = new HashMap<>();

	private static final Object KEY = new Object();

	/**
	 * 销毁通道
	 */
	public static void destroy(String builderKey) {
		if (StringTool.isEmpty(builderKey)) {
			for (Builder builder : BUILDER_MAP.values()) {
				builder.destroy();
			}
			BUILDER_MAP.clear();
		} else {
			if (BUILDER_MAP.containsKey(builderKey)) {
				BUILDER_MAP.get(builderKey).destroy();
				BUILDER_MAP.remove(builderKey);
			}
		}
		log.info("销毁RabbitMQTool完成");
	}

	/**
	 * 销毁通道
	 *
	 * @param exchangeName 路由名称
	 * @param routingKeys  路由键
	 * @return mq的连接
	 */
	protected static Connection destroy(String exchangeName, String[] routingKeys) {
		if (StringTool.isEmpty(exchangeName) || StringTool.isEmpty(routingKeys)) {
			return null;
		}
		String builderKey = exchangeName.concat("_").concat(StringUtils.join(routingKeys, "#"));
		if (BUILDER_MAP.containsKey(builderKey)) {
			Builder builder = BUILDER_MAP.get(builderKey);
			builder.closeChannel();
			BUILDER_MAP.remove(builderKey);
			return builder.connection;
		}
		return null;
	}

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
			Class classObj = Class.forName(className);
			//获取执行类实例
			Executor instance = (Executor) classObj.newInstance();
			//执行操作
			// 不要用反射,因为反射不能与业务异常bizException绑定起来
			response = instance.run(message);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("找不到类异常，加载MQ类出错,出错的MQ=" + className);
		} catch (InstantiationException e) {
			throw new InstantiationException("实例化异常，实例化MQ类出错,出错的MQ=" + className);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessException("不合法访问异常，实例化MQ类出错,出错的MQ=" + className);
		} catch (BizRuntimeException e) {
			// 必须重新抛出异常给系统才能跳转
			throw new BizRuntimeException("BizRuntimeException，业务出错,出错的MQ=" + className);
		} catch (RuntimeException e) {
			// 必须重新抛出异常给系统才能跳转
			throw new RuntimeException("运行中RuntimeException，业务出错,出错的MQ=" + className);
		} catch (Throwable t) {
			throw new Throwable("Throwable异常，业务出错,出错的MQ=" + className);
		}
		return response;
	}

	/**
	 * 定义消费者侦听器
	 *
	 * @param builder 消息队列信息钩子类
	 */
	private static void consumerListener(Builder builder) throws IOException {
		final Consumer consumer = new DefaultConsumer(builder.channel) {
			@Override public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) {
				String message = new String(body, StandardCharsets.UTF_8);
				//接下来就可以根据消息处理一些事情
				try {
					executeClazz(builder.className, message);
				} catch (Throwable e) {
					log.info("执行实例类错误");
					log.error(DefaultConfig.SIGN_MQ, e);
				} finally {
					if (!builder.autoAck) {
						// 手动回执
						try {
							builder.channel.basicAck(envelope.getDeliveryTag(), false);
						} catch (IOException e) {
							log.info("回执消息错误", e);
						}
					}
				}
			}
		};
		try {
			builder.channel.basicConsume(builder.queueName, builder.autoAck, consumer);
		} catch (IOException e) {
			log.info("确认消息错误");
			throw e;
		}
		BUILDER_MAP.put(builder.exchangeName.concat("_").concat(StringUtils.join(builder.routingKeys, "#")), builder);
	}

	/**
	 * 发送队列消息
	 *
	 * @param builder 消息队列信息钩子类
	 */
	private static void sendQueue(Builder builder) throws IOException {
		if (builder == null) {
			throw new RuntimeException("消息队列信息钩子类为空");
		}
		try {
			// 发送消息
			builder.channel.basicPublish("", builder.queueName, null, builder.message);
		} catch (IOException e) {
			log.info("声明队列失败");
			throw e;
		} finally {
			builder.closeChannel();

		}
	}

	/**
	 * 接收队列
	 */
	private static void receiveQueue(Builder builder) throws IOException {
		if (builder == null) {
			throw new RuntimeException("消息队列信息钩子类为空");
		}
		if (!builder.autoAck) {
			builder.channel.basicQos(1);
		}
		consumerListener(builder);

	}

	/**
	 * 发送路由消息
	 *
	 * @param builder 消息队列信息钩子类
	 */
	private static void sendExchange(Builder builder) throws IOException {
		if (builder == null) {
			throw new RuntimeException("消息队列信息钩子类为空");
		}
		try {
			// 发送消息
			if (ContainerTool.notEmpty(builder.routingKeys)) {
				for (String routingKey : builder.routingKeys) {
					builder.channel.basicPublish(builder.exchangeName, routingKey, null, builder.message);
				}
			} else {
				builder.channel.basicPublish(builder.exchangeName, "", null, builder.message);
			}
		} catch (IOException e) {
			log.info("声明队列失败");
			throw e;
		} finally {
			builder.closeChannel();
		}
	}

	/**
	 * 接收路由
	 */
	private static void receiveExchange(Builder builder) throws IOException {
		if (builder == null) {
			throw new RuntimeException("消息队列信息钩子类为空");
		}

		if (ContainerTool.notEmpty(builder.routingKeys)) {
			for (String routingKey : builder.routingKeys) {
				builder.channel.queueBind(builder.queueName, builder.exchangeName, routingKey);
			}
		} else {
			builder.channel.queueBind(builder.queueName, builder.exchangeName, "");
		}
		consumerListener(builder);
	}

	/**
	 * 发送RPC路由消息
	 *
	 * @param builder 消息队列信息钩子类
	 * @return 回调信息
	 */
	private static String sendRPCExchange(Builder builder) throws IOException, InterruptedException {
		if (builder == null) {
			throw new RuntimeException("消息队列信息钩子类为空");
		}
		//通道id
		String corrId = UUID.randomUUID().toString();
		//回调配置
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(builder.queueName)
				.build();
		// 发送消息
		if (ContainerTool.notEmpty(builder.routingKeys)) {
			for (String routingKey : builder.routingKeys) {
				builder.channel.basicPublish(builder.exchangeName, routingKey, props, builder.message);
			}
		} else {
			builder.channel.basicPublish(builder.exchangeName, "", props, builder.message);
		}

		//接收回调消息
		final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
		String tag = builder.channel.basicConsume(builder.queueName, true, (consumerTag, delivery) -> {
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
			}
		}, consumerTag -> {
		});
		try {
			String take = response.take();
			builder.channel.basicCancel(tag);
			return take;
		} catch (IOException | InterruptedException e) {
			log.info("接收回调消息失败");
			throw e;
		} finally {
			builder.closeChannel();
		}
	}

	/**
	 * 接收RPC路由消息
	 */
	private static void receiveExchangeRPC(Builder builder) throws IOException {
		if (builder == null) {
			throw new RuntimeException("消息队列信息钩子类为空");
		}
		//接收消息，绑定路由键
		if (ContainerTool.notEmpty(builder.routingKeys)) {
			for (String routingKey : builder.routingKeys) {
				builder.channel.queueBind(builder.queueName, builder.exchangeName, routingKey);
			}
		} else {
			builder.channel.queueBind(builder.queueName, builder.exchangeName, "");
		}

		if (!builder.autoAck) {
			builder.channel.basicQos(1);
		}
		//消息回调
		final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
					.correlationId(delivery.getProperties().getCorrelationId()).build();
			String response = "";
			try {
				String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
				response = executeClazz(builder.className, message);
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
				JSONObject jObj = new JSONObject();
				jObj.put("success", false);
				jObj.put("msg", e.getMessage());
				jObj.put("codeSys", "500");
				jObj.put("code", "500");
				response = jObj.toString();
			} finally {
				log.debug("返回的消息为=" + response);
				builder.channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps,
						response.getBytes(StandardCharsets.UTF_8));
				// 手动回执
				builder.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				synchronized (KEY) {
					KEY.notify();
				}
			}
		};
		// 消息确认
		builder.channel.basicConsume(builder.queueName, builder.autoAck, deliverCallback, (consumerTag -> {
		}));
		BUILDER_MAP.put(builder.exchangeName.concat("_").concat(StringUtils.join(builder.routingKeys, "#")), builder);
	}

	/**
	 * 发送队列消息
	 *
	 * @param connection 消息队列链接通道
	 * @param queueName  队列名称
	 * @param message    发送消息
	 * @return 发送结果
	 */
	public static String sendQueue(Connection connection, String queueName, String message) throws IOException {
		try {
			//创建发送钩子类
			Builder builder = new Builder(connection, queueName, message.getBytes(StandardCharsets.UTF_8)).binding();
			//发送队列消息
			sendQueue(builder);
		} catch (IOException e) {
			log.info("发送队列消息失败");
			throw e;
		}
		return TypeEnum.TRUE.name();
	}

	/**
	 * 接收并处理队列消息
	 *
	 * @param connection 消息队列链接通道
	 * @param className  执行类名
	 * @param queueName  队列名称
	 * @throws IOException 发送队列消息失败
	 */
	public static void receiveQueue(Connection connection, String queueName, String className) throws IOException {
		//创建接收钩子类
		try {
			Builder builder = new Builder(connection, queueName, className).bind();
			receiveQueue(builder);
		} catch (IOException e) {
			log.info("接收并处理队列消息失败");
			throw e;
		}

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
			String routingKey, String message) throws IOException {
		try {
			Builder builder = new Builder(connection, exchangeType, exchangeName, routingKey,
					message.getBytes(StandardCharsets.UTF_8)).binding();
			sendExchange(builder);
			return TypeEnum.TRUE.name();
		} catch (IOException e) {
			log.info("发送队列消息失败");
			throw e;
		}
	}

	/**
	 * 接收路由消息
	 *
	 * @param connection   消息队列链接通道
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingKeys  路由键数组
	 * @param className    执行类名
	 */
	public static void receiveExchange(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
			String[] routingKeys, String className) throws IOException {
		//创建接收钩子类
		try {
			Builder builder = new Builder(connection, exchangeType, exchangeName, routingKeys, className).bind();
			receiveExchange(builder);
		} catch (IOException e) {
			log.info("接收并处理队列消息失败");
			throw e;
		}
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
	public static String sendExchangeRPC(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
			String routingKey, String message) throws IOException, InterruptedException {

		try {
			Builder builder = new Builder(connection, exchangeType, exchangeName, routingKey,
					message.getBytes(StandardCharsets.UTF_8)).binding();
			return sendRPCExchange(builder);
		} catch (IOException | InterruptedException e) {
			log.info("发送队列消息失败");
			throw e;
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
	 */
	public static void receiveExchangeRPC(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
			String[] routingKeys, String className) throws IOException {
		//创建接收钩子类
		try {
			Builder builder = new Builder(connection, exchangeType, exchangeName, routingKeys, className).bind();
			receiveExchangeRPC(builder);
		} catch (IOException e) {
			log.info("接收并处理队列消息失败");
			throw e;
		}
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
	 * 消息队列信息构建类
	 */
	private static class Builder {
		public Connection connection;
		private Channel channel;
		private String className;
		private String queueName;
		private String exchangeName;
		private String[] routingKeys;
		private byte[] message;
		private BuiltinExchangeType exchangeType;

		private boolean autoAck = false;

		private boolean durable = true;
		private boolean exclusive = false;
		private boolean autoDelete = false;
		private Map<String, Object> arguments = null;

		/**
		 * 关闭通道
		 */
		void closeChannel() {
			try {
				if (connection != null && connection.isOpen() && channel != null && channel.isOpen()) {
					channel.abort();
				}
				channel = null;
			} catch (IOException e) {
				log.error("销毁通道错误", e);
			}
		}

		public void closeConnection() {
			try {
				if (connection != null && connection.isOpen()) {
					connection.close();
				}
			} catch (IOException e) {
				log.error("销毁链接错误", e);
			}
		}

		public void destroy() {
			closeChannel();
			closeConnection();
		}

		/**
		 * 初始化发送钩子类
		 *
		 * @return 钩子类
		 */
		Builder binding() throws IOException {
			try {
				channel = connection.createChannel();
			} catch (IOException e) {
				log.error("创建发送通道失败");
				throw e;
			}
			//队列名称
			if (StringTool.isEmpty(queueName)) {

				queueName = channel.queueDeclare().getQueue();
			}
			if (exchangeType != null) {
				// 声明路由
				if (StringTool.isEmpty(exchangeName)) {
					throw new RuntimeException("未知的路由名称");
				}
				channel.exchangeDeclare(exchangeName, exchangeType);
			} else {
				// 声明队列
				channel.queueDeclare(queueName, durable, exclusive, autoDelete, arguments);
			}

			return this;

		}

		/**
		 * 初始化接收钩子类
		 */
		Builder bind() throws IOException {
			try {
				channel = connection.createChannel();
			} catch (IOException e) {
				log.error("创建接收通道失败");
				throw e;
			}

			//队列名称
			if (StringTool.isEmpty(queueName)) {
				queueName = channel.queueDeclare().getQueue();
			}

			if (exchangeType != null) {
				// 声明路由
				if (StringTool.isEmpty(exchangeName)) {
					throw new RuntimeException("未知的通道名称");
				}
				channel.exchangeDeclare(exchangeName, exchangeType);
				if (ContainerTool.notEmpty(routingKeys)) {
					for (String routingKey : routingKeys) {
						channel.queueBind(queueName, exchangeName, routingKey);
					}
				} else {
					channel.queueBind(queueName, exchangeName, "");
				}
			} else {
				//声明队列
				channel.queueDeclare(queueName, durable, exclusive, autoDelete, arguments);
			}

			return this;
		}

		/**
		 * 队列消息-发送者
		 *
		 * @param connection mq链接
		 * @param queueName  队列名称
		 * @param message    发送的消息
		 */
		Builder(Connection connection, String queueName, byte[] message) {
			this.connection = connection;
			this.queueName = queueName;
			this.message = message;
		}

		/**
		 * 队列消息-接收者
		 *
		 * @param connection mq链接
		 * @param queueName  队列名称
		 * @param className  执行类名
		 */
		Builder(Connection connection, String queueName, String className) {
			this.connection = connection;
			this.queueName = queueName;
			this.className = className;
		}

		/**
		 * 路由消息-发送者
		 *
		 * @param connection   mq链接
		 * @param exchangeType 路由类型
		 * @param exchangeName 路由名称
		 * @param message      发送的消息
		 */
		Builder(Connection connection, BuiltinExchangeType exchangeType, String exchangeName, String routingKey,
				byte[] message) {
			this.connection = connection;
			this.exchangeType = exchangeType;
			this.exchangeName = exchangeName;
			if (StringTool.notEmpty(routingKey)) {
				this.routingKeys = new String[]{routingKey};
			}
			this.message = message;
		}

		/**
		 * 路由消息-接收者
		 *
		 * @param connection   mq链接
		 * @param exchangeType 路由类型
		 * @param exchangeName 路由名称
		 * @param routingKeys  路由键
		 * @param className    执行类名
		 */
		Builder(Connection connection, BuiltinExchangeType exchangeType, String exchangeName, String[] routingKeys,
				String className) {
			this.connection = connection;
			this.exchangeType = exchangeType;
			this.exchangeName = exchangeName;
			this.routingKeys = routingKeys;
			this.className = className;

		}

	}
}
