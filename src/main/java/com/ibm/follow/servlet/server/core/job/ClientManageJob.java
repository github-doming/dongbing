package com.ibm.follow.servlet.server.core.job;

import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.quartz.JobExecutionContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 客户端管理job
 * @Author: null
 * @Date: 2019-12-25 09:49
 * @Version: v1.0
 */
public class ClientManageJob extends BaseCommJob {
    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        //已开启的客户端
        List<String> clientIds= new IbmClientService().findAll();

        //获取会员正在使用中的客户端
        IbmClientHmService clientHmService=new IbmClientHmService();
        List<String> hmUsingClientIds=clientHmService.findUsingClientIds();

        //获取代理正在使用中的客户端
        IbmClientHaService clientHaService=new IbmClientHaService();
        List<String> haUsingClientIds=clientHaService.findUsingClientIds();

        //会员正在使用中的客户端-已开启的客户端数据-清除登录信息
        Set<Object> expiredHmClientIds = new HashSet<>(ContainerTool.getDifferent(hmUsingClientIds, clientIds));

        //代理正在使用中的客户端-已开启的客户端数据-清除登录信息
        Set<Object> expiredHaClientIds = new HashSet<>(ContainerTool.getDifferent(haUsingClientIds, clientIds));

        clearLoginInfo(expiredHmClientIds,expiredHaClientIds,clientHmService,clientHaService);
    }

    /**
     * 需要清除相关客户端的会员和代理信息
     * @param expiredHmClientIds        需要清除的会员客户端ids
     * @param expiredHaClientIds        需要清除的代理客户端ids
     * @param clientHmService           客户端盘口会员服务类
     * @param clientHaService           客户端盘口代理服务类
     */
    private void clearLoginInfo(Set<Object> expiredHmClientIds, Set<Object> expiredHaClientIds,
                                IbmClientHmService clientHmService,IbmClientHaService clientHaService) throws Exception {
        //清除会员信息
        if (ContainerTool.notEmpty(expiredHmClientIds)) {
            IbmHandicapMemberService handicapMemberService=new IbmHandicapMemberService();
            List<String> handicapMemberIds=clientHmService.findHmIdByClientIds(expiredHmClientIds);
            LogoutHmController logoutHmController=new LogoutHmController();
            for(String handicapMemberId:handicapMemberIds){
                logoutHmController.execute(handicapMemberId);
                handicapMemberService.updateOperating(handicapMemberId);
            }
        }
        //清除代理信息
        if (ContainerTool.notEmpty(expiredHaClientIds)) {
            IbmHandicapAgentService handicapAgentService=new IbmHandicapAgentService();
            List<String> handicapAgentIds=clientHaService.findHaIdByClientIds(expiredHaClientIds);
            LogoutHaController logoutHaController=new LogoutHaController();
            for(String handicapAgentId:handicapAgentIds){
                logoutHaController.execute(handicapAgentId);
                handicapAgentService.updateOperating(handicapAgentId);
            }
        }
        //清除客户端容量信息
        expiredHmClientIds.addAll(expiredHaClientIds);
        if (ContainerTool.notEmpty(expiredHmClientIds)) {
            new IbmClientCapacityService().clearClientInfo(expiredHmClientIds);

            new IbmClientHandicapCapacityService().clearClientInfo(expiredHmClientIds);
        }

    }

}
