package com.cloud.common.core;
/**
 * @Description: 默认配置类
 * @Author: Dongming
 * @Date: 2018-12-03 15:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface DefaultConfig {

	String TENANT_CODE = "CLOUD";


	String CLOUD_START = "FALSE";

	String MQ_XML = "/config/com/cloud/mq/rabbitmq.xml";
	String MODULE = "connector";

	String POOL_CONFIG = "100,400,200,20";
	String CONNECTOR_PACKAGE = "com.cloud";

	String CLOUD_URL = "http://cloud.tzxyz.xyz";
}
