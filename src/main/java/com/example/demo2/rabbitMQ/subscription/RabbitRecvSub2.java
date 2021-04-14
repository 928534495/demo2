package com.example.demo2.rabbitMQ.subscription;

import com.example.demo2.rabbitMQ.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitRecvSub2 {
//接收者  rabbitMQ消息的接收者  --第三种 订阅模式或者叫广播  方式
    private final static String QUEUE_NAME = "q_test_03"; //消息接收的通道名
    private final static String EXCHANGE_NAME = "q_sub2_01"; //交换机
    public static void main(String[] args) throws IOException {
        // 获取到连接以及mq通道
        Connection connection = null;
        try {
            connection = ConnectionSubUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列 --- 队列的声明必须与生产者保持一致
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //通道绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        channel.basicQos(1);  //每次只能消费一个消息

        /**
         * 启动一个消费者，并返回服务端生成的消费者标识
         * queue:队列名
         * autoAck：true 接收到传递过来的消息后acknowledged（应答服务器），false 接收到消息后不应答服务器
         * * consumerTag:客户端生成的一个消费者标识
         * callback: 消费者对象的回调接口
         * @return 服务端生成的消费者标识
         */


        channel.basicConsume(QUEUE_NAME, false, //false为手动确认，true为自动确认 消息是否消费
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body)
                            throws IOException
                    {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        String message =new String(body);
                        long deliveryTag = envelope.getDeliveryTag();  //获取消息的标志
                        // (process the message components here ...)
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("routingKey="+routingKey+"  message="+message+"  contentType="+contentType +"  deliveryTag="+deliveryTag);
                        //消息确认   --参数1 确认队列中哪个消息使用消息标志进行确认，参数2 是否开启多个消息同时确认
                        channel.basicAck(deliveryTag, false);


                    }
                });
    }

}
