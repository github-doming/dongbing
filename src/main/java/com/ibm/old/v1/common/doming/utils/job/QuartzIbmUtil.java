package com.ibm.old.v1.common.doming.utils.job;

import c.a.config.SysConfig;
import c.a.config.core.JdbcDataSourceConfig;
import c.a.util.core.bean.BeanThreadLocal;
import com.ibm.old.v1.client.core.job.CheckIdcJob;
import com.ibm.old.v1.client.core.job.CheckSgWinJob;
import com.ibm.old.v1.client.core.job.CheckWs2Job;
import com.ibm.old.v1.client.core.job.bet.*;
import com.ibm.old.v1.client.core.job.manage.ClientDataCleaningJob;
import com.ibm.old.v1.common.doming.configs.QuartzConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.DateTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.job.QuartzUtil;
import org.quartz.*;

import java.text.ParseException;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
/**
 * @Description: 定时器工具类
 * @Author: Dongming
 * @Date: 2018-12-07 15:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class QuartzIbmUtil {

	protected static final Logger log = LogManager.getLogger(QuartzIbmUtil.class);

	private static volatile QuartzUtil util = null;

	/**
	 * 获取定时器工具类
	 *
	 * @return 定时器工具类
	 */
	private static QuartzUtil findQuartzUtil() {
		if (util == null) {
			synchronized (QuartzIbmUtil.class) {
				if (util == null) {
					try {
						String machineCode = BeanThreadLocal
								.find(SysConfig.findInstance().findMap().get("comm.local.machine"), "");
						if (StringTool.isEmpty(machineCode)) {
							machineCode = RandomTool.getNumLetter32();
						}
						String driver = JdbcDataSourceConfig.findInstance().getDriver();
						String url = JdbcDataSourceConfig.findInstance().getUrl();
						String username = JdbcDataSourceConfig.findInstance().getUsername();
						String password = JdbcDataSourceConfig.findInstance().getPassword();
						QuartzUtil.dbConfig(driver, url, username, password, machineCode);
						util = QuartzUtil.findInstance();
					} catch (Exception e) {
						log.error("初始化定时器工具类失败", e);
					}
				}
			}
		}
		return util;
	}
	public static void destroy() throws Exception {
		QuartzUtil.findInstance().destroy();
	}

	/**
	 * 定时检查用户job
	 *
	 * @param existHmId    盘口会员存在id
	 * @param handicapCode 盘口code
	 * @param bean         返回结果
	 */
	public static void checkUser(String existHmId, String handicapCode, JsonResultBeanPlus bean) {
		// 添加定时检查的任务
		String jobName = String.format(QuartzConfig.IDENTITY_NAME_CHECK, existHmId, handicapCode, "Job");
		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_CHECK, handicapCode);

		// 存在该job即返回，不存在job则重新添加job任务
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			return;
		}

		// 设置定时任务
		JobBuilder builder;

		//设置不同盘口校验时间，秒
		int second = DateTool.getSecond();
		String cron;
		switch (handicapCode) {
			case "WS2":
				builder = JobBuilder.newJob(CheckWs2Job.class);
				// 定时20秒检验一次 19/20 * 8-3 * * ? *
				cron = second%20 +"/20 * 8-3 * * ? *";
				break;
			case "IDC":
				builder = JobBuilder.newJob(CheckIdcJob.class);
				//定时1分钟检验一次 20 * 9-3 * * ? *
				cron = second +" * 8-3 * * ? *";
				break;
			case "SGWIN":
				builder = JobBuilder.newJob(CheckSgWinJob.class);
				// 定时30秒检验一次 19/30 * 8-3 * * ? *
				cron = second%30 +"/30 * 8-3 * * ? *";
				break;
			default:
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return;
		}
		// 传递定时参数
		JobDataMap map = new JobDataMap();
		map.put("existHmId", existHmId);
		map.put("handicapCode", handicapCode);

		// 定时检验
		JobDetail jobDetail = builder
				.withDescription(String.format(QuartzConfig.DESCRIPTION_CHECK, existHmId, handicapCode, "工作"))
				.withIdentity(jobName, jobGroupName).usingJobData(map).build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withDescription(String.format(QuartzConfig.DESCRIPTION_CHECK, existHmId, handicapCode, "触发器"))
				.withIdentity(String.format(QuartzConfig.IDENTITY_NAME_CHECK, existHmId, handicapCode, "Trigger"),
						String.format(QuartzConfig.IDENTITY_GROUP_CHECK, handicapCode))
				.withSchedule(cronSchedule(cron)).build();

		// 启动定时器
		try {
			findQuartzUtil().scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			log.error("启动定时检查用户job失败", e);
		}
	}

	/**
	 * 投注job
	 *
	 * @param existHmId    盘口会员存在id
	 * @param handicapEnum 盘口code枚举类
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @param betTime      投注时间
	 * @return 返回结果
	 */
	public static JsonResultBeanPlus betJob(String existHmId, IbmHandicapEnum handicapEnum, String gameCode,
			Object period, Date betTime) throws Exception {

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String jobName = String.format(QuartzConfig.IDENTITY_NAME_BET, existHmId, handicapEnum.name(), gameCode, period, "Job");

		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_BET, handicapEnum.name());
		// 存在该job即返回，不存在job则重新添加job任务
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			bean.success();
			return bean;
		}
		// 设置定时任务
		JobBuilder builder;
		switch (handicapEnum) {
			case WS2:
				builder = JobBuilder.newJob(BetWs2Job.class);
				break;
			case IDC:
				builder = JobBuilder.newJob(BetIDCJob.class);
				break;
			case SGWIN:
				builder=JobBuilder.newJob(BetSgWinJob.class);
				break;
			default:
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
		}
		JobDataMap map = new JobDataMap();
		// 传递定时参数
		map.put("existHmId", existHmId);
		map.put("period", period);
		map.put("gameCode", gameCode);

		//设置投注定时器
		JobDetail jobDetail = builder
				.withDescription(String.format(QuartzConfig.DESCRIPTION_BET, existHmId, handicapEnum.name(), "工作"))
				.withIdentity(jobName, jobGroupName).usingJobData(map).build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withDescription(String.format(QuartzConfig.DESCRIPTION_BET, existHmId, handicapEnum.name(), "触发器"))
				.withIdentity(
						String.format(QuartzConfig.IDENTITY_NAME_BET, existHmId, handicapEnum.name(), gameCode, period,
								"Trigger"), String.format(QuartzConfig.IDENTITY_GROUP_BET, handicapEnum.name()))
				.startAt(betTime).build();
		// 启动定时器
		findQuartzUtil().scheduleJob(jobDetail, trigger);
		bean.success();
		return bean;
	}

	/**
	 * 投注结果job定时器<br>
	 * 下一期开奖的时候开始，每10秒启动一次，循环10次
	 *
	 * @param existHmId    存在盘口会员id
	 * @param handicapEnum 盘口枚举
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @return 设置结果
	 */
	public static JsonResultBeanPlus betProfitJob(String existHmId, IbmHandicapEnum handicapEnum, String gameCode,
			String period) throws SchedulerException, ParseException {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String jobName = String
				.format(QuartzConfig.IDENTITY_NAME_PROFIT, existHmId, handicapEnum.name(), gameCode, period, "Job");
		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_PROFIT, handicapEnum.name());
		// 存在该job即返回，不存在job则重新添加job任务
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			bean.success();
			return bean;
		}

		// 设置定时任务
		JobBuilder builder;
		switch (handicapEnum) {
			case WS2:
				builder = JobBuilder.newJob(BetProfitWs2Job.class);
				break;
			case IDC:
				builder = JobBuilder.newJob(BetProfitIdcJob.class);
				break;
			case SGWIN:
				builder=JobBuilder.newJob(BetProfitSgWinJob.class);
				break;
			default:
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
		}
		IbmGameEnum gameEnum = IbmGameEnum.valueOf(gameCode);
		long waitTime;
		ScheduleBuilder scheduleBuilder;
		switch (gameEnum) {
			case PK10:
				waitTime = DateTool.getMillisecondsMinutes(3);
				scheduleBuilder = simpleSchedule()
						//间隔秒数
						.withIntervalInSeconds(10)
						//10次重复
						.withRepeatCount(30);
				break;
			case XYFT:
				waitTime = 30 * 1000L;
				scheduleBuilder = simpleSchedule()
						//间隔秒数
						.withIntervalInSeconds(10)
						//10次重复
						.withRepeatCount(10);
				break;
			default:
				throw new RuntimeException(IbmCodeEnum.IBM_404_GAME.getMsgCn());
		}
		Date startDate = new Date(PeriodTool.getDrawTime(gameCode, period) + waitTime);

		JobDataMap map = new JobDataMap();
		// 传递定时参数
		map.put("existHmId", existHmId);
		map.put("period", period);
		map.put("gameCode", gameCode);
		JobDetail jobDetail = builder
				.withDescription(String.format(QuartzConfig.DESCRIPTION_PROFIT, existHmId, handicapEnum.name(), "工作"))
				.withIdentity(jobName, jobGroupName).usingJobData(map).build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withDescription(String.format(QuartzConfig.DESCRIPTION_PROFIT, existHmId, handicapEnum.name(), "触发器"))
				.withIdentity(String.format(QuartzConfig.IDENTITY_NAME_PROFIT, existHmId, handicapEnum.name(), gameCode,
						period, "Trigger"), String.format(QuartzConfig.IDENTITY_GROUP_PROFIT, handicapEnum.name()))
				.startAt(startDate).withSchedule(scheduleBuilder).build();
		// 启动定时器
		findQuartzUtil().scheduleJob(jobDetail, trigger);
		bean.success();

		return bean;
	}

	/**
	 * 暂停定时检查用户job
	 *
	 * @param existHmId    盘口会员存在id
	 * @param handicapCode 盘口code
	 */
	public static void pauseCheckJob(String existHmId, String handicapCode) {
		String jobName = String.format(QuartzConfig.IDENTITY_NAME_CHECK, existHmId, handicapCode, "Job");
		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_CHECK, handicapCode);
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			findQuartzUtil().pauseJob(jobName, jobGroupName);
		}
	}

	/**
	 * 恢复定时检查用户job
	 *
	 * @param existHmId    盘口会员存在id
	 * @param handicapCode 盘口code
	 */
	public static void resumeCheckJob(String existHmId, String handicapCode) {
		String jobName = String.format(QuartzConfig.IDENTITY_NAME_CHECK, existHmId, handicapCode, "Job");
		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_CHECK, handicapCode);
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			findQuartzUtil().resumeJob(jobName, jobGroupName);
		}
	}


	/**
	 * 移除登陆job
	 *
	 * @param existHmId    盘口会员存在id
	 * @param handicapEnum 盘口code枚举类
	 */
	public static void removeLoginJob(String existHmId, IbmHandicapEnum handicapEnum) {
		String jobName = String.format(QuartzConfig.IDENTITY_NAME_LOGIN, existHmId, handicapEnum.name(), "Job");
		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_LOGIN, handicapEnum.name());
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			String triggerName = String
					.format(QuartzConfig.IDENTITY_NAME_LOGIN, existHmId, handicapEnum.name(), "Trigger");
			String triggerGroupName = String.format(QuartzConfig.IDENTITY_GROUP_LOGIN, handicapEnum.name());
			findQuartzUtil().removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
		}
	}

	/**
	 * 移除定时检查用户job
	 *
	 * @param existHmId    盘口会员存在id
	 * @param handicapCode 盘口code
	 */
	public static void removeCheckJob(String existHmId, String handicapCode) {
		String jobName = String.format(QuartzConfig.IDENTITY_NAME_CHECK, existHmId, handicapCode, "Job");
		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_CHECK, handicapCode);

		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			String triggerName = String.format(QuartzConfig.IDENTITY_NAME_CHECK, existHmId, handicapCode, "Trigger");
			String triggerGroupName = String.format(QuartzConfig.IDENTITY_GROUP_CHECK, handicapCode);
			findQuartzUtil().removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
		}
	}

	/**
	 * 移除投注Job
	 *
	 * @param existHmId    存在盘口会员id
	 * @param handicapEnum 盘口枚举
	 * @param gameCode     游戏code
	 * @param period       期数
	 */
	public static void removeBetJob(String existHmId, IbmHandicapEnum handicapEnum, String gameCode, Object period) {
		String jobName = String
				.format(QuartzConfig.IDENTITY_NAME_BET, existHmId, handicapEnum.name(), gameCode, period, "Job");
		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_BET, handicapEnum.name());
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			String triggerName = String
					.format(QuartzConfig.IDENTITY_NAME_BET, existHmId, handicapEnum.name(), gameCode, period,
							"Trigger");
			String triggerGroupName = String.format(QuartzConfig.IDENTITY_GROUP_BET, handicapEnum.name());
			findQuartzUtil().removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
		}
	}

	/**
	 * 移除投注结果job
	 *
	 * @param existHmId    存在盘口会员id
	 * @param handicapEnum 盘口枚举
	 * @param gameCode     游戏code
	 * @param period       期数
	 */
	public static void removeBetProfitJob(String existHmId, IbmHandicapEnum handicapEnum, String gameCode,
			Object period) {
		//工作信息
		String jobName = String
				.format(QuartzConfig.IDENTITY_NAME_PROFIT, existHmId, handicapEnum.name(), gameCode, period, "Job");
		String jobGroupName = String.format(QuartzConfig.IDENTITY_GROUP_PROFIT, handicapEnum.name());
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			//触发器信息
			String triggerName = String
					.format(QuartzConfig.IDENTITY_NAME_PROFIT, existHmId, handicapEnum.name(), gameCode, period,
							"Trigger");
			String triggerGroupName = String.format(QuartzConfig.IDENTITY_GROUP_PROFIT, handicapEnum.name());

			findQuartzUtil().removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
		}
	}

	/**
	 * 移除所有Job
	 *
	 * @param existHmId    存在盘口会员id
	 * @param handicapEnum 盘口枚举
	 */
	public static void removeJob(String existHmId, IbmHandicapEnum handicapEnum) throws Exception {
		for (IbmGameEnum gameEnum : IbmGameEnum.values()) {
			removeBetJob(existHmId, handicapEnum, gameEnum.name(), PeriodTool.findPeriod(gameEnum.name()));
			removeBetProfitJob(existHmId, handicapEnum, gameEnum.name(), PeriodTool.findPeriod(gameEnum.name()));
			removeBetProfitJob(existHmId, handicapEnum, gameEnum.name(), PeriodTool.findLotteryPeriod(gameEnum.name()));
		}
		removeCheckJob(existHmId, handicapEnum.getCode());
		removeLoginJob(existHmId, handicapEnum);
	}

	/**
	 * 定时清理冗余数据
	 */
	public static void clientDataCleaning(){
		//工作信息
		String jobName = String.format(QuartzConfig.CLIENT_DATA_CLEANING, "Job");
		String jobGroupName = String.format(QuartzConfig.CLIENT_DATA_GROUP_CLEANING, "工作");

		//触发器信息
		String triggerName = String.format(QuartzConfig.CLIENT_DATA_CLEANING, "Trigger");
		String triggerGroupName = String.format(QuartzConfig.CLIENT_DATA_GROUP_CLEANING, "工作");

		// 存在该job即删除，不存在job则重新添加job任务
		if (findQuartzUtil().isExistsJob(jobName, jobGroupName)) {
			findQuartzUtil().removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
		}

		JobBuilder builder = JobBuilder.newJob(ClientDataCleaningJob.class);
		String cron = "0 0 5 1/2 * ? ";

		// 定时检验
		JobDetail jobDetail = builder.withDescription(String.format(QuartzConfig.CLIENT_DATA_CLEANING, "工作"))
				.withIdentity(jobName, jobGroupName).build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withDescription(String.format(QuartzConfig.CLIENT_DATA_GROUP_CLEANING, "触发器"))
				.withIdentity(triggerName, triggerGroupName).withSchedule(cronSchedule(cron))
				.build();

		// 启动定时器
		try {
			//设置定时器
			findQuartzUtil().scheduleJob(jobDetail, trigger);
			log.info("启动定时清理冗余数据job成功");
		} catch (SchedulerException e) {
			log.error("启动定时清理冗余数据job失败", e);
		}

	}

}
