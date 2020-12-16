package com.ibm.old.v1.common.doming.enums;

import org.doming.core.tools.StringTool;
/**
 * @Description: 任务状态或数据状态
 * @Author: Dongming
 * @Date: 2018年10月15日17:44:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmStateEnum {

	/**
	 * open
	 */
	OPEN("open") {
		@Override public String getMsgCn() {
			return "打开";
		}
	},
	/**
	 * stop
	 */
	STOP("stop") {
		@Override public String getMsgCn() {
			return "停止";
		}
	},
	/**
	 * ready
	 */
	READY("ready") {
		@Override public String getMsgCn() {
			return "就绪";
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
	 * def
	 */
	DEF("def") {
		@Override public String getMsgCn() {
			return "默认";
		}
	},
	/**
	 * def
	 */
	DEL("del") {
		@Override public String getMsgCn() {
			return "删除";
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
	 * logout
	 */
	LOGOUT("logout") {
		@Override public String getMsgCn() {
			return "登出";
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
	 * lock
	 */
	LOCK("lock") {
		@Override public String getMsgCn() {
			return "锁定";
		}
	},
	/**
	 * lock
	 */
	FULL("full") {
		@Override public String getMsgCn() {
			return "全部";
		}
	},

	//FIXME 阅读状态
	/**
	 * unread
	 */
	UNREAD("unread") {
		@Override public String getMsgCn() {
			return "未读";
		}
		@Override public Byte getVal() {
			return 1;
		}
	},
	/**
	 * read
	 */
	READ("read") {
		@Override public String getMsgCn() {
			return "已读";
		}
		@Override public Byte getVal() {
			return 2;
		}
	},
	/**
	 * new
	 */
	NEW("new") {
		@Override public String getMsgCn() {
			return "新增";
		}
	},

	//FIXME 回执状态
	/**
	 * send
	 */
	SEND("send") {
		@Override public String getMsgCn() {
			return "发送";
		}
	},
	/**
	 * process
	 */
	PROCESS("process") {
		@Override public String getMsgCn() {
			return "处理";
		}
	},
	/**
	 * fail
	 */
	FAIL("fail") {
		@Override public String getMsgCn() {
			return "失败";
		}
	},
	/**
	 * finish
	 */
	FINISH("finish") {
		@Override public String getMsgCn() {
			return "投注完成";
		}
	},
	/**
	 * logoutException
	 */
	LOGOUT_EXCEPTION("logoutException") {
		@Override public String getMsgCn() {
			return "异常登出";
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
	 * error
	 */
	ERROR("error") {
		@Override public String getMsgCn() {
			return "错误";
		}
	},
	/**
	 * info
	 */
	INFO("info") {
		@Override public String getMsgCn() {
			return "信息";
		}
	},
	/**
	 * trace
	 */
	TRACE("trace") {
		@Override public String getMsgCn() {
			return "跟踪";
		}
	},

	/**
	 * unfinish
	 */
	UNFINISH("unfinish") {
		@Override public String getMsgCn() {
			return "未完成";
		}
	},


	//FIXME 新增状态
	/**
	 * auto
	 */
	AUTO("auto") {
		@Override public String getMsgCn() {
			return "自动";
		}
	},
	
	/**
	 * manual
	 */
	MANUAL("manual") {
		@Override public String getMsgCn() {
			return "手动投注";
		}
	},
	/**
	 * process
	 */
	RIGHT("right") {
		@Override public String getMsgCn() {
			return "立即";
		}
	},
	/**
	 * none
	 */
	NONE("none") {
		@Override public String getMsgCn() {
			return "没有";
		}
	},
	
	/**
	 * success
	 */
	SUCCESS("success") {
		@Override public String getMsgCn() {
			return "投注成功";
		}
	},
	/**
	 * end
	 */
	END("end") {
		@Override public String getMsgCn() {
			return "结束";
		}
	},
	
	/**
	 * replay
	 */
	REPLAY("replay") {
		@Override public String getMsgCn() {
			return "重置";
		}
	},
	/**
	 * begin
	 */
	BEGIN("begin") {
		@Override public String getMsgCn() {
			return "开始";
		}
	};



	private String msg;

	/**
	 * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	 *
	 * @param msg message
	 */
	IbmStateEnum(String msg) {
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

	/**
	 * 是否是 回执码
 	 * @param state 状态码
	 * @return 是 回执码 true
	 */
	public static boolean isReceiptState(String state) {
		if(StringTool.isEmpty(state)){
			return false;
		}
		switch (state) {
			case "SEND":
			case "PROCESS":
			case "SUCCESS":
			case "FINISH":
				return true;
			default:
				return false;
		}
	}
	public static IbmStateEnum getLogState(Object state) {
		if (StringTool.isEmpty(state)) {
			return null;
		}
		switch (state.toString()) {
			case "TRACE":
				return TRACE;
			case "INFO":
				return INFO;
			case "ERROR":
				return ERROR;
			default:
				return null;
		}
	}
	/**
	 * 获取回执码
	 * @param state 回执码
	 * @return  回执码
	 */
	public static IbmStateEnum getReceiptState(String state) {
		if(StringTool.isEmpty(state)){
			return null;
		}
		switch (state) {
			case "SEND":
				return SEND;
			case "PROCESS":
				return PROCESS;
			case "SUCCESS":
				return SUCCESS;
			case "ERROR":
				return ERROR;
			case "FAIL":
				return FAIL;
			case "FINISH":
				return FINISH;
			default:
				return null;
		}
	}

	/**
	 * 是否属于最基础的状态码
	 * @param state 状态码
	 * @return 是 最基础的状态码 true
	 */
	public static boolean isState(String state) {
		switch (state) {
			case "OPEN":
			case "CLOSE":
			case "DEL":
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * 
	 * @Description:  盘口设置状态
	 *
	 * 参数说明 
	 * @param state 状态
	 * @return 
	 * 返回类型 boolean
	 */
	public static boolean hmSetState(String state){
		switch(state){
			case "OPEN":
				return true;
			case "CLOSE" :
				return true;
			default : 
				return false;
		}
	}

	/**
	 * 
	 * @Description:  盘口游戏设置状态
	 *
	 * 参数说明 
	 * @param state 状态
	 * @return 
	 * 返回类型 boolean
	 */
	public static boolean hmGameSetState(String state){
		if(StringTool.isEmpty(state)){
			return false;
		}
		switch(state){
			case "TRUE":
			case "FALSE" :
				return true;
			default : 
				return false;
		}
	}
	
	/**
	 * 
	 * @Description:  投注模式状态
	 *
	 * 参数说明 
	 * @param state 状态
	 * @return 
	 * 返回类型 boolean
	 */
	public static boolean betModeState(String state){
		if(StringTool.isEmpty(state)){
			return false;
		}
		switch(state){
			case "REAL":
			case "VIRTUAL" :
				return true;
			default : 
				return false;
		}
	}
	
	/**
	 * 
	 * @Description:  自动新增状态
	 *
	 * 参数说明 
	 * @param state 状态
	 * @return 
	 * 返回类型 boolean
	 */
	public static boolean increaseState(String state){
		if(StringTool.isEmpty(state)){
			return false;
		}
		switch(state){
			case "AUTO":
			case "STOP" :
			case "NONE" :
				return true;
			default : 
				return false;
		}
	}
	/**
	 * 获取启动状态
	 * @param state	状态
	 * @return
	 */
	public static IbmStateEnum getState(Object state) {
		if (StringTool.isEmpty(state)){
			return null;
		}
		switch (state.toString()) {
			case "OPEN":
				return OPEN;
			case "CLOSE":
				return CLOSE;
			default:
				return null;
		}
	}

}
