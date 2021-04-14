package com.example.demo2.rabbitMQ.work;

import com.example.demo2.rabbitMQ.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RabbitProducerWork {
//生产者，负责消息的发送  --第二种 work  方式
    private final static String QUEUE_NAME = "q_test_01"; //创建通道名称

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

       /*  、QueueDeclare　　声明队列

        1 public static QueueDeclareOk QueueDeclare(String queue, Boolean durable, Boolean exclusive, Boolean autoDelete, IDictionary arguments);

       queue:声明的队列名称

        durable：是否持久化，是否将队列持久化到mnesia数据库中，有专门的表保存我们的队列声明。

        exclusive：排外，①当前定义的队列是connection的channel是共享的，其他的connection是访问不到的。②当connection关闭的时候，队列将被删除。

        autoDelete：自动删除，当最后一个consumer(消费者)断开之后，队列将自动删除。

        arguments：参数是rabbitmq的一个扩展，功能非常强大，基本是AMPQ中没有的。
*/

        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "Hello World999!=="+i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            //线程休眠
            Thread.sleep(10);
        }



        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
