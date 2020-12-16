package com.ibs.common.enums;

import org.doming.core.tools.StringTool;

/**
 * 任务状态或数据状态
 *
 * @Author: null
 * @Date: 2020-05-09 10:38
 * @Version: v1.0
 */
public enum IbsTypeEnum {
	/**
	 * 成功失败状态
	 */
	TRUE("true", "是"), FALSE("false", "否"),NONE("none", "没有"),
	/**
	 * 平台用户类型
	 */
	FREE("free", "免费"), CHARGE("charge", "收费"), OTHER("other", "其他"),
	/**
	 * 用户类型
	 */
	USER("user", "用户"), ADMIN("admin", "管理者"), SYS("sys", "系统管理员"),
	POWER_USER("power_user", "超级用户"),
	/**
	 * 类别
	 */
	MEMBER("member", "会员"), AGENT("agent", "代理"), PARTNER("PARTNER", "股东"),
	/**
	 * 投注类型
	 */
	MANUAL("manual", "手动投注"), MERGE("merge", "合并投注"), PLAN("PLAN", "方案投注"), BET("BET", "投注"),
	/**
	 * 投注模式
	 */
	REAL("real", "真实投注"), VIRTUAL("virtual", "模拟投注"), STOP("stop", "停止投注"),
	/**
	 * 球类类型
	 */
	BIG("big", "大"), SMALL("small", "小"), SINGLE("single", "单"), DOUBLE("double", "双"),
	/**
	 * 消息类型
	 */
	ANNOUNCE("announce", "公告"), REMIND("remind", "提醒"), MESSAGE("message", "信息"),
	/**
	 * 操作人类型
	 */
	SELF("self", "自己"), HIGHER_UP("higher_up", "上级"),
	/**
	 * 游戏类型
	 */
	NUMBER("number", "号码类"), BALL("ball", "球类"), HAPPY("happy", "快乐类");
	String code;
	String msg;

	IbsTypeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}

	/**
	 * 比较
	 *
	 * @param obj 对象Obj
	 * @return 比较
	 */
	public boolean equal(Object obj) {
		return this.name().equalsIgnoreCase(obj.toString());
	}

	/**
	 * 判断是否属于状态类别
	 *
	 * @param booleanType 状态类别
	 * @return TRUE，FALSE-true
	 */
	public static boolean booleanType(Object booleanType) {
		if (StringTool.isEmpty(booleanType)) {
			return false;
		}
		IbsTypeEnum type = valueOf(booleanType.toString());
		switch (type) {
			case TRUE:
			case FALSE:
				return true;
			default:
				return false;
		}
	}

	/**
	 * 判断是否属于投注模式类别
	 *
	 * @param betModeType 投注模式
	 * @return REAL, VIRTUAL-true
	 */
	public static boolean betModelType(String betModeType) {
		if (StringTool.isEmpty(betModeType)) {
			return false;
		}
		IbsTypeEnum type = valueOf(betModeType);
		switch (type) {
			case REAL:
			case VIRTUAL:
				return true;
			default:
				return false;
		}
	}

	/**
	 * 获取用户类型
	 */
	public static IbsTypeEnum getUserType(String userType) {
		switch (userType) {
			case "SYS":
				return SYS;
			case "ADMIN":
				return ADMIN;
			case "POWER_USER":
				return POWER_USER;
			default:
				return USER;
		}
	}

	/**
	 * 返回类型
	 *
	 * @param userType 用户类型
	 * @return 类型
	 */
	public static IbsTypeEnum getType(String userType) {
		switch (userType) {
			case "CARD_ADMIN":
			case "ADMIN":
				return ADMIN;
			case "POWER_USER":
				return POWER_USER;
			case "CARD_PARTNER":
			case "PARTNER":
				return PARTNER;
			case "CARD_AGENT":
			case "AGENT":
				return AGENT;
			default:
				return null;
		}
	}
}
