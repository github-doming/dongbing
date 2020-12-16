package com.ibm.old.v1.app.ibm_plan_user.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemMain;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynCommAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.tools.IbmCmdTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

/**
 * @Description: 用户方案信息修改
 * @Author: Dongming
 * @Date: 2019-08-02 15:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(name = "planUserShow", value = "/ibm/app/ibm_plan_user/edit.dm")
public class IbmPlanUserEditAction
        extends BaseAsynCommAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus jrb = new JsonResultBeanPlus();
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
        String planCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("PLAN_CODE_"), "");
        String planItemTableId = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("ID_"), "");

        if (StringTool.isEmpty(planCode, cmd,planItemTableId)) {
            jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
            jrb.putSysEnum(IbmCodeEnum.CODE_401);
            return this.returnJson(jrb);
        }

        try {
            PlanTool.Code plan = PlanTool.Code.valueOf(planCode);

            IbmPlanItemService planItemService = new IbmPlanItemService();
            IbmPlanItemMain planItem = planItemService.findPlanItem(plan, planItemTableId);

            Boolean updateType = null;
            switch (IbmCmdTool.Plan.valueOf(cmd)) {
                case PROFIT_LIMIT:
                    if (!ContainerTool.containsKey(dataMap, "PROFIT_LIMIT_MAX_", "LOSS_LIMIT_MIN_")) {
                        jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
                        jrb.putSysEnum(IbmCodeEnum.CODE_401);
                        return jrb;
                    }
                    // 止盈上限
                    long profitLimitMaxTh = NumberTool.longValueT(dataMap.getOrDefault("PROFIT_LIMIT_MAX_", "0"));
                    // 止损下限
                    long lossLimitMinTh = NumberTool.longValueT(dataMap.getOrDefault("LOSS_LIMIT_MIN_", "0"));
                    planItem.setProfitLimitMaxT(profitLimitMaxTh);
                    planItem.setLossLimitMinT(lossLimitMinTh);
                    updateType = planItemService.update(planItem);
                    break;
                case FUNDS_BET_RULE:
                    if (!ContainerTool.containsKey(dataMap, "FUNDS_LIST_", "FOLLOW_PERIOD_",
                            "BET_MODE_", "FUND_SWAP_MODE_", "PERIOD_ROLL_MODE_")) {
                        jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
                        jrb.putSysEnum(IbmCodeEnum.CODE_401);
                        return jrb;
                    }
                    String fundsList = dataMap.get("FUNDS_LIST_").toString();
                    int followPeriod = NumberTool.getInteger(dataMap.get("FOLLOW_PERIOD_"));
                    String betMode = dataMap.get("BET_MODE_").toString();
                    String fundSwapMode = dataMap.get("FUND_SWAP_MODE_").toString();
                    String periodRollMode = dataMap.get("PERIOD_ROLL_MODE_").toString();
                    planItem.setFundsList(fundsList);
                    planItem.setFollowPeriod(followPeriod);
                    planItem.setMonitorPeriod(dataMap.get("MONITOR_PERIOD_"));
                    planItem.setBetMode(betMode);
                    planItem.setFundSwapMode(fundSwapMode);
                    planItem.setPeriodRollMode(periodRollMode);
                    updateType = planItemService.update(planItem);
                    break;

                case BET_ITEM:
                    if (!ContainerTool.containsKey(dataMap, "PLAN_GROUP_DATA_")) {
                        jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
                        jrb.putSysEnum(IbmCodeEnum.CODE_401);
                        return jrb;

                    }
                    String planGroupData = dataMap.get("PLAN_GROUP_DATA_").toString();
                    planItem.setPlanGroupData(planGroupData);
                    updateType = planItemService.update(planItem);

                    break;

                default:
                    jrb.putEnum(IbmCodeEnum.IBM_404_CMD);
                    jrb.putSysEnum(IbmCodeEnum.CODE_404);
            }
            if (updateType != null) {
                if (updateType) {
                    jrb.success();
                } else {
                    jrb.putEnum(IbmCodeEnum.IBM_403_UPDATE);
                    jrb.putSysEnum(IbmCodeEnum.CODE_403);
                }
            }
        } catch (Exception e) {
            log.error(IbmConfig.LOG_SIGN + "打开用户方案列表错误", e);
            jrb.putEnum(IbmCodeEnum.IBM_500);
            jrb.putSysEnum(IbmCodeEnum.CODE_500);
            throw e;
        }
        return jrb;
    }
}
