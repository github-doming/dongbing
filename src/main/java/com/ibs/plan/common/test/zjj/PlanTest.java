package com.ibs.plan.common.test.zjj;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-05-15 10:01
 * @Version: v1.0
 */
public class PlanTest {
	@Test
	public void test() throws SQLException, ParseException {
		/**
		 * 云端处理开奖
		 * 刚启动的客户机需要有过去20期左右的开奖结果信息
		 * 编码转换处理过程类似之前历史统计处理过程，然后在进行投注项合并
		 */
		/*
		单人版方案生成过程，多个用户通过开启线程的形式进行处理
		1，获取到已处理过的开奖结果，保存开奖信息，并结算
		2，编码
		3，转换
		4，合并
		5，发送投注信息回主服务器
		6，投注
		7，投注结果发送回主服务器
		 */

		/**
		 * 结算
		 * 编码
		 * 1，获取用户投注状态信息，方案开启信息
		 * 2，获取用户该游戏的方案详情信息
		 * 3，处理资金信息
		 * 4，处理停止新增信息
		 * 5，去重
		 * 6，组装数据，处理方案组信息，对监控期数内的信息进行整合
		 */

		/**
		 * 1，获取用户投注状态信息，方案开启信息
		 * 2，获取用户该游戏的方案详情信息
		 * 3，获取激活的方案组信息
		 * 4，组装数据
		 * 5，获取历史执行详情，得出使用资金key
		 * 6，方案组详情处理
		 * 7，判断期期滚
		 */

		//判断开奖结果信息是否是上一期开奖信息

		//判断用户信息是否存在

		//判断游戏是否封盘

		//获取用户投注状态信息

		//获取用户用户方案详情信息

		//获取激活的方案组key
		//主服务器直接发送开启的方案组信息到客户端
		//验证该方案组是否验证通过

		//合并前的数据还需不需要？

		//要，合并还是需要在用户设置的投注时刻前才能进行合并
		//否则会
		//如果用户在第一次投注之后在修改方案设置，导致有新的方案组key激活
		// 按理来说是要将刚激活的方案组进行方案生成的，但是要如何区分有没有生成过
		// 那就需要在编码完成之后进行判断当期当前方案的方案组key是否已经生成好了投注项
		//如果开启合并，原则上，只需要发合并后的投注数据回主服务器，合并前的数据不予展示
		// 那这样的话合并就不能等到最后才能合并了，只能先处理完进行合并，然后有新的方案组来，在重新进行方案生成处理
		//将方案组信息存到内存里，来新的先进行筛选出来，然后在进行保存

		//后续的投注过程，和跟单的流程大致相同，就是投注可以在优化一下，处理成可多次复投

		//获取到的数据是否用对象来进行处理

		//TODO 结算
		//新加个执行结果表




		String existHmId="";
		GameUtil.Code gameCode=GameUtil.Code.XYFT;
		Object period="";
//		IbscExistHmService existHmService=new IbscExistHmService();

		String handicapCode ="";
//				new IbscExistHmService().findHandicapCode(existHmId);
		if (StringTool.isEmpty(handicapCode)) {
			return ;
		}
		HandicapUtil.Code code = HandicapUtil.Code.valueOf(handicapCode);

		//获取距离封盘时间
//		long sealTime = CustomerCache.sealTime(existHmId, gameCode) - System.currentTimeMillis();
//		if (sealTime <= 0) {
//			//重新获取封盘事件
//			sealTime = CustomerCache.resetSealTime(existHmId, gameCode, code, period) - System.currentTimeMillis();
//			if (sealTime <= 0) {
//				return ;
//			}
//		}
		//个人配置信息
//		Map<String, Object> setGameInfo = new IbscHmGameSetService().findInfo(existHmId, gameCode.name());
//		if (!IbsTypeEnum.TRUE.equal(setGameInfo.get("BET_STATE_"))) {
//			return ;
//		}
		//获取该用户开启的游戏方案信息.
		List<Map<String,Object>> plans=null;
//		IbscHmPlanService hmPlanService=new IbscHmPlanService();
//		List<Map<String,Object>> plans= hmPlanService.findGamePlan(existHmId,gameCode);
		if(ContainerTool.isEmpty(plans)){
			return ;
		}
		//获取游戏方案详情信息，可以考虑存放到内存里
		List<Map<String,Object>> planItems=null;
//		IbscPlanItemService planItemService=new IbscPlanItemService();
//		List<Map<String,Object>> planItems= planItemService.findItems(existHmId,plans);
		if(ContainerTool.isEmpty(planItems)){
			return ;
		}
		//循环处理开启的方案
		for(Map<String,Object> planItem:planItems){
			if(StringTool.isEmpty(planItem.get("PLAN_GROUP_ACTIVE_DATA_"))){
				continue;
			}
			//方案组，需要过滤掉当期已经处理过的方案组
			JSONObject activePlanGroup = JSONObject.parseObject(planItem.get("PLAN_GROUP_ACTIVE_DATA_").toString());
			//监控期数
			int monitorPeriod = NumberTool.getInteger(planItem.get("MONITOR_PERIOD_"));
			//资金列表
			String funds = planItem.get("FUNDS_LIST_").toString();
			//资金切换模式
			String fundSwapMode = planItem.get("FUND_SWAP_MODE_").toString();

			//期期滚模式
			String periodRollMode=null;
			if(planItem.containsKey("PERIOD_ROLL_MODE_")){
				periodRollMode=planItem.get("PERIOD_ROLL_MODE_").toString();
			}
			for (Map.Entry<String, Object> entry : activePlanGroup.entrySet()) {
				//激活组
				String activeKey = entry.getKey();

				String planGroupDesc  = String.format("第%s组", StringTool.addOne(activeKey));

				//组装数据
				JSONObject groupData =new JSONObject();
//				JSONObject groupData = new SpliceGroupDate(JSONObject.parseObject(entry.getValue().toString()),
//						basePeriod, monitorPeriod, game).splice(planCode, false);

				if (ContainerTool.isEmpty(groupData)) {
					continue;
				}

				//资金处理
				String fundsKey = "0";
				//可以将历史方案组数据存储到内存里
//				for (int i = 1; i <= planGroupMap.size(); i++) {
					//获取历史执行详情
//					Map<String, Object> historyMap =StatisticsTool
//							.findHistory(planGroupMap, PeriodTool.findBeforePeriod(game, period, i),
//									groupData.toString());
//					if (ContainerTool.notEmpty(historyMap)) {
//						fundsKey = StatisticsTool
//								.fundsKey(funds, fundSwapMode, historyMap.get("fundGroupKey").toString(),
//										Boolean.parseBoolean(historyMap.get("execResult").toString()));
//						break;
//					}
//				}
				if (StringTool.isEmpty(fundsKey)) {
					continue;
				}
				String fundsValue =null;
				if (StringTool.notEmpty(funds)) {
					if (funds.split(",").length < Integer.parseInt(fundsKey)) {
						fundsValue=funds.split(",")[0];
					}else{
						fundsValue= funds.split(",")[Integer.parseInt(fundsKey)];
					}
				}
				//turn
				//方案组详情
				String betContent="";
//				String betContent = new PlanGroupContent(planCode, game, groupData, basePeriod)
//						.getBetContent(exitsMap, true);

				if (StringTool.isEmpty(betContent)) {
					continue;
				}
				//TODO 判断期期滚是否为“重复内容不投注”


				if (StringTool.isEmpty(betContent)) {
					continue;
				}

				//TODO 存储数据，整个coding和turn的过程结束了


			}

			//TODO 所有方案生成结束后才进行合并

			//合并之后的投注信息，个人余额


			//TODO 合并结束后按设置的投注时间来选择是否直接投注还是添加投注job

			//执行状态和数据状态分离


			//客户端需要规定好游戏的类型 SGWIN#JS10#TYPE

		}


	}
}
