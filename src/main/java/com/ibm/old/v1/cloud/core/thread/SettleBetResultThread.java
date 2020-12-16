package com.ibm.old.v1.cloud.core.thread;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.cloud.ibm_profit.t.entity.IbmProfitT;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.cloud.ibm_profit_info.t.entity.IbmProfitInfoT;
import com.ibm.old.v1.cloud.ibm_profit_info.t.service.IbmProfitInfoTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.entity.IbmProfitPlanT;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 结算投注结果
 * @Author: Dongming
 * @Date: 2019-04-10 16:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Deprecated
public class SettleBetResultThread extends BaseCommThread {
	private List<Map<String, Object>> execBetItems;
	private String drawNumber;
	private String tableName;
	public SettleBetResultThread(List<Map<String, Object>> execBetItems, String drawNumber,String tableName) {
		this.execBetItems = execBetItems;
		this.drawNumber = drawNumber;
		this.tableName=tableName;
	}

	@Override public String execute(String ignore) throws Exception {

		IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
		for (Map<String, Object> execBetItem : execBetItems) {
			execBetItem = execBetItemTService.updateReceiptResult(execBetItem, drawNumber,tableName);
			if (ContainerTool.notEmpty(execBetItem)){
				betProfit(execBetItem);
			}
		}

		return null;
	}

	private void betProfit(Map<String, Object> execBetItem) throws Exception {
		String handicapMemberId = execBetItem.get("HANDICAP_MEMBER_ID_").toString();
		String planId = execBetItem.get("PLAN_ID_").toString();

		long profitT = NumberTool.getLong(execBetItem.get("PROFIT_T_"));
		long fundT = NumberTool.getLong(execBetItem.get("FUND_T_"));
		int betLen = NumberTool.getInteger(execBetItem.get("BET_CONTENT_LEN_"));

		IbmExecPlanGroupTService execPlanGroupTService = new IbmExecPlanGroupTService();

		Date nowTime = new Date();

		//更新执行方案组
		execPlanGroupTService.updateResult(execBetItem.get("EXEC_PLAN_GROUP_ID_").toString(),
				profitT > 0 ? IbmTypeEnum.TRUE.name() : IbmTypeEnum.FALSE.name(), nowTime,this.getClass().getName());

		IbmProfitTService profitTService = new IbmProfitTService();
		IbmHmSetTService hmSetTService = new IbmHmSetTService();
		IbmHmSetT hmSetT = hmSetTService.findByHandicapMemberId(handicapMemberId);
		//投注积分 = 每注积分 * 注数 * (比率 / 100)
		long betFundT = (long) (fundT * betLen * NumberTool.doubleT(hmSetT.getBetRateT()) / 100);

		//盘口会员总盈亏
		String profitId = profitTService.findIdByHmId(handicapMemberId);
		if (StringTool.isEmpty(profitId)) {
			IbmProfitT profitTEntity = new IbmProfitT();
			profitTEntity.setHandicapId(hmSetT.getHandicapId());
			profitTEntity.setAppUserId(hmSetT.getAppUserId());
			profitTEntity.setHandicapMemberId(handicapMemberId);
			profitTEntity.setProfitT(profitT);
			profitTEntity.setBetFundsT(betFundT);
			profitTEntity.setBetLen(betLen);
			profitTEntity.setProfitLimitMaxT(hmSetT.getProfitLimitMaxT());
			profitTEntity.setProfitLimitMinT(hmSetT.getProfitLimitMinT());
			profitTEntity.setLossLimitMinT(hmSetT.getLossLimitMinT());
			profitTEntity.setCreateTime(nowTime);
			profitTEntity.setCreateTimeLong(nowTime.getTime());
			profitTEntity.setUpdateTime(nowTime);
			profitTEntity.setUpdateTimeLong(nowTime.getTime());
			profitTEntity.setState(IbmStateEnum.OPEN.name());
			profitId = profitTService.save(profitTEntity);
		} else {
			profitTService.updateProfit(profitId, profitT, betFundT, betLen, nowTime,this.getClass().getName());
		}

		IbmProfitPlanTService profitPlanTService = new IbmProfitPlanTService();
		//盘口会员方案盈亏
		String profitPlanId = profitPlanTService.findId(profitId, planId);
		if (StringTool.isEmpty(profitPlanId)) {
			IbmPlanUserTService planUserTService = new IbmPlanUserTService();
			Map<String, Object> limitInfo = planUserTService.getLimitInfo(handicapMemberId, planId);

			IbmProfitPlanT profitPlanT = new IbmProfitPlanT();
			profitPlanT.setProfitId(profitId);
			profitPlanT.setPlanId(planId);
			profitPlanT.setHandicapMemberId(handicapMemberId);
			profitPlanT.setProfitT(profitT);
			profitPlanT.setBetFundsT(betFundT);
			profitPlanT.setBetLen(betLen);
			profitPlanT.setProfitLimitMaxT(limitInfo.get("PROFIT_LIMIT_MAX_T_"));
			profitPlanT.setLossLimitMinT(limitInfo.get("LOSS_LIMIT_MIN_T_"));
			profitPlanT.setCreateTime(nowTime);
			profitPlanT.setCreateTimeLong(nowTime.getTime());
			profitPlanT.setUpdateTime(nowTime);
			profitPlanT.setUpdateTimeLong(nowTime.getTime());
			profitPlanT.setState(IbmStateEnum.OPEN.name());
			profitPlanId = profitPlanTService.save(profitPlanT);
		} else {
			profitPlanTService.updateProfit(profitPlanId, profitT, betFundT, betLen, nowTime,this.getClass().getName());
		}

		//盘口会员盈亏信息
		IbmProfitInfoTService profitInfoTService = new IbmProfitInfoTService();
		IbmProfitInfoT profitInfoT = new IbmProfitInfoT();
		profitInfoT.setProfitPlanId(profitPlanId);
		profitInfoT.setExecBetItemId(execBetItem.get("EXEC_BET_ITEM_ID_"));
		profitInfoT.setHandicapMemberId(handicapMemberId);
		profitInfoT.setPeriod(execBetItem.get("PERIOD_"));
		profitInfoT.setProfitT(profitT);
		profitInfoT.setBetFundsT(betFundT);
		profitInfoT.setBetLen(betLen);
		profitInfoT.setCreateTime(nowTime);
		profitInfoT.setCreateTimeLong(nowTime.getTime());
		profitInfoT.setUpdateTime(nowTime);
		profitInfoT.setUpdateTimeLong(nowTime.getTime());
		profitInfoT.setState(IbmStateEnum.OPEN.name());
		profitInfoTService.save(profitInfoT);


	}
}
