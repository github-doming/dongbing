package com.ibm.follow.servlet.cloud.ibm_sys_quartz_simple.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_quartz_simple.entity.IbmSysQuartzSimple;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBM_定时器运行设置 服务类
 *
 * @author Robot
 */
public class IbmSysQuartzSimpleService extends BaseServicePlus {

	/**
	 * 保存IBM_定时器运行设置 对象数据
	 *
	 * @param entity IbmSysQuartzSimple对象数据
	 */
	public String save(IbmSysQuartzSimple entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_sys_quartz_simple 的 IBM_SYS_QUARTZ_SIMPLE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_quartz_simple set state_='DEL' where IBM_SYS_QUARTZ_SIMPLE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_sys_quartz_simple  的 IBM_SYS_QUARTZ_SIMPLE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_quartz_simple where IBM_SYS_QUARTZ_SIMPLE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_QUARTZ_SIMPLE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_sys_quartz_simple 的 IBM_SYS_QUARTZ_SIMPLE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_sys_quartz_simple where IBM_SYS_QUARTZ_SIMPLE_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysQuartzSimple实体信息
	 *
	 * @param entity IBM_定时器运行设置 实体
	 */
	public void update(IbmSysQuartzSimple entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_quartz_simple表主键查找 IBM_定时器运行设置 实体
	 *
	 * @param id ibm_sys_quartz_simple 主键
	 * @return IBM_定时器运行设置 实体
	 */
	public IbmSysQuartzSimple find(String id) throws Exception {
		return (IbmSysQuartzSimple) this.dao.find(IbmSysQuartzSimple.class, id);

	}
	/**
	 * 根据任务主键查找触发简单主键
	 *
	 * @return 任务主键
	 * 触发主键
	 */
	public String findId(String taskId) throws SQLException {
		String sql = "SELECT IBM_SYS_QUARTZ_SIMPLE_ID_ FROM ibm_sys_quartz_simple where QUARTZ_TASK_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(taskId);
		parameterList.add(IbmStateEnum.OPEN.name());
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
		String sql = "UPDATE ibm_sys_quartz_simple set START_TIME_ = ?,INTERVAL_TIME_ = ?,REPEAT_COUNT_ = ?, "
				+ "UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where IBM_SYS_QUARTZ_SIMPLE_ID_ = ?";
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
		String sql = "INSERT INTO ibm_sys_quartz_simple (IBM_SYS_QUARTZ_SIMPLE_ID_, QUARTZ_TASK_ID_, "
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
		parameterList.add(IbmStateEnum.OPEN.name());
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
				+ "INTERVAL_TIME_,REPEAT_COUNT_ FROM ibm_sys_quartz_simple iqs LEFT JOIN ibm_sys_quartz_task iqt "
				+ " ON iqs.QUARTZ_TASK_ID_ = iqt.IBM_SYS_QUARTZ_TASK_ID_ WHERE iqs.STATE_ = ? AND iqt.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameterList);
	}
}
