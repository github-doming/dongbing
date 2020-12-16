package com.ibm.follow.connector.admin.card;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_report.service.IbmCardReportService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 以卡种分类查询充值卡报表
 * @Author: wwj
 * @Date: 2020/4/6 15:30
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/reportByCardTree")
public class QueryReportByCardTreeAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        Map<String, Object> data;
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
            IbmCardReportService cardReportService = new IbmCardReportService();

            // 查询直属下级
            IbmCardAdminService cardAdminService = new IbmCardAdminService();
            List<String> subAgentIds = cardAdminService.listAgentIds(adminUserId);

            data = cardReportService.listReportByCardTree(adminUserId,subAgentIds);

            bean.success(data);
        } catch (Exception e) {
            log.error("以卡种分类查询充值卡报表出错", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}

