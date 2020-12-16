package com.ibm.old.v1.client.core.controller.bet;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.core.job.bet.BetWs2Job;
import com.ibm.old.v1.client.ibm_client_bet_error.t.entity.IbmClientBetErrorT;
import com.ibm.old.v1.client.ibm_client_bet_error.t.service.IbmClientBetErrorTService;
import com.ibm.old.v1.client.ibm_client_config.t.service.IbmClientConfigTService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.entity.IbmClientExistBetT;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.service.IbmClientHmGameSetTService;
import com.ibm.old.v1.common.doming.configs.Ws2Config;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.*;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.*;

import java.util.*;
/**
 * @Description: 投注进WS2盘口
 * @Author: Dongming
 * @Date: 2019-03-23 14:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetWS2Controller implements ClientExecutor {

	protected Logger log = LogManager.getLogger(this.getClass());

	private String existHmId;
	private String messageReceiptBetId;
	private String handicapMemberId;
	private String gameCode;
	private String period;

	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmClientExistHmT existHmT, String messageId)
			throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String gameId = msgObj.getString("gameId");
		//参数中是否包含数据
		if (StringTool.isEmpty(gameId)) {
			bean.putEnum(IbmCodeEnum.IBM_404_GAME);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		if (ContainerTool.isEmpty(msgObj.get("betInfo"))) {
			bean.putEnum(IbmCodeEnum.IBM_404_BET_INFO);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		// 获取盘口会员账号中游戏的投注信息
		IbmClientHmGameSetTService hmGameSetTService = new IbmClientHmGameSetTService();
		Map<String, Object> hmBetInfo = hmGameSetTService.findBetHmInfo(existHmT.getIbmClientExistHmId(), gameId);
		if (ContainerTool.isEmpty(hmBetInfo)) {
			bean.putEnum(IbmCodeEnum.IBM_404_HM_BET_INFO);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		gameCode = hmBetInfo.get("GAME_CODE_").toString();
		period = msgObj.get("period").toString();
		existHmId = existHmT.getIbmClientExistHmId();
		handicapMemberId = existHmT.getHandicapMemberId();
		messageReceiptBetId = messageId;

		// 开奖时间戳
		long drawTime = PeriodTool.getDrawTime(gameCode, period);
		// 当前时间与开奖时间差
		long timeDistance = DateTool.distanceSecond(drawTime, System.currentTimeMillis());
		if (timeDistance <= 0) {
			bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
			return bean;
		}

		//查询投注时间
		int betSecond;
		if (Integer.parseInt(hmBetInfo.get("BET_SECOND_").toString()) != 0) {
			betSecond = Integer.parseInt(hmBetInfo.get("BET_SECOND_").toString());
		} else {
			IbmClientConfigTService configTService = new IbmClientConfigTService();
			String sealTime = configTService
					.findValue(IbmHandicapEnum.WS2.name().concat("_").concat(gameCode).concat("_SEAL"));
			betSecond = NumberTool.getInteger(sealTime);
		}

		//手动投注
		if (IbmMethodEnum.MANUAL_BET.name().equals(msgObj.get("method"))) {
			//保存投注项
			if (saveBetItem(hmBetInfo, msgObj)) {
				BetWs2Job betWs2Job = new BetWs2Job();
				return betWs2Job.execute(existHmId, gameCode, period);
			}
		} else if (IbmTypeEnum.TRUE.name().equals(hmBetInfo.get("BET_STATE_"))) {
			//保存投注项
			saveBetItem(hmBetInfo, msgObj);
			return autoBet(betSecond, drawTime, timeDistance);
		} else {
			bean.putEnum(IbmCodeEnum.IBM_403_BET_ERROR);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
		}
		return bean;
	}

	/**
	 * 自动投注
	 *
	 * @param betSecond    投注时间
	 * @param drawTime     开奖时间
	 * @param timeDistance 当前时间与开奖时间差
	 * @return 投注结果
	 */
	private JsonResultBeanPlus autoBet(int betSecond, long drawTime, long timeDistance) throws Exception {
		if (timeDistance - betSecond <= 0) {
			//马上投注
			BetWs2Job betWs2Job = new BetWs2Job();
			return betWs2Job.execute(existHmId, gameCode, period);
		} else {
			// 获取定时投注时间
			Date betTime = new Date(drawTime - betSecond * 1000);
			// 定时投注
			return QuartzIbmUtil.betJob(existHmId, IbmHandicapEnum.WS2, gameCode, period, betTime);
		}
	}

	/**
	 * 保存投注项
	 *
	 * @param hmBetInfo 开始投注的盘口会员信息
	 * @param msgObj    消息
	 * @return 保存结果
	 */
	private boolean saveBetItem(Map<String, Object> hmBetInfo, JSONObject msgObj) throws Exception {
		IbmGameEnum game = IbmGameEnum.valueOf(gameCode);
		if (ContainerTool.isEmpty(hmBetInfo.get("BET_LIMIT_"))) {
			log.error("未获取投注限额");
			return false;
		}
		JSONObject betLimit = JSONObject.fromObject(hmBetInfo.get("BET_LIMIT_"));
		JSONArray betItems = JSONArray.fromObject(msgObj.get("betInfo"));

		int splitTwoSideAmount = NumberTool.getInteger(hmBetInfo.get("SPLIT_TWO_SIDE_AMOUNT_"));
		int splitNumberAmount = NumberTool.getInteger(hmBetInfo.get("SPLIT_NUMBER_AMOUNT_"));
		Map<String, List<String>> errorBetItem = null;
		switch (game) {
			case PK10:
			case XYFT:
				errorBetItem = saveBallBetItem(betItems, betLimit, splitTwoSideAmount, splitNumberAmount);
				break;
			default:
		}
		// 保存投注失败的项
		if (ContainerTool.notEmpty(errorBetItem)) {
			Map<String, List<String> > errorInfo = saveErrorBetItem(errorBetItem);
			//获取更新异常的投注信息，返回到主服务器
			MessageReceipt.messageReceiptBetError(errorInfo, gameCode, period, IbmHandicapEnum.WS2.name());
		}
		return true;
	}
	/**
	 * 保存CQSSC投注项
	 *
	 * @param betItems           投注项
	 * @param betLimit           投注限额
	 * @param betRate            投注赔率
	 * @param splitTwoSideAmount 双面分投
	 * @param splitNumberAmount  号码分投
	 * @return
	 */
	private Map<String, List<String>> saveCqsscBetItem(JSONArray betItems, JSONObject betLimit, double betRate,
			int splitTwoSideAmount, int splitNumberAmount) throws Exception {
		//错误投注信息
		Map<String, List<String>> errorBetItem = new HashMap<>();

		JSONObject betCode, betItem;
		for (int i = 0; i < betItems.size(); i++) {
			//投注项
			betItem = betItems.getJSONObject(i);
			betCode = new JSONObject();
			//投注内容
			String betContent = betItem.getString("BET_CONTENT_");
			String[] bets = betContent.split("#");
			List<String> errorItems = new ArrayList<>();

			List<String> item = new ArrayList<>();

			for (String bet : bets) {
				String limitCode = Ws2Config.CQSSC_LIMIT_CODE.get(bet);
				JSONArray fundsLimit = betLimit.getJSONArray(limitCode);
				//投注金额
				int funds = (int) Math.floor(NumberTool.doubleT(betItem.get("FUND_T_")) * betRate / 100);
				int limitMin = fundsLimit.getInt(0);
				int limitMax = fundsLimit.getInt(1);

				if (funds < limitMin) {
					//低于最低限额
					errorItems.add(bet.concat("#单注低于最低限额"));

				} else if (funds > limitMax) {
					int code = NumberTool.getInteger(limitCode);
					if (code >= 2 || (splitNumberAmount == 0 && splitTwoSideAmount == 0) || (
							limitMax < splitNumberAmount && limitMax < splitTwoSideAmount)) {
						//高于最高限额
						errorItems.add(bet.concat("#单注高于最高限额"));
						continue;
					}
					if (Ws2Config.CQSSC_BET_CODE.containsKey(bet)) {
						if (code >= 0 && splitNumberAmount != 0 && limitMax >= splitNumberAmount) {
							//号码分投额度
							item.addAll(Arrays.asList(getItem(splitNumberAmount, bet, funds, limitMin)));
						} else if (code >= 1 && splitTwoSideAmount != 0 && limitMax >= splitTwoSideAmount) {
							// 双面分投额度
							item.addAll(Arrays.asList(getItem(splitTwoSideAmount, bet, funds, limitMin)));
						}
					}

				} else {
					if (Ws2Config.CQSSC_BET_CODE.containsKey(bet)) {
						item.add(bet.concat("#").concat(String.valueOf(funds)));
					}
				}
			}
			//投注code
			if (ContainerTool.notEmpty(item)) {
				betCode.put("BetCode", JSONArray.fromObject(item));
			}
			String execBetItemId = betItem.get("IBM_EXEC_BET_ITEM_ID_").toString();
			if (ContainerTool.notEmpty(errorItems)) {
				if (errorBetItem.containsKey(execBetItemId)) {
					errorBetItem.get(execBetItemId).addAll(errorItems);
				} else {
					errorBetItem.put(execBetItemId, errorItems);
				}
			}
			if (ContainerTool.notEmpty(betCode)) {
				// 将投注信息存到客户端上
				saveExistBetItem(execBetItemId, betCode);
			}
		}
		return errorBetItem;
	}

	/**
	 * 保存球投注项
	 *
	 * @param betItems           投注项
	 * @param betLimit           投注限额
	 * @param splitTwoSideAmount 双面分投
	 * @param splitNumberAmount  号码分投
	 * @return 错误投注项
	 */
	private Map<String, List<String>> saveBallBetItem(JSONArray betItems, JSONObject betLimit, int splitTwoSideAmount,
			int splitNumberAmount) throws Exception {
		//错误投注信息
		Map<String, List<String>> errorBetItem = new HashMap<>();

		JSONObject betCode, betItem;
		for (int i = 0; i < betItems.size(); i++) {
			//投注项
			betItem = betItems.getJSONObject(i);

			betCode = new JSONObject();
			//投注内容
			String betContent = betItem.getString("BET_CONTENT_");
			//投注金额
			int funds = (int) NumberTool.doubleT(betItem.get("FUND_T_"));

			String[] bets = betContent.split("#");
			List<String> errorItems = new ArrayList<>();

			//各个盘的容器
			List<String> no15Item = new ArrayList<>();
			List<String> no60Item = new ArrayList<>();
			List<String> sumItem = new ArrayList<>();

			for (String bet : bets) {
				String limitCode = Ws2Config.BALL_LIMIT_CODE.get(bet);
				JSONArray fundsLimit = betLimit.getJSONArray(limitCode);

				int limitMin = fundsLimit.getInt(0);
				int limitMax = fundsLimit.getInt(1);
				int code = NumberTool.getInteger(limitCode);
				boolean twoSide=funds > splitTwoSideAmount && limitMax > splitTwoSideAmount;
				boolean number =funds > splitNumberAmount && limitMax > splitNumberAmount;
				if (funds < limitMin) {
					//低于最低限额
					errorItems.add(bet.concat("#单注低于最低限额"));
				} else if (funds > limitMax) {
					if (code >= 12 || (splitNumberAmount == 0 && splitTwoSideAmount == 0) || (
							limitMax < splitNumberAmount && limitMax < splitTwoSideAmount)) {
						//高于最高限额
						errorItems.add(bet.concat("#单注高于最高限额"));
						continue;
					}
					if (Ws2Config.BALL_NO15_CODE.containsKey(bet)) {
						setSplitItem(no15Item, code, splitNumberAmount, splitTwoSideAmount, bet, funds, limitMin,
								limitMax);
					} else if (Ws2Config.BALL_NO60_CODE.containsKey(bet)) {
						setSplitItem(no60Item, code, splitNumberAmount, splitTwoSideAmount, bet, funds, limitMin,
								limitMax);
					} else if (Ws2Config.BALL_SUMDT_CODE.containsKey(bet)) {
						setSplitItem(sumItem, code, splitNumberAmount, splitTwoSideAmount, bet, funds, limitMin,
								limitMax);
					}
				} else if (twoSide||number) {
					if (Ws2Config.BALL_NO15_CODE.containsKey(bet)) {
						setSplitItem(no15Item, code, splitNumberAmount, splitTwoSideAmount, bet, funds, limitMin,
								limitMax);
					} else if (Ws2Config.BALL_NO60_CODE.containsKey(bet)) {
						setSplitItem(no60Item, code, splitNumberAmount, splitTwoSideAmount, bet, funds, limitMin,
								limitMax);
					} else if (Ws2Config.BALL_SUMDT_CODE.containsKey(bet)) {
						setSplitItem(sumItem, code, splitNumberAmount, splitTwoSideAmount, bet, funds, limitMin,
								limitMax);
					}
				} else {
					if (Ws2Config.BALL_NO15_CODE.containsKey(bet)) {
						no15Item.add(bet.concat("#").concat(String.valueOf(funds)));
					} else if (Ws2Config.BALL_NO60_CODE.containsKey(bet)) {
						no60Item.add(bet.concat("#").concat(String.valueOf(funds)));
					} else if (Ws2Config.BALL_SUMDT_CODE.containsKey(bet)) {
						sumItem.add(bet.concat("#").concat(String.valueOf(funds)));
					}
				}
			}
			//投注code
			if (ContainerTool.notEmpty(no15Item)) {
				betCode.put("ballNO15", JSONArray.fromObject(no15Item));
			} else if (ContainerTool.notEmpty(no60Item)) {
				betCode.put("ballNO60", JSONArray.fromObject(no60Item));
			} else if (ContainerTool.notEmpty(sumItem)) {
				betCode.put("sumDT", JSONArray.fromObject(sumItem));
			}

			String execBetItemId = betItem.get("IBM_EXEC_BET_ITEM_ID_").toString();
			if (ContainerTool.notEmpty(errorItems)) {
				if (errorBetItem.containsKey(execBetItemId)) {
					errorBetItem.get(execBetItemId).addAll(errorItems);
				} else {
					errorBetItem.put(execBetItemId, errorItems);
				}
			}
			if (ContainerTool.notEmpty(betCode)) {
				// 将投注信息存到客户端上
				saveExistBetItem(execBetItemId, betCode);
			}
		}
		return errorBetItem;
	}

	/**
	 * 分投
	 *
	 * @param itemList           投注项集合
	 * @param code               投注项编码
	 * @param splitNumberAmount  号码分投额度
	 * @param splitTwoSideAmount 双面分投额度
	 * @param bet                投注项单项内容
	 * @param funds              金额
	 * @param limitMin           最低限额
	 * @param limitMax           最高限额
	 */
	private void setSplitItem(List<String> itemList, int code, int splitNumberAmount, int splitTwoSideAmount,
			String bet, int funds, int limitMin, int limitMax) {
		if (code >= 0 && code <= 9 && splitNumberAmount != 0 && limitMax >= splitNumberAmount) {
			//号码分投额度
			itemList.addAll(Arrays.asList(getItem(splitNumberAmount, bet, funds, limitMin)));
		} else if (code >= 10 && code <= 12 && splitTwoSideAmount != 0 && limitMax >= splitTwoSideAmount) {
			// 双面分投额度
			itemList.addAll(Arrays.asList(getItem(splitTwoSideAmount, bet, funds, limitMin)));
		}
	}

	/**
	 * 保存投注信息
	 *
	 * @param execBetItemId 投注项id
	 * @param betCode       投注编码code
	 */
	private void saveExistBetItem(String execBetItemId, JSONObject betCode) throws Exception {
		IbmClientExistBetTService existBetTService = new IbmClientExistBetTService();
		IbmClientExistBetT betInfoExistT = new IbmClientExistBetT();
		betInfoExistT.setClientExistHmId(existHmId);
		betInfoExistT.setExecBetItemId(execBetItemId);
		betInfoExistT.setCloudReceiptBetId(messageReceiptBetId);
		betInfoExistT.setHandicapMemberId(handicapMemberId);
		betInfoExistT.setGameCode(gameCode);
		betInfoExistT.setPeriod(period);
		betInfoExistT.setBetInfo(betCode.toString());
		betInfoExistT.setCreateTime(new Date());
		betInfoExistT.setCreateTimeLong(betInfoExistT.getCreateTime().getTime());
		betInfoExistT.setState(IbmStateEnum.OPEN.name());
		existBetTService.save(betInfoExistT);
	}

	/**
	 * 保存错误投注项
	 *
	 * @param errorBetItem 错误投注项
	 */
	private Map<String, List<String>> saveErrorBetItem(Map<String, List<String>> errorBetItem) throws Exception {
		IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
		IbmClientBetErrorT betErrorT;
		Map<String, List<String>> errorInfo = new HashMap<>(errorBetItem.size());
		for (Map.Entry<String, List<String>> entry : errorBetItem.entrySet()) {
			betErrorT = new IbmClientBetErrorT();
			betErrorT.setClientExistHmId(existHmId);
			betErrorT.setExecBetItemId(entry.getKey());
			betErrorT.setCloudReceiptBetId(messageReceiptBetId);
			betErrorT.setHandicapMemberId(handicapMemberId);
			betErrorT.setGameCode(gameCode);
			betErrorT.setPeriod(period);
			betErrorT.setErrorBetInfo(String.join(",", entry.getValue()));
			betErrorT.setCreateTime(new Date());
			betErrorT.setCreateTimeLong(betErrorT.getCreateTime().getTime());
			betErrorT.setState(IbmStateEnum.CLOSE.name());
			betErrorTService.save(betErrorT);
			errorInfo.put(entry.getKey(), entry.getValue());
		}
		return errorInfo;
	}
	/**
	 * 根据分投获取投注项
	 *
	 * @param splitAmount 分投金额
	 * @param bet         投注项
	 * @param funds       资金
	 * @param limitMin    最小限额
	 * @return 投注项
	 */
	private String[] getItem(int splitAmount, String bet, int funds, int limitMin) {
		String[] item;
		int len = funds / splitAmount;
		int surplus = funds % splitAmount;
		if(surplus==0){
			item = new String[len ];
			for (int j = 0; j < len ; j++) {
				item[j] = bet.concat("#").concat(String.valueOf(splitAmount));
			}
		}else{
			item = new String[len + 1];
			for (int j = 0; j < len - 1; j++) {
				item[j] = bet.concat("#").concat(String.valueOf(splitAmount));
			}
			if (surplus > limitMin) {
				item[len - 1] = bet.concat("#").concat(String.valueOf(splitAmount));
				item[len] = bet.concat("#").concat(String.valueOf(surplus));
			} else {
				//切割后的余数小于最低限额
				item[len - 1] = bet.concat("#").concat(String.valueOf(splitAmount + surplus - limitMin));
				item[len] = bet.concat("#").concat(String.valueOf(limitMin));
			}
		}
		return item;
	}
}
