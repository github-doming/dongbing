package com.ibm.follow.connector.admin.card;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 获取用户卡类列表(新增点卡查询时使用)
 * @Author: wwj
 * @Date: 2020/4/4 18:24
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/showUserCardTree", method = HttpConfig.Method.GET)
public class ShowUserCardTreeAction extends CommAdminAction {

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
            // 权限码超过110的用户没有权限 ADMIN:0-99 PARTNER:100 AGENT:110
            if (IbmTypeEnum.FALSE.name().equals(cardAdminType)) {
                bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean.toJsonString();
            }

            if (IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
                adminUserId = IbmTypeEnum.ADMIN.name() ;
            }

            List<Map<String, Object>> treeInfos;
            IbmCardTreeService cardTreeService = new IbmCardTreeService();
            if (IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
                treeInfos = cardTreeService.listCardTreeSimpleByAdmin();
            } else {
                //查出所有下级代理拥有的卡类
                IbmCardAdminPriceService cardAdminPriceService = new IbmCardAdminPriceService();
                List<String> myTreeIds = cardAdminPriceService.listUserCardTreeIds(adminUserId);
                Set<String> allTreeIds = new HashSet<>(myTreeIds);
                IbmCardAdminService cardAdminService = new IbmCardAdminService();
                List<String> subAgentIds = cardAdminService.listAgentIds(adminUserId);
                listSubAgentTreeIds(cardAdminPriceService, cardAdminService, subAgentIds, allTreeIds);
                treeInfos = cardTreeService.listCardTreeSimpleByTreeIds(allTreeIds);
            }

            bean.success(treeInfos);
        } catch (Exception e) {
            log.error("获取用户拥有的卡种卡种列表出错", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    private void listSubAgentTreeIds(IbmCardAdminPriceService cardAdminPriceService, IbmCardAdminService cardAdminService,
                                     List<String> subAgentIds, Set<String> allTreeIds) throws SQLException {
        List<String> subTreeIds;
        for (String agentId : subAgentIds) {
            subTreeIds = cardAdminPriceService.listUserCardTreeIds(agentId);
            allTreeIds.addAll(subTreeIds);
            List<String> subSubAgentIds = cardAdminService.listAgentIds(agentId);
            if (ContainerTool.isEmpty(subSubAgentIds)) {
                continue;
            }
            listSubAgentTreeIds(cardAdminPriceService, cardAdminService, subSubAgentIds, allTreeIds);
        }
    }


}
