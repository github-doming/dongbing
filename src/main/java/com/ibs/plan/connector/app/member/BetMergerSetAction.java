package com.ibs.plan.connector.app.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import com.ibs.plan.module.cloud.ibsp_log_hm.entity.IbspLogHm;
import com.ibs.plan.module.cloud.ibsp_log_hm.service.IbspLogHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 修改投注合并
 * @Author: null
 * @Date: 2019-11-23 18:19
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/member/hmBetMerger", method = HttpConfig.Method.GET)
public class BetMergerSetAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if(super.denyTime()){
            bean.putEnum(CodeEnum.IBS_503_TIME);
            bean.putSysEnum(CodeEnum.CODE_503);
            return bean;
        }
        String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        String betMerger = dataMap.getOrDefault("BET_MERGER_", "").toString();

        if (StringTool.isEmpty(handicapMemberId,betMerger)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            bean.putSysEnum(CodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        Date nowTime = new Date();
        String desc = this.getClass().getName().concat("修改投注合并");
        try {
            //用户是否登录
            if (!IbsStateEnum.LOGIN.equal(new IbspHmInfoService().findLoginState(handicapMemberId))) {
                bean.putEnum(CodeEnum.IBS_403_LOGIN);
                bean.putSysEnum(CodeEnum.CODE_403);
                return bean;
            }

            IbspHmSetService ibmHmSetService = new IbspHmSetService();
            if (!ibmHmSetService.updateEntity(handicapMemberId,betMerger,"",nowTime,desc)) {
                bean.putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
                bean.putSysEnum(CodeEnum.CODE_404);
                return super.returnJson(bean);
            }

            //写入客户设置事件
            JSONObject content = new JSONObject();
            content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
            content.put("BET_MERGER_", betMerger);
            content.put("BET_RATE_T_", ibmHmSetService.findInfo(handicapMemberId).get("BET_RATE_T_"));
            content.put("SET_ITEM_", IbsMethodEnum.SET_HANDICAP.name());
            content.put("METHOD_", IbsMethodEnum.MEMBER_SET.name());
            EventThreadDefine.saveMemberConfigSetEvent(content,nowTime, desc,new IbspEventConfigSetService());

            //添加盘口会员日志信息
            saveHmLog(handicapMemberId,betMerger,nowTime);
            bean.success();
        } catch (Exception e) {
            log.error(IbsConfig.LOG_SIGN.concat("保存会员设置信息错误"));
            throw e;
        }
        return bean;
    }
    private void saveHmLog(String handicapMemberId,String betMerger,Date nowTime) throws Exception {
        IbspLogHm logHm = new IbspLogHm();
        logHm.setHandicapMemberId(handicapMemberId);
        logHm.setAppUserId(appUserId);
        logHm.setHandleType("UPDATE");
        logHm.setHandleContent("BET_MERGER_:".concat(betMerger));
        logHm.setCreateTime(nowTime);
        logHm.setCreateTimeLong(System.currentTimeMillis());
        logHm.setUpdateTimeLong(System.currentTimeMillis());
        logHm.setState(IbsStateEnum.OPEN.name());
        logHm.setDesc(this.getClass().getName());
        new IbspLogHmService().save(logHm);
    }
}
