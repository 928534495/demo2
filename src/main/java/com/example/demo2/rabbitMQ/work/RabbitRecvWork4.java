package com.example.demo2.rabbitMQ.work;

import com.example.demo2.rabbitMQ.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitRecvWork4 {
//接收者  rabbitMQ消息的接收者  --第二种 work 方式  2，多劳多得分配模式
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
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        channel.basicQos(1);  //每次只能消费一个消息


        /**
         * 启动一个消费者，并返回服务端生成的消费者标识
         * queue:队列名
         * autoAck：true 接收到传递过来的消息后acknowledged（应答服务器），false 接收到消息后不应答服务器
         * * consumerTag:客户端生成的一个消费者标识
         * callback: 消费者对象的回调接口
         * @return 服务端生成的消费者标识
         */


        channel.basicConsume(QUEUE_NAME, false,//false为手动确认，true为自动确认 消息是否消费
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
