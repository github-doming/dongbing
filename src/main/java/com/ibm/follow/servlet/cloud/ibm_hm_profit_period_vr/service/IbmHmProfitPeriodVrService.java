package com.ibm.follow.servlet.cloud.ibm_hm_profit_period_vr.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period_vr.entity.IbmHmProfitPeriodVr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHmProfitPeriodVrService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmHmProfitPeriodVr对象数据
	 */
	public String save(IbmHmProfitPeriodVr entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_hm_profit_period_vr的 IBM_HM_PROFIT_PERIOD_VR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_profit_period_vr set state_='DEL' where IBM_HM_PROFIT_PERIOD_VR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HM_PROFIT_PERIOD_VR_ID_主键id数组的数据
	 * @param idArray 要删除ibm_hm_profit_period_vr的 IBM_HM_PROFIT_PERIOD_VR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_hm_profit_period_vr set state_='DEL' where IBM_HM_PROFIT_PERIOD_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_hm_profit_period_vr的 IBM_HM_PROFIT_PERIOD_VR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_profit_period_vr where IBM_HM_PROFIT_PERIOD_VR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HM_PROFIT_PERIOD_VR_ID_主键id数组的数据
	 * @param idArray 要删除ibm_hm_profit_period_vr的 IBM_HM_PROFIT_PERIOD_VR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_hm_profit_period_vr where IBM_HM_PROFIT_PERIOD_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHmProfitPeriodVr实体信息
	 * @param entity IbmHmProfitPeriodVr实体
	 */
	public void update(IbmHmProfitPeriodVr entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_hm_profit_period_vr表主键查找IbmHmProfitPeriodVr实体
	 * @param id ibm_hm_profit_period_vr 主键
	 * @return IbmHmProfitPeriodVr实体
	 */
	public IbmHmProfitPeriodVr find(String id) throws Exception {
		return (IbmHmProfitPeriodVr) this.dao.find(IbmHmProfitPeriodVr. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_hm_profit_period_vr where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_profit_period_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_profit_period_vr  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmProfitPeriodVr数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmHmProfitPeriodVr数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_profit_period_vr where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_profit_period_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_profit_period_vr  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHmProfitPeriodVr. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_profit_period_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmProfitPeriodVr数据信息
	 * @return 可用<IbmHmProfitPeriodVr>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_profit_period_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmProfitPeriodVr. class,sql);
	}

	/**
	 * 保存盘口会员当期盈亏
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param period           期数
	 * @param profit           盈亏金额
	 * @param funds            投注金额
	 * @param betTotal         投注数
	 * @param nowTime          创建时间
	 */
	public void save(String handicapMemberId,Object period, long profit, long funds, int betTotal,
                     Date nowTime,int profitBetLen,int lossBetLen) throws Exception {
		IbmHmProfitPeriodVr profitPeriod = new IbmHmProfitPeriodVr();
		profitPeriod.setHandicapMemberId(handicapMemberId);
		profitPeriod.setPeriod(period);
		profitPeriod.setProfitT(profit);
		profitPeriod.setBetFundsT(funds);
		profitPeriod.setBetLen(betTotal);
        profitPeriod.setProfitBetLen(profitBetLen);
        profitPeriod.setLossBetLen(lossBetLen);
		profitPeriod.setCreateTime(nowTime);
		profitPeriod.setCreateTimeLong(System.currentTimeMillis());
		profitPeriod.setUpdateTimeLong(System.currentTimeMillis());
		profitPeriod.setState(IbmStateEnum.OPEN.name());
		save(profitPeriod);
	}

    /**
     *
     * @param handicapMemberId
     * @param period
     * @return
     */
    public IbmHmProfitPeriodVr findByHmIdAndPeriod(String handicapMemberId, String period) throws Exception {
        String sql="select * from ibm_hm_profit_period_vr where HANDICAP_MEMBER_ID_=? and PERIOD_=? and STATE_=?";
        List<Object> parameters=new ArrayList<>(3);
        parameters.add(handicapMemberId);
        parameters.add(period);
        parameters.add(IbmStateEnum.OPEN.name());
        return (IbmHmProfitPeriodVr) super.dao.findObject(IbmHmProfitPeriodVr.class,sql,parameters);
    }
}
