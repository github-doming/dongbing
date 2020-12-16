package com.ibm.old.v1.servlet.ibm_plan_statistics.common;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-06-10 14:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmServletCmdEnum {
	/**
	 * gameList
	 */
	GAME_LIST("gameList") {
		@Override public String getMsgCn() {
			return "游戏列表";
		}
	},
	/**
	 * planList
	 */
	PLAN_LIST("planList") {
		@Override public String getMsgCn() {
			return "方案列表";
		}
	},
	/**
	 * statistics
	 */
	STATISTICS("statistics") {
		@Override public String getMsgCn() {
			return "统计";
		}
	},
	/**
	 * result
	 */
	RESULT("result") {
		@Override public String getMsgCn() {
			return "结果";
		}
	},
	/**
	 * excel
	 */
	EXCEL("excel") {
		@Override public String getMsgCn() {
			return "表格";
		}
	},
	/**
	 * start
	 */
	START("start") {
		@Override public String getMsgCn() {
			return "开始";
		}
	},
	/**
	 * stop
	 */
	STOP("stop") {
		@Override public String getMsgCn() {
			return "停止";
		}
	};

	String code;
	IbmServletCmdEnum(String code) {
		this.code = code;
	}

	/**
	 * 获取消息
	 *
	 * @return 消息
	 */

	public abstract String getMsgCn();
}
