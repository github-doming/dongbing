package com.ibm.old.v1.common.doming.test;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.test.CommTest;
import com.rabbitmq.client.BuiltinExchangeType;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
import org.junit.Test;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-13 16:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MqTest extends CommTest {

	public static void main(String[] args) {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		try {
			RabbitMqUtil.setConfigPathKey("rabbitmq.cloud.xml");
			RabbitMqUtil util = RabbitMqUtil.findInstance();
			System.out.println("开始接收云服务开奖信息" + util.receiveExchangeById("test", "1"));
//			System.out.println("开始接收云服务开奖信息" + util.receiveExchangeById("test", "2"));
//			System.out.println("开始接收云服务开奖信息" + util.receiveExchangeById("test", "3"));
//			System.out.println("开始接收云服务开奖信息" + util.receiveExchangeById("test", "4"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test public void test() {

		try {
			RabbitMqUtil.setConfigPathKey("rabbitmq.cloud.xml");
			RabbitMqUtil util = RabbitMqUtil.findInstance();
			for (int i = 0; i < 40; i++) {
				util.sendExchangeById(BuiltinExchangeType.TOPIC, "test", "test." + (i % 4 + 1), "测试发送" + i);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
