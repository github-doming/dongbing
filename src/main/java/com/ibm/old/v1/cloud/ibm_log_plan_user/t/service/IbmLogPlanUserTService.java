package com.ibm.old.v1.cloud.ibm_log_plan_user.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.entity.IbmLogPlanUserT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.*;

public class IbmLogPlanUserTService extends BaseService {

	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(IbmLogPlanUserT entity) throws Exception {

		return dao.save(entity);
	}
	/**
	 * 
	 * 逻辑删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {

		String sql = "update ibm_log_plan_user set state_='DEL' where IBM_LOG_PLAN_USER_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 逻辑删除所有
	 * 
	 * @param idArray
	 * @throws Exception
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update ibm_log_plan_user set state_='DEL' where IBM_LOG_PLAN_USER_ID_ in("
					+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 
	 * 物理删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {

		String sql = "delete from ibm_log_plan_user where IBM_LOG_PLAN_USER_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 
	 * 物理删除所有
	 * 
	 * @param idArray
	 * @throws Exception
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from ibm_log_plan_user where IBM_LOG_PLAN_USER_ID_ in(" + stringBuffer.toString()
					+ ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(IbmLogPlanUserT entity) throws Exception {
		dao.update(entity);
	}
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IbmLogPlanUserT find(String id) throws Exception {
		return (IbmLogPlanUserT) this.dao.find(IbmLogPlanUserT.class, id);

	}

	/**
	 * 
	 * 分页
	 * 
	 * @param pageSize
	 * @param sortFieldName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {

		String sql_count ;
		String sql ;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM ibm_log_plan_user where state_!='DEL'";
			sql = "SELECT * FROM ibm_log_plan_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM ibm_log_plan_user where state_!='DEL'";
			sql = "SELECT * FROM ibm_log_plan_user  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}

		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count);

		return basePage;
	}

	/**
	 * 
	 * 分页
	 * 
	 * @param pageSize
	 * @param sortFieldName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<IbmLogPlanUserT> findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count ;
		String sql ;

		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM ibm_log_plan_user where state_!='DEL'";
			sql = "SELECT * FROM ibm_log_plan_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM ibm_log_plan_user where state_!='DEL'";
			sql = "SELECT * FROM ibm_log_plan_user  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}

		PageCoreBean<IbmLogPlanUserT> basePage = dao.page(IbmLogPlanUserT.class, sql, null, pageIndex.intValue(),
				pageSize.intValue(), sql_count);
		return basePage;
	}
	
	/**
	 * 批量增加重置方案日志信息
	 * 
	 * @param planMap
	 * @param appUserId
	 * @throws SQLException
	 */
	public void save(Map<Object,Object> planMap,String gameId, String appUserId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		Date nowTime=new Date();
		sql.append("insert into ibm_log_plan_user(IBM_LOG_PLAN_USER_ID_,PLAN_ID_,APP_USER_ID_,GAME_ID_,"
				+ "HANDLE_TYPE_,CREATE_TIME_,CREATE_TIME_LONG_,STATE_) values ");
		for (Map.Entry<Object,Object> entry:planMap.entrySet()) {
			String ibmHmPlanGroupBetItemId = UUID.randomUUID().toString().replace("-", "");
			sql.append(" (?,?,?,?,?,?,?,?),");
			parameters.add(ibmHmPlanGroupBetItemId);
			parameters.add(entry.getKey());
			parameters.add(appUserId);
			parameters.add(gameId);
			parameters.add("ReplayPlan");
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbmStateEnum.OPEN.name());			
		}
		sql.deleteCharAt(sql.length() - 1);
		dao.execute(sql.toString(), parameters);

	}
	/**
	 * 添加重置所有方案日志
	 * @param planMap
	 * @param appUserId
	 * @throws SQLException
	 */
	public void save(Map<Object, Object> planMap, String appUserId) throws SQLException {
		List<Object> parameters = new ArrayList<>();
		Date nowTime=new Date();
		String sql="insert into ibm_log_plan_user(IBM_LOG_PLAN_USER_ID_,APP_USER_ID_,"
				+ "HANDLE_TYPE_,CREATE_TIME_,CREATE_TIME_LONG_,STATE_,DESC_) values (?,?,?,?,?,?,?)";
		String ibmHmPlanGroupBetItemId = UUID.randomUUID().toString().replace("-", "");
		parameters.add(ibmHmPlanGroupBetItemId);
		parameters.add(appUserId);
		parameters.add("ReplayAllPlan");
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(planMap.keySet().toString());
		dao.execute(sql, parameters);
	}
}
