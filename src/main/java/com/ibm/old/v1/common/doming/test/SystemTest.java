package com.ibm.old.v1.common.doming.test;
import org.doming.core.common.servlet.MvcExecutor;
import org.doming.core.tools.LogTool;
import org.junit.Test;

import java.io.IOException;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-18 18:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SystemTest {
	@Test public void test01() throws IOException {
		Runtime r = Runtime.getRuntime();
		r.exec("");
	}

	@Test public void test02() {
		long nanoTime = System.nanoTime();
		System.out.println("nanoTime=" + nanoTime);
		long millisTime = System.currentTimeMillis();
		System.out.println("millisTime=" + millisTime);
		System.out.println(LogTool.printHashtagM());
		nanoTime = System.nanoTime();
		System.out.println("nanoTime=" + nanoTime);
		millisTime = System.currentTimeMillis();
		System.out.println("millisTime=" + millisTime);
		System.out.println(LogTool.printHashtagM());
		nanoTime = System.nanoTime();
		System.out.println("nanoTime=" + nanoTime);
		millisTime = System.currentTimeMillis();
		System.out.println("millisTime=" + millisTime);
		System.out.println(LogTool.printHashtagM());
		nanoTime = System.nanoTime();
		System.out.println("nanoTime=" + nanoTime);
		millisTime = System.currentTimeMillis();
		System.out.println("millisTime=" + millisTime);

	}

	@Test public void test03() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		Class<Object> clazz = (Class<Object>) Class.forName(MyMvcTest.class.getName());

		System.out.println("isAssignableFrom=" + clazz.isAssignableFrom(MvcExecutor.class));
		System.out.println("isInstance=" + clazz.isInstance(MvcExecutor.class));
		System.out.println("instanceof=" + (clazz.newInstance() instanceof MvcExecutor));

	}
}
