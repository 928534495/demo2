package com.example.demo2.SpringRabbitMQ;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 类功能描述：<br>
 * <ul>
 * <li>类功能描述1<br>
 * <li>类功能描述2<br>
 * <li>类功能描述3<br>
 * </ul>
 * 修改记录：<br>
 * <ul>
 * <li>修改记录描述1<br>
 * <li>修改记录描述2<br>
 * <li>修改记录描述3<br>
 * </ul>
 *
 * @author xuefl
 * @version 5.0 since 2020-01-02
 */
@Component
public class MsgProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send2FanoutTestQueue(String massage){
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_FANOUT_EXCHANGE,
                "", massage);
    }

    public void send2DirectTestQueue(String massage){
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_DIRECT_EXCHANGE,
                RabbitConfig.DIRECT_ROUTINGKEY, massage);
    }

    public void send2TopicTestAQueue(String massage){
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_TOPIC_EXCHANGE,
                "test.aaa", massage);
    }

    public void send2TopicTestBQueue(String massage){
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_TOPIC_EXCHANGE,
                "test.bbb", massage);
    }

    /**
     * 发送消息时往请求头添加信息
     * @param message
     */
    public void sendMessageWithProperties(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE_NAME,
                RabbitConfig.DIRECT_ROUTING_KEY_NAME,
                message,
                originalMessage -> {
                    MessageProperties messageProperties = originalMessage.getMessageProperties();
                    messageProperties.setHeader("X-TOKEN", UUID.randomUUID().toString());
                    return originalMessage;
                });
    }

}

