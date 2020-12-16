package com.ibm.follow.connector.pc.member.report;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period.service.IbmHmProfitPeriodService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 获取报表信息
 * @Author: null
 * @Date: 2019-12-05 16:03
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/reportInfo", method = HttpConfig.Method.GET)
public class ReportInfoAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try {
            Map<String,Object> data=new HashMap<>(2);

            IbmHmProfitPeriodService profitPeriodService=new IbmHmProfitPeriodService();
            List<Map<String,Object>> memberInfo=profitPeriodService.findMemberInfo(appUserId);
            data.put("memberInfo",memberInfo);

            IbmGameService gameService=new IbmGameService();
            List<Map<String, Object>> gameInfo=gameService.findGameInfo();
            data.put("gameInfo",gameInfo);

            bean.success(data);
        } catch (SQLException e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("获取报表信息-出错"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
