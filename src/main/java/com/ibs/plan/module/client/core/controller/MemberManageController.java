package com.ibs.plan.module.client.core.controller;

import com.common.handicap.crawler.entity.MemberUser;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.client.ibsc_exist_hm.entity.IbscExistHm;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import com.ibs.plan.module.client.ibsc_handicap_member.entity.IbscHandicapMember;
import com.ibs.plan.module.client.ibsc_handicap_member.service.IbscHandicapMemberService;
import com.ibs.plan.module.client.ibsc_hm_info.entity.IbscHmInfo;
import com.ibs.plan.module.client.ibsc_hm_info.service.IbscHmInfoService;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
/**
 * 会员管理控制器
 *
 * @Author: null
 * @Date: 2020-05-14 14:16
 * @Version: v1.0
 */
public class MemberManageController {
	private Date nowTime;
	private String existId;

	public MemberManageController() {
		this(new Date());
	}
	public MemberManageController(Date nowTime) {
		this.nowTime = nowTime;
	}

	public MemberManageController existId(String existId) {
		this.existId = existId;
		return this;
	}

	/**
	 * 查找个人存在主键 - 找到使用，未找到，新增
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param handicapCode     盘口编码
	 * @return 存在主键
	 */
	public String exist(String handicapMemberId, String handicapCode) throws Exception {
		IbscExistHmService existHmService = new IbscExistHmService();
		String existId = existHmService.findIdByHmId(handicapMemberId);
		if (StringTool.isEmpty(existId)) {
			IbscExistHm existHm = new IbscExistHm();
			existHm.setIbscExistHmId(this.existId);
			existHm.setHandicapMemberId(handicapMemberId);
			existHm.setHandicapCode(handicapCode);
			existHm.setHmState(IbsStateEnum.OPEN.name());
			existHm.setCreateTime(nowTime);
			existHm.setCreateTimeLong(System.currentTimeMillis());
			existHm.setUpdateTime(nowTime);
			existHm.setUpdateTimeLong(System.currentTimeMillis());
			existHm.setState(IbsStateEnum.OPEN.name());
			existId = existHmService.save(existHm);
			this.existId = existId;
		}
		return existId;
	}

	/**
	 * 用户登录
	 */
	public void login() throws SQLException {
		IbscExistHmService existHmService = new IbscExistHmService();
		existHmService.login(existId, nowTime);
	}
	/**
	 * 保存盘口会员
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param handicapUrl      盘口地址
	 * @param handicapCaptcha  盘口验证码
	 * @param memberAccount    会员账户
	 * @param memberPassword   会员密码
	 */
	public void saveHandicapMember(String handicapMemberId, String handicapUrl, String handicapCaptcha,
			String memberAccount, String memberPassword) throws Exception {
		IbscHandicapMemberService handicapMemberService = new IbscHandicapMemberService();
		IbscHandicapMember handicapMember = handicapMemberService.findByExistId(existId);
		if (Objects.isNull(handicapMember)) {
			handicapMember = new IbscHandicapMember();
			handicapMember.setExistHmId(existId);
			handicapMember.setHandicapMemberId(handicapMemberId);
			handicapMember.setMemberAccount(memberAccount);
			handicapMember.setMemberPassword(memberPassword);
			handicapMember.setHandicapUrl(handicapUrl);
			handicapMember.setHandicapCaptcha(handicapCaptcha);
			handicapMember.setCreateTime(nowTime);
			handicapMember.setCreateTimeLong(System.currentTimeMillis());
			handicapMember.setUpdateTime(nowTime);
			handicapMember.setUpdateTimeLong(System.currentTimeMillis());
			handicapMember.setState(IbsStateEnum.OPEN.name());
			handicapMember.setDesc("用户登录，新增盘口会员");
			handicapMemberService.save(handicapMember);
		} else {
			handicapMember.setHandicapMemberId(handicapMemberId);
			handicapMember.setMemberAccount(memberAccount);
			handicapMember.setMemberPassword(memberPassword);
			handicapMember.setHandicapUrl(handicapUrl);
			handicapMember.setHandicapCaptcha(handicapCaptcha);
			handicapMember.setUpdateTime(nowTime);
			handicapMember.setUpdateTimeLong(System.currentTimeMillis());
			handicapMember.setDesc("用户登录，修改盘口会员");
			handicapMemberService.update(handicapMember);
		}
	}

	/**
	 * 保存用户信息
	 *
	 * @param userInfo 用户信息
	 */
	public void saveUserInfo(MemberUser userInfo) throws Exception {
		IbscHmInfoService hmInfoService = new IbscHmInfoService();
		IbscHmInfo hmInfo = hmInfoService.findByExistId(existId);
		if (Objects.isNull(hmInfo)) {
			hmInfo = new IbscHmInfo();
			hmInfo.setExistHmId(existId);
			if (userInfo != null) {
				hmInfo.setMemberAccount(userInfo.memberAccount());
				hmInfo.setCreditQuotaT(NumberTool.longValueT(userInfo.creditQuota()));
				hmInfo.setUsedAmountT(NumberTool.longValueT(userInfo.usedAmount()));
				hmInfo.setProfitAmountT(NumberTool.longValueT(userInfo.profitAmount()));
			}
			hmInfo.setCreateTime(nowTime);
			hmInfo.setCreateTimeLong(System.currentTimeMillis());
			hmInfo.setUpdateTime(nowTime);
			hmInfo.setUpdateTimeLong(System.currentTimeMillis());
			hmInfo.setState(IbsStateEnum.OPEN.name());
			hmInfo.setDesc("用户登录，新增盘口会员信息");
			hmInfoService.save(hmInfo);
		} else {
			if (userInfo != null) {
				hmInfo.setMemberAccount(userInfo.memberAccount());
				hmInfo.setCreditQuotaT(NumberTool.longValueT(userInfo.creditQuota()));
				hmInfo.setUsedAmountT(NumberTool.longValueT(userInfo.usedAmount()));
				hmInfo.setProfitAmountT(NumberTool.longValueT(userInfo.profitAmount()));
			}
			hmInfo.setUpdateTime(nowTime);
			hmInfo.setUpdateTimeLong(System.currentTimeMillis());
			hmInfo.setState(IbsStateEnum.OPEN.name());
			hmInfo.setDesc("用户登录，新增盘口会员信息");
			hmInfoService.update(hmInfo);
		}

	}
}
