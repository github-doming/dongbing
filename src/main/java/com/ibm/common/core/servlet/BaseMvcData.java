package com.ibm.common.core.servlet;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.ibm.common.core.MvcActionTool;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.tools.EncryptTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;
/**
 * MVC 数据解析类
 * @Author: Dongming
 * @Date: 2020-03-27 10:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseMvcData extends BaseMvcTransactions {

	private static String tenant;

	protected String json = null;
	protected String cmd = null;
	protected Map<String, Object> dataMap = null;
	protected boolean encryptDate = false;


	/**
	 * 获取 请求中的JSON数据
	 *
	 * @return 请求数据
	 */
	public String findJson() throws Exception {
		logAccessUrl();
		return this.findJsonPlus();
	}

	/**
	 * 获取 请求中的JSON数据 并初始化 - 请求数据
	 *
	 * @return 请求数据
	 */
	public Map<String, Object> findDateMap() throws Exception {
		logAccessUrl();
		return this.findDateMapPlus();
	}

	/**
	 * 获取请求参数中的json 数据
	 *
	 * @return json 数据
	 */
	protected String findJsonPlus() {
		String[] jsons = request.getParameterMap().get("json");
		json =request.getParameter("json");
		if (jsons == null || jsons.length != 1 || !jsons[0].equals(json)) {
			log.warn("访问的JSON错误");
			log.info("jsons=" + Arrays.toString(jsons));
			log.info("json=" + json);
			MvcActionTool.returnCode400();
		} else {
			LogThreadLocal.setLog(true, "处理成功");
			log.info("所访问的json=" + json);
		}
		return json;
	}

	/**
	 * 获取请求数据
	 *
	 * @return 请求数据
	 */
	private Map<String, Object> findDateMapPlus() {
		findJsonPlus();
		if (StringTool.notEmpty(json)) {
			Map<String, Object> map = JSON.parseObject(json);
			if (ContainerTool.notEmpty(map)) {
				findDateMap(map);
			} else {
				log.info(IbmMainConfig.LOG_SIGN + "没有找到数据，所请求的json=" + json);
				MvcActionTool.returnCode400();
			}
		} else {
			log.info(IbmMainConfig.LOG_SIGN + "没有找到数据，所请求的request=" + getParamsString());
			MvcActionTool.returnCode400();
		}
		if (ContainerTool.isEmpty(dataMap) && LogThreadLocal.isSuccess()) {
			MvcActionTool.returnCode400();
		}
		return dataMap;
	}

	/**
	 * 查找数据
	 *
	 * @param map 数据
	 */
	void findDateMap(Map<String, Object> map) {
		Object dataObject = map.get("data");
		// 识别配置文件是否加密data
		String dataRsa;
		try {
			dataRsa = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.dataRSA"), "");
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN + "读取配置失败");
			MvcActionTool.returnCode500();
			return;
		}
		if (StringTool.notEmpty(dataRsa)) {
			encryptDate = Boolean.parseBoolean(dataRsa);
		}
		if (dataObject != null) {
			// 加密
			if (encryptDate) {
				String decode;
				try {
					decode = EncryptTool.decode(EncryptTool.Type.RSA, dataObject.toString());
				} catch (Exception e) {
					log.warn(IbmMainConfig.LOG_SIGN + "data解密失败,dataObject=" + dataObject.toString());
					MvcActionTool.returnCode403Rsa();
					return;
				}
				if (StringTool.notEmpty(decode)) {
					decode = StringTool.trimAll(decode);
				} else {
					log.warn(IbmMainConfig.LOG_SIGN + "data解密失败,解密的data为空");
					MvcActionTool.returnCode404();
					return;
				}
				try {
					dataMap = JSON.parseObject(decode);
				} catch (Exception e) {
					log.warn(IbmMainConfig.LOG_SIGN + "data转化失败,data解码=" + decode);
					MvcActionTool.returnCode404();
					return;
				}
			} else {
				String data = dataObject.toString();
				data = StringTool.trimAll(data);
				dataMap = JSON.parseObject(data);
			}
		}
		cmd = map.getOrDefault("cmd", "").toString();
		LogThreadLocal.setLog(true, "处理成功");
	}

	/**
	 * 访问地址
	 */
	void logAccessUrl() throws Exception {
		String commLocalProject = SysConfig.findInstance().findMap().get("comm.local.type").toString();
		String stringBuffer =
				("项目comm.local.type=" + commLocalProject) + ";" + "当前IP=" + findServletIp() + ";" + "ServletPath="
						+ findServletPath() + ";" + "tokenAction=" + this.getClass().getName() + ";";
		log.info(stringBuffer);
	}

	/**
	 * 获取机构码
	 * @return 机构码
	 */
	protected String getTenant() throws Exception {
		if (tenant == null) {
			tenant = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalTenant), IbmMainConfig.TENANT_CODE);
		}
		return tenant;
	}
}
