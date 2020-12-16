package com.ibm.common.test.zjj.thread;

import c.a.util.job.QuartzUtil;
import com.ibm.follow.servlet.module.event.MasterCheckEvent;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-01-09 16:16
 * @Version: v1.0
 */
public class Run1 {

    public static void main(String[] args) throws Exception {

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

        executor.scheduleAtFixedRate(new MasterCheckEvent(),5, 10, TimeUnit.SECONDS);

        QuartzUtil quartzUtil=QuartzUtil.findInstance();

        quartzUtil.doSchedulerStart();
        QuartzUtil.destroy();

        Runnable runnable1 = new MyRunnable("A");
        Runnable runnable2 = new MyRunnable("B");
        Runnable runnable3 = new MyRunnable("C");
        Runnable runnable4 = new MyRunnable("D");
        Runnable runnable5 = new MyRunnable("E");

        System.out.println(runnable1.hashCode());
        System.out.println(runnable2.hashCode());
        System.out.println(runnable3.hashCode());
        System.out.println(runnable4.hashCode());
        System.out.println(runnable5.hashCode());

        executor.scheduleAtFixedRate(runnable1, 10, 2, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(runnable2, 10, 2, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(runnable3, 10, 2, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(runnable4, 10, 2, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(runnable5, 10, 2, TimeUnit.SECONDS);

        System.out.println("------------");

        BlockingQueue<Runnable> queue = executor.getQueue();
        Iterator<Runnable> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Runnable runnable = (Runnable) iterator.next();
            System.out.println("队列中的：" + runnable);
        }
    }
}
