package org.doming.example.mq.activemq.eg3;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.doming.example.mq.activemq.Config;

import javax.jms.*;
/**
 * @Description: Queue队列方式发送点对点消息数据 - 消息发送者
 * @Author: Dongming
 * @Date: 2018-11-05 15:51
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class QueueSenderTest {

	/**
	 * 发送次数
	 */
	public static final int SEND_NUM = 5;

	public static void run() throws JMSException {
		QueueConnection connection = null;
		QueueSession session = null;
		try {
			//创建链接工厂（链接用户名，链接密码，链接地址）
			QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD, Config.BROKER_URL);

			//通过工厂创建一个链接
			connection = factory.createQueueConnection();

			//启动链接
			connection.start();

			//创建一个session会话
			session = connection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

			//创建一个消息队列
			Queue queue = session.createQueue(Config.DESTINATION);

			//创建一个消息发送者
			QueueSender sender = session.createSender(queue);

			//设置持久化模式
			sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			sendMessage(session,sender);

			//提交会话
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			// 关闭释放资源
			if (session!= null){
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}
	private static void sendMessage(QueueSession session, QueueSender sender) throws JMSException {
		for (int i = 0; i < SEND_NUM; i++) {
			String message = "发送消息第" + (i + 1) + "条";

			MapMessage mm = session.createMapMessage();
			mm.setString("text",message);
			mm.setLong("time",System.currentTimeMillis());
			System.out.println(message);
			sender.send(mm);
		}
	}

	public static void main(String[] args) throws JMSException {
		run();
	}

}
