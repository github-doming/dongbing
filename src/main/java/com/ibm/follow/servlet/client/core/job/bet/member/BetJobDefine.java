package com.ibm.follow.servlet.client.core.job.bet.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibm.common.core.configs.SgWinConfig;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_hm_bet.service.IbmcHmBetService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_error.service.IbmcHmBetErrorService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_info.service.IbmcHmBetInfoService;
import com.ibm.follow.servlet.client.ibmc_hm_info.service.IbmcHmInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * @Description: 投注工具类
 * @Author: null
 * @Date: 2019-10-26 14:40
 * @Version: v1.0
 */
public class BetJobDefine {
	protected static final Logger log = LogManager.getLogger(BetJobDefine.class);
	/**
	 * 分投处理
	 *
	 * @param hmGameSet          限额信息
	 * @param info               投注项
	 * @param ballCode           分投球号
	 * @param limitTypeCodes     限制code
	 * @param errorMap           错误信息map
	 * @param hmBetId            盘口会员投注id
	 * @return 分投信息
	 */
	static String[] classifyProcess(Map<String, Object> hmGameSet,String info, Map<String, String> ballCode,
        Map<String, String> limitTypeCodes, Map<String, Object> errorMap, String hmBetId) {
		int splitTwoSideAmount = NumberTool.getInteger(hmGameSet.get("SPLIT_TWO_SIDE_AMOUNT_"));
		int splitNumberAmount = NumberTool.getInteger(hmGameSet.get("SPLIT_NUMBER_AMOUNT_"));

		String[] infos = info.split("\\|");
		//投注项
		String bet = infos[0].concat("|").concat(infos[1]);
		//投注金额
		double fund = NumberTool.doubleT(infos[2]);
		if (fund < 1) {
			saveErrorInfo(errorMap, hmBetId, info);
			return null;
		}
		//只获取限额
		int limitCode = NumberTool.getInteger(ballCode.get(bet));
		String[] item;
		if(ContainerTool.isEmpty(hmGameSet.get("BET_LIMIT_"))){
			log.error("盘口限额为空，投注项为" + info);
			item = new String[1];
			item[0] = info;
			return item;
		}
		JSONArray betLimit = JSONArray.parseArray(hmGameSet.get("BET_LIMIT_").toString());
		JSONArray limit = betLimit.getJSONArray(limitCode);
		//0为单注最小，1为单注最大，2为单期最大
		int minimum = limit.getInteger(0);
		int maximum = limit.getInteger(1);

		//区分双面，特号等类型，0为特号，1为双面
		int typeCode = NumberTool.getInteger(limitTypeCodes.get(bet));
		//进行分投处理
		boolean number =
				typeCode == 0 && fund > splitNumberAmount && maximum > splitNumberAmount && splitNumberAmount > minimum;

		boolean twoSide = typeCode == 1 && fund > splitTwoSideAmount && maximum > splitTwoSideAmount
				&& splitTwoSideAmount > minimum;

		if (fund < minimum) {
			//低于最低限额
			saveErrorInfo(errorMap, hmBetId, info);
			return null;
		} else if (fund > maximum) {
			number = typeCode == 0 && (splitNumberAmount < minimum || maximum < splitNumberAmount);
			twoSide = typeCode == 1 && (splitTwoSideAmount < minimum || maximum < splitTwoSideAmount);
			if (typeCode >= 2 || number || twoSide) {
				//高于最高限额
				saveErrorInfo(errorMap, hmBetId, info);
				return null;
			}
			if (typeCode == 0) {
				//号码分投额度
				item = GameTool.getItem(splitNumberAmount, bet, fund, minimum);
			} else {
				//双面分投额度
				item = GameTool.getItem(splitTwoSideAmount, bet, fund, minimum);
			}
		} else if (number) {
			//号码分投额度
			item = GameTool.getItem(splitNumberAmount, bet, fund, minimum);
		} else if (twoSide) {
			//双面分投额度
			item = GameTool.getItem(splitTwoSideAmount, bet, fund, minimum);
		} else {
			item = new String[1];
			item[0] = info;
		}
		return item;
	}
	/**
	 * 保存错误信息
	 *
	 * @param errorMap 错误信息map
	 * @param hmBetId  投注id
	 * @param info     错误信息
	 */
	private static void saveErrorInfo(Map<String, Object> errorMap, String hmBetId, String info) {
		if (errorMap.containsKey(hmBetId)) {
			errorMap.put(hmBetId, errorMap.get(hmBetId) + "#" + info);
		} else {
			errorMap.put(hmBetId, info);
		}
	}
	/**
	 * 分批保存投注信息
	 *
	 * @param betItemMap 投注信息map
	 * @param fundMap    金额map
	 * @param hmBetIdMap 投注主键map
	 * @param items      投注项
	 * @param hmBetId    投注主键
	 */
	static void saveBetInfo(Map<String, List<String>> betItemMap, Map<String, Long> fundMap,
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
	/**
	 * 分批保存投注信息 NewWs盘口
	 *
	 * @param betItemMap 投注信息map
	 * @param fundMap    金额map
	 * @param hmBetIdMap 投注主键map
	 * @param items      投注项
	 * @param hmBetId    投注主键
	 */
	static void NewWsSaveBetInfo(Map<String, List<String>> betItemMap, Map<String, Long> fundMap,
							Map<String, String> hmBetIdMap, String[] items, String hmBetId,String gameCode) {
		for (String item : items) {
			String[] info = item.split("\\|");
			//投注项
			String bet = info[0].concat("|").concat(info[1]);
			// 广东快乐
			if("GDKLC".equals(gameCode)){
				saveBetItem(betItemMap, fundMap, hmBetIdMap, info[0], item, Long.parseLong(info[2]), hmBetId);
			}else{
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
	/**
	 * 分批保存投注信息 NewMoa盘口
	 *
	 * @param betItemMap 投注信息map
	 * @param fundMap    金额map
	 * @param hmBetIdMap 投注主键map
	 * @param items      投注项
	 * @param hmBetId    投注主键
	 */
	static void NewMoaSaveBetInfo(Map<String, List<String>> betItemMap, Map<String, Long> fundMap,
								 Map<String, String> hmBetIdMap, String[] items, String hmBetId,String gameCode) {
		for (String item : items) {
			String[] info = item.split("\\|");

			// 广东快乐
			if("GDKLC".equals(gameCode)){
				saveBetItem(betItemMap, fundMap, hmBetIdMap, info[0], item, Long.parseLong(info[2]), hmBetId);
			}else{
				saveBetItem(betItemMap, fundMap, hmBetIdMap, "zh", item, Long.parseLong(info[2]), hmBetId);

			}

		}
	}

	/**
	 * 分批保存投注信息 NewMoa盘口
	 *
	 * @param betItemMap 投注信息map
	 * @param fundMap    金额map
	 * @param hmBetIdMap 投注主键map
	 * @param items      投注项
	 * @param hmBetId    投注主键
	 */
	static void UnSaveBetInfo(Map<String, List<String>> betItemMap, Map<String, Long> fundMap,
								  Map<String, String> hmBetIdMap, String[] items, String hmBetId) {
		for (String item : items) {
			String[] info = item.split("\\|");
			saveBetItem(betItemMap, fundMap, hmBetIdMap, "B_109", item, Long.parseLong(info[2]), hmBetId);
		}
	}
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
	 * 消息内容
	 *
	 * @param existHmId   已存在盘口会员id
	 * @param gameCode    游戏code
	 * @param period      期数
	 * @param methodType  类型
	 * @param betInfoCode 投注信息code
	 * @return 消息内容
	 */
	static JSONObject messageInfo(String existHmId, GameUtil.Code gameCode, Object period, String methodType,
                                  List<String> betInfoCode) {
		JSONObject content = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("EXIST_HM_ID_", existHmId);
		data.put("GAME_CODE_", gameCode.name());
		data.put("PERIOD_", period);
		data.put("BET_INFO_CODE_", String.join(",", betInfoCode));
		content.put("token", data.toString());
		content.put("command", methodType);
		return content;
	}
	/**
	 * 发送用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param userInfo  用户信息
	 */
	static void sendUserInfo(String existHmId, MemberUser userInfo) throws Exception {
		if (ContainerTool.isEmpty(userInfo)) {
			return;
		}
		new IbmcHmInfoService().updateAmoutInfo(existHmId, userInfo.getCreditQuota(), userInfo.getUsedQuota(),
				userInfo.getProfitAmount());
		JSONObject content = new JSONObject();
		content.put("usedQuota", userInfo.getUsedQuota());
		content.put("profitAmount", userInfo.getProfitAmount());
		content.put("method", IbmMethodEnum.CUSTOMER_INFO.name());
		content.put("requestType", IbmStateEnum.SUCCESS.name());
		content.put("EXIST_HM_ID_", existHmId);
		RabbitMqTool.sendInfoReceipt(content.toString(), "member");
	}

    /**
     * 处理错误信息
     * @param existHmId         已存在盘口会员id
     * @param gameCode          游戏code
     * @param period            期数
     * @param errorMap          错误信息map
     * @param methodType        类型
     */
    static void errorProcess(String existHmId, GameUtil.Code gameCode, Object period, Map<String, Object> errorMap, String methodType) throws Exception {
        if (ContainerTool.isEmpty(errorMap)) {
            return;
        }
        log.error("盘口会员【{}】游戏【{}】期数【{}】错误投注项:{}",existHmId,gameCode,period,errorMap);
        new IbmcHmBetErrorService().save(existHmId, gameCode, period, errorMap);
        List<String> followBetIds = new IbmcHmBetService().findByIds(String.join(",", errorMap.keySet()));
        if (ContainerTool.isEmpty(followBetIds)) {
            log.error("盘口会员【{}】游戏【{}】期数【{}】会员投注信息不存在",existHmId,gameCode,period);
            return;
        }
        sendErrorReceipt(existHmId,gameCode,period,String.join(",", followBetIds),
                methodType,errorMap.values(),"投注项不符合限额");
    }

    /**
     * 发送错误信息
     * @param existHmId         已存在盘口会员id
     * @param gameCode          游戏code
     * @param period            期数
     * @param followBetIds      跟投ids
     * @param methodType        类型
     * @param errorBetInfo      错误投注信息
     * @param msg               错误信息
     */
    public static void sendErrorReceipt(String existHmId, GameUtil.Code gameCode, Object period,
        String followBetIds, String methodType, Collection<Object> errorBetInfo, String msg) throws Exception {
        JSONObject content = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("EXIST_HM_ID_", existHmId);
        data.put("GAME_CODE_", gameCode.name());
        data.put("PERIOD_", period);
        data.put("FOLLOW_BET_ID_", followBetIds);
        content.put("token", data.toString());
        content.put("command", methodType);
        if(ContainerTool.notEmpty(errorBetInfo)){
            content.put("ERROR_BET_INFO_", errorBetInfo);
        }
        content.put("requestType", IbmStateEnum.ERROR.name());
        content.put("msg", msg);
        RabbitMqTool.sendInfoReceipt(content.toString(), "bet.result");
    }

    /**
     * 发送结果信息
     * @param existHmId         已存在盘口会员id
     * @param period            期数
     * @param gameCode          游戏code
     * @param methodType        投注类型（手动投注，自动投注）
     */
    static void sendResultReceipt(String existHmId, Object period, GameUtil.Code gameCode, String methodType)
            throws Exception {
        IbmcHmBetService hmBetService = new IbmcHmBetService();
        IbmcHmBetInfoService hmBetInfoService = new IbmcHmBetInfoService();
        IbmcHmBetErrorService hmBetErrorService = new IbmcHmBetErrorService();

        JSONObject content;
        List<String> betInfoCode;
        List<Map<String, Object>> errorInfo;
        List<String> hmbets;
        //获取状态为success的投注信息，包含成功和部分成功的投注项
        List<String> hmBetInfoIds = hmBetInfoService
                .findHmBetInfoIds(existHmId, period, gameCode, IbmStateEnum.SUCCESS.name());
        if (ContainerTool.notEmpty(hmBetInfoIds)) {
            //获取投注部分成功的投注项
            errorInfo = hmBetErrorService.findErrorInfo(hmBetInfoIds);
            if (ContainerTool.notEmpty(errorInfo)) {
                //投注ids
                hmbets = new ArrayList<>();
                for (Map<String, Object> map : errorInfo) {
                    map.remove("ROW_NUM");
                    hmBetInfoIds.remove(map.remove("HM_BET_INFO_ID_"));
                    String[] hmBetIdList = map.remove("HM_BET_ID_LIST_").toString().split(",");
                    for (String hmBetId : hmBetIdList) {
                        if (!hmbets.contains(hmBetId)) {
                            hmbets.add(hmBetId);
                        }
                    }
                }
                //发送部分成功投注信息，状态为again
                betInfoCode = hmBetService.findBetInfoCodes(hmbets);
                content = BetJobDefine.messageInfo(existHmId, gameCode, period, methodType, betInfoCode);
                content.put("errorInfo", errorInfo);
                content.put("requestType", IbmStateEnum.AGAIN.name());
                RabbitMqTool.sendInfoReceipt(content.toString(), "bet.result");
            }
            if (ContainerTool.notEmpty(hmBetInfoIds)) {
                //发送成功投注信息
                betInfoCode = hmBetService.findSuccessBetInfoCodes(hmBetInfoIds);
                content = BetJobDefine.messageInfo(existHmId, gameCode, period, methodType, betInfoCode);
                content.put("requestType", IbmStateEnum.SUCCESS.name());
                RabbitMqTool.sendInfoReceipt(content.toString(), "bet.result");
            }
        }
        //发送错误投注信息,查询状态为fail或者again的
        hmBetInfoIds = hmBetInfoService.findHmBetInfoIds(existHmId, period, gameCode);
        if (ContainerTool.notEmpty(hmBetInfoIds)) {
            errorInfo = hmBetErrorService.findErrorInfo(hmBetInfoIds);
            if (ContainerTool.notEmpty(errorInfo)) {
                //投注ids
                hmbets = new ArrayList<>();
                for (Map<String, Object> map : errorInfo) {
                    map.remove("ROW_NUM");
                    map.remove("HM_BET_INFO_ID_");
                    String[] hmBetIdList = map.remove("HM_BET_ID_LIST_").toString().split(",");
                    for (String hmBetId : hmBetIdList) {
                        if (!hmbets.contains(hmBetId)) {
                            hmbets.add(hmBetId);
                        }
                    }
                }
                //发送部分成功投注信息，状态为again
                betInfoCode = hmBetService.findBetInfoCodes(hmbets);
                content = BetJobDefine.messageInfo(existHmId, gameCode, period, methodType, betInfoCode);
                content.put("errorInfo", errorInfo);
                content.put("requestType", IbmStateEnum.FAIL.name());
                RabbitMqTool.sendInfoReceipt(content.toString(), "bet.result");
            }
        }
    }


}
