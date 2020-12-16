package com.cloud.common.core;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import net.sf.json.JSONObject;
/**
 * json返回bean类扩展类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JsonResultBeanPlus extends JsonTcpBean {

	public boolean notSuccess() {
		return !success;
	}

	/**
	 * 设置参考枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putEnum(CodeEnum enumCode) {
		super.setCode(enumCode.getCode());
		super.setMsg(enumCode.getMsgCn());
	}
	/**
	 * 设置系统级提示枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putSysEnum(CodeEnum enumCode) {
		super.setCodeSys(enumCode.getCode());
		super.setMsgSys(enumCode.getMsgCn());
	}

	/**
	 * 设置参考枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putEnum(ReturnCodeEnum enumCode) {
		super.setCode(enumCode.getCode());
		super.setMsg(enumCode.getMsgCn());
	}

	/**
	 * 设置系统级提示枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putSysEnum(ReturnCodeEnum enumCode) {
		super.setCodeSys(enumCode.getCode());
		super.setMsgSys(enumCode.getMsgCn());
	}

	/**
	 * 成功
	 *
	 * @return 当前对象
	 */
	public JsonResultBeanPlus success() {
		putEnum(CodeEnum.CLOUD_200);
		putSysEnum(CodeEnum.CODE_200);
		setSuccess(true);
		return this;
	}
	/**
	 * 成功
	 *
	 * @param data 成功信息
	 * @return 当前对象
	 */
	public JsonResultBeanPlus success(Object data) {
		setData(data);
		success();
		return this;
	}


	/**
	 * 错误
	 *
	 * @param message 错误原因
	 * @return 当前对象
	 */
	public JsonResultBeanPlus error(String message) {
		putSysEnum(CodeEnum.CODE_500);
		super.setCode(CodeEnum.CLOUD_500.getCode());
		super.setMsg(message);
		super.setSuccess(false);
		return this;
	}

	/**
	 * 没有找到数据
	 *
	 * @return 当前对象
	 */
	public JsonResultBeanPlus put401Data() {
		putEnum(CodeEnum.CLOUD_401_DATA);
		putSysEnum(CodeEnum.CODE_401);
		super.setSuccess(false);
		return this;
	}


	/**
	 * 转换为json字符串
	 *
	 * @return json字符串
	 */
	@Override public String toString() {
		JSONObject jsonObject = JSONObject.fromObject(this);
		return jsonObject.toString();
	}

	public static JsonResultBeanPlus successConstant() {
		return new JsonResultBeanPlus().success();
	}

}
