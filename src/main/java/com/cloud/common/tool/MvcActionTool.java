package com.cloud.common.tool;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import org.doming.core.enums.CodeEnum;

/**
 * @Author: Dongming
 * @Date: 2020-04-01 09:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MvcActionTool {

	public static void returnCode400() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal, ReturnCodeEnum.app400JSON);
		putSysEnum(jrbThreadLocal, ReturnCodeEnum.code400);
		LogThreadLocal.setLog(jrbThreadLocal);
	}

	public static void returnCode401() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal, ReturnCodeEnum.app401Token);
		putSysEnum(jrbThreadLocal, ReturnCodeEnum.code401);
		LogThreadLocal.setLog(jrbThreadLocal);
	}

	public static void returnCode403Disable() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal, ReturnCodeEnum.code403Disable);
		putSysEnum(jrbThreadLocal, ReturnCodeEnum.code403);
		LogThreadLocal.setLog(jrbThreadLocal);
	}

	public static void returnCode403Rsa() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal, ReturnCodeEnum.app403TokenRSA);
		putSysEnum(jrbThreadLocal, ReturnCodeEnum.code403);
		LogThreadLocal.setLog(jrbThreadLocal);
	}


	public static void returnCode404() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal, ReturnCodeEnum.code404);
		putSysEnum(jrbThreadLocal, ReturnCodeEnum.code404);
		LogThreadLocal.setLog(jrbThreadLocal);
	}

	public static void returnCode500() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal, ReturnCodeEnum.app500VerifyCode);
		putSysEnum(jrbThreadLocal, ReturnCodeEnum.code500);
		LogThreadLocal.setLog(jrbThreadLocal);
	}


	private static void putSysEnum(JsonResultBean bean, ReturnCodeEnum enumCode) {
		bean.setCodeSys(enumCode.getCode());
		bean.setMessageSys(enumCode.getMsgCn());
	}

	private static void putEnum(JsonResultBean bean, ReturnCodeEnum enumCode) {
		bean.setCode(enumCode.getCode());
		bean.setMessage(enumCode.getMsgCn());
	}

	public static void return403Time() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal,CodeEnum.DM_403_TIME);
		putSysEnum(jrbThreadLocal,CodeEnum.CODE_403);
		LogThreadLocal.setLog(jrbThreadLocal);

	}

	public static void return403Restrict() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal,CodeEnum.DM_403_RESTRICT);
		putSysEnum(jrbThreadLocal,CodeEnum.CODE_403);
		LogThreadLocal.setLog(jrbThreadLocal);
	}

	private static void putSysEnum(JsonResultBean bean, CodeEnum enumCode) {
		bean.setCodeSys(enumCode.getCode());
		bean.setMessageSys(enumCode.getMsgCn());
	}

	private static void putEnum(JsonResultBean bean, CodeEnum enumCode) {
		bean.setCode(enumCode.getCode());
		bean.setMessage(enumCode.getMsgCn());
	}
}
