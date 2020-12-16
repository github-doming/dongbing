package org.doming.core.enums;
/**
 * @Description: doming state字段说明
 * @Author: Dongming
 * @Date: 2018-10-20 09:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public enum CodeEnum {
	/**
	 * 200
	 */
	CODE_200("200","Request success") {
		@Override public String getMsgCn() {
			return "请求成功";
		}
	},
	/**
	 * 200
	 */
	DM_200("200","Request success") {
		@Override public String getMsgCn() {
			return "请求成功";
		}
	},
	/**
	 * 401
	 */
	CODE_401("401","Data verification failed"){
		@Override public String getMsgCn() {
			return "数据验证失败";
		}
	},
	/**
	 * 401NotFindData
	 */
	DM_401_NOT_FIND_DATA("401NotFindData","No data found"){
		@Override public String getMsgCn() {
			return "没有找到数据";
		}
	},
	/**
	 * 403
	 */
	CODE_403("403","Resource not available"){
		@Override public String getMsgCn() {
			return "资源不可用";
		}
	},
	/**
	 * 403Time
	 */
	DM_403_TIME("403Time", "Insufficient user time, please recharge time") {
		@Override
		public String getMsgCn() {
			return "用户时长不足，请充值时长";
		}
	},
	/**
	 * 403Point
	 */
	DM_403_POINT("403Point", "Insufficient user point, please recharge point") {
		@Override
		public String getMsgCn() {
			return "用户积分不足，请充值";
		}
	},
	/**
	 * 403Restrict
	 */
	DM_403_RESTRICT("403Restrict", "Requests are too frequent, please try again later") {
		@Override
		public String getMsgCn() {
			return "请求过于频繁，请稍后再试";
		}
	},
	/**
	 * 404
	 */
	CODE_404("401","Data not found"){
		@Override public String getMsgCn() {
			return "数据没有找到";
		}
	},
	/**
	 * 404DataNotFind
	 */
	DM_404_DATA("404Data","Data not found"){
		@Override public String getMsgCn() {
			return "数据没有找到";
		}
	},
	
	/**
	 * 404NotFindPre
	 */
	DM_404_PER("404Per","User does not have permission"){
		@Override public String getMsgCn() {
			return "用户没有权限";
		}
	},
	/**
	 * 500
	 */
	CODE_500("500","Server error, please try again late") {
		@Override public String getMsgCn() {
			return "服务器出错，请稍候再试";
		}
	},
	/**
	 * 500
	 */
	DM_500("500","Server error, please try again later"){
		@Override public String getMsgCn() {
			return "服务器出错，请稍候再试";
		}
	};

	private String code;
	private String msg;

	/**
	 * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	 * @param code code
	 * @param msg message
	 */
	CodeEnum(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	@Override public String toString() {
		return code;
	}

	/**
	 * 获取中文消息
	 * @return 中文消息
	 */
	public abstract String getMsgCn();
}
