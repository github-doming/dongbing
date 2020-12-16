package com.cloud.lottery.cloud_lottery_jsssc.service;

import c.x.platform.root.common.service.BaseService;
import com.cloud.lottery.cloud_lottery_jsssc.entity.CloudLotteryJsssc;
import org.doming.core.enums.StateEnum;

import java.util.ArrayList;
import java.util.List;

/**
* 云服务_开奖_急速时时彩 服务类
 * @author Robot
 */
public class CloudLotteryJssscService extends BaseService {

	/**
	 * 保存云服务_开奖_急速时时彩 对象数据
	 * @param entity CloudLotteryJsssc对象数据
	 */
	public String save(CloudLotteryJsssc entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除cloud_lottery_jsssc 的 CLOUD_LOTTERY_JSSSC_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update cloud_lottery_jsssc set state_='DEL' where CLOUD_LOTTERY_JSSSC_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除CLOUD_LOTTERY_JSSSC_ID_主键id数组的数据
	 * @param idArray 要删除 cloud_lottery_jsssc 的 CLOUD_LOTTERY_JSSSC_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update cloud_lottery_jsssc set state_='DEL' where CLOUD_LOTTERY_JSSSC_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 cloud_lottery_jsssc  的 CLOUD_LOTTERY_JSSSC_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from cloud_lottery_jsssc where CLOUD_LOTTERY_JSSSC_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除CLOUD_LOTTERY_JSSSC_ID_主键id数组的数据
	 * @param idArray 要删除cloud_lottery_jsssc 的 CLOUD_LOTTERY_JSSSC_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from cloud_lottery_jsssc where CLOUD_LOTTERY_JSSSC_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CloudLotteryJsssc实体信息
	 * @param entity 云服务_开奖_急速时时彩 实体
	 */
	public void update(CloudLotteryJsssc entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据cloud_lottery_jsssc表主键查找 云服务_开奖_急速时时彩 实体
	 * @param id cloud_lottery_jsssc 主键
	 * @return 云服务_开奖_急速时时彩 实体
	 */
	public CloudLotteryJsssc find(String id) throws Exception {
		return (CloudLotteryJsssc) this.dao.find(CloudLotteryJsssc. class,id);

	}

	/**
	 * 根据期数获取开奖信息
	 *
	 * @param period 期数
	 * @return 开奖信息列表
	 * 返回类型 CloudLotteryJs10
	 */
	public CloudLotteryJsssc findByPeriod(Object period,String handicapCode) throws Exception {
		String sql = "SELECT * FROM cloud_lottery_jsssc  where PERIOD_ = ? and HANDICAP_CODE_ = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(period+"");
		parameterList.add(handicapCode);
		parameterList.add(StateEnum.DEL.name());
		return (CloudLotteryJsssc) super.dao.findObject(CloudLotteryJsssc.class,sql,parameterList);
	}

	/**
	 * 根据期数获取开奖信息
	 *
	 * @param period 期数
	 * @return 开奖信息列表
	 * 返回类型 CloudLotteryJs10
	 */
	public CloudLotteryJsssc findByPeriod(Object period) throws Exception {
		String sql = "SELECT * FROM cloud_lottery_jsssc  where PERIOD_ = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(period+"");
		parameterList.add(StateEnum.DEL.name());
		return (CloudLotteryJsssc) super.dao.findObject(CloudLotteryJsssc.class,sql,parameterList);
	}
}
