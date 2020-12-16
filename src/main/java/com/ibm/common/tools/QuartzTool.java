package com.ibm.common.tools;

import c.a.config.SysConfig;
import c.a.config.core.JdbcDataSourceConfig;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.QuartzConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.job.*;
import com.ibm.follow.servlet.client.core.job.bet.ClientMergeBetJob;
import com.ibm.follow.servlet.client.core.job.bet.agent.*;
import com.ibm.follow.servlet.client.core.job.bet.member.*;
import com.ibm.follow.servlet.cloud.core.job.CloudMergeBetJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.job.QuartzUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ibm.common.core.configs.QuartzConfig.*;

/**
 * @Description: 定时器工具类
 * @Author: Dongming
 * @Date: 2019-08-27 10:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class QuartzTool {
	private static final Logger log = LogManager.getLogger(QuartzTool.class);

	//region 中心端定时器

	private static QuartzUtil cloudQuartz = null;

	/**
	 * 获取定时器工具类
	 *
	 * @return 定时器工具类
	 */
	private static QuartzUtil findCloudQuartz() {
		if (cloudQuartz == null) {
			try {
				Object machineCode = SysConfig.findInstance().findMap()
						.getOrDefault("comm.local.machine", IpTool.getIpAddress());
				if (StringTool.isEmpty(machineCode)) {
					machineCode = RandomTool.getNumLetter32();
				}
				String driver = JdbcDataSourceConfig.findInstance().getDriver();
				String url = JdbcDataSourceConfig.findInstance().getUrl();
				String username = JdbcDataSourceConfig.findInstance().getUsername();
				String password = JdbcDataSourceConfig.findInstance().getPassword();
				QuartzUtil.dbConfig(driver, url, username, password, machineCode.toString(), null);
				cloudQuartz = QuartzUtil.findInstance();
			} catch (Exception e) {
				log.error("初始化定时器工具类失败", e);
			}
		}
		return cloudQuartz;
	}

	/**
	 * 保存合并投注Job
	 *
	 * @param handicapId       盘口主键
	 * @param handicapMemberId 盘口会员主键
	 * @param handicapCode     盘口code
	 * @param gameCode         游戏code
	 * @param period           期数
	 * @param betRate          投注比例
	 * @param betMode          投注模式
	 * @param startTime        投注时间
	 * @return 投注结果
	 */
	public static JsonResultBeanPlus saveCloudMergeBetJob(String handicapId, String handicapMemberId,
														  HandicapUtil.Code handicapCode, GameUtil.Code gameCode, Object period, double betRate, String betMode,
														  Date startTime) throws SchedulerException, SQLException {
		// 传递定时参数
		JobDataMap map = new JobDataMap();
		map.put("handicapMemberId", handicapMemberId);
		map.put("handicapId", handicapId);
		map.put("betMode", betMode);
		QuartzUtil quartz = findCloudQuartz();
		quartz.recordTask(false);
		return saveMergeBetJob(quartz, handicapMemberId, handicapCode, gameCode, period, betRate, startTime,
				CloudMergeBetJob.class, map);

	}
	//endregion

	//region 客户端定时器

	//region 基础方法

	private static QuartzUtil clientQuartz = null;

	private static QuartzUtil findClientQuartz() {
		if (clientQuartz == null) {
			try {
				String driver = JdbcDataSourceConfig.findInstance().getDriver();
				String url = JdbcDataSourceConfig.findInstance().getUrl();
				String username = JdbcDataSourceConfig.findInstance().getUsername();
				String password = JdbcDataSourceConfig.findInstance().getPassword();
				QuartzUtil.dbConfig(driver, url, username, password);
				clientQuartz = QuartzUtil.findInstance();
			} catch (Exception e) {
				log.error("初始化客户端定时器失败", e);
			}
		}
		return clientQuartz;
	}

	/**
	 * 开启客户端定时器
	 *
	 * @param clientCode 客户端编码
	 */
	public static void startClient(String clientCode) throws SQLException, ClassNotFoundException, SchedulerException {
		QuartzUtil.servletConfig(clientCode, null);
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
	 * 开启客户端定时器
	 *
	 * @param clientCode 客户端编码
	 */
	public static void startVrClient(String clientCode) throws SQLException, ClassNotFoundException, SchedulerException {
		QuartzUtil.servletConfig(clientCode, null);
		QuartzUtil quartz = findClientQuartz();
		quartz.startFormRecord();
		quartz.recordTask(false);
		quartz.scheduleJob(VrClientHeartbeatJob.class,
				new Date(System.currentTimeMillis() + DateTool.getMillisecondsMinute()), 60 * 1000, null,
				NAME_HEARTBEAT, GROUP_HEARTBEAT, DESCRIPTION_HEARTBEAT, clientCode);
		//服务器数据整理
		//循环方式：每天5点运行，一直运行
		String cron = "0 0 5 * * ? ";
		quartz.scheduleJob(VrClientCleaningJob.class, cron, NAME_CLEANING, GROUP_CLEANING, DESCRIPTION_CLEANING,
				clientCode);
		quartz.recordTask(true);
	}

	/**
	 * 关闭定时器 - 不会清理记录
	 */
	public static void shutdownClient() {
		if (clientQuartz == null) {
			log.debug("销毁客户端定时器完成，不存在定时器");
			return;
		}
		clientQuartz.destroy();
		clientQuartz = null;

	}

	/**
	 * 销毁定时器 - 清理所有信息
	 */
	public static void destroyClient() throws Exception {
		if (clientQuartz == null) {
			log.debug("销毁客户端定时器完成，不存在定时器");
			return;
		}
		clientQuartz.destroy();
		clientQuartz.cleanupRecord();
		clientQuartz = null;

	}

	public static void resumeJob(String jobName, String groupName) {
		QuartzUtil quartz = findClientQuartz();
		try {
			quartz.resumeJob(jobName, groupName);
		} catch (Exception e) {
			log.error("恢复心跳检测失败", e);
		}
	}

	/**
	 * 暂停job
	 *
	 * @param jobName
	 * @param groupName
	 */
	public static void pauseJob(String jobName, String groupName) {
		QuartzUtil quartz = findClientQuartz();
		try {
			quartz.pauseJob(jobName, groupName);
		} catch (Exception e) {
			log.error("暂停心跳检测失败", e);
		}
	}
	//endregion

	//region 会员定时器

	/**
	 * 保存定时校验工作
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 */
	public static void saveCheckHmJob(String existHmId, HandicapUtil.Code handicapCode)
			throws SchedulerException, SQLException {
		int second = RandomTool.getInt(60);
		String cron;
		switch (handicapCode) {
			case IDC:
			case NCOM1:
			case NCOM2:
			case BW:
				cron = second % 30 + "/30 * 7-3 * * ? *";
				break;
			case SGWIN:
			case HQ:
			case COM:
			case NEWCC:
			case UN:
			case NEWWS:
			case NEWMOA:
			case FC:
				cron = second % 10 + "/10 * 7-3 * * ? *";
				break;
			default:
				throw new RuntimeException("尚未完成的盘口类型捕捉:".concat(handicapCode.name()));
		}
		JobDataMap map = new JobDataMap();
		// 传递定时参数
		map.put("existHmId", existHmId);
		map.put("handicapCode", handicapCode);

		String name = String.format(QuartzConfig.NAME_CHECK, existHmId, handicapCode.name());
		String groupName = String.format(QuartzConfig.GROUP_BET, handicapCode.name());
		String description = String.format(QuartzConfig.DESCRIPTION_BET, existHmId, handicapCode.name());
		// 启动定时器
		findClientQuartz().scheduleJob(CheckMemberJob.class, map, cron, name, groupName, description);
	}

	/**
	 * 保存定时校验工作
	 *
	 * @param existHaId    已存在盘口代理id
	 * @param handicapCode 盘口code
	 */
	public static void saveCheckHaJob(String existHaId, HandicapUtil.Code handicapCode)
			throws SchedulerException, SQLException {
		int second = RandomTool.getInt(60);
		String cron;
		switch (handicapCode) {
			case IDC:
			case NCOM1:
			case NCOM2:
			case BW:
				cron = second % 30 + "/30 * 7-3 * * ? *";
				break;
			case SGWIN:
			case HQ:
			case COM:
			case NEWCC:
			case UN:
			case NEWWS:
			case NEWMOA:
			case FC:
				cron = second % 10 + "/10 * 7-3 * * ? *";
				break;
			default:
				throw new RuntimeException("尚未完成的盘口类型捕捉:".concat(handicapCode.name()));
		}
		JobDataMap map = new JobDataMap();
		// 传递定时参数
		map.put("existHaId", existHaId);
		map.put("handicapCode", handicapCode);

		String name = String.format(QuartzConfig.NAME_CHECK, existHaId, handicapCode.name());
		String groupName = String.format(QuartzConfig.GROUP_BET, handicapCode.name());
		String description = String.format(QuartzConfig.DESCRIPTION_BET, existHaId, handicapCode.name());
		// 启动定时器
		findClientQuartz().scheduleJob(CheckAgentJob.class, map, cron, name, groupName, description);
	}

	/**
	 * 保存投注Job
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @param startTime    投注时间
	 * @return 投注结果
	 */
	public static JsonResultBeanPlus saveBetJob(String existHmId, HandicapUtil.Code handicapCode,
												GameUtil.Code gameCode, Object period, Date startTime) throws SchedulerException, SQLException {
		// 设置定时任务
		Class<? extends Job> jobClass;
		switch (handicapCode) {
			case IDC:
				jobClass = BetIdcJob.class;
				break;
			case SGWIN:
				jobClass = BetSgwinJob.class;
				break;
			case HQ:
				jobClass = BetHqJob.class;
				break;
			case NCOM1:
				jobClass = BetNcom1Job.class;
				break;
			case NCOM2:
				jobClass = BetNcom2Job.class;
				break;
			case BW:
				jobClass = BetBwJob.class;
				break;
			case COM:
				jobClass = BetComJob.class;
				break;
			case NEWCC:
				jobClass = BetNewCcJob.class;
				break;
			case UN:
				jobClass = BetUNJob.class;
				break;
			case NEWWS:
				jobClass = BetNewWsJob.class;
				break;
			case FC:
				jobClass = BetFCJob.class;
				break;
			default:
				throw new RuntimeException("尚未完成的盘口类型捕捉:".concat(handicapCode.name()));
		}
		Map<String, Object> map = new HashMap<>(3);
		// 传递定时参数
		map.put("existHmId", existHmId);
		map.put("gameCode", gameCode);
		map.put("period", period);

		//设置投注定时器
		String name = String.format(QuartzConfig.NAME_BET, existHmId, handicapCode.name(), gameCode.name(), period);
		String groupName = String.format(QuartzConfig.GROUP_BET, handicapCode.name());
		String description = String.format(QuartzConfig.DESCRIPTION_BET, existHmId, handicapCode.name());
		// 启动定时器
		findClientQuartz().scheduleJob(jobClass, map, startTime, null, null, name, groupName, description);
		return new JsonResultBeanPlus().success();
	}

	/**
	 * 保存合并投注Job
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @param startTime    投注时间
	 * @param betRate      投注比例
	 * @return 投注结果
	 */
	public static JsonResultBeanPlus saveClientMergeBetJob(String existHmId, HandicapUtil.Code handicapCode,
														   GameUtil.Code gameCode, Object period, double betRate, Date startTime)
			throws SchedulerException, SQLException {

		// 传递定时参数
		Map<String, Object> map = new HashMap<>(5);
		map.put("existHmId", existHmId);
		map.put("handicapCode", handicapCode);
		return saveMergeBetJob(findClientQuartz(), existHmId, handicapCode, gameCode, period, betRate, startTime,
				ClientMergeBetJob.class, map);
	}

	/**
	 * 移除盘口会员定时检验job
	 *
	 * @param existId      已存在盘口id
	 * @param handicapCode 盘口code
	 */
	public static void removeCheckJob(String existId, HandicapUtil.Code handicapCode) {
		String name = String.format(QuartzConfig.NAME_CHECK, existId, handicapCode.name());
		String groupName = String.format(QuartzConfig.GROUP_BET, handicapCode.name());
		String jobName = name.concat("Job");
		if (findClientQuartz().isExistsJob(jobName, groupName)) {
			String triggerName = name.concat("Job");
			String triggerGroupName = String.format(QuartzConfig.GROUP_CHECK, handicapCode);
			findClientQuartz().removeJob(jobName, groupName, triggerName, triggerGroupName);
		}
	}

	/**
	 * 移除投注Job
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param period       期数
	 */
	private static void removeBetJob(String existHmId, String handicapCode, String gameCode, Object period) {
		String name = String.format(QuartzConfig.NAME_BET, existHmId, handicapCode, gameCode, period);
		String groupName = String.format(QuartzConfig.GROUP_BET, handicapCode);
		String jobName = name.concat("Job");
		if (findClientQuartz().isExistsJob(jobName, groupName)) {
			findClientQuartz().removeJob(jobName, groupName, name.concat("Trigger"), groupName);
		}
	}
	//endregion

	//region 代理定时器

	/**
	 * 保存投注爬取定时器
	 *
	 * @param existHaId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @return 保存结果
	 */
	public static void saveGrabBetJob(String existHaId, HandicapUtil.Code handicapCode,
									  GameUtil.Code gameCode) throws SchedulerException, SQLException {
		// 传递定时参数
		JobDataMap map = new JobDataMap();
		map.put("existHaId", existHaId);
		map.put("gameCode", gameCode);
		// 设置定时任务
		int second = RandomTool.getInt(60);
		String cron;
		Class<? extends Job> jobClass;
		switch (handicapCode) {
			case IDC:
				jobClass = GrabBetIdcJob.class;
				cron = second % 10 + "/10 * 7-4 * * ? *";
				break;
			case SGWIN:
				jobClass = GrabBetSgwinJob.class;
				cron = second % 10 + "/10 * 7-4 * * ? *";
				break;
			case HQ:
				jobClass = GrabBetHqJob.class;
				cron = second % 10 + "/10 * 7-4 * * ? *";
				break;
			case COM:
				jobClass = GrabBetComJob.class;
				cron = second % 10 + "/10 * 7-4 * * ? *";
				break;
			case NEWCC:
				jobClass = GrabBetNewCcJob.class;
				cron = second % 10 + "/10 * 7-4 * * ? *";
				break;
			case NEWWS:
				jobClass = GrabBetNewWsJob.class;
				cron = second % 10 + "/10 * 7-4 * * ? *";
				break;
			case UN:
				jobClass = GrabBetUNJob.class;
				cron = second % 10 + "/10 * 7-4 * * ? *";
				break;
			case FC:
				jobClass = GrabBetFCJob.class;
				cron = second % 10 + "/10 * 7-4 * * ? *";
				break;
			default:
				throw new IllegalArgumentException("盘口编码不存在");
		}
		String name = String.format(QuartzConfig.NAME_GRAB_BET, existHaId, handicapCode.name(), gameCode.name());
		String groupName = String.format(QuartzConfig.GROUP_GRAB_BET, handicapCode.name());
		String description = String.format(QuartzConfig.DESCRIPTION_GRAB_BET, existHaId, handicapCode.name());
		findClientQuartz().scheduleJob(jobClass, map, cron, name, groupName, description);
	}

	/**
	 * 移除投注爬取定时器
	 *
	 * @param existHaId    已存在盘口会员id
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 */
	public static void removeGrabBetJob(String existHaId, HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
		String name = String.format(QuartzConfig.NAME_GRAB_BET, existHaId, handicapCode.name(), gameCode.name());
		String groupName = String.format(QuartzConfig.GROUP_GRAB_BET, handicapCode.name());
		findClientQuartz().removeJob(name, groupName);
	}

	/**
	 * 移除盘口代理所有job
	 *
	 * @param existHaId    已存在盘口代理id
	 * @param handicapCode 盘口code
	 */
	public static void removeHaJob(String existHaId, HandicapUtil.Code handicapCode) {
		//todo 未处理
		for (GameUtil.Code gameCode : GameUtil.Code.values()) {
			removeGrabBetJob(existHaId, handicapCode, gameCode);
		}
		removeCheckJob(existHaId, handicapCode);
	}

	//  代理时器

	//endregion

	//endregion

	/**
	 * @param quartz       定时器工具类
	 * @param id           合并者主键
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @param betRate      投注比例
	 * @param startTime    投注时间
	 * @param jobClass     工作类
	 * @param map          工作类型
	 * @return 合并投注工作
	 */
	private static JsonResultBeanPlus saveMergeBetJob(QuartzUtil quartz, String id, HandicapUtil.Code handicapCode,
													  GameUtil.Code gameCode, Object period, double betRate, Date startTime, Class<? extends Job> jobClass,
													  Map<String, Object> map) throws SchedulerException, SQLException {
		map.put("gameCode", gameCode);
		map.put("period", period);
		map.put("betRate", betRate);

		//设置合并投注定时器
		String name = String.format(QuartzConfig.NAME_MERGE, id, handicapCode.name(), gameCode.name(), period);
		String groupName = String.format(QuartzConfig.GROUP_MERGE, handicapCode.name());
		String description = String.format(QuartzConfig.DESCRIPTION_MERGE, id, handicapCode.name());

		// 启动定时器
		quartz.scheduleJob(jobClass, map, startTime, null, null, name, groupName, description);
		return new JsonResultBeanPlus().success();
	}
}
