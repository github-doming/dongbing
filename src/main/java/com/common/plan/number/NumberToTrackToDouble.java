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
 * 号码追踪一起翻倍
 *
 * @Author: Dongming
 * @Date: 2020-06-11 16:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class NumberToTrackToDouble extends PlanBase {
	public static NumberToTrackToDouble getInstance() {
		return new NumberToTrackToDouble();
	}


	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		if (!expand.containsKey(PlanInfoEnum.FUNDS_OPTIONS_.name())) {
			return true;
		}
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.intFilter("track",data,1,10);
			PlanDataFilterTool.strFilter("bet",data,10);
			entry.setValue(data);
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		JSONObject activePlanGroupDouble = new JSONObject();
		for (String activeKey : activeKeys) {
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		activePlanGroupDouble.put("0", activePlanGroup);
		return activePlanGroupDouble;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		JSONObject betGroupData;
		String track, bet;
		for (Map.Entry<String, Object> planGroup : planGroupItem().entrySet()) {
			betGroupData = JSONObject.parseObject(planGroup.getValue().toString());
			track = betGroupData.getString("track");
			bet = betGroupData.getString("bet");
			//监控期数=0，即不监控，所有方案都按规则生成投注项
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(track, bet.split(","), monitor(), basePeriod());
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
			String rank, track;
			String[] bets;
			JSONObject betData;
			Game game=gameCode().getGameFactory().game();
			int drawMin=game.getDrawNumberMin();
			int drawMax=game.getDrawNumberMax();
			for (Map.Entry<String, Object> planGroup : planGroupItem().entrySet()) {
				betData = JSONObject.parseObject(planGroup.getValue().toString());
				//解析数据
				track = betData.getString("track");
				bets = betData.getString("bet").split(",");
				// 追踪位置或投注位置为空
				if (StringTool.isEmpty(track) || ContainerTool.isEmpty(bets)) {
					log.info("解析数据出错，投注号码：{},追踪信息：{}", Arrays.toString(bets), track);
					continue;
				}
				int index = ContainerTool.findIndex(baseData, track);

				//投注位置
				rank = PlanTool.getRankCn(index + 1, gameCode());
				for (String bet : bets) {
					if (Integer.parseInt(bet) < drawMin || Integer.parseInt(bet) > drawMax) {
						continue;
					}
					result.append(rank).append("|").append(bet).append("|").append(fundTh).append("#");
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
	 * 监控期数内全部匹配 不中
	 *
	 * @param track      追踪号码
	 * @param bets       投注号码
	 * @param monitor    监控期数
	 * @param basePeriod 基准期数
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(String track, String[] bets, int monitor, Object basePeriod) {
		try {
			//监控期数计算
			for (int i = 1; i <= monitor; i++) {
				Object monitorPeriod = period().findBeforePeriod(basePeriod, i);
				String valiDrawNumber = CacheTool.getDraw(gameCode(), handicapCode(), drawType(), monitorPeriod);
				if (StringTool.isEmpty(valiDrawNumber)) {
					log.info("获取验证数据失败，验证期数为：" + (monitorPeriod));
					return false;
				}
				String[] valiData = valiDrawNumber.split(",");
				//监控期数内，匹配成功一次，就返回，中，不投注
				if (PlanTool.matchNum(baseData, valiData, track, bets)) {
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
