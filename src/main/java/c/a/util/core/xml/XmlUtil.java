package c.a.util.core.xml;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import c.a.util.core.file.FileUtil;
/**
 * 
 * @author cxy
 * @Email:  使用范围：
 * 
 */
public class XmlUtil {
	private Document document = null;
	private Element elementRoot = null;
	private InputStream inputStream = null;
	/**
	 * 将xml字符串转换成map
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */

	public Map xml2map(String xml) {
		Map map = new HashMap();
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
		// log.trace(document.asXML());
		Element elementRoot = document.getRootElement();// 声明根元素
		List<Element> listElement_action = elementRoot.elements();
		for (Element element_action : listElement_action) {
			// map.put(element_action.getName(),element_action.getData());
			map.put(element_action.getName(), element_action.getTextTrim());
		}
		return map;
	}
	/**
	 * 创建微信文本消息
	 * 
	 * @param content
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String map2xml(String content, Map map) {
		// <xml>
		// <ToUserName><![CDATA[toUser]]></ToUserName>
		// <FromUserName><![CDATA[fromUser]]></FromUserName>
		// <CreateTime>12345678</CreateTime>
		// <MsgType><![CDATA[text]]></MsgType>
		// <Content><![CDATA[你好]]></Content>
		// </xml>
		Document doc = DocumentHelper.createDocument();
		Element element = doc.addElement("xml");
		addMyElement(element, "ToUserName", (String) map.get("ToUserName"));
		addMyElement(element, "FromUserName", (String) map.get("FromUserName"));
		addMyElement(element, "CreteTime", "" + System.currentTimeMillis());
		addMyElement(element, "MagType", "text");
		addMyElement(element, "Content", content);
		return doc.asXML();
	}
	private Element addMyElement(Element element, String string, String string2) {
		Element elementItem = element.addElement(string);
		elementItem.addCDATA(string2);
		return elementItem;
	}
	/**
	 * 根据节点的其中一个属性为name的值和另一个属性名取另一个属性的值
	 * 
	 * @param filePath
	 * @param name
	 *            节点的其中一个属性为name的值
	 * @param attributeName
	 *            另一个属性名
	 * @return 另一个属性的值
	 * @throws DocumentException
	 */
	public String findAttributeValue(String filePath, String name,
			String attributeName) throws DocumentException, Exception {
		this.build(filePath);
		return this.findAttributeValue(this.findRoot(), "name", name,
				attributeName);
	}
	/**
	 * 
	 * 根据节点的其中一个属性名(orig)和另一个属性名(dest)取另一个属性的值
	 * 
	 * @param elementRoot
	 * 
	 * @param oridAttributeName
	 *            节点的其中一个属性名(orig)
	 * @param oridAttributeValue
	 *            节点的其中一个属性名(orig)的值
	 * @param destAttributeName
	 *            另一个属性名(dest)
	 * @return 取另一个属性的值
	 * @throws DocumentException
	 */

	public String findAttributeValue(Element elementRoot,
			String oridAttributeName, String oridAttributeValue,
			String destAttributeName) throws DocumentException {
		List<Element> list = elementRoot.elements();
		for (Element e : list) {
			String attributeValue = e.attributeValue(oridAttributeName);
			if (attributeValue != null
					&& attributeValue.equals(oridAttributeValue)) {
				return e.attributeValue(destAttributeName);
			}
		}
		return null;
	}
	/**
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public boolean build(String filePath) throws Exception {
		if (filePath == null) {
			return false;
		}
		FileUtil io = new FileUtil();
		SAXReader reader = new SAXReader();
		inputStream = io.findBufferedInputStream(filePath);
		if (inputStream == null) {
			return false;
		} else {
			// 读取XML文档
			document = reader.read(inputStream);
			// 声明根元素
			elementRoot = document.getRootElement();
		}
		return true;
	}
	/**
	 * 
	 * 取得开始节点
	 * 
	 * @return
	 */
	public Element findRoot() {
		return this.elementRoot;
	}
}
