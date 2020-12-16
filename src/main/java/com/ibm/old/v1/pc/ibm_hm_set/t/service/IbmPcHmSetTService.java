package com.ibm.old.v1.pc.ibm_hm_set.t.service;

import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Robot
 */
public class IbmPcHmSetTService extends IbmHmSetTService {
	/**
	 * 更新登录时的配置
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param landedTime       定时登陆时间
	 * @param saveAccount      保存账号状态
	 */
	public void updateLoginSave(String handicapMemberId, Date landedTime, String saveAccount) throws Exception {
		String sql = "UPDATE ibm_hm_set set SAVE_ACCOUNT_ = ?,LANDED_TIME_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?"
				+ " WHERE HANDICAP_MEMBER_ID_ = ? AND STATE_ != ?" ;
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(saveAccount);
		parameterList.add(landedTime);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取定时登陆时间
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 登陆时间
	 */
	public String findLandedTime(String handicapMemberId) throws SQLException {
		String sql = "select LANDED_TIME_ from ibm_hm_set where HANDICAP_MEMBER_ID_=? and STATE_= ? " ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("LANDED_TIME_", sql, parameterList);
	}
}
