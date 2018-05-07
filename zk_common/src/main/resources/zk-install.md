#搭建步骤：
1、官网下载zookeeper安装包：
tickTime：这个时间是作为 Zookeeper 服务器之间或客户端与服务器之间维持心跳的时间间隔，也就是每个 tickTime 时间就会发送一个心跳。
dataDir：顾名思义就是 Zookeeper 保存数据的目录，默认情况下，Zookeeper 将写数据的日志文件也保存在这个目录里。
clientPort：这个端口就是客户端连接 Zookeeper 服务器的端口，Zookeeper 会监听这个端口，接受客户端的访问请求。
initLimit:集群中的follower服务器(F)与leader服务器(L)之间初始连接时能容忍的最多心跳数（tickTime的数量）
syncLimit:集群中的follower服务器与leader服务器之间请求和应答之间能容忍的最多心跳数（tickTime的数量）。

#启动zookeeper，windows下执行zookeeper-3.4.6\bin >zkServer.cmd；
linux下ookeeper-3.4.6\bin >zkServer.sh start启动

1.配置文件zoo.cfg:zookeeper安装目录下，copy一个cfg文件，重命名为zoo.cfg
```
# The number of milliseconds of each tick
tickTime=2000
# The number of ticks that the initial
# synchronization phase can take
initLimit=10
# The number of ticks that can pass between
# sending a request and getting an acknowledgement
syncLimit=5
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just
# example sakes.
dataDir=D:/ruanjain/zookeeper/service3/zookeeper-3.4.12/data/
# the port at which the clients will connect
clientPort=2181
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
server.1=localhost:2887:3887
server.2=localhost:2888:3888
server.3=localhost:2889:3889
```

#步骤：

```
1.配置zoo.cfg文件，如上
2.拷贝三个zookeeper实例，分别修改clientPort=2181，2182,2183
    配置不同的data路径，data下创建myid文件，分别配置server的别名：1、2、3：
    dataDir=D:/ruanjain/zookeeper/service3/zookeeper-3.4.12/data/
3.bin目录启动zkServer.cmd，前两个会抛异常，当最后一个启动成功之后会，集群搭建成功
4.前两个启动会异常，第三个启动成功后则集群搭建成功。
5.验证是否启动：zkCli.cmd -server 127.0.0.1:2181
```
#查询
```
zookeeper客户端操作：
https://www.cnblogs.com/sherrykid/p/5813148.html
1.zkCli.cmd
2.查询相关指令
ls path：列出path下的文件
get path：获取指定节点的内容
stat path：查看节点状态
3.创建指令
a.命令：create [-s] [-e] path data acl
  在根目录创建了node_1节点，携带数据 123
  1 [zk: 127.0.0.1:2181(CONNECTED) 10] create /node_1 123
  2 Created /node_1
  使用 get /node_1 验证是否添加节点及其数据成功
b.创建了一个临时节点（-e）
  create -e /node_1/node_1_1 234
  Created /node_1/node_1_1
  查看状态：stat /node_1/node_1_1
c.create -s /node_1/node_1_2 234
通过使用-s参数，创建一个顺序节点，我们虽然指定的节点名是node_1_1，但是实际上，名称却是 node_1_10000000001，如果我们重复执行：
-s 和 -e 可以同时使用
4.其他参数含义
cZxid:创建节点时的事务id
pZxid:子节点列表最后一次被修改的事务id
cversion:节点版本号
dataCersion:数据版本号
aclVerson:acl权限版本号
ephemeralOwner值不再是0，表示这个临时节点的版本号，如果是永久节点则其值为 0x0
5.退出：quit
6.修改相关指定：
 set path data [version]
7.删除指令：
  delete path [version]
  删除指定节点数据，其version参数的作用于set指定一致
  delete /node_1/node_1_10000000001
  整个节点全删除
  注意：delete只能删除不包含子节点的节点，如果要删除的节点包含子节点，使用rmr命令
  rmr /node_1
```
#操作示例
create /root 123
get /root

```
[zk: localhost:2181(CONNECTED) 1] get /root
My first zookeeper app
cZxid = 0x100000002
ctime = Mon May 07 16:29:48 CST 2018
mZxid = 0x100000002
mtime = Mon May 07 16:29:48 CST 2018
pZxid = 0x100000002
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 22
numChildren = 0
[zk: localhost:2181(CONNECTED) 2] ls
[zk: localhost:2181(CONNECTED) 3] ls/
ZooKeeper -server host:port cmd args
        stat path [watch]
        set path data [version]
        ls path [watch]
        delquota [-n|-b] path
        ls2 path [watch]
        setAcl path acl
        setquota -n|-b val path
        history
        redo cmdno
        printwatches on|off
        delete path [version]
        sync path
        listquota path
        rmr path
        get path [watch]
        create [-s] [-e] path data acl
        addauth scheme auth
        quit
        getAcl path
        close
        connect host:port
[zk: localhost:2181(CONNECTED) 4] stat /zookeeper
cZxid = 0x0
ctime = Thu Jan 01 08:00:00 CST 1970
mZxid = 0x0
mtime = Thu Jan 01 08:00:00 CST 1970
pZxid = 0x0
cversion = -1
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 0
numChildren = 1
```
