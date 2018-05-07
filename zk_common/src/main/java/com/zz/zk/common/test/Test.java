package com.zz.zk.common.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangzuizui
 * @date 2018/5/4 11:13
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        final int loopCount=2000000;
        final CountDownLatch latch = new CountDownLatch(loopCount);
        for (int i=0;i<loopCount;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("hello Work"+latch.getCount());
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
}
