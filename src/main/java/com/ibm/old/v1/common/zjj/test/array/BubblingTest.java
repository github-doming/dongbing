package com.ibm.old.v1.common.zjj.test.array;
import java.util.Arrays;
/**
 * @Description: 冒泡
 * @Author: zjj
 * @Date: 2019-04-07 10:00
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class BubblingTest {
	public static void main(String[] args) {
		int[] arr={13,15,46,24,14,43,44,65,76};

		//冒泡排序
		for(int x=0;x<arr.length-1;x++){
			//arr.length-x 是因为每次递减是都减少1， arr.length-x-1是防止下标越界因为当x=0的时候上面组数的长度是9-0
			for(int j=0;j<arr.length-x-1;j++){
				//,j取的最大值是8，下面的j+1就等于9了,上面数组没有下标为9的，所以要-1。
				if(arr[j]<arr[j+1]){
					int t=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=t;
				}
			}
		}
		System.out.println(Arrays.toString(arr));



	}
}
