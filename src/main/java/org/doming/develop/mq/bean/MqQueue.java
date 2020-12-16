package org.doming.develop.mq.bean;
import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 消息队列队列端
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-14 18:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class MqQueue {

	private String id;
	private String name;
	private String queueFormat;
	private List<MqReceiveClient> clientList;
	private String desc;


	// -- 下面的方法不再更新 --//

	public String getId() {
		return id;
	}
	public void setId(String id) {
		if (id != null) {
			this.id = id;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}
	public List<MqReceiveClient> getClientList() {
		return clientList;
	}
	public void setClientList(List<MqReceiveClient> clientList) {
		this.clientList = clientList;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		if (desc != null) {
			this.desc = desc;
		}
	}

	public void setClient(MqReceiveClient client) {
		if(getClientList() == null){
			setClientList(new ArrayList<>());
		}
		getClientList().add(client);
	}


	public String getQueueFormat() {
		return queueFormat;
	}
	public MqQueue setQueueFormat(String queueFormat) {
		if (queueFormat != null) {
			this.queueFormat = queueFormat;
		}
		return this;
	}
	// -- 下面的方法不再更新 --//

}
