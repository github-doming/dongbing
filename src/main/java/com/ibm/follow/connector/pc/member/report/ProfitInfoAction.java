package com.ibm.follow.connector.pc.member.report;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period.service.IbmHmProfitPeriodService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 盈亏详情信息
 * @Author: null
 * @Date: 2019-12-05 15:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/profitInfo", method = HttpConfig.Method.GET)
public class ProfitInfoAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        String pageIndex = dataMap.getOrDefault("pageIndex", "1").toString();
        String pageSize = dataMap.getOrDefault("pageSize", "10").toString();
        //校验
        if (StringTool.isEmpty(gameCode, handicapMemberId)) {
            bean.putEnum(IbmCodeEnum.IBM_404_GAME);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return bean;
        }
        try {
            IbmHmProfitPeriodService profitPeriodService = new IbmHmProfitPeriodService();
            PageCoreBean profitInfo = profitPeriodService.findProfitInfo(handicapMemberId, gameCode,
                    Integer.parseInt(pageIndex), Integer.parseInt(pageSize));

            bean.success(profitInfo);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("获取有投注内容的会员列表信息-出错"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
