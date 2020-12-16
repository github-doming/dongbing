package com.ibm.old.v1.common.wck.init;

import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.entity.IbmPlanUserT;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserSetService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @author wck
 * @Description: 初始化用户数据
 * @date 2019年2月14日 下午2:47:02
 * @Email: 810160078@qq.com
 * @Version v1.0
 */
public class InitUserDataInfo {
	private Logger log = LogManager.getLogger(this.getClass());

	public InitUserDataInfo() {
	}

	public InitUserDataInfo(String userId) throws Exception {
		initPlan(userId);
		initHandicap(userId);
	}

	/**
	 * @param userId 用户ID
	 * @Description: 初始化用户方案
	 */
	public void initPlan(String userId) throws Exception {
		//获取用户可开启的方案ID
		AppUserService userService = new AppUserService();
		String planIds = userService.findPlanById(userId);

		//获取用户可开启的方案
		IbmPlanTService planTService = new IbmPlanTService();
		List<Map<String, Object>> plans = planTService.findPlan(planIds);

		//获取用户拥有的所有方案
		IbmPlanUserTService planUserTService = new IbmPlanUserTService();
		List<String> allPlanIds = planUserTService.listPlanByUserId(userId);
		//排除的方案ID
		List<String> excPlanIds = new ArrayList<>();
		for (String planId : allPlanIds) {
			if (!planIds.contains(planId)) {
				excPlanIds.add(planId);
			}
		}
		IbmPlanUserSetService planUserSetService = new IbmPlanUserSetService();
		if(ContainerTool.notEmpty(excPlanIds)){
			Map<Object, Object> planMap=planTService.findExcPlan(excPlanIds);
			//删除无效的方案
			planUserTService.delByPlanIds(excPlanIds,userId);
			for(String plan:excPlanIds){
				//删除无效的方案设置
				planUserSetService.delplanUserSet(excPlanIds, userId, planMap.get(plan).toString());
			}
		}
		for (Map<String, Object> plan : plans) {
			//初始化用户方案信息
			IbmPlanUserT ibmPlanUserT = planUserTService.findByPlanId(plan.get("IBM_PLAN_ID_").toString(), userId);
			if (ibmPlanUserT != null) {
				continue;
			}
			initPlanUser(plan, userId);
		}

	}

	/**
	 * @param plan   方案信息
	 * @param userId 用户ID
	 *               返回类型 IbmPlanUserT
	 * @Description: 初始化用户方案
	 * <p>
	 * 参数说明
	 */
	public void initPlanUser(Map<String, Object> plan, String userId) throws Exception {
		Date nowTime = new Date();
		IbmPlanUserT ibmPlanUserT = new IbmPlanUserT();
		String planId = plan.get("IBM_PLAN_ID_").toString();
		String gameId = plan.get("GAME_ID_").toString();
		//获取方案设置表名
		String table = plan.get("PLAN_ITEM_TABLE_NAME_").toString();
		ibmPlanUserT.setPlanId(planId);
		ibmPlanUserT.setPlanItemTableName(table);

		IbmPlanUserTService planUserTService = new IbmPlanUserTService();
		IbmPlanUserSetService planUserSetService = new IbmPlanUserSetService();

		String id = planUserSetService.findIdByPlanId(planId, userId, table);
		if (StringTool.isEmpty(id)) {
			Map<String, Object> map = planUserSetService.findDefData(gameId, table);
			if (ContainerTool.isEmpty(map)) {
				log.error("方案【" + planId + "】游戏【" + gameId + "】详情表未发现默认数据");
				return;
			}
			map.put("PLAN_ID_", planId);
			map.put("USER_ID_", userId);
			map.put("FUNDS_LIST_ID_", null);
			map.put("CREATE_TIME_", nowTime);
			map.put("CREATE_TIME_LONG_", nowTime.getTime());
			map.put("UPDATE_TIME_", nowTime);
			map.put("UPDATE_TIME_LONG_", nowTime.getTime());
			map.put("STATE_", IbmStateEnum.OPEN.name());
			id = planUserSetService.saveEdit(map, table);
		}
		ibmPlanUserT.setPlanItemTableId(id);
		ibmPlanUserT.setBetMode(IbmModeEnum.BET_MODE_REGULAR.name());
		ibmPlanUserT.setAppUserId(userId);
		ibmPlanUserT.setGameId(gameId);
		ibmPlanUserT.setPlanName(plan.get("PLAN_NAME_").toString());
		ibmPlanUserT.setProfitLimitMaxT(0);
		ibmPlanUserT.setLossLimitMinT(0);
		ibmPlanUserT.setMonitorPeriod(0);
		ibmPlanUserT.setCreateTime(nowTime);
		ibmPlanUserT.setCreateTimeLong(nowTime.getTime());
		ibmPlanUserT.setUpdateTime(nowTime);
		ibmPlanUserT.setUpdateTimeLong(nowTime.getTime());
		ibmPlanUserT.setState(IbmStateEnum.CLOSE.name());
		planUserTService.save(ibmPlanUserT);
	}

	/**
	 * @param userId 用户ID
	 * @Description: 初始化用户盘口
	 */
	public void initHandicap(String userId) throws Exception {
		//获取用户可开启的盘口ID
		AppUserService userService = new AppUserService();
		String handicapIds = userService.findHandicapById(userId);

		//获取用户拥有的所有盘口
		IbmHandicapUserTService handicapUserService = new IbmHandicapUserTService();
		List<String> allHandicapIds = handicapUserService.listHandicapByUserId(userId);
		for (String handicapId : allHandicapIds) {
			if (handicapIds.contains(handicapId)) {
				handicapIds = handicapIds.replace(handicapId.concat(","), "");
				//去除最后一个
				handicapIds = handicapIds.replace(",".concat(handicapId), "");
			} else {
				handicapUserService.delByHandicapId(handicapId, "移除分配资源中不存在的盘口id");
			}
		}
		//获取用户可开启的盘口
		List<Map<String, Object>> handicaps = new IbmHandicapTService().findHandicap(handicapIds);
		if (ContainerTool.notEmpty(handicaps)) {
			handicapUserService.saveBatch(handicaps, userId);
		}
	}
}
