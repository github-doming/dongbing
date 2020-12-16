package com.ibm.old.v1.pc.ibm_hm_set.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_log_handicap_member.t.entity.IbmLogHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_log_handicap_member.t.service.IbmLogHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.entity.IbmProfitPlanT;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.cloud.ibm_profit_plan_vr.t.entity.IbmProfitPlanVrT;
import com.ibm.old.v1.cloud.ibm_profit_plan_vr.t.service.IbmProfitPlanVrTService;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.service.IbmProfitVrTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import com.ibm.old.v1.pc.ibm_hm_set.t.service.IbmPcHmSetTService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zjj
 * @Description: 会员盘口设置
 * @ClassName: handicapMemberSetAction
 * @date 2018年10月20日 下午2:24:20
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class HandicapMemberSetAction extends BaseAppAction {
	private JsonResultBeanPlus jrb = new JsonResultBeanPlus();
	private String handicapMemberId;
	private String betRecordRows;
	private String betRate;
	private String profitLimitMax;
	private String profitLimitMin;
	private String lossLimitMin;
	private String resetType;
	private String resetProfitMax;
	private String resetLossMin;
	private String blastStop;
	private String betMerger;

	@Override public String run() throws Exception {
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		if (!validation()) {
			return this.returnJson(jrb);
		}
		try {
			IbmPcHandicapMemberTService ibmHandicapMemberTService = new IbmPcHandicapMemberTService();
			Map<String,Object> handicapMemberMap = ibmHandicapMemberTService.findHandicapIdAndLoginState(handicapMemberId);
			// 判断盘口会员是否存在
			if (ContainerTool.isEmpty(handicapMemberMap)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			Date nowTime = new Date();
			// 根据盘口会员id获取盘口用户设置
			IbmPcHmSetTService ibmHmSetTService = new IbmPcHmSetTService();
			IbmHmSetT hmSetT = ibmHmSetTService.findByHandicapMemberId(handicapMemberId);

			// 判断盘口会员设置是否存在
			if (hmSetT == null) {
				if (StringTool.isEmpty(betRecordRows, betRate, resetType, blastStop, betMerger)) {
					jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
					jrb.putSysEnum(IbmCodeEnum.CODE_404);
					return this.returnJson(jrb);
				}
				hmSetT = new IbmHmSetT();
				hmSetT.setAppUserId(appUserId);
				hmSetT.setHandicapMemberId(handicapMemberId);
				hmSetT.setHandicapId(handicapMemberMap.get("HANDICAP_ID_"));
				hmSetT.setBetRecordRows(Integer.parseInt(betRecordRows));
				hmSetT.setBetRateT(NumberTool.notEmptyLongValue(betRate));
				hmSetT.setProfitLimitMaxT(NumberTool.notEmptyLongValue(profitLimitMax));
				hmSetT.setProfitLimitMinT(NumberTool.notEmptyLongValue(profitLimitMin));
				hmSetT.setLossLimitMinT(NumberTool.notEmptyLongValue(lossLimitMin));
				hmSetT.setResetType(resetType);
				hmSetT.setResetProfitMaxT(NumberTool.notEmptyLongValue(resetProfitMax));
				hmSetT.setResetLossMinT(NumberTool.notEmptyLongValue(resetLossMin));
				hmSetT.setBlastStop(blastStop);
				hmSetT.setBetMerger(betMerger);
				hmSetT.setCreateTime(nowTime);
				hmSetT.setCreateTimeLong(nowTime.getTime());
				hmSetT.setState(IbmStateEnum.OPEN.name());
				ibmHmSetTService.save(hmSetT);
			} else {
				hmSetT.setBetRecordRows(Integer.parseInt(betRecordRows));
				hmSetT.setBetRateT(NumberTool.notEmptyLongValue(betRate));
				hmSetT.setProfitLimitMaxT(NumberTool.notEmptyLongValue(profitLimitMax));
				hmSetT.setProfitLimitMinT(NumberTool.notEmptyLongValue(profitLimitMin));
				hmSetT.setLossLimitMinT(NumberTool.notEmptyLongValue(lossLimitMin));
				hmSetT.setResetType(resetType);
				hmSetT.setResetProfitMaxT(NumberTool.notEmptyLongValue(resetProfitMax));
				hmSetT.setResetLossMinT(NumberTool.notEmptyLongValue(resetLossMin));
				hmSetT.setBlastStop(blastStop);
				hmSetT.setBetMerger(betMerger);
				hmSetT.setUpdateTime(nowTime);
				hmSetT.setUpdateTimeLong(nowTime.getTime());
				ibmHmSetTService.update(hmSetT);
			}
			//设置盘口会员方案止盈止损
			profitPlan(hmSetT, handicapMemberId);
			profitPlanVr(hmSetT, handicapMemberId);

			//设置盘口会员止盈止损
			profit(hmSetT,handicapMemberId);
			profitVr(hmSetT,handicapMemberId);

			saveLogInfo(hmSetT, handicapMemberMap.get("HANDICAP_ID_").toString(), nowTime);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			throw e;
		}
		return this.returnJson(jrb);
	}
	private void profitVr(IbmHmSetT hmSetT, String handicapMemberId) throws SQLException {
		IbmProfitVrTService profitVrTService=new IbmProfitVrTService();
		String profitVrId = profitVrTService.findIdByHmId(handicapMemberId);

		if (StringTool.isEmpty(profitVrId)) {
			return ;
		}
		profitVrTService.update(profitVrId,hmSetT,this.getClass().getName());
	}
	private void profit(IbmHmSetT hmSetT, String handicapMemberId) throws SQLException {
		IbmProfitTService profitTService=new IbmProfitTService();
		String profitId = profitTService.findIdByHmId(handicapMemberId);

		if (StringTool.isEmpty(profitId)) {
			return ;
		}
		profitTService.update(profitId,hmSetT,this.getClass().getName());
	}

	private void profitPlanVr(IbmHmSetT hmSetT, String handicapMemberId) throws Exception {
		IbmProfitPlanVrTService profitPlanVrTService=new IbmProfitPlanVrTService();
		List<IbmProfitPlanVrT> list=profitPlanVrTService.findByHmId(handicapMemberId);
		if(ContainerTool.isEmpty(list)){
			return ;
		}
		if("2".equals(hmSetT.getResetType())){
			for (IbmProfitPlanVrT profitPlanVrT : list) {
				profitPlanVrT.setProfitLimitMaxT(hmSetT.getResetProfitMaxT());
				profitPlanVrT.setLossLimitMinT(hmSetT.getResetLossMinT());
				profitPlanVrTService.update(profitPlanVrT);
			}
		}else{
			IbmPlanUserTService planUserTService = new IbmPlanUserTService();
			for (IbmProfitPlanVrT profitPlanVrT : list) {
				Map<String, Object> limitInfo=planUserTService.getLimitInfo(handicapMemberId,profitPlanVrT.getPlanId());
				profitPlanVrT.setProfitLimitMaxT(limitInfo.get("PROFIT_LIMIT_MAX_T_"));
				profitPlanVrT.setLossLimitMinT(limitInfo.get("LOSS_LIMIT_MIN_T_"));
				profitPlanVrTService.update(profitPlanVrT);
			}
		}
	}

	private void profitPlan(IbmHmSetT hmSetT, String handicapMemberId) throws Exception {
		IbmProfitPlanTService profitPlanTService=new IbmProfitPlanTService();
		List<IbmProfitPlanT> list=profitPlanTService.findByHmId(handicapMemberId);
		if(ContainerTool.isEmpty(list)){
			return ;
		}
		if("2".equals(hmSetT.getResetType())){
			for (IbmProfitPlanT profitPlanT : list) {
				profitPlanT.setProfitLimitMaxT(hmSetT.getResetProfitMaxT());
				profitPlanT.setLossLimitMinT(hmSetT.getResetLossMinT());
				profitPlanTService.update(profitPlanT);
			}
		}else{
			IbmPlanUserTService planUserTService = new IbmPlanUserTService();
			for (IbmProfitPlanT profitPlanT : list) {
				Map<String, Object> limitInfo=planUserTService.getLimitInfo(handicapMemberId,profitPlanT.getPlanId());
				profitPlanT.setProfitLimitMaxT(limitInfo.get("PROFIT_LIMIT_MAX_T_"));
				profitPlanT.setLossLimitMinT(limitInfo.get("LOSS_LIMIT_MIN_T_"));
				profitPlanTService.update(profitPlanT);
			}
		}
	}

	/**
	 * 写入日志
	 *
	 * @param ibmHmSetT  盘口设置信息
	 * @param handicapId 盘口id0
	 * @param nowTime    当前时间
	 */
	private void saveLogInfo(IbmHmSetT ibmHmSetT, String handicapId, Date nowTime) throws Exception {
		IbmLogHandicapMemberT ibmLogHandicapMemberT = new IbmLogHandicapMemberT();
		IbmLogHandicapMemberTService ibmLogHandicapMemberTService = new IbmLogHandicapMemberTService();
		ibmLogHandicapMemberT.setHandicapMemberId(handicapMemberId);
		ibmLogHandicapMemberT.setHandicapId(handicapId);
		ibmLogHandicapMemberT.setAppUserId(appUserT.getAppUserId());
		ibmLogHandicapMemberT.setHandleType("HANDICAPSET");
		ibmLogHandicapMemberT.setCreateTime(nowTime);
		ibmLogHandicapMemberT.setCreateTimeLong(nowTime.getTime());
		ibmLogHandicapMemberT.setState(IbmStateEnum.OPEN.name());

		if (StringTool.isEmpty(betRecordRows, betRate, profitLimitMax, profitLimitMin, lossLimitMin, resetType,
				resetProfitMax, resetLossMin, blastStop, betMerger)) {
			ibmLogHandicapMemberT.setDesc("LANDED_TIME:" + ibmHmSetT.getLandedTime());
		} else {
			ibmLogHandicapMemberT.setDesc(
					"LANDED_TIME:" + ibmHmSetT.getLandedTime() + ",BET_RECORD_ROWS_:" + ibmHmSetT.getBetRecordRows()
							.toString() + ",BET_RATE_T_:" + ibmHmSetT.getBetRateT().toString() + ",PROFIT_LIMIT_MAX_T_:"
							+ ibmHmSetT.getProfitLimitMaxT() + ",PROFIT_LIMIT_MIN_T_:" + ibmHmSetT.getProfitLimitMinT()
							+ ",LOSS_LIMIT_MIN_T_:" + ibmHmSetT.getLossLimitMinT() + ",RESET_TYPE_:" + ibmHmSetT
							.getResetType() + ",RESET_PROFIT_MAX_T_:" + ibmHmSetT.getResetProfitMaxT()
							+ ",RESET_LOSS_MIN_T_:" + ibmHmSetT.getResetLossMinT() + ",BLAST_STOP_:" + ibmHmSetT
							.getBlastStop() + ",BET_MERGER_:" + ibmHmSetT.getBlastStop());
		}

		ibmLogHandicapMemberTService.save(ibmLogHandicapMemberT);
	}

	/**
	 * 校验参数
	 */
	private boolean validation() {
		// 盘口会员id
		handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		// 投注记录保存行数
		betRecordRows = BeanThreadLocal.find(dataMap.get("BET_RECORD_ROWS_"), "100");
		// 投注比例
		betRate = BeanThreadLocal.find(dataMap.get("BET_RATE_T_"), "100");
		// 止盈上限
		profitLimitMax = BeanThreadLocal.find(dataMap.get("PROFIT_LIMIT_MAX_T_"), "");
		// 止盈下限
		profitLimitMin = BeanThreadLocal.find(dataMap.get("PROFIT_LIMIT_MIN_T_"), "");
		// 止损下限
		lossLimitMin = BeanThreadLocal.find(dataMap.get("LOSS_LIMIT_MIN_T_"), "");
		// 重置类型
		resetType = BeanThreadLocal.find(dataMap.get("RESET_TYPE_"), "1");
		// 重置盈利上限
		resetProfitMax = BeanThreadLocal.find(dataMap.get("RESET_PROFIT_MAX_T_"), "");
		// 重置亏损下限
		resetLossMin = BeanThreadLocal.find(dataMap.get("RESET_LOSS_MIN_T_"), "");
		// 炸停止
		blastStop = BeanThreadLocal.find(dataMap.get("BLAST_STOP_"), "");
		// 投注合并
		betMerger = BeanThreadLocal.find(dataMap.get("BET_MERGER_"), "");

		// 判断盘口会员id是否为空
		if (StringTool.isEmpty(handicapMemberId)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}
		//炸停止和投注合并状态校验
		if (StringTool.notEmpty(betMerger, blastStop)) {
			if (!IbmStateEnum.hmSetState(betMerger) || !IbmStateEnum.hmSetState(blastStop)) {
				jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_STATE);
				jrb.putSysEnum(IbmCodeEnum.CODE_401);
				return false;
			}
		}
		return true;
	}

}
