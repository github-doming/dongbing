package c.a.util.mq.bean;
import java.util.ArrayList;
import java.util.List;
public class MqExchange {
	private String name = null;
	private List<MqQueue> queueList = new ArrayList<MqQueue>();
	// -- 下面的方法不再更新 --//
	public List<MqQueue> getQueueList() {
		return queueList;
	}
	public void setQueueList(List<MqQueue> queueList) {
		this.queueList = queueList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	// -- 上面的方法不再更新 --//
}
