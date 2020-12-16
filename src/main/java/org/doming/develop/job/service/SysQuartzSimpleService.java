package org.doming.develop.job.service;

import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时器运行设置 服务类
 *
 * @Author: Dongming
 * @Date: 2020-04-08 15:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SysQuartzSimpleService  extends BaseServiceProxy {

	/**
	 * 逻辑删除SELF_QUARTZ_SIMPLE_ID_主键id数组的数据
	 *
	 */
	public void delAll() throws Exception {
		String sql="update self_quartz_simple set STATE_=?,UPDATE_TIME_LONG_=?,DESC_=? where STATE_!=?";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(StateEnum.DEL.name());
		parameters.add(System.currentTimeMillis());
		parameters.add("注销客户机");
		parameters.add(StateEnum.DEL.name());
		execute(sql, parameters);
	}
	/**
	 * 根据任务主键查找触发简单主键
	 *
	 * @return 任务主键
	 * 触发主键
	 */
	public String findId(String taskId) throws SQLException {
		String sql = "SELECT SELF_QUARTZ_SIMPLE_ID_ FROM self_quartz_simple where QUARTZ_TASK_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(taskId);
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}
	/**
	 * 更新触发时间
	 *
	 * @param startTime    开始时间
	 * @param intervalTime 间隔时间
	 * @param repeatCount  循环次数
	 * @param nowTime      更新时间
	 * @param simpleId     触发主键
	 */
	public void update(Date startTime, Integer intervalTime, Integer repeatCount, Date nowTime, String simpleId)
			throws SQLException {
		String sql = "UPDATE self_quartz_simple set START_TIME_ = ?,INTERVAL_TIME_ = ?,REPEAT_COUNT_ = ?, "
				+ "UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where SELF_QUARTZ_SIMPLE_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(startTime.getTime());
		parameterList.add(intervalTime);
		parameterList.add(repeatCount);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(simpleId);
		super.execute(sql, parameterList);
	}

	/**
	 * 保存触发时间
	 *
	 * @param taskId       任务主键
	 * @param startTime    开始时间
	 * @param intervalTime 间隔时间
	 * @param repeatCount  循环次数
	 * @param nowTime      更新时间
	 * @return 触发主键
	 */
	public String save(String taskId, Date startTime, Integer intervalTime, Integer repeatCount, Date nowTime)
			throws SQLException {
		String simpleId = RandomTool.getNumLetter32();
		String sql = "INSERT INTO self_quartz_simple (SELF_QUARTZ_SIMPLE_ID_, QUARTZ_TASK_ID_, "
				+ " START_TIME_, INTERVAL_TIME_, REPEAT_COUNT_, CREATE_TIME_, CREATE_TIME_LONG_, UPDATE_TIME_LONG_, "
				+ " STATE_) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object> parameterList = new ArrayList<>(9);
		parameterList.add(simpleId);
		parameterList.add(taskId);
		parameterList.add(startTime.getTime());
		parameterList.add(intervalTime);
		parameterList.add(repeatCount);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(StateEnum.OPEN.name());
		super.execute(sql, parameterList);
		return simpleId;
	}

	/**
	 * 获取所有的QUARTZ记录
	 *
	 * @return QUARTZ记录
	 */
	public List<Map<String, Object>> listAllQuartzRecord() throws SQLException {
		String sql = "SELECT TASK_NAME_,TASK_GROUP_,TASK_DESCRIPTION_,TASK_CLASS_,TASK_CONTENT_,START_TIME_,"
				+ "INTERVAL_TIME_,REPEAT_COUNT_ FROM self_quartz_simple iqs LEFT JOIN self_quartz_task iqt "
				+ " ON iqs.QUARTZ_TASK_ID_ = iqt.SELF_QUARTZ_TASK_ID_ WHERE iqs.STATE_ = ? AND iqt.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(StateEnum.OPEN.name());
		return super.findMapList(sql, parameterList);
	}
}
