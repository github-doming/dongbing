package c.a.util.mq.bean;
import java.util.ArrayList;
import java.util.List;
public class MqTopic {
	private String name = null;
	private List<MqClient> clientList = new ArrayList<MqClient>();
	// -- 下面的方法不再更新 --//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<MqClient> getClientList() {
		return clientList;
	}
	public void setClientList(List<MqClient> clientList) {
		this.clientList = clientList;
	}
	// -- 上面的方法不再更新 --//
}
