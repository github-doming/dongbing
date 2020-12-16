package com.ibm.old.v1.admin.webservletcontent.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
import org.doming.core.common.servlet.WebServletContent;

/**
 * @Description: 线程池
 * @Author: wck
 * @Date: 2019-05-22 14:00
 * @Email: 810160078@qq.com
 * @Version: v1.0
 */
public class WebServletContentEditAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        String code = request.getParameter("code");
        String corePoolSize = request.getParameter("corePoolSize");
        String maximumPoolSize = request.getParameter("maximumPoolSize");
        String keepAliveTimeSeconds = request.getParameter("keepAliveTimeSeconds");
//
//        WebServletContent.setCorePoolSize(code,Integer.parseInt(corePoolSize));
//        WebServletContent.setKeepAliveTimeSeconds(code,Integer.parseInt(keepAliveTimeSeconds));
//        WebServletContent.setMaximumPoolSize(code,Integer.parseInt(maximumPoolSize));

        return CommViewEnum.Default.toString();
    }
}
