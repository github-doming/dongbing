package com.ibm.old.v1.cloud.ibm_profit_info.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_profit_info.t.entity.IbmProfitInfoT;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.DateTool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmProfitInfoTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmProfitInfoT对象数据
	 */
	public String save(IbmProfitInfoT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_profit_info的 IBM_PROFIT_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_profit_info set state_='DEL' where IBM_PROFIT_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PROFIT_INFO_ID_主键id数组的数据
	 * @param idArray 要删除ibm_profit_info的 IBM_PROFIT_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_profit_info set state_='DEL' where IBM_PROFIT_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_profit_info的 IBM_PROFIT_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_profit_info where IBM_PROFIT_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PROFIT_INFO_ID_主键id数组的数据
	 * @param idArray 要删除ibm_profit_info的 IBM_PROFIT_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_profit_info where IBM_PROFIT_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmProfitInfoT实体信息
	 * @param entity IbmProfitInfoT实体
	 */
	public void update(IbmProfitInfoT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_profit_info表主键查找IbmProfitInfoT实体
	 * @param id ibm_profit_info 主键
	 * @return IbmProfitInfoT实体
	 */
	public IbmProfitInfoT find(String id) throws Exception {
		return (IbmProfitInfoT) this.dao.find(IbmProfitInfoT. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_profit_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_profit_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmProfitInfoT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmProfitInfoT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_profit_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_profit_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmProfitInfoT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmProfitInfoT数据信息
	 * @return 可用<IbmProfitInfoT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmProfitInfoT. class,sql);
	}
	/**
	 * 清理冗余数据
	 *
	 * @param nowTime  清理时间
	 * @param type     清理类型
	 * @param ruleTime 时间规则
	 */
	public void clearRedundancy(Date nowTime, IbmTypeEnum type, String ruleTime) throws Exception {
		String tableName = "ibm_profit_info";
		if (IbmTypeEnum.VIRTUAL.equals(type)) {
			tableName = "ibm_profit_info_vr";
		}
		String sql = "DELETE FROM ".concat(tableName).concat(" WHERE CREATE_TIME_LONG_< ? ");
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(DateTool.getAfterRule(nowTime, ruleTime).getTime());
		super.dao.execute(sql, parameterList);
	}
}
