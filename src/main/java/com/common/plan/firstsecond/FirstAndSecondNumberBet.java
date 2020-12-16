package com.common.plan.firstsecond;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 冠亚和号码投注
 * @Author: null
 * @Date: 2020-09-10 10:52
 */
public class FirstAndSecondNumberBet extends PlanBase {

	public static FirstAndSecondNumberBet getInstance() {
		return new FirstAndSecondNumberBet();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		planItem.setPlanGroupData(betFilter(planGroupData));
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		for (String activeKey : activeKeys) {
			if(StringTool.isEmpty(planGroupData.get(activeKey))){
				continue;
			}
			JSONObject json = new JSONObject();
			json.put("bet",planGroupData.get(activeKey));
			activePlanGroup.put(activeKey, json);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String bet = planGroupItem().getString("bet");
		if (monitor() != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(bet.split(","));
			if (!flag) {
				return null;
			}
		}
		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("bet", bet);
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String[] bets = groupData.getString("bet").split(",");
		//投注位置为空
		if (ContainerTool.isEmpty(bets)) {
			log.info("解析数据出错，投注信息:{}", Arrays.toString(bets));
			return null;
		}
		String rank = PlanTool.getRankCn(0, gameCode());
		StringBuilder result = new StringBuilder();
		for (String bet : bets) {
			int num=Integer.parseInt(bet);
			if(num<3||num>19){
				continue;
			}
			result.append(rank).append("|").append(num).append("|").append(fundTh).append("#");

		}
		return result.toString();
	}
	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-号码投注
	 *
	 * @param bets    投注位置
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(String[] bets) {
		try {
			int baseSum = NumberTool.getInteger(baseData[0])+NumberTool.getInteger(baseData[1]);
			//监控期数计算
			for (int i = 1; i <= monitor(); i++) {
				Object monitorPeriod = period().findBeforePeriod(basePeriod(), i);

				String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
				if (StringTool.isEmpty(valiDrawNumber)) {
					log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
					return false;
				}
				String[] valiData = valiDrawNumber.split(",");

				//监控期数内，匹配成功失败一次，就返回
				if (matchNum(valiData,bets,baseSum)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}

	/**
	 * 位置投注
	 * @param valiData 验证开奖数据
	 * @param bets     投注位置《基准开奖数据》
	 * @return 匹配成功
	 */
	public boolean matchNum(String[] valiData,  String[] bets,int baseSum) {
		int sum = NumberTool.getInteger(valiData[0])+NumberTool.getInteger(valiData[1]);
		int bet;
		for (String betStr : bets) {
			bet = NumberTool.getInteger(betStr);
			if (baseSum-bet==0 ||sum-bet==0) {
				return true;
			}
		}
		return false;
	}


	private JSONObject betFilter(JSONObject planGroupData) {
		String[] bets;
		StringBuilder betPlus;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			betPlus=new StringBuilder();
			bets=entry.getValue().toString().split(",");
			for(String bet:bets){
				if(NumberTool.getInteger(bet)>19 || NumberTool.getInteger(bet)<3){
					continue;
				}
				betPlus.append(bet).append(",");
			}
			if(betPlus.length()>0){
				betPlus.delete(betPlus.length()-1,betPlus.length());
			}
			entry.setValue(betPlus.toString());
		}
		return planGroupData;
	}
}
