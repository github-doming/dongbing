package com.ibm.follow.servlet.cloud.ibm_client.action;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommBaseAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.service.IbmClientHeartbeatService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 迁移客户端
 * @Author: null
 * @Date: 2020-01-15 09:30
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/cloud/manage/migrateClient", method = HttpConfig.Method.GET)
public class MigrateClientAction extends CommBaseAction {
    @Override
    public Object run() throws Exception {
        super.findJson();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return threadJrb;
        }
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        JSONObject jsonData = JSON.parseObject(json);
        Object clientCode = jsonData.getOrDefault("clientCode", "");
        if (StringTool.isEmpty(clientCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try{
            String ip = findServletIp();
            IbmClientService clientService = new IbmClientService();

            Map<String, Object> clientInfo = clientService.findInfo(clientCode, ip);
            //客户端不存在
            if (ContainerTool.isEmpty(clientInfo)) {
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Date nowTime = new Date();
            //  注销客户端
            Object clientId = clientInfo.getOrDefault("IBM_CLIENT_ID_", "");
            clientService.cancelClient(clientId, nowTime, IbmStateEnum.CANCEL);
            //清理容量
            new IbmClientCapacityService().cancelClient(clientId, nowTime);
            new IbmClientHandicapCapacityService().cancelClient(clientId, nowTime);
            new IbmClientHeartbeatService().cancelClient(clientCode,nowTime);

            //清理已存在会员信息
            IbmClientHmService clientHmService = new IbmClientHmService();
            List<String> handicapMemberIds = new IbmClientHmService().listHmIds(clientId);
            for (String handicapMemberId : handicapMemberIds) {
                clientHmService.updateByHmId(handicapMemberId, nowTime, "客户端退出");
            }
            //清理已存在代理信息
            IbmClientHaService clientHaService = new IbmClientHaService();
            List<String> handicapAgentIds = new IbmClientHaService().listHaIds(clientId);
            for (String handicapAgentId : handicapAgentIds) {
                clientHaService.updateByHaId(handicapAgentId, nowTime, "客户端退出");
            }

        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN, e);
            throw e;
        }
        return bean.success();
    }
}
