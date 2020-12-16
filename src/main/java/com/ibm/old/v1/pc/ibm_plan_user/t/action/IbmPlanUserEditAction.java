package com.ibm.old.v1.pc.ibm_plan_user.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.entity.IbmLogPlanUserT;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.service.IbmLogPlanUserTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemMain;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.service.IbmProfitVrTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 用户方案详情信息修改
 * @Author: Dongming
 * @Date: 2019-01-17 10:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmPlanUserEditAction extends BaseAppAction {
	private String planCode;
	private String id;
	private String profitLimitMax;
	private String lossLimitMin;
	private String fundsList;
	private String followPeriod;
	private String monitorPeriod;
	private String betMode;
	private String fundSwapMode;
	private String periodRollMode;
	private JSONObject planGroupData;


	private JsonResultBeanPlus jrb;

	@Override
	public String run() throws Exception {
		jrb = new JsonResultBeanPlus();
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
		if (valiParameter()) {
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			long profitLimitMaxT = NumberTool.longValueT(profitLimitMax);
			long lossLimitMinT = NumberTool.longValueT(lossLimitMin);
			//通过方案code获取表名
			String tableName=PlanTool.Code.valueOf(planCode).getTableName();

			IbmPlanItemService planItemService = new IbmPlanItemService();
			planItemService.update(new IbmPlanItemMain(tableName, id, profitLimitMaxT, lossLimitMinT, fundsList,
					followPeriod, monitorPeriod, betMode, fundSwapMode, periodRollMode, planGroupData.toString()));

			IbmPlanUserTService planUserTService = new IbmPlanUserTService();
			planUserTService.update(profitLimitMaxT, lossLimitMinT,betMode,monitorPeriod,appUserId,id,this.getClass().getName());

			boolean flag;
			IbmProfitPlanTService profitPlanTService = new IbmProfitPlanTService();
			flag = profitPlanTService.updateLimit(profitLimitMaxT, lossLimitMinT,appUserId,id,tableName,this.getClass().getName());

			if(!flag){
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}

			IbmProfitVrTService profitVrTService = new IbmProfitVrTService();
			flag = profitVrTService.updateLimit(profitLimitMaxT, lossLimitMinT,appUserId,id,tableName,this.getClass().getName());

			if(!flag){
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}

			//同步用户的方案状态
			new IbmPlanHmTService().syncUserPlanState(appUserId,id,this.getClass().getName());

			Date nowTime =new Date();
			//用户方案操作记录日志
			IbmLogPlanUserT ibmLogPlanUserT = new IbmLogPlanUserT();
			IbmLogPlanUserTService ibmLogPlanUserTService = new IbmLogPlanUserTService();
			ibmLogPlanUserT.setPlanId(planUserTService.findPlanIdByTableId(id));
			ibmLogPlanUserT.setAppUserId(appUserT.getAppUserId());
			ibmLogPlanUserT.setGameId(planUserTService.findTablePlanId(id));
			ibmLogPlanUserT.setHandleType("REPLAYPLAN");
			ibmLogPlanUserT.setCreateTime(nowTime);
			ibmLogPlanUserT.setCreateTimeLong(nowTime.getTime());
			ibmLogPlanUserT.setState(IbmStateEnum.OPEN.name());
			ibmLogPlanUserT.setDesc(
					",betMode:" + betMode + ",followPeriod:" + followPeriod + "," +
							"monitorPeriod:" + monitorPeriod + ",fundSwapMode:" + fundSwapMode +
							",periodRollMode:" + periodRollMode + ",fundsList:" + fundsList +
							",profitLimitMax:" + profitLimitMax + ",lossLimitMin" + lossLimitMin +
							",planGroupData" + planGroupData.toString());

			ibmLogPlanUserTService.save(ibmLogPlanUserT);

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "修改用户方案详情错误", e);
			throw e;
		}
		return returnJson(jrb);
	}
	private boolean valiParameter() {
		// 方案code
		planCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("Code"), "");
		// 方案详情表主键
		id = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("ID_"), "");
		// 止盈上限
		profitLimitMax = BeanThreadLocal.find(dataMap.get("profitLimitMax"), "0");
		// 止损下限
		lossLimitMin = BeanThreadLocal.find(dataMap.get("lossLimitMin"), "0");
		// 资金列表
		fundsList = BeanThreadLocal.find(dataMap.get("fundsList"), "");
		// 跟进期数
		followPeriod = BeanThreadLocal.find(dataMap.get("followPeriod"), "1");
		// 监控次数
		monitorPeriod = BeanThreadLocal.find(dataMap.get("monitorPeriod"), "0");
		// 投注模式
		betMode = BeanThreadLocal.find(dataMap.get("betMode"), "");
		// 资金切换方式
		fundSwapMode = BeanThreadLocal.find(dataMap.get("fundSwapMode"), "");
		// 期期滚选项
		periodRollMode = BeanThreadLocal.find(dataMap.get("periodRollMode"), "");

		Object planGroupDataObj = dataMap.get("planGroupData");

		if (StringTool.isEmpty(planCode, id, fundsList, betMode, fundSwapMode,planGroupDataObj)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			return true;
		}

		// 方案组数据
		planGroupData = JSONObject.fromObject(planGroupDataObj);

		if (!IbmModeEnum.isBetMode(betMode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_BET_MODE);
			return true;
		}
		if (!IbmModeEnum.isFundSwapMode(fundSwapMode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FUND_SWAP_MODE);
			return true;
		}
		if (StringTool.notEmpty(periodRollMode)) {
			if (!IbmModeEnum.isPeriodRollMode(betMode, periodRollMode)) {
				jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FUND_PERIOD_ROLL);
				return true;
			}
		}
		return false;
	}

}
