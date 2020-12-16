package c.x.all.simple.xml.single_tools;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class XmlTool {
	public static String findTextContent(String xml, String tagName)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(xml);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);
		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName(tagName);
		String content = nodelist1.item(0).getTextContent();
		return content;
	}
	public static Map xml2map(String xml) throws ParserConfigurationException,
			SAXException, IOException {
		Map map = new HashMap();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(xml);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			map.put(node.getNodeName(), node.getTextContent());
		}
		return map;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
		try {
			map1 = XmlTool.xml2map(xml);
			System.out.println("ToUserName=" + map1.get("ToUserName"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
