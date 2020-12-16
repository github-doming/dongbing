package org.doming.example.mq.activemq.eg4;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.doming.example.mq.activemq.Config;

import javax.jms.*;
/**
 * @Description: Topic主题发布和订阅消息 - 消息发送者
 * @Author: Dongming
 * @Date: 2018-11-05 16:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TopicSenderTest {

	/**
	 * 发送次数
	 */
	public static final int SEND_NUM = 5;

	public static void run() throws JMSException {
		TopicConnection connection = null;
		TopicSession session = null;
		try {
			//创建链接工厂（链接用户名，链接密码，链接地址）
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD, Config.BROKER_URL);
			//通过工厂创建一个链接
			connection = factory.createTopicConnection();

			//启动链接
			connection.start();

			//创建一个session会话
			session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

			//创建一个消息队列
			Topic topic = session.createTopic(Config.DESTINATION);

			//创建消息发送者
			TopicPublisher publisher = session.createPublisher(topic);

			//设置持久化模式
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(session, publisher);

			//提交会话
			session.commit();

		} finally {
			// 关闭释放资源
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}
	/**
	 * <b>function:</b> 发送消息
	 *
	 * @param session   会话
	 * @param publisher 发布者
	 * @throws JMSException
	 */
	private static void sendMessage(TopicSession session, TopicPublisher publisher) throws JMSException {
		for (int i = 0; i < SEND_NUM; i++) {
			String message = "发送消息第" + (i + 1) + "条";
			MapMessage map = session.createMapMessage();
			map.setString("text", message);
			map.setLong("time", System.currentTimeMillis());
			System.out.println(map);

			publisher.send(map);

		}
	}

	public static void main(String[] args) throws JMSException {
		run();
	}
}
