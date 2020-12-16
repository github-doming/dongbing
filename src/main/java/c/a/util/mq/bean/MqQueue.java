package c.a.util.mq.bean;
import java.util.ArrayList;
import java.util.List;
public class MqQueue {
	private String name = null;
	private List<MqClient> clientList = new ArrayList<MqClient>();
	private List<MqRouting> routingList = new ArrayList<MqRouting>();
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
	public List<MqRouting> getRoutingList() {
		return routingList;
	}
	public void setRoutingList(List<MqRouting> routingList) {
		this.routingList = routingList;
	}
	// -- 上面的方法不再更新 --//
}
