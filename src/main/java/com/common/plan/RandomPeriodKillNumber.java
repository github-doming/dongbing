package com.common.plan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.game.Game;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import com.google.common.collect.ImmutableList;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 任意期数杀号
 * @Author: null
 * @Date: 2020-09-11 16:05
 */
public class RandomPeriodKillNumber extends PlanBase{

	public static RandomPeriodKillNumber getInstance() {
		return new RandomPeriodKillNumber();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getBetMode(),
				planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());

		String[] selects;
		StringBuilder result;
		JSONObject data;
		for (Map.Entry<String, Object> entry : planGroupData.entrySet()) {
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.strFilter("bet",data,10);
			selects = data.getString("select").split(",");
			if (ContainerTool.isEmpty(selects)) {
				data.put("select","0-0");
				entry.setValue(data);
				continue;
			}
			result=new StringBuilder();
			for (String select : selects) {
				String[] arr = select.split("-");
				if (ContainerTool.isEmpty(arr) || arr.length != 2) {
					continue;
				}
				int num=NumberTool.getInteger(arr[0]);
				if(num<=0||num>10||NumberTool.getInteger(arr[1])>10){
					continue;
				}
				result.append(select).append(",");
			}
			if(result.length()>0){
				result.delete(result.length()-1,result.length());
			}else{
				result.append("0-0");
			}
			data.put("select",result.toString());
			entry.setValue(data);
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		JSONObject data;
		for (String activeKey : activeKeys) {
			data=planGroupData.getJSONObject(activeKey);
			if("0-0".equals(data.getString("select"))){
				continue;
			}
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String[] selects = planGroupItem().getString("select").split(",");
		String bet = planGroupItem().getString("bet");
		try {
			List<String> killNumbers=new ArrayList<>();
			String[] arr;
			for(String select:selects){
				arr=select.split("-");
				int period=NumberTool.getInteger(arr[0]);
				int location=NumberTool.getInteger(arr[1]);
				if(period==0||location==0){
					continue;
				}
				Object monitorPeriod = period().findBeforePeriod(basePeriod(), period);
				String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
				if (StringTool.isEmpty(valiDrawNumber)) {
					log.error("获取验证数据失败，监控期数为：" + (monitorPeriod));
					continue;
				}
				String killNumber= valiDrawNumber.split(",")[location-1];
				if(!killNumbers.contains(killNumber)){
					killNumbers.add(killNumber);
				}
			}
			if(ContainerTool.isEmpty(killNumbers)){
				log.error("获取杀号信息失败，方案组信息：{}" , planGroupItem());
				return null;
			}
			//监控期数=0，即不监控，所有方案都按规则生成投注项
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(killNumbers, bet.split(","));
				if (!flag) {
					return null;
				}
			}
			//方案组详情
			JSONObject groupData = new JSONObject();
			groupData.put("bet",bet);
			groupData.put("killNumbers",killNumbers);
			return groupData;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return null;
		}
	}
	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String[] bets= groupData.getString("bet").split(",");
		JSONArray killNumbers = groupData.getJSONArray("killNumbers");
		//选择位置或者投注位置为空
		if (ContainerTool.isEmpty(bets) || ContainerTool.isEmpty(killNumbers)) {
			log.info("解析数据出错，选择位置:{},投注信息:{}", killNumbers, Arrays.toString(bets));
			return null;
		}
		List<String> numbers = ImmutableList.<String>builder().add("1").add("2").add("3").add("4").add("5").add("6")
				.add("7").add("8").add("9").add("10").build();

		StringBuilder result=new StringBuilder();
		Game game=gameCode().getGameFactory().game();
		int rankMin=game.getRankMin();
		int rankMax=game.getRankMax();
		for(String bet:bets){
			int num=NumberTool.getInteger(bet);
			if(num<rankMin||num>rankMax){
				continue;
			}
			String rank = PlanTool.getRankCn(num, gameCode());
			for(String number:numbers){
				if(killNumbers.contains(number)){
					continue;
				}
				result.append(rank).append("|").append(number).append("|").append(fundTh).append("#");
			}
		}
		return result.toString();
	}
	/**
	 * 验证该方案组是否验证通过<br>
	 * @param killNumbers		要杀的号码
	 * @param bets				投注位置
	 * @return
	 */
	private boolean valiBet(List<String> killNumbers, String[] bets) {
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
				for(String bet:bets){
					int num=NumberTool.getInteger(bet);
					if(num==0||num>valiData.length){
						continue;
					}
					//监控期数内，检验的号码不在杀号内就不通过
					if(!killNumbers.contains(valiData[num-1])){
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}

}
