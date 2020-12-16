package com.ibm.common.test.zjj.thread;

import java.util.concurrent.Callable;

/**
 * @Description: 延迟运行
 * @Author: null
 * @Date: 2020-01-09 16:25
 * @Version: v1.0
 */
public class MyCallableA implements Callable<String> {

    @Override
    public String call() throws Exception {
        try {
            System.out.println("callA begin " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
            Thread.sleep(3000);
            System.out.println("callA   end " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "returnA";
    }

}
