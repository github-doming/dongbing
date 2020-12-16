package com.ibm.old.v1.common.doming.core.servlet;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonThreadLocal;
import net.sf.json.JSONObject;
import org.doming.core.common.TransactionsBase;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.common.servlet.WorkedCallable;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * @Description: 异步mvc执行器
 * @Author: Dongming
 * @Date: 2019-05-18 10:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseAsynMvcExecutor extends TransactionsBase{

	protected String code;
	protected AsyncContext asyncContext;
	protected boolean otherOrigin = false;
	protected Map<String,String[]> parameterMap;

	/**
	 * 方法实际调用
	 *
	 * @return 调用结果
	 * @throws Exception 执行失败
	 */
	public abstract Object call() throws Exception;

	public void execute(HttpServletRequest request, HttpServletResponse response, WebServletContent content) {

		//开启异步上下文
		if (!request.isAsyncStarted()) {
			parameterMap =  request.getParameterMap();
			asyncContext = request.startAsync();
		}
		asyncContext.setTimeout(content.getAsyncTimeOut());
		if (content.getAsyncListener() != null) {
			asyncContext.addListener(content.getAsyncListener());
		}
		final ThreadPoolExecutor executor = content.getExecutorPool(code);
		executor.execute(new WorkedCallable(asyncContext) {
			@Override public void run() {
				long starTime = System.currentTimeMillis();
				//业务处理调用
				try {
					log.info("MVC 处理业务开始，请求参数为".concat(JSONObject.fromObject(parameterMap).toString()));
					Object result = call();
					//业务完成后，响应处理
					if (result == null) {
						callBack(null);
					}
					if (result instanceof CompletableFuture) {
						CompletableFuture<Object> future = (CompletableFuture<Object>) result;
						future.thenAccept(this::callBack).exceptionally(e -> {
							callBack(e.getMessage());
							return null;
						});
					} else {
						callBack(result);
					}
				} catch (Exception e) {
					callBack(e.getMessage());
				} finally {
					long endTime = System.currentTimeMillis();
					log.info("MVC 处理业务结束， 消耗时间=" + (endTime - starTime) + "ms");
				}
			}
		});
	}

	/**
	 * 获取访问者路径
	 *
	 * @return 访问者路径
	 */
	protected String findServletPath() {
		if (!(asyncContext.getRequest() instanceof HttpServletRequest)) {
			throw new RuntimeException("获取访问者路径失败，不能由请求获取访问者路径");
		}
		HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
		return request.getServletPath();
	}
	/**
	 * 获取访问者ip
	 *
	 * @return 访问者ip
	 */
	protected String findServletIp() {
		if (!(asyncContext.getRequest() instanceof HttpServletRequest)) {
			throw new RuntimeException("获取ip失败，不能由请求获取ip");
		}
		HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 打印request 字符串
	 *
	 * @return request.getParameterMap 字符串
	 */
	protected String getParamsString() {
		ServletRequest request = asyncContext.getRequest();
		Map<String, String[]> parameterMap = request.getParameterMap();
		StringBuilder mapStr = new StringBuilder("{");
		for (Map.Entry entry : parameterMap.entrySet()) {
			mapStr.append(entry.getKey()).append(":");
			if (entry.getValue() instanceof String[]) {
				mapStr.append(Arrays.toString((String[]) entry.getValue()));
			} else {
				mapStr.append(entry.getValue());
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
