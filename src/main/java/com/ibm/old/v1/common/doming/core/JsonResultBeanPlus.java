package com.ibm.old.v1.common.doming.core;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import net.sf.json.JSONObject;
/**
 * @Description: json返回bean类扩展类
 * @Author: Dongming
 * @Date: 2018-12-01 17:02
 * @Date: 2019-3-18 16:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JsonResultBeanPlus extends JsonResultBean {

	// -- 下面的方法不再更新 --//

	private String msgEn = null;
	public String getMsgEn() {
		return msgEn;
	}
	private JsonResultBeanPlus setMsgEn(String msgEn) {
		if (msgEn != null) {
			this.msgEn = msgEn;
		}
		return this;
	}

	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --
	/**
	 * 设置参考枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putEnum(IbmCodeEnum enumCode) {
		this.setMsgEn(enumCode.getMsg());
		super.setCode(enumCode.getCode());
		super.setMessage(enumCode.getMsgCn());

	}

	/**
	 * 设置系统级提示枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putSysEnum(IbmCodeEnum enumCode) {
		super.setCodeSys(enumCode.getCode());
		super.setMessageSys(enumCode.getMsgCn());
	}

	/**
	 * 设置参考枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putEnum(IbmHcCodeEnum enumCode) {
		this.setMsgEn(enumCode.getMsg());
		super.setCode(enumCode.getCode());
		super.setMessage(enumCode.getMsgCn());

	}

	/**
	 * 设置系统级提示枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putSysEnum(IbmHcCodeEnum enumCode) {
		super.setCodeSys(enumCode.getCode());
		super.setMessageSys(enumCode.getMsgCn());
	}

	/**
	 * 设置参考枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putEnum(ReturnCodeEnum enumCode) {
		this.setMsgEn(enumCode.getMsg());
		super.setCode(enumCode.getCode());
		super.setMessage(enumCode.getMsgCn());
	}

	/**
	 * 设置系统级提示枚举类型
	 *
	 * @param enumCode 枚举类型编码
	 */
	public void putSysEnum(ReturnCodeEnum enumCode) {
		super.setCodeSys(enumCode.getCode());
		super.setMessageSys(enumCode.getMsgCn());
	}

	/**
	 * 成功
	 */
	public void success() {
		putEnum(IbmCodeEnum.IBM_200);
		putSysEnum(IbmCodeEnum.CODE_200);
		setSuccess(true);
	}

	/**
	 * 处理中
	 */
	public void process() {
		putEnum(IbmCodeEnum.IBM_202);
		putSysEnum(IbmCodeEnum.CODE_202);
		setSuccess(true);
	}

	/**
	 * 错误
	 * @param message 错误原因
	 */
	public void error(String message) {
		putSysEnum(IbmCodeEnum.CODE_500);
		setCode(IbmCodeEnum.IBM_500.getCode());
		setMsg(message);
		setData(message);
		setSuccess(false);
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

	/**
	 * 将json转换为jrb
	 *
	 * @param json 待转换json
	 */
	public void set(JSONObject json) {
		this.setMsgEn(json.getOrDefault("msgEn", "").toString());
		super.setCode(json.getOrDefault("code", "").toString());
		super.setCodeSys(json.getOrDefault("codeSys", "").toString());
		super.setMessage(json.getOrDefault("msg", "").toString());
		super.setMessageSys(json.getOrDefault("messageSys", "").toString());
		super.setSuccess(Boolean.parseBoolean(json.getOrDefault("success", "").toString()));
	}
}
