package com.example.demo2.rabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;
//import com.rabbitmq.client.QueueingConsumer;

public class Recv {

    private final static String QUEUE_NAME = "q_test_01";

    public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 定义队列的消费者
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        DefaultConsumer   consumer = new DefaultConsumer(channel);
        // 监听队列
//        channel.basicConsume(QUEUE_NAME, true, consumer);
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);
                System.out.println("Received: " + message);
                // 消息确认
                try {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

// 关闭自动消息确认，autoAck = false
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
