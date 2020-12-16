package com.ibm.common.core.servlet.boot;
import org.doming.core.common.servlet.AsynMvcServletBase;

import javax.servlet.annotation.WebServlet;
/**
 * @Description: MVC服务
 * @Author: Dongming
 * @Date: 2019-08-21 16:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@WebServlet(name = "IbmMvcServlet", urlPatterns = "/ibm/*", asyncSupported = true) public class IbmMvcServlet
		extends AsynMvcServletBase {
}
