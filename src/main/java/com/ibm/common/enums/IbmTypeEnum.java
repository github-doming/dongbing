package com.ibm.common.enums;
import org.doming.core.tools.StringTool;
/**
 * @Description: 任务状态或数据状态
 * @Author: Dongming
 * @Date: 2019-08-28 14:00
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmTypeEnum {
	/**
	 * 成功失败状态
	 */
	TRUE("true", "是"), FALSE("false", "否"),
	/**
	 * 用户类型
	 */
	FREE("free", "免费"), CHARGE("charge", "收费") ,ADMIN("admin", "管理者"),POWER_USER("power_user","超级管理员"),
	SYS("system", "系统管理者"),CARD_AGENT("card_agent", "代理"),CARD_PARTNER("card_partner","股东"),CARD_ADMIN("card_admin", "管理者"),SYSTEM("system", "系统管理者"),
	/**
	 * 类别
	 */
	MEMBER("member", "会员"), AGENT("agent", "代理"),PARTNER("partner", "股东"),
	/**
	 * 投注类型
	 */
	MANUAL("manual", "手动投注"),MERGE("merge","合并投注"),FOLLOW("FOLLOW","跟随投注"),
	/**
	 * 投注模式
	 */
	REAL("real", "真实投注"), VIRTUAL("virtual", "虚拟投注"),
	/**
	 * 跟随会员类型
	 */
	ALL("all", "所有会员"), APPOINT("appoint", "指定会员"),
	/**
	 * 球类类型
	 */
	BIG("big", "大"), SMALL("small", "小"), SINGLE("single", "单"), DOUBLE("double", "双"),
    /**
     * 角色类型 USER 和 admin
     */
    USER("user","用户"),SELF("self","自己"),HIGHER_UP("higher_up","上级"),
	/**
	 * 操作类型
	 */
	UPDATE("update","更新"),SHOW("show","显示"),
	/**
	 * 消息类型
	 */
	ANNOUNCE("announce","公告"),REMIND("remind","提醒"),MESSAGE("message","信息"),
	/**
	 * 模块类型
	 */
	REQUEST("request","请求"),MQ("mq","消息"),SERVER("server","服务"),CLIENT("client","客户端");

	String code;
	String msg;

	IbmTypeEnum(String code, String msg) {
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
	 * 获取客户类型
	 *
	 * @param type 类别
	 * @return 客户方法类型
	 */
	public static IbmTypeEnum valueCustomerTypeOf(String type) {
		if (StringTool.isEmpty(type)){
			return null;
		}
		switch (type) {
			case "MEMBER":
				return MEMBER;
			case "AGENT":
				return AGENT;
			default:
				return null;
		}
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
		IbmTypeEnum type = valueOf(booleanType.toString());
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
		IbmTypeEnum type = valueOf(betModeType);
		switch (type) {
			case REAL:
			case VIRTUAL:
				return true;
			default:
				return false;
		}
	}
	/**
	 * 判断跟随类型是否合法
	 *
	 * @param followType 跟随会员类型
	 * @return 是否跟随类型
	 */
	public static boolean followType(String followType) {
		if (StringTool.isEmpty(followType)) {
			return true;
		}
		IbmTypeEnum type = valueOf(followType);
		switch (type) {
			case ALL:
			case APPOINT:
				return false;
			default:
				return true;
		}
	}

	/**
	 * 是属于充值卡类型
	 * @param cardType 充值卡类型
	 * @return 是 true 否 false
	 */
	public static boolean cardType(Object cardType) {
		if (StringTool.isEmpty(cardType)) {
			return false;
		}
		IbmTypeEnum type = valueOf(cardType.toString());
		switch (type) {
			case CARD_AGENT:
			case CARD_PARTNER:
				return true;
			default:
				return false;
		}
	}

	/**
	 * 返回类型
	 *
	 * @param userType 用户类型
	 * @return 类型
	 */
	public static IbmTypeEnum getType(String userType) {
		switch (userType) {
			case "CARD_ADMIN":
			case "ADMIN":
				return ADMIN;
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

	public boolean equal(Object type) {
		return this.name().equalsIgnoreCase(type.toString());
	}




}
