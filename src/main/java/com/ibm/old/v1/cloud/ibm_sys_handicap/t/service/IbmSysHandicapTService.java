package com.ibm.old.v1.cloud.ibm_sys_handicap.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_sys_handicap.t.entity.IbmSysHandicapT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmSysHandicapTService extends BaseService {

	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmSysHandicapT对象数据
	 */
	public String save(IbmSysHandicapT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_sys_handicap的 IBM_SYS_HANDICAP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_handicap set state_='DEL' where IBM_SYS_HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_HANDICAP_ID_主键id数组的数据
	 * @param idArray 要删除ibm_sys_handicap的 IBM_SYS_HANDICAP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_sys_handicap set state_='DEL' where IBM_SYS_HANDICAP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_sys_handicap的 IBM_SYS_HANDICAP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_handicap where IBM_SYS_HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_HANDICAP_ID_主键id数组的数据
	 * @param idArray 要删除ibm_sys_handicap的 IBM_SYS_HANDICAP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_handicap where IBM_SYS_HANDICAP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysHandicapT实体信息
	 * @param entity IbmSysHandicapT实体
	 */
	public void update(IbmSysHandicapT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_handicap表主键查找IbmSysHandicapT实体
	 * @param id ibm_sys_handicap 主键
	 * @return IbmSysHandicapT实体
	 */
	public IbmSysHandicapT find(String id) throws Exception {
		return (IbmSysHandicapT) this.dao.find(IbmSysHandicapT. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_sys_handicap where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_sys_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_sys_handicap  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmSysHandicapT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmSysHandicapT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_sys_handicap where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_sys_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_sys_handicap  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmSysHandicapT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_sys_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmSysHandicapT数据信息
	 * @return 可用<IbmSysHandicapT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_sys_handicap  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmSysHandicapT. class,sql);
	}
	/**
	 * 更新上次校验时间
	 * @param handicapCode	盘口编码
	 */
	public void updateLastCheckTime(String handicapCode,String className) throws SQLException {
		String sql="update ibm_sys_handicap set DESC_=?, LAST_CHECK_TIME_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where"
				+ " HANDICAP_CODE_=? and STATE_!=?";
		List<Object> parameters = new ArrayList<>(5);
		Date nowTime=new Date();
		parameters.add(className+"更新上次校验时间");
		parameters.add(nowTime.getTime());
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		parameters.add(handicapCode);
		parameters.add(IbmStateEnum.DEL.name());
		dao.execute(sql,parameters);
	}
}
