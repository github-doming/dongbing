package com.common.plan.lost;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 遗漏大于设定
 * @Author: null
 * @Date: 2020-08-24 16:40
 * @Version: v1.0
 */
public class LostMoreThanSetUp extends PlanBase {

	public static LostMoreThanSetUp getInstance() {
		return new LostMoreThanSetUp();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())){
			return true;
		}
		//{"SELECT_AMOUNT_":"1","OMISSION_PERIOD_":"10"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		return !expand.containsKey(PlanInfoEnum.SELECT_AMOUNT_.name()) || !expand.containsKey(PlanInfoEnum.OMISSION_PERIOD_.name());
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();

		for (String activeKey : activeKeys) {
			JSONObject data=new JSONObject();
			data.put("activeKey",activeKey);
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String activeKey = planGroupItem().getString("activeKey");

		int location= NumberTool.getInteger(activeKey);

		try {
			int[][] interval=CacheTool.getLastInterval(gameCode(),handicapCode(),drawType(),period().findLotteryPeriod());
			if (ContainerTool.isEmpty(interval)) {
				log.info("获取号码间隔数为空，期数为：{}" , period().findLotteryPeriod());
				return null;
			}
			StringBuilder bet=new StringBuilder();
			//扩展信息：选号数量，最少遗漏
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());
			int amount=expand.getInteger(PlanInfoEnum.SELECT_AMOUNT_.name());
			int omissionPeriod=expand.getInteger(PlanInfoEnum.OMISSION_PERIOD_.name());

			int[] locationInterval=new int[interval[location].length];
			System.arraycopy(interval[location],0,locationInterval,0,interval[location].length);

			for(int i=0;i<amount;i++){
				int index=PlanTool.getMaxIndex(locationInterval);

				if(locationInterval[index]<omissionPeriod){
					break;
				}
				bet.append(index).append(",");
				locationInterval[index]=0;
			}
			if(StringTool.isEmpty(bet.toString())){
				return null;
			}
			//监控期数=0，即不监控，所有方案都按规则生成投注项
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(location, bet.toString().split(","));
				if (!flag) {
					return null;
				}
			}
			JSONObject groupData = new JSONObject();
			groupData.put("select",location+1);
			groupData.put("bet",bet.toString());
			//方案组详情
			return groupData;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return null;
		}
	}



	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String[] bets = groupData.getString("bet").split(",");
		int select = groupData.getInteger("select");
		//选择位置或者投注位置为空
		if (ContainerTool.isEmpty(bets)) {
			log.info("解析数据出错，选择位置:{},投注信息:{}", select, Arrays.toString(bets));
			return null;
		}
		try {
			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();
			StringBuilder result = new StringBuilder();
			String rank = PlanTool.getRankCn(select, gameCode());
			for (String bet : bets) {
				int num=Integer.parseInt(bet)+1;
				if(num<rankMin||num>rankMax){
					continue;
				}
				result.append(rank).append("|").append(num).append("|").append(fundTh).append("#");
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}


	private boolean valiBet(int select, String[] bets) {
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
				if (matchNum(valiData, select, bets)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}

	private boolean matchNum(String[] valiData, int select, String[] bets) {
		String vail=valiData[select-1];
		for(String bet:bets){
			if(baseData.length<Integer.parseInt(bet)){
				continue;
			}
			if(bet.equals(vail)){
				return true;
			}
		}
		return false;
	}
}
