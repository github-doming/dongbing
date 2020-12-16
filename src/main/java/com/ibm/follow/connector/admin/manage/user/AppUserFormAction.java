package com.ibm.follow.connector.admin.manage.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户新增页面表单
 * @Author: null
 * @Date: 2020-03-23 17:16
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/form", method = HttpConfig.Method.GET)
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
            List<Map<String, Object>> allGameInfo = new IbmGameService().findGameInfo();
            data.put("allGameInfo", allGameInfo);
            //获取所有会员盘口
            IbmHandicapService handicapService = new IbmHandicapService();
            List<String> memberAllUsable = handicapService.findHandicapCode(IbmTypeEnum.MEMBER);
            data.put("memberAllUsable", memberAllUsable);
            //获取所有代理盘口
            List<String> agentAllUsable = handicapService.findHandicapCode(IbmTypeEnum.AGENT);
            data.put("agentAllUsable", agentAllUsable);

            bean.setData(data);
            bean.success();
        } catch (Exception e) {
            log.error("注册用户失败，", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
