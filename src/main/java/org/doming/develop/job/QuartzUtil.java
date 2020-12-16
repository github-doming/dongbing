package org.doming.develop.job;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ClassTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.job.service.SysQuartzCronService;
import org.doming.develop.job.service.SysQuartzSimpleService;
import org.doming.develop.job.service.SysQuartzTaskService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * @Description: 定时器工具类
 * @Author: Dongming
 * @Date: 2018-12-07 15:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class QuartzUtil {

	protected static final Logger log = LogManager.getLogger(QuartzUtil.class);

	/*
		读取数据库配置
	 */

	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static String machineCode;
	private static String servlet;
	private boolean recordTask = true;

	private volatile Scheduler scheduler = null;

	protected static volatile QuartzUtil instance = null;
	private QuartzUtil() {
	}

	//region 基础
	/**
	 * 获取 QuartzIbmUtil 实例
	 *
	 * @return QuartzIbmUtil 实例
	 */
	public static QuartzUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (QuartzUtil.class) {
				if (instance == null) {
					QuartzUtil instance = new QuartzUtil();
					// 初始化
					instance.init();
					QuartzUtil.instance = instance;
				}
			}
		}
		return instance;
	}

	/**
	 * 设置数据库配置
	 *
	 * @param driver   驱动
	 * @param url      url路径
	 * @param username 用户名
	 * @param password 密码
	 */
	public static void dbConfig(String driver, String url, String username, String password) {
		QuartzUtil.driver = driver;
		QuartzUtil.url = url;
		QuartzUtil.username = username;
		QuartzUtil.password = password;

	}
	/**
	 * 设置数据库配置
	 *
	 * @param driver   驱动
	 * @param url      url路径
	 * @param username 用户名
	 * @param password 密码
	 */
	public static void dbConfig(String driver, String url, String username, String password, String machineCode) {
		dbConfig(driver, url, username, password);
		QuartzUtil.machineCode = machineCode;

	}
	/**
	 * 设置数据库配置
	 *
	 * @param driver   驱动
	 * @param url      url路径
	 * @param username 用户名
	 * @param password 密码
	 */
	public static void dbConfig(String driver, String url, String username, String password, String machineCode,
			String servlet) {
		dbConfig(driver, url, username, password, machineCode);
		QuartzUtil.servlet = servlet;

	}

	/**
	 * 设置服务配置
	 *
	 * @param machineCode 机器码
	 * @param servlet     服务
	 */
	public static void servletConfig(String machineCode, String servlet) {
		QuartzUtil.machineCode = machineCode;
		QuartzUtil.servlet = servlet;
	}

	/**
	 * 记录工作
	 * this.recordTask -> true	记录
	 * -> false	不记录
	 *
	 * @param recordTask 更改是否记录
	 */
	public void recordTask(boolean recordTask) {
		this.recordTask = recordTask;
	}

	/**
	 * 初始化 QuartzIbmUtil 连接构建工具
	 */
	private void init() throws Exception {
		String key = machineCode.concat(StringTool.isEmpty(servlet) ? "" : "_" + servlet);
		// 定时器配置
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Properties properties = new Properties();
		properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, "name_".concat(key));
		properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, "id_".concat(key));

		//线程设置
		properties.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
		properties.put("org.quartz.threadPool.threadCount", "100");
		//容许的最大作业延长时间,是用来设置调度引擎对触发器超时的忍耐时间，简单来说 假设misfireThreshold=6000(单位毫秒)。
		properties.put("org.quartz.jobStore.misfireThreshold", "600000");

		//持久化

		//数据保存方式为持久化
		properties.put(StdSchedulerFactory.PROP_JOB_STORE_CLASS, "org.quartz.impl.jdbcjobstore.JobStoreTX");
		//数据库平台
		properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");

		properties.put(StdSchedulerFactory.PROP_JOB_STORE_USE_PROP, "false");
		properties.put("org.quartz.jobStore.dataSource", "doming");
		properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		properties.put("org.quartz.jobStore.isClustered", "true");
		properties.put("org.quartz.dataSource.doming.driver", driver);
		properties.put("org.quartz.dataSource.doming.URL", url);
		properties.put("org.quartz.dataSource.doming.user", username);
		properties.put("org.quartz.dataSource.doming.password", password);
		// 使用连接时先判断是否有效。
		properties.put("org.quartz.dataSource.doming.validateOnCheckout", "true");
		// 使用连接时先判断是否有效。
		properties.put("org.quartz.dataSource.doming.validationQuery", "select 1");

		// 将idleConnectionTestPeriod 设为 30，这个数字要根据项目情况设定，比8小时小就好
		properties.put("org.quartz.dataSource.doming.maxConnections", "30");

		//集群检查间隔时间
		properties.put("org.quartz.jobStore.clusterCheckinInterval", "20000");

		schedulerFactory.initialize(properties);
		scheduler = schedulerFactory.getScheduler();
	}

	/**
	 * 关闭定时器
	 */
	public void shutdown() {
		try {
			if (scheduler != null && scheduler.isStarted()) {
				if (scheduler.isInStandbyMode()) {
					scheduler.pauseAll();
				}
				scheduler.clear();
				scheduler.shutdown();
				scheduler = null;
			}
		} catch (SchedulerException e) {
			log.error(" 关闭定时器失败", e);
		}
		log.info("关闭定时器成功");
	}

	/**
	 * 销毁工厂
	 */
	public void destroy() {
		if (instance == null) {
			return;
		}
		instance.shutdown();
		instance = null;
		log.info("  销毁定时器工具类完成" + QuartzUtil.class);
	}

	/**
	 * 清理所有定时器记录
	 */
	public void cleanupRecord() throws Exception {
		new SysQuartzCronService().delAll();
		new SysQuartzSimpleService().delAll();
		new SysQuartzTaskService().delAll();

	}
	//endregion

	//region 调度器

	/**
	 * 获取一个调度器
	 *
	 * @return 调度器
	 */
	public Scheduler getScheduler() {
		return scheduler;
	}

	/**
	 * 安排工作
	 *
	 * @param jobDetail 工作详情
	 * @param trigger   触发器
	 */
	public void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
		Scheduler scheduler = getScheduler();
		JobKey jobKey = jobDetail.getKey();
		if (!scheduler.checkExists(jobKey)) {
			synchronized (QuartzUtil.class) {
				if (!scheduler.checkExists(jobKey)) {
					scheduler.scheduleJob(jobDetail, trigger);
				}
			}
		}

		if (!scheduler.isStarted()) {
			synchronized (QuartzUtil.class) {
				if (!scheduler.isStarted()) {
					scheduler.start();
				}
			}
		}
	}
	//endregion

	//region job操作
	/**
	 * 查找调度程序中是否已存在工作
	 *
	 * @param jobName      job名称
	 * @param jobGroupName job所属组名称
	 * @return 存在true
	 */
	public boolean isExistsJob(String jobName, String jobGroupName) {
		if (scheduler == null) {
			return false;
		}
		JobKey jobKey = new JobKey(jobName, jobGroupName);
		try {
			return scheduler.checkExists(jobKey);
		} catch (SchedulerException e) {
			log.error(" 查找调度程序存在job失败", e);
		}
		return false;
	}
	/**
	 * 恢复,切换状态
	 *
	 * @param jobName      job名称
	 * @param jobGroupName job所属组名称
	 */
	public void resumeJob(String jobName, String jobGroupName) {
		try {
			if (scheduler == null) {
				return;
			}
			JobKey jobKey = new JobKey(jobName, jobGroupName);
			scheduler.resumeJob(jobKey);
		} catch (Exception e) {
			log.error("恢复调度程序job失败", e);
		}
	}

	/**
	 * 停止,切换状态
	 *
	 * @param jobName      job名称
	 * @param jobGroupName job所属组名称
	 */
	public void pauseJob(String jobName, String jobGroupName) {
		try {
			if (scheduler == null) {
				return;
			}
			JobKey jobKey = new JobKey(jobName, jobGroupName);
			scheduler.pauseJob(jobKey);
		} catch (Exception e) {
			log.error("停止调度程序job失败", e);
		}
	}

	/**
	 * 移除一个任务
	 *
	 * @param jobName          job名称
	 * @param jobGroupName     job所属组名称
	 * @param triggerName      触发器名称
	 * @param triggerGroupName 触发器所属组名称
	 */
	public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			if (scheduler == null) {
				return;
			}
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			// 停止触发器
			scheduler.pauseTrigger(triggerKey);
			// 移除触发器
			scheduler.unscheduleJob(triggerKey);
			// 删除任务
			scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (Exception e) {
			log.error(" 移除调度程序job失败", e);
		}
	}

	/**
	 * 移除工作
	 *
	 * @param name      工作名称
	 * @param groupName 工作组名称
	 */
	public void removeJob(String name, String groupName) {
		String jobName = name.concat("Job");
		if (isExistsJob(jobName, groupName)) {
			//触发器信息
			removeJob(jobName, groupName, name.concat("Trigger"), groupName);
		}
	}
	//endregion

	//region SIMPLE
	/**
	 * 安排工作
	 *
	 * @param jobClass          工作类
	 * @param startTime         启动时间
	 * @param intervalTime      间隔时间
	 * @param repeatCount       循环次数
	 * @param nameFormat        名字格式
	 * @param groupFormat       组格式
	 * @param descriptionFormat 描述格式
	 * @param args              格式化参数
	 */
	public void scheduleJob(Class<? extends Job> jobClass, Date startTime, Integer intervalTime, Integer repeatCount,
			String nameFormat, String groupFormat, String descriptionFormat, Object... args)
			throws SchedulerException, SQLException {
		//工作信息
		String name = String.format(nameFormat, args);
		String groupName = String.format(groupFormat, args);
		String description = String.format(descriptionFormat, args);

		// 安排工作
		scheduleJob(jobClass, null, startTime, intervalTime, repeatCount, name, groupName, description);
	}

	/**
	 * 安排工作
	 *
	 * @param jobClass    工作类
	 * @param map         工作传递参数
	 * @param startTime   工作开始时间
	 * @param name        工作名称
	 * @param groupName   工作组名称
	 * @param description 工作描述
	 */
	public void scheduleJob(Class<? extends Job> jobClass, Map<String, Object> map, Date startTime,
			Integer intervalTime, Integer repeatCount, String name, String groupName, String description)
			throws SchedulerException, SQLException {
		//启动时间为空 - 比当前时间早
		if (startTime == null || startTime.before(new Date())) {
			return;
		}
		String jobName = name.concat("Job");
		if (isExistsJob(jobName, groupName)) {
			return;
		}
		JobBuilder builder = JobBuilder.newJob(jobClass).withDescription(description.concat("工作"))
				.withIdentity(jobName, groupName);
		if (map != null) {
			builder.usingJobData(new JobDataMap(map));
		}
		recordTask(name, groupName, description, jobClass, map, startTime, intervalTime, repeatCount);
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger().withDescription(description.concat("触发器"))
				.withIdentity(name.concat("Trigger"), groupName).startAt(startTime);
		//存在循环时间
		if (intervalTime != null && intervalTime > 0) {
			SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInMilliseconds(intervalTime);
			//循环次数
			if (repeatCount == null) {
				scheduleBuilder.repeatForever();
			} else if (repeatCount > 1) {
				scheduleBuilder.withRepeatCount(repeatCount - 1);
			}
			triggerBuilder.withSchedule(scheduleBuilder);
		}
		scheduleJob(builder.build(), triggerBuilder.build());

	}
	//endregion

	//region CRON
	/**
	 * 安排工作
	 *
	 * @param jobClass          工作类
	 * @param cron              cron时间语句
	 * @param nameFormat        名字格式
	 * @param groupFormat       组格式
	 * @param descriptionFormat 描述格式
	 * @param args              格式化参数
	 */
	public void scheduleJob(Class<? extends Job> jobClass, String cron, String nameFormat, String groupFormat,
			String descriptionFormat, Object... args) throws SchedulerException, SQLException {
		//工作信息
		String name = String.format(nameFormat, args);
		String groupName = String.format(groupFormat, args);
		String description = String.format(descriptionFormat, args);
		//安排工作
		scheduleJob(jobClass, new JobDataMap(), cron, name, groupName, description);

	}

	/**
	 * 安排工作
	 *
	 * @param jobClass    工作类
	 * @param map         工作传递参数
	 * @param cron        工作执行CRON语句
	 * @param name        工作名称
	 * @param groupName   工作组名称
	 * @param description 工作描述
	 */
	public void scheduleJob(Class<? extends Job> jobClass, Map<String, Object> map, String cron, String name,
			String groupName, String description) throws SchedulerException, SQLException {
		String jobName = name.concat("Job");
		if (isExistsJob(jobName, groupName)) {
			return;
		}

		JobBuilder builder = JobBuilder.newJob(jobClass).withDescription(description.concat("工作"))
				.withIdentity(jobName, groupName);
		if (map != null) {
			builder.usingJobData(new JobDataMap(map));
		}
		recordTask(name, groupName, description, jobClass, map, cron);
		Trigger trigger = TriggerBuilder.newTrigger().withDescription(description.concat("触发器"))
				.withSchedule(CronScheduleBuilder.cronSchedule(cron)).withIdentity(name.concat("Trigger"), groupName)
				.build();
		scheduleJob(builder.build(), trigger);
	}
	//endregion

	//region 记录
	/**
	 * 记录任务
	 *
	 * @param name         任务名称
	 * @param group        任务组
	 * @param description  任务描述
	 * @param jobClass     工作类
	 * @param map          工作传递参数
	 * @param startTime    开始时间
	 * @param intervalTime 间隔时间
	 * @param repeatCount  循环次数
	 */
	private void recordTask(String name, String group, String description, Class<? extends Job> jobClass,
			Map<String, Object> map, Date startTime, Integer intervalTime, Integer repeatCount) throws SQLException {
		if (!recordTask) {
			return;
		}
		SysQuartzTaskService taskService = new SysQuartzTaskService();
		SysQuartzSimpleService simpleService = new SysQuartzSimpleService();
		String taskId = taskService.findId(name, group);
		JSONObject content = new JSONObject(map);
		Date nowTime = new Date();
		if (StringTool.notEmpty(taskId)) {
			taskService.update(jobClass.getName(), content.toString(), description, nowTime, taskId);
			String simpleId = simpleService.findId(taskId);
			if (StringTool.notEmpty(taskId)) {
				simpleService.update(startTime, intervalTime, repeatCount, nowTime, simpleId);
				return;
			}
		} else {
			taskId = taskService.save(name, group, description, jobClass.getName(), content.toString(), nowTime);
		}
		simpleService.save(taskId, startTime, intervalTime, repeatCount, nowTime);

	}

	/**
	 * 记录任务
	 *
	 * @param name        任务名称
	 * @param group       任务组
	 * @param description 任务描述
	 * @param jobClass    工作类
	 * @param map         工作传递参数
	 * @param cron        CRON表达式
	 */
	private void recordTask(String name, String group, String description, Class<? extends Job> jobClass,
			Map<String, Object> map, String cron) throws SQLException {
		if (!recordTask) {
			return;
		}
		SysQuartzTaskService taskService = new SysQuartzTaskService();
		SysQuartzCronService cronService = new SysQuartzCronService();
		String taskId = taskService.findId(name, group);
		JSONObject content = new JSONObject(map);
		Date nowTime = new Date();
		if (StringTool.notEmpty(taskId)) {
			taskService.update(jobClass.getName(), content.toString(), description, nowTime, taskId);
			String cronId = cronService.findId(taskId);
			if (StringTool.notEmpty(taskId)) {
				cronService.update(cron, nowTime, cronId);
				return;
			}
		} else {
			taskId = taskService.save(name, group, description, jobClass.getName(), content.toString(), nowTime);
		}

		cronService.save(taskId, cron, nowTime);
	}

	/**
	 * 根据记录信息启动
	 */
	public void startFormRecord() throws SQLException, ClassNotFoundException, SchedulerException {
		recordTask = false;
		List<Map<String, Object>> quartzRecords = new SysQuartzCronService().listAllQuartzRecord();
		for (Map<String, Object> quartzRecord : quartzRecords) {
			Class<?> clazz = ClassTool.getClass(quartzRecord.get("TASK_CLASS_").toString());
			if (ClassTool.notAssignableFrom(clazz, Job.class)) {
				log.error("查询到的工作类没有继承Job接口，开启错误：{}", quartzRecord.get("TASK_CLASS_"));
				continue;
			}
			Class<? extends Job> jobClass = (Class<? extends Job>) clazz;

			String taskName = quartzRecord.get("TASK_NAME_").toString();
			String taskGroup = quartzRecord.get("TASK_GROUP_").toString();
			String taskDescription = quartzRecord.get("TASK_DESCRIPTION_").toString();
			Map<String, Object> taskContent = JSONObject
					.parseObject(quartzRecord.get("TASK_CONTENT_").toString(), Map.class);

			String cronExpression = quartzRecord.get("CRON_EXPRESSION_").toString();
			scheduleJob(jobClass, taskContent, cronExpression, taskName, taskGroup, taskDescription);
		}

		quartzRecords = new SysQuartzSimpleService().listAllQuartzRecord();
		for (Map<String, Object> quartzRecord : quartzRecords) {
			Class<?> clazz = ClassTool.getClass(quartzRecord.get("TASK_CLASS_").toString());
			if (ClassTool.notAssignableFrom(clazz, Job.class)) {
				log.error("查询到的工作类没有继承Job接口，开启错误：{}", quartzRecord.get("TASK_CLASS_"));
				continue;
			}
			Class<? extends Job> jobClass = (Class<? extends Job>) clazz;

			String taskName = quartzRecord.get("TASK_NAME_").toString();
			String taskGroup = quartzRecord.get("TASK_GROUP_").toString();
			String taskDescription = quartzRecord.get("TASK_DESCRIPTION_").toString();
			Map<String, Object> taskContent = JSONObject
					.parseObject(quartzRecord.get("TASK_CONTENT_").toString(), Map.class);
			Date startTime = new Date(NumberTool.getLong(quartzRecord.get("START_TIME_")));
			Integer intervalTime = NumberTool.getInteger(quartzRecord.get("INTERVAL_TIME_"), null);
			Integer repeatCount = NumberTool.getInteger(quartzRecord.get("REPEAT_COUNT_"), null);

			scheduleJob(jobClass, taskContent, startTime, intervalTime, repeatCount, taskName, taskGroup,
					taskDescription);
		}
		recordTask = true;
	}
	//endregion

}
