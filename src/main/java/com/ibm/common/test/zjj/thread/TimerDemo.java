package com.ibm.common.test.zjj.thread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-01-09 17:02
 * @Version: v1.0
 */
public class TimerDemo {
private static int index = 0;
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(++index);
            }
        }, 1000L, 1000L);
        Thread.sleep(10000L);
        timer.cancel();
    }
}
