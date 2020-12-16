package all.job.service;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.sys_quartz_config.t.entity.SysQuartzConfigT;
import all.gen.sys_quartz_config.t.service.SysQuartzConfigTService;
import c.a.util.job.QuartzUtil;

public class SysQuartzConfigService extends SysQuartzConfigTService {
	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 
	 * 停止
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String shutdown(SysQuartzConfigT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		boolean isSchedulerStart = quartzUtil.doSchedulerIsStart();
		boolean isInStandbyMode = quartzUtil.doSchedulerIsInStandbyMode();
		System.out.println("1 shutdown isSchedulerStart=" + isSchedulerStart);
		System.out.println("1 shutdown isInStandbyMode=" + isInStandbyMode);
		//quartzUtil.doSchedulerShutdow();
		// quartzUtil.getScheduler().shutdown(true);
		quartzUtil.doSchedulerShutdow(true);
		System.out.println("2 shutdown isSchedulerStart=" + isSchedulerStart);
		System.out.println("2 shutdown isInStandbyMode=" + isInStandbyMode);

		return null;
	}

	/**
	 * 
	 * 启动
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String start(SysQuartzConfigT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		boolean isSchedulerStart = quartzUtil.doSchedulerIsStart();
		boolean isInStandbyMode = quartzUtil.doSchedulerIsInStandbyMode();
		System.out.println("1  start  isSchedulerStart=" + isSchedulerStart);
		System.out.println("1  start  isInStandbyMode=" + isInStandbyMode);

		if (isInStandbyMode) {

			quartzUtil.doSchedulerStart();

		}
		System.out.println("2 start  isSchedulerStart=" + isSchedulerStart);
		System.out.println("2 start  isInStandbyMode=" + isInStandbyMode);

		return null;
	}

	/**
	 * 
	 * 停止
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String pause(SysQuartzConfigT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		boolean isSchedulerStart = quartzUtil.doSchedulerIsStart();
		boolean isInStandbyMode = quartzUtil.doSchedulerIsInStandbyMode();
		System.out.println("1 pause isSchedulerStart=" + isSchedulerStart);
		System.out.println("1 pause isInStandbyMode=" + isInStandbyMode);

		if (isInStandbyMode) {

		} else {
			quartzUtil.doSchedulerStandby();
		}
		System.out.println("2 pause isSchedulerStart=" + isSchedulerStart);
		System.out.println("2 pause isInStandbyMode=" + isInStandbyMode);

		return null;
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(SysQuartzConfigT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		System.out.println("QuartzUtil.findInstance=" + quartzUtil);

		Date date = new Date();

		entity.setCreateTime(date);
		// entity.setCreateTimeS(DateThreadLocal.findThreadLocal().get().doUtilDate2String(date));
		entity.setCreateTimeLong(date.getTime());

		entity.setUpdateTime(date);
		// entity.setUpdateTimeS(DateThreadLocal.findThreadLocal().get().doUtilDate2String(date));
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
	public void update(SysQuartzConfigT entity) throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		System.out.println("QuartzUtil.findInstance=" + quartzUtil);

		Date date = new Date();

		entity.setUpdateTime(date);
		// entity.setUpdateTimeS(DateThreadLocal.findThreadLocal().get().doUtilDate2String(date));
		entity.setUpdateTimeLong(date.getTime());
		dao.update(entity);
	}
}
