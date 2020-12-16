package com.ibm.common.enums;

/**
 * @Description: ibm 返回字段说明
 * @Author: Dongming
 * @Date: 2019-06-19 13:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmCodeEnum {

	/**
	 * 200
	 */
	CODE_200("200", "Request success") {
		@Override
		public String getMsgCn() {
			return "请求成功";
		}
	},
	/**
	 * 200
	 */
	IBM_200("200", "Request success") {
		@Override
		public String getMsgCn() {
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
	 * 200
	 */
	IBM_202("200", "Request Accepted") {
		@Override
		public String getMsgCn() {
			return "请求处理中";
		}
	},
	/**
	 * 202
	 */
	IBM_202_EVENT("202Event", "Event processing") {
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
	 * 401NotFindData
	 */
	IBM_401_DATA("401Data", "No data found") {
		@Override
		public String getMsgCn() {
			return "没有找到数据";
		}
	},
	/**
	 * 401NotFindEventId
	 */
	IBM_401_EVENT_ID("401EventId", "Event id not found") {
		@Override
		public String getMsgCn() {
			return "事件id没有找到";
		}
	},
	/**
	 * 401Method
	 */
	IBM_401_METHOD("401Method", "Method type not found") {
		@Override
		public String getMsgCn() {
			return "方法类型没有找到";
		}
	},
	/**
	 * 401State
	 */
	IBM_401_STATE("401State", "Status type not found") {
		@Override
		public String getMsgCn() {
			return "状态类型没有找到";
		}
	},
	/**
	 * 401Cmd
	 */
	IBM_401_CMD("401Cmd", "Command type not found") {
		@Override
		public String getMsgCn() {
			return "命令类型没有找到";
		}
	},
	/**
	 * 401NotFindMessage
	 */
	IBM_401_MESSAGE("401NotFindMessage", "Communication content is empty") {
		@Override
		public String getMsgCn() {
			return "通讯内容为空";
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
     * 403MaxCapacity
     */
    IBM_403_MAX_CAPACITY("403MaxCapacity", "Server capacity limit") {
        @Override
        public String getMsgCn() {
            return "服务器容量已达上限";
        }
    },
	/**
	 * 403User
	 */
	IBM_403_USER("401User", "The user was not found") {
				@Override
				public String getMsgCn() {
					return "没有找到该用户";
				}
			},
	/**
	 * 403heartbeatError
	 */
	IBM_403_HEARTBEAT_ERROR("403heartbeatError", "heartbeat error") {
		@Override
		public String getMsgCn() {
			return "心跳检测出错，请检测日志信息";
		}
	},
    /**
     * 403heartbeatInterval
     */
    IBM_403_HEARTBEAT_INTERVAL("403heartbeatInterval", "heartbeat interval less than one minute") {
        @Override
        public String getMsgCn() {
            return "上次心跳检测时间间隔小于一分钟";
        }
    },
	/**
	 * 403MaxHandicapCapacity
	 */
	IBM_403_MAX_HANDICAP_CAPACITY("403MaxHandicapCapacity", "Server disk capacity reaches the upper limit") {
		@Override
		public String getMsgCn() {
			return "盘口容量已达上限,请联系代理扩容";
		}
	},
	/**
	 * 403DataError
	 */
	IBM_403_DATA_ERROR("403DataError", "Data error") {
		@Override
		public String getMsgCn() {
			return "数据错误";
		}
	},
	/**
	 * 403DataError
	 */
	IBM_403_MQ("403Mq", "Message queue failed to send") {
		@Override
		public String getMsgCn() {
			return "消息队列发送失败";
		}
	},
	/**
	 * 403DataError
	 */
	IBM_403_HTTP("403Http", "http failed") {
		@Override
		public String getMsgCn() {
			return "请求发送失败";
		}
	},
	/**
	 * 403PasswordNotDifferent
	 */
	IBM_403_PASSWORD_NOT_DIFFERENT("403PasswordNotDifferent", "The new password is the same as the old password") {
		@Override
		public String getMsgCn() {
			return "新密码与旧密码相同";
		}
	},
	/**
	 * 403Password
	 */
	IBM_403_PASSWORD_IS_INCORRECT("403PasswordIsIncorrect", "The ole password is incorrect") {
		@Override
		public String getMsgCn() {
			return "旧密码不正确";
		}
	},
	/**
	 * 403PermissionDenied
	 */
	IBM_403_MAX_USER_CAPACITY("i403MaxUserCapacity", "User online capacity has reached the upper limit") {
		@Override
		public String getMsgCn() {
			return "用户在线容量已达上限";
		}
	},
	/**
	 * 403ExistCode
	 */
	IBM_403_EXIST_CODE("403ExistCode", "The server code already exists") {
		@Override
		public String getMsgCn() {
			return "服务者编码已存在";
		}
	},
	/**
	 * 403ExistLogin
	 */
	IBM_403_EXIST_LOGIN("403ExistLogin", "This account has been logged in to this platform.") {
		@Override
		public String getMsgCn() {
			return "该账号已登录在本平台";
		}
	},
    /**
     * 403ExistLogin
     */
    IBM_403_LOGOUT("403Logout", "This account logouting") {
        @Override
        public String getMsgCn() {
            return "该账号正在登出中,请稍后在登录";
        }
    },
	/**
	 * 403PermissionDenied
	 */
	IBM_403_EXIST_HANDICAP("403ExistHandicap", "Handicap code already exists") {
		@Override
		public String getMsgCn() {
			return "盘口已存在";
		}
	},
	/**
	 * 403ExistMenu
	 */
	IBM_403_EXIST("403Exist", "Resources code already exists") {
		@Override
		public String getMsgCn() {
			return "资源已存在";
		}
	},
	/**
	 * 403Register
	 */
	IBM_403_REGISTER("403Register", "Registration rejected") {
		@Override
		public String getMsgCn() {
			return "注册被拒绝";
		}
	},
	/**
	 * 403PermissionDenied
	 */
	IBM_403_LOGIN("403Login", "Customer not logged in") {
		@Override
		public String getMsgCn() {
			return "客户未登录";
		}
	},
	/**
	 * 403ClosingTime
	 */
	IBM_403_CLOSING_TIME("403ClosingTime", "Closing time") {
		@Override
		public String getMsgCn() {
			return "封盘时间";
		}
	},
	/**
	 * 403ClosingTime
	 */
	IBM_403_INSUFFICIENT_TIME("403InsufficientTime", "Insufficient user time, please recharge time") {
		@Override
		public String getMsgCn() {
			return "用户时长不足，请充值时长";
		}
	},
	/**
	 * 403ClosingTime
	 */
	IBM_403_INSUFFICIENT_POINT("403InsufficientPoint", "Insufficient user point, please recharge point") {
		@Override
		public String getMsgCn() {
			return "用户积分不足，请充值";
		}
	},
	/**
	 * 403ClosingTime
	 */
	IBM_403_RESTRICT_ACCESS("403RestrictAccess", "Requests are too frequent, please try again later") {
		@Override
		public String getMsgCn() {
			return "请求过于频繁，请稍后再试";
		}
	},
	/**
	 * 403ManualBet
	 */
	IBM_403_MANUAL_BET("403ManualBet", "Manual bet failed") {
		@Override
		public String getMsgCn() {
			return "手动投注失败";
		}
	},
	/**
	 * 403ManualBet
	 */
	IBM_403_USED_QUOTA("403UsedQuota", "User balance is insufficient") {
		@Override
		public String getMsgCn() {
			return "用户余额不足";
		}
	},
	/**
	 * 403ManualBet
	 */
	IBM_403_BET_STATE("403BetState", "Bet status is not yet turned on") {
		@Override
		public String getMsgCn() {
			return "投注状态尚未开启";
		}
	},
	/**
	 * 403Permission
	 */
	IBM_403_PERMISSION("403Permission", "Insufficient permission level, request denied") {
		@Override
		public String getMsgCn() {
			return "权限等级不够，请求被拒绝";
		}
	},
	/**
	 * 403ManualBet
	 */
	IBM_403_USER_ADD("403UserAdd", "Cannot create agent / shareholder level account") {
		@Override
		public String getMsgCn() {
			return "不能越级创建代理/股东级别账号";
		}
	},
	/**
	 * 403ManualBet
	 */
	IBM_403_CARD_PASSWORD("403CardPassword", "Card Password verification error") {
		@Override
		public String getMsgCn() {
			return "充值卡验证错误";
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
	 * 404HandicapCode
	 */
	IBM_404_HANDICAP_CODE("404HandicapCode", "Handicap code does not exist") {
		@Override
		public String getMsgCn() {
			return "盘口编码不存在";
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_DATA("404Data", "Data not found") {
		@Override
		public String getMsgCn() {
			return "数据没有找到";
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_PERIOD_ERROR("404PeriodError", "period error") {
		@Override
		public String getMsgCn() {
			return "指定期数为非交易状态";
		}
	},
	/**
	 * 404Game
	 */
	IBM_404_GAME("404Game", "Did not find the game") {
		@Override
		public String getMsgCn() {
			return "没有找到游戏";
		}
	},
	/**
	 * 404VrNotManualBet
	 */
	IBM_404_VR_NOT_MANUAL_BET("404VrNotManualBet", "Vr not manual bet") {
		@Override
		public String getMsgCn() {
			return "虚拟投注暂未开放手动投注";
		}
	},
	/**
	 * 404Method
	 */
	IBM_404_METHOD("404Method", "No capture method") {
		@Override
		public String getMsgCn() {
			return "没有捕捉到方法";
		}
	},
	/**
	 * 404Method
	 */
	IBM_404_TYPE("404Type", "No capture type") {
		@Override
		public String getMsgCn() {
			return "没有捕捉到类型";
		}
	},
	/**
	 * 404HandicapItem
	 */
	IBM_404_HANDICAP_ITEM("404HandicapItem", "Unrecognized handicap details") {
		@Override
		public String getMsgCn() {
			return "未能识别的盘口详情";
		}
	},
	/**
	 * 404Handicap
	 */
	IBM_404_HANDICAP("404Handicap", "Handicap did not find") {
		@Override
		public String getMsgCn() {
			return "盘口没有找到";
		}
	},
	/**
	 * 404HandicapMember
	 */
	IBM_404_HANDICAP_MEMBER("404HandicapMember", "Handicap member did not find") {
		@Override
		public String getMsgCn() {
			return "盘口会员没有找到";
		}
	},
	/**
	 * 404HandicapAgent
	 */
	IBM_404_HANDICAP_AGENT("404HandicapAgent", "Handicap agent did not find") {
		@Override
		public String getMsgCn() {
			return "盘口代理没有找到";
		}
	},
	/**
	 * 404HandicapGame
	 */
	IBM_404_HANDICAP_GAME("404HandicapGame", "Handicap game did not find") {
		@Override
		public String getMsgCn() {
			return "盘口游戏没有找到";
		}
	},
	/**
	 * 404CustomerLogin
	 */
	IBM_404_CUSTOMER_LOGIN("404CustomerLogin", "Customer is not logged in") {
		@Override
		public String getMsgCn() {
			return "客户没有登录";
		}
	},
	/**
	 * 404ExistId
	 */
	IBM_404_EXIST_ID("404ExistId", "The client has a primary key that does not exist in the client.") {
		@Override
		public String getMsgCn() {
			return "客户端存在主键不存在于客户端中";
		}
	},
	/**
	 * 404Exist
	 */
	IBM_404_EXIST("404Exist", "Data does not exist") {
		@Override
		public String getMsgCn() {
			return "数据不存在";
		}
	},
	/**
	 * 404Exist
	 */
	IBM_404_CARD_ADMIN("404CardAdmin", "Card user role can be modified") {
		@Override
		public String getMsgCn() {
			return "开卡用户角色无法修改";
		}
	},
	/**
	 * 503
	 */
	IBM_503_SERVER_RENEW("503ServerRnew", "Server renew,please try after seven") {
		@Override
		public String getMsgCn() {
			return "服务器更新时间，请七点之后再试";
		}
	},
	/**
	 * 503
	 */
	CODE_503("503ServerRnew", "Server renew,please try after seven") {
		@Override
		public String getMsgCn() {
			return "服务器更新时间，请七点之后再试";
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
	IBM_500("500", "Server error, please try again later") {
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
	IbmCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return code;
	}

	/**
	 * 获取中文消息
	 *
	 * @return 中文消息
	 */
	public abstract String getMsgCn();
}
