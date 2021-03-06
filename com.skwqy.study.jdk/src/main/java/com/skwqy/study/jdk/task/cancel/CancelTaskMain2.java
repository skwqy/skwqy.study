package com.skwqy.study.jdk.task.cancel;

import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务无法停止
 * 
 * @author skwqy
 * @Created on 2019 03 2019-03-01 22:05
 */
public class CancelTaskMain2 {
    private static final Logger LOG = LoggerFactory.getLogger(CancelTaskMain2.class);
    public static void main(String[] args) {
        BlockingQueue queue = new LinkedBlockingQueue<Runnable>(100);
        ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setDaemon(true).setNameFormat("skwqy-threadtest-%d").build();
        ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS, queue, threadFactory, (Runnable r, ThreadPoolExecutor executor)->{
            LOG.error("task queue is full");
        }
        );


        /**
         * 任务无法停止
         */
        Future future = taskExecutor.submit(new CancelTask2());

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            LOG.error("sleep is Interrupted",e);
        }

        future.cancel(true);

        LOG.info("cancel future task");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            LOG.error("sleep is Interrupted",e);
        }

        taskExecutor.submit(new PrintThreadNameTask());

        LOG.info("end of main");

    }

}
