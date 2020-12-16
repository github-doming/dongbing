package com.ibm.common.test.wwj.handicap;
/**
 * @Description: http请求类型
 * @Author: Dongming
 * @Date: 2019-12-06 10:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum HttpType {

	/**
	 * 默认
	 */
	Normal("常态"),
	/**
	 * 登录
	 */
	SelectRoute("获取线路"), Routes("线路列表"), LoginInfo("登录信息"), Login("登录"), Home("主页面"),
	/**
	 * 会员
	 */
	UserInfo("用户信息"), GameLimit("游戏限额"),OddsInfo("赔率信息"),Betting("投注"),IsSettle("未结算详情"),

	/**
	 * 代理
	 */
	MemberList("会员列表"),BetSummary("未结算摘要"),BetDetail("投注详情");

	String msg;
	HttpType(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
}
