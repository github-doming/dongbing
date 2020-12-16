package com.cloud.lottery.cloud_lottery_self_10_5.service;

import com.cloud.lottery.cloud_lottery_self_10_2.entity.CloudLotterySelf102;
import com.cloud.lottery.cloud_lottery_self_10_5.entity.CloudLotterySelf105;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;

import java.util.ArrayList;
import java.util.List;

/**
* 云服务_开奖_自有赛车5分彩 服务类
 * @author Robot
 */
public class CloudLotterySelf105Service extends BaseServiceProxy {

	/**
	 * 保存云服务_开奖_自有赛车5分彩 对象数据
	 * @param entity CloudLotterySelf105对象数据
	 */
	public String save(CloudLotterySelf105 entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除cloud_lottery_self_10_5 的 CLOUD_LOTTERY_SELF_10_5_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update cloud_lottery_self_10_5 set state_='DEL' where CLOUD_LOTTERY_SELF_10_5_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CLOUD_LOTTERY_SELF_10_5_ID_主键id数组的数据
	 * @param idArray 要删除 cloud_lottery_self_10_5 的 CLOUD_LOTTERY_SELF_10_5_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update cloud_lottery_self_10_5 set state_='DEL' where CLOUD_LOTTERY_SELF_10_5_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 cloud_lottery_self_10_5  的 CLOUD_LOTTERY_SELF_10_5_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from cloud_lottery_self_10_5 where CLOUD_LOTTERY_SELF_10_5_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CLOUD_LOTTERY_SELF_10_5_ID_主键id数组的数据
	 * @param idArray 要删除cloud_lottery_self_10_5 的 CLOUD_LOTTERY_SELF_10_5_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from cloud_lottery_self_10_5 where CLOUD_LOTTERY_SELF_10_5_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CloudLotterySelf105实体信息
	 * @param entity 云服务_开奖_自有赛车5分彩 实体
	 */
	public void update(CloudLotterySelf105 entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据cloud_lottery_self_10_5表主键查找 云服务_开奖_自有赛车5分彩 实体
	 * @param id cloud_lottery_self_10_5 主键
	 * @return 云服务_开奖_自有赛车5分彩 实体
	 */
	public CloudLotterySelf105 find(String id) throws Exception {
		return dao.find(CloudLotterySelf105.class,id);
	}

	/**
	 * 根据期数获取开奖信息
	 *
	 * @param period 期数
	 * @param handicapCode 盘口编码
	 * @return 开奖信息列表
	 * 返回类型 CloudLotteryPk10T
	 */
	public CloudLotterySelf105 findByPeriod(String period, String handicapCode) throws Exception {
		String sql = "SELECT * FROM cloud_lottery_self_10_5  where PERIOD_ = ? and HANDICAP_CODE_ = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(period);
		parameterList.add(handicapCode);
		parameterList.add(StateEnum.DEL.name());
		return (CloudLotterySelf105) super.dao.findObject(CloudLotterySelf105.class,sql,parameterList);
	}
}
