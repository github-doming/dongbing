package org.doming.example.log4j.eg3.log4j2.my;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * 我自己的log日志文件
 *
 * @Author: Dongming
 * @Date: 2020-01-07 14:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MyLog {
	protected Logger log = LogManager.getLogger(this.getClass());

	@Before public void before() throws FileNotFoundException {
		System.setProperty("webapp.root", "D:\\");
		String path = MyLog.class.getResource("/org/doming/example/log4j/log4j2_my.xml").getPath();
		File file = new File(path);
		FileInputStream fileInputStream = new FileInputStream(path);
		ConfigurationSource configurationSource = new ConfigurationSource(fileInputStream, file);
		LoggerContext loggerContext = Configurator.initialize(null, configurationSource);
		XmlConfiguration xmlConfiguration = new XmlConfiguration(loggerContext, configurationSource);
		loggerContext.start(xmlConfiguration);
	}

	@Test public void test() {
		int i = 0;
		while(true){
			i++;
			if (i % 6 == 0) {
				log.trace("trace=" + i);
			} else if (i % 6 == 1) {
				log.debug("debug=" + i);
			} else if (i % 6 == 2) {
				log.info("info=" + i);
			} else if (i % 6 == 3) {
				log.error("error=" + i);
			} else if (i % 6 == 4) {
				log.warn("warn=" + i);
			} else {
				log.fatal("fatal=" + i);
			}

		}
	}

}
