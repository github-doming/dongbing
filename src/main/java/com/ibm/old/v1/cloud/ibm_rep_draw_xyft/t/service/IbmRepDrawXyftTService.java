package com.ibm.old.v1.cloud.ibm_rep_draw_xyft.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.google.common.collect.ImmutableList;
import com.ibm.old.v1.cloud.ibm_rep_draw_xyft.t.entity.IbmRepDrawXyftT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmRepDrawXyftTService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmRepDrawXyftT对象数据
	 */
	public String save(IbmRepDrawXyftT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_rep_draw_xyft的 IBM_REP_DRAW_XYFT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_rep_draw_xyft set state_='DEL' where IBM_REP_DRAW_XYFT_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_REP_DRAW_XYFT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_rep_draw_xyft的 IBM_REP_DRAW_XYFT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_rep_draw_xyft set state_='DEL' where IBM_REP_DRAW_XYFT_ID_ in(" + stringBuilder
					.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_rep_draw_xyft的 IBM_REP_DRAW_XYFT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_rep_draw_xyft where IBM_REP_DRAW_XYFT_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_REP_DRAW_XYFT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_rep_draw_xyft的 IBM_REP_DRAW_XYFT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_rep_draw_xyft where IBM_REP_DRAW_XYFT_ID_ in(" + stringBuilder.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmRepDrawXyftT实体信息
	 *
	 * @param entity IbmRepDrawXyftT实体
	 */
	public void update(IbmRepDrawXyftT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_rep_draw_xyft表主键查找IbmRepDrawXyftT实体
	 *
	 * @param id ibm_rep_draw_xyft 主键
	 * @return IbmRepDrawXyftT实体
	 */
	public IbmRepDrawXyftT find(String id) throws Exception {
		return (IbmRepDrawXyftT) this.dao.find(IbmRepDrawXyftT.class, id);

	}

	/**
	 * 获取分页Map数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_rep_draw_xyft where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_rep_draw_xyft  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_rep_draw_xyft  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmRepDrawXyftT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmRepDrawXyftT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_rep_draw_xyft where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_rep_draw_xyft  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_rep_draw_xyft  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmRepDrawXyftT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_rep_draw_xyft  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmRepDrawXyftT数据信息
	 *
	 * @return 可用<IbmRepDrawXyftT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_rep_draw_xyft  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findObjectList(IbmRepDrawXyftT.class, sql);
	}

	/**
	 * 获取开奖号码列表
	 *
	 * @param periods 期数数组
	 * @return 开奖号码列表
	 */
	public Map<Object, Object> listDrawNumber(String[] periods) throws SQLException {
		String sql = "SELECT period_ as key_,DRAW_NUMBER_ as value_ FROM ibm_rep_draw_xyft  where state_ !='DEL'" ;
		StringBuilder sqlBuilder = new StringBuilder(" and period_ in ( ");
		List<Object> parameterList = new ArrayList<>(periods.length);
		for (String period : periods) {
			sqlBuilder.append("?,");
			parameterList.add(period);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(")  order by period_ desc ");
		sql += sqlBuilder.toString();
		return super.findKVMap(sql, parameterList);
	}
	/**
	 * 获取开奖号码列表
	 *
	 * @param period 期数
	 * @return 开奖号码
	 */
	public String findDrawNumber(String period) throws SQLException {
		String sql = "SELECT DRAW_NUMBER_ FROM ibm_rep_draw_xyft  where period_ = ? and state_ != ? " ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("DRAW_NUMBER_",sql,parameterList);
	}

	/**
	 * 查询该期数是否存在数据
	 *
	 * @param period 期数
	 * @return 存在true
	 */
	public boolean isExist(String period) throws SQLException {
		String sql = "SELECT DRAW_TIME_ FROM ibm_rep_draw_xyft  where PERIOD_ = ? and state_ = ?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(period);
		parameters.add(IbmStateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.dao.findMap(sql, parameters));
	}
	/**
	 * 根据期数获取开奖数据
	 *
	 * @param basePeriod 期数
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findDrawResultByPeriod(String basePeriod) throws SQLException {
		String sql = "SELECT PERIOD_,DRAW_NUMBER_ FROM ibm_rep_draw_xyft  where state_ !='DEL' and PERIOD_=? LIMIT 1" ;
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(basePeriod);
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 获取冷热数据
	 *IbmHandicapEditAction
	 * @param drawTime 开奖时间
	 * @return 冷热数据
	 */
	public String[][] findHotAndCold(long drawTime) throws SQLException {
		String sqlFormat = " SELECT %s FROM `ibm_rep_draw_xyft` WHERE DRAW_TIME_LONG_  <= ? "
				+ " ORDER BY DRAW_TIME_LONG_ DESC LIMIT 100" ;
		String keyFormat = "P%d_NUMBER_" ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(drawTime);

		//包含数组
		List<String> number = ImmutableList.<String>builder().add("1").add("2").add("3").add("4").add("5").add("6")
				.add("7").add("8").add("9").add("10").build();

		String[][] hotAndCold = new String[10][10];
		//p1-p10
		for (int i = 0; i < 10; i++) {
			String key = String.format(keyFormat, i + 1);
			List<String> pNumbers = super.dao.findStringList(key, String.format(sqlFormat, key), parameterList);
			if (pNumbers.size() < 10) {
				return null;
			}
			List<String> numberCopy = new ArrayList<>(number);
			int j = 0;
			for (String pNumber : pNumbers) {
				//移除的只剩下最后一个
				if (numberCopy.size() == 1) {
					hotAndCold[i][j] = numberCopy.get(0);
					break;
				}
				if (numberCopy.contains(pNumber)) {
					hotAndCold[i][j++] = pNumber;
					numberCopy.remove(pNumber);
				}
			}
		}
		return hotAndCold;
	}
}
