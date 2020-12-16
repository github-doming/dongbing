package com.ibm.old.v1.pc.ibm_handicap_member.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_hm_info.t.service.IbmHmInfoTService;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.service.IbmProfitVrTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynQueryAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import com.ibm.old.v1.pc.ibm_profit.t.service.IbmPcProfitTService;
import net.sf.json.JSONObject;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 刷新盘口会员信息
 * @Author: wck
 * @Date: 2019-04-17 17:28
 * @Email: 810160078@qq.com
 * @Version: v1.0
 */
@ActionMapping(name = "refreshMemberInfo", value = "/ibm/pc/ibm_handicap_member/refreshMemberInfo.dm")
public class RefreshMemberInfoAction extends BaseAsynQueryAction {

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
            //获取盘口会员信息
            IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
            JSONObject memberInfoJson = handicapMemberTService.findMemberInfo(handicapMemberId);
            if(ContainerTool.isEmpty(memberInfoJson)){
                jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
                jrb.putSysEnum(IbmCodeEnum.CODE_404);
                return this.returnJson(jrb);
            }

            boolean flag = handicapMemberTService.hmIsExist(handicapMemberId);
            if(!flag){
                jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                jrb.putSysEnum(IbmCodeEnum.CODE_404);
                return returnJson(jrb);
            }
            Map<String,Object> data = new HashMap<>(6);



            IbmHmInfoTService hmInfoTService=new IbmHmInfoTService();
            hmInfoTService.findByHm(handicapMemberId,data,this.getClass().getName());


            // 真实投注结果
            IbmPcProfitTService ibmProfitTService = new IbmPcProfitTService();
            Map<String, Object> realBetProfit = ibmProfitTService.findProfit(handicapMemberId);


            // 模拟投注结果
            IbmProfitVrTService ibmProfitVrTService = new IbmProfitVrTService();
            Map<String, Object> vrBetProfit = ibmProfitVrTService.findProfitVr(handicapMemberId);



            data.put("vrBetProfit",vrBetProfit);
            data.put("realBetProfit",realBetProfit);
            data.put("memberInfoJson",memberInfoJson);

            jrb.setData(data);
            jrb.success();
        } catch (Exception e) {
            log.error(IbmConfig.LOG_SIGN+"刷新盘口用户信息失败",e);
            throw e;
        }
        return returnJson(jrb);

    }
}
