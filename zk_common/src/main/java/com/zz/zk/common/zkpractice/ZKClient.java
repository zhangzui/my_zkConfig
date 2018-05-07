package com.zz.zk.common.zkpractice;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * zk客户端
 * @author zhangzuizui
 * @date 2018/5/7 18:15
 */
public class ZKClient implements Watcher {

    public ZooKeeper zk;
    /**
     * 根目录
     */
    public String root = "/locks";

    /**
     * 竞争资源的标志
     */
    public String lockName;

    /**
     * 等待前一个锁
     */
    public String waitNode;

    /**
     * 当前锁节点
     */
    public String myZnode;

    /**
     * 计数器
     */
    public CountDownLatch latch;

    /**
     * 初始化
     */
    public CountDownLatch connectedSignal=new CountDownLatch(1);

    /**
     * 超时时间
     */
    public int sessionTimeout = 500000;

    public ZKClient(String config, String lockName){
        this.lockName = lockName;
        // 创建一个与服务器的连接
        try {
            zk = new ZooKeeper(config, sessionTimeout, this);
            connectedSignal.await();
            //此去不执行 Watcher
            Stat stat = zk.exists(root, false);
            if(stat == null){
                // 创建根节点
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException");
        } catch (KeeperException e) {
            throw new RuntimeException("KeeperException");
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException");
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //建立连接用
        if(watchedEvent.getState()== Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
            return;
        }
        //其他线程放弃锁的标志
        if(this.latch != null) {
            this.latch.countDown();
        }
    }

    /**
     * 等待锁
     */
    public boolean waitForLock(String lower, long waitTime) throws InterruptedException, KeeperException {
        //同时注册监听。
        Stat stat = zk.exists(root + "/" + lower,true);
        //判断比自己小一个数的节点是否存在,如果不存在则无需等待锁,同时注册监听
        if(stat != null){
            System.out.println("等待获取锁："+"Thread " + Thread.currentThread().getId() + " waiting for " + root + "/" + lower);
            this.latch = new CountDownLatch(1);
            //等待，这里应该一直等待其他线程释放锁
            this.latch.await(waitTime, TimeUnit.MILLISECONDS);
            this.latch = null;
        }
        return true;
    }
}
