package com.ibs.plan.connector.pc.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.entity.IbspHmModeCutover;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import com.ibs.plan.module.cloud.ibsp_hm_set.entity.IbspHmSet;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import com.ibs.plan.module.cloud.ibsp_log_hm.entity.IbspLogHm;
import com.ibs.plan.module.cloud.ibsp_log_hm.service.IbspLogHmService;
import com.ibs.plan.module.cloud.ibsp_profit.service.IbspProfitService;
import com.ibs.plan.module.cloud.ibsp_profit_vr.service.IbspProfitVrService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * @Description: 保存会员设置信息
 * @Author: null
 * @Date: 2020-05-26 16:30
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/hmSet", method = HttpConfig.Method.POST)
public class HandicapSetAction extends CommCoreAction {
	private String handicapMemberId;
	private String betRate;
	private String profitLimitMax;
	private String profitLimitMin;
	private String lossLimitMin;
	private String resetType;
	private String resetProfitMax;
	private String resetLossMin;
	private String modeCutoverState;
	private String cutoverProfit;
	private String cutoverLoss;
	private String blastStop;
	private String betMerger;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		if (valiParameters()) {
			return bean.put401Data();
		}
		if (super.denyTime()) {
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		String desc = this.getClass().getName().concat("修改盘口设置");
		try {
			//用户是否登录
			if (!IbsStateEnum.LOGIN.equal(new IbspHmInfoService().findLoginState(handicapMemberId))) {
				bean.putEnum(CodeEnum.IBS_404_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			IbspHmSetService hmSetService = new IbspHmSetService();
			IbspHmSet hmSet = hmSetService.findEntityByHmId(handicapMemberId);
			if (hmSet == null) {
				return bean.put404HandicapMember();
			}
			//模式切换状态为CLOSE，清除模式智能切换信息
			if(!hmSet.getModeCutoverState().equals(modeCutoverState)){
				IbspHmModeCutoverService hmModeCutoverService=new IbspHmModeCutoverService();
				IbspHmModeCutover hmModeCutover=hmModeCutoverService.findEntity(handicapMemberId);
				hmModeCutover.setProfitT(0);
				hmModeCutover.setUpdateTime(new Date());
				hmModeCutover.setUpdateTimeLong(System.currentTimeMillis());
				if(IbsStateEnum.CLOSE.name().equals(modeCutoverState)){
					hmModeCutover.setCurrentIndex(null);
					hmModeCutover.setCurrent(null);
					hmModeCutover.setCutoverProfitT(0);
					hmModeCutover.setCutoverLossT(0);
					hmModeCutover.setCutover(null);
					hmModeCutover.setResetProfit(null);
				}else{
					if(StringTool.notEmpty(hmModeCutover.getCutoverGroupData(),hmModeCutover.getCutoverKey())){
						String[] cutoverKeys=hmModeCutover.getCutoverKey().split(",");
						JSONObject data=JSONObject.parseObject(hmModeCutover.getCutoverGroupData()).getJSONObject(cutoverKeys[0]);
						hmModeCutover.setCurrentIndex(cutoverKeys[0]);
						hmModeCutover.setCurrent(data.getString("CURRENT_"));
						hmModeCutover.setCutoverProfitT(NumberTool.longValueT(data.get("CUTOVER_PROFIT_")));
						hmModeCutover.setCutoverLossT(NumberTool.longValueT(data.get("CUTOVER_LOSS_")));
						hmModeCutover.setCutover(data.getString("VIRTUAL"));
						hmModeCutover.setResetProfit(data.get("RESET_PROFIT_"));
					}
				}
				hmModeCutoverService.update(hmModeCutover);
			}

			Integer betRateTh = NumberTool.intValueT(betRate) / 100;
			hmSet.setBetRateT(betRateTh);
			hmSet.setProfitLimitMax(NumberTool.getInteger(profitLimitMax));
			hmSet.setProfitLimitMin(NumberTool.getInteger(profitLimitMin));
			hmSet.setLossLimitMin(NumberTool.getInteger(lossLimitMin));
			hmSet.setResetType(resetType);
			if(StringTool.notEmpty(resetProfitMax,resetLossMin)){
				hmSet.setResetProfitMax(NumberTool.getInteger(resetProfitMax));
				hmSet.setResetLossMin(NumberTool.getInteger(resetLossMin));
			}
			hmSet.setModeCutoverState(modeCutoverState);
			hmSet.setBlastStop(blastStop);
			hmSet.setBetMerger(betMerger);
			hmSet.setUpdateTimeLong(System.currentTimeMillis());
			hmSet.setDesc(desc);
			hmSetService.update(hmSet);

			//设置盘口会员止盈止损
			profit(hmSet);
			profitVr(hmSet);

			Map<String, Object> existInfo = new IbspClientHmService().findExistHmInfo(handicapMemberId);
			if (ContainerTool.isEmpty(existInfo)) {
				bean.putSysEnum(CodeEnum.IBS_404_DATA);
				bean.putEnum(CodeEnum.CODE_404);
				return bean;
			}
			String clientCode = existInfo.remove("CLIENT_CODE_").toString();

			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
			content.put("BET_MERGER_", betMerger);
			content.put("BET_RATE_T_", betRateTh);
			content.put("BLAST_STOP_", blastStop);
			content.put("METHOD_", IbsMethodEnum.SET_HANDICAP.name());
			String eventId = EventThreadDefine.saveMemberConfigSetEvent(content, new Date());
			content.put("EVENT_ID_", eventId);

			RabbitMqTool.sendMember(content.toString(), clientCode, "set");

			//添加盘口会员日志信息
			saveHmLog();
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "保存会员设置信息错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}

	private void profitVr(IbspHmSet hmSet) throws SQLException {
		IbspProfitVrService profitVrService = new IbspProfitVrService();
		String profitVrId = profitVrService.findIdByHmId(handicapMemberId);
		if (StringTool.isEmpty(profitVrId)) {
			return;
		}
		profitVrService.update(hmSet, profitVrId, this.getClass().getName());
	}

	private void profit(IbspHmSet hmSet) throws SQLException {
		IbspProfitService profitService = new IbspProfitService();
		String profitId = profitService.findByHmId(handicapMemberId);
		if (StringTool.isEmpty(profitId)) {
			return;
		}
		profitService.update(hmSet, profitId, this.getClass().getName());
	}

	private void saveHmLog() throws Exception {
		IbspLogHm logHm = new IbspLogHm();
		logHm.setHandicapMemberId(handicapMemberId);
		logHm.setAppUserId(appUserId);
		logHm.setHandleType("UPDATE");
		logHm.setHandleContent("BET_RATE_:".concat(betRate).concat(",PROFIT_LIMIT_MAX_:").concat(profitLimitMax)
				.concat(",PROFIT_LIMIT_MIN_:").concat(profitLimitMin).concat(",LOSS_LIMIT_MIN_:").concat(lossLimitMin)
				.concat(",RESET_TYPE_:").concat(resetType).concat(",RESET_PROFIT_MAX_:").concat(resetProfitMax)
				.concat(",RESET_LOSS_MIN_:").concat(resetLossMin).concat(",BET_MERGER_:").concat(betMerger).concat(",MODE_CUTOVER_STATE_:")
		.concat(modeCutoverState).concat(",CUTOVER_PROFIT_:").concat(cutoverProfit).concat(",CUTOVER_LOSS_:").concat(cutoverLoss));
		logHm.setCreateTime(new Date());
		logHm.setCreateTimeLong(System.currentTimeMillis());
		logHm.setUpdateTimeLong(System.currentTimeMillis());
		logHm.setState(IbsStateEnum.OPEN.name());
		logHm.setDesc(this.getClass().getName());
		new IbspLogHmService().save(logHm);
	}

	private boolean valiParameters() {
		handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		betRate = dataMap.getOrDefault("BET_RATE_", "100").toString();
		profitLimitMax = dataMap.getOrDefault("PROFIT_LIMIT_MAX_", "").toString();
		profitLimitMin = dataMap.getOrDefault("PROFIT_LIMIT_MIN_", "").toString();
		lossLimitMin = dataMap.getOrDefault("LOSS_LIMIT_MIN_", "").toString();

		resetType = dataMap.getOrDefault("RESET_TYPE_", "").toString();
		resetProfitMax = dataMap.getOrDefault("RESET_PROFIT_MAX_", "").toString();
		resetLossMin = dataMap.getOrDefault("RESET_LOSS_MIN_", "").toString();

		modeCutoverState = dataMap.getOrDefault("MODE_CUTOVER_STATE_", "").toString();
		cutoverProfit = dataMap.getOrDefault("CUTOVER_PROFIT_", "").toString();
		cutoverLoss = dataMap.getOrDefault("CUTOVER_LOSS_", "").toString();

		blastStop = dataMap.getOrDefault("BLAST_STOP_", "").toString();
		betMerger = dataMap.getOrDefault("BET_MERGER_", "").toString();

		if (StringTool.isEmpty(handicapMemberId, betRate, profitLimitMax, profitLimitMin, lossLimitMin)) {
			return true;
		}

		if (!IbsStateEnum.resetType(resetType)) {
			return true;
		}

		return !IbsStateEnum.stateType(blastStop)||!IbsStateEnum.stateType(modeCutoverState)||
				!IbsStateEnum.stateType(betMerger);
	}
}
