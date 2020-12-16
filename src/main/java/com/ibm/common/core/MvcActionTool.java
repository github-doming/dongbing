package com.ibm.common.core;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.enums.IbmCodeEnum;

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

	public static void returnCode403() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal, ReturnCodeEnum.code403);
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

	public static void returnInsufficientTime() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal,IbmCodeEnum.IBM_403_INSUFFICIENT_TIME);
		putSysEnum(jrbThreadLocal,IbmCodeEnum.CODE_403);
		LogThreadLocal.setLog(jrbThreadLocal);

	}

	public static void returnRestrictAccess() {
		JsonTcpBean jrbThreadLocal = new JsonTcpBean();
		putEnum(jrbThreadLocal,IbmCodeEnum.IBM_403_RESTRICT_ACCESS);
		putSysEnum(jrbThreadLocal,IbmCodeEnum.CODE_403);
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

	private static void putSysEnum(JsonResultBean bean, IbmCodeEnum enumCode) {
		bean.setCodeSys(enumCode.getCode());
		bean.setMessageSys(enumCode.getMsgCn());
	}

	private static void putEnum(JsonResultBean bean, IbmCodeEnum enumCode) {
		bean.setCode(enumCode.getCode());
		bean.setMessage(enumCode.getMsgCn());
	}
}
