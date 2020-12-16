package com.common.core;


import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import net.sf.json.JSONObject;

import java.util.Map;

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
	 * 设置系统级提示枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putSysEnum(HcCodeEnum enumCode) {
		super.setCodeSys(enumCode.getCode());
		super.setMsgSys(enumCode.getMsgCn());
	}

	/**
	 * 设置参考枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putEnum(HcCodeEnum enumCode) {
		super.setCode(enumCode.getCode());
		super.setMsg(enumCode.getMsgCn());
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
		putEnum(CodeEnum.IBS_200);
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
	 * 处理中
	 *
	 * @return 当前对象
	 */
	public JsonResultBeanPlus process() {
		return process(CodeEnum.IBS_202);
	}
	/**
	 * 处理中
	 *
	 * @param data 处理信息
	 * @return 当前对象
	 */
	public JsonResultBeanPlus process(Map<String,Object> data) {
		setData(data);
		return process(CodeEnum.IBS_202);
	}

	/**
	 * 处理中
	 *
	 * @param code 处理信息
	 * @return 当前对象
	 */
	public JsonResultBeanPlus process(CodeEnum code) {
		putEnum(code);
		putSysEnum(CodeEnum.CODE_202);
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
		super.setCode(CodeEnum.IBS_500.getCode());
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
		putEnum(CodeEnum.IBS_401_DATA);
		putSysEnum(CodeEnum.CODE_401);
		super.setSuccess(false);
		return this;
	}

	/**
	 * 盘口会员没有找到
	 *
	 * @return 当前对象
	 */
	public JsonResultBeanPlus put404HandicapMember() {
		putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
		putSysEnum(CodeEnum.CODE_404);
		super.setSuccess(false);
		return this;
	}
	/**
	 * 将所有数据进行清空
	 * @return 当前对象
	 */
	public JsonResultBeanPlus clear() {
		super.setSuccess(false);
		super.setData(null);
		super.setCodeSys(null);
		super.setMsgSys(null);
		super.setCode(null);
		super.setMsg(null);
		return this;
	}
	/**
	 * 转换为json字符串
	 *
	 * @return json字符串
	 */
	public String toJsonString() {
		JSONObject jsonObject = JSONObject.fromObject(this);
		return jsonObject.toString();
	}
	public boolean equalSysCode(HcCodeEnum code) {
		return code.getCode().equals(super.getCodeSys());
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
