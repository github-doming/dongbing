package com.ibm.follow.connector.app.home;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 点击会员
 * @Author: null
 * @Date: 2019-11-23 16:17
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/home/clickMember", method = HttpConfig.Method.GET)
public class MemberClickAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try {
            Map<String, Object> data = new HashMap<>(1);
            IbmHmUserService hmUserService = new IbmHmUserService();
            IbmHmInfoService hmInfoService = new IbmHmInfoService();
            // 会员盘口
            List<Map<String, Object>> memberHandicapInfo = hmUserService.listUserHandicap(appUserId);

            List<Map<String, Object>> offlineInfo;
            List<Map<String, Object>> onlineInfo;
            for (Map<String, Object> map : memberHandicapInfo) {
                map.remove("ROW_NUM");
                String handicapCode = map.get("HANDICAP_CODE_").toString();
				String handicapId =HandicapUtil.id(handicapCode,IbmTypeEnum.MEMBER);
                String onlineMembersIds = hmUserService.findOnlineMembersId(appUserId, handicapId);
                if (StringTool.isEmpty(onlineMembersIds)) {
                    onlineInfo=new ArrayList<>();
                    offlineInfo = hmInfoService.listOfflineInfo(appUserId, handicapId, null);
                }else{
                    onlineInfo = hmInfoService.listOnlineInfoByHmIds(onlineMembersIds.split(","));
                    offlineInfo = hmInfoService.listOfflineInfo(appUserId, handicapId,
                            ContainerTool.getValSet4Key(onlineInfo, "HANDICAP_MEMBER_ID_"));
                }
                map.put("onHostingInfo", onlineInfo);
                map.put("offHostingInfo", offlineInfo);
            }
            data.put("memberInfo", memberHandicapInfo);

            bean.success();
            bean.setData(data);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("点击会员错误"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
