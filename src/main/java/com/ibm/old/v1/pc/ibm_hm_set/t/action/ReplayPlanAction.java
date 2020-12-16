package com.ibm.old.v1.pc.ibm_hm_set.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_result.service.IbmExecBetResultService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.common.doming.core.BaseAsynCommAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.List;
/**
 * @Description: 盘口会员重置方案
 * @Author: Dongming
 * @Date: 2019-08-08 11:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(name = "HandicapShow", value = "/ibm/pc/ibm_hm_set/replayPlan.dm")
public class ReplayPlanAction extends BaseAsynCommAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		// 盘口会员id
		String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		if (StringTool.isEmpty(cmd, handicapMemberId)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(jrb);
		}

		switch (cmd) {
			case "REPLAY_ALL":
				replayAll(handicapMemberId);
				break;
			case "REPLAY_GAME":
				String gameCode = BeanThreadLocal.find(dataMap.get("GAME_CODE_"), "");
				if (StringTool.isEmpty(gameCode, handicapMemberId)) {
					jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
					jrb.putSysEnum(IbmCodeEnum.CODE_401);
					return returnJson(jrb);
				}
				String gameId = GameTool.findId(gameCode);
				if (StringTool.isEmpty(gameId, handicapMemberId)) {
					jrb.putEnum(IbmCodeEnum.IBM_404_GAME);
					jrb.putSysEnum(IbmCodeEnum.CODE_404);
					return returnJson(jrb);
				}
				replayGame(handicapMemberId, gameId);
				break;
			default:
				jrb.putEnum(IbmCodeEnum.IBM_404_CMD);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);

		}
		jrb.success();
		return returnJson(jrb);
	}

	/**
	 * 重置游戏方案
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 */
	private void replayGame(String handicapMemberId, String gameId) throws SQLException {

		//同步用户方案设置
		List<String> planIds = new IbmPlanHmTService().replayGame(this.getClass().getName(), handicapMemberId, appUserId, gameId);

		//重置执行情况
		new IbmExecPlanGroupTService().replayAll4Hm(this.getClass().getName(), handicapMemberId, gameId);
		new IbmExecBetResultService().replayAll4Hm(this.getClass().getName(), handicapMemberId, gameId);

		//重置盈利情况
		new IbmProfitPlanTService().replayAll4Hm(this.getClass().getName(), handicapMemberId, planIds);
	}

	/**
	 * 重置所有方案
	 *
	 * @param handicapMemberId 盘口会员id
	 */
	private void replayAll(String handicapMemberId) throws SQLException {
		//同步用户方案设置
		new IbmPlanHmTService().replayAll(this.getClass().getName(), handicapMemberId, appUserId);

		//重置执行情况
		new IbmExecPlanGroupTService().replayAll4Hm(this.getClass().getName(), handicapMemberId);
		new IbmExecBetResultService().replayAll4Hm(this.getClass().getName(), handicapMemberId);

		//重置盈利情况
		new IbmProfitPlanTService().replayAll4Hm(this.getClass().getName(), handicapMemberId);
	}
}
