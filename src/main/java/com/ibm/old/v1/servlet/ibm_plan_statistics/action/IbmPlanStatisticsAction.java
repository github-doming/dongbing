package com.ibm.old.v1.servlet.ibm_plan_statistics.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynCommAction;
import com.ibm.old.v1.common.doming.core.IbmExecutor;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.common.IbmServletCmdEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.controller.StatisticsSaveEventController;
import com.ibm.old.v1.servlet.ibm_plan_statistics.ibm_event_planstatistics.t.service.IbmEventPlanstatisticsTService;
import com.ibm.old.v1.servlet.ibm_plan_statistics.service.IbmPlanStatisticsService;
import com.ibm.old.v1.servlet.ibm_plan_statistics.thread.PlanStatisticsThread;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.MvcResult;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * @Description: 历史统计基础请求
 * @Author: Dongming
 * @Date: 2019-06-10 14:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(name = "planStatistics", value = "/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm") public class IbmPlanStatisticsAction
		extends BaseAsynCommAction {

	@Override public Object run() throws Exception {
		super.findAppUser();
		JsonResultBean threadLocalJrb = LogThreadLocal.findLog();
		if (!threadLocalJrb.isSuccess()) {
			return returnJson(threadLocalJrb);
		}
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(jrb);
		}
		if (StringTool.isEmpty(cmd)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_METHOD);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(jrb);
		}
		IbmServletCmdEnum cmdEnum = IbmServletCmdEnum.valueOf(cmd);
		try {
			switch (cmdEnum) {
				case START:
					if (PlanStatisticsThread.updateThreadState(true)) {
						jrb.setData(cmdEnum.name());
						jrb.success();
					} else {
						jrb.error("开始统计线程失败");
					}
					break;
				case STOP:
					if (PlanStatisticsThread.updateThreadState(false)) {
						jrb.setData(cmdEnum.name());
						jrb.success();
					} else {
						jrb.error("结束统计线程失败");
					}
					break;
				case GAME_LIST:
					getGameList(jrb);
					break;
				case PLAN_LIST:
					getPlanList(jrb);
					break;
				case STATISTICS:
					IbmExecutor executor = new StatisticsSaveEventController(appUserT, dataMap);
					executor.execute(jrb);
					break;
				case RESULT:
					getResult(jrb);
					break;
				case EXCEL:
					return getExcel(jrb);
				default:
					jrb.putEnum(IbmCodeEnum.IBM_404_CMD);
					jrb.putSysEnum(IbmCodeEnum.CODE_404);
			}
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			jrb.setSuccess(false);
		}

		return this.returnJson(jrb);
	}


	/**
	 * 获取用户已拥有的游戏列表
	 *
	 * @param jrb 获取结果
	 * @return 获取结果
	 */
	private JsonResultBeanPlus getGameList(JsonResultBeanPlus jrb) throws SQLException {
		IbmPlanStatisticsService planStatisticsService = new IbmPlanStatisticsService();
		List<Map<String, Object>> list = planStatisticsService.listGameCode();
		jrb.setData(list);
		jrb.success();
		return jrb;
	}

	/**
	 * 获取用户已拥有的方案列表
	 *
	 * @param jrb 获取结果
	 * @return 获取结果
	 */
	private JsonResultBeanPlus getPlanList(JsonResultBeanPlus jrb) throws SQLException {
		//游戏code
		String gameCode = BeanThreadLocal.find(dataMap.get("gameCode"), "");
		if (StringTool.isEmpty(gameCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return jrb;
		}
		IbmPlanStatisticsService planStatisticsService = new IbmPlanStatisticsService();
		//游戏id
		String gameId = planStatisticsService.findGameId(gameCode);
		if (StringTool.isEmpty(gameId)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_GAME);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return jrb;
		}
		//拥有的方案code
		List<Map<String, Object>> planList = planStatisticsService.listPlanCode(appUserT.getAppUserId(), gameId);
		jrb.setData(planList);
		jrb.success();
		return jrb;
	}

	/**
	 * 获取事件执行结果
	 *
	 * @param jrb 获取结果
	 * @return 获取结果
	 */
	private JsonResultBeanPlus getResult(JsonResultBeanPlus jrb) throws SQLException {
		String eventId = BeanThreadLocal.find(dataMap.get("eventId"), "");
		if (StringTool.isEmpty(eventId)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return jrb;
		}
		String result = new IbmEventPlanstatisticsTService().findResult(eventId);
		if (StringTool.notEmpty(result)) {
			jrb.setData(result);
			jrb.success();
		} else {
			jrb.process();
		}
		return jrb;
	}

	/**
	 * 获取执行结果表
	 *
	 * @param jrb 获取结果
	 * @return 获取结果
	 */
	private Object getExcel(JsonResultBeanPlus jrb) throws SQLException {
		String eventId = BeanThreadLocal.find(dataMap.get("eventId"), "");
		String state = BeanThreadLocal.find(dataMap.get("state"), "");
		if (StringTool.isEmpty(eventId)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return jrb;
		}
		String result = new IbmEventPlanstatisticsTService().findFile(eventId);
		if (StringTool.isEmpty(result)) {
			jrb.process();
			return jrb;
		}
		if ("file".equalsIgnoreCase(state)){
			return MvcResult.fileResult(result);
		}
		jrb.success();
		return jrb;
	}
}
