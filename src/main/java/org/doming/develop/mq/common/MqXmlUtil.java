package org.doming.develop.mq.common;
import c.a.util.core.xml.XmlUtil;
import org.dom4j.Element;
import org.doming.develop.mq.bean.*;

import java.util.List;
/**
 * @Description: MQ配置文件工具类
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-14 17:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class MqXmlUtil extends XmlUtil {

	/**
	 * 获取消息队列配置实例
	 *
	 * @return 消息队列配置实例
	 */
	public MqConfigEntity findMQConfigEntity() {
		MqConfigEntity configEntity = new MqConfigEntity();
		//获取实体类信息
		List<Element> elementList = super.findRoot().elements();
		String elementName;
		for (Element element : elementList) {
			elementName = element.getName();
			switch (elementName) {
				case "config":
					MqConfig config = getMQConfig();
					configEntity.setConfig(config);
					break;
				case "queue":
					MqQueue queue = getMqQueue(element);
					configEntity.setQueue(queue);
					break;
				case "exchange":
					MqExchange exchange = getMqExchange(element);
					configEntity.setExchange(exchange);
					break;
				default:
					break;
			}

		}
		return configEntity;
	}

	/**
	 * 获取配置文件的MQ配置信息
	 *
	 * @return MQ配置信息
	 */
	public MqConfig getMQConfig() {

		MqConfig config = new MqConfig();

		//解析树节点
		List<Element> elementList = this.findRoot().element("config").elements();
		String elementName;
		for (Element childElement : elementList) {
			elementName = childElement.getName();
			switch (elementName) {
				case "ip":
					config.setIp(childElement.getTextTrim());
					break;
				case "port":
					config.setPort(childElement.getTextTrim());
					break;
				case "virtualHost":
					config.setVirtualHost(childElement.getTextTrim());
					break;
				case "userName":
					config.setUserName(childElement.getTextTrim());
					break;
				case "password":
					config.setPassword(childElement.getTextTrim());
					break;
				case "poolSize":
					config.setPoolSize(childElement.getTextTrim());
					break;
				case "exchangeFormat":
					config.setExchangeFormat(childElement.getTextTrim());
					break;
				case "queueFormat":
					config.setQueueFormat(childElement.getTextTrim());
					break;
				case "clientFormat":
					config.setClientFormat(childElement.getTextTrim());
					break;
				default:
					config.setDesc(childElement.getTextTrim());
					break;
			}
		}
		return config;
	}

	/**
	 * 获取路由配置信息
	 *
	 * @param element 路由节点
	 * @return 路由配置信息
	 */
	private MqExchange getMqExchange(Element element) {
		MqExchange exchange = new MqExchange();

		//解析属性节点
		exchange.setId(element.attributeValue("id"));
		exchange.setName(element.attributeValue("name"));
		exchange.setType(element.attributeValue("type"));
		exchange.setDesc(element.attributeValue("desc"));
		//解析树节点
		List<Element> elementList = element.elements();
		String elementName;
		for (Element childElement : elementList) {
			elementName = childElement.getName();
			switch (elementName) {
				case "queue":
					MqQueue queue = getMqQueue(element);
					exchange.setQueue(queue);
					break;
				case "client":
					MqReceiveClient client = getMqReceiveClient(childElement);
					exchange.setClient(client);
					break;
				default:
					break;
			}
		}
		return exchange;
	}

	/**
	 * 获取队列配置信息
	 *
	 * @param element 队列节点
	 * @return 队列配置信息
	 */
	private MqQueue getMqQueue(Element element) {
		MqQueue queue = new MqQueue();

		//解析属性节点
		queue.setId(element.attributeValue("id"));
		queue.setName(element.attributeValue("name"));
		queue.setDesc(element.attributeValue("desc"));
		queue.setQueueFormat(element.attributeValue("queueFormat"));

		//解析树节点
		List<Element> elementList = element.elements();
		for (Element childElement : elementList) {
			if ("client".equals(childElement.getName())) {
				MqReceiveClient client = getMqReceiveClient(childElement);
				queue.setClient(client);
			}
		}
		return queue;
	}

	/**
	 * 获取接收端节点设置信息
	 *
	 * @param element 收端节点
	 * @return 设置信息
	 */
	private MqReceiveClient getMqReceiveClient(Element element) {
		MqReceiveClient client = new MqReceiveClient();

		//解析属性节点
		client.setId(element.attributeValue("id"));
		client.setName(element.attributeValue("name"));
		client.setSize(element.attributeValue("size"));
		client.setClassName(element.attributeValue("class"));

		//解析树节点
		List<Element> elementList = element.elements();
		for (Element childElement : elementList) {
			if ("routing".equals(childElement.getName())) {
				MqRouting routing = getMqRouting(childElement);
				client.setRouting(routing);
			}
		}
		return client;
	}

	/**
	 * 获取线路配置信息
	 *
	 * @param element 线路配置节点
	 * @return 线路配置信息
	 */
	private MqRouting getMqRouting(Element element) {
		MqRouting routing = new MqRouting();
		routing.setId(element.attributeValue("id"));
		routing.setKey(element.attributeValue("key"));
		routing.setDesc(element.attributeValue("desc"));
		return routing;
	}

}
