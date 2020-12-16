package org.doming.example.mq.activemq;
/**
 * @Description: 配置类
 * @Author: Dongming
 * @Date: 2018-11-05 15:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface Config {
	/**
	 * MQ tcp地址
	 */
	String BROKER_URL = "tcp://139.159.142.104:61616";

	/**
	 * 目标
	 */
	String DESTINATION = "doming.mq.queue";
}
