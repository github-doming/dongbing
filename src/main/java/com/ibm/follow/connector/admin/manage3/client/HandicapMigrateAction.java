package com.ibm.follow.connector.admin.manage3.client;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.service.IbmClientHeartbeatService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description: 盘口迁移
 * @Author: null
 * @Date: 2020-01-16 10:14
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicapMigrate1", method = HttpConfig.Method.POST)
public class HandicapMigrateAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
//        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }


        //客户类型
        String customer = request.getParameter("customerType");
        //盘口code
        String handicapCode = request.getParameter("handicapCode");
        //发送端客户机active
        String sendClientCode = request.getParameter("sendClientCode");
        //接收端客户机passive
        String receiveClientCode = request.getParameter("receiveClientCode");

        /*
        客户机迁移，以单盘口单类型为单位(eg:A盘口会员，A盘口代理分别为两个盘口迁移单位)
        1,客户机是否存在
        2,发送端客户机是否有登录中的客户
        3,这里登出只处理了ibm_client_hm，ibm_client_ha数据，对用户的其他数据没有修改
        4,发送消息给发送端客户机，客户机接收到后，将本机的客户信息（还有未投注的投注信息）发送到主服务器
        5,主服务器接收到后，信息进行核对，将无误的信息发送到接收端客户机
        */
        IbmClientService clientService = new IbmClientService();
        //不存在或者客户机状态不为open
        if (!IbmStateEnum.OPEN.name().equals(clientService.findState(sendClientCode))
                || !IbmStateEnum.OPEN.name().equals(clientService.findState(receiveClientCode))) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        IbmTypeEnum customerType = IbmTypeEnum.valueCustomerTypeOf(customer);
        if (customerType == null) {
            bean.putEnum(IbmCodeEnum.IBM_401_METHOD);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        //判断两台客户机心跳检测是否停止
        IbmClientHeartbeatService clientHeartbeatService=new IbmClientHeartbeatService();
        Map<String,Object> heartbeatMap=clientHeartbeatService.findResult(sendClientCode);
        if(ContainerTool.isEmpty(heartbeatMap)||!IbmStateEnum.SUCCESS.name().equals(heartbeatMap.get("HEARTBEAT_RESULT_"))){
            bean.putEnum(IbmCodeEnum.IBM_403_HEARTBEAT_ERROR);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }
        if(System.currentTimeMillis()- Long.parseLong(heartbeatMap.get("UPDATE_TIME_LONG_").toString())<61000L){
            bean.putEnum(IbmCodeEnum.IBM_403_HEARTBEAT_INTERVAL);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }
        heartbeatMap=clientHeartbeatService.findResult(receiveClientCode);
        if(ContainerTool.isEmpty(heartbeatMap)||!IbmStateEnum.SUCCESS.name().equals(heartbeatMap.get("HEARTBEAT_RESULT_"))){
            bean.putEnum(IbmCodeEnum.IBM_403_HEARTBEAT_ERROR);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }
        if(System.currentTimeMillis()- Long.parseLong(heartbeatMap.get("UPDATE_TIME_LONG_").toString())<61000L){
            bean.putEnum(IbmCodeEnum.IBM_403_HEARTBEAT_INTERVAL);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }

        //盘口会员ids或者盘口代理ids
        //清除旧客户机信息
        List<String> customerIds=clearOldInfo(customerType, handicapCode, sendClientCode);

        //发消息到发送端
        JSONObject content = new JSONObject();
        content.put("METHOD_", IbmMethodEnum.CLIENT_MIGRATE.name());
        content.put("customerType", customerType.name());
        content.put("handicapCode", handicapCode);
        content.put("methodType", IbmMethodEnum.SEND.name());
        String result = RabbitMqTool.sendClientConfig(content.toString(), sendClientCode, "info");
        if (StringTool.isEmpty(result)) {
            bean.putEnum(IbmCodeEnum.IBM_401_MESSAGE);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        if (!Boolean.parseBoolean(result)) {
            bean.putEnum(IbmCodeEnum.IBM_403_MQ);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }
        //重启事务
        transactionRestart();

        //发消息到接收端
        if(ContainerTool.notEmpty(customerIds)){
            getCustomerInfo(customerIds, customerType,content);
        }
        content.put("methodType", IbmMethodEnum.RECEIVE.name());
        result = RabbitMqTool.sendClientConfig(content.toString(), receiveClientCode, "info");
        if (StringTool.isEmpty(result)) {
            bean.putEnum(IbmCodeEnum.IBM_401_MESSAGE);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        if (!Boolean.parseBoolean(result)) {
            bean.putEnum(IbmCodeEnum.IBM_403_MQ);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }

        bean.success();
        return bean.toJsonString();
    }

    /**
     * 获取客户信息
     *  @param customerIds  盘口会员或盘口代理ids
     * @param customerType 客户类型
     */
    private void getCustomerInfo(List<String> customerIds, IbmTypeEnum customerType,JSONObject content) throws SQLException {
        switch (customerType) {
            case MEMBER:
                List<Map<String,Object>> memberInfos=new IbmHandicapMemberService().findMemberInfos(customerIds);
                //获取会员游戏设置信息
                List<Map<String, Object>> memberGameInfos =new IbmHmGameSetService().findByHmIds(customerIds);
                //获取会员设置信息
                List<Map<String, Object>> hmSetInfos =new IbmHmSetService().findByHmIds(customerIds);
                content.put("memberGameInfos",memberGameInfos);
                content.put("hmSetInfos",hmSetInfos);
                content.put("customerInfos",memberInfos);
                break;
            case AGENT:
                List<Map<String,Object>> agentInfos=new IbmHandicapAgentService().findAgentInfos(customerIds);
                //获取代理游戏设置信息
                List<Map<String, Object>> agentGameInfos = new IbmHaGameSetService().findByHaIds(customerIds);
                //代理设置信息
                List<Map<String, Object>> haSetInfos= new IbmHaSetService().findByHaIds(customerIds);
                content.put("agentGameInfos",agentGameInfos);
                content.put("haSetInfos",haSetInfos);
                content.put("customerInfos",agentInfos);
                break;
            default:
        }
    }

    /**
     * 清除旧的信息
     * @param customerType      客户类型
     * @param handicapCode      盘口code
     * @param sendClientCode    发送端客户机编码
     */
    private List<String> clearOldInfo(IbmTypeEnum customerType, String handicapCode,
         String sendClientCode) throws Exception {
        List<String> customerIds ;
        switch (customerType) {
            case MEMBER:
                //获取需要迁移的客户
                IbmClientHmService clientHmService=new IbmClientHmService();
                customerIds = clientHmService.findHmIds(sendClientCode, handicapCode);
                if(ContainerTool.notEmpty(customerIds)){
                    clientHmService.updateByHmIds(customerIds);
                }
                break;
            case AGENT:
                IbmClientHaService clientHaService=new IbmClientHaService();
                customerIds =clientHaService.findHaIds(sendClientCode, handicapCode);
                if(ContainerTool.notEmpty(customerIds)){
                    clientHaService.updateByHaIds(customerIds);
                }
                break;
            default:
                return new ArrayList<>();
        }
        return customerIds;
    }
}
