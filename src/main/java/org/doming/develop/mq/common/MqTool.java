package org.doming.develop.mq.common;
import c.a.config.SysConfig;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.doming.develop.mq.bean.MqConfigEntity;

/**
 * @Description: 消息队列工具类
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2018-11-13 10:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */

public class MqTool {
	protected static final Logger log = LogManager.getLogger(MqTool.class);
	/**
	 * 获取配置实体类
	 * @param configPathKey 配置路径键
	 * @return 实体类
	 */
	public static MqConfigEntity getConfigEntity(String configPathKey) throws Exception {
		String configPath = SysConfig.findInstance().findMap().get(configPathKey).toString();
		return getConfigEntityByPath(configPath);
	}
	/**
	 * 获取配置实体类
	 * @param configPath 配置路径
	 * @return 实体类
	 */
	public static MqConfigEntity getConfigEntityByPath(String configPath) throws Exception {
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		MqXmlUtil xmlUtil = new MqXmlUtil();
		xmlUtil.build(pathUtil.findPath(configPath));
		return xmlUtil.findMQConfigEntity();
	}

}
