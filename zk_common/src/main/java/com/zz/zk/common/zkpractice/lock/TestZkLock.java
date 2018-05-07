package com.zz.zk.common.zkpractice.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangzuizui
 * @date 2018/5/7 18:20
 */
public class TestZkLock {

    public static void main(String[] args) {
        final ZKLock zkLock = new ZKLock("localhost","lock");

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(new Runnable() {
            @Override
            public void run() {

                zkLock.lock();
                System.out.println("do something !!!");
                zkLock.unlock();
                if(zkLock.tryLock()){
                    System.out.println("get lock do something !!!");
                }else {
                    System.out.println("is locking ,please wait or do next time .");
                }
                zkLock.unlock();

                System.out.println("111111");
            }
        });
    }
}
