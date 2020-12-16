package com.ibm.follow.connector.admin.manage3.sys;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.connector.admin.manage3.service.IbmSysService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @Description: 删除数据 物理删除
 * @Author: wwj
 * @Date: 2020/1/18 16:02
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/sys/cleanData", method = HttpConfig.Method.GET)

public class CleanDataAction extends CommAdminAction {
    /**
     * 执行方法
     *
     * @return 执行结果
     * @throws Exception 执行失败
     */
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        // day  按天 // 数据库读取
        // expires 过期用户（超过2个月未登陆的用户）
        // bet 投注类数据

        String type = request.getParameter("type");
        Calendar c = Calendar.getInstance();

        IbmSysService sysService = new IbmSysService();
        List<String> tableNames = new ArrayList<>();
        int num = 0;
        switch (type) {
            case "day":
                int day = 1;
                c.add(Calendar.DATE, -day);
                tableNames = Arrays.asList("ibm_event_client_close", "ibm_event_client_open", "ibm_event_config_info",
                        "ibm_event_config_set", "ibm_event_login", "ibm_event_login_vali", "ibm_ha_follow_period", "ibm_ha_member_bet_item",
                        "ibm_hm_bet_item", "ibm_hm_profit", "ibm_hm_profit_item", "ibm_hm_profit_period", "ibm_hm_profit_period_vr",
                        "ibm_hm_profit_vr");
                break;
            case "expires":
                c.add(Calendar.DATE, -30);
                List<String> userList = sysService.listUidsByExpireDataTime(c.getTime().getTime());

                if (userList != null&& !userList.isEmpty() &&userList.size()>0 ) {
                    IbmHaInfoService haInfoService = new IbmHaInfoService();
                    IbmHmInfoService hmInfoService = new IbmHmInfoService();
                    List<String> haIds = haInfoService.listHaIdByUserIds(userList);
                    if (haIds != null && !haIds.isEmpty() &&haIds.size()>0 ) {
                        num = sysService.cleanDataByHaId(Arrays.asList("ibm_client_ha", "ibm_ha_game_set", "ibm_ha_notice", "ibm_ha_info","ibm_ha_set", "ibm_handicap_agent_member"), haIds);
                    }
                    List<String> hmIds = hmInfoService.listHmIdByUserIds(userList);
                    if (hmIds != null&& !hmIds.isEmpty() &&hmIds.size()>0) {
                        num += sysService.cleanDataByHmId(Arrays.asList("ibm_handicap_member", "ibm_client_hm", "ibm_hm_game_set","ibm_hm_info", "ibm_hm_notice", "ibm_hm_set"), hmIds);
                    }
                }
                tableNames = Arrays.asList("app_account","app_user", "ibm_ha_user", "ibm_manage_point", "ibm_manage_time");

                break;
            case "bet":
                tableNames = Arrays.asList("ibm_ha_follow_period", "ibm_ha_member_bet_item",
                        "ibm_hm_bet_item", "ibm_hm_profit", "ibm_hm_profit_item", "ibm_hm_profit_period", "ibm_hm_profit_period_vr",
                        "ibm_hm_profit_vr");
                break;
            default:
                break;
        }
        num += sysService.cleanDataByTime(tableNames, c.getTime().getTime());

        log.info("---------清理数据执行成功,数据" + num + "条---------");
        return null;
    }
}
