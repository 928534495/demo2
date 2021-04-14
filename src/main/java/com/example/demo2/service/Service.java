package com.example.demo2.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("ser")
public class Service {
private static  final Logger log =  LoggerFactory.getLogger(Service.class);

    @RequestMapping("/u")
@ResponseBody
    public String u(){
        System.out.println("ss");
log.info("log----");
        return "/web/we.jsp";
    }
    @RequestMapping("/p")
    public Object p(){
        System.out.println("s3s");
        return "/web/we.jsp";
    }

}
