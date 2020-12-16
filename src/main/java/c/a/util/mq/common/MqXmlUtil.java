package c.a.util.mq.common;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import c.a.util.core.xml.XmlUtil;
import c.a.util.mq.bean.MqClient;
import c.a.util.mq.bean.MqConfig;
import c.a.util.mq.bean.MqExchange;
import c.a.util.mq.bean.MqQueue;
import c.a.util.mq.bean.MqRouting;
import c.a.util.mq.bean.MqTopic;
public class MqXmlUtil extends XmlUtil {
	protected Logger log = LogManager.getLogger(MqXmlUtil.class);
	/**
	 * 
	 * @param url
	 * @return
	 * @throws DocumentException
	 */
	public MqConfig findMqConfig() throws DocumentException {
		MqConfig config = new MqConfig();
		List<Element> elementList = this.findRoot().elements();
		for (Element element : elementList) {
			String elementName = element.getName();
			if ("config".equals(elementName)) {
				// 子元素
				List<Element> childElementList = element.elements();
				for (Element childElement : childElementList) {
					String childElementName = childElement.getName();
					if ("virtualHost".equals(childElementName)) {
						config.setVirtualHost(childElement.getTextTrim());
					}
					if ("ip".equals(childElementName)) {
						config.setIp(childElement.getTextTrim());
					}
					if ("port".equals(childElementName)) {
						config.setPort(childElement.getTextTrim());
					}
					if ("userName".equals(childElementName)) {
						config.setUserName(childElement.getTextTrim());
					}
					if ("password".equals(childElementName)) {
						config.setPassword(childElement.getTextTrim());
					}
					if ("exchangeFormat".equals(childElementName)) {
						config.setExchangeFormat(childElement.getTextTrim());
					}
					if ("topicFormat".equals(childElementName)) {
						config.setTopicFormat(childElement.getTextTrim());
					}
					if ("queueFormat".equals(childElementName)) {
						config.setQueueFormat(childElement.getTextTrim());
					}
					if ("clientIdFormat".equals(childElementName)) {
						config.setClientIdFormat(childElement.getTextTrim());
					}
//					if ("machineList".equals(childElementName)) {
//						config.setMachineList(childElement.getTextTrim());
//					}
				}
			}
			if ("topic".equals(elementName)) {
				MqTopic mqTopic = new MqTopic();
				mqTopic.setName(element.attributeValue("name"));
				// 子元素
				List<Element> childElementList = element.elements();
				for (Element childElement : childElementList) {
					String childElementName = childElement.getName();
					if ("client".equals(childElementName)) {
						MqClient mqClient = new MqClient();
						mqClient.setId(childElement.attributeValue("class"));
						mqClient.setClassName(childElement.attributeValue("class"));
						mqTopic.getClientList().add(mqClient);
					}
				}
				config.getTopicList().add(mqTopic);
			}
			if ("queue".equals(elementName)) {
				MqQueue mqQueue = new MqQueue();
				mqQueue.setName(element.attributeValue("name"));
				// 子元素
				List<Element> childElementList = element.elements();
				for (Element childElement : childElementList) {
					String childElementName = childElement.getName();
					if ("client".equals(childElementName)) {
						MqClient mqClient = new MqClient();
						mqClient.setId(childElement.attributeValue("class"));
						mqClient.setClassName(childElement.attributeValue("class"));
						mqQueue.getClientList().add(mqClient);
					}
				}
				config.getQueueList().add(mqQueue);
			}
			if ("exchange".equals(elementName)) {
				MqExchange mqExchange = new MqExchange();
				mqExchange.setName(element.attributeValue("name"));
				// 子元素
				List<Element> queueElementList = element.elements();
				for (Element queueElement : queueElementList) {
					String queueElementName = queueElement.getName();
					if ("queue".equals(queueElementName)) {
						MqQueue mqQueue = new MqQueue();
						mqQueue.setName(queueElement.attributeValue("name"));
						// 子元素
						List<Element> childElementList = queueElement.elements();
						for (Element childElement : childElementList) {
							String childElementName = childElement.getName();
							if ("client".equals(childElementName)) {
								MqClient mqClient = new MqClient();
								mqClient.setId(childElement.attributeValue("class"));
								mqClient.setClassName(childElement.attributeValue("class"));
								mqQueue.getClientList().add(mqClient);
							}
							if ("routing".equals(childElementName)) {
								MqRouting mqRouting = new MqRouting	();
								mqRouting.setKey(childElement.attributeValue("key"));
								mqQueue.getRoutingList().add(mqRouting);
							}
						}
						mqExchange.getQueueList().add(mqQueue);
					}
				}
				config.getExchangeList().add(mqExchange);
			}
		}
		return config;
	}
}
