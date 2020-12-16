package com.ibs.common.enums;

import org.doming.core.tools.StringTool;

/**
 * 任务状态或数据状态
 *
 * @Author: null
 * @Date: 2020-05-14 11:32
 * @Version: v1.0
 */
public enum IbsStateEnum {
	/**
	 * 运行状态
	 */
	BEGIN("开始"), SEND("发送"), PROCESS("处理"), ERROR("错误"), FAIL("失败"), SUCCESS("成功"), FINISH("完成"), MERGE("合并投注"),
	/**
	 * 数据状态
	 */
	OPEN("开启"), CLOSE("关闭"), DEL("删除"), FULL("充满"), ACTIVATE("激活"), CANCEL("注销"), DEF("默认"), STOP("停止"), DISABLE(
			"禁用"), ALLOT("分配"),
	/**
	 * 数据类型
	 */
	FIRST("首次"),AGAIN("补投"),
	/**
	 * 登录状态
	 */
	LOGIN("登入"), LOGOUT("登出"), BET("投注中"), INIT("初始化"), NONE("没有"),
	/**
	 * 消息状态
	 */
	FAILED("未通过"),AUDIT_PASS("审核通过"),MODIFY_FINIS("修改完成"),MODIFY_FAIL("修改未通过"),FINISHED("完成"),
	/**
	 * 重置方案状态 NONE("没有"),
	 */
	CUSTOM("自定义重置"), DAY("每日重置"), RESET("重置"),
	/**
	 * 停止新增状态 NONE("没有"),
	 */
	NOW("立即停止"), AUTO("自动停止");
	private String msg;

	IbsStateEnum(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public boolean equal(Object state) {
		return this.name().equals(state);
	}

	/**
	 * 重置状态类型
	 *
	 * @param resetType 重置类型
	 * @return
	 */
	public static boolean resetType(String resetType) {
		if (StringTool.isEmpty(resetType)) {
			return false;
		}
		switch (valueOf(resetType)) {
			case DAY:
			case NONE:
			case CUSTOM:
				return true;
			default:
				return false;
		}
	}
	/**
	 * 新增状态
	 *
	 * @param increaseState 新增状态
	 * @return
	 */
	public static boolean increaseType(String increaseState) {
		if (StringTool.isEmpty(increaseState)) {
			return false;
		}
		switch (valueOf(increaseState)) {
			case NOW:
			case NONE:
			case AUTO:
				return true;
			default:
				return false;
		}
	}
	/**
	 * 状态类型
	 *
	 * @param state 状态
	 * @return
	 */
	public static boolean stateType(String state) {
		if (StringTool.isEmpty(state)) {
			return false;
		}
		switch (valueOf(state)) {
			case OPEN:
			case CLOSE:
				return true;
			default:
				return false;
		}
	}
}
