package com.ibm.old.v1.common.doming.utils.http.httpclient.tools;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.Map;
/**
 * @Description: 盘口的httpunit工具类
 * @Author: Dongming
 * @Date: 2018-12-08 16:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HandicapHttpClientTool {

	/**
	 * 获取一个 HttpClient请求配置类<br>
	 * 如果存在则获取一个，不存在就创建一个新的
	 *
	 * @param existHmId 盘口会员存在id
	 * @param clientMap 请求配置类Map
	 * @return HttpClient请求配置类
	 */
	public static HttpClientConfig getHttpConfig(Map<String, HttpClientConfig> clientMap, String existHmId) {
		if (!clientMap.containsKey(existHmId)) {
			synchronized (HandicapHttpClientTool.class) {
				if (!clientMap.containsKey(existHmId)) {
					HttpClientConfig config = new HttpClientConfig();
					config.httpClient(HttpClientUtil.findInstance().createHttpClient());
					clientMap.put(existHmId, config);
					return config;
				}
			}
		}
		return clientMap.get(existHmId);
	}

	/**
	 * 开始登陆
	 *
	 * @param statusMap 登陆状态Map
	 * @param beanMap   登陆结果beanMap
	 * @param userMap   用户信息Map
	 * @param existHmId 盘口会员存在id
	 * @return 开始登陆成功 TRUE
	 */
	public static synchronized boolean startLogin(Map<String, Boolean> statusMap,
			Map<String, JsonResultBeanPlus> beanMap, Map<String, Map<String, String>> userMap, String existHmId) {
		if (statusMap.containsKey(existHmId)) {
			if (statusMap.get(existHmId)) {
				statusMap.put(existHmId, false);
				beanMap.remove(existHmId);
				userMap.remove(existHmId);
				return true;
			}
			return false;
		} else {
			statusMap.put(existHmId, false);
			beanMap.remove(existHmId);
			return true;
		}
	}

	/**
	 * 结束登陆
	 *
	 * @param statusMap 登陆状态Map
	 * @param beanMap   登陆结果beanMap
	 * @param existHmId 盘口会员存在id
	 * @param bean      登陆结果bean
	 * @return 结束登陆成功 TRUE
	 */
	public static synchronized boolean finishLogin(Map<String, Boolean> statusMap,
			Map<String, JsonResultBeanPlus> beanMap, String existHmId, JsonResultBeanPlus bean) {
		if (statusMap.containsKey(existHmId)) {
			if (!statusMap.get(existHmId)) {
				statusMap.put(existHmId, true);
				beanMap.put(existHmId, bean);
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否处于登陆中
	 *
	 * @param statusMap 登陆状态Map
	 * @param existHmId 盘口会员存在id
	 * @return 登录中 TRUE
	 */
	public static boolean isLogging(Map<String, Boolean> statusMap, String existHmId) {
		return statusMap.get(existHmId) == null || !statusMap.get(existHmId);
	}
}
