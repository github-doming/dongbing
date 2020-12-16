package com.ibm.old.v1.common.doming.utils.mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.enums.TypeEnum;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: RabbitMQ智能投注工具具类
 * @Author: Dongming
 * @Date: 2019-01-28 11:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RabbitMqIbmUtil {

	protected static final Logger log = LogManager.getLogger(RabbitMqIbmUtil.class);

	private static volatile RabbitMqUtil util = null;

	/**
	 * 获取定时器工具类
	 *
	 * @return 定时器工具类
	 */
	private static RabbitMqUtil findRabbitMqUtil() {
		if (util == null) {
			synchronized (RabbitMqIbmUtil.class) {
				if (util == null) {
					try {
						RabbitMqUtil.setConfigPathKey("rabbitmq.ibm.xml");
						util = RabbitMqUtil.findInstance();
					} catch (Exception e) {
						log.error("初始化定时器工具类失败", e);
					}
				}
			}
		}
		return util;
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		RabbitMqUtil.destroy();
	}

	/**
	 * 获取mq链接
	 *
	 * @return mq链接
	 */
	public static Connection connection() throws IOException, TimeoutException {
		return findRabbitMqUtil().createConnection();
	}

	/**
	 * 开启客户机管理服务
	 *
	 * @param clientId 客户机id
	 */
	public static void receiveExchange4Manage(String clientId) {
		try {
			log.info("客户端《" + clientId + "》,开启客户机管理服务" + findRabbitMqUtil().receiveExchangeById("manage", clientId));
		} catch (Exception e) {
			log.error("初始化消息队列工具类失败", e);
		}
	}

	/**
	 * 开启盘口会员监听服务
	 *
	 * @param handicapMemberId 盘口会员id
	 */
	public static void receiveExchange4HandicapMember(String handicapMemberId) {
		try {
			log.info("盘口会员【" + handicapMemberId + "】,开启盘口会员用户信息监听服务" + findRabbitMqUtil()
					.receiveExchangeById("user", handicapMemberId));
			log.info("盘口会员【" + handicapMemberId + "】,开启盘口会员用户投注监听服务" + findRabbitMqUtil()
					.receiveExchangeById("bet", handicapMemberId));
			log.info("盘口会员【" + handicapMemberId + "】,开启盘口会员用户操作监听服务" + findRabbitMqUtil()
					.receiveExchangeById("method", handicapMemberId));
		} catch (Exception e) {
			log.error("盘口会员【" + handicapMemberId + "】,初始化消息队列工具类失败", e);
		}
	}

	/**
	 * 关闭盘口会员监听服务
	 *
	 * @param handicapMemberId 盘口会员id
	 */
	public static void destroyExchange4HandicapMember(String handicapMemberId) {
		try {
			log.info("盘口会员【" + handicapMemberId + "】,关闭盘口会员用户信息监听服务" + findRabbitMqUtil()
					.destroyExchangeById("user", handicapMemberId));
			log.info("盘口会员【" + handicapMemberId + "】,关闭盘口会员用户投注监听服务" + findRabbitMqUtil()
					.destroyExchangeById("bet", handicapMemberId));
			log.info("盘口会员【" + handicapMemberId + "】,关闭盘口会员用户操作监听服务" + findRabbitMqUtil()
					.destroyExchangeById("method", handicapMemberId));
		} catch (Exception e) {
			log.error("盘口会员【" + handicapMemberId + "】,关闭化消息队列工具类失败", e);
		}

	}

	/**
	 * 发送管理消息到客户端
	 *
	 * @param clientId 客户端id
	 * @param message  消息内容
	 */
	public static String sendExchange4Manage(String clientId, String message) {
		try {
			log.trace("客户端《" + clientId + "》,发送管理消息：{" + message + "}");
			String result = findRabbitMqUtil()
					.sendExchangeRpcById(BuiltinExchangeType.TOPIC, "manage", "service.manage." + clientId, message);
			log.trace("客户端《" + clientId + "》,接收管理消息：{" + result + "}");
			return result;
		} catch (Exception e) {
			log.error("向客户端{" + clientId + "},发送管理消息失败", e);
			return TypeEnum.FALSE.name();

		}
	}

	/**
	 * 发送用户的消息到客户端
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param code             用户行为
	 * @param message          消息内容
	 * @return 发送结果
	 */
	public static String sendExchange4User(String handicapMemberId, String code, String message) {
		try {
			log.trace("盘口会员【" + handicapMemberId + "】,发送<" + code + ">信息，内容为：{" + message + "}");
			return findRabbitMqUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "user",
					String.format("set.%s.%s", handicapMemberId, code), message);
		} catch (Exception e) {
			log.error("盘口会员【" + handicapMemberId + "】,发送用户的消息失败", e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 发送投注消息到客户端
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param message          消息内容
	 * @return 发送结果
	 */
	public static String sendExchange4Bet(String handicapMemberId, String message) {
		try {
			log.trace("盘口会员【" + handicapMemberId + "】发送投注信息，内容为：{" + message + "}");
			return findRabbitMqUtil()
					.sendExchangeById(BuiltinExchangeType.TOPIC, "bet", String.format("bet.%s", handicapMemberId),
							message);
		} catch (Exception e) {
			log.error("盘口会员【" + handicapMemberId + "】,发送投注消息失败", e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 发送投注消息到客户端
	 *
	 * @param connection       mq连接
	 * @param handicapMemberId 盘口会员id
	 * @param message          消息内容
	 * @return 发送结果
	 */
	public static String sendExchange4Bet(Connection connection, String handicapMemberId, String message) {
		try {
			log.trace("盘口会员【" + handicapMemberId + "】发送投注信息，内容为：{" + message + "}");
			return findRabbitMqUtil().sendExchangeById(connection, BuiltinExchangeType.TOPIC, "bet",
					String.format("bet.%s", handicapMemberId), message);
		} catch (Exception e) {
			log.error("盘口会员【" + handicapMemberId + "】,发送投注消息失败", e);
			return TypeEnum.FALSE.name();
		}
	}

	/**
	 * 发送操作消息到客户端
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param message          消息内容
	 * @return 发送结果
	 */
	public static String sendExchange4Method(String handicapMemberId, String message) {
		try {
			log.trace("盘口会员【" + handicapMemberId + "】发送操作信息，内容为：{" + message + "}");
			return findRabbitMqUtil()
					.sendExchangeById(BuiltinExchangeType.TOPIC, "method", String.format("method.%s", handicapMemberId),
							message);
		} catch (Exception e) {
			log.error("初始化消息队列工具类失败", e);
			return TypeEnum.FALSE.name();
		}
	}
}
