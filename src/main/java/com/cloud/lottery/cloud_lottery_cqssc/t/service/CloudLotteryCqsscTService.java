package com.cloud.lottery.cloud_lottery_cqssc.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.cloud.lottery.cloud_lottery_cqssc.t.entity.CloudLotteryCqsscT;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.doming.core.enums.StateEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class CloudLotteryCqsscTService extends BaseService {

	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity CloudLotteryCqsscT对象数据
	 */
	public String save(CloudLotteryCqsscT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除cloud_lottery_cqssc的 CLOUD_LOTTERY_CQSSC_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update cloud_lottery_cqssc set state_='DEL' where CLOUD_LOTTERY_CQSSC_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除CLOUD_LOTTERY_CQSSC_ID_主键id数组的数据
	 * @param idArray 要删除cloud_lottery_cqssc的 CLOUD_LOTTERY_CQSSC_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update cloud_lottery_cqssc set state_='DEL' where CLOUD_LOTTERY_CQSSC_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除cloud_lottery_cqssc的 CLOUD_LOTTERY_CQSSC_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from cloud_lottery_cqssc where CLOUD_LOTTERY_CQSSC_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除CLOUD_LOTTERY_CQSSC_ID_主键id数组的数据
	 * @param idArray 要删除cloud_lottery_cqssc的 CLOUD_LOTTERY_CQSSC_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from cloud_lottery_cqssc where CLOUD_LOTTERY_CQSSC_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CloudLotteryCqsscT实体信息
	 * @param entity CloudLotteryCqsscT实体
	 */
	public void update(CloudLotteryCqsscT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据cloud_lottery_cqssc表主键查找CloudLotteryCqsscT实体
	 * @param id cloud_lottery_cqssc 主键
	 * @return CloudLotteryCqsscT实体
	 */
	public CloudLotteryCqsscT find(String id) throws Exception {
		return (CloudLotteryCqsscT) this.dao.find(CloudLotteryCqsscT. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM cloud_lottery_cqssc where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM cloud_lottery_cqssc  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM cloud_lottery_cqssc  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页CloudLotteryCqsscT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页CloudLotteryCqsscT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM cloud_lottery_cqssc where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM cloud_lottery_cqssc  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM cloud_lottery_cqssc  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(CloudLotteryCqsscT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM cloud_lottery_cqssc  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用CloudLotteryCqsscT数据信息
	 * @return 可用<CloudLotteryCqsscT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM cloud_lottery_cqssc  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(CloudLotteryCqsscT. class,sql);
	}

	/**
	 * 根据期数获取开奖信息
	 *
	 * @param period 期数
	 * @return 开奖信息列表
	 * 返回类型 CloudLotteryCqsscT
	 */
	public CloudLotteryCqsscT findByPeriod(String period) throws Exception {
		String sql = "SELECT * FROM cloud_lottery_cqssc  where PERIOD_ = ? and state_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(period.toString());
		parameterList.add(StateEnum.DEL.name());
		return (CloudLotteryCqsscT) super.dao.findObject(CloudLotteryCqsscT.class,sql,parameterList);
	}
}
