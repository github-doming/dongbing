package com.ibm.old.v1.cloud.ibm_handicap_member.t.thread;

import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 初始化盘口会员相关信息
 * @Author: wck
 * @Date: 2019-05-20 17:44
 * @Email: 810160078@qq.com
 * @Version: v1.0
 */
public class InitHandicapMemberThread extends BaseCommThread {

    private String handicapMemberId;

    public InitHandicapMemberThread(String handicapMemberId) {
        this.handicapMemberId = handicapMemberId;
    }

    @Override
    public String execute(String ignore) throws Exception {
        //获取该盘口会员所有方案
        IbmPlanUserTService planUserTService = new IbmPlanUserTService();
        List<Map<String, Object>> plans = planUserTService.findPlanIdByHmId(handicapMemberId);
        if(ContainerTool.isEmpty(plans)){
            log.error("盘口会员【"+handicapMemberId+"】找不到方案");
            return null;
        }
        IbmPlanHmTService planHmTService = new IbmPlanHmTService();


        //初始化盘口会员方案
        planHmTService.initPlanHm(handicapMemberId,plans);
        return null;
    }

}
