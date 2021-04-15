package com.example.demo2.SpringRabbitMQ;

//import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/rabbit")
@Controller
//@Api(value = "SwaggerValue", tags={"RabbitMQController"},description = "swagger应用",  produces = MediaType.APPLICATION_JSON_VALUE)
public class RabbitMQController {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQController.class);

    @Autowired
    private MsgProducer msgProducer;

    @GetMapping(value = "/sendFanout")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void sendMsg(){
        msgProducer.send2FanoutTestQueue("this is a test fanout message!");
    }

    @GetMapping(value = "/sendDirect")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void sendDirectMsg(){
        msgProducer.send2DirectTestQueue("this is a test direct message!");
    }

    @GetMapping(value = "/sendTopicA")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void sendTopicAMsg(){
        msgProducer.send2TopicTestAQueue("this is a test topic aaa message!");
    }

    @GetMapping(value = "/sendTopicB")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void sendTopicBMsg(){
        msgProducer.send2TopicTestBQueue("this is a test topic bbb message!");
    }

    @GetMapping(value = "/send")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void send(){
        msgProducer.sendMessageWithProperties("发送消息时往请求头添加信息this is a test topic bbb message!");
    }
}
