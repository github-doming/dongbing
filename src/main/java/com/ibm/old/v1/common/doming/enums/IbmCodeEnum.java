package com.ibm.old.v1.common.doming.enums;
/**
 * @Description: ibm state字段说明
 * @Author: Dongming
 * @Date: 2018-10-20 09:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmCodeEnum {

	/**
	 * 200
	 */
	CODE_200("200", "Request success") {
		@Override public String getMsgCn() {
			return "请求成功" ;
		}
	},
	/**
	 * 200
	 */
	IBM_200("200", "Request success") {
		@Override public String getMsgCn() {
			return "请求成功" ;
		}
	},
	/**
	 * 206
	 */
	CODE_202("202", "Request processing") {
		@Override public String getMsgCn() {
			return "请求处理中" ;
		}
	},
	/**
	 * 206
	 */
	IBM_202("202", "Request processing") {
		@Override public String getMsgCn() {
			return "请求处理中" ;
		}
	},
	/**
	 * 401
	 */
	CODE_401("401", "Data verification failed") {
		@Override public String getMsgCn() {
			return "数据验证失败" ;
		}
	},
	/**
	 * 401BetStateFalse
	 */
	Ibm_401_BET_STATE_FALSE("401BetStateFalse", "No bet state false") {
		@Override public String getMsgCn() {
			return "投注状态为false" ;
		}
	},
	/**
	 * 401NotFindData
	 */
	IBM_401_NOT_FIND_DATA("401NotFindData", "No data found") {
		@Override public String getMsgCn() {
			return "没有找到数据" ;
		}
	},
	/**
	 * 401NotFindMessage
	 */
	IBM_401_NOT_FIND_MESSAGE("401NotFindMessage", "Communication content is empty") {
		@Override public String getMsgCn() {
			return "通讯内容为空" ;
		}
	},
	/**
	 * 401NotFindHandicapMemberId
	 */
	IBM_401_NOT_FIND_HANDICAP_MEMBER_ID("401NotFindHandicapMemberId", "No handicap member id found") {
		@Override public String getMsgCn() {
			return "盘口会员id为空" ;
		}
	},
	/**
	 * 401NotFindHandicapCode
	 */
	IBM_401_NOT_FIND_HANDICAP_CODE("401NotFindHandicapCode", "No handicap code found") {
		@Override public String getMsgCn() {
			return "盘口code为空" ;
		}
	},
	/**
	 * 401NotFindHandicapId
	 */
	IBM_401_NOT_FIND_HANDICAP_ID("401NotFindHandicapId", "No handicap ID found") {
		@Override public String getMsgCn() {
			return "盘口ID为空" ;
		}
	},
	/**
	 * 401NotFindMethod
	 */
	IBM_401_NOT_FIND_METHOD("401NotFindMethod", "No method found") {
		@Override public String getMsgCn() {
			return "执行方法为空" ;
		}
	},
	/**
	 * 401NotFindMethod
	 */
	IBM_401_NOT_FIND_STATE("401NotFindState", "No state found") {
		@Override public String getMsgCn() {
			return "执行状态码错误" ;
		}
	},
	/**
	 * 401NotFindUserId
	 */
	IBM_401_NOT_FIND_USER_ID("401NotFindUserId", "No userId code found") {
		@Override public String getMsgCn() {
			return "用户id为空" ;
		}
	},
	/**
	 * 401NotFindUser
	 */
	IBM_401_NOT_FIND_USER("401NotFindUser", "No user found") {
		@Override public String getMsgCn() {
			return "用户不存在" ;
		}
	},
	/**
	 * 401NotFindMessageReceiptId
	 */
	IBM_401_NOT_FIND_MESSAGE_RECEIPT_ID("401NotFindMessageReceiptId", "No message receipt id found") {
		@Override public String getMsgCn() {
			return "消息回执id为空" ;
		}
	},
	/**
	 * 401NotBetMode
	 */
	IBM_401_NOT_BET_MODE("401NotBetMode", "Non-existent betting mode") {
		@Override public String getMsgCn() {
			return "不存在的投注模式" ;
		}
	},
	/**
	 * 401NotFundSwapMode
	 */
	IBM_401_NOT_FUND_SWAP_MODE("401NotFundSwapMode", "Non-existent fund swap mode") {
		@Override public String getMsgCn() {
			return "不存在的资金切换模式" ;
		}
	},
	/**
	 * 401NotFundPeriodRoll
	 */
	IBM_401_NOT_FUND_PERIOD_ROLL("401NotFundPeriodRoll", "Non-existent period roll mode") {
		@Override public String getMsgCn() {
			return "不存在的期期滚模式" ;
		}
	},
	/**
	 * 401NotFundPeriodRoll
	 */
	IBM_401_NOT_STATE("401NotState", "Status code that does not exist") {
		@Override public String getMsgCn() {
			return "不存在的状态码" ;
		}
	},
	/**
	 * 401NotFindClientExistHmId
	 */
	IBM_401_NOT_FIND_CLIENT_HM_EXIST_ID("401NotFindClientExistHmId",
			"Exist client handicap member id that does not exist") {
		@Override public String getMsgCn() {
			return "不存在的存在客户端盘口会员id" ;
		}
	},
	/**
	 * 403
	 */
	CODE_403("403", "Resource not available") {
		@Override public String getMsgCn() {
			return "资源不可用" ;
		}
	},
	/**
	 * 403DataRsa
	 */
	IBM_403_DATA_RSA("403DataRsa", "Data is not encrypted") {
		@Override public String getMsgCn() {
			return "data没有加密" ;
		}
	},
	/**
	 * 403DataDecodeFail
	 */
	IBM_403_DATA_DECODE_FAIL("403DataDecodeFail", "Data decoding failed") {
		@Override public String getMsgCn() {
			return "data解码失败" ;
		}
	},
	/**
	 * 403MaxCapacity
	 */
	IBM_403_MAX_CAPACITY("403MaxCapacity", "Server capacity limit") {
		@Override public String getMsgCn() {
			return "服务器容量已达上限" ;
		}
	},
	/**
	 * 403MaxHandicapCapacity
	 */
	IBM_403_MAX_HANDICAP_CAPACITY("403MaxHandicapCapacity", "Server disk capacity reaches the upper limit") {
		@Override public String getMsgCn() {
			return "盘口容量已达上限,请联系代理扩容" ;
		}
	},
	/**
	 * 403MaxCapacity
	 */
	IBM_403_ALREADY_EXISTS("403AlreadyExists", "Data already exists") {
		@Override public String getMsgCn() {
			return "数据已存在" ;
		}
	},
	/**
	 * 403MemberLogged
	 */
	IBM_403_MEMBER_LOGGED_IN("403MemberLogged", "User has logged in") {
		@Override public String getMsgCn() {
			return "用户已登录" ;
		}
	},
	/**
	 * 403DataError
	 */
	IBM_403_DATA_ERROR("403DataError", "Data error") {
		@Override public String getMsgCn() {
			return "数据错误" ;
		}
	},
	/**
	 * 403BetError
	 */
	IBM_403_BET_ERROR("403BetError", "Bet error") {
		@Override public String getMsgCn() {
			return "投注错误" ;
		}
	},
	/**
	 * 401DataError
	 */
	IBM_403_TIME_CLOSE("403TimeClose", "Closing time has arrived") {
		@Override public String getMsgCn() {
			return "封盘时间已到" ;
		}
	},
	/**
	 * 403NotInDrawTime
	 */
	IBM_403_NOT_IN_DRAW_TIME("403NotInDrawTime", "Not yet in the draw time") {
		@Override public String getMsgCn() {
			return "未到开奖时间" ;
		}
	},
	/**
	 * 403Update
	 */
	IBM_403_UPDATE("403Update", "Update failed, please try again later") {
		@Override public String getMsgCn() {
			return "更新失败，请稍后再试" ;
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
	 * 404DataNotFind
	 */
	IBM_404_DATA_NOT_FIND("404DataNotFind", "Data not found") {
		@Override public String getMsgCn() {
			return "数据没有找到" ;
		}
	},
	/**
	 * 404NotFindUser
	 */
	IBM_404_NOT_FIND_USER("404NotFindUser", "No user found") {
		@Override public String getMsgCn() {
			return "没有找到用户" ;
		}
	},
	/**
	 * 404NotFindPre
	 */
	IBM_404_NOT_CAPTURE_METHOD("404NotCaptureMethod", "No capture method") {
		@Override public String getMsgCn() {
			return "没有捕捉到方法" ;
		}
	},
	/**
	 * 404NotFindPre
	 */
	IBM_404_MQ_CONNECTION_ERROR("404MqConnectionError", "MQ Connection error") {
		@Override public String getMsgCn() {
			return "mq连接失败" ;
		}
	},
	/**
	 * 404NotFindPre
	 */
	IBM_404_MQ_SEND_FAIL("404MqSendFail", "MQ Send Fail") {
		@Override public String getMsgCn() {
			return "消息发送失败" ;
		}
	},
	/**
	 * 404HandicapCode
	 */
	IBM_404_HANDICAP_USER("404HandicapUser", "Handicap user does not exist") {
		@Override public String getMsgCn() {
			return "用户不存在该盘口" ;
		}
	},
	/**
	 * 401NotFindHandicapCode
	 */
	IBM_404_HANDICAP_MEMBER("401NotFindHandicapMember", "No handicap member found") {
		@Override public String getMsgCn() {
			return "盘口会员不存在" ;
		}
	},
	/**
	 * 401NotFindUserId
	 */
	IBM_404_HANDICAP_MEMBER_LOGIN("404HandicapMemberLogin", "handicap member login account is empty") {
		@Override public String getMsgCn() {
			return "盘口会员登录数量为空" ;
		}
	},
	/**
	 * 404HandicapCode
	 */
	IBM_404_HANDICAP_CODE("404HandicapCode", "Handicap code does not exist") {
		@Override public String getMsgCn() {
			return "盘口编码不存在" ;
		}
	},
	/**
	 * 404HandicapGame
	 */
	IBM_404_HANDICAP_GAME("404HandicapGame", "Handicap game does not exist") {
		@Override public String getMsgCn() {
			return "盘口游戏不存在" ;
		}
	},
	/**
	 * 404HandicapUrlCaptcha
	 */
	IBM_404_HANDICAP_URL_CAPTCHA("404HandicapUrlCaptcha", "Handicap url captcha does not exist") {
		@Override public String getMsgCn() {
			return "未能找盘口信息" ;
		}
	},
	/**
	 * 404HandicapItem
	 */
	IBM_404_HANDICAP_ITEM("404HandicapItem", "handicapItemId is empty") {
		@Override public String getMsgCn() {
			return "盘口详情id为空" ;
		}
	},

	/**
	 * 404ClientExist
	 */
	IBM_404_CLIENT_EXIST("404ClientExist", "Client capacity is full, please try again later") {
		@Override public String getMsgCn() {
			return "客户端容量已满，请稍后再试" ;
		}
	},

	/**
	 * 404ClientHmExist
	 */
	IBM_404_CLIENT_HM_EXIST("404ClientHmExist", "Client is empty existence Handicap Member") {
		@Override public String getMsgCn() {
			return "存在客户端盘口会员为空" ;
		}
	},

	/**
	 * 404ClientExistHmId
	 */
	IBM_404_CLIENT_HM_EXIST_ID("404ClientExistHmId", "Client id is empty existence Handicap Member") {
		@Override public String getMsgCn() {
			return "存在客户端盘口会员id为空" ;
		}
	},
	/**
	 * 404BetState
	 */
	IBM_404_BET_STATE("404BetState", "No bet state") {
		@Override public String getMsgCn() {
			return "不存在该投注状态" ;
		}
	},
	/**
	 * 404BetInfo
	 */
	IBM_404_BET_INFO("404BetInfo", "Bet information not found") {
		@Override public String getMsgCn() {
			return "投注信息没有找到" ;
		}
	},
	/**
	 * 404HmBetInfo
	 */
	IBM_404_HM_BET_INFO("404HmBetInfo", "Handicap member betting information not found") {
		@Override public String getMsgCn() {
			return "盘口会员投注信息没有找到" ;
		}
	},
	/**
	 * 401NotFindUserId
	 */
	IBM_404_GAME("404Game", "No game exists") {
		@Override public String getMsgCn() {
			return "不存在该游戏" ;
		}
	},
	/**
	 * 404Plan
	 */
	IBM_404_PLAN("404Plan", "No plan exists") {
		@Override public String getMsgCn() {
			return "不存在该方案" ;
		}
	},
	/**
	 * 404PlanItem
	 */
	IBM_404_PLAN_ITEM("404PlanItem", "There is no program details") {
		@Override public String getMsgCn() {
			return "不存在该方案详情信息" ;
		}
	},
	/**
	 * 404PlanGroupActive
	 */
	IBM_404_PLAN_GROUP_ACTIVE("404PlanGroupActive", "There is no existing scenario group activation") {
		@Override public String getMsgCn() {
			return "不存在已有的方案组激活" ;
		}
	},
	/**
	 * 404Receipt
	 */
	IBM_404_RECEIPT("404Receipt", "No message receipt") {
		@Override public String getMsgCn() {
			return "不存在消息回执" ;
		}
	},
	/**
	 * 404NotFindClientExistHmId
	 */
	IBM_404_PASSWORD_NOT_DIFFERENT("404PasswordNotDifferent", "The new password is the same as the old password") {
		@Override public String getMsgCn() {
			return "新密码与旧密码相同" ;
		}
	},
	/**
	 * 404UnrecognizedSchemaTableName
	 */
	IBM_404_UNRECOGNIZED_SCHEMA_TABKE_NAME("404UnrecognizedSchemaTableName", "Unrecognized schema table name") {
		@Override public String getMsgCn() {
			return "不能识别的方案表名称" ;
		}
	},
	/**
	 * 404HistoricalLotteryData
	 */
	IBM_404_HISTORICAL_LOTTERY_DATA("404HistoricalLotteryData", "No historical lottery data") {
		@Override public String getMsgCn() {
			return "不存在历史开奖数据" ;
		}
	},
	/**
	 * 404ExecResult
	 */
	IBM_404_EXEC_RESULT("404ExecResult", "Not find exec result") {
		@Override public String getMsgCn() {
			return "不能识别的执行结果" ;
		}
	},
	/**
	 * 404Cmd
	 */
	IBM_404_CMD("404Cmd", "Unrecognized request classification") {
		@Override public String getMsgCn() {
			return "不能识别的请求分类" ;
		}
	},

	/**
	 * 500
	 */
	CODE_500("500", "Server error, please try again late") {
		@Override public String getMsgCn() {
			return "服务器出错，请稍候再试" ;
		}
	},
	/**
	 * 500
	 */
	IBM_500("500", "Server error, please try again later") {
		@Override public String getMsgCn() {
			return "服务器出错，请稍候再试" ;
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
