package com.common.plan.twoside;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.enums.TypeEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 跟反长龙
 * @Author: null
 * @Date: 2020-08-24 15:49
 * @Version: v1.0
 */
public class UnFollowDoubleSide extends PlanBase {

	public static UnFollowDoubleSide getInstance() {
		return new UnFollowDoubleSide();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"MONITOR_MODE_":"0","STOP_BUY_":"0","MONITOR_LENGTH_":"0"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		return !expand.containsKey(PlanInfoEnum.STOP_BUY_.name()) || !expand.containsKey(PlanInfoEnum.MONITOR_MODE_.name())
				|| !expand.containsKey(PlanInfoEnum.MONITOR_LENGTH_.name());
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
		String stateStr=planGroupItem().getString("state");
		if(!TypeEnum.TRUE.name().equalsIgnoreCase(stateStr)&& !TypeEnum.FALSE.name().equalsIgnoreCase(stateStr)){
			log.info("获取方案组数据失败，方案组数据:{}", planGroupItem().toString());
			return null;
		}
		String code = planGroupItem().getString("code");

		//扩展信息：{"MONITOR_MODE_":"0","STOP_BUY_":"0","MONITOR_LENGTH_":"0"}
		JSONObject expand = JSONObject.parseObject(expandInfo.toString());
		//monitorMode==2的暂未处理
		int monitorMode=expand.getInteger(PlanInfoEnum.MONITOR_MODE_.name());
		int monitorLength=expand.getInteger(PlanInfoEnum.MONITOR_LENGTH_.name());
		if (monitorLength != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(code, monitorLength,monitorMode);
			if (!flag) {
				return null;
			}
		}
		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("state", stateStr);
		groupData.put("code", code);
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//解析数据
			boolean state = Boolean.parseBoolean(groupData.getString("state"));
			String[] strs = groupData.getString("code").split("_");
			int numIndex = Integer.parseInt(strs[0]);
			int typeIndex = Integer.parseInt(strs[1]);

			Game game = gameCode().getGameFactory().game();
			switch (gameCode()) {
				case XYFT:
				case JS10:
				case SELF_10_2:
				case PK10:
				case COUNTRY_10:
					return betContentNumber(state,numIndex,typeIndex,game,fundTh);
				case JSSSC:
				case CQSSC:
				case SELF_SSC_5:
				case COUNTRY_SSC:
					return betContentBall(state,numIndex,typeIndex,game,fundTh);
				case XYNC:
				case GDKLC:
					return betContentHappy(state,numIndex,typeIndex,game,fundTh);
				default:
					log.warn("非法的游戏类型，类型为" + gameCode().name());
					return null;
			}
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

	private String betContentHappy(boolean state, int numIndex, int typeIndex, Game game, long fundTh) {
		StringBuilder result = new StringBuilder();
		int number;
		if (numIndex == 8) {
			//总和
			number = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]) + Integer.parseInt(baseData[2])
					+ Integer.parseInt(baseData[3]) + Integer.parseInt(baseData[4]) + Integer.parseInt(baseData[5])
					+ Integer.parseInt(baseData[6]) + Integer.parseInt(baseData[7]);
			result.append("总和");
		} else if (numIndex < 0 || numIndex > 8) {
			throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
		}else{
			number = Integer.parseInt(baseData[numIndex]);
			if(typeIndex==4){
				result.append("总和");
			}else{
				result.append(PlanTool.getRankCn(numIndex+1, gameCode()));
			}
		}
		switch (typeIndex) {
			//大小
			case 0:
				String size;
				if (numIndex==8) {
					//总和和
					if(number==84){
						return null;
					}
					size=game.sumSize(number,state);
				}else{
					size=game.size(number, state);
				}
				result.append("|").append(size);
				break;
			//单双
			case 1:
				result.append("|").append(game.single(number, state));
				break;
			//尾大尾小
			case 2:
				result.append("|").append(game.tailSize(number, state));
				break;
			//合单合双
			case 3:
				result.append("|").append(game.sumSingle(number, state));
				break;
			//龙虎
			case 4:
				int number2 = Integer.parseInt(baseData[7]);
				result.append("|").append(game.dragon(number,number2,state));
				break;
			default:
				throw new RuntimeException("非法的类型捕捉，类型为" + typeIndex);
		}
		return result.append("|").append(fundTh).toString();
	}

	private String betContentBall(boolean state, int numIndex, int typeIndex, Game game, long fundTh) {
		StringBuilder result = new StringBuilder();
		int number;
		if (numIndex == 5) {
			//总和
			number = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]) + Integer.parseInt(baseData[2])
					+ Integer.parseInt(baseData[3]) + Integer.parseInt(baseData[4]);
			result.append("总和");
		} else if (numIndex < 0 || numIndex > 5) {
			log.warn("非法的排序捕捉，索引为" + numIndex);
			return null;
		} else {
			number = Integer.parseInt(baseData[numIndex]);
			if (typeIndex == 2) {
				numIndex=6;
			}
			result.append(PlanTool.getRankCn(numIndex+1, gameCode()));
		}
		switch (typeIndex) {
			case 0:
				//大小
				String size;
				if(numIndex==5){
					//总和大小
					size=game.sumSize(number,state);
				}else{
					size=game.size(number, state);
				}
				result.append("|").append(size);
				break;
			case 1:
				//单双
				result.append("|").append(game.single(number, state));
				break;
			case 2:
				//龙虎
				if (numIndex != 6) {
					throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
				}
				int number2 = Integer.parseInt(baseData[4]);
				String dTSum=game.dragon(number,number2,state);
				result.append("|").append(dTSum);
				break;
			default:
				log.warn("非法的类型捕捉，类型为" + typeIndex);
				return null;
		}
		return result.append("|").append(fundTh).toString();
	}

	private String betContentNumber(boolean state, int numIndex, int typeIndex, Game game, long fundTh) {
		StringBuilder result = new StringBuilder();
		int number;
		if (numIndex == 10) {
			//冠亚和
			number = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]);
			result.append("冠亚");
		} else if (numIndex < 0 || numIndex > 10) {
			log.warn("非法的排序捕捉，索引为" + numIndex);
			return null;
		} else {
			result.append(PlanTool.getRankCn(numIndex + 1,gameCode()));
			number = Integer.parseInt(baseData[numIndex]);
		}
		switch (typeIndex) {
			case 0:
				//大小
				String size;
				if(numIndex == 10){
					//冠亚和大小
					size=game.sumSize(number,state);
				}else{
					size=game.size(number, state);
				}
				result.append("|").append(size);
				break;
			case 1:
				//单双
				result.append("|").append(game.single(number, state));
				break;
			case 2:
				//龙虎
				if (numIndex <= 0 || numIndex > 5) {
					log.warn("非法的排序捕捉，索引为" + numIndex);
					return null;
				}
				int number2 = Integer.parseInt(baseData[baseData.length - numIndex - 1]);
				result.append("|").append(game.dragon(number, number2, state));
				break;
			default:
				log.warn("非法的类型捕捉，类型为" + typeIndex);
				return null;
		}
		return result.append("|").append(fundTh).toString();
	}

	private boolean valiBet(String code,int monitorLength,int monitorMode) {
		//监控期数计算
		try {
			for (int i = 1; i <= monitorLength; i++) {
				Object monitorPeriod = period().findBeforePeriod(basePeriod(), i);

				String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
				if (StringTool.isEmpty(valiDrawNumber)) {
					log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
					return false;
				}
				String[] valiData = valiDrawNumber.split(",");
				try {
					//监控期数内，匹配失败一次，就返回
					if (!PlanTool.matchNum(baseData, valiData, true, code, gameCode())) {
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
			//不监控的时候，超过监控长度的都返回
			if(monitorMode==0){
				Object monitorPeriod = period().findBeforePeriod(basePeriod(), monitorLength+1);
				String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
				if (StringTool.isEmpty(valiDrawNumber)) {
					return true;
				}
				String[] valiData = valiDrawNumber.split(",");
				//监控期数内，匹配失败一次，就返回
				if (PlanTool.matchNum(baseData, valiData, true, code, gameCode())) {
					return false;
				}
			}
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
		return true;
	}
}
