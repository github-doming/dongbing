package com.common.plan.auto;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 随机号码投注
 * @Author: null
 * @Date: 2020-07-22 14:42
 * @Version: v1.0
 */
public class RandomBet extends PlanBase {

	public static RandomBet getInstance() {
		return new RandomBet();
	}


	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		//TODO 过滤方案组数据
		planItem.getPlanGroupData();

		//{"RENEW_PERIOD_":"0","UN_INCREASE_":"CLOSE"}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.RENEW_PERIOD_.name()) || !expand.containsKey(PlanInfoEnum.UN_INCREASE_.name())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.strFilter("select",data,10);
			PlanDataFilterTool.intFilter("select_count",data,0,10);
			PlanDataFilterTool.intFilter("bet_count",data,0,10);
			strFilter("bet",data);
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
		return  planGroupItem();
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//扩展信息：N期换新号，不中停止新增
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());
			if (ContainerTool.notEmpty(historyMap)) {
				boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
				if (!execResult) {
					if (StateEnum.OPEN.name().equals(expand.get(PlanInfoEnum.UN_INCREASE_.name()))) {
						log.info("触发扩展信息，不中停止新增,方案组信息:{}", groupData);
						return null;
					}
					int renewPeriod = expand.getInteger(PlanInfoEnum.RENEW_PERIOD_.name());
					Object oldBasePeriod = historyMap.get("BASE_PERIOD_");
					for (int i = 1; i < renewPeriod; i++) {
						Object basePeriod=gameCode().getGameFactory().period(handicapCode()).findBeforePeriod(basePeriod(),i-1);
						if(basePeriod.equals(oldBasePeriod)){
							StringBuilder result = new StringBuilder();
							getPlanGroup().setBasePeriod(basePeriod);
							String betContent = historyMap.get("BET_CONTENT_").toString();
							String[] betContents = betContent.split("#");
							for (String contents : betContents) {
								result.append(contents, 0, contents.lastIndexOf("|") + 1).append(fundTh).append("#");
							}
							return result.toString();
						}
					}
				}
			}
			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			String[] selects = groupData.getString("select").split(",");
			int selectCount = NumberTool.getInteger(groupData.get("select_count"));
			int betCount = NumberTool.getInteger(groupData.get("bet_count"));
			//投注位置为空或者投注号码为空或者投注数量大于投注位置长度/投注号码长度
			if (ContainerTool.isEmpty(selects) || ContainerTool.isEmpty(bets) || selectCount > selects.length || betCount > bets.length) {
				log.info("解析数据出错，投注位置:{},投注内容:{}", Arrays.toString(selects), Arrays.toString(bets));
				return null;
			}
			return betContent(selects, selectCount, bets, betCount, fundTh);
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

	/**
	 * 投注信息
	 *
	 * @param selects		 投注位置
	 * @param selectCount 投注数量
	 * @param bets        投注号码/双面
	 * @param betCount    投注号码数量
	 * @param fundTh      投注金额
	 * @return
	 */
	private String betContent(String[] selects, int selectCount, String[] bets, int betCount, long fundTh) {
		Game game=gameCode().getGameFactory().game();
		int rankMin=game.getRankMin();
		int rankMax=game.getRankMax();

		List<String> selectsRank=new ArrayList<>();
		for (String select : selects) {
			int num=Integer.parseInt(select);
			if(num<rankMin||num>rankMax){
				continue;
			}
			selectsRank.add(PlanTool.getRankCn(num, gameCode()));
		}
		List<String> betPosition=new ArrayList<>();
		for(String bet:bets){
			bet=PlanTool.getBetPosition(bet, gameCode());
			if(StringTool.isEmpty(bet)){
				continue;
			}
			betPosition.add(bet);
		}
		//数量大于投注位置长度
		if(betCount>betPosition.size()||selectCount>selectsRank.size()){
			return null;
		}
		List<String> selectList=getRandom(selectsRank,selectCount);
		List<String> betList=getRandom(betPosition,betCount);

		StringBuilder result = new StringBuilder();
		for (String select : selectList) {
			for (String bet : betList) {
				result.append(select).append("|").append(bet).append("|").append(fundTh).append("#");
			}
		}
		return result.toString();
	}

	/**
	 * 获取随机数组
	 * @param randoms		数组
	 * @param count		数量
	 * @return
	 */
	private List<String> getRandom(List<String> randoms,int count){
		List<String> result=new ArrayList<>();

		Random random = new Random();
		for (; count >=1;count--) {
			//在数组大小之间产生一个随机数 j
			int j = random.nextInt(randoms.size()-1);
			result.add(randoms.get(j));
			randoms.remove(j);
		}
		return result;
	}

	/**
	 * 过滤字符串方案组数据
	 * @param selectPosition		方案组数据key
	 * @param data					方案组数据
	 */
	public void strFilter(String selectPosition, JSONObject data) {
		if(StringTool.isEmpty(data.get(selectPosition))){
			data.put(selectPosition,"");
			return;
		}
		List<String> vailData=Arrays.asList(StringTool.HIGH_WAY);

		StringBuilder selectPlus=new StringBuilder();
		String[] selects=data.getString(selectPosition).split(",");
		for(String select:selects){
			if(!vailData.contains(select)){
				continue;
			}
			selectPlus.append(select).append(",");
		}
		if(selectPlus.length()>0){
			selectPlus.delete(selectPlus.length()-1,selectPlus.length());
		}
		data.put(selectPosition,selectPlus.toString());
	}
}
