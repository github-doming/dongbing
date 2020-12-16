package com.ibm.follow.servlet.cloud.ibm_sys_quartz_cron.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_quartz_cron.entity.IbmSysQuartzCron;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBM_定时器CRON 服务类
 *
 * @author Robot
 */
public class IbmSysQuartzCronService extends BaseServicePlus {

	/**
	 * 保存IBM_定时器CRON 对象数据
	 *
	 * @param entity IbmSysQuartzCron对象数据
	 */
	public String save(IbmSysQuartzCron entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_sys_quartz_cron 的 IBM_SYS_QUARTZ_CRON_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_quartz_cron set state_='DEL' where IBM_SYS_QUARTZ_CRON_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_sys_quartz_cron  的 IBM_SYS_QUARTZ_CRON_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_quartz_cron where IBM_SYS_QUARTZ_CRON_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_QUARTZ_CRON_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_sys_quartz_cron 的 IBM_SYS_QUARTZ_CRON_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_quartz_cron where IBM_SYS_QUARTZ_CRON_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysQuartzCron实体信息
	 *
	 * @param entity IBM_定时器CRON 实体
	 */
	public void update(IbmSysQuartzCron entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_quartz_cron表主键查找 IBM_定时器CRON 实体
	 *
	 * @param id ibm_sys_quartz_cron 主键
	 * @return IBM_定时器CRON 实体
	 */
	public IbmSysQuartzCron find(String id) throws Exception {
		return (IbmSysQuartzCron) this.dao.find(IbmSysQuartzCron.class, id);

	}


}
