package com.ibm.follow.servlet.cloud.ibm_sys_quartz_task.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_quartz_task.entity.IbmSysQuartzTask;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * IBM_定时器任务 服务类
 *
 * @author Robot
 */
public class IbmSysQuartzTaskService extends BaseServicePlus {

	/**
	 * 保存IBM_定时器任务 对象数据
	 *
	 * @param entity IbmSysQuartzTask对象数据
	 */
	public String save(IbmSysQuartzTask entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_sys_quartz_task 的 IBM_SYS_QUARTZ_TASK_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_quartz_task set state_='DEL' where IBM_SYS_QUARTZ_TASK_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_QUARTZ_TASK_ID_主键id数组的数据
	 *
	 */
	public void delAll() throws Exception {
		String sql="update ibm_sys_quartz_task set STATE_=?,UPDATE_TIME_LONG_=?,DESC_=? where STATE_!=?";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(System.currentTimeMillis());
		parameters.add("注销客户机");
		parameters.add(IbmStateEnum.DEL.name());
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_sys_quartz_task  的 IBM_SYS_QUARTZ_TASK_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_quartz_task where IBM_SYS_QUARTZ_TASK_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_QUARTZ_TASK_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_sys_quartz_task 的 IBM_SYS_QUARTZ_TASK_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_quartz_task where IBM_SYS_QUARTZ_TASK_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysQuartzTask实体信息
	 *
	 * @param entity IBM_定时器任务 实体
	 */
	public void update(IbmSysQuartzTask entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_quartz_task表主键查找 IBM_定时器任务 实体
	 *
	 * @param id ibm_sys_quartz_task 主键
	 * @return IBM_定时器任务 实体
	 */
	public IbmSysQuartzTask find(String id) throws Exception {
		return (IbmSysQuartzTask) this.dao.find(IbmSysQuartzTask.class, id);

	}

	/**
	 * 根据任务名称和任务组找到任务主键
	 *
	 * @param name  任务名称
	 * @param group 任务组
	 * @return 任务主键
	 */
	public String findId(String name, String group) throws SQLException {
		String sql = "SELECT IBM_SYS_QUARTZ_TASK_ID_ FROM `ibm_sys_quartz_task` where TASK_NAME_ = ? and TASK_GROUP_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(name);
		parameterList.add(group);
		parameterList.add(IbmStateEnum.OPEN.name());
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
		String sql = "UPDATE `ibm_sys_quartz_task` SET TASK_CLASS_ = ?, TASK_CONTENT_ = ?, TASK_DESCRIPTION_ = ?, "
				+ " UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? WHERE IBM_SYS_QUARTZ_TASK_ID_ = ?";
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
		String sql = "INSERT INTO `ibm_sys_quartz_task` (`IBM_SYS_QUARTZ_TASK_ID_`, `TASK_NAME_`, `TASK_GROUP_`, "
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
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);
		return taskId;
	}
}
