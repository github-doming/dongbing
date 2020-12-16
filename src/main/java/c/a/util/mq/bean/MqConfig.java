package c.a.util.mq.bean;
import java.util.ArrayList;
import java.util.List;
public class MqConfig {
	private String machine=null;
	//private String machineList =null;
	private String virtualHost = null;
	private String ip = null;
	private String port = null;
	private String userName = null;
	private String password = null;
	private String exchangeFormat= null;
	private String topicFormat= null;
	private String queueFormat= null;
	private String clientIdFormat= null;
	private List<MqExchange> exchangeList=new ArrayList<MqExchange>();
	private List<MqTopic> topicList = new ArrayList<MqTopic>();
	private List<MqQueue> queueList = new ArrayList<MqQueue>();
	// -- 下面的方法不再更新 --//
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
//	public String getMachineList() {
//		return machineList;
//	}
//	public void setMachineList(String machineList) {
//		this.machineList = machineList;
//	}
	public String getVirtualHost() {
		return virtualHost;
	}
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getExchangeFormat() {
		return exchangeFormat;
	}
	public void setExchangeFormat(String exchangeFormat) {
		this.exchangeFormat = exchangeFormat;
	}
	public String getTopicFormat() {
		return topicFormat;
	}
	public void setTopicFormat(String topicFormat) {
		this.topicFormat = topicFormat;
	}
	public String getQueueFormat() {
		return queueFormat;
	}
	public void setQueueFormat(String queueFormat) {
		this.queueFormat = queueFormat;
	}
	public String getClientIdFormat() {
		return clientIdFormat;
	}
	public void setClientIdFormat(String clientIdFormat) {
		this.clientIdFormat = clientIdFormat;
	}
	public List<MqExchange> getExchangeList() {
		return exchangeList;
	}
	public void setExchangeList(List<MqExchange> exchangeList) {
		this.exchangeList = exchangeList;
	}
	public List<MqTopic> getTopicList() {
		return topicList;
	}
	public void setTopicList(List<MqTopic> topicList) {
		this.topicList = topicList;
	}
	public List<MqQueue> getQueueList() {
		return queueList;
	}
	public void setQueueList(List<MqQueue> queueList) {
		this.queueList = queueList;
	}
	// -- 上面的方法不再更新 --//
}
