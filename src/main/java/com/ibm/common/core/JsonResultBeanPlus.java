package com.ibm.common.core;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import com.common.enums.HcCodeEnum;
import com.ibm.common.enums.IbmCodeEnum;
import net.sf.json.JSONObject;
/**
 * @Description: json返回bean类扩展类
 * @Author: Dongming
 * @Date: 2018-12-01 17:02
 * @Date: 2019-3-18 16:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JsonResultBeanPlus extends JsonTcpBean {

	// -- 下面的方法不再更新 --//

	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --
	/**
	 * 设置参考枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putEnum(IbmCodeEnum enumCode) {
		super.setCode(enumCode.getCode());
		super.setMsg(enumCode.getMsgCn());

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
	 * 设置系统级提示枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putSysEnum(IbmCodeEnum enumCode) {
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
	 * @return 当前对象
	 */
	public JsonResultBeanPlus success() {
		putEnum(IbmCodeEnum.IBM_200);
		putSysEnum(IbmCodeEnum.CODE_200);
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
	 * @return 当前对象
	 */
	public JsonResultBeanPlus process() {
		return process(IbmCodeEnum.IBM_202);
	}

	/**
	 * 处理中
	 * @param code 处理信息
	 * @return 当前对象
	 */
	public JsonResultBeanPlus process(IbmCodeEnum code) {
		putEnum(code);
		putSysEnum(IbmCodeEnum.CODE_202);
		return this;
	}

	/**
	 * 失败
	 */
	public void fail() {
		putEnum(IbmCodeEnum.IBM_404_DATA);
		putSysEnum(IbmCodeEnum.CODE_404);
		setSuccess(false);
	}

	/**
	 * 失败
	 * @param message 失败原因
	 * @return 当前对象
	 */
	public JsonResultBeanPlus fail(String message) {
		fail();
		setMsg(message);
		return this;
	}

	/**
	 * 错误
	 *
	 * @param message 错误原因
	 * @return 当前对象
	 */
	public JsonResultBeanPlus error(String message) {
		putSysEnum(IbmCodeEnum.CODE_500);
		super.setCode(IbmCodeEnum.IBM_500.getCode());
		super.setMsg(message);
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
}
