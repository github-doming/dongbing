package com.ibm.old.v1.common.doming.enums;
/**
 * @Description: HttpClient执行结果消息枚举类
 * @Author: Dongming
 * @Date: 2018-12-08 18:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmHcCodeEnum {

	/**
	 * 200
	 */
	CODE_200("200", "Request success") {
		@Override public String getMsgCn() {
			return "请求成功" ;
		}
	},
	/**
	 * 403
	 */
	CODE_401("401", "Data validation error") {
		@Override public String getMsgCn() {
			return "数据验证错误" ;
		}
	},
	/**
	 * 403VerifyCode
	 */
	IBM_401_GAME("401Game", "The game has not yet opened") {
		@Override public String getMsgCn() {
			return "游戏尚未开通" ;
		}
	},
	/**
	 * 403
	 */
	CODE_403("403", "Data verification failed") {
		@Override public String getMsgCn() {
			return "数据验证失败" ;
		}
	},
	/**
	 * 403
	 */
	IBM_403_OTHER_PLACE_LOGIN("403OtherPlaceLogin", "other place login") {
		@Override public String getMsgCn() {
			return "您已經在其它地方登錄" ;
		}
	},
	/**
	 * 403
	 */
	IBM_403_NOT_LOGIN("403NotLogin", "not login,please again login") {
		@Override public String getMsgCn() {
			return "您尚未登入，請重新登入！" ;
		}
	},
	/**
	 * 403VerifyCode
	 */
	IBM_403_CHECK_CODE("403CheckCode", "http request check failed") {
		@Override public String getMsgCn() {
			return "http请求校验失败" ;
		}
	},
	/**
	 * 403VerifyCode
	 */
	IBM_403_VERIFY_CODE("403VerifyCode", "Verification code verification failed") {
		@Override public String getMsgCn() {
			return "验证码验证失败" ;
		}
	},
	/**
	 * 403LoginOften
	 */
	IBM_403_LOGIN_OFTEN("403LoginOften", "Sign in frequently, please try again later") {
		@Override public String getMsgCn() {
			return "登录频繁，请稍后再试" ;
		}
	},
	/**
	 * 403UserAccount
	 */
	IBM_403_USER_ACCOUNT("403UserAccount", "Username or password is incorrect") {
		@Override public String getMsgCn() {
			return "用户名或密码不正确" ;
		}
	},
	/**
	 * 403UserAccount
	 */
	IBM_403_TIMING_CHECKOUT("403TimingCheckout", "Timing checkout fail") {
		@Override public String getMsgCn() {
			return "定时检验失败" ;
		}
	},
	/**
	 * 403Request
	 */
	IBM_403_REQUEST("403Request", "Request error") {
		@Override public String getMsgCn() {
			return "请求错误" ;
		}
	},
	/**
	 * 403ClientBlocked
	 */
	IBM_403_CLIENT_BLOCKED("403ClientBlocked", "The client server is blocked") {
		@Override public String getMsgCn() {
			return "客户端服务器被屏蔽" ;
		}
	},
	/**
	 * 403ChangePassword
	 */
	IBM_403_CHANGE_PASSWORD("403ChangePassword", "Need to change the password") {
		@Override public String getMsgCn() {
			return "需要修改密码" ;
		}
	},
	/**
	 * 403UserState
	 */
	IBM_403_USER_STATE("403UserState", "User has been disabled") {
		@Override public String getMsgCn() {
			return "用户已停用" ;
		}
	},
	/**
	 * 403UserBanBet
	 */
	IBM_403_USER_BAN_BET("403UserBanBet", "User has banned betting") {
		@Override public String getMsgCn() {
			return "用户已停押" ;
		}
	},
	/**
	 * 403UserState
	 */
	IBM_403_USER_TEMPORARY_STOP_STATE("403TemporaryStopState", "The user has been temporarily disabled") {
		@Override public String getMsgCn() {
			return "用户临时停用" ;
		}
	},
	/**
	 * 403Unknown
	 */
	IBM_403_UNKNOWN("403Unknown", "Unknown reason for failure") {
		@Override public String getMsgCn() {
			return "未知的失败原因" ;
		}
	},
	/**
	 * 403LoginFail
	 */
	IBM_403_LOGIN_FAIL("403LoginFail", "Login fail") {
		@Override public String getMsgCn() {
			return "登录失败" ;
		}
	},
	/**
	 * 403SealHandicap
	 */
	IBM_403_SEAL_HANDICAP("403SealHandicap", "The game has been closed") {
		@Override public String getMsgCn() {
			return "游戏已封盘" ;
		}
	},
	/**
	 * 403PointNotEnough
	 */
	IBM_403_POINT_NOT_ENOUGH("403PointNotEnough", "TInsufficient points") {
		@Override public String getMsgCn() {
			return "积分不足" ;
		}
	},
	/**
	 * 403PointNotEnough
	 */
	IBM_403_POINT_LESS("403PointLess", "Less points") {
		@Override public String getMsgCn() {
			return "积分小于限额" ;
		}
	},
	/**
	 * 403MoreThanLimit
	 */
	IBM_403_MORE_THAN_LIMIT("403MoreThanLimit", "More than limit") {
		@Override public String getMsgCn() {
			return "超过限额" ;
		}
	},
	/**
	 * 403BetFail
	 */
	IBM_403_BET_FAIL("403BetFail", "bet fail") {
		@Override public String getMsgCn() {
			return "投注失败" ;
		}
	},/**
	 * 403BetFail
	 */
	IBM_403_BET_FAIL_TOKEN("403BetFail", "bet fail no token") {
		@Override public String getMsgCn() {
			return "没有token投注失败" ;
		}
	},
	/**
	 * 403BetFail
	 */
	IBM_403_PAGE_FAIL("403PageFail", "Get result data page error") {
		@Override public String getMsgCn() {
			return "获取结果数据页面错误" ;
		}
	},
	/**
	 * 403Period
	 */
	IBM_403_PERIOD("403Period", "Number of periods does not match") {
		@Override public String getMsgCn() {
			return "期数不匹配" ;
		}
	},
	/**
	 * 403ResultPageIsNull
	 */
	IBM_403_CLIENT_IP("403ClientIp", "Client IP is blocked") {
		@Override public String getMsgCn() {
			return "客户端IP被封" ;
		}
	},
	/**
	 * 404
	 */
	CODE_404("404", "Data not found") {
		@Override public String getMsgCn() {
			return "数据没有找到" ;
		}
	},
	/**
	 * 404Data
	 */
	IBM_404_DATA("404Data", "Data not found") {
		@Override public String getMsgCn() {
			return "数据没有找到" ;
		}
	},
	/**
	 * 404Data
	 */
	IBM_404_HANDICAP_URL_ERROR("404handicapUrlError", "Handicap url error") {
		@Override public String getMsgCn() {
			return "盘口网址错误" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_USER_INFO("404UserInfoNotFind", "User info not found") {
		@Override public String getMsgCn() {
			return "用户信息没有找到" ;
		}
	},
	/**
	 * 404UserCredit
	 */
	IBM_404_USER_CREDIT("404UserCredit", "User balance is insufficient") {
		@Override public String getMsgCn() {
			return "用户余额不足" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_PAGE_NAVIGATE("404PageNavigateNotFind", "Navigation page not found") {
		@Override public String getMsgCn() {
			return "网址验证码识别错误，请重新输入" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_PAGE_ROUTE("404PageRouteNotFind", "Route page not found") {
		@Override public String getMsgCn() {
			return "线路页面没有找到，请重试" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_PAGE_LOGIN("404PageLoginNotFind", "Login page not found") {
		@Override public String getMsgCn() {
			return "登陆页面没有找到，请重试" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_PAGE_LOGIN_INFO("404PageLoginInfoNotFind", "Login Info page not found") {
		@Override public String getMsgCn() {
			return "登陆信息页面没有找到" ;
		}
	},
	/**
	 * 404PageDrawInfo
	 */
	IBM_404_PAGE_DRAW_INFO("404PageDrawInfo", "Closing information page not found") {
		@Override public String getMsgCn() {
			return "封盘信息页面没有找到" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_BET_INFO("404BetInfo", "Game bet info page not found") {
		@Override public String getMsgCn() {
			return "游戏投注信息界面没有找到" ;
		}
	},
	/**
	 * 404DataNotFind
	 */
	IBM_404_BET_LIMIT("404BetLimit", "Game bet info page not found") {
		@Override public String getMsgCn() {
			return "游戏限额信息界面没有找到" ;
		}
	},
	/**
	 * 404ExistInfo
	 */
	IBM_404_EXIST_INFO("404ExistInfo", "The presence information not found") {
		@Override public String getMsgCn() {
			return "存在信息没有找到" ;
		}
	},
	/**
	 * 404ProfitInfo
	 */
	IBM_404_PROFIT_INFO("404ProfitInfo", "Profit information not found") {
		@Override public String getMsgCn() {
			return "盈利信息没有找到" ;
		}
	},
	/**
	 * 404ProfitInfo
	 */
	IBM_404_RESULT_INFO("404ResultInfo", "The return message not found") {
		@Override public String getMsgCn() {
			return "回传信息没有找到" ;
		}
	},
	/**
	 * 403VerifyCode
	 */
	IBM_404_CHECK_CODE("404CheckCode", "http request check null") {
		@Override public String getMsgCn() {
			return "http请求校验为空" ;
		}
	},
	/**
	 * 404ResultPage
	 */
	IBM_404_RESULT_PAGE("404ResultPage", "Billing page is null") {
		@Override public String getMsgCn() {
			return "结算页面为空" ;
		}
	},
	/**
	 * 500
	 */
	CODE_500("500", "Request fail") {
		@Override public String getMsgCn() {
			return "请求失败" ;
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
	IbmHcCodeEnum(String code, String msg) {
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
