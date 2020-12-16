package com.ibs.plan.common.core.configs;
import com.ibs.common.enums.IbsTypeEnum;
import org.doming.core.tools.RandomTool;

import java.util.Map;
/**
 * 智能投注启动配置类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 10:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface PlanMainConfig {

	//region MAIN

	String HOST = "http://ibs.tzxyz.xyz";
	String PROJECT = "/request";


	String START = IbsTypeEnum.FALSE.name();
	String MQ_XML = "/config/com/ibs/mq/ibs_config.xml";
	String MODULE = Module.modules(Module.REQUEST, Module.CLIENT);
	//endregion

	//region  REQUEST 模块

	String CLIENT_TYPE="COLLECTION";

	String REQUEST_POOL = "core,base,query";
	String REQUEST_PACKAGE = "com.ibs";

	String CORE_POOL_CONFIG = "100,400,200,20";
	String BASE_POOL_CONFIG = "10,40,50,20";
	String QUERY_POOL_CONFIG = "50,200,150,20";


	/**
	 * 获取线程池配置
	 *
	 * @param sysMap 系统配置
	 * @param pool   线程池类型
	 * @return 线程池配置
	 */
	static Object getPoolConfig(Map<String, Object> sysMap, String pool) {
		switch (pool) {
			case "core":
				return sysMap.getOrDefault("ibs.request.core", CORE_POOL_CONFIG);
			case "base":
				return sysMap.getOrDefault("ibs.request.base", BASE_POOL_CONFIG);
			case "query":
				return sysMap.getOrDefault("ibs.request.query", QUERY_POOL_CONFIG);
			default:
				throw new IllegalArgumentException("未知的code捕捉");

		}
	}
	//endregion

	//region CLIENT 模块

	Object CLIENT_CODE = RandomTool.getInt(1000, 9999).toString();
	//endregion

	enum Module {
		/**
		 * 模块
		 */
		REQUEST("请求"), MQ("消息队列"), CLIENT("客户端"), SERVER("服务端");
		private String name;
		Module(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}

		/**
		 * 获取模块类型
		 *
		 * @param modules 模块数组
		 * @return 字符串
		 */
		static String modules(Module... modules) {
			if (modules.length == 0) {
				return "";
			}
			StringBuilder result = new StringBuilder();
			for (Module module : modules) {
				result.append(module.name()).append(",");
			}
			return result.toString();
		}

	}
}
