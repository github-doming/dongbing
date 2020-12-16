package org.doming.example.mq.rabbitmq.eg6.my.current;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-22 15:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RecvTest {

//	public static void main(String[] args) {
//		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
//		if (contextUtil == null) {
//			contextUtil = new ContextUtil();
//			contextUtil.init();
//			ContextThreadLocal.findThreadLocal().set(contextUtil);
//		}
//		try {
//			RabbitMqUtil.setConfigPathVal("/config/com/cloud/mq/rabbitmq.xml");
//			RabbitMqUtil util = RabbitMqUtil.findInstance();
//			util.setExecutor(ThreadExecuteUtil.findInstance().getMqExecutor());
//			String exchangeName = "exchange.topic.demo.simple";
//			for (int i = 0; i < 2; i++) {
//				System.out.println("开始接收路由消息" + util.receiveExchange(exchangeName + i));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			ContextThreadLocal.findThreadLocal().remove();
//			contextUtil.remove();
//		}
//	}
}
