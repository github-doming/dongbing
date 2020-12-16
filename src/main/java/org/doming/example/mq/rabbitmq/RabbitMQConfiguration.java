package org.doming.example.mq.rabbitmq;
/**
 * @Description: RabbitMq配置类
 * @Author: Dongming
 * @Date: 2018-11-07 13:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface RabbitMQConfiguration {

	/**
	 * 用于连接的默认主机
	 */
	String HOST_NAME = "119.27.179.52";

	/**
	 * 链接用户名
	 */
	String USER_NAME = "admin";

	/**
	 * 链接密码
	 */
	String PASS_WORD = "admin#";

	/**
	 * 定义队列名字
	 */
	String QUEUE_NAME = "hello";
	String QUEUE_NAME_1 = "hello-1";
	String QUEUE_NAME_2 = "hello-2";
	String QUEUE_NAME_RPC = "rpc_queue";

	/**
	 * 定义交换器名字
	 */
	String EXCHANGE_NAME = "logs";
	String EXCHANGE_NAME_DIRECT = "direct_logs";
	String EXCHANGE_NAME_TOPIC = "topic_logs";

}
