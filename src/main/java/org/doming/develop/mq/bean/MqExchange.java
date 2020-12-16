package org.doming.develop.mq.bean;
import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 消息队列队交换端
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-15 09:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class MqExchange {

	private String id;
	private String name;
	private String type;
	private List<MqReceiveClient> clientList;
	private List<MqQueue> queueList;
	private String desc;

	// -- 下面的方法不再更新 --//

	public String getId() {
		return id;
	}
	public MqExchange setId(String id) {
		if (id != null) {
			this.id = id;
		}
		return this;
	}
	public String getName() {
		return name;
	}
	public MqExchange setName(String name) {
		if (name != null) {
			this.name = name;
		}
		return this;
	}
	public String getType() {
		return type;
	}
	public MqExchange setType(String type) {
		if (type != null) {
			this.type = type;
		}
		return this;
	}
	public List<MqReceiveClient> getClientList() {
		return clientList;
	}
	public MqExchange setClientList(List<MqReceiveClient> clientList) {
		if (clientList != null) {
			this.clientList = clientList;
		}
		return this;
	}
	public String getDesc() {
		return desc;
	}
	public MqExchange setDesc(String desc) {
		if (desc != null) {
			this.desc = desc;
		}
		return this;
	}
	public List<MqQueue> getQueueList() {
		return queueList;
	}
	public MqExchange setQueueList(List<MqQueue> queueList) {
		if (queueList != null) {
			this.queueList = queueList;
		}
		return this;
	}

	// -- 上面的方法不再更新 --//

	public void setClient(MqReceiveClient client) {
		if(getClientList() == null){
			setClientList(new ArrayList<>());
		}
		getClientList().add(client);
	}
	public void setQueue(MqQueue queue) {
		if(getQueueList() == null){
			setQueueList(new ArrayList<>());
		}
		getQueueList().add(queue);
	}


}
