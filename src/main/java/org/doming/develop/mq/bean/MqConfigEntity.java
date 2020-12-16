package org.doming.develop.mq.bean;
import java.util.ArrayList;
import java.util.List;
/**
 * @Description: MQ配置实体类
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-14 17:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class MqConfigEntity {

	/**
	 * 配置信息
	 */
	private MqConfig config;

	/**
	 * 队列列表
	 */
	private List<MqQueue> queueList;

	/**
	 * 路由列表
	 */
	private List<MqExchange> exchangeList;

	// -- 下面的方法不再更新 --//

	public MqConfig getConfig() {
		return config;
	}
	public void setConfig(MqConfig config) {
		this.config = config;
	}
	public List<MqQueue> getQueueList() {
		return queueList;
	}
	public void setQueueList(List<MqQueue> queueList) {
		this.queueList = queueList;
	}
	public List<MqExchange> getExchangeList() {
		return exchangeList;
	}
	public MqConfigEntity setExchangeList(List<MqExchange> exchangeList) {
		if (exchangeList != null) {
			this.exchangeList = exchangeList;
		}
		return this;
	}
	// -- 上面的方法不再更新 --//

	public void setQueue(MqQueue queue){
		if (getQueueList()== null){
			setQueueList(new ArrayList<>());
		}
		getQueueList().add(queue);
	}

	public void setExchange(MqExchange exchange) {
		if (getExchangeList()== null){
			setExchangeList(new ArrayList<>());
		}
		getExchangeList().add(exchange);
	}

	@Override public String toString() {
		return "MqConfigEntity{" + "config=" + config + ", queueList=" + queueList + ", exchangeList=" + exchangeList
				+ '}';
	}
}
