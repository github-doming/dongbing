package org.doming.develop.http.htmlunit;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description: HtmlUnit 工具类
 * @Author: Dongming
 * @Date: 2019-11-26 10:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HtmlUnitTool {

    public static String doGet(String url) throws IOException {
        try ( WebClient webClient = new WebClient()) {

            //运行js脚本执行
            webClient.getOptions().setJavaScriptEnabled(true);
            //设置支持AJAX
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            //忽略css
            webClient.getOptions().setCssEnabled(false);
            //ssl安全访问
            webClient.getOptions().setUseInsecureSSL(true);
            //解析js出错时不抛异常
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            //超时时间  ms
            webClient.getOptions().setTimeout(10000);

            LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
            Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
            return webClient.getPage(url);
        }
    }
}
