package org.doming.example.mq.rabbitmq.eg6.my.current;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-22 15:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RecvListener extends BaseCommMq {

	@Override
	public String execute(String message) throws Exception {
		System.out.println("消息内容为：" + message);
		Thread.sleep(1000);
		return TypeEnum.TRUE.name() + " : " + System.currentTimeMillis();
	}
}
