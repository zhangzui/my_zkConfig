package com.zz.zk.common.api;

/**
 * @author zhangzuizui
 * @date 2018/5/7 10:41
 */
public interface ZookeeperApi {
    String getData(String path);
    String deleteData(String path);
    String setData(String path);
    String updateData(String path);
}
