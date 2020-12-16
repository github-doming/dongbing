package com.ibm.old.v1.common.doming.enums;

import org.doming.core.tools.StringTool;
/**
 * @Description: 任务状态或数据状态
 * @Author: Dongming
 * @Date: 2018年10月16日10:06:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmTypeEnum {

	/**
	 * free
	 */
	FREE {
		@Override public String getMsg() {
			return "free" ;
		}
		@Override public String getMsgCn() {
			return "免费" ;
		}
	},
	/**
	 * pay
	 */
	PAY {
		@Override public String getMsg() {
			return "pay" ;
		}
		@Override public String getMsgCn() {
			return "付费" ;
		}
	},
	/**
	 * client
	 */
	CLIENT {
		@Override public String getMsg() {
			return "client" ;
		}
		@Override public String getMsgCn() {
			return "客户端" ;
		}

	},
	/**
	 * free
	 */
	ADMIN {
		@Override public String getMsg() {
			return "true" ;
		}
		@Override public String getMsgCn() {
			return "管理者" ;
		}
	},

	/**
	 * true
	 */
	TRUE {
		@Override public String getMsg() {
			return "true" ;
		}
		@Override public String getMsgCn() {
			return "是" ;
		}
	},
	/**
	 * false
	 */
	FALSE {
		@Override public String getMsg() {
			return "false" ;
		}
		@Override public String getMsgCn() {
			return "否" ;
		}
	},
	/**
	 * false
	 */
	READY {
		@Override public String getMsg() {
			return "ready" ;
		}
		@Override public String getMsgCn() {
			return "就绪" ;
		}
	},
	/**
	 * SUCCESS
	 */
	SUCCESS {
		@Override public String getMsg() {
			return "SUCCESS" ;
		}
		@Override public String getMsgCn() {
			return "投注成功" ;
		}
	},
	/**
	 * FAIL
	 */
	FAIL {
		@Override public String getMsg() {
			return "FAIL" ;
		}
		@Override public String getMsgCn() {
			return "投注失败" ;
		}
	},
	/**
	 * auto
	 */
	AUTO {
		@Override public String getMsg() {
			return "auto" ;
		}
		@Override public String getMsgCn() {
			return "自动" ;
		}
	},
	/**
	 * none
	 */
	NONE {
		@Override public String getMsg() {
			return "none" ;
		}
		@Override public String getMsgCn() {
			return "无" ;
		}
	},
	/**
	 * real
	 */
	REAL {
		@Override public String getMsg() {
			return "real bet" ;
		}
		@Override public String getMsgCn() {
			return "真实投注" ;
		}
	},
	/**
	 * virtual
	 */
	VIRTUAL {
		@Override public String getMsg() {
			return "virtual bet" ;
		}
		@Override public String getMsgCn() {
			return "虚拟投注" ;
		}
	},
	/**
	 * coding
	 */
	CODING {
		@Override public String getMsg() {
			return "coding" ;
		}
		@Override public String getMsgCn() {
			return "编码" ;
		}
	},
	/**
	 * turn
	 */
	TURN {
		@Override public String getMsg() {
			return "turn" ;
		}
		@Override public String getMsgCn() {
			return "转换" ;
		}
	},
	/**
	 * merge
	 */
	MERGE {
		@Override public String getMsg() {
			return "merge" ;
		}
		@Override public String getMsgCn() {
			return "合并" ;
		}
	},
	/**
	 * send
	 */
	SEND {
		@Override public String getMsg() {
			return "send" ;
		}
		@Override public String getMsgCn() {
			return "发送" ;
		}
	},
	/**
	 * settle
	 */
	SETTLE {
		@Override public String getMsg() {
			return "settle" ;
		}
		@Override public String getMsgCn() {
			return "结算" ;
		}
	},
	/**
	 * blast
	 */
	BLAST {
		@Override public String getMsg() {
			return "blast" ;
		}
		@Override public String getMsgCn() {
			return "炸" ;
		}
	},
	/**
	 * small
	 */
	SMALL {
		@Override public String getMsg() {
			return "small" ;
		}
		@Override public String getMsgCn() {
			return "小" ;
		}
	},
	/**
	 * big
	 */
	BIG {
		@Override public String getMsg() {
			return "big" ;
		}
		@Override public String getMsgCn() {
			return "大" ;
		}
	},
	/**
	 * single
	 */
	SINGLE {
		@Override public String getMsg() {
			return "single" ;
		}
		@Override public String getMsgCn() {
			return "单" ;
		}
	},
	/**
	 * double
	 */
	DOUBLE {
		@Override public String getMsg() {
			return "double" ;
		}
		@Override public String getMsgCn() {
			return "双" ;
		}
	},
	/**
	 * open
	 */
	OPEN {
		@Override public String getMsg() {
			return "open" ;
		}
		@Override public String getMsgCn() {
			return "开启" ;
		}
	},
	/**
	 * close
	 */
	CLOSE {
		@Override public String getMsg() {
			return "close" ;
		}
		@Override public String getMsgCn() {
			return "关闭" ;
		}
	};
	/**
	 * 获取消息
	 *
	 * @return 消息
	 */
	public abstract String getMsg();

	public abstract String getMsgCn();

	public static boolean isTrue(String type) {
		return TRUE.name().equals(type);
	}

	public static boolean isTrueOrFalse(String type) {
		return TRUE.name().equals(type) || FALSE.name().equals(type);
	}

	public static boolean isCode(String type) {
		return CODING.name().equals(type);
	}
	public static boolean isTurn(String type) {
		return TURN.name().equals(type);
	}
	public static boolean isMerge(String type) {
		return MERGE.name().equals(type);
	}
	public static boolean isSend(String type) {
		return SEND.name().equals(type);
	}
	public static boolean isSettle(String type) {
		return SETTLE.name().equals(type);
	}
	public static IbmTypeEnum getType(Object type) {
		if (StringTool.isEmpty(type)) {
			return null;
		}
		switch (type.toString()) {
			case "OPEN":
				return OPEN;
			case "CLOSE":
				return CLOSE;
			default:
				return null;
		}
	}

	/**
	 * 是否赢
	 *
	 * @param type 结果
	 * @return 赢 TRUE
	 */
	public static String isWin(String type) {
		if ("中獎".equals(type)) {
			return TRUE.name();
		} else if ("不中獎".equals(type)) {
			return FALSE.name();
		}
		return FALSE.name();
	}

}