package com.ibm.follow.connector.admin.card;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card.service.IbmCardService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 查询点卡列表
 * @Author: wwj
 * @Date: 2020/4/6 14:51
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/listCard")
public class QueryCardAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String cardName = StringTool.getString(dataMap.get("cardName"), "");
        String cardState = StringTool.getString(dataMap.get("cardState"), "");
        String cardId = StringTool.getString(dataMap.get("cardId"), "");

        long timeStart = NumberTool.getLong(dataMap.get("timeStart"), 0L);
        long timeEnd = NumberTool.getLong(dataMap.get("timeEnd"), 0L);

        Long startTime = null, endTime = null;
        if (timeStart != 0) {
            startTime = DateTool.getDayStart(new Date(timeStart)).getTime();
            if (timeEnd == 0) {
                timeEnd = System.currentTimeMillis();
            }
            endTime = DateTool.getDayEnd(new Date(timeEnd)).getTime();
        }

        Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
        Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
        Map<String, Object> data = new HashMap<>(3);
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
                adminUserId = IbmTypeEnum.ADMIN.name();
            }
            PageCoreBean<Map<String, Object>> basePage = new IbmCardService().listCard(cardId, adminUserId, cardName, cardState, startTime, endTime, pageIndex, pageSize);

            //回传数据
            data.put("rows", basePage.getList());
            data.put("index", pageIndex);
            data.put("total", basePage.getTotalCount());

        } catch (Exception e) {
            log.error("查询点卡列表出错", e);
            data.clear();
            data.put("rows", null);
            data.put("index", 0);
            data.put("total", 0);
        }
        return data;
    }
}
