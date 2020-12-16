package com.ibm.follow.connector.admin.manage3.periods;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_sys_periods.service.IbmSysPeriodsService;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 期数数据工具类
 * @Author: Dongming
 * @Date: 2019-10-15 10:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PeriodDataUtil {
	public static Map<GameUtil.Code, List<Map<String, Object>>> data;
	private static volatile PeriodDataUtil instance;
	private PeriodDataUtil() {
	}

	public static PeriodDataUtil getInstance() throws SQLException {
		if (instance == null) {
			synchronized (PeriodDataUtil.class) {
				PeriodDataUtil util = new PeriodDataUtil();
				util.init();
				instance = util;
			}
		}
		return instance;
	}
	/**
	 * 初始化
	 */
	private void init() throws SQLException {
		IbmSysPeriodsService periodsService = new IbmSysPeriodsService();
		data = periodsService.mapPauseAll();
	}

	/**
	 * 获取游戏的暂停时间列表
	 * @param gameCode 游戏编码
	 * @return 暂停时间列表
	 */
	public  List<Map<String, Object>> getGamePauseList(GameUtil.Code gameCode) throws SQLException {
		if (!data.containsKey(gameCode)){
			IbmSysPeriodsService periodsService = new IbmSysPeriodsService();
			List<Map<String,Object>>  pauseList = periodsService.listPause(gameCode);
			if (ContainerTool.notEmpty(pauseList)){
				data.put(gameCode,pauseList);
			}
		}
		return data.get(gameCode);
	}


}
