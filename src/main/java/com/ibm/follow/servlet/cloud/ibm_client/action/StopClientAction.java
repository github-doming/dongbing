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
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.service.IbmClientHeartbeatService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 关闭客户端
 * @Author: null
 * @Date: 2020-01-02 16:03
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/cloud/manage/stopClient", method = HttpConfig.Method.GET)
public class StopClientAction extends CommBaseAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 关闭客户端-中心端登记
        super.findJson();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return threadJrb;
        }
        JSONObject jsonData = JSON.parseObject(json);
        Object clientCode = jsonData.getOrDefault("clientCode", "");
        if (StringTool.isEmpty(clientCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
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
            clientService.cancelClient(clientId, nowTime, IbmStateEnum.STOP);
            //清理容量
            new IbmClientCapacityService().cancelClient(clientId, nowTime);
            new IbmClientHandicapCapacityService().cancelClient(clientId, nowTime);
            new IbmClientHeartbeatService().cancelClient(clientCode,nowTime);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN, e);
            throw e;
        }
        return bean.success();
    }
}
