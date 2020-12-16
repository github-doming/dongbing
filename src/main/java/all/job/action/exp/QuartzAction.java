package all.job.action.exp;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.CommAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Dongming
 * @Date: 2020-05-15 17:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class QuartzAction extends CommAction {
	JsonTcpBean jrb = new JsonTcpBean();
	JSONObject jsonData;

	/**
	 * 下一个action
	 * @return 运行结果
	 * @throws Exception 运行错误
	 */
	protected abstract String run() throws Exception;

	@Override
	public String execute() throws Exception {
		String json = request.getParameter("json");
		jsonData = JSON.parseObject(json);
		String time = jsonData.getString("time");
		String valiDate = jsonData.getString("valiDate");
		if ( StringUtil.isBlank(valiDate) || StringUtil.isBlank(time)) {
			jrb.setCode(ReturnCodeEnum.app400Blank.toString());
			jrb.setMsg(ReturnCodeEnum.app400Blank.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code400.toString());
			jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			jrb.setSuccess(false);
			return this.returnJson(jrb);
		}
		//大于10s请求失败
		if (System.currentTimeMillis() - Long.parseLong(time) > 10 * 1000L) {
			jrb.setCode(ReturnCodeEnum.app400Session.toString());
			jrb.setMsg(ReturnCodeEnum.app400Session.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code400.toString());
			jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			jrb.setSuccess(false);
			return this.returnJson(jrb);
		}
		if (verify(time, valiDate)) {
			return run();
		}
		jrb.setCode(ReturnCodeEnum.app500VerifyCode.toString());
		jrb.setMsg(ReturnCodeEnum.app500VerifyCode.getMsgCn());
		jrb.setCodeSys(ReturnCodeEnum.code500.toString());
		jrb.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
		jrb.setSuccess(false);
		return this.returnJson(jrb);
	}


	private static final String SALT = "dongmingdongming";
	/**
	 * 生成含有随机盐的加密码
	 *
	 * @param strData 未加密字符串
	 * @return 加密后的字符串
	 */
	private   String generate(String strData) throws NoSuchAlgorithmException {
		strData = md5Hex(strData + SALT);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = strData.charAt(i / 3 * 2);
			char c = SALT.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = strData.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}

	/**
	 * 校验密码是否正确
	 *
	 * @param strData   未加密字符串
	 * @param verifyStr 加密后的字符串
	 * @return 校验结果
	 */
	private  boolean verify(String strData, String verifyStr) throws NoSuchAlgorithmException {
		char[] cs1 = new char[32];
		char[] cs2 = new char[16];
		for (int i = 0; i < 48; i += 3) {
			cs1[i / 3 * 2] = verifyStr.charAt(i);
			cs1[i / 3 * 2 + 1] = verifyStr.charAt(i + 2);
			cs2[i / 3] = verifyStr.charAt(i + 1);
		}
		String salt = new String(cs2);
		return md5Hex(strData + salt).equals(new String(cs1));
	}
	/**
	 * 获取十六进制字符串形式的MD5摘要
	 *
	 * @param str 输入字符串
	 * @return MD5摘要字符串
	 */
	private  String md5Hex(String str) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] bs = md5.digest(str.getBytes());
		return new String(new Hex().encode(bs));
	}
}
