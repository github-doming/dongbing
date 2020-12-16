package com.cloud.lottery.cloud_lottery_country_10.service;

import com.cloud.lottery.cloud_lottery_country_10.entity.CloudLotteryCountry10;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;

import java.util.ArrayList;
import java.util.List;

/**
* 云服务_开奖_国家赛车 服务类
 * @author Robot
 */
public class CloudLotteryCountry10Service extends BaseServiceProxy {

	/**
	 * 保存云服务_开奖_国家赛车 对象数据
	 * @param entity CloudLotteryCountry10对象数据
	 */
	public String save(CloudLotteryCountry10 entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除cloud_lottery_country_10 的 CLOUD_LOTTERY_COUNTRY_10_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update cloud_lottery_country_10 set state_='DEL' where CLOUD_LOTTERY_COUNTRY_10_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除CLOUD_LOTTERY_COUNTRY_10_ID_主键id数组的数据
	 * @param idArray 要删除 cloud_lottery_country_10 的 CLOUD_LOTTERY_COUNTRY_10_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update cloud_lottery_country_10 set state_='DEL' where CLOUD_LOTTERY_COUNTRY_10_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 cloud_lottery_country_10  的 CLOUD_LOTTERY_COUNTRY_10_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from cloud_lottery_country_10 where CLOUD_LOTTERY_COUNTRY_10_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除CLOUD_LOTTERY_COUNTRY_10_ID_主键id数组的数据
	 * @param idArray 要删除cloud_lottery_country_10 的 CLOUD_LOTTERY_COUNTRY_10_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from cloud_lottery_country_10 where CLOUD_LOTTERY_COUNTRY_10_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CloudLotteryCountry10实体信息
	 * @param entity 云服务_开奖_国家赛车 实体
	 */
	public void update(CloudLotteryCountry10 entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据cloud_lottery_country_10表主键查找 云服务_开奖_国家赛车 实体
	 * @param id cloud_lottery_country_10 主键
	 * @return 云服务_开奖_国家赛车 实体
	 */
	public CloudLotteryCountry10 find(String id) throws Exception {
		return (CloudLotteryCountry10) this.dao.find(CloudLotteryCountry10. class,id);

	}
	/**
	 * 根据期数获取开奖信息
	 *
	 * @param period 期数
	 * @return 开奖信息列表
	 * 返回类型 CloudLotteryCountry10
	 */
	public CloudLotteryCountry10 findByPeriod(Integer period) throws Exception {
		String sql = "SELECT * FROM cloud_lottery_country_10 where PERIOD_ = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(period+"");
		parameterList.add(StateEnum.DEL.name());
		return (CloudLotteryCountry10) dao.findObject(CloudLotteryCountry10.class,sql,parameterList);
	}
}
