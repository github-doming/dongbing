package com.ibm.connector.service.user;
import all.gen.app_verify_account.t.entity.AppVerifyAccountT;
import all.gen.app_verify_account.t.service.AppVerifyAccountTService;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.verify_code.VerifyCodeUtil;
import com.ibm.common.enums.IbmStateEnum;
import org.doming.core.tools.RandomTool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Description: 验证码服务类
 * @Author: Dongming
 * @Date: 2019-08-28 16:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AppVerifyAccountService extends AppVerifyAccountTService {

	/**
	 * 创建一个新的会话
	 *
	 * @param channelType 会话类型
	 * @return 会话id
	 */
	public String newAppSessionByType(ChannelTypeEnum channelType) throws Exception {
		Date nowTime = new Date();
		String sessionId = RandomTool.getNumLetter32();
		AppVerifyAccountT verifyAccount = new AppVerifyAccountT();
		verifyAccount.setType(channelType.name());
		verifyAccount.setSessionId(sessionId);
		verifyAccount.setCreateTime(nowTime);
		verifyAccount.setCreateTimeLong(System.currentTimeMillis());
		verifyAccount.setUpdateTime(nowTime);
		verifyAccount.setUpdateTimeLong(System.currentTimeMillis());
		verifyAccount.setState(IbmStateEnum.OPEN.name());
		super.save(verifyAccount);
		return sessionId;
	}

	/**
	 * 通过SessionId查找实体
	 *
	 * @param sessionId SessionId
	 * @param type      会话类型
	 * @return 验证码 实体
	 */
	public AppVerifyAccountT findBySessionId(String sessionId, ChannelTypeEnum type) throws Exception {
		String sql = "SELECT * FROM app_verify_account WHERE SESSION_ID_ = ? and TYPE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(sessionId);
		parameterList.add(type.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return (AppVerifyAccountT) super.dao.findObject(AppVerifyAccountT.class, sql, parameterList);
	}
	/**
	 * 创建一个新的验证码
	 *
	 * @param verifyAccount 验证码 实体
	 * @return 验证码
	 */
	private String newVerifyCode(AppVerifyAccountT verifyAccount) throws Exception {
		String verifyCode = VerifyCodeUtil.findVerifyCode();
		verifyAccount.setCode(verifyCode);
		verifyAccount.setUpdateTime(new Date());
		verifyAccount.setUpdateTimeLong(System.currentTimeMillis());
		super.update(verifyAccount);
		return verifyCode;
	}

	/**
	 * 获取一个新的验证码code
	 *
	 * @param sessionId SessionId
	 * @param channelType      会话类型
	 * @return 验证码code
	 */
	public String newVerifyCode(String sessionId, ChannelTypeEnum channelType) throws Exception {
		AppVerifyAccountT verifyAccount = findBySessionId(sessionId, channelType);
		if (verifyAccount == null) {
			log.error(String.format("会话：%s,不存在类型为：%s,的账户验证信息", sessionId, channelType.getMsg()));
			return null;
		}
		return newVerifyCode(verifyAccount);
	}

	/**
	 * 获取验证码
	 *
	 * @param sessionId SessionId
	 * @param channelType      会话类型
	 * @return 验证码
	 */
	public String findVerifyCode(String sessionId, ChannelTypeEnum channelType) throws Exception {
		AppVerifyAccountT verifyAccount = findBySessionId(sessionId, channelType);
		String verifyCode = verifyAccount.getCode();
		newVerifyCode(verifyAccount);
		return verifyCode;
	}
}
