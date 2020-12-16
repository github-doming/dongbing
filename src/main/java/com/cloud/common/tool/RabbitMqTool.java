package com.cloud.common.tool;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.Logger;
import org.doming.develop.mq.bean.MqConfig;
import org.doming.develop.mq.bean.MqConfigEntity;
import org.doming.develop.mq.bean.MqExchange;
import org.doming.develop.mq.bean.MqReceiveClient;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
/**
 * MQ工具类
 *
 * @Author: Dongming
 * @Date: 2020-06-02 16:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RabbitMqTool {

	private static Connection connection;

	/**
	 * 获取MQ工具类
	 *
	 * @return MQ工具类
	 */
	private static RabbitMqUtil findUtil() throws Exception {
		return RabbitMqUtil.findInstance();
	}
	/**
	 * 销毁  MQ路由
	 */
	public static void destroy()  {
		if (connection == null) {
			return;
		}
		try {
			if (connection.isOpen()) {
				connection.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("销毁MQ路由失败");
		}
	}

	/**
	 * 设置MQ 路径
	 * @param mqXmlPath MQ XML路径
	 */
	public static void setMqXmlPath(String mqXmlPath) {
		RabbitMqUtil.setConfigPathVal(mqXmlPath);
	}

	/**
	 * 发送开奖信息
	 *
	 *
	 * @param log 日志
	 * @param message 发送消息
	 * @param type    接收类型
	 * @return 发送结果
	 */
	public static String sendLottery(Logger log, String message, String type) throws Exception {
		log.debug("发送开奖信息，内容为：{}，处理类型为：{}", message, type);
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "lottery", "cloud.lottery.".concat(type), message);
	}

	/**
	 * 注册路由服务
	 * @param configPath 配置路径
	 */
	public static void receive4Lottery(String configPath) throws Exception {
		MqConfigEntity configEntity = getMqConfigEntity(configPath);
		for (MqExchange exchange : configEntity.getExchangeList()) {
			if ("lottery".equals(exchange.getId())) {
				//路由类型
				BuiltinExchangeType exchangeType = org.doming.develop.mq.rabbitmq.RabbitMqTool.getExchangeType(exchange.getType());
				//路由名称
				String exchangeName = String.format(configEntity.getConfig().getExchangeFormat(), "lottery");

				for (MqReceiveClient client : exchange.getClientList()) {
					if ("RPC".equals(exchange.getDesc())) {
						org.doming.develop.mq.rabbitmq.RabbitMqTool
								.receiveExchangeRpc(connection, exchangeType, exchangeName, client.getRoutingKeys(null),
										client.getClassName());
					} else {
						org.doming.develop.mq.rabbitmq.RabbitMqTool
								.receiveExchange(connection, exchangeType, exchangeName, client.getRoutingKeys(null),
										client.getClassName());
					}
				}
			}
		}
	}

	/**
	 * 获取mq配置实例
	 *
	 * @param configPathPath 配置文件路径
	 * @return mq配置实例
	 */
	private static MqConfigEntity getMqConfigEntity(String configPathPath) throws Exception {
		MqConfigEntity configEntity = org.doming.develop.mq.rabbitmq.RabbitMqTool.getConfigEntityByPath(configPathPath);
		MqConfig config = configEntity.getConfig();
		// 定义连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 设置服务地址
		factory.setHost(config.getIp());
		// 端口
		factory.setPort(Integer.parseInt(config.getPort()));
		// 设置账号信息，用户名、密码、虚拟主机
		factory.setVirtualHost(config.getVirtualHost());
		factory.setUsername(config.getUserName());
		factory.setPassword(config.getPassword());

		connection = factory.newConnection();
		return configEntity;
	}

}
