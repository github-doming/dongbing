package com.ibs.plan.common.tools;
import c.a.config.core.JdbcDataSourceConfig;
import com.ibs.common.configs.QuartzConfig;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.core.job.CheckMemberJob;
import com.ibs.plan.module.client.core.job.ClientCleaningJob;
import com.ibs.plan.module.client.core.job.ClientHeartbeatJob;
import com.ibs.plan.module.client.core.job.bet.BaseBetJob;
import org.doming.core.tools.DateTool;
import org.doming.develop.job.QuartzUtil;
import org.quartz.JobDataMap;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ibs.plan.common.core.configs.PlanQuartzConfig.*;
/**
 * 定时器工具类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 14:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class QuartzTool {

	//region 客户端定时器

	private static String clientCode;
	private static QuartzUtil clientQuartz = null;
	/**
	 * 获取定时器工具类
	 *
	 * @return 定时器工具类
	 */
	private static QuartzUtil findClientQuartz() throws Exception {
		if (clientQuartz == null) {
			JdbcDataSourceConfig dbConfig = JdbcDataSourceConfig.findInstance();
			String driver = dbConfig.getDriver();
			String url = dbConfig.getUrl();
			String username = dbConfig.getUsername();
			String password = dbConfig.getPassword();
			QuartzUtil.dbConfig(driver, url, username, password, clientCode);
			clientQuartz = QuartzUtil.findInstance();

		}
		return clientQuartz;

	}
	/**
	 * 开启客户端定时器
	 *
	 * @param clientCode 客户端编码
	 */
	public static void startClient(String clientCode) throws Exception {
		QuartzTool.clientCode = clientCode;
		QuartzUtil quartz = findClientQuartz();
		quartz.startFormRecord();
		quartz.recordTask(false);
		quartz.scheduleJob(ClientHeartbeatJob.class,
				new Date(System.currentTimeMillis() + DateTool.getMillisecondsMinute()), 60 * 1000, null,
				NAME_HEARTBEAT, GROUP_HEARTBEAT, DESCRIPTION_HEARTBEAT, clientCode);
		//服务器数据整理
		//循环方式：每天5点运行，一直运行
		String cron = "0 0 5 * * ? ";
		quartz.scheduleJob(ClientCleaningJob.class, cron, NAME_CLEANING, GROUP_CLEANING, DESCRIPTION_CLEANING,
				clientCode);
		quartz.recordTask(true);
	}

	/**
	 * 保存定时校验工作
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 */
	public static void saveCheckJob(String existHmId, HandicapUtil.Code handicapCode) throws Exception {
		String cron = HandicapUtil.handicap(handicapCode).checkCron();
		JobDataMap map = new JobDataMap();
		// 传递定时参数
		map.put("existHmId", existHmId);
		map.put("handicapCode", handicapCode.name());

		String name = String.format(QuartzConfig.NAME_CHECK, existHmId, handicapCode.name());
		String groupName = String.format(QuartzConfig.GROUP_CHECK, handicapCode.name());
		String description = String.format(QuartzConfig.DESCRIPTION_CHECK, existHmId, handicapCode.name());
		// 启动定时器
		findClientQuartz().scheduleJob(CheckMemberJob.class, map, cron, name, groupName, description);
	}

	/**
	 * 移除盘口会员定时检验job
	 *
	 * @param existHmId    已存在盘口id
	 * @param handicapCode 盘口code
	 */
	public static void removeCheckJob(String existHmId, String handicapCode) throws Exception {
		String name = String.format(QuartzConfig.NAME_CHECK, existHmId, handicapCode);
		String groupName = String.format(QuartzConfig.GROUP_CHECK, handicapCode);
		String jobName = name.concat("Job");
		if (findClientQuartz().isExistsJob(jobName, groupName)) {
			String triggerName = name.concat("Job");
			String triggerGroupName = String.format(QuartzConfig.GROUP_CHECK, handicapCode);
			findClientQuartz().removeJob(jobName, groupName, triggerName, triggerGroupName);
		}
	}

	/**
	 * 保存投注Job
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @param startTime    投注时间
	 * @param betIds       投注主键
	 */
	public static void saveBetJob(Class<? extends BaseBetJob> jobClass, String existHmId, HandicapUtil.Code handicapCode,
			GameUtil.Code gameCode, Object period, Date startTime, List<String> betIds) throws Exception {
		Map<String, Object> map = new HashMap<>(3);
		// 传递定时参数
		map.put("existHmId", existHmId);
		map.put("gameCode", gameCode);
		map.put("period", period);
		map.put("betIds", betIds);
		//设置投注定时器
		String name = String.format(QuartzConfig.NAME_BET, existHmId, handicapCode.name(), gameCode.name(), period);
		String groupName = String.format(QuartzConfig.GROUP_BET, handicapCode.name());
		String description = String.format(QuartzConfig.DESCRIPTION_BET, existHmId, handicapCode.name());
		// 启动定时器
		findClientQuartz().scheduleJob(jobClass, map, startTime, null, null, name, groupName, description);
	}

	public static void shutdownClient() {
		if (clientQuartz == null) {
			return;
		}
		clientQuartz.destroy();
		clientQuartz = null;

	}
	//endregion
}
