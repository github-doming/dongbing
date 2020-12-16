package org.doming.example.idear.test;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-01-25 17:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ThreadMainTest implements Runnable {

    public int i = 0;


    public static void main(String[] args) {

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    }


    @Override
    public void run() {
        long time =System.currentTimeMillis();
    }





}

