package com.common.enums;

/**
 * @Description: 请求或通讯返回字段
 * @Author: zjj
 * @Date: 2020-07-10 15:41
 * @Email: 543974681@qq.com
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
	IBS_200("200", "Request success") {
		@Override public String getMsgCn() {
			return "请求成功";
		}
	},
	/**
	 * 202
	 */
	CODE_202("200", "Request Accepted") {
		@Override
		public String getMsgCn() {
			return "请求处理中";
		}
	},
	/**
	 * 202
	 */
	IBS_202("202", "Request Accepted") {
		@Override
		public String getMsgCn() {
			return "请求处理中";
		}
	},
	/**
	 * 202Event
	 */
	IBS_202_EVENT("202Event", "Event processing") {
		@Override
		public String getMsgCn() {
			return "事件处理中";
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
	IBS_401_DATA("401Data", "No data found") {
		@Override
		public String getMsgCn() {
			return "没有找到数据";
		}
	},
	/**
	 * 401Method
	 */
	IBS_401_ROLE_ERROR("401RoleError", "Role error") {
		@Override
		public String getMsgCn() {
			return "角色数量类型错误";
		}
	},
	/**
	 * 401Method
	 */
	IBS_401_METHOD("401Method", "Method type not found") {
		@Override
		public String getMsgCn() {
			return "方法类型没有找到";
		}
	},
	/**
	 * 401NotFindMessage
	 */
	IBS_401_MESSAGE("401NotFindMessage", "Communication content is empty") {
		@Override
		public String getMsgCn() {
			return "通讯内容为空";
		}
	},
	/**
	 * 401Cmd
	 */
	IBS_401_CMD("401Cmd", "Command type not found") {
		@Override
		public String getMsgCn() {
			return "命令类型没有找到";
		}
	},
	/**
	 * 401State
	 */
	IBS_401_STATE("401State", "Status type not found") {
		@Override
		public String getMsgCn() {
			return "状态类型没有找到";
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
	 * 403ManualBet
	 */
	IBS_403_BET_STATE("403BetState", "Bet status is not yet turned on") {
		@Override
		public String getMsgCn() {
			return "投注状态尚未开启";
		}
	},
	/**
	 * 403ExistMenu
	 */
	IBS_403_EXIST("403Exist", "Resources code already exists") {
		@Override
		public String getMsgCn() {
			return "资源已存在";
		}
	},
	/**
	 * 403ExistLogin
	 */
	IBS_403_EXIST_LOGIN("403ExistLogin", "This account has been logged in to this platform.") {
		@Override
		public String getMsgCn() {
			return "该账号已登录在本平台";
		}
	},
	/**
	 * 403MaxCapacity
	 */
	IBS_403_MAX_CAPACITY("403MaxCapacity", "Server capacity limit") {
		@Override
		public String getMsgCn() {
			return "服务器容量已达上限";
		}
	},
	/**
	 * 403Error
	 */
	IBS_403_ERROR("403Error", "Request fetch error") {
		@Override
		public String getMsgCn() {
			return "请求获取错误";
		}
	},
	/**
	 * 403Login
	 */
	IBS_403_LOGIN("403Login", "Customer not logged in") {
		@Override
		public String getMsgCn() {
			return "客户未登录";
		}
	},
	/**
	 * 403ClosingTime
	 */
	IBS_403_CLOSING_TIME("403ClosingTime", "Closing time") {
		@Override
		public String getMsgCn() {
			return "封盘时间";
		}
	},
	/**
	 * 403UsedQuota
	 */
	IBS_403_USED_QUOTA("403UsedQuota", "User balance is insufficient") {
		@Override
		public String getMsgCn() {
			return "用户余额不足";
		}
	},
	/**
	 * 403Handicap
	 */
	IBS_403_HANDICAP("403Handicap", "Handicap resource error") {
		@Override
		public String getMsgCn() {
			return "盘口资源错误";
		}
	},
	/**
	 * 403ExistLogin
	 */
	IBS_403_LOGOUT("403Logout", "This account logouting") {
		@Override
		public String getMsgCn() {
			return "该账号正在登出中,请稍后在登录";
		}
	},
	/**
	 * 403Permission
	 */
	IBS_403_PERMISSION("403Permission", "Insufficient permission level, request denied") {
		@Override
		public String getMsgCn() {
			return "权限等级不够，请求被拒绝";
		}
	},
	/**
	 * 403User
	 */
	IBS_403_USER("403User", "The user was not found") {
		@Override
		public String getMsgCn() {
			return "没有找到该用户";
		}
	},
	/**
	 * 403Mq
	 */
	IBS_403_MQ("403Mq", "Message queue failed to send") {
		@Override
		public String getMsgCn() {
			return "消息队列发送失败";
		}
	},
	/**
	 * 403heartbeatError
	 */
	IBS_403_HEARTBEAT_ERROR("403heartbeatError", "heartbeat error") {
		@Override
		public String getMsgCn() {
			return "心跳检测出错，请检测日志信息";
		}
	},
	/**
	 * 403heartbeatInterval
	 */
	IBS_403_HEARTBEAT_INTERVAL("403heartbeatInterval", "heartbeat interval less than one minute") {
		@Override
		public String getMsgCn() {
			return "上次心跳检测时间间隔小于一分钟";
		}
	},
	/**
	 * 403dataFormat
	 */
	IBS_403_DATA_FORMAT("403dataFormat", "data format erro") {
		@Override
		public String getMsgCn() {
			return "数据格式错误";
		}
	},
	/**
	 * 403DataError
	 */
	IBS_403_DATA_ERROR("403DataError", "Data error") {
		@Override
		public String getMsgCn() {
			return "数据错误";
		}
	},
	/**
	 * 403ClosingTime
	 */
	IBS_403_INSUFFICIENT_POINT("403InsufficientPoint", "Insufficient user point, please recharge point") {
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
	 * 404CustomerLogin
	 */
	IBS_404_CUSTOMER_LOGIN("404CustomerLogin", "Customer is not logged in") {
		@Override
		public String getMsgCn() {
			return "客户没有登录";
		}
	},
	/**
	 * 404VrNotManualBet
	 */
	IBS_404_VR_NOT_MANUAL_BET("404VrNotManualBet", "Vr not manual bet") {
		@Override
		public String getMsgCn() {
			return "虚拟投注暂未开放手动投注";
		}
	},
	/**
	 * 404HandicapGame
	 */
	IBS_404_HANDICAP_GAME("404HandicapGame", "Handicap game did not find") {
		@Override
		public String getMsgCn() {
			return "盘口游戏没有找到";
		}
	},
	/**
	 * 404Data
	 */
	IBS_404_DATA("404Data", "Data not found") {
		@Override
		public String getMsgCn() {
			return "数据没有找到";
		}
	},
	/**
	 * 404Exist
	 */
	IBS_404_EXIST("404Exist", "Data does not exist") {
		@Override
		public String getMsgCn() {
			return "数据不存在";
		}
	},
	/**
	 * 404Handicap
	 */
	IBS_404_HANDICAP("404Handicap", "Unrecognized handicap") {
		@Override
		public String getMsgCn() {
			return "未能识别的盘口";
		}
	},
	/**
	 * 404Game
	 */
	IBS_404_GAME("404Game", "Unrecognized game") {
		@Override
		public String getMsgCn() {
			return "未能识别的游戏";
		}
	},
	/**
	 * 404HandicapItem
	 */
	IBS_404_HANDICAP_ITEM("404HandicapItem", "Unrecognized handicap details") {
		@Override
		public String getMsgCn() {
			return "未能识别的盘口详情";
		}
	},
	/**
	 * 404HandicapMember
	 */
	IBS_404_HANDICAP_MEMBER("404HandicapMember", "Handicap member did not find") {
		@Override
		public String getMsgCn() {
			return "盘口会员没有找到";
		}
	},
	/**
	 * 404Data
	 */
	IBS_404_STATE("404State", "State not found") {
		@Override
		public String getMsgCn() {
			return "状态没有找到";
		}
	},
	/**
	 * 404Method
	 */
	IBS_404_METHOD("401Method", "Method type not found") {
		@Override
		public String getMsgCn() {
			return "没有找到方法类型";
		}
	},
	/**
	 * 404Method
	 */
	IBS_404_TYPE("404Type", "No capture type") {
		@Override
		public String getMsgCn() {
			return "没有捕捉到类型";
		}
	},
	/**
	 * 404Login
	 */
	IBS_404_LOGIN("404Login", "Customer is not logged in") {
		@Override
		public String getMsgCn() {
			return "客户未登录";
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
	IBS_503_TIME("503Time", "The server is currently unable to process the request, please try again after 7 o'clock") {
		@Override
		public String getMsgCn() {
			return "服务器当前无法处理请求，请七点以后再试";
		}
	},
	/**
	 * 500
	 */
	IBS_500("500", "Server error, please try again later") {
		@Override
		public String getMsgCn() {
			return "服务器出错，请稍候再试";
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
