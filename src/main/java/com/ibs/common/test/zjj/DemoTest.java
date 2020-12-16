package com.ibs.common.test.zjj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ibs.common.core.CommTest;
import org.junit.Test;

import java.util.*;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-06-05 17:16
 * @Version: v1.0
 */
public class DemoTest extends CommTest {

	@Test
	public void decode1() {

		JSONObject activePlanGroup = new JSONObject();

		JSONObject object=new JSONObject();
		object.put("123","123");

		for(int i=0;i<3;i++){
			JSONObject data=new JSONObject();
			data.put("selects",object);
			activePlanGroup.put(String.valueOf(i), data);
		}
		System.out.println(activePlanGroup.toString());
		System.out.println(JSON.toJSONString(activePlanGroup, SerializerFeature.DisableCircularReferenceDetect));

	}
	@Test
	public void test(){

		JSONObject groupData=new JSONObject();
		groupData.put("CUTOVER_KEY_","1,2,4");
		for(int i=0;i<5;i++){
			JSONObject data=new JSONObject();
			data.put("CURRENT_","REAL");
			data.put("CUTOVER_PROFIT_",100);
			data.put("CUTOVER_LOSS_",-100);
			data.put("CUTOVER_","VIRTUAL");
			data.put("RESET_PROFIT_","false");
			groupData.put(String.valueOf(i),data);
		}

		System.out.println(groupData.toString());

		System.out.println(	groupData.getJSONObject("0").getOrDefault("a",""));
		System.out.println(groupData.getJSONObject("0").get("b"));
		System.out.println(groupData.getJSONObject("0").get("CURRENT_"));
	}

	public static void main(String[] args) {
		//冷热排名
		//获取排名数据
		String str="1,2,3,4,5,6,7,8,9,10";

		String number="3";

		//目标结果{3,1,2,4,5,6,7,8,9,10}
		String[] strings=str.split(",");
		String[] newStrings=new String[10];
		newStrings[0]=number;
		arrayCopy(strings,newStrings,number);


		System.out.println(Arrays.asList(newStrings));

	}

	private static void arrayCopy(String[] strings, String[] newStrings,String number) {
		int j=1;
		for(int i=0;i<strings.length;i++){

			if(number.equals(strings[i])){
				j--;
				continue;
			}
			newStrings[i+j]=strings[i];
		}
	}

}
