package com.ibm.follow.connector.admin.card;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 获取用户拥有的卡种卡种列表
 * @Author: wwj
 * @Date: 2020/4/4 18:24
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/listCardTree")
public class QueryCardTreeAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try {
            // 获取用户权限信息
            Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
            int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
            String cardAdminType = CardTools.selectUserGroup(permGrade);

            if (IbmTypeEnum.FALSE.name().equals(cardAdminType)) {
                bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean.toJsonString();
            }

            List<Map<String, Object>> cardTreeInfos;
            if (IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
                IbmCardTreeService cardTreeService = new IbmCardTreeService();
                cardTreeInfos = cardTreeService.listCardTreeByAdmin();
            } else {
                //查出所有下级代理拥有的卡类
                IbmCardAdminPriceService cardAdminPriceService = new IbmCardAdminPriceService();
                cardTreeInfos = cardAdminPriceService.listCardTreeByUser(adminUserId);
            }

            for (Map<String, Object> info : cardTreeInfos) {
                info.put("CARD_PRICE_T_", NumberTool.doubleT(info.get("CARD_PRICE_T_")));
            }
            bean.success(cardTreeInfos);
        } catch (Exception e) {
            log.error("获取用户拥有的卡种卡种列表出错", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

}
