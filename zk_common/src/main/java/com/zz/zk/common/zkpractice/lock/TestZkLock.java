package com.zz.zk.common.zkpractice.lock;

/**
 * @author zhangzuizui
 * @date 2018/5/7 18:20
 */
public class TestZkLock {
    public static void main(String[] args) {
        ZKLock zkLock = new ZKLock("localhost","zzz");
        zkLock.lock();
    }
}
