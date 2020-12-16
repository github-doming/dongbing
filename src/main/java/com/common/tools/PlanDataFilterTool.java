package com.common.tools;

import com.alibaba.fastjson.JSONObject;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 方案组数据过滤工具类
 * @Author: null
 * @Date: 2020-08-28 14:57
 * @Version: v1.0
 */
public class PlanDataFilterTool {

	/**
	 * 过滤字符串方案组数据
	 * @param selectPosition		方案组数据key
	 * @param data					方案组数据
	 * @param max					最大值
	 */
	public static void strFilter(String selectPosition, JSONObject data,int max) {
		if(StringTool.isEmpty(data.get(selectPosition))){
			data.put(selectPosition,"0");
			return;
		}
		StringBuilder selectPlus=new StringBuilder();
		String[] selects=data.getString(selectPosition).split(",");
		for(String select:selects){
			if(NumberTool.getInteger(select)>max){
				continue;
			}
			selectPlus.append(select).append(",");
		}
		if(selectPlus.length()>0){
			selectPlus.delete(selectPlus.length()-1,selectPlus.length());
		}
		data.put(selectPosition,selectPlus.toString());
	}

	/**
	 * 过滤数字方案组数据
	 * @param selectPosition	方案组数据key
	 * @param data				方案组数据
	 * @param min				最小值
	 * @param max				最大值
	 */
	public static void intFilter(String selectPosition, JSONObject data, int min, int max) {
		int select= NumberTool.getInteger(data.get(selectPosition));
		if(select>max||select<min){
			select=0;
		}
		data.put(selectPosition,String.valueOf(select));
	}

	/**
	 * 符合方案条件
	 * 1，只有select bet类型
	 * 2，只有带逗号，不带等号过滤
	 * 3，select 为单个数字
	 *
	 * @param planGroupData		方案组数据
	 */
	public static void selectAndBetFilter(JSONObject planGroupData,String selectPosition,String betPosition) {
		JSONObject data;
		String[] bets;
		StringBuilder betPlus;
		int select;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			betPlus=new StringBuilder();
			data=JSONObject.parseObject(entry.getValue().toString());
			bets=data.getString(betPosition).split(",");
			for(String bet:bets){
				if(NumberTool.getInteger(bet)>20){
					continue;
				}
				betPlus.append(bet).append(",");
			}
			if(betPlus.length()>0){
				betPlus.delete(betPlus.length()-1,betPlus.length());
			}
			data.put(betPosition,betPlus.toString());
			select=NumberTool.getInteger(data.get(selectPosition));
			if(select>10||select<=0){
				select=1;
			}
			data.put(selectPosition,String.valueOf(select));
			entry.setValue(data);
		}
	}

	/**
	 * 符合方案条件
	 * 1，只有select bet类型
	 * 2，带逗号、等号过滤
	 * 3，select 为单个数字
	 *
	 * @param planGroupData		方案组数据
	 */
	public static void selectAndBetCutoverFilter(JSONObject planGroupData,String selectPosition,String betPosition,boolean isSingle) {
		JSONObject data;
		String[] bets , selects,selectArr;
		StringBuilder betPlus,selectPlus;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			betPlus=new StringBuilder();
			selectPlus=new StringBuilder();
			data=JSONObject.parseObject(entry.getValue().toString());
			bets=data.getString(betPosition).split("=");
			for(String bet:bets){
				for (String b :bet.split(",")){
					if(NumberTool.getInteger(b)<=0||NumberTool.getInteger(b)>10){
						continue;
					}
					betPlus.append(b).append(",");
				}
				if(betPlus.length()>0){
					betPlus.delete(betPlus.length()-1,betPlus.length());
				}
			}
			if (betPlus.length() > 0) {
				betPlus.delete(betPlus.length() - 1, betPlus.length());
			}

			selects = data.getString(selectPosition).split("=");
			for (String select:selects){

				selectArr = select.split(",");
				if(isSingle && selectArr.length>1){
					//select如果有多个矫正为第一个
					selectPlus.append(select.split(",")[0]).append("=");
					continue;
				}
				for (String st : selectArr){
					if (NumberTool.getInteger(st) <= 0 || NumberTool.getInteger(st) > 10) {
						st="1";
					}
					selectPlus.append(st).append(",");
				}
				if (selectPlus.length() > 0) {
					selectPlus.delete(selectPlus.length() - 1, selectPlus.length());
				}
			}
			if (selectPlus.length() > 0) {
				selectPlus.delete(selectPlus.length() - 1, selectPlus.length());
			}

			data.put(betPosition,betPlus.toString());
			data.put(selectPosition,selectPlus.toString());
			entry.setValue(data);
		}
	}

}
