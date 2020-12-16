package com.ibm.follow.servlet.cloud.ibm_exp_role.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_exp_role.entity.IbmExpRole;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBM_角色 服务类
 * @author Robot
 */
public class IbmExpRoleService extends BaseServicePlus {

	/**
	 * 保存IBM_角色 对象数据
	 * @param entity IbmExpRole对象数据
	 */
	public String save(IbmExpRole entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_exp_role 的 IBM_EXP_ROLE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_exp_role set state_='DEL' where IBM_EXP_ROLE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EXP_ROLE_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_exp_role 的 IBM_EXP_ROLE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_exp_role set state_='DEL' where IBM_EXP_ROLE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_exp_role  的 IBM_EXP_ROLE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_exp_role where IBM_EXP_ROLE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EXP_ROLE_ID_主键id数组的数据
	 * @param idArray 要删除ibm_exp_role 的 IBM_EXP_ROLE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_exp_role where IBM_EXP_ROLE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmExpRole实体信息
	 * @param entity IBM_角色 实体
	 */
	public void update(IbmExpRole entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_exp_role表主键查找 IBM_角色 实体
	 * @param id ibm_exp_role 主键
	 * @return IBM_角色 实体
	 */
	public IbmExpRole find(String id) throws Exception {
		return (IbmExpRole) this.dao.find(IbmExpRole. class,id);

	}

    /**
     * 根据角色code和角色等级获取角色
     * @param roleCode      角色code
     * @return 角色
     */
    public IbmExpRole findByCode(IbmTypeEnum roleCode) throws Exception {
        String sql="select * from ibm_exp_role where ROLE_CODE_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(roleCode.name());
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findObject(IbmExpRole.class,sql,parameterList);
    }

	/**
	 * 列出展示信息
	 * @return 展示信息
	 */
	public List<Map<String, Object>> listShow() throws SQLException {
		String sql="select IBM_EXP_ROLE_ID_,ROLE_NAME_,ROLE_CODE_,ROLE_LEVEL_,GAME_CODES_,MEMBER_HANDICAP_CODES_,AGENT_HANDICAP_CODES_,"
				+ " MEMBER_ONLINE_MAX_,AGENT_ONLINE_MAX_,HM_ONLINE_MAX_,HA_ONLINE_MAX_,STATE_ from ibm_exp_role where STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql,parameterList);
	}
}
