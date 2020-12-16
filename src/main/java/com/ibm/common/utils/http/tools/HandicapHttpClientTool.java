package com.ibm.common.utils.http.tools;

import com.common.handicap.crawler.entity.MemberUser;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.entity.Crawler;
import com.ibm.follow.servlet.client.core.controller.ClientDefineController;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
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
     * @param crawler 爬虫信息
     * @return HttpClient请求配置类c
     */
    public static HttpClientConfig getHttpConfig(Crawler crawler) {
        if(crawler.getHcConfig()==null){
            synchronized (HandicapHttpClientTool.class) {
                if(crawler.getHcConfig()==null){
                    HttpClientConfig config = new HttpClientConfig();
                    config.httpClient(HttpClientUtil.findInstance().createHttpClient());
                    crawler.setHcConfig(config);
                    return config;
                }
            }
        }
        return crawler.getHcConfig();
    }

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

	public static Double getUsedQuota(String existHmId, HandicapUtil.Code handicapCode, GameUtil.Code gameCode,boolean flag) {
		MemberUser userInfo=handicapCode.getMemberFactory().memberOption(existHmId).userInfo(flag);
		if (userInfo==null){
			throw new IllegalArgumentException("获取到的用户信息为空".concat(handicapCode.name()));
		}
		return userInfo.getUsedQuota();
	}

}
