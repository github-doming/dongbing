package com.cloud.common.core;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.cloud.common.tool.EncryptTool;
import com.cloud.common.tool.MvcActionTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Map;
/**
 * MVC 数据解析类
 *
 * @Author: Dongming
 * @Date: 2020-05-19 16:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseMvcData extends BaseMvcTransactions {
	public BaseMvcData() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_REPEATABLE_READ;
	}

	private static String tenant;
	protected String json = null;
	protected String cmd = null;
	protected String token = null;
	protected Map<String, Object> dataMap = null;
	private boolean encryptDate = false;

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
		json = request.getParameter("json");
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
	 * 获取机构码
	 *
	 * @return 机构码
	 */
	protected String getTenant() throws Exception {
		if (tenant == null) {
			tenant = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalTenant), DefaultConfig.TENANT_CODE);
		}
		return tenant;
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
				log.info("没有找到数据，所请求的json=" + json);
				MvcActionTool.returnCode400();
			}
		} else {
			log.info("没有找到数据，所请求的request=" + getParamsString());
			MvcActionTool.returnCode400();
		}
		if (StringTool.isEmpty(token) && ContainerTool.isEmpty(dataMap) && LogThreadLocal.isSuccess()) {
			MvcActionTool.returnCode400();
		}
		return dataMap;
	}
	/**
	 * 查找数据
	 *
	 * @param map 数据
	 */
	protected void findDateMap(Map<String, Object> map) {
		Object dataObject = map.get("data");
		// 识别配置文件是否加密data
		String dataRsa;
		try {
			dataRsa = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.dataRSA"), "");
		} catch (Exception e) {
			log.error("读取配置失败");
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
					log.warn("data解密失败,dataObject=" + dataObject.toString());
					MvcActionTool.returnCode403Rsa();
					return;
				}
				if (StringTool.notEmpty(decode)) {
					decode = StringTool.trimAll(decode);
				} else {
					log.warn("data解密失败,解密的data为空");
					MvcActionTool.returnCode404();
					return;
				}
				try {
					dataMap = JSON.parseObject(decode);
				} catch (Exception e) {
					log.warn("data转化失败,data解码=" + decode);
					MvcActionTool.returnCode404();
					return;
				}
			} else {
				String data = dataObject.toString();
				data = StringTool.trimAll(data);
				dataMap = JSON.parseObject(data);
			}
		}
		token = map.getOrDefault("token", "").toString();
		cmd = map.getOrDefault("cmd", "").toString();
		LogThreadLocal.setLog(true, "处理成功");
	}

	/**
	 * 访问地址
	 */
	protected void logAccessUrl() throws Exception {
		String commLocalProject = SysConfig.findInstance().findMap().get("comm.local.type").toString();
		String stringBuffer =
				("项目comm.local.type=" + commLocalProject) + ";" + "当前IP=" + findServletIp() + ";" + "ServletPath="
						+ findServletPath() + ";" + "tokenAction=" + this.getClass().getName() + ";";
		log.info(stringBuffer);
	}

	/**
	 * 获取访问者路径
	 *
	 * @return 访问者路径
	 */
	String findServletPath() {
		return request.getRequestURI().replaceFirst(request.getContextPath(), "");
	}

	/**
	 * 获取访问者ip
	 *
	 * @return 访问者ip
	 */
	protected String findServletIp() {

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if ("127.0.0.1".equals(ip)) {
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					throw new RuntimeException("获取ip失败，".concat(e.getMessage()));
				}
			}
		}
		return ip;
	}
	/**
	 * 打印request 字符串
	 *
	 * @return request.getParameterMap 字符串
	 */
	protected String getParamsString() {
		Map<String, String[]> parameterMap = request.getParameterMap();
		StringBuilder mapStr = new StringBuilder("{");
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			mapStr.append(entry.getKey()).append(":");
			if (entry.getValue() != null) {
				mapStr.append(Arrays.toString(entry.getValue()));
			}
			mapStr.append(";");
		}
		mapStr.deleteCharAt(mapStr.length() - 1);
		mapStr.append("}");
		return mapStr.toString();
	}

	/**
	 * 返回json
	 *
	 * @param bean json 封装实体类
	 * @return json字符串
	 */
	protected String returnJson(JsonResultBean bean) {
		return JsonThreadLocal.bean2json(bean);
	}
}
