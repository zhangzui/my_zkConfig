# my_zkConfig——ZK动态管理配置

#Zookeeper简介,核心内容有哪些？
1.Zookeeper简介:
    一句话：zk是分布式一致性协调的中间件，开源组织Apache Hadoop下的一个子项目,它主要是用来解决分布式
应用中一些数据管理问题.如:统一命名服务、状态同步服务、集群管理、分布式应用配置项的管理等
2.核心内容
    概括分布式文件系统+通知机制
    1.文件系统：维护的一个类似文件目录的数据结构，可以对节点上的文件进行曾删改查（节点状态有四种，持久，持久加顺序编号，临时，和临时加顺序编号）。
    2.通知机制：客户端注册监听它关心的目录节点,当目录节点发生变化(数据改变、被删除、子目录节点增加删除)
    时,zookeeper 会通知客户端。
3.核心机制：
    1.集群选举机制：数据一致性和paxos算法：
    在一个分布式数据库系统中,如果各节点的初始状态一致,每个节点都执行相同的操作序列,那么他们最后能得到一个一致的状态。
    2.原子广播:
    实现这个机制的协议叫做Zab 协议。Zab 协议有两种模式,它们分别是恢复模式(选主)和广播模式(同步)
    3.同步流程
#本地Zookeeper伪集群搭建：
1.安装ZK服务端，copy 三个以上奇数server 配置zoo.cfg文件，
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

2.拷贝zookeeper实例，分别修改clientPort=2181，2182,2183
    配置不同的data路径，data下创建myid文件，分别配置server的别名：1、2、3：
    dataDir=D:/ruanjain/zookeeper/service3/zookeeper-3.4.12/data/
3.bin目录启动zkServer.cmd，前两个会抛异常，当最后一个启动成功之后会，集群搭建成功;
4.前两个启动会异常，第三个启动成功后则集群搭建成功。
5.验证是否启动：zkCli.cmd -server 127.0.0.1:2181

#Zookeeper的工作原理

     1.集群选举机制：数据一致性和paxos算法：
        在一个分布式数据库系统中,如果各节点的初始状态一致,每个节点都执行相同的操作序列,那么他们最后能得到一个一致的状态。
     2.原子广播:
        实现这个机制的协议叫做Zab 协议。Zab 协议有两种模式,它们分别是恢复模式(选主)和广播模式(同步)
     3.同步流程

#zookeeper可以用于哪些场景？
    a.分布式配置中心

    b.订阅与发布

    c.命名服务

    d.分布式锁
    e.分布式队列
    f.集群管理
#学习基于zookeeper的分布式配置管理系统开源中间件，架构设计
    a.百度DisConfig
    b.携程的Apollo
    c.阿里的Diamond
#自己搭建一个my_config组件

#参考资料：
1.百度DisConfig：
https://blog.csdn.net/t_6666/article/details/51658589
2.携程的Apollo
3.阿里的Diamond


https://blog.csdn.net/dajiangtai007/article/details/68488701
https://www.processon.com/diagrams/new?category=flow#temp-system
