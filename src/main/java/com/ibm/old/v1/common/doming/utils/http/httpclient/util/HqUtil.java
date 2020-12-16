package com.ibm.old.v1.common.doming.utils.http.httpclient.util;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.HandicapHttpClientTool;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.HqTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: HQ盘口的httpClient工具类
 * @Author: zjj
 * @Date: 2019-08-05 17:07
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HqUtil {
	protected Logger log = LogManager.getLogger(this.getClass());

	private static final Integer MAX_RECURSIVE_SIZE = 5;

	private Map<String, HttpClientConfig> clientMap;
	private Map<String, Map<String, String>> userMap;
	private Map<String, Map<String, String>> dataMap;
	private static long checkLoginTime=0;
	private static final long CHECK_LOGIN_TIME=40*1000L;

	private static volatile HqUtil instance = null;

	public static HqUtil findInstance(){
		if (instance == null) {
			synchronized (HqUtil.class){
				if(instance==null){
					HqUtil instance=new HqUtil();
					instance.init();
					HqUtil.instance=instance;
				}
			}
		}
		return instance;
	}
	private void init() {
		clientMap=new HashMap<>(10);
		userMap=new HashMap<>(10);
		dataMap=new HashMap<>(10);
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}
		if (ContainerTool.notEmpty(instance.clientMap)) {
			for (HttpClientConfig clientConfig : instance.clientMap.values()) {
				clientConfig.destroy();
			}
		}
		instance.userMap.clear();
		instance.userMap = null;
		instance.dataMap.clear();
		instance.dataMap = null;
		instance = null;
	}
	/**
	 * 登陆
	 *
	 * @param hmExistId       盘口会员存在id
	 * @param memberAccount   会员账号
	 * @param memberPassword  会员密码
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 */
	public JsonResultBeanPlus login(String hmExistId, String memberAccount, String memberPassword, String handicapUrl,
			String handicapCaptcha) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//已存在数据
		if (userMap.containsKey(hmExistId)) {
			bean.setData(userMap.get(hmExistId));
			bean.success();
			return bean;
		}

		if (dataMap.containsKey(hmExistId)) {
			Map<String, String> data = dataMap.get(hmExistId);
			data.put("memberAccount", memberAccount);
			data.put("memberPassword", memberPassword);
			data.put("handicapUrl", handicapUrl);
			data.put("handicapCaptcha", handicapCaptcha);
		} else {
			Map<String, String> data = new HashMap<>(4);
			data.put("memberAccount", memberAccount);
			data.put("memberPassword", memberPassword);
			data.put("handicapUrl", handicapUrl);
			data.put("handicapCaptcha", handicapCaptcha);
			dataMap.put(hmExistId, data);
		}

		try{
			//获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, hmExistId);

			bean=login(httpConfig,handicapUrl,handicapCaptcha,memberAccount,memberPassword);
			if(!bean.isSuccess()){
				return bean;
			}
			Map<String, String> data = (Map<String, String>) bean.getData();
			userMap.put(hmExistId, data);

		}catch (Exception e){
			log.error("HQ盘口会员【" + hmExistId + "】登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 登录
	 *
	 * @param httpConfig      请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param memberAccount   账号
	 * @param memberPassword  密码
	 * @param index			 循环次数
	 * @return
	 */
	public JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			String memberAccount, String memberPassword, int... index) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//如果盘口地址不包含http://则添加
		String http = "http://";
		if (!handicapUrl.contains(http)) {
			handicapUrl = http.concat(handicapUrl);
		}
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}
		httpConfig.headers(null);
		httpConfig.httpContext(null);

		try {
			//获取会员登入页面
			String href=HqTool.getMemberHtml(httpConfig,handicapUrl,handicapCaptcha);

			if(StringTool.isEmpty(href)){
				log.info("获取会员登入页面" + href);
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_NAVIGATE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}

			//获取线路
			String[] routes=HqTool.getSelectRoutePage(httpConfig,href,handicapUrl);

			System.out.println(Arrays.toString(routes));

			if(ContainerTool.isEmpty(routes)){
				log.info("获取会员登入页面" + href);
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_NAVIGATE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}

			//获取登录页面
			Map<String,String> info =HqTool.getLoginHtml(httpConfig,routes,handicapCaptcha);


		}catch (Exception e){
			log.error("IDC盘口账号【" + memberAccount + "】登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
