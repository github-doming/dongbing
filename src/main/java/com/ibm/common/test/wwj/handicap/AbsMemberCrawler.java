package com.ibm.common.test.wwj.handicap;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;

import java.util.Map;
/**
 * @Description: 盘口会员爬虫 预实现类
 * @Author: Dongming
 * @Date: 2019-11-22 14:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class AbsMemberCrawler<T extends AbsHandicapGrab & GrabMember> extends AbsHandicapCrawler<T>
		implements CrawlerMember {

	protected DataMember member;

	public AbsMemberCrawler() {
		super.customerType(IbmTypeEnum.MEMBER);
	}

	/**
	 * 用户基本信息
	 *
	 * @return 用户信息
	 */
	protected abstract JsonResultBeanPlus userInfo();

	@Override public Map<String, String> userInfo(boolean refresh) throws HandicapException {
		if (member.customer() == null) {
			throw new HandicapException(getMsgTitle() + "用户尚未登录，无法获取用户信息");
		}
		if (member.userMap() == null || refresh) {
			//获取用户信息
			JsonResultBeanPlus bean = userInfo();
			//获取用户信息失败，返回空
			if (!bean.isSuccess()) {
				return null;
			}
		}
		return member.userMap();
	}

	/**
	 * 登录成功后创建会员对象-存储客户信息
	 */
	@Override protected void customerHold(DataCustomer customer) {
		member = new DataMember();
		member.customer(customer);
	}
	/**
	 * 会员信息
	 */
	protected class DataMember {
		DataCustomer customer;
		Map<String, String> userMap;

		Map<GameUtil.Code, Map<String, Object>> oddsMap;

		public DataCustomer customer() {
			return customer;
		}
		public DataMember customer(DataCustomer customer) {
			this.customer = customer;
			return this;
		}

		public Map<String, String> userMap() {
			return userMap;
		}
		public DataMember userMap(Map<String, String> userMap) {
			this.userMap = userMap;
			return this;
		}
		public Map<GameUtil.Code, Map<String, Object>> oddsMap() {
			return oddsMap;
		}
		public DataMember oddsMap(Map<GameUtil.Code, Map<String, Object>> oddsMap) {
			this.oddsMap = oddsMap;
			return this;
		}
	}

}
