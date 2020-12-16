package com.ibs.plan.common.tools;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
/**
 * 智能投注 RabbitMQ工具类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 10:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RabbitMqTool {

	private static final Logger log = LogManager.getLogger(RabbitMqTool.class);

	/**
	 * 获取MQ工具类
	 *
	 * @return MQ工具类
	 */
	private static RabbitMqUtil findUtil() throws Exception {
		return RabbitMqUtil.findInstance();
	}

	public static void setMqXmlPath(String mqXmlPath) {
		RabbitMqUtil.setConfigPathVal(mqXmlPath);
	}

	public static Connection createConnection() throws Exception {
		return findUtil().createConnection();
	}
	//region MQ模块

	/**
	 * 注册 MQ模块路由
	 */
	public static void receive4Mq() throws Exception {
		findUtil().setExecutor(ThreadExecuteUtil.findInstance().getMqExecutor());
		String result = findUtil().receiveExchangeById("mq_request");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启请求监听服务失败");
		}
		result = findUtil().receiveExchangeById("mq_bet");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启投注监听服务失败");
		}
		result = findUtil().receiveExchangeById("mq_info");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启信息监听服务失败");
		}
	}
	/**
	 * 销毁  MQ模块路由
	 */
	public static void destroy4Mq() throws Exception {
		if (RabbitMqUtil.isShutdown()) {
			return;
		}
		String result = findUtil().destroyExchangeById("mq_request");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁请求监听服务失败");
		}
		result = findUtil().destroyExchangeById("mq_bet");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁投注监听服务失败");
		}
		result = findUtil().destroyExchangeById("mq_info");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁信息监听服务失败");
		}

	}

	/**
	 * 发送请求回馈
	 *
	 * @param message 发送消息
	 * @param type    接收类型
	 * @return 发送结果
	 */
	public static String sendReceipt(String message, String type) throws Exception {
		log.debug("发送请求回馈信息到MQ端，内容为：{}，处理类型为：{}", message, type);
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "mq_request", type.concat(".request"), message);
	}

	/**
	 * 发送请求回馈
	 *
	 * @param message 发送消息
	 * @param type    接收类型
	 * @return 发送结果
	 */
	public static String sendInfo(String message, String type) throws Exception {
		log.debug("发送客户端上的信息到MQ端，内容为：{}，处理类型为：{}", message, type);
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "mq_info", type.concat(".info"), message);
	}

	/**
	 * 发送投注信息
	 *
	 * @param message 发送消息
	 * @param type    接收类型
	 * @return 发送结果
	 */
	public static String sendBet(String message, String type) throws Exception {
		log.debug("发送客户端上的信息到MQ端，内容为：{}，处理类型为：{}", message, type);
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "mq_bet", type.concat(".bet"), message);
	}

	/**
	 * 发送配置消息回馈
	 *
	 * @param message 回馈信息
	 * @param type    消息类型
	 * @return 发送结果
	 */
	public static String sendConfigReceipt(String message, String type) throws Exception {
		log.debug("发送配置消息回执信息，内容为：".concat(message).concat("，类型为：".concat(type)));
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "cloud_config", type.concat(".config"), message);
	}

	//endregion

	//region 客户端模块

	//region 基础功能
	/**
	 * 注册 客户端模块路由
	 *
	 * @param clientCode 客户端编码
	 */
	public static void receive4Client(String clientCode) throws Exception {
		String result = findUtil().receiveExchangeById("client_member", clientCode);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启会员监听服务失败");
		}
		result = findUtil().receiveExchangeById("client_base", clientCode);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启基础信息监听服务失败");
		}
	}

	/**
	 * 销毁  客户端模块路由
	 *
	 * @param clientCode 客户端编码
	 */
	public static void destroy4Client(String clientCode) throws Exception {
		if (RabbitMqUtil.isShutdown()) {
			return;
		}
		String result = findUtil().destroyExchangeById("client_member", clientCode);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁会员监听服务失败");
		}
		result = findUtil().destroyExchangeById("client_info", clientCode);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁基础信息监听服务失败");
		}
	}
	/**
	 * 发送客户端设置消息
	 *
	 * @param message    回馈信息
	 * @param clientCode 客户端编码
	 * @param type       消息类型
	 * @return 发送结果
	 */
	public static String sendClientConfig(String message, String clientCode, String type) throws Exception {
		log.debug("发送客户端设置事件消息信息，内容为："
				.concat(String.format("%s,%s,%s", message, "客户端为：".concat(clientCode), "类型为：".concat(type))));
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "client_config",
				String.format("config.%s.%s", clientCode, type), message);
	}

	/**
	 * 发送客户端设置消息
	 *
	 * @param message    回馈信息
	 * @return 发送结果
	 */
	public static String sendClientConfig(String message) throws Exception {
		log.debug("发送客户端设置事件消息信息，内容为："+message);
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "client_config",
				"client.info.*", message);
	}
	//endregion

	/**
	 * 发送会员消息
	 *
	 * @param message 回馈信息
	 * @param type    消息类型
	 * @return 发送结果
	 */
	public static String sendMember(String message, String clientCode, String type) throws Exception {
		log.debug("发送会员管理信息到客户端端，内容为：{},客户端为：{},类型为：{}", message, clientCode, type);
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "client_member",
				String.format("%s.member.%s", type, clientCode), message);
	}

	/**
	 * 发送会员消息
	 *
	 * @param connection 连接信息
	 * @param message    回馈信息
	 * @param type       消息类型
	 * @return 发送结果
	 */
	public static String sendMember(Connection connection, String message, String clientCode, String type)
			throws Exception {
		log.debug("发送会员管理信息到客户端端，内容为：{},客户端为：{},类型为：{}", message, clientCode, type);
		return findUtil().sendExchangeById(connection, BuiltinExchangeType.TOPIC, "client_member",
				String.format("%s.member.%s", type, clientCode), message);
	}
	//endregion

}
