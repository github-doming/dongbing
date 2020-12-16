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
 * @Description: 开某投某一起翻倍
 * @Author: null
 * @Date: 2020-09-03 15:18
 * @Version: v1.0
 */
public class FollowOpenBetToDouble extends PlanBase {

	public static FollowOpenBetToDouble getInstance() {
		return new FollowOpenBetToDouble();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"FUNDS_OPTIONS_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.FUNDS_OPTIONS_.name())){
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
		StringBuilder key=new StringBuilder();
		for (String activeKey : activeKeys) {
			key.append(activeKey).append(",");
		}
		JSONObject data=new JSONObject();
		data.put("activeKey",key.toString());
		data.put("selects",activeSelect);

		JSONObject planGroup=new JSONObject();
		planGroup.put("0",data);
		return planGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		JSONArray selects = planGroupItem().getJSONArray("selects");
		String[] activeKeys = planGroupItem().getString("activeKey").split(",");
		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor() != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(selects, activeKeys);
			if (!flag) {
				return null;
			}
		}
		JSONObject data=new JSONObject();
		JSONObject groupData;
		JSONObject item;
		for(String activeKey:activeKeys){
			String open = baseData[Integer.parseInt(activeKey)];
			groupData = new JSONObject();
			//方案组详情
			for(Object object:selects){
				item= (JSONObject) object;
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
			if(ContainerTool.notEmpty(groupData)){
				data.put(activeKey,groupData);
			}
		}
		return data;
	}
	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			StringBuilder result = new StringBuilder();
			Game game=gameCode().getGameFactory().game();
			int drawMin=game.getDrawNumberMin();
			int drawMax=game.getDrawNumberMax();
			JSONObject data;
			for(Map.Entry<String, Object> entry:groupData.entrySet()){
				data=JSONObject.parseObject(entry.getValue().toString());
				//解析数据
				String[] bets = data.getString("bet").split(",");
				String rank = data.getString("rank");
				if (StringTool.isEmpty(rank) || ContainerTool.isEmpty(bets)) {
					log.info("解析数据出错，投注位置：{},投注信息：{}",rank, Arrays.toString(bets));
					continue;
				}
				String rankCn = PlanTool.getRankCn(Integer.parseInt(rank) + 1,gameCode());
				for (String bet : bets) {
					if(Integer.parseInt(bet)<drawMin||Integer.parseInt(bet)>drawMax){
						continue;
					}
					result.append(rankCn).append("|").append(bet).append("|").append(fundTh).append("#");
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
	 * 监控期数内全部匹配成功-开某投某
	 *
	 * @param selects   方案组详情
	 * @param activeKeys 名次
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(JSONArray selects, String[] activeKeys) {
		try {
			for(String activeKey:activeKeys){
				String open = baseData[Integer.parseInt(activeKey)];
				//监控期数计算
				for (int i = 1; i <= monitor(); i++) {
					Object monitorPeriod = period().findBeforePeriod(basePeriod(), i);
					String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
					if (StringTool.isEmpty(valiDrawNumber)) {
						log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
						return false;
					}
					String[] valiData = valiDrawNumber.split(",");

					String vali = valiData[Integer.parseInt(activeKey)];
					//监控期数内，匹配失败一次，就返回
					if (PlanTool.matchNum(open, vali, selects)) {
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
