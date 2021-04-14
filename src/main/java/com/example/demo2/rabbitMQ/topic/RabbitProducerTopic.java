package com.example.demo2.rabbitMQ.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RabbitProducerTopic {
//生产者，负责消息的发送  --第五种 动态路由模式  方式

    /* 1、1个生产者，多个消费者
     在direct类型 不同的消息被不同的消费者消费
     1.2队列与交换机绑定不是任意绑定，需要指定一个routingKey  (路由key)
     1.3消息的发送方在向交换机Exchange发送消息时必须指定消息的RoutingKey
     1.4 只有队列的RoutingKey与消息的RoutingKey完全一致才能收到消息
 */
    private final static String EXCHANGE_NAME = "q_topic_01"; //创建交换机名称

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionRoutUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();


        // 声明exchange交换机和类型 topic 为路由类型
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

//        String routingKey = "ab.cd";
//        String routingKey= "ef.ab.cd";
        String routingKey= "ab.cd.ef";

//        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "Hello World999!==" ;
            //第一个参数 交换机；第二个参数  消息key
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            //线程休眠
//            Thread.sleep(10);
//        }


        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
