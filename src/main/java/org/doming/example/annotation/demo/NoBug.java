package org.doming.example.annotation.demo;
/**
 * @Description: 测试Demo
 * @Author: Dongming
 * @Date: 2018-09-27 18:12
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class NoBug {
	@Jiecha
	public void suanShu(){
		System.out.println("1234567890");
	}
	@Jiecha
	public void jiafa(){
		System.out.println("1+1="+1+1);
	}
	@Jiecha
	public void jianfa(){
		System.out.println("1-1="+(1-1));
	}
	@Jiecha
	public void chengfa(){
		System.out.println("3 x 5="+ 3*5);
	}
	@Jiecha
	public void chufa(){
		System.out.println("6 / 0="+ 6 / 0);
	}

	public void ziwojieshao(){
		System.out.println("我写的程序没有 bug!");
	}

}
