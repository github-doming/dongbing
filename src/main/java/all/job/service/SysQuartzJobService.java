package all.job.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.quartz.Job;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
import c.a.util.job.QuartzTriggerBean;
import c.a.util.job.QuartzUtil;
import c.a.tools.log.custom.common.BaseLog;
import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.gen.sys_quartz_job.t.service.SysQuartzJobTService;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
public class SysQuartzJobService extends SysQuartzJobTService {
	/**
	 * 
	 * 物理删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {
		// Quartz操作
		SysQuartzJobT sysQuartzJobT = find(id);
		if (sysQuartzJobT != null) {
			String jobName = sysQuartzJobT.getJobName();
			QuartzUtil qu = QuartzUtil.findInstance();
			qu.deleteJobByName(jobName);
		}
		// 数据库操作
		String sql = "delete from sys_quartz_job where SYS_QUARTZ_JOB_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 运行
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String run(SysQuartzJobT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		boolean isSchedulerStart = quartzUtil.doSchedulerIsStart();
		BaseLog.trace("11 isSchedulerStart=" + isSchedulerStart);
		System.out.println("11 doSchedulerIsInStandbyMode=" + quartzUtil.doSchedulerIsInStandbyMode());
		// quartzUtil.doSchedulerStart();
		// quartzUtil.doSchedulerStandby();
		BaseLog.trace("12 isSchedulerStart=" + isSchedulerStart);
		System.out.println("12 doSchedulerIsInStandbyMode=" + quartzUtil.doSchedulerIsInStandbyMode());
		String triggerId = Uuid.create().toStringDate20();
		// String className = "example.c.a.util.quartz.jobclass.JobSimple2";
		String jobClassName = entity.getJobClassName();
		Class classObject = Class.forName(jobClassName);
		boolean isAssignable = Job.class.isAssignableFrom(classObject);
		if (!isAssignable) {
			AssertUtil.isBlank(null, "JOB必须实现JOB接口!");
		}
		System.out.println("创建计划 QuartzUtil.findInstance=" + quartzUtil);
		QuartzTriggerBean bean = new QuartzTriggerBean();
		bean.setTriggerId(triggerId);
		bean.setJobClassName(jobClassName);
		bean.setJobDescription("JobDescription_" + bean.getTriggerId());
		bean.setTriggerDescription("TriggerDescription_" + bean.getTriggerId());
		quartzUtil.runTrigger("", bean);
		return null;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM sys_quartz_job";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void saveOrUpdate(SysQuartzJobT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		if (StringUtil.isBlank(entity.getJobName())) {
			String jobName = quartzUtil.createJobName(entity.getJobClassName());
			entity.setJobName(jobName);
		}
		SysQuartzJobT sysQuartzJobNew=	findSysQuartzJobTByJobName(entity.getJobName());
	
		if(sysQuartzJobNew!=null){
			sysQuartzJobNew.setJobName(entity.getJobName());
			this.update(sysQuartzJobNew);
		} else {
			this.save(entity);
		}
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(SysQuartzJobT entity) throws Exception {
		// QuartzUtil quartzUtil = QuartzUtil.findInstance();
		// if (StringUtil.isBlank(entity.getJobName())) {
		// String jobName = quartzUtil.createJobName(entity.getJobClassName());
		// entity.setJobName(jobName);
		// }
		Date date = new Date();
		entity.setCreateTime(date);
		entity.setCreateTimeLong(date.getTime());
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		return dao.save(entity);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysQuartzJobT entity) throws Exception {
		// QuartzUtil quartzUtil = QuartzUtil.findInstance();
		// if (StringUtil.isBlank(entity.getJobName())) {
		// String jobName = quartzUtil.createJobName(entity.getJobClassName());
		// entity.setJobName(jobName);
		// }
		Date date = new Date();
		entity.setUpdateTime(date);
		entity.setUpdateTimeLong(date.getTime());
		dao.update(entity);
	}
	/**
	 * 
	 * 查询是否存在
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysQuartzJobT findSysQuartzJobTByJobName(String jobName) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "select * from sys_quartz_job where JOB_NAME_=? ";
		parameterList.add(jobName);
		SysQuartzJobT sysQuartzJobT= (SysQuartzJobT) this.dao.findObject(SysQuartzJobT.class,sql, parameterList);
		return sysQuartzJobT;
	}
}
