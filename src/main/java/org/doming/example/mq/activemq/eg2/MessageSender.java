package org.doming.example.mq.activemq.eg2;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.doming.example.mq.activemq.Config;

import javax.jms.*;

/**
 * @Description: 使用JMS方式发送接收消息 - 消息发送者
 * @Author: Dongming
 * @Date: 2018-11-05 15:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MessageSender {

	/**
	 * 发送次数
	 */
	public static final int SEND_NUM = 5;

	public static void run() throws JMSException {
		Connection connection = null;
		Session session = null;
		try {
			//创建链接工厂（链接用户名，链接密码，链接地址）
			ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD, Config.BROKER_URL);

			//通过工厂创建一个链接
			connection = factory.createConnection();

			//启动链接
			connection.start();

			//创建一个session会话
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

			//创建一个消息队列
			Destination destination = session.createQueue(Config.DESTINATION);

			//创建消息制作者
			MessageProducer producer = session.createProducer(destination);

			//设置持久化模式
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(session, producer);

			//提交会话
			session.commit();

		} catch (JMSException e) {
			e.printStackTrace();
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
	 * @param session
	 * @param producer
	 */
	private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
		for (int i = 0; i < SEND_NUM; i++) {
			String message = "发送消息第" + (i + 1) + "条";
			TextMessage text = session.createTextMessage(message);

			System.out.println(message);
			producer.send(text);
		}
	}

	public static void main(String[] args) throws JMSException {
		MessageSender.run();
	}

}
