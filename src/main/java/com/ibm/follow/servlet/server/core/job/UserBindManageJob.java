package com.ibm.follow.servlet.server.core.job;

import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.server.core.thread.UserBindThread;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.quartz.JobExecutionContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Description: 用户绑定管理
 * @Author: null
 * @Date: 2020-01-13 10:58
 * @Version: v1.0
 */
public class UserBindManageJob extends BaseCommJob {

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        IbmHaInfoService haInfoService=new IbmHaInfoService();

        //开启一个线程，进行计算。
        ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
        ExecutorService executorService = threadExecutorService.findExecutorService();

        //分盘口获取用户登录的代理信息
        for(HandicapUtil.Code handicapCode:HandicapUtil.codes()){
            List<Map<String,Object>> haInfos=haInfoService.findLoginInfoByHandicapCode(handicapCode);
            if(ContainerTool.notEmpty(haInfos)){
                executorService.execute(new UserBindThread(haInfos));
            }
        }
    }
}
