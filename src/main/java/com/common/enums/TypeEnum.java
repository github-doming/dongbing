package com.common.enums;

/**
 * @Description:
 * @Author: zjj
 * @Date: 2020-07-10 14:54
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public enum TypeEnum {
	/**
	 * 成功失败状态
	 */
	TRUE("true", "是"), FALSE("false", "否"),
	/**
	 * 平台用户类型
	 */
	FREE("free", "免费"),CHARGE("charge", "收费"), OTHER("other", "其他"),
	/**
	 * 用户类型
	 */
	USER("user", "用户"), ADMIN("admin", "管理者"),SYS("sys","系统管理员"),
	/**
	 * 类别
	 */
	MEMBER("member", "会员"), AGENT("agent", "代理"),PARTNER("PARTNER", "股东"),
	/**
	 * 投注类型
	 */
	MANUAL("manual", "手动投注"), MERGE("merge", "合并投注"), PLAN("PLAN", "方案投注"),BET("BET", "投注"),
	/**
	 * 投注模式
	 */
	REAL("real", "真实投注"), VIRTUAL("virtual", "模拟投注"),STOP("stop","停止投注"),
	/**
	 * 球类类型
	 */
	BIG("big", "大"), SMALL("small", "小"), SINGLE("single", "单"), DOUBLE("double", "双"),
	/**
	 * 消息类型
	 */
	ANNOUNCE("announce","公告"),REMIND("remind","提醒"),MESSAGE("message","信息"),
	/**
	 *资金选项
	 */
	FLAT_OLD_FUND("flatOldFund", "打平原资金"),	FLAT_TO_LOSE("flatToLose", "打平算输"),	FLAT_TO_WIN("FlatToWin", "打平算赢"),
	/**
	 * 操作人类型
	 */
	SELF("self", "自己"), HIGHER_UP("higher_up", "上级"),
	/**
	 * 游戏类型
	 */
	NUMBER("number","号码类"),BALL("ball","球类"),HAPPY("happy","快乐类");
	String code;
	String msg;

	TypeEnum(String code, String msg) {
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
	 * @param obj 对象Obj
	 * @return 比较
	 */
	public boolean equal(Object obj){
		return this.name().equalsIgnoreCase(obj.toString());
	}






}
