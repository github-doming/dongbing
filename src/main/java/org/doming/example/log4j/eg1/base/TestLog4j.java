package org.doming.example.log4j.eg1.base;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * @Description: 基础测试
 * @Author: Dongming
 * @Date: 2019-04-16 13:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TestLog4j {

	public static void main(String[] args)  {
		PropertyConfigurator.configure("D:/svn/3/code/all/trunk/src/main/java/org/doming/example/log4j/log4j.properties");
		Logger log = Logger.getLogger(TestLog4j.class);

		for(int i = 0; i < 10; i++) {
			log.debug("这是一个debug信息");
			log.trace("这是一个trace信息");
			log.info("这是一个info信息");
			log.warn("这是一个warn信息");
			log.error("这是一个error信息");

		}

	}
}
