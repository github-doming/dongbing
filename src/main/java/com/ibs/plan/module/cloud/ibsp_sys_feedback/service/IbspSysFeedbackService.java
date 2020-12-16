package com.ibs.plan.module.cloud.ibsp_sys_feedback.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_sys_feedback.entity.IbspSysFeedback;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IBSP用户反馈表 服务类
 *
 * @author Robot
 */
public class IbspSysFeedbackService extends BaseServiceProxy {

	/**
	 * 保存IBSP用户反馈表 对象数据
	 *
	 * @param entity IbspSysFeedback对象数据
	 */
	public String save(IbspSysFeedback entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_sys_feedback 的 IBSP_SYS_FEEDBACK_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_sys_feedback set state_='DEL' where IBSP_SYS_FEEDBACK_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_SYS_FEEDBACK_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_sys_feedback 的 IBSP_SYS_FEEDBACK_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_sys_feedback set state_='DEL' where IBSP_SYS_FEEDBACK_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_sys_feedback  的 IBSP_SYS_FEEDBACK_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_sys_feedback where IBSP_SYS_FEEDBACK_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_SYS_FEEDBACK_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_sys_feedback 的 IBSP_SYS_FEEDBACK_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_sys_feedback where IBSP_SYS_FEEDBACK_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspSysFeedback实体信息
	 *
	 * @param entity IBSP用户反馈表 实体
	 */
	public void update(IbspSysFeedback entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_sys_feedback表主键查找 IBSP用户反馈表 实体
	 *
	 * @param id ibsp_sys_feedback 主键
	 * @return IBSP用户反馈表 实体
	 */
	public IbspSysFeedback find(String id) throws Exception {
		return dao.find(IbspSysFeedback.class, id);
	}

	/**
	 * 查询反馈信息
	 *
	 * @param feedbackType  反馈类型
	 * @param feedbackTitle 反馈标题
	 * @param feedbackCode  反馈编码
	 * @param state         状态
	 * @param queryRange    查询范围
	 * @param startTime     开始时间
	 * @param endTime       结束时间
	 * @param pageIndex     页码
	 * @param pageSize      大小
	 * @return 反馈信息
	 */
	public PageCoreBean<Map<String, Object>> findUserFeedbackInfo(String feedbackType, String feedbackTitle, String feedbackCode, String feedbackUser,
																  String state, String queryRange, Long startTime, Long endTime, Integer pageIndex, Integer pageSize) throws SQLException {
		String sql = "SELECT IBSP_SYS_FEEDBACK_ID_ pk,USER_NAME_ userName,FEEDBACK_INFO_ info,FEEDBACK_TITLE_ title,FEEDBACK_CODE_ feedbackCode,CREATE_TIME_LONG_ time,STATE_ state " +
				"FROM `ibsp_sys_feedback` WHERE FEEDBACK_TYPE_ = ?  ";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> paramList = new ArrayList<>();
		paramList.add(feedbackType);
		if ("PART".equals(queryRange)) {
			sql += " AND STATE_  not in (?,?) ";
			paramList.add(IbsStateEnum.DEF.name());
			paramList.add("FAILED");
		}
		if (StringTool.notEmpty(feedbackUser)) {
			sql += " AND USER_NAME_ like ? ";
			paramList.add("%" + feedbackUser + "%");
		}
		if (StringTool.notEmpty(feedbackTitle)) {
			sql += " AND FEEDBACK_TITLE_ like ? ";
			paramList.add("%" + feedbackTitle + "%");
		}
		if (StringTool.notEmpty(feedbackCode)) {
			sql += " AND FEEDBACK_CODE_ = ? ";
			paramList.add(feedbackCode);
		}
		if (StringTool.notEmpty(state)) {
			sql += " AND STATE_ = ? ";
			paramList.add(state);
		}
		if (startTime != null) {
			sql += " AND REPORT_TIME_LONG_ BETWEEN ? AND ? ";
			paramList.add(startTime);
			paramList.add(endTime);
		}

		sql += " ORDER BY CREATE_TIME_LONG_ desc";
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, paramList, pageIndex, pageSize, sqlCount, paramList);
	}

	/**
	 * 根据编码查询反馈信息
	 *
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findByCode(String code) throws SQLException {
		String sql = "SELECT IBM_SYS_FEEDBACK_ID_ pk,USER_NAME_ userName,FEEDBACK_CODE_ feedbackCode,FEEDBACK_TITLE_ title,FEEDBACK_INFO_ info, STATE_ state ,CREATE_TIME_ time " +
				"FROM `ibsp_sys_feedback` WHERE FEEDBACK_CODE_ = ? ";
		List<Object> paramList = new ArrayList<>();
		paramList.add(code);
		return dao.findMap(sql, paramList);
	}

	/**
	 * 更新反馈信息
	 *
	 * @param pk    主键
	 * @param state 状态
	 */
	public void updateFeedback(String pk, String state) throws SQLException {
		String sql = "UPDATE ibsp_sys_feedback SET  `STATE_`=? WHERE `IBSP_SYS_FEEDBACK_ID_`=?";
		List<Object> paramList = new ArrayList<>();
		paramList.add(state);
		paramList.add(pk);
		dao.execute(sql, paramList);
	}
}
