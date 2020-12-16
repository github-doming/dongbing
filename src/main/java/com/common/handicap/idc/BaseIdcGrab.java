package com.common.handicap.idc;

import com.common.enums.HcCodeEnum;
import com.common.handicap.crawler.BaseHandicapGrab;
import org.doming.core.tools.StringTool;

/**
 * IDC盘口抓取操作抽象类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 18:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseIdcGrab extends BaseHandicapGrab {


	/**
	 * 登陆错误
	 *
	 * @param msg 错误信息
	 * @return 错误信息
	 */
	public HcCodeEnum loginError(String msg) {
		if (StringTool.contains(msg, "帐号与密码不匹配")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "帐号被锁定")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "请求过于频繁")) {
			return HcCodeEnum.IBS_403_LOGIN_OFTEN;
		} else if (StringTool.contains(msg, "您的帐户为初次登陆", "密码由后台重新设定")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	public String ticket() {
		return crawlerImage().crawlerInfo().get("ticket");
	}
	public void ticket(String ticket) {
		crawlerImage().crawlerInfo("ticket", ticket);
	}
}
