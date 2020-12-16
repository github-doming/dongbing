package com.ibm.old.v1.cloud.ibm_role.w.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_role.w.entity.IbmRoleW;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class IbmRoleWService extends BaseService {

	/**
	 * 保存
	 *
	 * @param entity
	 * @throws Exception
	 */
	public String save(IbmRoleW entity) throws Exception {

		return dao.save(entity);
	}
	/**
	 * 逻辑删除
	 *
	 * @param id
	 * @throws Exception
	 */
	public int del(String id) throws Exception {

		String sql = "update ibm_role set state_='DEL' where IBM_ROLE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		return dao.execute(sql, parameterList);
	}
	/**
	 * 逻辑删除所有
	 *
	 * @param idArray
	 * @throws Exception
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_role set state_='DEL' where IBM_ROLE_ID_ in(" + stringBuilder.toString() + ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 物理删除
	 *
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {

		String sql = "delete from ibm_role where IBM_ROLE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除所有
	 *
	 * @param idArray
	 * @throws Exception
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_role where IBM_ROLE_ID_ in(" + stringBuilder.toString() + ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 更新
	 *
	 * @param entity
	 * @throws Exception
	 */
	public void update(IbmRoleW entity) throws Exception {
		dao.update(entity);
	}
	/**
	 * 查找实体
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IbmRoleW find(String id) throws Exception {
		return (IbmRoleW) this.dao.find(IbmRoleW.class, id);

	}

	/**
	 * 分页
	 *
	 * @param sortFieldName
	 * @param sortOrderName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {

		String sqlCount;
		String sql;
		ArrayList<Object> parameters = new ArrayList<>();
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM ibm_role where state_!='DEL'";
			sql = "SELECT * FROM ibm_role  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM ibm_role where state_!='DEL'";
			sql = "SELECT * FROM ibm_role  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}

		return dao.page(sql, parameters, pageIndex, pageSize, sqlCount);
	}
	/**
	 * 分页
	 *
	 * @param sortFieldName
	 * @param sortOrderName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<IbmRoleW> findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount;
		String sql;

		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM ibm_role where state_!='DEL'";
			sql = "SELECT * FROM ibm_role  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM ibm_role where state_!='DEL'";
			sql = "SELECT * FROM ibm_role  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}

		return dao.page(IbmRoleW.class, sql, null, pageIndex.intValue(), pageSize.intValue(), sqlCount);
	}
	/**
	 * 查询所有
	 *
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {

		String sql = "SELECT * FROM ibm_role  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 查询所有
	 *
	 * @return
	 * @throws Exception
	 */
	public List<IbmRoleW> findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_role  where state_!='DEL' order by UPDATE_TIME_ desc";

		return this.dao.findObjectList(IbmRoleW.class, sql);
	}

	/**
	 * @param roleCode
	 * @return
	 * @throws Exception
	 * @Description: 通过Code查询ID
	 */
	public String findIdByCode(String roleCode) throws Exception {
		String sql = "select IBM_ROLE_ID_ from ibm_role where STATE_ != ? and ROLE_CODE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(roleCode);
		return super.dao.findString("IBM_ROLE_ID_", sql, parameterList);
	}

	/**
	 * @param roleId      角色ID
	 * @param handicapIds 盘口ID
	 * @param roleId      方案ID
	 * @Description: 更新角色表盘口和方案信息
	 */
	public void updateResource(String roleId, List<String> handicapIds, List<String> planIds, String className)
			throws SQLException {
		StringBuilder planId = new StringBuilder();
		StringBuilder handicapId = new StringBuilder();
		for (String planIdStr : planIds) {
			planId.append(planIdStr).append(",");
		}
		for (String handicapIdStr : handicapIds) {
			handicapId.append(handicapIdStr).append(",");
		}
		planId.deleteCharAt(planId.length() - 1);
		handicapId.deleteCharAt(handicapId.length() - 1);
		String sql = "update ibm_role set DESC_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,HANDICAP_ID_ = ? , PLAN_ID_ = ? where IBM_ROLE_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(className + "更新角色表盘口和方案信息");
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapId.toString());
		parameterList.add(planId.toString());
		parameterList.add(roleId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 模糊查询分页
	 *
	 * @param roleName		角色名称
	 * @param pageIndex		当前页数
	 * @param pageSize		每页条数
	 * @return
	 * @throws SQLException
	 */
	public PageCoreBean find(String roleName, Integer pageIndex, Integer pageSize) throws SQLException {
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append("SELECT count(*) FROM ibm_role where state_!='DEL' ");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ibm_role where state_!='DEL' ");
		ArrayList<Object> parameters = null;
		ArrayList<Object> parametersCount = null;
		if (StringTool.notEmpty(roleName)) {
			parameters = new ArrayList<>();
			parametersCount = new ArrayList<>();
			parameters.add("%" + roleName + "%");
			parametersCount.add("%" + roleName + "%");
			sql.append("and ROLE_NAME_ like ? ");
			sqlCount.append("and ROLE_NAME_ like ? ");
		}
		sql.append("order by UPDATE_TIME_ desc");
		return dao.page(sql.toString(), parameters, pageIndex, pageSize, sqlCount.toString(), parametersCount);
	}
	/**
	 * @param userType 用户类型
	 * @return 角色等级
	 * 返回类型 String
	 * @throws SQLException
	 * @Description: 查询角色等级
	 * <p>
	 * 参数说明
	 */
	public String findLevel(String userType) throws SQLException {
		String sql = "SELECT ROLE_LEVEL_ from ibm_role where ROLE_CODE_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userType);
		return super.dao.findString("ROLE_LEVEL_", sql, parameterList);
	}
	/**
	 * @param roleLevel 权限等级
	 * @return 可修改的权限
	 * @Description: 可修改的权限
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> listRole(String roleLevel) throws SQLException {
		String sql = "SELECT * from ibm_role where ROLE_LEVEL_ >= ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(roleLevel);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}
	/**
	 * @return 所有角色拥有的盘口
	 * @throws SQLException
	 * @Description: 查询所有角色拥有的盘口
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> findAllHandicapId() throws SQLException {
		String sql = "SELECT IBM_ROLE_ID_,HANDICAP_ID_ FROM ibm_role ";
		return super.dao.findMapList(sql, null);
	}

	/**
	 * @param roleId      角色ID
	 * @param handicapIds 盘口ID
	 * @Description: 更新角色表盘口
	 */
	public void updateHandicap(String roleId, List<String> handicapIds ) throws SQLException {
		StringBuilder handicapId = new StringBuilder();
		for (String handicapIdStr : handicapIds) {
			handicapId.append(handicapIdStr).append(",");
		}
		handicapId.deleteCharAt(handicapId.length() - 1);
		String sql = "update ibm_role set UPDATE_TIME_=? ,UPDATE_TIME_LONG_=? ,HANDICAP_ID_ = ? where IBM_ROLE_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapId.toString());
		parameterList.add(roleId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * @return 所有角色拥有的方案
	 * @throws SQLException
	 * @Description: 查询所有角色拥有的方案
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> findAllPlanId() throws SQLException {
		String sql = "SELECT IBM_ROLE_ID_,PLAN_ID_ FROM ibm_role ";
		return super.dao.findMapList(sql, null);
	}

	/**
	 * @param roleId  角色ID
	 * @param planIds 方案ID
	 * @Description: 更新角色表方案
	 */
	public void updatePlan(String roleId, List<String> planIds ) throws SQLException {
		StringBuilder planId = new StringBuilder();
		for (String planIdStr : planIds) {
			planId.append(planIdStr).append(",");
		}
		planId.deleteCharAt(planId.length() - 1);
		String sql = "update ibm_role set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,PLAN_ID_ = ? where IBM_ROLE_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(planId.toString());
		parameterList.add(roleId);
		super.dao.execute(sql, parameterList);
	}

}
