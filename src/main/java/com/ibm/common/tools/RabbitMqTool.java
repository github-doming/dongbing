package com.ibm.common.tools;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
/**
 * @Description: 智能跟单 RabbitMQ工具类
 * @Author: Dongming
 * @Date: 2019-08-22 11:14
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

	//region 客户端模块
	//region 基础功能
	/**
	 * 注册客户端路由
	 *
	 * @param clientId 客户端id
	 */
	public static void receive4Client(String clientId) throws Exception {
		String result = findUtil().receiveExchangeById("client_member", clientId);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("客户端,开启会员监听服务失败");
		}
		result = findUtil().receiveExchangeById("client_agent", clientId);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("客户端,开启代理监听服务失败");
		}
		result = findUtil().receiveExchangeById("client_config", clientId);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("客户端,开启配置监听服务失败");
		}
	}

	public static void destroy4Client(String clientId) throws Exception {
		if (RabbitMqUtil.isShutdown()) {
			return;
		}
		String result = findUtil().destroyExchangeById("client_member", clientId);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("客户端,销毁会员监听服务失败");
		}
		result = findUtil().destroyExchangeById("client_agent", clientId);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("客户端,销毁代理监听服务失败");
		}
		result = findUtil().destroyExchangeById("client_config", clientId);
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("客户端,销毁配置监听服务失败");
		}
	}
	//endregion

	//region 发送功能
	/**
	 * 发送消息回馈
	 *
	 * @param message 回馈信息
	 * @param type    消息类型
	 * @return 发送结果
	 */
	public static String sendMemberReceipt(String message, String type) throws Exception {
		log.debug("发送会员消息回执信息，内容为：".concat(message).concat("，类型为：".concat(type)));
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "cloud_member", type.concat(".member"), message);
	}

	/**
	 * 发送消息回馈
	 *
	 * @param message 回馈信息
	 * @param type    消息类型
	 * @return 发送结果
	 */
	public static String sendAgentReceipt(String message, String type) throws Exception {
		log.debug("发送代理消息回执信息，内容为：".concat(message).concat("，类型为：".concat(type)));
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "cloud_agent", type.concat(".agent"), message);
	}

	/**
	 * 发送消息回馈
	 *
	 * @param message 回馈信息
	 * @param type    消息类型
	 * @return 发送结果
	 */
	public static String sendInfoReceipt(String message, String type) throws Exception {
		log.debug("发送客户端消息回执信息，内容为：".concat(message).concat("，类型为：".concat(type)));
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "cloud_info", type.concat(".info"), message);
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
	//endregion

	//region MQ模块
	//region 基础
	/**
	 * 注册中心端路由
	 */
	public static void receive4Mq() throws Exception {
		findUtil().setExecutor(ThreadExecuteUtil.findInstance().getMqExecutor());
		String result = findUtil().receiveExchangeById("cloud_member");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启会员监听服务失败");
		}
		result = findUtil().receiveExchangeById("cloud_agent");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启代理监听服务失败");
		}
		result = findUtil().receiveExchangeById("cloud_info");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启信息监听服务失败");
		}
		result = findUtil().receiveExchangeById("cloud_config");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("开启配置监听服务失败");
		}
		log.debug("注册监听服务成功");
	}

	/**
	 * 销毁消息队列路由
	 */
	public static void destroy4Mq() throws Exception {
		if (RabbitMqUtil.isShutdown()) {
			return;
		}
		String result = findUtil().destroyExchangeById("cloud_member");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁会员监听服务失败");
		}
		result = findUtil().destroyExchangeById("cloud_agent");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁代理监听服务失败");
		}
		result = findUtil().destroyExchangeById("cloud_info");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁信息监听服务失败");
		}
		result = findUtil().destroyExchangeById("cloud_config");
		if (!Boolean.parseBoolean(result)) {
			throw new RuntimeException("销毁配置监听服务失败");
		}
		log.debug("销毁监听服务成功");
	}
	//endregion

	//region 发送
	/**
	 * 发送会员消息
	 *
	 * @param message 回馈信息
	 * @param type    消息类型
	 * @return 发送结果
	 */
	public static String sendMemberInfo(String message, String clientCode, String type) throws Exception {
		log.debug("发送会员事件消息信息，内容为："
				.concat(String.format("%s,%s,%s", message, "客户端为：".concat(clientCode), "类型为：".concat(type))));
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "client_member",
				String.format("member.%s.%s", clientCode, type), message);
	}
	/**
	 * 发送会员消息
	 *
	 * @param message 回馈信息
	 * @return 发送结果
	 */
	public static String sendMemberBetInfo(String message, String clientCode) throws Exception {
		log.debug("发送会员事件消息信息，内容为：".concat(String.format("%s,%s,%s", message, "客户端为：".concat(clientCode), "类型为：投注")));
		return findUtil().sendExchange(BuiltinExchangeType.TOPIC, "ibm_exchange_bet_info",
				String.format("bet.info.%s", clientCode), message);
	}
	/**
	 * 发送虚拟会员投注消息
	 *
	 * @param message 回馈信息
	 * @return 发送结果
	 */
	public static void sendVrMemberBetInfo(String message, String clientCode) throws Exception {
		log.debug("发送会员事件消息信息，内容为：".concat(String.format("%s,%s,%s", message, "客户端为：".concat(clientCode), "类型为：投注")));
		findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "client_member",
				String.format("bet.vr.info.%s", clientCode), message);
	}
	/**
	 * 发送代理消息
	 *
	 * @param message 回馈信息
	 * @return 发送结果
	 */
	public static String sendAgentBetInfo(String message) throws Exception {
		log.debug("发送代理事件消息信息，内容为：".concat(String.format("%s,%s", message, "类型为：投注")));
		return findUtil().sendExchange(BuiltinExchangeType.TOPIC, "ibm_exchange_bet_info", "bet.info.agent", message);
	}
	/**
	 * 发送代理消息
	 *
	 * @param message 回馈信息
	 * @param type    消息类型
	 * @return 发送结果
	 */
	public static String sendAgentInfo(String message, String clientCode, String type) throws Exception {
		log.debug("发送代理事件消息信息，内容为："
				.concat(String.format("%s,%s,%s", message, "客户端为：".concat(clientCode), "类型为：".concat(type))));
		return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "client_agent",
				String.format("agent.%s.%s", clientCode, type), message);
	}

	/**
	 * 发送虚拟会员投注信息
	 * @param message		消息内容
	 */
	public static void sendVrMemberBetInfo(String message) throws Exception {
		log.debug("发送虚拟会员事件消息信息，内容为：".concat(String.format("%s,%s", message, "类型为：投注")));
		findUtil().sendExchange(BuiltinExchangeType.TOPIC, "ibm_exchange_bet_info", "bet.info.vr", message);
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
     * @param type       消息类型
     * @return 发送结果
     */
    public static String sendClientConfig(String message,  String type) throws Exception {
        log.debug("发送客户端设置事件消息信息，内容为："
                .concat(String.format("%s,%s", message,  "类型为：".concat(type))));
        return findUtil().sendExchangeById(BuiltinExchangeType.TOPIC, "client_config",
                String.format("config.*.%s", type), message);
    }
	//endregion
	//endregion

}
