package com.cloud.common.core.boot;
import org.doming.core.common.servlet.AsynMvcServletBase;

import javax.servlet.annotation.WebServlet;
/**
 * MVC服务
 *
 * @Author: Dongming
 * @Date: 2020-05-08 18:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@WebServlet(name = "CloudMvcServlet", urlPatterns = "/cloud/*", asyncSupported = true) public class CloudMvcServlet
		extends AsynMvcServletBase {
}
