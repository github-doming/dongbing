package org.doming.develop.mq.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.DefaultConfig;
import org.doming.develop.mq.bean.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

/**
 * @Description: MQ工具类
 * @Author: null
 * @Date: 2018-11-15 14:57
 * @Version: v1.0
 */
public class RabbitMqUtil {

	protected static final Logger log = LogManager.getLogger(RabbitMqUtil.class);

	private static boolean isShutdown = true;

	private ConnectionFactory factory;

	private MqConfigEntity configEntity;

	private Map<String, Map<Connection, List<Channel>>> destroyInfo;
	private static final String DESTROY_FORMAT = "%s_%s";

	private static volatile RabbitMqUtil instance = null;

	private static String configPathKey;
	private static String configPathVal;

	private ThreadPoolExecutor messageExecutor;

	protected RabbitMqUtil() {
	}

	//TODO 工具方法
	// 工具方法

	public static RabbitMqUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (RabbitMqUtil.class) {
				if (instance == null) {
					RabbitMqUtil instance = new RabbitMqUtil();
					// 初始化
					instance.init();
					RabbitMqUtil.instance = instance;
					isShutdown = false;
				}
			}
		}
		return instance;
	}

	/**
	 * 设置配置文件键
	 *
	 * @param configPathKey 配置文件键
	 */
	public static boolean setConfigPathKey(String configPathKey) {
		if (StringTool.notEmpty(RabbitMqUtil.configPathKey)) {
			return false;
		}
		if (instance == null) {
			synchronized (RabbitMqUtil.class) {
				if (instance == null) {
					RabbitMqUtil.configPathKey = configPathKey;
				}
			}
		}
		return true;
	}

	/**
	 * 设置配置文件键
	 *
	 * @param configPathVal 配置文件值
	 */
	public static boolean setConfigPathVal(String configPathVal) {
		if (StringTool.notEmpty(RabbitMqUtil.configPathVal)) {
			return false;
		}
		if (instance == null) {
			synchronized (RabbitMqUtil.class) {
				if (instance == null) {
					RabbitMqUtil.configPathVal = configPathVal;
				}
			}
		}
		return true;
	}

	private void init() throws Exception {
		if (StringTool.notEmpty(configPathVal)) {
			configEntity = RabbitMqTool.getConfigEntityByPath(configPathVal);
		} else if (StringTool.notEmpty(configPathKey)) {
			configEntity = RabbitMqTool.getConfigEntity(configPathKey);
		} else {

			throw new RuntimeException("初始化失败，未指定读取配置文件信息");
		}
		MqConfig config = configEntity.getConfig();
		// 定义连接工厂
		factory = new ConnectionFactory();
		// 设置服务地址
		factory.setHost(config.getIp());
		// 端口
		factory.setPort(Integer.parseInt(config.getPort()));
		// 设置账号信息，用户名、密码、虚拟主机
		factory.setVirtualHost(config.getVirtualHost());
		factory.setUsername(config.getUserName());
		factory.setPassword(config.getPassword());
		destroyInfo = new HashMap<>();
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}
		if (ContainerTool.notEmpty(instance.destroyInfo)) {
			for (Map<Connection, List<Channel>> chMap : instance.destroyInfo.values()) {
				instance.destroy(chMap);
			}
		}
		instance.destroyInfo = null;
		instance.configEntity = null;
		instance.factory = null;
		instance = null;
		isShutdown = true;
	}

	/**
	 * 销毁通道和链接
	 *
	 * @param chMap 通道和链接
	 */
	private void destroy(Map<Connection, List<Channel>> chMap) {
		if (ContainerTool.isEmpty(chMap)) {
			return;
		}
		for (Map.Entry<Connection, List<Channel>> entry : chMap.entrySet()) {
			for (Channel channel : entry.getValue()) {
				try {
					if (channel.isOpen()) {
						channel.close();
					}
				} catch (IOException | TimeoutException e) {
					log.error(DefaultConfig.SIGN_MQ, e);
				}
			}
			Connection connection = entry.getKey();
			if (connection.isOpen()) {
				try {
					connection.close();
				} catch (IOException e) {
					log.error(DefaultConfig.SIGN_MQ, e);
				}
			}
		}
	}

	/**
	 * 是否关闭
	 *
	 * @return 关闭
	 */
	public static boolean isShutdown() {
		return isShutdown;
	}

	/**
	 * 创建一个连接
	 *
	 * @return MQ连接
	 */
	public Connection createConnection() throws IOException, TimeoutException {
		//采用默认的工厂类
		Connection connection;
		try {
			connection = factory.newConnection();
		} catch (IOException | TimeoutException e) {
			log.info("创建消息队列链接失败");
			throw e;
		}
		return connection;
	}

	/**
	 * 关闭连接
	 *
	 * @param connection 连接
	 */
	public void closeConnection(Connection connection) throws IOException {
		try {
			if (connection != null && connection.isOpen()) {
				connection.close();
			}
		} catch (IOException e) {
			log.info("关闭链接失败");
			throw e;
		}
		log.trace("关闭链接完成");
	}

	/**
	 * 设置消费者执行者的执行线程池
	 *
	 * @param executor 执行线程池
	 */
	public void setExecutor(ThreadPoolExecutor executor) {
		if (messageExecutor != null) {
			throw new IllegalArgumentException("已经存在消费者执行者的执行线程池,无法重复指定");
		}
		this.messageExecutor = executor;
	}

	/**
	 * 设置消费者执行者的执行线程池
	 *
	 * @return 执行线程池
	 */
	public ThreadPoolExecutor getExecutor() {
		return this.messageExecutor;
	}

	// 工具方法

	// TODO 基础方法
	// 基础方法

	/**
	 * 发送队列消息
	 *
	 * @param queueName 队列名称
	 * @param message   发送消息
	 * @return 发送结果
	 */
	public String sendQueue(String queueName, String message) {
		try (Connection connection = createConnection()) {
			return sendQueue(connection, queueName, message);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("发送队列消息失败"), e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 发送队列消息
	 *
	 * @param connection mq连接
	 * @param queueName  队列名称
	 * @param message    发送消息
	 * @return 发送结果
	 */
	public String sendQueue(Connection connection, String queueName, String message) {
		try {
			return RabbitMqTool.sendQueue(connection, queueName, message);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("发送队列消息失败"), e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 接收队列消息
	 *
	 * @param queueName 队列名称
	 * @return 接收结果
	 */
	public String receiveQueue(String queueName) {
		try {
			return receiveQueue(createConnection(), queueName);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("接收队列消息失败"), e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 接收队列消息
	 *
	 * @param connection mq连接
	 * @param queueName  队列名称
	 * @return 接收结果
	 */
	public String receiveQueue(Connection connection, String queueName) throws IOException {
		Map<Connection, List<Channel>> chMap = new HashMap<>(1);
		List<Channel> channels = new ArrayList<>();
		for (MqQueue queue : configEntity.getQueueList()) {
			if (queueName.equals(queue.getName())) {
				for (MqReceiveClient client : queue.getClientList()) {
					Channel channel = RabbitMqTool
							.receiveQueue(messageExecutor, connection, queueName, client.getClassName());
					channels.add(channel);
				}
			}

		}
		if (ContainerTool.notEmpty(channels)) {
			chMap.put(connection, channels);
			destroyInfo.put(String.format(DESTROY_FORMAT, "queue", queueName), chMap);
		}
		return TypeEnum.TRUE.name();
	}

	/**
	 * 发送路由消息
	 *
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param message      消息
	 * @return 发送结果
	 */
	public String sendExchange(BuiltinExchangeType exchangeType, String exchangeName, String routingKey,
							   String message) {
		try (Connection connection = createConnection()) {
			return sendExchange(connection, exchangeType, exchangeName, routingKey, message);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("发送路由消息失败"), e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 发送路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param message      消息
	 * @return 发送结果
	 */
	public String sendExchange(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
							   String routingKey, String message) {
		try {
			return RabbitMqTool.sendExchange(connection, exchangeType, exchangeName, routingKey, message);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("发送路由消息失败"), e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 接收路由消息
	 * 根据配置文件判断是什么类型
	 *
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchange(String exchangeName) {
		return receiveExchange(exchangeName, null);

	}

	/**
	 * 接收路由消息
	 * 根据配置文件判断是什么类型
	 *
	 * @param exchangeName 路由名称
	 * @param routingName  路由键name
	 * @return 接收结果
	 */
	public String receiveExchange(String exchangeName, String routingName) {
		try {
			return receiveExchange(createConnection(), exchangeName, routingName);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("接收路由消息失败"), e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 接收路由
	 *
	 * @param connection   mq连接
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchange(Connection connection, String exchangeName) throws IOException {
		return receiveExchange(connection, exchangeName, null);
	}

	/**
	 * 接收路由
	 *
	 * @param connection  mq连接
	 * @param name        路由名称
	 * @param routingName 路由键name
	 * @return 接收结果
	 */
	public String receiveExchange(Connection connection, String name, String routingName) throws IOException {
		Map<Connection, List<Channel>> chMap = new HashMap<>(1);
		List<Channel> channels = new ArrayList<>();
		for (MqExchange exchange : configEntity.getExchangeList()) {
			if (name.equals(exchange.getName())) {
				BuiltinExchangeType exchangeType = RabbitMqTool.getExchangeType(exchange.getType());
				for (MqReceiveClient client : exchange.getClientList()) {
					Channel channel;
					String exchangeName = StringTool.notEmpty(client.getName()) ? client.getName() : name;
					if ("RPC".equals(exchange.getDesc())) {
						channel = RabbitMqTool.receiveExchangeRpc(connection, exchangeType, exchangeName,
								client.getRoutingKeys(routingName), client.getClassName());
					} else {
						channel = RabbitMqTool.receiveExchange(messageExecutor, connection, exchangeType, exchangeName,
								client.getRoutingKeys(routingName), client.getClassName());
					}
					channels.add(channel);
				}
			}
		}
		if (ContainerTool.notEmpty(channels)) {
			chMap.put(connection, channels);
			destroyInfo.put(String.format(DESTROY_FORMAT, name, routingName), chMap);
			return TypeEnum.TRUE.name();
		}
		return TypeEnum.FALSE.name();
	}

	/**
	 * 接收路由消息
	 *
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchange(BuiltinExchangeType exchangeType, String exchangeName) {
		return receiveExchange(exchangeType, exchangeName, null);
	}

	/**
	 * 接收路由消息
	 *
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingName  路由键name
	 * @return 接收结果
	 */
	public String receiveExchange(BuiltinExchangeType exchangeType, String exchangeName, String routingName) {
		try {
			return receiveExchange(createConnection(), exchangeType, exchangeName, routingName);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("接收路由消息失败"), e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 接收路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchange(Connection connection, BuiltinExchangeType exchangeType, String exchangeName)
			throws IOException {
		return receiveExchange(connection, exchangeType, exchangeName, null);
	}

	/**
	 * 接收路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingName  路由键name
	 * @return 接收结果
	 */
	public String receiveExchange(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
								  String routingName) throws IOException {
		Map<Connection, List<Channel>> chMap = new HashMap<>(1);
		List<Channel> channels = new ArrayList<>();
		for (MqExchange exchange : configEntity.getExchangeList()) {
			if (exchangeType.name().equals(exchange.getType()) && exchangeName.equals(exchange.getName())) {
				for (MqReceiveClient client : exchange.getClientList()) {
					Channel channel;
					if ("RPC".equals(exchange.getDesc())) {
						channel = RabbitMqTool.receiveExchangeRpc(connection, exchangeType, exchangeName,
								client.getRoutingKeys(routingName), client.getClassName());
					} else {
						channel = RabbitMqTool.receiveExchange(messageExecutor, connection, exchangeType, exchangeName,
								client.getRoutingKeys(routingName), client.getClassName());
					}
					channels.add(channel);
				}
			}
		}
		if (ContainerTool.notEmpty(channels)) {
			chMap.put(connection, channels);
			destroyInfo.put(String.format(DESTROY_FORMAT, exchangeName, routingName), chMap);
		}
		return TypeEnum.TRUE.name();
	}

	/**
	 * 发送Rpc路由消息
	 *
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 回调信息
	 */
	public String sendExchangeRpc(BuiltinExchangeType exchangeType, String exchangeName, String routingKey,
								  String message) {
		try (Connection connection = createConnection()) {
			return sendExchangeRpc(connection, exchangeType, exchangeName, routingKey, message);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("接收路由消息失败"), e);
			return null;
		}
	}

	/**
	 * 发送Rpc路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeType 路由类型
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 回调信息
	 */
	public String sendExchangeRpc(Connection connection, BuiltinExchangeType exchangeType, String exchangeName,
								  String routingKey, String message) {
		try {
			return RabbitMqTool.sendExchangeRpc(connection, exchangeType, exchangeName, routingKey, message);
		} catch (IOException | InterruptedException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("发送Rpc路由消息失败"), e);
			return null;
		}
	}

	/**
	 * 发送路由消息
	 *
	 * @param exchangeType 路由类型
	 * @param exchangeId   路由id
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 回调信息
	 */
	public String sendExchangeById(BuiltinExchangeType exchangeType, String exchangeId, String routingKey,
								   String message) {
		return sendExchange(exchangeType, String.format(configEntity.getConfig().getExchangeFormat(), exchangeId),
				routingKey, message);
	}

	/**
	 * 发送路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeType 路由类型
	 * @param exchangeId   路由id
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 回调信息
	 */
	public String sendExchangeById(Connection connection, BuiltinExchangeType exchangeType, String exchangeId,
								   String routingKey, String message) {
		return sendExchange(connection, exchangeType,
				String.format(configEntity.getConfig().getExchangeFormat(), exchangeId), routingKey, message);
	}

	/**
	 * 根据路由配置文件id绑定
	 * 接收路由消息
	 *
	 * @param exchangeId 路由id
	 * @return 接收结果
	 */
	public String receiveExchangeById(String exchangeId) {
		return receiveExchangeById(exchangeId, "");
	}

	/**
	 * 根据路由配置文件id绑定
	 * 接收路由消息
	 *
	 * @param exchangeId  路由id
	 * @param routingName 路由键id
	 * @return 接收结果
	 */
	public String receiveExchangeById(String exchangeId, String routingName) {
		try {
			return receiveExchangeById(createConnection(), exchangeId, routingName);
		} catch (IOException | TimeoutException e) {
			log.error(DefaultConfig.SIGN_MQ.concat("接收路由消息失败"), e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 根据路由配置文件id绑定
	 * 接收路由消息
	 *
	 * @param connection mq连接
	 * @param exchangeId 路由id
	 * @return 接收结果
	 */
	public String receiveExchangeById(Connection connection, String exchangeId) throws IOException {
		return receiveExchangeById(connection, exchangeId, null);
	}

	/**
	 * 根据路由配置文件id绑定
	 * 接收路由消息
	 *
	 * @param connection  mq连接
	 * @param exchangeId  路由id
	 * @param routingName 路由键id
	 * @return 接收结果
	 */
	public String receiveExchangeById(Connection connection, String exchangeId, String routingName) throws IOException {
		Map<Connection, List<Channel>> chMap = new HashMap<>(1);
		List<Channel> channels = new ArrayList<>();
		for (MqExchange exchange : configEntity.getExchangeList()) {
			if (exchangeId.equals(exchange.getId())) {
				//路由类型
				BuiltinExchangeType exchangeType = RabbitMqTool.getExchangeType(exchange.getType());
				//路由名称
				String name = String.format(configEntity.getConfig().getExchangeFormat(), exchangeId);
				for (MqReceiveClient client : exchange.getClientList()) {
					Channel channel;
					String exchangeName = StringTool.notEmpty(client.getName()) ? client.getName() : name;
					if ("RPC".equals(exchange.getDesc())) {
						channel = RabbitMqTool.receiveExchangeRpc(connection, exchangeType, exchangeName,
								client.getRoutingKeys(routingName), client.getClassName());
					} else {
						channel = RabbitMqTool.receiveExchange(messageExecutor, connection, exchangeType, exchangeName,
								client.getRoutingKeys(routingName), client.getClassName());
					}
					channels.add(channel);
				}
			}
		}
		if (ContainerTool.notEmpty(channels)) {
			chMap.put(connection, channels);
			destroyInfo.put(String.format(DESTROY_FORMAT, exchangeId, routingName), chMap);
		}
		return TypeEnum.TRUE.name();
	}

	/**
	 * 发送Rpc路由消息
	 *
	 * @param exchangeType 路由类型
	 * @param exchangeId   路由id
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 回调信息
	 */
	public String sendExchangeRpcById(BuiltinExchangeType exchangeType, String exchangeId, String routingKey,
									  String message) {
		return sendExchangeRpc(exchangeType, String.format(configEntity.getConfig().getExchangeFormat(), exchangeId),
				routingKey, message);
	}

	/**
	 * 根据路由id销毁通道
	 *
	 * @param exchangeId  路由id
	 * @param routingName 路由键名称
	 * @return 销毁结果
	 */
	public String destroyExchangeById(String exchangeId, String routingName) {
		if (ContainerTool.isEmpty(destroyInfo)) {
			return TypeEnum.TRUE.name();
		}
		String destroyKey = String.format(DESTROY_FORMAT, exchangeId, routingName);
		destroy(destroyInfo.get(destroyKey));
		return TypeEnum.TRUE.name();
	}

	/**
	 * 根据路由id销毁通道
	 *
	 * @param exchangeId 路由id
	 * @return 销毁结果
	 */
	public String destroyExchangeById(String exchangeId) {
		return destroyExchangeById(exchangeId, "");
	}

	// 基础方法

	//TODO 扩展方法
	// 扩展方法

	/**
	 * 发送广播型路由消息
	 *
	 * @param exchangeName 路由名称
	 * @param message      消息
	 * @return 发送结果
	 */

	public String sendExchangeFanout(String exchangeName, String message) {
		return sendExchange(BuiltinExchangeType.FANOUT, exchangeName, null, message);
	}

	/**
	 * 发送广播型路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeName 路由名称
	 * @param message      消息
	 * @return 发送结果
	 */
	public String sendExchangeFanout(Connection connection, String exchangeName, String message) {
		return sendExchange(connection, BuiltinExchangeType.FANOUT, exchangeName, null, message);
	}

	/**
	 * 接收广播型路由消息
	 *
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchangeFanout(String exchangeName) {
		return receiveExchange(exchangeName);
	}

	/**
	 * 接收队列消息
	 *
	 * @param connection   mq连接
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchangeFanout(Connection connection, String exchangeName) throws IOException {
		return receiveExchange(connection, BuiltinExchangeType.FANOUT, exchangeName);
	}

	/**
	 * 发送订阅型路由消息
	 *
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 发送结果
	 */
	public String sendExchangeDirect(String exchangeName, String routingKey, String message) {
		return sendExchange(BuiltinExchangeType.DIRECT, exchangeName, routingKey, message);
	}

	/**
	 * 发送订阅型路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 发送结果
	 */
	public String sendExchangeDirect(Connection connection, String exchangeName, String routingKey, String message) {
		return sendExchange(connection, BuiltinExchangeType.DIRECT, exchangeName, routingKey, message);
	}

	/**
	 * 接收订阅型路由消息
	 *
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchangeDirect(String exchangeName) {
		return receiveExchange(BuiltinExchangeType.DIRECT, exchangeName);
	}

	/**
	 * 接收订阅型路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchangeDirect(Connection connection, String exchangeName) throws IOException {
		return receiveExchange(connection, BuiltinExchangeType.DIRECT, exchangeName);
	}

	/**
	 * 发送主题型路由消息
	 *
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 发送结果
	 */
	public String sendExchangeTopic(String exchangeName, String routingKey, String message) {
		return sendExchange(BuiltinExchangeType.TOPIC, exchangeName, routingKey, message);
	}

	/**
	 * 发送主题型路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 发送结果
	 */
	public String sendExchangeTopic(Connection connection, String exchangeName, String routingKey, String message) {
		return sendExchange(connection, BuiltinExchangeType.TOPIC, exchangeName, routingKey, message);
	}

	/**
	 * 接收主题型路由消息
	 *
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchangeTopic(String exchangeName) {
		return receiveExchange(BuiltinExchangeType.TOPIC, exchangeName);
	}

	/**
	 * 接收主题型路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeName 路由名称
	 * @return 接收结果
	 */
	public String receiveExchangeTopic(Connection connection, String exchangeName) throws IOException {
		return receiveExchange(connection, BuiltinExchangeType.TOPIC, exchangeName);
	}

	/**
	 * 发送Rpc主题路由消息
	 *
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 回调信息
	 */
	public String sendExchangeRpcTopic(String exchangeName, String routingKey, String message) {
		return sendExchangeRpc(BuiltinExchangeType.TOPIC, exchangeName, routingKey, message);
	}

	/**
	 * 发送Rpc主题路由消息
	 *
	 * @param connection   mq连接
	 * @param exchangeName 路由名称
	 * @param routingKey   路由键
	 * @param message      消息
	 * @return 回调信息
	 */
	public String sendExchangeRpcTopic(Connection connection, String exchangeName, String routingKey, String message) {
		return sendExchangeRpc(connection, BuiltinExchangeType.TOPIC, exchangeName, routingKey, message);
	}

	// 扩展方法

}
