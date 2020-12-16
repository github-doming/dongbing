package com.common.plan.number;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 号码投注一起翻倍
 * @Author: null
 * @Date: 2020-09-03 15:17
 * @Version: v1.0
 */
public class NumberBetToDouble extends PlanBase {

	public static NumberBetToDouble getInstance() {
		return new NumberBetToDouble();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"UN_INCREASE_":"CLOSE","INVERSE_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());

		if(!expand.containsKey(PlanInfoEnum.FUNDS_OPTIONS_.name())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.strFilter("select",data,10);
			PlanDataFilterTool.strFilter("bet",data,10);
			entry.setValue(data);
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		for (String activeKey : activeKeys) {
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		JSONObject planGroup=new JSONObject();
		planGroup.put("0",activePlanGroup);
		return planGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {

		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupItem().entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			String select = data.getString("select");
			String bet =data.getString("bet");
			//算出跟进期数
			//监控期数=0，即不监控，所有方案都按规则生成投注项
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(select.split(","), bet.split(","));
				if (!flag) {
					return null;
				}
			}
		}
		return planGroupItem();
	}


	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			StringBuilder result = new StringBuilder();
			JSONObject data;
			String[] bets;
			String[] selects;
			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();
			int drawNumberMin=game.getDrawNumberMin();
			int drawNumberMax=game.getDrawNumberMax();
			for (Map.Entry<String, Object> entry : groupData.entrySet()) {
				data = JSONObject.parseObject(entry.getValue().toString());
				//解析数据
				bets = data.getString("bet").split(",");
				selects = data.getString("select").split(",");
				//选择位置或者投注位置为空
				if (ContainerTool.isEmpty(selects) || ContainerTool.isEmpty(bets)) {
					log.info("解析数据出错，选择位置:{},投注信息:{}", Arrays.toString(selects), Arrays.toString(bets));
					continue;
				}
				for (String select : selects) {
					int num=Integer.parseInt(select);
					if(num<rankMin||num>rankMax){
						continue;
					}
					String rank = PlanTool.getRankCn(num, gameCode());
					for (String bet : bets) {
						num=Integer.parseInt(bet);
						if(num<drawNumberMin||num>drawNumberMax){
							continue;
						}
						result.append(rank).append("|").append(bet).append("|").append(fundTh).append("#");
					}
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-号码投注
	 *
	 * @param selects 选择位置
	 * @param bets    投注位置
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(String[] selects, String[] bets){
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
				if (PlanTool.matchNum(baseData, valiData, selects, bets)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}
}
