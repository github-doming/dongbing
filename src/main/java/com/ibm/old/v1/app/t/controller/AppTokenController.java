package com.ibm.old.v1.app.t.controller;
import all.gen.app_verify_account.t.entity.AppVerifyAccountT;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.verify_code.VerifyCodeUtil;
import com.ibm.old.v1.app.t.service.AppVerifyAccountService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.RandomTool;

import java.util.Date;
/**
 * @Description: 用户token
 * @Author: zjj
 * @Date: 2019-05-05 10:49
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppTokenController {
	/**
	 * 获取一个用户session
	 *
	 * @return 用户session
	 */
	public String findNewAppSession() throws Exception {
		String sessionId = RandomTool.getNumLetter32();
		AppVerifyAccountService verifyAccountDService = new AppVerifyAccountService();
		AppVerifyAccountT verifyAccountT = new AppVerifyAccountT();
		verifyAccountT.setType(ChannelTypeEnum.PC.name());
		verifyAccountT.setSessionId(sessionId);
		verifyAccountT.setCreateTime(new Date());
		verifyAccountT.setCreateTimeLong(verifyAccountT.getCreateTime().getTime());
		verifyAccountT.setUpdateTime(new Date());
		verifyAccountT.setUpdateTimeLong(verifyAccountT.getUpdateTime().getTime());
		verifyAccountT.setState(IbmStateEnum.OPEN.name());
		verifyAccountDService.save(verifyAccountT);
		return sessionId;
	}

	/**
	 * 生成验证码
	 *
	 * @param sessionId sessionId
	 */
	public String verifyCode(String sessionId) throws Exception {
		String code = VerifyCodeUtil.findVerifyCode();
		AppVerifyAccountService verifyAccountService = new AppVerifyAccountService();
		AppVerifyAccountT verifyAccountT = verifyAccountService.findBySessionId(sessionId);
		verifyAccountT.setCode(code);
		verifyAccountT.setUpdateTime(new Date());
		verifyAccountT.setUpdateTimeLong(verifyAccountT.getUpdateTime().getTime());
		verifyAccountService.update(verifyAccountT);
		return code;
	}

	/**
	 * 获取验证码
	 *
	 * @param sessionId 服务器sessionId
	 * @return 验证码
	 */
	public String findVerifyCode(String sessionId) throws Exception {
		AppVerifyAccountService verifyAccountService = new AppVerifyAccountService();
		return verifyAccountService.findAppVerifyCode(sessionId);
	}
}
