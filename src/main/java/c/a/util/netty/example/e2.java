package c.a.util.netty.example;
import java.util.List;

import c.a.util.netty.core.TcpNettyUtil;
public class e2 {
	public static void main(String[] args) {
		try {
			TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
			String inputStr = "a1234567890";
			List<String> strList =nettyUtil.doSplit(inputStr);
			System.out.println("returnStr=" +  strList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
