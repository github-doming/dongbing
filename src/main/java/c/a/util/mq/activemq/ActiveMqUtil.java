package c.a.util.mq.activemq;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import c.a.util.mq.bean.MqClient;
import c.a.util.mq.bean.MqConfig;
import c.a.util.mq.bean.MqTopic;
import c.a.util.mq.common.MqXmlUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.List;
/**
 * 
 * ActiveMq工具类
 * 
 * @Description:
 * @ClassName: 
 * @date 2012年12月02日 上午11:21:51
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class ActiveMqUtil{
	protected Logger log = LogManager.getLogger(this.getClass());
	public static String configPath = "/config/mq/activemq/activemq.xml";
	private static Connection connection = null;
	private static MqConfig config = null;
	private static ActiveMQConnectionFactory factory = null;
	// 单例
	private static ActiveMqUtil instance = null;
	private final static Object key = new Object();
	// 构造函数
	private ActiveMqUtil() {
	}
	public static ActiveMqUtil findInstance() throws Exception {
		synchronized (key) {
			if (instance == null) {
				instance = new ActiveMqUtil();
				// 初始化
				init();
			}
		}
		return instance;
	}
	/**
	 * 初始化
	 * 
	 * @Title: init
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	private static void init() throws Exception {
		PathUtil pathUtil =PathThreadLocal.findThreadLocal().get();
		MqXmlUtil xml = new MqXmlUtil();
		xml.build(pathUtil.findPath(configPath));
		config = xml.findMqConfig();
		String urlInit = "tcp://" + config.getIp() + ":" + config.getPort();
		String url = "failover:(" + urlInit + ")?initialReconnectDelay=1000&timeout=3000&startupMaxReconnectAttempts=2";
		// 连接到ActiveMQ服务器
		factory = new ActiveMQConnectionFactory();
		// Caused by: java.lang.SecurityException: User name [system] or
		// password is invalid.
		factory.setUserName(config.getUserName());
		factory.setPassword(config.getPassword());
		// factory.setBrokerURL("tcp://localhost:61616");
		factory.setBrokerURL(url);
	}
	public Connection createConnection() throws Exception {
		connection = factory.createConnection();
		return connection;
	}
	public void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			log.error("系统异常:",e);
		}
	}
	/**
	 * 接收订阅模式(有持久);
	 * 
	 * 发布/订阅（publish/subscribe，简称pub/sub）Topic消息传递模型：
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveTopic() throws Exception {
		String clientIdFormat = config.getClientIdFormat();
		String topicFormat = config.getTopicFormat();
		List<MqTopic> topicList = config.getTopicList();
		for (MqTopic activeMqTopic : topicList) {
			String topicName = String.format(topicFormat, activeMqTopic.getName());
			List<MqClient> clinetList = activeMqTopic.getClientList();
			for (MqClient mqTopicClient : clinetList) {
				Connection connection = null;
				Session session = null;
				try {
					String clientIdCustom = mqTopicClient.getId();
					// 客户端id
					String clientId = String.format(clientIdFormat, clientIdCustom);
					log.trace("clientId=" + clientId);
					// 客户端ID,持久订阅需要设置
					connection = this.createConnection();
					connection.setClientID(clientId);
					session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
					// 创建主题
					Topic topic = session.createTopic(topicName);
					// 创建持久订阅,指定客户端ID。
					MessageConsumer consumer = session.createDurableSubscriber(topic, clientId);
					Class<?> classObject = Class.forName(mqTopicClient.getClassName());
					MessageListener messageListener = (MessageListener) classObject.newInstance();
					consumer.setMessageListener(messageListener);
					log.trace("connection=" + connection);
					connection.start();
					
				} catch (Exception e) {
					e.printStackTrace();
					try {
						session.rollback();
					} catch (JMSException e1) {
						e1.printStackTrace();
					}
				} finally {
//					 try {
//					 session.close();
//					 } catch (JMSException e) {
//					 e.printStackTrace();
//					 }
				}
				
			}
		}
	}
	
	
}