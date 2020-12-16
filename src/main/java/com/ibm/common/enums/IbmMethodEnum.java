package com.ibm.common.enums;

/**
 * @Description: 方法枚举类
 * @Author: Dongming
 * @Date: 2019-08-26 17:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmMethodEnum {

	/**
	 * 管理
	 */
	OPEN("开启"), CLOSE("关闭"), CLEAR("清理"),
	/**
	 * 登录
	 */
	VALI_LOGIN("验证登录"), LOGIN("登录"), SNATCH_LOGIN("抢占登录"),
	/**
	 * 客户端
	 */
	CLIENT_HEARTBEAT("客户端心跳"), CLIENT_MIGRATE("客户端迁移"), CAPACITY_SET("客户端容量设置"),
	HANDICAP_CAPACITY_SET("客户端盘口容量设置"),CLIENT_DEL("客户端删除"), HANDICAP_CAPACITY_DEL("客户端盘口容量删除"),
	SEAL_TIME("封盘时间"), THREAD_SET("线程设置"), CLIENT_MONITOR("客户端监控"),VR_CLIENT_HEARTBEAT("虚拟客户端心跳"),
	/**
	 * 设置
	 */
	MEMBER_SET("会员设置"), AGENT_SET("代理设置"), MANAGE_SET("管理设置"), CONFIG_INFO("客户端设置"),MANAGE_VR("管理虚拟端"),
	GAME_VR("虚拟会员游戏设置"),
	/**
	 * 设置详情-SET_ITEM
	 */
	SET_BET_STATE("游戏投注状态"), SET_HANDICAP("设置盘口详情"), SET_GAME("设置游戏详情"), SET_FILTER("设置过滤信息"), SET_GAME_INFO(
			"游戏信息"), SET_BIND("设置绑定"), SET_UNBIND("设置解绑"), SET_BET_MODE("设置投注模式"),BIND_VR_MEMBER("绑定虚拟会员"),
	SET_GAME_VR("虚拟会员游戏设置"),SET_PLAN_VR("虚拟方案设置"),BIND_VR_CLIENT("虚拟会员绑定客户端"),DEL_MEMBER_VR("虚拟会员删除"),
	/**
	 * 投注
	 */
	MANUAL_BET("手动投注"), FOLLOW_BET("跟随投注"), AGENT_BET_INFO("代理投注信息"), FOLLOW_VR_MEMBER_BET("跟随虚拟会员投注"),VR_MEMBER_BET_INFO("虚拟会员投注信息"),
	/**
	 * 投注结果
	 */
	ERROR("错误"), SUCCESS("成功"),
	/**
	 * 代理或会员消息
	 */
	LOGOUT("登出"), CUSTOMER_INFO("客户信息"),
	/**
	 * 客户端迁移、切换
	 */
	SEND("发送"), RECEIVE("接收"), REPLACE("切换"), HEARTBEAT("心跳"), ADMIN_INFO("后台消息"),

	ALL("所有"), WEEK("一周"), DELIMIT("自定义");

	private String msg;

	IbmMethodEnum(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
