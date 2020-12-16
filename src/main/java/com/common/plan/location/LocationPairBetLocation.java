package com.common.plan.location;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 位置对子投位置
 * @Author: null
 * @Date: 2020-08-24 11:31
 * @Version: v1.0
 */
public class LocationPairBetLocation extends PlanBase {

	public static LocationPairBetLocation getInstance() {
		return new LocationPairBetLocation();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"UN_INCREASE_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.UN_INCREASE_.name())|| ContainerTool.isEmpty(planItem.getPlanGroupData())){
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
		JSONObject data;
		for (String activeKey : activeKeys) {
			data=planGroupData.getJSONObject(activeKey);
			if(data.getInteger("select")==0||StringTool.isEmpty(data.get("bet"))){
				continue;
			}
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		int select = planGroupItem().getInteger("select");
		String bet = planGroupItem().getString("bet");
		String open;
		try {
			if (select == 0 || select > baseData.length) {
				log.info("获取验证数据失败，选择位置大于号码开奖长度,选择位置为：{}",select);
				return null;
			}
			open=baseData[select-1];
			Object monitorPeriod = period().findBeforePeriod(basePeriod(), 1);
			String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
			if (StringTool.isEmpty(valiDrawNumber)) {
				log.info("获取验证数据失败，监控期数为：{}" , monitorPeriod);
				return null;
			}
			String[] valiData = valiDrawNumber.split(",");
			String vail=valiData[select-1];
			//开出号码不相同
			if(!open.equals(vail)){
				return null;
			}
			//监控期数=0，即不监控，所有方案都按规则生成投注项
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(select, bet.split(","));
				if (!flag) {
					return null;
				}
			}
		} catch (Exception e) {
			log.info("获取验证数据失败，选择位置大于号码开奖长度,选择位置为：" + (select));
			return null;
		}
		//方案组详情,把要投注的号码open 进行保存
		JSONObject groupData = new JSONObject();
		groupData.put("select", open);
		groupData.put("bet", bet);
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//扩展信息：不中停止新增
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());
			if (StateEnum.OPEN.name().equals(expand.get(PlanInfoEnum.UN_INCREASE_.name())) && ContainerTool.notEmpty(historyMap)) {
				boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
				if(!execResult){
					log.info("触发扩展信息，不中停止新增,方案组信息:{}", groupData);
					return null;
				}
			}
			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			String select = groupData.getString("select");

			//选择位置或者投注位置为空
			if (StringTool.isEmpty(select) || ContainerTool.isEmpty(bets)) {
				log.info("解析数据出错，选择位置:{},投注信息:{}", select, Arrays.toString(bets));
				return null;
			}
			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();
			StringBuilder result = new StringBuilder();
			for (String bet : bets) {
				int num=Integer.parseInt(bet);
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

	/**
	 * 匹配号码 - 反投
	 *
	 * @param valiData 验证开奖数据
	 * @param select   选择位置《验证开奖数据》
	 * @param bets     投注位置《基准开奖数据》
	 * @return 匹配成功
	 */
	private boolean matchNum(String[] valiData, int select, String[] bets) {
		String vali = valiData[select - 1];
		//监控期数内，匹配成功失败一次，就返回
		for (String bet : bets) {
			if (bet.equals(vali)) {
				return true;
			}
		}
		return false;
	}
}
