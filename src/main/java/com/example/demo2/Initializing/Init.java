package com.example.demo2.Initializing;

import com.example.demo2.rabbitMQ.ConnectionUtil;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
//继承这个类ApplicationRunner 调用run方法 可以在 spring  启动时执行某个方法
@Component
public class Init implements ApplicationRunner {
    private final static String QUEUE_NAME = "q_test_01";
    private  static final Logger log= LoggerFactory.getLogger(Init.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("ong ===RabbitMQ=====");
        // 获取到连接以及mq通道
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明（创建）队列
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, "/",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
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
