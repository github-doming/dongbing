package org.doming.core.common.mq;

import org.doming.core.common.BaseTransaction;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
/**
 * @Description: mq事物类
 * @Author: null
 * @Date: 2018-12-03 11:30
 * @Version: v1.0
 */
public abstract class BaseTransactionMq extends BaseTransaction implements MessageListener{

	/**
	 * 接收消息
	 *
	 * @param message 消息内容
	 */
	@Override public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			run(textMessage.getText());
		} catch (Exception e) {
			log.error("MQ系统异常:",e);
		}

	}
}
