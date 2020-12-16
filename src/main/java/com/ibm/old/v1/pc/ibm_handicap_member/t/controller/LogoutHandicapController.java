package com.ibm.old.v1.pc.ibm_handicap_member.t.controller;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.cloud.ibm_profit_plan_vr.t.service.IbmProfitPlanVrTService;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.service.IbmProfitVrTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 盘口会员登出信息控制器
 * @Author: zjj
 * @Date: 2019-07-04 17:30
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class LogoutHandicapController implements CloudExecutor {

	@Override public JsonResultBeanPlus execute(String handicapMemberId) throws Exception {
		Date nowTime=new Date();

		//1，修改登录状态
		IbmHandicapMemberTService handicapMemberTService=new IbmHandicapMemberTService();
		handicapMemberTService.updateLogout(handicapMemberId,nowTime);

		//2，修改盘口用户登录在线数量
		IbmHandicapUserTService handicapUserTService=new IbmHandicapUserTService();
		handicapUserTService.updateLogout(handicapMemberId,nowTime);

		//3，修改盘口会员所有游戏投注状态
		IbmHmGameSetTService hmGameSetTService=new IbmHmGameSetTService();
		hmGameSetTService.updateBetState(handicapMemberId,nowTime,this.getClass().getName());

		//4，清除盘口会员盈亏信息
		//重置真实投注盈亏信息
		betProfit(handicapMemberId);

		//重置模拟投注盈亏信息
		betProfitVr(handicapMemberId);

		//5，清除盘口会员方案盈亏信息
		IbmProfitPlanTService profitPlanTService=new IbmProfitPlanTService();
		profitPlanTService.resetPlanProfit(handicapMemberId,this.getClass().getName());

		IbmProfitPlanVrTService profitPlanVrTService=new IbmProfitPlanVrTService();
		profitPlanVrTService.resetPlanProfitVr(handicapMemberId,this.getClass().getName());

		//7，重置资金轮次
		IbmExecPlanGroupTService execPlanGroupTService=new IbmExecPlanGroupTService();
		execPlanGroupTService.resetHistory(handicapMemberId,this.getClass().getName());

		return null;
	}
	private void betProfitVr(String handicapMemberId) throws Exception {
		IbmProfitVrTService profitVrTService = new IbmProfitVrTService();
		String profitVrId = profitVrTService.findIdByHmId(handicapMemberId);

		if (StringTool.isEmpty(profitVrId)) {
			return ;
		}
		profitVrTService.clearById(handicapMemberId,this.getClass().getName());
	}

	private void betProfit(String handicapMemberId) throws Exception {
		IbmProfitTService profitTService = new IbmProfitTService();
		String profitId = profitTService.findIdByHmId(handicapMemberId);

		if (StringTool.isEmpty(profitId)) {
			return ;
		}
		profitTService.clearById(handicapMemberId,this.getClass().getName());
	}
}
