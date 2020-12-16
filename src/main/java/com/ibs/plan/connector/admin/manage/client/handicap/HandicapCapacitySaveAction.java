package com.ibs.plan.connector.admin.manage.client.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 新增盘口容量信息
 * @Author: null
 * @Date: 2020-04-11 16:17
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/handicap/save", method = HttpConfig.Method.POST)
public class HandicapCapacitySaveAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String clientCode = dataMap.getOrDefault("clientCode", "").toString();
        String handicapCode = dataMap.getOrDefault("handicapCode", "").toString();
        String capacityHandicapMax = dataMap.getOrDefault("capacityHandicapMax", "").toString();

        if (StringTool.isEmpty(clientCode, handicapCode, capacityHandicapMax)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        try {
            IbspClientService clientService=new IbspClientService();
            Map<String, Object> clientInfo= clientService.findInfo(clientCode);
            if(ContainerTool.isEmpty(clientInfo)){
                bean.putEnum(CodeEnum.IBS_401_DATA);
                bean.putSysEnum(CodeEnum.CODE_401);
                return bean;
            }
            JSONObject content=new JSONObject();
            content.put("handicapCode",handicapCode);
            content.put("capacityHandicapMax",capacityHandicapMax);
            content.put("METHOD_", IbsMethodEnum.HANDICAP_CAPACITY_SET.name());
            String result= RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
            if (StringTool.isEmpty(result)) {
                bean.putEnum(CodeEnum.IBS_401_MESSAGE);
                bean.putSysEnum(CodeEnum.CODE_401);
                return bean;
            }
            if (!Boolean.parseBoolean(result)) {
                bean.putEnum(CodeEnum.IBS_403_MQ);
                bean.putSysEnum(CodeEnum.CODE_403);
                return bean;
            }

            bean.success();
        } catch (Exception e) {
            log.error("盘口容量列表信息错误", e);
            return bean.error(e.getMessage());
        }
        return bean;
    }
}
