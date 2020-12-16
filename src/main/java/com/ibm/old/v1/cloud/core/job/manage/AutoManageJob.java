package com.ibm.old.v1.cloud.core.job.manage;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.core.controller.mq.LoginClientController;
import com.ibm.old.v1.cloud.core.controller.mq.SetAllGameBetStateController;
import com.ibm.old.v1.cloud.core.controller.mq.SetGameBetStateController;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_hm_info.t.service.IbmHmInfoTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.quartz.JobExecutionContext;

import java.util.List;
import java.util.Map;
/**
 * @Description: 用户管理工作类
 * @Author: Dongming
 * @Date: 2019-02-16 17:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AutoManageJob extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		JsonResultBeanPlus bean;
		// 处理自动登录盘口会员
		IbmHmSetTService hmSetService = new IbmHmSetTService();
		List<String> loginTimeList = hmSetService.findLoginTime();

		CloudExecutor controller = new LoginClientController();
		for (String handicapMember : loginTimeList) {
			bean = controller.execute(handicapMember);
			if (!bean.isSuccess()) {
				log.error(handicapMember + "_" + returnJson(bean));
			}
		}

		// 处理自动开始和自动停止投注盘口会员
		IbmHmGameSetTService hmGameSetService = new IbmHmGameSetTService();
		List<Map<String, Object>> autoList = hmGameSetService.autoStop();

		controller = new SetGameBetStateController();
		IbmHmInfoTService hmInfo = new IbmHmInfoTService();

		if (ContainerTool.notEmpty(autoList)) {
			//批量修改单盘口游戏状态
			hmInfo.autoBettingState(autoList, IbmTypeEnum.FALSE.name());
			for (Map<String, Object> autoStop : autoList) {
				bean = controller
						.execute(autoStop.get("HANDICAP_MEMBER_ID_").toString(), autoStop.get("GAME_ID_").toString(),
								IbmTypeEnum.FALSE.name());
				if (!bean.isSuccess()) {
					log.error(autoStop.get("HANDICAP_MEMBER_ID_").toString() + "_" + autoStop.get("GAME_ID_").toString()
							+ "_" + returnJson(bean));
				}
			}
		}
		autoList = hmGameSetService.autoStart();
		if (ContainerTool.notEmpty(autoList)) {
			//批量修改单盘口游戏状态
			hmInfo.autoBettingState(autoList, IbmTypeEnum.TRUE.name());
			for (Map<String, Object> autoStart : autoList) {
				bean = controller
						.execute(autoStart.get("HANDICAP_MEMBER_ID_").toString(), autoStart.get("GAME_ID_").toString(),
								IbmTypeEnum.TRUE.name());
				if (!bean.isSuccess()) {
					log.error(
							autoStart.get("HANDICAP_MEMBER_ID_").toString() + "_" + autoStart.get("GAME_ID_").toString()
									+ "_" + returnJson(bean));
				}
			}
		}

		//处理自动停止新增盘口会员
		hmGameSetService.autoIncrease();

		// 清理已经登出的盘口会员的盈利信息
		IbmProfitTService profitService = new IbmProfitTService();
		profitService.clearLogout();

		IbmProfitPlanTService profitPlanService = new IbmProfitPlanTService();

		//盘口方案自定义重置
		profitPlanService.resetByLimit(IbmTypeEnum.REAL.name());
		profitPlanService.resetByLimit(IbmTypeEnum.VIRTUAL.name());

		// 方案止损止盈限制
		profitPlanService.profitLimit(IbmTypeEnum.REAL.name());
		profitPlanService.profitLimit(IbmTypeEnum.VIRTUAL.name());

		controller = new SetAllGameBetStateController();
		// 盘口会员止盈止损限制
		List<String> handicapMemberIds = profitService.profitLimit(IbmTypeEnum.REAL,this.getClass().getName());
		if (ContainerTool.notEmpty(handicapMemberIds)) {
			//批量修改盘口会员所有游戏状态
			hmInfo.allAutoBettingState(handicapMemberIds, IbmTypeEnum.FALSE.name());
			for (String handicapMemberId : handicapMemberIds) {
				bean = controller.execute(handicapMemberId);
				if (!bean.isSuccess()) {
					log.error(handicapMemberId + "_止盈止损关闭盘口游戏投注状态_" + returnJson(bean));
				}
			}
		}
		profitService.profitLimit(IbmTypeEnum.VIRTUAL,this.getClass().getName());

		hmSetService.customReset(IbmTypeEnum.REAL.name(),this.getClass().getName());
		hmSetService.customReset(IbmTypeEnum.VIRTUAL.name(),this.getClass().getName());

		log.trace("自动管理工作类执行完成");
	}
}
