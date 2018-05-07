package com.zz.zk.common.impl;

import com.zz.zk.common.utils.ZooKeeperConnection;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

public class ZKCreate {

   private static ZooKeeper zk;

   private static ZooKeeperConnection conn;

   public static void create(String path, byte[] data) throws
      KeeperException,InterruptedException {
      zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
   }

   public static void main(String[] args) {

      String path = "/MyFirstZnode";

      // data in byte array
      byte[] data = "My first zookeeper app".getBytes();

      try {
         zk = new ZooKeeperConnection().connect("127.0.0.1");
         create(path, data);
         conn.close();
      } catch (Exception e) {
         System.out.println("error"+e.getMessage());
      }
   }
}