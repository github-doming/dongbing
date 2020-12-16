package org.doming.example.mq.activemq.eg3;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.doming.example.mq.activemq.Config;

import javax.jms.*;
/**
 * @Description: Queue队列方式发送点对点消息数据 - 消息接收方
 * @Author: Dongming
 * @Date: 2018-11-05 16:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class QueueReceiverTest {

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

			//创建一个消息接受者
			QueueReceiver receiver = session.createReceiver(queue);

			receiver.setMessageListener((msg)->{
				if (msg != null){
					MapMessage message = (MapMessage) msg;
					try {
						System.out.println(message.getLong("time")+"接收#" +message.getString("text"));
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

			//休眠100s在关闭
			Thread.sleep(100*1000L);

			//提交会话
			session.commit();

		} catch (JMSException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 关闭释放资源
			if (session != null){
				session.close();
			}
			if (connection != null){
				connection.close();
			}
		}
	}
	public static void main(String[] args) throws JMSException {
		run();
	}
}
