# message queue

- producer/consumer vs. publisher/subscriber  
这两个其实没什么好比的，本质上都是一个生产消息，一个消费消息。在message queue领域，有两种典型的消息处理模式：
queue和sub-pub，queue比较原始，一条消息被消费者拿到之后就从queue中删除，也就是说message不能递送给多个消费者或重复消费；
sub-pub为了解决这个问题，引入topic的概念，其实还是一个queue，只不过消息被消费的时候不从queue中删除，这就允许多个消费者
消费同一条消息。  
常说的producer/consumer就message queue模式，PUB-SUB是topic模式，现在流行的MQ中间件都支持SUB/PUB模式，但rabbitMQ是个例外。

- PUB/SUB模式中，如何保证消息可被多个消费者消费的同时又能兼顾消费者的水平扩展能力  
对消费者的管理引入group的概念，每个group相当于一个subscribe，同一条消息会被每个group拿到，
但是同一个group内的不同消费者只能拿到不同的消息。

- 如何保证消息不丢失  
应用层ACK, 需要client配合。

- 消息递送的三个可靠性等级，exactly ONCE如何实现  
分别是at most once, at least once, exactly once. 

- kafka如何实现高性能吞吐的  
    * sequence write
    * async
    * batch
    * directly use of system cache

- Kafka消息在磁盘上的存储结构是怎样的


- Kafka中broker、node、topic、partition、leader、controller这些概念是什么意思，相互之间有什么关系？


- kafka 如何决定每条消息写入哪一个partition


- 如果producer要写消息到不同partition，需要重新建立TCP连接吗  


- Kafka producer写一条消息后，server在什么时候返回ACK(具体步骤)  


- kafka consumer poll到一条消息后，没有发送ACK, 然后再次poll，会再次拿到这条消息吗？  
不会，会正常的拿到下一条消息（如果有）。因为committed offset只会在发生rebalance的时候才会用到，正常的poll情况下，client会递增poll的offset。
在rebalance的时候，如果某consumer所属group没有committed offset，则根据auto.offset.reset的值决定，默认是返回latest的消息。
（需要注意rebalance之后可能拿到消费过但尚未commit的消息）

- 如何决定consumer group中的member数量  
Kafka中最大可同时POLL的group member数量等于topic partition数量。

- Kafka如何判断一个consumer已经死掉了  
consumer调用`poll()`方法的间隔超过`max.poll.interval.ms` || hearbeats 间隔超过 `session.timeout.ms`.   
The `max.poll.interval.ms` configuration is used to prevent a `livelock`, where the application did not crash but fails to make progress for some reason.

- Kafka rebalance是什么  
Kafka限制任意时间每个consumer group中有且只有一个member可以poll得到message，当这个member离开group，Kafka会进行rebalance操作，
把离开member关联的partition分给其他subscribed members，并且是从这些partition的committed offset之后的消息开始发送给新的member。  
rebalance主要是为了提高Kafka consumer的可用性。


- 如果想要容忍3个节点的失效，Kafka集群应该至少部署几个节点，为什么


- Kafka如何应对所有IN-SYNC节点都失效了的情况


- zookeeper在Kafka中扮演什么角色，为什么client连接server时不是连接zookeeper  



