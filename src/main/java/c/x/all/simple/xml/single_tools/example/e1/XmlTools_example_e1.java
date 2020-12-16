package c.x.all.simple.xml.single_tools.example.e1;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import c.x.all.simple.xml.single_tools.XmlTool;

public class XmlTools_example_e1 {

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
