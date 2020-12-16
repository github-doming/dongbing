package org.doming.example.annotation.eg4;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-09-27 15:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@TestAnnotation(id = 3)
public class Test {

	@Check("hello")
	int a;

	@Perform
	public void testMethod(){}
}
