package org.doming.example.mq.activemq.eg1;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
/**
 * @Description: 发送端测试类
 * @Author: Dongming
 * @Date: 2018-11-05 14:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MQProducerQueueTest {

	@Test public void testMQProducerQueue() throws JMSException {

		//1、创建工厂连接对象，需要制定ip和端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://139.159.142.104:61616");


		//2、使用连接工厂创建一个连接对象
		Connection connection = connectionFactory.createConnection();

		//3、开启连接
		connection.start();

		//4、使用连接对象创建会话（session）对象
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

		//5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
		Queue queue = session.createQueue("test-queue");

		//6、使用会话对象创建生产者对象
		MessageProducer producer = session.createProducer(queue);

		//7、使用会话对象创建一个消息对象
		TextMessage textMessage = session.createTextMessage("hello!test-queue");

		//8、发送消息
		producer.send(textMessage);

		producer.close();
		session.close();
		connection.close();

	}
}
