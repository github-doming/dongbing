package org.doming.develop.mq.bean;
/**
 * @Description: MQ配置类
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-14 18:00
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class MqConfig {

	private String ip;
	private String port;
	private String virtualHost;
	private String userName;
	private String password;
	private String poolSize;
	private String exchangeFormat;
	private String queueFormat;
	private String clientFormat;
	private String desc;


	// -- 下面的方法不再更新 --//

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
	public String getVirtualHost() {
		return virtualHost;
	}
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
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
	public String getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(String poolSize) {
		this.poolSize = poolSize;
	}
	public String getExchangeFormat() {
		return exchangeFormat;
	}
	public void setExchangeFormat(String exchangeFormat) {
		this.exchangeFormat = exchangeFormat;
	}
	public String getQueueFormat() {
		return queueFormat;
	}
	public void setQueueFormat(String queueFormat) {
		this.queueFormat = queueFormat;
	}
	public String getClientFormat() {
		return clientFormat;
	}
	public void setClientFormat(String clientFormat) {
		this.clientFormat = clientFormat;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	// -- 下面的方法不再更新 --//

	@Override public String toString() {
		return "MqConfig{" + "ip='" + ip + '\'' + ", port='" + port + '\'' + ", virtualHost='" + virtualHost + '\''
				+ ", userName='" + userName + '\'' + ", password='" + password + '\'' + ", poolSize='" + poolSize + '\''
				+ ", exchangeFormat='" + exchangeFormat + '\'' + ", queueFormat='" + queueFormat + '\''
				+ ", clientFormat='" + clientFormat + '\'' + ", desc='" + desc + '\'' + '}';
	}
}
