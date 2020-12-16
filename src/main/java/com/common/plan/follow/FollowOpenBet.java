package com.common.plan.follow;

import com.alibaba.fastjson.JSONArray;
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
 * 开某投某
 *
 * @Author: Dongming
 * @Date: 2020-06-11 16:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FollowOpenBet extends PlanBase {
	public static FollowOpenBet getInstance() {
		return new FollowOpenBet();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"SAME_DOUBLE_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.SAME_DOUBLE_.name())){
			return true;
		}
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONArray  planData = planGroupData.getJSONArray("selects");
		JSONObject data;
		for (int i = 0; i <planData.size() ; i++) {
			data = planData.getJSONObject(i);
			PlanDataFilterTool.intFilter("select",data,0,10);
			PlanDataFilterTool.strFilter("bet",data,20);
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONArray activeSelect=new JSONArray();
		JSONArray selects=planGroupData.getJSONArray("selects");
		String[] activeGroup=planGroupData.getString("activeKey").split(",");
		for(String group:activeGroup){
			activeSelect.add(selects.getJSONObject(Integer.parseInt(group)));
		}
		JSONObject activePlanGroup = new JSONObject();
		for (String activeKey : activeKeys) {
			JSONObject data=new JSONObject();
			data.put("activeKey",activeKey);
			data.put("selects",activeSelect);
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		JSONArray selects = planGroupItem().getJSONArray("selects");
		String activeKey = planGroupItem().getString("activeKey");
		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor() != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(selects, activeKey);
			if (!flag) {
				return null;
			}
		}
		String open = baseData[Integer.parseInt(activeKey)];
		JSONObject groupData = new JSONObject();
		//方案组详情
		for(Object object:selects){
			JSONObject item= (JSONObject) object;
			String select = item.getString("select");
			if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2) {
				continue;
			}
			if (open.equals(select)) {
				if (groupData.containsKey("rank")) {
					groupData.put("bet", groupData.getString("bet").concat(",").concat(item.getString("bet")));
				} else {
					groupData.put("rank",activeKey);
					groupData.put("bet", item.getString("bet"));
				}
			}
		}
		return groupData;
	}
	@Override public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//TODO 扩展信息：下注相同内容时才翻倍
			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			String rank = groupData.getString("rank");
			if (StringTool.isEmpty(rank) || ContainerTool.isEmpty(bets)) {
				log.info("解析数据出错，投注位置：{},投注信息：{}",rank, Arrays.toString(bets));
				return null;
			}
			StringBuilder result = new StringBuilder();

			Game game=gameCode().getGameFactory().game();
			int drawMin=game.getDrawNumberMin();
			int drawMax=game.getDrawNumberMax();

			String rankCn = PlanTool.getRankCn(Integer.parseInt(rank) + 1,gameCode());
			for (String bet : bets) {
				if(Integer.parseInt(bet)<drawMin||Integer.parseInt(bet)>drawMax){
					continue;
				}
				result.append(rankCn).append("|").append(bet).append("|").append(fundTh).append("#");
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-开某投某
	 *
	 * @param selects   方案组详情
	 * @param activeKey 名次
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(JSONArray selects, String activeKey) {
		try {
			String open = baseData[Integer.parseInt(activeKey)];
			//监控期数计算
			for (int i = 1; i <= monitor(); i++) {
				Object monitorPeriod = period().findBeforePeriod(basePeriod(), i);
				String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
				if (StringTool.isEmpty(valiDrawNumber)) {
					log.info("获取验证数据失败，监控期数为：{}" , monitorPeriod);
					return false;
				}
				String[] valiData = valiDrawNumber.split(",");

				String vali = valiData[Integer.parseInt(activeKey)];
				//监控期数内，匹配失败一次，就返回
				if (PlanTool.matchNum(open, vali, selects)) {
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
