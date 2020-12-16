package com.ibm.common.core.configs;
import c.a.config.SysConfig;
import com.ibm.common.enums.IbmTypeEnum;
import org.doming.core.tools.RandomTool;
/**
 * @Description: 智能跟单启动配置类
 * @Author: Dongming
 * @Date: 2019-06-18 14:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface IbmMainConfig {

	//region MAIN
	/**
	 * 是否运行 - 运行模块 - MQ配置文件
	 */

    String HOST = "http://ibm.tzxyz.xyz";
    String PROJECT = "/ibm_main";
	String TENANT_CODE = "IBM";
	String LOG_SIGN = "[~ibm~]";
	Integer MAX_RECURSIVE_SIZE = 5;

	String START = IbmTypeEnum.FALSE.name();
	String MODULE =  Module.REQUEST.name();
	String MQ_XML = "/config/com/ibm/mq/rabbitmq/config.xml";
	String SERVER_LOCAL = "http://ibm.tzxyz.xyz/ibm_server";
	String CLOUD_URL = "http://192.168.14.123:9180/cloud";
	//endregion

	//region REQUEST 模块
	/**
	 * mvc 包 - 启动池 - 池大小
	 */
	String REQUEST_PACKAGE = "com.ibm";

	String REQUEST_POOL = "core,base,query";
	String CORE_POOL_CONFIG = "100,400,200,20";
	String BASE_POOL_CONFIG = "10,40,50,20";
	String QUERY_POOL_CONFIG = "50,200,150,20";

	/**
	 * 获取线程池配置
	 *
	 * @param pool 线程池类型
	 * @return 线程池配置
	 * @throws Exception 查找配置key错误
	 */
	static Object getPoolConfig(String pool) throws Exception {
		switch (pool) {
			case "core":
				return SysConfig.findInstance().findMap()
						.getOrDefault("ibm.request.core", IbmMainConfig.CORE_POOL_CONFIG);
			case "base":
				return SysConfig.findInstance().findMap()
						.getOrDefault("ibm.request.base", IbmMainConfig.BASE_POOL_CONFIG);
			case "query":
				return SysConfig.findInstance().findMap()
						.getOrDefault("ibm.request.query", IbmMainConfig.QUERY_POOL_CONFIG);
			default:
				throw new IllegalArgumentException("未知的code捕捉");

		}
	}

	//endregion

	//region EVENT 模块
	/**
	 * 主事件运行间隔时间
	 */
	int EVENT_DELAY = 10;
	//endregion

	//region CLIENT 模块
	/**
	 * 服务编码
	 */
	String CLIENT_CODE = RandomTool.getInt(1000, 9999).toString();
	String CLIENT_TEST = IbmTypeEnum.FALSE.name() ;
	//endregion


	//region SERVER 模块
	/**
	 * 服务请求池
	 */
	String SERVER_POOL = "base";
	//endregion

	enum Module {
		/**
		 * 模块
		 */
		REQUEST("请求"), MQ("消息队列"), CLIENT("客户端"), SERVER("服务端"),VR("虚拟客户机");
		private String name;
		Module(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}

	}
}
