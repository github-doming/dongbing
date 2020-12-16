package com.ibs.plan.module.cloud.ibsp_sys_feedback_result.service;

import com.ibs.plan.module.cloud.ibsp_sys_feedback_result.entity.IbspSysFeedbackResult;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBSP用户反馈结果表 服务类
 * @author Robot
 */
public class IbspSysFeedbackResultService extends BaseServiceProxy {

	/**
	 * 保存IBSP用户反馈结果表 对象数据
	 * @param entity IbspSysFeedbackResult对象数据
	 */
	public String save(IbspSysFeedbackResult entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_sys_feedback_result 的 IBSP_SYS_FEEDBACK_RESULT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_sys_feedback_result set state_='DEL' where IBSP_SYS_FEEDBACK_RESULT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_SYS_FEEDBACK_RESULT_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_sys_feedback_result 的 IBSP_SYS_FEEDBACK_RESULT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_sys_feedback_result set state_='DEL' where IBSP_SYS_FEEDBACK_RESULT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_sys_feedback_result  的 IBSP_SYS_FEEDBACK_RESULT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_sys_feedback_result where IBSP_SYS_FEEDBACK_RESULT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_SYS_FEEDBACK_RESULT_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_sys_feedback_result 的 IBSP_SYS_FEEDBACK_RESULT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_sys_feedback_result where IBSP_SYS_FEEDBACK_RESULT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspSysFeedbackResult实体信息
	 * @param entity IBSP用户反馈结果表 实体
	 */
	public void update(IbspSysFeedbackResult entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_sys_feedback_result表主键查找 IBSP用户反馈结果表 实体
	 * @param id ibsp_sys_feedback_result 主键
	 * @return IBSP用户反馈结果表 实体
	 */
	public IbspSysFeedbackResult find(String id) throws Exception {
		return dao.find(IbspSysFeedbackResult.class,id);
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
				" FROM `ibsp_sys_feedback_result` WHERE FEEDBACK_CODE_ = ? ORDER BY CREATE_TIME_LONG_ DESC" ;
		List<Object> paramList = new ArrayList<>();
		paramList.add(code);
		return dao.findMapList(sql,paramList);
	}
}
