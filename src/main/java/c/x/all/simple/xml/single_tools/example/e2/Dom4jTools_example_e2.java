package c.x.all.simple.xml.single_tools.example.e2;

import java.util.HashMap;
import java.util.Map;

import c.a.util.core.xml.XmlUtil;
import c.x.all.simple.xml.single_tools.Dom4jTool;

public class Dom4jTools_example_e2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XmlUtil xmlUtil = new XmlUtil();
		Dom4jTool dom4jTool = new Dom4jTool();
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
		map1 = dom4jTool.xml2map(xml);
		System.out.println("ToUserName=" + map1.get("ToUserName"));
		Map map2 = new HashMap();
		map2.put("ToUserName", "obR6hv27FSu0DRepM3hEl5bXKKTk");
		map2.put("FromUserName", "gh_228a800b73ec");
		String str = xmlUtil.map2xml("你好", map2);
		System.out.println(str);
	}
}
