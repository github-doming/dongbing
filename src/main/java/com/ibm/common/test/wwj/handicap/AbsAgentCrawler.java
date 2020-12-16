package com.ibm.common.test.wwj.handicap;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;

import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口代理爬虫 预实现类
 * @Author: Dongming
 * @Date: 2019-11-22 10:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class AbsAgentCrawler<T extends AbsHandicapGrab & GrabAgent> extends AbsHandicapCrawler<T> implements CrawlerAgent {

	protected DataAgent agent;

	public AbsAgentCrawler() {
		super.customerType(IbmTypeEnum.AGENT);
	}

	/**
	 * 获取会员账号信息
	 *
	 * @return 会员账号信息
	 */
	protected abstract JsonResultBeanPlus memberListInfo();

	/**
	 * 如果没有数据-或者数据为空则刷新数据
	 */
	@Override public List<Map<String,String>> memberList(boolean refresh) throws HandicapException {
		if (agent.customer() == null){
			throw new HandicapException(getMsgTitle()+"用户尚未登录，无法获取会员列表信息");
		}
		if ( agent.memberList() == null || refresh) {
			//获取用户信息
			JsonResultBeanPlus bean = memberListInfo();
			//获取用户信息失败，返回空
			if (!bean.isSuccess()) {
				return null;
			}
		}
		return agent.memberList();
	}

	/**
	 * 登录成功后创建代理对象-存储客户信息
	 */
	@Override protected void customerHold(DataCustomer customer) {
		agent = new DataAgent();
		agent.customer(customer);
	}

	/**
	 * 代理信息
	 */
	protected class DataAgent  {
		private DataCustomer customer;
		private List<Map<String,String>> memberList;

		public DataCustomer customer() {
			return customer;
		}
		public DataAgent customer(DataCustomer customer) {
			this.customer = customer;
			return this;
		}
		public List<Map<String,String>> memberList() {
			return memberList;
		}
		public DataAgent memberList(List<Map<String,String>> memberList) {
			this.memberList = memberList;
			return this;
		}
	}
}
