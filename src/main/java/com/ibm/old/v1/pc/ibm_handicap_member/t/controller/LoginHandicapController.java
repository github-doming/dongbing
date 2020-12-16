package com.ibm.old.v1.pc.ibm_handicap_member.t.controller;
import com.ibm.old.v1.cloud.core.controller.CloudExecutor;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.cloud.ibm_profit_plan_vr.t.service.IbmProfitPlanVrTService;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.service.IbmProfitVrTService;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import net.sf.json.JSONObject;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 盘口会员登录数据控制器
 * @Author: zjj
 * @Date: 2019-07-04 16:30
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class LoginHandicapController implements CloudExecutor {

	@Override public JsonResultBeanPlus execute(String... inVar) throws Exception {
		String handicapMemberId = inVar[0];

		String memberInfo = inVar[1];
		JSONObject info=JSONObject.fromObject(memberInfo);
		//盘口盈亏信息
		Object profit=info.remove("PROFIT");

		//1，修改登录状态，获取盘口会员信息
		IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
		boolean state = handicapMemberTService.updateLogin(handicapMemberId, info);
		if (!state) {
			return null;
		}

		//2，修改盘口用户登录在线数量
		IbmHandicapUserTService handicapUserTService = new IbmHandicapUserTService();
		handicapUserTService.updateLogin(handicapMemberId);

		//3，获取盘口盈亏信息，清除历史盘口会员盈亏信息，新增新的盘口会员盈亏信息
		IbmHmSetTService hmSetTService = new IbmHmSetTService();
		IbmHmSetT hmSetT = hmSetTService.findByHandicapMemberId(handicapMemberId);
		// 真实投注信息
		betProfit(handicapMemberId,hmSetT,profit);

		//模拟投注信息
		betProfitVr(handicapMemberId,hmSetT);

		//4，清除历史盘口会员方案盈亏信息，新增新的盘口会员方案盈亏信息
		//盘口会员方案盈亏信息单个盘口会员有多个盘口游戏多个方案，但是实际用到的不一定有那么多
		//数据冗余量过大，所有在盘口会员登出的时候清除历史盘口会员方案盈亏信息，在游戏结算的时候有需要的才新增数据
		IbmProfitPlanTService profitPlanTService=new IbmProfitPlanTService();
		profitPlanTService.resetPlanProfit(handicapMemberId,this.getClass().getName());

		IbmProfitPlanVrTService profitPlanVrTService=new IbmProfitPlanVrTService();
		profitPlanVrTService.resetPlanProfitVr(handicapMemberId,this.getClass().getName());

		//5，重置资金轮次，重置盘口会员执行详情
		IbmExecPlanGroupTService execPlanGroupTService=new IbmExecPlanGroupTService();
		execPlanGroupTService.resetHistory(handicapMemberId,this.getClass().getName());

		//6，开启盘口会员与方案状态
		IbmPlanHmTService planHmTService=new IbmPlanHmTService();
		planHmTService.openPlanHm(handicapMemberId);

		return null;
	}

	private void betProfitVr(String handicapMemberId, IbmHmSetT hmSetT) throws Exception {
		IbmProfitVrTService profitVrTService = new IbmProfitVrTService();
		String profitVrId = profitVrTService.findIdByHmId(handicapMemberId);

		if (StringTool.notEmpty(profitVrId)) {
			profitVrTService.clearById(profitVrId,this.getClass().getName());
		}
		 profitVrTService.save(hmSetT,handicapMemberId);
	}

	private void betProfit(String handicapMemberId, IbmHmSetT hmSetT,Object profit) throws Exception {
		IbmProfitTService profitTService = new IbmProfitTService();
		String profitId = profitTService.findIdByHmId(handicapMemberId);

		if (StringTool.notEmpty(profitId)) {
			profitTService.clearById(profitId,this.getClass().getName());
		}
		 profitTService.save(hmSetT,handicapMemberId, NumberTool.longValueT(profit.toString().replace(",","")));
	}
}
