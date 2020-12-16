package com.ibm.old.v1.common.doming.enums;
/**
 * @Description: 游戏枚举类
 * @Author: zjj
 * @Date: 2019-03-21 16:09
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public enum IbmGameEnum {
	/**
	 * 北京赛车
	 */
	PK10("北京赛车"),
	/**
	 * 重庆时时彩
	 */
	CQSSC("重庆时时彩"),
	/**
	 * 广东快乐彩
	 */
	KLC("广东快乐彩"),
	/**
	 * 幸运农场
	 */
	XYNC("幸运农场"),
	/**
	 * 幸运飞艇
	 */
	XYFT("幸运飞艇");
	private String name;
	IbmGameEnum(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

}
