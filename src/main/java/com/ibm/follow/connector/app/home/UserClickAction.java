package com.ibm.follow.connector.app.home;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.follow.servlet.cloud.ibm_manage_point.service.IbmManagePointService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 点击用户信息
 * @Author: null
 * @Date: 2019-11-23 16:38
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/home/clickUser", method = HttpConfig.Method.GET)
public class UserClickAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try {
            // 用户信息
            Map<String, Object> userInfo = new HashMap<>(2);
            userInfo.put("endTimeLong", new IbmManageTimeService().getEndTime(appUserId));
            // TODO 用户系统消息条数
            userInfo.put("cmsInfo", 0);

            bean.success();
            bean.setData(userInfo);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("点击用户信息错误"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
