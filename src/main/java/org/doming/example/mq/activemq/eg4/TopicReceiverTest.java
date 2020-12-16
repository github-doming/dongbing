package org.doming.example.mq.activemq.eg4;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.doming.example.mq.activemq.Config;

import javax.jms.*;
/**
 * @Description: Topic主题发布和订阅消息 - 消息接收者
 * @Author: Dongming
 * @Date: 2018-11-05 16:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TopicReceiverTest {
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

			//创建消息接收者
			TopicSubscriber subscriber = session.createSubscriber(topic);

			subscriber.setMessageListener((msg)->{
				if (msg != null) {
					MapMessage map = (MapMessage) msg;
					try {
						System.out.println(map.getLong("time") + "接收#" + map.getString("text"));
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

			//休眠100s在关闭
			Thread.sleep(100*1000L);

			//提交会话
			session.commit();
		} catch (InterruptedException e) {
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

	public static void main(String[] args) throws JMSException {
		run();
	}
}
