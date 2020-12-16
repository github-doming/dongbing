package com.ibm.follow.connector.admin.card;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 管理员/股东 获取代理的信息
 * @Author: wwj
 * @Date: 2020/4/9 18:03
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/showAllAgentInfo", method = HttpConfig.Method.GET)
public class ShowAllAgentInfoAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        String reType = StringTool.getString(dataMap.get("reType"), "");
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
            IbmCardAdminService cardAdminService = new IbmCardAdminService();
            if (IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
                adminUserId = IbmTypeEnum.ADMIN.name() ;
            }
            // 获取直属下级ID列表
            List<Map<String, Object>> subAgents = cardAdminService.listAgentInfo(adminUserId);
            List<Map<String, Object>> allSubAgentIds = new ArrayList<>(subAgents);
            if( IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType) || !"report".equals(reType) ){
                // 获取下级的下级代理
                listSubSubAgentInfo(subAgents, allSubAgentIds, cardAdminService);
            }

            bean.success(allSubAgentIds);
        } catch (Exception e) {
            log.error("获取直属代理的信息出错", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    private void listSubSubAgentInfo(List<Map<String, Object>> subAgents, List<Map<String, Object>> allSubAgentIds, IbmCardAdminService cardAdminService) throws SQLException {
        for (Map<String, Object> subAgentInfo : subAgents) {

            List<Map<String, Object>> subSubAgents = cardAdminService.listAgentInfo(subAgentInfo.get("APP_USER_ID_").toString());
            if (ContainerTool.isEmpty(subSubAgents)) {
                continue;
            }
            allSubAgentIds.addAll(subSubAgents);
            listSubSubAgentInfo(subSubAgents, allSubAgentIds, cardAdminService);
        }
    }
}
