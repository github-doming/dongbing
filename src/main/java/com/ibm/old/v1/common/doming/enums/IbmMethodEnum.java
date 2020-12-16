package com.ibm.old.v1.common.doming.enums;

import org.doming.core.tools.StringTool;
/**
 * @Description: 任务状态或数据状态
 * @Author: Dongming
 * @Date: 2018年10月15日17:44:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmMethodEnum {

	/**
	 * open
	 */
	OPEN("open") {
		@Override public String getMsgCn() {
			return "打开";
		}
	},
	/**
	 * close
	 */
	CLOSE("close") {
		@Override public String getMsgCn() {
			return "关闭";
		}
	},
	/**
	 * login
	 */
	LOGIN("login") {
		@Override public String getMsgCn() {
			return "登录";
		}
	},
	/**
	 * logon
	 */
	LOGON("logon") {
		@Override public String getMsgCn() {
			return "登录";
		}
	},
	/**
	 * loginTime
	 */
	LOGIN_TIME("loginTime") {
		@Override public String getMsgCn() {
			return "定时登录";
		}
	},
	/**
	 * logout
	 */
	LOGOUT("logout") {
		@Override public String getMsgCn() {
			return "登出";
		}
	},
	/**
	 * check
	 */
	CHECK("check") {
		@Override public String getMsgCn() {
			return "校验";
		}
	},
	/**
	 * checkLogin
	 */
	CHECK_LOGIN("checkLogin") {
		@Override public String getMsgCn() {
			return "校验登录";
		}
	},
	/**
	 * setBetRate
	 */
	SET_BET_RATE("setBetRate") {
		@Override public String getMsgCn() {
			return "设置赔率";
		}
	},
	/**
	 * setGameBetInfo
	 */
	SET_GAME_BET_INFO("setGameBetInfo") {
		@Override public String getMsgCn() {
			return "设置游戏投注信息";
		}
	},
	/**
	 * setGameBetState
	 */
	SET_GAME_BET_STATE("setGameBetState") {
		@Override public String getMsgCn() {
			return "设置游戏投注状态";
		}
	},
	/**
	 * setGameBetState
	 */
	SET_ALL_GAME_BET_STATE("setAllGameBetState") {
		@Override public String getMsgCn() {
			return "设置所有游戏投注状态";
		}
	},
	/**
	 * setGameBetDetail
	 */
	SET_GAME_BET_DETAIL("setGameBetDetail") {
		@Override public String getMsgCn() {
			return "设置游戏投注详情";
		}
	},
	/**
	 * logoutException
	 */
	LOGOUT_EXCEPTION("logoutException") {
		@Override public String getMsgCn() {
			return "异常退出";
		}
	},
	/**
	 * manualBet
	 */
	MANUAL_BET("manualBet") {
		@Override public String getMsgCn() {
			return "手动投注";
		}
	},
	/**
	 * autoBet
	 */
	AUTO_BET("autoBet") {
		@Override public String getMsgCn() {
			return "自动投注";
		}
	},
	/**
	 * bet
	 */
	BET("bet") {
		@Override public String getMsgCn() {
			return "投注";
		}
	},
	/**
	 * other
	 */
	OTHER("other") {
		@Override public String getMsgCn() {
			return "其他";
		}
	};

	private String msg;

	/**
	 * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	 *
	 * @param msg message
	 */
	IbmMethodEnum(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}

	/**
	 * 获取中文消息
	 *
	 * @return 中文消息
	 */
	public abstract String getMsgCn();

	public Byte getVal() {
		return null;
	}

	public static IbmMethodEnum getMethod(Object method) {
		if (StringTool.isEmpty(method)) {
			return null;
		}
		switch (method.toString()) {
			case "OPEN":
				return OPEN;
			case "CLOSE":
				return CLOSE;
			case "LOGIN":
				return LOGIN;
			case "LOGON":
				return LOGON;
			case "LOGIN_TIME":
				return LOGIN_TIME;
			case "SET_BET_RATE":
				return SET_BET_RATE;
			case "SET_GAME_BET_INFO":
				return SET_GAME_BET_INFO;
			case "SET_GAME_BET_STATE":
				return SET_GAME_BET_STATE;
			case "SET_GAME_BET_DETAIL":
				return SET_GAME_BET_DETAIL;
			case "LOGOUT_EXCEPTION":
				return LOGOUT_EXCEPTION;
			case "CHECK":
				return CHECK;
			case "BET":
				return BET;
			case "CHECK_LOGIN":
				return CHECK_LOGIN;
			case "SET_ALL_GAME_BET_STATE":
				return SET_ALL_GAME_BET_STATE;
			default:
				return null;
		}
	}
}
