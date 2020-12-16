package org.doming.develop.job.service;

import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时器CRON 服务类
 *
 * @Author: Dongming
 * @Date: 2020-04-08 15:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SysQuartzCronService extends BaseServiceProxy {


	/**
	 * 逻辑删除SELF_QUARTZ_CRON_ID_主键id数组的数据
	 */
	public void delAll() throws Exception {
		String sql = "update self_quartz_cron set STATE_=?,UPDATE_TIME_LONG_=?,DESC_=? where STATE_!=?";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(StateEnum.DEL.name());
		parameters.add(System.currentTimeMillis());
		parameters.add("注销客户机");
		parameters.add(StateEnum.DEL.name());
		execute(sql, parameters);
	}


	/**
	 * 根据任务主键查找触发CRON主键
	 *
	 * @return 任务主键
	 * 触发主键
	 */
	public String findId(String taskId) throws SQLException {
		String sql = "SELECT SELF_QUARTZ_CRON_ID_ FROM self_quartz_cron where QUARTZ_TASK_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(taskId);
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}
	/**
	 * 更新触发时间
	 *
	 * @param cron    CRON表达式
	 * @param nowTime 更新时间
	 * @param cronId  触发主键
	 */

	public void update(String cron, Date nowTime, String cronId) throws SQLException {
		String sql = "UPDATE self_quartz_cron set CRON_EXPRESSION = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " where SELF_QUARTZ_CRON_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(cron);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(cronId);
		super.execute(sql, parameterList);
	}
	/**
	 * 保存触发时间
	 *
	 * @param taskId  任务主键
	 * @param cron    CRON表达式
	 * @param nowTime 更新时间
	 * @return 触发主键
	 */
	public String save(String taskId, String cron, Date nowTime) throws SQLException {
		String simpleId = RandomTool.getNumLetter32();
		String sql = "INSERT INTO self_quartz_cron (SELF_QUARTZ_CRON_ID_, QUARTZ_TASK_ID_, "
				+ " CRON_EXPRESSION_, CREATE_TIME_, CREATE_TIME_LONG_, UPDATE_TIME_LONG_, "
				+ " STATE_) VALUES ('%s','%s','%s','%s', %d, %d, '%s')";
		sql = String.format(sql, simpleId, taskId, cron, DateTool.get(nowTime), System.currentTimeMillis(),
				System.currentTimeMillis(), StateEnum.OPEN.name());
		super.execute(sql);
		return simpleId;

	}

	/**
	 * 获取所有的QUARTZ记录
	 *
	 * @return QUARTZ记录
	 */
	public List<Map<String, Object>> listAllQuartzRecord() throws SQLException {
		String sql = "SELECT TASK_NAME_,TASK_GROUP_,TASK_DESCRIPTION_,TASK_CLASS_,TASK_CONTENT_,CRON_EXPRESSION_ "
				+ " FROM self_quartz_cron iqc LEFT JOIN self_quartz_task iqt ON "
				+ " iqc.QUARTZ_TASK_ID_ = iqt.SELF_QUARTZ_TASK_ID_ WHERE iqc.STATE_ = ? AND iqt.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(StateEnum.OPEN.name());
		return super.findMapList(sql, parameterList);
	}
}
