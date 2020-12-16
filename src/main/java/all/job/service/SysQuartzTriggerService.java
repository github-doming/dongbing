package all.job.service;

import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.gen.sys_quartz_trigger.t.service.SysQuartzTriggerTService;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
import c.a.util.job.QuartzTriggerBean;
import c.a.util.job.QuartzUtil;
import org.quartz.Job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SysQuartzTriggerService extends SysQuartzTriggerTService {
	/**
	 * 物理删除所有
	 */
	@Override
	public void delAllPhysical(String[] ids) throws Exception {
		if (ids != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {

				// Quartz操作
				SysQuartzTriggerT sysQuartzTriggerT = find(id);
				if (sysQuartzTriggerT != null) {
					String triggerId = sysQuartzTriggerT.getTriggerId();
					QuartzUtil qu = QuartzUtil.findInstance();
					qu.deleteTriggerById(triggerId);
				}
				// 数据库操作

				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from sys_quartz_trigger where SYS_QUARTZ_TRIGGER_ID_ in(" + stringBuffer.toString()
					+ ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 物理删除
	 */
	@Override
	public void delPhysical(String id) throws Exception {
		// Quartz操作
		SysQuartzTriggerT sysQuartzTriggerT = find(id);
		if (sysQuartzTriggerT != null) {
			String triggerId = sysQuartzTriggerT.getTriggerId();
			QuartzUtil qu = QuartzUtil.findInstance();
			qu.deleteTriggerById(triggerId);
		}

		// 数据库操作
		String sql = "delete from sys_quartz_trigger where SYS_QUARTZ_TRIGGER_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 暂停定时计划
	 *
	 * @param entity
	 * @throws Exception
	 */
	public String pause(SysQuartzTriggerT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		quartzUtil.doTriggerPause(entity.getTriggerId());
		return null;
	}

	/**
	 * 执行定时计划
	 *
	 * @param entity
	 * @throws Exception
	 */
	public String run(SysQuartzTriggerT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		quartzUtil.doTriggerResume(entity.getTriggerId());
		return null;
	}

	/**
	 * 启动定时计划
	 *
	 * @param entity
	 * @throws Exception
	 */
	public String start(SysQuartzTriggerT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		boolean isSchedulerStart = quartzUtil.doSchedulerIsStart();
		String triggerId = Uuid.create().toStringDate20();
		String jobClassName = entity.getJobClassName();
		Class classObject = Class.forName(jobClassName);
		boolean isAssignable = Job.class.isAssignableFrom(classObject);
		if (!isAssignable) {
			AssertUtil.isBlank(null, "JOB必须实现JOB接口!");
		}
		QuartzTriggerBean bean = new QuartzTriggerBean();
		bean.setTriggerId(entity.getTriggerId());
		bean.setJobClassName(jobClassName);
		bean.setJobDescription("JobDescription_" + bean.getTriggerId());
		bean.setTriggerDescription("TriggerDescription_" + bean.getTriggerId());
		// 定时触发
		bean.setCronExpression(entity.getCronExpression());
		quartzUtil.createTriggerCron("", bean);
		return null;
	}

	/**
	 * 查询所有
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM sys_quartz_trigger  ORDER BY SYS_TRIGGER_NAME_";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 查询展示
	 *
	 * @param jobName 任务名称
	 * @param state   任务状态
	 * @return 展示
	 */
	public PageCoreBean<Map<String, Object>> listShow(String jobName, String state, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "SELECT count(*) from (";
		String sql = "SELECT SYS_QUARTZ_TRIGGER_ID_ as QUARTZ_TRIGGER_ID_,SYS_TRIGGER_NAME_,TRIGGER_NAME_,JOB_NAME_, " +
				" CRON_EXPRESSION_,DESC_ FROM sys_quartz_trigger ";
		List<Object> parameterList = new ArrayList<>();
		if (StringUtil.isNotBlank(jobName)) {
			sql += " AND SYS_TRIGGER_NAME_ like ?";
			parameterList.add("%" + jobName + "%");
		}
		if (StringUtil.isNotBlank(state)) {
			sql += " AND STATE_ = ?";
			parameterList.add(state);
		}
		sqlCount += sql+") t";
		sql += " ORDER BY SYS_TRIGGER_NAME_";
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 查询展示
	 *
	 * @param quartzTriggerId 定时器触发器主键
	 * @return 展示
	 */
	public Map<String, Object> findShow(String quartzTriggerId) throws SQLException {
		String sql = "SELECT SYS_TRIGGER_NAME_ AS TRIGGER_NAME_,JOB_NAME_,DESC_,JOB_CLASS_NAME_,CRON_EXPRESSION_ FROM " +
				" sys_quartz_trigger WHERE SYS_QUARTZ_TRIGGER_ID_ = ? AND STATE_ != 'DEL'";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(quartzTriggerId);
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 保存
	 */
	@Override
	public String save(SysQuartzTriggerT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		if (StringUtil.isBlank(entity.getJobName())) {
			String jobName = quartzUtil.createJobName(entity.getJobClassName());
			entity.setJobName(jobName);
		}
		String triggerId = Uuid.create().toStringDate20();
		entity.setTriggerId(triggerId);
		entity.setTriggerName(quartzUtil.createTriggerName(triggerId));
		Date date = new Date();
		entity.setCreateTime(date);
		entity.setCreateTimeLong(date.getTime());
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		return dao.save(entity);
	}

	/**
	 * 更新(先删除，再增加)
	 *
	 * @param entity
	 * @throws Exception
	 */
	@Override
	public void update(SysQuartzTriggerT entity) throws Exception {
		this.delPhysical(entity.getSysQuartzTriggerId());

		this.save(entity);
	}

}
