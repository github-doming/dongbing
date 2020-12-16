package org.doming.core;
/**
 * @Description: 简单的执行结果类
 * @Author: Dongming
 * @Date: 2018-09-17 11:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class EasyResultBean {
	private String msg = null;
	private String message = null;
	private boolean flag = false;
	private Long startTime = null;
	private Long endTime = null;
	private Long useTime = null;

	public String msg() {
		return msg;
	}
	public void msg(String msg) {
		this.msg = msg;
	}
	public boolean flag() {
		return flag;
	}
	public void flag(boolean flag) {
		this.flag = flag;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void startTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long endTime() {
		return endTime;
	}
	public void endTime(Long endTime) {
		this.endTime = endTime;
	}
	public Long useTime() {
		return useTime;
	}
	private void useTime(Long useTime) {
		this.useTime = useTime;
	}
	public String message() {
		return message;
	}
	public void message(String message) {
		this.message = message;
	}

	/**
	 * 初始化
 	 * @param bean
	 * @return
	 */
	public static EasyResultBean init(
			EasyResultBean bean) {
		if (bean == null) {
			bean = new EasyResultBean();
		}
		bean.start();
		return bean;
	}

	public void failed() {
		flag(false);
		msg("失败");
	}
	public void succeeded() {
		flag(true);
		msg("成功");
	}

	public void start() {
		endTime(null);
		useTime(null);
		msg("");
		flag(false);
		startTime(System.currentTimeMillis());

	}

	public EasyResultBean end() {
		endTime(System.currentTimeMillis());
		useTime(endTime - startTime);
		return this;
	}


}
