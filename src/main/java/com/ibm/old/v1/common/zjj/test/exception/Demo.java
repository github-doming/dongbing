package com.ibm.old.v1.common.zjj.test.exception;

public class Demo {
	public static void main(String[] args) {
//		int i = 1/0;
		try {
			int a = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("----------"+e.toString());
		}
		
	}
	

}
