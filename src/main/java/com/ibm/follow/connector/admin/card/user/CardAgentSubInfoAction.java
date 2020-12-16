package com.ibm.follow.connector.admin.card.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.connector.admin.card.CardTools;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取子代理信息
 *
 * @Author: Dongming
 * @Date: 2020-04-10 20:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/agent/subInfo")
public class CardAgentSubInfoAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        String subAgentId = StringTool.getString(dataMap, "subAgentId", "");
        if (StringTool.isEmpty(subAgentId)) {
            subAgentId = adminUserId;
        }
        try {
            //加载查询人资料
            IbmCardAdminService cardAdminService = new IbmCardAdminService();
            if (cardAdminService.hasEditPermission(bean, subAgentId, adminUserId)) {
                return bean;
            }

            // 获取用户权限信息
            Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
            int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
            String cardAdminType = CardTools.selectUserGroup(permGrade);
            if (IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
                adminUserId = IbmTypeEnum.ADMIN.name();
            }
            Map<String, Object> result = (Map<String, Object>) bean.getData();
            //加载子代理信息
            List<Map<String, Object>> subAdminPrices;
            if ((boolean) result.get("isAdmin") && (boolean) result.get("isSelf")) {
                // 管理员查自己的卡种价格
                subAdminPrices = new IbmCardTreeService().listPrice();
            } else {
                subAdminPrices = new IbmCardAdminPriceService().listInfo(adminUserId, subAgentId);
            }

            if (ContainerTool.notEmpty(subAdminPrices)) {
                for (Map<String, Object> prices : subAdminPrices) {
                    prices.put("CARD_PRICE_", NumberTool.doubleT(prices.remove("CARD_PRICE_T_")));
                }
            }
            Map<String, Object> data = new HashMap<>(2);
            data.put("subAdminInfo", result.get("subAdminInfo"));
            data.put("subAdminPrices", subAdminPrices);
            bean.success(data);

        } catch (Exception e) {
            log.error("下级管理列表出错", e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
