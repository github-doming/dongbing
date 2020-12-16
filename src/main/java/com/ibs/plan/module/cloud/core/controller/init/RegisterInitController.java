package com.ibs.plan.module.cloud.core.controller.init;

import c.a.config.SysConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import com.ibs.plan.module.cloud.ibsp_exp_user.entity.IbspExpUser;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_log_manage_time.entity.IbspLogManageTime;
import com.ibs.plan.module.cloud.ibsp_log_manage_time.service.IbspLogManageTimeService;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import com.ibs.plan.module.cloud.ibsp_plan_item.service.IbspPlanItemService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.entity.IbspSysManageTime;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.service.IbspSysManageTimeService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

import java.sql.SQLException;
import java.util.*;

/**
 * 用户注册初始化控制器
 *
 * @Author: null
 * @Date: 2020-05-22 17:53
 * @Version: v1.0
 */
public class RegisterInitController {
	public void execute(String appUserId) throws Exception {
		Date nowTime = new Date();
		// 初始化数据
		//自己注册的用户默认为免费用户类型
		IbspExpRoleService expRoleService = new IbspExpRoleService();
		IbspExpRole expRole = expRoleService.findByCode(IbsTypeEnum.FREE);

		//初始化用户角色信息
		IbspExpUser expUser = new IbspExpUser();
		expUser.setAppUserId(appUserId);
		expUser.setExpRoleId(expRole.getIbspExpRoleId());
		expUser.setAvailableGame(expRole.getGameCodes());
		expUser.setAvailablePlan(expRole.getPlanCodes());
		expUser.setAvailableHandicap(expRole.getHandicapCodes());
		expUser.setOnlineMax(expRole.getOnlineMax());
		expUser.setOnline(0);
		expUser.setCreateUser(IbsTypeEnum.SELF.name());
		expUser.setCreateTime(nowTime);
		expUser.setCreateTimeLong(System.currentTimeMillis());
		expUser.setUpdateTime(nowTime);
		expUser.setUpdateTimeLong(System.currentTimeMillis());
		expUser.setState(IbsStateEnum.OPEN.name());
		new IbspExpUserService().save(expUser);

		// 初始化会员盘口
		Set<String> handicapCodes = new HashSet<>(Arrays.asList(expUser.getAvailableHandicap().split(",")));
		//获取用户拥有的所有盘口codes
		IbspHmUserService hmUserService = new IbspHmUserService();
		List<String> allHandicapCodes = hmUserService.findHandicapCodeByUserId(appUserId);
		for (String handicapCode : allHandicapCodes) {
			handicapCodes.remove(handicapCode);
		}
		//获取用户可开启的盘口信息
		if(ContainerTool.notEmpty(handicapCodes)){
			List<Map<String, Object>> handicaps = new IbspHandicapService().listInfoByCodes(handicapCodes);
			if (ContainerTool.notEmpty(handicaps)) {
				hmUserService.saveRegister(handicaps, appUserId, expRole.getHmOnlineMax(), nowTime);
			}
		}
		//初始化方案信息
		initPlan(appUserId, expUser);
		//初始化点数、时间
		initPointAndTime(appUserId, nowTime, 0, System.currentTimeMillis());

	}

	public void initPointAndTime(String appUserId, Date nowTime, long useablePoint, long endTime) throws Exception {
		// 初始化点数信息
		//校验云接口数据
		JSONObject data = new JSONObject();
		data.put("userId", appUserId);
		data.put("useablePoint", useablePoint);
		String time = System.currentTimeMillis() + "";
		data.put("time", time);
		data.put("valiDate", Md5Tool.generate(time));
		Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
		String cloudUrl = url+ "/cloud/user/api/point/editPoint";
		String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
		JSONObject result = JSON.parseObject(html);
		if (!result.getBoolean("success")) {
			return;
		}
//		result.getJSONObject("data").getString("REP_POINT_ID_");
		String repId = result.getString("data");

		//初始化时长信息
		IbspLogManageTime repTime = new IbspLogManageTime();
		repTime.setLogPointId(repId);
		repTime.setPreId(IbsStateEnum.FIRST.name());
		repTime.setAppUserId(appUserId);
		repTime.setUsedPointT(useablePoint);
		repTime.setAddTimeLong(0);
		repTime.setStartTime(nowTime);
		repTime.setStartTimeLong(System.currentTimeMillis());
		repTime.setEndTime(new Date(endTime));
		repTime.setEndTimeLong(endTime);
		repTime.setRepEndTime(nowTime);
		repTime.setRepEndTimeLong(System.currentTimeMillis());
		repTime.setCreateTime(nowTime);
		repTime.setCreateTimeLong(System.currentTimeMillis());
		repTime.setUpdateTimeLong(System.currentTimeMillis());
		repTime.setState(IbsStateEnum.OPEN.name());
		repId = new IbspLogManageTimeService().save(repTime);

		IbspSysManageTime manageTime = new IbspSysManageTime();
		manageTime.setAppUserId(appUserId);
		manageTime.setLogTimeId(repId);
		manageTime.setStartTime(nowTime);
		manageTime.setStartTimeLong(System.currentTimeMillis());
		manageTime.setEndTime(new Date(endTime));
		manageTime.setEndTimeLong(endTime);
		manageTime.setCreateUser(appUserId);
		manageTime.setCreateTime(nowTime);
		manageTime.setCreateTimeLong(System.currentTimeMillis());
		manageTime.setUpdateTimeLong(System.currentTimeMillis());
		manageTime.setState(IbsStateEnum.OPEN.name());
		new IbspSysManageTimeService().save(manageTime);
	}

	public void initPlan(String appUserId, IbspExpUser expUser) throws SQLException {
		if (StringTool.isEmpty(expUser.getAvailablePlan(), expUser.getAvailableGame())) {
			return;
		}
		Set<String> planCodes = new HashSet<>(Arrays.asList(expUser.getAvailablePlan().split(",")));
		IbspPlanUserService planUserService = new IbspPlanUserService();
		List<String> allPlanCodes = planUserService.findPlanCodeByUserId(appUserId);
		for (String planCode : allPlanCodes) {
			planCodes.remove(planCode);
		}
		if (ContainerTool.isEmpty(planCodes)) {
			return;
		}
		//获取可用的方案游戏,在结合可用游戏进行过滤
		Set<String> gameCodes = new HashSet<>(Arrays.asList(expUser.getAvailableGame().split(",")));
		List<String> gameIds = new IbspGameService().findIds(gameCodes);

		List<Map<String, Object>> planItems;
		IbspPlanService planService = new IbspPlanService();
		//获取方案的基本信息
		Map<String, Map<String, Object>> planInfos = planService.findPlanInfo(planCodes);

		Map<String, List<Object>> planGames = new IbspPlanGameService().listInfoByCodes(planCodes);

		//获取方案初始化信息
		IbspPlanItemService planItemService = new IbspPlanItemService();
		Map<Object, Map<String, Object>> initPlanInfo = planItemService.findInitInfo(planCodes);

		if (ContainerTool.notEmpty(planGames) && ContainerTool.notEmpty(initPlanInfo)) {
			for (Map.Entry<String, List<Object>> entry : planGames.entrySet()) {
				String planCode = entry.getKey();
				List<Object> planGameIds = entry.getValue();

				Map<String, Object> planInfo = planInfos.get(planCode);
				//初始化方案详情信息
				planItems = planService
						.savePlanItem(planInfo, planGameIds, gameIds, appUserId, initPlanInfo.get(planCode));
				for(Map<String,Object> planItem:planItems){
					String snStr = planUserService.findUserPlanMaxSn(appUserId, planItem.get("GAME_ID_").toString());
					int sn = StringTool.isEmpty(snStr) ? 1 : NumberTool.getInteger(snStr)+1;
					planItem.put("SN_",sn);
				}
				planUserService.saveRegister(planCode, planInfo, planItems, appUserId);
			}
		}
	}
}
