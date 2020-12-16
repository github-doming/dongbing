package com.ibm.old.v1.common.zjj.test.str;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-04-08 10:40
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class StrTest {
	private static Map<String, Map<String, String>> userMap= new HashMap<>(10);
	public static void main(String[] args) {
		String period="20190404-132";
		String[] str=period.split("-");
		StringBuilder stringBuffer=new StringBuilder("20190404");
		stringBuffer.insert(6,"-");
		stringBuffer.insert(4,"-");
//		System.out.println(stringBuffer.toString());

		Map<String,String> map=new HashMap<>();

		map.put("11","11");
		map.put("22","11");
		map.put("33","11");
		map.put("44","11");
		userMap.put("qq",map);


		System.out.println(userMap.get("qq"));

		userMap.remove("qq");

		map.put("zz","zz");

		userMap.put("qq",map);

		System.out.println(userMap.get("qq"));


	}
}
