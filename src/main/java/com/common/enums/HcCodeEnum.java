package com.common.enums;

/**
 * @Description: HttpClient执行结果消息枚举类
 * @Author: zjj
 * @Date: 2020-07-10 15:34
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public enum HcCodeEnum {
	/**
	 * 403
	 */
	CODE_403("403", "Data verification failed") {
		@Override public String getMsgCn() {
			return "数据验证失败";
		}
	},
	/**
	 * 403PageNavigate
	 */
	IBS_403_PAGE_NAVIGATE("403PageNavigate",
			"URL or verification code verification error, please verify and re-enter!") {
		@Override public String getMsgCn() {
			return "网址或验证码校验错误，请校验后重新输入!";
		}
	},
	/**
	 * 403LoginFail
	 */
	IBS_403_LOGIN_FAIL("403LoginFail", "Login fail") {
		@Override public String getMsgCn() {
			return "登录失败";
		}
	},
	/**
	 * 403UserAccount
	 */
	IBS_403_USER_ACCOUNT("403UserAccount", "Username or password is incorrect") {
		@Override public String getMsgCn() {
			return "用户名或密码不正确";
		}
	},
	/**
	 * 403UserState
	 */
	IBS_403_USER_STATE("403UserState", "User has been disabled") {
		@Override public String getMsgCn() {
			return "用户已停用";
		}
	},
	/**
	 * 403LoginOften
	 */
	IBS_403_LOGIN_OFTEN("403LoginOften", "Sign in frequently, please try again later") {
		@Override public String getMsgCn() {
			return "登录频繁，请稍后再试";
		}
	},
	/**
	 * 403VerifyCode
	 */
	IBS_403_VERIFY_CODE("403VerifyCode", "Verification code verification failed") {
		@Override public String getMsgCn() {
			return "验证码验证失败";
		}
	},
	/**
	 * 403ChangePassword
	 */
	IBS_403_CHANGE_PASSWORD("403ChangePassword", "Need to change the password") {
		@Override public String getMsgCn() {
			return "需要修改密码";
		}
	},
	/**
	 * 403PasswordExpired
	 */
	IBS_403_PASSWORD_EXPIRED("403PasswordExpired", "Need to change the password") {
		@Override public String getMsgCn() {
			return "抱歉。您的密码已超过使用期限。为安全起见，请重新设定";
		}
	},
	/**
	 * 403Unknown
	 */
	IBS_403_UNKNOWN("403Unknown", "Unknown reason for failure") {
		@Override public String getMsgCn() {
			return "未知的失败原因";
		}
	},
	/**
	 * 403SealHandicap
	 */
	IBS_403_SEAL_HANDICAP("403SealHandicap", "The game has been closed") {
		@Override public String getMsgCn() {
			return "游戏已封盘";
		}
	},
	/**
	 * 403BetFail
	 */
	IBS_403_BET_FAIL("403BetFail", "bet fail") {
		@Override public String getMsgCn() {
			return "投注失败";
		}
	},
	/**
	 * 403HandicapUpdate
	 */
	IBS_403_HANDICAP_UPDATE("403HandicapUpdate", "Handicap update") {
		@Override public String getMsgCn() {
			return "盘口维护中";
		}
	},
	/**
	 * 403MoreThanLimit
	 */
	IBS_403_MORE_THAN_LIMIT("403MoreThanLimit", "More than limit") {
		@Override public String getMsgCn() {
			return "超过限额";
		}
	},
	/**
	 * 404ResultPage
	 */
	IBS_404_RESULT_PAGE("404ResultPage", "Billing page is null") {
		@Override public String getMsgCn() {
			return "结算页面为空" ;
		}
	},
	/**
	 * 403_STOP_BET
	 */
	IBS_403_STOP_BET("403_STOP_BET", "User has been suspended") {
		@Override public String getMsgCn() {
			return "用户已停押";
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBS_404_RULE_ERROR("404RuleError", "Game bet rule error") {
		@Override public String getMsgCn() {
			return "下注規則有誤" ;
		}
	},
	/**
	 * 403PointNotEnough
	 */
	IBS_403_POINT_LESS("403PointLess", "Less points") {
		@Override public String getMsgCn() {
			return "积分小于限额" ;
		}
	},
	/**
	 * 403
	 */
	IBS_403_OTHER_PLACE_LOGIN("403OtherPlaceLogin", "other place login") {
		@Override public String getMsgCn() {
			return "您已經在其它地方登錄" ;
		}
	},
	/**
	 * 403PointNotEnough
	 */
	IBS_403_POINT_NOT_ENOUGH("403PointNotEnough", "TInsufficient points") {
		@Override public String getMsgCn() {
			return "积分不足" ;
		}
	},

	/**
	 * 404DataNotFind
	 */
	IBS_404_PAGE_LOGIN_INFO("404PageLoginInfoNotFind", "Login Info page not found") {
		@Override public String getMsgCn() {
			return "登陆信息页面没有找到" ;
		}
	},

	/**
	 * 404Data
	 */
	IBS_404_DATA("404Data", "Data not found") {
		@Override public String getMsgCn() {
			return "数据没有找到" ;
		}
	},
	/**
	 * 403UserBanBet
	 */
	IBS_403_USER_BAN_BET("403UserBanBet", "User has banned betting") {
		@Override public String getMsgCn() {
			return "用户已停押" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBS_404_PAGE_LOGIN("404PageLoginNotFind", "Login page not found") {
		@Override public String getMsgCn() {
			return "登陆页面没有找到，请重试" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBS_404_USER_INFO("404UserInfoNotFind", "User info not found") {
		@Override public String getMsgCn() {
			return "用户信息没有找到" ;
		}
	},

	/**
	 * 404DataNotFind
	 */
	IBS_404_BET_LIMIT("404BetLimit", "Game bet info page not found") {
		@Override public String getMsgCn() {
			return "游戏限额信息界面没有找到" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBS_404_PAGE_ROUTE("404PageRouteNotFind", "Route page not found") {
		@Override public String getMsgCn() {
			return "线路页面没有找到，请重试" ;
		}
	},

	/**
	 * 404DataNotFind
	 */
	IBS_404_BET_INFO("404BetInfo", "Game bet info page not found") {
		@Override public String getMsgCn() {
			return "游戏投注信息界面没有找到" ;
		}
	},
	/**
	 * 404
	 */
	CODE_404("404", "Data not found") {
		@Override public String getMsgCn() {
			return "数据没有找到";
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBS_404_INDEX_INFO("404IndexPageNotFind", "index page not found") {
		@Override public String getMsgCn() {
			return "盘口主页面没有找到" ;
		}
	},
	/**
	 * 404SettlementPage
	 */
	IBS_404_SETTLEMENT_PAGE("404SettlementPage", "Billing page is null") {
		@Override public String getMsgCn() {
			return "结算页面为空";
		}
	},
	/**
	 * 404ExistInfo
	 */
	IBS_404_EXIST_INFO("404ExistInfo", "The presence information not found") {
		@Override public String getMsgCn() {
			return "存在信息没有找到" ;
		}
	},
	/**
	 * 404Crawler
	 */
	IBS_404_CRAWLER("404Crawler", "Crawler information is empty") {
		@Override public String getMsgCn() {
			return "爬虫信息为空";
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
	HcCodeEnum(String code, String msg) {
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
