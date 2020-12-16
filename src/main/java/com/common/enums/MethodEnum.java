package com.common.enums;

/**
 * @Description: 方法枚举类
 * @Author: zjj
 * @Date: 2020-07-14 13:48
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public enum MethodEnum {
	/**
	 * 管理
	 */
	OPEN("开启"), CLOSE("关闭"), CLEAR("清理"),
	/**
	 * 登录
	 */
	VALI_LOGIN("验证登录"), LOGIN("登录"),LOGOUT("登出"),
	/**
	 * 客户端
	 */
	CLIENT_HEARTBEAT("客户端心跳"),CLIENT_MONITOR("客户端监控"), CAPACITY_SET("客户端容量设置"),CLIENT_DEL("客户端删除"),
	CLIENT_MIGRATE("客户端迁移"),SEAL_TIME("封盘时间"),HANDICAP_CAPACITY_SET("客户端盘口容量设置"),HANDICAP_CAPACITY_DEL("客户端盘口容量删除"),
	/**
	 * 设置
	 */
	MEMBER_SET("会员设置"), MANAGE_SET("管理设置"), CONFIG_INFO("客户端设置"),
	/**
	 * 设置详情-SET_ITEM
	 */
	SET_BET_STATE("游戏投注状态"), SET_HANDICAP("设置盘口详情"), SET_INCREASE("设置停止新增"), SET_GAME("设置游戏详情"),
	SET_GAME_INFO("游戏信息"), SET_BET_MODE("设置投注模式"),SET_INVERSE("设置游戏反投"),

	SET_PLAN_INFO("方案信息"),SET_PLAN("设置方案信息"),SET_PLAN_STATE("设置方案状态"),SET_PLAN_RESET("方案重置"),
	SET_PLAN_BATCH_STATE("批量设置方案状态"),CLEAR_GAME_PLAN("清除会员游戏方案"),DEL_GAME_PLAN("删除游戏方案"),
	/**
	 * 代理或会员消息
	 */
	CUSTOMER_INFO("客户信息"),
	/**
	 * 客户端迁移、切换
	 */
	SEND("发送"), RECEIVE("接收"), REPLACE("切换"), HEARTBEAT("心跳"), ADMIN_INFO("后台消息"),
	/**
	 * 投注
	 */
	MANUAL_BET("手动投注"), MERGE_BET( "合并投注"), PLAN_BET( "方案投注"),VR_MEMBER_BET_INFO("虚拟会员投注信息");

	private String msg;
	MethodEnum(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
