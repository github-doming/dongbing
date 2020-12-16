package com.ibm.follow.servlet.module.event;

import org.doming.core.tools.ThreadTool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 事件线程池
 * @Author: null
 * @Date: 2019-12-24 10:44
 * @Version: v1.0
 */
public class EventThreadExecute {

    /**
     * 登录线程池
     */
    private ThreadPoolExecutor loginExecutor;
    /**
     * 验证线程池
     */
    private ThreadPoolExecutor valiLoginExecutor;
    /**
     * 客户设置线程池
     */
    private ThreadPoolExecutor setExecutor;
    /**
     * 设置线程池
     */
    private ThreadPoolExecutor infoExecutor;
    /**
     * 其他线程池
     */
    private ThreadPoolExecutor otherExecutor;


    private EventThreadExecute() {
    }

    private static volatile EventThreadExecute instance = null;

    public static EventThreadExecute findInstance() {
        if (instance == null) {
            synchronized (EventThreadExecute.class) {
                if (instance == null) {
                    // 初始化
                    instance = new EventThreadExecute();
                }
            }
        }
        return instance;
    }

    public static void destroy() throws InterruptedException {
        if (instance == null) {
            return;
        }
        ThreadTool.close(instance.loginExecutor);
        ThreadTool.close(instance.valiLoginExecutor);
        ThreadTool.close(instance.setExecutor);
        ThreadTool.close(instance.infoExecutor);
        ThreadTool.close(instance.otherExecutor);
    }
    /**
     * 获取登录线程池
     *
     * @return 线程池
     */
    public ThreadPoolExecutor getLoginExecutor() {
        if (loginExecutor == null) {
            synchronized (EventThreadExecute.class) {
                loginExecutor = ThreadTool.createExecutor("login", 5, 5 * 4, 5 * 3, 2000);
            }
        }
        return loginExecutor;
    }
    /**
     * 获取验证登录线程池
     *
     * @return 线程池
     */
    public ThreadPoolExecutor getValiLoginExecutor() {
        if (valiLoginExecutor == null) {
            synchronized (EventThreadExecute.class) {
                valiLoginExecutor = ThreadTool.createExecutor("valiLogin", 5, 5 * 4, 5 * 3, 2000);
            }
        }
        return valiLoginExecutor;
    }
    /**
     * 获取客户设置线程池
     *
     * @return 线程池
     */
    public ThreadPoolExecutor getSetExecutor() {
        if (setExecutor == null) {
            synchronized (EventThreadExecute.class) {
                setExecutor = ThreadTool.createExecutor("set", 5, 5 * 4, 5 * 3, 2000);
            }
        }
        return setExecutor;
    }
    /**
     * 获取设置线程池
     *
     * @return 线程池
     */
    public ThreadPoolExecutor getInfoExecutor() {
        if (infoExecutor == null) {
            synchronized (EventThreadExecute.class) {
                infoExecutor = ThreadTool.createExecutor("info", 5, 5 * 4, 5 * 3, 2000);
            }
        }
        return infoExecutor;
    }
    /**
     * 获取设置线程池
     *
     * @return 线程池
     */
    public ThreadPoolExecutor getOtherExecutor() {
        if (otherExecutor == null) {
            synchronized (EventThreadExecute.class) {
                otherExecutor = ThreadTool.createExecutor("other", 5, 5 * 4, 5 * 3, 2000);
            }
        }
        return otherExecutor;
    }
}
