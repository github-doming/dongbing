package com.ibm.old.v1.app.t.service;
import all.gen.app_verify_account.t.entity.AppVerifyAccountT;
import all.gen.app_verify_account.t.service.AppVerifyAccountTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.DateTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Description: 验证码服务类
 * @Author: zjj
 * @Date: 2019-05-05 11:16
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppVerifyAccountService extends AppVerifyAccountTService {
	/**
	 * 获取验证码
	 * @param sessionId  验证账户Id
	 * @return 验证码
	 */
	public String findAppVerifyCode(String sessionId) throws SQLException {
		String sql = "SELECT CODE_ FROM `app_verify_account` where SESSION_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(sessionId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("CODE_",sql,parameterList);
	}
	/**
	 *
	 * 通过SessionId查找实体
	 *
	 * @param sessionId SessionId
	 * @return 验证码 实体
	 */
	public AppVerifyAccountT findBySessionId(String sessionId) throws Exception {
		String sql = "SELECT * FROM app_verify_account WHERE SESSION_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(sessionId);
		return (AppVerifyAccountT) super.dao.findObject(AppVerifyAccountT.class, sql, parameterList);
	}

	/**
	 * 清理冗余数据
	 * 	清理五分钟前更新的数据
	 * @param nowTime 清理时间
	 */
	public void clearRedundancy(Date nowTime, String ruleTime) throws Exception {
		String sql = "DELETE FROM `app_verify_account` WHERE UPDATE_TIME_LONG_< ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(DateTool.getAfterRule(nowTime,ruleTime).getTime());
		super.dao.execute(sql,parameterList);
	}
}
