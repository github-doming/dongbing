package c.x.all.simple.xml.single_tools;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import c.a.util.core.xml.XmlUtil;

public class Dom4jTool {

	/**
	 * 将xml字符串转换成map
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public Map xml2map(String xml) {
		XmlUtil xmlUtil = new XmlUtil();
		return xmlUtil.xml2map(xml);
	}

	/**
	 * 将xml字符串转换成map
	 * 
	 * @param xml
	 * @return
	 */
	public Map xmlmap_v1(String xml) {
		String[] XML_ELEMENT = {"ToUserName", "FromUserName", "CreateTime",
				"Event", "EventKey", "MsgType", "Content", "Voice", "MediaId"};
		Map map = new HashMap();
		Document doc = null;
		try {
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xml);
			// 获取根节点
			Element elementRoot = doc.getRootElement();
			// 拿到根节点的名称
			// System.out.println("根节点：" + rootElt.getName());
			for (int i = 0; i < XML_ELEMENT.length; i++) {
				Element element = elementRoot.element(XML_ELEMENT[i]);
				if (element != null) {
					map.put(element.getName(), element.getData());
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @param args
	 */
	public void main(String[] args) {
		XmlUtil xmlUtil = new XmlUtil();
		// <xml>
		// <ToUserName><![CDATA[gh_228a800b73ec]]></ToUserName>
		// <FromUserName><![CDATA[obR6hv27FSu0DRepM3hEl5bXKKTk]]></FromUserName>
		// <CreateTime>1435647724</CreateTime>
		// <MsgType><![CDATA[text]]></MsgType>
		// <Content><![CDATA[冲]]></Content>
		// <MsgId>6166060023368386079</MsgId>
		// </xml>
		//
		String xml = "<xml><ToUserName><![CDATA[gh_228a800b73ec]]></ToUserName><FromUserName><![CDATA[obR6hv27FSu0DRepM3hEl5bXKKTk]]></FromUserName><CreateTime>1435647724</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[冲]]></Content><MsgId>6166060023368386079</MsgId></xml>";
		Map map1;
		map1 = xmlUtil.xml2map(xml);
		System.out.println("ToUserName=" + map1.get("ToUserName"));
		Map map2 = new HashMap();
		map2.put("ToUserName", "obR6hv27FSu0DRepM3hEl5bXKKTk");
		map2.put("FromUserName", "gh_228a800b73ec");
		String str = xmlUtil.map2xml("你好", map2);
		System.out.println(str);
	}
}
