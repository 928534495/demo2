package com.example.demo2.SpringRabbitMQ;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;


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
 * 在spring容器中构建SimpleMessageListenerContainer来消费消息，我们也可以使用@RabbitListener来消费消息。
 *@RabbitListener注解指定目标方法来作为消费消息的方法，通过注解参数指定所监听的队列或者Binding。
 * 使用@RabbitListener可以设置一个自己明确默认值的RabbitListenerContainerFactory对象。
 *
 * 作者：二月_春风
 * 链接：https://www.jianshu.com/p/382d6f609697
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *消费者类中通过@RabbitListener和@RabbitHandler注解将一个方法定义为消息监听的方法，使用方法如下
 *
 * @RabbitListener可以通过定义bindings={@QueueBinding}，
 * @QueueBinding可以通过赋值value=@Queue(value = RabbitConfig.FANOUT_QUEUE_NAME, durable = "true")定义出消息队列名，
 * @QueueBinding也可以通过赋值exchange=@Exchange(value = RabbitConfig.TEST_FANOUT_EXCHANGE, type = "fanout")来定义当前方法所监听的Exchange，以及类型，类型默认是direct
 *
 *
 *
 *
 * @author xuefl
 * @version 5.0 since 2020-01-02
 */
@Component

public class MsgConsumer {

    private static final Logger log = LoggerFactory.getLogger(MsgConsumer.class);

    @RabbitListener(
            bindings =
                    {
                            @QueueBinding(value = @Queue(value = RabbitConfig.FANOUT_QUEUE_NAME, durable = "true"),
                                    exchange = @Exchange(value = RabbitConfig.TEST_FANOUT_EXCHANGE, type = "fanout"))
                    })
    @RabbitHandler
    public void processFanoutMsg(Message massage) throws InterruptedException {
        Thread.sleep(5000);
        String msg = new String(massage.getBody(), StandardCharsets.UTF_8);
        log.info("收到 广播 消息 received Fanout message : " + msg);
    }

    @RabbitListener(
            bindings =
                    {
                            @QueueBinding(value = @Queue(value = RabbitConfig.FANOUT_QUEUE_NAME1, durable = "true"),
                                    exchange = @Exchange(value = RabbitConfig.TEST_FANOUT_EXCHANGE, type = "fanout"))
                    })
    @RabbitHandler
    public void processFanout1Msg(Message massage) {
        String msg = new String(massage.getBody(), StandardCharsets.UTF_8);
        log.info("收到 广播1 消息 received Fanout1 message : " + msg);
    }

    @RabbitListener(
            bindings =
                    {
                            @QueueBinding(value = @Queue(value = RabbitConfig.DIRECT_QUEUE_NAME, durable = "true"),
                                    exchange = @Exchange(value = RabbitConfig.TEST_DIRECT_EXCHANGE),
                                    key = RabbitConfig.DIRECT_ROUTINGKEY)
                    })
    @RabbitHandler
    public void processDirectMsg(Message massage) {
        String msg = new String(massage.getBody(), StandardCharsets.UTF_8);
        log.info("收到 路由 消息received Direct message : " + msg);
    }

    @RabbitListener(
            bindings =
                    {
                            @QueueBinding(value = @Queue(value = RabbitConfig.TOPIC_QUEUE_NAME, durable = "true"),
                                    exchange = @Exchange(value = RabbitConfig.TEST_TOPIC_EXCHANGE, type = "topic"),
                                    key = RabbitConfig.TOPIC_ROUTINGKEY)
                    })
    @RabbitHandler
    public void processTopicMsg(Message massage) {
        String msg = new String(massage.getBody(), StandardCharsets.UTF_8);
        log.info("收到 动态路由 消息 received Topic message : " + msg);
    }

    @RabbitListener(queues = {RabbitConfig.DIRECT_QUEUE_NAME})
    @RabbitHandler
    public void receiveMessage(String message, Message originalMessage) throws JsonProcessingException {
        Map<String, Object> headers = originalMessage.getMessageProperties().getHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        String headersParam = objectMapper.writeValueAsString(headers);
        log.info("direct consumer receive the message:{},original message:{},\n headers param:{}", message, originalMessage.toString(), headersParam);
    }

}
