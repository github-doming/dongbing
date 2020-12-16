package com.ibm.common.enums;
/**
 * @Description: 任务状态或数据状态
 * @Author: Dongming
 * @Date: 2019-08-26 17:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmStateEnum {

	/**
	 * 运行状态
	 */
	BEGIN("开始"), SEND("发送"), PROCESS("处理"), FAIL("失败"), SUCCESS("成功"), FINISH("完成"),AGAIN("补投"),ERROR("错误"),
	/**
	 * 数据状态
	 */
	OPEN("开启"), CLOSE("关闭"), DEL("删除"), FULL("充满"), ACTIVATE("激活"), CANCEL("注销"), DEF("默认"),
	STOP("停止"), UNBIND("解绑"),DISABLE("禁用"),ALLOT("分配"),
	/**
	 * 数据类型
	 */
	FIRST("首次"),
	/**
	 * 登录状态
	 */
	LOGIN("登入"), LOGOUT("登出"), BET("投注中"),NONE("没有"),INIT("初始化"),
	/**
	 * 消息状态
	 */
	FAILED("未通过"),AUDIT_PASS("审核通过"),MODIFY_FINIS("修改完成"),MODIFY_FAIL("修改未通过"),FINISHED("完成");
	private String msg;
	IbmStateEnum(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}

	public boolean equal(Object state) {
		return this.name().equals(state);
	}
}
