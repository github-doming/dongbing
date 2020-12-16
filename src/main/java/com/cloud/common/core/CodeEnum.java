package com.cloud.common.core;
/**
 * 请求或通讯返回字段
 *
 * @Author: Dongming
 * @Date: 2020-05-09 17:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum CodeEnum {
	/**
	 * 200
	 */
	CODE_200("200", "Request success") {
		@Override public String getMsgCn() {
			return "请求成功";
		}
	},
	/**
	 * 200
	 */
	CLOUD_200("200", "Request success") {
		@Override public String getMsgCn() {
			return "请求成功";
		}
	},

	/**
	 * 401
	 */
	CODE_401("401", "Data verification failed") {
		@Override
		public String getMsgCn() {
			return "数据验证失败";
		}
	},
	/**
	 * 401Data
	 */
	CLOUD_401_DATA("401Data", "No data found") {
		@Override
		public String getMsgCn() {
			return "没有找到数据";
		}
	},
	/**
	 * 403
	 */
	CODE_403("403", "Data forbidden") {
		@Override
		public String getMsgCn() {
			return "数据被拒绝";
		}
	},
	/**
	 * 403DataError
	 */
	CLOUD_403_DATA_ERROR("403DataError", "Data error") {
		@Override
		public String getMsgCn() {
			return "数据错误";
		}
	},
	/**
	 * 403ExistMenu
	 */
	CLOUD_403_EXIST("403Exist", "Resources code already exists") {
		@Override
		public String getMsgCn() {
			return "资源已存在";
		}
	},
	/**
	 * 403ExistLogin
	 */
	CLOUD_403_EXIST_LOGIN("403ExistLogin", "This account has been logged in to this platform.") {
		@Override
		public String getMsgCn() {
			return "该账号已登录在本平台";
		}
	},

	/**
	 * 403Error
	 */
	CLOUD_403_ERROR("403Error", "Request fetch error") {
		@Override
		public String getMsgCn() {
			return "请求获取错误";
		}
	},
	/**
	 * 403Refresh
	 */
	CLOUD_403_REFRESH("403Refresh", "Request fetch error, please refresh and try again") {
		@Override
		public String getMsgCn() {
			return "请求获取错误，请刷新重试";
		}
	},
	/**
	 * 403Permission
	 */
	CLOUD_403_PERMISSION("403Permission", "Insufficient permission level, request denied") {
		@Override
		public String getMsgCn() {
			return "权限等级不够，请求被拒绝";
		}
	},
	/**
	 * 403Permission
	 */
	CLOUD_403_DISABLE("403Disable", "Account is disable,please contact admin") {
		@Override
		public String getMsgCn() {
			return "账号已被禁用，请联系管理员";
		}
	},
	/**
	 * 403existSub
	 */
	CLOUD_403_EXIST_SUB("403existSub", "Account has sub users,Can't delete") {
		@Override
		public String getMsgCn() {
			return "该账号下存在可用子代理，无法删除";
		}
	},
	/**
	 * 403Activate
	 */
	CLOUD_403_ACTIVATE("403Activate", "The prepaid card has been activated and cannot be modified") {
		@Override
		public String getMsgCn() {
			return "该充值卡已被激活，无法修改";
		}
	},
	/**
	 * 403InsufficientPoint
	 */
	CLOUD_403_INSUFFICIENT_POINT("403InsufficientPoint", "Insufficient user point, please recharge point") {
		@Override
		public String getMsgCn() {
			return "用户积分不足，请充值";
		}
	},
	/**
	 * 404
	 */
	CODE_404("404", "Data not found") {
		@Override
		public String getMsgCn() {
			return "数据没有找到";
		}
	},
	/**
	 * 404Data
	 */
	CLOUD_404_DATA("404Data", "Data not found") {
		@Override
		public String getMsgCn() {
			return "数据没有找到";
		}
	},
	/**
	 * 404Exist
	 */
	CLOUD_404_EXIST("404Exist", "Data does not exist") {
		@Override
		public String getMsgCn() {
			return "数据不存在";
		}
	},
	/**
	 * 500
	 */
	CODE_500("500", "Server error, please try again late") {
		@Override
		public String getMsgCn() {
			return "服务器出错，请稍候再试";
		}
	},
	/**
	 * 500
	 */
	CLOUD_500("500", "Server error, please try again later") {
		@Override
		public String getMsgCn() {
			return "服务器出错，请稍候再试";
		}
	},
	/**
	 * 503
	 */
	CODE_503("503", "The server is currently unable to process the request") {
		@Override
		public String getMsgCn() {
			return "服务器当前无法处理请求";
		}
	},
	/**
	 * 503
	 */
	CLOUD_503_TIME("503Time", "The server is currently unable to process the request, please try again after 7 o'clock") {
		@Override
		public String getMsgCn() {
			return "服务器当前无法处理请求，请七点以后再试";
		}
	};

	private String code;
	private String msg;

	/**
	 * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	 *
	 * @param code code
	 * @param msg  message
	 */
	CodeEnum(String code, String msg) {
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
	 *
	 * @return 中文消息
	 */
	public abstract String getMsgCn();

}
