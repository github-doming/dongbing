package com.ibm.common.core.servlet;

import c.a.config.core.CommContextUtil;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;
import org.doming.core.common.servlet.AsynAction;
import org.doming.core.common.servlet.MvcExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Map;

/**
 * @Description: mvc执行事物基类
 * @Author: Dongming
 * @Date: 2019-06-19 11:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@AsynAction public abstract class BaseMvcTransactions extends TransactionsBase implements MvcExecutor {
	private long startCalendarLong = 0;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	protected boolean context = false;
	protected boolean database = false;
	protected String dataSource;

	/**
	 * 执行方法
	 *
	 * @return 执行结果
	 * @throws Exception 执行失败
	 */
	public abstract Object run() throws Exception;

	@Override public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long startTime = System.currentTimeMillis();
		//如果需要事物，肯定需要上下文
		this.request = request;
		this.response = response;
		if (database) {
			context = true;
		}
		Object result = null;
		if (context) {
			//获取上下文
			CommContextUtil commContextUtil = new CommContextUtil();
			CurrentTransaction.setDatabase(this);
			try {
				startCalendarLong = commContextUtil.start();
				if (database) {
					// 需要数据库操作
					result = this.executeDatabase();
				} else {
					// 不需要需要数据库操作
					result = this.executeOnly();
				}
			} catch (Exception e) {
				log.warn("上下文执行出错", e);
			} finally {
				CurrentTransaction.removeDataBase();
				commContextUtil.end(startCalendarLong);
			}
		} else {
			// 不需要上下文操作
			try {
				result = this.executeOnly();
			} catch (Exception e) {
				log.warn("执行出错", e);
			} finally {
				long endTime = System.currentTimeMillis();
				log.debug("花费时间spend time=" + (endTime - startTime));
			}
		}
		return result;
	}

	/**
	 * 需要数据库操作
	 *
	 * @return 执行结果
	 */
	private Object executeDatabase() throws Exception {
		Object result;
		try {
			//开始jdbc链接
			Connection connection = connectionBegin(dataSource);
			if (transaction != TRANSACTION_NONE && connection.getTransactionIsolation() != TRANSACTION_NONE) {
				//数据库连接支持事物
				try {
					beginTransaction(connection, transaction);
					result = run();
					endTransaction(connection);
				} catch (Exception e) {
					rollTransaction(connection);
					throw e;
				}
			} else {
				//数据库连接不支持事物
				result = run();
			}
		} finally {
			this.connectionEnd();
		}
		return result;
	}

	/**
	 * 仅执行
	 *
	 * @return 执行结果
	 */
	private Object executeOnly() throws Exception {
		return run();
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
	protected String returnJson(JsonTcpBean bean) {
		return JsonThreadLocal.bean2json(bean);
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

	@Override
	public Boolean otherOrigin() {
		return true;
	}
}
