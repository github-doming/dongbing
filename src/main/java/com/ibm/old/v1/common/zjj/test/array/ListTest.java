package com.ibm.old.v1.common.zjj.test.array;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-04-13 11:22
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class ListTest {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for (int h = 0; h < 2550; h++) {
			list.add(h);
		}
		int listSize = list.size();
		int toIndex = 100;
		//用map存起来新的分组后数据
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < list.size(); i += 100) {
			//作用为toIndex最后没有100条数据则剩余几条newList中就装几条
			if (i + 100 > listSize) {
				toIndex = listSize - i;
			}
			jsonArray.add(list.subList(i, i + toIndex));

		}
		System.out.println(jsonArray);
	}
}
