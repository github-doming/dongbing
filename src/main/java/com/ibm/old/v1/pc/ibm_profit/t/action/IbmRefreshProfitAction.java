package com.ibm.old.v1.pc.ibm_profit.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.service.IbmProfitVrTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import com.ibm.old.v1.pc.ibm_profit.t.service.IbmPcProfitTService;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 更新盈利信息
 * @Author: wck
 * @Date: 2019-04-10 15:44
 * @Email: 810160078@qq.com
 * @Version: v1.0
 */
public class IbmRefreshProfitAction extends BaseAppAction {

    @Override
    public String run() throws Exception {
        JsonResultBeanPlus jrb = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if(!threadJrb.isSuccess()){
            return returnJson(threadJrb);
        }

        if(appUserT == null){
            jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
            jrb.putSysEnum(IbmCodeEnum.CODE_401);
            return returnJson(jrb);
        }

        String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");

        if(StringTool.isEmpty(handicapMemberId)){
            jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
            jrb.putSysEnum(IbmCodeEnum.CODE_401);
            return returnJson(jrb);
        }

        try{
            IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
            boolean flag = handicapMemberTService.hmIsExist(handicapMemberId);
            if(!flag){
                jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                jrb.putSysEnum(IbmCodeEnum.CODE_404);
                return returnJson(jrb);
            }

            Map<String,Object> data = new HashMap<>(2);

            // 真实投注结果
            IbmPcProfitTService ibmProfitTService = new IbmPcProfitTService();
            Map<String, Object> realBetProfit = ibmProfitTService.findProfit(handicapMemberId);
            data.put("realBetProfit",realBetProfit);

            // 模拟投注结果
            IbmProfitVrTService ibmProfitVrTService = new IbmProfitVrTService();
            Map<String, Object> vrBetProfit = ibmProfitVrTService.findProfitVr(handicapMemberId);
            data.put("vrBetProfit",vrBetProfit);

            jrb.setData(data);
            jrb.success();
        } catch (Exception e) {
            log.error(IbmConfig.LOG_SIGN+"刷新盈利信息失败",e);
            throw e;
        }
        return returnJson(jrb);
    }
}
