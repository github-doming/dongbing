package com.cloud.lottery.cloud_lottery_gxklsf.service;

import com.cloud.lottery.cloud_lottery_gxklsf.entity.CloudLotteryGxklsf;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;

import java.util.ArrayList;
import java.util.List;

/**
* 云服务_开奖_广西快乐十分 服务类
 * @author Robot
 */
public class CloudLotteryGxklsfService extends BaseServiceProxy {

	/**
	 * 保存云服务_开奖_广西快乐十分 对象数据
	 * @param entity CloudLotteryGxklsf对象数据
	 */
	public String save(CloudLotteryGxklsf entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除cloud_lottery_gxklsf 的 CLOUD_LOTTERY_GXKLSF_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update cloud_lottery_gxklsf set state_='DEL' where CLOUD_LOTTERY_GXKLSF_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除CLOUD_LOTTERY_GXKLSF_ID_主键id数组的数据
	 * @param idArray 要删除 cloud_lottery_gxklsf 的 CLOUD_LOTTERY_GXKLSF_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update cloud_lottery_gxklsf set state_='DEL' where CLOUD_LOTTERY_GXKLSF_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 cloud_lottery_gxklsf  的 CLOUD_LOTTERY_GXKLSF_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from cloud_lottery_gxklsf where CLOUD_LOTTERY_GXKLSF_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除CLOUD_LOTTERY_GXKLSF_ID_主键id数组的数据
	 * @param idArray 要删除cloud_lottery_gxklsf 的 CLOUD_LOTTERY_GXKLSF_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from cloud_lottery_gxklsf where CLOUD_LOTTERY_GXKLSF_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CloudLotteryGxklsf实体信息
	 * @param entity 云服务_开奖_广西快乐十分 实体
	 */
	public void update(CloudLotteryGxklsf entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据cloud_lottery_gxklsf表主键查找 云服务_开奖_广西快乐十分 实体
	 * @param id cloud_lottery_gxklsf 主键
	 * @return 云服务_开奖_广西快乐十分 实体
	 */
	public CloudLotteryGxklsf find(String id) throws Exception {
		return (CloudLotteryGxklsf) this.dao.find(CloudLotteryGxklsf. class,id);

	}

	/**
	 * 根据期数获取开奖信息
	 *
	 * @param period 期数
	 * @return 开奖信息列表
	 * 返回类型 CloudLotteryGxklsf
	 */
	public CloudLotteryGxklsf findByPeriod(String period) throws Exception {
		String sql = "SELECT * FROM cloud_lottery_gxklsf  where PERIOD_ = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(period+"");
		parameterList.add(StateEnum.DEL.name());
		return (CloudLotteryGxklsf) super.dao.findObject(CloudLotteryGxklsf.class,sql,parameterList);
	}
}
