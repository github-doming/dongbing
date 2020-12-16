package com.ibm.old.v1.common.doming.demos.pattern;
import java.net.URI;
/**
 * @Description: 正则表达式测试类
 * @Author: Dongming
 * @Date: 2018-08-07 11:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Simple_eg2 {
	public static void main1(String[] args) {
		String str = "http://pc4.1ll11.com/sscas8137262f/login/b2f5201c6b_rdsess/k";
		URI uri = URI.create(str);
		System.out.println(uri.getPath());
	}


	public static void main(String[] args) {

		String str = "[/sscas8137262f_6670/\n" + "http://host/sscas8137262f/login/de013523f7_rdsess/k\n" + "]";



		System.out.println();

	}
}
