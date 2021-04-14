package com.example.demo2.rabbitMQ.subscription;

import com.example.demo2.rabbitMQ.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RabbitProducerSub {
//生产者，负责消息的发送  --第三种 订阅模式或者叫广播  方式

   /* 1、1个生产者，多个消费者
     2、每一个消费者都有自己的一个队列
     3、生产者没有将消息直接发送到队列，而是发送到了交换机
     4、每个队列都要绑定到交换机
     5、生产者发送的消息，经过交换机，到达队列，实现，一个消息被多个消费者获取的目的
    注意：一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费
    注意：消息发送到没有队列绑定的交换机时，消息将丢失，因为，交换机没有存储消息的能力，消息只能存在在队列中。
*/
    private final static String EXCHANGE_NAME = "q_sub2_01"; //创建交换机名称

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionSubUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();


        // 声明exchange交换机和类型 fanout 为广播类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");


        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "Hello World999!=="+i;
            channel.basicPublish(EXCHANGE_NAME,"", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            //线程休眠
            Thread.sleep(10);
        }



        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
