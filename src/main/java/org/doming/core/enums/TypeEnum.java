package org.doming.core.enums;

/**
 * @Description: 任务状态或数据状态
 * @Author: Dongming
 * @Date: 2018年10月16日10:06:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public enum TypeEnum {

	/**
	 * true
	 */
	TRUE("true") {
		@Override public String getMsgCn() {
			return "是";
		}
	},
	/**
	 * false
	 */
	FALSE("false") {
		@Override public String getMsgCn() {
			return "否";
		}
	},
	/**
	 * false
	 */
	READY("ready") {
		@Override public String getMsgCn() {
			return "就绪";
		}
	},
	/**
	 * auto
	 */
	AUTO("auto") {
		@Override public String getMsgCn() {
			return "自动";
		}
	},
	/**
	 * none
	 */
	NONE("none") {
		@Override public String getMsgCn() {
			return "无";
		}
	},
	/**
	 * real
	 */
	REAL("real") {
		@Override public String getMsgCn() {
			return "真实";
		}
	},
	/**
	 * virtual
	 */
	VIRTUAL("virtual") {
		@Override public String getMsgCn() {
			return "虚拟";
		}
	},
	/**
	 * send
	 */
	SEND("send") {
		@Override public String getMsgCn() {
			return "发送";
		}
	},
	/**
	 * 用户类型
	 */
	USER("user") {
		@Override public String getMsgCn() {
			return "用户";
		}
	}, ADMIN("admin") {
		@Override public String getMsgCn() {
			return "管理者";
		}
	};
	String msg;
	TypeEnum(String msg) {
		this.msg = msg;
	}

	/**
	 * 获取消息
	 *
	 * @return 消息
	 */
	public String getMsg() {
		return msg;
	}
	public abstract String getMsgCn();

}