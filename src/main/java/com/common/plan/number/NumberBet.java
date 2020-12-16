package com.common.plan.number;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
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
 * @Description: 号码投注
 * @Author: null
 * @Date: 2020-05-19 14:15
 * @Version: v1.0
 */
public class NumberBet extends PlanBase {

	public static NumberBet getInstance() {
		return new NumberBet();
	}


	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.strFilter("select",data,10);
			PlanDataFilterTool.strFilter("bet",data,20);
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
		String bet = planGroupItem().getString("bet");
		//算出跟进期数
		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor() != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(select.split(","), bet.split(","));
			if (!flag) {
				return null;
			}
		}
		return planGroupItem();
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {

			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			String[] selects = groupData.getString("select").split(",");

			//选择位置或者投注位置为空
			if (ContainerTool.isEmpty(selects) || ContainerTool.isEmpty(bets)) {
				log.info("解析数据出错，选择位置:{},投注信息:{}", Arrays.toString(selects), Arrays.toString(bets));
				return null;
			}
			return betContent(bets,selects,fundTh);
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

	/**
	 * 投注信息
	 * @param bets       投注位置
	 * @param selects    选择位置
	 * @param fundTh     金额
	 * @return
	 */
	private String betContent(String[] bets, String[] selects, long fundTh) {
		Game game=gameCode().getGameFactory().game();
		int rankMin=game.getRankMin();
		int rankMax=game.getRankMax();
		int drawNumberMin=game.getDrawNumberMin();
		int drawNumberMax=game.getDrawNumberMax();

		StringBuilder result = new StringBuilder();
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
		return result.toString();
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-号码投注
	 *
	 * @param selects 选择位置
	 * @param bets    投注位置
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(String[] selects, String[] bets) {
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
				if (matchNum(valiData, selects, bets)) {
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
	 * 号码投注验证
	 * @param valiData	验证开奖数据
	 * @param selects		投注位置
	 * @param bets			投注号码
	 * @return
	 */
	private boolean matchNum(String[] valiData, String[] selects, String[] bets) {
		for(String select:selects){
			if(valiData.length<Integer.parseInt(select)){
				continue;
			}
			String open = valiData[Integer.parseInt(select) - 1];
			for (String bet : bets) {
				if (open.equals(bet)) {
					return true;
				}
			}
		}
		return false;
	}
}
