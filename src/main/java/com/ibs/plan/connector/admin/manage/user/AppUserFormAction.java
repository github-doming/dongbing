package com.ibs.plan.connector.admin.manage.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户新增页面表单
 * @Author: null
 * @Date: 2020-03-23 17:16
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/form", method = HttpConfig.Method.GET)
public class AppUserFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try {
            Map<String, Object> data = new HashMap<>(10);

            //获取所有游戏
            data.put("allGameInfo", new IbspGameService().findGameInfo());
            //获取所有会员盘口
            data.put("memberAllUsable", new IbspHandicapService().findHandicapCode());


            bean.success(data);
        } catch (Exception e) {
            log.error("用户新增页面表单,", e);
            throw e;
        }
        return bean.toString();
    }
}
