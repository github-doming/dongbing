package c.a.util.job;
import c.a.config.core.JdbcDataSourceQuartzConfig;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.a.util.core.typeconst.TypePrimitiveConst;
import net.sf.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
/**
 * 定时工具
 *
 * @author cxy
 * @Description:
 * @ClassName: QuartzUtil
 * @date 2012年02月16日
 * @Email: 使用范围：
 */
public class QuartzUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	public static String autoParameter = "auto";
	private String jobGroupName = "job_group";
	private String jobNamePrefix = "job_";
	private String triggerGroupName = "trigger_group";
	private String triggerNamePrefix = "trigger_";
	private static StdSchedulerFactory schedulerFactory = null;
	private static Scheduler scheduler = null;
	private static QuartzUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private QuartzUtil() {
	}
	public static QuartzUtil findInstance() throws SchedulerException, Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new QuartzUtil();
					// 初始化
					init();
				}
			}
		}
		return instance;
	}
	/**
	 * 初始化
	 *
	 * @throws SchedulerException
	 * @throws Exception          返回类型 void
	 * @Title: init
	 * @Description: 参数说明
	 */
	private static void init() throws SchedulerException, Exception {
		// 读取数据库配置
		String driver = JdbcDataSourceQuartzConfig.findInstance().getDriver();
		String url = JdbcDataSourceQuartzConfig.findInstance().getUrl();
		String username = JdbcDataSourceQuartzConfig.findInstance().getUsername();
		String password = JdbcDataSourceQuartzConfig.findInstance().getPassword();
		// 定时器配置
		schedulerFactory = new StdSchedulerFactory();
		Properties properties = new Properties();
		properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME,
				"quartz_name_" + RequestThreadLocal.findThreadLocal().get().findIPLocal());
		properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID,
				"quartz_id_" + RequestThreadLocal.findThreadLocal().get().findIPLocal());
		properties.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
		//true或者false,标识scheduler主线程是否为守护线程。
		properties.put("org.quartz.scheduler.makeSchedulerThreadDaemon", true);
		//定时器阻塞
		properties.put("org.quartz.threadPool.threadCount", "10");
		properties.put("org.quartz.threadPool.threadPriority", "5");
		//是用来设置调度引擎对触发器超时的忍耐时间，简单来说 假设misfireThreshold=6000(单位毫秒)。
		properties.put("org.quartz.jobStore.misfireThreshold", "600000");
		properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		properties.put("org.quartz.jobStore.useProperties", "false");
		properties.put("org.quartz.jobStore.dataSource", "myDS");
		properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		properties.put("org.quartz.jobStore.isClustered", "true");
		//集群检查间隔时间
		properties.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
		properties.put("org.quartz.dataSource.myDS.driver", driver);
		properties.put("org.quartz.dataSource.myDS.URL", url);
		properties.put("org.quartz.dataSource.myDS.user", username);
		properties.put("org.quartz.dataSource.myDS.password", password);
		// 使用连接时先判断是否有效。
		properties.put("org.quartz.dataSource.myDS.validateOnCheckout", "true");
		// 使用连接时先判断是否有效。
		properties.put("org.quartz.dataSource.myDS.validationQuery", "select 1");
		// 将testConnectionOnCheckout 设为 false
		// 将testConnectionOnCheckin 设为 true
		// 将idleConnectionTestPeriod 设为 30，这个数字要根据项目情况设定，比8小时小就好
		properties.put("org.quartz.dataSource.myDS.maxConnections", "30");
		schedulerFactory.initialize(properties);
		scheduler = schedulerFactory.getScheduler();
	}

	/**
	 * 销毁
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}

		//停止quartz
		if (scheduler != null) {
			instance.shutdown();
		}

		instance = null;

	}

	/**
	 * 开启定时器
	 */
	public void start() throws SchedulerException {
		if (doSchedulerIsInStandbyMode()) {
			doSchedulerStart();
		}
	}

	/**
	 * 关闭
	 */
	private void shutdown() {
		try {
			if (doSchedulerIsStart()) {
				doSchedulerShutdow(false);
				log.debug("关闭定时器完成");
			}
		} catch (SchedulerException e) {
			log.error("关闭定时器失败", e);
		}
	}
	public static void setScheduler(Scheduler scheduler) {
		QuartzUtil.scheduler = scheduler;
	}
	public static Scheduler getScheduler() {
		return scheduler;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}
	public String getTriggerGroupName() {
		return triggerGroupName;
	}
	/**
	 * 创建时间表达式(天)
	 *
	 * @param seconds
	 * @param minutes
	 * @param hours
	 * @param days
	 * @param months
	 * @param years
	 * @return
	 */
	public String createCronExpressionForDay(String second, String minute, String hour, String day, String month,
			String year) {
		String[] years = null;
		if (StringUtil.isNotBlank(year)) {
			years = year.split(",");
		}
		String[] months = null;
		if (StringUtil.isNotBlank(month)) {
			months = month.split(",");
		}
		String[] days = null;
		if (StringUtil.isNotBlank(day)) {
			days = day.split(",");
		}
		String[] hours = null;
		if (StringUtil.isNotBlank(hour)) {
			hours = hour.split(",");
		}
		String[] minutes = null;
		if (StringUtil.isNotBlank(minute)) {
			minutes = minute.split(",");
		}
		String[] seconds = null;
		if (StringUtil.isNotBlank(second)) {
			seconds = second.split(",");
		}
		String expression = createCronExpressionForDay(seconds, minutes, hours, days, months, years);
		return expression;
	}
	/**
	 * 创建时间表达式(天)
	 *
	 * @param seconds
	 * @param minutes
	 * @param hours
	 * @param days
	 * @param months
	 * @param years
	 * @return
	 */
	public String createCronExpressionForDay(String[] seconds, String[] minutes, String[] hours, String[] days,
			String[] months, String[] years) {
		StringBuilder stringBuffer = new StringBuilder();
		// 秒
		StringBuilder secondStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(seconds)) {
			for (String second : seconds) {
				secondStringBuilder.append(second).append(",");
			}
			secondStringBuilder.deleteCharAt(secondStringBuilder.length() - 1);
		} else {
			secondStringBuilder.append("*");
		}
		stringBuffer.append(secondStringBuilder).append(" ");
		// 分钟
		StringBuilder minuteStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(minutes)) {
			for (String minute : minutes) {
				minuteStringBuilder.append(minute).append(",");
			}
			minuteStringBuilder.deleteCharAt(minuteStringBuilder.length() - 1);
		} else {
			minuteStringBuilder.append("*");
		}
		stringBuffer.append(minuteStringBuilder).append(" ");
		// 时
		StringBuilder hourStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(hours)) {
			for (String hour : hours) {
				hourStringBuilder.append(hour).append(",");
			}
			hourStringBuilder.deleteCharAt(hourStringBuilder.length() - 1);
		} else {
			hourStringBuilder.append("*");
		}
		stringBuffer.append(hourStringBuilder).append(" ");
		// 天
		StringBuilder dayStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(days)) {
			for (String day : days) {
				dayStringBuilder.append(day).append(",");
			}
			dayStringBuilder.deleteCharAt(dayStringBuilder.length() - 1);
		} else {
			dayStringBuilder.append("*");
		}
		stringBuffer.append(dayStringBuilder).append(" ");
		// 月
		StringBuilder monthStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(months)) {
			for (String month : months) {
				monthStringBuilder.append(month).append(",");
			}
			monthStringBuilder.deleteCharAt(monthStringBuilder.length() - 1);
		} else {
			monthStringBuilder.append("*");
		}
		stringBuffer.append(monthStringBuilder).append(" ");
		// 星期
		stringBuffer.append("? ");
		// 年
		StringBuilder yearStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(years)) {
			for (String year : years) {
				yearStringBuilder.append(year).append(",");
			}
			yearStringBuilder.deleteCharAt(yearStringBuilder.length() - 1);
		} else {
			yearStringBuilder.append("*");
		}
		stringBuffer.append(yearStringBuilder).append("");
		return stringBuffer.toString();
	}
	/**
	 * 创建时间表达式(星期)
	 *
	 * @param seconds
	 * @param minutes
	 * @param hours
	 * @param weeks
	 * @param months
	 * @param years
	 * @return
	 */
	public String createCronExpressionForWeek(String second, String minute, String hour, String week, String month,
			String year) {
		String[] years = null;
		if (StringUtil.isNotBlank(year)) {
			years = year.split(",");
		}
		String[] months = null;
		if (StringUtil.isNotBlank(month)) {
			months = month.split(",");
		}
		String[] weeks = null;
		if (StringUtil.isNotBlank(week)) {
			weeks = week.split(",");
		}
		String[] hours = null;
		if (StringUtil.isNotBlank(hour)) {
			hours = hour.split(",");
		}
		String[] minutes = null;
		if (StringUtil.isNotBlank(minute)) {
			minutes = minute.split(",");
		}
		String[] seconds = null;
		if (StringUtil.isNotBlank(second)) {
			seconds = second.split(",");
		}
		String expression = createCronExpressionForWeek(seconds, minutes, hours, weeks, months, years);
		return expression;
	}
	/**
	 * 创建时间表达式(星期)
	 *
	 * @param seconds
	 * @param minutes
	 * @param hours
	 * @param weeks
	 * @param months
	 * @param years
	 * @return
	 */
	public String createCronExpressionForWeek(String[] seconds, String[] minutes, String[] hours, String[] weeks,
			String[] months, String[] years) {
		StringBuilder stringBuffer = new StringBuilder();
		// 秒
		StringBuilder secondStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(seconds)) {
			for (String second : seconds) {
				secondStringBuilder.append(second).append(",");
			}
			secondStringBuilder.deleteCharAt(secondStringBuilder.length() - 1);
		} else {
			secondStringBuilder.append("*");
		}
		stringBuffer.append(secondStringBuilder).append(" ");
		// 分钟
		StringBuilder minuteStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(minutes)) {
			for (String minute : minutes) {
				minuteStringBuilder.append(minute).append(",");
			}
			minuteStringBuilder.deleteCharAt(minuteStringBuilder.length() - 1);
		} else {
			minuteStringBuilder.append("*");
		}
		stringBuffer.append(minuteStringBuilder).append(" ");
		// 时
		StringBuilder hourStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(hours)) {
			for (String hour : hours) {
				hourStringBuilder.append(hour).append(",");
			}
			hourStringBuilder.deleteCharAt(hourStringBuilder.length() - 1);
		} else {
			hourStringBuilder.append("*");
		}
		stringBuffer.append(hourStringBuilder).append(" ");
		// 天
		stringBuffer.append("? ");
		// 月
		StringBuilder monthStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(months)) {
			for (String month : months) {
				monthStringBuilder.append(month).append(",");
			}
			monthStringBuilder.deleteCharAt(monthStringBuilder.length() - 1);
		} else {
			monthStringBuilder.append("*");
		}
		stringBuffer.append(monthStringBuilder).append(" ");
		// 星期
		StringBuilder weekStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(weeks)) {
			for (String week : weeks) {
				weekStringBuilder.append(DateThreadLocal.findThreadLocal().get().findWeekMap().get(week)).append(",");
			}
			weekStringBuilder.deleteCharAt(weekStringBuilder.length() - 1);
		} else {
			weekStringBuilder.append("*");
		}
		stringBuffer.append(weekStringBuilder).append(" ");
		// 年
		StringBuilder yearStringBuilder = new StringBuilder();
		if (StringUtil.isNotBlank(years)) {
			for (String year : years) {
				yearStringBuilder.append(year).append(",");
			}
			yearStringBuilder.deleteCharAt(yearStringBuilder.length() - 1);
		} else {
			yearStringBuilder.append("*");
		}
		stringBuffer.append(yearStringBuilder).append("");
		return stringBuffer.toString();
	}
	/**
	 * 构造job名称
	 *
	 * @Description: desc @Title: createJobName @param
	 * jobClassName @return 参数说明 @return String
	 * 返回类型 @throws
	 */
	public String createJobName(String jobClassName) {
		String jobName = jobNamePrefix + jobClassName;
		return jobName;
	}
	/**
	 * 构造计划名称
	 *
	 * @Description @Title createTriggerName @param jobId @return 参数说明 @return
	 * String 返回类型 @throws
	 */
	public String createTriggerName(String triggerId) {
		String triggerName = triggerNamePrefix + triggerId;
		return triggerName;
	}
	/**
	 * 直接执行计划
	 *
	 * @param jobName
	 * @throws SchedulerException
	 */
	public void executeJob(String jobName) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		JobKey key = new JobKey(jobName, this.jobGroupName);
		scheduler.triggerJob(key);
	}
	/**
	 * 找出Trigger触发器的状态
	 *
	 * @Title: findState @Description: @param state @return 参数说明 @return
	 * String 返回类型 @throws
	 */
	public TriggerStateEnum findState(TriggerState state) {
		if (state == null) {
			return TriggerStateEnum.NONE;
		}
		if (state == Trigger.TriggerState.NORMAL) {
			return TriggerStateEnum.NORMAL;
		}
		if (state == Trigger.TriggerState.PAUSED) {
			return TriggerStateEnum.PAUSED;
		}
		if (state == Trigger.TriggerState.COMPLETE) {
			return TriggerStateEnum.COMPLETE;
		}
		if (state == Trigger.TriggerState.ERROR) {
			return TriggerStateEnum.ERROR;
		}
		if (state == Trigger.TriggerState.BLOCKED) {
			return TriggerStateEnum.BLOCKED;
		}
		if (state == Trigger.TriggerState.NONE) {
			return TriggerStateEnum.NONE;
		}
		throw new java.lang.RuntimeException("未知的状态类型");
	}
	/**
	 * 设置job参数
	 *
	 * @Description @Title initJobParameter @param isAuto @param json @param
	 * jobBuilder 参数说明 @return void 返回类型 @throws
	 */
	private void initJobParameter(boolean isAuto, String json, JobBuilder jobBuilder) {
		if (isAuto) {
			jobBuilder.usingJobData(QuartzUtil.autoParameter, true);
		} else {
			jobBuilder.usingJobData(QuartzUtil.autoParameter, false);
		}
		if (StringUtil.isBlank(json)) {
			return;
		}
		JSONArray jsonArray = JSONArray.fromObject(json);
		ParameterBean[] parameterBeanArray = (ParameterBean[]) JSONArray.toArray(jsonArray, ParameterBean.class);
		for (int i = 0; i < parameterBeanArray.length; i++) {
			ParameterBean parameterBean = parameterBeanArray[i];
			String type = parameterBean.getType();
			String keyData = parameterBean.getKey();
			String value = parameterBean.getValue();
			if (type.equals(TypePrimitiveConst.TypeInt)) {
				if (StringUtil.isBlank(value)) {
					jobBuilder.usingJobData(keyData, 0);
				} else {
					jobBuilder.usingJobData(keyData, Integer.parseInt(value));
				}
			}
			if (type.equals(TypePrimitiveConst.TypeLong)) {
				if (StringUtil.isBlank(value)) {
					jobBuilder.usingJobData(keyData, 0);
				} else {
					jobBuilder.usingJobData(keyData, Long.parseLong(value));
				}
			}
			if (type.equals(TypePrimitiveConst.TypeFloat)) {
				if (StringUtil.isBlank(value)) {
					jobBuilder.usingJobData(keyData, 0.0);
				} else {
					jobBuilder.usingJobData(keyData, Float.parseFloat(value));
				}
			}
			if (type.equals(TypePrimitiveConst.TypeBoolean)) {
				if (StringUtil.isBlank(value)) {
					jobBuilder.usingJobData(keyData, false);
				} else {
					jobBuilder.usingJobData(keyData, Boolean.parseBoolean(value));
				}
			}
			if (type.equals(TypePrimitiveConst.TypeString)) {
				if (StringUtil.isBlank(value)) {
					jobBuilder.usingJobData(keyData, value);
				} else {
					jobBuilder.usingJobData(keyData, value);
				}
			}
		}
	}
	/**
	 * 创建job
	 *
	 * @Description @Title createJob @param parameterJson @param
	 * bean @return return @throws SchedulerException @throws
	 * ClassNotFoundException 参数说明 @return JobDetail 返回类型 @throws
	 */
	public JobDetail createJob(String parameterJson, QuartzTriggerBean bean)
			throws SchedulerException, ClassNotFoundException {
		AssertUtil.isBlank(bean.getTriggerId(), "TriggerId不能为空");
		bean.setJobName(this.createJobName(bean.getJobClassName()));
		boolean isJobExist = this.isJobExists(bean.getJobName());
		if (isJobExist) {
			LogThreadLocal.setLog(false, "job已存在");
			return null;
		}
		Class classObj = Class.forName(bean.getJobClassName());
		JobBuilder jobBuilder = JobBuilder.newJob(classObj);
		jobBuilder.withIdentity(bean.getJobName(), this.jobGroupName);
		initJobParameter(bean.isAuto(), parameterJson, jobBuilder);
		// 保存到数据库
		jobBuilder.storeDurably();
		jobBuilder.withDescription(bean.getJobDescription());
		JobDetail jobDetail = jobBuilder.build();
		scheduler.addJob(jobDetail, true);
		return jobDetail;
	}
	/**
	 * 删除job
	 *
	 * @param jobId
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public void deleteJobById(String jobId) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		String jobName = this.createJobName(jobId);
		JobKey jobKey = new JobKey(jobName, this.jobGroupName);
		scheduler.deleteJob(jobKey);
	}
	/**
	 * 删除job
	 *
	 * @param jobId
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public void deleteJobByName(String jobName) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		JobKey jobKey = new JobKey(jobName, this.jobGroupName);
		scheduler.deleteJob(jobKey);
	}
	/**
	 * 更新job
	 *
	 * @Title: updateJob @Description: @param parameterJson @param
	 * bean @return return @throws
	 * SchedulerException @throws ClassNotFoundException
	 * 参数说明 @return JobDetail 返回类型 @throws
	 */
	public JobDetail updateJob(String parameterJson, QuartzTriggerBean bean)
			throws SchedulerException, ClassNotFoundException {
		this.deleteJobById(bean.getJobName());
		return this.createJob(parameterJson, bean);
	}
	/**
	 * 停止或恢复,切换状态
	 *
	 * @param triggerName
	 * @throws SchedulerException
	 */
	public void doJobPause(String jobName) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		JobKey jobKey = new JobKey(jobName, this.jobGroupName);
		scheduler.pauseJob(jobKey);
	}
	/**
	 * 停止或恢复,切换状态
	 *
	 * @param triggerName
	 * @throws SchedulerException
	 */
	public void doJobResume(String jobName) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		JobKey jobKey = new JobKey(jobName, this.jobGroupName);
		scheduler.resumeJob(jobKey);
	}
	/**
	 * 打印所有的状态
	 *
	 * @throws SchedulerException
	 */
	public void printState() throws SchedulerException {
		List<JobDetail> jobDetailList = findJobList();
		for (JobDetail job : jobDetailList) {
			JobKey jobKey = job.getKey();
			List<Trigger> triggerList = findTriggerListByJobName(jobKey.getName());
			for (Trigger trigger : triggerList) {
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			}
		}
	}
	/**
	 * 暂停调度中所有的job计划
	 *
	 * @Description @Title doSchedulerPauseAll @throws SchedulerException
	 * 参数说明 @return void 返回类型 @throws
	 */
	public void doSchedulerPauseAll() throws SchedulerException {
		scheduler.pauseAll();
	}
	/**
	 * 恢复调度中所有的job的计划
	 *
	 * @Description @Title doSchedulerResumeAll @throws SchedulerException
	 * 参数说明 @return void 返回类型 @throws
	 */
	public void doSchedulerResumeAll() throws SchedulerException {
		scheduler.resumeAll();
	}
	/**
	 * 定时器是否启动;
	 * <p>
	 * * scheduler.start()执行启动;
	 * <p>
	 * scheduler.isStarted第一次启动false,以后都是true;
	 * <p>
	 * scheduler.isInStandbyMode启动前true(是否暂停);
	 * <p>
	 * scheduler.isInStandbyMode启动后false(是否暂停);
	 * <p>
	 * scheduler.standby停止;
	 *
	 * @Description @Title isSchedulerStart @return return @throws SchedulerException
	 * 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean doSchedulerIsStart() throws SchedulerException {
		return scheduler.isStarted();
	}
	/**
	 * 永久停止定时器(完全中止不可恢复)
	 */
	public void doSchedulerShutdow(boolean waitForJobsToComplete) throws SchedulerException {
		scheduler.shutdown(waitForJobsToComplete);
	}
	/**
	 * 永久停止定时器(完全中止不可恢复)
	 */
	public void doSchedulerShutdow() throws SchedulerException {
		scheduler.shutdown();
		// SchedulerMetaData schedulerMetaData = scheduler.getMetaData();
		// log.trace("执行完成计划数 Executed["
		// + schedulerMetaData.getNumberOfJobsExecuted() + "]jobs");
	}
	/**
	 * 恢复或启动定时器;
	 * <p>
	 * scheduler.start()执行启动;
	 * <p>
	 * scheduler.isStarted第一次启动false,以后都是true;
	 * <p>
	 * <p>
	 * scheduler.isInStandbyMode启动前true(是否暂停);
	 * <p>
	 * scheduler.isInStandbyMode启动后false(是否暂停);
	 * <p>
	 * scheduler.standby停止;
	 *
	 * @Description @Title doSchedulerStart @throws SchedulerException
	 * 参数说明 @return void 返回类型 @throws
	 */
	public void doSchedulerStart() throws SchedulerException {
		scheduler.start();
		// SchedulerMetaData schedulerMetaData = scheduler.getMetaData();
		// log.trace("执行完成计划数 Executed["
		// + schedulerMetaData.getNumberOfJobsExecuted() + "]jobs");
	}
	/**
	 * 停止定时器(临时中止);
	 * <p>
	 * scheduler.start()执行启动;
	 * <p>
	 * scheduler.isStarted第一次启动false,以后都是true;
	 * <p>
	 * <p>
	 * scheduler.isInStandbyMode启动前true(是否暂停);
	 * <p>
	 * scheduler.isInStandbyMode启动后false(是否暂停);
	 * <p>
	 * scheduler.standby停止;
	 *
	 * @Description @Title doSchedulerStandby @throws SchedulerException
	 * 参数说明 @return void 返回类型 @throws
	 */
	public void doSchedulerStandby() throws SchedulerException {
		scheduler.standby();
	}
	/**
	 * 停止定时器(临时中止);
	 * <p>
	 * * scheduler.start()执行启动;
	 * <p>
	 * scheduler.isStarted第一次启动false,以后都是true;
	 * <p>
	 * <p>
	 * scheduler.isInStandbyMode启动前true(是否暂停);
	 * <p>
	 * scheduler.isInStandbyMode启动后false(是否暂停); scheduler.standby停止;
	 *
	 * @Description @Title doSchedulerStandby @throws SchedulerException
	 * 参数说明 @return void 返回类型 @throws
	 */
	public void doSchedulerStop() throws SchedulerException {
		this.doSchedulerStandby();
	}
	/**
	 * 是否stand-by模式 启动前true 启动后false;
	 * <p>
	 * scheduler.start()执行启动;
	 * <p>
	 * scheduler.isStarted第一次启动false,以后都是true;
	 * <p>
	 * <p>
	 * scheduler.isInStandbyMode启动前true(是否暂停);
	 * <p>
	 * scheduler.isInStandbyMode启动后false(是否暂停);
	 * <p>
	 * scheduler.standby停止;
	 *
	 * @Description @Title doSchedulerIsInStandbyMode @return return @throws
	 * SchedulerException 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean doSchedulerIsInStandbyMode() throws SchedulerException {
		return scheduler.isInStandbyMode();
	}
	/**
	 * 定时器是否停止;
	 * <p>
	 * scheduler.start()执行启动;
	 * <p>
	 * scheduler.isStarted第一次启动false,以后都是true;
	 * <p>
	 * <p>
	 * scheduler.isInStandbyMode启动前true(是否暂停);
	 * <p>
	 * scheduler.isInStandbyMode启动后false(是否暂停);
	 * <p>
	 * scheduler.standby停止;
	 *
	 * @Description @Title doSchedulerIsInStandbyMode @return return @throws
	 * SchedulerException 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean isSchedulerStop() throws SchedulerException {
		return this.doSchedulerIsInStandbyMode();
	}
	/**
	 * 删除计划
	 *
	 * @param triggerName
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public void deleteTriggerById(String triggerId) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		String triggerName = this.createTriggerName(triggerId);
		TriggerKey key = new TriggerKey(triggerName, this.triggerGroupName);
		scheduler.unscheduleJob(key);
	}
	/**
	 * 删除计划
	 *
	 * @param triggerName
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public void deleteTriggerByName(String triggerName) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		TriggerKey key = new TriggerKey(triggerName, this.triggerGroupName);
		scheduler.unscheduleJob(key);
	}
	/**
	 * 是否存在计划
	 *
	 * @param bean
	 * @return
	 * @throws SchedulerException
	 */
	public boolean isTriggerExist(QuartzTriggerBean bean) throws SchedulerException {
		TriggerKey triggerKeyCheck = new TriggerKey(bean.getTriggerName(), this.triggerGroupName);
		Trigger triggerCheck = scheduler.getTrigger(triggerKeyCheck);
		if (triggerCheck != null) {
			Trigger.TriggerState state = getScheduler().getTriggerState(triggerCheck.getKey());
			// log.trace(" state ="+ state );
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 停止或恢复计划,切换状态
	 *
	 * @param triggerName
	 * @throws SchedulerException
	 */
	public void doTriggerPause(String triggerName) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		triggerName = this.createTriggerName(triggerName);
		TriggerKey key = new TriggerKey(triggerName, this.triggerGroupName);
		Trigger.TriggerState state = scheduler.getTriggerState(key);
		// log.trace("state=" + state);
		if (state == TriggerState.NORMAL) {
			scheduler.pauseTrigger(key);
		}
	}
	/**
	 * 停止或恢复计划,切换状态
	 *
	 * @param triggerName
	 * @throws SchedulerException
	 */
	public void doTriggerResume(String triggerName) throws SchedulerException {
		if (scheduler == null) {
			return;
		}
		triggerName = this.createTriggerName(triggerName);
		TriggerKey key = new TriggerKey(triggerName, this.triggerGroupName);
		Trigger.TriggerState state = scheduler.getTriggerState(key);
		// log.trace("state=" + state);
		if (state == TriggerState.PAUSED) {
			scheduler.resumeTrigger(key);
		}
	}
	/**
	 * 运行计划(只执行一次)
	 *
	 * @Description @Title createTrigger @param json @param bean @return return @throws
	 * SchedulerException @throws ClassNotFoundException
	 * 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean runTrigger(String json, QuartzTriggerBean bean) throws SchedulerException, ClassNotFoundException {
		AssertUtil.isBlank(bean.getTriggerId(), "TriggerId不能为空");
		// 删除job
		// this.deleteJob(bean.getJobId());
		bean.setJobName(this.createJobName(bean.getJobClassName()));
		bean.setTriggerName(this.createTriggerName(bean.getTriggerId()));
		AssertUtil.isBlank(bean.getTriggerName(), "计划名称不能为空");
		log.trace("job=" + this.isJobExists(bean.getJobName()));
		JobDetail jobDetail = this.findJob(bean.getJobName());
		if (jobDetail == null) {
			this.createJob(json, bean);
			jobDetail = this.findJobNew(bean.getJobName());
		}
		// 加上计划
		TriggerBuilder triggerBuilder = newTrigger().forJob(jobDetail);
		triggerBuilder.withDescription(bean.getTriggerDescription());
		Trigger trigger = triggerBuilder.withIdentity(bean.getTriggerName(), triggerGroupName).startNow().build();
		scheduler.scheduleJob(trigger);
		return true;
	}
	/**
	 * 创建计划(只执行一次)
	 *
	 * @Description @Title createTrigger @param json @param bean @return return @throws
	 * SchedulerException @throws ClassNotFoundException
	 * 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean createTrigger(String json, QuartzTriggerBean bean)
			throws SchedulerException, ClassNotFoundException {
		AssertUtil.isBlank(bean.getTriggerId(), "TriggerId不能为空");
		// 删除job
		// this.deleteJob(bean.getJobId());
		bean.setJobName(this.createJobName(bean.getJobClassName()));
		bean.setTriggerName(this.createTriggerName(bean.getTriggerId()));
		AssertUtil.isBlank(bean.getTriggerName(), "计划名称不能为空");
		log.trace("job=" + this.isJobExists(bean.getJobName()));
		JobDetail jobDetail = this.findJob(bean.getJobName());
		if (jobDetail == null) {
			this.createJob(json, bean);
			jobDetail = this.findJobNew(bean.getJobName());
		}
		if (this.isTriggerExist(bean)) {
			return false;
		} else {
			// 加上计划
			TriggerBuilder triggerBuilder = newTrigger().forJob(jobDetail);
			triggerBuilder.withDescription(bean.getTriggerDescription());
			Trigger trigger = triggerBuilder.withIdentity(bean.getTriggerName(), triggerGroupName).startNow().build();
			scheduler.scheduleJob(trigger);
			return true;
		}
	}
	/**
	 * 创建定时计划
	 *
	 * @Description @Title createTriggerCron @param json @param
	 * bean @return return @throws SchedulerException @throws
	 * ClassNotFoundException 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean createTriggerCron(String json, QuartzTriggerBean bean)
			throws SchedulerException, ClassNotFoundException {
		AssertUtil.isBlank(bean.getTriggerId(), "TriggerId不能为空");
		// 删除job
		// this.deleteJob(bean.getJobId());
		bean.setJobName(this.createJobName(bean.getTriggerId()));
		bean.setTriggerName(this.createTriggerName(bean.getTriggerId()));
		AssertUtil.isBlank(bean.getTriggerName(), "计划名称不能为空");
		JobDetail jobDetail = this.findJob(bean.getJobName());
		if (jobDetail == null) {
			this.createJob(json, bean);
			jobDetail = this.findJobNew(bean.getJobName());
		}
		AssertUtil.isBlank(bean.getCronExpression(), "计划表达式不能为空");
		if (this.isTriggerExist(bean)) {
			return false;
		} else {
			CronTrigger cronTrigger = newTrigger().forJob(jobDetail).withDescription(bean.getTriggerDescription())
					.withIdentity(bean.getTriggerName(), triggerGroupName)
					.withSchedule(cronSchedule(bean.getCronExpression())).build();
			if (false) {
				//https://www.cnblogs.com/skyLogin/p/6927629.html
				CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
				// (默认)
				// ——以当前时间为触发频率立刻触发一次执行
				// ——然后按照Cron频率依次执行
				csb.withMisfireHandlingInstructionFireAndProceed();
				// ——不触发立即执行
				// ——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
				csb.withMisfireHandlingInstructionDoNothing();
				// ——以错过的第一个频率时间立刻开始执行
				// ——重做错过的所有频率周期后
				// ——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
				csb.withMisfireHandlingInstructionIgnoreMisfires();
			}
			Date date = scheduler.scheduleJob(cronTrigger);
			return true;
		}
	}
	/**
	 * 创建间隔计划(马上创建)
	 *
	 * @Description @Title createTriggerInterval @param json @param
	 * bean @return return @throws SchedulerException @throws
	 * ClassNotFoundException 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean createTriggerInterval(String json, QuartzTriggerBean bean)
			throws SchedulerException, ClassNotFoundException {
		AssertUtil.isBlank(bean.getTriggerId(), "TriggerId不能为空");
		// 删除job
		// this.deleteJob(bean.getJobId());
		bean.setJobName(this.createJobName(bean.getTriggerId()));
		bean.setTriggerName(this.createTriggerName(bean.getTriggerId()));
		AssertUtil.isBlank(bean.getTriggerName(), "计划名称不能为空");
		JobDetail jobDetail = this.findJob(bean.getJobName());
		if (jobDetail == null) {
			this.createJob(json, bean);
			jobDetail = this.findJobNew(bean.getJobName());
		}
		AssertUtil.isNull(bean.getIntervalInMillis(), "间隔时间不能为空");
		AssertUtil.equal(0l, bean.getIntervalInMillis(), "计划间隔时间不能为0");
		if (this.isTriggerExist(bean)) {
			return false;
		} else {
			// 加上计划
			TriggerBuilder triggerBuilder = newTrigger().forJob(jobDetail);
			triggerBuilder.withDescription(bean.getTriggerDescription());
			Trigger trigger = triggerBuilder.withIdentity(bean.getTriggerName(), triggerGroupName).startNow()
					.withSchedule(
							simpleSchedule().withIntervalInMilliseconds(bean.getIntervalInMillis()).repeatForever())
					.build();
			scheduler.scheduleJob(trigger);
			return true;
		}
	}
	/**
	 * 创建间隔计划(马上创建)
	 *
	 * @Description @Title createTriggerIntervalByCountRepeat @param json @param
	 * bean @return return @throws SchedulerException @throws
	 * ClassNotFoundException 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean createTriggerIntervalByCountRepeat(String json, QuartzTriggerBean bean)
			throws SchedulerException, ClassNotFoundException {
		AssertUtil.isBlank(bean.getTriggerId(), "TriggerId不能为空");
		// 删除job
		// this.deleteJob(bean.getJobId());
		bean.setJobName(this.createJobName(bean.getTriggerId()));
		bean.setTriggerName(this.createTriggerName(bean.getTriggerId()));
		AssertUtil.isBlank(bean.getTriggerName(), "计划名称不能为空");
		JobDetail jobDetail = this.findJob(bean.getJobName());
		if (jobDetail == null) {
			this.createJob(json, bean);
			jobDetail = this.findJobNew(bean.getJobName());
		}
		AssertUtil.isNull(bean.getIntervalInMillis(), "间隔时间不能为空");
		AssertUtil.equal(0l, bean.getIntervalInMillis(), "计划间隔时间不能为0");
		AssertUtil.isNull(bean.getCountRepeatTrigger(), "间隔次数不能为空");
		if (this.isTriggerExist(bean)) {
			return false;
		} else {
			// 加上计划
			TriggerBuilder triggerBuilder = newTrigger().forJob(jobDetail);
			triggerBuilder.withDescription(bean.getTriggerDescription());
			Trigger trigger = triggerBuilder.withIdentity(bean.getTriggerName(), triggerGroupName).startNow()
					.withSchedule(simpleSchedule().withIntervalInMilliseconds(bean.getIntervalInMillis())
							.withRepeatCount(bean.getCountRepeatTrigger())).build();
			scheduler.scheduleJob(trigger);
			return true;
		}
	}
	/**
	 * 创建间隔计划(指定间隔时间和启动时间)
	 *
	 * @Description @Title createTriggerIntervalByCountRepeatAndStartAt @param
	 * json @param bean @return return @throws SchedulerException @throws
	 * ClassNotFoundException 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean createTriggerIntervalByCountRepeatAndStartAt(String json, QuartzTriggerBean bean)
			throws SchedulerException, ClassNotFoundException {
		// 是否重复，永远执行
		boolean isForever = false;
		int countRepeatTrigger = bean.getCountRepeatTrigger();
		if (countRepeatTrigger == 0) {
			isForever = true;
		}
		if (countRepeatTrigger > 0) {
			bean.setCountRepeatTrigger(countRepeatTrigger - 1);
		} else {
			bean.setCountRepeatTrigger(0);
		}
		AssertUtil.isBlank(bean.getTriggerId(), "TriggerId不能为空");
		// 删除job
		// this.deleteJob(bean.getJobId());
		bean.setJobName(this.createJobName(bean.getTriggerId()));
		bean.setTriggerName(this.createTriggerName(bean.getTriggerId()));
		AssertUtil.isBlank(bean.getTriggerName(), "计划名称不能为空");
		JobDetail jobDetail = this.findJob(bean.getJobName());
		if (jobDetail == null) {
			this.createJob(json, bean);
			jobDetail = this.findJobNew(bean.getJobName());
		}
		AssertUtil.isNull(bean.getIntervalInMillis(), "间隔时间不能为空");
		AssertUtil.equal(0l, bean.getIntervalInMillis(), "计划间隔时间不能为0");
		AssertUtil.isNull(bean.getCountRepeatTrigger(), "间隔次数不能为空");
		AssertUtil.isNull(bean.getTimeStartTrigger(), "计划开始时间不能为空");
		if (this.isTriggerExist(bean)) {
			return false;
		} else {
			// 加上计划
			Trigger trigger = null;
			TriggerBuilder triggerBuilder = newTrigger().forJob(jobDetail);
			triggerBuilder.withDescription(bean.getTriggerDescription());
			if (isForever) {
				trigger = triggerBuilder.withIdentity(bean.getTriggerName(), triggerGroupName)
						.startAt(bean.getTimeStartTrigger())
						.withSchedule(simpleSchedule().withIntervalInMilliseconds(bean.getIntervalInMillis())).build();
			} else {
				trigger = triggerBuilder.withIdentity(bean.getTriggerName(), triggerGroupName)
						.startAt(bean.getTimeStartTrigger()).withSchedule(
								simpleSchedule().withIntervalInMilliseconds(bean.getIntervalInMillis())
										.withRepeatCount(bean.getCountRepeatTrigger())).build();
			}
			scheduler.scheduleJob(trigger);
			return true;
		}
	}
	/**
	 * 创建间隔计划(指定启动时间)
	 *
	 * @Description @Title createTriggerIntervalByStartAt @param json @param
	 * bean @return return @throws SchedulerException @throws
	 * ClassNotFoundException 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean createTriggerIntervalByStartAt(String json, QuartzTriggerBean bean)
			throws SchedulerException, ClassNotFoundException {
		AssertUtil.isBlank(bean.getTriggerId(), "TriggerId不能为空");
		// 删除job
		// this.deleteJob(bean.getJobId());
		bean.setJobName(this.createJobName(bean.getTriggerId()));
		bean.setTriggerName(this.createTriggerName(bean.getTriggerId()));
		AssertUtil.isBlank(bean.getTriggerName(), "计划名称不能为空");
		JobDetail jobDetail = this.findJob(bean.getJobName());
		if (jobDetail == null) {
			this.createJob(json, bean);
			jobDetail = this.findJobNew(bean.getJobName());
		}
		AssertUtil.isNull(bean.getIntervalInMillis(), "间隔时间不能为空");
		AssertUtil.equal(0l, bean.getIntervalInMillis(), "计划间隔时间不能为0");
		AssertUtil.isNull(bean.getTimeStartTrigger(), "计划开始时间不能为空");
		if (this.isTriggerExist(bean)) {
			return false;
		} else {
			// 加上计划
			TriggerBuilder triggerBuilder = newTrigger().forJob(jobDetail);
			triggerBuilder.withDescription(bean.getTriggerDescription());
			Trigger trigger = triggerBuilder.withIdentity(bean.getTriggerName(), triggerGroupName)
					.startAt(bean.getTimeStartTrigger()).withSchedule(
							simpleSchedule().withIntervalInMilliseconds(bean.getIntervalInMillis()).repeatForever())
					.build();
			scheduler.scheduleJob(trigger);
			return true;
		}
	}
	/**
	 * 取得新的job
	 *
	 * @Title: findJobNew @Description: @param
	 * jobName @return return @throws SchedulerException
	 * 参数说明 @return JobDetail 返回类型 @throws
	 */
	public JobDetail findJobNew(String jobName) throws SchedulerException {
		if (scheduler == null) {
			return null;
		}
		JobKey jobKey = new JobKey(jobName, this.jobGroupName);
		JobDetail JobDetail = scheduler.getJobDetail(jobKey);
		return JobDetail;
	}
	/**
	 * 找出Job
	 *
	 * @Description @Title findJob @param jobName @return return @throws
	 * SchedulerException 参数说明 @return JobDetail 返回类型 @throws
	 */
	public JobDetail findJob(String jobName) throws SchedulerException {
		if (scheduler == null) {
			return null;
		}
		JobDetail detail = null;
		GroupMatcher<JobKey> groupMatcher = GroupMatcher.groupEquals(jobGroupName);
		Set<JobKey> jobKeyList = scheduler.getJobKeys(groupMatcher);
		for (JobKey jobKey : jobKeyList) {
			if (jobKey.getName().equals(jobName)) {
				// //log.trace("查询job name=" + jobKey.getName());
				detail = scheduler.getJobDetail(jobKey);
				break;
			}
		}
		return detail;
	}
	/**
	 * 判断job是否存在
	 *
	 * @Description @Title isJobExists @param jobName @return return @throws
	 * SchedulerException 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean isJobExists(String jobName) throws SchedulerException {
		if (scheduler == null) {
			return false;
		}
		JobKey jobKey = new JobKey(jobName, this.jobGroupName);
		return scheduler.checkExists(jobKey);
	}
	/**
	 * 找出Trigger触发器
	 *
	 * @Title: findTrigger @Description: @param
	 * triggerName @return return @throws SchedulerException
	 * 参数说明 @return Trigger 返回类型 @throws
	 */
	public Trigger findTrigger(String triggerName) throws SchedulerException {
		if (scheduler == null) {
			return null;
		}
		triggerName = this.createJobName(triggerName);
		TriggerKey key = new TriggerKey(triggerName, this.triggerGroupName);
		Trigger trigger = scheduler.getTrigger(key);
		return trigger;
	}
	/**
	 * 取得Job列表
	 *
	 * @Description @Title findJobList @return return @throws SchedulerException
	 * 参数说明 @return List<JobDetail> 返回类型 @throws
	 */
	public List<JobDetail> findJobList() throws SchedulerException {
		if (scheduler == null) {
			return new ArrayList<JobDetail>();
		}
		List<JobDetail> jobDetailList = new ArrayList<JobDetail>();
		GroupMatcher<JobKey> groupMatcher = GroupMatcher.groupEquals(jobGroupName);
		Set<JobKey> jobKeyList = scheduler.getJobKeys(groupMatcher);
		for (JobKey jobKey : jobKeyList) {
			// log.trace("查询job name=" + jobKey.getName());
			JobDetail detail = scheduler.getJobDetail(jobKey);
			jobDetailList.add(detail);
		}
		return jobDetailList;
	}
	/**
	 * 根据job名称获取触发器
	 *
	 * @Description @Title findTriggerListByJob @param jobName @return return @throws
	 * SchedulerException 参数说明 @return List<Trigger> 返回类型 @throws
	 */
	public List<Trigger> findTriggerListByJobName(String jobName) throws SchedulerException {
		if (scheduler == null) {
			return new ArrayList<Trigger>();
		}
		JobKey key = new JobKey(jobName, jobGroupName);
		return (List<Trigger>) scheduler.getTriggersOfJob(key);
	}
	/**
	 * 取得计划列表
	 *
	 * @Description @Title findQuartzJobBeanList @return return @throws
	 * SchedulerException 参数说明 @return List
	 * <QuartzTriggerBean> 返回类型 @throws
	 */
	public List<QuartzTriggerBean> findTriggerList() throws SchedulerException {
		List<QuartzTriggerBean> quartzTriggerBeanList = new ArrayList<QuartzTriggerBean>();
		List<JobDetail> jobDetailList = findJobList();
		for (JobDetail jobDetail : jobDetailList) {
			JobKey jobKey = jobDetail.getKey();
			List<Trigger> triggerList = findTriggerListByJobName(jobKey.getName());
			if (triggerList.size() > 0) {
				for (Trigger trigger : triggerList) {
					Trigger.TriggerState state = getScheduler().getTriggerState(trigger.getKey());
					QuartzTriggerBean quartzTriggerBean = new QuartzTriggerBean();
					quartzTriggerBean.setJobName(jobKey.getName());
					quartzTriggerBean.setJobClassName(jobDetail.getJobClass().getName());
					quartzTriggerBean.setTriggerName(trigger.getKey().getName());
					quartzTriggerBean.setTriggerDescription(trigger.getDescription());
					if (trigger instanceof CronTriggerImpl) {
						CronTriggerImpl cronTriggerImpl = null;
						try {
							cronTriggerImpl = (CronTriggerImpl) trigger;
							quartzTriggerBean.setCronExpression(cronTriggerImpl.getCronExpression());
						} catch (Exception e) {
							log.error("系统异常:",e);
						}
					}
					quartzTriggerBean.setTriggerState(findState(state));
					quartzTriggerBeanList.add(quartzTriggerBean);
				}
			}
		}
		return quartzTriggerBeanList;
	}
	/**
	 * 通过jobId查找所有的计划
	 *
	 * @Description @Title findQuartzJobBeanList @param jobId @return return @throws
	 * SchedulerException 参数说明 @return List
	 * <QuartzTriggerBean> 返回类型 @throws
	 */
	public List<QuartzTriggerBean> findTriggerList(String jobId) throws SchedulerException {
		String jobName = this.createJobName(jobId);
		List<QuartzTriggerBean> quartzTriggerBeanList = new ArrayList<QuartzTriggerBean>();
		JobDetail jobDetail = this.findJob(jobName);
		if (jobDetail == null) {
			return quartzTriggerBeanList;
		}
		JobKey jobKey = jobDetail.getKey();
		List<Trigger> triggerList = findTriggerListByJobName(jobKey.getName());
		if (triggerList.size() > 0) {
			for (Trigger trigger : triggerList) {
				Trigger.TriggerState state = getScheduler().getTriggerState(trigger.getKey());
				QuartzTriggerBean quartzTriggerBean = new QuartzTriggerBean();
				quartzTriggerBean.setJobName(jobKey.getName());
				quartzTriggerBean.setJobClassName(jobDetail.getJobClass().getName());
				quartzTriggerBean.setTriggerName(trigger.getKey().getName());
				if (trigger instanceof CronTriggerImpl) {
					CronTriggerImpl cronTriggerImpl = null;
					try {
						cronTriggerImpl = (CronTriggerImpl) trigger;
						quartzTriggerBean.setCronExpression(cronTriggerImpl.getCronExpression());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				quartzTriggerBean.setTriggerState(findState(state));
				quartzTriggerBeanList.add(quartzTriggerBean);
			}
		} else {
			QuartzTriggerBean bean = new QuartzTriggerBean();
			bean.setJobName(jobKey.getName());
			bean.setJobClassName(jobDetail.getJobClass().getName());
			bean.setTriggerState(this.findState(Trigger.TriggerState.NONE));
			quartzTriggerBeanList.add(bean);
		}
		return quartzTriggerBeanList;
	}
}
