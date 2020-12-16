package org.doming.develop.job.service;

import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时器任务 服务类
 * @Author: Dongming
 * @Date: 2020-04-08 15:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SysQuartzTaskService extends BaseServiceProxy {

	/**
	 * 逻辑删除SELF_QUARTZ_TASK_ID_主键id数组的数据
	 *
	 */
	public void delAll() throws Exception {
		String sql="update self_quartz_task set STATE_=?,UPDATE_TIME_LONG_=?,DESC_=? where STATE_!=?";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(StateEnum.DEL.name());
		parameters.add(System.currentTimeMillis());
		parameters.add("注销客户机");
		parameters.add(StateEnum.DEL.name());
		execute(sql, parameters);
	}

	/**
	 * 根据任务名称和任务组找到任务主键
	 *
	 * @param name  任务名称
	 * @param group 任务组
	 * @return 任务主键
	 */
	public String findId(String name, String group) throws SQLException {
		String sql = "SELECT SELF_QUARTZ_TASK_ID_ FROM `self_quartz_task` where TASK_NAME_ = ? and TASK_GROUP_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(name);
		parameterList.add(group);
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}
	/**
	 * 更新任务
	 *
	 * @param taskClass   任务工作类
	 * @param taskContent 任务正文
	 * @param description 任务描述
	 * @param nowTime     更新时间
	 * @param taskId      任务主键
	 */
	public void update(String taskClass, String taskContent, String description, Date nowTime, String taskId)
			throws SQLException {
		String sql = "UPDATE `self_quartz_task` SET TASK_CLASS_ = ?, TASK_CONTENT_ = ?, TASK_DESCRIPTION_ = ?, "
				+ " UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? WHERE SELF_QUARTZ_TASK_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(taskClass);
		parameterList.add(taskContent);
		parameterList.add(description);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(taskId);
		super.execute(sql, parameterList);

	}

	/**
	 * 保存任务
	 *
	 * @param name        任务名称
	 * @param group       任务组
	 * @param description 任务描述
	 * @param taskClass   任务工作类
	 * @param taskContent 任务正文
	 * @param nowTime 更新时间
	 * @return 任务主键
	 */
	public String save(String name, String group, String description, String taskClass, String taskContent,
					   Date nowTime) throws SQLException {
		String taskId = RandomTool.getNumLetter32();
		String sql = "INSERT INTO `self_quartz_task` (`SELF_QUARTZ_TASK_ID_`, `TASK_NAME_`, `TASK_GROUP_`, "
				+ " `TASK_DESCRIPTION_`, `TASK_CLASS_`, `TASK_CONTENT_`, `CREATE_TIME_`, `CREATE_TIME_LONG_`, "
				+ " `UPDATE_TIME_LONG_`, `STATE_`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object> parameterList = new ArrayList<>(10);
		parameterList.add(taskId);
		parameterList.add(name);
		parameterList.add(group);
		parameterList.add(description);
		parameterList.add(taskClass);
		parameterList.add(taskContent);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(StateEnum.OPEN.name());
		super.execute(sql, parameterList);
		return taskId;
	}
}
