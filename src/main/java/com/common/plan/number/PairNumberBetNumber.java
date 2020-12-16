package com.common.plan.number;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.MethodEnum;
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
 * @Description: 对子号码投号码
 * @Author: null
 * @Date: 2020-09-03 15:03
 * @Version: v1.0
 */
public class PairNumberBetNumber extends PlanBase {

	public static PairNumberBetNumber getInstance() {
		return new PairNumberBetNumber();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"TRACK_MODE_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());

		if(!expand.containsKey(PlanInfoEnum.TRACK_MODE_.name())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.intFilter("select",data,1,10);
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
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String select = planGroupItem().getString("select");
		String[] bets = planGroupItem().getString("bet").split(",");

		Game game=gameCode().getGameFactory().game();
		int rankMin=game.getRankMin();
		int rankMax=game.getRankMax();
		//方案组详情
		JSONObject groupData = new JSONObject();
		try {
			//扩展信息：{"TRACK_MODE_":"CLOSE"}
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());
			String trackMode=expand.getOrDefault(PlanInfoEnum.TRACK_MODE_.name(), MethodEnum.CLOSE.name()).toString();
			if(MethodEnum.OPEN.name().equals(trackMode)){
				boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
				if(!execResult){
					String betContent=historyMap.get("BET_CONTENT_").toString();
					//获取到上期投注名次
					String betInfo=betContent.split("#")[0].split("\\|")[0];
					groupData.put("select",betInfo);
					groupData.put("bet", planGroupItem().getString("bet"));
					return groupData;
				}
			}
			int index = ContainerTool.findIndex(baseData, select);

			Object monitorPeriod = period().findBeforePeriod(basePeriod(), 1);
			String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
			if (StringTool.isEmpty(valiDrawNumber)) {
				log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
				return null;
			}
			String[] valiData = valiDrawNumber.split(",");
			if(!select.equals(valiData[index])){
				return null;
			}
			//监控期数=0，即不监控，所有方案都按规则生成投注项
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(index, bets);
				if (!flag) {
					return null;
				}
			}
			index++;
			if(index<rankMin||index>rankMax){
				return null;
			}
			String rank =PlanTool.getRankCn(index, gameCode());
			groupData.put("select", rank);
			groupData.put("bet", planGroupItem().getString("bet"));
			return groupData;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return null;
		}
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			String select = groupData.getString("select");

			//选择位置或者投注位置为空
			if (StringTool.isEmpty(select) || ContainerTool.isEmpty(bets)) {
				log.info("解析数据出错，选择位置:{},投注信息:{}", select, Arrays.toString(bets));
				return null;
			}
			StringBuilder result = new StringBuilder();
			for(String bet:bets){
				result.append(select).append("|").append(bet).append("|").append(fundTh).append("#");
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
	 * @param select  选择位置
	 * @param bets    投注位置
	 * @return 开启方案组验证通过 true
	 */
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
				String vail=valiData[select];
				//监控期数内，匹配成功失败一次，就返回
				for (String bet : bets) {
					if(vail.equals(bet)){
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
