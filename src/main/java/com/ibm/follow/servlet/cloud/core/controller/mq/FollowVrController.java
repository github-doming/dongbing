package com.ibm.follow.servlet.cloud.core.controller.mq;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.*;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.CloudCustomerTool;
import com.ibm.follow.servlet.cloud.core.CloudTool;
import com.ibm.follow.servlet.cloud.core.controller.tool.ProfitPeriodTool;
import com.ibm.follow.servlet.cloud.core.job.CloudMergeBetJob;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.entity.IbmHmBetItem;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import com.ibm.follow.servlet.cloud.vr_client_member.service.VrClientMemberService;
import com.ibm.follow.servlet.cloud.vr_fm_member_bet_item.entity.VrFmMemberBetItem;
import com.ibm.follow.servlet.cloud.vr_fm_member_bet_item.service.VrFmMemberBetItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 跟随虚拟会员投注控制器
 * @Author: null
 * @Date: 2020-07-14 14:51
 * @Version: v1.0
 */
public class FollowVrController {
	private static final Logger log = LogManager.getLogger(FollowVrController.class);
	private GameUtil.Code gameCode;
	private Object period;
	private String betContent;
	private String userId;

	public FollowVrController(GameUtil.Code gameCode, Object period, String betContent) {
		this.gameCode = gameCode;
		this.period = period;
		this.betContent = betContent;
	}

	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		userId=msgObj.getString("USER_ID_");
		String existMemberVrId = msgObj.getString("EXIST_MEMBER_VR_ID_");
		if(StringTool.isEmpty(userId,existMemberVrId)){
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		Map<String,Object> vrMemberInfo=new VrClientMemberService().findMemberInfo(existMemberVrId);
		if (ContainerTool.isEmpty(vrMemberInfo)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String vrMemberAccount=vrMemberInfo.get("VR_MEMBER_ACCOUNT_").toString();
		String vrMemberId=vrMemberInfo.get("VR_MEMBER_ID_").toString();
		//获取用户已登录盘口会员
		IbmHmInfoService hmInfoService=new IbmHmInfoService();
		Map<String, Map<String, Object>> handicapMemberInfos= hmInfoService.listHmIdByUserId(userId);
		if(ContainerTool.isEmpty(handicapMemberInfos)){
			saveBetItem(msgObj,vrMemberId,vrMemberAccount,IbmStateEnum.PROCESS.name());
			return bean.success();
		}
		//获取开启投注的盘口会员
		List<Map<String, Object>> gameInfos = new IbmHmGameSetService().findInfos(handicapMemberInfos.keySet(), GameUtil.id(gameCode));
		if(ContainerTool.isEmpty(gameInfos)){
			saveBetItem(msgObj,vrMemberId,vrMemberAccount,IbmStateEnum.PROCESS.name());
			return bean.success();
		}
		HandicapUtil.Code handicapCode =HandicapUtil.Code.value(vrMemberInfo.get("HANDICAP_CODE_").toString());
		String handicapId = HandicapUtil.id(handicapCode, IbmTypeEnum.MEMBER);

		JSONObject content=new JSONObject();
		content.put("METHOD_", IbmMethodEnum.FOLLOW_BET.name());
		content.put("PERIOD_", period);
		content.put("GAME_CODE_", gameCode.name());
		content.put("BET_CONTENT_", betContent);
		content.put("FUNDS_T_", msgObj.get("FUNDS_T_"));
		content.put("FOLLOW_BET_ID_", msgObj.get("FOLLOW_BET_ID_"));

		IbmHmSetService hmSetService=new IbmHmSetService();
		for(Map<String, Object> gameInfo:gameInfos){
			String handicapMemberId=gameInfo.get("HANDICAP_MEMBER_ID_").toString();
			Map<String, Object> setInfo = hmSetService.findInfo(handicapMemberId);

			double betRate = NumberTool.doubleT(setInfo.getOrDefault("BET_RATE_T_", 1000));
			//封盘前投注时间
			long betSecond = NumberTool.longValueT(gameInfo.get("BET_SECOND_"));
			//距离封盘大于投注设定时间，开启了投注合并
			String betMode = gameInfo.get("BET_MODE_").toString();

			long sealTime = gameCode.getGameFactory().period(handicapCode).getDrawTime(period)
					- CloudTool.getSealTime(handicapCode, gameCode) - System.currentTimeMillis();
			if (sealTime <= 0) {
				saveHmBet(msgObj, handicapId, handicapMemberId, betMode, betRate, IbmStateEnum.FAIL.name(),vrMemberId, vrMemberAccount,"已封盘");
				continue;
			}
			Map<String,Object> existHmInfo=handicapMemberInfos.get(handicapMemberId);
			content.put("EXIST_HM_ID_",existHmInfo.get("EXIST_HM_ID_"));
			RabbitMqTool.sendVrMemberBetInfo(content.toString(),existHmInfo.get("CLIENT_CODE_").toString());

			if (sealTime <= betSecond || betSecond == 0) {
				if (IbmStateEnum.OPEN.equal(setInfo.get("BET_MERGER_"))) {
					CloudCustomerTool.putBetItem(handicapMemberId, gameCode, betContent.split("#"),msgObj.getString("FOLLOW_BET_ID_"));
					mergeBet(handicapMemberId,handicapId, betRate,betMode);
					continue;
				}
			}
			if (IbmStateEnum.OPEN.equal(setInfo.get("BET_MERGER_"))) {
				Date startTime = new Date(sealTime - betSecond + System.currentTimeMillis());
				CloudCustomerTool.putBetItem(handicapMemberId, gameCode, betContent.split("#"),msgObj.getString("FOLLOW_BET_ID_"));
				// 开启合并投注定时器
				QuartzTool.saveCloudMergeBetJob(handicapId, handicapMemberId, handicapCode, gameCode, period, betRate,
						betMode, startTime);
				continue;
			}
			//存入会员投注数据
			saveHmBet(msgObj, handicapId, handicapMemberId, betMode, betRate, IbmStateEnum.PROCESS.name(),vrMemberId, vrMemberAccount,null);
		}

		bean.success();
		return bean;
	}

	private void saveBetItem(JSONObject msgObj,String vrMemberId,String vrMemberAccount,String execState) throws Exception {

		VrFmMemberBetItem fmMemberBetItem=new VrFmMemberBetItem();
		fmMemberBetItem.setVrFollowBetId(msgObj.get("FOLLOW_BET_ID_"));
		fmMemberBetItem.setUserId(userId);
		fmMemberBetItem.setGameCode(gameCode.name());
		fmMemberBetItem.setVrMemberId(vrMemberId);
		fmMemberBetItem.setFollowMemberAccount(vrMemberAccount);
		fmMemberBetItem.setPeriod(period);
		fmMemberBetItem.setBetContentLen(msgObj.getString("BET_INFO_").split("#").length);
		fmMemberBetItem.setBetContent(msgObj.getString("BET_INFO_"));
		fmMemberBetItem.setBetFundT(msgObj.get("BET_FUNDS_T_"));
		fmMemberBetItem.setFollowContent(betContent);
		fmMemberBetItem.setFollowFundT(msgObj.get("FUNDS_T_"));
		fmMemberBetItem.setCreateTime(new Date());
		fmMemberBetItem.setCreateTimeLong(System.currentTimeMillis());
		fmMemberBetItem.setUpdateTime(new Date());
		fmMemberBetItem.setUpdateTimeLong(System.currentTimeMillis());
		fmMemberBetItem.setState(IbmStateEnum.OPEN.name());
		fmMemberBetItem.setExecState(execState);
		new VrFmMemberBetItemService().save(fmMemberBetItem);
	}

	private static ThreadPoolExecutor getExecutor(){
		return ThreadExecuteUtil.findInstance().getJobExecutor();
	}

	private void mergeBet(String handicapMemberId,String handicapId, double betRate,String betMode) {
		getExecutor().execute(() -> {
			try {
				new CloudMergeBetJob().mergeBet(handicapMemberId, gameCode, period, betMode,handicapId, betRate).execute(null);
			} catch (Exception e) {
				log.error("投注失败", e);
			}
		});
	}

	private void saveHmBet(JSONObject msgObj, String handicapId, String handicapMemberId, String betMode,
						   double betRate, String execState,String vrMemberId,String vrMemberAccount, String msg) throws Exception {
		int fundsTh = 0;
		int betLen = 0;
		StringBuilder content = new StringBuilder();
		//获取投注code
		Map<Integer, Map<Integer, Integer>> betCode = GameTool.getBetCode(gameCode, betContent.split("#"), betRate);
		if (ContainerTool.isEmpty(betCode)) {
			return;
		}
		//获取投注详情信息
		for (Map.Entry<Integer, Map<Integer, Integer>> codeEntry : betCode.entrySet()) {
			for (Map.Entry<Integer, Integer> typeEntry : codeEntry.getValue().entrySet()) {
				betLen++;
				fundsTh += typeEntry.getValue();
				content.append(GameTool.itemStr(gameCode, codeEntry.getKey(), typeEntry.getKey(), typeEntry.getValue()))
						.append("#");
			}
		}
		//投注为虚拟投注时，汇总到当期投注信息,并且修改代理跟投信息
		if (IbmTypeEnum.VIRTUAL.name().equals(betMode)) {
			ProfitPeriodTool.saveHmProfitPeriodVr(handicapMemberId, handicapId, GameUtil.id(gameCode), period, fundsTh, betLen);
			//修改虚拟会员跟投信息
			new VrFmMemberBetItemService().updateProcessInfo(msgObj.getString("FOLLOW_BET_ID_"));
			execState=IbmStateEnum.SUCCESS.name();
		}
		saveBetItem(msgObj,vrMemberId,vrMemberAccount,execState);
		if(StringTool.isContains(period.toString(),"-")){
			period=period.toString().substring(4);
		}
		IbmHmBetItem hmBetItem = new IbmHmBetItem();
		hmBetItem.setClientHaFollowBetId(msgObj.getString("FOLLOW_BET_ID_"));
		hmBetItem.setHandicapId(handicapId);
		hmBetItem.setHandicapMemberId(handicapMemberId);
		hmBetItem.setGameId(GameUtil.id(gameCode));
		hmBetItem.setPeriod(period);
		hmBetItem.setBetMode(betMode);
		hmBetItem.setBetType(IbmTypeEnum.FOLLOW.ordinal());
		hmBetItem.setFollowMemberAccount(vrMemberAccount);
		hmBetItem.setBetContentLen(betLen);
		hmBetItem.setBetContent(content.toString());
		hmBetItem.setFundT(fundsTh);
		hmBetItem.setBetInfoCode(EncryptTool.encode(EncryptTool.Type.MD5, hmBetItem.getBetContent()));
		hmBetItem.setCreateTime(new Date());
		hmBetItem.setCreateTimeLong(System.currentTimeMillis());
		hmBetItem.setUpdateTime(new Date());
		hmBetItem.setUpdateTimeLong(System.currentTimeMillis());
		hmBetItem.setState(IbmStateEnum.OPEN.name());
		hmBetItem.setDesc(msg);

		hmBetItem.setExecState(execState);
		new IbmHmBetItemService().save(hmBetItem);
	}
}
