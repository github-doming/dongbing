package com.common.plan.number;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanTool;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 号码在位置遗漏
 * @Author: null
 * @Date: 2020-08-24 17:51
 * @Version: v1.0
 */
public class NumberInLocationLost extends PlanBase {

	public static NumberInLocationLost getInstance() {
		return new NumberInLocationLost();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"SELECT_AMOUNT_":"1","SELECT_MODE_":"HOT"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.SELECT_AMOUNT_.name())||!expand.containsKey(PlanInfoEnum.SELECT_MODE_.name())){
			return true;
		}
		if(StringTool.isEmpty(expand.get(PlanInfoEnum.SELECT_AMOUNT_.name()))){
			expand.put(PlanInfoEnum.SELECT_AMOUNT_.name(),"1");
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());

		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			int num=NumberTool.getInteger(entry.getValue());
			entry.setValue(String.valueOf(num));
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		for (String activeKey : activeKeys) {
			JSONObject data=new JSONObject();
			data.put("activeKey",planGroupData.get(activeKey));
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String activeKey = planGroupItem().getString("activeKey");
		JSONObject groupData = new JSONObject();
		try {
			String[][] hotAndColdData = CacheTool.getHotAndCold(gameCode(),handicapCode(),drawType(),period().findLotteryPeriod());
			if (ContainerTool.isEmpty(hotAndColdData)) {
				log.info("获取冷热数据为空，期数为：{}" , period().findLotteryPeriod());
				return null;
			}
			//扩展信息：{"SELECT_AMOUNT_":"1","SELECT_MODE_":"HOT"}
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());

			groupData.put("select",activeKey);
			int amount=expand.getInteger(PlanInfoEnum.SELECT_AMOUNT_.name());
			//冷热位置
			String selectMode=expand.getString(PlanInfoEnum.SELECT_MODE_.name());
			if(amount>=baseData.length){
				groupData.put("bet", StringUtils.join(baseData, ","));
				return groupData;
			}
			//1,2,3,2,5,6,7,9,8,10
			int[] rankSorts=new int[hotAndColdData[0].length];
			StringBuilder bet=new StringBuilder();

			for(int i=0;i<hotAndColdData.length;i++){
				int rank = ContainerTool.findIndex(hotAndColdData[i], activeKey);
				rankSorts[i]=rank+1;
			}
			int index;
			for(int i=0;i<amount;i++){
				if("COLD".equalsIgnoreCase(selectMode)){
					index=PlanTool.getMaxIndex(rankSorts);
				}else{
					index=PlanTool.getMinIndex(rankSorts);
				}
				bet.append(index).append(",");
				rankSorts[index]=0;
			}
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(activeKey, bet.toString().split(","));
				if (!flag) {
					return null;
				}
			}
			groupData.put("bet",bet.toString());
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return null;
		}
		//方案组详情
		return groupData;
	}


	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String[] bets = groupData.getString("bet").split(",");
		String select = groupData.getString("select");

		//选择位置或者投注位置为空
		if (StringTool.isEmpty(select) || ContainerTool.isEmpty(bets)) {
			log.info("解析数据出错，选择位置:{},投注信息:{}", select, Arrays.toString(bets));
			return null;
		}
		try {
			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();

			StringBuilder result = new StringBuilder();
			for (String bet : bets) {
				int num=Integer.parseInt(bet)+1;
				if(num<rankMin||num>rankMax){
					continue;
				}
				String rank = PlanTool.getRankCn(num, gameCode());
				result.append(rank).append("|").append(select).append("|").append(fundTh).append("#");
			}

			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}
	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-位置投注
	 *
	 * @param activeKey 选择位置
	 * @param bets    投注位置
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(String activeKey, String[] bets) {
		try {
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
				if (matchNum(valiData, activeKey, bets)) {
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
	 * 匹配号码 - 反投
	 *
	 * @param valiData  验证开奖数据
	 * @param activeKey 选择位置《验证开奖数据》
	 * @param bets      投注位置《基准开奖数据》
	 * @return 匹配成功
	 */
	private boolean matchNum(String[] valiData, String activeKey, String[] bets) {
		int rank = ContainerTool.findIndex(valiData, activeKey);
		for(String bet:bets){
			if(rank==Integer.parseInt(bet)){
				return true;
			}
		}
		return false;
	}
}
