package com.ibs.plan.module.client.core.job.bet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.SgWinConfig;
import com.common.tools.PlanTool;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.client.ibsc_bet_error.service.IbscBetErrorService;
import com.ibs.plan.module.client.ibsc_bet_info.service.IbscBetInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 投注工具类
 *
 * @Author: null
 * @Date: 2020-06-06 15:06
 * @Version: v1.0
 */
public class BetJobDefine {
	protected static final Logger log = LogManager.getLogger(BetJobDefine.class);

	/**
	 * 保存投注信息
	 *
	 * @param betItemMap 投注信息map
	 * @param fundMap    金额map
	 * @param hmBetIdMap 投注主键map
	 * @param betType    投注类型
	 * @param item       投注项
	 * @param fund       投注金额
	 * @param hmBetId    投注主键
	 */
	public static void saveBetItem(Map<String, List<String>> betItemMap, Map<String, Long> fundMap,
								   Map<String, String> hmBetIdMap, String betType, String item, long fund, String hmBetId) {
		if (betItemMap.containsKey(betType)) {
			betItemMap.get(betType).add(item);
			//存储投注金额
			fundMap.put(betType, fundMap.get(betType) + fund);
			if (!StringTool.isContains(hmBetIdMap.get(betType), hmBetId)) {
				hmBetIdMap.put(betType, hmBetIdMap.get(betType).concat(",").concat(hmBetId));
			}
		} else {
			//存储投注项
			List<String> betItems = new ArrayList<>();
			betItems.add(item);
			betItemMap.put(betType, betItems);
			//存储投注金额
			fundMap.put(betType, fund);
			//存储投注id
			hmBetIdMap.put(betType, hmBetId);
		}
	}
	/**
	 * 发送错误回执
	 *
	 * @param msg     错误信息
	 * @param betType 投注类型
	 * @param data    回执数据
	 */
	public static void sendErrorReceipt(Object data, IbsTypeEnum betType, String msg) throws Exception {
		if(ContainerTool.isEmpty(data)){
			return ;
		}
		JSONObject content = new JSONObject();
		content.put("data", data);
		content.put("requestType", IbsStateEnum.ERROR.name());
		content.put("command", betType);
		content.put("msg", msg);
		RabbitMqTool.sendBet(content.toString(), "result");
	}

	/**
	 * 发送结果信息
	 * @param existHmId	已存在盘口会员id
	 * @param period		期数
	 * @param gameCode	游戏编码
	 */
	public static void sendResultReceipt(String existHmId, Object period, GameUtil.Code gameCode) throws Exception {
		JSONObject content = new JSONObject();
		IbscBetInfoService betInfoService = new IbscBetInfoService();
		IbscBetErrorService betErrorService = new IbscBetErrorService();
		//获取状态为success的投注信息，包含成功和部分成功的投注项
		List<String> betInfoIds = betInfoService
				.findBetInfoIds(existHmId, period, gameCode, IbsStateEnum.SUCCESS.name());

		List<Map<String, Object>> errorInfo;
		List<String> betIds;
		if (ContainerTool.notEmpty(betInfoIds)) {
			//获取投注部分成功的投注项
			errorInfo = betErrorService.findErrorInfo(betInfoIds);
			if (ContainerTool.notEmpty(errorInfo)) {
				//投注ids
				betIds = new ArrayList<>();
				for (Map<String, Object> map : errorInfo) {
					map.remove("ROW_NUM");
					betInfoIds.remove(map.remove("BET_INFO_ID_"));
					String[] betIdList = map.remove("BET_ID_LIST_").toString().split(",");
					for (String betId : betIdList) {
						if (!betIds.contains(betId)) {
							betIds.add(betId);
						}
					}
				}
				//发送部分成功投注信息，状态为again

				content.put("data", betIds);
				content.put("requestType", IbsStateEnum.AGAIN.name());
				content.put("msg", errorInfo);
				RabbitMqTool.sendBet(content.toString(), "result");
			}
			if (ContainerTool.notEmpty(betInfoIds)) {
				//发送成功投注信息
				betIds = betInfoService.findBetIds(betInfoIds);
				content.put("data", betIds);
				content.put("requestType", IbsStateEnum.SUCCESS.name());
				RabbitMqTool.sendBet(content.toString(), "result");
			}
		}
		//发送错误投注信息,查询状态为fail或者again的
		betInfoIds = betInfoService.findBetInfoIds(existHmId, period, gameCode);
		if (ContainerTool.notEmpty(betInfoIds)) {
			errorInfo = betErrorService.findErrorInfo(betInfoIds);
			if (ContainerTool.notEmpty(errorInfo)) {
				//投注ids
				betIds = new ArrayList<>();
				for (Map<String, Object> map : errorInfo) {
					map.remove("ROW_NUM");
					map.remove("BET_INFO_ID_");
					String[] betIdList = map.remove("BET_ID_LIST_").toString().split(",");
					for (String betId : betIdList) {
						if (!betIds.contains(betId)) {
							betIds.add(betId);
						}
					}
				}
				//发送部分成功投注信息，状态为again
				content.put("data", betIds);
				content.put("requestType", IbsStateEnum.FAIL.name());
				content.put("msg", errorInfo);
				RabbitMqTool.sendBet(content.toString(), "result");
			}
		}
	}
	/**
	 * 获取投注job
	 *
	 * @param handicapCode 盘口编码
	 * @return 投注实例
	 */
	public static BaseBetJob getJob(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return new BetIdcJob();
			case SGWIN:
				return new BetSgwinJob();
			case COM:
				return new BetComJob();
			case HQ:
				return new BetHqJob();
			default:
				throw new IllegalArgumentException("错误的盘口类型捕捉");
		}
	}
	/**
	 * 获取投注job
	 *
	 * @param handicapCode 盘口编码
	 * @return 投注实例
	 */
	public static Class<? extends BaseBetJob> getJobClass(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return BetIdcJob.class;
			case SGWIN:
				return BetSgwinJob.class;
			case COM:
				return BetComJob.class;
			case HQ:
				return BetHqJob.class;
			default:
				throw new IllegalArgumentException("错误的盘口类型捕捉");
		}
	}
	/**
	 * 分投处理
	 *
	 * @param gameSet   限额信息
	 * @param betInfo   投注项
	 * @param itemType  详情类型
	 * @param itemLimit 详情限额
	 * @param errorMap  错误信息map
	 * @param betId     投注id
	 * @return 分投信息
	 */
	public static String[] classifyProcess(Map<String, Object> gameSet, String betInfo, Map<String, String> itemType,
			Map<String, String> itemLimit, Map<String, Object> errorMap, String betId) {
		int splitTwoSideAmount = NumberTool.getInteger(gameSet.get("SPLIT_TWO_SIDE_AMOUNT_"));
		int splitNumberAmount = NumberTool.getInteger(gameSet.get("SPLIT_NUMBER_AMOUNT_"));

		String[] infos = betInfo.split("\\|");
		//投注项
		String bet = infos[0].concat("|").concat(infos[1]);
		//投注金额
		double fund = NumberTool.doubleT(infos[2]);
		if (fund < 1) {
			saveErrorInfo(errorMap, betId, betInfo);
			return null;
		}
		//只获取限额
		int type = NumberTool.getInteger(itemType.get(bet));
		String[] item;
		if (ContainerTool.isEmpty(gameSet.get("BET_LIMIT_"))) {
			log.error("盘口限额为空，投注项为" + betInfo);
			item = new String[1];
			item[0] = betInfo;
			return item;
		}
		JSONArray betLimit = JSONArray.parseArray(gameSet.get("BET_LIMIT_").toString());
		JSONArray limitInfo = betLimit.getJSONArray(type);
		//0为单注最小，1为单注最大，2为单期最大
		int minimum = limitInfo.getInteger(0);
		int maximum = limitInfo.getInteger(1);

		//区分双面，特号等类型，0为特号，1为双面
		int limit = NumberTool.getInteger(itemLimit.get(bet));
		//进行分投处理
		boolean number =
				limit == 0 && fund > splitNumberAmount && maximum > splitNumberAmount && splitNumberAmount > minimum;
		boolean twoSide =
				limit == 1 && fund > splitTwoSideAmount && maximum > splitTwoSideAmount && splitTwoSideAmount > minimum;

		if (fund < minimum) {
			//低于最低限额
			saveErrorInfo(errorMap, betId, betInfo);
			return null;
		} else if (fund > maximum) {
			number = limit == 0 && (splitNumberAmount < minimum || maximum < splitNumberAmount);
			twoSide = limit == 1 && (splitTwoSideAmount < minimum || maximum < splitTwoSideAmount);
			if (limit >= 2 || number || twoSide) {
				//高于最高限额
				saveErrorInfo(errorMap, betId, betInfo);
				return null;
			}
			if (limit == 0) {
				//号码分投额度
				item = PlanTool.getItem(splitNumberAmount, bet, fund, minimum);
			} else {
				//双面分投额度
				item = PlanTool.getItem(splitTwoSideAmount, bet, fund, minimum);
			}
		} else if (number) {
			//号码分投额度
			item = PlanTool.getItem(splitNumberAmount, bet, fund, minimum);
		} else if (twoSide) {
			//双面分投额度
			item = PlanTool.getItem(splitTwoSideAmount, bet, fund, minimum);
		} else {
			item = new String[1];
			item[0] = betInfo;
		}
		return item;

	}

	/**
	 * 保存错误信息
	 *
	 * @param errorMap 错误信息map
	 * @param betId    投注id
	 * @param betInfo  错误信息
	 */
	private static void saveErrorInfo(Map<String, Object> errorMap, String betId, String betInfo) {
		if (errorMap.containsKey(betId)) {
			errorMap.put(betId, errorMap.get(betId) + "#" + betInfo);
		} else {
			errorMap.put(betId, betInfo);
		}
	}

	public static void saveBetInfo(Map<String, List<String>> betItemMap, Map<String, Long> fundMap,
								   Map<String, String> hmBetIdMap, String[] items, String hmBetId) {
		for (String item : items) {
			String[] info = item.split("\\|");
			//投注项
			String bet = info[0].concat("|").concat(info[1]);
			//判断投注类型，双面，冠亚，特号
			if (StringTool.isContains(SgWinConfig.DOUBLE_SIDE.toString(), info[1])) {
				saveBetItem(betItemMap, fundMap, hmBetIdMap, "dobleSides", item, Long.parseLong(info[2]), hmBetId);
			} else if (StringTool.isContains(SgWinConfig.SUM_DT.toString(), bet)) {
				saveBetItem(betItemMap, fundMap, hmBetIdMap, "sumDT", item, Long.parseLong(info[2]), hmBetId);
			} else {
				saveBetItem(betItemMap, fundMap, hmBetIdMap, "ballNO", item, Long.parseLong(info[2]), hmBetId);
			}
		}
	}
}
