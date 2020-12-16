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
import com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.service.IbspClientHandicapCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 盘口容量设置
 * @Author: null
 * @Date: 2020-03-24 16:51
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/handicap/edit",method = HttpConfig.Method.POST)
public class HandicapCapacityEditAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String clientCode = dataMap.getOrDefault("clientCode","").toString();
        String handicapCode = dataMap.getOrDefault("handicapCode","").toString();
        String capacityHandicapMax = dataMap.getOrDefault("capacityHandicapMax","").toString();


        try{
            Map<String, Object> capacityMap=new IbspClientHandicapCapacityService().findHandicapCapacityInfo(clientCode,handicapCode);
            if(ContainerTool.isEmpty(capacityMap)){
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            Map<String, Object> clientInfo= new IbspClientService().findInfo(clientCode);
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
            log.error("盘口容量设置错误", e);
            return bean.error(e.getMessage());
        }
        return bean;
    }
}
