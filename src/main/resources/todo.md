# 并发/IO
- 并发写同一个socket会怎样
- 并发写同一个file会怎样
- 同一个进程的不同线程如何通信
- 硬中断和软中断是什么
- memcached client 使用的IO线程模型
- kafka client 中使用的IO线程模型
- tomcat 中使用的IO线程模型


# network
- TCP 滑动窗口如何实现的
- socket 发送队列和对端接受队列一样大吗？
- socket read/write 的阻塞和非阻塞模式是如何实现的
- SO_RCVBUF、SO_SNDBUF是什么，在linux上如何查看
- TCP keepalive(SO_KEEPALIVE) 是什么，http keepalive呢
- SO_LINGER、TCP_NODELAY 是什么
- 多个应用层请求会放到同一个tcp报文发送吗
- 为什么SYNC 攻击会导致网络瘫痪，但是CPU负载却不高


# 性能指标
- memcached单个节点的读写上限是多少
- mysql简单读写TPS上限
- 机械硬盘顺序读写上限
- 机械硬盘随机读写上限
- SSD顺序读写上限
- SSD随机读写上限


# OO设计思想
- memcached client中的的OO设计
- kafka client 中的OO设计
- tomcat 中的OO设计