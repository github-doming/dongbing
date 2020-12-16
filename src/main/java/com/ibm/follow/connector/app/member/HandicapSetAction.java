package com.ibm.follow.connector.app.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_vr.service.IbmHmProfitVrService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.entity.IbmHmSet;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import com.ibm.follow.servlet.cloud.ibm_log_hm.entity.IbmLogHm;
import com.ibm.follow.servlet.cloud.ibm_log_hm.service.IbmLogHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.Date;

/**
 * @Description: 保存会员设置信息
 * @Author: null
 * @Date: 2019-11-23 17:31
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/member/hmSet", method = HttpConfig.Method.POST)
public class HandicapSetAction extends CommCoreAction {
    private String handicapMemberId;
    private String betRate;
    private String profitLimitMax;
    private String profitLimitMin;
    private String lossLimitMin;

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
        if (valiParameters()) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        String desc = this.getClass().getName().concat("修改盘口设置");
        try{
            //用户是否登录
            if (!IbmStateEnum.LOGIN.equal(new IbmHmInfoService().findLoginState(handicapMemberId))) {
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            IbmHmSetService hmSetService = new IbmHmSetService();
            IbmHmSet hmSet = hmSetService.findEntityByHmId(handicapMemberId);
            if (hmSet == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return super.returnJson(bean);
            }
            Integer betRateTh = NumberTool.intValueT(betRate) / 100;
            hmSet.setBetRateT(betRateTh);
            hmSet.setProfitLimitMax(Integer.parseInt(profitLimitMax));
            hmSet.setProfitLimitMin(Integer.parseInt(profitLimitMin));
            hmSet.setLossLimitMin(Integer.parseInt(lossLimitMin));
            hmSet.setUpdateTimeLong(System.currentTimeMillis());
            hmSet.setDesc(desc);
            hmSetService.update(hmSet);

            //设置盘口会员止盈止损
            profit(hmSet);
            profitVr(hmSet);

            //写入客户设置事件
            JSONObject content = new JSONObject();
            content.put("BET_MERGER_", hmSet.getBetMerger());
            content.put("BET_RATE_T_", betRateTh);
            content.put("SET_ITEM_", IbmMethodEnum.SET_HANDICAP.name());
            content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());

			boolean flag=EventThreadDefine.sendClientConfig(content,handicapMemberId, IbmTypeEnum.MEMBER,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
            //添加盘口会员日志信息
            saveHmLog(hmSet);
            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("保存会员设置信息错误"),e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    private void saveHmLog(IbmHmSet hmSet) throws Exception {
        IbmLogHm logHm = new IbmLogHm();
        logHm.setHandicapMemberId(hmSet.getHandicapMemberId());
        logHm.setAppUserId(appUserId);
        logHm.setHandleType("UPDATE");
        logHm.setHandleContent("BET_RATE_:".concat(betRate)
                .concat(",PROFIT_LIMIT_MAX_:").concat(profitLimitMax).concat(",PROFIT_LIMIT_MIN_:")
                .concat(profitLimitMin).concat(",LOSS_LIMIT_MIN_:").concat(lossLimitMin));
        logHm.setCreateTime(new Date());
        logHm.setCreateTimeLong(System.currentTimeMillis());
        logHm.setUpdateTimeLong(System.currentTimeMillis());
        logHm.setState(IbmStateEnum.OPEN.name());
        logHm.setDesc(this.getClass().getName());
        new IbmLogHmService().save(logHm);
    }
    private void profitVr(IbmHmSet hmSet) throws SQLException {
        IbmHmProfitVrService hmProfitVrService = new IbmHmProfitVrService();
        String profitVrId = hmProfitVrService.findByHmId(hmSet.getHandicapMemberId());
        if (StringTool.isEmpty(profitVrId)) {
            return;
        }
        hmProfitVrService.update(hmSet, profitVrId, this.getClass().getName());
    }
    private void profit(IbmHmSet hmSet) throws SQLException {
        IbmHmProfitService hmProfitService = new IbmHmProfitService();
        String profitId = hmProfitService.findByHmId(hmSet.getHandicapMemberId());
        if (StringTool.isEmpty(profitId)) {
            return;
        }
        hmProfitService.update(hmSet, profitId, this.getClass().getName());
    }

    private boolean valiParameters() {
        handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        betRate = dataMap.getOrDefault("BET_RATE_", "100").toString();
        profitLimitMax = dataMap.getOrDefault("PROFIT_LIMIT_MAX_", "").toString();
        profitLimitMin = dataMap.getOrDefault("PROFIT_LIMIT_MIN_", "").toString();
        lossLimitMin = dataMap.getOrDefault("LOSS_LIMIT_MIN_", "").toString();

        return StringTool
                .isEmpty(handicapMemberId, betRate, profitLimitMax, profitLimitMin, lossLimitMin);


    }
}
