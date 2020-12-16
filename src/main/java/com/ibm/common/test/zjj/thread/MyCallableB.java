package com.ibm.common.test.zjj.thread;

import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-01-09 16:29
 * @Version: v1.0
 */
public class MyCallableB implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("callB begin " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
        System.out.println("callB   end " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
        return "returnB";
    }

}
