package com.ibm.old.v1.client.core.controller.bet;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.core.job.bet.BetIDCJob;
import com.ibm.old.v1.client.ibm_client_bet_error.t.entity.IbmClientBetErrorT;
import com.ibm.old.v1.client.ibm_client_bet_error.t.service.IbmClientBetErrorTService;
import com.ibm.old.v1.client.ibm_client_config.t.service.IbmClientConfigTService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.entity.IbmClientExistBetT;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.service.IbmClientHmGameSetTService;
import com.ibm.old.v1.common.doming.configs.IdcConfig;
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
 * @Description: IDC定时投注-消息接收端
 * @Author: zjj
 * @Date: 2019-03-09 16:58
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class BetIDCController implements ClientExecutor {
	protected Logger log = LogManager.getLogger(this.getClass());

	private String existHmId;
	private String messageReceiptBetId;
	private String handicapMemberId;
	private String gameCode;
	private String period;

	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmClientExistHmT hmExistT, String messageId)
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
		/*
		 * 查询盘口会员游戏设置
		 * 投注状态，游戏code,一定会有
		 * 每期投注时刻,双面分投额度,号码分投额度,BET_RATE_T_ 不一定有
		 */
		IbmClientHmGameSetTService hmGameSetTService = new IbmClientHmGameSetTService();
		Map<String, Object> hmBetInfo = hmGameSetTService.findBetHmInfo(hmExistT.getIbmClientExistHmId(), gameId);
		if (ContainerTool.isEmpty(hmBetInfo)) {
			bean.putEnum(IbmCodeEnum.IBM_404_HM_BET_INFO);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		messageReceiptBetId = messageId;
		gameCode = hmBetInfo.get("GAME_CODE_").toString();
		period = msgObj.get("period").toString();
		existHmId = hmExistT.getIbmClientExistHmId();
		handicapMemberId = hmExistT.getHandicapMemberId();

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
		if (Integer.parseInt(hmBetInfo.get("BET_SECOND_").toString())!=0) {
			betSecond = Integer.parseInt(hmBetInfo.get("BET_SECOND_").toString());
		} else {
			IbmClientConfigTService configTService = new IbmClientConfigTService();
			String sealTime = configTService.findValue(IbmHandicapEnum.IDC.name().concat("_").concat(gameCode).concat("_SEAL"));
			betSecond = NumberTool.getInteger(sealTime);
		}

		//手动投注
		if (IbmMethodEnum.MANUAL_BET.name().equals(msgObj.get("method"))) {
			//保存投注项
			if (saveBetItems(msgObj, hmBetInfo)) {
				BetIDCJob betIdcJob = new BetIDCJob();
				return betIdcJob.execute(existHmId, gameCode, period);
			}
		} else if (IbmTypeEnum.TRUE.name().equals(hmBetInfo.get("BET_STATE_"))) {
			//保存投注项
			saveBetItems(msgObj, hmBetInfo);
			return autoBet(period, existHmId, gameCode, betSecond, drawTime, timeDistance);
		} else {
			bean.putEnum(IbmCodeEnum.IBM_403_BET_ERROR);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
		}
		return bean;
	}

	/**
	 * 自动投注
	 *
	 * @param period       期数
	 * @param existHmId    存在信息id
	 * @param gameCode     游戏code
	 * @param betSecond    投注时间
	 * @param drawTime     开奖时间
	 * @param timeDistance 剩余时间
	 * @return 投注结果
	 */
	private JsonResultBeanPlus autoBet(String period, String existHmId, String gameCode, int betSecond, long drawTime,
			long timeDistance) throws Exception {
		JsonResultBeanPlus bean;//判断时间差和每期封盘前投注时间大小,小于或等于就直接投注,大于就按每期投注时刻设定定时任务
		if (timeDistance - betSecond <= 0) {
			// 马上投注
			BetIDCJob betIDCJob = new BetIDCJob();
			bean = betIDCJob.execute(existHmId, gameCode, period);
			return bean;
		} else {
			// 定时投注
			// 获取定时投注时间
			Date betTime = new Date(drawTime - betSecond * 1000);
			return QuartzIbmUtil.betJob(existHmId, IbmHandicapEnum.IDC, gameCode, period, betTime);
		}
	}
	/**
	 * 处理投注信息
	 *
	 * @param msgObj    消息内容
	 * @param hmBetInfo 盘口游戏设置
	 * @return 保存结果
	 */
	private boolean saveBetItems(JSONObject msgObj, Map<String, Object> hmBetInfo) throws Exception {
		IbmGameEnum game = IbmGameEnum.valueOf(hmBetInfo.get("GAME_CODE_").toString());
		if (game == null) {
			log.error("未知的游戏code");
			return false;
		}
		JSONArray betItems = JSONArray.fromObject(msgObj.get("betInfo"));
		Map<String, List<String>> errorBetItem;
		switch (game) {
			case PK10:
				errorBetItem = saveBallBetItem(betItems, hmBetInfo, "PK10_NUMBER");
				break;
			case XYFT:
				errorBetItem = saveBallBetItem(betItems, hmBetInfo, "XYFT_NUMBER");
				break;
			case CQSSC:
				//TODO,CQSSC尚未测试
//				errorBetItem =saveCqsscBetItem(msgObj, betRate, hmBetInfo);
//				break;
				//添加其他游戏
			default:
				return false;
		}
		// 保存投注失败的项
		if (ContainerTool.notEmpty(errorBetItem)) {
			Map<String,List<String>> errorInfo = saveErrorBetItem(errorBetItem);
			//获取更新异常的投注信息，返回到主服务器
			MessageReceipt.messageReceiptBetError(errorInfo, gameCode, period,IbmHandicapEnum.IDC.name());
		}
		return true;
	}
	/**
	 * 保存cqssc投注项
	 *
	 * @param betItems   投注内容
	 * @param betRate   投注比例
	 * @param hmBetInfo 盘口游戏设置
	 */
	private Map<String, List<String>> saveCqsscBetItem(JSONArray betItems, double betRate, Map<String, Object> hmBetInfo) throws Exception {

		//错误投注信息
		Map<String, List<String>> errorBetItem = new HashMap<>();
		int splitTwoSideAmount = NumberTool.getInteger(hmBetInfo.get("SPLIT_TWO_SIDE_AMOUNT_"));
		int splitNumberAmount = NumberTool.getInteger(hmBetInfo.get("SPLIT_NUMBER_AMOUNT_"));

		// 将投注信息存到客户端上
		JSONObject jsonObject;
		JSONObject betItem = new JSONObject();
		JSONArray number = new JSONArray();
		JSONArray poker = new JSONArray();
		JSONArray sum = new JSONArray();
		List<String> errorItems = new ArrayList<>();

		// 对投注项进行拼装并保存
		for (Object json : betItems) {
			jsonObject = (JSONObject) json;
			String betContent = jsonObject.getString("BET_CONTENT_");
			int fund = (int) (NumberTool.doubleT(jsonObject.get("FUND_T_")) * betRate / 100);
			String execBetItemId = jsonObject.get("IBM_EXEC_BET_ITEM_ID_").toString();
			if (fund < IdcConfig.LIMIT_MIN) {
				errorItems.add(json.toString().concat("#单注低于最低限额"));
				if (!errorItems.isEmpty()) {
					if (errorBetItem.containsKey(execBetItemId)) {
						errorBetItem.get(execBetItemId).addAll(errorItems);
					} else {
						errorBetItem.put(execBetItemId, errorItems);
					}
				}
				continue;
			}
			String[] betContentStr = betContent.split("#");
			String[] item;
			for (String bet : betContentStr) {
				//TODO,投注code未修改
				String limitCode = IdcConfig.BET_LIMIT_CODE.get(bet);
				int code = NumberTool.getInteger(limitCode);

				if (fund > splitNumberAmount && code >= 0 && code <= 9 && splitNumberAmount >= 10) {
					//号码分投额度
					item = getItem(splitNumberAmount, bet, fund);
				} else if (fund > splitTwoSideAmount && code >= 10 && code <= 12 && splitTwoSideAmount >= 10) {
					//双面分投额度
					item = getItem(splitTwoSideAmount, bet, fund);
				} else {
					item = new String[1];
					item[0] = bet.concat("#").concat(String.valueOf(fund));
				}
				for (String str : item) {
					//进行分投处理
					if (IdcConfig.CQSSC_NUMBER_CODE.get(bet) != null) {
						number.add(str);
					} else if (IdcConfig.CQSSC_POKER_CODE.get(bet) != null) {
						poker.add(str);
					} else if (IdcConfig.CQSSC_SUM_CODE.get(bet) != null) {
						sum.add(str);
					}
				}
			}
			if (number.size() != 0) {
				betItem.put("CQSSC_NUMBER", number);
				number.clear();
			}
			if (poker.size() != 0) {
				betItem.put("CQSSC_POKER", poker);
				poker.clear();
			}
			if (sum.size() != 0) {
				betItem.put("CQSSC_SUM", sum);
				sum.clear();
			}
			saveExistBetItem(jsonObject.get("IBM_EXEC_BET_ITEM_ID_"), betItem);
			betItem.clear();
		}
		return null;
	}

	/**
	 * 保存pk10投注项
	 *
	 * @param betItems    投注内容
	 * @param hmBetInfo 盘口游戏设置
	 * @return 错误投注项
	 */
	private Map<String, List<String>> saveBallBetItem(JSONArray betItems , Map<String, Object> hmBetInfo,
			String betHandicap) throws Exception {
		//错误投注信息
		Map<String, List<String>> errorBetItem = new HashMap<>();

		int splitTwoSideAmount = NumberTool.getInteger(hmBetInfo.get("SPLIT_TWO_SIDE_AMOUNT_"));
		int splitNumberAmount = NumberTool.getInteger(hmBetInfo.get("SPLIT_NUMBER_AMOUNT_"));

		JSONObject jsonObject;
		JSONObject betItem = new JSONObject();
		JSONArray number = new JSONArray();
		List<String> errorItems = new ArrayList<>();
		// 对投注项进行拼装并保存
		for (Object json : betItems) {
			//投注项
			jsonObject = (JSONObject) json;
			//投注内容
			String betContent = jsonObject.getString("BET_CONTENT_");
			int fund = (int) NumberTool.doubleT(jsonObject.get("FUND_T_"));

			String execBetItemId = jsonObject.get("IBM_EXEC_BET_ITEM_ID_").toString();
			if (fund < IdcConfig.LIMIT_MIN) {
				errorItems.add(jsonObject.get("BET_CONTENT_").toString().concat("#单注低于最低限额"));
				if (ContainerTool.notEmpty(errorItems)) {
					if (errorBetItem.containsKey(execBetItemId)) {
						errorBetItem.get(execBetItemId).addAll(errorItems);
					} else {
						errorBetItem.put(execBetItemId, errorItems);
					}
				}
				continue;
			}
			String[] bets = betContent.split("#");
			String[] item;
			for (String bet : bets) {
				String limitCode = IdcConfig.BET_LIMIT_CODE.get(bet);
				int code = NumberTool.getInteger(limitCode);
				//进行分投处理
				if (fund > splitNumberAmount && code >= 0 && code <= 9 && splitNumberAmount >= IdcConfig.LIMIT_MIN) {
					//号码分投额度
					item = getItem(splitNumberAmount, bet, fund);
				} else if (fund > splitTwoSideAmount && code >= 10 && code <= 12
						&& splitTwoSideAmount >= IdcConfig.LIMIT_MIN) {
					//双面分投额度
					item = getItem(splitTwoSideAmount, bet, fund);
				} else {
					item = new String[1];
					item[0] = bet.concat("#").concat(String.valueOf(fund));
				}
				number.addAll(Arrays.asList(item));
			}
			if (ContainerTool.notEmpty(number)) {
				betItem.put(betHandicap, number);
				number.clear();
			}
			if (ContainerTool.notEmpty(errorItems)) {
				if (errorBetItem.containsKey(execBetItemId)) {
					errorBetItem.get(execBetItemId).addAll(errorItems);
				} else {
					errorBetItem.put(execBetItemId, errorItems);
				}
			}
			// 将投注信息存到客户端上
			saveExistBetItem(execBetItemId, betItem);
			betItem.clear();
		}
		return errorBetItem;
	}

	/**
	 * 保存投注信息
	 *
	 * @param execBetItemId 执行投注项主键
	 * @param betItem       投注内容
	 */
	public void saveExistBetItem(Object execBetItemId, JSONObject betItem)
			throws Exception {
		IbmClientExistBetTService existBetTService = new IbmClientExistBetTService();
		IbmClientExistBetT betInfoExistT = new IbmClientExistBetT();
		betInfoExistT.setClientExistHmId(existHmId);
		betInfoExistT.setExecBetItemId(execBetItemId);
		betInfoExistT.setCloudReceiptBetId(messageReceiptBetId);
		betInfoExistT.setHandicapMemberId(handicapMemberId);
		betInfoExistT.setGameCode(gameCode);
		betInfoExistT.setPeriod(period);
		betInfoExistT.setBetInfo(betItem);
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
	private Map<String,List<String> > saveErrorBetItem(Map<String, List<String>> errorBetItem) throws Exception {
		IbmClientBetErrorTService betErrorTService = new IbmClientBetErrorTService();
		IbmClientBetErrorT betErrorT;
		Map<String,List<String>> errorInfo = new HashMap<>(errorBetItem.size());
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
			errorInfo.put(entry.getKey(),entry.getValue());
		}
		return errorInfo;
	}

	/**
	 * 根据分投获取投注项
	 *
	 * @param splitAmount 分投金额
	 * @param bet         投注项
	 * @param funds       资金
	 * @return 投注项
	 */
	private String[] getItem(int splitAmount, String bet, int funds) {
		String[] item;
		int len = funds / splitAmount;
		int surplus = funds % splitAmount;
		if(surplus==0){
			item = new String[len];
			for (int j = 0; j < len ; j++) {
				item[j] = bet.concat("#").concat(String.valueOf(splitAmount));
			}
		}else{
			item = new String[len + 1];
			for (int j = 0; j < len-1 ; j++) {
				item[j] = bet.concat("#").concat(String.valueOf(splitAmount));
			}
			if (surplus > IdcConfig.LIMIT_MIN) {
				item[len - 1] = bet.concat("#").concat(String.valueOf(splitAmount));
				item[len] = bet.concat("#").concat(String.valueOf(surplus));
			} else {
				//切割后的余数小于最低限额
				item[len - 1] = bet.concat("#").concat(String.valueOf(splitAmount + surplus - IdcConfig.LIMIT_MIN));
				item[len] = bet.concat("#").concat(String.valueOf(IdcConfig.LIMIT_MIN));
			}
		}
		return item;
	}
}
