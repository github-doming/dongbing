package com.ibm.old.v1.common.doming.enums;
/**
 * @Description: 盘口枚举类
 * @Author: Dongming
 * @Date: 2019-03-09 14:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public enum IbmHandicapEnum {

	/**
	 * SGWIN
	 */
	SGWIN("SGWIN") {
		@Override public String getName() {
			return "大唐";
		}
	},
	/**
	 * WS2
	 */
	WS2("WS2") {
		@Override public String getName() {
			return "泰源";
		}
	},
	/**
	 * IDC
	 */
	IDC("IDC") {
		@Override public String getName() {
			return  "丰泰";
		}
	};
	private String code;
	IbmHandicapEnum(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public abstract String getName();

}
