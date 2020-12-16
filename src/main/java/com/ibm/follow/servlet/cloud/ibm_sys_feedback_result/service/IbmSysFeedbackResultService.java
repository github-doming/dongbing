package com.ibm.follow.servlet.cloud.ibm_sys_feedback_result.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback_result.entity.IbmSysFeedbackResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*  服务类
 * @author Robot
 */
public class IbmSysFeedbackResultService extends BaseServicePlus {

	/**
	 * 保存 对象数据
	 * @param entity IbmSysFeedbackResult对象数据
	 */
	public String save(IbmSysFeedbackResult entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_sys_feedback_result 的 IBM_SYS_FEEDBACK_RESULT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_feedback_result set state_='DEL' where IBM_SYS_FEEDBACK_RESULT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_FEEDBACK_RESULT_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_sys_feedback_result 的 IBM_SYS_FEEDBACK_RESULT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_sys_feedback_result set state_='DEL' where IBM_SYS_FEEDBACK_RESULT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_sys_feedback_result  的 IBM_SYS_FEEDBACK_RESULT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_feedback_result where IBM_SYS_FEEDBACK_RESULT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_FEEDBACK_RESULT_ID_主键id数组的数据
	 * @param idArray 要删除ibm_sys_feedback_result 的 IBM_SYS_FEEDBACK_RESULT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_feedback_result where IBM_SYS_FEEDBACK_RESULT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysFeedbackResult实体信息
	 * @param entity  实体
	 */
	public void update(IbmSysFeedbackResult entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_feedback_result表主键查找  实体
	 * @param id ibm_sys_feedback_result 主键
	 * @return  实体
	 */
	public IbmSysFeedbackResult find(String id) throws Exception {
		return (IbmSysFeedbackResult) this.dao.find(IbmSysFeedbackResult. class,id);

	}

	/**
	 * 根据编码查询反馈信息
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> findByCode(String code) throws SQLException {
		String sql = "SELECT UPDATE_USER_ updateUser,FEEDBACK_RESULTS_ result," +
				"STATE_ state ,CREATE_Time_LONG_ time " +
				" FROM `ibm_sys_feedback_result` WHERE FEEDBACK_CODE_ = ? ORDER BY CREATE_TIME_LONG_ DESC" ;
		List<Object> paramList = new ArrayList<>();
		paramList.add(code);
		return dao.findMapList(sql,paramList);
	}

}
