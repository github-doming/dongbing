package com.ibm.old.v1.admin.webservletcontent.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
import org.doming.core.common.servlet.WebServletContent;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 线程池
 * @Author: wck
 * @Date: 2019-05-22 14:00
 * @Email: 810160078@qq.com
 * @Version: v1.0
 */
public class WebServletContentListAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        Map<String,Map<String,Object>> data = new HashMap<>(2);

        Map<String,Object> coreThreadPool = new HashMap<>(6);
//        coreThreadPool.put("corePoolSize", WebServletContent.getCorePoolSize("core"));
//        coreThreadPool.put("poolSize",WebServletContent.getPoolSize("core"));
//        coreThreadPool.put("activeCount",WebServletContent.getActiveCount("core"));
//        coreThreadPool.put("keepAliveTimeSeconds",WebServletContent.getKeepAliveTimeSeconds("core"));
//        coreThreadPool.put("maximumPoolSize",WebServletContent.getMaximumPoolSize("core"));
//        coreThreadPool.put("taskCount",WebServletContent.getTaskCount("core"));
//        data.put("core",coreThreadPool);
//
//        Map<String,Object> queryThreadPool = new HashMap<>(6);
//        queryThreadPool.put("corePoolSize",WebServletContent.getCorePoolSize("query"));
//        queryThreadPool.put("poolSize",WebServletContent.getPoolSize("query"));
//        queryThreadPool.put("activeCount",WebServletContent.getActiveCount("query"));
//        queryThreadPool.put("keepAliveTimeSeconds",WebServletContent.getKeepAliveTimeSeconds("query"));
//        queryThreadPool.put("maximumPoolSize",WebServletContent.getMaximumPoolSize("query"));
//        queryThreadPool.put("taskCount",WebServletContent.getTaskCount("query"));
//        data.put("query",queryThreadPool);

        request.setAttribute("threadPool",data);
        return CommViewEnum.Default.toString();
    }
}
