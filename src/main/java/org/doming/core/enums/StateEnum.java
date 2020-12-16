package org.doming.core.enums;
/**
 * @Description: 状态枚举类
 * @Author: Dongming
 * @Date: 2019-01-19 14:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public enum StateEnum {

	/**
	 * 打开
	 */
	OPEN("open") {
		@Override public String getCn() {
			return "打开";
		}
	},
	/**
	 * 关闭
	 */
	CLOSE("CLOSE") {
		@Override public String getCn() {
			return "关闭";
		}
	},
	/**
	 * 删除
	 */
	DEL("DEL") {
		@Override public String getCn() {
			return "删除";
		}
	},
	/**
	 * 默认
	 */
	DEF("DEF") {
		@Override public String getCn() {
			return "默认";
		}
	},

	/**
	 * 取消
	 */
	CANCEL("CANCEL") {
		@Override public String getCn() {
			return "取消";
		}
	},
	/**
	 * 登录
	 */
	LOGIN("LOGIN") {
		@Override public String getCn() {
			return "登录";
		}
	},
	/**
	 * 退出
	 */
	LOGOUT("LOGOUT") {
		@Override public String getCn() {
			return "退出";
		}
	};

	private String code;

	StateEnum(String code) {
		this.code = code;
	}

	public abstract String getCn();
}
