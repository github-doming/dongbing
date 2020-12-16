package com.ibs.plan.module.cloud.ibsp_exp_role.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBSP_角色 服务类
 * @author Robot
 */
public class IbspExpRoleService extends BaseServiceProxy {

	/**
	 * 保存IBSP_角色 对象数据
	 * @param entity IbspExpRole对象数据
	 */
	public String save(IbspExpRole entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_exp_role 的 IBSP_EXP_ROLE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_exp_role set state_='DEL' where IBSP_EXP_ROLE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_EXP_ROLE_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_exp_role 的 IBSP_EXP_ROLE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_exp_role set state_='DEL' where IBSP_EXP_ROLE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_exp_role  的 IBSP_EXP_ROLE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_exp_role where IBSP_EXP_ROLE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_EXP_ROLE_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_exp_role 的 IBSP_EXP_ROLE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_exp_role where IBSP_EXP_ROLE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspExpRole实体信息
	 * @param entity IBSP_角色 实体
	 */
	public void update(IbspExpRole entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_exp_role表主键查找 IBSP_角色 实体
	 * @param id ibsp_exp_role 主键
	 * @return IBSP_角色 实体
	 */
	public IbspExpRole find(String id) throws Exception {
		return dao.find(IbspExpRole.class,id);
	}

	/**
	 * 根据角色code获取角色
	 * @param roleCode      角色code
	 * @return 角色
	 */
	public IbspExpRole findByCode(IbsTypeEnum roleCode) throws Exception {
		String sql="select * from ibsp_exp_role where ROLE_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(roleCode.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspExpRole.class,sql,parameterList);
	}
	/**
	 * 根据角色code获取角色
	 * @param roleCode      角色code
	 * @return 角色
	 */
	public IbspExpRole findByCode(String roleCode) throws Exception {
		String sql="select * from ibsp_exp_role where ROLE_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(roleCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspExpRole.class,sql,parameterList);
	}

	/**
	 * 列出展示信息
	 * @return 展示信息
	 */
	public List<Map<String, Object>> listShow() throws SQLException {
		String sql="select IBSP_EXP_ROLE_ID_,ROLE_NAME_,ROLE_CODE_,ROLE_LEVEL_,GAME_CODES_,HANDICAP_CODES_,"
				+ " HM_ONLINE_MAX_,ONLINE_MAX_,PLAN_CODES_,STATE_ from ibsp_exp_role where STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMapList(sql,parameterList);
	}

	/**
	 * 获取角色列表
	 * @return
	 */
	public List<Map<String, Object>> listRole() throws SQLException {
		String sql="select IBSP_EXP_ROLE_ID_,ROLE_NAME_,ROLE_CODE_,ROLE_LEVEL_ from ibsp_exp_role where STATE_=? ORDER BY ROLE_LEVEL_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql,parameterList);
	}
}
