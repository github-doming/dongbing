package c.a.util.mq.activemq;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import c.a.util.mq.bean.*;
import c.a.util.mq.common.MqXmlUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.util.List;
/**
 * 
 * ActiveMq工具类(有连接池)
 * 
 * @Description:
 * @ClassName:
 * @date 2012年12月02日 上午11:21:51
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class ActiveMqPoolUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	public static String configPath = "/config/mq/activemq/activemq.xml";
	private static MqConfig config = null;
	private static PooledConnectionFactory poolFactory = null;
	// 单例
	private static ActiveMqPoolUtil instance = null;
	private final static Object key = new Object();
	// 构造函数
	private ActiveMqPoolUtil() {
	}
	public static ActiveMqPoolUtil findInstance() throws Exception {
		synchronized (key) {
			if (instance == null) {
				instance = new ActiveMqPoolUtil();
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
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		MqXmlUtil xml = new MqXmlUtil();
		xml.build(pathUtil.findPath(configPath));
		config = xml.findMqConfig();
		String urlInit = "tcp://" + config.getIp() + ":" + config.getPort();
		String url = "failover:(" + urlInit + ")?initialReconnectDelay=1000&timeout=3000&startupMaxReconnectAttempts=2";
		// 连接到ActiveMQ服务器
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		// Caused by: java.lang.SecurityException: User name [system] or
		// password is invalid.
		factory.setUserName(config.getUserName());
		factory.setPassword(config.getPassword());
		// factory.setBrokerURL("tcp://localhost:61616");
		factory.setBrokerURL(url);
		poolFactory = new PooledConnectionFactory(factory);
	}
	public PooledConnection createConnection() throws Exception {
		PooledConnection connection = (PooledConnection) poolFactory.createConnection();
	   connection.start();
		return connection;
	}
	public void closeConnection(PooledConnection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			log.error("系统异常:",e);
		}
	}
	/**
	 * 发布订阅模式(有持久);
	 * 
	 * 发布/订阅（publish/subscribe，简称pub/sub）Topic消息传递模型：
	 * 
	 * @Title: sendTopic
	 * @Description:
	 *
	 * 				参数说明
	 * @param topicCustomName
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendTopic(String topicCustomName, MqSendBean bean) throws Exception {
		this.sendTopic(topicCustomName, this.createConnection(), bean);
	}
	public void sendTopic(String topicCustomName, PooledConnection connection, MqSendBean bean) throws Exception {
		String topicFormat = config.getTopicFormat();
		Session session = null;
		try {
			// log.trace("connection="+connection);
			// 主题
			String topicName = String.format(topicFormat, topicCustomName);
			// log.trace("topicName="+topicName);
			// 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
			// 第二个参数为false时，
			// paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
			// Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
			// Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
			// DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建主题
			Topic topic = session.createTopic(topicName);
			MessageProducer producer = session.createProducer(topic);
			// NON_PERSISTENT 非持久化 PERSISTENT 持久化,发送消息时用使用持久模式
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage message = session.createTextMessage();
			message.setText(bean.getText());
			List<MqSendProperty> list = bean.getPropertyList();
			for (MqSendProperty activeMqProperty : list) {
				message.setStringProperty(activeMqProperty.getKey(), activeMqProperty.getValue());
			}
			// 发布主题消息
			producer.send(message);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}
		session.close();
		connection.close();
	}
	/**
	 * 不指定主题，所有主题都发;
	 * 
	 * 发布订阅模式(有持久);
	 * 
	 * 发布/订阅（publish/subscribe，简称pub/sub）Topic消息传递模型：
	 * 
	 * @Title: sendTopic
	 * @Description:
	 *
	 * 				参数说明
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendTopic(MqSendBean bean) throws Exception {
		this.sendTopic(this.createConnection(), bean);
	}
	public void sendTopic(PooledConnection connection, MqSendBean bean) throws Exception {
		String topicFormat = config.getTopicFormat();
		Session session = null;
		List<MqTopic> topicList = config.getTopicList();
		for (MqTopic mqTopic : topicList) {
			try {
				// log.trace("connection="+connection);
				String topicCustomName = mqTopic.getName();
				// 主题
				String topicName = String.format(topicFormat, topicCustomName);
				// log.trace("topicName="+topicName);
				// 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
				// 第二个参数为false时，
				// paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
				// Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
				// Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
				// DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
				session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
				// 创建主题
				Topic topic = session.createTopic(topicName);
				MessageProducer producer = session.createProducer(topic);
				// NON_PERSISTENT 非持久化 PERSISTENT 持久化,发送消息时用使用持久模式
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
				TextMessage message = session.createTextMessage();
				message.setText(bean.getText());
				List<MqSendProperty> list = bean.getPropertyList();
				for (MqSendProperty activeMqProperty : list) {
					message.setStringProperty(activeMqProperty.getKey(), activeMqProperty.getValue());
				}
				// 发布主题消息
				producer.send(message);
				session.commit();
			} catch (Exception e) {
				log.error("系统异常:",e);
				if(session!=null){
					session.rollback();
				}
			}
		}
		if(session!=null){
			session.close();
		}
		connection.close();
	}
	
	/**
	 * 发布；
	 * 
	 * 点对点（point-to-point，简称PTP）Queue消息传递模型；
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param queueCustomName
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendQueue(String queueCustomName, MqSendBean bean) throws Exception {
		this.sendQueue(queueCustomName, this.createConnection(), bean);
	}
	public void sendQueue(String queueCustomName, PooledConnection connection, MqSendBean bean) throws Exception {
		String queueFormat = config.getQueueFormat();
		Session session = null;
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination;
		// MessageProducer：消息发送者
		MessageProducer producer;
		try {
			// log.trace("connection="+connection);
			// 主题
			String queueName = String.format(queueFormat, queueCustomName);
			// 获取操作连接
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queueName);
			// 得到消息生成者【发送者】
			producer = session.createProducer(destination);
			// 设置不持久化，此处学习，实际根据项目决定
			// producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// 设置持久化
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			// 构造消息
			TextMessage message = session.createTextMessage(bean.getText());
			List<MqSendProperty> list = bean.getPropertyList();
			for (MqSendProperty activeMqProperty : list) {
				message.setStringProperty(activeMqProperty.getKey(), activeMqProperty.getValue());
			}
			// 发送消息到目的地方
			producer.send(message);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}
		session.close();
		connection.close();
	}
	/**
	 * 接收;
	 * 
	 * 点对点（point-to-point，简称PTP）Queue消息传递模型;
	 * 
	 * @Title: receiveQueue
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveQueue() throws Exception {
		this.receiveQueue(this.createConnection());
	}
	public void receiveQueue(PooledConnection connection) throws Exception {
		String queueFormat = config.getQueueFormat();
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination = null;
		// 消费者，消息接收者
		MessageConsumer consumer = null;
		List<MqQueue> queueList = config.getQueueList();
		for (MqQueue mqQueue : queueList) {
			String queueName = String.format(queueFormat, mqQueue.getName());
			Session session = null;
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queueName);
		
			List<MqClient> clientList = mqQueue.getClientList();
			for (MqClient client : clientList) {
				consumer = session.createConsumer(destination);
				Class<?> classObject = Class.forName(client.getClassName());
				MessageListener messageListener = (MessageListener) classObject.newInstance();
				consumer.setMessageListener(messageListener);
				// while (true) {
				// // 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				// // TextMessage message = (TextMessage)
				// consumer.receive(100000);
				// TextMessage message = (TextMessage) consumer.receive(1000);
				// if (null != message) {
				// log.trace("收到消息=" + message.getText());
				// } else {
				// break;
				// }
				// }
			}

		}
	}
}