package org.doming.develop.http.htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @Description: htmlunit的工具类
 * @Author: Dongming
 * @Date: 2018-12-11 18:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HtmlUnitUtil {

    protected static final Logger log = LogManager.getLogger(HtmlUnitUtil.class);

    private static ThreadLocal<WebClient> WEBCLIENT = null;

    private static volatile HtmlUnitUtil instance;

    private HtmlUnitUtil() {
    }

    public static HtmlUnitUtil findInstance() {
        if (instance == null) {
            synchronized (HtmlUnitUtil.class) {
                if (instance == null) {
                    instance = new HtmlUnitUtil();
                    init();
                }
            }
        }
        return instance;
    }

    private static void init() {
        WEBCLIENT = new ThreadLocal<>();
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
        //当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setTimeout(5000);
        //该方法阻塞线程
        webClient.waitForBackgroundJavaScript(2000);
        //设置JS执行的超时时间
        webClient.setJavaScriptTimeout(5000);
        // 开启cookie管理
        webClient.getCookieManager().setCookiesEnabled(true);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.setAlertHandler((page, message) -> alertMessage(message));
        WEBCLIENT.set(webClient);
    }

    public WebClient webclient() {
        return WEBCLIENT.get();
    }

    public WebClient webclient(BrowserVersion browserVersion) {
        WebClient webClient = new WebClient(browserVersion);

		/*
		webClient.setIncorrectnessListener(new SilentIncorrectnessListener());
		 //js异常控制主要的一步
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.setCssErrorHandler(new QuietCssErrorHandler());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		//设置日志级别
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		*/


        //当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setTimeout(5000);
        //该方法阻塞线程
        webClient.waitForBackgroundJavaScript(2000);
        //设置JS执行的超时时间
        webClient.setJavaScriptTimeout(5000);
        // 开启cookie管理
        webClient.getCookieManager().setCookiesEnabled(true);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.setAlertHandler((page, message) -> alertMessage(message));
        return WEBCLIENT.get();
    }

    private static void alertMessage(String message) {
        log.info("页面存在一个弹窗，弹窗消息" + message);
    }

    /**
     * webClient
     */
    public void close() {
        WEBCLIENT.remove();
    }

}
