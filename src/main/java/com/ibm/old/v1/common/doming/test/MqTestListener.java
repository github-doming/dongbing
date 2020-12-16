package com.ibm.old.v1.common.doming.test;
import org.doming.core.common.mq.BaseCommMq;
/**
 * @Description: mq测试类监听
 * @Author: Dongming
 * @Date: 2019-03-07 17:51
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MqTestListener extends BaseCommMq {

	@Override public String execute(String message) throws Exception {
		System.out.println(message);
		return message;
	}

}
