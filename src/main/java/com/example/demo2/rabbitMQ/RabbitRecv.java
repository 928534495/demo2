package com.example.demo2.rabbitMQ;

import com.rabbitmq.client.*;

import java.io.Console;
import java.io.IOException;

public class RabbitRecv {
//接收者  rabbitMQ消息的接收者
    private final static String QUEUE_NAME = "q_test_01"; //消息接收的通道名
    public static void main(String[] args) throws IOException {
        // 获取到连接以及mq通道
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列 --- 队列的声明必须与生产者保持一致
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME, false, "/",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body)
                            throws IOException
                    {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        String message =new String(body);
                        long deliveryTag = envelope.getDeliveryTag();
                        // (process the message components here ...)

                        System.out.println("routingKey="+routingKey+"  message="+message+"  contentType="+contentType +"  deliveryTag="+deliveryTag);
                        channel.basicAck(deliveryTag, false);

                    }
                });
    }

}
