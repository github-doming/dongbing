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
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import com.ibm.follow.servlet.cloud.ibm_log_hm.entity.IbmLogHm;
import com.ibm.follow.servlet.cloud.ibm_log_hm.service.IbmLogHmService;
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
@ActionMapping(value = "/ibm/app/member/hmBetMerger", method = HttpConfig.Method.GET)
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
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
        String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        String betMerger = dataMap.getOrDefault("BET_MERGER_", "").toString();

        if (StringTool.isEmpty(handicapMemberId,betMerger)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        Date nowTime = new Date();
        String desc = this.getClass().getName().concat("修改投注合并");
        try {
            //用户是否登录
            if (!IbmStateEnum.LOGIN.equal(new IbmHmInfoService().findLoginState(handicapMemberId))) {
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }

            IbmHmSetService ibmHmSetService = new IbmHmSetService();
            if (!ibmHmSetService.updateEntity(handicapMemberId,betMerger,nowTime,desc)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return super.returnJson(bean);
            }
			//添加盘口会员日志信息
			saveHmLog(handicapMemberId,betMerger,nowTime);

            //写入客户设置事件
            JSONObject content = new JSONObject();
            content.put("BET_MERGER_", betMerger);
            content.put("BET_RATE_T_", ibmHmSetService.findInfo(handicapMemberId).get("BET_RATE_T_"));
            content.put("SET_ITEM_", IbmMethodEnum.SET_HANDICAP.name());
            content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());

			boolean flag=EventThreadDefine.sendClientConfig(content,handicapMemberId, IbmTypeEnum.MEMBER,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("保存会员设置信息错误"),e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    private void saveHmLog(String handicapMemberId,String betMerger,Date nowTime) throws Exception {
        IbmLogHm logHm = new IbmLogHm();
        logHm.setHandicapMemberId(handicapMemberId);
        logHm.setAppUserId(appUserId);
        logHm.setHandleType("UPDATE");
        logHm.setHandleContent("BET_MERGER_:".concat(betMerger));
        logHm.setCreateTime(nowTime);
        logHm.setCreateTimeLong(System.currentTimeMillis());
        logHm.setUpdateTimeLong(System.currentTimeMillis());
        logHm.setState(IbmStateEnum.OPEN.name());
        logHm.setDesc(this.getClass().getName());
        new IbmLogHmService().save(logHm);
    }
}
