package com.ibs.common.test.zjj;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.core.CommTest;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.common.enums.IbsModeEnum;
import com.ibs.plan.module.cloud.ibsp_plan_item.entity.IbspPlanItem;
import com.ibs.plan.module.cloud.ibsp_plan_item.service.IbspPlanItemService;
import org.junit.Test;

import java.util.Date;

/**
 * @Description: 初始化方案信息
 * @Author: null
 * @Date: 2020-06-02 16:41
 * @Version: v1.0
 */
public class InitPlanItemTest extends CommTest {

	public static void main(String[] args) {


		JSONObject groupData=new JSONObject();
		//高级开某投某
		for(int i=0;i<10;i++){
			groupData.put(String.valueOf(i),"2-1,3-1");
		}
		System.out.println(groupData.toString());
	}

	@Test
	public void initPlanItem(){
		super.transactionBegin();
		try{
			JSONObject groupData=new JSONObject();
			//高级开某投某
			for(int i=0;i<10;i++){
				groupData.put(String.valueOf(i),"2-1,3-1");
			}
			System.out.println(groupData.toString());

//			JSONObject expandInfo=new JSONObject();
//			expandInfo.put(PlanInfoEnum.STATISTICS_PERIOD_.name(),"0");


			IbspPlanItem planItem=new IbspPlanItem();
			planItem.setPlanCode(PlanUtil.Code.RANDOM_PERIOD_KILL_NUMBER.name());
			planItem.setGameType(IbsTypeEnum.NUMBER.name());
			planItem.setPlanItemTableName(PlanUtil.Code.RANDOM_PERIOD_KILL_NUMBER.getTableName());


			planItem.setProfitLimitMax(10000);
			planItem.setLossLimitMin(-10000);
			planItem.setFundsList("2,4,8,16,32");
			planItem.setFollowPeriod(0);
			planItem.setMonitorPeriod(0);
			planItem.setBetMode(IbsModeEnum.BET_MODE_REGULAR.name());
			planItem.setFundSwapMode(IbsModeEnum.FUND_SWAP_MODE_NO_SWAP_ON_RESET.name());
			planItem.setPeriodRollMode(IbsModeEnum.PERIOD_ROLL_MODE_ALL.name());
			planItem.setPlanGroupData(groupData.toString());
//			planItem.setExpandInfo(expandInfo.toString());
			planItem.setCreateTime(new Date());
			planItem.setCreateTimeLong(System.currentTimeMillis());
			planItem.setUpdateTime(new Date());
			planItem.setUpdateTimeLong(System.currentTimeMillis());
			planItem.setState(IbsStateEnum.OPEN.name());
			new IbspPlanItemService().save(planItem);

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
}
